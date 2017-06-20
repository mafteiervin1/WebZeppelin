/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

//import com.sun.xml.internal.ws.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.osbcp.cssparser.CSSParser;
import com.osbcp.cssparser.Rule;
import java.util.List;
/**
 *
 * @author Ervin
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;
public class TheRealOne {

    public static void main(String[] args) throws IOException, Exception {
        File F=new File("F:\\Facultate\\TW\\Proiect\\1\\test"); 
        OpenFolder a=new OpenFolder(F);
        File FF=a.getFile(F, 0);
        int ok=1;
        //File FFF=a.getLastFileUploaded(F);
        OpenFileContent d=new OpenFileContent(FF);
        //HtmlCheck h=new HtmlCheck(FF);
        //System.out.println(a.getFilesNumber(F));
        Document dd;
        dd = Jsoup.parse(d.toString());
        File testpath=new File("F:\\Facultate\\TW\\Proiect\\1\\test"); 
        File patternspath=new File("F:\\Facultate\\TW\\Proiect\\1\\patterns");
        OpenFolder testFolder=new OpenFolder(testpath);
        //File comercial=testFolder.openSpecificFolder(testpath, "Comercial");
        OpenFolder patternsFolder=new OpenFolder(patternspath);
        //CC.listi();
        
        int i;
        testFolder.CheckForExtension(testpath);
        //System.out.println(testFolder.filescounter);
        for(i=0;i<=testFolder.filescounter;i++)
        {
            ComercialCheck CC;
            if(testFolder.getFileName(testpath, i).contains(".html"))
            {
                CC=new ComercialCheck(testFolder.getFile(testpath, i));
                CC.CheckTitleImg();
                CC.countFooterHref();
                CC.countMenuHref();
                CC.checkFormular();
                System.out.println(testFolder.getFileName(testpath, i).toString());
                CC.printErrors();
            }
            else
            {
                ComercialCheckCss CCC=new ComercialCheckCss(testFolder.getFile(testpath, i));
                //CCC.checkColors();
                //CCC.checkFont();
                CCC.checkMenu();
            }
        }
        
        //for(i=0;i<rules.size();i++)System.out.println(rules.get(i).getPropertyValues()+" "+rules.get(i).getSelectors());
        //System.out.println(dd);
        /*File testpath=new File("F:\\Facultate\\TW\\Proiect\\1\\test"); 
        File patternspath=new File("F:\\Facultate\\TW\\Proiect\\1\\patterns");
        OpenFolder testFolder=new OpenFolder(testpath);
        //File comercial=testFolder.openSpecificFolder(testpath, "Comercial");
        OpenFolder patternsFolder=new OpenFolder(patternspath);
        //System.out.println(testFolder.getLastFileUploadedName(testpath));
        int i,j;
        //HtmlCheck h=new HtmlCheck(testFile);
        while(ok==1)
        {
            
            for(i=0;i<testFolder.filescounter;i++)
            {
                File testFile=testFolder.getFile(testpath, i);
                HtmlCheck hc=new HtmlCheck(testFile);
                OpenFileContent pt;
                pt = new OpenFileContent(patternsFolder.getFile(patternspath, 0));
                hc.CheckTitleImg();
                hc.CheckforPattern(pt.fileContent);
                hc.countFooterHref();
                hc.countMenuHref();
                hc.countSocialMediaPages();
                System.out.println(hc.CheckforPattern(pt.fileContent)+ " "+testFolder.getFileName(testpath, i));
                hc.printErrors();  
            }
            
            ok=0;
        }
        
        CssCheck c=new CssCheck(testFolder.getFile(testpath, 3),patternsFolder.getFile(patternspath, 1));
        List<Rule> rules = CSSParser.parse(c.fileContent);*/
        //c.cssColorCheck(patternspath);
       // for(i=0;i<rules.size();i++)System.out.println(rules.get(i).getPropertyValues()+" "+rules.get(i).getSelectors());//parsez cu asta cssul lui si vad daca se afla in .getRules prorpietatile astea
        /*
        HtmlCheck h=new HtmlCheck(FF);
        //System.out.println(a.getFilesNumber(F));
        Document dd;
        dd = Jsoup.parse(d.toString());
        //System.out.println(dd.getElementById("menu"));
        h.countMenuHref();
        h.countFooterHref();
        h.CheckTitleImg();
        h.countSocialMediaPages();
        h.printErrors();
        
        //System.out.println(dd.getElementsByTag("form"));
        System.out.println(StringUtils.countOccurrencesOf(dd.getElementsByTag("form").toString(),"input"));
*/
       
        //StringUtils.countOccurrencesOf();
        //System.out.println(h.fileContent.contains("href=\"https://www.facebook.com"));
        //System.out.println(h.menuHREFflag);
        //System.out.println(h.footerHREFflag);
        //System.out.println(dd.getElementById("footer").toString());   //vad daca are elementul span in body,si returneaza null daca nu are----pentru patternuri
        //d.listAllContent();
        
        
        
    }   
    }
