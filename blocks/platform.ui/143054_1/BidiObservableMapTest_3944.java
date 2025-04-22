		withAndWithoutListeners(() -> {
			wrappedMap.put(key1, value1);
			wrappedMap.put(key2, value1);

			Set expected = new HashSet();
			expected.add(key1);
			expected.add(key2);
			assertEquals(expected, bidiMap.getKeys(value1));
