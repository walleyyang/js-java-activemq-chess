/**
 * ActiveMQ module
 */

const Stomp = require('@stomp/stompjs')
export default class ActiveMQ {
  constructor () {
    this.host = 'ws://localhost:61614'
    this.user = 'admin'
    this.password = 'admin'
    this.heartBeat = 2000
    this.delay = 1000
    this.chessStatus = '/topic/Chess.Status'
    this.chessStatusValidated = '/topic/Chess.Status.Validated'
    this.message = null
    this.readClient = this.client()
    this.sendClient = this.client()
  }

  /**
   * Creates a new Client
   *
   * @returns {Object}
   */
  client () {
    return (
      new Stomp.Client({
        brokerURL: this.host,
        connectHeaders: {
          login: this.user,
          passcode: this.password
        },
        reconnectDelay: this.delay,
        heartbeatIncoming: this.heartBeat,
        heartbeatOutgoing: this.heartBeat
      })
    )
  }

  /**
   * Subscribes to ActiveMQ
   */
  readMessage () {
    this.readClient.onConnect = (frame) => {
      this.readClient.subscribe(this.chessStatusValidated, (message) => {
        if (message.body) {
          this.message = message.body
        }
      })
    }

    this.readClient.onStompError = (frame) => {
      this.error(frame)
    }

    this.readClient.activate()
  }

  /**
   * Sends message to ActiveMQ
   *
   * @param {Object} The message to send
   */
  sendMessage (message) {
    this.sendClient.onConnect = (frame) => {
      this.sendClient.publish({
        destination: this.chessStatus,
        body: JSON.stringify(message)
      })

      this.sendClient.deactivate()
    }

    this.sendClient.activate()
  }

  /**
   * Disconnects from ActiveMQ
   */
  disconnect () {
    this.client.disconnect()
  }

  /**
   * Error
   *
   * @param frame The frame
   */
  error (frame) {
    console.log('ActiveMQ Error: ' + frame.headers['message'])
  }
}
