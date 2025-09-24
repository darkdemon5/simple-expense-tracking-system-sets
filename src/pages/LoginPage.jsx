import React from "react";

const LoginPage = (props) => {
  return (
    <div className='"bg-sky-500/100 bg-linear-to-l from-sky-500 to-violet-500 h-screen w-full flex justify-center items-center'>
      <div className="h-[40%] w-[30%] bg-white/20 backdrop-blur-md border border-white/30 rounded-xl shadow-lg">
        <div className="flex justify-center items-start text-2xl font-bold pt-6">
          <h1>Login</h1>
        </div>
        <div className="flex flex-col justify-center-safe gap-6 mx-15 overflow-hidden text-xl">
          <div className="flex flex-row gap-5">
            <label htmlFor="username">Username</label>
            <input type="text" name="username" className="border-1 rounded-lg mx-2" />
          </div>
          <div className="flex flex-row gap-5">
            <label htmlFor="password">Password</label>
            <input type="text" name="password" className="border-1 rounded-lg mx-2" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
