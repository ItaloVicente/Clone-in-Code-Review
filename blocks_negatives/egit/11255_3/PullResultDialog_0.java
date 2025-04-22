			GridLayoutFactory.swtDefaults().applyTo(mergeResultGroup);
			Status status = result.getRebaseResult().getStatus();
			switch (status) {
			case OK:
			case FAST_FORWARD:
			case UP_TO_DATE:
			case FAILED:
			case ABORTED:
				break;
			case STOPPED:
				Label errorLabel = new Label(mergeResultGroup, SWT.NONE);
				errorLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
				Text errorText = new Text(mergeResultGroup, SWT.READ_ONLY);
				errorText.setText(UIText.PullResultDialog_RebaseStoppedMessage);
				break;
			}
			Label statusLabel = new Label(mergeResultGroup, SWT.NONE);
			statusLabel.setText(UIText.PullResultDialog_RebaseStatusLabel);
			Text statusText = new Text(mergeResultGroup, SWT.READ_ONLY);
			statusText.setText(status.name());
