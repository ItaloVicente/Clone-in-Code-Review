				try {
					pm.setTaskName(MessageFormat.format(
							CoreText.BranchOperation_performingBranch, target));
					int numberOfRepositories = repositories.length;
					SubMonitor progress = SubMonitor.convert(pm,
							numberOfRepositories * 2);
					for (Repository repository : repositories) {
						CheckoutResult result = checkoutRepository(repository,
								progress.newChild(1), numberOfRepositories > 1);
						if (result.getStatus() == Status.NONDELETED) {
							retryDelete(repository, result.getUndeletedList());
						}
						results.put(repository, result);
