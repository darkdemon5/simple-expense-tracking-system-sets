import React from "react";
import LogoOnlyNavbar from "../components/LogoOnlyNavbar";
import { EyeFilledIcon, EyeSlashFilledIcon } from "../components/Icons";
import { Link } from "react-router";

const SignUp = () => {
  const [isVisible, setIsVisible] = React.useState(false);
  const [isVisible1, setIsVisible1] = React.useState(false);
  const toggleVisibility = () => setIsVisible(!isVisible);
  const toggleVisibility1 = () => setIsVisible1(!isVisible1);
  return (
    <div className="bg-cover h-screen bg-[url(src/assets/DashboardSignup.jpg)]">
      <LogoOnlyNavbar />
      <div className="w-full h-[80%] flex justify-center items-center mx-auto">
        <div className="bg-white/20 backdrop-blur-2xl border-white/30 shadow-xl rounded-2xl fl-max-w-80/96 w-full max-h-xl">
          <div className="grid w-full gap-4 p-6">
            <div className="h-full w-full flex justify-center">
              <h1 className="text-3xl text-white font-extrabold">SignUp</h1>
            </div>
            <form className="flex flex-col gap-3">
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
                    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </g>
                </svg>
                <input
                  type="text"
                  required
                  placeholder="Name"
                  pattern="[A-Za-z][A-Za-z\-]*"
                  minLength="3"
                  maxLength="30"
                  title="Put a name so that we can address you properly."
                />
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
                    <rect width="20" height="16" x="2" y="4" rx="2"></rect>
                    <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"></path>
                  </g>
                </svg>
                <input
                  type="email"
                  placeholder="Example@xyz.com"
                  required
                  id="email"
                  title="So we can know its you!"
                />
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
                  type={isVisible ? "text" : "password"}
                  required
                  placeholder="Password"
                  minLength="8"
                  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                  title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
                  id="pass"
                />
                <button
                  aria-label="toggle password visibility"
                  className="focus:outline-solid outline-transparent"
                  type="button"
                  onClick={toggleVisibility}
                >
                  {isVisible ? (
                    <EyeFilledIcon className="text-2xl text-default-400 cursor-pointer" />
                  ) : (
                    <EyeSlashFilledIcon className="text-2xl text-gray-300 cursor-pointer" />
                  )}
                </button>
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
                  type={isVisible1 ? "text" : "password"}
                  required
                  placeholder="Confirm Password"
                  minLength="8"
                  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
                  title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
                  id="cpass"
                />
                <button
                  aria-label="toggle password visibility"
                  className="focus:outline-solid outline-transparent"
                  type="button"
                  onClick={toggleVisibility1}
                >
                  {isVisible1 ? (
                    <EyeFilledIcon className="text-2xl text-default-400 cursor-pointer" />
                  ) : (
                    <EyeSlashFilledIcon className="text-2xl text-gray-300 cursor-pointer" />
                  )}
                </button>
              </label>
              <button
                class="btn bg-blue-700 text-white w-full mt-3 btn-active shadow-lg"
                type="submit"
              >
                Submit
              </button>
              <div className="text-white cursor-pointer mt-3 flex justify-center">
                Do you have an account? <Link to="/login">Login</Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
