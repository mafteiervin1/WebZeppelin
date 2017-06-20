/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Ervin
 */
public class HtmlCheck {
    public ArrayList<String> FileContent;
    public String fileContent;
    public int menuHREFflag;
    public int footerHREFflag;
    public int imgTitleflag;
    public int socialMediaflag;
    public String menuModel;
    public Document doc;
    
    public HtmlCheck(File file)
    {
        OpenFileContent ofc=new OpenFileContent(file); 
        this.fileContent=ofc.toString();
        this.doc=Jsoup.parse(fileContent);
        this.FileContent=ofc.fileContent;
        this.menuHREFflag=0;this.footerHREFflag=0;this.imgTitleflag=0;
        this.menuModel=(doc.getElementById("menu")).toString();
        this.doc=Jsoup.parse(this.fileContent);//trebuie sa verificam daca meniul este la fel in toate paginile html
    }
    
    public void countMenuHref()
    {
        Document dd= Jsoup.parse(this.fileContent);
        String s=dd.getElementById("menu").toString();
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
        String s=dd.getElementById("footer").toString();
        int i;
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
    
    public double CheckforPattern(ArrayList<String> pattern)
    {
        int i=0,j=0;
        int bothtags=0;
        int lastpos,flag;
        double score;
        while(j<pattern.size())
        {
            lastpos=i;flag=0;
            while(!this.FileContent.get(i).contains(pattern.get(j).trim())&&i<this.FileContent.size()-1)
            {
                i++;if(this.FileContent.get(i).contains(pattern.get(j).trim()))flag=1;
            }
            if(flag==1)bothtags++;
            if(i==this.FileContent.size()-1)i=lastpos;
            j++;
        }
        if(bothtags==0)return 0;
        score=100.0*bothtags/(pattern.size()-1);
        return score;
    }
    
    public void CheckTitleImg()
    {
        String s=doc.getElementsByTag("title").toString();
        if(s.contains("img="))this.imgTitleflag=1;
        else this.imgTitleflag=0;
    }
    
    public void countSocialMediaPages()
    {
        
        String body=doc.body().toString();
        String head=doc.head().toString();
        if(body.contains("href=\"https://www.facebook.com")||head.contains("href=\"https://www.facebook.com"))this.socialMediaflag++;
        if(body.contains("href=\"https://www.twitter.com")||head.contains("href=\"https://www.twitter.com"))this.socialMediaflag++;
        if(body.contains("href=\"https://www.instagram.com")||head.contains("href=\"https://www.instagram.com"))this.socialMediaflag++;
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
    
    public void printErrors()
    {
        if(this.menuHREFflag<=2)System.err.println("Your menu doesn’t have so many options. You should add more. ");
        if(this.menuHREFflag>9)System.err.println("You menu have too many options. Try compress them to look more softly. ");
        if(this.footerHREFflag!=this.menuHREFflag)System.err.println("Your footer should contain more shortcuts to facilitate acces to your services and not obligate the client to scroll up to move to other page. ");
        if(this.imgTitleflag==0)System.err.println("You should use an image for your site title to make your browser tab title looks more atracttive. ");
        if(this.socialMediaflag==0)System.err.println("You should have an least one social page. ");
        //System.out.println();
    }
    
}
