		System.out.println(
				"BasePackPushConnection: pushOptions = " + pushOptions);
		System.out.println("BasePackPushConnection: capablePushOptions = "
				+ capablePushOptions);
		if (pushOptions != null && !capablePushOptions) {
			throw new TransportException(uri
					MessageFormat.format(JGitText.get().pushOptionsNotSupported
							pushOptions.toString()));
		}

