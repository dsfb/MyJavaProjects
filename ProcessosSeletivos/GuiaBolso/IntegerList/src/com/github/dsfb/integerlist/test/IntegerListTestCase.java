package com.github.dsfb.integerlist.test;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.github.dsfb.integerlist.ListMain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

public class IntegerListTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSuccessForModernMax() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(0);
		lista.add(-1);
		lista.add(2);
		ListMain lm = new ListMain();
		int actual = lm.getModernMax(lista);
		int expected = 2;
		assertEquals(actual, expected);
	}

	@Test(expected = RuntimeException.class)
	public void testNullForModernMax() {
		List<Integer> lista = null;
		ListMain lm = new ListMain();
		lm.getModernMax(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyForModernMax() {
		List<Integer> lista = new ArrayList<Integer>();
		assertTrue(lista.isEmpty());
		ListMain lm = new ListMain();
		lm.getModernMax(lista);
	}

	@Test
	public void testEqualModernMax() {
		ListMain lm = new ListMain();
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(-1);
		lista.add(-2);
		int classicMax = lm.getModernMax(lista);
		int newMax = lm.getMax(lista);
		assertEquals(classicMax, newMax);
	}
	
	@Test
	public void testSuccessForMax() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(0);
		lista.add(-1);
		lista.add(2);
		ListMain lm = new ListMain();
		int actual = lm.getMax(lista);
		int expected = 2;
		assertEquals(actual, expected);
	}

	@Test(expected = RuntimeException.class)
	public void testNullForMax() {
		List<Integer> lista = null;
		ListMain lm = new ListMain();
		lm.getMax(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyForMax() {
		List<Integer> lista = new ArrayList<Integer>();
		assertTrue(lista.isEmpty());
		ListMain lm = new ListMain();
		lm.getMax(lista);
	}

	@Test
	public void testEqualMax() {
		ListMain lm = new ListMain();
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(1);
		lista.add(2);
		lista.add(3);
		lista.add(-1);
		lista.add(-2);
		int classicMax = lm.getClassicMax(lista);
		int newMax = lm.getMax(lista);
		assertEquals(classicMax, newMax);
	}

	@Test(expected = RuntimeException.class)
	public void testNullForClassicMax() {
		List<Integer> lista = null;
		ListMain lm = new ListMain();
		lm.getClassicMax(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyForClassicMax() {
		List<Integer> lista = new ArrayList<Integer>();
		assertTrue(lista.isEmpty());
		ListMain lm = new ListMain();
		lm.getClassicMax(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testNullForArithmeticAvg() {
		List<Integer> lista = null;
		ListMain lm = new ListMain();
		lm.getArithmeticAvg(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyForArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		assertTrue(lista.isEmpty());
		ListMain lm = new ListMain();
		lm.getArithmeticAvg(lista);
	}

	@Test
	public void testIntegerArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(5);
		lista.add(5);
		lista.add(5);
		ListMain lm = new ListMain();
		double actual = lm.getArithmeticAvg(lista);
		double expected = 5;
		assertEquals(actual, expected, 0.1);
	}

	@Test
	public void testFloatingArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(5);
		lista.add(6);
		lista.add(8);
		ListMain lm = new ListMain();
		double actual = lm.getArithmeticAvg(lista);
		double expected = 6.333d;
		assertEquals(actual, expected, 0.3);
	}
	
	@Test(expected = RuntimeException.class)
	public void testNullForModernArithmeticAvg() {
		List<Integer> lista = null;
		ListMain lm = new ListMain();
		lm.getModernArithmeticAvg(lista);
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyForModernArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		assertTrue(lista.isEmpty());
		ListMain lm = new ListMain();
		lm.getModernArithmeticAvg(lista);
	}

	@Test
	public void testIntegerModernArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(5);
		lista.add(5);
		lista.add(5);
		ListMain lm = new ListMain();
		double actual = lm.getModernArithmeticAvg(lista);
		double expected = 5;
		assertEquals(actual, expected, 0.1);
	}

	@Test
	public void testFloatingModernArithmeticAvg() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(5);
		lista.add(6);
		lista.add(8);
		ListMain lm = new ListMain();
		double actual = lm.getModernArithmeticAvg(lista);
		double expected = 6.333d;
		assertEquals(actual, expected, 0.3);
	}
}
