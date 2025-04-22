
	@SuppressWarnings("deprecation")
	private static List<NamedFactory<Signature>> getSignatureFactories() {
		return Arrays.asList(
				BuiltinSignatures.nistp256_cert
				BuiltinSignatures.nistp384_cert
				BuiltinSignatures.nistp521_cert
				BuiltinSignatures.ed25519_cert
				BuiltinSignatures.rsaSHA512_cert
				BuiltinSignatures.rsaSHA256_cert
				BuiltinSignatures.rsa_cert
				BuiltinSignatures.nistp256
				BuiltinSignatures.nistp384
				BuiltinSignatures.nistp521
				BuiltinSignatures.ed25519
				BuiltinSignatures.sk_ecdsa_sha2_nistp256
				BuiltinSignatures.sk_ssh_ed25519
				BuiltinSignatures.rsaSHA512
				BuiltinSignatures.rsaSHA256
				BuiltinSignatures.rsa
				BuiltinSignatures.dsa_cert
				BuiltinSignatures.dsa);
	}
