
		String signatureVersion = props.getProperty(Keys.SIGNATURE_VERSION
			String region = props.getProperty(Keys.REGION
			amazonV4Signer = new AmazonV4Signer(publicKey
			amazonV4Signer = null;
		} else {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().wrongAmazonSignatureVersion
		}

