
	private void getAffectedElements(Set<IResource> resources,
			Object[] elements, IStructuredContentProvider contentProvider,
			List<Object> result) {
		for (Object element : elements) {
			IResource resource = AdapterUtils.adapt(element, IResource.class);
			if (resource != null && resources.contains(resource)) {
				result.add(element);
				if (contentProvider instanceof ITreeContentProvider) {
					getAffectedElements(resources,
							((ITreeContentProvider) contentProvider)
									.getChildren(element),
							contentProvider, result);
				}
			}
		}
	}
