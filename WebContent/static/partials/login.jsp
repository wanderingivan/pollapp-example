
<div class="poll">
  <div class="container">
    <div class="row">
      <div class="col-md-offset-3 col-md-5"> 

        <div class="user-form" ng-controller="authentication as auth">        

          <div class="user-form block">
            <form name="loginform" ng-submit="auth.login()">
              <div class="form-header">
                <h3><i class="fa fa-lock"></i>&nbsp;{{ 'LOGIN' | translate}}:</h3>
              </div>

              <div class="form-input">
                <i class="fa fa-user prefix"></i>
                <input id="username" type="text" name="username" ng-model="auth.credentials.username" placeholder="{{ 'USERNAME' | translate}}" class="form-control" required>
                <div ng-show="loginform.$submitted || loginform.username.$touched"  class="colortext">
      			  <div ng-show="loginform.username.$error.required">{{ 'USERNAMEREQUIRED' | translate}}</div>
                </div>
              </div>

              <div class="form-input">
                <i class="fa fa-lock prefix"></i>
                <input id="password" type="password" name="password" ng-model="auth.credentials.password" placeholder="{{ 'PASSWORD' | translate}}" class="form-control" required>
                <div ng-show="loginform.$submitted || loginform.password.$touched" class="colortext">
      			  <div ng-show="loginform.password.$error.required">{{ 'PASSWORDREQUIRED' | translate}}</div>
                </div>                
              </div>

              <div class="text-center">
                <button id="loginSubmit" class="btn btn-default">{{ 'LOGIN' | translate}}</button>
              </div>          
              <div class="alert alert-danger" style="margin-top:6px" ng-show="auth.error">
				{{ 'LOGIN_ERROR' | translate}}
          	  </div>
	        </form>
          </div>
        </div>
        <div class="form-footer">
          <div class="options">
            <p>{{ 'NOT_MEMBER' | translate}}&nbsp;?<a ui-sref="newUser"><span class="colortext">&nbsp;{{ 'SIGNUP' | translate}}</span></a></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>