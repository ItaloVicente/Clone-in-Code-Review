
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackWriter;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.pack.PackConfig;

public class BundleWriter {
	private final Repository db;

	private final ObjectReader reader;

	private final Map<String

	private final Set<RevCommit> assume;

	private final Set<ObjectId> tagTargets;

	private PackConfig packConfig;

	private ObjectCountCallback callback;

	public BundleWriter(Repository repo) {
		db = repo;
		reader = null;
		include = new TreeMap<>();
		assume = new HashSet<>();
		tagTargets = new HashSet<>();
	}

	public BundleWriter(ObjectReader or) {
		db = null;
		reader = or;
		include = new TreeMap<>();
		assume = new HashSet<>();
		tagTargets = new HashSet<>();
	}

	public void setPackConfig(PackConfig pc) {
		this.packConfig = pc;
	}

	public void include(String name
		boolean validRefName = Repository.isValidRefName(name) || Constants.HEAD.equals(name);
		if (!validRefName)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidRefName
		if (include.containsKey(name))
			throw new IllegalStateException(JGitText.get().duplicateRef + name);
		include.put(name
	}

	public void include(Ref r) {
		include(r.getName()

		if (r.getPeeledObjectId() != null)
			tagTargets.add(r.getPeeledObjectId());

		else if (r.getObjectId() != null
				&& r.getName().startsWith(Constants.R_HEADS))
			tagTargets.add(r.getObjectId());
	}

	public void assume(RevCommit c) {
		if (c != null)
			assume.add(c);
	}

	public void writeBundle(ProgressMonitor monitor
			throws IOException {
		try (PackWriter packWriter = newPackWriter()) {
			packWriter.setObjectCountCallback(callback);

			final HashSet<ObjectId> inc = new HashSet<>();
			final HashSet<ObjectId> exc = new HashSet<>();
			inc.addAll(include.values());
			for (RevCommit r : assume)
				exc.add(r.getId());
			packWriter.setIndexDisabled(true);
			packWriter.setDeltaBaseAsOffset(true);
			packWriter.setThin(exc.size() > 0);
			packWriter.setReuseValidatingObjects(false);
			if (exc.isEmpty())
				packWriter.setTagTargets(tagTargets);
			packWriter.preparePack(monitor

			final Writer w = new OutputStreamWriter(os
			w.write(TransportBundle.V2_BUNDLE_SIGNATURE);
			w.write('\n');

			final char[] tmp = new char[Constants.OBJECT_ID_STRING_LENGTH];
			for (RevCommit a : assume) {
				w.write('-');
				a.copyTo(tmp
				if (a.getRawBuffer() != null) {
					w.write(' ');
					w.write(a.getShortMessage());
				}
				w.write('\n');
			}
			for (Map.Entry<String
				e.getValue().copyTo(tmp
				w.write(' ');
				w.write(e.getKey());
				w.write('\n');
			}

			w.write('\n');
			w.flush();
			packWriter.writePack(monitor
		}
	}

	private PackWriter newPackWriter() {
		PackConfig pc = packConfig;
		if (pc == null) {
			pc = db != null ? new PackConfig(db) : new PackConfig();
		}
		return new PackWriter(pc
	}

	public BundleWriter setObjectCountCallback(ObjectCountCallback callback) {
		this.callback = callback;
		return this;
	}
}
