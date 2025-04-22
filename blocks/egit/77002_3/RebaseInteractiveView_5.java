	private class ContextMenuKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
			for (IAction item : contextMenuItems) {
				if (key == item.getAccelerator()) {
					e.doit = false;
					item.run();
					return;
				}
			}
			int[] moveAccelerators = actionToolBarProvider
					.getMoveAccelerators();
			if (key == moveAccelerators[0]) {
				actionToolBarProvider.moveUp();
				e.doit = false;
			} else if (key == moveAccelerators[1]) {
				actionToolBarProvider.moveDown();
				e.doit = false;
			} else if ((e.stateMask & SWT.MODIFIER_MASK) == 0) {
				switch (key) {
				case SWT.ARROW_DOWN:
				case SWT.ARROW_UP:
				case SWT.PAGE_DOWN:
				case SWT.PAGE_UP:
				case SWT.HOME:
				case SWT.END:
					break;
				default:
					e.doit = false;
					break;
				}
			}
		}
	}

