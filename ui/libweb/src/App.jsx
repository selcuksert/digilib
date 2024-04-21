import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/js/src/collapse.js'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Portal from "./Portal.jsx";
import Search from "./routes/Search.jsx";
import Add from "./routes/Add.jsx";
import Remove from "./routes/Remove.jsx";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Portal/>}>
                    <Route
                        path="add"
                        element={<Add/>}
                    />
                    <Route
                        path="remove"
                        element={<Remove/>}
                    />
                    <Route
                        path="search"
                        element={<Search/>}
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    )
}