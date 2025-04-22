		getRealm().exec(new Runnable() {
			@Override
			public void run() {
				if (dependencies == null) {
					getSet();
				}
