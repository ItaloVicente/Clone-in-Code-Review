		Composite displayArea = new Composite(body, toolkit.getOrientation()) {

			@Override
			public boolean setFocus() {
				Control control = focusTracker.getLastFocusControl();
				if (control != null && control.forceFocus()) {
					return true;
				}
				return super.setFocus();
			}
		};
