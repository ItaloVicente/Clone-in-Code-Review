		while (parent != null && !(parent instanceof MWindow)) {
			if (MArea.class.isInstance(parent)
					&& parent.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG))
				parent = MArea.class.cast(parent).getCurSharedRef();
			else
				parent = parent.getParent();
		}
