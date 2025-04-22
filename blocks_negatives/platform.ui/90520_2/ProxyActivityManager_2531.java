/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.internal.activities;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.util.ConfigurationElementMemento;

public final class Persistence {



















    public final static String fullContextTemplate = " (contributed by ''{0}'', extension ID ''{1}'')"; //$NON-NLS-1$;

    static ActivityRequirementBindingDefinition readActivityRequirementBindingDefinition(
            IMemento memento, String sourceIdOverride) { //, IStatus status) {
        String childActivityId = memento.getString(TAG_REQUIRED_ACTIVITY_ID);
        if (childActivityId == null) {
        	log(memento, ACTIVITY_REQUIREMENT_BINDING_DESC, "missing ID of the required activity"); //$NON-NLS-1$
			return null;
		}
        String parentActivityId = memento.getString(TAG_ACTIVITY_ID);
        if (parentActivityId == null) {
        	log(memento, ACTIVITY_REQUIREMENT_BINDING_DESC, "missing ID of the activity to bind"); //$NON-NLS-1$
			return null;
		}
        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new ActivityRequirementBindingDefinition(childActivityId,
                parentActivityId, sourceId);
    }

    static String readDefaultEnablement(IMemento memento) {
        return memento.getString(TAG_ID);
    }

    static ActivityDefinition readActivityDefinition(IMemento memento,
            String sourceIdOverride) {

        String id = memento.getString(TAG_ID);
        if (id == null) {
        	log(memento, ACTIVITY_DESC, "missing a unique identifier"); //$NON-NLS-1$
			return null;
		}
        String name = memento.getString(TAG_NAME);
        if (name == null) {
        	log(memento, ACTIVITY_DESC, "missing a translatable name"); //$NON-NLS-1$
			return null;
		}
        String description = memento.getString(TAG_DESCRIPTION);
        if (description == null) {
		}
        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new ActivityDefinition(id, name, sourceId, description);
    }

    static ActivityPatternBindingDefinition readActivityPatternBindingDefinition(
            IMemento memento, String sourceIdOverride) {

        String activityId = memento.getString(TAG_ACTIVITY_ID);
        if (activityId == null) {
        	log(memento, ACTIVITY_PATTERN_BINDING_DESC, "missing an ID of the activity to bind"); //$NON-NLS-1$
			return null;
		}
        String pattern = memento.getString(TAG_PATTERN);
        if (pattern == null) {
        	log(memento, ACTIVITY_PATTERN_BINDING_DESC, "missing the pattern to be bound"); //$NON-NLS-1$
			return null;
		}
        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);

        final String isEqualityPatternStr = memento.getString(TAG_IS_EQUALITY_PATTERN);
        final boolean isEqualityPattern = (isEqualityPatternStr != null && isEqualityPatternStr

        return new ActivityPatternBindingDefinition(activityId, pattern,
                sourceId, isEqualityPattern);
    }

    static CategoryActivityBindingDefinition readCategoryActivityBindingDefinition(
            IMemento memento, String sourceIdOverride) {

        String activityId = memento.getString(TAG_ACTIVITY_ID);
        if (activityId == null) {
        	log(memento, CATEGORY_ACTIVITY_BINDING_DESC, "missing the ID of the activity to bind"); //$NON-NLS-1$
			return null;
		}
        String categoryId = memento.getString(TAG_CATEGORY_ID);
        if (categoryId == null) {
        	log(memento, CATEGORY_ACTIVITY_BINDING_DESC, "missing the ID of the category to bind"); //$NON-NLS-1$
			return null;
		}
        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new CategoryActivityBindingDefinition(activityId, categoryId,
                sourceId);
    }

    static CategoryDefinition readCategoryDefinition(IMemento memento,
            String sourceIdOverride) {

        String id = memento.getString(TAG_ID);
        if (id == null) {
        	log(memento, CATEGORY_DESC, "has no ID"); //$NON-NLS-1$
			return null;
		}
        String name = memento.getString(TAG_NAME);
        if (name == null) {
        	log(memento, CATEGORY_DESC, "missing a translatable name"); //$NON-NLS-1$
			return null;
		}
        String description = memento.getString(TAG_DESCRIPTION);
        if (description == null) {
		}
        String sourceId = sourceIdOverride != null ? sourceIdOverride : memento
                .getString(TAG_SOURCE_ID);
        return new CategoryDefinition(id, name, sourceId, description);
    }

    private Persistence() {
    }

    static public void log(IMemento memento, String elementName, String msg) {
    	if (memento instanceof ConfigurationElementMemento) {
    		ConfigurationElementMemento cMemento = (ConfigurationElementMemento) memento;
    		log(elementName, msg, cMemento.getContributorName(), cMemento.getExtensionID());
    	} else
    		log(elementName, msg, null, null);
    }

    static public void log(IConfigurationElement element, String elementName, String msg) {
    	String contributorName = element.getContributor().getName();
    	String extensionID = element.getDeclaringExtension().getUniqueIdentifier();
   		log(elementName, msg, contributorName, extensionID);
    }

    static public void log(String elementName, String msg, String contributorName, String extensionID) {
    	if (contributorName != null && extensionID != null)
    		msgInContext += NLS.bind(fullContextTemplate, contributorName, extensionID);
    	else if (contributorName != null)
    		msgInContext += NLS.bind(shortContextTemplate, contributorName);
        WorkbenchPlugin.log(msgInContext);
    }
}
