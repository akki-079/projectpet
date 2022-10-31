$(function () {
    changeTab('visit-tab','/visit');
});


function changeTab(id,page){
	$('#main-content').load(page);
	var oldSelectedPage = document.getElementsByClassName('selectedTab');
    for (var i = 0; i < oldSelectedPage.length; i++) {
        oldSelectedPage[i].classList.remove("text-warning");
        oldSelectedPage[i].classList.remove("selectedTab");
    }
	document.getElementById(id).classList.add("text-warning");
    document.getElementById(id).classList.add("selectedTab");
}


