				IResource resource = getResource();
				if (resource instanceof IFile) {
					FileOutputStream out = null;
					File file = path.toFile();
					try {
						if (!file.exists())
							FileUtils.createNewFile(file);
						out = new FileOutputStream(file);
						out.write(getContent());
						fDirty = false;
					} catch (IOException e) {
						throw new CoreException(
								new Status(
										IStatus.ERROR,
										Activator.getPluginId(),
										UIText.LocalNonWorkspaceTypedElement_errorWritingContents,
										e));
					} finally {
						fireContentChanged();
						RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
						if (mapping != null) {
							mapping.getRepository().fireEvent(new IndexChangedEvent());
