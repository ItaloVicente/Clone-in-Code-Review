        IStatus status = StatusUtil.newStatus(IStatus.ERROR, exception
                .getMessage(), exception);
		String message = NLS.bind(WorkbenchMessages.DecoratorWillBeDisabled,
				decorator.getName());
        WorkbenchPlugin.log(message, status);
        decorator.crashDisable();
    }
