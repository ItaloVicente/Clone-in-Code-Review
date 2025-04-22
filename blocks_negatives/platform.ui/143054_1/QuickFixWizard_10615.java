		} catch (InvocationTargetException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							e.getLocalizedMessage(), e));
			return false;
		} catch (InterruptedException e) {
			StatusManager.getManager().handle(
					StatusUtil.newStatus(IStatus.ERROR,
							e.getLocalizedMessage(), e));
