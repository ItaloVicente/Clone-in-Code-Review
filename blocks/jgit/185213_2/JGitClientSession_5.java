										this
							}
						} else {
							newKexFactories
									.add(ClientBuilder.DH2KEX.apply(factory));
						}
					}
				});
				if (!newKexFactories.isEmpty()) {
					newKexFactories.addAll(kexFactories);
					setKeyExchangeFactories(newKexFactories);
				}
				return result;
			}
			log.warn(format(SshdText.get().configNoKnownAlgorithms
					SshConstants.KEX_ALGORITHMS
		}
		return defaultKexMethods;
	}

	@Override
	protected String resolveSessionKexProposal(String hostKeyTypes)
			throws IOException {
		String kexMethods = String.join("
