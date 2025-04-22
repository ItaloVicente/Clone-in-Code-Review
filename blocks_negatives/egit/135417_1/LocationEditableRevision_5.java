			runnableContext.run(fork, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					IFileStore store = EFS.getLocalFileSystem().getStore(
							location);
					BufferedOutputStream out = null;
					try {
						out = new BufferedOutputStream(store.openOutputStream(
								0, monitor));
						out.write(newContent);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					} catch (IOException e) {
						throw new InvocationTargetException(e);
					} finally {
						if (out != null) {
							try {
								out.close();
							} catch (IOException e) {
							}
						}
					}
