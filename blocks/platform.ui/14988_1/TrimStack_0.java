			if (minimizedElement instanceof MPartStack) {
				MPartStack theStack = (MPartStack) minimizedElement;
				MStackElement curSel = theStack.getSelectedElement();
				if (curSel instanceof MPart) {
					partService.activate((MPart) curSel);
				} else if (curSel instanceof MPlaceholder) {
					MPlaceholder ph = (MPlaceholder) curSel;
					if (ph.getRef() instanceof MPart) {
						partService.activate((MPart) ph.getRef());
					}
				}
			}

