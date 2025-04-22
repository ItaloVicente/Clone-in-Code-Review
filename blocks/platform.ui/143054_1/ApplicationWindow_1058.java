				mgr.setCancelEnabled(cancelWasEnabled);
				if (currentFocus != null && !currentFocus.isDisposed()) {
					currentFocus.forceFocus();
				}
			}
		} finally {
			operationInProgress = false;
		}
	}

	public void setStatus(String message) {
		if (statusLineManager != null) {
			statusLineManager.setMessage(message);
		}
	}

	protected boolean toolBarChildrenExist() {
		Control toolControl = getToolBarControl();
		if (toolControl instanceof ToolBar) {
			return ((ToolBar) toolControl).getItemCount() > 0;
		}
		return false;
	}

	protected boolean coolBarChildrenExist() {
		Control coolControl = getCoolBarControl();
		if (coolControl instanceof CoolBar) {
			return ((CoolBar) coolControl).getItemCount() > 0;
		}
		return false;
	}
