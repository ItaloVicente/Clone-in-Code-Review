        boolean defaultEditor = false;

        if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
            return true;
        }

        String extensionsString = element.getAttribute(IWorkbenchRegistryConstants.ATT_EXTENSIONS);
        if (extensionsString != null) {
            StringTokenizer tokenizer = new StringTokenizer(extensionsString,
                    ",);//$NON-NLS-1$
-            while (tokenizer.hasMoreTokens()) {
-                extensionsVector.add(tokenizer.nextToken().trim());
-            }
-        }
-        String filenamesString = element.getAttribute(IWorkbenchRegistryConstants.ATT_FILENAMES);
-        if (filenamesString != null) {
-            StringTokenizer tokenizer = new StringTokenizer(filenamesString,
-                    ,);//$NON-NLS-1$
-            while (tokenizer.hasMoreTokens()) {
-                filenamesVector.add(tokenizer.nextToken().trim());
-            }
-        }
-
-		IConfigurationElement [] bindings = element.getChildren(IWorkbenchRegistryConstants.TAG_CONTENT_TYPE_BINDING);
+		boolean defaultEditor = false;
+
+		// Get editor name (required field).
+		if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
+			logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
+			return true;
+		}
+
+		// Get target extensions (optional field)
+		String extensionsString = element.getAttribute(IWorkbenchRegistryConstants.ATT_EXTENSIONS);
+		if (extensionsString != null) {
+			StringTokenizer tokenizer = new StringTokenizer(extensionsString, ,);//$NON-NLS-1$
+			while (tokenizer.hasMoreTokens()) {
+				extensionsVector.add(tokenizer.nextToken().trim());
+			}
+		}
+		String filenamesString = element.getAttribute(IWorkbenchRegistryConstants.ATT_FILENAMES);
+		if (filenamesString != null) {
+			StringTokenizer tokenizer = new StringTokenizer(filenamesString, ,);//$NON-NLS-1$
+			while (tokenizer.hasMoreTokens()) {
+				filenamesVector.add(tokenizer.nextToken().trim());
+			}
+		}
+
+		IConfigurationElement[] bindings = element.getChildren(IWorkbenchRegistryConstants.TAG_CONTENT_TYPE_BINDING);
 		for (IConfigurationElement binding : bindings) {
 			String contentTypeId = binding.getAttribute(IWorkbenchRegistryConstants.ATT_CONTENT_TYPE_ID);
 			if (contentTypeId == null) {
@@ -139,26 +138,23 @@ public class EditorRegistryReader extends RegistryReader {
 			contentTypeVector.add(contentTypeId);
 		}
 
-        // Is this the default editor?
-        String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
-        if (def != null) {
-			defaultEditor = Boolean.valueOf(def).booleanValue();
+		// Is this the default editor?
+		String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
+		if (def != null) {
+			defaultEditor = Boolean.parseBoolean(def);
 		}
 
-        // Add the editor to the manager.
-        editorRegistry.addEditorFromPlugin(editor, extensionsVector,
-                filenamesVector, contentTypeVector, defaultEditor);
-        return true;
+		// Add the editor to the manager.
+		editorRegistry.addEditorFromPlugin(editor, extensionsVector, filenamesVector, contentTypeVector, defaultEditor);
+		return true;
 	}
 
-
-    /**
-     * @param editorRegistry
-     * @param element
-     */
-    public void readElement(EditorRegistry editorRegistry,
-            IConfigurationElement element) {
-        this.editorRegistry = editorRegistry;
-        readElement(element);
-    }
+	/**
+	 * @param editorRegistry
+	 * @param element
+	 */
+	public void readElement(EditorRegistry editorRegistry, IConfigurationElement element) {
+		this.editorRegistry = editorRegistry;
+		readElement(element);
+	}
 }
diff --git a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java
index 0ab5ca84ec..3dac1ca16b 100644
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java	
+++ b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java	
@@ -1,9 +1,12 @@
 /*******************************************************************************
  * Copyright (c) 2000, 2015 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
+ *
+ * This program and the accompanying materials
+ * are made available under the terms of the Eclipse Public License 2.0
  * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
+ * https://www.eclipse.org/legal/epl-2.0/
+ *
+ * SPDX-License-Identifier: EPL-2.0
  *
  * Contributors:
  *     IBM Corporation - initial API and implementation
@@ -14,8 +17,8 @@ package org.eclipse.ui.internal.registry;
 
 import java.util.ArrayList;
 import java.util.Collection;
-import java.util.Iterator;
 import java.util.List;
+import java.util.Objects;
 import org.eclipse.core.runtime.Assert;
 import org.eclipse.jface.resource.ImageDescriptor;
 import org.eclipse.osgi.util.TextProcessor;
@@ -29,291 +32,253 @@ import org.eclipse.ui.internal.WorkbenchImages;
  * Implementation of IFileEditorMapping.
  *
  */
-public class FileEditorMapping extends Object implements IFileEditorMapping,
-    Cloneable {
+public class FileEditorMapping extends Object implements IFileEditorMapping, Cloneable {
 
 	private static final String STAR = *"; //$NON-NLS-1$
