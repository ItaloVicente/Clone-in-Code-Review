
				if (toolBarManager instanceof ToolBarManager2) {
					ToolBar control = ((ToolBarManager2) toolBarManager).getControl();
					if (control != null) {
						control.requestLayout();
					}
				}
