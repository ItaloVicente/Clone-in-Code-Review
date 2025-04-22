		TableItem items[] = folderNames.getSelection();
		if (items.length == 1) {
			ArrayList result = new ArrayList();
			result.add(items[0].getData());
			setResult(result);
		}
		super.okPressed();
	}

	protected boolean select(IResource resource) {
		return true;
	}

	protected void refresh(boolean force) {
		if (gatherResourcesDynamically) {
			gatherResources(force);
		} else {
			filterResources(force);
		}
	}

	private void updateFolders(final ResourceDescriptor desc) {
		BusyIndicator.showWhile(getShell().getDisplay(), () -> {
			if (!desc.resourcesSorted) {
				Collections.sort(desc.resources, (o1, o2) -> {
					String s1 = getParentLabel((IResource) o1);
					String s2 = getParentLabel((IResource) o2);
					return collator.compare(s1, s2);
