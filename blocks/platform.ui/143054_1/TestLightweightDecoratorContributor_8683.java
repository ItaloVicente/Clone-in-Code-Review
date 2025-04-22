		while (iterator.hasNext()) {
			LabelProviderChangedEvent event = new LabelProviderChangedEvent(
					this, element);
			iterator.next()
					.labelProviderChanged(event);
		}
	}

	public ImageDescriptor getOverlay(Object element) {
		Assert.isTrue(element instanceof IResource);
		if (descriptor == null) {
			Bundle bundle = FrameworkUtil.getBundle(BadIndexDecorator.class);
			URL entry = bundle.getEntry("icons/binary_co.gif");
			descriptor = ImageDescriptor.createFromURL(entry);
		}
		return descriptor;

	}

	@Override
