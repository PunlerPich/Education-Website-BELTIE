import {
  Navbar,
  NavbarBrand,
  NavbarContent,
  NavbarItem,
  Link,
  DropdownItem,
  DropdownTrigger,
  Dropdown,
  DropdownMenu,
  Avatar,
  Button,
} from "@nextui-org/react";
import { AcmeLogo } from "../assets/AcmeLogo";
import { ThemeSwitcher } from "../assets/ThemeSwitcher";

interface AppbarProps {
  isLoggedIn: boolean;
  userEmail: string;
  onLogout: () => void;
}

export const Appbar: React.FC<AppbarProps> = ({
  isLoggedIn,
  userEmail,
  onLogout,
}) => {
  const handleLogout = () => {
    if (typeof onLogout === "function") {
      onLogout();
    }
  };

  return (
    <Navbar shouldHideOnScroll>
      <NavbarBrand>
        <img
          className="w-28 "
          src="https://mynewsforbelty.s3.amazonaws.com/TestImage/belet%25.png"
          alt=""
        />
        {/* <p className="font-bold text-inherit ">BELTEI</p> */}
      </NavbarBrand>
      <NavbarContent
        className="hidden sm:flex gap-4 font-bold"
        justify="center">
        <NavbarItem>
          <Link color="foreground" href="/">
            Home
          </Link>
        </NavbarItem>
        <NavbarItem isActive>
          <Link href="/news" aria-current="page">
            News
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link color="foreground" href="/event">
            Event
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link color="foreground" href="/student">
            Student
          </Link>
        </NavbarItem>
      </NavbarContent>
      <NavbarContent justify="end">
        <NavbarItem className="hidden lg:flex">
          {/* Theme Switcher */}
          <ThemeSwitcher />
        </NavbarItem>
        {isLoggedIn ? (
          <>
            {/* <NavbarItem>
              <p>{userEmail}</p>
            </NavbarItem>
            <NavbarItem>
              <Button onClick={handleLogout} color="primary" variant="flat">
                Logout
              </Button>
            </NavbarItem> */}
            <NavbarContent as="div" justify="end">
              <Dropdown placement="bottom-end">
                <DropdownTrigger>
                  <Avatar
                    isBordered
                    as="button"
                    className="transition-transform"
                    color="secondary"
                    name="Jason Hughes"
                    size="sm"
                    src="https://i.pravatar.cc/150?u=a042581f4e29026704d"
                  />
                </DropdownTrigger>
                <DropdownMenu aria-label="Profile Actions" variant="flat">
                  <DropdownItem key="profile" className="h-14 gap-2">
                    <p className="font-semibold">Signed in as</p>
                    <p className="font-semibold">{userEmail}</p>
                  </DropdownItem>
                  <DropdownItem key="settings">My Settings</DropdownItem>
                  <DropdownItem key="team_settings">Team Settings</DropdownItem>
                  <DropdownItem key="analytics">Analytics</DropdownItem>
                  <DropdownItem key="system">System</DropdownItem>
                  <DropdownItem key="configurations">
                    Configurations
                  </DropdownItem>
                  <DropdownItem key="help_and_feedback">
                    Help & Feedback
                  </DropdownItem>
                  <DropdownItem
                    onClick={handleLogout}
                    key="logout"
                    color="danger">
                    Log Out
                  </DropdownItem>
                </DropdownMenu>
              </Dropdown>
            </NavbarContent>
          </>
        ) : (
          <>
            <NavbarItem className="hidden lg:flex">
              <Link href="/login">Login</Link>
            </NavbarItem>
            <NavbarItem>
              <Button as={Link} color="primary" href="#" variant="flat">
                Sign Up
              </Button>
            </NavbarItem>
          </>
        )}
      </NavbarContent>
    </Navbar>
  );
};

export default Appbar;
