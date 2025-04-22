		sys_normalCursor = t.getCursor();

		t.addListener(SWT.MouseMove, new Listener() {
			@Override
			public void handleEvent(final Event e) {
				StyleRange styleRange = getStyleRange(e.x, e.y);
				if (styleRange != null && styleRange.underline)
					t.setCursor(SYS_LINK_CURSOR);
				else
					t.setCursor(sys_normalCursor);
			}
		});
		t.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				if (e.button != 1)
					return;

				final StyleRange r = getStyleRange(e.x, e.y);
				if (r instanceof ObjectLink) {
					final RevCommit c = ((ObjectLink) r).targetCommit;
					for (final Object l : navListeners.getListeners())
						((CommitNavigationListener) l).showCommit(c);
				}
			}
		});
