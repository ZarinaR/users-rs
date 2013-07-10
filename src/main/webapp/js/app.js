App = Ember.Application.create({});

// add route
App.Router.map(function() {
	this.resource('addUser', function() {
		this.resource('addUserForm');
	});

	this.resource('removeUser');
	this.resource('displayAllUsers');
	this.resource('retrieve');

});

App.TextField = Em.TextField.extend(Ember.TargetActionSupport, {
	insertNewline : function() {
		this.triggerAction();
	}
});

// check local storage

App.Store = DS.Store.extend({
	revision : 12,
	adapter : DS.RESTAdapter.extend({
		url : 'http://localhost:8080/jboss-as-users-rs/rest'
	})

});

// User Model
App.User = DS.Model.extend({

	idString : DS.attr('number'),
	fname : DS.attr('string'),
	lname : DS.attr('string')
});



App.AddUserController = Ember.Controller.extend({
	addNewUser : function() {

		$.post('http://localhost:8080/jboss-as-users-rs/rest/users', {
			"id" : this.get("userId"),
			"fname" : this.get("fname"),
			"lname" : this.get("lname")
		});

		window.alert("New user " + this.get("fname") + " is added");

		
		this.get('target').transitionTo('displayAllUsers');

	}
});

// when we visit displayAllUsers we associate which models we want to use/see

App.DisplayAllUsersRoute = Ember.Route.extend({
	model : function() {
		// return all the User models
		return App.User.find();
	}
});

App.RemoveUserController = Ember.Controller.extend({
	removeUser : function() {

		var id = this.get("usern");
		var urlString = "http://localhost:8080/jboss-as-users-rs/rest/users/"
				+ id;

		var jsonObj = null;
		$.getJSON(urlString, function(data) {
			jsonObj = data;
		});

		$.ajax({
			url : urlString,
			type : 'DELETE',
			success : function(result) {
				alert("User removed: " + jsonObj.fname + " "
						+ jsonObj.lname);
			}
		});

		
		this.get('target').transitionTo('displayAllUsers');

	}

});

App.RetrieveController = Ember.Controller.extend({
	getUser : function() {

		var id = this.get("usern");
		var urlString = "http://localhost:8080/jboss-as-users-rs/rest/users/"
				+ id;

		var jsonObj = null;
		$.getJSON(urlString, function(data) {
			jsonObj = data;
			alert("User is: " + jsonObj.fname + " " + jsonObj.lname);
		});

	}

});
