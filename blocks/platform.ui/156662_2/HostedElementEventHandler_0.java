		EObject eObj = (EObject) changedElement;
		if (!(eObj.eContainer() instanceof MWindow)) {
			return;
		}
		MWindow hostingWindow = (MWindow) eObj.eContainer();
		hostingWindow.getSharedElements().remove(changedElement);
		changedElement.getTags().remove(ModelServiceImpl.HOSTED_ELEMENT);
