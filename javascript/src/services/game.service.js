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

    this.status = undefined

    this.checkJoinedPlayer()
  }

  checkJoinedPlayer () {
    let delay = 3000
    let url = '/check-game'

    let joinedPlayerStatus = setInterval(() => {
      this.$http.get(url).then((res) => {
        let data = res.data[0]

        if (data.black !== '') {
          clearInterval(joinedPlayerStatus)

          this.getStatus()
        }
      })
    }, delay)
  }

  getStatus () {
    // let delay = 1000

    // setInterval(() => {
    //   this.ActiveMQ.readMessage()

    //   if (this.ActiveMQ.message !== this.status) {
    //     this.status = this.ActiveMQ.message

    //     return this.status
    //   }

    //   return null
    // }, delay)
    //let status = this.ActiveMQ.readMessage()
    this.ActiveMQ.readMessage()

    if (this.ActiveMQ.message !== this.status) {
      this.status = this.ActiveMQ.message
    }

    return this.status
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
    this.$http.post(url).then((res) => {
      this.$state.go(this.Variables.STATE_GAME)
    })
  }

  checkCookieExist () {
    if (this.$cookies.getObject(this.Variables.CHESSGAME)) {
      this.$state.go(this.Variables.STATE_GAME)
    }
  }
}
