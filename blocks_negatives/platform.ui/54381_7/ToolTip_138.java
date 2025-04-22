				control.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (CURRENT_TOOLTIP != null
								&& CURRENT_TOOLTIP.isDisposed()) {
							toolTipHookByTypeRecursively(CURRENT_TOOLTIP,
									hideOnMouseDown, SWT.MouseDown);
						}
