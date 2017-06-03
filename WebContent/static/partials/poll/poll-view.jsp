    <div class="poll">
      <div class="colorblock">
        <div class="cover"></div> 
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-12 poll-title">
            <h1 id="pollname">{{view.poll.name}}</h1>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row details">
          <div class="col-md-6">
            <div class="description info">
              <p id="description">{{view.poll.description}}</p>
            </div>
            <p>{{'CREATED' | translate }} {{view.poll.created | date }}</p>
            <div class="">
              <b>{{ 'VOTES' | translate }}</b>: <span class="colortext">{{view.poll.votes}}</span> 
            </div>
            <div class="vote-form">
 	 		  <div><h4 class="text-center">{{ 'CHOOSE' | translate}}</h4></div>           
              <form  ng-submit="view.vote()">
                <ul class="list-unstyled options">
                  <li ng-repeat="opt in view.poll.options">
                    <input type="radio" ng-model="view.selectedOption" value="{{opt.id}}"></input>&nbsp;
                    <span id="opt{{$index}}">
                      {{opt.optionName}}
                    </span>&nbsp;
                    <span class="colortext">{{opt.voteCount}}</span>&nbsp;
                    <b>{{'VOTED' | translate}}</b>
                    
                  </li>
                </ul>
                <button type="submit" class="btn btn-default"><i class="fa fa-envelope"></i>&nbsp;{{ 'VOTE' | translate}}</button>
              </form>
            </div>
          </div>
          <div class="col-md-6">
	  	    <div class="chart" ng-if="view.poll.votes>0">
		      <canvas id="doughnut" class="chart chart-doughnut"
                  chart-data="view.data" chart-labels="view.labels" chart-colors="view.colors" chart-options="view.options" >
              </canvas> 
	  	    </div>	 		
	  	    <div class="message well well-sm" ng-if="view.poll.votes<1">
	  	      <h4>No one has voted yet</h4>
	  		  <p>Be the first to vote !</p>
	  	    </div>            
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row details">
          <div class="col-md-6">
            <div class="user-info">
              <div class="image-container">
                <a ui-sref="viewUser({id:view.poll.owner.id})"><img class="user-image" ng-src="images/{{view.poll.owner.imagePath}}" alt="{{view.poll.owner.username}} 's profile pic"></img></a>
              </div>
              <h4><a id="author" ui-sref="viewUser({id:view.poll.owner.id})">{{view.poll.owner.username}}</a>&nbsp;{{ 'ADDED' | translate}}</h4>
      	      <h5><a ui-sref="viewUserPolls({id:view.poll.owner.id})" class="btn btn-default" translate="USERPOLLS" translate-value-username="{{view.poll.owner.username}}"></a></h5>
            </div>
            <div class="col-md-12">
	          <div class="controlls" ng-show="username === view.poll.owner.username || admin">
	            <a class="btn btn-default" ui-sref="editPoll({id:view.poll.id})">{{ 'EDIT' | translate}}</a>
	            <a class="btn btn-danger"  ng-click="view.deletePoll(view.poll)">{{ 'DELETE' | translate}}</a><br/>
	          </div>
            </div>            
          </div>
        </div>
        <div class="row details" ng-if="view.poll.options.length<8">
          <div class="col-md-offset-8 com-md-offset-4">
        	   <h4 class="text-center">{{ 'WANT_MORE' | translate}}&nbsp;<span class="colortext">{{ 'OPTIONS' | translate}}</span>?</h4>
		   <div class="add-form" ng-show="authenticated">
			<h5 class="text-center">{{ 'ADD_OWN' | translate}}</h5>
			<form action="" class="form-horizonthal poll-form" ng-submit="view.addOption()">
			  <div class="form-group row">
				<input type="text" name="optionName" ng-model="view.optionName" placeholder="{{ 'ADDOPT' | translate}}" class="form-control" required/>
				<button type="submit" class="form-control btn btn-default addopt" value="Submit"><i class="fa fa-pencil-square"></i>&nbsp;{{ 'SUBMIT' | translate}}</button>
			  </div>
			</form>
		   </div>
		   <div class="login-link text-center" ng-show="!authenticated">
		     <a ui-sref="login" class="">{{ 'LOGIN_TO_ADD' | translate}}</a>
		   </div>		   
          </div>
        </div>
      </div>
    </div>
