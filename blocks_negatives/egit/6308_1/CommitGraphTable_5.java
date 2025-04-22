		table.getTable().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseHover(MouseEvent e) {
				synchronized (this) {
					disposeHover();

					TableItem item = table.getTable().getItem(
							new Point(e.x, e.y));
					if (item == null)
						return;
					SWTCommit commit = (SWTCommit) item.getData();
					if (commit == null || commit.getRefCount() == 0)
						return;

					int relativeX = e.x - item.getBounds().x;
					for (int i = 0; i < commit.getRefCount(); i++) {
						Point textSpan = renderer.getRefHSpan(commit.getRef(i));
						if ((textSpan != null)
								&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {
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
						}
					}
				}
			}

			private String getHooverText(Ref r) {
				String name = r.getName();
				if (r.isSymbolic())
				return name;
			}

		});

		table.getTable().addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				synchronized (this) {
					disposeHover();
				}
			}
		});
