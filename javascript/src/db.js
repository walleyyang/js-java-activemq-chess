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
  }

  async getActiveGame () {
    try {
      let response = await this.pool.query('SELECT * FROM active_game')
      return response.rows
    } catch (e) {
      console.log('Database Error: ', e)
    }
  }

  createGame (id, name) {
    this.pool.query('INSERT INTO active_game(game_id, white, black) VALUES($1, $2, $3)', [id, name, ''])
  }

  deleteGame (id) {
    this.pool.query('DELETE FROM active_game WHERE game_id = $1', [id])
  }

  addSecondPlayer (id, name) {
    this.pool.query('UPDATE active_game SET black = $2 WHERE game_id = $1', [id, name])
  }
}
