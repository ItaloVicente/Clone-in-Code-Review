		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.removeAll(Arrays.asList(new String[] { "0" }));
			}
		});
