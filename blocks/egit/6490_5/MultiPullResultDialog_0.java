			Point location = null;
			Rectangle clientArea = getShell().getMonitor().getClientArea();
			int yMin = clientArea.y + clientArea.height;
			for (Entry<Repository, Object> item : (List<Entry<Repository, Object>>) sel
					.toList()) {
				if (item.getValue() instanceof PullResult) {
					PullResultDialog dialog = new PullResultDialog(getShell(),
							item.getKey(), (PullResult) item.getValue());
					dialog.open();
					Shell shell = dialog.getShell();
					if (location != null) {
						int delta = shell.getFont().getFontData()[0].getHeight() * 3;
						int y = Math.min(location.y + delta, yMin - shell.getClientArea().height);
						shell.setLocation(location.x, y);
					}
					location = shell.getLocation();
				}
