			remoteRefs = rc.call();
		} catch (JGitInternalException e) {
			Throwable cause = e.getCause();
			if (cause != null)
				throw new InvocationTargetException(cause);
			else
				throw new InvocationTargetException(e);
		} catch (GitAPIException e) {
