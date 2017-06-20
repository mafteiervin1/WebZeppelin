/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Ervin
 */
public class ComercialCheckCss {
    public String fileContent;
    public Document doc;
    public List<Rule> FileContent;
    public int colors;
    public int fonts;
    public int backgrounds;
    public int match[];
    public ArrayList<Atr> CuloriPatterns;
    public ArrayList<ArrayList<String>> FontPatterns;
    public List<Rule> CssCuloriFile;
    public List<Rule> CssFonturiFile;
    public int menuLeft;
    public int menuUp;
    public int menuDown;
    public int menuRight;
    
    public ComercialCheckCss(File CssFile) throws Exception
    {
        
        File comercialpath=new File("F:\\Facultate\\TW\\Proiect\\1\\patterns\\Comerciale");
        OpenFolder ComercialFolder=new OpenFolder(comercialpath);
        OpenFileContent culori=new OpenFileContent(ComercialFolder.getFile(comercialpath, 0));
        //culori.listAllContent();
        OpenFileContent fonturi=new OpenFileContent(ComercialFolder.getFile(comercialpath, 1));
        OpenFileContent cssFile=new OpenFileContent(CssFile);
        this.fileContent=cssFile.toString();
        this.FileContent=CSSParser.parse(this.fileContent);
        //System.out.println(culori.toString());
        //System.out.println(fonturi.toString());
        this.CssCuloriFile=CSSParser.parse(culori.toString());
        this.CssFonturiFile=CSSParser.parse(fonturi.toString());
        this.colors=0;
        this.fonts=0;
        this.backgrounds=0;
        this.menuLeft=this.menuRight=this.menuDown=this.menuUp=0;
        int i,j;
        this.match=new int[20];
        for(i=0;i<=19;i++)match[i]=0;
        this.CuloriPatterns=new ArrayList<Atr>();
        //Atr a=new Atr(0);
        for(i=0;i<CssCuloriFile.size();i++)
        {
            //ArrayList<Atr> atrb=new ArrayList<Atr>();
            Atr a=new Atr(0);
            for(j=0;j<CssCuloriFile.get(i).getPropertyValues().size();j++)
            {
                Atrr at=new Atrr(CssCuloriFile.get(i).getPropertyValues().get(j).getValue());
                a.atrb.add(at);
                //atrb.add(CssCuloriFile.get(i).getPropertyValues().get(j).getValue()); 
            }
            CuloriPatterns.add(a);
            //System.out.println(CuloriPatterns.get(i).atrb);
            //System.out.println();
        }
        
        this.FontPatterns=new ArrayList<ArrayList<String>>();
        for(i=0;i<CssFonturiFile.size();i++)
        {
            ArrayList<String> a=new ArrayList<String>();
            for(j=0;j<CssFonturiFile.get(i).getPropertyValues().size();j++)
            {
                a=tok(CssFonturiFile.get(i).getPropertyValues().get(j).getValue());
                //System.out.println(FontPatterns.get(j));
                FontPatterns.add(a);
                //System.out.println(FontPatterns.get(j).toString());
            }
            //FontPatterns.add(a);
            
            //System.out.println();
        }   
    }
    
    public void checkColors()
    {
        int i,j;
        int maxi=0,pos;
        int fl=0;
        for(i=0;i<this.CuloriPatterns.size();i++)
        {
            for(j=0;j<this.CuloriPatterns.get(i).atrb.size();j++)
            {
                //System.out.println(this.fileContent.contains(this.CuloriPatterns.get(i).atrb.get(j).name));
                if(this.fileContent.contains(this.CuloriPatterns.get(i).atrb.get(j).name))
                {
                    this.match[i]++;
                    this.CuloriPatterns.get(i).id=1;
                    this.CuloriPatterns.get(i).atrb.get(j).flag=1;
                    fl=1;
                }
            }
            if(this.match[i]>maxi)maxi=this.match[i];
        }
        
        if(fl==0)
        {
            System.err.println("We see that your colors dont match with any of our patterns.You can use for exemple this color pattern: ");
            for(j=0;j<this.CuloriPatterns.get(0).atrb.size();j++)
                    {
                        System.err.println(this.CuloriPatterns.get(0).atrb.get(j).name);
                    }
        }
        else
        {
        for(i=0;i<=19;i++)
        {
            if(this.match[i]==maxi)
            {
                fl=1;
                System.out.println("Your site match with a pattern!");
                if(this.CuloriPatterns.get(i).id==1)
                {
                    for(j=0;j<this.CuloriPatterns.get(i).atrb.size();j++)
                    {
                        if(this.CuloriPatterns.get(i).atrb.get(j).flag==1)
                            System.out.println("You use this color from the pattern:"+ this.CuloriPatterns.get(i).atrb.get(j).name);
                    }
                    System.out.println("Additionaly,you should use this colors to fully match with this pattern:");
                    for(j=0;j<this.CuloriPatterns.get(i).atrb.size();j++)
                    {
                        if(this.CuloriPatterns.get(i).atrb.get(j).flag==0)
                            System.out.println(this.CuloriPatterns.get(i).atrb.get(j).name);
                    }
                    System.out.println();
                }
            }
        }
        
        }
    }
    
