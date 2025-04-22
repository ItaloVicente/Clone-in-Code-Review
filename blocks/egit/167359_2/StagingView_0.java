			this.base = base;
			this.provider = new DecoratingLabelProvider(
					new DecoratingLabelProvider(base, decorator),
					PlatformUI.getWorkbench().getDecoratorManager()
							.getLabelDecorator());
		}

		public StagingViewLabelProvider getBaseLabelProvider() {
			return base;
