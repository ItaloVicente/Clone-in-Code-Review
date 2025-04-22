package org.eclipse.ui.internal.registry;

import org.eclipse.ui.PlatformUI;

public interface IWorkbenchRegistryConstants {

	public static String ATT_ACCELERATOR = "accelerator"; //$NON-NLS-1$

	public static String ATT_ADAPTABLE = "adaptable"; //$NON-NLS-1$

	public static String ATT_ADVISORID = "triggerPointAdvisorId"; //$NON-NLS-1$

	public static String ATT_ALLOW_LABEL_UPDATE = "allowLabelUpdate";//$NON-NLS-1$

	public static String ATT_ALLOW_MULTIPLE = "allowMultiple"; //$NON-NLS-1$

	public static String ATT_RESTORABLE = "restorable"; //$NON-NLS-1$
	
	public static String ATT_CAN_FINISH_EARLY = "canFinishEarly"; //$NON-NLS-1$

	public static String ATT_CATEGORY = "category"; //$NON-NLS-1$

	public static String ATT_CATEGORY_ID = "categoryId"; //$NON-NLS-1$
	
	public static String ATT_CHECK_ENABLED = "checkEnabled"; //$NON-NLS-1$

	public static String ATT_CLASS = "class"; //$NON-NLS-1$

	public static String ATT_CLOSEABLE = "closeable"; //$NON-NLS-1$    

	public static String ATT_COLORFACTORY = "colorFactory"; //$NON-NLS-1$

	public static String ATT_COMMAND = "command";//$NON-NLS-1$

	public static String ATT_COMMAND_ID = "commandId"; //$NON-NLS-1$

	public static String ATT_CONFIGURATION = "configuration"; //$NON-NLS-1$

	public static String ATT_CONTENT_DETECTOR = "contentDetector"; //$NON-NLS-1$
	
	public static String ATT_CONTENT_TYPE_ID = "contentTypeId"; //$NON-NLS-1$

	public static String ATT_CONTEXT_ID = "contextId"; //$NON-NLS-1$

	public static String ATT_CONTRIBUTOR_CLASS = "contributorClass"; //$NON-NLS-1$

	public static String ATT_CONVERTER = "converter"; //$NON-NLS-1$

	public static String ATT_DEFAULT = "default";//$NON-NLS-1$

	public static String ATT_DEFAULT_HANDLER = "defaultHandler"; //$NON-NLS-1$

	public static String ATT_DEFAULTS_TO = "defaultsTo"; //$NON-NLS-1$

	public static String ATT_DEFINITION_ID = "definitionId";//$NON-NLS-1$

	public static String ATT_DELETED = "deleted";//$NON-NLS-1$	

	public static String ATT_DESCRIPTION = "description"; //$NON-NLS-1$

	public static String ATT_DESCRIPTION_IMAGE = "descriptionImage"; //$NON-NLS-1$

	public static String ATT_DISABLEDICON = "disabledIcon";//$NON-NLS-1$

	public static String ATT_ENABLES_FOR = "enablesFor"; //$NON-NLS-1$

	public static String ATT_EXTENSIONS = "extensions";//$NON-NLS-1$

	public static String ATT_FAST_VIEW_WIDTH_RATIO = "fastViewWidthRatio"; //$NON-NLS-1$

	public static String ATT_FILENAMES = "filenames";//$NON-NLS-1$

	public static String ATT_FILL_MAJOR = "fillMajor";//$NON-NLS-1$

	public static String ATT_FILL_MINOR = "fillMinor";//$NON-NLS-1$

	public static String ATT_FIXED = "fixed";//$NON-NLS-1$

	public static String ATT_HAS_PAGES = "hasPages"; //$NON-NLS-1$

	public static String ATT_HELP_CONTEXT_ID = "helpContextId";//$NON-NLS-1$

	public static String ATT_HELP_HREF = "helpHref"; //$NON-NLS-1$

	public static String ATT_HOVERICON = "hoverIcon";//$NON-NLS-1$

	public static String ATT_ICON = "icon"; //$NON-NLS-1$

	public static String ATT_ID = "id"; //$NON-NLS-1$

	public static String ATT_IMAGE_STYLE = "imageStyle"; //$NON-NLS-1$

	public static String ATT_IS_EDITABLE = "isEditable"; //$NON-NLS-1$

	public static String ATT_KEY = "key"; //$NON-NLS-1$

