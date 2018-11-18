/**
 * Main entry point
 */

import angular from 'angular'
import uirouter from '@uirouter/angularjs'

import 'angular-cookies'
import './services'

import Variables from './common/constants'

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
  .constant('Variables', Variables)
  .config(['$stateProvider', '$urlRouterProvider', routes])
  .filter('unsafe', ($sce) => { return $sce.trustAsHtml })

function routes ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/')

  $stateProvider
    .state(Variables.STATE_HOME, {
      url: '/',
      templateUrl: HomeView,
      controller: HomeController,
      controllerAs: '$ctrl'
    })
    .state(Variables.STATE_GAME, {
      url: '/',
      templateUrl: GameView,
      controller: GameController,
      controllerAs: '$ctrl'
    })
}

export default app
