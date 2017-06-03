/**
 * 
 */
'use strict';

angular.module('pollApp.services')
  .factory('PollService', ['$resource',
    function($resource) {
      return $resource('polls/:id', {id : '@id',optionId : '@optionId',index: '@index'},{
    	  update : {
    		  method : 'PUT'
    	  },
    	  addOption : {
    		  url : 'polls/:id/option',
    	      method:'POST'
    	  },
    	  vote : {
    		  url : 'polls/:id/vote/:optionId',
    		  method : 'POST',
    	  },
		  search : {
		  		 url : 'polls/search/:query',
			  	 method : 'GET',
			  	 isArray : true	  		  
		  },
		  getForUser : {
			  url : 'polls/user/:id',
			  method : 'GET',
			  isArray : true	  			  
		  },
		  
		  loadMore : {
			  url : 'polls/index/:index',
			  method : 'GET',
			  isArray : true	  			  
		  }

      });
    }
  ])
  .factory('UserService', ['$resource',
    function($resource) {
	  return $resource('user/:id', {id : '@id',query:'@query',name:'@name'}, {
		  update : {
			  method : 'PUT'
		  },
	  	  recent : {
	  		 url : 'users',
	  	     method : 'GET',
	  	     isArray : true
	  	  },
	  	  search : {
	  		  url : 'polls/search/:query',
		  	  method : 'GET',
		  	  isArray : true	  		  
	  	  },
	  	  changePassword : {
	  		  url : 'user/password',
	  		  method : 'POST'
	  	  },
	  	  getByName : {
	  		  url : 'user/view/:name',
		  	  method : 'GET',	  		  
	  	  }
	  });
  	}                       
  ]);