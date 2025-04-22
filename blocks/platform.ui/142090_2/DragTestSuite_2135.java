	}

	private TestDropLocation[] getDetachedWindowDropTargets(IWorkbenchWindowProvider dragSource) {
		TestDropLocation[] targets = new TestDropLocation[] {
			new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1, SWT.CENTER),
			new ViewDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId3, SWT.CENTER),
			new ViewTabDropTarget(dragSource, DragDropPerspectiveFactory.dropViewId1),
			new DetachedDropTarget()
		};
