/**
 * ActiveMQ module
 */
module.exports = class ActiveMQ {
  constructor () {
    this.StompIt = require('stompit')
    this.host = 'localhost'
    this.port = 61613
    this.user = 'admin'
    this.password = 'admin'
    this.heartBeat = 2000
    this.chessStatus = '/topic/Chess.Status'
    this.chessStatusValidated = '/topic/Chess.Status.Validated'
    this.client = null
  }

  /**
   * Connects to ActiveMQ
   */
  connect () {
    let connectOptions = {
      'host': this.host,
      'port': this.port,
      'connectHeaders': {
        'host': '/',
        'login': this.user,
        'passcode': this.password,
        'heart-beat': this.heartBeat + ',' + this.heartBeat
      }
    }

    this.StompIt.connect(connectOptions, (error, client) => {
      if (error) {
        console.log('Connect Error: ' + error.message)

        return
      }

      this.client = client
      this.readMessage()
      this.sendMessage('what')
    })
  }

  /**
   * Subscribes to ActiveMQ
   */
  readMessage () {
    let subscribeHeaders = {
      'destination': this.chessStatusValidated,
      'ack': 'client-individual'
    }

    this.client.subscribe(subscribeHeaders, (error, message) => {
      if (error) {
        console.log('Subscribe Error: ' + error.message)

        return
      }

      message.readString('UTF-8', (error, body) => {
        if (error) {
          console.log('Read Message Error: ' + error.message)
          return
        }

        console.log('Received Message: ' + body)
        this.client.ack(message)
      })
    })
  }

  /**
   * Sends message to ActiveMQ
   *
   * @param message The message to send
   */
  sendMessage (message) {
    let sendHeaders = {
      'destination': this.chessStatus,
      'content-type': 'text/plain'
    }

    let frame = this.client.send(sendHeaders)
    frame.write(message)
    frame.end()
  }

  /**
   * Disconnects from ActiveMQ
   */
  disconnect () {
    this.client.disconnect()
  }
}
