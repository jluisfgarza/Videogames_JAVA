/*
Autores:
Version:
Fecha:
*/

//Librerias
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JFrame;


public class Juego3 extends JFrame implements Runnable, KeyListener {
    /* Defino tamaño del applet */
    private static final int WIDTH = 1000;      //Ancho del JFrame
    private static final int HEIGHT = 600;      //Alto del JFrame

    private Base basPrincipal;                  // Objeto principal
    private LinkedList<Base> lklMalos;          //Linked list malos    
    private Image imaImagenFondo;               // para dibujar la imagen de fondo
    
    /* objetos para manejar el buffer del Applet y que la imagen no parpadee */
    private Image    imaImagenApplet;       // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;      // Objeto grafico de la Imagen
    private AudioClip sonidoPain;           // Sonido de colision con malos    
    
    /* variables para controlar movimiento */
    private int iDireccion;                 // Direccion de principal
    
    /* variable e imagen para el control de vidas */
    private int iVidas;
    private int iContColisionMalo;
    private Image imaGameOver;
    private Image imaCora;
    
    /* variable para el control de puntos */
    private int iPuntos;
    
    private Vector vecPuntaje;         // Objeto vector para agregar el puntaje.
    private String sNombreArchivo;     //Nombre del archivo.
    private String[] arrArchivo;       //Arreglo del archivo divido.
    
    //Guarde el numero de malos 
    private int iRandMalos;
    private int iVeloMalos;             //velocidad de los malos
    
    private boolean bpausa = false;     //verificador estado de pausa. 

    public Juego3(){
        sNombreArchivo = "Puntaje.txt"; // Inicializamos con el nombre del arch
        vecPuntaje = new Vector();      // creamos el vector
        
        // Inicializamos el contador con 5 vidas
        iVidas = 5;
        
        // Inicializamos el contador de las colisiones de los malos
        iContColisionMalo = 0;
        iPuntos = 0;                    // Inicializamos los puntos en 0
                
        addKeyListener(this);           // Añadir KeyListener       
        inicializoPrincipal();          // inicializo principal        
        inicializoMalos();              // inicializo malos 
        inicializoSonidos();            // inicializo sonidos        
        inicializoImagenes();           // inicializo imagenes  
        Thread th = new Thread (this);  // Declaras un hilo        
        th.start ();                    // Empieza el hilo
    }
    
    /** 
     * inicializoImagenes
     * 
     * Metodo que inicializa las imagenes de Fondo y Game Over
     * 
     */
    public void inicializoImagenes() {
        // Creo la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("fondo.png");
        imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        
        // Creo la imagen del Game Over
        URL urlImagenGameOver = this.getClass().getResource("gameover.jpg");
        imaGameOver = Toolkit.getDefaultToolkit().getImage(urlImagenGameOver);
        
        // Creo la imagen de vidas
        URL urlImagenCora = this.getClass().getResource("Heart.png");
        imaCora = Toolkit.getDefaultToolkit().getImage(urlImagenCora);
    
    }    
    
    
    /** 
     * inicializoSonidos
     * 
     * Metodo que inicializa los sonidos
     * 
     */
    public void inicializoSonidos() {        
        // Creo el sonido de vida menos
        URL eaURL2 = Juego3.class.getResource("Explosion.wav");
        sonidoPain = Applet.newAudioClip(eaURL2);
    }
    
