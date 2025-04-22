			new CompositeUpdater(composite, list) {
				@Override
				protected Widget createWidget(int index) {
					Label label = new Label(composite, SWT.BORDER);
					return label;
				}
