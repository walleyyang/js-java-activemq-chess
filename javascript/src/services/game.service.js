
export default class GameService {
  constructor ($http) {
    'ngInject'

    this.$http = $http
  }

  createGame (name) {
    let id = Math.floor(Math.random() * 10000000) + 1000
    let url = '/create/' + id + '/player/' + name.name

    this.$http.post(url)
    // this.$http.post(url).then((data, status) => {
    //   this.gameId = id
    //   this.white = name.name
    // })
  }
}
