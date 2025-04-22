package org.eclipse.jgit.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.Test;

public class TransIteratorTest {

	@Test
	public void transformation() {
		List<String> source = Arrays.asList("a"
		TransIterator<String
				source
		assertThat(it.next()
		assertThat(it.next()
		assertThat(it.next()
		assertThat(it.next()
		assertFalse(it.hasNext());
	}

	@Test
	public void failedTransformation() {
		List<String> source = Arrays.asList("a"
		TransIterator<String
				str -> null);
		assertFalse(it.hasNext());
	}

	@Test
	public void failedSomeTransformation() {
		List<String> source = Arrays.asList("a"
		TransIterator<String
				new FailAfter(2));
		assertTrue(it.hasNext());
		assertThat(it.next()
		assertTrue(it.hasNext());
		assertThat(it.next()
		assertFalse(it.hasNext());
	}

	@Test
	public void hasNext() {
		List<String> source = Arrays.asList("a"
		TransIterator<String
				source
		int expectedLength = 1;
		while (it.hasNext()) {
			assertThat(it.next()
			expectedLength += 1;
		}
	}

	@Test
	public void isLazy() {
		Counter transformAndCount = new Counter();
		List<String> source = Arrays.asList("aaaa"
		TransIterator<String
				source
		assertThat(it.next()
		assertThat(transformAndCount.getInvocationCounter()
	}

	private static class Counter implements Function<String

		private int invocationCounter = 0;

		@Override
		public Integer apply(String t) {
			invocationCounter += 1;
			return t.length();
		}

		public int getInvocationCounter() {
			return invocationCounter;
		}
	}

	private static class FailAfter implements Function<String

		private int failAfter;

		FailAfter(int n) {
			this.failAfter = n;
		}

		@Override
		public Integer apply(String t) {
			this.failAfter -= 1;
			if (failAfter < 0) {
				return null;
			}

			return t.length();
		}
	}

}
