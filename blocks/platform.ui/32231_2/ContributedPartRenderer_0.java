
		Composite parent = null;
		if (element.getWidget() instanceof Composite) {
			parent = ((Composite) element.getWidget()).getParent();
		}

		if (parent != null) {
			try {
				parent.setRedraw(false);
				super.disposeWidget(element);
			} finally {
				parent.setRedraw(true);
			}
		} else {
			super.disposeWidget(element);
		}
