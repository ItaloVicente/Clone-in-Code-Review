					ObjectId id = notFound.getObjectId();
					if (wantIds.contains(id)) {
						String msg = MessageFormat.format(
								JGitText.get().wantNotValid
						pckOut.writeString("ERR " + msg);
						throw new PackProtocolException(msg
