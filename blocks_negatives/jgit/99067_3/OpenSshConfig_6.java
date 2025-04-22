		void copyFrom(final Host src) {
			if (hostName == null)
				hostName = src.hostName;
			if (port == 0)
				port = src.port;
			if (identityFile == null)
				identityFile = src.identityFile;
			if (user == null)
				user = src.user;
			if (preferredAuthentications == null)
				preferredAuthentications = src.preferredAuthentications;
			if (batchMode == null)
				batchMode = src.batchMode;
			if (strictHostKeyChecking == null)
				strictHostKeyChecking = src.strictHostKeyChecking;
			if (connectionAttempts == 0)
				connectionAttempts = src.connectionAttempts;
