			try {
				discardChange(res, repo);
			} catch (GitAPIException e) {
				errorOccurred = true;
				String message = NLS.bind(
						CoreText.DiscardChangesOperation_discardFailed, res
								.getFullPath());
				Activator.logError(message, e);
			}
