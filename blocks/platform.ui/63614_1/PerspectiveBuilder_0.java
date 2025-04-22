			MPartSashContainer psc = modelService.createModelElement(MPartSashContainer.class);
			psc.setHorizontal(true);
			psc.setContainerData(Integer.toString(5000));
			stack.setContainerData(Integer.toString(2500));
			psc.getChildren().add(stack);
			List<MPartSashContainer> list = modelService.findElements(perspective, null,
					MPartSashContainer.class, null);
			if (list == null || list.size() == 0) {
				perspective.getChildren().add(psc);
			} else {
				int size = list.size();
				MPartSashContainer container = list.get(size - 1);
				container.getChildren().add(psc);
			}
