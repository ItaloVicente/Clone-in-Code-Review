			setImages(contributionParameters.serviceLocator, contributionParameters.iconStyle);

			if (contributionParameters.helpContextId == null) {
				try {
					this.helpContextId = commandService.getHelpContextId(contributionParameters.commandId);
				} catch (NotDefinedException e) {