	public static String ATT_KEY_CONFIGURATION_ID = "keyConfigurationId"; //$NON-NLS-1$

	public static String ATT_KEY_SEQUENCE = "keySequence"; //$NON-NLS-1$

	public static String ATT_LABEL = "label"; //$NON-NLS-1$

	public static String ATT_LAUNCHER = "launcher";//$NON-NLS-1$

	public static String ATT_LIGHTWEIGHT = "lightweight"; //$NON-NLS-1$

	public static String ATT_LOCALE = "locale"; //$NON-NLS-1$

	public static String ATT_LOCATION = "location"; //$NON-NLS-1$

	public static String ATT_MATCHING_STRATEGY = "matchingStrategy"; //$NON-NLS-1$

	public static String ATT_MENU_ID = "menuId"; //$NON-NLS-1$

	public static String ATT_MENUBAR_PATH = "menubarPath";//$NON-NLS-1$

	public static String ATT_MNEMONIC = "mnemonic"; //$NON-NLS-1$

	public static String ATT_MINIMIZED = "minimized"; //$NON-NLS-1$

	public static String ATT_MOVEABLE = "moveable"; //$NON-NLS-1$

	public static String ATT_NAME = "name"; //$NON-NLS-1$

	public static String ATT_MATCH_TYPE = "match"; //$NON-NLS-1$

	public static String ATT_NAME_FILTER = "nameFilter"; //$NON-NLS-1$

	public static String ATT_NODE = "node"; //$NON-NLS-1$

	public static String ATT_OBJECTCLASS = "objectClass";//$NON-NLS-1$

	public static String ATT_OPTIONAL = "optional"; //$NON-NLS-1$

	public static String ATT_OS = "os"; //$NON-NLS-1$

	public static String ATT_PARENT = "parent"; //$NON-NLS-1$

	public static String ATT_PARENT_CATEGORY = "parentCategory"; //$NON-NLS-1$

	public static String ATT_PARENT_ID = "parentId"; //$NON-NLS-1$

	public static String ATT_PARENT_SCOPE = "parentScope"; //$NON-NLS-1$

	public static String ATT_PATH = "path"; //$NON-NLS-1$

	public static String ATT_PLATFORM = "platform"; //$NON-NLS-1$

	public static String ATT_POSITION = "position"; //$NON-NLS-1$

	public static String ATT_PRESENTATIONID = "presentationId"; //$NON-NLS-1$

	public static String ATT_PRODUCTID = "productId"; //$NON-NLS-1$

	public static String ATT_PROJECT = "project";//$NON-NLS-1$	/**

	public static String ATT_PULLDOWN = "pulldown"; //$NON-NLS-1$

	public static String ATT_RATIO = "ratio"; //$NON-NLS-1$

	public static String ATT_RELATIONSHIP = "relationship";//$NON-NLS-1$

	public static String ATT_RELATIVE = "relative";//$NON-NLS-1$

	public static String ATT_RELATIVE_TO = "relativeTo"; //$NON-NLS-1$

	public static String ATT_RETARGET = "retarget";//$NON-NLS-1$

	public static String ATT_RETURN_TYPE_ID = "returnTypeId"; //$NON-NLS-1$

	public static String ATT_SCHEME_ID = "schemeId"; //$NON-NLS-1$

	public static String ATT_SCOPE = "scope"; //$NON-NLS-1$

	public static String ATT_SEPARATORS_VISIBLE = "separatorsVisible"; //$NON-NLS-1$

	public static String ATT_SEQUENCE = "sequence"; //$NON-NLS-1$

	public static String ATT_SHOW_TITLE = "showTitle";//$NON-NLS-1$

	public static String ATT_SINGLETON = "singleton";//$NON-NLS-1$
	
	public static String ATT_SPLASH_ID = "splashId"; //$NON-NLS-1$

	public static String ATT_STANDALONE = "standalone";//$NON-NLS-1$

	public static String ATT_STATE = "state";//$NON-NLS-1$

	public static String ATT_STRING = "string"; //$NON-NLS-1$

	public static String ATT_STYLE = "style";//$NON-NLS-1$

	public static String ATT_TARGET_ID = "targetID";//$NON-NLS-1$

	public static String ATT_TOOLBAR_PATH = "toolbarPath";//$NON-NLS-1$

