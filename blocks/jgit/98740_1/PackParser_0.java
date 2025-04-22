			byte[] data = inflateAndReturn(Source.DATABASE
			if (objCheck != null) {
				try {
					objCheck.check(obj
				} catch (CorruptObjectException e) {
					if (e.getErrorType() != null) {
						throw e;
					}
					throw new CorruptObjectException(MessageFormat.format(
							JGitText.get().invalidObject
							Constants.typeString(info.type)
							readCurs.abbreviate(obj
							e.getMessage())
				}
			}
