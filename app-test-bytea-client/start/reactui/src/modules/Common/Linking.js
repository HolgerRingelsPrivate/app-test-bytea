/**
 * Generated Code to access: Sensyb Mediathek REST Server
 */
export async function getRestServiceUrl () {
     let sPort = "8080"; //port is taken from:  Build's Environment.properties
     let link = window.location.protocol + "//" + window.location.hostname + ":" + sPort;
     let result = link + "/";
     return result;
}
