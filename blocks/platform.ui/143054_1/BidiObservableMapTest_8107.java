		withAndWithoutListeners(() -> {
			wrappedMap.put(key1, value1);
			assertEquals(Collections.singleton(key1), bidiMap.getKeys(value1));
			assertEquals(Collections.EMPTY_SET, bidiMap.getKeys(value2));
			wrappedMap.put(key1, value2);
			assertEquals(Collections.EMPTY_SET, bidiMap.getKeys(value1));
			assertEquals(Collections.singleton(key1), bidiMap.getKeys(value2));
