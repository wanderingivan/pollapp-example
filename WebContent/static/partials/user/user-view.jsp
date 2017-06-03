	
    <div class="poll">
      <div class="colorblock">
        <div class="cover"></div> 
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-12 poll-title">
            <h1 id="username" translate="USERPROFILE" translate-value-username="{{view.user.username}}"></h1>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row details">
          <div class="col-md-3">
           <div class="image-container">
			  <img class="user-image" ng-src="images/{{view.user.imagePath}}" alt="{{view.user.username}} 's profile pic"></img>             
           </div>
          </div>
          <div class="col-md-6">
            <h3>{{ 'ABOUT' | translate}}</h3>
            <p id="description">{{view.user.description}}</p>
            <p class="text-muted text-center">{{ 'JOINED' | translate}}: {{view.user.joined | date }} </p>
          </div>          
          <div class="col-md-12">
	        <div class="controlls pull-right" ng-show="username === view.user.username || admin">
	          <a class="btn btn-primary" style="width:100%" ui-sref="editUser({id:view.user.id})">{{ 'EDIT' | translate}}</a><br/>
	          <a class="btn btn-primary" ui-sref="changePassword({id:view.user.id})">{{ 'CHANGEPWD' | translate}}</a><br/>
	        </div>
	        <div class="controlls pull-right" ng-show="admin">
	          <a class="btn btn-danger" style="width:100%" ng-click="view.deleteUser(view.user)">{{ 'DELETE' | translate}}</a><br/>
	        </div>
          </div>      
	    </div>
      </div>
      <div class="container">
     	<div class="row text-center">
          <div class="colorline"></div>
      	  <a ui-sref="viewUserPolls({id:view.user.id})" class="btn btn-default" translate="USERPOLLS" translate-value-username="{{view.user.username}}"></a>
      	</div>
      </div>
    </div>