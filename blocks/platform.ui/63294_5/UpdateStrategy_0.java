	protected IStatus logErrorWhileSettingValue(Exception ex) {
		IStatus errorStatus = ValidationStatus
				.error(BindingMessages.getString(BindingMessages.VALUEBINDING_ERROR_WHILE_SETTING_VALUE), ex);
		Policy.getLog().log(errorStatus);
		return errorStatus;
	}

	public Object convert(Object value) {
		if (converter != null) {
			try {
				return converter.convert(value);
			} catch (Exception ex) {
				Policy.getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
			}
		}
		return value;
	}

