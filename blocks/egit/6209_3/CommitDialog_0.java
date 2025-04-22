			type = IMessageProvider.INFORMATION;
		} else {
			commitMessageComponent.validate();
			message = commitMessageComponent.getMessage();
			type = commitMessageComponent.getMessageType();
		}
