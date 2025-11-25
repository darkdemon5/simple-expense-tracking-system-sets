import React from "react";

const Login = () => {
  return (
    <div className="w-full h-screen flex flex-col justify-center items-center mx-auto bg-[#DBE2EF]">
      <div className="flex flex-col gap-4 border p-8 rounded-lg shadow-lg justify-center items-center mx-auto bg-white">
        <div className="text-2xl mb-7 text-[#3F72AF] font-bold">Login</div>
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
            <input type="email" placeholder="mail@site.com" required />
          </label>
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
              minlength="8"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
              title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
            />
          </label>
          <div className="w-full flex justify-end"><p className="cursor-pointer text-sm text-[#3F72AF]">Forgot Password?</p></div>
            <button class="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg xl:btn-lg bg-[#3F72AF] text-white">Submit</button>
          <div><p className="cursor-pointer">New User? <a className="text-[#3F72AF]">SignUp</a></p></div>
        </div>
      </div>
    </div>
  );
};

export default Login;
