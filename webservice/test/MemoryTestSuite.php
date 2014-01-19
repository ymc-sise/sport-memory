<?php

if (!defined('PHPUnit_MAIN_METHOD')) {
  define( 'PHPUnit_MAIN_METHOD', 'MemoryAllTests::main' );
}
 
require_once 'PHPUnit/Framework/TestSuite.php';
//require_once 'PHPUnit/TextUI/TestRunner.php';
 
require_once 'memory/MemoryTest.php';
require_once 'memory/CommunicatorTest.php';
 
class MemoryAllTests
{
	public static function main()
	{
		PHPUnit2_TextUI_TestRunner::run(self::suite());
	}
 
	public static function suite()
	{
		$suite = new PHPUnit_Framework_TestSuite( 'MemoryTest' );
 
        //add more tests to the suite
		$suite->addTestSuite(new PHPUnit_Framework_TestSuite( 'CommunicatorTest' ));
 
		return $suite;
	}
}
 
if (PHPUnit_MAIN_METHOD == 'MemoryAllTests::main')
{
	MemoryAllTests::main();
}

?>