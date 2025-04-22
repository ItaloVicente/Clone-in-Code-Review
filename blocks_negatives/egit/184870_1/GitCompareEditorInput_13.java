				try {
					return buildDiffContainer(baseCommit, compareCommit,
							monitor);
				} catch (IOException e) {
					throw new InvocationTargetException(e);
				}
			} finally {
				monitor.done();
