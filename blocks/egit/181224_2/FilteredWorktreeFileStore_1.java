package org.eclipse.egit.core.internal.efs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;

public class EgitFileSystem extends FileSystem {

	public static final String SCHEME = "egit-internal"; //$NON-NLS-1$

	@Override
	public IFileStore getStore(IPath path) {
		return EFS.getNullFileSystem().getStore(path);
	}

	@Override
	public IFileStore getStore(URI uri) {
		if (uri.getScheme().equalsIgnoreCase(getScheme())) {
			try {
				UriComponents parsedUri = UriComponents.parse(uri);
				switch (parsedUri.getKind()) {
				case COMMIT:
					throw new URISyntaxException(uri.toString(),
							"COMMIT selector not implemented yet"); //$NON-NLS-1$
				case INDEX:
					throw new URISyntaxException(uri.toString(),
							"INDEX selector not implemented yet"); //$NON-NLS-1$
				case WORKTREE:
					if (StringUtils.isEmptyOrNull(parsedUri.getArguments())) {
						return parsedUri.getBaseFile();
					}
					return new FilteredWorktreeFileStore(parsedUri);
				default:
					throw new URISyntaxException(uri.toString(),
							MessageFormat.format("Unknown selector {0}", //$NON-NLS-1$
									parsedUri.getKind()));
				}
			} catch (IOException | URISyntaxException e) {
				Activator.logError(e.getLocalizedMessage(), e);
			}
		}
		return EFS.getNullFileSystem().getStore(uri);
	}

	@Override
	public boolean canWrite() {
		return true;
	}

	@Override
	public int attributes() {
		return EFS.ATTRIBUTE_READ_ONLY | EFS.ATTRIBUTE_SYMLINK; // immutable?
	}

	public static URI createURI(Repository repository, String gitPath,
			String arguments) throws URISyntaxException {
		File gitDir = repository.getDirectory();
		String repoPath = gitDir.toURI().getPath();
		int n = slashCount(repoPath);
		if (n > 0) {
			n--;
			if (n > 0 && repoPath.length() > 2 && repoPath.charAt(1) == '/') {
				n--;
			}
		}
		URI uri = new URI(SCHEME, null,
				"//" + repoPath + '$' + n + '$' + arguments + '/' + gitPath, //$NON-NLS-1$
				null);
		UriComponents.parse(uri);
		return uri;
	}

	private static int slashCount(String s) {
		int n = 0;
		int l = s.length();
		for (int i = 0; i < l; i++) {
			if (s.charAt(i) == '/') {
				n++;
			}
		}
		return n;
	}

	enum StoreKind {

		INDEX {

			@Override
			public boolean validate(String args) {
				return false; // Not implemented yet
			}
		},

		COMMIT {

			@Override
			public boolean validate(String args) {
				return false; // Not implemented yet
			}
		},

		WORKTREE {

			@Override
			public boolean validate(String args) {
				if (StringUtils.isEmptyOrNull(args)) {
					return true;
				}
				return WORKTREE_ARGS.matcher(args).matches();
			}
		};

		private static final Pattern WORKTREE_ARGS = Pattern
				.compile("[oO]\\d*"); //$NON-NLS-1$

		public abstract boolean validate(String args);
	}

	static class UriComponents {

		private static final Pattern MARKER = Pattern
				.compile("/\\$(\\d+)\\$([^/]*)(?:/|$)"); //$NON-NLS-1$

		private final String repoPath;

		private final int segmentCount;

		private final StoreKind kind;

		private final String arguments;

		private final String gitPath;

		private UriComponents(String repoPath, int segmentCount,
				StoreKind kind, String arguments, String gitPath) {
			this.repoPath = repoPath;
			this.segmentCount = segmentCount;
			this.kind = kind;
			this.arguments = arguments;
			this.gitPath = gitPath;
		}

