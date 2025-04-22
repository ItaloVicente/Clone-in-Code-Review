				final IEventLoopAdvisor finalAdvisor = advisor;
				display.setErrorHandler(e -> {
					if (e instanceof LinkageError || e instanceof AssertionError) {
						handle(e, finalAdvisor);
					} else {
						throw e;
					}
				});
				display.setRuntimeExceptionHandler(e -> handle(e, finalAdvisor));
