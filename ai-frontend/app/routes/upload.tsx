import type { Route } from "./+types/home";
import { Welcome } from "../welcome/welcome";
import { useCallback, useState } from "react";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  const [text, setText] = useState("");
  const handleSave = async () => {
    const response = await fetch("http://localhost:8080/products", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ description: text }),
    });
    if (!response.ok) {
      alert("Failed to save text");
      return;
    }
    alert("Text saved successfully!");
    setText("");
  };
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 to-green-100 dark:from-gray-900 dark:to-gray-950 py-12 px-4">
      <div className="w-full max-w-xl bg-white dark:bg-gray-900 rounded-3xl shadow-2xl p-10 flex flex-col items-center">
        <h1 className="text-4xl font-extrabold text-green-800 dark:text-green-300 mb-2 text-center">
          Gem din tekst
        </h1>
        <p className="text-gray-600 dark:text-gray-300 mb-8 text-center">
          Indtast din tekst og gem den i vektordatabasen
        </p>
        <textarea
          className="bg-green-50 dark:bg-gray-800 rounded-xl p-4 w-full border-2 border-gray-200 dark:border-gray-700 focus:outline-none focus:ring-2 focus:ring-green-400 text-lg transition mb-6 resize-none shadow-sm"
          placeholder="Skriv din tekst her..."
          rows={8}
          onChange={(e) => setText(e.target.value)}
          value={text}
        ></textarea>
        <button
          onClick={handleSave}
          className="w-full py-3 px-6 bg-gradient-to-r from-green-500 to-green-700 hover:from-green-600 hover:to-green-800 text-white font-semibold text-lg rounded-xl shadow-md hover:shadow-xl transition-all duration-200 focus:outline-none focus:ring-2 focus:ring-green-400"
        >
          Gem tekst
        </button>
      </div>
    </div>
  );
}
