import React from 'react';

export class RelatedMessage extends React.Component {
  render() {
    return (
      <div className="related-message">
        <img src={this.props.relatedMessage.userImage ? this.props.relatedMessage.userImage : "user.jpg"} className="user-icon" />
        <span>{this.props.relatedMessage.content}</span>
      </div>
    );
  }
}