    public ArrayList<String> tok(String rules)
    {
        //System.out.println(rules);
        ArrayList<String> Rules=new ArrayList<String>();
        String delim = ",";
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
    
    public void checkFont()
    {
        int i,j,k;
        int fl=0;int pos=0;
        for(i=0;i<=19;i++)match[i]=0;
        for(i=0;i<this.FontPatterns.size();i++)
        {
            for(j=0;j<this.FileContent.size();j++)
            {
                //System.out.println(this.FileContent.get(j).getPropertyValues().toString());
            if(this.FileContent.get(j).getPropertyValues().toString().contains("font-family"))
            {
                
                //System.out.println(this.FileContent.get(j).getSelectors().toString().indexOf("font-family")+" "+this.FileContent.get(j).getSelectors().get(pos).toString());
                for(k=0;k<this.FileContent.get(j).getPropertyValues().size();k++)
                {//System.out.println(this.FileContent.get(j).getPropertyValues().get(k).getProperty().contains("font-family"));
                    if(this.FileContent.get(j).getPropertyValues().get(k).getProperty().contains("font-family"))
                        {
                            fl=1;
                            if(this.FileContent.get(j).getPropertyValues().get(k).getValue().contains(this.FontPatterns.get(i).get(1)))
                            {
                                 match[i]++;
                            }
                            if(this.FileContent.get(j).getPropertyValues().get(k).getValue().contains(this.FontPatterns.get(i).get(0)))
                            {
                                match[i]++;
                            }
                        }
                }
                
               
            }
            }
        }
        int fl1=0;
        if(fl==0)
        {
            System.out.println("Unfortunately you don't use for your website any font family from top 20 font family that are used for your site type. We Recomand you to use one of this:\n");
            for(j=0;j<5;j++)
                System.out.println(this.FontPatterns.get(j+j).get(0)+" "+this.FontPatterns.get(j+j).get(1));
        }
        
        else
            
        {
            for(i=0;i<=19;i++)
            {
                if(match[i]==1)fl1=1;
            }
            if(fl1==0)
            {
                for(i=0;i<=19;i++)
                {
                    if(match[i]==2)
                    {
                        System.out.println("Congratulations!! You Chose verry well your fonts family for your website.\n"+this.FontPatterns.get(i).get(0) +" and " +this.FontPatterns.get(i).get(1)+" are a verry good combination for your website.");
                        break;
                    }
                }
            }
            else
            {
            for(i=0;i<=19;i++)
                if(match[i]==1)
                {
                System.out.println("You use the generic family "+ this.FontPatterns.get(i).get(1) + " but the top sites use "+this.FontPatterns.get(i).get(0)+" from this generic family for define the font-family. ");
                }
                    }
        }
        }
    
    
    public void checkMenu()
    {
        int ok=0;int i;int k;
        if(this.fileContent.toLowerCase().contains("nav")||this.fileContent.toLowerCase().contains("navigation")||this.fileContent.toLowerCase().contains("menu")||this.fileContent.toLowerCase().contains("navigationbar")||this.fileContent.toLowerCase().contains("navbar")||this.fileContent.toLowerCase().contains("nav_wrapper")||this.fileContent.toLowerCase().contains("navigation")||this.fileContent.toLowerCase().contains("Menu_bar"))ok=1;
        if(ok==1)
        {
            for(i=0;i<this.FileContent.size();i++)
            {
                //System.out.println(this.FileContent.get(i).getPropertyValues().get(0).getValue().substring(0, 2));
                if(this.FileContent.get(i).getSelectors().toString().contains("nav")||this.FileContent.get(i).getSelectors().toString().contains("navigationbar")||this.FileContent.get(i).getSelectors().toString().contains("menu")||this.FileContent.get(i).getSelectors().toString().contains("navigation")||this.FileContent.get(i).getSelectors().toString().contains("navbar")||this.FileContent.get(i).getSelectors().toString().contains("nav_wrapper")||this.FileContent.get(i).getSelectors().toString().contains("navigation")||this.FileContent.get(i).getSelectors().toString().contains("Menu_bar"))
                {
                    if(this.FileContent.get(i).getSelectors().toString().contains("width:"))
                    {
                        for(k=0;k<this.FileContent.get(i).getPropertyValues().size();k++)
                        if(Integer.parseInt(this.FileContent.get(i).getPropertyValues().get(k).getValue().substring(0, 2))<=30)this.menuLeft=1;
                    }
                    else if(this.FileContent.get(i).getSelectors().toString().contains("margin-right:"))
                    {
                        for(k=0;k<this.FileContent.get(i).getPropertyValues().size();k++)
                        if(Integer.parseInt(this.FileContent.get(i).getPropertyValues().get(k).getValue().substring(0, 2))>=75)this.menuRight=1;
                    }
                    else if(this.FileContent.get(i).getSelectors().toString().contains("margin-top:"))
                    {
                        for(k=0;k<this.FileContent.get(i).getPropertyValues().size();k++)
                        if(Integer.parseInt(this.FileContent.get(i).getPropertyValues().get(k).getValue().substring(0, 2))>=80)this.menuDown=1;
                    }
                    else this.menuUp=1;
                }
            }
            if(this.menuUp==0)
            {
                System.err.println("Your menu should be in the top of page.");
            }
        }
    }
    
    
}

