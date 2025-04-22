	public static final String IGNORE_AS_DROP_TARGET = "ignore_as_drop_target";

	public static Rectangle getDisplayBounds(Control boundsControl) {
		Control parent = boundsControl.getParent();
		if (parent == null || boundsControl instanceof Shell) {
			return boundsControl.getBounds();
		}

		return Geometry.toDisplay(parent, boundsControl.getBounds());
	}

	public static Control findControl(Display displayToSearch, Point locationToFind) {
		Shell[] shells = displayToSearch.getShells();

		return findControl(shells, locationToFind);
	}

	public static Control findControl(Control[] toSearch, Point locationToFind) {
		for (int idx = toSearch.length - 1; idx >= 0; idx--) {
			Control next = toSearch[idx];

			if (next.getData(IGNORE_AS_DROP_TARGET) != null) {
				continue;
			}

			if (!next.isDisposed() && next.isVisible()) {
				Rectangle bounds = getDisplayBounds(next);

				if (bounds.contains(locationToFind)) {
					if (next instanceof Composite) {
						Control result = findControl((Composite) next, locationToFind);

						if (result != null) {
							return result;
						}
					}

					return next;
				}
			}
		}

		return null;
	}

	public static Control findControl(Composite toSearch, Point locationToFind) {
		Control[] children = toSearch.getChildren();

		return findControl(children, locationToFind);
	}
