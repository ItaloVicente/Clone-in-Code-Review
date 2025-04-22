package org.eclipse.egit.ui.internal.clone;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.internal.provisional.wizards.IRepositorySearchResult;
import org.eclipse.egit.ui.internal.provisional.wizards.IRepositoryServerProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.osgi.framework.Bundle;

public class GitCloneSourceProviderExtension {

	private static final String CLONE_SOURCE_PROVIDER_ID = "org.eclipse.egit.ui.cloneSourceProvider"; //$NON-NLS-1$

	public static List<CloneSourceProvider> getCloneSourceProvider() {
		List<CloneSourceProvider> cloneSourceProvider = new ArrayList<CloneSourceProvider>();

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(CLONE_SOURCE_PROVIDER_ID);
		if (config.length > 0)
			addCloneSourceProvider(cloneSourceProvider, config, 0);

		return cloneSourceProvider;
	}

	private static void addCloneSourceProvider(
			List<CloneSourceProvider> cloneSourceProvider,
			IConfigurationElement[] config, int index) {
		try {
			int myIndex = index;
			String label = config[myIndex].getAttribute("label"); //$NON-NLS-1$
			boolean hasFixLocation = Boolean.valueOf(
					config[myIndex].getAttribute("hasFixLocation")).booleanValue(); //$NON-NLS-1$


			String iconPath = config[myIndex].getAttribute("icon"); //$NON-NLS-1$
			ImageDescriptor icon = null;
			if (iconPath != null) {
				Bundle declaringBundle = Platform.getBundle(config[myIndex]
						.getDeclaringExtension().getNamespaceIdentifier());
				icon = ImageDescriptor.createFromURL(declaringBundle.getResource(iconPath));
			}
			myIndex++;
			IConfigurationElement serverProviderElement = null;
			if (myIndex < config.length
					&& config[myIndex].getName().equals("repositoryServerProvider")) { //$NON-NLS-1$
				serverProviderElement = config[myIndex];
				myIndex++;
			}
			IConfigurationElement pageElement = null;
			if (myIndex < config.length
					&& config[myIndex].getName().equals("repositorySearchPage")) { //$NON-NLS-1$
				pageElement = config[myIndex];
				myIndex++;
			}
			cloneSourceProvider.add(new CloneSourceProvider(label,
					serverProviderElement, pageElement, hasFixLocation, icon));
			if (myIndex == config.length)
				return;
			addCloneSourceProvider(cloneSourceProvider, config, myIndex);
		} catch (Exception e) {
			Activator.logError("Could not create extension provided by " + //$NON-NLS-1$
					Platform.getBundle(config[index].getDeclaringExtension().getNamespaceIdentifier()), e);
		}
	}

	public static class CloneSourceProvider {

		public static final CloneSourceProvider LOCAL = new CloneSourceProvider(
				UIText.GitCloneSourceProviderExtension_Local, null, null, true, UIIcons.REPOSITORY);

		private static final ImageDescriptor defaultImage = UIIcons.REPOSITORY;

		private final String label;

		private final IConfigurationElement repositoryServerProviderElement;

		private final IConfigurationElement repositorySearchPageELement;

		private boolean hasFixLocation = false;

		private ImageDescriptor image = UIIcons.REPOSITORY;

		private CloneSourceProvider(String label,
				IConfigurationElement repositoryServerProviderElement,
				IConfigurationElement repositorySearchPageElement,
				boolean hasFixLocation,
				ImageDescriptor image) {
			this.label = label;
			this.repositoryServerProviderElement = repositoryServerProviderElement;
			this.repositorySearchPageELement = repositorySearchPageElement;
			this.hasFixLocation = hasFixLocation;
			this.image = image;
		}

		public String getLabel() {
			return label;
		}

		public ImageDescriptor getImage() {
			return image != null ? image : defaultImage;
		}

		public IRepositoryServerProvider getRepositoryServerProvider()
				throws CoreException {
			if (repositoryServerProviderElement == null)
				return null;
			Object object = repositoryServerProviderElement
					.createExecutableExtension("class"); //$NON-NLS-1$
			IRepositoryServerProvider provider = null;
			if (object instanceof IRepositoryServerProvider)
				provider = (IRepositoryServerProvider) object;
			return provider;
		}

		public WizardPage getRepositorySearchPage() throws CoreException {
			if (repositorySearchPageELement == null)
				return null;
			Object object = repositorySearchPageELement
					.createExecutableExtension("class"); //$NON-NLS-1$
			WizardPage page = null;
			if (object instanceof WizardPage
					&& object instanceof IRepositorySearchResult)
				page = (WizardPage) object;
			return page;
		}

		public boolean hasFixLocation() {
			return hasFixLocation;
		}
	}

}
