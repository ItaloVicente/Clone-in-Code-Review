        }
        return false;
    }

    /**
     * @return returns the <code>IActivity</code>.
     */
    public IActivity getActivity() {
        return activity;
    }

    @Override
	public Set getActivityRequirementBindings() {
        return activity.getActivityRequirementBindings();
    }

    @Override
	public Set getActivityPatternBindings() {
        return activity.getActivityPatternBindings();
    }

    /**
     * @return returns the <code>ICategory</code>.
     */
    public ICategory getCategory() {
        return category;
    }

    @Override
