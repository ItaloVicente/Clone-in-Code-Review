		assertContainsDuringChangeEvent(new Runnable() {
			@Override
			public void run() {
				assertSame(element1, list.set(0, element2));
			}
		}, "List.set(int, Object)", list, element2);
