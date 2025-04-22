			runnableContext.run(fork, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor myMonitor)
						throws InvocationTargetException, InterruptedException {
					try {
						file.setContents(new ByteArrayInputStream(newContent),
								false, true, myMonitor);
					} catch (CoreException e) {
						throw new InvocationTargetException(e);
					}