	public static String ATT_TOOLTIP = "tooltip";//$NON-NLS-1$

	public static String ATT_TYPE = "type"; //$NON-NLS-1$

	public static String ATT_TYPE_ID = "typeId"; //$NON-NLS-1$

	public static String ATT_VALUE = "value"; //$NON-NLS-1$

	public static String ATT_VISIBLE = "visible";//$NON-NLS-1$

	public static String ATT_WS = "ws"; //$NON-NLS-1$

	public static String AUTOGENERATED_PREFIX = "AUTOGEN:::"; //$NON-NLS-1$

	public static String PL_ACCELERATOR_CONFIGURATIONS = "acceleratorConfigurations"; //$NON-NLS-1$

	public static String PL_ACCELERATOR_SCOPES = "acceleratorScopes"; //$NON-NLS-1$

	public static String PL_ACTION_DEFINITIONS = "actionDefinitions"; //$NON-NLS-1$

	public static String PL_ACTION_SET_PART_ASSOCIATIONS = "actionSetPartAssociations"; //$NON-NLS-1$

	public static String PL_ACTION_SETS = "actionSets"; //$NON-NLS-1$

	public static String PL_ACTIVITIES = "activities"; //$NON-NLS-1$

	public static String PL_ACTIVITYSUPPORT = "activitySupport"; //$NON-NLS-1$

	public static String PL_BINDINGS = "bindings"; //$NON-NLS-1$

	public static String PL_BROWSER_SUPPORT = "browserSupport"; //$NON-NLS-1$

	public static String PL_COLOR_DEFINITIONS = "colorDefinitions"; //$NON-NLS-1$

	public static String PL_COMMAND_IMAGES = "commandImages"; //$NON-NLS-1$

	public static String PL_COMMANDS = "commands"; //$NON-NLS-1$

	public static String PL_CONTEXTS = "contexts"; //$NON-NLS-1$

	public static String PL_DECORATORS = "decorators"; //$NON-NLS-1$

	public static String PL_DROP_ACTIONS = "dropActions"; //$NON-NLS-1$

	public static String PL_EDITOR = "editors"; //$NON-NLS-1$

	public static String PL_EDITOR_ACTIONS = "editorActions"; //$NON-NLS-1$

	public static String PL_ELEMENT_FACTORY = "elementFactories"; //$NON-NLS-1$

	public static String PL_ENCODINGS = "encodings"; //$NON-NLS-1$

	public static String PL_EXPORT = "exportWizards"; //$NON-NLS-1$

	public static String PL_FONT_DEFINITIONS = "fontDefinitions"; //$NON-NLS-1$

	public static String PL_HANDLERS = "handlers"; //$NON-NLS-1$

	public static String PL_HELPSUPPORT = "helpSupport"; //$NON-NLS-1$

	public static String PL_IMPORT = "importWizards"; //$NON-NLS-1$

	public static String PL_INTRO = "intro"; //$NON-NLS-1$

	public static String PL_KEYWORDS = "keywords"; //$NON-NLS-1$

	public static String PL_MENUS = "menus"; //$NON-NLS-1$

	public static String PL_MENU_CONTRIBUTION = "menuContribution"; //$NON-NLS-1$

	public static String PL_NEW = "newWizards"; //$NON-NLS-1$

	public static String PL_PERSPECTIVE_EXTENSIONS = "perspectiveExtensions"; //$NON-NLS-1$

	public static String PL_PERSPECTIVES = "perspectives"; //$NON-NLS-1$

	public static String PL_POPUP_MENU = "popupMenus"; //$NON-NLS-1$

	public static String PL_PREFERENCE_TRANSFER = "preferenceTransfer"; //$NON-NLS-1$

	public static String PL_PREFERENCES = "preferencePages"; //$NON-NLS-1$

	public static String PL_PROPERTY_PAGES = "propertyPages"; //$NON-NLS-1$

	public static String PL_STARTUP = "startup"; //$NON-NLS-1$
	
	public static String PL_SPLASH_HANDLERS = "splashHandlers"; //$NON-NLS-1$

	public static String PL_SYSTEM_SUMMARY_SECTIONS = "systemSummarySections"; //$NON-NLS-1$

	public static String PL_THEMES = "themes"; //$NON-NLS-1$

	public static String PL_VIEW_ACTIONS = "viewActions"; //$NON-NLS-1$

