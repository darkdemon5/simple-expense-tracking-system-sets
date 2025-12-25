import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../services/authService";
import { EyeFilledIcon, EyeSlashFilledIcon } from "../components/Icons";
import { toast } from "sonner";
import { addToast } from "@heroui/react";

const Login = () => {
  const navigate = useNavigate();
  const [email, setEmail] = React.useState("");
  const [pass, setPass] = React.useState("");
  const [msg, setMsg] = React.useState("");
  const [err, setErr] = React.useState(false);
  const [success, setSuccess] = React.useState(false);
  const [loading, setLoading] = React.useState(false);
  const [isVisible, setIsVisible] = React.useState(false);
  const toggleVisibility = () => setIsVisible(!isVisible);

  const validate = () => {
    if (!email || !pass) {
      setErr("All fields are required");
      return false;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      setErr(true);
      setMsg("Please enter a valid email address");
      return false;
    }
    if (pass.length < 8) {
      setErr(true);
      setMsg("Password must be at least 8 characters long");
      return false;
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErr(null);
    setSuccess(null);
    setMsg(null);
    if (!validate()) return;

    setLoading(true);
    try {
      // const data = await login({ email: email, password: pass });
      const loginData = { email: email, password: pass };

      const data = await login(loginData);
      console.log("Login page: ", data);
      if (data.status === 200 || data.ok) {
        if (data.accessToken) {
          try {
            setAccessToken(data.accessToken);
          } catch {
            localStorage.setItem("accessToken", data.accessToken);
          }
        }
        setSuccess(true);
        setErr(false);
        setMsg("Login Successful!");
        console.log("Login successful:", data);
        setLoading(false);
        return;
      }
      setErr(true);
      setSuccess(false);
      console.log(data.status);
      // console.log("Login error:", data.error);
      setLoading(false);
      // window.location.href = "/";
      // navigate("/");
    } catch (error) {
      setErr(
        error?.response?.data?.message || error.message || "Login Failed!"
      );
      setLoading(false);
    }
  };

  return (
    <div className="w-full h-screen flex flex-col justify-center items-center mx-auto bg-[#A7CBE3]">
      <div className="flex flex-col gap-4 border p-8 rounded-lg shadow-lg justify-center items-center mx-auto bg-white">
        <h2 className="text-2xl mb-4 text-[#3F72AF] font-bold">Login</h2>

        <form onSubmit={handleSubmit} className="flex flex-col gap-4 w-80">
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
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
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
              type={isVisible ? "text" : "password"}
              required
              placeholder="Password"
              minLength="8"
              pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
              title="Must be more than 8 characters, including number, lowercase letter, uppercase letter"
              id="pass"
              value={pass}
              onChange={(e) => setPass(e.target.value)}
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
          <p className="validator-hint hidden">Required</p>
          <div className="w-full flex justify-end">
            <p className="cursor-pointer text-sm text-[#3F72AF]">
              Forgot Password?
            </p>
          </div>
          <button
            className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg xl:btn-lg bg-[#3F72AF] text-white"
            title="Click me! I won't scam you ;)"
            type="submit"
            disabled={loading}
            aria-busy={loading}
            onClick={() => {
              success
                ? addToast({
                    title: "Login Success",
                    description: "You have successfully logged in.",
                    color: "success",
                  })
                : addToast({
                    title: "Login Failed",
                    description: err || "An error occurred during login.",
                    color: "danger",
                    classNames: {
                      closeButton:
                        "opacity-100 absolute right-4 top-1/2 -translate-y-1/2",
                    },
                  });
            }}
          >
            {loading ? "Logging in..." : "Login"}
          </button>
          <div>
            <p className="text-base mx-auto">
              New User?{" "}
              <Link className="cursor-pointer text-[#3F72AF]" to="/signup">
                SignUp
              </Link>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;

// const successAlert = (text) => {
//   return addToast({
//     title: "Success",
//     description: text,
//     variant: "solid",
//     color: "success",
//   });
//   <div role="alert" className="alert alert-success">
//     <svg
//       xmlns="http://www.w3.org/2000/svg"
//       className="h-6 w-6 shrink-0 stroke-current"
//       fill="none"
//       viewBox="0 0 24 24"
//     >
//       <path
//         strokeLinecap="round"
//         strokeLinejoin="round"
//         strokeWidth="2"
//         d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
//       />
//     </svg>
//     <span>${text}</span>
//   </div>
// };
// const errorAlert = (text) => {
//   return addToast({
//     title: "Error",
//     description: text,
//     variant: "solid",
//     color: "danger",
//   });
//   <div role="alert" className="alert alert-error">
//     <svg
//       xmlns="http://www.w3.org/2000/svg"
//       className="h-6 w-6 shrink-0 stroke-current"
//       fill="none"
//       viewBox="0 0 24 24"
//     >
//       <path
//         strokeLinecap="round"
//         strokeLinejoin="round"
//         strokeWidth="2"
//         d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
//       />
//     </svg>
//     <span>${text}</span>
//   </div>
// };
