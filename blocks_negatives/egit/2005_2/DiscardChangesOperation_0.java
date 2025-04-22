		for (GitIndex index : modifiedIndexes) {
			try {
				index.write();
			} catch (IOException e) {
				errorOccured = true;
				Activator.logError(
						CoreText.DiscardChangesOperation_writeIndexFailed, e);
			}
		}
		monitor.worked(1);
