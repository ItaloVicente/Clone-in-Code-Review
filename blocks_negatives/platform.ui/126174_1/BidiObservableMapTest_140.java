		withAndWithoutListeners(new Runnable() {
			@Override
			public void run() {
				wrappedMap.put(key1, null);
				assertEquals(Collections.singleton(key1), bidiMap.getKeys(null));
			}
