			if (INDEX.equals(castElement.getContentIdentifier()))
				return NLS.bind(
						UIText.GitCompareFileRevisionEditorInput_StagedVersion,
						element.getName());
			else
				return NLS.bind(
						UIText.GitCompareFileRevisionEditorInput_RevisionLabel,
						new Object[] {
								element.getName(),
								CompareUtils.truncatedRevision(castElement
										.getContentIdentifier()),
								castElement.getAuthor() });

		} else if (element instanceof LocalResourceTypedElement)
