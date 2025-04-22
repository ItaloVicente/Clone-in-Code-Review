			Policy.setLog(new ILogger() {
				@Override
				public void log(IStatus status) {
					if (status != null && status.getSeverity() == IStatus.ERROR && status.getPlugin().equals(Policy.JFACE)) {
						errorLogged[0] = true;
					}
				}} );
