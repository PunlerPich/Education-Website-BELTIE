import { useState, useEffect } from "react";
import { Card, CardBody, Image, Button } from "@nextui-org/react";
import { Link } from "react-router-dom";

interface EventData {
  id: number;
  title: string;
  imageUrl: string;
  date: string;
}

export const Home_Event = () => {
  const [eventData, setEventData] = useState<EventData[] | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          "http://localhost:8888/news/getAllEvent?direction=DESC&sortBy=id&page=0&pageSize=3"
        );
        const data = await response.json();
        setEventData(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  return (
    <div className="w-6/12 m-auto">
      <div className=" m-auto text-3xl p-9">
        <b>Event</b>
      </div>
      {eventData &&
        eventData.map((event) => (
          <div
            key={event.id}
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              paddingTop: "10px",
            }}>
            <Link to={`/event/${event.id}`}>
              <Card
                isBlurred
                className="border-none bg-background/60 dark:bg-default-100/50 max-w-[850px] "
                shadow="sm">
                <CardBody>
                  <div className="grid grid-cols-6 md:grid-cols-12 gap-6 md:gap-4 items-center justify-center">
                    <div className="relative col-span-6 md:col-span-4">
                      <Image
                        alt="Album cover"
                        className="object-cover"
                        height={200}
                        shadow="md"
                        src={event.imageUrl}
                        width="100%"
                      />
                    </div>

                    <div className="flex flex-col col-span-6 md:col-span-8">
                      <div className="flex justify-between   items-start">
                        <div className="flex flex-col  ">
                          <h3 className="font-semibold text-foreground/90 pl-2 pt-10 pb-5 text-center">
                            {event.title}
                          </h3>
                        </div>
                        <p className="font-semibold text-xs text-sky-400">
                          {new Date(event.date).toLocaleDateString()}
                        </p>
                      </div>
                      <Link to={`/news/${event.id}`}>
                        <Button
                          radius="full"
                          size="sm"
                          className="float-right text-white"
                          color="primary">
                          Read More
                        </Button>
                      </Link>
                    </div>
                  </div>
                </CardBody>
              </Card>
            </Link>
          </div>
        ))}
    </div>
  );
};
