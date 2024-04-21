import Header from "./components/Header.jsx";
import {Outlet} from "react-router-dom";
import Footer from "./components/Footer.jsx";

export default function Portal() {
    return (
        <main>
            <Header headerBrandText={`Digital Library`}/>
            <Outlet/>
            <Footer copyrightText={`Selcuk SERT`}/>
        </main>
    )
}