	public static String PL_VIEWS = "views"; //$NON-NLS-1$

	public static String PL_WORKINGSETS = "workingSets"; //$NON-NLS-1$

	public static String EXTENSION_ACCELERATOR_CONFIGURATIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACCELERATOR_CONFIGURATIONS;

	public static String EXTENSION_ACCELERATOR_SCOPES = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACCELERATOR_SCOPES;

	public static String EXTENSION_ACTION_DEFINITIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_ACTION_DEFINITIONS;

	public static String EXTENSION_ACTION_SETS = PlatformUI.PLUGIN_ID
			+ '.' + IWorkbenchRegistryConstants.PL_ACTION_SETS;

	public static String EXTENSION_BINDINGS = PlatformUI.PLUGIN_ID + '.'
			+ PL_BINDINGS;

	public static String EXTENSION_COMMAND_IMAGES = PlatformUI.PLUGIN_ID
			+ '.' + PL_COMMAND_IMAGES;

	public static String EXTENSION_COMMANDS = PlatformUI.PLUGIN_ID + '.'
			+ PL_COMMANDS;

	public static String EXTENSION_CONTEXTS = PlatformUI.PLUGIN_ID + '.'
			+ PL_CONTEXTS;

	public static String EXTENSION_EDITOR_ACTIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_EDITOR_ACTIONS;

	public static String EXTENSION_HANDLERS = PlatformUI.PLUGIN_ID + '.'
			+ PL_HANDLERS;

	public static String EXTENSION_MENUS = PlatformUI.PLUGIN_ID + '.'
			+ PL_MENUS;

	public static String COMMON_MENU_ADDITIONS = PlatformUI.PLUGIN_ID + '.'
			+ PL_MENUS + '2';

	public static String EXTENSION_POPUP_MENUS = PlatformUI.PLUGIN_ID
			+ '.' + PL_POPUP_MENU;

	public static String EXTENSION_VIEW_ACTIONS = PlatformUI.PLUGIN_ID
			+ '.' + PL_VIEW_ACTIONS;

	public static String POSITION_AFTER = "after"; //$NON-NLS-1$

	public static String POSITION_BEFORE = "before"; //$NON-NLS-1$

	public static String POSITION_END = "end"; //$NON-NLS-1$

	public static String POSITION_START = "start"; //$NON-NLS-1$

	public static String STYLE_PULLDOWN = "pulldown"; //$NON-NLS-1$

	public static String STYLE_RADIO = "radio"; //$NON-NLS-1$

	public static String STYLE_TOGGLE = "toggle"; //$NON-NLS-1$

	public static String TAG_ACCELERATOR_CONFIGURATION = "acceleratorConfiguration"; //$NON-NLS-1$

	public static String TAG_ACCELERATOR_SCOPE = "acceleratorScope"; //$NON-NLS-1$

	public static String TAG_ACTION = "action"; //$NON-NLS-1$

	public static String TAG_ACTION_DEFINITION = "actionDefinition"; //$NON-NLS-1$

	public static String TAG_ACTION_SET = "actionSet";//$NON-NLS-1$

	public static String TAG_ACTION_SET_PART_ASSOCIATION = "actionSetPartAssociation";//$NON-NLS-1$

	public static String TAG_ACTIVE_KEY_CONFIGURATION = "activeKeyConfiguration"; //$NON-NLS-1$

	public static String TAG_SEQUENCE_MODIFIER = "sequenceModifier"; //$NON-NLS-1$

	public static String TAG_ACTIVE_WHEN = "activeWhen"; //$NON-NLS-1$

	public static String TAG_ACTIVITY_IMAGE_BINDING = "activityImageBinding"; //$NON-NLS-1$

	public static String TAG_ADVISORPRODUCTBINDING = "triggerPointAdvisorProductBinding"; //$NON-NLS-1$

	public static String TAG_BAR = "bar"; //$NON-NLS-1$

	public static String TAG_CATEGORY = "category";//$NON-NLS-1$

	public static String TAG_CATEGORY_IMAGE_BINDING = "categoryImageBinding"; //$NON-NLS-1$

	public static String TAG_CATEGORYDEFINITION = "themeElementCategory"; //$NON-NLS-1$

	@Deprecated
	public static String TAG_CATEGORYPRESENTATIONBINDING = "categoryPresentationBinding"; //$NON-NLS-1$

