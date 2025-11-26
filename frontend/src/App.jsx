import React from 'react'
import Login from './components/Pages/Login'
import SignUp from './components/Pages/SignUp'
import { BrowserRouter, Route, Routes } from 'react-router'


function App() {
  return (
    <>
    <BrowserRouter>
    <Routes>
      <Route path="/login" element={<Login/>} />
      <Route path="/signup" element={<SignUp/>} />
    </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
