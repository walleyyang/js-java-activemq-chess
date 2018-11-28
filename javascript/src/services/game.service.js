/**
 * The Game Service
 */
import ActiveMQ from '../common/activemq' // Probably can put this elsewhere

export default class GameService {
  constructor ($cookies, $http, $state, Variables) {
    'ngInject'

    this.$cookies = $cookies
    this.$http = $http
    this.$state = $state
    this.Variables = Variables
    this.ActiveMQ = new ActiveMQ()

    // this.checkJoinedPlayer()
  }

  /**
   * Reads the message from ActiveMQ
   */
  readMessage () {
    this.ActiveMQ.readMessage()

    return this.ActiveMQ.message
  }

  /**
   * Sends message to backend through ActiveMQ to validate move
   *
   * @param {number} gameId
   * @param {string} turn
   * @param {number[]} current
   * @param {number[]} future
   */
  validateMove (gameId, turn, current, future) {
    let move = {
      "id": gameId,
      "currentPlayerTurnColor": turn,
      "currentPosition": current,
      "futurePosition": future,
      "gameOver": false
    }

    this.ActiveMQ.sendMessage(move)
  }

  /**
   * Updates the chess board
   *
   * @param {Objects[]} pieces
   *
   * @returns {string[]} Board with chess unicode
  */
  updateBoard (pieces) {
    let board = [[], [], [], [], [], [], [], []]
    let space = '&nbsp;'
    let size = 8

    // Create the board
    for (let i = 0; i < size; i++) {
      for (let j = 0; j < size; j++) {
        board[i][j] = space
      }
    }

    // Add pieces to board
    for (let key in pieces) {
      let x = pieces[key].position[0]
      let y = pieces[key].position[1]

      board[x][y] = pieces[key].icon
    }

    return board
  }

  /**
   * Checks if a player joined a game
   */
  checkJoinedPlayer () {
    let delay = 3000
    let url = '/check-game'

    let joinedPlayerStatus = setInterval(() => {
      this.$http.get(url).then((res) => {
        let data = res.data[0]

        if (data.black !== '') {
          clearInterval(joinedPlayerStatus)
        }
      })
    }, delay)
  }

  /**
   * Creates the game
   *
   * @param {string} name
   */
  createGame (name) {
    let id = Math.floor(Math.random() * 10000000) + 1000
    let playerName = name.name
    let url = '/create/' + id + '/player/' + playerName

    let cookieData = {
      gameId: id,
      black: playerName
    }

    this.createCookieAndGoToGame(cookieData, url)
  }

  /**
   * Allows second player to join the game
   *
   * @param {string} name
   */
  joinGame (name) {
    let playerName = name.name
    let url = '/check-game'

    this.$http.get(url).then((res) => {
      let data = res.data[0]
      let addSecondPlayer = 'game/' + data.game_id + '/add-second-player/' + playerName

      let cookieData = {
        gameId: data.game_id,
        black: playerName
      }

      this.createCookieAndGoToGame(cookieData, addSecondPlayer)
    })
  }

  /**
   * Updates the database game status
   *
   * @param {number} id
   * @param {Object} gameStatus
   */
  updateDatabaseGameStatus (id, gameStatus) {
    let url = '/id/' + id + '/game-status'

    this.$http({
      method: 'post',
      url: url,
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify(gameStatus)
    })
  }

  /**
   * Updates the database game status
   *
   * @param {string} turn
   * @param {boolean} gameOver
   * @param {number[]} current
   * @param {number[]} future
   * @param {Object} gameStatus
   * @param {boolean} pawnPromotion
   *
   * @returns {Object} gameStatus
   */
  updateGameStatus (turn, gameOver, current, future, gameStatus, pawnPromotion) {
    for (let key in gameStatus.pieces) {
      let item = gameStatus.pieces[key]
      let x = item.position[0]
      let y = item.position[1]

      if (x === future[0] && y === future[1]) {
        gameStatus.pieces.splice(key, 1)
      }

      if (x === current[0] && y === current[1]) {
        item.position = [future[0], future[1]]

        if (pawnPromotion) {
          // Nothing but queens for promotion!
          item.icon = item.color === this.Variables.WHITE ? this.Variables.WHITE_QUEEN_ICON : this.Variables.BLACK_QUEEN_ICON
          item.type = this.Variables.QUEEN
        }
      }
    }

    gameStatus.turn = turn

    return gameStatus
  }

  /**
   * Changes game to game over status
   *
   * @param {number} id
   */
  gameOver (id) {
    let url = '/id/' + id + '/game-over'
    this.$http.post(url)
  }

  /**
   * Creates the cookie and redirects to game
   *
   * @param {Object} cookieData
   * @param {string} url
   */
  createCookieAndGoToGame (cookieData, url) {
    this.$cookies.putObject(this.Variables.CHESSGAME, cookieData)
    this.$http.post(url).then((res) => {
      this.$state.go(this.Variables.STATE_GAME)
    })
  }

  /**
   * Checks if a cookie exists and redirects to game
   */
  checkCookieExist () {
    if (this.$cookies.getObject(this.Variables.CHESSGAME)) {
      this.$state.go(this.Variables.STATE_GAME)
    }
  }
}
