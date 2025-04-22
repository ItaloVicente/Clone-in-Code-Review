package org.eclipse.ui.examples.readmetool;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ReadmeModelFactory {
    private static ReadmeModelFactory instance = new ReadmeModelFactory();

    private boolean registryLoaded = false;

    IReadmeFileParser parser = null;

    private ReadmeModelFactory() {
    }

    protected void addSections(AdaptableList list, MarkElement element) {
        list.add(element);
        Object[] children = element.getChildren(element);
        for (int i = 0; i < children.length; ++i) {
            addSections(list, (MarkElement) children[i]);
        }
    }

    public AdaptableList getContentOutline(IAdaptable adaptable) {
        return new AdaptableList(getToc((IFile) adaptable));
    }

    public static ReadmeModelFactory getInstance() {
        return instance;
    }

    public AdaptableList getSections(IFile file) {
        MarkElement[] topLevel = getToc(file);
        AdaptableList list = new AdaptableList();
        for (int i = 0; i < topLevel.length; i++) {
            addSections(list, topLevel[i]);
        }
        return list;
    }

    public AdaptableList getSections(ISelection sel) {
        if (!(sel instanceof IStructuredSelection))
            return null;
        IStructuredSelection structured = (IStructuredSelection) sel;

        Object object = structured.getFirstElement();
        if (object instanceof IFile) {
            IFile file = (IFile) object;
            String extension = file.getFileExtension();
            if (extension != null
                    && extension.equals(IReadmeConstants.EXTENSION)) {
                return getSections(file);
            }
        }

        return null;
    }

    private MarkElement[] getToc(IFile file) {
        if (registryLoaded == false)
            loadParser();
        return parser.parse(file);
    }

    private void loadParser() {
        IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(
                IReadmeConstants.PLUGIN_ID, IReadmeConstants.PP_SECTION_PARSER);
        if (point != null) {
            IExtension[] extensions = point.getExtensions();
            for (int i = 0; i < extensions.length; i++) {
                IExtension currentExtension = extensions[i];
                if (i == extensions.length - 1) {
                    IConfigurationElement[] configElements = currentExtension
                            .getConfigurationElements();
                    for (int j = 0; j < configElements.length; j++) {
                        IConfigurationElement config = configElements[i];
                        if (config.getName()
                                .equals(IReadmeConstants.TAG_PARSER)) {
                            processParserElement(config);
                            break;
                        }
                    }
                }
            }
        }
        if (parser == null)
            parser = new DefaultSectionsParser();
        registryLoaded = true;
    }

    private void processParserElement(IConfigurationElement element) {
        try {
            parser = (IReadmeFileParser) element
                    .createExecutableExtension(IReadmeConstants.ATT_CLASS);
        } catch (CoreException e) {
            System.out
                    .println(MessageUtil
                            .getString("Unable_to_create_file_parser") + e.getStatus().getMessage()); //$NON-NLS-1$
            parser = null;
        }
    }
}
