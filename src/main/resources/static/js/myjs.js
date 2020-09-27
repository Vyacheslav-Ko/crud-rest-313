const URL_API = 'http://localhost:8080/admin/api/users/';
function refreshTable() {
    fetch(URL_API).then(
        response => {
            response.json().then(
                data => {
                    let table ="";
                    data.forEach((user) => {
                        table += "<tr >";
                        table += "<td >"+ user.id + "</td>";
                        table += "<td >"+ user.firstname + "</td>";
                        table += "<td >"+ user.lastname + "</td>";
                        table += "<td >"+ user.age + "</td>";
                        table += "<td >"+ user.username + "</td>";
                        if (user.flagRole==2) {table += "<td > ADMIN USER </td>";}
                        if (user.flagRole==1) {table += "<td > USER </td>";}
                        table += "<td >" +
                            "    <a class='btn btn-primary' role='button' onclick='fillEditModal(" + user.id + ")' data-toggle='modal' data-target='#editUser'>Edit</a>" +
                            "    <a class='btn btn-danger' role='button' onclick='fillDeleteModal(" + user.id + ")' data-toggle='modal' data-target='#deleteUser'>Delete</a>" +
                            "    </td>"
                        table += "</tr>";
                    })
                    $("#mainUsersTable").empty().append(table);
                })
        })
}
function fillEditModal(userId) {
    console.log("try to get user - " + userId);
    $.get(URL_API + userId, function (userJSON) {
        $('#idToEditUser').val(userJSON.id);
        $('#firstnameToEditUser').val(userJSON.firstname);
        $('#lastnameToEditUser').val(userJSON.lastname);
        $('#ageToEditUser').val(userJSON.age);
        $('#emailToEditUser').val(userJSON.username);
        $('#passwordToEditUser').val(userJSON.password);
        if(userJSON.flagRole==2){
            $("#ROLE_USER").prop('checked', true);
            $("#ROLE_ADMIN").prop('checked', true);
        } else if (userJSON.flagRole==1){
            $("#ROLE_USER").prop('checked', true);
            $("#ROLE_ADMIN").prop('checked', false);
        } else {
            $("#ROLE_USER").prop('checked', false);
            $("#ROLE_ADMIN").prop('checked', false);
        }
    });
}
function fillDeleteModal(userId) {
    console.log("try to delete user - " + userId);
    $.get(URL_API + userId, function (userJSON) {
        console.log(userJSON)
        $('#idToDeleteUser').val(userId);
        $('#firstnameToDeleteUser').val(userJSON.firstname);
        $('#lastnameToDeleteUser').val(userJSON.lastname);
        $('#ageToDeleteUser').val(userJSON.age);
        $('#emailToDeleteUser').val(userJSON.username);
        $('#passwordToDeleteUser').val(userJSON.password);
        if(userJSON.flagRole==2){
            $("#Delete_ROLE_USER").prop('checked', true);
            $("#Delete_ROLE_ADMIN").prop('checked', true);
        } else if (userJSON.flagRole==1){
            $("#Delete_ROLE_USER").prop('checked', true);
            $("#Delete_ROLE_ADMIN").prop('checked', false);
        } else {
            $("#Delete_ROLE_USER").prop('checked', false);
            $("#Delete_ROLE_ADMIN").prop('checked', false);
        }
    });
    refreshTable();
}

function reloadNewUserTable(){
    $('#newName').val('');
    $('#newAge').val('');
    $('#newEmail').val('');
    $('#newPassword').val('');
    $("#New_ROLE_USER").prop('checked', false);
    $("#New_ROLE_ADMIN").prop('checked', false);
}

$(function () {
    $("#logout").append("<a class='custom-a' href='/logout'>Logout</a>");
    $('#addSubmit').on("click", function () {
        let checked = [];
        $('input:checkbox:checked').each(function () {
            checked.push($(this).val());
        });
        let user = {
            name : $("#newName").val(),
            age : $("#newAge").val(),
            email : $("#newEmail").val(),
            password : $("#newPassword").val(),
            role : checked
        };
        fetch(URL_API, {
            method: "POST",
            credentials: 'same-origin',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
        reloadNewUserTable();
        refreshTable();
    });
    $('#modalDeleteBtn').on("click", function () {
        fetch(URL_API + $('#idToDeleteUser').val(), {
            method: "DELETE",
            credentials: 'same-origin'
        }).then(r => refreshTable());
        /*refreshTable();
        reloadNewUserTable();*/
    });
    $('#modalEditBtn').on("click", function () {
        let checked = [];
        $('input:checkbox:checked').each(function () {
            checked.push($(this).val());
        });
        let user = {
            id : $('#idToEditUser').val(),
            firstname : $("#firstnameToEditUser").val(),
            lastname : $("#lastnameToEditUser").val(),
            age : $("#ageToEditUser").val(),
            username : $("#emailToEditUser").val(),
            password : $("#passwordToEditUser").val(),
            flagRole : 1
        };
        fetch(URL_API, {
            method: "PUT",
            credentials: 'same-origin',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(r => refreshTable());
    });
});
refreshTable();