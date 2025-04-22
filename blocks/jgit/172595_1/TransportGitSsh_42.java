				RemoteSession session = getSession();
				TransferConfig.ProtocolVersion gitProtocol = protocol;
				if (gitProtocol == null) {
					gitProtocol = TransferConfig.ProtocolVersion.V2;
				}
				if (session instanceof RemoteSession2
						&& TransferConfig.ProtocolVersion.V2
								.equals(gitProtocol)) {
					process = ((RemoteSession2) session).exec(
							commandFor(getOptionUploadPack())
									.singletonMap(
											GitProtocolConstants.PROTOCOL_ENVIRONMENT_VARIABLE
											GitProtocolConstants.VERSION_2_REQUEST)
							getTimeout());
				} else {
					process = session.exec(commandFor(getOptionUploadPack())
							getTimeout());
				}
