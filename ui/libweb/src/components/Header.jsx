import classes from '../styles/Header.module.css';

export default function Header({headerBrandText}) {
    return (
        <nav className="navbar sticky-top navbar-dark bg-primary navbar-expand-lg">
            <div className="container-fluid">
                <a className="navbar-brand" href="/">
                    <img src="/library.svg" alt="Logo" width="30" height="24"
                         className="d-inline-block align-text-top"/><span className={classes.navbarTxt}>{headerBrandText}</span></a>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <a className="nav-link active" aria-current="page" href="/">My Library</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/add">Add Book</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/remove">Remove Book</a>
                        </li>
                        <li className="nav-item">
                            <a className="nav-link" href="/search">Search Book</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}