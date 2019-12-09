'use strict';

var app = require('http').createServer(handler)
var io = require('socket.io').listen(app);
var fs = require('fs');
var port = process.env.PORT || 6543;
var kafkaUrl = '10.16.23.3:2181';

app.listen(port);

function handler (req, res) {
  fs.readFile(__dirname + '/index.html',
  function (err, data) {
    if (err) {
      res.writeHead(500);
      return res.end('Error loading index.html');
    }

    res.writeHead(200);
    res.end(data);
  });
}

io.on('connection', function (socket) {
    socket.on('start stream', function() {
      var kafka = require('kafka-node'),
            Consumer = kafka.Consumer,
            client = new kafka.Client(
                  kafkaUrl,
                  'kafka-node-client'
            ),
            consumer = new Consumer(
                  client,
                  [
                        { topic: 'twitter', partition: 0 }
                  ],
                  {
                        autoCommit: false
                  }
            );
            consumer.on('message', function (message) {
                  console.log(message);
                  socket.emit('new tweet', message.value);
            });
    });
});
