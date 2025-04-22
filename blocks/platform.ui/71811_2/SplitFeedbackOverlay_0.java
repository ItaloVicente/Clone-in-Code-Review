		Composite parent = feedbackShell.getParent();
		if (parent instanceof Shell) {
			Shell parentShell = (Shell) parent;

			Rectangle bounds = parentShell.getBounds();
			rgn.add(bounds.width - 1, bounds.height - 1, 1, 1);
		}

