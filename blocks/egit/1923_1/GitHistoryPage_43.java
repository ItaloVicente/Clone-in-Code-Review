			try {
				initAndStartRevWalk(true);
			} catch (IllegalStateException e) {
				Activator.handleError(e.getMessage(), e.getCause(), true);
				return false;
			}
