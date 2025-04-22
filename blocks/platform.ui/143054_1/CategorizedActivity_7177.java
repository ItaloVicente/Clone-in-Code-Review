		}
		return false;
	}

	public IActivity getActivity() {
		return activity;
	}

	@Override
	public Set<IActivityRequirementBinding> getActivityRequirementBindings() {
		return activity.getActivityRequirementBindings();
	}

	@Override
	public Set<IActivityPatternBinding> getActivityPatternBindings() {
		return activity.getActivityPatternBindings();
	}

	public ICategory getCategory() {
		return category;
	}

	@Override
