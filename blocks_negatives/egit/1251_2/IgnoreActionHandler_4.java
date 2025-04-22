
			private void addIgnore(IProgressMonitor monitor, IResource resource)
					throws UnsupportedEncodingException, CoreException {
				IContainer container = resource.getParent();
				IFile gitignore = container.getFile(new Path(
						Constants.GITIGNORE_FILENAME));
				ByteArrayInputStream entryBytes = asStream(entry);

				if (gitignore.exists())
					gitignore.appendContents(entryBytes, true, true, monitor);
				else
					gitignore.create(entryBytes, true, monitor);
			}

			private ByteArrayInputStream asStream(String entry)
					throws UnsupportedEncodingException {
				return new ByteArrayInputStream(entry
						.getBytes(Constants.CHARACTER_ENCODING));
			}
