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
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())

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

app.post('/id/:id/game-status', (req, res) => {
  let id = req.body.id
  let gameStatus = req.body

  database.getActiveGame().then((data) => {
    database.updateGameStatus(id, gameStatus)
    res.send()
  })
})

app.post('/id/:id/game-over', (req, res) => {
  let id = req.params.id

  database.getActiveGame().then((data) => {
    database.deleteGame(id)
    res.send()
  })
})

app.post('/delete-all-games', (req, res) => {
  database.getActiveGame().then((data) => {
    database.deleteAllGames()
    res.send()
  })
})

app.listen(3000, () => {
  console.log('Listening on localhost:3000')
})
