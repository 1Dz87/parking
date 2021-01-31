let addCarSubmit = (function () {

    let isCreate;

    let cancelClickEvent = function() {
        let dialog = document.getElementById('addCarDialog');
        dialog.close();
    };

    let setCreate = function() {
        addCarSubmit.isCreate = true;
    };

    let onTableClick = function (model, carId, bodyDescription, engineValue, typeDescription) {
        let dialog = document.getElementById('addCarDialog'),
            modelIn = document.getElementById('model'),
            bodyIn = document.getElementById('body'),
            eType = document.getElementById('eType'),
            engineValueIn = document.getElementById('engineValue'),
            carIdInput = document.getElementById('carIdInput');
        modelIn.value = model;
        bodyIn.value = bodyDescription;
        eType.value = typeDescription;
        engineValueIn.value = engineValue;
        carIdInput.value = carId;
        addCarSubmit.isCreate = false;
        dialog.show();
    };

    let submit = function () {
        let url = addCarSubmit.isCreate ? '/user/addCar' : '/user/updateCar',
            model = document.getElementById('model').value,
            body = document.getElementById('body').value,
            eType = document.getElementById('eType').value,
            engineValue = document.getElementById('engineValue').value,
            carPhoto = document.getElementById('carPhotoIn').value,
            carIdInput = document.getElementById('carIdInput').value;
        axios({
            method: 'post',
            url: url,
            params: {
                model: model,
                body: body,
                engineType: eType,
                engineValue: engineValue,
                id: carIdInput,
                carImage: carPhoto
            }
        }).then(function (response) {
            if (response.data.success) {
                // Изменить запись о текущем автомобиле в таблице автомобилей
                document.getElementById('addCarDialog').close();
            } else {
                document.getElementById('error').textContent = response.data.msg;
            }
        })
        .catch(function (error) {
            console.log(error);
        });
    };

    return {
        onTableClick: onTableClick,
        cancelEvent: cancelClickEvent,
        submit: submit,
        setCreate: setCreate
    }
})();