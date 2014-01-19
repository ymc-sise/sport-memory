<?php
/**
 * create.php
 * 
 * This files handles receives json object from client and delivers result as a JSON object back to the client.
 * @author Simon Schneeberger <sischnee@gmail.com>
 * @version 1.0
 * @package Memory
 */
session_start(); 
 
include_once("Memory/Communicator.php");
include_once("Memory/Memory.php");

//read from json
$json = file_get_contents('php://input');

//$json = '{ "action": "create", "params": {"row": 4, "column":5 } }';
//$json = '{ "action": "move", "params": {"firstCard": 4, "secondCard":5 } }';

try
{
	$communicator = new Communicator( $json );
	$result = $communicator->handleJSON();
	
}
catch( Exception $e )
{
    $result = array( 'result' => 'error', 'report' => $e->getMessage() );
}

header('Content-type: application/json');
echo json_encode( $result );

?>