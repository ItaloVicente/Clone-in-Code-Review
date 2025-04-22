			MPerspective thePersp = modelService.getPerspectiveFor(toBeRemoved);
			if (needNewSel) {
				if (parent.getSelectedElement() == toBeRemoved) {
					MUIElement candidate = partActivationHistory.getSiblingSelectionCandidate(part);
					candidate = candidate == null ? null
							: candidate.getCurSharedRef() == null ? candidate : candidate
									.getCurSharedRef();
					if (candidate != null && children.contains(candidate)) {
						parent.setSelectedElement(candidate);
					} else {
						for (MUIElement child : children) {
							if (child != toBeRemoved && child.isToBeRendered()) {
								parent.setSelectedElement(child);
								break;
							}
						}
					}
				}
