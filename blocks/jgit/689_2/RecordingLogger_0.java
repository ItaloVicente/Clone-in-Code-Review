
	public void warn(String msg
		synchronized (warnings) {
			warnings.add(new Warning(MessageFormat.format(msg
		}
	}

	public void warn(Throwable thrown) {
		synchronized (warnings) {
			warnings.add(new Warning(thrown));
		}
	}

	public void info(String msg
	}

	public void info(Throwable thrown) {
	}

	public void info(String msg
	}

	public void debug(String msg
	}

	public void debug(Throwable thrown) {
	}
