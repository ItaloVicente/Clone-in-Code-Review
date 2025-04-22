					final File p = new File(db.getDirectory(), name);
					final LockFile lck = new LockFile(p);
					if (!lck.lock())
						throw new ObjectWritingException("Can't write " + p);
					try {
						lck.write(bin);
					} catch (IOException ioe) {
						throw new ObjectWritingException("Can't write " + p);
					}
					if (!lck.commit())
						throw new ObjectWritingException("Can't write " + p);
