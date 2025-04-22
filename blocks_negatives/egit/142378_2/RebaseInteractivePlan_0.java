		RebaseInteractivePlan plan = planRegistry.get(repo.getDirectory());
		if (plan == null) {
			plan = new RebaseInteractivePlan(repo);
			planRegistry.put(repo.getDirectory(), plan);
		}
		return plan;
