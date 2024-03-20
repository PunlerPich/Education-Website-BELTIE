import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Image,
  Avatar,
  Button,
} from "@nextui-org/react";
import { CircularProgress } from "@nextui-org/react";

export default function App() {
  const [isFollowed, setIsFollowed] = useState(false);
  const [newsData, setNewsData] = useState(null);
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8888/news/news/${id}`);
        const data = await response.json();
        setNewsData(data);
        setIsLoading(false);
      } catch (error) {
        console.error("Error fetching news data:", error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (isLoading) {
    return (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100vh",
          position: "absolute",
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
        }}>
        <CircularProgress
          aria-label="Loading..."
          size="lg"
          color="warning"
          showValueLabel={true}
        />
      </div>
    );
  }

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "80vh",
      }}>
      <Card className="max-w-[600px]">
        <CardHeader className="justify-between">
          <div className="flex gap-5">
            <Avatar
              isBordered
              radius="full"
              size="md"
              src="https://mynewsforbelty.s3.amazonaws.com/TestImage/BELTIE.png"
            />
            <div className="flex flex-col gap-1 items-start justify-center">
              <h4 className="text-small font-semibold leading-none text-default-600">
                {newsData ? newsData.categoryName : "Loading..."}
              </h4>
              <h5 className="text-small tracking-tight text-default-400">
                {newsData ? newsData.date : "Loading..."}
              </h5>
            </div>
          </div>
          <Button
            className={
              isFollowed
                ? "bg-transparent text-foreground border-default-200"
                : ""
            }
            color="primary"
            radius="full"
            size="sm"
            variant={isFollowed ? "bordered" : "solid"}
            onPress={() => setIsFollowed(!isFollowed)}>
            {isFollowed ? "Unfollow" : "Follow"}
          </Button>
        </CardHeader>
        <CardBody className="px-3 py-0 text-small text-default-400">
          <Image
            width="100%"
            className="w-full object-cover h-[400px]"
            src={newsData ? newsData.imageUrl : ""}
          />
          <h3 className="text-small font-semibold leading-none text-default-600 pt-3 p-1">
            {newsData ? newsData.title : "Loading..."}
          </h3>
          <p className=" text-sm text-justify p-2">
            {newsData ? newsData.content : ""}
          </p>
          <span className="pt-2">
            {newsData ? `#${newsData.categoryName}` : ""}
            <span className="py-2" aria-label="computer" role="img">
              💻
            </span>
          </span>
        </CardBody>
        <CardFooter className="gap-3">
          <div className="flex gap-1">
            <p className="font-semibold text-default-400 text-small">4</p>
            <p className=" text-default-400 text-small">Following</p>
          </div>
          <div className="flex gap-1">
            <p className="font-semibold text-default-400 text-small">97.1K</p>
            <p className="text-default-400 text-small">Followers</p>
          </div>
        </CardFooter>
      </Card>
    </div>
  );
}
