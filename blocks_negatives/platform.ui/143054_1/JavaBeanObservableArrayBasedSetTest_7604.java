		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				set.removeAll(Arrays.asList(new String[] { "0" }));
			}
		});
