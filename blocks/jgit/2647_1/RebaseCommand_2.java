				try {
					if (!lastStepWasForward)
						newHead = new Git(repo).cherryPick()
								.include(commitToPick).call();
				} catch (AbnormalMergeFailureException e) {
					if (this.operation != Operation.CONTINUE)
						return fail(e);
				} finally {
					monitor.endTask();
				}
