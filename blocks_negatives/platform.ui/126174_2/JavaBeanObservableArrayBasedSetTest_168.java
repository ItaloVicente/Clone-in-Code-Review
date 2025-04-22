		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				set.retainAll(Arrays.asList(new String[] { "0" }));
			}
		});
