import type { Route } from "./+types/home";
import { useState } from "react";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

type Conversation = {
  message: string;
  user: "bot" | "user";
};

export default function Home() {
  const [conversation, setConversation] = useState<Array<Conversation>>([]);

  const [text, setText] = useState("");
  const getResponse = async () => {
    setConversation((prev) => [...prev, { message: text, user: "user" }]);
    setText("");
    const response = await fetch(
      "http://localhost:8080/products?search=" + text,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    if (!response.ok) {
      alert("Failed to get response");
      setConversation((prev) => [
        ...prev,
        { message: "Failed to get response", user: "bot" },
      ]);
      return;
    }
    const json = await response.json();

    setConversation((prev) => [
      ...prev,
      { message: json.description, user: "bot" },
    ]);
  };
  return (
    <div className="container mx-auto pt-16 flex flex-col justify-between h-screen">
      <div className="flex flex-col space-y-4 overflow-auto">
        {conversation.map((c, index) => (
          <div
            key={index}
            className={`p-2 rounded-md ${
              c.user === "bot" ? "bg-green-100" : "bg-blue-100"
            }`}
          >
            <strong>{c.user === "bot" ? "Bot" : "User"}:</strong> {c.message}
          </div>
        ))}
      </div>
      <div className="bg-white py-8">
        <div className="flex space-x-2 items-center">
          <textarea
            className="bg-green-50 rounded-md p-2 w-full shadow-lg"
            placeholder="Skriv din tekst her..."
            rows={3}
            cols={50}
            onChange={(e) => setText(e.target.value)}
            value={text}
          ></textarea>
          <button
            onClick={getResponse}
            className="bg-green-700 hover:shadow-lg cursor-pointer text-white p-2 rounded-md mt-4"
          >
            Send
          </button>
        </div>
      </div>
    </div>
  );
}
