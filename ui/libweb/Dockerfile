FROM node:21.6-alpine AS base
LABEL author="Selcuk SERT"
LABEL mail="selcuk.sert@gmail.com"
LABEL version="1.0"
LABEL description="Builder image for diglib web app"

# Install dependencies only when needed
FROM base AS deps
# Check https://github.com/nodejs/docker-node/tree/b4117f9333da4138b03a546ec926ef50a31506c3#nodealpine to understand why libc6-compat might be needed.
RUN apk add --no-cache libc6-compat
WORKDIR /app

# Install dependencies based on the preferred package manager
COPY package.json yarn.lock* package-lock.json* pnpm-lock.yaml* ./
RUN \
  if [ -f yarn.lock ]; then yarn --frozen-lockfile; \
  elif [ -f package-lock.json ]; then npm ci; \
  elif [ -f pnpm-lock.yaml ]; then yarn global add pnpm && pnpm i --frozen-lockfile; \
  else echo "Lockfile not found." && exit 1; \
  fi

# Rebuild the source code only when needed
FROM base AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .

RUN yarn build

# Production image, copy all the files and run next
FROM nginx:stable-alpine AS runner
WORKDIR /app

RUN addgroup --system --gid 1001 appgrp
RUN adduser --system --uid 1001 appuser -G appgrp

COPY --from=builder --chown=appuser:appgrp /app/dist /usr/share/nginx/html
COPY ./default.conf.template /etc/nginx/templates/default.conf.template
COPY ./nginx.conf /etc/nginx/nginx.conf

RUN chown -R appuser:appgrp /etc/nginx/conf.d

USER appuser

EXPOSE 3000