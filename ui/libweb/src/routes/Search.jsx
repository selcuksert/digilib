// noinspection JSUnresolvedReference

import classes from '../styles/Search.module.scss';
import {useState} from "react";
import BookInfo from "../components/BookInfo.jsx";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBook, faSearch} from '@fortawesome/free-solid-svg-icons';

export default function Search() {

    const [isbn, setIsbn] = useState('');
    const [isbnTxt, setIsbnTxt] = useState('');
    const [imageSrc, setImageSrc] = useState('');
    const [bookLoaded, setBookLoaded] = useState(false);
    const [bookTitle, setBookTitle] = useState('');
    const [bookSubTitle, setBookSubTitle] = useState('');
    const [authors, setAuthors] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [publishers, setPublishers] = useState([]);
    const [publishDate, setPublishDate] = useState('');
    const [bookUrl, setBookUrl] = useState('');
    const [fetching, setFetching] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [noOfPages, setNoOfPages] = useState(0);

    const search = () => {
        setError(false);

        if (isbn.trim().length === 0) {
            setError(true);
            setErrorMsg("Please enter an ISBN!");
            setBookLoaded(false);
            return;
        }

        setFetching(true);
        setBookLoaded(false);

        fetch(`${import.meta.env.DIGILIB_BASE_URL}/book/${isbn}`).then(res => res.json())
            .then(res => Object.keys(res).length > 0 ?
                res : Promise.reject(new Error(`No book found in database for ISBN: ${isbn}`)))
            .then(res => res[`ISBN:` + isbn])
            .then(res => {
                setImageSrc(res?.cover?.medium);
                setBookTitle(res?.title);
                setBookSubTitle(res?.subtitle);

                let authors = res?.authors;
                let authorArray = []
                for (let author of authors) {
                    authorArray.push(author?.name);
                }
                setAuthors(authorArray);

                setSubjects(res?.subjects);
                setPublishers(res?.publishers);
                setPublishDate(res?.publish_date)
                setIsbnTxt(isbn);
                setBookUrl(res?.url);
                setNoOfPages(res?.number_of_pages);

                setBookLoaded(true);
            }).catch((error) => {
            setError(true);
            setErrorMsg(error?.message);
        }).finally(() => {
            setIsbn('');
            setFetching(false);
        })
    }

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            event.preventDefault();
            search();
        }
    }

    return (
        <div className="container-lg mt-5">
            <form className="row g-3 needs-validation" noValidate>
                <div className="h-100 p-5 bg-body-tertiary border rounded-3">
                    <h2>Search Book <FontAwesomeIcon icon={faBook}/></h2>
                    <p>
                        <span> database. Please enter ISBN (International Standard Book Number) of the book
                        you want to search for:</span>
                    </p>
                    <div className="form-check">
                        <div className="input-group mb-3">
                            <input type="text" className="form-control" placeholder="Enter ISBN of book to search"
                                   aria-label="ISBN" aria-describedby="search-button"
                                   value={isbn}
                                   onChange={e => setIsbn(e.target.value)}
                                   onKeyDown={handleKeyDown}
                            />
                            <button className="btn btn-primary" type="button"
                                    id="search-button"
                                    disabled={fetching}
                                    onClick={search}><FontAwesomeIcon icon={faSearch}/> Search
                            </button>
                        </div>
                        {error ?
                            <div className={classes.invalidMessage}>
                                {errorMsg}
                            </div> :
                            <></>
                        }
                        {fetching ?
                            <div className="d-flex align-items-center">
                                <strong>Loading...</strong>
                                <output className="spinner-border text-secondary ms-auto" aria-hidden="true"></output>
                            </div>
                            :
                            <></>
                        }
                        <BookInfo image={imageSrc} loaded={bookLoaded} title={bookTitle} subTitle={bookSubTitle}
                                  authors={authors} isbn={isbnTxt} subjects={subjects} publishers={publishers}
                                  publishDate={publishDate} bookUrl={bookUrl} noOfPages={noOfPages}/>
                    </div>
                </div>
            </form>
        </div>
    );
}

