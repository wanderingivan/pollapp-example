<div class="poll">
  <div class="container">
    <div class="row">
      <div class="col-md-offset-3 col-md-5">
        <div class="user-form">
          <div class="user-form block">
            <form name="userform" role="form" ng-submit="ctl.updateUser()">
              <div class="form-header">
                <h3><i class="fa fa-pencil-square-o"></i>&nbsp;{{ 'EDIT' | translate}} {{ctl.user.username}}:</h3>
              </div>

              <div class="form-input">
                <i class="fa fa-user prefix"></i>
                <input type="text" id="username" name="username" ng-model="ctl.user.username" placeholder="{{ 'USERNAME' | translate}}" class="form-control" required>
                <div ng-show="userform.$submitted || userform.username.$touched"  class="colortext">
      			  <div  id="usernameError" ng-show="userform.username.$error.required">{{ 'USERNAMEREQUIRED' | translate}}</div>
                </div>
              </div>


              <div class="form-input">
                <i class="fa fa-envelope prefix"></i>
                <input type="email" id="mail" name="email" ng-model="ctl.user.email" placeholder="{{ 'EMAIL' | translate}}" class="form-control" required>
                <div ng-show="userform.$submitted || userform.email.$touched"  class="colortext">
      			  <div id="emailError" ng-show="userform.email.$invalid">{{ 'EMAILINVALID' | translate}}</div>
                </div>
              </div>

              <div class="form-input">
                <i class="fa fa-bookmark-o prefix"></i>
                <input type="text" id="description" name="description" ng-model="ctl.user.description" placeholder="{{ 'DESCRIPTION' | translate}}" class="form-control">
              </div>

              <div class="text-center">
                <button id="editSubmit" class="btn btn-default">{{ 'EDIT' | translate}}</button>
              </div>  
            </form>  
          </div>
        </div>
      </div>
    </div>
  </div>
</div>