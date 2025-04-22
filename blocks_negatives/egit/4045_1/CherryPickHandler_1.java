		} catch (Exception e) {
			throw new ExecutionException(UIText.CherryPickOperation_InternalError, e);
		}
		if (newHead == null) {
			CherryPickStatus status = cherryPickResult.getStatus();
			switch (status) {
			case CONFLICTING:
				MessageDialog.openWarning(parent,
