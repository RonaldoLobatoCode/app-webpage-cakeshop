
// Pagination

var table = document.getElementById('id_table');
var prevBtn = document.getElementById('prevBtn');
var nextBtn = document.getElementById('nextBtn');
var currentPageElement = document.getElementById('currentPage');

var rowsPerPage = 5;
var currentPage = 1;

var totalRows = table.tBodies[0].rows.length;
var totalPages = Math.ceil(totalRows / rowsPerPage);

function showPage(page) {
	var startIndex = (page - 1) * rowsPerPage;
	var endIndex = startIndex + rowsPerPage;

	for (var i = 0; i < totalRows; i++) {
		var row = table.tBodies[0].rows[i];
		row.style.display = 'none';
	}

	for (var j = startIndex; j < endIndex && j < totalRows; j++) {
		var row = table.tBodies[0].rows[j];
		row.style.display = '';
	}

	currentPageElement.textContent = 'Página ' + page + ' de ' + totalPages;
}

function goToPrevPage() {
	if (currentPage > 1) {
		currentPage--;
		showPage(currentPage);
	}
}

function goToNextPage() {
	if (currentPage < totalPages) {
		currentPage++;
		showPage(currentPage);
	}
}

showPage(currentPage);

prevBtn.addEventListener('click', goToPrevPage);
nextBtn.addEventListener('click', goToNextPage);


function updateFileNameUpdate() {
    const updatefile = document.getElementById("updatefile");
    const fileName = updatefile.files[0].name;
    const fileDisplay = document.getElementById("file-name-display");
    fileDisplay.textContent = fileName;

    if (updatefile.files[0].type.startsWith("image/")) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $("#imagePreview").attr("src", e.target.result);
        };
        reader.readAsDataURL(updatefile.files[0]);
    }
}




function updateFileName() {
    const fileInput = document.getElementById("fileInput");
    const fileName = fileInput.files[0].name;
    const uploadText = document.querySelector(".upload-text-register");
    uploadText.textContent = fileName;
}



//Validation register end product
function registerProduct() {
	var isValid = true;

	var name = document.getElementById('name');
	var description = document.getElementById('description');
	var price = document.getElementById('price');
	var category = document.getElementById('category');
	var fileInput = document.getElementById('fileInput');

	var nameError = document.getElementById('nameError');
	var descriptionError = document.getElementById('descriptionError');
	var priceError = document.getElementById('priceError');
	var nameCategory = document.getElementById('nameCategory');
	var imageError = document.getElementById('imageError');

	nameError.textContent = "";
	descriptionError.textContent = "";
	priceError.textContent = "";
	nameCategory.textContent = "";
	imageError.textContent = "";

	if (name.value.trim() === "") {
        nameError.textContent = 'This field is required';
        isValid = false;
        event.preventDefault();
    } else if (name.value.length > 100) { 
        nameError.textContent = 'Name should be 100 characters or less';
        isValid = false;
        event.preventDefault();
    } else if (!isNaN(name.value)) {
        nameError.textContent = 'Name should contain only letters';
        isValid = false;
		event.preventDefault();
    } else {
        nameError.textContent = '';
    }

	if (description.value.trim() === "") {
        descriptionError.textContent = 'This field is required';
        isValid = false;
        event.preventDefault();
    } else if (description.value.length > 255) { 
        descriptionError.textContent = 'Description should be 255 characters or less';
        isValid = false;
        event.preventDefault();
    } else if (!isNaN(description.value)) {
        descriptionError.textContent = 'Description should contain only letters';
        isValid = false;
		event.preventDefault();
    } else {
        descriptionError.textContent = '';
    }

    var priceValue = parseFloat(price.value); 
    if (isNaN(priceValue) || priceValue <= 0) {
        isValid = false;
        price.classList.add('error'); 
        priceError.textContent = 'Enter a valid price';
		event.preventDefault();
    } else {
        priceError.textContent = '';
    }

	if (category.value === "-1") {
		nameCategory.textContent = 'Select a valid category';
		isValid = false;
		event.preventDefault();
	} else {
		nameCategory.textContent = '';
	}

	if (fileInput.files.length === 0) {
		imageError.textContent = 'Select an image file';
		isValid = false;
		event.preventDefault();
	} else {
        // Verificar el tamaño de la imagen
        var maxSizeInBytes = 5242880; // Tamaño máximo permitido en bytes (5 MB)
        var fileSize = fileInput.files[0].size; // Tamaño del archivo seleccionado

        if (fileSize > maxSizeInBytes) {
            imageError.textContent = 'Image size is too large. Max size allowed is 5 MB.';
            isValid = false;
            event.preventDefault();
        } else {
            imageError.textContent = '';
        }
    }

	if (isValid) {
		alert('Registro correcto');
	}

	return isValid;
}

//Search Product
const searchContainer = document.getElementById("search-container");
const searchInput = document.getElementById("search-input");

searchContainer.addEventListener("click", () => {
  searchInput.focus();
  searchContainer.style.width = "300px";
});

searchInput.addEventListener("blur", () => {
  searchContainer.style.width = "180px";
});

// Delete Product
function confirmarEliminacion(event, idproduct) {
    if (confirm("¿Está seguro de que desea eliminar este producto?")) {
        // Realizar la solicitud DELETE
        fetch(`/delete/${idproduct}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.status === 200) {
				
            } else {
				
            }
        });

        event.preventDefault();
    }
    return false;
}

// Confirm Delete Product 
function confirmDeletion() {
	if (confirm("Are you sure you want to remove this product?")) {
		alert("Product removed")
		return true;
	} else {
		return false;
	}
}

