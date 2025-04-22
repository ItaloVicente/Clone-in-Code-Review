				if (Integer.MAX_VALUE < sz) {
					throw new CorruptObjectException("DIRC extension "
							+ formatExtensionName(hdr) + " is too large at "
							+ sz + " bytes.");
				}
				final byte[] raw = new byte[(int) sz];
