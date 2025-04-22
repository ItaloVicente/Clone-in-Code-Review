		EObject eObj = (EObject) changedElement;
		if (!(eObj.eContainer() instanceof MWindow)) {
			return;
		}
		System.out.println("Relevnt " + called++); //$NON-NLS-1$
		MWindow hostingWindow = (MWindow) eObj.eContainer();
		hostingWindow.getSharedElements().remove(changedElement);
		changedElement.getTags().remove(ModelServiceImpl.HOSTED_ELEMENT);
