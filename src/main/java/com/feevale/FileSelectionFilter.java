/*
**===========================================================================
**  @file    FileSelectionFilter.java
**  @author  Eduardo Lorscheiter e Loreno Enrique Ribeiro 
**  @class   Projeto - Compiladores
**  @date    Junho/2026
**  @version 1.0
**  @brief   Filtros de seleção de arquivos para o compilador
**===========================================================================
*/

package com.feevale;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileSelectionFilter extends FileFilter {
    // Extensão dos arquivos que podem ser selecionados
    private String extension;

    /**
     * Construtor do filtro
     * 
     * @param extension Extensão permitida
     */
    public FileSelectionFilter(String extension) {
        this.extension = extension.toLowerCase();
    }

    /**
     * Retorna quais extensões poderão ser escolhidas
     */
    @Override
    public String getDescription() {
        return "*." + extension;
    }

    /**
     * Retorna se o arquivo pode ser selecionado ou não
     * 
     * @param file Arquivo a ser verificado
     * @return Retorna true se o arquivo for válido, ou false caso contrário
     */
    @Override
    public boolean accept(File file) {
        if (file == null)
            return false;

        if (file.isDirectory())
            return true;

        String fileExtension = getExtension(file);

        return fileExtension != null && fileExtension.equalsIgnoreCase(extension);
    }

    /**
     * Retorna a parte com a extensão de um arquivo
     * 
     * @param file Arquivo cujo a extensão deve ser retornada
     */
    private String getExtension(File file) {
        if (file == null)
            return null;

        String filename = file.getName();
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) {
            return filename.substring(i + 1).toLowerCase();
        }

        return null;
    }
}