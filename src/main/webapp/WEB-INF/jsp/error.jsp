<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">

    <div class="panel panel-danger">

        <div class="panel-heading">
            <h3>Error en la aplicación</h3>
        </div>

        <div class="panel-body">

            <div class="alert alert-danger">

                <strong>¡Ha ocurrido un error!</strong>

                <p>
                    Se ha producido un problema inesperado.
                    Por favor, inténtalo de nuevo.
                </p>

            </div>

            <a href="${pageContext.request.contextPath}/"
               class="btn btn-primary">
                Volver a la página principal
            </a>

        </div>

    </div>

</div>

<%@ include file="common/footer.jspf" %>