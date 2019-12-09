var _ = require('lodash');
var slackMessage = function(slackMessage, user) {
    console.log("constructing slack messages with email ..");
        return {
            useremail: user.profile.email,
            username: slackMessage.user_name,
            message: slackMessage.text,
            channel: slackMessage.channel_name,
            image: user.profile.image_32,
        }
}

module.exports = slackMessage;
