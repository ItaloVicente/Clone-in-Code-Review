			Object fileObject = fileRevisionElement.getFileRevision();
			if (fileObject instanceof LocalFileRevision){
				try {
					IStorage storage = ((LocalFileRevision) fileObject).getStorage(new NullProgressMonitor());
					if (AdapterUtils.adapt(storage, IFileState.class) != null) {
						return UIText.GitCompareFileRevisionEditorInput_LocalRevision;
					} else if (AdapterUtils.adapt(storage, IFile.class) != null) {
						return UIText.GitCompareFileRevisionEditorInput_CurrentRevision;
					}
				} catch (CoreException e) {
					Activator
							.logError(
									UIText.GitCompareFileRevisionEditorInput_contentIdentifier,
									e);
				}
			} else {
				return fileRevisionElement.getContentIdentifier();
			}
