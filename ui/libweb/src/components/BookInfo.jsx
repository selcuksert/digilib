import PropTypes from "prop-types";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faCircleMinus, faCirclePlus} from '@fortawesome/free-solid-svg-icons';
import {useEffect, useState} from "react";

export default function BookInfo({
                                     loaded,
                                     image,
                                     title,
                                     subTitle,
                                     authors,
                                     isbn,
                                     subjects,
                                     publishers,
                                     publishDate,
                                     bookUrl,
                                     noOfPages
                                 }) {

    const [exists, setExists] = useState(false);
    const [fetching, setFetching] = useState(false);

    const addBook = (isbn) => {
        setFetching(true);
        fetch(`${import.meta.env.DIGILIB_BASE_URL}/book/${isbn}`, {
            method: 'POST',
        })
            .then(_ => setExists(true))
            .finally(() => setFetching(false));
    }

    const deleteBook = (isbn) => {
        setFetching(true);
        fetch(`${import.meta.env.DIGILIB_BASE_URL}/book/${isbn}`, {
            method: 'DELETE'
        })
            .then(res => res.json())
            .then(_ => setExists(false))
            .finally(() => setFetching(false));
    }

    const checkBookAddedToLibrary = (isbn) => {
        if (!loaded) {
            return;
        }

        fetch(`${import.meta.env.DIGILIB_BASE_URL}/inventory/exists/${isbn}`)
            .then(res => res.json())
            .then(res => setExists(res));
    }

    useEffect(() => {
        checkBookAddedToLibrary(isbn);
    }, [loaded]);

    return (
        loaded ?
            <div className="card mb-3">
                <div className="row g-0">
                    <div className="col-md-2 mt-2">
                        <div className="ms-2">
                            <img src={image} className="img-fluid rounded mx-auto d-block" alt="book"></img>
                            <div className="mt-2 mb-2 d-grid gap-3 mx-auto">
                                {exists ?
                                    <button type="button" className="btn btn-outline-danger btn-sm"
                                            onClick={() => deleteBook(isbn)}
                                            disabled={fetching}>
                                        <FontAwesomeIcon
                                            icon={faCircleMinus}/> Remove from library
                                    </button> :
                                    <button type="button" className="btn btn-outline-primary btn-sm"
                                            onClick={() => addBook(isbn)}
                                            disabled={fetching}><FontAwesomeIcon
                                        icon={faCirclePlus}/> Add to library
                                    </button>
                                }
                            </div>
                        </div>
                    </div>
                    <div className="col-md-10">
                        <div className="card-body">
                            <h5 className="card-title"><a href={bookUrl} target="_blank">{title}</a></h5>
                            <p className="card-text">{subTitle}</p>
                            <p className="card-text"><small
                                className="text-body-secondary">{authors.join(", ")}</small>
                            </p>
                        </div>
                        <ul className="list-group list-group-flush">
                            <li className="list-group-item"><span
                                style={{fontWeight: "bold"}}>ISBN: </span>{isbn}
                            </li>
                            <li className="list-group-item"><span
                                style={{fontWeight: "bold"}}>Publish Date: </span>{publishDate}</li>
                            <li className="list-group-item"><span
                                style={{fontWeight: "bold"}}>Number of Pages: </span>{noOfPages}</li>
                            <li className="list-group-item">
                                <span style={{fontWeight: "bold"}}>Publishers: </span>
                                {
                                    publishers?.map((publisher) =>
                                        <span
                                            key={publisher}
                                            className="badge rounded-pill text-bg-secondary me-1">{publisher?.name}</span>)
                                }
                            </li>
                            <li className="list-group-item">
                                <span style={{fontWeight: "bold"}}>Subjects: </span>
                                {
                                    subjects?.map((subject) =>
                                        <span
                                            key={subject.name}
                                            className="badge rounded-pill text-bg-info me-1">{subject?.name}</span>)
                                }
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            :
            <></>

    )
}

BookInfo.propTypes = {
    loaded: PropTypes.bool,
    image: PropTypes.string,
    title: PropTypes.string,
    subTitle: PropTypes.string,
    authors: PropTypes.arrayOf(PropTypes.string),
    isbn: PropTypes.string,
    subjects: PropTypes.arrayOf(PropTypes.shape({})),
    publishers: PropTypes.arrayOf(PropTypes.shape({})),
    publishDate: PropTypes.string,
    bookUrl: PropTypes.string,
    noOfPages: PropTypes.number,
};