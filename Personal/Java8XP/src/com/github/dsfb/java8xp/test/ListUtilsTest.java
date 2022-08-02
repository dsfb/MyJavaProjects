package com.github.dsfb.java8xp.test;

import com.github.dsfb.java8xp.ListUtils;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ListUtilsTest {

	@Test
	public void firstTest() {
		ListUtils utils = new ListUtils();

		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			data.add(i);
		}

		List<Integer> result = utils.filterAtLeast(data, 36);

		assertEquals(result.size(), 14);
		int counter = 0;
		for (int i = 36; i < 50; i++) {
			assertTrue(result.contains(i));
			counter++;
		}

		assertEquals(counter, 14);
	}

	@Test
	public void secondTest() {
		ListUtils utils = new ListUtils();

		List<Integer> data = null;

		List<Integer> result = utils.filterAtLeast(data, 36);

		assertEquals(result.size(), 0);
		int counter = 0;
		for (int i = 36; i < 50; i++) {
			assertFalse(result.contains(i));
			if (result.contains(i)) {
				counter++;
			}
		}

		assertEquals(counter, 0);
	}
}
