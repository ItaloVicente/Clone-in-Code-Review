		} else if (element instanceof WorkingDirNode) {
			Repository repository = ((WorkingDirNode) element).getRepository();
			if (repository != null) {
				decoratedLabel.append(" - ", StyledString.QUALIFIER_STYLER) //$NON-NLS-1$
						.append(repository.getWorkTree().getAbsolutePath(),
								StyledString.QUALIFIER_STYLER);
