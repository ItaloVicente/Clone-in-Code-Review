			byte[] bytes = new byte[0];
			InputStream is = null;
			try {
				is = file.getContents();
				bytes = new byte[is.available()];
				is.read(bytes);
			} finally {
				if (is != null)
					is.close();
			}
