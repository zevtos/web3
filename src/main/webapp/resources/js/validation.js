const form = document.querySelector("form");
const errorContainer = document.getElementById("error-container");

function validateForm() {
    const errorContainer = document.getElementById("error-container");
    errorContainer.innerHTML = "";

    let isValid = true;

    const xField = document.querySelector("#x");
    const yField = document.querySelector("#y");
    const rField = document.querySelector("#r");

    if (!xField.value) {
        addError("Ошибка: Не выбрано значение X.");
        isValid = false;
    }

    const yValue = parseFloat(yField.value);
    if (isNaN(yValue) || yValue < -3 || yValue > 5) {
        addError("Ошибка: Y должен быть числом от -3 до 5.");
        isValid = false;
    }

    if (!rField.value) {
        addError("Ошибка: Не выбрано значение R.");
        isValid = false;
    }

    return isValid;
}

function addError(message) {
    const error = document.createElement("p");
    error.textContent = message;
    error.style.color = "red";
    errorContainer.appendChild(error);
}
