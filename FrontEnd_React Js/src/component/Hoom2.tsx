import { useEffect, useState } from "react";
import { Card, CardFooter, CardBody, Image, Button } from "@nextui-org/react";
import { Link } from "react-router-dom";

interface NewsItem {
  id: number;
  title: string;
  date: string;
  imageUrl: string;
  // Add other properties as needed
}

export const Home2 = () => {
  const [data, setData] = useState<NewsItem[]>([]);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const response = await fetch(
        "http://localhost:8888/news/getAllNews?sortBy=id&sortOrder=desc&page=0&pageSize=6"
      );
      const jsonData = await response.json();
      setData(jsonData);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <div className="w-6/12 m-auto">
        <div className=" m-auto text-3xl p-10">
          <b>Last News</b>
        </div>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}>
          <div className="gap-3 grid grid-cols-1 xl:grid-cols-3 sm:grid-cols-3 p-1">
            {data.map((item) => (
              <div key={item.id}>
                <Card
                  className="p-1 w-[280px]"
                  shadow="sm"
                  key={item.id}
                  isPressable
                  onPress={() => console.log("item pressed")}>
                  <CardFooter className="text-small justify-between  text-sky-400">
                    <b>{item.date}</b>
                  </CardFooter>
                  <CardBody className="overflow-visible p-1">
                    <Image
                      shadow="sm"
                      radius="lg"
                      width="100%"
                      alt={item.title}
                      className="w-full object-cover h-[210px]"
                      src={item.imageUrl}
                    />
                  </CardBody>
                  <CardFooter className="text-small justify-between text-left">
                    <Link to={`/news/${item.id}`}>
                      <b>{item.title}</b>
                    </Link>
                  </CardFooter>
                </Card>
              </div>
            ))}
          </div>
        </div>
        <div>
          <Link to={`/news/`}>
            <Button href="/posts" className="float-right m-10" color="primary">
              See All News
            </Button>
          </Link>
        </div>
      </div>
    </>
  );
};
