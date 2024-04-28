import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/js/src/collapse.js'
import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Search from "./routes/Search.jsx";
import Main from "./routes/Main.jsx";
import Library from "./routes/Library.jsx";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Main/>}>
                    <Route
                        path="library"
                        element={<Library/>}
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