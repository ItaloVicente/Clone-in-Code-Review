			} catch (InstantiationException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(IStatus.ERROR, e
								.getLocalizedMessage(), e), StatusManager.SHOW);
				return false;
			} catch (IllegalAccessException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(IStatus.ERROR, e
								.getLocalizedMessage(), e), StatusManager.SHOW);
