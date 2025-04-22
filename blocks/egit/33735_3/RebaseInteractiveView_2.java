		if (repository != null) {
			currentPlan = RebaseInteractivePlan.getPlan(repository);
			planIndexer = new RebasePlanIndexer(currentPlan);
			currentPlan.addRebaseInteractivePlanChangeListener(this);
			form.setText(getRepositoryName(repository));
		} else {
			currentPlan = null;
			planIndexer = null;
			form.setText(UIText.RebaseInteractiveView_NoSelection);
		}
