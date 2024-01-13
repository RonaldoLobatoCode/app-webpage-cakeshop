const whatsappButton = document.querySelector('.whatsapp-button a');
const whatsappMessage = document.querySelector('#whatsapp-message');

whatsappButton.addEventListener('mouseenter', function () {
  whatsappMessage.textContent = 'WhatsApp';
});

whatsappButton.addEventListener('mouseleave', function () {
  whatsappMessage.textContent = 'Hola, ¿en qué podemos ayudarte?';
});
