			for (Entry<Repository, Object> item : (List<Entry<Repository, Object>>) sel
					.toList()) {

				if (item.getValue() instanceof PullResult) {
					final int x = xOffset;
					final int y = yOffset;
					xOffset += xDelta;
					yOffset += yDelta;

					PullResultDialog dialog = new PullResultDialog(shell,
							item.getKey(), (PullResult) item.getValue()) {
						private Point initialLocation;

						@Override
						protected Point getInitialLocation(Point initialSize) {
							initialLocation = super
									.getInitialLocation(initialSize);
							initialLocation.x += x;
							initialLocation.y += y;
							return initialLocation;
						}

						@Override
						public boolean close() {
							Shell resultShell = getShell();
							if (resultShell != null
									&& !resultShell.isDisposed()) {
								Point location = resultShell.getLocation();
								if (location.equals(initialLocation))
									resultShell.setLocation(location.x - x,
											location.y - y);
							}
							boolean result = super.close();

							Shell[] subShells = shell.getShells();
							if (subShells.length > 0) {
								subShells[subShells.length - 1].setActive();
							}
							return result;
						}
					};
					dialog.open();
				}
