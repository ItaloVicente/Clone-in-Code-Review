				Listener listener = new Listener() {
					@Override
					public void handleEvent(Event e) {
						switch (e.type) {
						case SWT.MouseDown:
							if (toggle != null)
								toggle.setFocus();
							break;
						case SWT.MouseUp:
							label.setCursor(FormsResources.getBusyCursor());
							programmaticToggleState();
							label.setCursor(FormsResources.getHandCursor());
							break;
						case SWT.MouseEnter:
							if (toggle != null) {
								label.setForeground(toggle
										.getHoverDecorationColor());
								toggle.hover = true;
								toggle.redraw();
							}
							break;
						case SWT.MouseExit:
							if (toggle != null) {
								label.setForeground(getTitleBarForeground());
								toggle.hover = false;
								toggle.redraw();
							}
							break;
						case SWT.Paint:
							if (toggle != null && (getExpansionStyle() & NO_TITLE_FOCUS_BOX) == 0) {
								paintTitleFocus(e.gc);
							}
							break;
