		withAndWithoutListeners(new Runnable() {
			@Override
			public void run() {
				wrappedMap.put(null, value1);
				assertEquals(Collections.singleton(null), bidiMap
						.getKeys(value1));
			}
