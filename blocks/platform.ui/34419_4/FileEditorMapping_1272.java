package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;

public class EditorRegistryReader extends RegistryReader {

    private EditorRegistry editorRegistry;

    protected void addEditors(EditorRegistry registry) {
        IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
        this.editorRegistry = registry;
        readRegistry(extensionRegistry, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_EDITOR);
    }

    @Override
	protected boolean readElement(IConfigurationElement element) {
        if (!element.getName().equals(IWorkbenchRegistryConstants.TAG_EDITOR)) {
			return false;
		}

        String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_ID);
            return true;
        }
        
        EditorDescriptor editor = new EditorDescriptor(id, element);
        
        List extensionsVector = new ArrayList();
        List filenamesVector = new ArrayList();
		List contentTypeVector = new ArrayList();
        boolean defaultEditor = false;

        if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
            return true;
        }
        
        String extensionsString = element.getAttribute(IWorkbenchRegistryConstants.ATT_EXTENSIONS);
        if (extensionsString != null) {
            StringTokenizer tokenizer = new StringTokenizer(extensionsString,
                    ",");//$NON-NLS-1$
            while (tokenizer.hasMoreTokens()) {
                extensionsVector.add(tokenizer.nextToken().trim());
            }
        }
        String filenamesString = element.getAttribute(IWorkbenchRegistryConstants.ATT_FILENAMES);
        if (filenamesString != null) {
            StringTokenizer tokenizer = new StringTokenizer(filenamesString,
                    ",");//$NON-NLS-1$
            while (tokenizer.hasMoreTokens()) {
                filenamesVector.add(tokenizer.nextToken().trim());
            }
        }
        
		IConfigurationElement [] bindings = element.getChildren(IWorkbenchRegistryConstants.TAG_CONTENT_TYPE_BINDING);
		for (int i = 0; i < bindings.length; i++) {
			String contentTypeId = bindings[i].getAttribute(IWorkbenchRegistryConstants.ATT_CONTENT_TYPE_ID);
			if (contentTypeId == null) {
				continue;
			}
			contentTypeVector.add(contentTypeId);
		}
		
        String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
        if (def != null) {
			defaultEditor = Boolean.valueOf(def).booleanValue();
		}

        editorRegistry.addEditorFromPlugin(editor, extensionsVector,
                filenamesVector, contentTypeVector, defaultEditor);
        return true;
    }


    public void readElement(EditorRegistry editorRegistry,
            IConfigurationElement element) {
        this.editorRegistry = editorRegistry;
        readElement(element);
    }
}
