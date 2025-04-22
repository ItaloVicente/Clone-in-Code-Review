		} catch (CoreException exception) {
			handleCoreException(exception);
			return false;
		}
		return false;
	}

	protected abstract IBaseLabelProvider internalGetLabelProvider() throws CoreException;


	protected void handleCoreException(CoreException exception) {

		WorkbenchPlugin.log(exception);
		crashDisable();
	}

	public void crashDisable() {
		this.enabled = false;
	}

	public abstract boolean isFull();
