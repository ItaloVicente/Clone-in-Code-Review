
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

	private boolean isChildAndParent(String childContextId, String parentContextId) {
		for (String c = getParentContextId(childContextId); c != null; c = getParentContextId(c)) {
			if (c.equals(parentContextId)) {
				return true;
			}
		}
		return false;
	}

	private String getParentContextId(String contextId) {
		ContextElement contextElement = (ContextElement) contextModel.getContextIdToElement().get(contextId);
		try {
			return ((Context) contextElement.getModelObject()).getParentId();
		} catch (NotDefinedException e) {
			return null;
		}

	}
