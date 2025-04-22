		try {
			dialog.setInitialPattern("c/f");
			dialog.open();
			dialog.refresh();
			Assert.assertTrue(DisplayHelper.waitForCondition(dialog.getShell().getDisplay(), 3000,
					() -> file.equals(dialog.getSelectedItems().getFirstElement())
			));
		} finally {
			dialog.close();
		}
