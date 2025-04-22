						if (log.isTraceEnabled()) {
							log.trace(
									"Ignoring SSH agent {} key not in explicit IdentityFile in SSH config: {}"
									KeyUtils.getKeyType(pk)
									KeyUtils.getFingerPrint(pk));
						}
