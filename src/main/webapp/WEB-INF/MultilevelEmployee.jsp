<%-- 
    Document   : MultilevelEmployee
    Created on : 20/10/2016, 09:23:52 AM
    Author     : Cristhian de la cruz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users"/>

<t:template pageTitle="BID Group: Multinivel Empleado">
    <jsp:attribute name="scripts">
        <script type="text/javascript">
            var vm = new Vue({
                el: '#content',
                ready: function () {
                    this.getBranchs();
                    this.getAreas();
                },
                data: {
                    updateEmployeeUrl: ROOT_URL + '/saem/MultilevelEmployee?idDwEmployee=',
                    status: null,
                    dwEmployees: [],
                    employeesHistories: [],
                    searching: false,
                    arrayAdvisersObj: {
                        arrayAdvisers: [],
                        supervisor: {}
                    },
                    searchSelectedOptions: {
                        area: {
                            idArea: 0,
                            name: 'TODOS'
                        },

                        branch: {
                            idBranch: 0,
                            name: 'TODOS'
                        },
                        role: {
                            idRole: 0,
                            name: 'TODOS'
                        }
                    },
                    selectedArea: {},
                    allStatus: {
                        name: 'TODOS',
                        value: 0
                    },

                    defaultArea: {
                        idArea: 0,
                        name: 'TODOS'
                    },


                    defaultBranch: {
                        idBranch: 0,
                        name: 'TODOS'
                    },
                    defaultRole: {
                        idRole: 81,
                        roleName: 'SUPERVISOR'
                    },


                    employeesUrl: null,
                    isThereItems: false,
                    currentDwEmployee: {},
                    nameSearch: '',
                    rfcSearch: '',

                    role: {},
                    selectedOptions: {
                        area: {
                            idArea: 0
                        },
                        branch: {
                            idBranch: 0
                        },
                        role: {
                            idRole: 0
                        }
                    },
                    branchs: [],
                    selectOptions: {
                        distributors: [],
                        areas: [],
                        roles: [],
                        dwEnterprises: []
                    },
                    employeeH: {},

                    defaultAreas: {
                        idArea: 0,
                        name: ''
                    },
                    defaultBranchs: {
                        idBranch: 0,
                        name: ''
                    },
                    defaultRoles: {
                        idRole: 0,
                        name: ''
                    },
                    checkLastAssign: false,
                    rolesRespaldo: {},
                    branchRespaldo: [],
                    areaRespaldo: {},
                    rolesRespaldos: [],
                    branchRespaldos: [],
                    findByRfcStatus: true,
                    areas: [],
                    branchch: [],
                    registerNumber: 0,
                    dwEnterprises: [],
                    supervisor: {},
                    advisersBranch: [],
                    dwEnterpriseByBranch: [],
                    dwEnterprisesSupervisor: {},
                    advisersBySupervisor: []
                },
                methods: {
                    arrayObjectIndexOf: function (myArray, searchTerm, property) {
                        for (var i = 0, len = myArray.length; i < len; i++) {
                            if (myArray[i][property] === searchTerm) return i;
                        }
                        return -1;
                    },

                    setEmployeesUrl: function (status) {

                        if (status === 1) {
                            this.employeesUrl = '/employees-history?status=1';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=1';
                        } else if (status === 2) {
                            this.employeesUrl = '/employees-history?status=2';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=2';
                        } else if (status === 0) {
                            this.employeesUrl = '/employees-history?status=0 ';
                            this.createReportUrl = ROOT_URL + '/employees-history/create-report?status=0';
                        }

                        if (this.searchSelectedOptions.branch.idBranch != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.idBranch;
                            this.createReportUrl += 'idBranch=' +
                                    this.searchSelectedOptions.branch.idBranch;
                        }

                        if (this.searchSelectedOptions.area.idArea != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.idArea;
                            this.createReportUrl += 'idArea=' +
                                    this.searchSelectedOptions.area.idArea;
                        }

                        if (this.searchSelectedOptions.role.idRole != 0) {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                            this.createReportUrl += 'idRole=' +
                                    this.searchSelectedOptions.role.idRole;
                        }

                        if (this.nameSearch != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'fullname=' +
                                    this.nameSearch;
                            this.createReportUrl += 'fullname=' +
                                    this.nameSearch;
                        }

                        if (this.rfcSearch != '') {
                            this.employeesUrl = this.setEmployeesUrlCharacters(this.employeesUrl);
                            this.createReportUrl = this.setEmployeesUrlCharacters(this.createReportUrl);
                            this.employeesUrl += 'rfc=' +
                                    this.rfcSearch;
                            this.createReportUrl += 'rfc=' +
                                    this.rfcSearch;
                        }


                        return true;
                    },
                    setEmployeesUrlCharacters: function (url) {
                        if (url.indexOf('?') > -1) {
                            url += '&';
                            return url;
                        } else {
                            url += '?';
                            return url;
                        }
                    },
                    getDwEmployees: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            if (this.dwEmployees.length > 0) {
                                this.registerNumber = this.dwEmployees.length;
                                this.isThereItems = true;
                            } else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    getEmployees: function () {
                        this.dwEmployees = [];
                        this.employeesHistories = [];
                        this.searching = true;

                        if (this.setEmployeesUrl(1)) {
                            this.getEmployeesHistory();
                        }
                    },
                    getEmployeesHistory: function () {
                        var self = this;
                        this.$http.get(
                                ROOT_URL + this.employeesUrl
                        ).success(function (data) {
                            this.dwEmployees = data;
                            if (this.dwEmployees.length > 0) {
                                this.isThereItems = true;
                                this.registerNumber = this.dwEmployees.length;
                            } else {
                                showAlert("No hay datos para esa busqueda, intente con otra combinaciòn", {type: 3});
                                setInterval(function () {
                                    location.reload();
                                }, 3000);
                            }
                        }).error(function (data) {
                            showAlert("No se pudo obtener informacion intente de nuevo", {type: 3});
                        });
                    },
                    getBranchs: function () {
                        this.$http.get(ROOT_URL + "/branchs").success(function (data) {
                            this.branchch = data;
                        });
                    },
                    getAreas: function () {
                        this.$http.get(ROOT_URL + "/areas").success(function (data) {
                            this.areas = data;
                        });
                    },
                    onExportButton: function (dwEmployee) {
                        console.log(dwEmployee);
                        this.supervisor = dwEmployee;
                        $("#promoterstModal").modal("show");
                        this.$http.get(ROOT_URL + "/dw-enterprises?idBranch=" + this.supervisor.dwEnterprisesR.branch.idBranch).success(function (data) {
                            this.dwEnterpriseByBranch = data;
                            this.getDwEnterpirseAndRoleAdvisers(this.dwEnterpriseByBranch[0]);
                            this.selectedAdvisersBySupervisor(this.supervisor.idEmployee);
                        });


                    },
                    getDwEnterpirseAndRoleAdvisers: function (dwEnterprises) {
                        this.dwEnterprisesSupervisor = dwEnterprises;
                        this.advisersBranch = [];
                        this.$http.get(ROOT_URL + "/dw-employees/advisers-by-branch/" + dwEnterprises.idDwEnterprise).success(function (data) {
                            this.advisersBranch = data;

                        });


                    },

                    saveSelectedAdvisers: function () {
                        this.arrayAdvisersObj.supervisor = this.supervisor;

                        if(this.arrayAdvisersObj.arrayAdvisers.length == 0){
                            showAlert("Es necesario seleccionar un asesor", {type: 3});
                        }else {
                            var sum = this.advisersBySupervisor.length+this.arrayAdvisersObj.arrayAdvisers.length;
                            if (this.arrayAdvisersObj.arrayAdvisers.length >= 3 || this.advisersBySupervisor.length >= 3 || sum >= 3) {
                                this.$http.post(ROOT_URL + "/multilevel-employee/new-multilevel", JSON.stringify(this.arrayAdvisersObj)).success(function (data) {
                                    this.getDwEnterpirseAndRoleAdvisers(this.dwEnterprisesSupervisor);
                                    this.selectedAdvisersBySupervisor(this.supervisor.idEmployee);
                                    this.arrayAdvisersObj.arrayAdvisers = [];
                                    showAlert("Asesor(es) asignados correctamente");
                                }).error(function () {
                                    showAlert("error", {type: 3});
                                });

                            } else {
                                showAlert("Seleccionar por lo menos 3 asesores");
                            }
                        }
                        // this.$http.post(ROOT_URL + "/c-agreements-groups/new", JSON.stringify(this.group)).success(function (data) {
                        //   showAlert("");
                        //   }).error(function () {
                        //     showAlert("Error en la solicitud", {type: 3});

                    },
                    selectedAdvisersBySupervisor: function (idEmployee) {
                        this.advisersBySupervisor = [];
                        this.$http.get(ROOT_URL + "/multilevel-employee/find-by-supervisor/" + idEmployee).success(function (data) {
                            this.advisersBySupervisor = data;
                        }).error(function () {
                            showAlert("Hubo un error al obtener información", {type: 3});
                        });
                    },
                    deleteMultilevel: function (multilevel) {
                        this.$http.post(ROOT_URL + "/multilevel-employee/delete/" + multilevel.idMultilevel).success(function (data) {
                            showAlert("El asesor se elimino correctamente");
                            this.getDwEnterpirseAndRoleAdvisers(this.dwEnterprisesSupervisor);
                            this.selectedAdvisersBySupervisor(this.supervisor.idEmployee);
                        }).error(function () {
                            showAlert("Hubo un error al generae la transacción", {type: 3});
                        });
                    }


                }


            });
        </script>
    </jsp:attribute>

    <jsp:attribute name="styles">
        <style>
            .table-header {
                padding: 1rem;
                margin-top: 2rem;
            }

            .table-body .table-row:nth-child(2n+1) {
                background: white;
            }

            .table-row {
                padding: 1rem;
            }

            .flex-content {
                overflow-x: hidden;
            }
        </style>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-8">
                        <h2>Busqueda de Supervisores</h2>
                    </div>
                    <div class="col-md-2">
                    </div>
                    <div class="col-md-2" v-if="dwEmployees.length > 0" style="margin-top: 35px">
                        <label><p style="color: darkblue">Nùmero de registros: {{registerNumber}}</p></label>
                    </div>
                </div>
                    <%-- <div v-if="!hierarchy.length > 0"
                         style="height: 6rem; padding: 2rem 0;">
                        <div class="loader">Cargando...</div>
                    </div> --%>
                <div>

                    <br>
                    <div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-12">

                                    <div class="col-md-2">
                                        <label>Nombre</label>
                                        <input v-model="nameSearch" type="text" class="form-control"
                                               placeholder="Nombre del Empleado">
                                    </div>
                                    <div class="col-md-2">
                                        <label>RFC</label>
                                        <input v-model="rfcSearch" type="text" class="form-control" placeholder="RFC">
                                    </div>

                                    <div class="col-md-2">
                                        <label>Sucursal</label>
                                        <select v-model="searchSelectedOptions.branch" class="form-control"
                                                @change="branchChanged"
                                                :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                            <option selected :value="defaultBranch">{{defaultBranch.name}}</option>
                                            <option v-for="branch in branchch"
                                                    :value="branch" v-if="branch.saemFlag == 1">
                                                {{ branch.branchShort }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Área</label>
                                        <select v-model="searchSelectedOptions.area"
                                                class="form-control" @change="areaChanged"
                                                :disabled="searchSelectedOptions.distributor.idDistributor < 0">
                                            <option selected :value="defaultArea">{{defaultArea.name}}</option>
                                            <option v-for="area in areas"
                                                    :value="area" v-if="area.saemFlag == 1">
                                                {{ area.areaName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <label>Puesto</label>
                                        <select v-model="searchSelectedOptions.role" class="form-control"
                                                :disabled="searchSelectedOptions.area.idArea == 0">
                                            <option selected :value="defaultRole">{{defaultRole.roleName}}</option>
                                            <option v-for="role in selectedArea"
                                                    :value="role.role">
                                                {{ role.role.roleName }}
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-md-2">
                                        <button style="margin-top: 25px" class="btn btn-info" @click="getEmployees">
                                            Buscar
                                        </button>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <br>
                <div v-if="!isThereItems && searching == true"
                     style="height: 6rem; padding: 2rem 0;">
                    <div class="loader">Cargando...</div>
                </div>
                <div style="background: #ddd" class="panel panel-default" v-if="isThereItems">
                    <!-- Default panel contents -->
                    <!-- Table -->
                    <div class="flex-box container-fluid" v-if="dwEmployees.length > 0">
                        <div class="row table-header active">
                            <div class="col-md-2"><b>Nombre de empleado</b></div>
                            <div class="col-md-2"><b>RFC</b></div>
                            <div class="col-md-2"><b>Distribuidor</b></div>
                            <div class="col-md-2"><b>Sucursal</b></div>
                            <div class="col-md-1"><b>Puesto</b></div>
                            <right></right>
                            <div class="col-md-3"><b>Agregar Promotores</b></div>
                        </div>
                        <br>
                        <div class="table-body flex-row flex-content">
                            <div class="row table-row" v-for="dwEmployee in dwEmployees">
                                <div class="col-md-2">{{dwEmployee.fullName}}</div>
                                <div class="col-md-2">{{dwEmployee.rfc}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.distributor.distributorName}}</div>
                                <div class="col-md-2">{{dwEmployee.dwEnterprisesR.branch.branchShort}}</div>
                                <div class="col-md-1">{{dwEmployee.rolesR.roleName}}</div>
                                <div class="col-md-2 text-center">
                                    <button class="btn btn-success btn-sm" @click="onExportButton(dwEmployee)"
                                            data-toggle="tooltip" data-placement="top"
                                    >
                                        <span class="glyphicon glyphicon-refresh"></span>
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Exportar -->
            <div class="modal fade" id="promoterstModal" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header a.bg-success">
                            <h4 class="modal-title">Supervisor <b class="text-primary">{{supervisor.fullName}}</b></h4>
                            <h4 class="modal-title">Sucursal <b class="text-primary">{{supervisor.dwEnterprisesR.branch.branchShort}}</b>
                            </h4>
                        </div>

                        <div class="modal-body">
                            <div class="panel-body">
                                <h4><label>Asesores asignados</label></h4>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>RFC</th>
                                            <th>Eliminar</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr v-for="advisers in advisersBySupervisor">
                                            <td>{{advisers.employees.fullName}}</td>
                                            <td>{{advisers.employees.rfc}}</td>
                                            <td><button class="btn btn-danger btn-sm" @click="deleteMultilevel(advisers)"
                                                        data-toggle="tooltip" data-placement="top"
                                            >
                                                <span class="glyphicon glyphicon-trash"></span>
                                            </button></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h4><label>Asesores por asignar</label></h4>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>Nombre</th>
                                            <th>RFC</th>
                                            <th>Puesto</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr v-for="aB in advisersBranch">
                                            <td><input type="checkbox" :value="aB"
                                                       v-model="arrayAdvisersObj.arrayAdvisers"></td>
                                            <td>{{aB.employee.fullName}}</td>
                                            <td>{{aB.employee.rfc}}</td>
                                            <td>{{aB.role.roleName}}</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Salir</button>
                            <button type="button" class="btn btn-success" @click="saveSelectedAdvisers()">Guardar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>

    
