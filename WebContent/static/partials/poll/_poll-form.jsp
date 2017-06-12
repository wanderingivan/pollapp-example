
<div class="form-group">
  <div class="col-sm-10" ng-show="pollform.$submitted && ctl.error" >
  	<div class="alert alert-danger">
		{{ 'ERRORFORM' | translate}}  		
  	</div>
  </div>
  <div class="col-sm-10">
    <input id="title" type="text" name="title" ng-model="ctl.poll.name" class="form-control" placeholder="{{ 'POLLNAME' | translate}}" required />
    <div ng-show="pollform.$submitted || pollform.title.$touched"  class="colortext">
      <div id="titleError" ng-show="pollform.title.$error.required">{{ 'TITLEREQUIRED' | translate}}</div>
    </div>    
  </div>
</div>
<div class="form-group">
  <div class="col-sm-10">
    <textarea id="description" ng-model="ctl.poll.description" name="description" class="form-control"  placeholder="{{ 'POLLDESCRIPTION' | translate}}"></textarea>
  </div>
</div>
<div class="form-group" data-ng-repeat="opt in ctl.poll.options">
  <div class="col-sm-10">
    <input id="optionInput{{$index}}" type="text" name="option" class="form-control" ng-model="opt.optionName" value="" class=""  placeholder="{{ 'OPTNAME' | translate}}" required>
    <div class="btn btn-default" ng-show="$last" ng-click="ctl.addOption()"><i class="fa fa-plus-circle" aria-hidden="true"></i>&nbsp;{{ 'ADDOPT' | translate}}</div>
  </div>
</div>
<hr/>
<div class="form-group">
  <div class="col-sm-10">
    <button id="pollSubmit" type="submit" class="btn btn-default" value="Save">{{ 'SUBMIT' | translate}}</button>
  </div>
</div>