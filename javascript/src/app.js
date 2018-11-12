import angular from 'angular'
import uirouter from '@uirouter/angularjs'

import 'angular-cookies'
import './services'

import GameView from './templates/game.html'
import HomeView from './templates/home.html'

import GameController from './controllers/game.controller'
import HomeController from './controllers/home.controller'

const requires = [
  uirouter,
  'ngCookies',
  'app.services'
]

const app = angular
  .module('app', requires)
  .config(['$stateProvider', '$urlRouterProvider', routes])

function routes ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/')

  $stateProvider
    .state('home', {
      url: '/',
      templateUrl: HomeView,
      controller: HomeController,
      controllerAs: '$ctrl'
    })
    .state('game', {
      url: '/',
      templateUrl: GameView,
      controller: GameController,
      controllerAs: '$ctrl'
    })
}

export default app
