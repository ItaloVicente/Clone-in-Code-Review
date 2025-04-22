				StyledString string = new StyledString(directory
						.getParentFile().getName());
				string
						.append(
								" - " + directory.getAbsolutePath(), StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
				string
						.append(
								" [" + repository.getBranch() + "]", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$//$NON-NLS-2$
