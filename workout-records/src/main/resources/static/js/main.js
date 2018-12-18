$(document).ready(function() {
	var page = new Page();
	page.init();
});

function Page() {
//	this.name = prompt("Please provide your name:");
}

Page.prototype.init = function() {
	this.bind();
};

Page.prototype.bind = function() {
	this.bindNavigation();
};

Page.prototype.bindNavigation = function() {
	var that = this;
	$("#my-reservations").click(function() {
		$.get("http://localhost:8081/myworkout/consume", function(data) {
				var html = "<h2>Member Information</h2>"; 
				html += "<table>";
				html += "<tr>";
				html += "<th>Member Id</th>"
				html += "<th>Person Name</th>"
				html += "<th>Date</th>"
				html += "</tr>"
					
				for (var x = 0; x < data.length-1; x++) {
					html += "<tr>";
					html += "<td>"+ data[x].memberId +"</td>";
					html += "<td>"+ data[x].personName + "</td>";
					html += "<td>"+ data[x].date +"</td>";
					html += "</tr>";
				}
				html += "</table>";
				
				$("#content").html(html);
		})
	});

	$("#catalog").click(function() {
		$.get("http://localhost:8081/myworkout/consume", function(data) {
			var html = "<h2>Workout</h2>"; 
			html += "<table>";
			html += "<tr>";
			html += "<th>Person Name</th>"
			html += "<th>Workout </th>"
			html += "<th>Heart Rate </th>"
			html += "<th>RPM</th>"
			html += "<th>Speed</th>"
			html += "</tr>"
				
			for (var x = 0; x < data.length-1; x++) {
				html += "<tr>";
				html += "<td>"+ data[x].personName +"</td>";
				html += "<td>"+ data[x].workoutType + " - " + data[x].workoutName + "</td>";
				html += "<td>"+ data[x].pulse +"</td>";
				html += "<td>"+ data[x].rpm +"</td>";
				html += "<td>"+ data[x].speed +"</td>";
				html += "</tr>";
			}
			html += "</table>";
			
			$("#content").html(html);
			
			$("a").click(function(){return false;});
			$(".reserve-link").click(function(e){
				var href = $(this).attr("href");
				href = href.replace("{user}", that.name);
				$.post(href, function(data){
					alert(data);
				});
				return false;
			});
		})
	});
};