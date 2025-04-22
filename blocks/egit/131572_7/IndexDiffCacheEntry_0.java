		Repository repository = getRepository();
		ISchedulingRule rule;
		if (repository == null) {
			rule = ResourcesPlugin.getWorkspace().getRoot();
		} else {
			rule = RuleUtil.getRule(repository);
		}
