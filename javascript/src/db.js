/**
 * Creates connection pool to PostgreSQL
 */

const Pool = require('pg').Pool

module.exports = class DB {
  constructor () {
    let config = {
      host: 'localhost',
      user: 'postgres',
      password: 'password',
      database: 'game'
    }

    this.pool = new Pool(config)
    console.log('hello')
    
  }

  async getActiveGame () {
    try {
      let response = await this.pool.query('SELECT * FROM active_game')
      console.log('in getActiveGame')
      console.log(response.rows)
      return response.rows
    } catch (e) {
      console.log('Database Error: ', e)
    }
  }

  createGame (id, name) {
    this.pool.query('INSERT INTO active_game(game_id, white, black) VALUES($1, $2, $3)', [id, name, ''])
  }
}
