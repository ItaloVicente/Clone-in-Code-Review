package org.eclipse.jgit.gitrepo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.gitrepo.RepoProject.CopyFile;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class ManifestParser extends DefaultHandler {
	private final String filename;
	private final String baseUrl;
	private final String defaultBranch;
	private final Repository rootRepo;
	private final Map<String
	private final Set<String> plusGroups;
	private final Set<String> minusGroups;
	private final List<RepoProject> projects;
	private final List<RepoProject> filteredProjects;
	private final IncludedFileReader includedReader;

	private String defaultRemote;
	private String defaultRevision;
	private int xmlInRead;
	private RepoProject currentProject;

	public interface IncludedFileReader {
		public InputStream readIncludeFile(String path)
				throws GitAPIException
	}

	public ManifestParser(IncludedFileReader includedReader
			String defaultBranch
			Repository rootRepo) {
		this.includedReader = includedReader;
		this.filename = filename;
		this.defaultBranch = defaultBranch;
		this.rootRepo = rootRepo;

		int lastIndex = baseUrl.length() - 1;
		while (lastIndex >= 0 && baseUrl.charAt(lastIndex) == '/')
			lastIndex--;
		this.baseUrl = baseUrl.substring(0

		plusGroups = new HashSet<String>();
		minusGroups = new HashSet<String>();
		if (groups == null || groups.length() == 0
		} else {
			for (String group : groups.split("
					minusGroups.add(group.substring(1));
				else
					plusGroups.add(group);
			}
		}

		remotes = new HashMap<String
		projects = new ArrayList<RepoProject>();
		filteredProjects = new ArrayList<RepoProject>();
	}

	public void read(InputStream inputStream) throws IOException {
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
			String uri
			String localName
			String qName
			Attributes attributes) throws SAXException {
			currentProject = new RepoProject(
					attributes.getValue("name")
					attributes.getValue("path")
					attributes.getValue("revision")
					attributes.getValue("remote")
			remotes.put(attributes.getValue("name")
			if (alias != null)
				remotes.put(alias
			if (defaultRevision == null)
				defaultRevision = defaultBranch;
			if (currentProject == null)
				throw new SAXException(RepoText.get().invalidManifest);
			currentProject.addCopyFile(new CopyFile(
						rootRepo
						currentProject.path
						attributes.getValue("src")
			InputStream is = null;
			if (includedReader != null) {
				try {
					is = includedReader.readIncludeFile(name);
				} catch (Exception e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile
				}
			} else if (filename != null) {
				int index = filename.lastIndexOf('/');
				String path = filename.substring(0
				try {
					is = new FileInputStream(path);
				} catch (IOException e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile
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
			String uri
			String localName
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

		Map<String
		URI baseUri;
		try {
			baseUri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			throw new SAXException(e);
		}
		for (RepoProject proj : projects) {
			String remote = proj.remote;
			if (remote == null) {
				if (defaultRemote == null) {
					if (filename != null)
						throw new SAXException(MessageFormat.format(
								RepoText.get().errorNoDefaultFilename
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
				remoteUrls.put(remote
			}
			proj.setUrl(remoteUrl + proj.name)
					.setDefaultRevision(defaultRevision);
		}

		filteredProjects.addAll(projects);
		removeNotInGroup();
		removeOverlaps();
	}

	public List<RepoProject> getProjects() {
		return projects;
	}

	public List<RepoProject> getFilteredProjects() {
		return filteredProjects;
	}

	void removeNotInGroup() {
		Iterator<RepoProject> iter = filteredProjects.iterator();
		while (iter.hasNext())
			if (!inGroups(iter.next()))
				iter.remove();
	}

	void removeOverlaps() {
		Collections.sort(filteredProjects);
		Iterator<RepoProject> iter = filteredProjects.iterator();
		if (!iter.hasNext())
			return;
		RepoProject last = iter.next();
		while (iter.hasNext()) {
			RepoProject p = iter.next();
			if (last.isAncestorOf(p))
				iter.remove();
			else
				last = p;
		}
	}

	boolean inGroups(RepoProject proj) {
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
		}
		return false;
	}
}
