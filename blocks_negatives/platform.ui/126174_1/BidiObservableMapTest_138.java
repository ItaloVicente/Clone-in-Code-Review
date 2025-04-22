		withAndWithoutListeners(new Runnable() {
			@Override
			public void run() {
				assertEquals(Collections.EMPTY_SET, bidiMap.getKeys(value1));
			}
		});