		static UriComponents parse(URI uri) throws URISyntaxException {
			String path = uri.getPath();
			if (!path.startsWith("/")) { //$NON-NLS-1$
				throw new URISyntaxException(uri.toString(),
						"URI must be absolute"); //$NON-NLS-1$
			}
			Matcher m = MARKER.matcher(path);
			String repoPath;
			String selector;
			String filePath;
			int segments = -1;
			if (m.find()) {
				repoPath = path.substring(0, m.start());
				try {
					segments = Integer.parseInt(m.group(1));
				} catch (NumberFormatException e) {
					throw new URISyntaxException(uri.toString(),
							"Invalid number of segments", //$NON-NLS-1$
							m.start(1));
				}
				selector = m.group(2);
				filePath = path.substring(m.end());
			} else {
				throw new URISyntaxException(uri.toString(),
						"Invalid URI"); //$NON-NLS-1$
			}
			if (repoPath.isEmpty()) {
				throw new URISyntaxException(uri.toString(),
						"No repository path"); //$NON-NLS-1$
			}
			int n = segmentCount(repoPath);
			if (segments >= 0 && n != segments) {
				throw new URISyntaxException(uri.toString(),
						MessageFormat.format(
								"Expected {0} segments for the repository path", //$NON-NLS-1$
								Integer.toString(segments)));
			}
			int i = selector.indexOf(':');
			StoreKind kind = null;
			String arguments = null;
			try {
				if (i < 0) {
					kind = StoreKind.valueOf(selector);
				} else {
					kind = StoreKind.valueOf(selector.substring(0, i));
					arguments = selector.substring(i + 1);
				}
			} catch (IllegalArgumentException e) {
				throw new URISyntaxException(uri.toString(),
						MessageFormat.format("Unknown selector {0}", //$NON-NLS-1$
								selector));
			}
			if (!kind.validate(arguments)) {
				throw new URISyntaxException(uri.toString(),
						MessageFormat.format("Unknown selector arguments {0}", //$NON-NLS-1$
								arguments));
			}
			return new UriComponents(repoPath, segments, kind,
					arguments, filePath);
		}

		private static int segmentCount(String path) {
			int n = slashCount(path);
			if (path.length() > 1 && path.charAt(1) == '/') {
				return n - 1;
			}
			return n;
		}

		UriComponents parent() {
			int i = gitPath.lastIndexOf('/');
			if (i > 0) {
				return new UriComponents(repoPath, segmentCount,
						kind, arguments, gitPath.substring(0, i));
			}
			return null;
		}

		UriComponents child(String name) {
			return new UriComponents(repoPath, segmentCount, kind, arguments,
					gitPath.isEmpty() ? name : gitPath + '/' + name);
		}

		private String path() {
			String args = StringUtils.isEmptyOrNull(arguments) ? "" //$NON-NLS-1$
					: ':' + arguments;
			return repoPath + '/' + '$' + segmentCount + '$' + kind.name()
					+ args + '/' + gitPath;
		}

		URI toUri() {
			try {
				return new URI(SCHEME, null, path(), null);
			} catch (URISyntaxException e) {
				throw new IllegalArgumentException(e);
			}
		}

		IFileStore getBaseFile() throws IOException {
			File gitDir = getRepoDir();
			Repository repository = RepositoryCache.getInstance()
					.lookupRepository(gitDir);
			if (repository == null) {
				throw new IOException(MessageFormat
						.format("Cannot find repository {0}", gitDir)); //$NON-NLS-1$
			}
			File worktree = repository.getWorkTree();
			if (!StringUtils.isEmptyOrNull(gitPath)) {
				worktree = new File(worktree, gitPath);
			}
			return EFS.getLocalFileSystem().fromLocalFile(worktree);
		}

		final Repository getRepository() {
			try {
				return RepositoryCache.getInstance()
						.lookupRepository(getRepoDir());
			} catch (IOException e) {
				return null;
			}
		}

		final File getRepoDir() {
			String path = repoPath;
			if (SystemReader.getInstance().isWindows()) {
				if (path.length() > 2 && path.charAt(0) == '/'
						&& path.charAt(2) == ':') {
					char ch = path.charAt(1);
					if (ch >= 'A' && ch <= 'Z') {
						path = path.substring(1);
					}
				}
			}
			if (File.separatorChar != '/') {
				path = path.replace('/', File.separatorChar);
			}
			return new File(path);
		}

		final int getSegmentCount() {
			return segmentCount;
		}

		final String getGitPath() {
			return gitPath;
		}

		final StoreKind getKind() {
			return kind;
		}

		final String getArguments() {
			return arguments;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			UriComponents other = (UriComponents) obj;
			return segmentCount == other.segmentCount
					&& Objects.equals(repoPath, other.repoPath)
					&& Objects.equals(gitPath, other.gitPath)
					&& Objects.equals(arguments, other.arguments);
		}

		@Override
		public int hashCode() {
			return Objects.hash(repoPath, gitPath, arguments) * 31
					+ Integer.hashCode(segmentCount);
		}

		@Override
		public String toString() {
			return SCHEME + "://" + path(); //$NON-NLS-1$
		}
	}
}
