		} else if (object instanceof GitModelCacheFile) {
			return new GitCacheCompareInput((GitModelCacheFile) object);
		} else if (object instanceof GitModelWorkingFile) {
			return new GitLocalCompareInput((GitModelWorkingFile) object);
		} else if (object instanceof GitModelBlob) {
			return new GitCompareInput((GitModelBlob) object);
