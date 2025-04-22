				if (TransferConfig.ProtocolVersion.V2
						.equals(getProtocolVersion())) {
					String header = pckIn.readString();
					if (PacketLineIn.isEnd(header)) {
						return;
					}

						throw new PackProtocolException(header.substring(4));
					}
					if (!GitProtocolConstants.SECTION_PACKFILE.equals(header)) {
						throw new PackProtocolException(MessageFormat.format(
								JGitText.get().expectedGot,
								GitProtocolConstants.SECTION_PACKFILE, header));
					}
				}
