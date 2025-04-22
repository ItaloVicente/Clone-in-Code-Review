	private MPart createPart(MPartDescriptor descriptor) {
		if (descriptor == null) {
			return null;
		}
		MPart part = modelService.createModelElement(MPart.class);
		part.setElementId(descriptor.getElementId());
		part.getMenus().addAll(EcoreUtil.copyAll(descriptor.getMenus()));
		if (descriptor.getToolbar() != null) {
			part.setToolbar((MToolBar) EcoreUtil.copy((EObject) descriptor.getToolbar()));
		}
		part.setContributorURI(descriptor.getContributorURI());
		part.setCloseable(descriptor.isCloseable());
		part.setContributionURI(descriptor.getContributionURI());
		part.setLabel(descriptor.getLabel());
		part.setIconURI(descriptor.getIconURI());
		part.setTooltip(descriptor.getTooltip());
		part.getHandlers().addAll(EcoreUtil.copyAll(descriptor.getHandlers()));
		part.getTags().addAll(descriptor.getTags());
		part.getVariables().addAll(descriptor.getVariables());
		part.getProperties().putAll(descriptor.getProperties());
		part.getPersistedState().putAll(descriptor.getPersistedState());
		part.getBindingContexts().addAll(descriptor.getBindingContexts());
		return part;
	}

