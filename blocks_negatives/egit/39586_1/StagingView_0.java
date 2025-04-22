		if (!addPaths.isEmpty())
			try {
				AddCommand add = git.add();
				for (String addPath : addPaths)
					add.addFilepattern(addPath);
				add.call();
			} catch (NoFilepatternException e1) {
			} catch (JGitInternalException e1) {
				Activator.handleError(e1.getCause().getMessage(),
						e1.getCause(), true);
			} catch (Exception e1) {
				Activator.handleError(e1.getMessage(), e1, true);
			}
		if (!rmPaths.isEmpty())
			try {
				RmCommand rm = git.rm().setCached(true);
				for (String rmPath : rmPaths)
					rm.addFilepattern(rmPath);
				rm.call();
			} catch (NoFilepatternException e) {
			} catch (JGitInternalException e) {
				Activator.handleError(e.getCause().getMessage(), e.getCause(),
						true);
			} catch (Exception e) {
				Activator.handleError(e.getMessage(), e, true);
			}
