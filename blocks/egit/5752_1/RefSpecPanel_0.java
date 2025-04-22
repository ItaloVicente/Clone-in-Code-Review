		} else if (!srcWildcard && !isRemoteRef(src))
			setControlDecoration(
					creationSrcDecoration,
					FieldDecorationRegistry.DEC_ERROR,
					NLS
							.bind(
									UIText.RefSpecPanel_validationRefNonExistingRemote,
									src));
		else if (srcWildcard && !isMatchingAny(src, remoteRefNames)) {
			setControlDecoration(
					creationSrcDecoration,
					FieldDecorationRegistry.DEC_WARNING,
					NLS
							.bind(
									UIText.RefSpecPanel_validationRefNonMatchingRemote,
									src));
			srcOk = true;
