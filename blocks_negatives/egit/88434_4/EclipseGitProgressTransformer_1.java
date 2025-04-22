		if (task != null) {
			try {
				task.done();
			} finally {
				task = null;
			}
		}
