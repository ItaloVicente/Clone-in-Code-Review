		withAndWithoutListeners(new Runnable() {
			@Override
			public void run() {
				assertFalse(bidiMap.containsValue(value1));
				wrappedMap.put(key1, value1);
				assertTrue(bidiMap.containsValue(value1));
				wrappedMap.put(key2, value1);
				assertTrue(bidiMap.containsValue(value1));
				wrappedMap.remove(key1);
				assertTrue(bidiMap.containsValue(value1));
				wrappedMap.remove(key2);
				assertFalse(bidiMap.containsValue(value1));
			}
