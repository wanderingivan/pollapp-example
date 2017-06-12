<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">


	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css" />
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>
  	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/0.3.1/angular-ui-router.js" type="text/javascript"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-resource.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.2.1/Chart.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/angular.chartjs/1.0.0/angular-chart.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.7/angular-cookies.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-translate/2.10.0/angular-translate.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-translate-loader-static-files/2.10.0/angular-translate-loader-static-files.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/upload.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/app.module.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/app.config.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/service/services.module.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/service/services.config.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/controller/controllers.module.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/static/js/controller/controllers.config.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
    <script type="text/javascript" src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <style>
            [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
           display: none !important;}
           
           .prefix:hover{
           		cursor:pointer;
           }
           #search-form{
           		display:none;
           }
    </style> 
	<title>Poll App Index</title>
  </head>

  <body ng-app="pollApp"> 
    <div class="header">
      <div class="header-menu">
      	<div class="container">
      	  <div class="row">
      	  
      	    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-1" aria-expanded="false">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
      	    <a class="navbar-brand" ui-sref="polls">
      	    	Poll<span>App</span>
      	    </a>
      	    <nav  class="navbar pull-right" role="navigation" translate-cloak>
      	      <div id="navbar-collapse-1" class="collapse navbar-collapse">
      	      	<ul class="nav navbar-nav nav-menu">
      	      		<li class="nav-btn"><a class="nav-link" ui-sref="polls">{{'HOME' | translate}}</a></li>
      	      		<li class="nav-btn" ng-show="authenticated"><a ui-sref="newPoll" class="nav-link" >{{'NEW_POLL' | translate}}</a></li>
      	      		<li class="nav-btn" ng-show="!authenticated"><a ui-sref="login" class="nav-link" >{{'NEW_POLL' | translate}}</a></li>
      	      		<li class="nav-btn"><a class="nav-link search-ctl" href="#">{{'SEARCH' | translate}}</a></li>
      	      		<li class="nav-btn" ng-show="!authenticated"><a id="loginLink" ui-sref="login" class="nav-link">{{'LOGIN' | translate}}</a></li>
      	      		<li id="userMenu" class="nav-btn" ng-show="authenticated" class="dropdown">
      	      		  <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">{{'USER_OPTIONS' | translate}}&nbsp;<i class="fa fa-angle-down menucaret"></i></a>
      	      		  <ul class="dropdown-menu">
      	      		  	<li><a ui-sref="viewUser({id:0})" class="dropdown-link nav-link">{{'HOMEPAGE' | translate}}</a></li>
    	  	            <li ng-controller="authentication as auth" ng-show="authenticated"><a id="logoutSubmit" class="nav-link" ng-click="auth.logout()">{{'LOGOUT' | translate}}</a></li>
      	      		  </ul>
      	      		</li>
      	      		<li class="nav-btn visible-xs" ng-show="authenticated">
      	      		  <a ui-sref="viewUser({id:0})" class="dropdown-link nav-link">{{'HOMEPAGE' | translate}}</a>      	      		
      	      		</li>
      	      		<li id="search-form" ng-controller="PollSearchController as search">      
      	      		  <form ng-submit="search.search()" class="form-inline">
      	      		     <div class="form-input form-group">
                           <i class="fa fa-times-circle prefix search-ctl"></i>
                           <input type="text" id="email" name="email" ng-model="search.query" placeholder="Search polls" class="form-control">
                         </div>
                         <div class="form-group" style="margin-left:30px;">
                           <button type="submit" class="btn btn-default">{{'SEARCH' | translate}}</button>
                         </div>
                      </form>
                    </li>
      	      	</ul>
      	      	<ul class="nav navbar-nav pull-right nav-menu" ng-controller="translation as ctl">
      	      	  <li class="nav-btn"><a class="nav-link" href="#" ng-click="ctl.changeLanguage('en')">English</a></li>
      	      	  <li class="nav-btn"><a class="nav-link" href="#" ng-click="ctl.changeLanguage('bg')">Български</a></li>
      	      	</ul>
      	      </div>
      	    </nav>
      	  </div>
      	</div>
      </div>
    </div>
    <div ui-view ng-cloak style="min-height:750px;"></div>
    <div class="footer">
     <div class="container">
       <div class="row">
       	 <div class="col-md-4">
       	   <h4 class="colortext">Made possible by</h4>
		    <ul>
		      <li><a class="colortext" href="https://angularjs.org/">AngularJS</a></li>
			  <li><a class="colortext" href="https://spring.io/">Spring</a></li>
			  <li><a class="colortext" href="http://www.h2database.com">H2Database</a></li>
			  <li><a class="colortext" href="http://www.mysql.com/">MySql</a></li>
		    </ul>
       	 </div>
       	 <div class="col-md-4">
       	 </div>
       	 <div class="col-md-4 text-center">
       	   <h4 class="center colortext">About me</h4>
       	   <p><a class="colortext" href="https://github.com/wanderingivan" class="colortext">GitHub</a></p>
       	 </div>
       </div>
     </div> 
    </div>
    <script type="text/javascript">
      	$(document).ready(function(){
      		$(".search-ctl").click(function(){
      			$(".nav-btn").toggle(750);
      			$("#search-form").toggle(750);
      		})
      	});
    </script>
  </body>
</html>