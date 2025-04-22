			if (MArea.class.isInstance(parentElement)
					&& parentElement.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				MArea area = MArea.class.cast(parentElement);
				if (!area.getChildren().isEmpty() && MPartSashContainer.class.isInstance(area.getChildren().get(0))
						&& hasMinimizableChildren(area.getChildren().get(0)))
					return element;
			}

