    
    <div class="poll">
      <div class="colorblock">
        <div class="cover"></div> 
      </div>
      <div class="container">
        <div class="row">
          <div class="col-md-12 poll-title">
            <h1>{{ 'NEW_POLL' | translate }}</h1>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row details">      
          <div class="col-md-5">
            <div class="poll-form">
			  <form name="pollform" role="form" ng-submit="ctl.addPoll()">
  			    <div ng-include="'static/partials/poll/_poll-form.jsp'"></div>
		      </form>
            </div>
          </div>
          <div class="col-md-5">
            <div class="chart">
		      <canvas id="doughnut" class="chart chart-doughnut"
                    chart-data="ctl.data" chart-labels="ctl.labels" chart-colors="ctl.colors">
              </canvas> 
              <h2 style="text-align:center;text-transform:capitalize">{{ctl.poll.name}}</h2>
              <p>{{ctl.poll.description}}</p>
	  	    </div>	 	
          </div>
        </div>
	  </div>
	</div>

