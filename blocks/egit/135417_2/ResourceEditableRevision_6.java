			runnableContext.run(fork, false, monitor -> {
				try {
					file.setContents(new ByteArrayInputStream(newContent),
							false, true, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
