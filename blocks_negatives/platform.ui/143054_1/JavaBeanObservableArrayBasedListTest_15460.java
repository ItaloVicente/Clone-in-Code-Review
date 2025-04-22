		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.retainAll(Arrays.asList(new String[] { "0" }));
			}
		});
