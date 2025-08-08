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
    <div className="container mx-auto pt-16">
      <h1 className="text-green-950 ">
        Input din tekst, og gem den i vektordatabasen
      </h1>
      <textarea
        className="bg-green-50 rounded-md p-2 w-full shadow-lg"
        placeholder="Skriv din tekst her..."
        rows={10}
        cols={50}
        onChange={(e) => setText(e.target.value)}
        value={text}
      ></textarea>
      <button
        onClick={handleSave}
        className="bg-green-700 hover:shadow-lg cursor-pointer text-white p-2 rounded-md mt-4"
      >
        Gem tekst
      </button>
    </div>
  );
}
