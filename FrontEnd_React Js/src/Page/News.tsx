import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import {
  Listbox,
  ListboxItem,
  Card,
  CardFooter,
  CardBody,
  Image,
  Pagination,
  Spinner,
} from "@nextui-org/react";
import { IconWrapper } from "../assets/IconWrapper";

import { BugIcon } from "../assets/BugIcon";
import { PullRequestIcon } from "../assets/PullRequestIcon";
import { ChatIcon } from "../assets/ChatIcon";
import { PlayCircleIcon } from "../assets/PlayCircleIcon";
import { LayoutIcon } from "../assets/LayoutIcon";
import { TagIcon } from "../assets/TagIcon";
import { UsersIcon } from "../assets/UsersIcon";
import { WatchersIcon } from "../assets/WatchersIcon";
import { BookIcon } from "../assets/BookIcon";

interface NewsItem {
  id: number;
  title: string;
  content: string;
  author: string;
  date: string;
  imageUrl: string;
  categoryName: string;
  category: {
    id: number;
    name: string;
  };
  saved: boolean;
}
const MyComponent: React.FC = () => {
  const [data, setData] = useState<NewsItem[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [categoryId, setCategoryId] = useState<number | string>(1);
  const [isLoading, setIsLoading] = useState(false); // Add isLoading state

  const fetchData = async (categoryId: number | string, page: number) => {
    try {
      setIsLoading(true); // Set isLoading to true before making the request

      const response = await axios.get<Response>(
        `http://localhost:8888/news/category/${categoryId}?page=${page}&size=8`
      );

      console.log("Response data:", response.data);

      if (response.data.content && Array.isArray(response.data.content)) {
        setData(response.data.content);
        setCurrentPage(page);
      } else {
        console.error("Invalid response data:", response.data);
      }
    } catch (error) {
      console.error("Error:", error);
    } finally {
      setIsLoading(false); // Set isLoading to false after the request is completed
    }
  };

  useEffect(() => {
    fetchData(categoryId, 0); // Fetch data for the default category (Collage) when the component mounts
  }, []); // Empty dependency array ensures it only runs once

  const handlePageChange = (pageNumber: number) => {
    fetchData(categoryId, pageNumber);
  };

  return (
    <>
      <div className="w-full  xl:flex sm:flex justify-center content-center items-center mt-10  ">
        {data.length > 0 && (
          <div>
            <Listbox
              className="p-0  gap-0 divide-y divide-default-300/50 dark:divide-default-100/80 bg-content1 max-w-[300px] overflow-visible shadow-small rounded-medium"
              itemClasses={{
                base: "px-3 first:rounded-t-medium last:rounded-b-medium rounded-none gap-2 h-20 data-[hover=true]:bg-default-100/80",
              }}>
              <ListboxItem
                onClick={() => fetchData(1, 0)}
                startContent={
                  <IconWrapper className="bg-danger/10 text-danger dark:text-danger-500">
                    <UsersIcon />
                  </IconWrapper>
                }
                key={""}>
                Collage
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(2, 0)}
                startContent={
                  <IconWrapper className="bg-success/10 text-success dark:text-success-500">
                    <BookIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Arts
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(3, 0)}
                startContent={
                  <IconWrapper className="bg-primary/10 text-primary ">
                    <BugIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Science
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(4, 0)}
                startContent={
                  <IconWrapper className="bg-secondary/10 text-secondary">
                    <ChatIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Business
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(5, 0)}
                startContent={
                  <IconWrapper className="bg-warning/10 text-warning">
                    <PlayCircleIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Medicine
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(6, 0)}
                startContent={
                  <IconWrapper className="bg-default/50 text-foreground">
                    <PullRequestIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Law
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(7, 0)}
                startContent={
                  <IconWrapper className="bg-lime-600/10 text-lime-700">
                    <LayoutIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Education
              </ListboxItem>

              <ListboxItem
                onClick={() => fetchData(8, 0)}
                startContent={
                  <IconWrapper className="bg-sky-600/10 text-sky-700">
                    <TagIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Social Sciences
              </ListboxItem>
              <ListboxItem
                onClick={() => fetchData(9, 0)}
                startContent={
                  <IconWrapper className="bg-indigo-600/10 text-indigo-600">
                    <WatchersIcon />
                  </IconWrapper>
                }
                key={""}>
                Faculty of Information Technology
              </ListboxItem>
            </Listbox>
          </div>
        )}
        <div className="gap-2 grid grid-cols-1 xl:grid-cols-4 sm:grid-cols-3 p-3">
          {data.map((item) => (
            <div key={item.id}>
              <Card
                className="p-1 w-64"
                shadow="sm"
                key={item.id}
                isPressable
                onPress={() => console.log("item pressed")}>
                <CardFooter className="text-small justify-between">
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
        {data.length > 0 && (
          <div
            className="pl-3 xl:absolute sm:relative"
            style={{ bottom: "5px", left: "1330px" }}>
            <Pagination
              color="warning"
              total={5} // replace with the total number of pages
              initialPage={currentPage}
              onChange={handlePageChange}
            />
          </div>
        )}
        {isLoading && (
          <div
            style={{
              color: "black",
              position: "fixed",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
              zIndex: 50,
              backgroundColor: "White",
              opacity: "0.9",
              padding: "20px",
              borderRadius: "10px",
            }}>
            <Spinner label="Loading..." color="danger" labelColor="danger" />
          </div>
        )}
      </div>
      <div></div>
    </>
  );
};

export default MyComponent;
