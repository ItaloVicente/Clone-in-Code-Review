		themeIdCombo.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITheme) element).getLabel();
			}
		});
