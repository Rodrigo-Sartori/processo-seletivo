package com.desafio.agenda.config;

import com.desafio.agenda.model.DocumentType;

public class FormatUtils {


    public static DocumentType validateDocument(String document) {
        String filteredDocument = document.replaceAll("\\D", "");
        if (filteredDocument.matches("\\d{11}")) {
            return DocumentType.CPF;
        } else if (filteredDocument.matches("\\d{14}")) {
            return DocumentType.CNPJ;
        } else {
            throw new RuntimeException("invalid document");
        }
    }

    public static void validateCEP(String cep) {
        var isValidCep = cep.matches("^\\d{5}-?\\d{2}$");
        if (!isValidCep) {
            throw new RuntimeException("invalid cep");
        }
    }
}
