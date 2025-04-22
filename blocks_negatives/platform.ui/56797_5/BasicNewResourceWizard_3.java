            ISetSelectionTarget target = null;
            if (part instanceof ISetSelectionTarget) {
				target = (ISetSelectionTarget) part;
			} else {
				target = part.getAdapter(ISetSelectionTarget.class);
			}
