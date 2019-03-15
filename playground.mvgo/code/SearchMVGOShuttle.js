"use strict";

const MVGO_STATIONS = require("./MVGOStationCodes");
const AM_STATIONS = MVGO_STATIONS.AM_STATIONS;
const PM_STATIONS = MVGO_STATIONS.PM_STATIONS;
var Console = require('console');
var Http = require('http');

module.exports = {
  function: searchMVGOShuttle
}

function searchMVGOShuttle(boardingStation) {
  Console.log("boarding from " + boardingStation);
  let endpoint = "https://www.ridemvgo.org/Stop/";
  let stationCode = stationNametoCode(boardingStation);
  let timing = "";

  let url = endpoint + stationCode;
  let response = Http.getUrl(url, { format: 'json' });
  for (let i = 0; i < response.length; i++) {
    let arrivals = response[i].Arrivals;
    if (arrivals) {
      for (let i = 0; i < arrivals.length; i++) {
        let next = arrivals[i].Time;
        Console.log(next);
        timing = "The next MVGO shuttle will arrive at " + boardingStation + " station in " +  next + " minutes."
        break;
      }
    }
  }
  return {
    speech: timing
  };
};

function stationNametoCode(name) {
  Console.log("query code for " + name);
  var d = new Date();
  // var d = new Date().toLocaleString("en-US", {timeZone: "America/New_York"});
  Console.log("hours: " + d.getHours());
  var STATIONS;
  if (d.getHours() <  12) {
    Console.log("PM Stations");
    STATIONS = PM_STATIONS;
  } else {
    Console.log("AM Stations");
    STATIONS = AM_STATIONS;
  }
  let index = PM_STATIONS.findIndex(p => p.name == name);

  Console.log("index " + index + ", got the code as " + STATIONS[index].code)
  return PM_STATIONS[index].code;
}
