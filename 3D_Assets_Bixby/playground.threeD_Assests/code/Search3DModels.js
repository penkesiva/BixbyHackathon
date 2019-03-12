"use strict";

var Console = require('console');
var Http = require('http');

// available categories in poly - https://developers.google.com/poly/reference/api/rest/v1/assets/list
var categories = ['architecture', 'animals', 'art', 'scenes', 'food', 'nature', 'objects', 'people', 'technology', 'transport'];

// Public API key - change to your own key for production use.
// https://poly.googleapis.com/v1/assets/ASSET_ID_HERE?key=YOUR_API_KEY_HERE
// https://poly.googleapis.com/v1/assets?keywords=${keywords}&format=OBJ&key=${API_KEY}
const POLY_API_KEY = "AIzaSyB5Wo2XxUHYa1KyDwZTcF5HkfwagCQlvHE";

module.exports = {
  function: searchFor3DAssest
}

function searchFor3DAssest(searchString) {
  let models = [];
  let url;

  if (categories.indexOf(searchString.trim()) > -1) {
    url = 'https://poly.googleapis.com/v1/' + 'assets?category=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  } else {
    url = 'https://poly.googleapis.com/v1/' + 'assets?keywords=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  }

  Console.log('requested url ' + url);
  
  try {
    let response = Http.getUrl(url, { format: 'json' });
    let assets = response.assets;
    if (assets) {
      for ( let i = 0; i < assets.length; i ++ ) {
        Console.log("**** New Asset found ****");

        let asset = assets[i];
        let format = asset.formats.find( format => { return format.formatType === 'OBJ'; } );
        let obj = format.root;
        let mtl = format.resources.find( resource => { return resource.url.endsWith( 'mtl' ) } );
        
        let model = {
          displayName : asset.displayName,
          authorName : asset.authorName,
          thumbnailUrl : asset.thumbnail.url,
          objUrl : obj.url,
          mtlUrl : mtl.url,
        };

        models.push(model);
      }
    } else {
      Console.log("No Assets found");
      return null;
    }
  }
  catch(err) {
    Console.log("Error in POLY API call " + err);
    return null;
  }

  return models;
  
 }
