		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.addAll(Arrays.asList(new String[] { "0", "1" }));
			}
		});
