
	@Override
	public boolean belongsTo(Object family) {
		if (family.equals(JobFamilies.GENERATE_HISTORY))
			return true;
		return super.belongsTo(family);
	}

