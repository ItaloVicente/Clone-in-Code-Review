			if (mode == FileMode.SYMLINK) {
				return new ByteArrayInputStream(FS.DETECTED.readSymLink(
						asFile()).getBytes(Constants.CHARACTER_ENCODING));
			} else {
				if (rsrc.getType() == IResource.FILE)
					try {
						return ((IFile) rsrc).getContents(true);
					} catch (CoreException err) {
						final IOException ioe = new IOException(err.getMessage());
						ioe.initCause(err);
						throw ioe;
					}
			}
