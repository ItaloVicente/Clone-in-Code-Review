			boolean equalsBaseCommit;
			RevCommit objBaseCommit = objCommit.getBaseCommit();
			if (objBaseCommit != null)
				equalsBaseCommit = objBaseCommit.equals(baseCommit);
			else
				equalsBaseCommit = baseCommit == null;


			return equalsBaseCommit
					&& objCommit.getRemoteCommit().equals(remoteCommit)
					&& objCommit.getLocation().equals(getLocation());
