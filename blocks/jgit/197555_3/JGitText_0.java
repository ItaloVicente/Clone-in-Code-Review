package org.eclipse.jgit.internal.storage.memory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;

public class TernarySearchTreeTest {

	private TernarySearchTree<String> tree;

	@Before
	public void setup() {
		tree = new TernarySearchTree<>();
		tree.insert("foo"
		tree.insert("bar"
		tree.insert("foobar"
		tree.insert("foobaz"
		tree.insert("johndoe"
	}

	@Test
	public void testInsert() {
		tree.insert("foobarbaz"
		assertEquals(6
		assertEquals("6"
	}

	@Test
	public void testBatchInsert() {
		Map<String
		m.put("refs/heads/master"
		m.put("refs/heads/stable-1.0"
		m.put("refs/heads/stable-2.1"
		m.put("refs/heads/stable-2.0"
		m.put("refs/heads/stable-1.1"
		m.put("refs/heads/stable-2.2"
		m.put("aaa"
		m.put("refs/tags/xyz"
		m.put("refs/tags/v1.1"
		m.put("refs/tags/v2.2"
		m.put("refs/tags/v1.0"
		m.put("refs/tags/v2.1"
		m.put("refs/tags/v2.0"
		tree.insert(m);
		assertArrayEquals(
				new String[] { "refs/heads/master"
						"refs/heads/stable-1.1"
						"refs/heads/stable-2.1"
				toArray(tree.getKeysWithPrefix("refs/heads/")));
		assertArrayEquals(
				new String[] { "refs/tags/v1.0"
						"refs/tags/v2.0"
						"refs/tags/xyz" }
				toArray(tree.getKeysWithPrefix("refs/tags/")));
		assertEquals("aaa"
	}

	@Test
	public void testInsertWithNullKey() {
		Exception exception = assertThrows(IllegalArgumentException.class
				() -> {
					tree.insert(null
				});
		assertTrue(exception.getMessage()
				.contains("TernarySearchTree key must not be null or empty"));
	}

	@Test
	public void testOverwriteValue() {
		tree.insert("foo"
		assertEquals(5
		assertEquals("overwritten"
	}

	@Test
	public void testInsertNullValue() {
		Exception exception = assertThrows(IllegalArgumentException.class
				() -> {
					tree.insert("xxx"
				});
		assertTrue(exception.getMessage()
				.contains("cannot insert null value into TernarySearchTree"));
	}

	@Test
	public void testReloadAll() {
		Map<String
		map.put("foo"
		map.put("bar"
		tree.replace(map.entrySet());
		assertArrayEquals(new String[] { "bar"
				toArray(tree.getKeys()));
	}

	@Test
	public void testReload() {
		Map<String
		map.put("foo"
		map.put("bar"
		tree.reload(map.entrySet());
		assertEquals("foo-value"
		assertEquals("bar-value"
		assertEquals("3"
		assertEquals("4"
		assertEquals("5"
		assertEquals(5
	}

	@Test
	public void testContains() {
		assertTrue(tree.contains("foo"));
		assertTrue(tree.contains("foobaz"));
		assertFalse(tree.contains("ship"));
	}

	@Test
	public void testContainsWithNullKey() {
		Exception exception = assertThrows(IllegalArgumentException.class
				() -> {
					tree.contains(null);
				});
		assertTrue(exception.getMessage()
				.contains("TernarySearchTree key must not be null or empty"));
	}

	@Test
	public void testGet() {
		assertEquals("1"
		assertEquals("5"
		assertNull(tree.get("ship"));
	}

	@Test
	public void testGetWithNullKey() {
		Exception exception = assertThrows(IllegalArgumentException.class
				() -> {
					tree.get(null);
				});
		assertTrue(exception.getMessage()
				.contains("TernarySearchTree key must not be null or empty"));
	}

	@Test
	public void testDelete() {
		tree.delete("foo");
		assertNull(tree.get("foo"));
		assertEquals(4
		tree.delete("cake");
		assertEquals(4
	}

	@Test
	public void testDeleteWithNullKey() {
		Exception exception = assertThrows(IllegalArgumentException.class
				() -> {
					tree.delete((String) null);
				});
		assertTrue(exception.getMessage()
				.contains("TernarySearchTree key must not be null or empty"));
	}

	@Test
	public void testDeleteMultiple() {
		tree.delete(Arrays.asList(new String[] { "foobar"
		assertArrayEquals(new String[] { "bar"
				toArray(tree.getKeys()));
	}

	@Test
	public void testClear() {
		assertEquals(5
		tree.clear();
		assertEquals(0
		tree.getKeys().forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				throw new IllegalStateException("should find no key");
			}
		});
	}

	@Test
	public void testKeyLongestPrefix() {
		assertEquals("foobar"
		assertEquals("foo"
		assertEquals(""
		assertEquals("johndoe"
		assertEquals(""
		assertNull(tree.keyLongestPrefixOf(""));
		assertNull(tree.keyLongestPrefixOf(null));
	}

	@Test
	public void testGetKeys() {
		assertArrayEquals(
				new String[] { "bar"
				toArray(tree.getKeys()));
	}

	@Test
	public void testGetKeysWithPrefix() {
		assertArrayEquals(new String[] { "foo"
				toArray(tree.getKeysWithPrefix("foo")));
		assertArrayEquals(new String[] { "johndoe" }
				toArray(tree.getKeysWithPrefix("john")));
		assertArrayEquals(new String[0]
				toArray(tree.getKeysWithPrefix("cake")));
		assertArrayEquals(
				new String[] { "bar"
				toArray(tree.getKeysWithPrefix("")));
		assertArrayEquals(new String[0]
	}

	@Test
	public void testGetWithPrefixFoo() {
		Map<String
		assertEquals(3
		assertEquals("1"
		assertEquals("3"
		assertEquals("4"
	}

	@Test
	public void testGetWithPrefixNotFound() {
		Map<String
		assertEquals(0
	}

	@Test
	public void testGetWithPrefixNull() {
		Map<String
		assertEquals(0
	}

	@Test
	public void testGetWithPrefixEmptyPrefix() {
		Map<String
		assertEquals(5
		assertEquals("1"
		assertEquals("2"
		assertEquals("3"
		assertEquals("4"
		assertEquals("5"
	}

	@Test
	public void testGetValuesWithPrefixFoo() {
		List<String> result = tree.getValuesWithPrefix("foo");
		assertEquals(3
		assertArrayEquals(new String[] { "1"
				result.toArray());
	}

	@Test
	public void testGetValuesWithPrefixNotFound() {
		List<String> result = tree.getValuesWithPrefix("cheese");
		assertEquals(0
	}

	@Test
	public void testGetValuesWithPrefixNull() {
		List<String> result = tree.getValuesWithPrefix(null);
		assertEquals(0
	}

	@Test
	public void testGetValuesWithPrefixEmptyPrefix() {
		List<String> result = tree.getValuesWithPrefix("");
		assertEquals(5
		assertArrayEquals(new String[] { "2"
				result.toArray());
	}

	@Test
	public void testGetKeysMatching() {
		assertArrayEquals(new String[] { "foobar" }
				toArray(tree.getKeysMatching("fo?bar")));
		assertArrayEquals(new String[] { "foobar"
				toArray(tree.getKeysMatching("fooba?")));
		assertArrayEquals(new String[] { "foobar"
				toArray(tree.getKeysMatching("?o?ba?")));
		assertArrayEquals(new String[0]
		assertArrayEquals(new String[0]
	}

	private static String[] toArray(Iterable<String> iter) {
		List<String> keys = StreamSupport.stream(iter.spliterator()
				.collect(Collectors.toList());
		return keys.toArray(new String[0]);
	}
}
