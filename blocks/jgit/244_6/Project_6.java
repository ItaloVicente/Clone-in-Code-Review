
package org.eclipse.jgit.iplog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileBasedConfig;
import org.eclipse.jgit.lib.LockFile;

public class IpLogMeta {
	public static final String IPLOG_CONFIG_FILE = ".eclipse_iplog";

	private static final String S_PROJECT = "project";

	private static final String S_CQ = "CQ";

	private static final String K_NAME = "name";

	private static final String K_COMMENTS = "comments";

	private static final String K_LICENSE = "license";

	private static final String K_DESCRIPTION = "description";

	private static final String K_USE = "use";

	private static final String K_STATE = "state";

	private List<Project> projects = new ArrayList<Project>();

	private Set<CQ> cqs = new HashSet<CQ>();

	List<Project> getProjects() {
		return projects;
	}

	Set<CQ> getCQs() {
		return cqs;
	}

	void loadFrom(Config cfg) {
		projects.clear();
		cqs.clear();

		for (String id : cfg.getSubsections(S_PROJECT)) {
			String name = cfg.getString(S_PROJECT
			Project project = new Project(id
			project.setComments(cfg.getString(S_PROJECT

			for (String license : cfg.getStringList(S_PROJECT
				project.addLicense(license);
			projects.add(project);
		}

		for (String id : cfg.getSubsections(S_CQ)) {
			CQ cq = new CQ(Long.parseLong(id));
			cq.setDescription(cfg.getString(S_CQ
			cq.setLicense(cfg.getString(S_CQ
			cq.setUse(cfg.getString(S_CQ
			cq.setState(cfg.getString(S_CQ
			cq.setComments(cfg.getString(S_CQ
			cqs.add(cq);
		}
	}

	public void syncCQs(File file
			throws IOException
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		LockFile lf = new LockFile(file);
		if (!lf.lock())
			throw new IOException("Cannot lock " + file);
		try {
			FileBasedConfig cfg = new FileBasedConfig(file);
			cfg.load();
			loadFrom(cfg);

			IPZillaQuery ipzilla = new IPZillaQuery(base
			Set<CQ> current = ipzilla.getCQs(projects);

			for (CQ cq : sort(current
				String id = Long.toString(cq.getID());

				set(cfg
				set(cfg
				set(cfg
				set(cfg
				set(cfg
			}

			for (CQ cq : cqs) {
				if (!current.contains(cq))
					cfg.unsetSection(S_CQ
			}

			lf.write(Constants.encode(cfg.toText()));
			if (!lf.commit())
				throw new IOException("Cannot write " + file);
		} finally {
			lf.unlock();
		}
	}

	private static void set(Config cfg
			String key
		if (value == null || "".equals(value))
			cfg.unset(section
		else
			cfg.setString(section
	}

	private static <T
			Collection<T> objs
		ArrayList<T> sorted = new ArrayList<T>(objs);
		Collections.sort(sorted
		return sorted;
	}
}
