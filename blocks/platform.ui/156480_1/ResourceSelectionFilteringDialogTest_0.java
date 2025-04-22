		try {
			dialog.setInitialPattern("c/f");
			dialog.open();
			dialog.refresh();
			Assert.assertTrue(DisplayHelper.waitForCondition(dialog.getShell().getDisplay(), 3000,
					() -> dialog.getSelectedItems().getFirstElement().equals(file)));
		} finally {
			dialog.close();
		}
