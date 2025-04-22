			MPerspectiveStack perspectives = getPerspectiveStack();
			for (MPerspective mperspective : perspectives.getChildren()) {
				if (mperspective.getElementId().equals(perspective.getId())) {
					((ModelServiceImpl) modelService).handleNullRefPlaceHolders(mperspective,
							window);
				}
			}
