import React from "react";
import { Link } from "react-router";

const Navbar = () => {
  return (
    <div className="grid grid-cols-2 w-full md:grid-cols-3 lg:grid-cols-3 p-4 items-center mx-auto">
      <div className="flex items-center">
        <h1 className="font-transcity fl-text-3xl/5xl">
          PocketTrails
        </h1>
      </div>
      <div className="gap-4 fl-text-xs/lg cursor-pointer items-center justify-center hidden md:flex lg:flex">
        <Link to="/">Home</Link>
        <p>About Us</p>
        <p>Contact Us</p>
      </div>
      <div className="drawer drawer-end md:hidden lg:hidden bg-transparent flex justify-end">
        <input id="my-drawer-5" type="checkbox" className="drawer-toggle" />
        <div className="drawer-content">
          {/* Page content here */}
          <label
            htmlFor="my-drawer-5"
            className="drawer-button btn btn-primary bg-transparent border-0 hover:bg-transparent shadow-none"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              strokeWidth={1.5}
              stroke="currentColor"
              className="size-6 text-black"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
              />
            </svg>
          </label>
        </div>
        <div className="drawer-side">
          <label
            htmlFor="my-drawer-5"
            aria-label="close sidebar"
            className="drawer-overlay"
          ></label>
          <ul className="menu bg-base-200 min-h-full w-50 p-4 gap-4">
            {/* Sidebar content here */}
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <p>About Us</p>
            </li>
            <li>
              <p>Contact Us</p>
            </li>
            <li><p>Account</p></li>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/signup">SignUp</Link></li>
          </ul>
        </div>
      </div>
      <div className="hidden md:flex gap-2 cursor-pointer text-xs md:text-base lg:text-lg items-center justify-end">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          strokeWidth={1.5}
          stroke="currentColor"
          className="size-4.5 md:size-5.5 lg:size-7.5"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
          />
        </svg>
        Account
      </div>
    </div>
  );
};

export default Navbar;
