				if (windows && isInvalidOnWindows(c)) {
					if (c > 31)
						throw new CorruptObjectException(String.format(
								"name contains '%c'"
					throw new CorruptObjectException(String.format(
							"name contains byte 0x%x"
				}
