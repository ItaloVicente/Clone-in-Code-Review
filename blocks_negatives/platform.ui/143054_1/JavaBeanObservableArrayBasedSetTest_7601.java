		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				set.add("0");
			}
		});
