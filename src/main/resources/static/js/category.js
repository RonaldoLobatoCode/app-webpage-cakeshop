
//Register Category
function registerCategory(){

    var isValid = true;

    var name = document.getElementById('name');

    var nameError = document.getElementById('nameError');

    nameError.textContent = "";

    if (name.value.trim() === "") {
        nameError.textContent = 'This field is required';
        isValid = false;
        event.preventDefault();
    } else if (name.value.length > 25) { 
        nameError.textContent = 'Name should be 25 characters or less';
        isValid = false;
        event.preventDefault();
    } else if (!isNaN(name.value)) {
        nameError.textContent = 'Name should contain only letters';
        isValid = false;
		event.preventDefault();
    } else {
        nameError.textContent = '';
    }

    if (isValid) {
		alert('Registro correcto');
	}

	return isValid;

}

