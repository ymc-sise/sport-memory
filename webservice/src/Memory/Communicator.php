<?php
/**
 * Communicator.php
 * 
 * This file contains the communicator class. The class reads json and sends a json back.
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version 1.0
 * @package memory
 * @subpackage classes
 */
 
class Communicator
{
   	/**
     * holds the json object which comes from the client
     * @access private
     */
	private $jsonObject = false;

	/**
     * Constructor of Communicator
     * @access public
	 * @throws InvalidArgumentException if jsonObject is invalid
	 * @return void
     */
	public function __construct( $jsonObject )
	{	
		if ( is_object( json_decode( $jsonObject ) ) )
		{
			$this->jsonObject = json_decode($jsonObject);
		}
		else
		{
			throw new InvalidArgumentException("invalid argument in " . __CLASS__ . ": " . __METHOD__ );
		}
	}
	
	/**
	 * Destructor
	 * @access public
	 */
	public function __destruct( )
	{
	    unset( $this->jsonObject );
	}
	
	/**
	 * Handles the JSON object which comes from the client.
	 * @access public
	 * @throws BadFunctionCallException if action method is not existing
	 * @return array
	 */
	public function handleJSON()
	{
		$action = (string) $this->jsonObject->{'action'};
		if ( in_array( $action, array( 'create', 'move' ) ) )
		{
			return $this->{$action}();
		}
		else
		{
			throw new BadFunctionCallException("invalid method call of ". $action ." in " . __CLASS__ . ": " . __METHOD__ );
		}
	}

	/**
	 * Creates a new memory instance
	 * @access private
	 * @return array - the memory matrix
	 */
	private function create()
	{
		$memory = new Memory( $this->jsonObject->{'params'}->{'row'}, $this->jsonObject->{'params'}->{'column'} );
		$memoryMatrix = $memory->getMemory();
		$_SESSION['memory'] = serialize($memory);
	
		return array( 'result' => $memoryMatrix );
	}
	
	/**
	 * Handles a game move.
	 * @access private
	 * @throws BadFunctionCallException if memory object is invalid
	 * @return array - the move result
	 */
	private function move()
	{
		$memory = unserialize($_SESSION['memory']);

		//session could be empty		
		if ( $memory instanceOf Memory )
		{
			$result = $memory->move( (int)$this->jsonObject->{'params'}->{'firstCard'}, (int)$this->jsonObject->{'params'}->{'secondCard'} );
			$_SESSION['memory'] = serialize($memory);
			return array( 'result' => array($result) );
		}
		else
		{
			throw new BadFunctionCallException("invalid memory object in " . __CLASS__ . ": " . __METHOD__ );
		}
	}
}