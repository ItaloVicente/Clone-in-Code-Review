					byte[] bs = IO.readFully(f);
					FileOutputStream fos = new FileOutputStream(getFile(
							fh.getNewPath(),
							true));
					try {
						fos.write(bs);
					} finally {
						fos.close();
					}
