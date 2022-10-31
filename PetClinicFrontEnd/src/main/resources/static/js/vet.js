domain = "localhost";

$(function() {
	changePage('vet-page', '/vetPage', 'vet');
});


function getData(page) {
	if (page == 'vet') {
		$.ajax({
			url: "http://" + domain + ":8187/vet/",
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			console.log(data);
			document.getElementById('vet-table').innerHTML = ``;
			var tableHtml = "<tr><th>Vet Id</th><th>Name</th><th>Phone Number</th><th>Speciality Id</th><th>Actions</th></tr>";
			data.forEach(vet => {
				tableHtml += `<tr><td>${vet.vetId}</td><td>${vet.name}</td><td>${vet.phNumber}</td><td>${vet.splId}</td><td><input class="btn btn-info" type="button" value="Edit" data-bs-toggle="modal"
				data-bs-target="#vetModal" onclick="editVetModal(${vet.vetId})">&nbsp;&nbsp;<input class="btn btn-danger" type="button" value="Delete" onclick="deleteVet(${vet.vetId})"></td></tr>`;
			})
			document.getElementById('vet-table').innerHTML = tableHtml;
		})
	} else if (page == 'spl') {
		$.ajax({
			url: "http://" + domain + ":8187/vet/spl",
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			document.getElementById('spl-table').innerHTML = ``;
			var tableHtml = "<tr><th>Speciality Id</th><th>Speciality Name</th><th>Actions</th></tr>";
			data.forEach(spl => {
				tableHtml += `<tr><td>${spl.splId}</td><td>${spl.splName}</td><td>&nbsp;&nbsp;<input class="btn btn-danger" type="button" value="Delete" onclick="deleteSpeciality(${spl.splId})"></td></tr>`;
			})
			document.getElementById('spl-table').innerHTML = tableHtml;
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


function listVetsModal() {

	document.getElementById("vetModalLabel").innerHTML = ``;
	document.getElementById("vetModalLabel").innerHTML = 'Search Vet By Speciality';

	document.getElementById("vetModalBody").innerHTML = ``;
	document.getElementById("vetModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchS" class="form-label">Speciality<span class="text-danger">*</span></label><select class="form-control select2bs4" id="SearchS" style="width: 100%;" data-placeholder="Select Speciality">
                    
                  </select></div></form>`;

	document.getElementById("vetModalFooter").innerHTML = '';
	document.getElementById("vetModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getVetsBySpeciality()">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';

	let options = `<option value="">Select</option>`;
	document.getElementById("SearchS").innerHTML = ``;
	$.ajax({
		url: "http://" + domain + ":8187/vet/spl",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(spl => {
			options += `<option value=${spl.splName}>${spl.splName}</option>`
		})

		document.getElementById("SearchS").innerHTML = options;
	})

}

function getVetsBySpeciality() {
	let spl = document.getElementById("SearchS").value;
	$.ajax({
		url: "http://" + domain + ":8187/vet/" + spl,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		if (data.length == 0) {
			Swal.fire({
				title: 'Failure',
				text: 'No vets available for this speciality yet!!',
				icon: 'warning',
				showConfirmButton: false,
				timer: 1500
			})
		} else {
			document.getElementById("vetModalBody").innerHTML = ``;
			document.getElementById("vetModalBody").innerHTML = `<table class="table table-dark table-striped" id="vet-search-table">
			</table>`;
			document.getElementById("vet-search-table").innerHTML = ``;
			var tableHtml = `<tr><th>Vet Id</th><th>Name</th><th>Phone Number</th><th>Speciality Id</th></tr>`;
			data.forEach(vet => {
				tableHtml += `<tr><td>${vet.vetId}</td><td>${vet.name}</td><td>${vet.phNumber}</td><td>${vet.splId}</td></tr>`;
			})
			document.getElementById("vet-search-table").innerHTML = tableHtml;
			document.getElementById("vetModalFooter").innerHTML = '';
			document.getElementById("vetModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="listVetsModal()">Change Speciality</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
		}

	})
}

function addVetModal() {
	document.getElementById("vetModalLabel").innerHTML = ``;
	document.getElementById("vetModalLabel").innerHTML = 'Add new Vet';

	document.getElementById("vetModalBody").innerHTML = ``;
	document.getElementById("vetModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-4">
		<label for="vetName" class="form-label">Name<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="vetName" placeholder="Enter Vet Name" />
	</div>
	<div class="form-group col-sm-4">
		<label for="vetNumber" class="form-label">Phone Number<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="vetNumber" placeholder="Enter Phone Number" />
	</div>
	<div class="form-group col-sm-4">
		<label for="splId" class="form-label">Speciality<span
			class="text-danger">*</span></label>
			<select class="form-control select2bs4" id="splId" style="width: 100%;" data-placeholder="Select Speciality">
                    
                  </select>
	</div>
	</form>`;

	let options = `<option value="">Select</option>`;
	document.getElementById("splId").innerHTML = ``;
	$.ajax({
		url: "http://" + domain + ":8187/vet/spl",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(spl => {
			options += `<option value=${spl.splId}>${spl.splName}</option>`
		})

		document.getElementById("splId").innerHTML = options;
	})

	document.getElementById("vetModalFooter").innerHTML = '';
	document.getElementById("vetModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="saveVet()">Save</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';

}

function saveVet() {
	let name = document.getElementById("vetName").value;
	let number = document.getElementById("vetNumber").value;
	let splId = document.getElementById("splId").value;

	if (number.length != 10) {

		alert("Phone Number Invalid");
	} else {
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["phNumber"] = number;
		jsonObj["splId"] = parseInt(splId);
		$.ajax({
			"url": "http://" + domain + ":8187/vet/",
			"method": "POST",
			"timeout": 0,
			"headers": {
				"Content-Type": "application/json"
			},
			"data": JSON.stringify(jsonObj)
		}).done(function(data) {
			document.getElementById("vetName").value = ``;
			document.getElementById("vetNumber").value = ``;
			document.getElementById("splId").value = '';
			if (data.status == 'Phone number already registered') {
				Swal.fire({
					title: 'Failure',
					text: data.status,
					icon: 'error',
					showConfirmButton: false,
					timer: 1500
				})
			} else {
				Swal.fire({
					title: 'Success',
					text: data.status,
					icon: 'success',
					showConfirmButton: false,
					timer: 1500
				})
			}
			getData('vet');
		})
	}
}

function editVetModal(id) {
	document.getElementById("vetModalLabel").innerHTML = ``;
	document.getElementById("vetModalLabel").innerHTML = 'Edit Vet Details';

	document.getElementById("vetModalBody").innerHTML = ``;
	document.getElementById("vetModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-3">
		<label for="vetId" class="form-label">Vet Id<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="vetId" placeholder="Enter Vet Id" disabled/>
	</div>
	<div class="form-group col-sm-3">
		<label for="vetName" class="form-label">Name<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="vetName" placeholder="Enter Vet Name" />
	</div>
	<div class="form-group col-sm-3">
		<label for="vetNumber" class="form-label">Phone Number<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="vetNumber" placeholder="Enter Phone Number" />
	</div>
	<div class="form-group col-sm-3">
		<label for="splId" class="form-label">Speciality<span
			class="text-danger">*</span></label>
			<select class="form-control select2bs4" id="splId" style="width: 100%;" data-placeholder="Select Speciality">
                    
                  </select>
	</div>
	</form>`;

	let options = `<option value="">Select</option>`;
	document.getElementById("splId").innerHTML = ``;
	$.ajax({
		url: "http://" + domain + ":8187/vet/spl",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(spl => {
			options += `<option value=${spl.splId} id="spl-${spl.splId}">${spl.splName}</option>`
		})

		document.getElementById("splId").innerHTML = options;
	})

	document.getElementById("vetId").value = id;
	$.ajax({
		url: "http://" + domain + ":8187/vet/id/" + id,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		document.getElementById("vetName").value = data.name;
		document.getElementById("vetNumber").value = data.phNumber;
		document.getElementById("spl-" + data.splId).setAttribute("selected", "selected");

		document.getElementById("vetModalFooter").innerHTML = '';
		document.getElementById("vetModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="editVet(${data.phNumber})">Update</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
	})

}

function editVet(oldNumber) {
	let vetId = document.getElementById("vetId").value;
	let name = document.getElementById("vetName").value;
	let number = document.getElementById("vetNumber").value;
	let splId = document.getElementById("splId").value;

	if (number.length != 10) {

		alert("Phone Number Invalid");
	} else {
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["phNumber"] = number;
		jsonObj["splId"] = parseInt(splId);
		$.ajax({
			"url": "http://" + domain + ":8187/vet/" + vetId + "/" + oldNumber,
			"method": "PUT",
			"timeout": 0,
			"headers": {
				"Content-Type": "application/json"
			},
			"data": JSON.stringify(jsonObj)
		}).done(function(data) {
			if (data.status == 'Phone number already registered') {
				Swal.fire({
					title: 'Failure',
					text: data.status,
					icon: 'error',
					showConfirmButton: false,
					timer: 1500
				})
			} else {
				Swal.fire({
					title: 'Success',
					text: data.status,
					icon: 'success',
					showConfirmButton: false,
					timer: 1500
				})
			}
			getData('vet');
		})
	}
}

function deleteVet(id) {
	Swal.fire({
		title: 'Do you want to de-register this Veterinarian?',
		showCancelButton: true,
		confirmButtonText: 'Delete',
	}).then((result) => {

		if (result.isConfirmed) {
			$.ajax({
				url: "http://" + domain + ":8187/vet/" + id,
				method: "DELETE",
				timeout: 0,
				headers: {
					"Content-Type": "application/json"
				}
			}).done(function(data) {
				Swal.fire(data.status, '', 'success');
				getData('vet')
			})

		}
	})
}

function addSplModal() {
	document.getElementById("splModalLabel").innerHTML = ``;
	document.getElementById("splModalLabel").innerHTML = 'Add new Speciality';

	document.getElementById("splModalBody").innerHTML = ``;
	document.getElementById("splModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-12">
		<label for="splName" class="form-label">Speciality<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="splName" placeholder="Enter New Speciality Name" />
	</div>
	</form>`;

	document.getElementById("splModalFooter").innerHTML = '';
	document.getElementById("splModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="saveSpeciality()">Save</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;

}

function saveSpeciality() {
	let splName = document.getElementById("splName").value;
	var jsonObj = {}
	jsonObj["splName"] = splName;
	console.log(jsonObj);
	$.ajax({
		"url": "http://" + domain + ":8187/vet/spl",
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Content-Type": "application/json"
		},
		"data": JSON.stringify(jsonObj)
	}).done(function(data) {
		document.getElementById("splName").value=``;
		console.log(data);
		if (data.status == 'Speciality already registered') {
			Swal.fire({
				title: 'Failure',
				text: data.status,
				icon: 'error',
				showConfirmButton: false,
				timer: 1500
			})
		} else {
			Swal.fire({
				title: 'Success',
				text: data.status,
				icon: 'success',
				showConfirmButton: false,
				timer: 1500
			})
		}
		getData('spl');
	})
}

function deleteSpeciality(id){
	Swal.fire({
		title: 'Do you want to delete this Speciality and all the veterinarians enrolled with it?',
		showCancelButton: true,
		confirmButtonText: 'Delete',
	}).then((result) => {

		if (result.isConfirmed) {
			$.ajax({
				url: "http://" + domain + ":8187/vet/spl/" + id,
				method: "DELETE",
				timeout: 0,
				headers: {
					"Content-Type": "application/json"
				}
			}).done(function(data) {
				Swal.fire(data.status, '', 'success');
				getData('spl')
			})

		}
	})
	
}