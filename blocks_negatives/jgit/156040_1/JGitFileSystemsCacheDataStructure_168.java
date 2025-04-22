            @Override
            public Supplier<JGitFileSystem> putIfAbsent(String key, Supplier<JGitFileSystem> value) {
                Supplier<JGitFileSystem> jGitFileSystemSupplier = super.putIfAbsent(key, value);
                if (size() > config.getJgitFileSystemsInstancesCache()) {
                    fitListToCacheSize();
                }
                return jGitFileSystemSupplier;
            }
