package org.eclipse.jgit.junit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class CartesianProduct {

	public static Set<Set<Object>> compute(Set<?>... sets) {
		if (sets == null) {
			return Collections.emptySet();
		}
		return Arrays.stream(sets)
				.filter(set -> set != null && set.size() > 0)
				.map(set -> set.stream().map(Set::<Object> of)
						.collect(Collectors.toSet()))
				.reduce((set1
						.flatMap(inner1 -> set2.stream()
								.map(inner2 -> Stream.of(inner1
										.flatMap(Set::stream)
										.collect(Collectors.toCollection(
												LinkedHashSet::new))))
						.collect(Collectors.toCollection(LinkedHashSet::new)))
				.orElse(Collections.emptySet());
	}

	public static Stream<Arguments> toArgumentStream(
			Set<Set<Object>> setOfSets) {
		return setOfSets.stream().map(Arguments::arguments);
	}

	public static <T> Set<T> toSet(T[] list) {
		return new HashSet<>(Arrays.asList(list));
	}

	public static <T> Set<T> toSet(Collection<T> collection) {
		return new HashSet<>(collection);
	}
}
