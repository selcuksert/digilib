import Header from "../components/Header.jsx";
import {BrowserRouter as Router, Outlet, Route, Routes} from "react-router-dom";
import Footer from "../components/Footer.jsx";
import Banner from "./Banner.jsx";
import Library from "./Library.jsx";
import Search from "./Search.jsx";

export default function Layout() {
    return (
        <Router basename="/">
            <Header headerBrandText={`Digital Library`}/>
            <Banner/>
            <Outlet/>
            <Footer copyrightText={`Selcuk SERT`}/>
            <Routes>
                <Route
                    path="library"
                    element={<Library/>}
                />
                <Route
                    path="search"
                    element={<Search/>}
                />
            </Routes>
        </Router>
    )
}