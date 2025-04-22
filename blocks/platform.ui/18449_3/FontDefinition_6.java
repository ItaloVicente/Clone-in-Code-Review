	@Override
	protected void setOverridden(boolean overridden) {
		super.setOverridden(overridden);
		if (!isAddedByCss() && !description.endsWith(getOverriddenLabel())) {
			description += ' ' + getOverriddenLabel();
		}
