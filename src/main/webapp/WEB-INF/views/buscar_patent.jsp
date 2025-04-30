<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Busca com AJAX</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/busca_style.css">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>

</head>
<body>
    <h1>Buscar Patente</h1>
    <a href="${pageContext.request.contextPath}/patents/filtrarPatents">Ir para Filtrar Patents</a>

    <div>
        <input type="text" id="termoBusca" placeholder="Digite o número do processo">
        <button id="btnBuscar">Buscar</button>
    </div>

    <div id="resultado"></div>

    <script>
    $(document).ready(function() {
        // Vincula o evento de click corretamente
        $('#btnBuscar').on('click', buscar);

        // Mantém o evento de Enter
        $('#termoBusca').keypress(function(e) {
            if (e.which == 13) buscar();
        });
    });

    function buscar() {
        const termo = $('#termoBusca').val().trim();
        if (!termo) {
            alert('Por favor, digite o número do processo');
            return;
        }

        const $btnBuscar = $('#btnBuscar');
        $btnBuscar.prop('disabled', true);

        $.ajax({
            url: 'search',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: { numeroProcesso: termo },
            success: function(resposta) {
                let html  = '<p><strong>Número da Publicação:</strong> ' + resposta.publicationNumber + '</p>';
                html += '<p><strong>Número Internacional de Aplicação:</strong> ' + resposta.internationalApplicationNumber + '</p>';
                html += '<p><strong>Data de Publicação:</strong> ' + resposta.publicationDate + '</p>';

                let applicantNames = resposta.applicants
                    .map(a => a.name)
                    .join(', ');
                html += '<p><strong>Requerentes:</strong> ' + applicantNames + '</p>';
                html += '<p><strong>Título:</strong> ' + resposta.title + '</p>';
                html += '<button id="salvarBtn">Salvar</button>';

                $('#resultado').html(html);

                $('#salvarBtn').on('click', function() {
                    $.ajax({
                        url: 'save',
                        type: 'POST',
                        contentType: 'application/json; charset=UTF-8',
                        data: JSON.stringify(resposta),
                        success: function() {
                            alert('Salvo com sucesso!');
                        },
                        error: function(xhr) {
                            alert('Erro ao salvar: ' + xhr.status);
                        }
                    });
                });
            },
            error: function(xhr) {
                $('#resultado').html(
                    '<p style="color:red">Nenhuma patente encontrada</p>'
                );
            },
            complete: function() {
                $btnBuscar.prop('disabled', false);
            }
        });
    }
    </script>
</body>
</html>