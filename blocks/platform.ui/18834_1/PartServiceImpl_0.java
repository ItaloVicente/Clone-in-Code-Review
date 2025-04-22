			if (p != null) {
				MPerspective persp = modelService.getPerspectiveFor(p);
				boolean inCurrentPerspective = persp == null
						|| persp == persp.getParent().getSelectedElement();
				if (inCurrentPerspective) {
					activate(p, true, true);
				}
			} else {
				activate(p, true, true);
			}
