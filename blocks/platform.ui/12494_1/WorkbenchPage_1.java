	public IWorkbenchPartReference[] getAllParts() {
		List<IWorkbenchPartReference> sortedReferences = new ArrayList<IWorkbenchPartReference>(
				getSortedEditorReferences());
		sortedReferences.addAll(Arrays.asList(getViewReferences()));
		return sortedReferences.toArray(new IWorkbenchPartReference[sortedReferences.size()]);
	}

