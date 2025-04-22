
	@NonNull
	static byte[] getKeyGrip(PGPPublicKey publicKey)
			throws PGPException {
		SHA1 grip = SHA1.newInstance();
		grip.setDetectCollision(false);

		switch (publicKey.getAlgorithm()) {
        case PublicKeyAlgorithmTags.RSA_GENERAL:
        case PublicKeyAlgorithmTags.RSA_ENCRYPT:
        case PublicKeyAlgorithmTags.RSA_SIGN:
        	BigInteger modulus = ((RSAPublicBCPGKey) publicKey
					.getPublicKeyPacket().getKey()).getModulus();
			hash(grip
        	break;
        case PublicKeyAlgorithmTags.DSA:
        	DSAPublicBCPGKey dsa = (DSAPublicBCPGKey) publicKey.getPublicKeyPacket().getKey();
			hash(grip
			hash(grip
			hash(grip
			hash(grip
            break;
        case PublicKeyAlgorithmTags.ELGAMAL_GENERAL:
        case PublicKeyAlgorithmTags.ELGAMAL_ENCRYPT:
        	ElGamalPublicBCPGKey eg = (ElGamalPublicBCPGKey) publicKey.getPublicKeyPacket().getKey();
			hash(grip
			hash(grip
			hash(grip
            break;
        case PublicKeyAlgorithmTags.ECDH:
		case PublicKeyAlgorithmTags.ECDSA:
		case PublicKeyAlgorithmTags.EDDSA:
        	ECPublicBCPGKey ec = (ECPublicBCPGKey) publicKey.getPublicKeyPacket().getKey();
            ASN1ObjectIdentifier curveOID = ec.getCurveOID();
			if (OID_OPENPGP_ED25519.equals(curveOID.getId())
					|| OID_RFC8410_ED25519.equals(curveOID.getId())) {
				return hash_ed25519(grip
			} else if (CryptlibObjectIdentifiers.curvey25519.equals(curveOID)
					|| OID_RFC8410_CURVE25519.equals(curveOID.getId())) {
				return hash_curve25519(grip
			}
			X9ECParameters params = getX9Parameters(curveOID);
			if (params == null) {
				throw new PGPException(
						MessageFormat.format(BCText.get().unknownCurve
								curveOID.getId()));
			}
			BigInteger q = ec.getEncodedPoint();
			byte[] g = params.getG().getEncoded(false);
			BigInteger a = params.getCurve().getA().toBigInteger();
			BigInteger b = params.getCurve().getB().toBigInteger();
			BigInteger n = params.getN();
			BigInteger p = null;
			FiniteField field = params.getCurve().getField();
			if (ECAlgorithms.isFpField(field)) {
				p = field.getCharacteristic();
			}
			if (p == null) {
				throw new PGPException(
						MessageFormat.format(
								BCText.get().unknownCurveParameters
								curveOID.getId()));
			}
			hash(grip
			hash(grip
			hash(grip
			hash(grip
			hash(grip
			if (publicKey.getAlgorithm() == PublicKeyAlgorithmTags.EDDSA) {
				byte[] data = q.toByteArray();
				hash(grip
						false);
			} else {
				hash(grip
			}
			break;
		default:
			throw new PGPException(
					MessageFormat.format(BCText.get().unknownKeyType
							Integer.toString(publicKey.getAlgorithm())));
		}
		return grip.digest();
	}

	private static void hash(SHA1 grip
		int i = 0;
		while (i < data.length && data[i] == 0) {
			i++;
		}
		int length = data.length - i;
		if (i < data.length) {
			if ((data[i] & 0x80) != 0) {
				grip.update((byte) 0);
			}
			grip.update(data
		}
	}

	private static void hash(SHA1 grip
		int i = 0;
		while (i < data.length && data[i] == 0) {
			i++;
		}
		int length = data.length - i;
		boolean addZero = false;
		if (i < data.length && zeroPad && (data[i] & 0x80) != 0) {
			addZero = true;
		}
		grip.update(prefix.getBytes(StandardCharsets.US_ASCII));
		if (addZero) {
			grip.update((byte) 0);
		}
		if (i < data.length) {
			grip.update(data
		}
		grip.update((byte) ')');
	}

	@SuppressWarnings("nls")
	private static byte[] hash_ed25519(SHA1 grip
		hash(grip
				"7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFED")
				'p'
		hash(grip
		hash(grip
				"2DFC9311D490018C7338BF8688861767FF8FF5B2BEBE27548A14B235ECA6874A")
				'b'
		hash(grip
				+ "216936D3CD6E53FEC0A4E231FDD6DC5C692CC7609525A7B2C9562D608F25D51A"
				+ "6666666666666666666666666666666666666666666666666666666666666658")
				'g'
		hash(grip
				"1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED")
				'n'
		byte[] data = q.toByteArray();
		hash(grip
		return grip.digest();
	}

	@SuppressWarnings("nls")
	private static byte[] hash_curve25519(SHA1 grip
		hash(grip
				"7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFED")
				'p'
		hash(grip
		hash(grip
		hash(grip
				+ "0000000000000000000000000000000000000000000000000000000000000009"
				+ "20AE19A1B8A086B4E01EDD2C7748D14C923D4D7E6D7C61B229E9C5A27ECED3D9")
				'g'
		hash(grip
				"1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED")
				'n'
		byte[] data = q.toByteArray();
		hash(grip
		return grip.digest();
	}

	private static X9ECParameters getX9Parameters(ASN1ObjectIdentifier curveOID)
    {
		X9ECParameters params = CustomNamedCurves.getByOID(curveOID);
		if (params == null) {
			params = ECNamedCurveTable.getByOID(curveOID);
        }
		return params;
    }
