		} else if (fileObject instanceof IndexFileRevision) {
			if (isEditable(element))
				return NLS.bind(
						UIText.GitCompareFileRevisionEditorInput_IndexEditableLabel,
						element.getName());
			else
				return NLS.bind(
						UIText.GitCompareFileRevisionEditorInput_IndexLabel,
						element.getName());
