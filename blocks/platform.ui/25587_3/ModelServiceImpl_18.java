		if (relTo instanceof MPartSashContainer
				&& directionsMatch((MPartSashContainer) relTo, horizontal)) {
			MPartSashContainer psc = (MPartSashContainer) relTo;
			int totalVisWeight = 0;
			for (MUIElement child : psc.getChildren()) {
				if (child.isToBeRendered()) {
					totalVisWeight += getWeight(child);
				}
			}
			int insertWeight = (int) ((totalVisWeight * ratio) / (1 - ratio));
			toInsert.setContainerData(Integer.toString(insertWeight));
			if (insertBefore) {
				psc.getChildren().add(0, toInsert);
			} else {
				psc.getChildren().add(toInsert);
			}
		} else if (relToParent instanceof MPartSashContainer && !(relToParent instanceof MArea)
				&& directionsMatch((MPartSashContainer) relToParent, horizontal)) {
			MPartSashContainer psc = (MPartSashContainer) relToParent;
			int relToIndex = psc.getChildren().indexOf(relTo);

			int relToWeight = getWeight(relTo);
			int insertWeight = (int) (relToWeight * ratio);
			toInsert.setContainerData(Integer.toString(insertWeight));
			relTo.setContainerData(Integer.toString(relToWeight - insertWeight));

			if (insertBefore) {
				psc.getChildren().add(relToIndex, toInsert);
			} else {
				int insertIndex = relToIndex + 1;
				if (insertIndex < psc.getChildren().size()) {
					psc.getChildren().add(insertIndex, toInsert);
				} else {
					psc.getChildren().add(toInsert);
				}
			}
		} else {
			MPartSashContainer newSash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
			newSash.setHorizontal(horizontal);

			newSash.setContainerData(relTo.getContainerData());
