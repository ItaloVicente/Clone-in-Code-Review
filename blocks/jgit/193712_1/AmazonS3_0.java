		awsApiSignatureVersion = props.getProperty(Keys.AWS_API_SIGNATURE_VERSION
		if (awsApiSignatureVersion.equals("4")) {
			if (region == null) {
				throw new IllegalArgumentException(JGitText.get().missingRegion);
			}
		}
		else if (awsApiSignatureVersion.equals("2")) {
			region = null;
		}
		else {
			throw new IllegalArgumentException(MessageFormat.format(
				JGitText.get().invalidAwsApiSignatureVersion
		}

