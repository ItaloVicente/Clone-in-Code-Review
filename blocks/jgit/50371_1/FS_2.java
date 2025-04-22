			if (binary) {
				byte buffer[] = new byte[4096];
				int readBytes;
				while ((readBytes = in.read(buffer)) != -1) {
					if (!writeFailure && out != null) {
						try {
							out.write(buffer
							out.flush();
						} catch (IOException e) {
							writeFailure = true;
						}
