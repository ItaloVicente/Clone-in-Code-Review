		return new IShowInSource() {
			@Override
			public ShowInContext getShowInContext() {
				return new ShowInContext(getCommonViewer().getInput(), getCommonViewer().getSelection());
			}
		};
