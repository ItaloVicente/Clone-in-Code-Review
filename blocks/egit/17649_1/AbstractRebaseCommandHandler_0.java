						if (operation == Operation.ABORT) {
							RebaseInteractivePlan plan = RebaseInteractivePlan
									.getPlan(repository);
							if (plan != null)
								plan.dispose();
						}
