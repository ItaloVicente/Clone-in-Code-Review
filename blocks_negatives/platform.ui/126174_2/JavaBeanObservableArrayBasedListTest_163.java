		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.set(0, "1");
			}
		});
