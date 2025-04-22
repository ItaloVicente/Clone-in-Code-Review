			syncExec(new Runnable() {

				@Override
				public void run() {
					try {
						future.complete(action.call());
					} catch (Exception e) {
						future.completeExceptionally(e);
					}
				}
			});
