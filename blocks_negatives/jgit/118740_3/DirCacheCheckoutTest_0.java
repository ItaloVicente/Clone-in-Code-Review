					FileInputStream is = new FileInputStream(file);
					byte[] buffer = new byte[(int) file.length()];
					int offset = 0;
					int numRead = 0;
					while (offset < buffer.length
							&& (numRead = is.read(buffer, offset, buffer.length
									- offset)) >= 0) {
						offset += numRead;
