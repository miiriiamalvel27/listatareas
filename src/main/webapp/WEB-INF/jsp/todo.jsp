<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">

    <div class="row">

        <div class="col-md-6 col-md-offset-3">

            <div class="panel panel-primary">

                <div class="panel-heading">
                    Añadir tarea
                </div>

                <div class="panel-body">

                    <form:form method="post" modelAttribute="todo">

                        <form:hidden path="id" />

                        <!-- Descripcion de la tarea -->

                        <fieldset class="form-group">

                            <form:label path="description">
                                Descripción
                            </form:label>

                            <form:input
                                path="description"
                                type="text"
                                class="form-control"
                                required="required" />

                            <form:errors
                                path="description"
                                cssClass="text-warning" />

                        </fieldset>

                        <!-- Fecha objetivo -->

                        <fieldset class="form-group">

                            <form:label path="targetDate">
                                Fecha objetivo
                            </form:label>

                            <form:input
                                path="targetDate"
                                type="text"
                                class="form-control"
                                required="required" />

                            <form:errors
                                path="targetDate"
                                cssClass="text-warning" />

                        </fieldset>

                        <!-- Boton guardar -->

                        <button type="submit" class="btn btn-success">
                            Guardar
                        </button>

                    </form:form>

                </div>

            </div>

        </div>

    </div>

</div>

<%@ include file="common/footer.jspf" %>