		} catch (NoRemoteRepositoryException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (TransportException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfFetchCommand
					e);
