		} catch (InvocationTargetException e) {
			Activator.handleError(MessageFormat.format(
					UIText.CleanRepositoryPage_Failed,
					repository.getWorkTree().getAbsolutePath()),
				e.getCause(), true);
		} catch (InterruptedException e) {
