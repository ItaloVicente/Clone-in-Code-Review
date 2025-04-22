	private static void fixShellOrder(Display display, Shell[] shells) {
		if (shells.length <= 1) {
			return;
		}
		Shell activeShell = display.getActiveShell();
		int lastIndex = shells.length - 1;
		if (activeShell == null || shells[lastIndex] == activeShell) {
			return;
		}
		for (int i = 0; i < shells.length; i++) {
			if (shells[i] == activeShell) {
				Shell toMove = shells[lastIndex];
				shells[i] = toMove;
				shells[lastIndex] = activeShell;
				break;
			}
		}
	}

