/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dan Rubel <dan_rubel@instantiations.com>
 *     - Fix for bug 11490 - define hidden view (placeholder for view) in plugin.xml
 *     Markus Alexander Kuppe, Versant Corporation - bug #215797
 *     Semion Chichelnitsky (semion@il.ibm.com) - bug 208564
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 441184, 441280
 *     Denis Zygann <d.zygann@web.de> - Bug 457390
 *******************************************************************************/
package org.eclipse.ui.internal.registry;

import org.eclipse.ui.PlatformUI;

/**
 * Interface containing various registry constants (tag and attribute names).
 *
 * @since 3.1
 */
public interface IWorkbenchRegistryConstants {

	/**
	 * Accelerator attribute. Value <code>accelerator</code>.
	 */

	/**
	 * Adaptable attribute. Value <code>adaptable</code>.
	 */

	/**
	 * Advisor id attribute. Value <code>triggerPointAdvisorId</code>.
	 */

	/**
	 * Allow label update attribute. Value <code>allowLabelUpdate</code>.
	 */

	/**
	 * View multiple attribute. Value <code>allowMultiple</code>.
	 */

	/**
	 * Attribute that specifies whether a view gets restored upon workbench restart. Value <code>restorable</code>.
	 */

	/**
	 * Attribute that specifies whether a wizard is immediately capable of
	 * finishing. Value <code>canFinishEarly</code>.
	 */

	/**
	 * The name of the category attribute, which appears on a command
	 * definition.
	 */

	/**
	 * Category id attribute. Value <code>categoryId</code>.
	 */

	/**
	 * The name of the attribute storing checkEnabled for the visibleWhen
	 * element. Value <code>checkEnabled</code>.
	 */

	/**
	 * Class attribute. Value <code>class</code>.
	 */

	/**
	 * Sticky view closable attribute. Value <code>closable</code>.
	 */

	/**
	 * Color factory attribute. Value <code>colorFactory</code>.
	 */

	/**
	 * Editor command attribute. Value <code>command</code>.
	 */

	/**
	 * The name of the attribute storing the command id.
	 */

	/**
	 * The name of the configuration attribute storing the scheme id for a
	 * binding.
	 */

	/**
	 * Intro content detector class attribute (optional). Value <code>contentDetector</code>.
	 */

	/**
	 * Editor content type id binding attribute. Value
	 * <code>contentTypeId</code>.
	 */

	/**
	 * The name of the attribute storing the context id for a binding.
	 */

	/**
	 * Editor contributor class attribute. Value <code>contributorClass</code>.
	 */

	/**
	 * The name of the attribute storing the AbstractParameterValueConverter for
	 * a commandParameterType.
	 */

	/**
	 * Perspective default attribute. Value <code>default</code>.
	 */

	/**
	 * The name of the default handler attribute, which appears on a command
	 * definition.
	 */

	/**
	 * Defaults-to attribute. Value <code>defaultsTo</code>.
	 */

	/**
	 * Action definition id attribute. Value <code>definitionId</code>.
	 */

	/**
	 * Resembles a deactivated SYSTEM binding. Value <code>deleted</code>.
	 */

	/**
	 * The name of the description attribute, which appears on named handle
	 * objects.
	 */

	/**
	 * Description image attribute. Value <code>descriptionImage</code>.
	 */

	/**
	 * Disabled icon attribute. Value <code>disabledIcon</code>.
	 */

	/**
	 * Editor id attribute. Value <code>editorId</code>.
	 */

	/**
	 * Enables-for attribute. Value <code>enablesFor</code>.
	 */

	/**
	 * Editor extensions attribute. Value <code>extensions</code>.
	 */

	/**
	 * Editor filenames attribute. Value <code>filenames</code>.
	 */

	/**
	 * Trim fill major attribute. Value <code>fillMajor</code>.
	 */

	/**
	 * Trim fill minor attribute. Value <code>fillMinor</code>.
	 */

	/**
	 * Perspective fixed attribute. Value <code>fixed</code>.
	 */

	/**
	 * Attribute that specifies whether a wizard has any pages. Value
	 * <code>hasPages</code>.
	 */

	/**
	 * Help context id attribute. Value <code>helpContextId</code>.
	 */

	/**
	 * Help url attribute. Value <code>helpHref</code>.
	 */

	/**
	 * Hover icon attribute. Value <code>hoverIcon</code>.
	 */

	/**
	 * Icon attribute. Value <code>icon</code>.
	 */

	/**
	 * Id attribute. Value <code>id</code>.
	 */

	/**
	 * The name of the image style attribute, which is used on location elements
	 * in the menus extension point.
	 */

