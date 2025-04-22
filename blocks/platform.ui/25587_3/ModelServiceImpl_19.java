			combine(toInsert, relTo, newSash, insertBefore, ratio);
		}

		if (relToParent != null) {
			return;
		}

		MUIElement insertRoot = relTo.getParent();
		if (insertRoot instanceof MPerspective) {
			insertRoot = relTo;
		}
		while (insertRoot != null && !(insertRoot instanceof MWindow)
				&& !(insertRoot instanceof MPerspective)
				&& !(insertRoot instanceof MPartSashContainer)) {
			relTo = (MPartSashContainerElement) insertRoot;
			insertRoot = insertRoot.getParent();
		}

		if (insertRoot instanceof MWindow || insertRoot instanceof MArea
				|| insertRoot instanceof MPerspective) {
			MPartSashContainer newSash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
			newSash.setHorizontal(where == LEFT_OF || where == RIGHT_OF);
			combine(toInsert, relTo, newSash, insertBefore, ratio);
		} else if (insertRoot instanceof MGenericTile<?>) {
			MGenericTile<MUIElement> curTile = (MGenericTile<MUIElement>) insertRoot;

			if (curTile.isHorizontal() && (where == ABOVE || where == BELOW)) {
				MPartSashContainer newSash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
				newSash.setHorizontal(false);
				newSash.setContainerData(relTo.getContainerData());
				combine(toInsert, relTo, newSash, insertBefore, ratio);
			} else if (!curTile.isHorizontal() && (where == LEFT_OF || where == RIGHT_OF)) {
				MPartSashContainer newSash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
				newSash.setHorizontal(true);
				newSash.setContainerData(relTo.getContainerData());
				combine(toInsert, relTo, newSash, insertBefore, ratio);
			} else {
				int relToIndex = relTo.getParent().getChildren().indexOf(relTo);
				if (insertBefore) {
					curTile.getChildren().add(relToIndex, toInsert);
				} else {
					curTile.getChildren().add(relToIndex + 1, toInsert);
				}

				int relToWeight = 10000;
				if (relTo.getContainerData() != null) {
					try {
						relToWeight = Integer.parseInt(relTo.getContainerData());
					} catch (NumberFormatException e) {
					}
				}
				int toInsertWeight = (int) ((ratio / 100.0) * relToWeight + 0.5);
				relToWeight = relToWeight - toInsertWeight;
				relTo.setContainerData(Integer.toString(relToWeight));
				toInsert.setContainerData(Integer.toString(toInsertWeight));
			}
		}
	}
