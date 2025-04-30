<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Filtrar Patentes</title>
       <link rel="stylesheet" href="${pageContext.request.contextPath}/css/filtra_style.css">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>

</head>
<body>

    <h1>Filtrar Patentes</h1>

    <div>
        <label>Número do Processo:</label><br>
        <input type="text" id="numeroProcesso" placeholder="Digite o número do processo"><br><br>

        <label>Requerente:</label><br>
        <input type="text" id="nomeRequerente" placeholder="Digite o nome do requerente"><br><br>

        <button onclick="filtrar()">Filtrar</button>
        <button onclick="voltar()">Voltar</button>
    </div>

    <div id="listaPatentes">
        <!-- Aqui vai a tabela de resultados -->
    </div>

    <script>
        function filtrar() {
            const numeroProcesso = $('#numeroProcesso').val().trim();
            const nomeRequerente = $('#nomeRequerente').val().trim();

            $.ajax({
                url: contextPath + '/patents/filter',
                type: 'GET',
                dataType: 'json',
                data: {
                    numeroProcesso: numeroProcesso,
                    nomeRequerente: nomeRequerente
                },
                success: function(data) {
                    if (data.length === 0) {
                        $('#listaPatentes').html('<p style="color:red;">Nenhuma patente encontrada.</p>');
                        return;
                    }

                    let html = '<table>';
                    html += '<thead><tr><th>Número da Publicação</th><th>Título</th><th>Requerentes</th><th>Data de Publicação</th></tr></thead>';
                    html += '<tbody>';

                    data.forEach(function(patente) {
                        html += '<tr>';
                        html += '<td>' + patente.publicationNumber + '</td>';
                        html += '<td>' + patente.title + '</td>';
                        html += '<td>' + patente.applicants.map(a => a.name).join(', ') + '</td>';
                        html += '<td>' + patente.publicationDate + '</td>';
                        html += '</tr>';
                    });

                    html += '</tbody></table>';

                    $('#listaPatentes').html(html);
                },
                error: function(xhr) {
                    alert('Erro ao buscar patentes: ' + xhr.status);
                }
            });
        }

        function voltar() {
            window.location.href = contextPath + '/patents/';
        }
    </script>

</body>
</html>
