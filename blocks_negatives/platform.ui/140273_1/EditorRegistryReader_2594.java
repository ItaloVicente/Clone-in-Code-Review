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
@@ -142,26 +138,23 @@ public class EditorRegistryReader extends RegistryReader {
 			contentTypeVector.add(contentTypeId);
 		}
 
-        // Is this the default editor?
-        String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
-        if (def != null) {
+		// Is this the default editor?
+		String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
+		if (def != null) {
 			defaultEditor = Boolean.parseBoolean(def);
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
index f852921b11..56fb365b9f 100644
--- a/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java	
+++ b/bundles/org.eclipse.ui.workbench/Eclipse UI/org/eclipse/ui/internal/registry/FileEditorMapping.java	
@@ -32,134 +32,131 @@ import org.eclipse.ui.internal.WorkbenchImages;
  * Implementation of IFileEditorMapping.
  *
  */
-public class FileEditorMapping extends Object implements IFileEditorMapping,
-    Cloneable {
+public class FileEditorMapping extends Object implements IFileEditorMapping, Cloneable {
 
 	private static final String STAR = *"; //$NON-NLS-1$
