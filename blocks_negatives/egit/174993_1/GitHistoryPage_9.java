			graph.getControl().setFocus();
		} else {
			Control control = focusTracker.getLastFocusControl();
			if (control == null) {
				control = graph.getControl();
			}
			control.setFocus();
