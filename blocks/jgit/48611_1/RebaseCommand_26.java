					} else {
						if (operation == Operation.BEGIN && mergeResult
								.getMergeStatus() == MergeResult.MergeStatus.FAILED)
							return abort(RebaseResult
									.failed(mergeResult.getFailingPaths()));
						return stop(commitToPick
					}
