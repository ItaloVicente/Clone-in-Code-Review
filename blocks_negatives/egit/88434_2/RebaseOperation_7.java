
				} catch (NoHeadException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (RefNotFoundException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (JGitInternalException e) {
					throw new CoreException(Activator.error(e.getMessage(), e));
				} catch (GitAPIException e) {
