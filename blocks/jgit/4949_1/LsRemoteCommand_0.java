		} catch (TransportException e) {
			Throwable cause = e.getCause();
			if (cause != null)
				throw new org.eclipse.jgit.api.errors.TransportException(
						e.getMessage()
						cause);
			else
				throw new org.eclipse.jgit.api.errors.TransportException(
						e.getMessage());
