			if (e.getCause() instanceof CoreException) {
				Activator.showErrorStatus(e.getCause().getLocalizedMessage(),
						((CoreException) e.getCause()).getStatus());
			} else {
				Activator.showError(e.getCause().getLocalizedMessage(),
						e.getCause());
			}