	/**
	 * Is-editable attribute. Value <code>isEditable</code>.
	 */

	/**
	 * Keys attribute. Value <code>keys</code>.
	 */

	/**
	 * The name of the attribute storing the identifier for the active key
	 * configuration identifier. This provides legacy support for the
	 * <code>activeKeyConfiguration</code> element in the commands extension
	 * point.
	 */

	/**
	 * The name of the attribute storing the trigger sequence for a binding.
	 * This is called a 'keySequence' for legacy reasons.
	 */

	/**
	 * Label attribute. Value <code>label</code>.
	 */

	/**
	 * Editor launcher attribute. Value <code>launcher</code>.
	 */

	/**
	 * Lightweight decorator tag. Value <code>lightweight</code>.
	 */

	/**
	 * The name of the attribute storing the locale for a binding.
	 */

	/**
	 * Sticky view location attribute. Value <code>location</code>.
	 */

	/**
	 * Editor management strategy attribute. Value <code>matchingStrategy</code>.
	 */

	/**
	 * The name of the menu identifier attribute, which appears on items.
	 */

	/**
	 * Menubar path attribute. Value <code>menubarPath</code>.
	 */

	/**
	 * The name of the mnemonic attribute, which appears on locations.
	 */

	/**
	 * The name of the minimized attribute, which appears
	 * when adding a view in a perspectiveExtension.
	 */

	/**
	 * Sticky view moveable attribute. Value <code>moveable</code>.
	 */

	/**
	 * Name attribute. Value <code>name</code>.
	 */

	/**
	 * Match type attribute. Value <code>match</code>.
	 */

	/**
	 * Name filter attribute. Value <code>nameFilter</code>.
	 */

	/**
	 * Node attribute. Value <code>node</code>.
	 */

	/**
	 * Object class attribute. Value <code>objectClass</code>.
	 */

	/**
	 * The name of the optional attribute, which appears on parameter
	 * definitions.
	 */

	/**
	 * Operating system attribute. Value <code>os</code>.
	 */

	/**
	 * The name of the deprecated parent attribute, which appears on scheme
	 * definitions.
	 */

	/**
	 * View parent category attribute. Value <code>parentCategory</code>.
	 */

	/**
	 * Parent id attribute. Value <code>parentId</code>.
	 */

	/**
	 * The name of the deprecated parent scope attribute, which appears on
	 * contexts definitions.
	 */

	/**
	 * Path attribute. Value <code>path</code>.
	 */

	/**
	 * The name of the attribute storing the platform for a binding.
	 */

	/**
	 * The name of the position attribute, which appears on order elements.
	 */

	/**
	 * Presentation id attribute. Value <code>presentationId</code>.
	 */

	/**
	 * Product id attribute. Value <code>productId</code>.
	 */

	/**
	 * Project attribute. Value <code>project</code>.
	 */

	/**
	 * The name of the pulldown attribute, which indicates whether the class is
	 * a pulldown delegate.
	 */

	/**
	 * View ratio attribute. Value <code>ratio</code>.
	 */

	/**
	 * Relationship attribute. Value <code>relationship</code>.
	 */

	/**
	 * Relative attribute. Value <code>relative</code>.
	 */

	/**
	 * The name of the relativeTo attribute, which appears on order elements.
	 */

	/**
	 * Retarget attribute. Value <code>retarget</code>.
	 */

	/**
	 * The name of the returnTypeId attribute, which appears on command
	 * elements.
	 */

	/**
	 * The name of the attribute storing the identifier for the active scheme.
	 * This is called a 'keyConfigurationId' for legacy reasons.
	 */

	/**
	 * Scope attribute. Value <code>scope</code>.
	 */

	/**
	 * The name of the separatorsVisible attribute, which appears on group
	 * elements.
	 */

	/**
	 * The name of the sequence attribute for a key binding.
	 */

	/**
	 * Show title attribute. Value <code>showTitle</code>.
	 */

	/**
	 * Perspective singleton attribute. Value <code>singleton</code>.
	 */

	/**
	 * Splash id attribute.  Value <code>splashId</code>.
	 *
	 * @since 3.3
	 */

	/**
	 * Standalone attribute. Value <code>standalone</code>.
	 */

	/**
	 * Action state attribute. Value <code>state</code>.
	 */

	/**
	 * The name of the string attribute (key sequence) for a binding in the
	 * commands extension point.
	 */

	/**
	 * Action style attribute. Value <code>style</code>.
	 */

	/**
	 * Target attribute. Value <code>targetID</code>.
	 */

	/**
	 * Toolbar path attribute. Value <code>toolbarPath</code>.
	 */

