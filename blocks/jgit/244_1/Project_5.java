
package org.eclipse.jgit.iplog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.iplog.Committer.ActiveRange;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BlobBasedConfig;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.WindowCursor;
import org.eclipse.jgit.revwalk.FooterKey;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.RawParseUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class IpLogGenerator {

	private static final String IPLOG_PFX = "iplog:";


	private static final String IPLOG_CONFIG_FILE = ".eclipse_iplog";

	private static final String S_PROJECT = "project";

	private static final String K_NAME = "name";

	private static final String K_COMMENTS = "comments";

	private static final String K_LICENSE = "license";

	private static final FooterKey BUG = new FooterKey("Bug");

	private final Map<String

	private final Map<String

	private final Map<String

	private final Map<String

	private final Set<CQ> cqs = new HashSet<CQ>();

	private String characterEncoding = "UTF-8";

	private Repository db;

	private RevWalk rw;

	private TreeWalk tw;

	private final WindowCursor curs = new WindowCursor();

	private final MutableObjectId idbuf = new MutableObjectId();

	private Document doc;

	public IpLogGenerator() {
	}

	public void setCharacterEncoding(String encodingName) {
		characterEncoding = encodingName;
	}

	public void scan(Repository repo
			ConfigInvalidException {
		try {
			db = repo;
			rw = new RevWalk(db);
			tw = new TreeWalk(db);

			RevObject startObject = rw.parseAny(startId);
			RevCommit startCommit = rw.parseCommit(startObject);
			rw.reset();
			rw.markStart(startCommit);

			loadEclipseIpLog(startObject
			loadCommitters(repo);
			scanProjectCommits();
		} finally {
			WindowCursor.release(curs);
			db = null;
			rw = null;
			tw = null;
		}
	}

	private void loadEclipseIpLog(RevObject startObject
			throws IOException
		TreeWalk log = TreeWalk.forPath(db
		if (log == null)
			return;

		Config cfg;
		try {
			cfg = new BlobBasedConfig(null
		} catch (ConfigInvalidException e) {
			throw new ConfigInvalidException("Configuration file "
					+ log.getPathString() + " in commit " + start.name()
					+ " is invalid"
		}

		for (String id : cfg.getSubsections(S_PROJECT)) {
			Project project = projects.get(id);
			if (project == null) {
				String name = cfg.getString(S_PROJECT
				project = new Project(id
				project.setComments(cfg.getString(S_PROJECT

				if (startObject instanceof RevTag)
					project.setVersion(((RevTag) startObject).getTagName());
				else
					project.setVersion(start.name());

				projects.put(project.getID()
			}

			for (String license : cfg.getStringList(S_PROJECT
				project.addLicense(license);
		}
	}

	private void loadCommitters(Repository repo) throws IOException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File list = new File(repo.getDirectory()
		BufferedReader br = new BufferedReader(new FileReader(list));
		String line;

		while ((line = br.readLine()) != null) {
			String[] field = line.trim().split(" *\\| *");
			String id = field[0];
			String name = field[1];
			String email = field[2];
			Date begin = parseDate(dt
			Date end = parseDate(dt

			Committer who = committersById.get(id);
			if (who == null) {
				who = new Committer(id);
				int sp = name.indexOf(' ');
				if (0 < sp) {
					who.setFirstName(name.substring(0
					who.setLastName(name.substring(sp + 1).trim());
				} else {
					who.setFirstName(name);
					who.setLastName(null);
				}

				sp = email.indexOf('@');
				if (0 < sp)
					who.setAffiliation(email.substring(sp + 1));
				committersById.put(who.getID()
			}

			who.addEmailAddress(email);
			who.addActiveRange(new ActiveRange(begin
			committersByEmail.put(email
		}
	}

	private Date parseDate(SimpleDateFormat dt
			throws IOException {
		if ("NULL".equals(value) || "".equals(value) || value == null)
			return null;
		int dot = value.indexOf('.');
		if (0 < dot)
			value = value.substring(0
		try {
			return dt.parse(value);
		} catch (ParseException e) {
			IOException err = new IOException("Invalid date: " + value);
			err.initCause(e);
			throw err;
		}
	}

	private void scanProjectCommits() throws IOException {
		RevCommit commit;
		while ((commit = rw.next()) != null) {
			final PersonIdent author = commit.getAuthorIdent();
			final Date when = author.getWhen();

			Committer who = committersByEmail.get(author.getEmailAddress());
			if (who != null && who.inRange(when)) {
				who.setHasCommits(true);
				continue;
			}

			Contributor contributor = contributorsByName.get(author.getName());
			if (contributor == null) {
				String id = null;
				String name = author.getName();
				contributor = new Contributor(id
				contributorsByName.put(name
			}

			String id = commit.name();
			String subj = commit.getShortMessage();
			SingleContribution item = new SingleContribution(id

			List<String> bugs = commit.getFooterLines(BUG);
			if (1 == bugs.size()) {
				item.setBugID(bugs.get(0));

			} else if (2 <= bugs.size()) {
				StringBuilder tmp = new StringBuilder();
				for (String bug : bugs) {
					if (tmp.length() > 0)
						tmp.append("
				contributor.add(item);
				continue;
			}

			int addedLines = 0;
			if (1 == commit.getParentCount()) {
				final RevCommit parent = commit.getParent(0);
				tw.setFilter(TreeFilter.ANY_DIFF);
				tw.setRecursive(true);
				tw.reset(new RevTree[] { parent.getTree()
				while (tw.next()) {
					if (tw.getFileMode(1).getObjectType() != Constants.OBJ_BLOB)
						continue;

					byte[] oldImage;
					if (tw.getFileMode(0).getObjectType() == Constants.OBJ_BLOB)
						oldImage = openBlob(0);
					else
						oldImage = new byte[0];

					EditList edits = new MyersDiff(new RawText(oldImage)
							new RawText(openBlob(1))).getEdits();
					for (Edit e : edits)
						addedLines += e.getEndB() - e.getBeginB();
				}

				tw.setFilter(TreeFilter.ALL);
				tw.setRecursive(true);
				tw.reset(commit.getTree());
				while (tw.next()) {
					if (tw.getFileMode(0).getObjectType() == Constants.OBJ_BLOB) {
						byte[] buf = openBlob(0);
						for (int ptr = 0; ptr < buf.length;) {
							ptr += RawParseUtils.nextLF(buf
							addedLines++;
						}
					}
				}
			}

			if (addedLines < 0)
				throw new IOException("Incorrectly scanned " + commit.name());
			if (1 == addedLines)
				item.setSize("+1 line");
			else
				item.setSize("+" + addedLines + " lines");
			contributor.add(item);
		}
	}

	private byte[] openBlob(int side) throws IOException {
		tw.getObjectId(idbuf
		ObjectLoader ldr = db.openObject(curs
		if (ldr == null)
			throw new MissingObjectException(idbuf.copy()
		return ldr.getCachedBytes();
	}

	public void loadCQs(URL base
			throws IOException {
		IPZillaQuery ipzilla = new IPZillaQuery(base
		cqs.addAll(ipzilla.getCQs(projects.values()));
	}

	public void writeTo(OutputStream out) throws IOException {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer s = factory.newTransformer();
			s.setOutputProperty(OutputKeys.ENCODING
			s.setOutputProperty(OutputKeys.METHOD
			s.setOutputProperty(OutputKeys.INDENT
			s.setOutputProperty(INDENT
			s.transform(new DOMSource(toXML())
		} catch (ParserConfigurationException e) {
			IOException err = new IOException("Cannot serialize XML");
			err.initCause(e);
			throw err;

		} catch (TransformerConfigurationException e) {
			IOException err = new IOException("Cannot serialize XML");
			err.initCause(e);
			throw err;

		} catch (TransformerException e) {
			IOException err = new IOException("Cannot serialize XML");
			err.initCause(e);
			throw err;
		}
	}

	private Document toXML() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		doc = factory.newDocumentBuilder().newDocument();

		Element root = createElement("iplog");
		doc.appendChild(root);

		if (projects.size() == 1) {
			Project soleProject = projects.values().iterator().next();
			root.setAttribute("name"
		}

		Set<String> licenses = new TreeSet<String>();
		for (Project project : sort(projects
			root.appendChild(createProject(project));
			licenses.addAll(project.getLicenses());
		}
		for (String name : sort(licenses))
			root.appendChild(createLicense(name));

		if (!cqs.isEmpty())
			appendBlankLine(root);
		for (CQ cq : sort(cqs
			root.appendChild(createCQ(cq));

		if (!committersByEmail.isEmpty())
			appendBlankLine(root);
		for (Committer committer : sort(committersById
			root.appendChild(createCommitter(committer));

		for (Contributor c : sort(contributorsByName
			appendBlankLine(root);
			root.appendChild(createContributor(c));
		}

		return doc;
	}

	private void appendBlankLine(Element root) {
		root.appendChild(doc.createTextNode("\n\n  "));
	}

	private Element createProject(Project p) {
		Element project = createElement(S_PROJECT);
		required(project
		required(project
		optional(project
		optional(project
		return project;
	}

	private Element createLicense(String name) {
		Element license = createElement("license");
		required(license
		optional(license
		optional(license
		return license;
	}

	private Element createCQ(CQ cq) {
		Element r = createElement("cq");
		required(r
		required(r
		optional(r
		optional(r
		optional(r
		optional(r
		return r;
	}

	private Element createCommitter(Committer who) {
		Element r = createElement("committer");
		required(r
		required(r
		required(r
		required(r
		required(r
		required(r
		optional(r
		return r;
	}

	private Element createContributor(Contributor c) {
		Element r = createElement("contributor");
		required(r
		required(r

		for (SingleContribution s : sort(c.getContributions()
				SingleContribution.COMPARATOR))
			r.appendChild(createContribution(s));

		return r;
	}

	private Element createContribution(SingleContribution s) {
		Element r = createElement("bug");
		required(r
		optional(r
		required(r
		required(r
		required(r
		required(r
		return r;
	}

	private String format(Date created) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(created);
	}

	private Element createElement(String name) {
		return doc.createElementNS(IPLOG_NS
	}

	private void required(Element r
		if (value == null)
			value = "";
		r.setAttribute(name
	}

	private void optional(Element r
		if (value != null && value.length() > 0)
			r.setAttribute(name
	}

	private static <T
			Collection<T> objs
		ArrayList<T> sorted = new ArrayList<T>(objs);
		Collections.sort(sorted
		return sorted;
	}

	private static <T
			Map<?
		return sort(objs.values()
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable> Iterable<T> sort(Collection<T> objs) {
		ArrayList<T> sorted = new ArrayList<T>(objs);
		Collections.sort(sorted);
		return sorted;
	}
}
