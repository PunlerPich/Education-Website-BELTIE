// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Card, CardHeader, CardFooter, Image, Button } from "@nextui-org/react";
import { Home2 } from "../component/Hoom2";
import { Home_Event } from "../component/Home_Event";
export const Home = () => {
  return (
    <>
      <div className="max-w-[900px] gap-2 grid grid-cols-12 grid-rows-2 px-8 m-auto mt-10">
        <Card className="col-span-12 sm:col-span-4 h-[300px]">
          <CardHeader className="absolute bg-white/30 bottom-0 border-t-1 border-zinc-100/50 z-10 justify-between">
            <div>
              <p className="text-black text-tiny">Available soon.</p>
              <p className="text-black text-tiny">Get notified.</p>
            </div>
            <Button
              className="text-tiny"
              color="primary"
              radius="full"
              size="sm">
              Notify Me
            </Button>
          </CardHeader>

          <Image
            removeWrapper
            alt="Card background"
            className="z-0 w-full h-full object-cover"
            src="https://mynewsforbelty.s3.amazonaws.com/TestImage/photo_2023-11-21_18-38-06.jpg"
          />
        </Card>

        <Card className="col-span-12 sm:col-span-4 h-[300px]">
          <CardFooter className="absolute bg-white/30 bottom-0 border-t-1 border-zinc-100/50 z-10 justify-between">
            <div>
              <p className="text-black text-tiny">Available soon.</p>
              <p className="text-black text-tiny">Get notified.</p>
            </div>
            <Button
              className="text-tiny"
              color="primary"
              radius="full"
              size="sm">
              Notify Me
            </Button>
          </CardFooter>
          <Image
            removeWrapper
            alt="Card background"
            className="z-0 w-full h-full object-cover"
            src="https://mynewsforbelty.s3.amazonaws.com/TestImage/photo_2023-12-15_02-51-40.jpg"
          />
        </Card>
        <Card className="col-span-12 sm:col-span-4 h-[300px]">
          <CardHeader className="absolute bg-white/30 bottom-0 border-t-1 border-zinc-100/50 z-10 justify-between">
            <div>
              <p className="text-black text-tiny">Available soon.</p>
              <p className="text-black text-tiny">Get notified.</p>
            </div>
            <Button
              className="text-tiny"
              color="primary"
              radius="full"
              size="sm">
              Notify Me
            </Button>
          </CardHeader>
          <Image
            removeWrapper
            alt="Card background"
            className="z-0 w-full h-full object-cover"
            src="https://mynewsforbelty.s3.amazonaws.com/TestImage/photo_2023-12-18_02-13-50.jpg"
          />
        </Card>

        <Card
          isFooterBlurred
          className="w-full h-[300px] col-span-12 sm:col-span-5">
          <CardHeader className="absolute z-10 top-1 flex-col items-start">
            <p className="text-tiny text-red-400 uppercase font-bold">New</p>
          </CardHeader>
          <Image
            removeWrapper
            alt="Card example background"
            className="z-0 w-full h-full scale-125 -translate-y-6 object-cover"
            src="https://mynewsforbelty.s3.amazonaws.com/TestImage/photo_2023-12-16_04-01-16.jpg"
          />
          <CardFooter className="absolute bg-white/30 bottom-0 border-t-1 border-zinc-100/50 z-10 justify-between">
            <div>
              <p className="text-black text-tiny">Available soon.</p>
              <p className="text-black text-tiny">Get notified.</p>
            </div>
            <Button
              className="text-tiny"
              color="primary"
              radius="full"
              size="sm">
              Notify Me
            </Button>
          </CardFooter>
        </Card>
        <Card
          isFooterBlurred
          className="w-full h-[300px] col-span-12 sm:col-span-7">
          <CardHeader className="absolute z-10 top-1 flex-col items-start">
            <p className="text-tiny text-white uppercase font-bold">
              Your day your way
            </p>
          </CardHeader>
          <Image
            removeWrapper
            alt="Relaxing app background"
            className="z-0 w-full h-full object-cover"
            src="https://mynewsforbelty.s3.amazonaws.com/TestImage/photo_2023-12-15_23-55-35.jpg"
          />
          <CardFooter className="absolute bg-black/40 bottom-0 z-10 border-t-1 border-default-600 dark:border-default-100">
            <div className="flex flex-grow gap-2 items-center">
              <Image
                alt="Breathing app icon"
                className="rounded-full w-10 h-11 bg-black"
                src="/images/breathing-app-icon.jpeg"
              />
              <div className="flex flex-col">
                <p className="text-tiny text-white/60">Breathing App</p>
                <p className="text-tiny text-white/60">
                  Get a good night's sleep.
                </p>
              </div>
            </div>
            <Button radius="full" size="sm">
              Get App
            </Button>
          </CardFooter>
        </Card>
      </div>
      <Home_Event />
      <Home2 />
    </>
  );
};
