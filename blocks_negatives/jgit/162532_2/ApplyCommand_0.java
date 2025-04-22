					FileOutputStream fos = new FileOutputStream(target);
					try {
						fos.write(bs);
					} finally {
						fos.close();
					}
