import React from "react";
import Navbar from "./components/Navbar";
import Landing from "./pages/Landing";
import Login from "./pages/Login";
import { BrowserRouter, Route, Routes } from "react-router";
import SignUp from "./pages/SignUp";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          {/* <Route path="/forgotPassword" element={<ForgotPassword />} /> */}
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
