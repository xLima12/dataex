package br.com.codenoir.exceptions;

import javax.swing.*;
import java.util.Map;

public class ExceptionHandler {
    private record ErrorMapping(String title, String message) {}

    private static final Map<Class<? extends Throwable>, ErrorMapping> EXCEPTION_MAPPINGS = Map.of(
            InvalidBranchException.class, new ErrorMapping("Filial Inválida",
                    "Falha na validação: as linhas do arquivo contêm filiais de destino diferentes.\n" +
                    "Verifique e corrija antes de gerar o XML."),
            FileReadException.class, new ErrorMapping("Erro de Leitura", "Não foi possível ler o arquivo."),
            FileEmptyException.class, new ErrorMapping("Arquivo Vazio", "O arquivo está vazio."),
            MissingOrInvalidHeaderException.class, new ErrorMapping("Arquivo Vazio",
                    "Cabeçalhos ausentes ou incorretos"),
            UnsupportedFileEncodingException.class, new ErrorMapping("Erro de Codificação do Arquivo",
                    "Não foi possível ler o arquivo com nenhuma codificação suportada.")
    );

    public static void handle(Throwable throwable) {
        ErrorMapping mapping = EXCEPTION_MAPPINGS.getOrDefault(
                throwable.getClass(),
                new ErrorMapping("Erro Desconhecido", "Ocorreu um erro inesperado.")
        );

        showErrorDialog(mapping.title(), mapping.message());
    }

    private static void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
