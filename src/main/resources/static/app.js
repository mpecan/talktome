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
			stomp.subscribe('/topic/message', function(data) {
				messages.push(JSON.parse(data.body));
				listener.notify();
			});
		};

		stomp.connect({
			'Login' : name,
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
			stomp.send('/app/message', {}, JSON.stringify(request));
		},
		promise : listener.promise,
		connect : connect,
		connected : connected
	};
}).controller('ChatController', function($scope, MyData, $resource) {
	
	var User = $resource('/users?name=:name',{});
	$scope.message = {};
	MyData.promise.then(function(){
		$scope.$apply();
	});
	$scope.send = function() {
		$scope.message.from = $scope.fromName;
		$scope.message.to = $scope.toName;
		$scope.MyData.send($scope.message);
	};
	$scope.connect = function(){
		User.query({name : $scope.fromName},{},function(response){
			console.log(response);
		});
		User.query({name : $scope.toName},{});
		MyData.connect($scope.fromName);
	};
	$scope.MyData = MyData;
});