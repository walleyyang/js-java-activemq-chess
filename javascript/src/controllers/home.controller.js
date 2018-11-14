/**
 * The Home Controller
 */

export default class HomeController {
  constructor ($http, $state, GameService, Variables) {
    'ngInject'

    this.$http = $http
    this.$state = $state
    this.GameService = GameService
    this.Variables = Variables

    this.buttonText = this.Variables.WAITING

    this.checkGame()
  }

  createGame (name) {
    this.GameService.createGame(name)
  }

  joinGame (name) {
    this.GameService.joinGame(name)
  }

  checkGame () {
    let url = '/check-game'

    this.$http.get(url).then((res) => {
      let data = res.data

      if (data.length === this.Variables.EMPTY) {
        this.buttonText = this.Variables.CREATE_GAME
      } else if (data.length > this.Variables.EMPTY) {
        this.GameService.checkCookieExist()
        this.buttonText = this.Variables.JOIN_GAME
      }
    })
  }
}
