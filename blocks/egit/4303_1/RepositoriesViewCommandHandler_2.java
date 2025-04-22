		return false;
	}

	protected void enableWhenAllRepositoriesHaveHead(Object evaluationContext) {
		setBaseEnabled(checkSelectionHasHead(evaluationContext, true));
	}
