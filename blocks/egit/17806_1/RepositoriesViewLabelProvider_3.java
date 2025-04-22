	@Override
	public String getToolTipText(Object element) {
		if (element instanceof AdditionalRefNode) {
			AdditionalRefNode additionalRefNode = (AdditionalRefNode) element;
			Ref ref = additionalRefNode.getObject();
			return GitLabelProvider.getRefDescription(ref);
		}
		return null;
	}

