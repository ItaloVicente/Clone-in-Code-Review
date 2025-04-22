			ForkJoinPool.commonPool().execute(() -> {
				try {
					future.complete(action.call());
				} catch (Exception e) {
					future.completeExceptionally(e);
				}
			});
