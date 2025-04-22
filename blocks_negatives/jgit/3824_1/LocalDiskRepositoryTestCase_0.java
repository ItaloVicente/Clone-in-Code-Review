				} else {
					if (!e.delete()) {
						if (!silent) {
							reportDeleteFailure(testName, failOnError, e);
						}
						silent = !failOnError;
					}
