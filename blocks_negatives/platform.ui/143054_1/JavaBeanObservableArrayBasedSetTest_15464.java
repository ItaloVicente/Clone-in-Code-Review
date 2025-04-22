		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				set.addAll(Arrays.asList(new String[] { "0", "1" }));
			}
		});
