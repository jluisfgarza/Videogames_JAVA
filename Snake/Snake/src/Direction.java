
/**
 * The {@code Direction} enum is used to determine which way the Snake is moving.
 * @author Brendan Jones
 *
 */
public enum Direction {

	/**
	 * Moving North (Up).
	 */
	North (0),
	
	/**
	 * Moving East (Right).
	 */
	East (1),
	
	/**
	 * Moving South (Down).
	 */
	South (2),
	
	/**
	 * Moving West (Left).
	 */
	West (3);
	
        /**
        * Define a type of fruit                
        */
        private int idir;
	
         /**
     * Creates a new TileType.     
     * @param iTipo The idir of snake.
     */
    private Direction(int idir) {
        this.idir = idir;        
    }
        
    /**
    * Gets the direction
    *
    * @return idir
    */
    public int getdir() { 
        return idir;
        }
        
}


