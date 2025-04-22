					for (int i = mi.length; --i >= srcIx;) {
						ToolItem item = mi[i];
						if (!item.isDisposed()) {
							Control ctrl = item.getControl();
							if (ctrl != null) {
								item.setControl(null);
								ctrl.dispose();
							}
							item.dispose();
						}
					}
