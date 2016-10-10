<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="../assets/css/barralateral.css">
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />

<t:template pageTitle="BID Group: Generador de calculo">

    <jsp:attribute name="scripts">
        <script type="text/javascript">

            var vm = new Vue({
                el: '#content',
                ready : function () {
                    this.obtainDateType();
                    this.activateDateTimePickerStartDateMonth();
                    this.activateDateTimePickerStartDate();
                },
                data: {
                    search : {
                        startDate: '',
                        endDate: ''
                    },
                    dateTimePickerStartDate : null,
                    dateTimePickerEndDate : null,
                    fromDate: '',
                    ofDate: '',
                    commission: [],
                    btn : false,
                    dateTypes: [],
                    typeCalculation: {}
                },
                methods: {
                    activateDateTimePickerStartDate: function () {

                        var currentDate = new Date();

                        this.dateTimePickerStartDate = $('#startDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerStartDateMonth: function () {
                        var currentDate = new Date();

                        this.dateTimePickerStartDate = $('#startDateMonth').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false
                        }).data();
                    },
                    activateDateTimePickerEndDate: function (fechaFinal) {

                        var currentDate = new Date();

                        var fecha = moment(fechaFinal, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        var maxFecha = moment(fecha).add(4, 'days');

                        this.dateTimePickerEndDate = $('#endDate').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha,
                            maxDate: maxFecha
                        }).data();
                    },
                    activateDateTimePickerEndDateMonth: function (fechaFinal) {
                        var fecha = '';
                        var maxFecha = '';
                        fecha = moment(fechaFinal, 'DD-MM-YYYY').format('YYYY-MM-DD');
                        maxFecha = moment(fecha).add(1, 'months').subtract(1, 'days');

                        this.dateTimePickerEndDate = $('#endDateMonth').datetimepicker({
                            locale: 'es',
                            format: 'DD-MM-YYYY',
                            useCurrent: false,
                            minDate: fecha,
                            maxDate: maxFecha
                        }).data();
                    },
                    destruirTimePickerEndDateMonth: function(){
                        this.search.endDate= "";
                        $("#startDateMonth").on("dp.change", function (e) {
                            var fecha = moment(e.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                            var maxFecha = moment(fecha).add(1, 'months').subtract(1, 'days');
                            $('#endDateMonth').data("DateTimePicker").maxDate(maxFecha);
                        });
                    },
                    destruirTimePickerEndDate: function () {
                        this.search.endDate= "";
                        $("#startDate").on("dp.change", function (e) {
                            var fecha = moment(e.date, 'DD-MM-YYYY').format('YYYY-MM-DD');
                            var maxFecha = moment(fecha).add(4, 'days');
                            $('#endDate').data("DateTimePicker").maxDate(maxFecha);
                        });
                    },
                    saveCalculation : function () {
                        this.$http.get(ROOT_URL + "/backup-commission/save").success(function (data) {
                            this.commission = data;
                            showAlert("Calculo guardado con exito");
                            this.btn = true;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type:3});
                            this.btn = false;
                        });
                    },
                    generateCalculation : function () {
                        this.fromDate = this.dateTimePickerStartDate.DateTimePicker.date().toISOString().slice(0, -1);
                        this.ofDate = this.dateTimePickerEndDate.DateTimePicker.date().toISOString().slice(0, -1);
                        window.location = ROOT_URL + "/sap-sale/prueba/"+this.typeCalculation.idDateCalculation+"?fromDate="+this.fromDate+"&toDate="+this.ofDate;
                        this.btn = true;
//                        showAlert('No es posible generar debido a que no existen empleados asignados', {type:2});
                    },
                    obtainDateType: function () {
                        this.$http.get(ROOT_URL + "/date-calculation").success(function (data) {
                            this.dateTypes = data;
                        }).error(function () {
                            showAlert("Error al generar la solicitud", {type: 3});
                        });
                    },
                    clear: function () {
                        this.search.endDate = "";
                        this.search.startDate = "";
                    }
                }
            });
        </script>
    </jsp:attribute>

    <jsp:body>
        <div id="content">
            <div class="container-fluid">
                <h2>Generador de cálculo de comisiones</h2>
                <br>
                <div class="row">
                    <div class="col-md-3">
                        <label>
                            Tipo de calculo
                        </label>
                        <select class="form-control" v-model="typeCalculation" @change="clear()">
                            <option v-for="type in dateTypes" :value="type">
                                {{type.nameDate}}
                            </option>
                        </select>
                    </div>
                </div>
                <br>
                <div class="row" v-show="typeCalculation.idDateCalculation == 1">
                    <form v-on:submit.prevent="searchCalculation">
                        <div class="col-md-3">
                            <label>Fecha de inicio</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDate">
                                    <input type="text" class="form-control" v-model="search.startDate" required>
                                    <span class="input-group-addon" @click="destruirTimePickerEndDate()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label>Fecha final</label>
                            <div class="form-group">
                                <div class="input-group date" id="endDate">
                                    <input type="text" class="form-control" v-model="search.endDate" required>
                                    <span class="input-group-addon" @click="activateDateTimePickerEndDate(search.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-success form-control"
                                    @click="generateCalculation" style="margin-top: 27px">
                                Generar cálculo
                            </button>
                        </div>
                        <div class="col-md-2" v-if="btn">
                            <button type="button" @click="saveCalculation" class="btn btn-info form-control" style="margin-top: 27px">
                                Guardar calculo
                            </button>
                        </div>
                    </form>
                </div>
                <div class="row" v-show="typeCalculation.idDateCalculation == 2">
                    <form v-on:submit.prevent="searchCalculation">
                        <div class="col-md-3">
                            <label>Fecha de inicio</label>
                            <div class="form-group">
                                <div class="input-group date" id="startDateMonth">
                                    <input type="text" class="form-control" v-model="search.startDate" required>
                                    <span class="input-group-addon" @click="destruirTimePickerEndDateMonth()">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label>Fecha final</label>
                            <div class="form-group">
                                <div class="input-group date" id="endDateMonth">
                                    <input type="text" class="form-control" v-model="search.endDate" required>
                                    <span class="input-group-addon" @click="activateDateTimePickerEndDateMonth(search.startDate)">
                                       <span class="glyphicon glyphicon-calendar"></span>
                                   </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <button type="button" class="btn btn-success form-control"
                                    @click="generateCalculation" style="margin-top: 27px">
                                Generar cálculo
                            </button>
                        </div>
                        <div class="col-md-2" v-if="btn">
                            <button type="button" @click="saveCalculation" class="btn btn-info form-control" style="margin-top: 27px">
                                Guardar calculo
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template>
