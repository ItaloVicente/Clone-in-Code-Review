		Runnable timer = new Runnable() {
			@Override
			public void run() {
				StatusLine.this.startTask(timestamp, animated);
			}
		};
