		if (canExecute && EXECUTE_FOR_OTHERS != null) {
			if (!isFile(f))
				return false;
			boolean ownerOnly = !EXECUTE_FOR_OTHERS.booleanValue();
			return f.setExecutable(canExecute
		}
