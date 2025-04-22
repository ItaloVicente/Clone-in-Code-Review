		for (MenuItem item : items) {
			String label = item.getText();
			System.out.println(label);
			Assert.assertTrue("Duplicate menu entry in: " + menuName + " "
					+ label, !labels.contains(label));
			if (item.getMenu() != null) {
