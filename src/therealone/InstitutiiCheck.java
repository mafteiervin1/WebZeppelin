/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

import com.osbcp.cssparser.Rule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Ervin
 */
public class InstitutiiCheck {
    public ArrayList<String> FileContent;
    public String fileContent;
    public int menuHREFflag;
    public int footerHREFflag;
    public int imgTitleflag;
    public int socialMediaflag;
    public int existingTitleFlag;
    public String menuModel;
    public int menuExisting;
    public int footerExisting;
    public int inputFlag;
    public List<Rule> CssCuloriFile;
    public List<Rule> CssFonturiFile;
    public Document doc;
    public int devicesFlag;
     public int searchFlag=0;
     public int filterFlag=0;
    
    public InstitutiiCheck(File institutiiFile) throws Exception
    {
        
        OpenFileContent ofc=new OpenFileContent(institutiiFile); 
        this.fileContent=ofc.toString();
       // System.out.println(this.fileContent);
        this.doc=Jsoup.parse(fileContent);
        this.FileContent=ofc.fileContent;
        //this.menuModel=new String();
        this.menuHREFflag=0;this.footerHREFflag=0;this.imgTitleflag=0;
        this.inputFlag=0;
        if(doc.getElementById("menu")!=null)this.menuModel=(doc.getElementById("menu")).toString();
        if(this.menuModel==null)this.menuExisting=0;
        else this.menuExisting=1;
        this.devicesFlag=0;
        if((doc.getElementById("footer"))!=null)this.footerExisting=1;
        else this.footerExisting=0;
        
        this.doc=Jsoup.parse(this.fileContent);//trebuie sa verificam daca meniul este la fel in toate paginile html
        this.existingTitleFlag=this.menuHREFflag=this.footerHREFflag=this.imgTitleflag=this.socialMediaflag=0;
        
        
    }
    public void listi()
    {
        int i;
        
        for(i=0;i<this.CssCuloriFile.size();i++)
        {
            System.out.println(this.CssCuloriFile);
        }
    }
    public ArrayList<String> tok(String rules)
    {
        //System.out.println(rules);
        ArrayList<String> Rules=new ArrayList<String>();
        String delim = "\n";
       // StringTokenizer tok = new StringTokenizer(rules, delim, true);
        StringTokenizer stt = new StringTokenizer(rules,delim);
            while (stt.hasMoreTokens()){
                String token = stt.nextToken();
                if(token.endsWith("="))Rules.add(token.substring(0, token.length()-1));
                else Rules.add(token);
                //System.out.println(token.substring(0, token.length()-1));
            }
        return Rules;
    }
    
    public void countMenuHref()
    {
        Document dd= Jsoup.parse(this.fileContent);
        String s=new String();
        if(dd.getElementById("menu")!=null)s=dd.getElementById("menu").toString();
        int i;
        Pattern atr=Pattern.compile("href=");
        ArrayList<String> menuAL=tok(s);
        for(i=0;i<menuAL.size();i++)
        {
            Matcher line=atr.matcher(menuAL.get(i));
            while(line.find())
             {
                //System.out.println(line.group());
                this.menuHREFflag++;
              }
        }       
    }
    
    public void countFooterHref()
    {
        Document dd= Jsoup.parse(this.fileContent);
        String s=new String();int i;
        if(dd.getElementById("footer")!=null)s=dd.getElementById("footer").toString();
        
        Pattern atr=Pattern.compile("href=.*");
        ArrayList<String> footerAL=tok(s);
        for(i=0;i<footerAL.size();i++)
        {
            Matcher line=atr.matcher(footerAL.get(i));
            while(line.find())
             {
                //System.out.println(line.group());
                this.footerHREFflag++;
              }
        }
    }
    
    public void CheckTitleImg()
    {
        String s=doc.getElementsByTag("title").toString();
        if(doc.getElementsByTag("title").toString()!=null)this.existingTitleFlag=1;
        else this.existingTitleFlag=0;
        if(s.contains("img="))this.imgTitleflag=1;
        else this.imgTitleflag=0;
    }
    
    public void checkFormular()
    {
        String formm=new String();
        if(this.fileContent.contains("<form>"))
        {
            formm=this.doc.getElementById("form").toString();
             StringTokenizer stt = new StringTokenizer(formm,"input");
            while (stt.hasMoreTokens()){
                this.inputFlag++;
            }
            
            
        }
    }
    
    public void checkForDevices()
    {
        if(this.fileContent.contains("content=\"width=device-width, initial-scale=1\"")||this.fileContent.contains("content=\"width=device-width, initial-scale=1\""))
            this.devicesFlag=1;
    }
    public void checkForSearch()
    {
        if(this.fileContent.contains("<input type=\"text\" name=\"search\">"))
        {
            this.searchFlag=1;
        }
    }
    
    public void checkForFilter()
    {
        if(this.fileContent.contains("<select>"))
        {
            this.filterFlag=1;
        }
    }
    
    public void printErrors()
    {
        if(this.menuExisting==1)
        {
            if(this.menuHREFflag<9)System.err.println("Your menu doesn’t have so many options. You should add more. ");
            if(this.menuHREFflag>35)System.err.println("You menu have too many options. Try compress them to look more softly. ");
        }
        if(this.footerExisting==1)
        {
            if(this.footerHREFflag<7)System.err.println("Your footer should contain more shortcuts to facilitate acces to your services and not obligate the client to scroll up to move to other page. ");
            if(this.footerHREFflag>14)System.err.println("Your footer contain too more shortcuts. ");
        }
        if(this.filterFlag==0)System.err.println("You should have some filters for your webpage.");
        if(this.searchFlag==0)System.err.println("You shouldn't have a searchbar.");
        if(this.devicesFlag==0)System.err.println("You need to fix your website to can be opened from all types all divices.");
        if(this.inputFlag<2)System.err.println("It's possible that you will need more informations about your clients.We recommand you to have more boxes in your formulars.");
        if(this.inputFlag>4)System.err.println("You have too more boxes in your formular.");
        if(this.imgTitleflag==0)System.err.println("You should use an image for your site title to make your browser tab title looks more atracttive. ");
        if(this.socialMediaflag==0)System.err.println("You should have an least one social page. ");
        if(this.existingTitleFlag==0)System.err.println("You should have a title for your website. ");
        //System.out.println();
    }
    
}
