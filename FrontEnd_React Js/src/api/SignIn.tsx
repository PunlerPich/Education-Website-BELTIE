import React, { useState } from "react";
import { Card, Button, Checkbox, Link, Input } from "@nextui-org/react";
import { EyeFilledIcon } from "../assets/EyeSlashFilledIcon";
import { EyeSlashFilledIcon } from "../assets/EyeFilledIcon";
import { UserIcon } from "../assets/UserIcon";
import axios from "axios";

export default function App(): JSX.Element {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isVisible, setIsVisible] = useState(false);

  const toggleVisibility = () => setIsVisible(!isVisible);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const response = await axios.post("http://localhost:8888/users/login", {
        email,
        password,
      });

      const token = response.data;
      localStorage.setItem("token", token); // Store the token in local storage
      console.log("Login successful");
      console.log("Email:", email);
      console.log("Password:", password);
      checkToken();
    } catch (error) {
      console.error("Login failed", error);
    }
  };

  const checkToken = () => {
    const token = localStorage.getItem("token");
    if (token) {
      console.log("Token:", token);
      // Perform further actions with the token, such as redirecting or fetching user data
    } else {
      console.log("Token not found in local storage");
    }
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
      }}
    >
      <Card className="lg:w-3/12 xs:w-3/12 flex-wrap md:flex-nowrap p-5 font-medium">
        <div className="flex mb-5 text-2xl">
          <UserIcon size={30} height={undefined} width={undefined} /> User Login
        </div>
        <form onSubmit={handleSubmit}>
          {/* Email Input */}
          <Input
            type="email"
            label="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          {/* Password Input */}
          <Input
            className="mt-5"
            label="Password"
            endContent={
              <button
                className="focus:outline-none"
                type="button"
                onClick={toggleVisibility}
              >
                {isVisible ? (
                  <EyeSlashFilledIcon className="text-2xl text-default-400 pointer-events-none m-auto" />
                ) : (
                  <EyeFilledIcon className="text-2xl text-default-400 pointer-events-none" />
                )}
              </button>
            }
            type={isVisible ? "text" : "password"}
            autoComplete="current-password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          {/* Check box */}
          <div className="flex py-2 px-1 justify-between">
            <Checkbox
              classNames={{
                label: "text-small",
              }}
            >
              Remember me
            </Checkbox>
            <Link color="primary" href="#" size="sm">
              Forgot password?
            </Link>
          </div>

          {/* Button Login */}
          <div className="mt-5">
            <Button className="w-full" color="primary" type="submit">
              Login
            </Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
