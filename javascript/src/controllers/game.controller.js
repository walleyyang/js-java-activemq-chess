/**
 * The Game Controller
 */
export default class GameController {
  constructor ($http, $scope, GameService, Variables) {
    'ngInject'

    this.$http = $http
    this.$scope = $scope
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
    this.gameMessage = ''
    this.overlay = true

    this.checkPlayersJoined()
  }

  /**
   * Runs the game
   */
  run () {
    this.updateBoard()

    if (this.black !== '') {
      this.gameMessage = this.Variables.CHECKING
    }

    let delay = 1000
    let checkPlayersDelay = 5000
    let url = '/check-game'

    let joinedPlayerStatus = setInterval(() => {
      this.$http.get(url).then((res) => {
        let data = res.data[0]

        if (data.black !== '') {
          this.gameMessage = this.Variables.GAME_STARTING
          this.overlay = false
          clearInterval(joinedPlayerStatus)
        }
      })
    }, checkPlayersDelay)

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

      if (this.receivedMessage.gameOver) {
        this.overlay = true
        this.GameService.gameOver(this.receivedMessage.id)

        this.$scope.$apply(() => {
          let winner = this.receivedMessage.currentPlayerTurnColor === this.Variables.WHITE ? this.white : this.black
          this.gameMessage = this.Variables.GAME_OVER + winner
        })
      }

      if (this.receivedMessage.validMove) {
        this.turn = this.turn === this.Variables.WHITE ? this.Variables.BLACK : this.Variables.WHITE
        this.currentPosition = []
        this.futurePosition = []
        this.gameStatus =
          this.GameService.updateGameStatus(this.turn, this.receivedMessage.gameOver, this.receivedMessage.currentPosition,
            this.receivedMessage.futurePosition, this.gameStatus, this.receivedMessage.pawnPromotion)

        this.GameService.updateDatabaseGameStatus(this.receivedMessage.id, this.gameStatus)

        this.updateBoard()
      } else if (!this.receivedMessage.validMove) {
        this.$scope.$apply(() => {
          this.gameMessage = this.Variables.INVALID_MOVE
        })

        this.currentPosition = []
        this.futurePosition = []
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
    // Reset the message
    this.gameMessage = ''

    let cookie = JSON.parse(this.GameService.getCookie())

    if (this.turn === this.Variables.BLACK) {
      if (cookie.black !== this.black) {
        this.gameMessage = this.Variables.WAIT_TURN
        this.currentPosition = []
        this.futurePosition = []
      }
    } else if (this.turn === this.Variables.WHITE) {
      if (cookie.white !== this.white) {
        this.gameMessage = this.Variables.WAIT_TURN
        this.currentPosition = []
        this.futurePosition = []
      }
    }

    if (this.currentPosition.length > this.Variables.EMPTY && this.futurePosition.length > this.Variables.EMPTY) {
      return
    }

    if (this.currentPosition.length === this.Variables.EMPTY) {
      this.currentPosition = [x, y]
    } else if (this.futurePosition.length === this.Variables.EMPTY) {
      this.futurePosition = [x, y]
      this.GameService.validateMove(this.gameId, this.turn, this.currentPosition, this.futurePosition)
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
          this.turn = this.gameStatus.turn
        }

        this.run()
      } else if (data.white.length > this.Variables.EMPTY) {
        this.white = data.white
        this.gameId = data.game_id
        this.gameStatus = data.game_status
        this.gameMessage = this.Variables.WAITING_ON_SECOND_PLAYER

        this.run()
      }
    })
  }
}
