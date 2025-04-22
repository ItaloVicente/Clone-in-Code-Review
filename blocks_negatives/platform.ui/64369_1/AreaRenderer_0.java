			final MUIElement changedElement = (MUIElement) event
					.getProperty(EventTags.ELEMENT);
			if (!(changedElement instanceof MPartStack))
				return;

			MArea areaModel = findArea(changedElement);
			if (areaModel != null)
				synchCTFState(areaModel);
		}

		private MArea findArea(MUIElement element) {
			MUIElement parent = element.getParent();
			while (parent != null) {
				if (parent instanceof MArea)
					return (MArea) parent;
				parent = parent.getParent();
			}
			return null;