	/**
	 * Tooltip attribute. Value <code>tooltip</code>.
	 */

	/**
	 * The name of the type attribute, which appears on bar elements and
	 * commandParameterType elments.
	 */

	/**
	 * The name of the typeId attribute, which appears on commandParameter
	 * elements.
	 */

	/**
	 * Value attribute. Value <code>value</code>.
	 */

	/**
	 * Visible attribute. Value <code>visible</code>.
	 */

	/**
	 * Windowing system attribute. Value <code>ws</code>.
	 */

	/**
	 * The prefix that all auto-generated identifiers start with. This makes the
	 * identifier recognizable as auto-generated, and further helps ensure that
	 * it does not conflict with existing identifiers.
	 */

	/**
	 * The legacy extension point (2.1.x and earlier) for specifying a key
	 * binding scheme.
	 *
	 * @since 3.1.1
	 */

	/**
	 * The legacy extension point (2.1.x and earlier) for specifying a context.
	 *
	 * @since 3.1.1
	 */

	/**
	 * The legacy extension point (2.1.x and earlier) for specifying a command.
	 *
	 * @since 3.1.1
	 */





	/**
	 * The extension point (3.1 and later) for specifying bindings, such as
	 * keyboard shortcuts.
	 *
	 * @since 3.1.1
	 */



	/**
	 * The extension point (3.2 and later) for associating images with commands.
	 *
	 * @since 3.2
	 */

	/**
	 * The extension point (2.1.x and later) for specifying a command. A lot of
	 * other things have appeared first in this extension point and then been
	 * moved to their own extension point.
	 *
	 * @since 3.1.1
	 */

	/**
	 * The extension point (3.0 and later) for specifying a context.
	 *
	 * @since 3.1.1
	 */






	/**
	 * The extension point for encoding definitions.
	 */



	/**
	 * The extension point (3.1 and later) for specifying handlers.
	 *
	 * @since 3.1.1
	 */




	/**
	 * The extension point for keyword definitions.
	 *
	 * @since 3.1
	 */

	/**
	 * The extension point (3.2 and later) for specifying menu contributions.
	 *
	 * @since 3.2
	 */

	/**
	 * The extension point (3.3 and later) for specifying menu contributions.
	 *
	 * @since 3.3
	 */









	/**
	 * @since 3.3
	 */






	/**
	 * The name of the deprecated accelerator configurations extension point.
	 */
	public static String EXTENSION_ACCELERATOR_CONFIGURATIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACCELERATOR_CONFIGURATIONS;

	/**
	 * The name of the accelerator scopes extension point.
	 */
	public static String EXTENSION_ACCELERATOR_SCOPES = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACCELERATOR_SCOPES;

	/**
	 * The name of the action definitions extension point.
	 */
	public static String EXTENSION_ACTION_DEFINITIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACTION_DEFINITIONS;

	/**
	 * The name of the <code>org.eclipse.ui.actionSets</code> extension point.
	 */
	public static String EXTENSION_ACTION_SETS = PlatformUI.PLUGIN_ID
			+ '.' + IWorkbenchRegistryConstants.PL_ACTION_SETS;

	/**
	 * The name of the bindings extension point.
	 */
	public static String EXTENSION_BINDINGS = PlatformUI.PLUGIN_ID + '.'
			+ PL_BINDINGS;

	/**
	 * The name of the commands extension point.
	 */
	public static String EXTENSION_COMMAND_IMAGES = PlatformUI.PLUGIN_ID
			+ '.' + PL_COMMAND_IMAGES;

	/**
	 * The name of the commands extension point, and the name of the key for the
	 * commands preferences.
	 */
	public static String EXTENSION_COMMANDS = PlatformUI.PLUGIN_ID + '.'
			+ PL_COMMANDS;

	/**
	 * The name of the contexts extension point.
	 */
	public static String EXTENSION_CONTEXTS = PlatformUI.PLUGIN_ID + '.'
			+ PL_CONTEXTS;

	/**
	 * The name of the <code>org.eclipse.ui.editorActions</code> extension
	 * point.
	 */
	public static String EXTENSION_EDITOR_ACTIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_EDITOR_ACTIONS;

	/**
	 * The name of the commands extension point.
	 */
	public static String EXTENSION_HANDLERS = PlatformUI.PLUGIN_ID + '.'
			+ PL_HANDLERS;

	/**
	 * The name of the <code>org.eclipse.ui.menus</code> extension point.
	 */
	public static String EXTENSION_MENUS = PlatformUI.PLUGIN_ID + '.'
			+ PL_MENUS;

