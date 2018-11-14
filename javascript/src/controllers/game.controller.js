/**
 * The Game Controller
 */

export default class GameController {
  constructor ($http, $state, $cookies, GameService, Variables) {
    'ngInject'

    this.$http = $http
    this.$state = $state
    this.$cookies = $cookies

    this.GameService = GameService
    this.Variables = Variables


    this.gameId = 0
    this.white = ''
    this.black = ''

    this.checkGame()
 
  }

  // checkCookie () {
  //   if (this.white.length === this.Variables.EMPTY) {
  //     if (!this.$cookies.getObject(chessGame)) {
  //       let data = {
  //         gameId: this.gameId,
  //         white: this.white
  //       }

  //       this.$cookies.putObject(chessGame, data)
  //     }
  //   } else if (this.black.length > this.Variables.EMPTY) {
  //     if (!this.$cookies.getObject(chessGame)) {
  //       let data = {
  //         gameId: this.gameId,
  //         black: this.black
  //       }

  //       this.$cookies.putObject(chessGame, data)
  //     }
  //   }
  // }

  checkGame () {
    let url = '/check-game'

    this.$http.get(url).then((res) => {
      let data = res.data[0]

      if (data.black.length > this.Variables.EMPTY) {
        this.black = data.black
      } else if (data.white.length > this.Variables.EMPTY) {
        this.white = data.white
        this.gameId = data.game_id
      }
    })
  }
}
