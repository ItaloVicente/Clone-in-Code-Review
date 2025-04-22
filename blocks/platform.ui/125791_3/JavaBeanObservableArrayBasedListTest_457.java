		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.add(0, "0");
			}
		});
