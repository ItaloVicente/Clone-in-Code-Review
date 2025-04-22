						CheckoutResult result;
						if (pm.isCanceled()) {
							result = CheckoutResult.NOT_TRIED_RESULT;
						}
						else {
							result = checkoutRepository(repository,
									progress.newChild(1),
									numberOfRepositories > 1);
							if (result.getStatus() == Status.NONDELETED) {
								retryDelete(repository,
										result.getUndeletedList());
							}
