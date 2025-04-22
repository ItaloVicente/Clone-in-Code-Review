	private static IShellProvider defaultModalParent = () -> {
		Display d = Display.getCurrent();

		if (d == null) {
			return null;
		}

		Shell parent = d.getActiveShell();

		if (parent == null) {
			parent = getModalChild(Display.getCurrent().getShells());
		} else {
			Shell modalChild = getModalChild(parent.getShells());
			if (modalChild != null) {
				parent = modalChild;
			}
		}

		return parent;
