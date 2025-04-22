	public List<MBindingContext> getBindingContexts() {
		if (bindingContexts == null) {
			bindingContexts = new EObjectResolvingEList<MBindingContext>(MBindingContext.class, this, AdvancedPackageImpl.PERSPECTIVE__BINDING_CONTEXTS);
		}
		return bindingContexts;
	}

