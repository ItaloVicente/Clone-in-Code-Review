		final String[] refSearchPath;
		final List<String> refSearchPathList = options.getRefSearchPaths();
		if (refSearchPathList == null) {
			refSearchPath = RefDatabase.getDefaultSearchPath();
		}
		else {
			refSearchPath = refSearchPathList.toArray(new String[refSearchPathList.size()]);
		}

		refs = new RefDirectory(this
