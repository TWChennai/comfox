import React from 'react';
import { RelatedMessage } from './related-message';

export class Message extends React.Component {
  renderRelatedMessages() {
    return this.props.message.relatedMessages.map(relatedMessage => (<RelatedMessage relatedMessage={relatedMessage} />));
  }

  render() {
    return (
      <div className="message-container">
        <img src={this.props.message.userImage} className="user-icon" />
        <h1 className="message">{this.props.message.content}</h1>
        <div id="related-messages-container">
          {this.renderRelatedMessages()}
        </div>
      </div>
    );
  }
}