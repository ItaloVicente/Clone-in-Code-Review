	public static DialogPageSupport createWithInitialMessageReset(DialogPage dialogPage, DataBindingContext dbc) {
		DialogPageSupport dialogPageSupport = new DialogPageSupport(dialogPage, dbc);
		dialogPageSupport.setValidationMessageProvider(new ValidationMessageProvider(dialogPage));
		return dialogPageSupport;
	}

