export default class GameController {
  constructor ($http, $state, $cookies, GameService) {
    'ngInject'

    this.$http = $http
    this.$state = $state
    this.$cookies = $cookies

    this.GameService = GameService

    this.notCreated = 0

    this.buttonText = 'Create Game'
    this.gameId = 0
    this.white = ''
    this.black = ''

    this.checkGame()
    this.checkCookie()
  }

  checkCookie () {
    let chessGame = 'chessgame'

    if (this.white.length === this.notCreated) {
      if (!this.$cookies.getObject(chessGame)) {
        let data = {
          gameId: this.gameId,
          white: this.white
        }

        this.$cookies.putObject(chessGame, data)
      }
    } else if (this.black.length > this.notCreated) {
      if (!this.$cookies.getObject(chessGame)) {
        let data = {
          gameId: this.gameId,
          black: this.black
        }

        this.$cookies.putObject(chessGame, data)
      }
    }
  }

  checkGame () {
    let url = '/check-game'

    this.$http.get(url).then((res) => {
      let data = res.data[0]

      if (data.black.length > this.notCreated) {
        this.black = data.black
      } else if (data.white.length > this.notCreated) {
        this.white = data.white
        this.gameId = data.game_id
      }
    })
  }
}
