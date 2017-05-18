package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import code.Inventory;

public class InventoryTests {

	@Test
	public void test() {
		Inventory myInventory = new Inventory();
		int size = 72;
		int actual = myInventory.getSize();
		assertTrue("The size I got was " + actual, actual == size);
	}

}
