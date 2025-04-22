	public void testMoveElement() {
		Mutable element1 = new Mutable(1);
		Mutable element2 = new Mutable(2);
		Mutable element3 = new Mutable(3);
		int newIndex_2 = 2;
		int oldIndex_3 = 3;

		input.add(element1); // 0
		input.add(element3); // 1
		input.add(element2); // 2
		input.add(element1); // 3
		input.add(element3); // 4
		viewer.getTable().select(new int[] { 0, 1, oldIndex_3 });

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, oldIndex_3 }));
		assertThat((Mutable) viewer.getElementAt(oldIndex_3), is(element1));
		assertThat((Mutable) viewer.getElementAt(newIndex_2), is(element2));

		input.move(oldIndex_3, newIndex_2);

		assertThat(viewer.getTable().getSelectionIndices(), is(new int[] { 0, 1, newIndex_2 }));
		assertThat((Mutable) viewer.getElementAt(oldIndex_3), is(element2));
		assertThat((Mutable) viewer.getElementAt(newIndex_2), is(element1));
	}

