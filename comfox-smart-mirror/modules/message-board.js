import React from 'react';
import { Message } from './message';

export class MessageBoard extends React.Component {
  constructor() {
    super();
    if(typeof(Storage) !== "undefined" && localStorage.getItem('serviceURL')) {
      this.serviceURL = localStorage.getItem('serviceURL');
    } else {
      this.serviceURL = prompt('Enter the service endpoint URL');
      if(typeof(Storage) !== "undefined") {
        localStorage.setItem('serviceURL', this.serviceURL);
      }
    }

    this.state = {
      messages: [],
      currentMessageIndex: 0
    };
    this._fetchMessages();
  }

  componentDidMount() {
    this._ticker = setInterval(this._tick.bind(this), 30 * 1000);
    this._messageUpdater = setInterval(this._fetchMessages.bind(this), 5 * 60 * 1000);
  }

  componentWillUnmount() {
    clearInterval(this._ticker);
    clearInterval(this._messageUpdater);
  }

  _tick() {
    this.setState({currentMessageIndex: (this.state.currentMessageIndex + 1) % this.state.messages.length});
  }

  _fetchMessages() {
    $.get(this.serviceURL, (data) => {
      this.setState({messages: data, currentMessageIndex: 0});
    });
  }

  render() {
    if(this.state.messages.length > 0) {
      return(<Message message={this.state.messages[this.state.currentMessageIndex]} />);
    } else {
      return (<div></div>);
    }
  }

}