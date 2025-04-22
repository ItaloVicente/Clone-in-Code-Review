
		return indexDiffData;
	}

	private void decorateResourceMapping(Object element, IDecoration decoration) {
		@SuppressWarnings("restriction")
		ResourceMapping mapping = Utils.getResourceMapping(element);

		IDecoratableResource decoRes = new DecoratableResourceMapping(mapping);

		if(!decoRes.isTracked())
