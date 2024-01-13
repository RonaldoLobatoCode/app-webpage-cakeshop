function showProductDetails(productId, productName, productImage, productDescription, productPrice) {
    const previewDiv = document.querySelector('.products-preview .preview');

    previewDiv.querySelector('img').src = productImage;
    previewDiv.querySelector('h3').textContent = productName;
    previewDiv.querySelector('p').textContent = productDescription;
    previewDiv.querySelector('.price').textContent = productPrice;

    previewDiv.style.display = 'block';
}

const productElements = document.querySelectorAll('.item');
productElements.forEach(function (productElement) {
    productElement.addEventListener('click', function () {
        const productId = productElement.getAttribute('data-product-id');
        const productName = productElement.querySelector('.item-name').textContent;
        const productImage = productElement.querySelector('img').src;
        const productDescription = productElement.querySelector('p').textContent;
        const productPrice = productElement.querySelector('.item-price').textContent;

        showProductDetails(productId, productName, productImage, productDescription, productPrice);
    });
});

// JavaScript
const searchContainer = document.getElementById("search-container");
const searchInput = document.getElementById("search-input");

searchContainer.addEventListener("click", () => {
  searchInput.focus();
  if (window.innerWidth > 800) {
    searchContainer.style.width = "30%"; 
  }
});

searchInput.addEventListener("blur", () => {
  if (window.innerWidth > 800) {
    searchContainer.style.width = "400px"; 
  }
});
