import React from "react";
import LogoOnlyNavbar from "../components/LogoOnlyNavbar";
import { Link } from "react-router";


const Login = () => {
  return (
    <div className="bg-cover h-screen bg-[url(src/assets/DashboardLogin.jpg)]">
      <LogoOnlyNavbar />
      <div className="w-full h-[80%] flex flex-col justify-center items-center mx-auto">
        <div className="flex flex-col justify-center items-center mx-auto bg-white/20 backdrop-blur-2xl border-white/30 shadow-xl rounded-2xl fl-max-w-72/96 w-full max-h-xl p-6">
          <div className="h-full grid w-full gap-4 ">
            <div className="h-full w-full flex justify-center">
              <h1 className="text-3xl text-white font-extrabold">Login</h1>
            </div>
            <form className="flex flex-col justify-center w-full h-full row-span-4">
              <div className="flex flex-col gap-4">
                <label className="input validator w-full">
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
                  <input type="email" placeholder="Example@mail.com" required />
                </label>

                <label className="input validator w-full">
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
                      <circle
                        cx="16.5"
                        cy="7.5"
                        r=".5"
                        fill="currentColor"
                      ></circle>
                    </g>
                  </svg>
                  <input
                    type="password"
                    required
                    placeholder="Password"
                    minLength="8"
                    pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                    title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
                  />
                </label>
              </div>
              <div className="text-white cursor-pointer flex justify-end">
                Forgot Password?
              </div>

              <button
                class="btn bg-blue-700 text-white w-full mt-3 btn-active shadow-lg"
                type="submit"
              >
                Submit
              </button>
              <div className="text-white cursor-pointer mt-3 flex justify-center">
                <Link to="/signup">Don't have an account? SignUp</Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
