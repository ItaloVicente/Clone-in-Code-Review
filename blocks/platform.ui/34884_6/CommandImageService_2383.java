
package org.eclipse.ui.internal.commands;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.services.RegistryPersistence;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

public final class CommandImagePersistence extends RegistryPersistence {

	private static final int INDEX_IMAGES = 0;

	private static final void readImagesFromRegistry(
			final IConfigurationElement[] configurationElements,
			final int configurationElementCount,
			final CommandImageManager commandImageManager,
			final ICommandService commandService) {
		commandImageManager.clear();

		final List warningsToLog = new ArrayList(1);

		for (int i = 0; i < configurationElementCount; i++) {
			final IConfigurationElement configurationElement = configurationElements[i];

			final String commandId = readRequired(configurationElement,
					ATT_COMMAND_ID, warningsToLog, "Image needs an id"); //$NON-NLS-1$
			if (commandId == null) {
				continue;
			}

			if (!commandService.getCommand(commandId).isDefined()) {
				addWarning(warningsToLog,
						"Cannot bind to an undefined command", //$NON-NLS-1$
						configurationElement, commandId);
				continue;
			}

			final String style = readOptional(configurationElement, ATT_STYLE);

			final String icon = readRequired(configurationElement, ATT_ICON,
					warningsToLog, commandId);
			if (icon == null) {
				continue;
			}

			final String disabledIcon = readOptional(configurationElement,
					ATT_DISABLEDICON);
			final String hoverIcon = readOptional(configurationElement,
					ATT_HOVERICON);

			String namespaceId = configurationElement.getNamespaceIdentifier();
			ImageDescriptor iconDescriptor = AbstractUIPlugin
					.imageDescriptorFromPlugin(namespaceId, icon);
			commandImageManager.bind(commandId,
					CommandImageManager.TYPE_DEFAULT, style, iconDescriptor);
			if (disabledIcon != null) {
				ImageDescriptor disabledIconDescriptor = AbstractUIPlugin
						.imageDescriptorFromPlugin(namespaceId, disabledIcon);
				commandImageManager.bind(commandId,
						CommandImageManager.TYPE_DISABLED, style,
						disabledIconDescriptor);
			}
			if (hoverIcon != null) {
				ImageDescriptor hoverIconDescriptor = AbstractUIPlugin
						.imageDescriptorFromPlugin(namespaceId, hoverIcon);
				commandImageManager.bind(commandId,
						CommandImageManager.TYPE_HOVER, style,
						hoverIconDescriptor);
			}
		}

		logWarnings(
				warningsToLog,
				"Warnings while parsing the images from the 'org.eclipse.ui.commandImages' extension point."); //$NON-NLS-1$
	}

	private final CommandImageManager commandImageManager;

	private final ICommandService commandService;

	CommandImagePersistence(final CommandImageManager commandImageManager,
			final ICommandService commandService) {
		this.commandImageManager = commandImageManager;
		this.commandService = commandService;
	}

	@Override
	protected final boolean isChangeImportant(final IRegistryChangeEvent event) {
		final IExtensionDelta[] imageDeltas = event.getExtensionDeltas(
				PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_COMMAND_IMAGES);
		return (imageDeltas.length != 0);
	}

	public void reRead() {
		read();
	}

	@Override
	protected final void read() {
		super.read();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		int imageCount = 0;
		final IConfigurationElement[][] indexedConfigurationElements = new IConfigurationElement[1][];

		final IConfigurationElement[] commandImagesExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_COMMAND_IMAGES);
		for (int i = 0; i < commandImagesExtensionPoint.length; i++) {
			final IConfigurationElement configurationElement = commandImagesExtensionPoint[i];
			final String name = configurationElement.getName();

			if (TAG_IMAGE.equals(name)) {
				addElementToIndexedArray(configurationElement,
						indexedConfigurationElements, INDEX_IMAGES,
						imageCount++);
			}
		}

		readImagesFromRegistry(indexedConfigurationElements[INDEX_IMAGES],
				imageCount, commandImageManager, commandService);
		IProduct product = Platform.getProduct();
		if (product != null) {
			Bundle productBundle = product.getDefiningBundle();
			if (productBundle != null) {
				String imageList = product.getProperty("windowImages"); //$NON-NLS-1$
				if (imageList != null) {
					String iconPath = imageList.split(",")[0]; //$NON-NLS-1$
					URL iconUrl = productBundle.getEntry(iconPath);
					ImageDescriptor icon = ImageDescriptor.createFromURL(iconUrl);
					if (icon != null) {
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_DEFAULT, null, icon);
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_DISABLED, null, icon);
						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
								CommandImageManager.TYPE_HOVER, null, icon);
					}

				}
			}
		}
	}
}
