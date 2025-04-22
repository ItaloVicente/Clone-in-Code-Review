			if (!isModified()) {
				if (dragElementLocation == EModelService.IN_SHARED_AREA) {
					MArea area = (MArea) relToElement;
					relToElement = area.getChildren().get(0);
				} else if (dragElementLocation == EModelService.IN_ACTIVE_PERSPECTIVE) {
					relToElement = relToElement.getCurSharedRef();
				}
			} else {
				if (dragElementLocation == EModelService.IN_SHARED_AREA) {
					relToElement = relToElement.getCurSharedRef();
				} else if (dragElementLocation == EModelService.IN_ACTIVE_PERSPECTIVE) {
					MArea area = (MArea) relToElement;
					relToElement = area.getChildren().get(0);
				}
