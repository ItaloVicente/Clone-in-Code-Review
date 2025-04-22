				int numberOfRepositories = repositories.length;
				SubMonitor progress = SubMonitor.convert(pm, 4);
				for (Repository repository : repositories) {
					CheckoutResult result = checkoutRepository(repository,
							progress, numberOfRepositories > 1);
					if (result.getStatus() == Status.NONDELETED) {
						retryDelete(repository, result.getUndeletedList());
