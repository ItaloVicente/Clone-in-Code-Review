			MPart newActivePart = perspective.getContext().getActiveLeaf().get(MPart.class);
			if (newActivePart == null) {
				MPart candidate = partActivationHistory.getActivationCandidate(perspective);
				if (candidate != null) {
					modelService.bringToTop(candidate);
					activate(candidate, true, false);
					return;
				}
			}
