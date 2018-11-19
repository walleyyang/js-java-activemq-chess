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

    // this.receivedMessage = undefined

    this.checkJoinedPlayer()
  }

  readMessage () {
    

    // if (this.ActiveMQ.message !== this.receivedMessage) {
    //   this.receivedMessage = this.ActiveMQ.message
    // }
    this.ActiveMQ.readMessage()
    return this.ActiveMQ.message
  }

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
      for (let piece in pieces[key]) {
        let x = pieces[key][piece].position[0]
        let y = pieces[key][piece].position[1]

        board[x][y] = pieces[key][piece].icon
      }
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
   * Updates the pieces
   *
   * @param {number} id
   * @param {string} turn
   * @param {boolean} gameOver
   * @param {number[]} current
   * @param {number[]} future
   */
  updatePieces (id, turn, gameOver, current, future) {
    let url = '/id/' + id + '/turn/' + turn + '/gameOver/' + gameOver + '/current/' + current + '/future/' + future
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
