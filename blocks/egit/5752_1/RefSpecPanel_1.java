		} else if (src == null || src.length() == 0)
			return UIText.RefSpecPanel_validationSrcUpdateRequired;
		else if (!wildcard && !isRemoteRef(src))
			return NLS
					.bind(
							UIText.RefSpecPanel_validationRefNonExistingRemote,
							src);
