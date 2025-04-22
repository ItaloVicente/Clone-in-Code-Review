		}
	}

	private void removeTransientModelElements() {
		Collection<MApplicationElement> transientElements = new HashSet<>();
		TreeIterator<EObject> content = resource.getAllContents();
		while (content.hasNext()) {
			EObject element = content.next();

			if ((element instanceof MApplicationElement) && (!((MApplicationElement) element).isPersistable())) {
				transientElements.add((MApplicationElement) element);
			}
		}

		for (MApplicationElement element : transientElements) {
			if (element instanceof BasicEObjectImpl)
				((BasicEObjectImpl) element).eBasicRemoveFromContainer(null);
		}