	/**
	 * The name of the <code>org.eclipse.ui.menus2</code> extension point.
	 */
	public static String COMMON_MENU_ADDITIONS = PlatformUI.PLUGIN_ID + '.'
			+ PL_MENUS + '2';

	/**
	 * The name of the <code>org.eclipse.ui.popupMenus</code> extension point.
	 */
	public static String EXTENSION_POPUP_MENUS = PlatformUI.PLUGIN_ID
			+ '.' + PL_POPUP_MENU;

	/**
	 * The name of the <code>org.eclipse.ui.viewActions</code> extension
	 * point.
	 */
	public static String EXTENSION_VIEW_ACTIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_VIEW_ACTIONS;

	/**
	 * The constant for the position attribute corresponding to
	 * {@link SOrder#POSITION_AFTER}.
	 */

	/**
	 * The constant for the position attribute corresponding to
	 * {@link SOrder#POSITION_BEFORE}.
	 */

	/**
	 * The constant for the position attribute corresponding to
	 * {@link SOrder#POSITION_END}.
	 */

	/**
	 * The constant for the position attribute corresponding to
	 * {@link SOrder#POSITION_START}.
	 */

	/**
	 * The action style for drop-down menus.
	 */

	/**
	 * The action style for radio buttons.
	 */

	/**
	 * The action style for check boxes.
	 */

	/**
	 * The name of the deprecated accelerator configuration element. This
	 * element was used in 2.1.x and earlier to define groups of what are now
	 * called schemes.
	 */

	/**
	 * The name of the element storing a deprecated accelerator scope.
	 */

	/**
	 * Action tag. Value <code>action</code>.
	 */

	/**
	 * The name of the element storing an action definition. This element only
	 * existed in
	 */

	/**
	 * Action set tag. Value <code>actionSet</code>.
	 */

	/**
	 * Part association tag. Value <code>actionSetPartAssociation</code>.
	 */

	/**
	 * The name of the element storing the active key configuration from the
	 * commands extension point.
	 */


	/**
	 * The name of the active when element, which appears on a handler
	 * definition.
	 */

	/**
	 * Activity image binding tag. Value <code>activityImageBindingw</code>.
	 */

	/**
	 * Advisor to product binding element. Value
	 * <code>triggerPointAdvisorProductBinding</code>.
	 */

	/**
	 * The name of the bar element, which appears in a location definition.
	 */

	/**
	 * Category tag. Value <code>category</code>.
	 */

	/**
	 * Category image binding tag. Value <code>categoryImageBinding</code>.
	 */

	/**
	 * Element category tag. Value <code>themeElementCategory</code>.
	 */

	/**
	 * Category presentation tag. Value <code>categoryPresentationBinding</code>
	 * .
	 *
	 * @deprecated used by the removal presentation API
	 */
	@Deprecated

	/**
	 * The name of the class element, which appears on an executable extension.
	 */
	public static String TAG_CLASS = ATT_CLASS;

	/**
	 * Color definition tag. Value <code>colorDefinition</code>.
	 */

	/**
	 * Color override tag. Value <code>colorOverride</code>.
	 */

	/**
	 * Color value tag. Value <code>colorValue</code>.
	 */

	/**
	 * The name of the element storing a command.
	 */

	/**
	 * The name of the element storing a parameter.
	 */

	/**
	 * The name of the element storing a parameter type.
	 */

	/**
	 * Editor content type binding tag. Value <code>contentTypeBinding</code>.
	 */

	/**
	 * The name of the element storing a context.
	 */

	/**
	 * Data tag. Value <code>data</code>.
	 */

	/**
	 * The name of the default handler element, which appears on a command
	 * definition.
	 */
	public static String TAG_DEFAULT_HANDLER = ATT_DEFAULT_HANDLER;

	/**
	 * Description element. Value <code>description</code>.
	 */

	/**
	 * The name of the dynamic menu element, which appears in a group or menu
	 * definition.
	 */

	/**
	 * Editor tag. Value <code>editor</code>.
	 */

	/**
	 * Editor tag. Value <code>editor</code>.
	 */

	/**
	 * The name of the deprecated editorContribution element. This is used for
	 * contributing actions to the top-level menus and tool bars when particular
	 * editors are visible.
	 */

	/**
	 * The name of the enabled when element, which appears on a handler
	 * definition.
	 */

	/**
	 * Enablement tag. Value <code>enablement</code>.
	 */

	/**
	 * Entry tag. Value <code>entry</code>.
	 */

	/**
	 * Filter tag. Value <code>filter</code>.
	 */

	/***************************************************************************
	 * Font definition tag. Value <code>fontDefinition</code>.
	 */

