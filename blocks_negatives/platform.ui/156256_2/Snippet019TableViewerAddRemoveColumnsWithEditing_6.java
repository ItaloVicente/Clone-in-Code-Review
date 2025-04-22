		mgr.addMenuListener(manager -> {
			if (v.getTable().getColumnCount() == 2) {
				manager.add(insertEmailBefore);
				manager.add(insertEmailAfter);
			} else {
				manager.add(removeEmail);
