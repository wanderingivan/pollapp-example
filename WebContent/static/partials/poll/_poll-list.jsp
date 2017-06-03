
   	  <div class="container">
   	    <div class="row details">
   	      <div class="col-md-6 col-sm-12 list-item" ng-repeat="poll in list.polls">
   	        <div class="media">
   	          <div class="media-left">
   	            <div class="image-container">
				  <a class="" ui-sref="viewUser({id:poll.owner.id})"><img class="user-image" alt="poll.owner.username 's profile pic" ng-src="images/{{poll.owner.imagePath}}"></img></a>	            
   	            </div>
   	          </div>
   	          <div class="media-body">
   	            <h4 class="media-heading"><a class="colortext" ui-sref="viewPoll({id:poll.id})">{{poll.name}}</a></h4>
   	            <p>{{poll.description}}</p>
                <p>{{'CREATED' | translate }} {{poll.created | date }}</p>
   	            <h5>{{'BY' | translate}} <a ui-sref="viewUser({id:poll.owner.id})">{{poll.owner.username}}</a>&nbsp;&nbsp;<span class="text-muted">{{poll.votes}}&nbsp;votes</span></h5>
   	          </div>
   	        </div>
   	      </div>
   	    </div>
   	  </div>