
		boolean commitObserverConfirmed = true;
		for(CommitObserver co: commitObserver) {
			boolean commitAccepted = co.finaliceCommit(commitOperation);
			commitObserverConfirmed &= commitAccepted;

			if( !commitObserverConfirmed ) {
				String refuseReason = co.getRefuseReason();
				if( refuseReason != null ) {
					MessageDialog.openWarning(this.shell,
							"Commit Canceled", //$NON-NLS-1$
							refuseReason);
				}
				break;
			}
		}
		if( !commitObserverConfirmed ) {
			return;
		}

