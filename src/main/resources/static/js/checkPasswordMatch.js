function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity("Пароли не совпадают");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}