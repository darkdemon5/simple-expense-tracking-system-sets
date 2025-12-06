import React from "react";
import Navbar from "../components/Navbar";

const Landing = () => {
  return (
    <div className="flex flex-col w-full mx-auto h-screen bg-cover bg-center bg-no-repeat bg-[url(src/assets/background.jpg)] inset-0 bg-black/10 bg-blend-multiply">
      <Navbar />

      <div className="flex flex-col h-[60vh] xl:h-[70vh] overflow-auto justify-center items-center gap-3 lg:gap-6 xl:gap-9 pl-3 sm:pl-0">
        <div className="flex flex-col justify-center sm:items-center text-5xl font-extrabold text-white text-shadow-lg lg:text-7xl 2xl:text-8xl gap-1">
          <p>Take control of your money</p>
          <p>One trail at a time</p>
        </div>
        <div>
          <p className="flex flex-col justify-center sm:items-center text-lg text-white text-shadow-lg lg:text-2xl 2xl:text-3xl font-extrabold">
            A simple, beautiful way to manage expenses and build financial
            freedom.
          </p>
        </div>
      </div>
      {/* <div className="flex flex-col justify-center md:items-center h-[80vh] gap-6">
        <div className="text-6xl md:text-7xl w-[80%] mt-2 font-extrabold text-white text-shadow-lg lg:w-full sm:justify-center sm:items-center flex flex-col lg:gap-6">
          <p>Take control of your money</p>
          <p>One trail at a time</p>
        </div>
        <div>
          <p className="text-2xl sm:text-4xl w-[80%] font-extrabold text-white/95  md:text-4xl lg:text-4xl md:justify-center md:items-center flex flex-col">
            A simple, beautiful way to manage expenses and build financial
            freedom.
          </p>
        </div>
      </div> */}
    </div>
  );
};

export default Landing;
