		awsApiSignatureVersion = props
				.getProperty(Keys.AWS_API_SIGNATURE_VERSION
		if (awsApiSignatureVersion.equals(AWS_API_V4)) {
			region = props.getProperty(Keys.REGION);
			if (region == null) {
				throw new IllegalArgumentException(
						JGitText.get().missingAwsRegion);
			}
		} else if (awsApiSignatureVersion.equals(AWS_API_V2)) {
			region = null;
		} else {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidAwsApiSignatureVersion
					awsApiSignatureVersion));
		}

