			type = IMessageProvider.INFORMATION;
		} else {
			CommitStatus status = commitMessageComponent.getStatus();
			message = status.getMessage();
			type = status.getMessageType();
		}
