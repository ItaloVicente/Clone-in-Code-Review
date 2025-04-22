package org.eclipse.egit.core.internal.merge;

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.variants.IResourceVariant;
import org.eclipse.team.core.variants.IResourceVariantTree;

public class EmptyResourceVariantTreeProvider implements
		GitResourceVariantTreeProvider {
	private IResourceVariantTree baseTree;

	private IResourceVariantTree remoteTree;

	private IResourceVariantTree sourceTree;

	public EmptyResourceVariantTreeProvider() {
		this.baseTree = new EmptyResourceVariantTree();
		this.remoteTree = new EmptyResourceVariantTree();
		this.sourceTree = new EmptyResourceVariantTree();
	}

	public IResourceVariantTree getBaseTree() {
		return baseTree;
	}

	public IResourceVariantTree getRemoteTree() {
		return remoteTree;
	}

	public IResourceVariantTree getSourceTree() {
		return sourceTree;
	}

	public Set<IResource> getRoots() {
		return Collections.emptySet();
	}

	public Set<IResource> getKnownResources() {
		return Collections.emptySet();
	}

	private static class EmptyResourceVariantTree implements
			IResourceVariantTree {
		public IResource[] roots() {
			return new IResource[0];
		}

		public IResource[] members(IResource resource) throws TeamException {
			return new IResource[0];
		}

		public IResourceVariant getResourceVariant(IResource resource)
				throws TeamException {
			return null;
		}

		public boolean hasResourceVariant(IResource resource)
				throws TeamException {
			return false;
		}

		public IResource[] refresh(IResource[] resources, int depth,
				IProgressMonitor monitor) throws TeamException {
			return new IResource[0];
		}

		public void flushVariants(IResource resource, int depth)
				throws TeamException {
		}

	}
}
