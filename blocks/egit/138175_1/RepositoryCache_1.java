						new WeakReference<>(result));
				return result;
			} else {
				Repository result = r.get();
				if (result != null && result.getDirectory().exists()) {
					return result;
				} else {
					repositoryCache.remove(normalizedGitDir);
				}
