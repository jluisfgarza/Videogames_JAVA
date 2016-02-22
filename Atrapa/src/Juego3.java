
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.net.URL;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;


/**
 * @author Juan Luis Flores
 * @version 2.0
 * @date 2/2/2016
 */
 
    public class Juego3 extends Applet implements Runnable, KeyListener, 
            MouseListener, MouseMotionListener{

    private Base basPlaneta;            // Objeto planeta
    private LinkedList<Base> lklMalos;  //creo que la lista para los malos    
    private AudioClip aChoque;          //Objeto de sonido para la colision 
                                        //del planeta con los asteroides 
    private int iVidas;                 //Vidas del planeta
    private Image imaGameOver;          //Imagen en caso de perder
   
    //Variables de velocidad
    private int iAumenX;                // Incremento en x
    private int iAumenY;                // Incremento en y  
    
    //Posicion del mouse
    private int iPosX;                  //posicion x del mouse
    private int iPosY;                  //posicion y del mous   
    
    //Variables de imagen
    private Image imaImagenFondo;       // para dibujar la imagen de fondo
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen    
    
    //Variables offset para desface del mouse con la imagen.
    private int iOffX;                  //offset eje x
    private int iOffY;                  //offset eje y
    private boolean bDragged;           //boleana check drag
    private int iScore;                 //Score del planeta
    private int iContAste;              //contador de asteroidesa tocan borde
                                        //inferior. 
    private AudioClip bChoque;          //Objeto de sonido para la colision
                                        //asteroides con borde inferior

    
   
    
    	
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public void init() {
        // asigna tamaño del applet
        setSize(1000,600);
        lklMalos = new LinkedList<Base>();
        int iRandom = (int)(Math.random() * 4)+7;
        iVidas = 5;             //Asignamos la cantidad de vidas del planeta
        iScore = 0;             //Asignamos el score del planeta a 0
        iContAste = 0;          //contador de asteroides que tocan suelo
        bDragged = false;
        //inicializo los ajustes de desface de coordenadas del mouse
        iOffX = 0;
        iOffY = 0;
                 
        // defino la imagen principal
	URL urlImagenPrincipal = this.getClass().getResource("Cannon.png");
		
        // Creo la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("fondo.png");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
               
        // Creo el objeto para principal 
	basPlaneta = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));
               
        // posiciono a principal  en posición aleatoria 
        iPosX = (int)(Math.random() * (getWidth() / 4));
        iPosY = (int)(Math.random() * (getWidth() / 4));
        basPlaneta.setX(iPosX);
        basPlaneta.setY(iPosY);      
        
        // Define el sonido que se reproducirá
        //Sonido choque asteroide con la tierra
        URL eaURL = this.getClass().getResource("Explosion.wav");
        aChoque = getAudioClip (eaURL);
        //sonido choque asteroide con el borde inferios
        URL eaURL2 = this.getClass().getResource("error.wav");
        bChoque = getAudioClip (eaURL2);        
        //Se crea la imagen del asteroide
        URL urlImagenMalo = this.getClass().getResource("piedra.gif");
        //creo a los malos 
        for (int iI = 0; iI < iRandom; iI++){
            //creo a un malo
            Base basMalo = new Base(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenMalo));
            //lo añado a la lista
            lklMalos.add(basMalo);
        }
        
        //ESTE METODO NO ES NECESARIO EN ESTE JUEGO. 
        //posiciono al asteroide en posicion aleatoria
        /*basAsteroide.setX((int)(Math.random()*(getWidth()-
                basAsteroide.getAncho())));
        basAsteroide.setY((int)(Math.random()*(getHeight()-
                basAsteroide.getAlto())));*/
        
        //imagen al finalizar el juego
        URL urlGameOver = this.getClass().getResource("gameover.jpg");
        imaGameOver = Toolkit.getDefaultToolkit().getImage(urlGameOver);
        
        //Se añaden los escuchadores
        addKeyListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        
        //Posiciono los objetos en el applet con la llamada a este metodo. 
        reposiciona();
    }
    
   /*
    * reposiciona
    *
    *
    *reposiciona el principal y el malo 
    *
    */
    public void reposiciona(){
       //Posiciono el planeta en la parte inferior del applet
        iPosX = (int)(Math.random() * (getWidth() / 4));
        iPosY = (1000/2);
        basPlaneta.setX(iPosX);
        basPlaneta.setY(iPosY);
        
        //reposiciona a cada malo de la lista en la parte superior del applet
        for (Base basMalo : lklMalos){
            basMalo.setX((int)(Math.random() * (getWidth() -
                basMalo.getAncho())));
            basMalo.setY((int)(Math.random() * (getHeight() -
                basMalo.getAlto()))-600);
        }
        
    }
    
    
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrÃ¡ las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        while (true && iVidas >0) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El hilo del juego se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
		}
        
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion de los objetos 
     * 
     */
    public void actualiza(){
        //para actualizar posicion de la imagen principal al hacer click
        if (bDragged)
        {
            basPlaneta.setX(iPosX - iOffX);
            basPlaneta.setY(iPosY - iOffY);
        }
        
        //actualizo la velocidad del asteroide segun las vidas
        if (iVidas == 5)
        {
            iAumenX = 1;
            iAumenY = 1;
        }
        if (iVidas == 3)
        {
            iAumenX = 4;
            iAumenY = 4;
        }
        if (iVidas == 2)
        {
            iAumenX = 6;
            iAumenY = 6;
        }        
        //se asigna una vleocidad nueva a la caida de asteroides
        for (Base basMalo : lklMalos){
          basMalo.setY(basMalo.getY()+iAumenY);
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos
     * 
     */
    public void checaColision(){
        //revisa que el planeta no pueda salir del marco
        if (basPlaneta.getX() <= 0) {
            basPlaneta.setX(0);
        } 
        if (basPlaneta.getX() + basPlaneta.getAncho() >= getWidth() ) {
            basPlaneta.setX(getWidth() - basPlaneta.getAncho());
        }
        //limita el movimiento en el eje y a solo 600 del a altura del applet
        if (basPlaneta.getY() <= 0){
            basPlaneta.setY(0);
        }
        if (basPlaneta.getY() + basPlaneta.getAlto() >= getHeight()){
            basPlaneta.setY(getHeight() - basPlaneta.getAlto());
        }
        
        for (Base basMalo : lklMalos){
        //Se revisa colisión del asteroide con los bordes
            if (basMalo.getX() + basMalo.getAncho() > getWidth()) {
                basMalo.setX(basMalo.getX() - iAumenX);
            }                                   
            if (basMalo.getX() < 0) {
                basMalo.setX(basMalo.getX() - iAumenX);
            }          
            
           // colision del asteroide con el borde inferior            
            if (basMalo.getY() + basMalo.getAlto() > getHeight()) {
               basMalo.setY(basMalo.getY() - iAumenY);
               iScore-=20;
               //si colisionan mas de 10 se pierde una vida 
               if (iContAste > 10)
               {
                   iVidas--;
                   //reset para el contador de colisiones
                   iContAste = 0;
               }
               
               if (iContAste <= 10)               
               {
                   //contar las colisiones 
                   iContAste++;
               }
                              
                bChoque.play();
                //al colisiona un objeto malo se reposiciona en la parte
                //superior del applet y se le da una nueva posicion aleatoria 
                //en el eje x
                basMalo.setY(basMalo.getY()-600);
                basMalo.setX((int)(Math.random() * (getWidth() -
                basMalo.getAncho())));
            }
           
           //colision superior
           /*
            if (basMalo.getY() < 0) {
                basMalo.setX(basMalo.getY() - iAumenY);
            }*/
        }
        
        for (Base basMalo : lklMalos){
        //reviso colision entre bueno y malo 
            if (basPlaneta.colisiona(basMalo) ){                
                iScore+= 100;
                aChoque.play();
                //al colisiona un objeto malo se reposiciona en la parte
                //superior del applet y se le da una nueva posicion aleatoria 
                //en el eje x
                basMalo.setY(basMalo.getY()-600);
                basMalo.setX((int)(Math.random() * (getWidth() -
                basMalo.getAncho())));
            }          
        }
    }
    
    /**
     * update
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void update (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("fondo.png");
        Image imaImagenFondo = 
                Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
         graGraficaApplet.drawImage(imaImagenFondo, 0, 0, getWidth(),
                 getHeight(), this);
         
         

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
        
            
    }
    
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado paradibujar.
     * 
     */
    public void paint(Graphics graDibujo) {
        
        if (iVidas > 0){
        // si la imagen ya se cargo
            if (basPlaneta != null && imaImagenFondo != null && 
                lklMalos != null) {
        	    // Dibuja la imagen de fondo
        	    graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(), 
                            getHeight(), this);
                //Dibuja la imagen de principal en el Applet
                basPlaneta.paint(graDibujo, this);
                //dibujo al malo
                for(Base basMalo : lklMalos){
                    basMalo.paint(graDibujo, this);
                }
                graDibujo.setFont(new Font("Arial",Font.BOLD,20));
                //Se dibuja el texto con la vidas y el score del juego
                graDibujo.setColor(Color.white);
                graDibujo.drawString("Tienes " + iVidas + " vidas", 30, 30);
                graDibujo.drawString("Score: " + iScore , 220, 30);
            } // sino se ha cargado se dibuja un mensaje 
            else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
            }        
        }
        else{
            //dibujo la imagen de fin de juego
         graDibujo.drawImage(imaGameOver, 0, 0, this);
        }
    }


    @Override
    public void mousePressed(MouseEvent mouEvent) {
        //Se guardan las coordenadas del mouse.
        iPosX= mouEvent.getX();
        iPosY = mouEvent.getY();        
    }

    @Override
    public void mouseReleased(MouseEvent mouEvent) 
    {
        bDragged = false;
    }


    @Override
    public void mouseDragged(MouseEvent mouEvent) {
        //En caso de no mover le mouse y hacer click al personaje, se 
        //actualiza el offset.
        if (!bDragged && basPlaneta.colisiona(iPosX, iPosY)){
            iOffX = mouEvent.getX() - basPlaneta.getX();
            iOffY = mouEvent.getY() - basPlaneta.getY();
            bDragged = true;
        }
        //actualizo las posiciones del mouse al arrastrar
        iPosX= mouEvent.getX();
        iPosY = mouEvent.getY();
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}