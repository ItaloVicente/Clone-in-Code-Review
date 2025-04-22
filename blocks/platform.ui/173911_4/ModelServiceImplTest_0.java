
	@Test
	public void testRemovePerspectiveModelWhenPerspectiveHasNoParent() {
		var perspective = mock(MPerspective.class);
		doReturn(null).when(perspective).getParent();

		modelService.removePerspectiveModel(perspective, mock(MWindow.class));

		Mockito.verify(perspective, never()).setToBeRendered(false);
	}
