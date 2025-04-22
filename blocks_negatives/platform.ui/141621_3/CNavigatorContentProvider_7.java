		for (int i = 0; i < cChildren.length; i++) {
			if (cChildren[i] instanceof CElement) {
				IResource resource = ((CElement) cChildren[i]).getResource();
				if (resource != null) {
					proposedChildren.remove(resource);
				}
				proposedChildren.add(cChildren[i]);
			} else if (cChildren[i] != null) {
				proposedChildren.add(cChildren[i]);
			}
