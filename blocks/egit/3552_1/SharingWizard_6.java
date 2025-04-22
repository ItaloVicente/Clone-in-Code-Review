			});
			return true;
		} catch (Throwable e) {
			if (e instanceof InvocationTargetException) {
				e = e.getCause();
			}
			if (e instanceof CoreException) {
				IStatus status = ((CoreException) e).getStatus();
				e = status.getException();
