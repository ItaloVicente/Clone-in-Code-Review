		if (!meParent.getTags().contains(HIDE_RESTORE)) {
			ToolItem restoreBtn = new ToolItem(trimStackTB, SWT.PUSH);
			restoreBtn.setToolTipText(Messages.TrimStack_RestoreText);
			restoreBtn.setImage(getRestoreImage());
			restoreBtn.addSelectionListener(SelectionListener
					.widgetSelectedAdapter(e -> minimizedElement.getTags().remove(IPresentationEngine.MINIMIZED)));
		}
