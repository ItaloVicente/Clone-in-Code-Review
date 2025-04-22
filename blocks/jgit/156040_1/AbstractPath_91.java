public abstract class AbstractPath<FS extends FileSystem> implements Path

	protected final FS fs;
	protected final boolean usesWindowsFormat;

	protected final boolean isAbsolute;
	protected final byte[] path;
	protected final boolean isRoot;
	protected final boolean isRealPath;
	protected final boolean isNormalized;
	protected final String host;
	protected final List<Map.Entry<Integer
	protected final AttrsStorage attrsStorage = new AttrsStorageImpl();
	protected String toStringFormat;
	protected File file = null;

	protected AbstractPath(final FS fs
		this(checkNotNull("fs"
	}

	protected AbstractPath(final FS fs
			boolean isNormalized) {
		checkNotNull("path"
		this.fs = checkNotNull("fs"
		this.host = checkNotNull("host"
		this.isRealPath = isRealPath;
		this.isNormalized = isNormalized;
		this.usesWindowsFormat = path.indexOf('\\') != -1;

		final RootInfo rootInfo = setupRoot(fs
		this.path = rootInfo.path;

		checkNotNull("rootInfo"

		this.isAbsolute = rootInfo.isAbsolute;

		int lastOffset = rootInfo.startOffset;
		for (int i = lastOffset; i < this.path.length; i++) {
			final byte b = this.path[i];
			if (b == getSeparator()) {
				offsets.add(new AbstractMap.SimpleEntry<>(lastOffset
				i++;
				lastOffset = i;
			}
		}

		if (lastOffset < this.path.length) {
			offsets.add(new AbstractMap.SimpleEntry<>(lastOffset
		}

		this.isRoot = rootInfo.isRoot;
	}

	protected abstract Path newPath(FS fs

	protected abstract Path newRoot(FS fs

	protected abstract RootInfo setupRoot(final FS fs

	@Override
	public FS getFileSystem() {
		return fs;
	}

	@Override
	public boolean isAbsolute() {
		return isAbsolute;
	}

	@Override
	public Path getRoot() {
		if (isRoot) {
			return this;
		}
		if (isAbsolute || !host.isEmpty()) {
			return newRoot(fs
		}
		return null;
	}

	private String substring(int index) {
		final byte[] result;
		if (index == -1) {
			result = new byte[offsets.get(0).getKey()];
			System.arraycopy(path
		} else {
			final Map.Entry<Integer
			result = new byte[offset.getValue() - offset.getKey()];
			System.arraycopy(path
		}

		return new String(result);
	}

	private String substring(int beginIndex
		final int initPos;
		if (beginIndex == -1) {
			initPos = 0;
		} else {
			initPos = offsets.get(beginIndex).getKey();
		}
		final Map.Entry<Integer
		final byte[] result = new byte[offsetEnd.getValue() - initPos];
		System.arraycopy(path

		return new String(result);
	}

	@Override
	public Path getFileName() {
		if (getNameCount() == 0) {
			return null;
		}
		return getName(getNameCount() - 1);
	}

	@Override
	public Path getParent() {
		if (getNameCount() <= 0) {
			return null;
		}
		if (getNameCount() == 1) {
			return getRoot();
		}
		return newPath(fs
	}

	@Override
	public int getNameCount() {
		return offsets.size();
	}

	@Override
	public Path getName(int index) throws IllegalArgumentException {
		if (isRoot && index > 0) {
			throw new IllegalArgumentException("Invalid index argument: " + index);
		}
		if (index < 0) {
			throw new IllegalArgumentException("Invalid index argument: " + index);
		}
		if (index >= offsets.size()) {
			throw new IllegalArgumentException(
					"Invalid index argument: " + index + "
		}

		return newPath(fs
	}

	@Override
	public Path subpath(int beginIndex
		if (beginIndex < 0) {
			throw new IllegalArgumentException("Invalid beginIndex argument: " + beginIndex);
		}
		if (beginIndex >= offsets.size()) {
			throw new IllegalArgumentException("Invalid beginIndex argument: " + beginIndex);
		}
		if (endIndex > offsets.size()) {
			throw new IllegalArgumentException("Invalid endIndex argument: " + endIndex);
		}
		if (beginIndex >= endIndex) {
			throw new IllegalArgumentException("Invalid arguments
					+ "bI " + beginIndex + "
		}

		return newPath(fs
	}

	@Override
	public URI toUri() throws IOError
		if (!isAbsolute()) {
			return toAbsolutePath().toUri();
		}
	}

	private String toURIString() {
		if (usesWindowsFormat) {
			return encodePath("/" + toString().replace("\\"
		}
		return encodePath(new String(path));
	}

	private String encodePath(final String s) {
		return EncodingUtil.encodePath(s);
	}

	@Override
	public Path toAbsolutePath() throws IOError
		if (isAbsolute()) {
			return this;
		}
		if (host.isEmpty()) {
			return newPath(fs
					isRealPath
		}
		return newPath(fs
	}

	protected abstract String defaultDirectory();

	@Override
	public Path toRealPath(final LinkOption... options) throws IOException
		if (isRealPath) {
			return this;
		}
		return newPath(fs
	}

	@Override
	public Iterator<Path> iterator() {
		return new Iterator<Path>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < getNameCount();
			}

			@Override
			public Path next() {
				if (i < getNameCount()) {
					Path result = getName(i);
					i++;
					return result;
				} else {
					throw new NoSuchElementException();
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public boolean startsWith(final Path other) {
		checkNotNull("other"

		if (other.isAbsolute() != isAbsolute()) {
			return false;
		}

		if (!(other instanceof AbstractPath)) {
			return false;
		}

		final AbstractPath<?> that = (AbstractPath) other;

		int thisNameCount = getNameCount();
		int thatNameCount = that.getNameCount();

		if (thatNameCount > thisNameCount) {
			return false;
		}

		List<String> thisNames = getNamesIncludingRoot();
		List<String> thatNames = that.getNamesIncludingRoot();

		for (int i = 0; i < thatNames.size(); i++) {
			if (!thisNames.get(i).equals(thatNames.get(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean startsWith(final String other) throws InvalidPathException {
		checkNotNull("other"
		return startsWith(getFileSystem().getPath(other));
	}

	@Override
	public boolean endsWith(final Path other) {
		checkNotNull("other"

		if (!(other instanceof AbstractPath)) {
			return false;
		}

		final AbstractPath<?> that = (AbstractPath) other;

		if (that.isAbsolute()) {
			if (!isAbsolute()) {
				return false;
			}
			if (!equalRoots(that)) {
				return false;
			}
		}

		if (endsWithSeparator() != that.endsWithSeparator()) {
			return false;
		}

		int thisNameCount = getNameCount();
		int thatNameCount = that.getNameCount();

		if (thatNameCount > thisNameCount) {
			return false;
		}

		if (thisNameCount > 0 && thatNameCount == 0) {
			return false;
		}

		if (thatNameCount == thisNameCount) {
			if (thisNameCount == 0) {
				return true;
			}
		} else {
			if (that.isAbsolute()) {
				return false;
			}
		}

		int thisPosition = thisNameCount;
		int thatPosition = thatNameCount;

		while (thatPosition > 0) {
			if (!getName(--thisPosition).equals(that.getName(--thatPosition))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean endsWith(final String other) throws InvalidPathException {
		checkNotNull("other"
		return endsWith(getFileSystem().getPath(other));
	}

	@Override
	public Path normalize() {
		if (isNormalized) {
			return this;
		}

		return newPath(fs
	}

	@Override
	public Path resolve(final Path other) {
		checkNotNull("other"
		if (other.isAbsolute()) {
			return other;
		}
		if (other.toString().trim().length() == 0) {
			return this;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append(new String(path));
		if (path[path.length - 1] != getSeparator()) {
			sb.append(getSeparator());
		}
		sb.append(other.toString());

		return newPath(fs
	}

	@Override
	public Path resolve(final String other) throws InvalidPathException {
		checkNotNull("other"
		return resolve(newPath(fs
	}

	@Override
	public Path resolveSibling(final Path other) {
		checkNotNull("other"

		final Path parent = this.getParent();
		if (parent == null || other.isAbsolute()) {
			return other;
		}

		return parent.resolve(other);
	}

	@Override
	public Path resolveSibling(final String other) throws InvalidPathException {
		checkNotNull("other"

		return resolveSibling(newPath(fs
	}

	@Override
	public Path relativize(final Path otherx) throws IllegalArgumentException {
		checkNotNull("otherx"
		final AbstractPath other = checkInstanceOf("otherx"

		if (isAbsolute() != other.isAbsolute()) {
			throw new IllegalArgumentException(
					"Could not relativize path 'otherx'
		}

		if (isAbsolute() && !equalRoots(other)) {
			throw new IllegalArgumentException(
					"Could not relativize path 'otherx'
		}

		if (getNamesIncludingRoot().equals(other.getNamesIncludingRoot())) {
			return emptyPath();
		}

		if (this.path.length == 0) {
			return other;
		}

		int n = (getNameCount() > other.getNameCount()) ? other.getNameCount() : getNameCount();
		int i = 0;
		while (i < n) {
			if (!this.getName(i).equals(other.getName(i))) {
				break;
			}
			i++;
		}

		int numberOfDots = getNameCount() - i;

		if (numberOfDots == 0 && i < other.getNameCount()) {
			return other.subpath(i
		}

		final StringBuilder sb = new StringBuilder();
		while (numberOfDots > 0) {
			sb.append("..");
			if (numberOfDots > 1) {
				sb.append(getSeparator());
			}
			numberOfDots--;
		}

		if (i < other.getNameCount()) {
			if (sb.length() > 0) {
				sb.append(getSeparator());
			}
			String subpath = ((AbstractPath<FS>) other.subpath(i
			subpath = other.getSeparator() == getSeparator() ? subpath
					: subpath.replaceAll(other.quoteSeparator()
			sb.append(subpath);
		}

		return newPath(fs
	}

	private Path emptyPath() {
		return newPath(fs
	}

	@Override
	public int compareTo(final Path other) {
		checkNotNull("other"
		throw new UnsupportedOperationException();
	}

	@Override
	public WatchKey register(WatchService watcher
			throws UnsupportedOperationException
			SecurityException {
		return watcher.poll();
	}

	@Override
	public WatchKey register(WatchService watcher
			IllegalArgumentException
		return watcher.poll();
	}

	@Override
	public String toString() {
		if (toStringFormat == null) {
			toStringFormat = toString(false);
		}
		return toStringFormat;
	}

	public String toString(boolean addHost) {
		if (!addHost || host.isEmpty()) {
			return new String(path);
		}
		if (isAbsolute) {
			return host + new String(path);
		} else {
			return host + ":" + new String(path);
		}
	}

	private char getSeparator() {
		if (usesWindowsFormat) {
			return '\\';
		}
		return '/';
	}

	public void clearCache() {
		file = null;
		attrsStorage.clear();
	}

	@Override
	public boolean equals(final Object o) {
		checkNotNull("o"

		if (this == o) {
			return true;
		}
		if (!(o instanceof AbstractPath)) {
			return false;
		}

		AbstractPath other = (AbstractPath) o;

		if (isAbsolute != other.isAbsolute) {
			return false;
		}
		if (isRealPath != other.isRealPath) {
			return false;
		}
		if (isRoot != other.isRoot) {
			return false;
		}
		if (usesWindowsFormat != other.usesWindowsFormat) {
			return false;
		}
		if (!host.equals(other.host)) {
			return false;
		}
		if (!fs.equals(other.fs)) {
			return false;
		}

		if (!usesWindowsFormat && !Arrays.equals(path
			return false;
		}

		if (usesWindowsFormat && !(new String(path).equalsIgnoreCase(new String(other.path)))) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = fs != null ? fs.hashCode() : 0;
		result = 31 * result + (usesWindowsFormat ? 1 : 0);
		result = 31 * result + (isAbsolute ? 1 : 0);

		if (!usesWindowsFormat) {
			result = 31 * result + (path != null ? Arrays.hashCode(path) : 0);
		} else {
			result = 31 * result + (path != null ? new String(path).toLowerCase().hashCode() : 0);
		}

		result = 31 * result + (isRoot ? 1 : 0);
		result = 31 * result + (isRealPath ? 1 : 0);
		result = 31 * result + (isNormalized ? 1 : 0);
		return result;
	}

	public String getHost() {
		return host;
	}

	public boolean isRealPath() {
		return isRealPath;
	}

	@Override
	public AttrsStorage getAttrStorage() {
		return attrsStorage;
	}

	@Override
	public <V extends AttributeView> void addAttrView(final V view) {
		attrsStorage.addAttrView(view);
	}

	@Override
	public <V extends AttributeView> V getAttrView(final Class<V> type) {
		return attrsStorage.getAttrView(type);
	}

	@Override
	public <V extends AttributeView> V getAttrView(final String name) {
		return (V) attrsStorage.getAttrView(name);
	}

	public static class RootInfo {

		private final int startOffset;
		private final boolean isAbsolute;
		private final boolean isRoot;
		private final byte[] path;

		public RootInfo(int startOffset
			this.startOffset = startOffset;
			this.isAbsolute = isAbsolute;
			this.isRoot = isRoot;
			this.path = path;
		}
	}

	private List<String> getNamesIncludingRoot() {
		String[] names = toString().split(String.valueOf(quoteSeparator()));
		if (!usesWindowsFormat && isAbsolute() && names.length > 0) {
			return Arrays.asList(Arrays.copyOfRange(names
		}
		return Arrays.asList(names);
	}

	private static String stripAllSeparators(String path) {
		return path.replaceAll("/"
	}

	private boolean equalRoots(AbstractPath other) {
		String thisRootName = stripAllSeparators(getRoot().toString());
		String otherRootName = stripAllSeparators(other.getRoot().toString());
		if (!thisRootName.equals(otherRootName) || !host.equals(other.getHost())) {
			return false;
		}
		return true;
	}

	private String quoteSeparator() {
		return Matcher.quoteReplacement(String.valueOf(getSeparator()));
	}

	private boolean endsWithSeparator() {
		return path[path.length - 1] == getSeparator();
	}
