angular.module('talkToMe', [ 'ngWebSocket','ngResource' ])
.factory('MyData', function($websocket, $q) {
	var listener = $q.defer();
	var connected = false;
	var messages = [];
	var dataStream = null;
	var stomp = null;
	
	var connect = function(name){
		if (connected){
			return;
		}
		dataStream = $websocket('ws://localhost:8080/chat');
		stomp = Stomp.over(dataStream.socket);
		var startListener = function() {
			connected = true;
			listener.notify(true);
			stomp.subscribe('/topic/message', function(data) {
				messages.push(JSON.parse(data.body));
				listener.notify(true);
			});
		};

		stomp.connect({
			login : name,
			passcode : name,
			'client-id' : name
		}, startListener);
		
		dataStream.onError(function(event) {
			console.log('connection Error', event);
		});
		dataStream.onClose(function(event) {
			connected = false;
			console.log('connection closed', event);
		});
		dataStream.onOpen(function() {
			console.log('connection open');
		});
	};

	return {
		messages : messages,
		send : function(request) {
			stomp.send('/app/message', {}, request);
		},
		promise : listener.promise,
		connect : connect
	};
}).controller('ChatController', function($scope, MyData, $resource) {
	var controller = this;
	$scope.connected = false;
	var User = $resource('/users?name=:name',{});
	controller.message = {};
	MyData.promise.then(function(connected){
		$scope.connected = true;
		$scope.$apply();
	},null, function(value) {
		$scope.connected = value;
    });
	$scope.send = function() {
		controller.message.from = controller.username;
		controller.message.to = controller.chatWith;
		controller.MyData.send(JSON.stringify(controller.message));
		controller.message.text = '';
	};
	$scope.connect = function(){
		User.get({name : controller.username},{},function(response){
			if (response["_embedded"].users && response["_embedded"].users.length > 0){
				controller.user = response["_embedded"].users[0];
			}
		});
		MyData.connect(controller.username);
	};
	controller.MyData = MyData;
});