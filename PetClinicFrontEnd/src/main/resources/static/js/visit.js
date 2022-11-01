domain = "52.23.162.91";

$(function() {
	changePage('add-page', '/addVisitPage', 'add');


});


function getData(page) {
	if (page == 'visit') {
		$.ajax({
			url: "http://" + domain + ":8187/visit/",
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			document.getElementById('visit-table').innerHTML = ``;
			var tableHtml = "<tr><th>Visit Id</th><th>Date Time</th><th>Owner Id</th><th>Pet Id</th><th>Symptoms</th><th>Speciality</th></tr>";
			data.forEach(visit => {
				tableHtml += `<tr><td>${visit.visitId}</td><td>${visit.dateTime}</td><td>${visit.ownerId}</td><td>${visit.petId}</td><td>${visit.symptoms}</td><td>${visit.speciality}</td></tr>`;
			})
			document.getElementById('visit-table').innerHTML = tableHtml;
		})
	}
}

function changePage(id, page, pageId) {
	$('#main-page-content').load(page);
	var oldSelectedPage = document.getElementsByClassName('nav-link active');
	for (var i = 0; i < oldSelectedPage.length; i++) {
		oldSelectedPage[i].classList.remove("text-warning");
		oldSelectedPage[i].classList.remove("active");
	}
	document.getElementById(id).classList.add("text-warning");
	document.getElementById(id).classList.add("active");
	getData(pageId);
}

