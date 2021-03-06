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

  /**
   * Creates database query pool for multiple concurrent requests
   */
  async getActiveGame () {
    try {
      let response = await this.pool.query('SELECT * FROM active_game')
      return response.rows
    } catch (e) {
      console.log('Database Error: ', e)
    }
  }

  /**
   * Creates the game in the database
   *
   * @param {number} id
   * @param {string} name
   */
  createGame (id, name) {
    this.pool.query('INSERT INTO active_game(game_id, white, black, game_status) VALUES($1, $2, $3, $4)', [id, name, '', this.initGame(id)])
  }

  /**
   * Deletes game from database
   *
   * @param {number} id
   */
  deleteGame (id) {
    this.pool.query('DELETE FROM active_game WHERE game_id = $1', [id])
  }

  /**
   * Deletes all games from datbase. Player no longer in room.
   */
  deleteAllGames () {
    console.log('deleteAllGames')
    this.pool.query('DELETE FROM active_game')
  }

  /**
   * Adds second player to database
   *
   * @param {number} id
   * @param {string} name
   */
  addSecondPlayer (id, name) {
    this.pool.query('UPDATE active_game SET black = $2 WHERE game_id = $1', [id, name])
  }

  /**
   * Updates the game status
   *
   * @param {number} id
   * @param {Object} newStatus
   */
  updateGameStatus (id, newStatus) {
    this.pool.query('UPDATE active_game SET game_status = $2 WHERE game_id = $1', [id, JSON.stringify(newStatus)])
  }

  /**
   * Creates the initial pieces and status
   *
   * @param {number} id
   *
   * @returns {Object} The initial pieces and status object
   */
  // Probably other better place to put this, but it works for now
  initGame (id) {
    return {
      "id": id,
      "gameOver": false,
      "pawnPromotion": false,
      "turn": "White",
      "pieces": [{
        "color": "Black",
        "type": "Rook",
        "position": [0, 0],
        "icon": "&#9820;"
      }, {
        "color": "Black",
        "type": "Rook",
        "position": [0, 7],
        "icon": "&#9820;"
      }, {
        "color": "White",
        "type": "Rook",
        "position": [7, 0],
        "icon": "&#9814;"
      }, {
        "color": "White",
        "type": "Rook",
        "position": [7, 7],
        "icon": "&#9814;"
      }, {
        "color": "Black",
        "type": "Knight",
        "position": [0, 1],
        "icon": "&#9822;"
      }, {
        "color": "Black",
        "type": "Knight",
        "position": [0, 6],
        "icon": "&#9822;"
      }, {
        "color": "White",
        "type": "Knight",
        "position": [7, 1],
        "icon": "&#9816;"
      }, {
        "color": "White",
        "type": "Knight",
        "position": [7, 6],
        "icon": "&#9816;"
      }, {
        "color": "Black",
        "type": "Bishop",
        "position": [0, 2],
        "icon": "&#9821;"
      }, {
        "color": "Black",
        "type": "Bishop",
        "position": [0, 5],
        "icon": "&#9821;"
      }, {
        "color": "White",
        "type": "Bishop",
        "position": [7, 2],
        "icon": "&#9815;"
      }, {
        "color": "White",
        "type": "Bishop",
        "position": [7, 5],
        "icon": "&#9815;"
      }, {
        "color": "Black",
        "type": "Queen",
        "position": [0, 3],
        "icon": "&#9819;"
      }, {
        "color": "White",
        "type": "Queen",
        "position": [7, 3],
        "icon": "&#9813;"
      }, {
        "color": "Black",
        "type": "King",
        "position": [0, 4],
        "icon": "&#9818;"
      }, {
        "color": "White",
        "type": "King",
        "position": [7, 4],
        "icon": "&#9812;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 0],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 1],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 2],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 3],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 4],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 5],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 6],
        "icon": "&#9823;"
      }, {
        "color": "Black",
        "type": "Pawn",
        "position": [1, 7],
        "icon": "&#9823;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 0],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 1],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 2],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 3],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 4],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 5],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 6],
        "icon": "&#9817;"
      }, {
        "color": "White",
        "type": "Pawn",
        "position": [6, 7],
        "icon": "&#9817;"
      }]
    }
  }
}
