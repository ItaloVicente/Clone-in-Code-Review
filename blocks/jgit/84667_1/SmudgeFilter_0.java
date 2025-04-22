			try {
				while ((b = in.read()) != -1) {
					out.write(b);
				}
			} finally {
				in.close();
				out.close();
