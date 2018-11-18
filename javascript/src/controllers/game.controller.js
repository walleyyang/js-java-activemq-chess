/**
 * The Game Controller
 */

export default class GameController {
  constructor ($http, $state, $cookies, $sce, GameService, Variables) {
    'ngInject'

    this.$http = $http
    this.$state = $state
    this.$cookies = $cookies
    this.$sce = $sce
    this.GameService = GameService
    this.Variables = Variables

    this.gameId = 0
    this.white = ''
    this.black = ''
    this.turn = ''
    this.gameStatus = undefined
    this.board = undefined

    this.checkGame()
  }

  run () {
    this.updateBoard()
    // let delay = 1000

    // setInterval(() => {
    //   let gameServiceStatus = this.GameService.getStatus()

    //   if (gameServiceStatus !== this.status) {
    //     this.status = gameServiceStatus

    //     this.setStatus(gameServiceStatus)
    //   }
    // }, delay)
  }

  // decodeHTML (value) {
  //   return this.$sce.trustAsHtml(value)
  // }
  

  updateBoard () {
    let pieces = this.gameStatus.pieces
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

    this.board = board
  }

  // setStatus (newStatus) {

  //   console.log(this)
  //   console.log(newStatus)
  //   this.status = newStatus
  //   console.log(this.status)
  // }

  checkGame () {
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
