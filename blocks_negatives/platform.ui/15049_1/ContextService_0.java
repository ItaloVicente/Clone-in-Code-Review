				if (cached != null && cached != EvaluationResult.FALSE) {
					runExternalCode(new Runnable() {
						public void run() {
							contextService.deactivateContext(contextId);
						}
					});
				}