	public static String TAG_CLASS = ATT_CLASS;

	public static String TAG_COLORDEFINITION = "colorDefinition"; //$NON-NLS-1$

	public static String TAG_COLOROVERRIDE = "colorOverride"; //$NON-NLS-1$    

	public static String TAG_COLORVALUE = "colorValue"; //$NON-NLS-1$

	public static String TAG_COMMAND = "command"; //$NON-NLS-1$

	public static String TAG_COMMAND_PARAMETER = "commandParameter"; //$NON-NLS-1$

	public static String TAG_COMMAND_PARAMETER_TYPE = "commandParameterType"; //$NON-NLS-1$

	public static String TAG_CONTENT_TYPE_BINDING = "contentTypeBinding"; //$NON-NLS-1$

	public static String TAG_CONTEXT = "context"; //$NON-NLS-1$

	public static String TAG_DATA = "data"; //$NON-NLS-1$

	public static String TAG_DEFAULT_HANDLER = ATT_DEFAULT_HANDLER;

	public static String TAG_DESCRIPTION = "description"; //$NON-NLS-1$

	public static String TAG_DYNAMIC = "dynamic"; //$NON-NLS-1$

	public static String TAG_EDITOR = "editor";//$NON-NLS-1$

	public static String TAG_EDITOR_CONTRIBUTION = "editorContribution"; //$NON-NLS-1$

	public static String TAG_ENABLED_WHEN = "enabledWhen"; //$NON-NLS-1$

	public static String TAG_ENABLEMENT = "enablement"; //$NON-NLS-1$

	public static String TAG_ENTRY = "entry"; //$NON-NLS-1$

	public static String TAG_FILTER = "filter"; //$NON-NLS-1$

	public static String TAG_FONTDEFINITION = "fontDefinition"; //$NON-NLS-1$

	public static String TAG_FONTOVERRIDE = "fontOverride"; //$NON-NLS-1$

	public static String TAG_FONTVALUE = "fontValue"; //$NON-NLS-1$

	public static String TAG_GROUP = "group"; //$NON-NLS-1$ 

	public static String TAG_GROUP_MARKER = "groupMarker"; //$NON-NLS-1$

	public static String TAG_HANDLER = "handler"; //$NON-NLS-1$

	public static String TAG_HANDLER_SUBMISSION = "handlerSubmission"; //$NON-NLS-1$

	public static String TAG_HIDDEN_MENU_ITEM = "hiddenMenuItem"; //$NON-NLS-1$

	public static String TAG_HIDDEN_TOOLBAR_ITEM = "hiddenToolBarItem"; //$NON-NLS-1$

	public static String TAG_HINT = "hint"; //$NON-NLS-1$

	public static String TAG_IMAGE = "image"; //$NON-NLS-1$

	public static String TAG_KEY = "key"; //$NON-NLS-1$

	public static String TAG_KEY_BINDING = "keyBinding"; //$NON-NLS-1$

	public static String TAG_KEY_CONFIGURATION = "keyConfiguration"; //$NON-NLS-1$

	public static String TAG_LOCATION = "location"; //$NON-NLS-1$

	public static String TAG_LOCATION_URI = "locationURI"; //$NON-NLS-1$

	public static String TAG_LAYOUT = "layout"; //$NON-NLS-1$

	public static String TAG_MAPPING = "mapping"; //$NON-NLS-1$

	public static String TAG_MENU = "menu"; //$NON-NLS-1$

	public static String TAG_NEW_WIZARD_SHORTCUT = "newWizardShortcut";//$NON-NLS-1$

	public static String TAG_OBJECT_CONTRIBUTION = "objectContribution";//$NON-NLS-1$

	public static String TAG_ORDER = "order"; //$NON-NLS-1$

	public static String TAG_PARAMETER = "parameter"; //$NON-NLS-1$

	public static String TAG_PART = "part";//$NON-NLS-1$

	public static String TAG_PERSP_SHORTCUT = "perspectiveShortcut";//$NON-NLS-1$

	public static String TAG_PERSPECTIVE = "perspective";//$NON-NLS-1$

	public static String TAG_PERSPECTIVE_EXTENSION = "perspectiveExtension";//$NON-NLS-1$

	public static String TAG_PRIMARYWIZARD = "primaryWizard"; //$NON-NLS-1$

	public static String TAG_REFERENCE = "reference"; //$NON-NLS-1$

