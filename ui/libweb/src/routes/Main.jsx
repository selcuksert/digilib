import Header from "../components/Header.jsx";
import {Outlet} from "react-router-dom";
import Footer from "../components/Footer.jsx";
import Banner from "./Banner.jsx";

export default function Main() {
    return (
        <main>
            <Header headerBrandText={`Digital Library`}/>
            <Banner/>
            <Outlet/>
            <Footer copyrightText={`Selcuk SERT`}/>
        </main>
    )
}