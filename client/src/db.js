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
    this.getActiveGame()
  }

  async getActiveGame () {
    try {
      let response = await this.pool.query('SELECT * FROM active_game')
      console.log('in getActiveGame')
      console.log(response.rows)
    } catch (e) {
      console.log('Database Error: ', e)
    }
  }
}
