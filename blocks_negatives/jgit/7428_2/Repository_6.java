	public String simplify(final String revstr)
			throws AmbiguousObjectException, IOException {
		RevWalk rw = new RevWalk(this);
		try {
			Object resolved = resolve(rw, revstr);
			if (resolved != null)
				if (resolved instanceof String)
					return (String) resolved;
				else
					return ((AnyObjectId) resolved).getName();
			return null;
		} finally {
			rw.release();
		}
	}

	private Object resolve(final RevWalk rw, final String revstr)
			throws IOException {
		char[] revChars = revstr.toCharArray();
		RevObject rev = null;
		String name = null;
		int done = 0;
		for (int i = 0; i < revChars.length; ++i) {
			switch (revChars[i]) {
			case '^':
				if (rev == null) {
					if (name == null)
						name = new String(revChars, done, i);
					rev = parseSimple(rw, name);
					name = null;
					if (rev == null)
						return null;
				}
				if (i + 1 < revChars.length) {
					switch (revChars[i + 1]) {
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
						rev = rw.parseCommit(rev);
						for (j = i + 1; j < revChars.length; ++j) {
							if (!Character.isDigit(revChars[j]))
								break;
						}
						String parentnum = new String(revChars, i + 1, j - i
								- 1);
						int pnum;
						try {
							pnum = Integer.parseInt(parentnum);
						} catch (NumberFormatException e) {
							throw new RevisionSyntaxException(
									JGitText.get().invalidCommitParentNumber,
									revstr);
						}
						if (pnum != 0) {
							RevCommit commit = (RevCommit) rev;
							if (pnum > commit.getParentCount())
								rev = null;
							else
								rev = commit.getParent(pnum - 1);
						}
						i = j - 1;
						done = i;
						break;
					case '{':
						int k;
						String item = null;
						for (k = i + 2; k < revChars.length; ++k) {
							if (revChars[k] == '}') {
								item = new String(revChars, i + 2, k - i - 2);
								break;
							}
						}
						i = k;
						if (item != null)
							if (item.equals("tree")) {
								rev = rw.parseTree(rev);
							} else if (item.equals("commit")) {
								rev = rw.parseCommit(rev);
							} else if (item.equals("blob")) {
								rev = rw.peel(rev);
								if (!(rev instanceof RevBlob))
									throw new IncorrectObjectTypeException(rev,
											Constants.TYPE_BLOB);
							} else if (item.equals("")) {
								rev = rw.peel(rev);
							} else
								throw new RevisionSyntaxException(revstr);
						else
							throw new RevisionSyntaxException(revstr);
						done = k;
						break;
					default:
						rev = rw.parseAny(rev);
						if (rev instanceof RevCommit) {
							RevCommit commit = ((RevCommit) rev);
							if (commit.getParentCount() == 0)
								rev = null;
							else
								rev = commit.getParent(0);
						} else
							throw new IncorrectObjectTypeException(rev,
									Constants.TYPE_COMMIT);

					}
				} else {
					rev = rw.peel(rev);
					if (rev instanceof RevCommit) {
						RevCommit commit = ((RevCommit) rev);
						if (commit.getParentCount() == 0)
							rev = null;
						else
							rev = commit.getParent(0);
					} else
						throw new IncorrectObjectTypeException(rev,
								Constants.TYPE_COMMIT);
				}
				break;
			case '~':
				if (rev == null) {
					if (name == null)
						name = new String(revChars, done, i);
					rev = parseSimple(rw, name);
					if (rev == null)
						return null;
				}
				rev = rw.peel(rev);
				if (!(rev instanceof RevCommit))
					throw new IncorrectObjectTypeException(rev,
							Constants.TYPE_COMMIT);
				int l;
				for (l = i + 1; l < revChars.length; ++l) {
					if (!Character.isDigit(revChars[l]))
						break;
				}
				int dist;
				if (l - i > 1) {
					String distnum = new String(revChars, i + 1, l - i - 1);
					try {
						dist = Integer.parseInt(distnum);
					} catch (NumberFormatException e) {
						throw new RevisionSyntaxException(
								JGitText.get().invalidAncestryLength, revstr);
					}
				} else
					dist = 1;
				while (dist > 0) {
					RevCommit commit = (RevCommit) rev;
					if (commit.getParentCount() == 0) {
						rev = null;
						break;
					}
					commit = commit.getParent(0);
					rw.parseHeaders(commit);
					rev = commit;
					--dist;
				}
				i = l - 1;
				break;
			case '@':
				if (rev != null)
					throw new RevisionSyntaxException(revstr);
				int m;
				String time = null;
				for (m = i + 2; m < revChars.length; ++m) {
					if (revChars[m] == '}') {
						time = new String(revChars, i + 2, m - i - 2);
						break;
					}
				}
				if (time != null) {
					if (time.equals("upstream")) {
						if (name == null)
							name = new String(revChars, done, i);
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						name = ref.getName();

						RemoteConfig remoteConfig;
						try {
							remoteConfig = new RemoteConfig(getConfig(),
									"origin");
						} catch (URISyntaxException e) {
							throw new RevisionSyntaxException(revstr);
						}
						String remoteBranchName = getConfig()
								.getString(
										ConfigConstants.CONFIG_BRANCH_SECTION,
								Repository.shortenRefName(ref.getName()),
										ConfigConstants.CONFIG_KEY_MERGE);
						List<RefSpec> fetchRefSpecs = remoteConfig
								.getFetchRefSpecs();
						for (RefSpec refSpec : fetchRefSpecs) {
							if (refSpec.matchSource(remoteBranchName)) {
								RefSpec expandFromSource = refSpec
										.expandFromSource(remoteBranchName);
								name = expandFromSource.getDestination();
								break;
							}
						}
						if (name == null)
							throw new RevisionSyntaxException(revstr);
					} else if (time.matches("^-\\d+$")) {
						if (name != null)
							throw new RevisionSyntaxException(revstr);
						else {
							String previousCheckout = resolveReflogCheckout(-Integer
									.parseInt(time));
							if (ObjectId.isId(previousCheckout))
								rev = parseSimple(rw, previousCheckout);
							else
								name = previousCheckout;
						}
					} else {
						if (name == null)
							name = new String(revChars, done, i);
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						rev = resolveReflog(rw, ref, time);
						name = null;
					}
					i = m;
				} else
					throw new RevisionSyntaxException(revstr);
				break;
			case ':': {
				RevTree tree;
				if (rev == null) {
					if (name == null)
						name = new String(revChars, done, i);
					if (name.equals(""))
						name = Constants.HEAD;
					rev = parseSimple(rw, name);
				}
				if (rev == null)
					return null;
				tree = rw.parseTree(rev);
				if (i == revChars.length - 1)
					return tree.copy();

				TreeWalk tw = TreeWalk.forPath(rw.getObjectReader(),
						new String(revChars, i + 1, revChars.length - i - 1),
						tree);
				return tw != null ? tw.getObjectId(0) : null;
			}
			default:
				if (rev != null)
					throw new RevisionSyntaxException(revstr);
			}
		}
		if (rev != null)
			return rev.copy();
		if (name != null)
			return name;
		name = revstr.substring(done);
		if (getRef(name) != null)
			return name;
		return resolveSimple(name);
	}

	private static boolean isHex(char c) {
				|| ('A' <= c && c <= 'F');
	}

	private static boolean isAllHex(String str, int ptr) {
		while (ptr < str.length()) {
			if (!isHex(str.charAt(ptr++)))
				return false;
		}
		return true;
	}

	private RevObject parseSimple(RevWalk rw, String revstr) throws IOException {
		ObjectId id = resolveSimple(revstr);
		return id != null ? rw.parseAny(id) : null;
	}

	private ObjectId resolveSimple(final String revstr) throws IOException {
		if (ObjectId.isId(revstr))
			return ObjectId.fromString(revstr);

		Ref r = getRefDatabase().getRef(revstr);
		if (r != null)
			return r.getObjectId();

		if (AbbreviatedObjectId.isId(revstr))
			return resolveAbbreviation(revstr);

		int dashg = revstr.indexOf("-g");
		if ((dashg + 5) < revstr.length() && 0 <= dashg
				&& isHex(revstr.charAt(dashg + 2))
				&& isHex(revstr.charAt(dashg + 3))
				&& isAllHex(revstr, dashg + 4)) {
			String s = revstr.substring(dashg + 2);
			if (AbbreviatedObjectId.isId(s))
				return resolveAbbreviation(s);
		}

		return null;
	}

	private String resolveReflogCheckout(int checkoutNo)
			throws IOException {
		List<ReflogEntry> reflogEntries = new ReflogReader(this, Constants.HEAD)
				.getReverseEntries();
		for (ReflogEntry entry : reflogEntries) {
			CheckoutEntry checkout = entry.parseCheckout();
			if (checkout != null)
				if (checkoutNo-- == 1)
					return checkout.getFromBranch();
		}
		return null;
	}

	private RevCommit resolveReflog(RevWalk rw, Ref ref, String time)
			throws IOException {
		int number;
		try {
			number = Integer.parseInt(time);
		} catch (NumberFormatException nfe) {
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().invalidReflogRevision, time));
		}
		assert number >= 0;
		ReflogReader reader = new ReflogReader(this, ref.getName());
		ReflogEntry entry = reader.getReverseEntry(number);
		if (entry == null)
			throw new RevisionSyntaxException(MessageFormat.format(
					JGitText.get().reflogEntryNotFound,
					Integer.valueOf(number), ref.getName()));

		return rw.parseCommit(entry.getNewId());
	}

	private ObjectId resolveAbbreviation(final String revstr) throws IOException,
			AmbiguousObjectException {
		AbbreviatedObjectId id = AbbreviatedObjectId.fromString(revstr);
		ObjectReader reader = newObjectReader();
		try {
			Collection<ObjectId> matches = reader.resolve(id);
			if (matches.size() == 0)
				return null;
			else if (matches.size() == 1)
				return matches.iterator().next();
			else
				throw new AmbiguousObjectException(id, matches);
		} finally {
			reader.release();
		}
	}
