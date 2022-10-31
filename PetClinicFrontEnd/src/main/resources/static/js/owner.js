domain = "localhost";

$(function() {
	changePage('owner-page', '/ownerPage', 'owner');
});


function getData(page) {
	if (page == 'owner') {
		$.ajax({
			url: "http://" + domain + ":8187/owner/",
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			document.getElementById('owner-table').innerHTML = ``;
			var tableHtml = "<tr><th>Owner Id</th><th>Name</th><th>City</th><th>Phone Number</th><th>Pets</th><th>Actions</th></tr>";
			data.forEach(owner => {
				var petList = owner.petList;
				var petString = '';
				for (i = 0; i < petList.length; i++) {
					if (i == petList.length - 1) {
						petString += `${petList[i].name}( ${petList[i].petType} )`;
					} else {
						petString += `${petList[i].name}( ${petList[i].petType} ),`;
					}
				}
				tableHtml += `<tr><td>${owner.ownerId}</td><td>${owner.name}</td><td>${owner.city}</td><td>${owner.phNumber}</td><td>${petString}</td><td><input class="btn btn-info" type="button" value="Edit" data-bs-toggle="modal"
				data-bs-target="#ownerModal" onclick="editOwnerModal(${owner.ownerId})">&nbsp;&nbsp;<input class="btn btn-danger" type="button" value="Delete" onclick="deleteOwner(${owner.ownerId})"></td></tr>`
			})
			document.getElementById('owner-table').innerHTML = tableHtml;
		})
	} else if (page == 'pet') {
		$.ajax({
			url: "http://" + domain + ":8187/owner/pet",
			method: "GET",
			timeout: 0,
			headers: {
				"Content-Type": "application/json"
			}
		}).done(function(data) {
			document.getElementById('pet-table').innerHTML = ``;
			var tableHtml = '<tr><th>Pet Id</th><th>Name</th><th>Pet Type</th><th>Owner Id</th><th>Actions</th></tr>';
			data.forEach(pet => {
				tableHtml += `<tr><td>${pet.petId}</td><td>${pet.name}</td><td>${pet.petType}</td><td>${pet.ownerId}</td><td>&nbsp;&nbsp;<input class="btn btn-danger" type="button" value="Delete" onclick="deletePet(${pet.petId})"></td></tr>`;
			})
			document.getElementById('pet-table').innerHTML = tableHtml;
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

function addOwnerModal() {
	document.getElementById("ownerModalLabel").innerHTML = ``;
	document.getElementById("ownerModalLabel").innerHTML = 'Add new Owner';

	document.getElementById("ownerModalBody").innerHTML = ``;
	document.getElementById("ownerModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-4">
		<label for="ownerName" class="form-label">Name<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerName" placeholder="Enter owner Name" />
	</div>
	<div class="form-group col-sm-4">
		<label for="ownerCity" class="form-label">City<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerCity" placeholder="Enter City" />
	</div>
	<div class="form-group col-sm-4">
		<label for="ownerNumber" class="form-label">Phone Number<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerNumber" placeholder="Enter Phone Number" />
	</div>
</form>`;

	document.getElementById("ownerModalFooter").innerHTML = '';
	document.getElementById("ownerModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="saveOwner()">Save</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}

function searchById() {
	document.getElementById("ownerModalLabel").innerHTML = ``;
	document.getElementById("ownerModalLabel").innerHTML = 'Search Owner By Id';

	document.getElementById("ownerModalBody").innerHTML = ``;
	document.getElementById("ownerModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchO" class="form-label">Owner Id<span class="text-danger">*</span></label><input type="text" class="form-control" id="SearchO" placeholder="Enter Owner Id" /></div></form>`;

	document.getElementById("ownerModalFooter").innerHTML = '';
	document.getElementById("ownerModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getOwnerById()">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}

function saveOwner() {
	let name = document.getElementById("ownerName").value;
	let city = document.getElementById("ownerCity").value;
	let number = document.getElementById("ownerNumber").value;

	if (number.length != 10) {

		alert("Phone Number Invalid");
	} else {
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["city"] = city;
		jsonObj["phNumber"] = number;
		$.ajax({
			"url": "http://" + domain + ":8187/owner/",
			"method": "POST",
			"timeout": 0,
			"headers": {
				"Content-Type": "application/json"
			},
			"data": JSON.stringify(jsonObj)
		}).done(function(data) {
			document.getElementById("ownerName").value = ``;
			document.getElementById("ownerCity").value = ``;
			document.getElementById("ownerNumber").value = ``;
			if (data.status == 'Phone number already registered') {
				Swal.fire({
					title: 'Failure',
					text: 'Phone number already registered',
					icon: 'error',
					showConfirmButton: false,
					timer: 1500
				})
			} else if (data.status == 'New Owner created!!') {
				Swal.fire({
					title: 'Success',
					text: 'New Owner created!!',
					icon: 'success',
					showConfirmButton: false,
					timer: 1500
				})
			}
			getData('owner');
		})
	}
}

function editOwnerModal(id) {
	document.getElementById("ownerModalLabel").innerHTML = ``;
	document.getElementById("ownerModalLabel").innerHTML = 'Update Owner';

	document.getElementById("ownerModalBody").innerHTML = ``;
	document.getElementById("ownerModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-3">
		<label for="ownerId" class="form-label">Owner Id<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerId" disabled/>
	</div>
	<div class="form-group col-sm-3">
		<label for="ownerName" class="form-label">Name<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerName" placeholder="Enter owner Name" />
	</div>
	<div class="form-group col-sm-3">
		<label for="ownerCity" class="form-label">City<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerCity" placeholder="Enter City" />
	</div>
	<div class="form-group col-sm-3">
		<label for="ownerNumber" class="form-label">Phone Number<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="ownerNumber" placeholder="Enter Phone Number" />
	</div>
</form>`;


	$.ajax({
		url: "http://" + domain + ":8187/owner/" + id,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		document.getElementById("ownerId").value = data.ownerId;
		document.getElementById("ownerName").value = data.name;
		document.getElementById("ownerCity").value = data.city;
		document.getElementById("ownerNumber").value = data.phNumber;

		document.getElementById("ownerModalFooter").innerHTML = '';
		document.getElementById("ownerModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="editOwner(${data.phNumber})">Update</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;
	})
}

function editOwner(oldNumber) {
	let ownerId = document.getElementById("ownerId").value;
	let name = document.getElementById("ownerName").value;
	let city = document.getElementById("ownerCity").value;
	let number = document.getElementById("ownerNumber").value;

	if (number.length != 10) {

		alert("Phone Number Invalid");
	} else {
		var jsonObj = {};
		jsonObj["name"] = name;
		jsonObj["city"] = city;
		jsonObj["phNumber"] = number;
		$.ajax({
			"url": "http://" + domain + ":8187/owner/" + ownerId + "/" + oldNumber,
			"method": "PUT",
			"timeout": 0,
			"headers": {
				"Content-Type": "application/json",
				"authorization": "Bearer " + sessionStorage.getItem("authToken")
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
			getData('owner');
		})
	}
}

function getOwnerById() {
	let oid = document.getElementById("SearchO").value


	$.ajax({
		url: "http://" + domain + ":8187/owner/" + oid,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		console.log(data);
		if (data.status == null) {
			document.getElementById("ownerModalBody").innerHTML = ``;
			document.getElementById("ownerModalBody").innerHTML = `<table class="table table-dark table-striped" id="owner-search-table"></table>`;
			document.getElementById('owner-search-table').innerHTML = ``;


			var tableHtml = "<tr><th>Owner Id</th><th>Name</th><th>City</th><th>Phone Number</th><th>Pets</th></tr>";
			var owner = data;
			var petList = owner.petList;
			var petString = '';
			for (i = 0; i < petList.length; i++) {
				if (i == petList.length - 1) {
					petString += `${petList[i].name}( ${petList[i].petType} )`;
				} else {
					petString += `${petList[i].name}( ${petList[i].petType} ),`;
				}
			}
			tableHtml += `<tr><td>${owner.ownerId}</td><td>${owner.name}</td><td>${owner.city}</td><td>${owner.phNumber}</td><td>${petString}</td></tr>`
			document.getElementById('owner-search-table').innerHTML = tableHtml;
			document.getElementById("ownerModalFooter").innerHTML = '';
			document.getElementById("ownerModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="searchById()">Change Id</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;

		} else {
			Swal.fire({
				title: 'Failure',
				text: data.status,
				icon: 'warning',
				showConfirmButton: false,
				timer: 1500
			})
		}
	})

}

function deleteOwner(id) {
	Swal.fire({
		title: 'Do you want to delete the Owner along with its registered pets?',
		showCancelButton: true,
		confirmButtonText: 'Delete',
	}).then((result) => {

		if (result.isConfirmed) {
			$.ajax({
				url: "http://" + domain + ":8187/owner/" + id,
				method: "DELETE",
				timeout: 0,
				headers: {
					"Content-Type": "application/json"
				}
			}).done(function(data) {
				Swal.fire(data.status, '', 'success');
				getData('owner')
			})

		}
	})
}

function addPetModal() {
	document.getElementById("petModalLabel").innerHTML = ``;
	document.getElementById("petModalLabel").innerHTML = 'Add new Pet';

	document.getElementById("petModalBody").innerHTML = ``;
	document.getElementById("petModalBody").innerHTML = `<form id="edit-form" class="row">
	<div class="form-group col-sm-4">
		<label for="petName" class="form-label">Name<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="petName" placeholder="Enter Pet Name" />
	</div>
	<div class="form-group col-sm-4">
		<label for="petType" class="form-label">Pet Type<span
			class="text-danger">*</span></label><input type="text" class="form-control"
			id="petType" placeholder="Enter Pet Type" />
	</div>
	<div class="form-group col-sm-4">
		<label for="ownerId" class="form-label">Owner<span
			class="text-danger">*</span></label>
			<select class="form-control select2bs4" id="ownerId" style="width: 100%;" data-placeholder="Select Owner">
                    
                  </select>
	</div>
</form>`;
	let options = `<option value="">Select</option>`;
	document.getElementById("ownerId").innerHTML = ``;
	$.ajax({
		url: "http://" + domain + ":8187/owner/",
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		data.forEach(owner => {
			options += `<option value=${owner.ownerId}>${owner.name}</option>`
		})

		document.getElementById("ownerId").innerHTML = options;
	})


	document.getElementById("petModalFooter").innerHTML = '';
	document.getElementById("petModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="savePet()">Save</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}

function savePet() {
	let name = document.getElementById("petName").value;
	let petType = document.getElementById("petType").value;
	let ownerId = document.getElementById("ownerId").value;

	let jsonObj = {};
	jsonObj["name"] = name;
	jsonObj["petType"] = petType;
	jsonObj["ownerId"] = ownerId;
	$.ajax({
		"url": "http://" + domain + ":8187/owner/pet",
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Content-Type": "application/json",
			"authorization": "Bearer " + sessionStorage.getItem("authToken")
		},
		"data": JSON.stringify(jsonObj)
	}).done(function(data) {
		document.getElementById("petName").value = ``;
		document.getElementById("petType").value = ``;
		document.getElementById("ownerId").value = '';
		Swal.fire({
			title: 'Success',
			text: data.status,
			icon: 'success',
			showConfirmButton: false,
			timer: 1500
		})

		getData('pet');
	})

}

function findPetModal() {
	document.getElementById("petModalLabel").innerHTML = ``;
	document.getElementById("petModalLabel").innerHTML = 'Search Pet by Pet Id';

	document.getElementById("petModalBody").innerHTML = ``;
	document.getElementById("petModalBody").innerHTML = `<form id="edit-form" class="row"><div class="form-group col-sm-12"><label for="SearchP" class="form-label">Pet Id<span class="text-danger">*</span></label><input type="text" class="form-control" id="SearchP" placeholder="Enter Pet Id" /></div></form>`;

	document.getElementById("petModalFooter").innerHTML = '';
	document.getElementById("petModalFooter").innerHTML = '<button type="button" class="btn btn-primary" onclick="getPetById()">Find</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>';
}

function getPetById() {
	let id = document.getElementById("SearchP").value;
	$.ajax({
		url: "http://" + domain + ":8187/owner/pet/" + id,
		method: "GET",
		timeout: 0,
		headers: {
			"Content-Type": "application/json"
		}
	}).done(function(data) {
		if (data.status == null) {
			document.getElementById("petModalBody").innerHTML = ``;
			document.getElementById("petModalBody").innerHTML = `<table class="table table-dark table-striped" id="pet-search-table">


		</table>`;
			document.getElementById('pet-search-table').innerHTML = ``;
			let pet = data;
			var tableHtml = '<tr><th>Pet Id</th><th>Name</th><th>Pet Type</th><th>Owner Id</th></tr>';
			tableHtml += `<tr><td>${pet.petId}</td><td>${pet.name}</td><td>${pet.petType}</td><td>${pet.ownerId}</td></tr>`;
			document.getElementById("pet-search-table").innerHTML = tableHtml;
			document.getElementById("petModalFooter").innerHTML = '';
			document.getElementById("petModalFooter").innerHTML = `<button type="button" class="btn btn-primary" onclick="findPetModal()">Change Id</button><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>`;

		} else {
			Swal.fire({
				title: 'Failure',
				text: data.status,
				icon: 'warning',
				showConfirmButton: false,
				timer: 1500
			})
		}
	})

}

function deletePet(id) {
	Swal.fire({
		title: 'Do you want to de-register this pet?',
		showCancelButton: true,
		confirmButtonText: 'Delete',
	}).then((result) => {

		if (result.isConfirmed) {
			$.ajax({
				url: "http://" + domain + ":8187/owner/pet/" + id,
				method: "DELETE",
				timeout: 0,
				headers: {
					"Content-Type": "application/json"
				}
			}).done(function(data) {
				Swal.fire(data.status, '', 'success');
				getData('pet')
			})

		}
	})
}