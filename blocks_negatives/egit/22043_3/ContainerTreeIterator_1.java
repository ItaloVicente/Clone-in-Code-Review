			if (rsrc.getType() == IResource.FILE)
				try {
					return ((IFile) rsrc).getContents(true);
				} catch (CoreException err) {
					final IOException ioe = new IOException(err.getMessage());
					ioe.initCause(err);
					throw ioe;
				}
