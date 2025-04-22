		assertNotEquals("Changing the TBR of the selected element should have moved selection to a TBR item", partA,
				container.getSelectedElement());

		if ("gtk".equals(SWT.getPlatform())) {
			assertTrue(
					"Changing the TBR of the selected element should have moved selection to a TBR item",
					container.getSelectedElement().isToBeRendered());

			partC.setToBeRendered(false);
			assertNull("Changing the TBR of all elements to false should have set the field to null",
					container.getSelectedElement());
		} else {
			assertTrue(
					"Changing the TBR of the selected element should have set the field to null",
					container.getSelectedElement() == null);
		}
