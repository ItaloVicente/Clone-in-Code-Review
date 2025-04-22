	private static final Logger LOG = LoggerFactory.getLogger(BlobAsInputStream.class);

	private final Git git;
	private final String treeRef;
	private final String path;

	public BlobAsInputStream(final Git git
		this.git = git;
		this.treeRef = treeRef;
		this.path = path;
	}

	public Optional<InputStream> execute() throws NoSuchFileException {
		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
			final ObjectId tree = git.getTreeFromRef(treeRef);
			tw.setFilter(PathFilter.create(path));
			tw.reset(tree);
			while (tw.next()) {
				if (tw.isSubtree() && !path.equals(tw.getPathString())) {
					tw.enterSubtree();
					continue;
				}
				return Optional.of(new ByteArrayInputStream(
						git.getRepository().open(tw.getObjectId(0)
			}
		} catch (final Throwable t) {
			LOG.debug("Unexpected exception
			throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
		}
		throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
	}
