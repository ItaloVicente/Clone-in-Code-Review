			UploadPack uploadPack = new UploadPack(repository);
			String gitProtocol = getEnvironment().getEnv().get("GIT_PROTOCOL");
			if (gitProtocol != null) {
				uploadPack
						.setExtraParameters(Collections.singleton(gitProtocol));
			}
			try {
