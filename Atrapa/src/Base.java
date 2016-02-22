/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author Juan Luis Flores
 * @version V1.5
 * @date 2/2/16
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Base {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private int iAncho; //ancho del objeto
    private int iAlto; //largo del objeto
    private Image imaImagen;	//imagen.
    private ImageIcon imiImagen;  // imagen con medidas

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param iAncho es el <code>ancho</code> del objeto.
     * @param iAlto es el <code>Largo</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Base(int iX, int iY ,  Image imaImagen ) {
        this.iX = iX;
        this.iY = iY;
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth()/2;
        this.iAlto = this.imiImagen.getIconHeight()/2;
    }

    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imaImagen = imaImagen;
        this.imiImagen = new ImageIcon(imaImagen);
        this.iAncho = this.imiImagen.getIconWidth();
        this.iAlto = this.imiImagen.getIconHeight();
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imaImagen;
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho de la imagen.
     * 
     */
    public int getAncho() {
        return iAncho;
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto de la imagen.
     * 
     */
    public int getAlto() {
        return iAlto;
    }
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), getAncho(), getAlto(), imoObserver);
    }

    /**
     * equals
     * 
     * Metodo para checar igualdad con otro objeto
     * 
     * @param objObjeto    objeto de la clase <code>Object</code> para comparar
     * @return un valor <code>boleano</code> que sera verdadero si el objeto
     *   que invoca es igual al objeto recibido como parámetro
     * 
     */
    public boolean equals(Object objObjeto) {
        // si el objeto parametro es una instancia de la clase Base
        if (objObjeto instanceof Base) {
            // se regresa la comparación entre este objeto que invoca y el
            // objeto recibido como parametro
            Base basParam = (Base) objObjeto;
            return this.getX() ==  basParam.getX() && 
                    this.getY() == basParam.getY() &&
                    this.getAncho() == basParam.getAncho() &&
                    this.getAlto() == basParam.getAlto() &&
                    this.getImagen() == basParam.getImagen();
        }
        else {
            // se regresa un falso porque el objeto recibido no es tipo Base
            return false;
        }
    }

    /**
     * toString
     * 
     * Metodo para obtener la interfaz del objeto
     * 
      * @return un valor <code>String</code> que representa al objeto
     * 
     */
    public String toString() {
        return " x: " + this.getX() + " y: "+ this.getY() +
                " ancho: " + this.getAncho() + " alto: " + this.getAlto();
    }
    
    /**
     *Colisiona
     *
     *Checa la colision con un objeto base
     *
     * @param obj objeto de la clase <code>Object</code>
     * @return boleano <code> true</code> si colisiona <code> false
     *      </code> si no colisiona
     *
    */

    
    
    public boolean colisiona(Object obj){
        //checo si el objeto es de la clase base
        if(obj instanceof Base){
           //Se crea un rectangulo personalizado para el objeto para logar que
           //colisione solo la parte superior dle mismo y no de sus lados. 
           Rectangle recEste = new Rectangle(getX()+70, getY()-1, 10, 3);
           //Se revisa colision del segundo objeto con dicho rectangulo
           //este rectangulo tambien está modificado para ocupar la parte
           //inferior del objeto malo.
           Rectangle recOtro = new Rectangle(((Base) obj).getX(),
                   ((Base) obj).getY()+50, 100, 
                   3);
           return recEste.intersects(recOtro);
       }
        else {
            return false;
        }
    
}
    
    /**
     *Colisiona
     *
     *Checa la colision con un objeto base
     *
     * @param iX valor <code>integer</code> que representa la x a revisar
     * @param iY valor <code>integer</code> que representa la y a revisar
     * @return boleano <code> true</code> si colisiona <code> false
     *      </code> si no colisiona
     *
    */
    
    public boolean colisiona(int iX, int iY){
        //checo si el objeto es de la clase base
       
           Rectangle recEste = new Rectangle(getX(), getY(), getAncho(), 
                   getAlto());
           return recEste.contains(iX, iY);
        
    
}
}
