var http = require("http");

var options = {
  "method": "POST",
  "hostname": "localhost",
  "port": "5000",
  "path": "/slack",
  "headers": {
    "content-type": "application/json",
    "cache-control": "no-cache",
    "postman-token": "728fbbf1-a8e0-7db3-586e-8d604af5df14"
  }
};

var req = http.request(options, function (res) {
  var chunks = [];

  res.on("data", function (chunk) {
    chunks.push(chunk);
  });

  res.on("end", function () {
    var body = Buffer.concat(chunks);
    console.log(body.toString());
  });
});

req.write(JSON.stringify({ token: 'TNtjsgFfT2nBFQLpo4tRX7fB',
  team_id: 'T024FM0EX',
  team_domain: 'thoughtworks',
  service_id: '47630661891',
  channel_id: 'C03UF420H',
  channel_name: 'chennai',
  timestamp: '1467090621.000015',
  user_id: 'U055BGQ6T',
  user_name: 'vijay',
  text: 'message',
  trigger_word: '@comfox' }));
req.end();