	@Override
	public String getSortLabel() {
		if (this.sortLabelCache == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(super.getSortLabel());
			if (preferenceNode instanceof WorkbenchPreferenceExtensionNode) {
				((WorkbenchPreferenceExtensionNode) preferenceNode).getKeywordLabels().forEach(label -> {
					builder.append(separator);
					builder.append(label);
				});
			}
			this.sortLabelCache = builder.toString();
		}
		return this.sortLabelCache;
	}

