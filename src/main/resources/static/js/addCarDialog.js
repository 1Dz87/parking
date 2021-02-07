let addCarDialogFunctions = (function () {

    let isCreate;

    let cancelClickEvent = function() {
        let dialog = document.getElementById('addCarDialog');
        dialog.close();
    };

    let setCreate = function() {
        addCarDialogFunctions.isCreate = true;
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
        addCarDialogFunctions.isCreate = false;
        dialog.show();
    };

    let submit = function () {
        let url = addCarDialogFunctions.isCreate ? '/user/addCar' : '/user/updateCar',
            model = document.getElementById('model').value,
            body = document.getElementById('body').value,
            eType = document.getElementById('eType').value,
            engineValue = document.getElementById('engineValue').value,
            carPhoto = document.getElementById('carPhotoIn').files[0],
            carIdInput = document.getElementById('carIdInput').value,
            form = new FormData();
        form.append('model', model);
        form.append('body', body);
        form.append('engineType', eType);
        form.append('engineValue', engineValue);
        form.append('body', body);
        form.append('id', carIdInput);
        form.append('carImage', carPhoto);
        axios.post(url, form)
            .then(function(response) {
                onAddCarSuccess(response);
            })
            .catch(function(error) {
                onAddCarError(error)
            });
        document.getElementById('addCarDialog').close();
    };

    let onAddCarSuccess = function (response) {
        if (response.data.success) {
            // Изменить запись о текущем автомобиле в таблице автомобилей или создать новую запись в таблице
            let table = document.getElementById("garageId");
            if (addCarDialogFunctions.isCreate) {
                createFun(response, table);
            } else {
                updateFun(response, table);
            }
        } else {
            document.getElementById('error').textContent = response.data.msg;
        }
    };

    let createFun = function(response, table) {
        let row = table.insertRow(table.rows.length),
            cellModel = row.insertCell(0),
            cellBody = row.insertCell(1),
            cellType = row.insertCell(2),
            cellValue = row.insertCell(3),
            cellImg = row.insertCell(4),
            cellId = row.insertCell(5),
            cellEdit = row.insertCell(6);
        cellModel.innerHTML = response.data.body.model;
        cellBody.innerHTML = response.data.body.body;
        cellType.innerHTML = response.data.body.type;
        cellValue.innerHTML = response.data.body.engineValue;
        cellId.innerHTML = response.data.body.id;
        cellId.hidden = true;
        let img = new Image(100, 100);
        img.src = 'data:image/' + response.data.body.image.format + ';base64,' + response.data.body.image.photo;
        cellImg.appendChild(img);
        let imgEdit = document.createElement('i');
        imgEdit.className = "fa fa-pencil fa-2x";
        imgEdit.style = "align-self: center";
        imgEdit.onclick = function() {
            this.onTableClick(response.data.body.model, response.data.body.id, response.data.body.body, response.data.body.engineValue, response.data.body.type);
        };
        cellEdit.appendChild(imgEdit);
    };

    let updateFun = function(response, table) {
        let car = response.data.body,
            row;
        Array.from(table.rows).forEach(function(r, index) {
            let cell = r.cells[6];
            if (cell && cell.innerHTML === ('' + car.id)) {
                row = r;
                return;
            }
        });
        if (row) {
            let cells = row.cells;
            Array.from(cells).forEach((cell, index) => {
                switch (index) {
                    case 0:
                        cell.innerHTML = car.model;
                        break;
                    case 1:
                        cell.innerHTML = car.body;
                        break;
                    case 2:
                        cell.innerHTML = car.type;
                        break;
                    case 3:
                        cell.innerHTML = car.engineValue;
                        break;
                    case 6:
                        cell.innerHTML = car.id;
                        break;
                }
            });
            let img = document.getElementById(car.id + '_id');
            img.src = 'data:image/' + car.image.format + ';base64,' + car.image.photo;
        }
    };

    let onAddCarError = function (error) {
        console.log(error);
    };

    return {
        onTableClick: onTableClick,
        cancelEvent: cancelClickEvent,
        submit: submit,
        setCreate: setCreate
    }
})();