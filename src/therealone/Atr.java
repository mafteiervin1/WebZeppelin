/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package therealone;

import java.util.ArrayList;

/**
 *
 * @author Ervin
 */
public class Atr {
    public int id;
    public ArrayList<Atrr> atrb;
    
    public Atr(int Id)
    {
        this.id=Id;
        this.atrb=new ArrayList<Atrr>();
    }
}