	/**
	 * Font override tag. Value <code>fontOverride</code>.
	 */

	/**
	 * Font value tag. Value <code>fontValue</code>.
	 */

	/**
	 * The name of the element storing a group.
	 */

	/**
	 * Group marker tag. Value <code>groupMarker</code>.
	 */

	/**
	 * The name of the element storing a handler.
	 */

	/**
	 * The name of the element storing a handler submission.
	 */

	/**
	 * The name of the element storing the id of a menu item to hide
	 */

	/**
	 * The name of the element storing the id of a toolbar item to hide
	 */

	/**
	 * Trigger point hint tag. Value <code>hint</code>.
	 */

	/**
	 * The name of the element storing an image.
	 */

	/**
	 * The name of the element storing a key binding.
	 */

	/**
	 * The name of the key binding element in the commands extension point.
	 */

	/**
	 * The name of the deprecated key configuration element in the commands
	 * extension point. This element has been replaced with the scheme element
	 * in the bindings extension point.
	 */

	/**
	 * The name of the element storing a location.
	 */

	/**
	 * The name of the element defining the insertion point for menu
	 * additions.
	 *
	 * @since 3.3
	 */

	/**
	 * The name of the element storing trim layout info for a widget.
	 */

	/**
	 * Mapping tag. Value <code>mapping</code>.
	 */

	/**
	 * Menu tag. Value <code>menu</code>.
	 */

	/**
	 * Wizard shortcut tag. Value <code>newWizardShortcut</code>.
	 */

	/**
	 * Object contribution tag. Value <code>objectContribution</code>.
	 */

	/**
	 * The name of the element storing the ordering information.
	 */

	/**
	 * The name of the element storing a parameter.
	 */

	/**
	 * Part tag. Value <code>part</code>.
	 */

	/**
	 * Perspective shortcut tag. Value <code>perspectiveShortcut</code>.
	 */

	/**
	 * Perspective tag. Value <code>perspective</code>.
	 */

	/**
	 * Perspective extension tag. Value <code>perspectiveExtension</code>.
	 */

	/**
	 * Primary wizard tag. Value <code>primaryWizard</code>.
	 */

	/**
	 * The name of the element storing the a menu element reference.
	 */

	/**
	 * The name of the scheme element in the bindings extension point.
	 */

	/**
	 * The name of the element storing a deprecated scope.
	 */

	/**
	 * Selectiont tag. Value <code>selection</code>.
	 */

	/**
	 * Separator tag. Value <code>separator</code>.
	 */


	/**
	 * Tag for the settings transfer entry.
	 */

	/**
	 * Show in part tag. Value <code>showInPart</code>.
	 */

	/**
	 * The name of the element storing some state.
	 */

	/**
	 * The name of the element describing splash handlers. Value
	 * <code>splashHandler</code>.
	 * @since 3.3
	 */


	/**
	 * The name of the element describing splash handler product bindings. Value
	 * <code>splashHandlerProductBinding</code>.
	 * @since 3.3
	 */

	/**
	 * Sticky view tag. Value <code>stickyView</code>.
	 */

	/**
	 * Browser support tag. Value <code>support</code>.
	 */

	/**
	 * Theme tag. Value <code>theme</code>.
	 */

	/**
	 * Transfer tag. Value <code>transfer</code>.
	 */

	/**
	 * Trigger point tag. Value <code>triggerPoint</code>.
	 */

	/**
	 * Advisor tag. Value <code>triggerPointAdvisor</code>.
	 */

	/**
	 * View tag. Value <code>view</code>.
	 */

	/**
	 * E4 view tag, used in the <code>org.eclipse.ui.view</code> extension point
	 * to point to a POJO class. Value <code>e4view</code>.
	 */

	/**
	 * View shortcut tag. Value <code>viewShortcut</code>.
	 */

	/**
	 * The name of the element storing a view contribution.
	 */

	/**
	 * Viewer contribution tag. Value <code>viewerContribution</code>.
	 */

	/**
	 * Visibility tag. Value <code>visibility</code>.
	 */

	/**
	 * The name of the element storing the visible when condition.
	 */

	/**
	 * The name of the element storing a widget.
	 */

	/**
	 * The name of the element storing a control hosted in a ToolBar.
	 */

	/**
	 * Wizard tag. Value <code>wizard</code>.
	 */

	/**
	 * Working set tag. Value <code>workingSet</code>.
	 */

	/**
	 * The type of reference which refers to a group.
	 */

	/**
	 * The type of reference which refers to an item.
	 */

	/**
	 * The type of reference which refers to an menu.
	 */

	/**
	 * The type of reference which refers to the widget.
	 */


















}
