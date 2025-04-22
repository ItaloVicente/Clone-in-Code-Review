
	@Override
	public String toString() {
		return "WWinPluginAction [" //$NON-NLS-1$
				+ "id=" + getId() //$NON-NLS-1$
				+ ", enabled=" + isEnabled() + //$NON-NLS-1$
				(actionSetId != null ? ", actionSet=" + actionSetId : "") //$NON-NLS-1$ //$NON-NLS-2$
				+ "]"; //$NON-NLS-1$
	}
