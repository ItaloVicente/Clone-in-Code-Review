		if (key.length() > 0) {
			if (awsApiSignatureVersion.equals(AWS_API_V2)) {
				HttpSupport.encode(urlstr
			} else if (awsApiSignatureVersion.equals(AWS_API_V4)) {
				urlstr.append(key);
			}
		}
