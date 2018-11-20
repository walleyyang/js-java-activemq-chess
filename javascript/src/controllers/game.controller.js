/**
 * The Game Controller
 */
export default class GameController {
  constructor ($http, GameService, Variables) {
    'ngInject'

    this.$http = $http
    this.GameService = GameService
    this.Variables = Variables

    this.gameId = 0
    this.white = ''
    this.black = ''
    this.turn = ''
    this.gameStatus = undefined
    this.board = undefined
    this.currentPosition = []
    this.futurePosition = []
    this.receivedMessage = undefined

    this.checkPlayersJoined()
  }

  /**
   * Runs the game
   */
  run () {
    let delay = 1000

    this.updateBoard()

    setInterval(() => {
      this.handleReceivedMessage()
    }, delay)
  }

  /**
   * Handles the received message from ActiveMQ
   */
  handleReceivedMessage () {
    let receivedMessage = this.GameService.readMessage()
    let currentReceivedmessage = JSON.stringify(this.receivedMessage)

    if (receivedMessage !== null && receivedMessage !== currentReceivedmessage) {
      this.receivedMessage = JSON.parse(receivedMessage)

      if (this.receivedMessage.validMove) {
        this.turn = this.receivedMessage.currentPlayerTurnColor === this.Variables.WHITE ? this.Variables.BLACK : this.Variables.WHITE
        this.currentPosition = []
        this.futurePosition = []

        this.gameStatus =
          this.GameService.updateGameStatus(this.turn, this.receivedMessage.gameOver, this.receivedMessage.currentPosition,
            this.receivedMessage.futurePosition, this.gameStatus)

        this.GameService.updateDatabaseGameStatus(this.receivedMessage.id, this.gameStatus)

        this.updateBoard()
      } else if (!this.receivedMessage.validMove) {
        console.log('invalid move!!!!')
      }
    }
  }

  /**
   * Sets the current and future positions for the clicked cells
   *
   * @param {number} x
   * @param {number} y
   */
  cellClicked (x, y) {
    if (this.currentPosition.length > this.Variables.EMPTY && this.futurePosition.length > this.Variables.EMPTY) {
      return
    }

    if (this.currentPosition.length === this.Variables.EMPTY) {
      this.currentPosition = [x, y]
    } else if (this.futurePosition.length === this.Variables.EMPTY) {
      let turn = this.turn === this.white ? this.Variables.WHITE : this.Variables.BLACK

      this.futurePosition = [x, y]
      this.GameService.validateMove(this.gameId, turn, this.currentPosition, this.futurePosition)
    }
  }

  /**
   * Sets the controller board to the updated board
   */
  updateBoard () {
    this.board = this.GameService.updateBoard(this.gameStatus.pieces)
  }

  /**
   * Checks if players joined game
   */
  checkPlayersJoined () {
    let url = '/check-game'

    this.$http.get(url).then((res) => {
      let data = res.data[0]

      if (data.black.length > this.Variables.EMPTY) {
        this.black = data.black
        this.white = data.white
        this.gameId = data.game_id
        this.gameStatus = data.game_status

        if (this.turn === '') {
          this.turn = data.white
        }

        this.run()
      } else if (data.white.length > this.Variables.EMPTY) {
        this.white = data.white
        this.gameId = data.game_id
      }
    })
  }
}
