package org.eclipse.egit.core;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.ignore.SimpleIgnoreCache;
public class IgnoreCache extends SimpleIgnoreCache {


	public IgnoreCache(RepositoryMapping mapping) {
		super(mapping.getRepository());
	}

	public void refreshNode(IResource rsrc) {
		addIgnoreNode(new File(rsrc.getParent().getRawLocationURI()));
	}

}
