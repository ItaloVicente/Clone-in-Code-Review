
package org.eclipse.e4.ui.internal.workbench;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.osgi.framework.Bundle;
import org.osgi.framework.namespace.BundleNamespace;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

public class ExtensionsSort extends TopologicalSort<IExtension, Bundle> {

	@Override
	protected Bundle getId(IExtension extension) {
		IContributor contributor = extension.getContributor();
		return Activator.getDefault().getBundleForName(contributor.getName());
	}

	@Override
	protected Collection<Bundle> getRequirements(Bundle bundle) {
		BundleWiring providerWiring = bundle.adapt(BundleWiring.class);
		if (!providerWiring.isInUse()) {
			return Collections.emptySet();
		}

		Set<Bundle> requiring = new HashSet<Bundle>();

		addRequirers(requiring, providerWiring);
		return Collections.unmodifiableSet(requiring);
	}

	private static void addRequirers(Set<Bundle> requiring, BundleWiring providerWiring) {
		List<BundleWire> requirerWires = providerWiring
				.getProvidedWires(BundleNamespace.BUNDLE_NAMESPACE);
		if (requirerWires == null) {
			return;
		}
		for (BundleWire requireBundleWire : requirerWires) {
			Bundle requirer = requireBundleWire.getRequirer().getBundle();
			if (requiring.contains(requirer)) {
				continue;
			}
			requiring.add(requirer);
			String reExport = requireBundleWire.getRequirement().getDirectives()
					.get(BundleNamespace.REQUIREMENT_VISIBILITY_DIRECTIVE);
			if (BundleNamespace.VISIBILITY_REEXPORT.equals(reExport)) {
				addRequirers(requiring, requireBundleWire.getRequirerWiring());
			}
		}
	}

	@Override
	protected Collection<Bundle> getDependencies(Bundle bundle) {
		return null;
	}
}
