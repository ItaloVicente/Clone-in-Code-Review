/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

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

/**
 * <p>
 * Handles persistence for the command images.
 * </p>
 * <p>
 * This class is only intended for internal use within the
 * <code>org.eclipse.ui.workbench</code> plug-in.
 * </p>
 * <p>
 * <strong>PROVISIONAL</strong>. This class or interface has been added as part
 * of a work in progress. There is a guarantee neither that this API will work
 * nor that it will remain the same. Please do not use this API without
 * consulting with the Platform/UI team.
 * </p>
 *
 * @since 3.2
 */
public final class CommandImagePersistence extends RegistryPersistence {

	/**
	 * The index of the image elements in the indexed array.
	 *
	 * @see CommandImagePersistence#read()
	 */
	private static final int INDEX_IMAGES = 0;

	/**
	 * Reads all of the images from the command images extension point.
	 *
	 * @param configurationElements
	 *            The configuration elements in the command images extension
	 *            point; must not be <code>null</code>, but may be empty.
	 * @param configurationElementCount
	 *            The number of configuration elements that are really in the
	 *            array.
	 * @param commandImageManager
	 *            The command image manager to which the images should be added;
	 *            must not be <code>null</code>.
	 * @param commandService
	 *            The command service for the workbench; must not be
	 *            <code>null</code>.
	 */
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
	}

	/**
	 * The command image manager which should be populated with the values from
	 * the registry; must not be <code>null</code>.
	 */
	private final CommandImageManager commandImageManager;

	/**
	 * The command service for the workbench; must not be <code>null</code>.
	 */
	private final ICommandService commandService;

	/**
	 * Constructs a new instance of <code>CommandImagePersistence</code>.
	 *
	 * @param commandImageManager
	 *            The command image manager which should be populated with the
	 *            values from the registry; must not be <code>null</code>.
	 * @param commandService
	 *            The command service for the workbench; must not be
	 *            <code>null</code>.
	 */
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

	/**
	 * Reads all of the command images from the registry.
	 */
	@Override
	protected final void read() {
		super.read();

		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		int imageCount = 0;
		final IConfigurationElement[][] indexedConfigurationElements = new IConfigurationElement[1][];

		final IConfigurationElement[] commandImagesExtensionPoint = registry
				.getConfigurationElementsFor(EXTENSION_COMMAND_IMAGES);
		for (final IConfigurationElement configurationElement : commandImagesExtensionPoint) {
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
				if (imageList != null) {
					String iconPath = imageList.split(",)[0]; //$NON-NLS-1$
-					URL iconUrl = productBundle.getEntry(iconPath);
-					ImageDescriptor icon = ImageDescriptor.createFromURL(iconUrl);
-					if (icon != null) {
-						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
-								CommandImageManager.TYPE_DEFAULT, null, icon);
-						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
-								CommandImageManager.TYPE_DISABLED, null, icon);
-						commandImageManager.bind(IWorkbenchCommandConstants.HELP_ABOUT,
-								CommandImageManager.TYPE_HOVER, null, icon);
-					}
-
-				}
-			}
-		}
-	}
-}
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/commands/CommandImageService.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/commands/CommandImageService.java
deleted file mode 100644
index 795507322c..0000000000
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/commands/CommandImageService.java	
+++ /dev/null
@@ -1,151 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2005, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.internal.commands;
-
-import java.net.URL;
-
-import org.eclipse.jface.resource.ImageDescriptor;
-import org.eclipse.ui.commands.ICommandImageService;
-import org.eclipse.ui.commands.ICommandService;
-
-/**
- * <p>
- * Provides services related to the command architecture within the workbench.
- * This service can be used to access the set of commands and handlers.
- * </p>
- *
- * @since 3.2
- */
-public final class CommandImageService implements ICommandImageService {
-
-	/**
-	 * The command image manager that supports this service. This value is never
-	 * <code>null</code>.
-	 */
-	private final CommandImageManager commandImageManager;
-
-	/**
-	 * The class providing persistence for this service.
-	 */
-	private final CommandImagePersistence commandImagePersistence;
-
-	/**
-	 * Constructs a new instance of <code>CommandService</code> using a
-	 * command image manager.
-	 *
-	 * @param commandImageManager
-	 *            The command image manager to use; must not be
-	 *            <code>null</code>.
-	 * @param commandService
-	 *            The workbench command service; must not be <code>null</code>.
-	 *            This is used for checking whether a command is defined when
-	 *            reading the registry.
-	 */
-	public CommandImageService(final CommandImageManager commandImageManager,
-			final ICommandService commandService) {
-		if (commandImageManager == null) {
-			throw new NullPointerException(
-					Cannot create a command image service with a null manager"); //$NON-NLS-1$
		}
		if (commandService == null) {
			throw new NullPointerException(
		}
		this.commandImageManager = commandImageManager;
		this.commandImagePersistence = new CommandImagePersistence(
				commandImageManager, commandService);
	}

	/**
	 * Binds a particular image descriptor to a command id, type and style
	 * triple
	 *
	 * @param commandId
	 *            The identifier of the command to which the image should be
	 *            bound; must not be <code>null</code>.
	 * @param type
	 *            The type of image to retrieve. This value must be one of the
	 *            <code>TYPE</code> constants defined in this class.
	 * @param style
	 *            The style of the image; may be <code>null</code>.
	 * @param descriptor
	 *            The image descriptor. Should not be <code>null</code>.
	 */
	public final void bind(final String commandId, final int type,
			final String style, final ImageDescriptor descriptor) {
		commandImageManager.bind(commandId, type, style, descriptor);
	}

	/**
	 * Binds a particular image path to a command id, type and style triple
	 *
	 * @param commandId
	 *            The identifier of the command to which the image should be
	 *            bound; must not be <code>null</code>.
	 * @param type
	 *            The type of image to retrieve. This value must be one of the
	 *            <code>TYPE</code> constants defined in this class.
	 * @param style
	 *            The style of the image; may be <code>null</code>.
	 * @param url
	 *            The URL to the image. Should not be <code>null</code>.
	 */
	public final void bind(final String commandId, final int type,
			final String style, final URL url) {
		commandImageManager.bind(commandId, type, style, url);
	}

	@Override
	public final void dispose() {
		commandImagePersistence.dispose();
	}

	/**
	 * Generates a style tag that is not currently used for the given command.
	 * This can be used by applications trying to create a unique style for a
	 * new set of images.
	 *
	 * @param commandId
	 *            The identifier of the command for which a unique style is
	 *            required; must not be <code>null</code>.
	 * @return A style tag that is not currently used; may be <code>null</code>.
	 */
	public final String generateUnusedStyle(final String commandId) {
		return commandImageManager.generateUnusedStyle(commandId);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId) {
		return commandImageManager.getImageDescriptor(commandId);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type) {
		return commandImageManager.getImageDescriptor(commandId, type);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final int type, final String style) {
		return commandImageManager.getImageDescriptor(commandId, type, style);
	}

	@Override
	public final ImageDescriptor getImageDescriptor(final String commandId,
			final String style) {
		return commandImageManager.getImageDescriptor(commandId, style);
	}

	public final void readRegistry() {
		commandImagePersistence.read();
	}
}
