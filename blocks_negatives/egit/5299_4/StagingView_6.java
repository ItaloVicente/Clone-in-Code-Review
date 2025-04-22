		} else {
			if (!headCommitChanged && oldState.getAmend())
				commitMessageComponent.setAmending(true);
			else
				commitMessageComponent.setAmending(false);
		}
