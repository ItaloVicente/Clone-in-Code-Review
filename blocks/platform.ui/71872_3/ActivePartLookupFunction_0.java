		MPart part = current.getActiveLeaf().get(MPart.class);
		if (part == null)
			return null;
		if (part.getCurSharedRef() != null
				&& part.getCurSharedRef().getParent().getTags().contains(IPresentationEngine.MINIMIZED)) {
			return null;
		}
		if (part.getParent() != null && part.getParent().getTags().contains(IPresentationEngine.MINIMIZED))
			return null;
		return part;

