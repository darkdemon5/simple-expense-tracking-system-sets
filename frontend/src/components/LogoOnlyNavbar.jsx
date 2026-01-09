import React from "react";
import { Link } from "react-router";

const LogoOnlyNavbar = () => {
  return (
    <div className="grid grid-cols-2 w-full md:grid-cols-3 lg:grid-cols-3 p-4 items-center mx-auto">
      <div className="flex items-center">
        <h1 className="font-transcity text-3xl md:text-3xl lg:text-5xl text-white">
          PocketTrails
        </h1>
      </div>
    </div>
  );
};

export default LogoOnlyNavbar;
