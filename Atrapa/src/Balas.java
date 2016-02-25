
import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Balas extends Base{

    
    private int cTipo;                 //creamos variable para leer dirección
    private int iVel;                   //variable para velocidad de cada bala
    
    
    /**
     * Balas
     * 
     * Metodo constructor usado para crear el objeto bala
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param cTipo es la <code>direccion</code> de la bala
     * @param iVel es la <code>velocidad</code> del objeto
     */
    public Balas(int iX, int iY, Image imaImagen, int cTipo, int iVel) {
        super(iX, iY, imaImagen);
        this.cTipo = cTipo;
        this.iVel = iVel;
        
    }
    
    public void Avanza(){
        if (this.cTipo == 2){
            this.setY(this.getY() - this.iVel);
        }
        if (this.cTipo == 3){
            this.setY(this.getY() - this.iVel);
            this.setX(this.getX() + this.iVel);
        }
        if (this.cTipo == 1){
            this.setY(this.getY() - this.iVel);
            this.setX(this.getX() - this.iVel);
            
        }
        
    }
    
    /**
     * gettipo
     * 
     * regresa el tipo de bala
     * 
     */
    public int getTipo() {
        return cTipo;
    }
    
    /**
     * get Velocidad
     * 
     * regresa el tipo de bala
     * 
     */
    public int getVel() {
        return iVel;
    }
    
}
