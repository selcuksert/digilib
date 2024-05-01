// noinspection JSUnresolvedReference

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faBookOpenReader, faTrash} from '@fortawesome/free-solid-svg-icons';
import {useEffect, useState} from "react";
import classes from "../styles/Library.module.scss";

export default function Library() {

    const [books, setBooks] = useState([]);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [fetching, setFetching] = useState(false);

    useEffect(() => {
        getAddedBooks();
    }, []);

    const getAddedBooks = () => {
        setBooks([]);
        setFetching(true);
        fetch(`${import.meta.env.DIGILIB_API_URL}/inventory/added`).then(res => res.json())
            .then(res => Object.keys(res).length > 0 ?
                res : Promise.reject(new Error(`No book found in library!`)))
            .then((res) => {
                setBooks(res);
            }).catch((error) => {
            setError(true);
            setErrorMsg(error?.message);
        }).finally(() => {
            setFetching(false);
        });
    }

    const deleteBook = (isbn) => {
        setBooks([]);
        setFetching(true);
        fetch(`${import.meta.env.DIGILIB_API_URL}/inventory/${isbn}`, {
            method: 'DELETE'
        }).then(_ => {
            getAddedBooks();
        }).catch((error) => {
            setError(true);
            setErrorMsg(error?.message);
        }).finally(() => {
            setFetching(false);
        });
    }

    return (
        <div className="container-lg mt-5">
            <div className="h-100 p-5 bg-body-tertiary border rounded-3">
                <h2>My Library <span className={`${classes.readerIcon} text-primary`}><FontAwesomeIcon
                    icon={faBookOpenReader}
                    onClick={getAddedBooks}/></span></h2>
                <p>
                    <span>Following is the list of books you added to your digital library (To refresh list please click on the reader icon above):</span>
                </p>
                {fetching ?
                    <div className="d-flex align-items-center">
                        <strong>Loading...</strong>
                        <output className="spinner-border text-secondary ms-auto" aria-hidden="true"></output>
                    </div>
                    :
                    <div className="table-responsive">
                        <table className="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">ISBN</th>
                                <th scope="col">Title</th>
                                <th scope="col">Author(s)</th>
                                <th scope="col">Added At</th>
                                <th scope="col">Remove from Library</th>
                            </tr>
                            </thead>
                            <tbody className="table-group-divider">
                            {books.map((book, index) => (
                                <tr key={book?.isbn}>
                                    <th scope="row">{index + 1}</th>
                                    <td>{book?.isbn.replace('ISBN:', "")}</td>
                                    <td><a href={book?.url} target="_blank">{book?.title}</a></td>
                                    <td>{book?.authors.join(", ")}</td>
                                    <td>{book.updatedAt}</td>
                                    <td className={`${classes.trashIcon} text-danger`}
                                        onClick={() => deleteBook(book?.isbn)}>
                                        <FontAwesomeIcon icon={faTrash}/>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                }

                {error ?
                    <div className={classes.invalidMessage}>
                        {errorMsg}
                    </div> :
                    <></>
                }
            </div>
        </div>
    )
        ;
}

