<?php
require_once 'PHPUnit/Framework/TestCase.php';
require_once 'src/Memory/Memory.php';
 
class MemoryTest extends PHPUnit_Framework_TestCase
{ 
    public static function suite()
    {
        return new MemoryTest('MemoryTest');
    }

    public function testMemoryHasValidArguments1()
	{
		try
		{
			$memory = new Memory(4, 5);
		}
		catch(InvalidArgumentException $e)
		{
			$this->fail();
		}
    }
	
	public function testMemoryHasInValidArguments2()
	{
		try
		{
			$memory = new Memory(-3, 5);
		}
		catch(InvalidArgumentException $e)
		{
			return;
		}
		$this->fail();
    }
	
	public function testMemoryHasInValidArguments3()
	{
		try
		{
			$memory = new Memory(3, 3);
		}
		catch(InvalidArgumentException $e)
		{
			return;
		}
		$this->fail();
    }
	
	public function testMemoryMove1()
	{
		$memory = new Memory(4, 4);
		$this->assertEquals(1, $memory->move(1,1) );
    }
	
	public function testMemoryMove2()
	{
		$memory = new Memory(4, 4);
		$this->assertEquals(0, $memory->move(2,1) );
    }
	
	public function testMemoryMove3()
	{
		$memory = new Memory(2, 2);
		$memory->move(1,1);
		$this->assertEquals(2, $memory->move(2,2) );
    }
	
}
?>