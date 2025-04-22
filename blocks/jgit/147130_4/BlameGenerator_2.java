	public BlameGenerator prepareHead() throws NoHeadException
		Repository repo = getRepository();
		ObjectId head = repo.resolve(Constants.HEAD);
		if (head == null) {
			throw new NoHeadException(MessageFormat
					.format(JGitText.get().noSuchRefKnown
		}
		if (repo.isBare()) {
			return push(null
		}
		DirCache dc = repo.readDirCache();
		try (TreeWalk walk = new TreeWalk(repo)) {
			walk.setOperationType(OperationType.CHECKIN_OP);
			FileTreeIterator iter = new FileTreeIterator(repo);
			int fileTree = walk.addTree(iter);
			int indexTree = walk.addTree(new DirCacheIterator(dc));
			iter.setDirCacheIterator(walk
			walk.setFilter(resultPath);
			walk.setRecursive(true);
			if (!walk.next()) {
				return this;
			}
			DirCacheIterator dcIter = walk.getTree(indexTree
					DirCacheIterator.class);
			if (dcIter == null) {
				return this;
			}
			iter = walk.getTree(fileTree
			if (iter == null || !isFile(iter.getEntryRawMode())) {
				return this;
			}
			EolStreamType eolType = iter.getEolStreamType();
			RawText inTree;
			if (eolType == null || EolStreamType.DIRECT.equals(eolType)) {
				inTree = new RawText(iter.getEntryFile());
			} else {
				try (InputStream stream = EolStreamTypeUtil
						.wrapInputStream(iter.openEntryStream()
					inTree = new RawText(getBytes(iter.getEntryFile().getPath()
							stream
				}
			}
			DirCacheEntry indexEntry = dcIter.getDirCacheEntry();
			if (indexEntry.getStage() == DirCacheEntry.STAGE_0) {
				push(null
				push(null
				push(null
			} else {
				HeadCandidate c = new HeadCandidate(getRepository()
						getHeads(repo
				c.sourceText = inTree;
				c.regionList = new Region(0
				remaining = inTree.size();
				push(c);
			}
		}
		return this;
	}

	private List<RevCommit> getHeads(Repository repo
			throws NoWorkTreeException
		List<ObjectId> mergeIds = repo.readMergeHeads();
		if (mergeIds == null || mergeIds.isEmpty()) {
			return Collections.singletonList(revPool.parseCommit(head));
		}
		List<RevCommit> heads = new ArrayList<>(mergeIds.size() + 1);
		heads.add(revPool.parseCommit(head));
		for (ObjectId id : mergeIds) {
			heads.add(revPool.parseCommit(id));
		}
		return heads;
	}

	private static byte[] getBytes(String path
			throws IOException {
		if (maxLength > Integer.MAX_VALUE) {
			throw new IOException(
					MessageFormat.format(JGitText.get().fileIsTooLarge
		}
		int max = (int) maxLength;
		byte[] buffer = new byte[max];
		int read = IO.readFully(in
		if (read == max) {
			return buffer;
		} else {
			byte[] copy = new byte[read];
			System.arraycopy(buffer
			return copy;
		}
	}

