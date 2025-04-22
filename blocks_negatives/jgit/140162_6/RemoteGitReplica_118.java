		getSystem().getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try (Repository git = getLeader().openRepository()) {
					try {
						push(git, req);
						req.done(git);
					} catch (Throwable err) {
						req.setException(git, err);
					}
				} catch (IOException err) {
					req.setException(null, err);
