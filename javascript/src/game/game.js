


// import ActiveMQ from '../common/activemq.js'

// export default class Game {
//   constructor (props) {
//     super(props)

//     this.state = {
//       id: 123,
//       joinable: false,
//       players: [null, null],
//       pieces: null,
//       showNameForm: false
//     }

//     this.game()
//   }

//   game () {
//     let delay = 1000
//     let activeMQ = new ActiveMQ()
//     activeMQ.readMessage()

//     setInterval(() => {
//       let game = activeMQ.message

//       if (!this.isEmpty(game)) {
//         if (JSON.stringify(game) !== JSON.stringify(this.state.game)) {
//           this.state.game = game
//         }
//       }

//       console.log(this.state.game)
//     }, delay)

    
//   //  setInterval(()=> {
//   //   activeMQ.sendMessage({"id":123,"currentPlayerTurnColor":"White","currentPosition":[6,0],"futurePosition":[5,0]})
//   // }, 5000)
  
    
//   }

//   isEmpty (game) {
//     for (let key in game) {
//       if (game.hasOwnProperty(key)) {
//         return false
//       }
//     }

//     return true
//   }

//   createGame () {
//     console.log('createGame')
//     this.state.showNameForm = true
//   }

//   joinGame () {
//     console.log('join game')
//   }

//   render () {
//     if (this.state.showNameForm) {
//       return (
//         <div>Form</div>
//       )
//     }
//     if (this.state.players[0] === null) {
//       return (
//         <div><button onClick={this.createGame}>Create Game</button></div>
//       )
//     } else if (this.state.players[0] !== null) {
//       return (
//         <div><button onClick={this.joinGame}>Join Game</button></div>
//       )
//     }
//   }
// }

