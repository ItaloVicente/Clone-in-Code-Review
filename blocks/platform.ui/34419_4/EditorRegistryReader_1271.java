package org.eclipse.ui.internal.registry;

import com.ibm.icu.text.Collator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.editorsupport.ComponentSupport;
import org.eclipse.ui.internal.misc.ExternalProgramImageDescriptor;
import org.eclipse.ui.internal.misc.ProgramImageDescriptor;
import org.eclipse.ui.internal.util.Util;


public class EditorRegistry extends EventManager implements IEditorRegistry,
		IExtensionChangeHandler {
	
	private final static IEditorDescriptor [] EMPTY = new IEditorDescriptor[0];
	
	class RelatedRegistry {

		public IEditorDescriptor[] getRelatedObjects(IContentType type) {			
			IEditorDescriptor[] relatedObjects = (IEditorDescriptor[]) contentTypeToEditorMappings.get(type);
			if (relatedObjects == null) {
				return EMPTY;
			}
			return (IEditorDescriptor[]) WorkbenchActivityHelper.restrictArray(relatedObjects);
		}

		public IEditorDescriptor[] getRelatedObjects(String fileName) {
			IFileEditorMapping mapping = getMappingFor(fileName);
			if (mapping == null) {
				return EMPTY;
			}
			
			return (IEditorDescriptor[]) WorkbenchActivityHelper.restrictArray(mapping.getEditors());
		}
		
	}
	
	private Map contentTypeToEditorMappings = new HashMap();
	
    private Map extensionImages = new HashMap();

    private List sortedEditorsFromPlugins = new ArrayList();

    private Map mapIDtoEditor = initialIdToEditorMap(10);

    private EditorMap typeEditorMappings;

    private static final Comparator comparer = new Comparator() {
        private Collator collator = Collator.getInstance();

		@Override
		public int compare(Object arg0, Object arg1) {
			String s1 = ((IEditorDescriptor) arg0).getLabel();
			String s2 = ((IEditorDescriptor) arg1).getLabel();
			return collator.compare(s1, s2);
		}
	};

	private RelatedRegistry relatedRegistry;

	public static final String EMPTY_EDITOR_ID = "org.eclipse.ui.internal.emptyEditorTab"; //$NON-NLS-1$

    public EditorRegistry() {
        super();
        initializeFromStorage();
        IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
        tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));
		relatedRegistry = new RelatedRegistry();
    }

    public void addEditorFromPlugin(EditorDescriptor editor, List extensions,
            List filenames, List contentTypeVector, boolean bDefault) {

    	PlatformUI.getWorkbench().getExtensionTracker().registerObject(
				editor.getConfigurationElement().getDeclaringExtension(),
				editor, IExtensionTracker.REF_WEAK);
        sortedEditorsFromPlugins.add(editor);

        Iterator itr = extensions.iterator();
        while (itr.hasNext()) {
            String fileExtension = (String) itr.next();

            if (fileExtension != null && fileExtension.length() > 0) {
                FileEditorMapping mapping = getMappingFor("*." + fileExtension); //$NON-NLS-1$
                if (mapping == null) { // no mapping for that extension
                    mapping = new FileEditorMapping(fileExtension);
                    typeEditorMappings.putDefault(mappingKeyFor(mapping),
                            mapping);
                }
                mapping.addEditor(editor);
                if (bDefault) {
					mapping.setDefaultEditor(editor);
				}
            }
        }

        itr = filenames.iterator();
        while (itr.hasNext()) {
            String filename = (String) itr.next();

            if (filename != null && filename.length() > 0) {
                FileEditorMapping mapping = getMappingFor(filename);
                if (mapping == null) { // no mapping for that extension
                    String name;
                    String extension;
                    int index = filename.indexOf('.');
                    if (index < 0) {
                        name = filename;
                        extension = ""; //$NON-NLS-1$
                    } else {
                        name = filename.substring(0, index);
                        extension = filename.substring(index + 1);
                    }
                    mapping = new FileEditorMapping(name, extension);
                    typeEditorMappings.putDefault(mappingKeyFor(mapping),
                            mapping);
                }
                mapping.addEditor(editor);
                if (bDefault) {
					mapping.setDefaultEditor(editor);
				}
            }
        }
		
		
		itr = contentTypeVector.iterator();
		while(itr.hasNext()) {
			String contentTypeId = (String) itr.next();
			if (contentTypeId != null && contentTypeId.length() > 0) {
				IContentType contentType = Platform.getContentTypeManager().getContentType(contentTypeId);
				if (contentType != null) {
					IEditorDescriptor [] editorArray = (IEditorDescriptor[]) contentTypeToEditorMappings.get(contentType);
					if (editorArray == null) {
						editorArray = new IEditorDescriptor[] {editor};
						contentTypeToEditorMappings.put(contentType, editorArray);
					}
					else {
						IEditorDescriptor [] newArray = new IEditorDescriptor[editorArray.length + 1];
						if (bDefault) { // default editors go to the front of the line
							newArray[0] = editor;
							System.arraycopy(editorArray, 0, newArray, 1, editorArray.length);
						}
						else {
							newArray[editorArray.length] = editor;
							System.arraycopy(editorArray, 0, newArray, 0, editorArray.length);
						}
						contentTypeToEditorMappings.put(contentType, newArray);
					}
				}
			}
		}

        mapIDtoEditor.put(editor.getId(), editor);
    }

    private void addExternalEditorsToEditorMap() {
        IEditorDescriptor desc = null;

        FileEditorMapping maps[] = typeEditorMappings.allMappings();
        for (int i = 0; i < maps.length; i++) {
            FileEditorMapping map = maps[i];
            IEditorDescriptor[] descArray = map.getEditors();
            for (int n = 0; n < descArray.length; n++) {
                desc = descArray[n];
                mapIDtoEditor.put(desc.getId(), desc);
            }
        }
    }

    @Override
	public void addPropertyListener(IPropertyListener l) {
        addListenerObject(l);
    }

    @Override
	public IEditorDescriptor findEditor(String id) {
        Object desc = mapIDtoEditor.get(id);
        if (WorkbenchActivityHelper.restrictUseOf(desc)) {
        	return null;
        }
		return (IEditorDescriptor) desc;
    }

    private void firePropertyChange(final int type) {
        Object[] array = getListeners();
        for (int nX = 0; nX < array.length; nX++) {
            final IPropertyListener l = (IPropertyListener) array[nX];
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.propertyChanged(EditorRegistry.this, type);
                }
            });
        }
    }

    @Override
	public IEditorDescriptor getDefaultEditor() {
        return findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
    }

    @Override
	public IEditorDescriptor getDefaultEditor(String filename) {
		IEditorDescriptor defaultEditor = getDefaultEditor(filename, guessAtContentType(filename));
		if (defaultEditor != null) {
			return defaultEditor;
		}

		IContentType[] contentTypes = Platform.getContentTypeManager()
				.findContentTypesFor(filename);
		for (int i = 0; i < contentTypes.length; i++) {
			IEditorDescriptor editor = getDefaultEditor(filename, contentTypes[i]);
			if (editor != null) {
				return editor;
			}
		}
		return null;
    }

	private IContentType guessAtContentType(String filename) {
		return Platform.getContentTypeManager().findContentTypeFor(filename);
	}

    private ImageDescriptor getDefaultImage() {
        return WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
    }

    @Override
	public IEditorDescriptor[] getEditors(String filename) {
		return getEditors(filename, guessAtContentType(filename));
	}

    @Override
	public IFileEditorMapping[] getFileEditorMappings() {
        FileEditorMapping[] array = typeEditorMappings.allMappings();
        final Collator collator = Collator.getInstance();
        Arrays.sort(array, new Comparator() {
            
            @Override
			public int compare(Object o1, Object o2) {
                String s1 = ((FileEditorMapping) o1).getLabel();
                String s2 = ((FileEditorMapping) o2).getLabel();
                return collator.compare(s1, s2);
            }
        });
        return array;
    }

    @Override
	public ImageDescriptor getImageDescriptor(String filename) {
		return getImageDescriptor(filename, guessAtContentType(filename));
	}

    private FileEditorMapping getMappingFor(String ext) {
        if (ext == null) {
			return null;
		}
        String key = mappingKeyFor(ext);
        return typeEditorMappings.get(key);
    }

    private FileEditorMapping[] getMappingForFilename(String filename) {
        FileEditorMapping[] mapping = new FileEditorMapping[2];

        mapping[0] = getMappingFor(filename);

        int index = filename.lastIndexOf('.');
        if (index > -1) {
            String extension = filename.substring(index);
            mapping[1] = getMappingFor("*" + extension); //$NON-NLS-1$
        }

        return mapping;
    }

    public IEditorDescriptor[] getSortedEditorsFromOS() {
        List externalEditors = new ArrayList();
        Program[] programs = Program.getPrograms();

        for (int i = 0; i < programs.length; i++) {

            EditorDescriptor editor = new EditorDescriptor();
            editor.setOpenMode(EditorDescriptor.OPEN_EXTERNAL);
            editor.setProgram(programs[i]);

            ImageDescriptor desc = new ExternalProgramImageDescriptor(
                    programs[i]);
            editor.setImageDescriptor(desc);
            externalEditors.add(editor);
        }

        Object[] tempArray = sortEditors(externalEditors);
        IEditorDescriptor[] array = new IEditorDescriptor[externalEditors
                .size()];
        for (int i = 0; i < tempArray.length; i++) {
            array[i] = (IEditorDescriptor) tempArray[i];
        }
        return array;
    }

    public IEditorDescriptor[] getSortedEditorsFromPlugins() {
		Collection descs = WorkbenchActivityHelper
				.restrictCollection(sortedEditorsFromPlugins, new ArrayList());
		return (IEditorDescriptor[]) descs.toArray(new IEditorDescriptor[descs
				.size()]);
	}

    private HashMap initialIdToEditorMap(int initialSize) {
        HashMap map = new HashMap(initialSize);
        addSystemEditors(map);
        return map;
    }

    private void addSystemEditors(HashMap map) {
        EditorDescriptor editor = new EditorDescriptor();
        editor.setID(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
        editor.setName(WorkbenchMessages.SystemEditorDescription_name); 
        editor.setOpenMode(EditorDescriptor.OPEN_EXTERNAL);
        map.put(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID, editor);

        if (ComponentSupport.inPlaceEditorSupported()) {
            editor = new EditorDescriptor();
            editor.setID(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID);
            editor.setName(WorkbenchMessages.SystemInPlaceDescription_name);
            editor.setOpenMode(EditorDescriptor.OPEN_INPLACE);
            map.put(IEditorRegistry.SYSTEM_INPLACE_EDITOR_ID, editor);
        }
        
		EditorDescriptor emptyEditorDescriptor = new EditorDescriptor();
		emptyEditorDescriptor.setID(EMPTY_EDITOR_ID);
		emptyEditorDescriptor.setName("(Empty)"); //$NON-NLS-1$
		emptyEditorDescriptor
				.setImageDescriptor(WorkbenchImages
						.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_ELEMENT));
		map.put(EMPTY_EDITOR_ID, emptyEditorDescriptor);
    }

    private void initializeFromStorage() {
        typeEditorMappings = new EditorMap();
        extensionImages = new HashMap();

        EditorRegistryReader registryReader = new EditorRegistryReader();
        registryReader.addEditors(this);
        sortInternalEditors();
        rebuildInternalEditorMap();

        IPreferenceStore store = PlatformUI.getPreferenceStore();
        String defaultEditors = store
                .getString(IPreferenceConstants.DEFAULT_EDITORS);
        String chachedDefaultEditors = store
                .getString(IPreferenceConstants.DEFAULT_EDITORS_CACHE);

        if (defaultEditors == null
                || defaultEditors.equals(chachedDefaultEditors)) {
            setProductDefaults(defaultEditors);
            loadAssociations(); //get saved earlier state
        } else {
            loadAssociations(); //get saved earlier state
            setProductDefaults(defaultEditors);
            store.putValue(IPreferenceConstants.DEFAULT_EDITORS_CACHE,
                    defaultEditors);
        }
        addExternalEditorsToEditorMap();
    }

    private void setProductDefaults(String defaultEditors) {
        if (defaultEditors == null || defaultEditors.length() == 0) {
			return;
		}

        StringTokenizer extEditors = new StringTokenizer(defaultEditors,
                new Character(IPreferenceConstants.SEPARATOR).toString());
        while (extEditors.hasMoreTokens()) {
            String extEditor = extEditors.nextToken().trim();
            int index = extEditor.indexOf(':');
            if (extEditor.length() < 3 || index <= 0
                    || index >= (extEditor.length() - 1)) {
                WorkbenchPlugin
                        .log("Error setting default editor. Could not parse '" + extEditor + "'. Default editors should be specified as '*.ext1:editorId1;*.ext2:editorId2'"); //$NON-NLS-1$ //$NON-NLS-2$
                return;
            }
            String ext = extEditor.substring(0, index).trim();
            String editorId = extEditor.substring(index + 1).trim();
            FileEditorMapping mapping = getMappingFor(ext);
            if (mapping == null) {
                WorkbenchPlugin
                        .log("Error setting default editor. Could not find mapping for '" + ext + "'."); //$NON-NLS-1$ //$NON-NLS-2$
                continue;
            }
            EditorDescriptor editor = (EditorDescriptor) findEditor(editorId);
            if (editor == null) {
                WorkbenchPlugin
                        .log("Error setting default editor. Could not find editor: '" + editorId + "'."); //$NON-NLS-1$ //$NON-NLS-2$
                continue;
            }
            mapping.setDefaultEditor(editor);
        }
    }

    private boolean readEditors(Map editorTable) {
        IPath workbenchStatePath = WorkbenchPlugin.getDefault().getDataLocation();
        if(workbenchStatePath == null) {
			return false;
		}        
        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();
        Reader reader = null;
        FileInputStream stream = null;
        try {
            String xmlString = store.getString(IPreferenceConstants.EDITORS);
            if (xmlString == null || xmlString.length() == 0) {
                stream = new FileInputStream(workbenchStatePath
                        .append(IWorkbenchConstants.EDITOR_FILE_NAME)
                        .toOSString());
                reader = new BufferedReader(new InputStreamReader(stream,
                        "utf-8")); //$NON-NLS-1$
            } else {
                reader = new StringReader(xmlString);
            }
            XMLMemento memento = XMLMemento.createReadRoot(reader);
            EditorDescriptor editor;
            IMemento[] edMementos = memento
                    .getChildren(IWorkbenchConstants.TAG_DESCRIPTOR);
            for (int i = 0; i < edMementos.length; i++) {
                editor = new EditorDescriptor();
                boolean valid = editor.loadValues(edMementos[i]);
                if (!valid) {
                    continue;
                }
                if (editor.getPluginID() != null) {
                    EditorDescriptor validEditorDescritor = (EditorDescriptor) mapIDtoEditor
                            .get(editor.getId());
                    if (validEditorDescritor != null) {
                        editorTable.put(validEditorDescritor.getId(),
                                validEditorDescritor);
                    }
                } else { //This is either from a program or a user defined
                    ImageDescriptor descriptor;
                    if (editor.getProgram() == null) {
						descriptor = new ProgramImageDescriptor(editor
                                .getFileName(), 0);
					} else {
						descriptor = new ExternalProgramImageDescriptor(editor
                                .getProgram());
					}
                    editor.setImageDescriptor(descriptor);
                    editorTable.put(editor.getId(), editor);
                }
            }
        } catch (IOException e) {
            return false;
        } catch (WorkbenchException e) {
            ErrorDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
                    WorkbenchMessages.EditorRegistry_errorMessage, 
                    e.getStatus());
            return false;
		} finally {
			try {
				if (reader != null) {
					reader.close();
				} else if (stream != null)
					stream.close();
			} catch (IOException ex) {
				WorkbenchPlugin.log("Error reading editors: Could not close steam", ex); //$NON-NLS-1$
			}
        }

        return true;
    }

    public void readResources(Map editorTable, Reader reader)
            throws WorkbenchException {
        XMLMemento memento = XMLMemento.createReadRoot(reader);
        String versionString = memento.getString(IWorkbenchConstants.TAG_VERSION);
        boolean versionIs31 = "3.1".equals(versionString); //$NON-NLS-1$
        
        IMemento[] extMementos = memento
                .getChildren(IWorkbenchConstants.TAG_INFO);
        for (int i = 0; i < extMementos.length; i++) {
            String name = extMementos[i]
                    .getString(IWorkbenchConstants.TAG_NAME);
            if (name == null) {
				name = "*"; //$NON-NLS-1$
			}
            String extension = extMementos[i]
                    .getString(IWorkbenchConstants.TAG_EXTENSION);
            IMemento[] idMementos = extMementos[i]
                    .getChildren(IWorkbenchConstants.TAG_EDITOR);
            String[] editorIDs = new String[idMementos.length];
            for (int j = 0; j < idMementos.length; j++) {
                editorIDs[j] = idMementos[j]
                        .getString(IWorkbenchConstants.TAG_ID);
            }
            idMementos = extMementos[i]
                    .getChildren(IWorkbenchConstants.TAG_DELETED_EDITOR);
            String[] deletedEditorIDs = new String[idMementos.length];
            for (int j = 0; j < idMementos.length; j++) {
                deletedEditorIDs[j] = idMementos[j]
                        .getString(IWorkbenchConstants.TAG_ID);
            }
			String key = name;
			if (extension != null && extension.length() > 0) {
				key = key + "." + extension; //$NON-NLS-1$
			}
			FileEditorMapping mapping = getMappingFor(key);
            if (mapping == null) {
                mapping = new FileEditorMapping(name, extension);
            }
            List editors = new ArrayList();
            for (int j = 0; j < editorIDs.length; j++) {
                if (editorIDs[j] != null) {
                    EditorDescriptor editor = (EditorDescriptor) editorTable
                            .get(editorIDs[j]);
                    if (editor != null) {
                        editors.add(editor);
                    }
                }
            }
            List deletedEditors = new ArrayList();
            for (int j = 0; j < deletedEditorIDs.length; j++) {
                if (deletedEditorIDs[j] != null) {
                    EditorDescriptor editor = (EditorDescriptor) editorTable
                            .get(deletedEditorIDs[j]);
                    if (editor != null) {
                        deletedEditors.add(editor);
                    }
                }
            }
            
            List defaultEditors = new ArrayList();
            
            if (versionIs31) { // parse the new format
				idMementos = extMementos[i]
						.getChildren(IWorkbenchConstants.TAG_DEFAULT_EDITOR);
				String[] defaultEditorIds = new String[idMementos.length];
				for (int j = 0; j < idMementos.length; j++) {
					defaultEditorIds[j] = idMementos[j]
							.getString(IWorkbenchConstants.TAG_ID);
				}
				for (int j = 0; j < defaultEditorIds.length; j++) {
					if (defaultEditorIds[j] != null) {
						EditorDescriptor editor = (EditorDescriptor) editorTable
								.get(defaultEditorIds[j]);
						if (editor != null) {
							defaultEditors.add(editor);
						}
					}
				}
			}
            else { // guess at pre 3.1 format defaults
            		if (!editors.isEmpty()) {
            			EditorDescriptor editor = (EditorDescriptor) editors.get(0);
            			if (editor != null) {
                			defaultEditors.add(editor);	
                		}
            		}
            		defaultEditors.addAll(Arrays.asList(mapping.getDeclaredDefaultEditors()));
            }
            
            IEditorDescriptor[] editorsArray = mapping.getEditors();
            for (int j = 0; j < editorsArray.length; j++) {
                if (!contains(editors, editorsArray[j])
                        && !deletedEditors.contains(editorsArray[j])) {
                    editors.add(editorsArray[j]);
                }
            }
            mapping.setEditorsList(editors);
            mapping.setDeletedEditorsList(deletedEditors);
            mapping.setDefaultEditors(defaultEditors);
            typeEditorMappings.put(mappingKeyFor(mapping), mapping);
        }
    }

    private boolean contains(List editorsArray,
            IEditorDescriptor editorDescriptor) {
        IEditorDescriptor currentEditorDescriptor = null;
        Iterator i = editorsArray.iterator();
        while (i.hasNext()) {
            currentEditorDescriptor = (IEditorDescriptor) i.next();
            if (currentEditorDescriptor.getId()
                    .equals(editorDescriptor.getId())) {
				return true;
			}
        }
        return false;

    }

    private boolean readResources(Map editorTable) {
        IPath workbenchStatePath = WorkbenchPlugin.getDefault().getDataLocation();
        if(workbenchStatePath == null) {
			return false;
		}
        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();
        Reader reader = null;
        FileInputStream stream = null;
        try {
            String xmlString = store.getString(IPreferenceConstants.RESOURCES);
            if (xmlString == null || xmlString.length() == 0) {
                stream = new FileInputStream(workbenchStatePath
                        .append(IWorkbenchConstants.RESOURCE_TYPE_FILE_NAME)
                        .toOSString());
                reader = new BufferedReader(new InputStreamReader(stream,
                        "utf-8")); //$NON-NLS-1$
            } else {
                reader = new StringReader(xmlString);
            }
            readResources(editorTable, reader);
        } catch (IOException e) {
            MessageDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
                    WorkbenchMessages.EditorRegistry_errorMessage);
            return false;
        } catch (WorkbenchException e) {
            ErrorDialog.openError((Shell) null, WorkbenchMessages.EditorRegistry_errorTitle,
                    WorkbenchMessages.EditorRegistry_errorMessage,
                    e.getStatus());
            return false;
        } finally {
            try {
                if (reader != null) {
					reader.close();
				} else if (stream != null) {
                	stream.close();
				}
            } catch (IOException ex) {
				WorkbenchPlugin.log("Error reading resources: Could not close steam", ex); //$NON-NLS-1$
            }
        }
        return true;

    }

    private boolean loadAssociations() {
        Map editorTable = new HashMap();
        if (!readEditors(editorTable)) {
            return false;
        }
        return readResources(editorTable);
    }

    private String mappingKeyFor(String type) {
        return type.toLowerCase();
    }

    private String mappingKeyFor(FileEditorMapping mapping) {
        return mappingKeyFor(mapping.getName()
                + (mapping.getExtension().length() == 0 ? "" : "." + mapping.getExtension())); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private void rebuildEditorMap() {
        rebuildInternalEditorMap();
        addExternalEditorsToEditorMap();
    }

    private void rebuildInternalEditorMap() {
        Iterator itr = null;
        IEditorDescriptor desc = null;

        mapIDtoEditor = initialIdToEditorMap(mapIDtoEditor.size());

        itr = sortedEditorsFromPlugins.iterator();
        while (itr.hasNext()) {
            desc = (IEditorDescriptor) itr.next();
            mapIDtoEditor.put(desc.getId(), desc);
        }
    }

    @Override
	public void removePropertyListener(IPropertyListener l) {
        removeListenerObject(l);
    }

    public void saveAssociations() {
        List editors = new ArrayList();
        IPreferenceStore store = WorkbenchPlugin.getDefault()
                .getPreferenceStore();

        XMLMemento memento = XMLMemento
                .createWriteRoot(IWorkbenchConstants.TAG_EDITORS);
        memento.putString(IWorkbenchConstants.TAG_VERSION, "3.1"); //$NON-NLS-1$
        FileEditorMapping maps[] = typeEditorMappings.userMappings();
        for (int mapsIndex = 0; mapsIndex < maps.length; mapsIndex++) {
            FileEditorMapping type = maps[mapsIndex];
            IMemento editorMemento = memento
                    .createChild(IWorkbenchConstants.TAG_INFO);
            editorMemento.putString(IWorkbenchConstants.TAG_NAME, type
                    .getName());
            editorMemento.putString(IWorkbenchConstants.TAG_EXTENSION, type
                    .getExtension());
            IEditorDescriptor[] editorArray = type.getEditors();
            for (int i = 0; i < editorArray.length; i++) {
                EditorDescriptor editor = (EditorDescriptor) editorArray[i];
                if (!editors.contains(editor)) {
                    editors.add(editor);
                }
                IMemento idMemento = editorMemento
                        .createChild(IWorkbenchConstants.TAG_EDITOR);
                idMemento.putString(IWorkbenchConstants.TAG_ID, editorArray[i]
                        .getId());
            }
            editorArray = type.getDeletedEditors();
            for (int i = 0; i < editorArray.length; i++) {
                EditorDescriptor editor = (EditorDescriptor) editorArray[i];
                if (!editors.contains(editor)) {
                    editors.add(editor);
                }
                IMemento idMemento = editorMemento
                        .createChild(IWorkbenchConstants.TAG_DELETED_EDITOR);
                idMemento.putString(IWorkbenchConstants.TAG_ID, editorArray[i]
                        .getId());
            }
            editorArray = type.getDeclaredDefaultEditors();
            for (int i = 0; i < editorArray.length; i++) {
                EditorDescriptor editor = (EditorDescriptor) editorArray[i];
                if (!editors.contains(editor)) {
                    editors.add(editor);
                }
                IMemento idMemento = editorMemento
                        .createChild(IWorkbenchConstants.TAG_DEFAULT_EDITOR);
                idMemento.putString(IWorkbenchConstants.TAG_ID, editorArray[i]
                        .getId());
            }
        }
        Writer writer = null;
        try {
            writer = new StringWriter();
            memento.save(writer);
            writer.close();
            store.setValue(IPreferenceConstants.RESOURCES, writer.toString());
        } catch (IOException e) {
            try {
                if (writer != null) {
					writer.close();
				}
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MessageDialog.openError((Shell) null, "Saving Problems", //$NON-NLS-1$
                    "Unable to save resource associations."); //$NON-NLS-1$
            return;
        }

        memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_EDITORS);
        Iterator itr = editors.iterator();
        while (itr.hasNext()) {
            EditorDescriptor editor = (EditorDescriptor) itr.next();
            IMemento editorMemento = memento
                    .createChild(IWorkbenchConstants.TAG_DESCRIPTOR);
            editor.saveValues(editorMemento);
        }
        writer = null;
        try {
            writer = new StringWriter();
            memento.save(writer);
            writer.close();
            store.setValue(IPreferenceConstants.EDITORS, writer.toString());
        } catch (IOException e) {
            try {
                if (writer != null) {
					writer.close();
				}
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            MessageDialog.openError((Shell) null,
                    "Error", "Unable to save resource associations."); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }
    }

    public void setFileEditorMappings(FileEditorMapping[] newResourceTypes) {
        typeEditorMappings = new EditorMap();
        for (int i = 0; i < newResourceTypes.length; i++) {
            FileEditorMapping mapping = newResourceTypes[i];
            typeEditorMappings.put(mappingKeyFor(mapping), mapping);
        }
        extensionImages = new HashMap();
        rebuildEditorMap();
        firePropertyChange(PROP_CONTENTS);
    }

    @Override
	public void setDefaultEditor(String fileName, String editorId) {
        EditorDescriptor desc = (EditorDescriptor) findEditor(editorId);
        FileEditorMapping[] mapping = getMappingForFilename(fileName);
        if (mapping[0] != null) {
			mapping[0].setDefaultEditor(desc);
		}
        if (mapping[1] != null) {
			mapping[1].setDefaultEditor(desc);
		}
    }

    private Object[] sortEditors(List unsortedList) {
        Object[] array = new Object[unsortedList.size()];
        unsortedList.toArray(array);

        Collections.sort(Arrays.asList(array), comparer);
        return array;
    }

    private void sortInternalEditors() {
        Object[] array = sortEditors(sortedEditorsFromPlugins);
        sortedEditorsFromPlugins = new ArrayList();
        for (int i = 0; i < array.length; i++) {
            sortedEditorsFromPlugins.add(array[i]);
        }
    }

    private static class EditorMap {
        HashMap defaultMap = new HashMap();

        HashMap map = new HashMap();

        public void putDefault(String key, FileEditorMapping value) {
            defaultMap.put(key, value);
        }

        public void put(String key, FileEditorMapping value) {
            Object result = defaultMap.get(key);
            if (value.equals(result)) {
				map.remove(key);
			} else {
				map.put(key, value);
			}
        }

        public FileEditorMapping get(String key) {
            Object result = map.get(key);
            if (result == null) {
				result = defaultMap.get(key);
			}
            return (FileEditorMapping) result;
        }

        public FileEditorMapping[] allMappings() {
            HashMap merge = (HashMap) defaultMap.clone();
            merge.putAll(map);
            Collection values = merge.values();
            FileEditorMapping result[] = new FileEditorMapping[values.size()];
            return (FileEditorMapping[]) values.toArray(result);
        }

        public FileEditorMapping[] userMappings() {
            Collection values = map.values();
            FileEditorMapping result[] = new FileEditorMapping[values.size()];
            return (FileEditorMapping[]) values.toArray(result);
        }
    }

    @Override
	public boolean isSystemInPlaceEditorAvailable(String filename) {
        return ComponentSupport.inPlaceEditorAvailable(filename);
    }

    @Override
	public boolean isSystemExternalEditorAvailable(String filename) {
        int nDot = filename.lastIndexOf('.');
        if (nDot >= 0) {
            String strName = filename.substring(nDot);
            return Program.findProgram(strName) != null;
        }
        return false;
    }

    @Override
	public ImageDescriptor getSystemExternalEditorImageDescriptor(
            String filename) {
        Program externalProgram = null;
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex >= 0) {
            externalProgram = Program.findProgram(filename
                    .substring(extensionIndex));
        }
        if (externalProgram == null) {
            return null;
        } 
        
        return new ExternalProgramImageDescriptor(externalProgram);
    }
    
    private void removeEditorFromMapping(HashMap map, IEditorDescriptor desc) {
        Iterator iter = map.values().iterator();
        FileEditorMapping mapping;
        IEditorDescriptor[] editors;
        while (iter.hasNext()) {
            mapping = (FileEditorMapping) iter.next();
            editors = mapping.getUnfilteredEditors();
            for (int i = 0; i < editors.length; i++) {
				if (editors[i] == desc) {
                    mapping.removeEditor((EditorDescriptor) editors[i]);
                    break;
                }
			}
            if (editors.length <= 0) {
                map.remove(mapping);
                break;
            }
        }
    }

	
    @Override
	public void removeExtension(IExtension source, Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof EditorDescriptor) {
                EditorDescriptor desc = (EditorDescriptor) objects[i];

                sortedEditorsFromPlugins.remove(desc);
                mapIDtoEditor.values().remove(desc);
                removeEditorFromMapping(typeEditorMappings.defaultMap, desc);
                removeEditorFromMapping(typeEditorMappings.map, desc);
                removeEditorFromContentTypeMappings(contentTypeToEditorMappings, desc);
            }

        }
    }

	private void removeEditorFromContentTypeMappings(Map map, IEditorDescriptor desc) {
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Entry entry = (Entry) iter.next();
			IEditorDescriptor[] descriptors = (IEditorDescriptor[]) entry.getValue();
			IEditorDescriptor[] newDescriptors = removeDescriptor(descriptors, desc);
			if (descriptors != newDescriptors) {
				if (newDescriptors == null) {
					iter.remove();
				} else {
					entry.setValue(newDescriptors);
				}
			}
		}
	}

	private IEditorDescriptor[] removeDescriptor(IEditorDescriptor[] descriptors, IEditorDescriptor desc) {
		for (int i = 0; i < descriptors.length; i++) {
			if (descriptors[i] == desc) {
				if (descriptors.length == 1) {
					return null;
				}
				
				IEditorDescriptor[] newDescriptors = new IEditorDescriptor[descriptors.length - 1];
				if (i == 0) {
					System.arraycopy(descriptors, 1, newDescriptors, 0, newDescriptors.length);
				} else {
					System.arraycopy(descriptors, 0, newDescriptors, 0, i);
					if (i < newDescriptors.length) {
						System.arraycopy(descriptors, i + 1, newDescriptors, i, newDescriptors.length - i);
					}
				}
				return newDescriptors;
			}
		}

		return descriptors;
	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
        EditorRegistryReader eReader = new EditorRegistryReader();
        IConfigurationElement[] elements = extension.getConfigurationElements();
        for (int i = 0; i < elements.length; i++) {
            String id = elements[i].getAttribute(IWorkbenchConstants.TAG_ID);
            if (id != null && findEditor(id) != null) {
				continue;
			}
            eReader.readElement(this, elements[i]);
        }
	}

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_EDITOR);
	}

	@Override
	public IEditorDescriptor getDefaultEditor(String fileName, IContentType contentType) {
        return getEditorForContentType(fileName, contentType);
	}

	private IEditorDescriptor getEditorForContentType(String filename,
			IContentType contentType) {
		IEditorDescriptor desc = null;
		Object[] contentTypeResults = findRelatedObjects(contentType, filename, relatedRegistry);
		if (contentTypeResults != null && contentTypeResults.length > 0) {
			desc = (IEditorDescriptor) contentTypeResults[0];
		}
		return desc;
	}

	@Override
	public IEditorDescriptor[] getEditors(String fileName, IContentType contentType) {
		return findRelatedObjects(contentType, fileName, relatedRegistry);
	}

	@Override
	public ImageDescriptor getImageDescriptor(String filename, IContentType contentType) {
        if (filename == null) {
			return getDefaultImage();
		}

		if (contentType != null) {
			IEditorDescriptor desc = getEditorForContentType(filename, contentType);
			if (desc != null) {
				ImageDescriptor anImage = (ImageDescriptor) extensionImages.get(desc);	
				if (anImage != null) {
					return anImage;
				}
				anImage = desc.getImageDescriptor();
				extensionImages.put(desc, anImage);
				return anImage;				
			}
		}
        String key = mappingKeyFor(filename);
        ImageDescriptor anImage = (ImageDescriptor) extensionImages.get(key);
        if (anImage != null) {
			return anImage;
		}

        FileEditorMapping[] mapping = getMappingForFilename(filename);
        for (int i = 0; i < 2; i++) {
            if (mapping[i] != null) {
                String mappingKey = mappingKeyFor(mapping[i]);
                ImageDescriptor mappingImage = (ImageDescriptor) extensionImages
                        .get(key);
                if (mappingImage != null) {
					return mappingImage;
				}
                IEditorDescriptor editor = mapping[i].getDefaultEditor();
                if (editor != null) {
                    mappingImage = editor.getImageDescriptor();
                    extensionImages.put(mappingKey, mappingImage);
                    return mappingImage;
                }
            }
        }

        anImage = getSystemExternalEditorImageDescriptor(filename);
        if (anImage == null) {
			anImage = getDefaultImage();
		}
        return anImage;

	}
    
	private IEditorDescriptor [] findRelatedObjects(IContentType type, String fileName,
			RelatedRegistry registry) {
		List allRelated = new ArrayList();
		List nonDefaultFileEditors = new ArrayList();
		IEditorDescriptor [] related;
		
		if (fileName != null) {
			FileEditorMapping mapping = getMappingFor(fileName);
			if (mapping != null) {
				related = mapping.getDeclaredDefaultEditors();
				for (int i = 0; i < related.length; i++) {
					if (!allRelated.contains(related[i])) {
						if (!WorkbenchActivityHelper.filterItem(related[i])) {
							allRelated.add(related[i]);
						}
					}
				}
				
				nonDefaultFileEditors.addAll(Arrays.asList(mapping.getEditors()));
			}
			
			int index = fileName.lastIndexOf('.');
			if (index > -1) {
				String extension = "*" + fileName.substring(index); //$NON-NLS-1$
				mapping = getMappingFor(extension);
				if (mapping != null) {
					related = mapping.getDeclaredDefaultEditors();
					for (int i = 0; i < related.length; i++) {
						if (!allRelated.contains(related[i])) {
							if (!WorkbenchActivityHelper.filterItem(related[i])) {
								allRelated.add(related[i]);
							}
						}
					}
					nonDefaultFileEditors.addAll(Arrays.asList(mapping.getEditors()));
				}
			}
		}
		
		if (type != null) {
			related = registry.getRelatedObjects(type);
			for (int i = 0; i < related.length; i++) {
				if (!allRelated.contains(related[i])) {
					if (!WorkbenchActivityHelper.filterItem(related[i])) {
						allRelated.add(related[i]);
					}
				}
			}

		}

		if (type != null) {
			while ((type = type.getBaseType()) != null) {
				related = registry.getRelatedObjects(type);
				for (int i = 0; i < related.length; i++) {
					if (!allRelated.contains(related[i])) {
						if (!WorkbenchActivityHelper.filterItem(related[i])) {
							allRelated.add(related[i]);
						}
					}
				}
			}
		}
			
		for (Iterator i = nonDefaultFileEditors.iterator(); i.hasNext();) {
			IEditorDescriptor editor = (IEditorDescriptor) i.next();
			if (!allRelated.contains(editor) && !WorkbenchActivityHelper.filterItem(editor)) {
				allRelated.add(editor);
			}
		}
		
		return (IEditorDescriptor []) allRelated.toArray(new IEditorDescriptor [allRelated
				.size()]);
	}

	public IEditorDescriptor [] getEditorsForContentType(IContentType type) {
		ArrayList allRelated = new ArrayList();
		if (type == null) {
			return new IEditorDescriptor [0];
		}
		
		Object [] related = relatedRegistry.getRelatedObjects(type);
		for (int i = 0; i < related.length; i++) {	
			if (!allRelated.contains(related[i])) {
				if (!WorkbenchActivityHelper.filterItem(related[i])) {
					allRelated.add(related[i]);
				}
				
			}
		}
		
		while ((type = type.getBaseType()) != null) {
			related = relatedRegistry.getRelatedObjects(type);
			for (int i = 0; i < related.length; i++) {
				if (!allRelated.contains(related[i])) {
					if (!WorkbenchActivityHelper.filterItem(related[i])) {
						allRelated.add(related[i]);
					}
				}
			}
		}
		
		return (IEditorDescriptor[]) allRelated.toArray(new IEditorDescriptor[allRelated.size()]);
	}
	
	public IFileEditorMapping [] getUnifiedMappings() {
        IFileEditorMapping[] standardMappings = PlatformUI.getWorkbench()
                .getEditorRegistry().getFileEditorMappings();
        
        List allMappings = new ArrayList(Arrays.asList(standardMappings));
        IContentType [] contentTypes = Platform.getContentTypeManager().getAllContentTypes();
        for (int i = 0; i < contentTypes.length; i++) {
			IContentType type = contentTypes[i];
			String [] extensions = type.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			for (int j = 0; j < extensions.length; j++) {
				String extension = extensions[j];
				boolean found = false;
				for (Iterator k = allMappings.iterator(); k.hasNext();) {
					IFileEditorMapping mapping = (IFileEditorMapping) k.next();
					if ("*".equals(mapping.getName()) && extension.equals(mapping.getExtension())) { //$NON-NLS-1$
						found = true;
						break;
					}
				}
				if (!found) {
					MockMapping mockMapping = new MockMapping(type, "*", extension); //$NON-NLS-1$
					allMappings.add(mockMapping);
				}
			}
		
			String [] filenames = type.getFileSpecs(IContentType.FILE_NAME_SPEC);
			for (int j = 0; j < filenames.length; j++) {
				String wholename = filenames[j];
				int idx = wholename.indexOf('.');				
				String name = idx == -1 ? wholename : wholename.substring(0, idx);
				String extension = idx == -1 ? "" : wholename.substring(idx + 1); //$NON-NLS-1$
				
				boolean found = false;
				for (Iterator k = allMappings.iterator(); k.hasNext();) {
					IFileEditorMapping mapping = (IFileEditorMapping) k.next();
					if (name.equals(mapping.getName()) && extension.equals(mapping.getExtension())) {
						found = true;
						break;
					}
				}
				if (!found) {
					MockMapping mockMapping = new MockMapping(type, name, extension);
					allMappings.add(mockMapping);
				}
			}
		}
        
        return (IFileEditorMapping []) allMappings
				.toArray(new IFileEditorMapping [allMappings.size()]);
	}
	
}


