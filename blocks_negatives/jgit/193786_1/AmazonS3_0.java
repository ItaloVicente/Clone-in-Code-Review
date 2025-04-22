		}
		else if (awsApiSignatureVersion.equals("4")) {
			AwsRequestSignerV4.sign(httpURLConnection, queryParams, data, "s3", region, publicKey, secretKey);
		}
		else {
			throw new IllegalStateException(MessageFormat.format(
				JGitText.get().unexpectedAwsApiSignatureVersion, awsApiSignatureVersion));
