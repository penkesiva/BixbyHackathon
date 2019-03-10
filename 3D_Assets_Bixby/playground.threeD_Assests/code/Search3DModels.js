"use strict";

var Console = require('console');
var Http = require('http');

// Public API key - change to your own key for production use.
// https://poly.googleapis.com/v1/assets/ASSET_ID_HERE?key=YOUR_API_KEY_HERE
// https://poly.googleapis.com/v1/assets?keywords=${keywords}&format=OBJ&key=${API_KEY}
const POLY_API_KEY = "AIzaSyB5Wo2XxUHYa1KyDwZTcF5HkfwagCQlvHE";

module.exports = {
  function: searchFor3DAssest
}

function searchFor3DAssest(searchString) {
  let displayName;
  let authorName;
  let thumbnailUrl;
  let objUrl;
  let mtlUrl;
  
  let url = 'https://poly.googleapis.com/v1/' + 'assets?keywords=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  Console.log('requested url ' + url);
  
  try {
    let response = Http.getUrl(url, { format: 'json' });
    let assets = response.assets;
    if (assets) {
      for ( let i = 0; i < assets.length; i ++ ) {
        Console.log("**** New Asset found ****");
        let asset = assets[i];
        thumbnailUrl = asset.thumbnail.url;
        displayName = asset.displayName;
        authorName = asset.authorName;
        let format = asset.formats.find( format => { return format.formatType === 'OBJ'; } );
        let obj = format.root;
        let mtl = format.resources.find( resource => { return resource.url.endsWith( 'mtl' ) } );
        
        objUrl = obj.url;
        mtlUrl = mtl.url;
        //let path = obj.url.slice( 0, obj.url.indexOf( obj.relativePath ) );
        Console.log("**** 7End of New Asset found **** " + thumbnailUrl + ' ' + objUrl  + ' ' + mtlUrl);
      }
    } else {
      Console.log("No Assets found");
      return null;
    }
  }
  catch(err) { //TODO
    Console.log("Error in POLY API call " + err);
    return null;
  }

  return {
          displayName : displayName,
          authorName : authorName,
          thumbnailUrl : thumbnailUrl,
          objUrl : objUrl,
          mtlUrl : mtlUrl
        };
  
 }
