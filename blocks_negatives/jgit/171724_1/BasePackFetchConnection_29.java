			if (needsAcknowledgementV2 && TransferConfig.ProtocolVersion.V2
					.equals(getProtocolVersion())) {
				String header = pckIn.readString();
				if (!GitProtocolConstants.SECTION_ACKNOWLEDGMENTS
						.equals(header)) {
					throw new PackProtocolException(MessageFormat.format(
							JGitText.get().expectedGot,
							GitProtocolConstants.SECTION_ACKNOWLEDGMENTS,
							header));
				}
				needsAcknowledgementV2 = false;
			}
