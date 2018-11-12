export default class HomeController {
  constructor ($http, $state, GameService, $cookies) {
    'ngInject'
 
    this.$cookies = $cookies
    this.$http = $http
    this.$state = $state
    this.GameService = GameService

    this.notCreated = 0
    this.buttonText = 'Waiting...'

    this.checkGame()
  }

  createGame (name) {
    this.GameService.createGame(name)
    this.$state.go('game')
  }

  joinGame (name) {
    this.GameService.createGame(name)
    this.$state.go('game')
  }

  checkGame () {
    let url = '/check-game'
    this.$http.get(url).then((res) => {
      let data = res.data
      if (data.length === this.notCreated) {
        this.buttonText = 'Create Game'
      } else if (data.length > this.notCreated) {
        this.checkCookie()
        this.buttonText = 'Join Game'
      }
    })
  }

  checkCookie () {
    let chessGame = 'chessgame'

    if (this.$cookies.getObject(chessGame)) {
      this.$state.go('game')
    }
  }
}
