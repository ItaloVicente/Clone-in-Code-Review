		assertPropertyChangeEvent(bean, new Runnable() {
			@Override
			public void run() {
				list.remove("0");
			}
		});
