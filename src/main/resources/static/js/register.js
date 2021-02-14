let register = (function () {

    document.getElementById('firstNameInput').addEventListener('input', function () {
        let input = document.getElementById('firstNameInput');
        if (input.value.length > 5) {
            document.getElementById('firstNameError').style.display = 'block';
        } else {
            document.getElementById('firstNameError').style.display = 'none';
        }
    });

})();