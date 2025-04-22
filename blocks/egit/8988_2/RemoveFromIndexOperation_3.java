			ResetCommand resetCommand = new Git(repository).reset();
			resetCommand.setRef(HEAD);
			for (String path : paths)
				if (path == "") // Working directory //$NON-NLS-1$
					resetCommand.addPath("."); //$NON-NLS-1$
				else
					resetCommand.addPath(path);

			try {
				resetCommand.call();
				monitor.worked(1);
			} catch (GitAPIException e) {
				Activator.logError(e.getMessage(), e);
			} finally {
				findRepositoryMapping(repository).fireRepositoryChanged();
			}
