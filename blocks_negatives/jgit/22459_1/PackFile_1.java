				if (Integer.MAX_VALUE <= sz)
					return delta.large(this, curs);

				final byte[] result;
				try {
					result = new byte[(int) sz];
				} catch (OutOfMemoryError tooBig) {
					return delta.large(this, curs);
				}

