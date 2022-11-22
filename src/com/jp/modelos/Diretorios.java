/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jp.modelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ALUNO
 */
public class Diretorios {

    private File repositorio = null;
    private File destino = null;
    
    public Diretorios(File repositorio, File destino) {
        this.repositorio = repositorio;
        this.destino = destino;
    }
    
    public void sincronizar() throws Exception{
        if(this.repositorio.exists()){
            if(this.destino.exists()){
                sincronizarArquivo(this.repositorio);
            }else throw  new Exception("O destino não existe.");
        }else throw  new Exception("O repositório não existe.");
        
    }
    
    private void sincronizarArquivo(File local)throws Exception{
        try {
            System.out.println("|||||||||||||||");
            String nomeDaPasta = local.getPath().replace(repositorio.getPath(), "") + "\\";
            File novoDestino = new File(destino.getPath() + nomeDaPasta);
            File pastasRepositorio[];
            pastasRepositorio = local.listFiles();
            
            for(int i = 0; i < pastasRepositorio.length; i++){
                if(pastasRepositorio[i].isFile()){
                    File novoArquivo = new File(this.destino, nomeDaPasta + pastasRepositorio[i].getName());
                    System.out.println("É um arquivo no local: " + novoArquivo.getPath());
                    novoArquivo.deleteOnExit();
                    
                    Files.copy(pastasRepositorio[i].toPath(), novoArquivo.toPath());
                    
                }else{
                    if(pastasRepositorio[i].isDirectory()){
                        File novoLocal = new File(this.destino, nomeDaPasta + pastasRepositorio[i].getName());
                        System.out.println("é um diretório no local: " + novoLocal.getPath());
                        
                        if(!novoLocal.exists()){
                            novoLocal.mkdir();
                        }
                        
                        sincronizarArquivo(pastasRepositorio[i]);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
