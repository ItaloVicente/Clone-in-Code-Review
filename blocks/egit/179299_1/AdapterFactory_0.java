package org.eclipse.egit.core.info;

import org.eclipse.jgit.lib.Repository;

public interface GitInfo {

	Repository getRepository();

	String getGitPath();

}
