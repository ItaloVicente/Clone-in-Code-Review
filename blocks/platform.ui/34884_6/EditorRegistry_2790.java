package org.eclipse.ui.internal.registry;

import java.io.File;
import java.io.Serializable;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.internal.IWorkbenchConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.ProgramImageDescriptor;
import org.eclipse.ui.internal.tweaklets.InterceptContributions;
import org.eclipse.ui.internal.tweaklets.Tweaklets;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public final class EditorDescriptor implements IEditorDescriptor, Serializable,
        IPluginContribution {

    private static final long serialVersionUID = 3905241225668998961L;

    public static final int OPEN_INTERNAL = 0x01;

    public static final int OPEN_INPLACE = 0x02;

    public static final int OPEN_EXTERNAL = 0x04;

    private String editorName;

    private String imageFilename;

    private transient ImageDescriptor imageDesc;
    private transient Object imageDescLock = new Object();

    private boolean testImage = true;

    private String className;

    private String launcherName;

    private String fileName;

    private String id = Util.ZERO_LENGTH_STRING;

    private boolean matchingStrategyChecked = false;
    private IEditorMatchingStrategy matchingStrategy;

    private Program program;

    private String pluginIdentifier;

    private int openMode = 0;

    private transient IConfigurationElement configurationElement;

        setID(id2);
        setConfigurationElement(element);
    }

    

		super();
	}



    public static EditorDescriptor createForProgram(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        EditorDescriptor editor = new EditorDescriptor();

        editor.setFileName(filename);
        editor.setID(filename);
        editor.setOpenMode(OPEN_EXTERNAL);

        int start = filename.lastIndexOf(File.separator);
        String name;
        if (start != -1) {
            name = filename.substring(start + 1);
        } else {
            name = filename;
        }
        int end = name.lastIndexOf('.');
        if (end != -1) {
            name = name.substring(0, end);
        }
        editor.setName(name);

        ImageDescriptor imageDescriptor = new ProgramImageDescriptor(filename,
                0);
        editor.setImageDescriptor(imageDescriptor);

        return editor;
    }

    private static Program findProgram(String programName) {

        Program[] programs = Program.getPrograms();
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].getName().equals(programName)) {
				return programs[i];
			}
        }

        return null;
    }

    public IEditorActionBarContributor createActionBarContributor() {
        if (configurationElement == null) {
            return null;
        }

        String className = configurationElement
                .getAttribute(IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
        if (className == null) {
			return null;
		}

        IEditorActionBarContributor contributor = null;
        try {
            contributor = (IEditorActionBarContributor) WorkbenchPlugin
                    .createExtension(configurationElement,
                            IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
        } catch (CoreException e) {
            WorkbenchPlugin.log("Unable to create editor contributor: " + //$NON-NLS-1$
                    id, e.getStatus());
        }
        return contributor;
    }

    public String getClassName() {
    	if (configurationElement == null) {
    		return className;
    	}
    	return RegistryReader.getClassValue(configurationElement,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }
    
    public IEditorPart createEditor() throws CoreException {        
        Object extension = WorkbenchPlugin.createExtension(getConfigurationElement(), IWorkbenchRegistryConstants.ATT_CLASS);
        return ((InterceptContributions)Tweaklets.get(InterceptContributions.KEY)).tweakEditor(extension);
    }

    public String getFileName() {
        if (program == null) {
        	if (configurationElement == null) {
        		return fileName;
        	}
        	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND);
    	}
        return program.getName();
    }

    @Override
	public String getId() {        
        if (program == null) {
        	if (configurationElement == null) {
        		return Util.safeString(id);
        	}
        	return Util.safeString(configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID));
        	
        }
        return Util.safeString(program.getName());
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
    	ImageDescriptor tempDescriptor = null;

    	synchronized (imageDescLock) {
	    	if (!testImage)
	            return imageDesc;
	    	
			if (imageDesc == null) {
				String imageFileName = getImageFilename();
				String command = getFileName();
				if (imageFileName != null && configurationElement != null)
					tempDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(configurationElement.getNamespaceIdentifier(), imageFileName);
				else if (command != null)
					tempDescriptor = WorkbenchImages.getImageDescriptorFromProgram(command, 0);
			} else
				tempDescriptor = imageDesc;

			if (tempDescriptor == null) { // still null? return default image
				imageDesc = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
				testImage = false;
		        return imageDesc;
			}
    	}
    	
		Image img = tempDescriptor.createImage(false);
		if (img == null) // @issue what should be the default image?
			tempDescriptor = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		else
		    img.dispose();
		
		synchronized (imageDescLock) {
			if (!testImage)
				return imageDesc;
			imageDesc = tempDescriptor;
			testImage = false;
			return imageDesc;
		}
    }

    	synchronized (imageDescLock) {
	        imageDesc = desc;
	        testImage = true;
    	}
    }

    public String getImageFilename() {
    	if (configurationElement == null) {
			return imageFilename;
		}
    	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
    }

    @Override
	public String getLabel() {
        if (program == null) {
        	if (configurationElement == null) {
        		return editorName;        		
        	}
        	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
        }
        return program.getName();
    }

    public String getLauncher() {
    	if (configurationElement == null) {
			return launcherName;
		}
    	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_LAUNCHER);
    }

    public String getPluginID() {
    	if (configurationElement != null) {
			return configurationElement.getNamespace();
		}
    	return pluginIdentifier;
    }

    public Program getProgram() {
        return this.program;
    }

    @Override
	public boolean isInternal() {
        return getOpenMode() == OPEN_INTERNAL;
    }

    @Override
	public boolean isOpenInPlace() {
        return getOpenMode() == OPEN_INPLACE;
    }

    @Override
	public boolean isOpenExternal() {
        return getOpenMode() == OPEN_EXTERNAL;
    }

    protected boolean loadValues(IMemento memento) {
        editorName = memento.getString(IWorkbenchConstants.TAG_LABEL);
        imageFilename = memento.getString(IWorkbenchConstants.TAG_IMAGE);
        className = memento.getString(IWorkbenchConstants.TAG_CLASS);
        launcherName = memento.getString(IWorkbenchConstants.TAG_LAUNCHER);
        fileName = memento.getString(IWorkbenchConstants.TAG_FILE);
        id = Util.safeString(memento.getString(IWorkbenchConstants.TAG_ID));
        pluginIdentifier = memento.getString(IWorkbenchConstants.TAG_PLUGIN);

        Integer openModeInt = memento
                .getInteger(IWorkbenchConstants.TAG_OPEN_MODE);
        if (openModeInt != null) {
            openMode = openModeInt.intValue();
        } else {
            boolean internal = new Boolean(memento
                    .getString(IWorkbenchConstants.TAG_INTERNAL))
                    .booleanValue();
            boolean openInPlace = new Boolean(memento
                    .getString(IWorkbenchConstants.TAG_OPEN_IN_PLACE))
                    .booleanValue();
            if (internal) {
                openMode = OPEN_INTERNAL;
            } else {
                if (openInPlace) {
                    openMode = OPEN_INPLACE;
                } else {
                    openMode = OPEN_EXTERNAL;
                }
            }
        }
        if (openMode != OPEN_EXTERNAL && openMode != OPEN_INTERNAL
                && openMode != OPEN_INPLACE) {
            WorkbenchPlugin
                    .log("Ignoring editor descriptor with invalid openMode: " + this); //$NON-NLS-1$
            return false;
        }

        String programName = memento
                .getString(IWorkbenchConstants.TAG_PROGRAM_NAME);
        if (programName != null) {
            this.program = findProgram(programName);
        }
        return true;
    }

    protected void saveValues(IMemento memento) {
        memento.putString(IWorkbenchConstants.TAG_LABEL, getLabel());
        memento.putString(IWorkbenchConstants.TAG_IMAGE, getImageFilename());
        memento.putString(IWorkbenchConstants.TAG_CLASS, getClassName());
        memento.putString(IWorkbenchConstants.TAG_LAUNCHER, getLauncher());
        memento.putString(IWorkbenchConstants.TAG_FILE, getFileName());
        memento.putString(IWorkbenchConstants.TAG_ID, getId());
        memento.putString(IWorkbenchConstants.TAG_PLUGIN, getPluginId());

        memento.putInteger(IWorkbenchConstants.TAG_OPEN_MODE, getOpenMode());
        memento.putString(IWorkbenchConstants.TAG_INTERNAL, String
                .valueOf(isInternal()));
        memento.putString(IWorkbenchConstants.TAG_OPEN_IN_PLACE, String
                .valueOf(isOpenInPlace()));

        if (this.program != null) {
			memento.putString(IWorkbenchConstants.TAG_PROGRAM_NAME,
                    this.program.getName());
		}
    }

	private int getOpenMode() {
		if (configurationElement == null) { // if we've been serialized, return our serialized value
			return openMode;
		}
		else if (getLauncher() != null) {
        	return EditorDescriptor.OPEN_EXTERNAL;
        } else if (getFileName() != null) {
            return EditorDescriptor.OPEN_EXTERNAL;
        } else if (getPluginId() != null) {
        	return EditorDescriptor.OPEN_INTERNAL;
        }
        else {
        	return 0; // default for system editor
        }
	}

        className = newClassName;
    }

            IConfigurationElement newConfigurationElement) {
        configurationElement = newConfigurationElement;
    }

        fileName = aFileName;
    }

        Assert.isNotNull(anID);
        id = anID;
    }

        imageFilename = aFileName;
    }

        launcherName = newLauncher;
    }

        editorName = newName;
    }

    public void setOpenMode(int mode) {
        openMode = mode;
    }

        pluginIdentifier = anID;
    }


        this.program = newProgram;
        if (editorName == null) {
			setName(newProgram.getName());
		}
    }

    @Override
	public String toString() {
        return "EditorDescriptor(id=" + getId() + ", label=" + getLabel() + ")"; //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-1$
    }

    @Override
	public String getLocalId() {
        return getId();
    }

    @Override
	public String getPluginId() {
        return getPluginID();
    }

    @Override
	public IEditorMatchingStrategy getEditorMatchingStrategy() {
        if (matchingStrategy == null && !matchingStrategyChecked) {
            matchingStrategyChecked = true;
            if (program == null && configurationElement != null) {
                if (configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_MATCHING_STRATEGY) != null) {
                    try {
                        matchingStrategy = (IEditorMatchingStrategy) WorkbenchPlugin.createExtension(configurationElement, IWorkbenchRegistryConstants.ATT_MATCHING_STRATEGY);
                    } catch (CoreException e) {
                        WorkbenchPlugin.log("Error creating editor management policy for editor id " + getId(), e); //$NON-NLS-1$
                    }
                }
            }
        }
        return matchingStrategy;
    }
    
    
}
