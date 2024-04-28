// noinspection JSUnresolvedReference

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faInfoCircle} from '@fortawesome/free-solid-svg-icons';

export default function Banner() {

    return (
        <div className="container-fluid mt-3">
            <div className="card border-info-subtle">
                <div className="card-body text-secondary">
                    <FontAwesomeIcon icon={faInfoCircle} className="me-1"/>
                    <span>Welcome to your digital library! Use links on header to navigate through the portal. This application uses </span>
                    <a href="https://openlibrary.org/" target="_blank">Open
                        Library</a> as book info database.
                </div>
            </div>
        </div>
    );
}

