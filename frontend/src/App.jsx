import React from 'react'
import Login from './components/Pages/Login'
import SignUp from './components/Pages/SignUp'
import { BrowserRouter, Route, Routes } from 'react-router'
import ForgotPassword from './components/Pages/ForgotPassword'


function App() {
  return (
    <>
    <BrowserRouter>
    <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/signup" element={<SignUp/>} />
      <Route path="/forgotPassword" element={<ForgotPassword/>}/>
    </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
