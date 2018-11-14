/**
 * The Game Service
 */

export default class GameService {
  constructor ($cookies, $http, $state, Variables) {
    'ngInject'

    this.$cookies = $cookies
    this.$http = $http
    this.$state = $state
    this.Variables = Variables
  }

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

  createCookieAndGoToGame (cookieData, url) {
    this.$cookies.putObject(this.Variables.CHESSGAME, cookieData)
    this.$http.post(url)
    this.$state.go(this.Variables.STATE_GAME)
  }

  checkCookieExist () {
    if (this.$cookies.getObject(this.Variables.CHESSGAME)) {
      this.$state.go(this.Variables.STATE_GAME)
    }
  }
}
