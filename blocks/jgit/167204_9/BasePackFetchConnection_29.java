				case ACK_EOF:
					if (TransferConfig.ProtocolVersion.V0
							.equals(getProtocolVersion())) {
						throw new PackProtocolException(
								JGitText.get().expectedACKNAKFoundEOF);
					}
					resultsPending--;
					break READ_RESULT;

