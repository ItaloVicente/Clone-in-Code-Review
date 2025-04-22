			asyncExec(new Runnable() {

				@Override
				public void run() {
					try {
						if (future.isCancelled()) {
							return;
						}
						future.complete(action.call());
					} catch (Exception e) {
						future.completeExceptionally(e);
					}
				}
			});
