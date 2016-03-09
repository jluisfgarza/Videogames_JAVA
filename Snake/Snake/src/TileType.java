
/**
 * The {@code TileType} class represents the different
 * types of tiles that can be displayed on the screen.
 * @author Brendan Jones
 *
 */
public enum TileType {

	Fruit (0),
	
	SnakeHead (1),
	
	SnakeBody (2),
        
        Badfruit (3), 
        
        Fruit1 (4), 
        
        Fruit2 (5);
                        
        /**
        * Define a type of fruit                
        */
        private int iTipo;
	
         /**
     * Creates a new TileType.     
     * @param iTipo The tipe of fruit.
     */
    private TileType(int iTipo) {
        this.iTipo = iTipo;        
    }
        
    /**
    * Gets the tipo
    *
    * @return itipo
    */
    public int getTipo() { 
        return iTipo;
        }

}

