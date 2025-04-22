		MPart editor = ems.createModelElement(MPart.class);
		editor.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);
		partStackForEditor.getChildren().add(editor);
		partStackForEditor.setSelectedElement(editor);

		ContextInjectionFactory.make(CleanupAddon.class, appContext);

		contextRule.createAndRunWorkbench(window);

		EPartService partService = window.getContext().get(EPartService.class);
		partService.hidePart(partA);
		contextRule.spinEventLoop();

		assertTrue(" PartStack with children should be rendered", partStackForPartBPartC.isToBeRendered());
		partService.hidePart(partB);
		partService.hidePart(partC);
		contextRule.spinEventLoop();
		assertTrue("CleanupAddon should ensure that partStack is not rendered anymore, as all childs have been removed",
				!partStackForPartBPartC.isToBeRendered());
		assertTrue("Part stack should be removed", !partStackForPartBPartC.isToBeRendered());
		partService.hidePart(editor, true);
		contextRule.spinEventLoop();
		assertTrue("PartStack with IPresentationEngine.NO_AUTO_COLLAPSE should not be closed if children are removed",
				partStackForEditor.isToBeRendered());

	}

	@Test
	public void testBug332463() {
