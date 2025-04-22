		getSystem().getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				MonotonicClock clk = getSystem().getClock();
				try (Repository git = getLeader().openRepository();
						ProposedTimestamp ts = clk.propose()) {
					try {
						update(git, req, ts);
						req.done(git);
					} catch (Throwable err) {
						req.setException(git, err);
					}
				} catch (IOException err) {
					req.setException(null, err);
				}
			}
		});
