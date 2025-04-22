	public static ISchedulingRule getRuleForRepositories(Collection<Repository> repositories) {
		ISchedulingRule result = null;
		for (Repository repository : repositories) {
			ISchedulingRule rule = getRule(repository);
			result = MultiRule.combine(result, rule);
		}
		return result;
	}

