import webpack from 'webpack'
import webpackMiddleware from 'webpack-dev-middleware'
import webpackConfig from '../webpack.config'

const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const DB = require('./db')

app.use(webpackMiddleware(webpack(webpackConfig)))
app.use(bodyParser.urlencoded({ extended: false }))



app.get('/', (req, res) => {
  //res.send({ data: 'this is data' })
  res.sendFile('index.html', { root: __dirname })
})

app.get('/check-game', (req, res) => {
  let database = new DB()

  database.getActiveGame().then((data) => {
    res.send(data)
  })
})

app.post('/create/:id/player/:name', (req, res) => {
  let id = req.params.id
  let name = req.params.name
  let database = new DB()

  database.getActiveGame().then((data) => {
    if (data.length === 0) {
      database.createGame(id, name)
    }
  })
})

app.listen(3000, () => {
  console.log('Listening on localhost:3000')
})

// const DB = require('./db')

// constant
// const http = require('http')


// const hostname = '127.0.0.1'
// const port = 9000

// const server = http.createServer((req, res) => {
//   res.statusCode = 200
//   res.setHeader('Content-Type', 'text/plain')
//   res.end('Hello')
// })

// server.listen(port, hostname, () => {
//   console.log('Server running at: ' + hostname + ':' + port)
// })

// let database = new DB()
