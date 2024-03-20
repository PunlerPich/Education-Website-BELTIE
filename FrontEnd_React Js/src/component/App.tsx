import { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import { Home } from "../Page/Home";
import SingIn from "../User/SignIn";
import Appbar from "./Appbar";
import News from "../Page/News";
import { Event } from "../Page/Event";
import Student from "../Page/Student";
import DetailPage from "../Page/DetailPage";
import EventDetail from "../Page/EventDetail";
import { Home2 } from "./Hoom2";
export const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userEmail, setUserEmail] = useState("");

  const handleLogin = (email: string) => {
    setIsLoggedIn(true);
    setUserEmail(email);
    localStorage.setItem("isLoggedIn", "true");
    localStorage.setItem("userEmail", email);
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUserEmail("");
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("token"); // Clear the token
    window.location.href = "/login";
  };

  useEffect(() => {
    const storedIsLoggedIn = localStorage.getItem("isLoggedIn");
    const storedUserEmail = localStorage.getItem("userEmail");
    if (storedIsLoggedIn === "true" && storedUserEmail) {
      setIsLoggedIn(true);
      setUserEmail(storedUserEmail);
    }
  }, []);

  return (
    <div>
      <Appbar
        isLoggedIn={isLoggedIn}
        userEmail={userEmail}
        onLogout={handleLogout}
      />
      <Routes>
        {/* Other components and routes */}
        <Route path="/" element={<Home />} />
        <Route path="/news" element={<News />} />
        <Route path="/test" element={<Home2 />} />
        <Route path="/event" element={<Event />} />
        <Route path="/student" element={<Student />} />
        <Route path="/news/:id" element={<DetailPage />} />
        <Route path="/event/:id" element={<EventDetail />} />
        <Route
          path="/login"
          element={<SingIn onLogin={handleLogin} isLoggedIn={isLoggedIn} />}
        />
      </Routes>
    </div>
  );
};

export default App;
