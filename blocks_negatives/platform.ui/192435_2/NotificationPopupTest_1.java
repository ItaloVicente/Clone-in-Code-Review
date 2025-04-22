		NotificationPopup notication = this.builder.title(new Function<Composite, Control>() {

			@Override
			public Control apply(Composite parent) {
				text[0] = new Text(parent, SWT.NONE);
				text[0].setText("My custom Title");
				return text[0];
			}
