				break;
			}

			if (System.currentTimeMillis() - t > T_MAX) {
				break;
			}
		}
	}

	@Override
	public void setBlocked(IStatus reason) {
		Dialog.getBlockedHandler().showBlocked(this, reason, taskName);
	}

	@Override
	public void setCanceled(boolean b) {
		super.setCanceled(b);
		taskName = null;
		runEventLoop();
	}

	@Override
	public void setTaskName(String name) {
		super.setTaskName(name);
		taskName = name;
		runEventLoop();
	}

	@Override
	public void subTask(String name) {
		if (taskName == null) {