	public static String TAG_SCHEME = "scheme"; //$NON-NLS-1$

	public static String TAG_SCOPE = "scope"; //$NON-NLS-1$

	public static String TAG_SELECTION = "selection"; //$NON-NLS-1$

	public static String TAG_SEPARATOR = "separator"; //$NON-NLS-1$
	
	
	public static String TAG_SETTINGS_TRANSFER = "settingsTransfer"; //$NON-NLS-1$

	public static String TAG_SHOW_IN_PART = "showInPart";//$NON-NLS-1$

	public static String TAG_STATE = "state"; //$NON-NLS-1$
	
	
	public static String TAG_SPLASH_HANDLER = "splashHandler"; //$NON-NLS-1$
	

	public static String TAG_SPLASH_HANDLER_PRODUCT_BINDING = "splashHandlerProductBinding"; //$NON-NLS-1$
	
	public static String TAG_STICKYVIEW = "stickyView";//$NON-NLS-1$

	public static String TAG_SUPPORT = "support"; //$NON-NLS-1$

	public static String TAG_THEME = "theme";//$NON-NLS-1$

	public static String TAG_TRANSFER = "transfer";//$NON-NLS-1$

	public static String TAG_TRIGGERPOINT = "triggerPoint"; //$NON-NLS-1$

	public static String TAG_TRIGGERPOINTADVISOR = "triggerPointAdvisor"; //$NON-NLS-1$

	public static String TAG_VIEW = "view";//$NON-NLS-1$

	public static String TAG_VIEW_SHORTCUT = "viewShortcut";//$NON-NLS-1$

	public static String TAG_VIEW_CONTRIBUTION = "viewContribution"; //$NON-NLS-1$

	public static String TAG_VIEWER_CONTRIBUTION = "viewerContribution"; //$NON-NLS-1$

	public static String TAG_VISIBILITY = "visibility"; //$NON-NLS-1$

	public static String TAG_VISIBLE_WHEN = "visibleWhen"; //$NON-NLS-1$

	public static String TAG_WIDGET = "widget"; //$NON-NLS-1$

	public static String TAG_CONTROL = "control"; //$NON-NLS-1$

	public static String TAG_WIZARD = "wizard";//$NON-NLS-1$

	public static String TAG_WORKING_SET = "workingSet"; //$NON-NLS-1$

	public static String TYPE_GROUP = "group"; //$NON-NLS-1$

	public static String TYPE_ITEM = "item"; //$NON-NLS-1$

	public static String TYPE_MENU = "menu"; //$NON-NLS-1$

	public static String TYPE_WIDGET = "widget"; //$NON-NLS-1$

	public static String TAG_TOOLBAR = "toolbar"; //$NON-NLS-1$
	
	public static String TAG_SERVICE_FACTORY = "serviceFactory"; //$NON-NLS-1$
	
	public static String TAG_SERVICE = "service"; //$NON-NLS-1$

	public static final String ATTR_FACTORY_CLASS = "factoryClass"; //$NON-NLS-1$

	public static final String ATTR_SERVICE_CLASS = "serviceClass"; //$NON-NLS-1$
	
	public static final String TAG_SOURCE_PROVIDER = "sourceProvider"; //$NON-NLS-1$

	public static final String ATTR_PROVIDER = "provider"; //$NON-NLS-1$

	public static final String TAG_VARIABLE = "variable"; //$NON-NLS-1$

	public static final String ATT_PRIORITY_LEVEL = "priorityLevel"; //$NON-NLS-1$
	
	public static final String ATT_MODE = "mode"; //$NON-NLS-1$

	public static final String ATT_PLATFORMS = "platforms"; //$NON-NLS-1$

	public static final String ATT_REPLACE = "replace"; //$NON-NLS-1$

	public static final String ATT_FIND = "find"; //$NON-NLS-1$

	public static final String TAG_KEYWORD_REFERENCE = "keywordReference"; //$NON-NLS-1$

	public static final String ATT_THEME_ASSOCIATION = "themeAssociation"; //$NON-NLS-1$

	public static final String ATT_THEME_ID = "themeId"; //$NON-NLS-1$

	public static final String ATT_COLOR_AND_FONT_ID = "colorAndFontId"; //$NON-NLS-1$ 

	public static final String ATT_OS_VERSION = "os_version"; //$NON-NLS-1$
}
