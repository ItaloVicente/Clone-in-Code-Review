package org.eclipse.egit.core.internal.merge;

import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.team.core.variants.IResourceVariantTree;

public interface GitResourceVariantTreeProvider {
	IResourceVariantTree getBaseTree();

	IResourceVariantTree getRemoteTree();

	IResourceVariantTree getSourceTree();

	Set<IResource> getRoots();

	Set<IResource> getKnownResources();
}
