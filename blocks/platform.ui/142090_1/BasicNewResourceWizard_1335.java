			if (target != null) {
				final ISetSelectionTarget finalTarget = target;
				window.getShell().getDisplay().asyncExec(() -> finalTarget.selectReveal(selection));
			}
		}
	}
