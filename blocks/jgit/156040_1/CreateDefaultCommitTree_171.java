	public CreateDefaultCommitTree(final Git git
			final DefaultCommitContent commitContent) {
		super(git
	}

	public Optional<ObjectId> execute() {
		final Map<String
		final Map<String
		final Set<String> path2delete = new HashSet<>();

		final DirCacheEditor editor = DirCache.newInCore().editor();

		try {
			for (final Map.Entry<String
				final String gPath = PathUtil.normalize(pathAndContent.getKey());
				if (pathAndContent.getValue() == null) {
					path2delete.addAll(searchPathsToDelete(git
				} else {
					paths.putAll(storePathsIntoHashMap(odi
				}
			}

			iterateOverTreeWalk(git
				if (paths.containsKey(walkPath) && paths.get(walkPath).getValue().equals(hTree.getEntryObjectId())) {
					paths.remove(walkPath);
				}

				if (paths.get(walkPath) == null && !path2delete.contains(walkPath)) {
					addToTemporaryInCoreIndex(editor
							hTree.getEntryFileMode());
				}
			});

			paths.forEach((key
				if (value.getKey() != null) {
					editor.add(new DirCacheEditor.PathEdit(new DirCacheEntry(key)) {
						@Override
						public void apply(final DirCacheEntry ent) {
							ent.setLength(value.getKey().length());
							ent.setLastModified(Instant.ofEpochMilli(value.getKey().lastModified()));
							ent.setFileMode(REGULAR_FILE);
							ent.setObjectId(value.getValue());
						}
					});
				}
			});

			editor.finish();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (path2delete.isEmpty() && paths.isEmpty()) {
			editor.getDirCache().clear();
			return Optional.empty();
		}

		return buildTree(editor);
	}

	private static Map<String
			final Map.Entry<String
		try (final InputStream inputStream = new FileInputStream(pathAndContent.getValue())) {
			final Map<String
			final ObjectId objectId = inserter.insert(Constants.OBJ_BLOB
					inputStream);
			paths.put(gPath
			return paths;
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static Set<String> searchPathsToDelete(final Git git
			throws java.io.IOException {
		try (final TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
			final Set<String> path2delete = new HashSet<>();
			treeWalk.addTree(new RevWalk(git.getRepository()).parseTree(headId));
			treeWalk.setRecursive(true);
			treeWalk.setFilter(PathFilter.create(gPath));

			while (treeWalk.next()) {
				path2delete.add(treeWalk.getPathString());
			}
			return path2delete;
		}
	}
