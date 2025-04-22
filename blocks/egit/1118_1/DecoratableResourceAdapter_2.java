	private boolean compareContent(InputStream stream1, InputStream stream2) {
		try {
			byte[] remoteBytes = new byte[8096];
			byte[] bytes = new byte[8096];

			int remoteRead = stream2.read(remoteBytes);
			int read = stream1.read(bytes);
			if (remoteRead != read) {
				return false;
			}

			while (Arrays.equals(bytes, remoteBytes)) {
				remoteRead = stream2.read(remoteBytes);
				read = stream1.read(bytes);
				if (remoteRead != read) {
					return false;
				} else if (read == -1) {
					return Arrays.equals(bytes, remoteBytes);
				}
			}
		} catch (IOException e) {
			IStatus error = new Status(IStatus.ERROR, Activator
					.getPluginId(), e.getMessage(), e);
			Activator.getDefault().getLog().log(error);
			return true;
		}
		return true;

	}

