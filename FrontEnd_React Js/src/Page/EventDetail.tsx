import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Listbox,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Image,
  ListboxItem,
  Dropdown,
  DropdownTrigger,
  DropdownMenu,
  DropdownItem,
  Button,
  Divider,
} from "@nextui-org/react";
import { CircularProgress } from "@nextui-org/react";
import { IconWrapper } from "../assets/IconWrapper";

import { CopyDocumentIcon } from "../assets/CopyDocumentIcon";
import { TagIcon } from "../assets/TagIcon";
export default function App() {
  const [newsData, setNewsData] = useState(null);
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8888/news/event/${id}`);
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

  const copyLinkToClipboard = () => {
    const el = document.createElement("textarea");
    el.value = window.location.href; // Copy the current URL
    document.body.appendChild(el);
    el.select();
    document.execCommand("copy");
    document.body.removeChild(el);

    // You can also provide user feedback that the link has been copied
    alert("Link copied to clipboard!");
  };

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
      <Card className="max-w-[550px]">
        <CardHeader>
          <Image
            width="100%"
            className="w-full object-cover h-[400px] p-2"
            src={newsData ? newsData.imageUrl : ""}
          />
        </CardHeader>

        <CardBody className="px-3 py-0 text-small text-default-400">
          <div className="flex">
            <div className="flex flex-col items-start justify-center">
              <h4 className="text-small font-semibold leading-2 text-default-600 pb-1">
                {newsData ? newsData.title : "Loading..."}
              </h4>
            </div>
          </div>
          <Divider />
          <h5 className=" font-semibold text-primary-500">
            {newsData ? newsData.date : "Loading..."}
          </h5>
          <p className=" text-sm text-justify p-1">
            {newsData ? newsData.content : ""}
          </p>
        </CardBody>
        <Divider />
        <CardFooter className="gap-3 justify-between">
          <Dropdown>
            <DropdownTrigger>
              <Button className="font-semibold" variant="flat" color="primary">
                Sharing to other social media
              </Button>
            </DropdownTrigger>
            <DropdownMenu
              variant="flat"
              aria-label="Dropdown menu with shortcut">
              <DropdownItem key="new" shortcut="⌘N">
                New file
              </DropdownItem>
              <DropdownItem key="copy" shortcut="⌘C">
                Copy link
              </DropdownItem>
              <DropdownItem key="edit" shortcut="⌘⇧E">
                Edit file
              </DropdownItem>
              <DropdownItem
                key="delete"
                shortcut="⌘⇧D"
                className="text-danger"
                color="danger">
                Delete file
              </DropdownItem>
            </DropdownMenu>
          </Dropdown>
          <div className="flex gap-1 font-semibold text-default-400 text-small">
            <Listbox>
              <ListboxItem
                onClick={copyLinkToClipboard}
                startContent={
                  <IconWrapper className=" bg-danger/10 text-danger dark:text-danger-500 font-semibold">
                    <CopyDocumentIcon />
                  </IconWrapper>
                }
                key={""}>
                <span className="font-semibold text-danger">Copy Link</span>
              </ListboxItem>
            </Listbox>
          </div>
        </CardFooter>
      </Card>
    </div>
  );
}
