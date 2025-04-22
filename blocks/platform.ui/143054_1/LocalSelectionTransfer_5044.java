		Object result = super.nativeToJava(transferData);
		if (result != null && isInvalidNativeType(result)) {
			Policy.getLog().log(new Status(
							IStatus.ERROR,
							Policy.JFACE,
							IStatus.ERROR,
							JFaceResources.getString("LocalSelectionTransfer.errorMessage"), null)); //$NON-NLS-1$
		}
		return selection;
	}

	public void setSelection(ISelection s) {
		selection = s;
	}

	public long getSelectionSetTime() {
		return selectionSetTime;
	}

	public void setSelectionSetTime(long time) {
		selectionSetTime = time;
	}
