			runnableContext.run(fork, false, monitor -> {
				IFileStore store = EFS.getLocalFileSystem().getStore(location);
				try (BufferedOutputStream out = new BufferedOutputStream(
						store.openOutputStream(EFS.NONE, monitor))) {
					out.write(newContent);
				} catch (CoreException | IOException e) {
					throw new InvocationTargetException(e);