function openPidModal() {
	document.getElementById("visitModalLabel").innerHTML = ``;
	document.getElementById("visitModalLabel").innerHTML = 'Find Visits by Pet Id';

	document.getElementById("visitModalBody").innerHTML = ``;
	document.getElementById("visitModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchPID" class="form-label">Pet Id<span class="text-danger">*</span></label><input type="text" class="form-control" id="SearchPID" placeholder="Enter Pet Id" /></div></form>`;

	document.getElementById("visitModalFooter").innerHTML = '';
	document.getElementById("visitModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getVisitById(`PID`)">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}
function openPidsModal() {
	document.getElementById("visitModalLabel").innerHTML = ``;
	document.getElementById("visitModalLabel").innerHTML = 'Find Visits by Pet Ids<p><small class="text-muted">enter comma separated pet Ids.</small></p>';

	document.getElementById("visitModalBody").innerHTML = ``;
	document.getElementById("visitModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchPIDs" class="form-label">Pet Ids<span class="text-danger">*</span></label><input type="text" class="form-control" id="SearchPIDs" placeholder="Enter Pet Ids" /></div></form>`;

	document.getElementById("visitModalFooter").innerHTML = '';
	document.getElementById("visitModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getVisitById(`PIDs`)">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}
function openOidModal() {
	document.getElementById("visitModalLabel").innerHTML = ``;
	document.getElementById("visitModalLabel").innerHTML = 'Find Visits by Owner Id';

	document.getElementById("visitModalBody").innerHTML = ``;
	document.getElementById("visitModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchOID" class="form-label">Owner Id<span class="text-danger">*</span></label><input type="text" class="form-control" id="SearchOID" placeholder="Enter Owner Id" /></div></form>`;

	document.getElementById("visitModalFooter").innerHTML = '';
	document.getElementById("visitModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getVisitById(`OID`)">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}

function getVisitById(IdType) {
	let url;
	let fn;
	if (IdType == 'OID') {
		let id = document.getElementById("Search" + IdType).value;
		url = "http://" + domain + ":8187/visit/owner/" + id;
		fn = ' openOidModal()';
	} else if (IdType == 'PIDs') {
		let id = document.getElementById("Search" + IdType).value;
		url = "http://" + domain + ":8187/visit/pets/" + id;
		fn = ' openPidsModal()';
	} else if (IdType == 'PID') {
		let id = document.getElementById("Search" + IdType).value;
		url = "http://" + domain + ":8187/visit/" + id;
		fn = ' openPidModal()';
	}
	document.getElementById("visitModalBody").innerHTML = ``;
	document.getElementById("visitModalBody").innerHTML = `<table class="table table-dark table-striped" id="visit-search-table"></table>`;

	$.ajax({
		url: url,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		document.getElementById('visit-search-table').innerHTML = ``;
		var tableHtml = "<tr><th>Visit Id</th><th>Date Time</th><th>Owner Id</th><th>Pet Id</th><th>Symptoms</th><th>Speciality</th></tr>";
		data.forEach(visit => {
			tableHtml += `<tr><td>${visit.visitId}</td><td>${visit.dateTime}</td><td>${visit.ownerId}</td><td>${visit.petId}</td><td>${visit.symptoms}</td><td>${visit.speciality}</td></tr>`;
		})
		document.getElementById('visit-search-table').innerHTML = tableHtml;

		document.getElementById("visitModalFooter").innerHTML = '';
		document.getElementById("visitModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="${fn}">Change Id</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
	})
}



function loadOwnerList() {
	let options = `<option value="">Select</option>`;
	$.ajax({
		url: "http://" + domain + ":8187/owner/",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(owner => {
			options += `<option value="${owner.ownerId}">${owner.name}</option>`;
		})
		document.getElementById("owner").innerHTML = options;
	})
}

function petList(owner) {
	console.log(owner.value);
	if (owner.value != "") {
		let options = `<option value="">Select</option>`;
		$.ajax({
			url: "http://" + domain + ":8187/owner/pets/" + owner.value,
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			data.forEach(pet => {
				options += `<option value="${pet.petId}">${pet.name}( ${pet.petType} )</option>`;
			})
			document.getElementById("pet").innerHTML = options;
		})
	}

}

function splList() {
	let options = `<option value="">Select</option>`;
	$.ajax({
		url: "http://" + domain + ":8187/vet/spl",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(spl => {
			options += `<option value="${spl.splName}">${spl.splName}</option>`;
		})
		document.getElementById("speciality").innerHTML = options;
	})
}

function loadMinutes() {

	let minHtml = '<option value="">Select</option>'
	for (let i = 0; i <= 59; i++) {
		minHtml += `<option value="${i}">${i}</option>`
	}
	document.getElementById('minutes').innerHTML = minHtml;
}

function loadHours() {
	let hrHtml = '<option value="">Select</option>'
	for (let i = 0; i <= 23; i++) {
		hrHtml += `<option value="${i}">${i}</option>`
	}
	document.getElementById('hours').innerHTML = hrHtml;
}


function recordVisit() {
	let ownerId = document.getElementById("owner").value;
	let petId = document.getElementById("pet").value;
	let spl = document.getElementById("speciality").value;
	let date = document.getElementById("Effective").value;
	let hours = document.getElementById("hours").value;
	let minutes = document.getElementById("minutes").value;
	let symptoms = document.getElementById("symptoms").value;
	
	if(parseInt(hours)<10){
		hours = "0"+hours;
	}
	if(parseInt(minutes)<10){
		minutes = "0"+minutes;
	}
	

	date = date.slice(3, 5) + "-" + date.slice(0, 2) + "-" + date.slice(6, 10) + " " + hours + minutes + "hrs";

	jsonObj = {};
	jsonObj["ownerId"] = ownerId;
	jsonObj["petId"] = petId;
	jsonObj["symptoms"] = symptoms;
	jsonObj["dateTime"] = date;
	jsonObj["speciality"] = spl;
	$.ajax({
		"url": "http://" + domain + ":8187/visit/",
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Content-Type": "application/json"
		},
		"data": JSON.stringify(jsonObj)
	}).done(function(data) {
		Swal.fire({
			title: 'Success',
			text: data.status,
			icon: 'success',
			showConfirmButton: false,
			timer: 1500
		})
		document.getElementById("owner").value = '';
		document.getElementById("pet").value = '';
		document.getElementById("speciality").value = '';
		document.getElementById("Effective").value = '';
		document.getElementById("hours").value = '';
		document.getElementById("minutes").value = '';
		document.getElementById("symptoms").value = '';
	})
}

function myFunction() {
	document.getElementById("owner").value = '';
	document.getElementById("pet").value = '';
	document.getElementById("speciality").value = '';
	document.getElementById("Effective").value = '';
	document.getElementById("hours").value = '';
	document.getElementById("minutes").value = '';
	document.getElementById("symptoms").value = '';
}