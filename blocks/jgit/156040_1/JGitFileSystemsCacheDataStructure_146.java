			@Override
			public Supplier<JGitFileSystem> putIfAbsent(String key
				Supplier<JGitFileSystem> jGitFileSystemSupplier = super.putIfAbsent(key
				if (size() > config.getJgitFileSystemsInstancesCache()) {
					fitListToCacheSize();
				}
				return jGitFileSystemSupplier;
			}
