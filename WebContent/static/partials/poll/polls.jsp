

    <div class="jumbotron jumbotron-fluid">
      <div class="container">
    	<h1 class="jumbotron-heading">An Example PollApp Site</h1>
    	<div class="lead">Built with AngularJs and SpringMVC</div>  
      </div>
    </div>
    <div class="introblock">
      <div class="container">
        <div class="row">
          <div class="col-md-12 text-center">
            <h3>Sed ut perspiciatis unde omnis iste natus error sit voluptatem <span class="colortext">accusantium</span> doloremque laudantium,<br/> totam rem aperiam, explicabo</h3>
          </div>
        </div>
      </div>
    </div>

    <div class="carouselblock">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h4 class="text-uppercase">
              <a href="#" ng-click="latest.loadLatest()"><span class="colortext">{{ 'LATESTPOLLS' | translate }}</span></a>
              <a  href="#" ng-click="latest.loadMore()">/ <i>{{ 'VIEW_MORE' | translate }}</i></a>
            </h4>
            <div class="recent" >
			  <div ng-repeat="poll in latest.polls | limitTo: 4" class="col-md-3 col-sm-6 col-xs-12">
			   <div class="poll-box">
				<div class="image-container">
				  <a class="" ui-sref="viewUser({id:poll.owner.id})"><img alt="{{poll.owner.username}}'s profile pic" ng-src="images/{{poll.owner.imagePath}}"></img></a>
				</div>
      	      	<h3><a class="colortext" ui-sref="viewPoll({id:poll.id})">{{poll.name}}</a></h3>
                <h4><a id="author" ui-sref="viewUser({id:poll.owner.id})">{{poll.owner.username}}</a>&nbsp;{{ 'ADDED' | translate}}</h4>
      	      	<div class="line"></div>
      	      	<p>{{poll.votes}} {{'VOTED' | translate}}</p>
      	       </div>
			  </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="infoblock">
      <div class="container">
      	<div class="row">
      	  <div class="col-md-3 col-sm-6 col-xs-12 text-center">
      	    <div class="icon-box">
      	      <a href="#">
      	        <i class="fa fa-mobile-phone circle"></i>
      	      </a>
      	      <h4>Nam in aliquam</h4>
      	      <p>Ipsum lorem dolor sit amet consecteur...</p>
      	    </div>
      	  </div>
      	  <div class="col-md-3 col-sm-6 col-xs-12 text-center">
      	    <div class="icon-box">
      	      <a href="#">
      	        <i class="fa fa-star circle"></i>
      	      </a>
      	      <h4>Fusce in enim</h4>
      	      <p>Sed vel ex in neque interdum...</p>
      	    </div>
      	  </div>
      	  <div class="col-md-3 col-sm-6 col-xs-12 text-center">
      	    <div class="icon-box">
      	      <a href="#">
      	        <i class="fa fa-circle-thin circle"></i>
      	      </a>
      	      <h4>Sed non ligula lorem</h4>
      	      <p>Suspendisse in malesuada ex. Mauris commodo eleifend...</p>
      	    </div>
      	  </div>
      	  <div class="col-md-3 col-sm-6 col-xs-12 text-center">
      	    <div class="icon-box">
      	      <a href="#">
      	        <i class="fa fa-rotate-right circle"></i>
      	      </a>
      	      <h4>Lorem ipsum</h4>
      	      <p>Donec ullamcorper, magna ut mattis convallis...</p>
      	    </div>
      	  </div>
      	</div>
      </div>
    </div>
