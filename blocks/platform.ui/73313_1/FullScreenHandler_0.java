			Point dialogLocation = new Point(bounds.x + bounds.width / 2 - textExtendX / 2,
					bounds.y + bounds.height / 5);
			System.out.println("-------- SHOWING FULLSCREEN DIALOG ---------"); //$NON-NLS-1$
			Monitor[] monitors = getShell().getDisplay().getMonitors();
			for (int i = 0; i < monitors.length; i++) {
				Rectangle monitorBounds = monitors[i].getBounds();
				System.out.printf("Monitor %d bounds: %s\n", i, monitorBounds); //$NON-NLS-1$
				if (monitorBounds.intersects(getShell().getBounds())) {
					System.out.println("  the window's bounds are on this monitor"); //$NON-NLS-1$
				}
				if (monitors[i].equals(getShell().getMonitor())) {
					System.out.println("  this is the window's monitor"); //$NON-NLS-1$
				}
				if (monitorBounds.contains(dialogLocation)) {
					System.out.println("  the fullscreen dialog should appear on this monitor"); //$NON-NLS-1$
				}
			}
