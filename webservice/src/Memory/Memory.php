<?php
/**
 * Memory.php
 * 
 * This file contains the memory class. The class contains the memory logic.
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version 1.0
 * @package memory
 * @subpackage classes
 */
 
class Memory
{
	const GAME_STATE_MOVE_INVALID = 0;
	const GAME_STATE_MOVE_VALID = 1;
	const GAME_STATE_WON = 2;

    /**
     * holds the memory game matrix
     * @access private
     */
	private $memory = array();
	
    /**
     * holds the solved pair identifier
     * @access private
     */
	private $solvedList = array();
	
	/**
     * holds the number of pairs
     * @access private
     */
	private $numberOfPairs = 0;
	
	/**
	 * Constructor
	 * @access public
	 * @author Simon Schneeberger
	 * @param int $rows 	- number of rows
	 * @param int $columns 	- number of columns
	 * @throws InvalidArgumentException if row or column are invalid
	 * @return void
	 */
	public function __construct( $rows = 0, $columns = 0 )
	{
	    if ( $rows < 0 or $columns < 0 or ( $rows * $columns ) % 2 != 0 )
		{
			throw new InvalidArgumentException("invalid argument in ." . __CLASS__ . ": " . __METHOD__ );
		}
		
		$counter = 0;
		$pairIdentifier = 0;
		$pairs = array();
		
		for ( $i = 0; $i < $rows * $columns; $i++ )
		{	
			if ( $counter % 2 == 0 )
			{
				$pairIdentifier++;
			}
			$counter++;
			$pairs[$i] = $pairIdentifier;
		}
		
		shuffle( $pairs );
		$this->numberOfPairs = count($pairs) / 2;
		$counter = 0;
		
		for( $row = 0; $row < $rows; $row++ )
		{
		  	for( $column = 0; $column < $columns; $column++ )
			{
				$this->memory[$row][$column] = $pairs[$counter];
				$counter++;
			}
		}
	}
	
	/**
	 * Destructor
	 * @access public
	 * @author Simon Schneeberger
	 */
	public function __destruct( )
	{
	    unset( $this->memory );
		unset( $this->solvedList );
	}
	
	/**
	 * Get the memory matrix
	 * @access public
	 * @author Simon Schneeberger
	 * @return array	-	memory
	 */
	public function getMemory( )
	{
	    return $this->memory;
	}
	
	/**
	 * This function handles a move and checks if the memory is solved.
	 * @access public
	 * @author Simon Schneeberger
	 * @param array $firstCard 		- first Card
	 * @param array $secondCard 	- second Card
	 * @return integer				- the state of the game
	 */
	public function move( $firstCardID, $secondCardID )
	{
		$result = self::GAME_STATE_MOVE_INVALID;
			
		$memoryFlatten = array();
		
		foreach ( new RecursiveIteratorIterator ( new RecursiveArrayIterator( $this->memory ) ) as $v )
		{
			$memoryFlatten[] = $v;
		}
		
		$memoryFlatten = array_unique($memoryFlatten);
		
		if ( in_array( $firstCardID, $memoryFlatten  ) and in_array( $secondCardID,  $memoryFlatten ) )
		{
			if ( $firstCardID === $secondCardID )
			{	
				$result = self::GAME_STATE_MOVE_VALID;			
			    $pairIdentifier = $firstCardID;
				
				if ( !in_array( $pairIdentifier, $this->solvedList ) )
				{
					$this->solvedList[] = $pairIdentifier;
				
					if ( count( $this->solvedList ) === $this->numberOfPairs )
					{
						$result = self::GAME_STATE_WON;
					}
				}
			}
		}
		
		return $result;
	}
}

?>