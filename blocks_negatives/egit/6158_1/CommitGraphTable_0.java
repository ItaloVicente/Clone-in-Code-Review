							hoverShell = new Shell(getTableView().getTable()
									.getShell(), SWT.ON_TOP | SWT.NO_FOCUS
									| SWT.TOOL);
							hoverShell.setLayout(new FillLayout());
							Point tableLocation = getTableView().getTable()
									.toControl(0, 0);
							hoverShell.setLocation(
									-tableLocation.x + e.x,
									-tableLocation.y + e.y
											- renderer.getTextHeight());
							Label label = new Label(hoverShell, SWT.NONE);
							label.setText(getHooverText(commit.getRef(i)));
							label.setBackground(infoBackgroundColor);
							hoverShell.pack();
							hoverShell.setVisible(true);
