import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/js/src/collapse.js'
import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Portal from "./Portal.jsx";
import Search from "./routes/Search.jsx";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Portal/>}>
                    <Route
                        path="search"
                        element={<Search/>}
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}