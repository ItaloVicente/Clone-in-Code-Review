	private void insert(MUIElement toInsert, MUIElement relTo,
			int swtSide, int ratio) {
		if (toInsert == null || relTo == null)
			return;

		MElementContainer<MUIElement> relParent = relTo.getParent();
		if (relParent != null) {
			List<MUIElement> children = relParent.getChildren();
			int index = children.indexOf(relTo);
			MPartSashContainer psc = modelService.createModelElement(MPartSashContainer.class);
			psc.setContainerData(relTo.getContainerData());
			relParent.getChildren().add(index + 1, psc);

			switch (swtSide) {
			case SWT.LEFT:
				psc.getChildren().add((MPartSashContainerElement) toInsert);
				psc.getChildren().add((MPartSashContainerElement) relTo);
				psc.setHorizontal(true);
				break;
			case SWT.RIGHT:
				psc.getChildren().add((MPartSashContainerElement) relTo);
				psc.getChildren().add((MPartSashContainerElement) toInsert);
				psc.setHorizontal(true);
				break;
			case SWT.TOP:
				psc.getChildren().add((MPartSashContainerElement) toInsert);
				psc.getChildren().add((MPartSashContainerElement) relTo);
				psc.setHorizontal(false);
				break;
			case SWT.BOTTOM:
				psc.getChildren().add((MPartSashContainerElement) relTo);
				psc.getChildren().add((MPartSashContainerElement) toInsert);
				psc.setHorizontal(false);
				break;
			}

			if (relTo.isToBeRendered() || toInsert.isToBeRendered()) {
				resetToBeRenderedFlag(psc, true);
			} else {
				psc.setToBeRendered(false);
			}
			return;
		}
	}

	private void insert(MUIElement toInsert, MUIElement relTo,
			int swtSide, float ratio) {
		int pct = (int) (ratio * 10000);
		insert(toInsert, relTo, swtSide, pct);
	}

