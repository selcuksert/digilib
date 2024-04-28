import classes from '../styles/Header.module.scss';
import {NavLink} from "react-router-dom";
import PropTypes from "prop-types";

export default function Header({headerBrandText}) {
    return (
        <nav className="navbar sticky-top navbar-dark bg-primary navbar-expand-lg">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">
                    <img src="/library.svg" alt="Logo" width="30" height="24"
                         className="d-inline-block align-text-top"/><span
                    className={classes.navbarTxt}>{headerBrandText}</span></a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink className={({isActive}) =>
                                isActive ? "nav-link active" : "nav-link"}
                                     aria-current="page" to={`/library`}>My Library</NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className={({isActive}) =>
                                isActive ? "nav-link active" : "nav-link"}
                                     aria-current="page" to={`/search`}>Search Book</NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

Header.propTypes = {
    headerBrandText: PropTypes.string
};
