		}

		try {
			this.handler.execute(event);
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
