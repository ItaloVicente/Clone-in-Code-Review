		if (element instanceof MPart) {
			PartLifeCycleState.transitionPartState((MPart) element,
					PartLifeCycleState.UNRENDERED);
		}
	}

	@Override
	public void postProcess(MUIElement childElement) {
		if (childElement instanceof MPart) {
			PartLifeCycleState.transitionPartState((MPart) childElement,
					PartLifeCycleState.RENDERED);
		}
		super.postProcess(childElement);
