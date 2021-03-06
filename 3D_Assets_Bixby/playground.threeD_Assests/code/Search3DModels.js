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

// Skecthfab (sf) API
// https://api.sketchfab.com/v3/search?type=models&q=heart
var sfApiBaseUrl="https://api.sketchfab.com/v3/search?type=models&q=";

module.exports = {
  function: searchFor3DAssest
}

function isAvailableInPoly(asset) {
  if ( asset.formats && asset.description && asset.displayName && asset.authorName && asset.thumbnail.url ) {
    return true;
  }
  return false;
}

function searchFor3DAssest(searchString) {
  let models = [];
  let polyUrl, sfUrl;

  // lookup if searchString exists in available categories
  if (categories.indexOf(searchString.trim()) > -1) {
    polyUrl = polyApiBaseUrl + 'assets?category=' + searchString + '&format=OBJ&key=' + POLY_API_KEY;
  } else {
    polyUrl = encodeURI(polyApiBaseUrl + 'assets?keywords=' + searchString + '&format=OBJ&key=' + POLY_API_KEY);
  }

  sfUrl = encodeURI(sfApiBaseUrl + searchString);

  //out.log('requested polyUrl ' + polyUrl + ' sfUrl: ' + sfUrl);
  // lets get SketchFab response
  try {
    let response = my_http.getUrl(sfUrl, { format: 'json' });
    let assets = response.results;
    if (assets) {
      for ( let i = 0; i < assets.length; i ++ ) {
        // Found asset, lets parse JSON
        let asset = assets[i];
        let viewerUrl = asset.viewerUrl;
        
        // Look for assets with high res thumbnails
        if( asset.thumbnails.images.length < 4 )
          continue;

        // build the model before adding it to the list of models
        let model = {
          displayName : asset.name,
          authorName : asset.user.displayName,
          thumbnailUrl : asset.thumbnails.images[3].url, // index 3 gives high resolution thumbnail
          objUrl : "unknown", // TODO: sketchfab supports gltf
          mtlUrl : "unknown",
          webUrl : viewerUrl,
          description : asset.description
        };

        models.push(model);
      }
    } else {
      out.log("Oops.. No Assets found in SketchFab");
    }
  }
  catch(err) {
    out.log("Error in SkecthFab API call " + err);
  }

  // lets get Poly response
  try {
    let response = my_http.getUrl(polyUrl, { format: 'json' });
    let assets = response.assets;
    if (assets) {
      for ( let i = 0; i < assets.length; i ++ ) {
        // found asset
        let asset = assets[i];
        // check if all elements in the json are not null
        if (!isAvailableInPoly(asset))
          continue;

        // lets parse JSON
        let assetName = assets[i].name.split('/'); // "name/assetID"
        let assetId = assetName[1]; // get assetId here
        let format = asset.formats.find( format => { return format.formatType == 'OBJ'; } );
        let obj = format.root;
        let mtl = format.resources.find( resource => { return resource.url.endsWith( 'mtl' ) } );

        // build the model before adding it to the list of models
        let model = {
          displayName : asset.displayName,
          authorName : asset.authorName,
          thumbnailUrl : asset.thumbnail.url,
          objUrl : obj.url,
          mtlUrl : mtl.url,
          webUrl : polyWebUrl + assetId,
          description : asset.description
        };

        models.push(model);
      }
    } else {
      out.log("Oops.. No Assets found in Poly");
    }
  } catch(err) {
    out.log("Error in POLY API call " + err);
  }

  if (models.length > 0)
    return models;
  
  return null;
 }
