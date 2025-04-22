				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							task.call();
						} catch (Throwable failure) {
							errors.add(failure);
						}
