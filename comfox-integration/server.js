var express = require('express');
var https = require('https');
var bodyParser = require('body-parser');

var app = express();
var port = process.env.PORT || 1337;
var jigsawToken=process.env.JIGSAW_TOKEN
var slackAuthToken=process.env.SLACK_AUTH_TOKEN

app.listen(port, function() {
  console.log('Listening on port ' + port);
});

app.use(bodyParser.urlencoded({
  extended: true
}));

app.post('/slack', function(request, response, next) {
  var userName = request.body.user_name;
  console.log('*******' + request.body.team_domain)
  console.log('*******' + request.body.channel_name)
  console.log('*******' + request.body.text)

  if (userName !== 'slackbot') {
    var responseJson = {
      text: 'Hello ' + userName + ', welcome to Slack channel! I\'ll be your guide.'
    };
    return response.status(200).json(responseJson);
  } else {
    return response.status(200).end();
  }
});

app.get('/slack/comfoxfeed', function(request, response){
 var options = {
    host: 'slack.com',
    path: '/api/search.messages?query=comfox&token='+ slackAuthToken ,
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  };

  var req = https.request(options, function(res) {
    console.log(res.statusCode);
    res.on('data', function(d) {
      response.status(200).json(JSON.parse(d.toString()));
    });
  });
  req.end();
  req.on('error', function(e) {
    console.error(e);
  });
})

app.get('/jigsaw/people', function(request, response) {
  var options = {
    host: 'jigsaw.thoughtworks.com',
    path: '/api/people/16610',
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': jigsawToken
    }
  };

  var req = https.request(options, function(res) {
    console.log(res.statusCode);
    res.on('data', function(d) {
      response.status(200).json(d.toString());
    });
  });
  req.end();
  req.on('error', function(e) {
    console.error(e);
  });
});
