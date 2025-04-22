		final Composite newComposite = new Composite((Composite) parentWidget,
				SWT.NONE) {

			/**
			 * Field to determine whether we are currently in the midst of
			 * granting focus to the part.
			 */
			private boolean beingFocused = false;

			@Override
			public boolean setFocus() {
				if (!beingFocused) {
					try {
						beingFocused = true;

						Object object = part.getObject();
						if (object != null && isEnabled()) {
							IPresentationEngine pe = part.getContext().get(
									IPresentationEngine.class);
							pe.focusGui(part);
							return true;
