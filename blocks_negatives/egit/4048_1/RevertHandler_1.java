		} catch (Exception e) {
			Activator.handleError(UIText.RevertOperation_InternalError, e, true);
			return null;
		}
		if (newHead == null)
			Activator.showError(UIText.RevertOperation_Failed, null);
		return null;
