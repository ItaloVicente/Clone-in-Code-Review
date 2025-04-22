                    changeRunnable = () -> {
					    IPath path = delta.getMovedToPath();
					    IFile newFile = delta.getResource().getWorkspace()
					            .getRoot().getFile(path);
					    if (newFile != null && embeddedEditor != null) {
					        embeddedEditor
					                .sourceChanged(new FileInPlaceEditorInput(
					                        newFile));
					    }
					};
