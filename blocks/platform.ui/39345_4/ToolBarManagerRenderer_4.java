			ToolBarManager parent = null;
			if (ici instanceof MenuManager) {
				parent = (ToolBarManager) ((MenuManager) ici).getParent();
			} else if (ici instanceof ContributionItem) {
				parent = (ToolBarManager) ((ContributionItem) ici).getParent();
			}

			if (parent != null) {
				parent.markDirty();
				parent.update(true);
				ToolBar tb = parent.getControl();
				if (tb != null && !tb.isDisposed()) {
					tb.pack(true);
					tb.getShell().layout(new Control[] { tb }, SWT.DEFER);
				}
