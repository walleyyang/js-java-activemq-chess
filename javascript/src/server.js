/**
 * Server with routes
 */
import webpack from 'webpack'
import webpackMiddleware from 'webpack-dev-middleware'
import webpackConfig from '../webpack.config'

const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const DB = require('./db')

const database = new DB()

app.use(webpackMiddleware(webpack(webpackConfig)))
app.use(bodyParser.urlencoded({ extended: false }))

// Get Routes
app.get('/', (req, res) => {
  res.sendFile('index.html', { root: __dirname })
})

app.get('/check-game', (req, res) => {
  let database = new DB()

  database.getActiveGame().then((data) => {
    res.send(data)
  })
})

// Post Routes
app.post('/create/:id/player/:name', (req, res) => {
  let id = req.params.id
  let name = req.params.name

  database.getActiveGame().then((data) => {
    database.createGame(id, name)
    res.send()
  })
})

app.post('/game/:id/add-second-player/:name', (req, res) => {
  let id = req.params.id
  let name = req.params.name

  database.getActiveGame().then((data) => {
    database.addSecondPlayer(id, name)
    res.send()
  })
})

app.post('/id/:id/turn/:turn/gameOver/:gameOver/current/:current/future/:future', (req, res) => {
  let id = req.params.id
  let turn = req.params.turn
  let gameOver = req.params.gameOver
  let current = [parseInt(req.params.current[0]), parseInt(req.params.current[2])]
  let future = [parseInt(req.params.future[0]), parseInt(req.params.future[2])]

  database.getActiveGame().then((data) => {
    let newStatus = updateGameStatus(data, turn, gameOver, current, future)

    database.updateGameStatus(id, newStatus)
    res.send()
  })
})

app.listen(3000, () => {
  console.log('Listening on localhost:3000')
})

function updateGameStatus (data, turn, gameOver, current, future) {
  let gameStatus = data[0].game_status

  for (let key in gameStatus.pieces) {
    for (let piece in gameStatus.pieces[key]) {
      let item = gameStatus.pieces[key][piece]
      let x = item.position[0]
      let y = item.position[1]

      if (x === current[0] && y === current[1]) {
        item.position = [future[0], future[1]]
      } else if (x === future[0] && y === future[1]) {
        if (item.color !== turn) {
          item.removed = true
        }
      }
    }
  }

  return gameStatus
}
