		if (key.length() > 0) {
			if (awsApiSignatureVersion.equals("2")) {
				HttpSupport.encode(urlstr
			}
			else if (awsApiSignatureVersion.equals("4")) {
				urlstr.append(key);
			}
			else {
				throw new IllegalStateException(MessageFormat.format(
					JGitText.get().unexpectedAwsApiSignatureVersion
			}
		}
