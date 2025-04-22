			cfg.setExecutor(new Executor() {
				public void execute(Runnable command) {
					command.run();
				}
			});
		}
