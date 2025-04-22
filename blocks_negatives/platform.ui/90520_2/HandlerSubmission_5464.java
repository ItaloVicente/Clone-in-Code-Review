/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.commands.IParameterValues;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;

/**
 * <p>
 * A generic implementation of <code>IParameterValues</code> that takes advantage
 * of the <code>IExecutableExtension</code> mechanism.  The parameter values and names can be
 * specified purely in XML.  This can be done as follows:
 * </p>
 * <p><pre><code>
 *     &lt;command
 *    		name="%name"
 *     		description="%description"
 *     		categoryId="categoryId"
 *     		id="commandId"&gt;
 *     		&lt;parameter
 *     			id="parameterId"
 *     			name="%parameterName"&gt;
 *     				&lt;values class="org.eclipse.ui.commands.ExtensionParameterValues"&gt;
 *     					&lt;parameter name="%parameterName1" value="parameterValue1" /&gt;
 *     					&lt;parameter name="%parameterName2" value="parameterValue2" /&gt;
 *     					&lt;parameter name="%parameterName3" value="parameterValue3" /&gt;
 *     				&lt;/values&gt;
 *          &lt;/parameter&gt;
 *     &lt;/command&gt;
 * </code></pre></p>
 *
 * @since 3.1
 */
public final class ExtensionParameterValues implements IParameterValues,
		IExecutableExtension {

	/**
	 * The delimiter between elements if the name-value pairs are specified in a
	 * single string.
	 */
	public static final String DELIMITER = ",; //$NON-NLS-1$
-
-	/**
-	 * The parameter values for this instance. This is initialization when the
-	 * executable extension is created. For example,
-	 */
-	private Map parameterValues = null;
-
-	@Override
-	public Map getParameterValues() {
-		return parameterValues;
-	}
-
-	@Override
-	public final void setInitializationData(final IConfigurationElement config,
-			final String propertyName, final Object data) {
-		if (data == null) {
-			parameterValues = Collections.EMPTY_MAP;
-
-		} else if (data instanceof String) {
-			parameterValues = new HashMap();
-			final StringTokenizer tokenizer = new StringTokenizer(
-					(String) data, DELIMITER);
-			while (tokenizer.hasMoreTokens()) {
-				final String name = tokenizer.nextToken();
-				if (tokenizer.hasMoreTokens()) {
-					final String value = tokenizer.nextToken();
-					parameterValues.put(name, value);
-				}
-			}
-			parameterValues = Collections.unmodifiableMap(parameterValues);
-
-		} else if (data instanceof Hashtable) {
-			parameterValues = Collections.unmodifiableMap((Hashtable) data);
-
-		}
-
-	}
-}
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/commands/HandlerEvent.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/commands/HandlerEvent.java
deleted file mode 100644
index d3e691c941..0000000000
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/commands/HandlerEvent.java	
+++ /dev/null
@@ -1,144 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2003, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.commands;
-
-import java.util.Map;
-import org.eclipse.ui.internal.util.Util;
-
-/**
- * An instance of this class describes changes to an instance of
- * <code>IHandler</code>.
- * <p>
- * This class is not intended to be extended by clients.
- * </p>
- *
- * @since 3.0
- * @see IHandlerListener#handlerChanged(HandlerEvent)
- * @deprecated Please use the org.eclipse.core.commands" plug-in instead.
 */
@Deprecated
@SuppressWarnings("all")
public final class HandlerEvent {

    /**
     * Whether the attributes of the handler changed.
     */
    private final boolean attributeValuesByNameChanged;

    /**
     * The handler that changed; this value is never <code>null</code>.
     */
    private final IHandler handler;

    /**
     * This is the cached result of getPreviousAttributeValuesByName. It is
     * computed the first time getPreviousAttributeValuesByName is called.
     */
    private Map previousAttributeValuesByName;

    /**
     * The map of previous attributes, if they changed.  If they did not change,
     * then this value is <code>null</code>.  The map's keys are the attribute
     * names (strings), and its value are any object.
     *
     * This is the original map passed into the constructor. This object always
     * returns a copy of this map, not the original. However the constructor of
     * this object is called very frequently and the map is rarely requested,
     * so we only copy the map the first time it is requested.
     *
     * @since 3.1
     */
    private final Map originalPreviousAttributeValuesByName;

    /**
     * Creates a new instance of this class.
     *
     * @param handler
     *            the instance of the interface that changed.
     * @param attributeValuesByNameChanged
     *            true, iff the attributeValuesByName property changed.
     * @param previousAttributeValuesByName
     *            the map of previous attribute values by name. This map may be
     *            empty. If this map is not empty, it's collection of keys must
     *            only contain instances of <code>String</code>. This map
     *            must be <code>null</code> if attributeValuesByNameChanged is
     *            <code>false</code> and must not be null if
     *            attributeValuesByNameChanged is <code>true</code>.
     */
	@Deprecated
    public HandlerEvent(IHandler handler, boolean attributeValuesByNameChanged,
            Map previousAttributeValuesByName) {
        if (handler == null) {
			throw new NullPointerException();
		}

        if (!attributeValuesByNameChanged
                && previousAttributeValuesByName != null) {
			throw new IllegalArgumentException();
		}

        if (attributeValuesByNameChanged) {
            this.originalPreviousAttributeValuesByName = previousAttributeValuesByName;
        } else {
            this.originalPreviousAttributeValuesByName = null;
        }

        this.handler = handler;
        this.attributeValuesByNameChanged = attributeValuesByNameChanged;
    }

    /**
     * Returns the instance of the interface that changed.
     *
     * @return the instance of the interface that changed. Guaranteed not to be
     *         <code>null</code>.
     */
	@Deprecated
    public IHandler getHandler() {
        return handler;
    }

    /**
     * Returns the map of previous attribute values by name.
     *
     * @return the map of previous attribute values by name. This map may be
     *         empty. If this map is not empty, it's collection of keys is
     *         guaranteed to only contain instances of <code>String</code>.
     *         This map is guaranteed to be <code>null</code> if
     *         haveAttributeValuesByNameChanged() is <code>false</code> and is
     *         guaranteed to not be null if haveAttributeValuesByNameChanged()
     *         is <code>true</code>.
     */
	@Deprecated
    public Map getPreviousAttributeValuesByName() {
        if (originalPreviousAttributeValuesByName == null) {
            return null;
        }

        if (previousAttributeValuesByName == null) {
            previousAttributeValuesByName = Util.safeCopy(
                    originalPreviousAttributeValuesByName, String.class, Object.class,
                    false, true);
        }

        return previousAttributeValuesByName;
    }

    /**
     * Returns whether or not the attributeValuesByName property changed.
     *
     * @return true, iff the attributeValuesByName property changed.
     */
	@Deprecated
    public boolean haveAttributeValuesByNameChanged() {
        return attributeValuesByNameChanged;
    }
}
