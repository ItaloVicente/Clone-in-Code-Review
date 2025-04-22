	private static class DecoratingLabelProviderWithToolTips
			extends DecoratingLabelProvider
			implements IStyledLabelProvider, IToolTipProvider {

		private final RepositoriesViewLabelProvider labelProvider;

		DecoratingLabelProviderWithToolTips(
				RepositoriesViewLabelProvider labelProvider) {
			super(labelProvider, PlatformUI.getWorkbench().getDecoratorManager()
					.getLabelDecorator());
			this.labelProvider = labelProvider;
