	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void warn(String msg) {
		synchronized (warnings) {
			warnings.add(new Warning(msg));
		}
	}

	@Override
	public void warn(String format
		warn(format
	}

	@Override
	public void warn(String format
