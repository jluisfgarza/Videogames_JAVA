
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Malos extends Base{
    
    
    
    private int Inteli; //creamos variable para revisar nivel de maldad
    //velocidades de lo malos
    private int iVel;  

    public Malos(int iX, int iY, Image imaImagen, int Inteli, int iVel) {
        super(iX, iY, imaImagen);
        this.Inteli = Inteli;
        this.iVel = iVel;
        
    }
    
    public void MalosCaen(int iVidas, Base basPrincipal){
        
        switch (iVidas){
            case 5: iVel = 2;
                break;
            case 4: iVel = 3;
                break;
            case 3: iVel = 4;
                break;
            case 2: iVel = 5;
                break;
            case 1: iVel = 6;
                break;
        }
        
        if (Inteli == 0){
            this.setY(this.getY() + iVel);
        }
        else{
            if (basPrincipal.getX() > this.getX()){
                this.setX(this.getX() + iVel);        
            }
            if (basPrincipal.getX() < this.getX()){
                this.setX(this.getX() - iVel);
            }            
            this.setY(this.getY() + iVel);             
        }
        
    }
    
    /**
     * getInteli
     * 
     * Metodo modificador usado para cambiar la velocidad
     * 
     */
    public int getInteli(){        
            return Inteli;
    }
    
}
