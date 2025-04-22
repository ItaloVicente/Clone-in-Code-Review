		if (element instanceof MPart) {
			PartLifeCycleState.transitionPartState((MPart) element,
					PartLifeCycleState.BROUGHT_TO_TOP);
		} else {
			UIEvents.publishEvent(UIEvents.UILifeCycle.BRINGTOTOP, element);
		}
