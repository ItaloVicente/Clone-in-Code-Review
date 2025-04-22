		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				set.remove("0");
			}
		});
