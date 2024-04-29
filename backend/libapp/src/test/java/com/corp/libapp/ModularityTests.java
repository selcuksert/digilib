package com.corp.libapp;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

    ApplicationModules modules = ApplicationModules.of(DigilibApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Test
    @SuppressWarnings("java:S2699")
    void createModuleDocumentation() {
        new Documenter(modules).writeDocumentation();
    }

}
