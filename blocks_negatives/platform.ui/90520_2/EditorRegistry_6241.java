/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     James Blackburn - Bug 256316 getImageDescriptor() is not thread safe
 *******************************************************************************/
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

/**
 * @see IEditorDescriptor
 */
public final class EditorDescriptor implements IEditorDescriptor, Serializable,
        IPluginContribution {

    /**
     * Generated serial version UID for this class.
     * @since 3.1
     */
    private static final long serialVersionUID = 3905241225668998961L;

    /**
     * Open internal constant.  Value <code>0x01</code>.
     */
    public static final int OPEN_INTERNAL = 0x01;

    /**
     * Open in place constant.  Value <code>0x02</code>.
     */
    public static final int OPEN_INPLACE = 0x02;

    /**
     * Open external constant.  Value <code>0x04</code>.
     */
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

	/**
     * Create a new instance of an editor descriptor. Limited
     * to internal framework calls.
     * @param element
     * @param id2
     */
    /* package */EditorDescriptor(String id2, IConfigurationElement element) {
        setID(id2);
        setConfigurationElement(element);
    }



	/**
	 * Create a new instance of an editor descriptor. Limited
     * to internal framework calls.
	 */
    /* package */ EditorDescriptor() {
		super();
	}



	/**
     * Creates a descriptor for an external program.
     *
     * @param filename the external editor full path and filename
     * @return the editor descriptor
     */
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

    /**
     * Return the program called programName. Return null if it is not found.
     * @return org.eclipse.swt.program.Program
     */
    private static Program findProgram(String programName) {

        Program[] programs = Program.getPrograms();
        for (Program program2 : programs) {
            if (program2.getName().equals(programName)) {
				return program2;
			}
        }

        return null;
    }

    /**
     * Create the editor action bar contributor for editors of this type.
     *
     * @return the action bar contributor, or <code>null</code>
     */
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
                    id, e.getStatus());
        }
        return contributor;
    }

    /**
     * Return the editor class name.
     *
     * @return the class name
     */
    public String getClassName() {
    	if (configurationElement == null) {
    		return className;
    	}
    	return RegistryReader.getClassValue(configurationElement,
                IWorkbenchRegistryConstants.ATT_CLASS);
    }

    /**
     * Return the configuration element used to define this editor, or <code>null</code>.
     *
     * @return the element or null
     */
    public IConfigurationElement getConfigurationElement() {
        return configurationElement;
    }

    /**
     * Create an editor part based on this descriptor.
     *
     * @return the editor part
     * @throws CoreException thrown if there is an issue creating the editor
     */
    public IEditorPart createEditor() throws CoreException {
        Object extension = WorkbenchPlugin.createExtension(getConfigurationElement(), IWorkbenchRegistryConstants.ATT_CLASS);
        return ((InterceptContributions)Tweaklets.get(InterceptContributions.KEY)).tweakEditor(extension);
    }

    /**
     * Return the file name of the command to execute for this editor.
     *
     * @return the file name to execute
     */
    public String getFileName() {
        if (program == null) {
        	if (configurationElement == null) {
        		return fileName;
        	}
        	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND);
    	}
        return program.getName();
    }

    /**
     * Return the id for this editor.
     *
     * @return the id
     */
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

    /**
     * Return the image descriptor describing this editor.
     *
     * @return the image descriptor
     */
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

				imageDesc = WorkbenchImages.getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
				testImage = false;
		        return imageDesc;
			}
    	}

		Image img = tempDescriptor.createImage(false);
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

    /**
     * The Image to use to repesent this editor
     */
    /* package */void setImageDescriptor(ImageDescriptor desc) {
    	synchronized (imageDescLock) {
	        imageDesc = desc;
	        testImage = true;
    	}
    }

    /**
     * The name of the image describing this editor.
     *
     * @return the image file name
     */
    public String getImageFilename() {
    	if (configurationElement == null) {
			return imageFilename;
		}
    	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
    }

    /**
     * Return the user printable label for this editor.
     *
     * @return the label
     */
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

    /**
     * Returns the class name of the launcher.
     *
     * @return the launcher class name
     */
    public String getLauncher() {
    	if (configurationElement == null) {
			return launcherName;
		}
    	return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_LAUNCHER);
    }

    /**
     * Return the contributing plugin id.
     *
     * @return the contributing plugin id
     */
    public String getPluginID() {
    	if (configurationElement != null) {
			return configurationElement.getNamespace();
		}
    	return pluginIdentifier;
    }

    /**
     * Get the program for the receiver if there is one.
     * @return Program
     */
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

    /**
     * Load the object properties from a memento.
     *
     * @return <code>true</code> if the values are valid, <code>false</code> otherwise
     */
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
            boolean internal = Boolean.parseBoolean(memento
                    .getString(IWorkbenchConstants.TAG_INTERNAL));
            boolean openInPlace = Boolean.parseBoolean(memento
                    .getString(IWorkbenchConstants.TAG_OPEN_IN_PLACE));
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
            return false;
        }

        String programName = memento
                .getString(IWorkbenchConstants.TAG_PROGRAM_NAME);
        if (programName != null) {
            this.program = findProgram(programName);
        }
        return true;
    }

    /**
     * Save the object values in a IMemento
     */
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

    /**
     * Return the open mode of this editor.
     *
	 * @return the open mode of this editor
	 * @since 3.1
	 */
	private int getOpenMode() {
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
        }
	}

	/**
     * Set the class name of an internal editor.
     */
    /* package */void setClassName(String newClassName) {
        className = newClassName;
    }

    /**
     * Set the configuration element which contributed this editor.
     */
    /* package */void setConfigurationElement(
            IConfigurationElement newConfigurationElement) {
        configurationElement = newConfigurationElement;
    }

    /**
     * Set the filename of an external editor.
     */
    /* package */void setFileName(String aFileName) {
        fileName = aFileName;
    }

    /**
     * Set the id of the editor.
     * For internal editors this is the id as provided in the extension point
     * For external editors it is path and filename of the editor
     */
    /* package */void setID(String anID) {
        Assert.isNotNull(anID);
        id = anID;
    }

    /**
     * The name of the image to use for this editor.
     */
    /* package */void setImageFilename(String aFileName) {
        imageFilename = aFileName;
    }

    /**
     * Sets the new launcher class name
     *
     * @param newLauncher the new launcher
     */
    /* package */void setLauncher(String newLauncher) {
        launcherName = newLauncher;
    }

    /**
     * The label to show for this editor.
     */
    /* package */void setName(String newName) {
        editorName = newName;
    }

    /**
     * Sets the open mode of this editor descriptor.
     *
     * @param mode the open mode
     *
     * @issue this method is public as a temporary fix for bug 47600
     */
    public void setOpenMode(int mode) {
        openMode = mode;
    }

    /**
     * The id of the plugin which contributed this editor, null for external editors.
     */
    /* package */void setPluginIdentifier(String anID) {
        pluginIdentifier = anID;
    }

    /**
     * Set the receivers program.
     * @param newProgram
     */
    /* package */void setProgram(Program newProgram) {

        this.program = newProgram;
        if (editorName == null) {
			setName(newProgram.getName());
		}
    }

    /**
     * For debugging purposes only.
     */
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
