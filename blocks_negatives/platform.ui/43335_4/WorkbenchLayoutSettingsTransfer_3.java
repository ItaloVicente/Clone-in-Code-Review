			File deltas = new File(currentLocation.toOSString(), "deltas.xml"); //$NON-NLS-1$
			if (deltas.exists()) {
				byte[] bytes = new byte[8192];
				FileInputStream inputStream = new FileInputStream(deltas);
				FileOutputStream outputStream = new FileOutputStream(new File(workspaceFile,
				int read = inputStream.read(bytes, 0, 8192);
				while (read != -1) {
					outputStream.write(bytes, 0, read);
					read = inputStream.read(bytes, 0, 8192);
