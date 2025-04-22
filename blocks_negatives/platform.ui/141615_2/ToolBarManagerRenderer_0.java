			parent.markDirty();
			parent.update(true);
			ToolBar toolbar = parent.getControl();
			if (toolbar != null && !toolbar.isDisposed()) {
				toolbar.requestLayout();
			}
