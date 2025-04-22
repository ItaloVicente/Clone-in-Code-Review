		} catch (ExecutionException e) {
			StatusManager.getManager().handle(StatusUtil.newStatus(IStatus.ERROR, "Failed to execute item " //$NON-NLS-1$
					+ getId(), e));
		} catch (NotDefinedException e) {
			StatusManager.getManager().handle(StatusUtil.newStatus(IStatus.ERROR, "Failed to execute item " //$NON-NLS-1$
					+ getId(), e));
		} catch (NotEnabledException e) {
			StatusManager.getManager().handle(StatusUtil.newStatus(IStatus.ERROR, "Failed to execute item " //$NON-NLS-1$
					+ getId(), e));
		} catch (NotHandledException e) {
