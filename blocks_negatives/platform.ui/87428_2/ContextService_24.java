						runExternalCode(new Runnable() {
							@Override
							public void run() {
								contextService.deactivateContext(contextId);
							}
						});
