		withAndWithoutListeners(new Runnable() {
			@Override
			public void run() {
				wrappedMap.put(key1, value1);
				assertEquals(Collections.singleton(key1), bidiMap
						.getKeys(value1));
			}
