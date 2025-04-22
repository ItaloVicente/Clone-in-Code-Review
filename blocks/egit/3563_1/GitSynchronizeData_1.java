		RemoteConfig srcRemoteConfig = extractRemoteName(srcRev);
		RemoteConfig dstRemoteConfig = extractRemoteName(dstRev);

		srcRemote = srcRemoteConfig.remote;
		srcMerge = srcRemoteConfig.merge;

		dstRemote = dstRemoteConfig.remote;
		dstMerge = dstRemoteConfig.merge;
