		while (parent != null && !(parent instanceof MWindow)) {
			if (parent instanceof MArea
					&& parent.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG))
				parent = ((MArea) parent).getCurSharedRef();
			else
				parent = parent.getParent();
		}
