		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PendingSyncExec)) {
			return false;
		}
		return (runnable == ((PendingSyncExec) obj).runnable);
	}
