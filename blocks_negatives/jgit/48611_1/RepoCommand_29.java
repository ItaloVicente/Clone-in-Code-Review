				result = reader.open(oid).getBytes(Integer.MAX_VALUE);
			} finally {
				reader.release();
			}
			return result;
		}
	}

	/**
	 * A callback to read included xml files.
	 *
	 * @since 3.5
	 */
	public interface IncludedFileReader {
		/**
		 * Read a file from the same base dir of the manifest xml file.
		 *
		 * @param path
		 *            The relative path to the file to read
		 * @return the {@code InputStream} of the file.
		 * @throws GitAPIException
		 * @throws IOException
		 */
		public InputStream readIncludeFile(String path)
				throws GitAPIException, IOException;
	}

	private static class CopyFile {
		final Repository repo;
		final String path;
		final String src;
		final String dest;

		CopyFile(Repository repo, String path, String src, String dest) {
			this.repo = repo;
			this.path = path;
			this.src = src;
			this.dest = dest;
		}

		void copy() throws IOException {
			File srcFile = new File(repo.getWorkTree(),
			File destFile = new File(repo.getWorkTree(), dest);
			FileInputStream input = new FileInputStream(srcFile);
			try {
				FileOutputStream output = new FileOutputStream(destFile);
				try {
					FileChannel channel = input.getChannel();
					output.getChannel().transferFrom(channel, 0, channel.size());
				} finally {
					output.close();
				}
			} finally {
				input.close();
			}
		}
	}

	private static class Project implements Comparable<Project> {
		final String name;
		final String path;
		final String revision;
		final String remote;
		final Set<String> groups;
		final List<CopyFile> copyfiles;

		Project(String name, String path, String revision,
				String remote, String groups) {
			this.name = name;
			if (path != null)
				this.path = path;
			else
				this.path = name;
			this.revision = revision;
			this.remote = remote;
			this.groups = new HashSet<String>();
			if (groups != null && groups.length() > 0)
				this.groups.addAll(Arrays.asList(groups.split(",))); //$NON-NLS-1$
-			copyfiles = new ArrayList<CopyFile>();
-		}
-
-		void addCopyFile(CopyFile copyfile) {
-			copyfiles.add(copyfile);
-		}
-
-		String getPathWithSlash() {
-			if (path.endsWith(/")) //$NON-NLS-1$
				return path;
			else
		}

		boolean isAncestorOf(Project that) {
			return that.getPathWithSlash().startsWith(this.getPathWithSlash());
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Project) {
				Project that = (Project) o;
				return this.getPathWithSlash().equals(that.getPathWithSlash());
			}
			return false;
		}

		@Override
		public int hashCode() {
			return this.getPathWithSlash().hashCode();
		}

		public int compareTo(Project that) {
			return this.getPathWithSlash().compareTo(that.getPathWithSlash());
		}
	}

	private static class XmlManifest extends DefaultHandler {
		private final RepoCommand command;
		private final String filename;
		private final String baseUrl;
		private final Map<String, String> remotes;
		private final Set<String> plusGroups;
		private final Set<String> minusGroups;
		private List<Project> projects;
		private String defaultRemote;
		private String defaultRevision;
		private IncludedFileReader includedReader;
		private int xmlInRead;
		private Project currentProject;

		XmlManifest(RepoCommand command, IncludedFileReader includedReader,
				String filename, String baseUrl, String groups) {
			this.command = command;
			this.includedReader = includedReader;
			this.filename = filename;

			int lastIndex = baseUrl.length() - 1;
			while (lastIndex >= 0 && baseUrl.charAt(lastIndex) == '/')
				lastIndex--;
			this.baseUrl = baseUrl.substring(0, lastIndex + 1);

			remotes = new HashMap<String, String>();
			projects = new ArrayList<Project>();
			plusGroups = new HashSet<String>();
			minusGroups = new HashSet<String>();
			} else {
				for (String group : groups.split(",)) { //$NON-NLS-1$
-					if (group.startsWith(-")) //$NON-NLS-1$
						minusGroups.add(group.substring(1));
					else
						plusGroups.add(group);
				}
			}
		}

		void read(InputStream inputStream) throws IOException {
			xmlInRead++;
			final XMLReader xr;
			try {
				xr = XMLReaderFactory.createXMLReader();
			} catch (SAXException e) {
				throw new IOException(JGitText.get().noXMLParserAvailable);
			}
			xr.setContentHandler(this);
			try {
				xr.parse(new InputSource(inputStream));
			} catch (SAXException e) {
				IOException error = new IOException(
							RepoText.get().errorParsingManifestFile);
				error.initCause(e);
				throw error;
			}
		}

		@Override
		public void startElement(
				String uri,
				String localName,
				String qName,
				Attributes attributes) throws SAXException {
				currentProject = new Project(
						attributes.getValue("name"), //$NON-NLS-1$
						attributes.getValue("path"), //$NON-NLS-1$
						attributes.getValue("revision"), //$NON-NLS-1$
						attributes.getValue("remote"), //$NON-NLS-1$
				remotes.put(attributes.getValue("name"), fetch); //$NON-NLS-1$
				if (alias != null)
					remotes.put(alias, fetch);
				if (defaultRevision == null)
					defaultRevision = command.branch;
				if (currentProject == null)
					throw new SAXException(RepoText.get().invalidManifest);
				currentProject.addCopyFile(new CopyFile(
							command.repo,
							currentProject.path,
							attributes.getValue("src"), //$NON-NLS-1$
				InputStream is = null;
				if (includedReader != null) {
					try {
						is = includedReader.readIncludeFile(name);
					} catch (Exception e) {
						throw new SAXException(MessageFormat.format(
								RepoText.get().errorIncludeFile, name), e);
					}
				} else if (filename != null) {
					int index = filename.lastIndexOf('/');
					String path = filename.substring(0, index + 1) + name;
					try {
						is = new FileInputStream(path);
					} catch (IOException e) {
						throw new SAXException(MessageFormat.format(
								RepoText.get().errorIncludeFile, path), e);
					}
				}
				if (is == null) {
					throw new SAXException(
							RepoText.get().errorIncludeNotImplemented);
				}
				try {
					read(is);
				} catch (IOException e) {
					throw new SAXException(e);
				}
			}
		}

		@Override
		public void endElement(
				String uri,
				String localName,
				String qName) throws SAXException {
				projects.add(currentProject);
				currentProject = null;
			}
		}

		@Override
		public void endDocument() throws SAXException {
			xmlInRead--;
			if (xmlInRead != 0)
				return;

			removeNotInGroup();
			removeOverlaps();

			Map<String, String> remoteUrls = new HashMap<String, String>();
			URI baseUri;
			try {
				baseUri = new URI(baseUrl);
			} catch (URISyntaxException e) {
				throw new SAXException(e);
			}
			for (Project proj : projects) {
				String remote = proj.remote;
				if (remote == null) {
					if (defaultRemote == null) {
						if (filename != null)
							throw new SAXException(MessageFormat.format(
									RepoText.get().errorNoDefaultFilename,
									filename));
						else
							throw new SAXException(
									RepoText.get().errorNoDefault);
					}
					remote = defaultRemote;
				}
				String remoteUrl = remoteUrls.get(remote);
				if (remoteUrl == null) {
					remoteUrl = baseUri.resolve(remotes.get(remote)).toString();
					remoteUrls.put(remote, remoteUrl);
				}

				command.addSubmodule(remoteUrl + proj.name,
						proj.path,
						proj.revision == null
								? defaultRevision : proj.revision,
						proj.copyfiles);
			}
		}

		/** Remove projects that are not in our desired groups. */
		void removeNotInGroup() {
			Iterator<Project> iter = projects.iterator();
			while (iter.hasNext())
				if (!inGroups(iter.next()))
					iter.remove();
		}

		/** Remove projects that sits in a subdirectory of any other project. */
		void removeOverlaps() {
			Collections.sort(projects);
			Iterator<Project> iter = projects.iterator();
			if (!iter.hasNext())
				return;
			Project last = iter.next();
			while (iter.hasNext()) {
				Project p = iter.next();
				if (last.isAncestorOf(p))
					iter.remove();
				else
					last = p;
			}
		}

		boolean inGroups(Project proj) {
			for (String group : minusGroups) {
				if (proj.groups.contains(group)) {
					return false;
				}
			}
				return true;
			}
			for (String group : plusGroups) {
				if (proj.groups.contains(group))
					return true;
