"use strict";

var out = require('console');
var my_http = require('http');
var con = require('config');

// available categories in poly - https://developers.google.com/poly/reference/api/rest/v1/assets/list
var categories = ['architecture', 'animals', 'art', 'scenes', 'food', 'nature', 'objects', 'people', 'technology', 'transport'];

// Public API key - change to your own key for production use.
// https://poly.googleapis.com/v1/assets/ASSET_ID_HERE?key=$(API_KEY)
// https://poly.googleapis.com/v1/assets?keywords=${keywords}&format=OBJ&key=${API_KEY}
var polyApiBaseUrl="https://poly.googleapis.com/v1/";
var polyWebUrl="https://poly.google.com/view/";
const POLY_API_KEY="AIzaSyB5Wo2XxUHYa1KyDwZTcF5HkfwagCQlvHE";

module.exports = {
  function: searchFor3DAssest
}

function searchFor3DAssest(searchString) {
  let models = [];
  let polyUrl;

  // lookup if searchString exists in available categories
  if (categories.indexOf(searchString.trim()) > -1) {
    polyUrl = polyApiBaseUrl + 'assets?category=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  } else {
    polyUrl = polyApiBaseUrl + 'assets?keywords=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  }

  out.log('requested polyUrl ' + polyUrl);
  
  try {
    let response = my_http.getUrl(polyUrl, { format: 'json' });
    let assets = response.assets;
    if (assets) {
      for ( let i = 0; i < assets.length; i ++ ) {
        // Found asset, lets parse JSON
        let asset = assets[i];
        let assetName = assets[i].name.split('/'); // "name/assetID"
        let assetId = assetName[1]; // get assetId here
        let format = asset.formats.find( format => { return format.formatType === 'OBJ'; } );
        let obj = format.root;
        let mtl = format.resources.find( resource => { return resource.url.endsWith( 'mtl' ) } );
        
        // build the model before adding it to the list of models
        let model = {
          displayName : asset.displayName,
          authorName : asset.authorName,
          thumbnailUrl : asset.thumbnail.url,
          objUrl : obj.url,
          mtlUrl : mtl.url,
          webUrl : polyWebUrl + assetId
        };

        models.push(model);
      }
    } else {
      out.log("Oops.. No Assets found");
      return null;
    }
  }
  catch(err) {
    out.log("Error in POLY API call " + err);
    return null;
  }

  return models;
 }
