	/**
	 * Create a command to update, create or delete a ref in this repository.
	 *
	 * @param ref
	 *            name of the ref the caller wants to modify.
	 * @return an update command. The caller must finish populating this command
	 *         and then invoke one of the update methods to actually make a
	 *         change.
	 * @throws IOException
	 *             a symbolic ref was passed in and could not be resolved back
	 *             to the base ref, as the symbolic ref could not be read.
	 */
	public RefUpdate updateRef(final String ref) throws IOException {
		return updateRef(ref, false);
	}

	/**
	 * Create a command to update, create or delete a ref in this repository.
	 *
	 * @param ref
	 *            name of the ref the caller wants to modify.
	 * @param detach
	 *            true to create a detached head
	 * @return an update command. The caller must finish populating this command
	 *         and then invoke one of the update methods to actually make a
	 *         change.
	 * @throws IOException
	 *             a symbolic ref was passed in and could not be resolved back
	 *             to the base ref, as the symbolic ref could not be read.
	 */
	public RefUpdate updateRef(final String ref, final boolean detach) throws IOException {
		return refs.newUpdate(ref, detach);
	}

	/**
	 * Create a command to rename a ref in this repository
	 *
	 * @param fromRef
	 *            name of ref to rename from
	 * @param toRef
	 *            name of ref to rename to
	 * @return an update command that knows how to rename a branch to another.
	 * @throws IOException
	 *             the rename could not be performed.
	 *
	 */
	public RefRename renameRef(final String fromRef, final String toRef) throws IOException {
		return refs.newRename(fromRef, toRef);
	}

	/**
	 * Parse a git revision string and return an object id.
	 *
	 * Currently supported is combinations of these.
	 * <ul>
	 *  <li>SHA-1 - a SHA-1</li>
	 *  <li>refs/... - a ref name</li>
	 *  <li>ref^n - nth parent reference</li>
	 *  <li>ref~n - distance via parent reference</li>
	 *  <li>ref@{n} - nth version of ref</li>
	 *  <li>ref^{tree} - tree references by ref</li>
	 *  <li>ref^{commit} - commit references by ref</li>
	 * </ul>
	 *
	 * Not supported is
	 * <ul>
	 * <li>timestamps in reflogs, ref@{full or relative timestamp}</li>
	 * <li>abbreviated SHA-1's</li>
	 * </ul>
	 *
	 * @param revstr A git object references expression
	 * @return an ObjectId or null if revstr can't be resolved to any ObjectId
	 * @throws IOException on serious errors
	 */
	public ObjectId resolve(final String revstr) throws IOException {
		char[] rev = revstr.toCharArray();
		Object ref = null;
		ObjectId refId = null;
		for (int i = 0; i < rev.length; ++i) {
			switch (rev[i]) {
			case '^':
				if (refId == null) {
					String refstr = new String(rev,0,i);
					refId = resolveSimple(refstr);
					if (refId == null)
						return null;
				}
				if (i + 1 < rev.length) {
					switch (rev[i + 1]) {
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						int j;
						ref = mapObject(refId, null);
						while (ref instanceof Tag) {
							Tag tag = (Tag)ref;
							refId = tag.getObjId();
							ref = mapObject(refId, null);
						}
						if (!(ref instanceof Commit))
							throw new IncorrectObjectTypeException(refId, Constants.TYPE_COMMIT);
						for (j=i+1; j<rev.length; ++j) {
							if (!Character.isDigit(rev[j]))
								break;
						}
						String parentnum = new String(rev, i+1, j-i-1);
						int pnum;
						try {
							pnum = Integer.parseInt(parentnum);
						} catch (NumberFormatException e) {
							throw new RevisionSyntaxException(
									JGitText.get().invalidCommitParentNumber,
									revstr);
						}
						if (pnum != 0) {
							final ObjectId parents[] = ((Commit) ref)
									.getParentIds();
							if (pnum > parents.length)
								refId = null;
							else
								refId = parents[pnum - 1];
						}
						i = j - 1;
						break;
					case '{':
						int k;
						String item = null;
						for (k=i+2; k<rev.length; ++k) {
							if (rev[k] == '}') {
								item = new String(rev, i+2, k-i-2);
								break;
							}
						}
						i = k;
						if (item != null)
							if (item.equals("tree")) {
								ref = mapObject(refId, null);
								while (ref instanceof Tag) {
									Tag t = (Tag)ref;
									refId = t.getObjId();
									ref = mapObject(refId, null);
								}
								if (ref instanceof Treeish)
									refId = ((Treeish)ref).getTreeId();
								else
									throw new IncorrectObjectTypeException(refId,  Constants.TYPE_TREE);
							}
							else if (item.equals("commit")) {
								ref = mapObject(refId, null);
								while (ref instanceof Tag) {
									Tag t = (Tag)ref;
									refId = t.getObjId();
									ref = mapObject(refId, null);
								}
								if (!(ref instanceof Commit))
									throw new IncorrectObjectTypeException(refId,  Constants.TYPE_COMMIT);
							}
							else if (item.equals("blob")) {
								ref = mapObject(refId, null);
								while (ref instanceof Tag) {
									Tag t = (Tag)ref;
									refId = t.getObjId();
									ref = mapObject(refId, null);
								}
								if (!(ref instanceof byte[]))
									throw new IncorrectObjectTypeException(refId,  Constants.TYPE_BLOB);
							}
							else if (item.equals("")) {
								ref = mapObject(refId, null);
								while (ref instanceof Tag) {
									Tag t = (Tag)ref;
									refId = t.getObjId();
									ref = mapObject(refId, null);
								}
							}
							else
								throw new RevisionSyntaxException(revstr);
						else
							throw new RevisionSyntaxException(revstr);
						break;
					default:
						ref = mapObject(refId, null);
						if (ref instanceof Commit) {
							final ObjectId parents[] = ((Commit) ref)
									.getParentIds();
							if (parents.length == 0)
								refId = null;
							else
								refId = parents[0];
						} else
							throw new IncorrectObjectTypeException(refId,  Constants.TYPE_COMMIT);

					}
				} else {
					ref = mapObject(refId, null);
					while (ref instanceof Tag) {
						Tag tag = (Tag)ref;
						refId = tag.getObjId();
						ref = mapObject(refId, null);
					}
					if (ref instanceof Commit) {
						final ObjectId parents[] = ((Commit) ref)
								.getParentIds();
						if (parents.length == 0)
							refId = null;
						else
							refId = parents[0];
					} else
						throw new IncorrectObjectTypeException(refId,  Constants.TYPE_COMMIT);
				}
				break;
			case '~':
				if (ref == null) {
					String refstr = new String(rev,0,i);
					refId = resolveSimple(refstr);
					if (refId == null)
						return null;
					ref = mapObject(refId, null);
				}
				while (ref instanceof Tag) {
					Tag tag = (Tag)ref;
					refId = tag.getObjId();
					ref = mapObject(refId, null);
				}
				if (!(ref instanceof Commit))
					throw new IncorrectObjectTypeException(refId, Constants.TYPE_COMMIT);
				int l;
				for (l = i + 1; l < rev.length; ++l) {
					if (!Character.isDigit(rev[l]))
						break;
				}
				String distnum = new String(rev, i+1, l-i-1);
				int dist;
				try {
					dist = Integer.parseInt(distnum);
				} catch (NumberFormatException e) {
					throw new RevisionSyntaxException(
							JGitText.get().invalidAncestryLength, revstr);
				}
				while (dist > 0) {
					final ObjectId[] parents = ((Commit) ref).getParentIds();
					if (parents.length == 0) {
						refId = null;
						break;
					}
					refId = parents[0];
					ref = mapCommit(refId);
					--dist;
				}
				i = l - 1;
				break;
			case '@':
				int m;
				String time = null;
				for (m=i+2; m<rev.length; ++m) {
					if (rev[m] == '}') {
						time = new String(rev, i+2, m-i-2);
						break;
					}
				}
				if (time != null)
					throw new RevisionSyntaxException(JGitText.get().reflogsNotYetSupportedByRevisionParser, revstr);
				i = m - 1;
				break;
			default:
				if (refId != null)
					throw new RevisionSyntaxException(revstr);
			}
		}
		if (refId == null)
			refId = resolveSimple(revstr);
		return refId;
	}

	private ObjectId resolveSimple(final String revstr) throws IOException {
		if (ObjectId.isId(revstr))
			return ObjectId.fromString(revstr);
		final Ref r = refs.getRef(revstr);
		return r != null ? r.getObjectId() : null;
	}

	/** Increment the use counter by one, requiring a matched {@link #close()}. */
	public void incrementOpen() {
		useCnt.incrementAndGet();
	}

	/**
	 * Close all resources used by this repository
	 */
	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			objectDatabase.close();
			refs.close();
		}
	}

