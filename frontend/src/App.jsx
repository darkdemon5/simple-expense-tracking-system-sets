import React from "react";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import { BrowserRouter, Route, Routes } from "react-router";
import ForgotPassword from "./pages/ForgotPassword";
import Landing from "./pages/Landing";
import { HeroUIProvider } from "@heroui/react";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/forgotPassword" element={<ForgotPassword />} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
