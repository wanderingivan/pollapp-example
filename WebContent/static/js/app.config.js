'use strict';

angular.module('pollApp').config(function($stateProvider,$translateProvider) {
	  $stateProvider
	  	  		
	        .state('newPoll', { //state for adding a new poll
	  			url: '/polls/new',
	  			templateUrl: 'static/partials/poll/poll-add.jsp',
	  			controller: 'PollCreateController as ctl'
	  		})
	  		
	  		.state('polls', { // state for showing all polls
	  			url: '/polls',
	  			templateUrl: 'static/partials/poll/polls.jsp',
	  			controller: 'PollLatestController as latest'
	  		})
	  		
	  		.state('viewPoll', { //state for showing single poll
	  			url: '/polls/:id/view',
	  			templateUrl: 'static/partials/poll/poll-view.jsp',
	  			controller: 'PollViewController as view'
	  		})
	  		
	  		.state('editPoll', { //state for updating a poll 
	  			url: '/polls/:id/edit',
	  			templateUrl: 'static/partials/poll/poll-edit.jsp',
	  			controller: 'PollEditController as ctl'
	  		})
	  		
	  		.state('searchPolls',{
	  			url: '/polls/search/:query',
	  			templateUrl: 'static/partials/poll/poll-search-list.jsp',
	  			controller: 'PollSearchController as list'
	  		})
	  		
	  		.state('newUser', { //state for adding a new user
		  		url: '/user/new',
		  		templateUrl: 'static/partials/user/user-add.jsp',
		  		controller: 'UserCreateController as ctl'
	  		})
	  
	  		.state('users', { // state for showing all polls
	  			url: '/users',
	  			templateUrl: 'static/partials/user/users.jsp',
	  			controller: 'UserLatestController as latestUsers'
	  		})
	  		
	  		.state('viewUser', { //state for showing single user
	  			url: '/user/:id/view',
	  			templateUrl: 'static/partials/user/user-view.jsp',
	  			controller: 'UserViewController as view',
	  		})
	  		
	  		.state('editUser', { //state for updating a user
	  			url: '/user/:id/edit',
	  			templateUrl: 'static/partials/user/user-edit.jsp',
	  			controller: 'UserEditController as ctl'
	  		})
	  		
	  		.state('changePassword',{
	  			url: '/user/:id/password',
	  			templateUrl: 'static/partials/user/user-password.jsp',
	  			controller: 'UserPasswordController as ctl'
	  		})
	  		
	  		.state('viewUserPolls',{
	  			url:'/user/:id/polls',
	  			templateUrl: 'static/partials/poll/poll-user-list.jsp',
	  			controller: 'UserPollController as list'
	  		})
	  		
	  		.state('login', { // state for login form
	  			url: '/login',
	  			templateUrl : 'static/partials/login.jsp',
	  			controller: 'authentication as auth'
	  		})
	  		
	  		.state('error', {
	  			url: '/error',
	  			templateUrl : 'static/partials/error.jsp',
	  			controller :'error as error'	
	  		}) 
	  		
	        .state('upload',{
	        	url: '/user/upload',
	        	templateUrl : 'static/partials/user/upload.jsp',
	        	controller: "uploaderCtrl as ctl"
	        });
	  

			
	  $translateProvider.preferredLanguage('en');	
	  
	  $translateProvider.useStaticFilesLoader({
		  prefix: 'static/languages/',
		  suffix: '.json'
	  });

	  $translateProvider.useSanitizeValueStrategy(null);
	  //$translateProvider.useLocalStorage();
	  
	}).run(function($state) {
		$state.go('polls'); //make a transition to polls state when app starts
	});
