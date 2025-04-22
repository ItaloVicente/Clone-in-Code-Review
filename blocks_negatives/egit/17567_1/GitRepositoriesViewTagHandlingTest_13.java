		for (String ref : repository.getTags().keySet()) {
			RefUpdate op = repository.updateRef(ref, true);
			op.setRefLogMessage("tag deleted", //$NON-NLS-1$
					false);
			op.setForceUpdate(true);
			op.delete();
		}
