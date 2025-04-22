			if (parent == null) {
				ici.setVisible(itemModel.isVisible());
				return;
			}

			IContributionManagerOverrides ov = parent.getOverrides();
			if (ov == null) {
				ici.setVisible(itemModel.isVisible());
			}

			parent.markDirty();
			parent.update(true);
			ToolBar tb = parent.getControl();
			if (tb != null && !tb.isDisposed()) {
				tb.pack(true);
				tb.getShell().layout(new Control[] { tb }, SWT.DEFER);
