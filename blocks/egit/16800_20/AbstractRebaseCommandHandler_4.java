
					private void finishRebaseInteractive() {
						RebaseInteractivePlan plan = RebaseInteractivePlan
								.getPlan(repository);
						if (plan != null && !plan.isRebasingInteractive())
							plan.dispose();
					}
