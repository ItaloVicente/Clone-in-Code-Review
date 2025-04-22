		final String[] refSearchPath;
		final List<String> refSearchPathList = options.getRefSearchPaths();
		if (refSearchPathList == null) {
			refSearchPath = RefDatabase.getSearchPath();
		}
		else {
			refSearchPath = refSearchPathList.toArray(new String[refSearchPathList.size()]);
		}

		refs = new RefDirectory(this
