				needsRefresh = warningLabel.hideMessage();
			}
		}
		if (needsRefresh) {
			System.out.println("Redrawing"); //$NON-NLS-1$
			commitMessageSection.redraw();
