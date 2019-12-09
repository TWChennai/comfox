    var express = require('express'),
        app = express(),
        server = require('http').Server(app),
        io = require('socket.io')(server),
        kafka = require('kafka-node');

    app.use(express.static('views'));

    server.listen(process.env.PORT || 8000);

    Consumer = kafka.Consumer,
        client = new kafka.Client(process.env.KAFKA_CLIENT_CONNECTION_STRING),
        consumer = new Consumer(
            client, [
                { topic: 'slack', partition: 0 }
            ], {
                autoCommit: false
            }
        );

    io.on('connection', function(socket) {
        console.log('socket io connection initiated ..');
        consumer.on('message', function(message) {
            console.log(message);
            socket.emit('newSlackMessage', message);
        });
    });
