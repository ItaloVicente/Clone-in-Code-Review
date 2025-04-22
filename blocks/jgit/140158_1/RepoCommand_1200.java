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

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.gitrepo.RepoProject.CopyFile;
import org.eclipse.jgit.gitrepo.RepoProject.LinkFile;
import org.eclipse.jgit.gitrepo.RepoProject.ReferenceFile;
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
	private final URI baseUrl;
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
		this.baseUrl = normalizeEmptyPath(URI.create(baseUrl));

		plusGroups = new HashSet<>();
		minusGroups = new HashSet<>();
		if (groups == null || groups.length() == 0
		} else {
			for (String group : groups.split("
					minusGroups.add(group.substring(1));
				else
					plusGroups.add(group);
			}
		}

		remotes = new HashMap<>();
		projects = new ArrayList<>();
		filteredProjects = new ArrayList<>();
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
			throw new IOException(RepoText.get().errorParsingManifestFile
		}
	}

	@Override
	public void startElement(
			String uri
			String localName
			String qName
			Attributes attributes) throws SAXException {
		if (null != qName) switch (qName) {
                case "project":
                        throw new SAXException(RepoText.get().invalidManifest);
                    }
                    currentProject = new RepoProject(
                            attributes.getValue("name")
                            attributes.getValue("path")
                            attributes.getValue("revision")
                            attributes.getValue("remote")
                    currentProject.setRecommendShallow(
                    break;
                case "remote":
                    Remote remote = new Remote(fetch
                    remotes.put(attributes.getValue("name")
                    if (alias != null)
                        remotes.put(alias
                    break;
                case "default":
                    break;
                case "copyfile":
                    if (currentProject == null)
                        throw new SAXException(RepoText.get().invalidManifest);
                    currentProject.addCopyFile(new CopyFile(
                            rootRepo
                            currentProject.getPath()
                            attributes.getValue("src")
                    break;
                case "linkfile":
                    if (currentProject == null) {
                        throw new SAXException(RepoText.get().invalidManifest);
                    }
                    currentProject.addLinkFile(new LinkFile(
                            rootRepo
                            currentProject.getPath()
                            attributes.getValue("src")
                    break;
                case "include":{
                    if (includedReader != null) {
                        try (InputStream is = includedReader.readIncludeFile(name)) {
                            if (is == null) {
                                throw new SAXException(
                                        RepoText.get().errorIncludeNotImplemented);
                            }
                            read(is);
                        } catch (Exception e) {
                            throw new SAXException(MessageFormat.format(
                                    RepoText.get().errorIncludeFile
                        }
                    } else if (filename != null) {
                        int index = filename.lastIndexOf('/');
                        String path = filename.substring(0
                        try (InputStream is = new FileInputStream(path)) {
                            read(is);
                        } catch (IOException e) {
                            throw new SAXException(MessageFormat.format(
                                    RepoText.get().errorIncludeFile
                        }
                    }
                        break;
                    }
                case "remove-project":{
                    projects.removeIf((p) -> p.getName().equals(name));
                        break;
                    }
                default:
                    break;
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
		if (defaultRevision == null && defaultRemote != null) {
			Remote remote = remotes.get(defaultRemote);
			if (remote != null) {
				defaultRevision = remote.revision;
			}
			if (defaultRevision == null) {
				defaultRevision = defaultBranch;
			}
		}
		for (RepoProject proj : projects) {
			String remote = proj.getRemote();
			String revision = defaultRevision;
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
			} else {
				Remote r = remotes.get(remote);
				if (r != null && r.revision != null) {
					revision = r.revision;
				}
			}
			URI remoteUrl = remoteUrls.get(remote);
			if (remoteUrl == null) {
				String fetch = remotes.get(remote).fetch;
				if (fetch == null) {
					throw new SAXException(MessageFormat
							.format(RepoText.get().errorNoFetch
				}
				remoteUrl = normalizeEmptyPath(baseUrl.resolve(fetch));
				remoteUrls.put(remote
			}
			proj.setUrl(remoteUrl.resolve(proj.getName()).toString())
				.setDefaultRevision(revision);
		}

		filteredProjects.addAll(projects);
		removeNotInGroup();
		removeOverlaps();
	}

	static URI normalizeEmptyPath(URI u) {
		if (u.getHost() != null && !u.getHost().isEmpty() &&
			(u.getPath() == null || u.getPath().isEmpty())) {
			try {
				return new URI(u.getScheme()
					u.getUserInfo()
						"/"
			} catch (URISyntaxException x) {
				throw new IllegalArgumentException(x.getMessage()
			}
		}
		return u;
	}

	public List<RepoProject> getProjects() {
		return projects;
	}

	@NonNull
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
		removeNestedCopyAndLinkfiles();
	}

	private void removeNestedCopyAndLinkfiles() {
		for (RepoProject proj : filteredProjects) {
			List<CopyFile> copyfiles = new ArrayList<>(proj.getCopyFiles());
			proj.clearCopyFiles();
			for (CopyFile copyfile : copyfiles) {
				if (!isNestedReferencefile(copyfile)) {
					proj.addCopyFile(copyfile);
				}
			}
			List<LinkFile> linkfiles = new ArrayList<>(proj.getLinkFiles());
			proj.clearLinkFiles();
			for (LinkFile linkfile : linkfiles) {
				if (!isNestedReferencefile(linkfile)) {
					proj.addLinkFile(linkfile);
				}
			}
		}
	}

	boolean inGroups(RepoProject proj) {
		for (String group : minusGroups) {
			if (proj.inGroup(group)) {
				return false;
			}
		}
			return true;
		}
		for (String group : plusGroups) {
			if (proj.inGroup(group))
				return true;
		}
		return false;
	}

	private boolean isNestedReferencefile(ReferenceFile referencefile) {
		if (referencefile.dest.indexOf('/') == -1) {
			return false;
		}
		for (RepoProject proj : filteredProjects) {
			if (proj.getPath().compareTo(referencefile.dest) > 0) {
				return false;
			}
			if (proj.isAncestorOf(referencefile.dest)) {
				return true;
			}
		}
		return false;
	}

	private static class Remote {
		final String fetch;
		final String revision;

		Remote(String fetch
			this.fetch = fetch;
			this.revision = revision;
		}
	}
}
