package org.eclipse.jgit.gitrepo;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.SubmoduleAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.gitrepo.internal.RepoText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class RepoCommand extends GitCommand<Void> {

	private String path;

	private String uri;

	private ProgressMonitor monitor;

	private static class Project {
		final String name;
		final String path;

		Project(String name
			this.name = name;
			this.path = path;
		}
	}

	private static class XmlManifest extends DefaultHandler {
		private final RepoCommand command;
		private final String filename;
		private final String baseUrl;
		private final Map<String
		private final List<Project> projects;
		private String defaultRemote;

		XmlManifest(RepoCommand command
			this.command = command;
			this.filename = filename;
			this.baseUrl = baseUrl;
			remotes = new HashMap<String
			projects = new ArrayList<Project>();
		}

		void read() throws IOException {
			final XMLReader xr;
			try {
				xr = XMLReaderFactory.createXMLReader();
			} catch (SAXException e) {
				throw new IOException(JGitText.get().noXMLParserAvailable);
			}
			xr.setContentHandler(this);
			final FileInputStream in = new FileInputStream(filename);
			try {
				xr.parse(new InputSource(in));
			} catch (SAXException e) {
				throw new IOException(MessageFormat.format(
							RepoText.get().errorParsingFromFile
			} finally {
				in.close();
			}
		}

		@Override
		public void startElement(
				String uri
				String localName
				String qName
				Attributes attributes) throws SAXException {
			if ("project".equals(qName)) {
				projects.add(new Project(attributes.getValue("name")
			} else if ("remote".equals(qName)) {
				remotes.put(attributes.getValue("name")
						attributes.getValue("fetch"));
			} else if ("default".equals(qName)) {
				defaultRemote = attributes.getValue("remote");
			} else if ("copyfile".equals(qName)) {
			}
		}

		@Override
		public void endDocument() throws SAXException {
			if (defaultRemote == null) {
				throw new SAXException(MessageFormat.format(
						RepoText.get().errorNoDefault
			}
			final String remoteUrl;
			try {
				URI uri = new URI(String.format("%s/%s/"
				remoteUrl = uri.normalize().toString();
			} catch (URISyntaxException e) {
				throw new SAXException(e);
			}
			for (Project proj : projects) {
				String url = remoteUrl + proj.name;
				command.addSubmodule(url
			}
		}
	}

	private static class ManifestErrorException extends GitAPIException {
		ManifestErrorException(Throwable cause) {
			super(RepoText.get().invalidManifest
		}
	}

	public RepoCommand(final Repository repo) {
		super(repo);
	}

	public RepoCommand setPath(final String path) {
		this.path = path;
		return this;
	}

	public RepoCommand setURI(final String uri) {
		this.uri = uri;
		return this;
	}

	public RepoCommand setProgressMonitor(final ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public Void call() throws GitAPIException {
		checkCallable();
		if (path == null || path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().pathNotConfigured);
		if (uri == null || uri.length() == 0)
			throw new IllegalArgumentException(JGitText.get().uriNotConfigured);

		XmlManifest manifest = new XmlManifest(this
		try {
			manifest.read();
		} catch (IOException e) {
			throw new ManifestErrorException(e);
		}

		return null;
	}

	private void addSubmodule(String url
		SubmoduleAddCommand add = new SubmoduleAddCommand(repo);
		try {
			Repository sub = add.setPath(name).setURI(url).call();
		} catch (GitAPIException e) {
			throw new SAXException(e);
		}
	}
}
