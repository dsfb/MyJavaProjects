package com.github.dsfb.java8xp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtils {
	public List<Integer> filterAtLeast(List<Integer> list, int minimun) {
		Stream<Integer> stream = list == null ? Stream.empty() : list.stream();
		return stream.filter(i -> i >= minimun).collect(Collectors.toList());
	}
}
