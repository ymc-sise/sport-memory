<?php
require_once 'PHPUnit/Framework/TestCase.php';
require_once 'src/Memory/Communicator.php';
 
class CommunicatorTest extends PHPUnit_Framework_TestCase
{ 
    public static function suite()
    {
        return new CommunicatorTest('CommunicatorTest');
    }

    public function testCommunicatorHasValidArguments1()
	{
		try
		{
		    $json = '{ "action": "create", "params": {"row": 4, "column": 5 } }';
			$memory = new Communicator($json);
		}
		catch(InvalidArgumentException $e)
		{
			$this->fail();
		}
    }
	
	public function testCommunicatorHasValidArguments2()
	{
		try
		{
		    $json = -1;
			$memory = new Communicator($json);
		}
		catch(InvalidArgumentException $e)
		{
			return;
		}
		$this->fail();
    }
	
	public function testCommunicatorCallMethod1()
	{
		try
		{
		    $json = '{ "action": "create", "params": {"row": 4, "column": 5 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
		}
		catch(InvalidArgumentException $e)
		{
			$this->fail();
		}
    }
	
	public function testCommunicatorCallMethod2()
	{
		try
		{
		    $json = '{ "action": "INVALIDMETHODCALL", "params": {"row": 4, "column": 5 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
		}
		catch(BadFunctionCallException $e)
		{
			return;
		}
		$this->fail();
    }
	
	public function testCommunicatorCallMethodCreate1()
	{
		try
		{			
		    $json = '{ "action": "create", "params": {"row": -1, "column": 5 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
			
			session_destroy();
		}
		catch(Exception $e)
		{
			return;
		}
		$this->fail();
    }
	
	public function testCommunicatorCallMethodCreate2()
	{
		try
		{			
		    $json = '{ "action": "create", "params": {"row": 4, "column": 5 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
					}
		catch(Exception $e)
		{
			$this->fail();
		}
    }
	
	public function testCommunicatorCallMethodMove1()
	{
		try
		{			
		    $json = '{ "action": "create", "params": {"row": 4, "column": 5 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
			
			$json = '{ "action": "move", "params": {"firstCard": 1, "secondCard": 1 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
		}
		catch(Exception $e)
		{
			echo $e->getMessage();
			$this->fail();
		}
    }
	
	public function testCommunicatorCallMethodMove2()
	{
		try
		{
			$json = '{ "action": "move", "params": {"firstCard": 1, "secondCard": 1 } }';
			$memory = new Communicator($json);
			$memory->handleJSON();
		}
		catch(Exception $e)
		{
			return;
		}
		
		$this->fail();
    }
}
?>