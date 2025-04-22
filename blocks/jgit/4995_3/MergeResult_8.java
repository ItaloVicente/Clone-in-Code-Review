
	public boolean isSuccessful() {
		for (MergeStatus ms : mergeStatus) {
			if (!ms.isSuccessful())
				return false;
		}
		return true;
	}

	public String name() {
		StringBuilder sb = new StringBuilder();
		for (Iterator i = mergeStatus.iterator(); i.hasNext();) {
			MergeStatus ms = (MergeStatus) i.next();
			sb.append(ms.name());
			if (i.hasNext())
				sb.append(" ");
		}
		return sb.toString();
	}
