					byte[] bs = IO.readFully(f);
					File target = getFile(fh.getNewPath(), true);
					FileOutputStream fos = new FileOutputStream(target);
					try {
						fos.write(bs);
					} finally {
						fos.close();
					}
