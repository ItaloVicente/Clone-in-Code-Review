			for (ReceiveCommand cmd : batch.getCommands()) {
				cmd.updateType(walk);
				if (cmd.getType() == UPDATE_NONFASTFORWARD
						&& cmd instanceof TrackingRefUpdate.Command
						&& !((TrackingRefUpdate.Command) cmd).canForceUpdate())
					cmd.setResult(REJECTED_NONFASTFORWARD);
			}
			if (transport.isDryRun()) {
				for (ReceiveCommand cmd : batch.getCommands()) {
					if (cmd.getResult() == NOT_ATTEMPTED)
						cmd.setResult(OK);
				}
			} else
				batch.execute(walk
		} catch (IOException err) {
			throw new TransportException(MessageFormat.format(
					JGitText.get().failureUpdatingTrackingRef
					getFirstFailedRefName(batch)
