import { useEffect, useState } from "react";
import { useTheme } from "next-themes";
import { Switch } from "@nextui-org/react";
import { MoonIcon } from "./MoonIcon";
import { SunIcon } from "./SunIcon";

export const ThemeSwitcher = () => {
  const { theme, setTheme } = useTheme();
  const [selected, setSelected] = useState(false);

  useEffect(() => {
    setSelected(theme === "dark" || theme === "system");
  }, [theme]);

  const handleSwitchChange = () => {
    const newTheme = selected ? "light" : "dark";
    setSelected(!selected);
    setTheme(newTheme);
  };

  return (
    <div>
      <Switch
        isSelected={selected}
        size="lg"
        color="secondary"
        thumbIcon={({ isSelected, className }) =>
          isSelected ? (
            <MoonIcon className={className} />
          ) : (
            <SunIcon className={className} />
          )
        }
        onChange={handleSwitchChange}
      />
    </div>
  );
};
