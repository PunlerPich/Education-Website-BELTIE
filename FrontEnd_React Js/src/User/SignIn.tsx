import { useState, FormEvent, useEffect } from "react";
import { Card, Button, Checkbox, Link, Input } from "@nextui-org/react";
import { EyeFilledIcon } from "../assets/EyeSlashFilledIcon";
import { EyeSlashFilledIcon } from "../assets/EyeFilledIcon";
import { UserIcon } from "../assets/UserIcon";
import jwtDecode from "jwt-decode";

type LoginPageProps = {
  onLogin: (email: string) => void;
  isLoggedIn: boolean;
};

const LoginPage: React.FC<LoginPageProps> = ({ onLogin }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const [isVisible, setIsVisible] = useState(false);

  const toggleVisibility = () => setIsVisible(!isVisible);

  // Perform login API request
  const handleLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // Perform login API request
    try {
      const response = await fetch("http://localhost:8888/news/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (response.ok) {
        const token = await response.text();

        // Store JWT token in local storage
        localStorage.setItem("token", token);

        // Decode the JWT token
        try {
          const decoded = jwtDecode(token);
          console.log(decoded);
          // Perform any necessary actions with the decoded token, such as updating the user's authentication status or storing user information in state.
        } catch (error) {
          console.error("Token decoding failed:", error);
          // Handle the error, such as displaying an error message to the user or redirecting to the sign-in page.
        }

        // Set the login status in local state or context
        setLoggedIn(true);

        // Call the onLogin prop with the email value
        onLogin(email);

        // Use the token in the Authorization header of subsequent requests
        await fetch("http://localhost:8888/api/news", {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        });

        // Continue with other code...
      } else {
        setError("Login failed");
      }
    } catch (error) {
      console.error("Error occurred:", error);
      setError("An error occurred. Please try again.");
    }
  };

  useEffect(() => {
    // Check if the user is already logged in by checking the token in local storage
    const token = localStorage.getItem("token");
    if (token) {
      // Set the login status in local state or context
      setLoggedIn(true);
      window.location.href = "/";
    }
  }, []);
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
      }}
    >
      {error && <p>{error}</p>}
      {loggedIn ? (
        (window.location.href = "/")
      ) : (
        <Card className="lg:w-3/12 xs:w-3/12 flex-wrap md:flex-nowrap p-5 font-medium">
          <div className="flex mb-5 text-2xl">
            <UserIcon size={30} height={undefined} width={undefined} /> User
            Login
          </div>
          <form onSubmit={handleLogin}>
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
      )}
    </div>
  );
};

export default LoginPage;
