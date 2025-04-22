		if (fileRevision instanceof WorkspaceFileRevision) {
			return SaveableCompareEditorInput.createFileElement(node.getFile());
		} else if (fileRevision instanceof WorkingTreeFileRevision) {
			IPath path = Path
					.fromPortableString(((WorkingTreeFileRevision) fileRevision)
							.getURI().getPath());
			return new LocalNonWorkspaceTypedElement(getRepository(), path);
