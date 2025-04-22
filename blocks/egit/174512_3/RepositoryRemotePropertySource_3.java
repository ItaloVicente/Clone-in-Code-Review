		List<IPropertyDescriptor> resultList = new ArrayList<>();
		PropertyDescriptor desc = new PropertyDescriptor(
				ConfigConstants.CONFIG_KEY_URL,
				UIText.RepositoryRemotePropertySource_RemoteFetchURL_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(FETCH,
				UIText.RepositoryRemotePropertySource_FetchLabel);
		resultList.add(desc);
		desc = new PropertyDescriptor(PUSHURL,
				UIText.RepositoryRemotePropertySource_RemotePushUrl_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(PUSH,
				UIText.RepositoryRemotePropertySource_PushLabel);
		resultList.add(desc);
		descriptors = resultList.toArray(new IPropertyDescriptor[0]);
