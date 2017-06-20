/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

import java.io.File;

/**
 *
 * @author Ervin
 */
public class OpenFolder {

    public int filescounter;
    OpenFolder(File FF) 
    {
        File[] listOfFiles = FF.listFiles();
        this.filescounter=listOfFiles.length-1;
    }
    
    //returneaza fisierul de pe pozitia index din folder
    public File getFile(File folder,int index)
    {
        File[] listOfFiles = folder.listFiles();
        return listOfFiles[index];      
    }
    
    //functia asta verifica daca un folder are doar fisiere .html sau .css ,
    //daca nu au extensia asta le sterge.putem folosi asta ca metoda de siguranta.
    public void CheckForExtension(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        String name = new String();
        for (int i = 0; i < listOfFiles.length; i++) {
            name=listOfFiles[i].getName();
            if(!(name.endsWith(".html"))&&!(name.contentEquals("style.css")))listOfFiles[i].delete();
        }
    }
       //sterge toate fisierele din folder
    public void deleteAllFiles(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        String name = new String();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            listOfFiles[i].delete();
        }
    }
    //returneaza numele ultimului fisier uploadat
    public String getLastFileUploadedName(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        //String name = listOfFiles[listOfFiles.length-1].getName();
        return listOfFiles[listOfFiles.length-1].getName();  
    }
     public String getFileName(File folder,int index)
     {
         File[] listOfFiles = folder.listFiles();
         return listOfFiles[index].getName();
     }
    //returneaza ultimul fisier uploadat
    public File getLastFileUploaded(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        //String name = listOfFiles[listOfFiles.length-1].getName();
        return listOfFiles[listOfFiles.length-1];
    }
    //numarul de fisiere din folder
    public int getFilesNumber(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        return listOfFiles.length;
    }
    
    public void listAllFiles(File folder)
    {
        File[] listOfFiles = folder.listFiles();
        String name = new String();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            System.out.println(listOfFiles[i]);
        }
    }
    public File openSpecificFolder(File folder,String name)
    {
        File[] listOfFiles = folder.listFiles();
        //String name = new String();
        for (int i = 0; i < listOfFiles.length; i++) 
        {
            if(listOfFiles[i].equals(name))return listOfFiles[i];
        }
        return null;
    }
}
