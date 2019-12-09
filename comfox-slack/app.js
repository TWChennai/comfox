var express = require('express'),
    app = express(),
    bodyParser = require('body-parser'),
    kafka = require('kafka-node'),
    Producer = kafka.Producer,
    client = new kafka.Client(process.env.KAFKA_CLIENT_CONNECTION_STRING),
    producer = new Producer(client),
    slackMessage = require('./models/slackMessage'),
    events = require('events'),
    eventEmitter = new events.EventEmitter(),
    https = require('https'),
    slackAuthToken = process.env.SLACK_AUTH_TOKEN;

producer.on('ready', function() {
    console.log("kafka producer ready ...")
    app.set('port', (process.env.PORT || 5000));
    app.use(bodyParser.json());
    app.use(bodyParser.urlencoded({
        extended: true
    }));

    app.listen(app.get('port'), function() {
        console.log('Node app is running on port', app.get('port'));
    });


    app.post('/slack', function(request, response, next) {
        console.log(request.body)
        getUseInfoFromSlackAndEmit(request.body.user_id);
        eventEmitter.once("slackMessageUserInfoFor" + request.body.user_id, function(user) {
            sendMessageToKafka(slackMessage(request.body, user));
            channelIds = [];
        })
        return response.status(200).end();
    });
});

var getUseInfoFromSlackAndEmit = function(userid) {
    var options = {
        host: 'slack.com',
        path: '/api/users.info?user=' + userid + '&token=' + slackAuthToken,
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    var req = https.request(options, function(res) {
        console.log("getting user email..." + res.statusCode);
        var user_data;
        res.on('data', function(data) {

            user_data = JSON.parse(data);
            console.log("user response data ..");
            console.log(user_data);
            eventEmitter.emit("slackMessageUserInfoFor" + user_data.user.id, user_data.user);
        });
    });
    req.end();
    req.on('error', function(e) {
        console.error(e);
    });
}

var sendMessageToKafka = function(slackMessage) {
    console.log("slackMessage ***********==============************" + JSON.stringify(slackMessage));
    var payload = {
        topic: 'slack',
        messages: JSON.stringify(slackMessage)
    }
    console.log("publishing to kafka...");
    console.log(payload);
    producer.send([payload], function(err, data) {
        console.log(data);
    });
    producer.on('error', function(err) {
        console.log(err);
    })
}