class MockMapping implements IFileEditorMapping {

	private IContentType contentType;
	private String extension;
	private String filename;
	
	MockMapping(IContentType type, String name, String ext) {
		this.contentType = type;
		this.filename = name;
		this.extension = ext;
	}

	@Override
	public IEditorDescriptor getDefaultEditor() {
		IEditorDescriptor[] candidates = ((EditorRegistry) PlatformUI
				.getWorkbench().getEditorRegistry())
				.getEditorsForContentType(contentType);
		if (candidates.length == 0
				|| WorkbenchActivityHelper.restrictUseOf(candidates[0])) {
			return null;
		}
		return candidates[0];
	}

	@Override
	public IEditorDescriptor[] getEditors() {
		IEditorDescriptor[] editorsForContentType = ((EditorRegistry) PlatformUI
				.getWorkbench().getEditorRegistry())
				.getEditorsForContentType(contentType);
		return (IEditorDescriptor[]) WorkbenchActivityHelper
				.restrictArray(editorsForContentType);
	}

	@Override
	public IEditorDescriptor[] getDeletedEditors() {
		return new IEditorDescriptor[0];
	}

	@Override
	public String getExtension() {
		return extension;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		IEditorDescriptor editor = getDefaultEditor();
		if (editor == null) {
			return WorkbenchImages
					.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}

		return editor.getImageDescriptor();
	}

	@Override
	public String getLabel() {
		return filename + '.' + extension; 
	}

	@Override
	public String getName() {
		return filename;
    }	

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MockMapping)) {
			return false;
		}

		MockMapping mapping = (MockMapping) obj;
		if (!this.filename.equals(mapping.filename)) {
			return false;
		}

		if (!this.extension.equals(mapping.extension)) {
			return false;
		}

		if (!Util.equals(this.getEditors(), mapping.getEditors())) {
			return false;
		}
		return Util.equals(this.getDeletedEditors(), mapping
				.getDeletedEditors());
	}
}

