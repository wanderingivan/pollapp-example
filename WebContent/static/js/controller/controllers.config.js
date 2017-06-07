/**
 * 
 */
'use strict';
angular.module('pollApp.controllers')
    
       .controller('PollLatestController',
           function($state,$log,PollService){
    	      this.query = null;
    	   	  this.index = 4;
    	   	  this.polls = [];
    	   	  
    	   	  this.loadMore = function(){
    	   		  var self = this;
    	   		  PollService.loadMore({index:self.index},function(polls){
    	   			  for(var i = 0; i <  polls.length;i++){
    	   				  self.polls.unshift(polls[i]);
    	   			  }
        	   		  self.index = self.polls.length;
        	   		  $log.info("Index shifted to "  + self.index);
    	   		  });

    	   	  }
    	   	  
    	   	  this.loadLatest = function(){
    	   		  this.polls = PollService.query(function(){},
    	   				  						 function(){$state.go("error");});
    	   		  this.index = 4;

    	   	  }
    	   	  
    	   	  this.loadLatest();
    	      			   
    	      this.dataLoaded = true;
    	      

           }
       )
       
       .controller('PollCreateController',
           function($state,PollService){
        	  this.poll = new PollService();
        	  this.poll.options = [{id:1},{id:2}];
        	  this.error = false
        	  this.labels= ["New Option","New Option"];
        	  this.data = [1,1]
        	  
	          this.colors = [
	      		                "#F86B16",
	      		                "#CCCCCC",
	      		                "#FFCE56"
	      		            ];
        	  
        	  this.addNewOption = function(){
        		  var newId = this.poll.options.length + 1;
        		  this.poll.options.push({id:newId,optionName:"New Option"});
        		  this.fillChart();
        	  }
        	  
        	  
        	  this.addPoll = function() {
        		  var self = this;
        	      this.poll.$save(function(){$state.go("viewPoll",{id:self.poll.id});},
        	    		          function(){$stage.go("error")});
        	      
        	  }
        	  
	          this.fillChart = function(){
	        	  var self= this;
	        	  self.labels = [];
	        	  self.data = [];
	        	  var pollData = self.poll.options;
           	  
	        	  for(var i = 0; i < pollData.length;i++){
	        		  self.labels.push(pollData[i].optionName);
	        		  self.data.push(1);
	        	  }
               
	        	  self.options = {
 	    		    tooltipEvents: [],
 	    		    showTooltips: true,
 	    		    tooltipCaretSize: 0,
 	    		    onAnimationComplete: function () {
 	    		        this.showTooltip(this.segments, true);
 	    		    },
	        	  };
	          }
           }
       )
        
       .controller('PollViewController',
           function($state,$stateParams,$cookies,PollService){
 	   	      var self = this;
	          self.colors = [
	      		                "#F86B16",
	      		                "#CCCCCC",
	      		                "#FFCE56",
	      		                "#1c1c1c"
	      		            ];
	          self.selectedOption=null;
	          self.optionName=null;
	          self.voted = false;
	          self.poll = null;
	          self.labels = [];
	          self.data = [];
 	      
	          self.fillChart = function(){
	        	  var pollData = self.poll.options;
	        	  self.labels.length= 0;
	        	  self.data.length= 0;
	        	  for(var i = 0; i < self.poll.options.length;i++){
	        		  self.labels.push(pollData[i].optionName);
	        		  self.data.push(pollData[i].voteCount);
	        	  }
	        	  self.options = {
 	    		    tooltipEvents: [],
 	    		    showTooltips: true,
 	    		    tooltipCaretSize: 0,
 	    		    onAnimationComplete: function () {
 	    		        this.showTooltip(this.segments, true);
 	    		    },
	        	  };
	          }
 	          
	          self.getPoll = function(){
	        	  self.poll = PollService.get({id: $stateParams.id},
 	    		                         	function(){self.fillChart();},
 	    		                         	function(){$state.go("error");}
 	      							      	 );  	    	  
	          }
 	      
	          self.addOption = function(){                   
	        	  PollService.addOption({id: $stateParams.id,optionName:self.optionName},
 	    			                function(){self.getPoll();},
 	    			                function(){$state.go("error");});
	          }
 	      
	          self.vote = function(){
	        	  PollService.vote({id: $stateParams.id,optionId:self.selectedOption},
 	    			           function(){self.getPoll();},
 	    			           function(){alert('You have already voted in this poll')})
	          }
 	      
	          self.deletePoll = function(){
	        	  self.poll.$delete(function(){alert('Deleted');$state.go('polls');},function(){$state.go("error");});
	          }
              self.getPoll();
    	   }
       )
       
       .controller('PollEditController',function($state,$stateParams,PollService,$controller){
    	   var self = this;
    	   var base = $controller('PollViewController',{$state:$state,$stateParams:$stateParams,PollService:PollService});
    	   angular.extend(self,base);
    	   
 	   	   self.updatePoll = function(){
	   		   self.poll.$update(function(){$state.go("viewPoll",{id:self.poll.id});self.fillChart();},
	   		                    function(){$state.go('error')});
	   	   }
     	   self.addOption = function(){
    		  var newId = self.poll.options.length + 1;
    		  self.poll.options.push({id:newId,optionName:"New option",voteCount:1});// only for tracking order on client this id will not be saved 
    		  self.fillChart();
     	   } 
       })
       
       
       .controller('PollSearchController',
    	   function($state,$stateParams,PollService){
    	      this.query = $stateParams.query;
    	      if(this.query != null){
    	    	  this.polls = PollService.search({query:$stateParams.query})
    	      }
    	      this.search = function(){
    	    	  $state.go("searchPolls",{query:this.query});
    	      }    	      
    	      
       })
       
       .controller('UserCreateController',
           function($state,UserService){
    	       this.user = new UserService();
    	       this.error = false;
    	       this.addUser = function() {
    	    	   var self = this
    	    	   this.user.$save(function(){$state.go('viewUser',{id:self.user.id});},
    	    			           function(){$state.go('error');});
    	       }
           }
       )
       
       .controller('UserLatestController',
           function($state,UserService){
    	      this.users = UserService.query(function(){},
                      						 function(){$state.go("error");});
           }
       )
       
       
       .controller('UserViewController',
           function($state,$stateParams,$rootScope,UserService){
    	   	  this.user = null;
    	   	  if($stateParams.id != 0){
    	   		  this.user = UserService.get({id: $stateParams.id},function(){},
	                         									    function(){$state.go("error");});
    	   	  }else if($rootScope.authenticated){
    	   		  this.user = UserService.getByName({name:$rootScope.username},function(){},
	                                                                           function(){$state.go("error");})
    	   	  }else{
    	   		  $state.go('error');
    	   	  }
    	   	  this.deleteUser = function(user){
    	   		  user.$delete(function(){alert('Deleted');$state.go('users')});
    	   	  }
       	   }
       )
      
     
       
       .controller('UserEditController',
    	   function($state,$stateParams,UserService){
    	   	  this.user = UserService.get({id: $stateParams.id}); 
    	   	  this.updateUser = function(){
    	   		  this.user.$update(function(){$state.go('viewUser',{id: $stateParams.id})},
    	   				            function(){$state.go('error')});
    	   	  }
       	   }
       )
       
       .controller('UserPollController',
    		function($state,$stateParams,PollService){
 	   	  		this.polls = PollService.getForUser({id: $stateParams.id},function(){},
                                                                          function(){$state.go('error');});
       		}
       )
       
       .controller('UserPasswordController',
    		function($state,$stateParams,$window,UserService){
    	   		this.oldPassword = null;
    	   		this.newPassword = null;
    	   		this.changePassword = function(){
    	   			UserService.changePassword({newPassword:this.newPassword,oldPassword:this.oldPassword},
    	   					                   function(){
    	   				                           $window.location.href="#/polls";
    	   				                           $window.location.reload();
    	   			                           },
    	   			                           function(){
    	   			                        	   $state.go("error");
    	   			                           });
    	   		}
       		}
       )
       
       .controller('translation',
       	   function($translate){
    	     this.changeLanguage = function(langKey){
    	    	 $translate.use(langKey);
    	     }
       	   }
		)
		
		.controller('error',
			function($state){
			
		    }
			
	   )
	   
	   .controller('uploaderCtrl', function($scope, $log, $state, $cookies, uiUploader) {

                $scope.btn_upload = function() {
                    $log.info('uploading...');
                    var token = $cookies.get('XSRF-TOKEN');
                    uiUploader.startUpload({
                        url: 'upload/',
                        data :{
                        	'_csrf':token
                        },
                        concurrency: 1,
                        onProgress: function(file) {
                            $log.info(file.name + '=' + file.humanSize);
                            $scope.$apply();
                        },
                        onCompleted: function(file, response) {
                            $state.go('viewUser',{id:0});
                        },
                        onError: function(e){
                        	$log.error("Caught error");
                        	$log.error(e);
                        	$state.go("error");
                        }
                    });
                }
                
                $scope.files = [];
                var element = document.getElementById('file1');
                element.addEventListener('change', function(e) {
                    var files = e.target.files;
                    uiUploader.addFiles(files);
                    $scope.files = uiUploader.getFiles();
                    $scope.$apply();
                });
	   })
                
       .controller('authentication',
    	   function($state,$http,$rootScope){
    	   	  this.error = false;
    	   	  
    	   	  this.authenticate = function(credentials, callback){
    	   		  var headers = credentials ? {authorization : 'Basic '+ btoa(credentials.username + ":"  + credentials.password)} 
    	   		                            : {};
    	   		  $http.get('user', {headers:headers})
    	   		       .success(
    	   				   function(data){
    	   		  	            if(data){
    	   		  	            	$rootScope.username = data.name;
    	   		  	            	$rootScope.authenticated = true;
    	   		  	            	if(data.authorities[0].authority==='ROLE_ADMIN'){
    	   		  	            		$rootScope.admin = true;
    	   		  	            	}
    	   		  	            }else{
    	   		  	            	$rootScope.authenticated = false;
    	   		  	            }
    	   		  	            callback && callback();
    	   				   })
    	   		  	   .error(
    	   		  		  function(){
    	   		  			  callback && callback();
    	   		  		  });
    	   		  
    	   	  }
    	   	  this.authenticate();
    	   	  
    	   	  this.credentials = {};
    	   	  
    	   	  this.login = function(){
    	   		  var self = this;
    	   		  this.authenticate(this.credentials,function(){
    	   			  if($rootScope.authenticated){
    	        	     $state.go('polls');
    	   			  }else{
    	   				 $state.go("login");
    	   				 self.error= true;
    	   			  }
    	   		  });
    	   	  }
    	   	  
    	   	  
    	   	  this.logout = function(){
    	   		  $http.post('logout',{})
    	               .success(
    	                  function(){
    	   		  			  $rootScope.username = null;
    	        	          $rootScope.authenticated = false;
    	        	          $rootScope.admin = false;
    	        	          $state.go('polls');
    	           	      }
    	               )
    	               .error(
    	                  function(){
    	   		  			  $rootScope.username = null;
    	        	          $rootScope.authenticated = false;
    	        	          $rootScope.admin = false;
    	                  }
    	               );
    	   	  }
    	   
       	   }
       );

