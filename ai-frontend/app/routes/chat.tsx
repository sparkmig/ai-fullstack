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
    if (!text.trim()) return;
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
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 to-green-100 dark:from-gray-900 dark:to-gray-950 py-12 px-4">
      <div className="w-full max-w-2xl h-[80vh] bg-white dark:bg-gray-900 rounded-3xl shadow-2xl flex flex-col overflow-hidden">
        <div className="flex-1 overflow-y-auto px-6 py-8 space-y-4 scrollbar-thin scrollbar-thumb-green-200 dark:scrollbar-thumb-gray-700">
          {conversation.length === 0 && (
            <div className="text-center text-gray-400 dark:text-gray-500 mt-20">
              Start en samtale...
            </div>
          )}
          {conversation.map((c, index) => (
            <div
              key={index}
              className={`flex ${c.user === "user" ? "justify-end" : "justify-start"}`}
            >
              {c.user === "bot" && (
                <div className="flex-shrink-0 w-9 h-9 rounded-full bg-green-200 dark:bg-green-700 flex items-center justify-center mr-3 text-green-900 dark:text-green-100 font-bold">
                  🤖
                </div>
              )}
              <div
                className={`max-w-[70%] px-4 py-3 rounded-2xl shadow-md text-base whitespace-pre-line ${
                  c.user === "bot"
                    ? "bg-green-100 dark:bg-green-800 text-green-900 dark:text-green-100 rounded-bl-none"
                    : "bg-blue-100 dark:bg-blue-800 text-blue-900 dark:text-blue-100 rounded-br-none"
                }`}
              >
                {c.message}
              </div>
              {c.user === "user" && (
                <div className="flex-shrink-0 w-9 h-9 rounded-full bg-blue-200 dark:bg-blue-700 flex items-center justify-center ml-3 text-blue-900 dark:text-blue-100 font-bold">
                  🧑
                </div>
              )}
            </div>
          ))}
        </div>
        <div className="border-t border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-900 px-6 py-5">
          <form
            className="flex items-end gap-3"
            onSubmit={(e) => {
              e.preventDefault();
              getResponse();
            }}
          >
            <textarea
              className="flex-1 bg-green-50 dark:bg-gray-800 rounded-xl p-3 border-2 border-gray-200 dark:border-gray-700 focus:outline-none focus:ring-2 focus:ring-green-400 text-base transition resize-none shadow-sm min-h-[44px] max-h-32"
              placeholder="Skriv din besked..."
              rows={2}
              onChange={(e) => setText(e.target.value)}
              value={text}
              onKeyDown={(e) => {
                if (e.key === "Enter" && !e.shiftKey) {
                  e.preventDefault();
                  getResponse();
                }
              }}
            ></textarea>
            <button
              type="submit"
              className="flex-shrink-0 py-3 px-6 bg-gradient-to-r from-green-500 to-green-700 hover:from-green-600 hover:to-green-800 text-white font-semibold text-lg rounded-xl shadow-md hover:shadow-xl transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-green-400"
              disabled={!text.trim()}
            >
              Send
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
