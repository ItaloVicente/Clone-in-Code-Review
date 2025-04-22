					@Override
					protected int getShellStyle() {
						return super.getShellStyle() | SWT.SHEET;
					}
				};
				int code = dialog.open();
				result[0] = code == 0;
			}
