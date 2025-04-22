		MPart part = current.getActiveLeaf().get(MPart.class);
		if (part == null)
			return null;
		MUIElement parent = part.getCurSharedRef() != null ? part.getCurSharedRef().getParent() : part.getParent();
		if (parent == null)
			return part;
		if (parent.getTags().contains(IPresentationEngine.MINIMIZED)
				&& !part.getTags().contains(IPresentationEngine.ACTIVE)) {
			return null;
		}
		return part;

