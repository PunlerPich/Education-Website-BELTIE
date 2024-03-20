import { useEffect, useState } from "react";

function MyComponent() {
  const [data, setData] = useState<unknown>(null);

  useEffect(() => {
    const fetchData = async () => {
      const apiUrl = "http://localhost:8888/api/news"; // Replace with your API URL
      try {
        const response = await fetch(apiUrl);
        if (response.ok) {
          const jsonData = await response.json();
          setData(jsonData);
        } else {
          throw new Error("Failed to fetch data from the API.");
        }
      } catch (error) {
        console.error("Error occurred:", error);
      }
    };

    fetchData();
  }, []);

  if (!data) {
    return <div>Loading...</div>;
  }

  // Render the fetched data
  return <div>Fetched data: {JSON.stringify(data)}</div>;
}

export default MyComponent;
