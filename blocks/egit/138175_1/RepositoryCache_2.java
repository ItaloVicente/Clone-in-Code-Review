			if (r == null) {
				return null;
			}
			Repository result = r.get();
			if (result != null && result.getDirectory().exists()) {
				return result;
			}
			repositoryCache.remove(normalizedGitDir);
