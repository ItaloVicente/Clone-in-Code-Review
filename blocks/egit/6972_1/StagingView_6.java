
		ILabelDecorator decorator = PlatformUI.getWorkbench().getDecoratorManager().getLabelDecorator();

		DecorationContext context = new DecorationContext();
		context.putProperty(GitLightweightDecorator.DISABLED, Boolean.TRUE);

		return new DecoratingStyledCellLabelProvider(baseProvider, decorator, context);
