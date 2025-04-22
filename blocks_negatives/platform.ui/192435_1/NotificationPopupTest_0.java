		this.builder.title("Hello World", false).content(new Function<Composite, Control>() {

			@Override
			public Control apply(Composite parent) {
				text[0] = new Text(parent, SWT.NONE);
				text[0].setText("My custom Text");
				return text[0];
			}
