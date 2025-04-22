			if (parentElement instanceof MArea
					&& parentElement.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				MArea area = (MArea) parentElement;
				if (!area.getChildren().isEmpty() && hasMinimizableChildren(area.getChildren().get(0)))
					return element;
			}

