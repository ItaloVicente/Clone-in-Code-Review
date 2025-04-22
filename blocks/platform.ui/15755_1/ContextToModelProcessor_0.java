		contexts.clear();
		contextFromModel.clear();
	}

	private void collectContexts(List<MBindingContext> bindingContexts) {
		for (MBindingContext ctx : bindingContexts) {
			contextFromModel.add(ctx);
			collectContexts(ctx.getChildren());
		}