    /** 
     * inicializoPrincipal
     * 
     * Metodo que inicializa a Principal
     * 
     */
    public void inicializoPrincipal() {
        // Defino la imagen de principal.
        URL urlImagenPrincipal = this.getClass().getResource("earth.gif");
        
        // Creo el objeto principal 
	basPrincipal = new Base(0, 0, 
                Toolkit.getDefaultToolkit().getImage(urlImagenPrincipal));

        // Posiciono al objeto principal
        basPrincipal.setX((getWidth() - basPrincipal.getAncho()) / 2);
        basPrincipal.setY((getHeight() - basPrincipal.getAlto()));
    }
                               
    
    /** 
     * inicializoMalos
     * 
     * Metodo que inicializa a todos los malos
     * 
     */
    public void inicializoMalos() {
        /* creo la lista de los malos */
        lklMalos = new LinkedList<Base>();
        iVeloMalos = 3;
        /* genero el random de los malos entre 8 y 10 */
        iRandMalos = (int)(Math.random() * 3) + 8;
        int iRanMalos = iRandMalos;
        
        // Defino la de los malos.
	URL urlImagenMalos = this.getClass().getResource("piedra.gif");
        
        // Creo a los malos
        for(int iI = 0; iI < iRanMalos; iI++){
            // Creo a un malo
            Base basMalo = new Base (0, 0, 
                Toolkit.getDefaultToolkit().getImage(urlImagenMalos));
            // Añado al malo a la lista
            lklMalos.add(basMalo);
        }
        
        // Posiciono a los malos
        for (Base basMalo : lklMalos){
            basMalo.setX((int)(Math.random() * (getWidth() - basMalo.getAncho())));
            basMalo.setY((int)(Math.random() * (getHeight() - basMalo.getAlto()))-500);
        }
    }
    
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Juego3 jfrmJuego = new Juego3();
        jfrmJuego.setSize(WIDTH,HEIGHT);
        jfrmJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrmJuego.setVisible(true);
    }

    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run() {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        while (iVidas > 0) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El hilo del juego se duerme.
                Thread.sleep (40);
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

        // Mueve a principal dependiendo de la dirección
        switch (iDireccion) {
            case 1:
                basPrincipal.setX(basPrincipal.getX() - 6);
                break;
            case 2:
                basPrincipal.setX(basPrincipal.getX() + 6);
                break;            
            default:
                break;
        }        
        actualizaMalos(); // actualizamos buenos y malos
    }
    
    /** 
     * actualizaBuenosyMalos
     * 
     * Metodo que actualiza la posicion de los malos 
     * 
     */
    public void actualizaMalos() {
        for (Base basMalo : lklMalos){
            // Se actualiza la posicion del bueno
            basMalo.setY(basMalo.getY() + iVeloMalos); 
        }
    }

    /**
     * checaColision
     * 
     * Metodo usado para checar la colision entre objetos.
     * 
     */
    public void checaColision(){
        
        // Checa que el principal no salga del marco
        if (basPrincipal.getX() <= 0) {
            basPrincipal.setX(0);
        } 
        if (basPrincipal.getX() + basPrincipal.getAncho() >= getWidth() ) {
            basPrincipal.setX(getWidth() - basPrincipal.getAncho());
        }
        if (basPrincipal.getY() <= 0){
            basPrincipal.setY(0);
        }
        if (basPrincipal.getY() + basPrincipal.getAlto() >= getHeight()){
            basPrincipal.setY(getHeight() - basPrincipal.getAlto());
        }
        
        // Llamamos a la funcion para checar las colisiones de los malos
        checaColisionMalos();
    }
    
    /**
     * checaColisionMalos
     * 
     * Metodo usado para checar la colision de los malos.
     * 
     */
    public void checaColisionMalos(){
        for (Base basMalo : lklMalos) {
            // Checa si algun malo llego hasta abajo
            if (basMalo.getY() >= 500 +basMalo.getAlto()) {
                // Se reposiciona malo
                basMalo.setX((int)(Math.random() * (getWidth() - 
                        basMalo.getAncho())));
                basMalo.setY(basMalo.getY()-600);
            }

            // Checar si malo colisiona con el principal
            if (basPrincipal.colisiona(basMalo)){
                iContColisionMalo++; // Sumar una colision al contador
                //Actualiza el score -1
                iPuntos --;
                // Se reposiciona el malo
                basMalo.setX((int)(Math.random() * (getWidth() -
                        basMalo.getAncho())));
                basMalo.setY(basMalo.getY()-600);
                
                // Checamos si ya van 5 colisiones
                if (iContColisionMalo == 5) {
                    iVidas--; // Quitamos una vida
                    iVeloMalos += 2; // aumenta la velocidad de los malos
                    iContColisionMalo = 0; // Se pone el contador  en 0                    
                    sonidoPain.play();// Suena efecto cuando se pierde una vida
                }
            }  
        }
    }

    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>JFrame</code>,
     * heredado de la clase Window.<P>
     * En este metodo lo que hace es actualizar la ventana y 
     * define cuando usar ahora el paint1
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        URL urlImagenFondo = this.getClass().getResource("fondo.png");
        Image imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        graGraficaApplet.drawImage(imaImagenFondo, 0, 0, getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }
    
    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paint1(Graphics graDibujo) {
        if (iVidas > 0) {
            // si la imagen ya se cargo
            if (basPrincipal != null && imaImagenFondo != null && 
                    lklMalos != null) {
                // llamamos funcion que dibuja el juego
                dibujarJuego(graDibujo);
            } // si no se ha cargado se dibuja un mensaje 
            else {
                // Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 50);
            }
        } else if (iVidas ==0) {
            // dibujo la imagen de fin de juego
            graDibujo.drawImage(imaGameOver, 0, 0, getWidth(), getHeight(), this);          
        } 
    }
    
    /**
     * dibujarJuego
     * 
     * En este metodo se dibuja la imagen del juego.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void dibujarJuego(Graphics graDibujo){
        // Dibuja la imagen de fondo
        graDibujo.drawImage(imaImagenFondo, 0, 0, getWidth(),getHeight(), this);
        //Dibuja la imagen de principal en el Applet
        basPrincipal.paint(graDibujo, this);
        // Dibujo al malo
        for (Base basMalo : lklMalos){
            basMalo.paint(graDibujo, this);
        }        
        // Dibujamos el texto con las vidas y el puntaje
        graDibujo.setColor(Color.white);
        graDibujo.fillRect(0,15,1000,40);
        graDibujo.drawRect(0,15,1000,40);
        graDibujo.setFont(new Font("Arial",Font.BOLD,14));
        graDibujo.setColor(Color.black);
        graDibujo.drawImage(imaCora,10,40,this);
        graDibujo.drawString("    x  "+iVidas+ "      Puntos: " + iPuntos , 10, 50);        
        if (bpausa)
        {
            graDibujo.setFont(new Font("Arial",Font.BOLD,40));
            graDibujo.setColor(Color.white);
            graDibujo.drawString("PAUSADO" , getWidth()/2-60, getHeight()/2);
        }
                

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        /* Dependiendo de si el usuario da click en la flecha izq o derecha se 
        le asigna una direccion a Principal */
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
            iDireccion = 1;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
            iDireccion = 2;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_P){
            iDireccion = 0;
            if (bpausa)
            {
               iVeloMalos = 3;
               bpausa = false;
            }
            else
            {   
                iVeloMalos = 0;
                bpausa = true;
            }
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_R){            
            
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_A){            
           
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_S){            
            
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE){            
            
        }
         else if (keyEvent.getKeyCode() == KeyEvent.VK_G){
            
            // pide el nombre de usuario                
                try {
                grabaArchivo();
            } 
            catch(IOException e) {
            }
         }
         else if (keyEvent.getKeyCode() == KeyEvent.VK_C){
             try {
                leeArchivo();    //lee el contenido del archivo             
             }
             catch(IOException e){                
             }        
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // Se cambia la dirección de Principal a 0 para que no se mueva
        iDireccion = 0;
    }
    
    /**
     * getWidth
     * 
     * Metodo de acceso que regresa el ancho de la pantalla
     * 
     * @return un <code>entero</code> que es el ancho de la pantalla.
     * 
     */
    public int getWidth(){
        return WIDTH;
    }
    
    /**
     * getHeight
     * 
     * Metodo de acceso que regresa la altura de la pantalla
     * 
     * @return un <code>entero</code> que es la altura de la pantalla.
     * 
     */
    public int getHeight(){
        return HEIGHT;
    }
    
    /**
     * leerArchivo
     * 
     * Se encarga de leer el contenido
     * 
     */
    public void leeArchivo() throws IOException {
                                                       
        BufferedReader fileIn;
        
        try {
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        } catch (FileNotFoundException e){
            File flePuntos = new File(sNombreArchivo);
            PrintWriter fileOut = new PrintWriter(flePuntos);
            fileOut.close();
            fileIn = new BufferedReader(new FileReader(sNombreArchivo));
        }
        
        String sDato = fileIn.readLine(); 
        if (sDato != null)
        {
            iVidas = Integer.parseInt(sDato);
            sDato = fileIn.readLine();
            iPuntos = Integer.parseInt(sDato);
            basPrincipal.setX(Integer.parseInt(fileIn.readLine()));
            basPrincipal.setY(Integer.parseInt(fileIn.readLine()));
                               
            iRandMalos = Integer.parseInt(fileIn.readLine());
            lklMalos.clear();
            // Creo a los malos
            URL urlImagenMalos = this.getClass().getResource("piedra.gif");
            for(int iI = 0; iI < iRandMalos; iI++){
            // Creo a un malo
            Base basMalo = new Base (Integer.parseInt(fileIn.readLine()), Integer.parseInt(fileIn.readLine()), 
                Toolkit.getDefaultToolkit().getImage(urlImagenMalos));
            // Añado al malo a la lista
            lklMalos.add(basMalo);
            }
        }              
        fileIn.close();                
               
    }
    /**
     * grabaArchivo 
     * 
     * Su función es guardar el contenido
     * 
     */
    public void grabaArchivo() throws IOException {
                                                          
        PrintWriter fpw = new PrintWriter(new FileWriter(sNombreArchivo));                       
        fpw.println(iVidas);
        fpw.println(iPuntos);
        fpw.println(basPrincipal.getX());
        fpw.println(basPrincipal.getY());
        fpw.println(iRandMalos);
        for(Base basMalo : lklMalos) {
            fpw.println(basMalo.getX());
            fpw.println(basMalo.getY());
        }
        fpw.close();
    }
    
    
}
