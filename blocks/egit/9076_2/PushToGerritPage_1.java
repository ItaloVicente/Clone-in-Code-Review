
			String reviewer = reviewerText.getText();
			if (reviewer != null && reviewer.length() > 0) {
				op.setReceivePack(String.format("git receive-pack --reviewer=%s", reviewer)); //$NON-NLS-1$
			}

