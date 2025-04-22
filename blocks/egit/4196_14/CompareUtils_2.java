	public static ITypedElement getHeadTypedElement(final IFile baseFile)
			throws IOException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(baseFile);
		final Repository repository = mapping.getRepository();
		final String gitPath = mapping.getRepoRelativePath(baseFile);

		DirCache dc = repository.lockDirCache();
		final DirCacheEntry entry = dc.getEntry(gitPath);
		dc.unlock();

		IFileRevision nextFile = GitFileRevision.inIndex(repository, gitPath);
		String encoding = CompareCoreUtils.getResourceEncoding(baseFile);
		final EditableRevision next = new EditableRevision(nextFile, encoding);

		IContentChangeListener listener = new IContentChangeListener() {
			public void contentChanged(IContentChangeNotifier source) {
				final byte[] newContent = next.getModifiedContent();
				DirCache cache = null;
				try {
					cache = repository.lockDirCache();
					DirCacheEditor editor = cache.editor();
					if (newContent.length == 0)
						editor.add(new DirCacheEditor.DeletePath(gitPath));
					else
						editor.add(new DirCacheEntryEditor(gitPath,
								repository, entry, newContent));
					try {
						editor.commit();
					} catch (RuntimeException e) {
						if (e.getCause() instanceof IOException)
							throw (IOException) e.getCause();
						else
							throw e;
					}

				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
				} finally {
					if (cache != null)
						cache.unlock();
				}
			}
		};

		next.addContentChangeListener(listener);
		return next;
	}

	public static DiffNode prepareGitCompare(ITypedElement actLeft, ITypedElement actRight) {
		if (actLeft.getType().equals(ITypedElement.FOLDER_TYPE)) {
			DiffNode diffNode = new DiffNode(null,Differencer.CHANGE,null,actLeft,actRight);
			ITypedElement[] lc = (ITypedElement[])((IStructureComparator)actLeft).getChildren();
			ITypedElement[] rc = (ITypedElement[])((IStructureComparator)actRight).getChildren();
			int li=0;
			int ri=0;
			while (li<lc.length && ri<rc.length) {
				ITypedElement ln = lc[li];
				ITypedElement rn = rc[ri];
				int compareTo = ln.getName().compareTo(rn.getName());
				if (compareTo == 0) {
					if (!ln.equals(rn))
						diffNode.add(prepareGitCompare(ln,rn));
					++li;
					++ri;
				} else if (compareTo < 0) {
					DiffNode childDiffNode = new DiffNode(Differencer.ADDITION, null, ln, null);
					diffNode.add(childDiffNode);
					if (ln.getType().equals(ITypedElement.FOLDER_TYPE)) {
						ITypedElement[] children = (ITypedElement[])((IStructureComparator)ln).getChildren();
						if(children != null && children.length > 0) {
							for (ITypedElement child : children) {
								childDiffNode.add(addDirectoryFiles(child, Differencer.ADDITION));
							}
						}
					}
					++li;
				} else {
					DiffNode childDiffNode = new DiffNode(Differencer.DELETION, null, null, rn);
					diffNode.add(childDiffNode);
					if (rn.getType().equals(ITypedElement.FOLDER_TYPE)) {
						ITypedElement[] children = (ITypedElement[])((IStructureComparator)rn).getChildren();
						if(children != null && children.length > 0) {
							for (ITypedElement child : children) {
								childDiffNode.add(addDirectoryFiles(child, Differencer.DELETION));
							}
						}
					}
					++ri;
				}
			}
			while (li<lc.length) {
				ITypedElement ln = lc[li];
				DiffNode childDiffNode = new DiffNode(Differencer.ADDITION, null, ln, null);
				diffNode.add(childDiffNode);
				if (ln.getType().equals(ITypedElement.FOLDER_TYPE)) {
					ITypedElement[] children = (ITypedElement[])((IStructureComparator)ln).getChildren();
					if(children != null && children.length > 0) {
						for (ITypedElement child : children) {
							childDiffNode.add(addDirectoryFiles(child, Differencer.ADDITION));
						}
					}
				}
				++li;
			}
			while (ri<rc.length) {
				ITypedElement rn = rc[ri];
				DiffNode childDiffNode = new DiffNode(Differencer.DELETION, null, null, rn);
				diffNode.add(childDiffNode);
				if (rn.getType().equals(ITypedElement.FOLDER_TYPE)) {
					ITypedElement[] children = (ITypedElement[])((IStructureComparator)rn).getChildren();
					if(children != null && children.length > 0) {
						for (ITypedElement child : children) {
							childDiffNode.add(addDirectoryFiles(child, Differencer.DELETION));
						}
					}
				}
				++ri;
			}
			return diffNode;
		} else {
			return new DiffNode(actLeft, actRight);
		}
	}

	private static DiffNode addDirectoryFiles(ITypedElement elem, int diffType) {
		ITypedElement l = null;
		ITypedElement r = null;
		if (diffType == Differencer.DELETION) {
			r = elem;
		} else {
			l = elem;
		}

		if (elem.getType().equals(ITypedElement.FOLDER_TYPE)) {
			DiffNode diffNode = null;
			diffNode = new DiffNode(null,Differencer.CHANGE,null,l,r);
			ITypedElement[] children = (ITypedElement[])((IStructureComparator)elem).getChildren();
			for (ITypedElement child : children) {
				diffNode.add(addDirectoryFiles(child, diffType));
			}
			return diffNode;
		} else {
			return new DiffNode(diffType, null, l, r);
		}
	}

	private static class DirCacheEntryEditor extends DirCacheEditor.PathEdit {

		private final Repository repo;

		private final DirCacheEntry oldEntry;

		private final byte[] newContent;

		public DirCacheEntryEditor(String path, Repository repo,
				DirCacheEntry oldEntry, byte[] newContent) {
			super(path);
			this.repo = repo;
			this.oldEntry = oldEntry;
			this.newContent = newContent;
		}

		@Override
		public void apply(DirCacheEntry ent) {
			ObjectInserter inserter = repo.newObjectInserter();
			if (oldEntry != null)
				ent.copyMetaData(oldEntry);
			else
				ent.setFileMode(FileMode.REGULAR_FILE);

			ent.setLength(newContent.length);
			ent.setLastModified(System.currentTimeMillis());
			InputStream in = new ByteArrayInputStream(newContent);
			try {
				ent.setObjectId(inserter.insert(Constants.OBJ_BLOB,
						newContent.length, in));
				inserter.flush();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

