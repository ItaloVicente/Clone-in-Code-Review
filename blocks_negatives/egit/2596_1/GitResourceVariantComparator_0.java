				if (resource.getType() == IResource.FILE) {
					IFile file = ((IFile) resource);
					if (!file.isSynchronized(0))
						file.refreshLocal(0, null);

					return file.getContents();
				} else
