
	public void updateOverridesFor(BindingElement bindingElement) {
		Collection<BindingElement> newOverrides = new ArrayList<>();
		if (bindingElement.getModelObject() instanceof Binding) {
			Binding selectedBinding = (Binding) bindingElement.getModelObject();
			@SuppressWarnings("unchecked")
			Collection<Binding> matches = (Collection<Binding>) bindingManager.getActiveBindingsDisregardingContext()
					.get(bindingElement.getTrigger());
			if (matches != null) {
				for (Binding b : matches) {
					if (selectedBinding != b && b.getSchemeId().equals(selectedBinding.getSchemeId())
							&& isChildAndParent(b.getContextId(), selectedBinding.getContextId())) {
						Object element = bindingModel.getBindingToElement().get(b);
						if (element != null) {
							newOverrides.add((BindingElement) element);
						}
					}
				}
			}
			controller.firePropertyChange(this, PROP_OVERRIDES, overrides, newOverrides);
			overrides = newOverrides;
		}
	}

	private static boolean isChildAndParent(String childContextId, String parentContextId) {
		IContextService contextService = PlatformUI.getWorkbench().getService(IContextService.class);
		try {
			for (String c = contextService.getContext(childContextId).getParentId(); c != null; c = contextService
					.getContext(c).getParentId()) {
				if (c.equals(parentContextId)) {
					return true;
				}
			}
		} catch (NotDefinedException e) {
		}
		return false;
	}
