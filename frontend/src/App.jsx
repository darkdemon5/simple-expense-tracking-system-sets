import React from "react";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import { BrowserRouter, Route, Routes } from "react-router";
import ForgotPassword from "./pages/ForgotPassword";
import Landing from "./pages/Landing";
import { Toaster } from "sonner";
import { ToastProvider } from "@heroui/react";

function App() {
  return (
    <>
      {/* <Toaster position='top-center' richColors /> */}
      <ToastProvider
        toastProps={{
          radius: "medium",
          variant: "flat",
          timeout: 1000,
          hideIcon: true,
          classNames: {
            closeButton:
              "opacity-100 absolute right-4 top-1/2 -translate-y-1/2",
          },
        }}
      />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/forgotPassword" element={<ForgotPassword />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
