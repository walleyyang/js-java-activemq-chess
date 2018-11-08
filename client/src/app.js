import angular from 'angular'
import uirouter from '@uirouter/angularjs'

import HomeController from './controllers/home.controller'

const app = angular
  .module('app', [uirouter])
  .config(['$stateProvider', '$urlRouterProvider', routes])

function routes ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise('/')

  $stateProvider
    .state('home', {
      url: '/',
      templateUrl: './src/templates/home.html',
      controller: HomeController,
      controllerAs: '$ctrl'
    })
}

export default app
