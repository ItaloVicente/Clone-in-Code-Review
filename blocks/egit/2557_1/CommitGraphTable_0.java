
		table.getTable().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				synchronized (this) {
					if (hoverShell != null) {
						hoverShell.setVisible(false);
						hoverShell.dispose();
						hoverShell = null;
					}

					TableItem item = table.getTable().getItem(
							new Point(e.x, e.y));
					if (item == null)
						return;
					SWTCommit commit = (SWTCommit) item.getData();
					if (commit == null || commit.getRefCount() == 0)
						return;

					int relativeX = e.x - item.getBounds().x;
					for (int i = 0; i < commit.getRefCount(); i++) {
						Point textSpan = renderer.getRefHSpan(commit
								.getRef(i));
						if ((relativeX >= textSpan.x && relativeX <= textSpan.y)) {
							hoverShell = new Shell(getTableView().getTable()
									.getShell(), SWT.ON_TOP | SWT.NO_FOCUS
									| SWT.TOOL);
							hoverShell.setLayout(new FillLayout());
							Point tableLocation = getTableView().getTable()
									.toControl(0, 0);
							hoverShell.setLocation(-tableLocation.x + e.x,
									-tableLocation.y + e.y);
							Label label = new Label(hoverShell, SWT.NONE);
							label.setText(commit.getRef(i).getName());
							hoverShell.pack();
							hoverShell.setVisible(true);
						}
					}
				}
			}
		});

		table.getTable().addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				synchronized (this) {
					if (hoverShell == null)
						return;
					hoverShell.setVisible(false);
					hoverShell.dispose();
					hoverShell = null;
				}
			}
		});
