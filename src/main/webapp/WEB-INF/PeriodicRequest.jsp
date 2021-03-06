<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="session" class="mx.bidg.model.Users" />


<t:template pageTitle="BID Group: Presupuestos">
    <jsp:attribute name="scripts">

        <script type="text/javascript">
        function validateFloatKeyPress(el, evt) {
          var charCode = (evt.which) ? evt.which : event.keyCode;
          var number = el.value.split('.');
          if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
          }
          //just one dot
          if(number.length>1 && charCode == 46)
          {
            return false;
          }
          //get the carat position
          var caratPos = getSelectionStart(el);
          var dotPos = el.value.indexOf(".");
          if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
            return false;
          }
          return true;
        }

          //thanks: http://javascript.nwbox.com/cursor_position/
          function getSelectionStart(o) {
             if (o.createTextRange) {
                 var r = document.selection.createRange().duplicate()
                   r.moveEnd('character', o.value.length)
                     if (r.text == '') return o.value.length
                       return o.value.lastIndexOf(r.text)
                      } else return o.selectionStart
                    }
        </script>

        <script type="text/javascript">
          var vm= new Vue({
          el: '#contenidos',
          created: function(){

          },
          ready: function ()
          {
            this.timePicker = $('#datetimepicker1').datetimepicker({
              locale: 'es',
              format: 'DD-MM-YYYY',
              useCurrent: false,
              minDate: moment().add(1, 'minutes')
              }).data();

            this.timePickerPagoInicial = $('#datePagoInicial').datetimepicker({
              locale: 'es',
              format: 'DD-MM-YYYY',
              useCurrent: false,
              minDate: moment().add(1, 'minutes')
              }).data();

            this.obtainUserInSession();
            this.obtainAllUsers();
            this.obtainCurrencies();
            this.obtainAllPeriods();
            this.obtainInformationAutorization();
            this.obtainRequestInformation.idRequestCategory= this.RequestCategory;
            this.$http.get(ROOT_URL+"/request-types/request-category/"+ this.obtainRequestInformation.idRequestCategory)
                    .success(function (data)
                    {
                       this.RequestTypes= data;
                    });
            this.verifyUpdateOrSave();
          },
          data:
          {
            months:[
              {idMonth: 1, name: 'Enero'},
              {idMonth: 2, name: 'Febrero'},
              {idMonth: 3, name: 'Marzo'},
              {idMonth: 4, name: 'Abril'},
              {idMonth: 5, name: 'Mayo'},
              {idMonth: 6, name: 'Junio'},
              {idMonth: 7, name: 'Julio'},
              {idMonth: 8, name: 'Agosto'},
              {idMonth: 9, name: 'Septiembre'},
              {idMonth: 10, name: 'Octubre'},
              {idMonth: 11, name: 'Noviembre'},
              {idMonth: 12, name: 'Diciembre'}
            ],
            objectRequest:
            {
            request:
              {
                idRequest: '',
                folio: '',
                description: '',
                purpose: '',
                creationDate: '',
                applyingDate: '',
                idUserRequest: '',
                idUserResponsable: '',
                idBudgetMonthBranch: '',
                idRequestTypesProduct: '',
                idRequestStatus: '',
                isSaved: false
              },
             products: []
           },
            responseSaveRequest: ''
           ,
            obtainRequestInformation:
            {
              idRequestCategory: '',
              idRequestType: '',
              idProductType: '',
              idUserResponsable: '',
              applyingDate: ''
            },
            RequestCategory: ${cat},
            idRequest: ${idRequest},
            ResponseRequestInformation: '',
            idRequestType: '',
            idProductType: '',
            optionSelect: [],
            RequestTypes: {},
            ProductTypes: {},
            Productos: {},
            Users: {},
            idProducto: '',
            desactivarCombos: false,
            isUpdate: false,
            desactivarGuardar: true,
            numberOfRequest: 0,
            estimations: [],
            suppliers: {},
            idSupplier: '',
            accounts: {},
            idAccount: '',
            timePicker: '',
            currencies: {},
            periodicPayment:
            {
            idPeriodicPayment: '',
            folio: '',
            amount: '',
            initialDate: '',
            nextPayment: '',
            dueDate: '',
            paymentNum: '',
            idPeriod: '',
            idPeriodicPaymentStatus: '',
            idCurrency: '',
            rate: ''
          },
          timePickerPagoInicial: '',
          timePickerFechaVencimiento: '',
          Periods: '',
          userInSession: '',
          isSavingNow: false,
          isAutoriced: true,
          infoAutorization: '',
          userRequest: '',
          flagrate: false,
          desaparecer: true,
          attachment: ROOT_URL +"/estimations/attachment/download/",
          authModal: {
            authorize: false,
            authorization: null
          }
          },
          methods:
          {
            setIsCollapsed: function (cotizacion) {
              if (cotizacion.isCollapsed == true)
              {
                cotizacion.isCollapsed = false;
              } else {
                Vue.set(cotizacion, "isCollapsed", true);
              }

            },
            obtainProductType: function()
            {
              this.obtainRequestInformation.idUserResponsable='';
              this.ProductTypes= {};
              this.Productos= {};
              this.$http.get(ROOT_URL+"/product-types/request-category-type/"+this.obtainRequestInformation.idRequestCategory+"/"+this.obtainRequestInformation.idRequestType)
                      .success(function (data)
                      {
                         this.ProductTypes= data;
                      });
              this.objectRequest.request.description='';
              this.objectRequest.request.purpose= '';
              this.idProducto= '';
              this.obtainRequestInformation.idProductType='';

            },
            obtainProducts: function()
            {
              this.Productos= {};
              this.$http.get(ROOT_URL+"/products/product-type/"+this.obtainRequestInformation.idProductType)
                      .success(function (data)
                      {
                         this.Productos= data;
                      });
              this.obtainSuppliers(this.obtainRequestInformation.idProductType);
            },
            obtainRequestInfo: function()
            {
              var self= this;

              this.$http.post(ROOT_URL+"/requests/month-branch-product-type", JSON.stringify(this.obtainRequestInformation))
                      .success(function (data)
                      {
                         this.ResponseRequestInformation= data;
                         this.matchInformation(this.ResponseRequestInformation);

                      }).error(function(data)
                      {

                        showAlert("No existe presupuesto para este tipo de solicitud", {type: 3});
                        this.obtainRequestInformation.idRequestType= '';
                        this.obtainRequestInformation.idProductType= '';
                        this.obtainRequestInformation.idUserResponsable= '';
                        this.obtainRequestInformation.applyingDate= '';
                        this.objectRequest.products= [];
                        this.idProducto= '';
                        this.desactivarCombos= false;
                        this.ProductTypes= {};
                        this.Productos= {};
                      });

            }
            ,
            matchInformation: function(requestInformation)
            {
              console.log(requestInformation.requestTypesProduct.idRequestTypeProduct);
              this.objectRequest.request.idUserResponsable= this.obtainRequestInformation.idUserResponsable;
              this.objectRequest.request.idBudgetMonthBranch = requestInformation.budgetMonthBranch.idBudgetMonthBranch;
              this.objectRequest.request.idRequestTypesProduct =requestInformation.requestTypesProduct.idRequestTypeProduct
            },
            saveProduct: function()
            {
              var producto= this.createProduct();
              var self= this;
              this.Productos.forEach(function(element)
              {
                if (self.idProducto == element.idProduct)
                {
                  producto.idProduct = element.idProduct;
                  producto.descripcion = element.product;
                  self.objectRequest.products.push(producto);
                }
              });
              if (this.objectRequest.products.length> 0)
              {
                this.desactivarCombos= true;
                this.desactivarGuardar= false;
              }
            },
            createProduct: function()
            {
              var producto= {
                idProduct: '',
                descripcion: ''
              };
              return producto;
            },
            deleteProduct: function(product)
            {
              this.objectRequest.products.$remove(product);
              if (this.objectRequest.products.length == 0)
              {
                this.desactivarCombos= false;
                this.desactivarGuardar = true;
                this.objectRequest.request.description='';
                this.objectRequest.request.purpose= '';
                this.idProducto= '';
              }
            },
            saveRequest: function(event)
            {
              this.isSavingNow= true;
              this.$http.post(ROOT_URL+"/requests", JSON.stringify(this.objectRequest)).
              success(function(data)
              {
                showAlert("Registro de solicitud exitoso, ahora puedes agregar las cotizaciones");
                history.pushState("", "BID Group: Solicitudes", "periodica/" + data.idRequest)
                this.idRequest = data.idRequest;
                this.isUpdate = true;
                this.fillRequestInformation(data);
              }).error(function(data)
              {

              });
            },
            fillRequestInformation: function(datos)
            {
              this.objectRequest.request.idRequest= datos.idRequest;
              this.objectRequest.request.folio= datos.folio;
              this.periodicPayment.folio= datos.folio;
              this.objectRequest.request.creationDate= datos.creationDateFormats.iso;
              this.objectRequest.request.idUserRequest= datos.userRequest.idUser;
              this.objectRequest.request.idRequestStatus= datos.requestStatus.idRequestStatus;
              Vue.set(this.objectRequest.request, "userRequest", datos.userRequest );
              Vue.set(this.objectRequest.request, "userResponsable", datos.userResponsible );
              this.objectRequest.request.isSaved = true;
              this.desactivarGuardar= true;
              this.isSavingNow= false;
              this.desaparecer= false;
            }
            ,
            obtainAllUsers: function()
            {
             this.$http.get(ROOT_URL + "/users").success(function (data)
              {
                 this.Users= data;
              });
            },
            createCotizacion: function()
            {
              var cotizacion=
              {
              idEstimation: '',
              amount: '',
              rate: '',
              fileName: '',
              fileNameActual: '',
              outOfBudget: '',
              idRequest: '',
              idEstimationStatus: '',
              idAccount: '',
              idCurrency: '',
              idUserAuthorization: '',
              idUserEstimation: '',
              creationDate: '',
              idSupplier: '',
              accountSupplier: {},
              indexOfForm: '',
              userAuthorization: {
                idUser: '',
                username: '',
                mail: ''
              },
              userEstimation: {
                idUser: '',
                username: '',
                mail: ''
              },
              isSaved: true,
              requiredFile: true,
              expanded: ''
              }
              return cotizacion;
            },
            newCotizacion: function()
            {
              var cotizacion= this.createCotizacion();
              cotizacion.idRequest= this.objectRequest.request.idRequest;
              cotizacion.indexOfForm= this.estimations.length;
              cotizacion.expanded= 'in';
              this.estimations.push(cotizacion);
            },
            deleteCotizacion: function(cotizacion)
            {
              if (cotizacion.idEstimationStatus== 2)
              {
                showAlert("No puedes eliminar una cotizacion autorizada");
              }
              else
              {
              if (cotizacion.idEstimation !== "")
              {
                this.isSavingNow= true;
                this.$http.delete(ROOT_URL + "/estimations/"+cotizacion.idEstimation).success(function (data)
                 {
                    showAlert("Cotizacion eliminada correctamente");
                    this.estimations.$remove(cotizacion);
                    this.isSavingNow= false;
                 });
              }
              else{
                  this.estimations.$remove(cotizacion);
              }
            }
            },
            createAccountPayable: function()
            {
              var accountPayable=
              {
                idAccountPayable: '',
                folio: '',
                amount: '',
                paidAmount: '',
                payNum: '',
                totalPayments: '',
                creationDate: '',
                dueDate: '',
                idAccountPayableStatus: '',
                idOperationType: '',
                idCurrency: '',
                rate: ''
              }
              return accountPayable;
            },
            obtainSuppliers: function(idProductType)
            {
              this.$http.get(ROOT_URL + "/providers/product-type/" + idProductType).success(function (data)
               {
                  this.suppliers= data;
               });
            },
            obtainAccounts: function(cotizacion)
            {
              this.$http.get(ROOT_URL + "/providers-accounts/provider/"+cotizacion.idSupplier).success(function (data)
               {
                    cotizacion.accountSupplier= data;
               });

            },
            obtainCurrencies: function()
            {
              this.$http.get(ROOT_URL + "/currencies").success(function (data)
               {
                    this.currencies= data;
               });

            },
            saveEstimations: function(cotizacion)
            {
              if (cotizacion.idEstimation > 0)
              {
                this.isSavingNow= true;
                //this.modifyCotizacion(cotizacion);
                if (cotizacion.fileName !== "")
                {
                  var form = document.getElementById("form-"+cotizacion.indexOfForm);
                  var formData = new FormData(form);

                  this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation, JSON.stringify(cotizacion)).
                  success(function(data)
                  {
                    var respuestaestimation = data;
                    this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation+"/attachment", formData).
                    success(function(data)
                    {
                      var respuestaarchivo = data
                      showAlert("Modificacion Realizada con Exito");
                      this.isSavingNow= false;
                      cotizacion.fileName= '';
                      this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
                      success(function(data)
                      {
                        this.estimations = [];
                        this.matchInformationEstimationsUpdate(data);
                      }).error(function(data){
                        showAlert("Ha habido un error al obtener la informacion de las cotizacion");
                      });

                    }).error(function(data){
                      showAlert("La modificacion se ha realizado, pero hubo un error al guardar el archivo");
                      this.isSavingNow= false;
                    });

                  }).error(function(data)
                  {
                    showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
                    this.isSavingNow= false;
                  });

                }
                else{
                  this.$http.post(ROOT_URL+"/estimations/"+cotizacion.idEstimation, JSON.stringify(cotizacion)).
                  success(function(data)
                  {
                    showAlert("Modificacion Exitosa");
                    this.isSavingNow= false;
                    this.estimations= [];
                    cotizacion.fileName= '';
                    this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
                    success(function(data)
                    {
                      this.matchInformationEstimationsUpdate(data);
                    }).error(function(data){
                      showAlert("Ha habido un error al obtener la informacion de las cotizacion", {type:3});
                    });

                  }).error(function(data)
                  {
                    showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
                    this.isSavingNow= false;
                  });
                }
              }
              else
              {
              this.isSavingNow= true;
              var form = document.getElementById("form-"+cotizacion.indexOfForm);
              var formData = new FormData(form);
              var responseOfEstimation;
              var responseOfFileUpload;
              this.$http.post(ROOT_URL+"/estimations", JSON.stringify(cotizacion)).
              success(function(data)
              {
                responseOfEstimation= data;
                this.$http.post(ROOT_URL+"/estimations/"+data.idEstimation+"/attachment", formData).
                success(function(data)
                {
                  showAlert("Registro de cotizacion Exitoso");
                  cotizacion.fileNameActual= data.fileName;
                  responseOfFileUpload= data;
                  this.isSavingNow = false;
                  cotizacion.fileName= '';
                  this.matchEstimationInfo(responseOfEstimation, responseOfFileUpload, cotizacion);
                  Vue.set(cotizacion, "isCollapsed", false);

                }).error(function(data){
                  showAlert("La cotizacion se ha guardado, pero hubo un error al guardar el archivo");
                  this.isSavingNow= false;
                });
                this.isSavingNow= false;
              }).error(function(data)
              {
                showAlert("Ha fallado el registro de su cotizacion, intente nuevamente");
              });
            }
            },
            matchEstimationInfo: function(responseOfEstimation, responseOfFileUpload, cotizacion)
            {
              cotizacion.idEstimation= responseOfEstimation.idEstimation;
              cotizacion.fileNameActual= responseOfFileUpload.fileName;
              cotizacion.outOfBudget= responseOfEstimation.outOfBudget;
              cotizacion.idEstimationStatus= responseOfEstimation.estimationStatus.idEstimationStatus;
              cotizacion.idUserEstimation= responseOfEstimation.userEstimation.idUser;
              cotizacion.creationDate= responseOfEstimation.creationDateFormats.iso;
              cotizacion.userEstimation.idUser= responseOfEstimation.userEstimation.idUser;
              cotizacion.userEstimation.username= responseOfEstimation.userEstimation.username;
              cotizacion.userEstimation.mail= responseOfEstimation.userEstimation.mail;
              cotizacion.isSaved= false;
              cotizacion.requiredFile= false;
              cotizacion.expanded= '';
              cotizacion.fileName= '';
            },
            verifyUpdateOrSave: function()
            {
              if (this.idRequest> 0)
              {
                this.$http.get(ROOT_URL+"/requests/"+this.idRequest).
                success(function(data)
                {
                  this.matchInformationUpdate(data);

                }).error(function(data){
                  //showAlert("Ha habido un error al obtener la informacion");
                });
              }
            },
            matchInformationUpdate: function(data)
            {

              var self= this;
              this.isUpdate= true;
              this.obtainRequestInformation.idRequestType= data.requestTypeProduct.idRequestType;
              this.obtainRequestInformation.applyingDate= data.applyingDateFormats.dateNumber;
              this.obtainRequestInformation.idUserResponsable= data.idUserResponsible;
              this.periodicPayment.folio= data.folio;
              this.objectRequest.request.idRequest= data.idRequest;
              this.objectRequest.request.folio= data.folio;
              this.objectRequest.request.creationDate= data.creationDateFormats.dateNumber;
              this.objectRequest.request.applyingDate= data.applyingDateFormats.dateNumber;
              this.objectRequest.request.idUserRequest= data.userRequest.idUser;
              this.objectRequest.request.idUserResponsable= data.idUserResponsible;
              this.objectRequest.request.idBudgetMonthBranch= data.idBudgetMonthBranch;
              this.objectRequest.request.idRequestTypeProduct= data.idRequestTypeProduct;
              this.objectRequest.request.idRequestStatus= data.idRequestStatus;
              this.objectRequest.request.description= data.description;
              this.objectRequest.request.purpose= data.purpose;
              this.userRequest = data.userRequest.dwEmployee.employee.fullName;
              this.desaparecer = false;
              var producTypein= [{
                idProductType: '',
                productType: ''
              }];
              producTypein[0].idProductType = data.requestTypeProduct.idProductType;
              producTypein[0].productType = data.requestTypeProduct.productType.productType;
              this.ProductTypes= producTypein;
              this.obtainRequestInformation.idProductType = data.requestTypeProduct.idProductType;
              this.obtainSuppliers(data.requestTypeProduct.idProductType);
              data.requestProductsList.forEach(function(element)
              {
              var producto= self.createProduct();
              producto.idProduct= element.product.idProduct;
              producto.descripcion= element.product.product;
              self.objectRequest.products.push(producto);
              });

              this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
              success(function(data)
              {
                this.matchInformationEstimationsUpdate(data);
              }).error(function(data){
                showAlert("Ha habido un error al obtener la informacion de las cotizacion", {type:3});
              });

            },
            matchInformationEstimationsUpdate: function(data)
            {
              var self = this;
                data.forEach(function(element)
                {
                  console.log(data);
                  var cotizacion= self.createCotizacion();
                  cotizacion.idEstimation= element.idEstimation;
                  cotizacion.fileNameActual= element.fileName;
                  cotizacion.amount = element.amount;
                  cotizacion.rate= element.rate;
                  cotizacion.outOfBudget = element.outOfBudget;
                  cotizacion.idRequest = element.idRequest;
                  cotizacion.idEstimationStatus = element.idEstimationStatus;
                  cotizacion.idAccount = element.idAccount;
                  cotizacion.idCurrency = element.idCurrency;
                  cotizacion.idUserEstimation = element.idUserEstimation;
                  cotizacion.creationDate = element.creationDateFormats.iso;
                  cotizacion.requiredFile = false;
                  self.fillSuppliers(cotizacion);
                });
            },
            fillSuppliers: function(cotizacion)
            {
              var self= this;
              this.$http.get(ROOT_URL+"/providers-accounts/account/"+cotizacion.idAccount).
              success(function(data)
              {
                cotizacion.idSupplier = data.idProvider;

                  self.$http.get(ROOT_URL + "/providers-accounts/provider/"+cotizacion.idSupplier).
                  success(function (data)
                   {
                        cotizacion.accountSupplier= data;
                   });
                  cotizacion.indexOfForm = self.estimations.length;
                  self.estimations.push(cotizacion);
                  this.obtainInformationAutorization();
              }).error(function(data){
                showAlert("Ha habido un error al obtener la informacion de las cotizacion");
              });
            },
            downloadFile: function(idEstimation)
            {
              this.$http.get(ROOT_URL + "/estimations/attachment/download/"+idEstimation).
              success(function (data)
               {

               }).error(function(data)
               {
                showAlert("Ha habido un error al obtener el archivo");
               });
            },
            obtainAllPeriods: function()
            {
              this.$http.get(ROOT_URL + "/periods").
              success(function (data)
               {
                 this.Periods = data;
               }).error(function(data)
               {
                showAlert("Ha habido un error al obtener los periodos");
               });

            },
            savePeriodicPayment: function()
            {
              this.isSavingNow= true;
              var dateInitialWithout= this.periodicPayment.initialDate;
              var datedueDateWithout= this.periodicPayment.dueDate;
              var self= this;
              if (this.periodicPayment.dueDate !== "")
              {
                var dateDueDate= moment(self.periodicPayment.dueDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                var dateDueDates= new Date(dateDueDate);
                var dateisoDue= dateDueDates.toISOString();
                self.periodicPayment.dueDate = dateisoDue.slice(0, -1);
              }
              var dateInitial = moment(self.periodicPayment.initialDate, "DD-MM-YYYY").format("YYYY-MM-DD");
              var dateInitials= new Date(dateInitial);
              var dateisoInitial= dateInitials.toISOString();
              this.periodicPayment.initialDate= dateisoInitial.slice(0, -1);

              this.$http.post(ROOT_URL+"/requests/period-payment", JSON.stringify(this.periodicPayment)).
              success(function(data)
              {
                showAlert("Registro de informacion de pago exitoso");
                this.periodicPayment.idPeriodicPayment= data.idPeriodicPayment;
                this.periodicPayment.initialDate= dateInitialWithout;
                this.periodicPayment.dueDate= datedueDateWithout;
                this.periodicPayment.idPeriodicPaymentStatus= data.periodicPaymentStatus.idPeriodicPaymentStatus;
                this.periodicPayment.paymentNum = data.paymentNum;
                this.isSavingNow= false;
                this.obtainInformationAutorization();
                $("#periodicPayment").modal("hide");
              }).error(function(data)
              {
                showAlert("Ha fallado el registro de su informacion, intente nuevamente");
                this.isSavingNow= false;
              });

            },
            prepareModalPeriodicPayment: function(cotizacion)
            {
              console.log(cotizacion);
              $("#periodicPayment").modal("show");
              this.periodicPayment.amount= cotizacion.amount;
              this.periodicPayment.idCurrency= cotizacion.idCurrency;
              this.periodicPayment.rate= cotizacion.rate;
              this.fillPeriodicPayments();
            },
            fillPeriodicPayments: function()
            {

              this.$http.get(ROOT_URL + "/periodic-payment/folio?folio="+this.periodicPayment.folio).
              success(function (data)
               {
                 console.log(data);
                 this.periodicPayment.idPeriodicPayment = data.idPeriodicPayment;
                 this.periodicPayment.folio = data.folio;
                 this.periodicPayment.amount = data.amount;
                 this.periodicPayment.paymentNum = data.paymentNum;
                 this.periodicPayment.idPeriod = data.idPeriod;
                 this.periodicPayment.idPeriodicPaymentStatus = data.idPeriodicPaymentStatus;
                 this.periodicPayment.idCurrency = data.idCurrency;
                 this.periodicPayment.rate = data.rate;
                 this.periodicPayment.initialDate = data.initialDateFormats.dateNumber;
                 this.periodicPayment.dueDate = data.dueDateFormats.dateNumber;


               }).error(function(data)
               {
                //showAlert("Ha habido un error al obtener los pagos periodicos");
               });
            },
            convertDates: function(date)
            {
              var dateinformatguion= date.split("-");
              return dateinformatguion[0]+"/"+dateinformatguion[1]+"/"+dateinformatguion[2];
            },
            convertDatesGuion: function(date)
            {
              var dateinformatguion= date.split("T");
              var datewithSlash = dateinformatguion.split("-");
              return datewithSlash[2]+"-"+datewithSlash[1]+"-"+datewithSlash[0];

            },
            obtainUserInSession: function()
            {
              this.$http.get(ROOT_URL + "/user").
              success(function (data)
               {
                 this.userInSession = data;
                 this.userRequest = data.dwEmployee.employee.fullName;

               }).error(function(data)
               {
                showAlert("Ha habido un error al obtener al usuario en sesion");
               });
            },
            autorizarCotizacion: function(cotizacion)
            {
              this.$http.post(ROOT_URL+"/estimations/authorization/"+ cotizacion.idEstimation).
              success(function(data)
              {
                showAlert("Cotización propuesta correctamente");
                this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
                  success(function(data)
                 {
                    this.estimations = [];
                    this.matchInformationEstimationsUpdate(data);
                  }).error(function(data){
                    showAlert("Ha habido un error al obtener la informacion de las cotizacion");
                  });

              }).error(function(data)
              {
                showAlert("Ha fallado la autorizacion de la cotizacion intente nuevamente");
              });
            },
            cancelarAutorizacion: function(cotizacion)
            {
              this.$http.post(ROOT_URL+"/estimations/reject/"+cotizacion.idEstimation).
              success(function(data)
              {
                showAlert("Se ha cancelado la aprobacion de la cotizacion correctamente");
                this.$http.get(ROOT_URL+"/estimations/request/"+this.idRequest).
                 success(function(data)
                 {
                   this.estimations = [];
                   this.matchInformationEstimationsUpdate(data);
                 }).error(function(data){
                   showAlert("Ha habido un error al obtener la informacion de las cotizacion");
                 });

              }).error(function(data)
              {
                showAlert("Ha fallado la autorizacion de la cotizacion intente nuevamente");
              });
            },
            modifyPeriodicPayment: function()
            {
              this.isSavingNow= true;
              var dateInitialWithout= this.periodicPayment.initialDate;
              var datedueDateWithout= this.periodicPayment.dueDate;
              var self= this;
              if (this.periodicPayment.dueDate !== "")
              {
                var dateDueDate= moment(self.periodicPayment.dueDate, "DD-MM-YYYY").format("YYYY-MM-DD");
                var dateDueDates= new Date(dateDueDate);
                var dateisoDue= dateDueDates.toISOString();
                self.periodicPayment.dueDate = dateisoDue.slice(0, -1);
              }
              var dateInitial = moment(self.periodicPayment.initialDate, "DD-MM-YYYY").format("YYYY-MM-DD");
              var dateInitials= new Date(dateInitial);
              var dateisoInitial= dateInitials.toISOString();
              this.periodicPayment.initialDate= dateisoInitial.slice(0, -1);

              this.$http.post(ROOT_URL+"/periodic-payment/"+ this.periodicPayment.idPeriodicPayment, JSON.stringify(this.periodicPayment)).
              success(function(data)
              {
                showAlert("Se ha modificado la forma de pago correctamente");
                this.periodicPayment.initialDate= dateInitialWithout;
                this.periodicPayment.dueDate= datedueDateWithout;
                this.isSavingNow = false;
                this.obtainInformationAutorization();
                $("#periodicPayment").modal("hide");
              }).error(function(data)
              {
                showAlert("Ha fallado la autorizacion de la cotizacion intente nuevamente");
              });
            },
            obtainInformationAutorization: function()
            {
              this.infoAutorization= '';
              this.$http.get(ROOT_URL+"/folios?folio="+ this.objectRequest.request.folio).
              success(function(data)
              {
                this.infoAutorization= data;

              }).error(function(data)
              {
                //showAlert("No se ha podido obtener la informacion de la autorizacion");
              });
            },
            commitAuthorization: function (auth, authorize) {
              if (authorize) {
                this.$http.post(ROOT_URL+"/folios/authorizations/"+ auth.idAuthorization +"/authorize",{
                  details: auth.details
                }).
                success(function(data)
                {
                  this.obtainInformationAutorization();
                  $("#auth-confirmation-modal").modal("hide");
                }).error(function() {
                  showAlert("Ha habido un error al autorizar la solicitud, intente nuevamente");
                });
              } else {
                this.$http.post(ROOT_URL+"/folios/authorizations/"+ auth.idAuthorization +"/reject", {
                  details: auth.details
                }).
                success(function(data)
                {
                  showAlert(data);
                  this.obtainInformationAutorization();
                  $("#auth-confirmation-modal").modal("hide");
                }).error(function() {
                  showAlert("Ha habido un error al cancelar la solicitud, intente nuevamente");
                });
              }
            },
            autorizarSolicitudIndividual: function(info)
            {
              this.authModal.authorization = info;
              this.authModal.authorize = true;
              $("#auth-confirmation-modal").modal("show");
            },
            rechazarSolicitudIndividual: function(info)
            {
              this.authModal.authorization = info;
              this.authModal.authorize = false;
              $("#auth-confirmation-modal").modal("show");
            },
            validateCurrency: function(cotizacion)
            {
              var self = this;
              if (cotizacion.idCurrency== '')
              {
                this.flagrate = false;

              }
              if (cotizacion.idCurrency == 1)
              {
                cotizacion.rate = 1;
                this.flagrate = true;
              }
              else
              {
                cotizacion.rate= '';
                this.flagrate = false;
              }
            },
            validateAmount: function(cotizacion)
            {
              if (cotizacion.amount <= 0)
              {
                  cotizacion.amount=1;
                  showAlert("No puedes tener numeros negativos para montos de las cotizaciones");
              }
            },
            validateRate: function(cotizacion)
            {
              if (cotizacion.rate <= 0)
              {
                  cotizacion.rate=1;
                  showAlert("No puedes tener numeros negativos para tipo de cambio de las cotizaciones");
              }
            },
            exit: function()
            {
              window.location.href= ROOT_URL;
            },
            activarTimePickerFinal: function(fechainicial)
            {
              var fecha= moment(fechainicial, 'DD-MM-YYYY').format('YYYY-MM-DD');
              var fechafinal = moment(fecha).add(1, "day");

                this.timePickerFechaVencimiento = $('#dateFechaVencimiento').datetimepicker({
                locale: 'es',
                format: 'DD-MM-YYYY',
                useCurrent: false,
                minDate: fechafinal
              }).data();

            }

          },
        filters:
        {
          obtainMailUser: function(param)
          {
            var self= this;
            var newParam;
            self.Users.forEach(function(element)
            {
                if (element.idUser == param)
                {
                newParam =element.mail;
                }
            });
            return newParam;
          },
          filterNull: function(param)
          {
            if (param == "null")
            {
                return ''
            }
            else
            {
              return param;
            }
          },
          filterCurrency: function(idCurrency)
          {
            var retorno;
            this.currencies.forEach(function(element){
                if (idCurrency == element.idCurrency)
              {
               retorno= element.acronym;
              }
            });
            return retorno;
          },
          filterMoney: function(monto)
          {
              var retorno;
              var retorno= accounting.formatNumber(monto,2,",");
              return retorno;
          },
          separate: function (value) {
                    return value.replace(/:/g, " ");
                }

        }
        });

        </script>
    </jsp:attribute>

    <jsp:body>
      <div id="contenidos">

        <div class="container-fluid" style="margin-left: 100px">

          <form v-on:submit.prevent="saveRequest">
          <div class="row">
            <div class="col-xs-4">
            <h1>Solicitud Periodica</h1>
            </div>
            <div class="col-xs-4 col-xs-offset-4">
              <label>
                Solicitante
              </label>
              <input class="form-control" type="text" name="name" value="" disabled="true" v-model="userRequest">
            </div>
          </div>
          <br>
          <div class="row">
            <div class="col-xs-2">
             <label>
               Rubro
             </label>
             <select class="form-control" v-model="obtainRequestInformation.idRequestType" :disabled="desactivarCombos || isUpdate" @change="obtainProductType" required>
               <option v-for="RequestType in RequestTypes"
                 value="{{RequestType.idRequestType}}">{{RequestType.requestType}}
               </option>
             </select>
            </div>

            <div class="col-xs-2">
              <label>
                Producto
              </label>
              <select class="form-control" v-model="obtainRequestInformation.idProductType" :disabled="desactivarCombos || isUpdate"
                @change="obtainProducts" required>
                <option></option>
                <option v-for="ProductType in ProductTypes" value="{{ProductType.idProductType}}">
                  {{ProductType.productType}}
                </option>
              </select>
            </div>

            <div class="col-xs-2">
              <label>
                Tipo de Producto
              </label>
              <select class="form-control" v-model="idProducto" id="selectProducto" :disabled="isUpdate" required>
                <option></option>
                <option v-for="Product in Productos" value="{{Product.idProduct}}">
                  {{Product.product}}
                </option>
              </select>
            </div>

            <div class="col-xs-1">
              <div class="col-xs-6">
                <button type="button" class="btn btn-default" style="margin-top: 25px; margin-left: -33px"
                  v-on:click="saveProduct" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Agregar Producto">
                  <span class="glyphicon glyphicon-plus"></span>
                </button>
              </div>
            </div>


            <div class="col-xs-5">
              <label>
                Centro de Costos
              </label>
              <select class="form-control" required="true" v-model="obtainRequestInformation.idUserResponsable"
              @change="obtainRequestInfo" :disabled="isUpdate">
              <option value="{{userInSession.idUser}}">
                    {{ userInSession.dwEmployee.dwEnterprise.branch.branchName }}/
                    {{ userInSession.dwEmployee.dwEnterprise.area.areaName }}
                </option>
              </select>
            </div>
          </div>
          <br>
            <div class="row">
              <div class="col-xs-12">
                <label>
                  Lista de Productos
                </label>
              </div>
            </div>

            <div class="row" v-for="produc in objectRequest.products">
              <div class="col-xs-4">
                <div class="col-xs-4">
                  {{produc.descripcion}}
                </div>
                <div class="col-xs-2 text-left">
                  <button class="btn btn-default" @click="deleteProduct(produc)" :disabled="isUpdate" data-toggle="tooltip" data-placement="top" title="Quitar Producto">
                    <span class="glyphicon glyphicon-remove"></span>
                  </button>
                </div>
              </div>

            </div>
            <br>
          <div class="row">
            <div class="col-xs-12">
              <label>
                Descripción del Servicio
              </label>
              <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.description"
                :disabled="isUpdate" required></textarea>
            </div>
          </div>
          <br>
          <div class="row">
            <div class="col-xs-12">
              <label>
                Justificación del Servicio
              </label>
              <textarea class="form-control" rows="3" cols="50" v-model="objectRequest.request.purpose"
                :disabled="isUpdate" required></textarea>
            </div>
          </div>

          <br>
            <div class="row">
              <div class="col-md-2 col-md-offset-10 text-right" v-if="desaparecer">
                <button class="btn btn-success" :disabled="desactivarGuardar||isSavingNow">Guardar</button>
              </div>
              <div class="col-md-4 col-md-offset-8 text-right" v-if="!desaparecer">
                <button type="button" class="btn btn-default" @click="newCotizacion"
                        v-if="objectRequest.request.isSaved || isUpdate ">Agregar Cotización
                </button>
                <button type="button" class="btn btn-default" @click="exit">Enviar</button>
                <button type="button" class="btn btn-default" @click="exit">Salir</button>
              </div>
            </div>

        </form>
        <br>
        <div class="row" v-for="cotizacion in estimations  | orderBy 'idEstimationStatus'">
          <form v-on:submit.prevent="saveEstimations(cotizacion)" id="form-{{cotizacion.indexOfForm}}">
          <div class="col-xs-12">
            <div class="panel panel-default">
              <div class="panel-heading">
                <div class="row">
                  <div href="#collapse{{cotizacion.indexOfForm}}">
                    <div class="col-xs-4 text-left">
                      <div class="col-xs-4">
                        <h3 class="panel-title">Cotización
                        </h3>
                      </div>
                      <div class="col-xs-8">
                        <h4 class="panel-title" v-if="cotizacion.idCurrency> 0">Monto MXN: {{cotizacion.amount * cotizacion.rate | filterMoney}} <br><span v-if="cotizacion.idCurrency != 1"> Monto en {{cotizacion.idCurrency | filterCurrency}}: {{cotizacion.amount | filterMoney}}</span></h4>
                      </div>
                    </div>
                    <div class="col-xs-2" >
                      <span class="label label-danger" v-if="cotizacion.outOfBudget == 1">Fuera de Presupuesto</span>
                    </div>
                    <div class="col-xs-2 text-right">
                      <label  class="label label-primary" v-if="cotizacion.idEstimationStatus== 2">Cotización Propuesta</label>
                    </div>
                  </div>
                  <div>
                    <div class="col-xs-4 text-right">
                      <button type="submit" class="btn btn-sm btn-default" v-if="cotizacion.idEstimation == 0"
                              :disabled="isSavingNow" data-toggle="tooltip" data-placement="bottom" title="Guardar Cotización">
                        <span class="glyphicon glyphicon-floppy-disk"></span>
                      </button>
                      <button v-if="cotizacion.idEstimation > 0" type="button" class="btn btn-sm btn-default"
                              @click="deleteCotizacion(cotizacion)" :disabled="isSavingNow" data-toggle="tooltip"
                              data-placement="bottom" title="Eliminar cotización">
                        <span class="glyphicon glyphicon-trash"></span>
                      </button>
                      <button v-if="cotizacion.idEstimation == ''" type="button" class="btn btn-sm btn-default"
                              @click="deleteCotizacion(cotizacion)" :disabled="isSavingNow" data-toggle="tooltip"
                              data-placement="bottom" title="Cancelar">
                        <span class="glyphicon glyphicon-remove"></span>
                      </button>
                      <button v-if="cotizacion.idEstimationStatus > 0 && cotizacion.idEstimationStatus < 2 && cotizacion.isCollapsed == true"
                              type="submit" class="btn btn-sm btn-default" :disabled="isSavingNow" data-toggle="tooltip"
                              data-placement="bottom" title="Modificar Cotización">
                        <span class="glyphicon glyphicon-pencil"></span>
                      </button>
                      <button href="#collapse{{cotizacion.indexOfForm}}" @click="setIsCollapsed(cotizacion)"
                              data-toggle="collapse" class="btn btn-sm btn-default" type="button">
                        <span :class="{ 'glyphicon-menu-down': cotizacion.isCollapsed, 'glyphicon-menu-up': ! cotizacion.isCollapsed }"
                              class="glyphicon" ></span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              <div id="collapse{{cotizacion.indexOfForm}}" class="panel-body collapse"
                  :class="{ 'in' : cotizacion.expanded || cotizacion.idEstimationStatus == 2  }">
                <div class="row">
                  <div class="col-xs-2">
                    <label>
                      Proveedor
                    </label>
                    <select class="form-control" v-model="cotizacion.idSupplier"
                      @change="obtainAccounts(cotizacion)" required="true"
                      :disabled="cotizacion.idEstimationStatus > 1">
                      <option></option>
                      <option v-for="supplier in suppliers" value="{{supplier.idProvider}}">
                        {{supplier.providerName | separate}}
                      </option>
                    </select>
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Cuenta Bancaria
                    </label>
                    <select class="form-control" v-model="cotizacion.idAccount" required="true"
                      :disabled="cotizacion.idEstimationStatus > 1">
                      <option></option>
                      <option v-for="accounts in cotizacion.accountSupplier" value="{{accounts.idAccount}}">
                        {{accounts.account.accountNumber}}
                      </option>
                    </select>
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Tipo de Moneda
                    </label>
                    <select class="form-control" v-model="cotizacion.idCurrency" required="true"
                      @change="validateCurrency(cotizacion)" :disabled="cotizacion.idEstimationStatus > 1">
                      <option></option>
                      <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                        {{curr.currency}}
                      </option>
                    </select>
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Monto
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">$</span>
                      <input number class="form-control" placeholder="" v-model="cotizacion.amount"
                        @change="validateAmount(cotizacion)" onkeypress="return validateFloatKeyPress(this,event)"
                        :disabled="cotizacion.idEstimationStatus > 1" required="true">
                    </div>
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Tipo de Cambio
                    </label>
                    <div class="input-group">
                      <span class="input-group-addon">$</span>
                      <input number class="form-control" :disabled="flagrate || cotizacion.idEstimationStatus > 1"
                        v-model="cotizacion.rate" @change="validateRate(cotizacion)"
                        onkeypress="return validateFloatKeyPress(this,event)" required="true">
                    </div>
                  </div>
                </div>
                <br>
                <div class="row">
                  <div class="col-xs-4">
                    <label>
                      Archivo de la Cotización
                    </label>
                    <input type="file" name="file" class="form-control"
                     v-model="cotizacion.fileName" required="{{cotizacion.requiredFile}}"
                           accept="application/pdf" :disabled="cotizacion.idEstimationStatus > 1">
                  </div>
                  <div class="col-xs-2">
                    <label>
                      Archivo Actual
                    </label>
                    <p>
                      {{cotizacion.fileNameActual}}
                    </p>
                  </div>
                  <div class="col-xs-1" v-if="cotizacion.idEstimation > 0">
                  <p style="margin-top: 25px">
                  <a :href="attachment + cotizacion.idEstimation">
                    <button type="button" class="btn btn-default" data-toggle="tooltip" data-placement="top" title="Descargar">
                      <span class="glyphicon glyphicon-download" style="font-size: 17px"><span>
                    </button>
                  </a>
                  </p>
                  </div>
                  <div class="col-xs-3">
                    <button type="button" class="btn btn-default" @click="prepareModalPeriodicPayment(cotizacion)"
                     style="margin-top: 25px" v-if="cotizacion.idEstimationStatus== 2">Agregar Informacion de Pago
                    </button>
                  </div>
                  <div class="col-xs-2 text-right">
                    <button type="button" class="btn btn-default" name="button"
                      v-if="cotizacion.idEstimationStatus== 1" style="margin-top:25px"
                      @click="autorizarCotizacion(cotizacion)">
                      Proponer Cotización
                    </button>
                    <button type="button" class="btn btn-default" name="button"
                      v-if="cotizacion.idEstimationStatus== 2 && isAutoriced" style="margin-top:25px"
                      @click="cancelarAutorizacion(cotizacion)">
                      Rechazar
                    </button>
                    <button type="button" class="btn btn-default" name="button"
                      v-if="!(isAutoriced)" style="margin-top:25px"
                      @click="autorizarCotizacion(cotizacion)">
                      Autorizar
                    </button>

                  </div>

                  </div>
                </div>
              </div>
            </div>
          </form>
          </div>
          <div class="row">
            <div class="col-xs-12">
              <label>
                Autorizaciones de la Solicitud.
              </label>
              <table class="table table-striped">
                <thead>
                  <th>
                    Nombre
                  </th>
                  <th>
                    Estatus
                  </th>
                  <th>
                    Autorizar
                  </th>
                  <th>
                    Detalles
                  </th>
                </thead>
                <tbody>
                  <tr v-for="info in infoAutorization.authorizations">
                    <td>
                      {{info.users.dwEmployee.employee.fullName}}
                    </td>
                    <td>
                      <span class="label label-success" v-if="info.idAuthorizationStatus == 2">Autorizado</span>
                      <span class="label label-warning" v-if="info.idAuthorizationStatus == 1">Pendiente</span>
                      <span class="label label-danger" v-if="info.idAuthorizationStatus == 3">Rechazado</span>
                    </td>
                    <td>
                      <button type="button" class="btn btn-success btn-sm" name="button" @click="autorizarSolicitudIndividual(info)"
                        v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Autorizar</button>
                      <button type="button" class="btn btn-danger btn-sm" name="button" @click="rechazarSolicitudIndividual(info)"
                        v-if="info.idAuthorizationStatus == 1 & info.idUser == userInSession.idUser">Rechazar</button>

                    </td>
                    <td>
                      <textarea name="name" rows="3" cols="40" v-model="info.details" v-if="info.idAuthorizationStatus == 1">

                      </textarea>
                      <textarea name="name" rows="3" cols="40" v-model="info.details | filterNull"
                        v-if="info.idAuthorizationStatus == 3 || info.idAuthorizationStatus == 2" disabled="true" >

                      </textarea>
                    </td>
                  </tr>
                </tbody>

              </table>

            </div>
          </div>
        </div>
          <div class="modal fade" id="periodicPayment" tabindex="-1" role="dialog" aria-labelledby="" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title">Introduzca la Información del Pago Periódico</h4>
                </div>
                <div class="modal-body">
                  <div class="row">
                    <div class="col-xs-6">
                      <label>
                        Monto:
                      </label>
                      <div class="input-group">
                        <span class="input-group-addon">$</span>
                        <input type="text" class="form-control" placeholder="" v-model="periodicPayment.amount"
                          disabled="true">
                      </div>
                    </div>

                    <div class="col-xs-6">
                      <label>
                        Periodicidad
                      </label>
                      <select class="form-control" name="" v-model="periodicPayment.idPeriod">
                        <option></option>
                        <option v-for="periodo in Periods" value="{{periodo.idPeriod}}">
                          {{ periodo.period}}
                        </option>
                      </select>
                    </div>

                  </div>
                  <br>
                  <div class="row">

                    <div class="col-xs-6">
                      <label>
                        Fecha Aplicación
                      </label>
                      <div class="form-group">
                      <div class='input-group date' id='datePagoInicial'>
                          <input type='text' class="form-control" v-model="periodicPayment.initialDate">
                          <span class="input-group-addon">
                              <span class="glyphicon glyphicon-calendar"></span>
                          </span>
                      </div>
                      </div>
                    </div>

                    <div class="col-xs-6">
                      <label>
                        Fecha de Vencimiento (Opcional)
                      </label>
                      <div class="form-group">
                      <div class='input-group date' id='dateFechaVencimiento'>
                          <input type='text' class="form-control" v-model="periodicPayment.dueDate" >
                          <span class="input-group-addon">
                              <span class="glyphicon glyphicon-calendar" @click="activarTimePickerFinal(periodicPayment.initialDate)"></span>
                          </span>
                      </div>
                      </div>
                    </div>
                  </div>
                  <br>
                    <div class="row">
                      <div class="col-xs-6">
                        <label>
                          Tipo de Moneda
                        </label>
                        <select class="form-control" v-model="periodicPayment.idCurrency" disabled="true">
                          <option></option>
                          <option v-for="curr in currencies" value="{{curr.idCurrency}}">
                            {{curr.currency}}
                          </option>
                        </select>
                      </div>

                      <div class="col-xs-6">
                        <label>
                          Tipo de Cambio
                        </label>
                        <div class="input-group">
                          <span class="input-group-addon">$</span>
                          <input number class="form-control" placeholder="" v-model="periodicPayment.rate"
                            disabled="true">
                        </div>
                      </div>
                    </div>
                    <br>
                      <div class="row">
                        <div class="col-xs-12 text-right">
                          <button type="button" class="btn btn-success" @click="savePeriodicPayment"
                            :disabled="isSavingNow" v-if="periodicPayment.idPeriodicPayment == ''">
                            Guardar
                          </button>
                          <button type="button" class="btn btn-success" @click="modifyPeriodicPayment"
                            :disabled="isSavingNow" v-if="periodicPayment.idPeriodicPayment !== ''">
                            Modificar
                          </button>
                        </div>
                      </div>
                </div>
              </div>
            </div>
          </div>
          <div id="auth-confirmation-modal" class="modal fade">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">¿Confirma que desea autorizar la solicitud?</h4>
                </div>
                <div class="modal-body">
                  <p></p>
                </div>
                <div class="modal-footer">
                  <button @click="commitAuthorization(authModal.authorization, authModal.authorize)" class="btn btn-default">Aceptar</button>
                  <button class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
              </div>
            </div>
          </div>
          </div> <!-- container-fluid -->

      </div> <!-- #contenidos -->
      <!-- Fecha de Termino- Agregar fecha dia de solicitud-->
    </jsp:body>
</t:template>
