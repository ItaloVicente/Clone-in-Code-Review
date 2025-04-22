					entry.getValue(SshConstants.CONNECTION_ATTEMPTS));
			strictHostKeyChecking = entry
					.getValue(SshConstants.STRICT_HOST_KEY_CHECKING);
			batchMode = Boolean.valueOf(OpenSshConfigFile
					.flag(entry.getValue(SshConstants.BATCH_MODE)));
			preferredAuthentications = entry
					.getValue(SshConstants.PREFERRED_AUTHENTICATIONS);
