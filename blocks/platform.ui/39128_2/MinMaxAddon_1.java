			if (MArea.class.isInstance(parentElement)
					&& parentElement.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				if (hasMinimizableChildren(MArea.class.cast(parentElement).getChildren().get(0))
						|| maximizeRestore)
					return element;
			}

