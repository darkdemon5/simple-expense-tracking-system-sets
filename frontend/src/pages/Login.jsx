import React from "react";
import { Link } from "react-router";
import { login } from "../services/authService";

const Login = () => {
  const checkValidation = () => {
    const email = document.getElementById("email").value;
    const pass = document.getElementById("pass").value;

    if (!email || !pass) {
      alert("All fields are required");
      return;
    }
    const loginData = { email: email, password: pass };
    login(loginData).then((data) => {
      if (data.error) {
        <div role="alert" className="alert alert-error">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6 shrink-0 stroke-current"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span>Error while login!!</span>
        </div>;
      }
      if (data.message) {
        localStorage.setItem("accessToken", data.accessToken);

        <div role="alert" className="alert alert-success">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6 shrink-0 stroke-current"
            fill="none"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          <span>Login Successful!</span>
        </div>;
      }
    });
  };
  return (
    <div className="w-full h-screen flex flex-col justify-center items-center mx-auto bg-[#A7CBE3]">
      <div className="flex flex-col gap-4 border p-8 rounded-lg shadow-lg justify-center items-center mx-auto bg-white">
        <div className="text-2xl mb-4 text-[#3F72AF] font-bold">Login</div>
        <div className="flex flex-col gap-4 w-80">
          <label className="input validator text-black border-black">
            <svg
              className="h-[1.5em] opacity-50"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <g
                strokeLinejoin="round"
                strokeLinecap="round"
                strokeWidth="2.5"
                fill="none"
                stroke="currentColor"
              >
                <rect width="20" height="16" x="2" y="4" rx="2"></rect>
                <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path>
              </g>
            </svg>
            <input
              type="email"
              placeholder="Example@mail.com"
              required
              id="email"
              title="Be good! give a valid email"
            />
          </label>
          <p className="validator-hint hidden">Required</p>
          <label className="input validator text-black border-black">
            <svg
              className="h-[1.5em] opacity-50"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <g
                strokeLinejoin="round"
                strokeLinecap="round"
                strokeWidth="2.5"
                fill="none"
                stroke="currentColor"
              >
                <path d="M2.586 17.414A2 2 0 0 0 2 18.828V21a1 1 0 0 0 1 1h3a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h1a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1h.172a2 2 0 0 0 1.414-.586l.814-.814a6.5 6.5 0 1 0-4-4z"></path>
                <circle cx="16.5" cy="7.5" r=".5" fill="currentColor"></circle>
              </g>
            </svg>
            <input
              type="password"
              required
              placeholder="Password"
              minLength="8"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
              title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
              id="pass"
            />
          </label>
          <p className="validator-hint hidden">Required</p>
          <div className="w-full flex justify-end">
            <p className="cursor-pointer text-sm text-[#3F72AF]">
              Forgot Password?
            </p>
          </div>
          <button
            className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg xl:btn-lg bg-[#3F72AF] text-white"
            title="Click me! I won't scam you ;)"
            onClick={checkValidation}
          >
            Submit
          </button>
          <div>
            <p className="cursor-pointer text-base mx-auto">
              New User?{" "}
              <Link className="text-[#3F72AF]" to="/signup">
                SignUp
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
