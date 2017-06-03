<div class="poll">
  <div class="container">
    <div class="row">
      <div class="col-md-offset-3 col-md-5">
        <div class="user-form">
          <div class="user-form block">
            <form role="form" ng-submit="ctl.changePassword()">
              <div class="form-header">
                <h3><i class="fa fa-pencil-square-o"></i> Change your password:</h3>
              </div>
              <div class="form-input">
                <i class="fa fa-lock prefix"></i>
                <input type="password" id="password" name="password" ng-model="ctl.oldPassword" placeholder="Your old password" class="form-control">
              </div>
              <div class="form-input">
                <i class="fa fa-lock prefix"></i>
                <input type="password" id="password" name="password" ng-model="ctl.newPassword" placeholder="Your new password" class="form-control">
              </div>
              <div class="text-center">
                <button class="btn btn-default">Change</button>
              </div>  
            </form>  
          </div>
        </div>
      </div>
    </div>
  </div>
</div>