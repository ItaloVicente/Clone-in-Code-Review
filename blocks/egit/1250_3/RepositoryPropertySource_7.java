				@Override
				public void run() {
					MenuManager mgr = new MenuManager();
					ToolItem item = (ToolItem) changeModeAction.getWidget();
					ToolBar control = item.getParent();
					final Menu ctxMenu = mgr.createContextMenu(control);

					for (final DisplayMode aMode : DisplayMode.values()) {
						mgr.add(new Action(aMode.getText()) {
							@Override
							public void run() {
								changeModeAction.getAction().setText(
										aMode.getText());
								editAction.getAction().setEnabled(
										aMode != DisplayMode.EFFECTIVE);
								myPage.refresh();
							}

							@Override
							public boolean isEnabled() {
								return aMode != getCurrentMode();
							}

							@Override
							public boolean isChecked() {
								return aMode == getCurrentMode();
							}

							@Override
							public int getStyle() {
								return IAction.AS_CHECK_BOX;
							}
						});
					}
