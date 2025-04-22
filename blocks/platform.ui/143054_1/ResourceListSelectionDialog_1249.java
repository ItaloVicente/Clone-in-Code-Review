		return text;
	}

	protected boolean getShowDerived() {
		return showDerived;
	}

	protected void setShowDerived(boolean show) {
		showDerived = show;
	}

	private void initDescriptors(final IResource resources[]) {
		BusyIndicator.showWhile(null, () -> {
			descriptors = new ResourceDescriptor[resources.length];
			for (int i1 = 0; i1 < resources.length; i1++) {
				IResource r = resources[i1];
				ResourceDescriptor d = new ResourceDescriptor();
				d.label = r.getName();
				d.resources.add(r);
				descriptors[i1] = d;
			}
			Arrays.sort(descriptors);
			descriptorsSize = descriptors.length;

			int index = 0;
			if (descriptorsSize < 2) {
