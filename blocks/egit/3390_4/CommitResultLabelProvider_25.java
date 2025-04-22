			if (layout == AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT)
				styled.append(MessageFormat.format(
						UIText.CommitResultLabelProvider_SectionRepository,
						commit.getRepositoryName()),
						StyledString.DECORATIONS_STYLER);
		} else if (element instanceof RepositoryMatch) {
			RepositoryMatch repository = (RepositoryMatch) element;
			styled.append(repository.getLabel(repository));
			styled.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			styled.append(repository.getRepository().getDirectory()
					.getAbsolutePath(), StyledString.QUALIFIER_STYLER);
			styled.append(MessageFormat.format(" ({0})", //$NON-NLS-1$
					Integer.valueOf(repository.getMatchCount())),
					StyledString.COUNTER_STYLER);
