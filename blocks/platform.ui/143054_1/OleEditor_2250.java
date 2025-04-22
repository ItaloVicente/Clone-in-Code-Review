			IResourceDelta mainDelta = event.getDelta();
			if (mainDelta == null)
				return;
			IResourceDelta affectedElement = mainDelta.findMember(resource
					.getFullPath());
			if (affectedElement != null)
				processDelta(affectedElement);
		}

		private boolean processDelta(final IResourceDelta delta) {

			Runnable changeRunnable = null;

			switch (delta.getKind()) {
			case IResourceDelta.REMOVED:
				if ((IResourceDelta.MOVED_TO & delta.getFlags()) != 0) {
