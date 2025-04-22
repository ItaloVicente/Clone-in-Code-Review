			clipboard.clearContents();
			processEvents();
			view.updateEnabledState();
			processEvents();

			assertFalse(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertFalse(view.getSelectAllAction().isEnabled());

