		synchronized void keptFinished(JobTreeElement finished) {
			keptFinished.add(finished);
		}

		synchronized void keptRemoved(JobTreeElement removed) {
			keptRemoved.add(removed);
		}

