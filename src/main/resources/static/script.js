
document.addEventListener('DOMContentLoaded', function () {
    const menuCheckbox = document.getElementById('check');
    const navMenu = document.querySelector('.navbar-nav');

    menuCheckbox.addEventListener('change', function () {
        if (menuCheckbox.checked) {
            navMenu.style.display = 'flex';
        } else {
            navMenu.style.display = 'none';
        }
    });
});
