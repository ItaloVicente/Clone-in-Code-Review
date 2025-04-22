				try {
					fLck = os.getChannel().tryLock();
					if (fLck == null)
						throw new OverlappingFileLockException();
				} catch (OverlappingFileLockException ofle) {
					haveLck = false;
					try {
						os.close();
					} catch (IOException ioe) {
					}
					os = null;
				}
