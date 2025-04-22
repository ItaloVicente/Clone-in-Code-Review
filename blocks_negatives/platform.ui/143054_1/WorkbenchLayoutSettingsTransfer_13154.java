				FileInputStream inputStream = new FileInputStream(workbenchModel);
				FileOutputStream outputStream = new FileOutputStream(new File(workspaceFile,
				int read = inputStream.read(bytes, 0, 8192);
				while (read != -1) {
					outputStream.write(bytes, 0, read);
					read = inputStream.read(bytes, 0, 8192);
