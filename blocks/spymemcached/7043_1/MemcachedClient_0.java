			public Boolean get(long duration, TimeUnit units) throws InterruptedException,
				TimeoutException, ExecutionException {
				try {
					status = new OperationStatus(true, "OK");
					return super.get(duration, units);
				} catch (InterruptedException e) {
					throw e;
				} catch (TimeoutException e) {
					throw e;
				} catch (ExecutionException e) {
					throw e;
				}
				
			}
			@Override
