let addCarSubmit = (function () {

    let submitFn = function () {
        let form = document.getElementById('addCarForm');
        form.action = '/user/addCar';
        form.method = 'POST';
        form.submit();
    };

    let cancelClickEvent = function() {
        let dialog = document.getElementById('addCarDialog');
        dialog.close();
    };

    let onTableClick = function (carId, carModel) {
        let input = document.getElementById(carModel + '_' + carId);
        input.style = 'display: block';
    };

    return {
        submit: submitFn,
        onTableClick: onTableClick,
        cancelEvent: cancelClickEvent
    }
})();