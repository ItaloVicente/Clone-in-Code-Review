		List<IPropertyDescriptor> resultList = new ArrayList<>();
		PropertyDescriptor desc = new PropertyDescriptor(RepositoriesView.URL,
				UIText.RepositoryRemotePropertySource_RemoteFetchURL_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.FETCH,
				UIText.RepositoryRemotePropertySource_FetchLabel);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.PUSHURL,
				UIText.RepositoryRemotePropertySource_RemotePushUrl_label);
		resultList.add(desc);
		desc = new PropertyDescriptor(RepositoriesView.PUSH,
				UIText.RepositoryRemotePropertySource_PushLabel);
		resultList.add(desc);
		return resultList.toArray(new IPropertyDescriptor[0]);
