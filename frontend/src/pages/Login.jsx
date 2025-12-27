import { Button, Form, Input } from "@heroui/react";
import React from "react";
import { Link } from "react-router";

export const MailIcon = (props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      fill="currentColor"
      className="size-6"
    >
      <path d="M1.5 8.67v8.58a3 3 0 0 0 3 3h15a3 3 0 0 0 3-3V8.67l-8.928 5.493a3 3 0 0 1-3.144 0L1.5 8.67Z" />
      <path d="M22.5 6.908V6.75a3 3 0 0 0-3-3h-15a3 3 0 0 0-3 3v.158l9.714 5.978a1.5 1.5 0 0 0 1.572 0L22.5 6.908Z" />
    </svg>
  );
};

export const PasswordIcon = (props) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      fill="currentColor"
      className="size-6"
    >
      <path
        fillRule="evenodd"
        d="M15.75 1.5a6.75 6.75 0 0 0-6.651 7.906c.067.39-.032.717-.221.906l-6.5 6.499a3 3 0 0 0-.878 2.121v2.818c0 .414.336.75.75.75H6a.75.75 0 0 0 .75-.75v-1.5h1.5A.75.75 0 0 0 9 19.5V18h1.5a.75.75 0 0 0 .53-.22l2.658-2.658c.19-.189.517-.288.906-.22A6.75 6.75 0 1 0 15.75 1.5Zm0 3a.75.75 0 0 0 0 1.5A2.25 2.25 0 0 1 18 8.25a.75.75 0 0 0 1.5 0 3.75 3.75 0 0 0-3.75-3.75Z"
        clipRule="evenodd"
      />
    </svg>
  );
};

const Login = () => {
  const [email, setEmail] = React.useState("");
  const [pass, setPass] = React.useState("");
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

  return (
    <div className="w-full h-screen flex flex-col justify-center items-center mx-auto bg-[#A7CBE3]">
      <div className="flex flex-col gap-4 border p-8 rounded-lg shadow-lg justify-center items-center mx-auto bg-white">
        <h2 className="text-2xl mb-4 text-[#3F72AF] font-bold">Login</h2>
        <Form>
          <div>
            <Input
              isRequired
              name="email"
              placeholder="Please enter valid email"
              type="email"
              description="We'll definitely share your email. ;)"
              startContent={<MailIcon />}
            />

            <Input
              isRequired
              name="password"
              placeholder="Please enter valid password"
              type="password"
              startContent={<PasswordIcon />}
              variant="bordered"
            />
            <Link to="/signup">
              Are you new user? <span className="text-[#3F72AF]">signUp</span>
            </Link>
          </div>
          <div>
            <Button type="submit">Submit</Button>
          </div>
        </Form>
      </div>
    </div>
  );
};

export default Login;
