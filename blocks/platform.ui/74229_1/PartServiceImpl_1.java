		MPerspective thePersp = modelService.getPerspectiveFor(toBeRemoved);
		boolean needNewSel = thePersp == null || !thePersp.getTags().contains("PerspClosing"); //$NON-NLS-1$
		if (needNewSel) {
			if (parent.getSelectedElement() == toBeRemoved) {
				MUIElement candidate = partActivationHistory.getSiblingSelectionCandidate(part);
				candidate = candidate == null ? null
						: candidate.getCurSharedRef() == null ? candidate : candidate.getCurSharedRef();
				if (candidate != null && children.contains(candidate)) {
					parent.setSelectedElement(candidate);
