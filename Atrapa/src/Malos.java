
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Malos extends Base{
    
    
    
    private boolean bHiperM; //creamos variable para revisar nivel de maldad
    //velocidades de lo malos
    private int iVel;  

    public Malos(int iX, int iY, Image imaImagen, boolean bHiperM, int iVel) {
        super(iX, iY, imaImagen);
        this.bHiperM = bHiperM;
        this.iVel = iVel;
        
    }
    
    public void MalosCaen(int iVidas, Base basPrincipal){
        
        switch (iVidas){
            case 5: iVel = -2;
                break;
            case 4: iVel = -3;
                break;
            case 3: iVel = -4;
                break;
            case 2: iVel = -5;
                break;
            case 1: iVel = -6;
                break;
        }
        
        if (!this.bHiperM){
            this.setY(this.getY()- iVel);
        }
        else{
            if (basPrincipal.getX() > this.getX()){
                this.setX(this.getX() + iVel);        
            }
            if (basPrincipal.getX() < this.getX()){
                this.setX(this.getX() + iVel);
            }
            if (basPrincipal.getY() > this.getY()){ 
                this.setY(this.getY() + iVel);
            } 
            if (basPrincipal.getY() < this.getY()){
                this.setY(this.getY() + iVel);
            }
        }
        
    }
    
    /**
     * setVel
     * 
     * Metodo modificador usado para cambiar la velocidad
     * 
     */
    public void setVelX(){
        
    }
    
    /**
     * setVel
     * 
     * Metodo de acceso usado para obtener la velocidad
     * 
     */
    public void getVelX(){
        
    }
    /**
     * setVelY
     * 
     * Metodo modificador usado para cambiar la velocidad
     * 
     */
    public void setVelY(){
        
    }
    /**
     * getVelY
     * 
     * Metodo de acceso usado para cambiar la velocidad
     * 
     */
    public void getVelY(){
        
    }
    
    
}
