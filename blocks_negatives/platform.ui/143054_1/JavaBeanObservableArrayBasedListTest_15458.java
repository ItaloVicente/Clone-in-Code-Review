		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.addAll(0, Arrays.asList(new String[] { "1", "2" }));
			}
		});
