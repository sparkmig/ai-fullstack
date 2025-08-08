import type { Route } from "./+types/home";
import { Welcome } from "../welcome/welcome";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  return (
    <div className="w-screen h-screen flex flex-col items-center justify-center gap-4">
      <a className="cursor-pointer text-blue-400 hover:underline" href="/chat">
        Go to Chat
      </a>
      <a
        className="cursor-pointer text-blue-400 hover:underline"
        href="/upload"
      >
        Go to upload text
      </a>
    </div>
  );
}
