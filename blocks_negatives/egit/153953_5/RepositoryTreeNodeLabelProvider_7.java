		StyledString decoratedLabel = super.getStyledText(element);
		String decoratedValue = decoratedLabel.getString();
		String simpleValue = labelProvider.getText(element);
		if (decoratedValue.equals(simpleValue)) {
			StyledString previousLabel = previousDecoratedLabels.get(element);
			if (previousLabel != null) {
				return previousLabel;
			}
		} else if (decoratedValue.trim().equals(simpleValue)) {
			decoratedLabel = labelProvider.getStyledText(element);
		}
		if (showPaths) {
			if (element instanceof RepositoryNode) {
				Repository repository = ((RepositoryNode) element)
						.getRepository();
				if (repository != null) {
					decoratedLabel.append(" - ", StyledString.QUALIFIER_STYLER) //$NON-NLS-1$
							.append(repository.getDirectory().getAbsolutePath(),
									StyledString.QUALIFIER_STYLER);
				}
			} else if (element instanceof WorkingDirNode) {
				Repository repository = ((WorkingDirNode) element)
						.getRepository();
				if (repository != null) {
					decoratedLabel.append(" - ", StyledString.QUALIFIER_STYLER) //$NON-NLS-1$
							.append(repository.getWorkTree().getAbsolutePath(),
									StyledString.QUALIFIER_STYLER);
				}
			}
		}
		previousDecoratedLabels.put(element, decoratedLabel);
		return decoratedLabel;
