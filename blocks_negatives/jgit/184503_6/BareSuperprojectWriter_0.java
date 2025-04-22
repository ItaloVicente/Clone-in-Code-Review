			BareWriterConfig bareWriterConfig = new BareWriterConfig();
			bareWriterConfig.ignoreRemoteFailures = false;
			bareWriterConfig.recordRemoteBranch = true;
			bareWriterConfig.recordShallowSubmodules = true;
			bareWriterConfig.recordSubmoduleLabels = true;
			return bareWriterConfig;
