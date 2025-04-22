/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jan-Hendrik Diederich, Bredex GmbH - bug 201052
 *******************************************************************************/
package org.eclipse.ui.internal.registry;

import com.ibm.icu.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.WizardCollectionElement;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;

/**
 *  Instances access the registry that is provided at creation time
 *  in order to determine the contained Wizards
 */
public class WizardsRegistryReader extends RegistryReader {

	private String pluginPoint;

    private WizardCollectionElement wizardElements = null;

    private ArrayList deferWizards = null;

    private ArrayList deferCategories = null;

    private Set deferPrimary;

    /**
     * Examples wizard category id
     */
    /**
     * Other wizard category id
     */
    /**
     * General wizard category id
     */

    final private static String UNCATEGORIZED_WIZARD_CATEGORY_LABEL = WorkbenchMessages.NewWizardsRegistryReader_otherCategory;


    private WorkbenchWizardElement[] primaryWizards = new WorkbenchWizardElement[0];

    private class CategoryNode {
        private Category category;

        private String path;

        CategoryNode(Category cat) {
            category = cat;
            String[] categoryPath = category.getParentPath();
            if (categoryPath != null) {
                for (String element : categoryPath) {
                    path += element + '/';
                }
            }
            path += cat.getId();
        }

        String getPath() {
            return path;
        }

        Category getCategory() {
            return category;
        }
    }

    private static final Comparator comparer = new Comparator() {
        private Collator collator = Collator.getInstance();

        @Override
		public int compare(Object arg0, Object arg1) {
            String s1 = ((CategoryNode) arg0).getPath();
            String s2 = ((CategoryNode) arg1).getPath();
            return collator.compare(s1, s2);
        }
    };

	private boolean readAll = true;

	private String plugin;

    /**
     *Create an instance of this class.
     *
     * @param pluginId the plugin id
     * @param pluginPointId java.lang.String
     */
    public WizardsRegistryReader(String pluginId, String pluginPointId) {
        pluginPoint = pluginPointId;
        plugin = pluginId;
    }

	/*
	 * <p> This implementation uses a defering strategy. For more info see
	 * <code>readWizards</code>. </p>
	 */
    protected void addNewElementToResult(WorkbenchWizardElement element,
            IConfigurationElement config) {
        deferWizard(element);
    }

    /**
     *
     * @param parent
     * @param element
     * @since 3.1
     */
    private WizardCollectionElement createCollectionElement(WizardCollectionElement parent, IConfigurationElement element) {
        WizardCollectionElement newElement = new WizardCollectionElement(
				element, parent);

        parent.add(newElement);
        return newElement;
	}
    /**
     *	Create and answer a new WizardCollectionElement, configured as a
     *	child of <code>parent</code>
     *
     *	@return org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param parent org.eclipse.ui.internal.model.WizardCollectionElement
     *  @param id the id of the new collection
     *  @param pluginId the originating plugin id of the collection, if any. <code>null</code> otherwise.
     *	@param label java.lang.String
     */
    protected WizardCollectionElement createCollectionElement(
            WizardCollectionElement parent, String id, String pluginId,
            String label) {
        WizardCollectionElement newElement = new WizardCollectionElement(id,
                pluginId, label, parent);

        parent.add(newElement);
        return newElement;
    }

    /**
     * Creates empty element collection. Overrider to fill
     * initial elements, if needed.
     */
    protected void createEmptyWizardCollection() {
        wizardElements = new WizardCollectionElement("root", null, "root", null);//$NON-NLS-2$//$NON-NLS-1$
    }

    /**
     * Set the initial wizard set for supplemental reading via dynamic plugin loading.
     *
     * @param wizards the wizards
     * @since 3.1
     */
    public void setInitialCollection(WizardCollectionElement wizards) {
    	wizardElements = wizards;
    	readAll = false;
    }

    /**
     * Stores a category element for deferred addition.
     */
    private void deferCategory(IConfigurationElement config) {
        Category category = null;
        try {
            category = new Category(config);
        } catch (CoreException e) {
            WorkbenchPlugin.log("Cannot create category: ", e.getStatus());//$NON-NLS-1$
            return;
        }

        if (deferCategories == null) {
			deferCategories = new ArrayList(20);
		}
        deferCategories.add(category);
    }


    /**
     * Stores a wizard element for deferred addition.
     */
    private void deferWizard(WorkbenchWizardElement element) {
        if (deferWizards == null) {
			deferWizards = new ArrayList(50);
		}
        deferWizards.add(element);
    }

    /**
     * Finishes the addition of categories.  The categories are sorted and
     * added in a root to depth traversal.
     */
    private void finishCategories() {
        if (deferCategories == null) {
			return;
		}

        CategoryNode[] flatArray = new CategoryNode[deferCategories.size()];
        for (int i = 0; i < deferCategories.size(); i++) {
            flatArray[i] = new CategoryNode((Category) deferCategories.get(i));
        }
        Collections.sort(Arrays.asList(flatArray), comparer);

        for (CategoryNode element : flatArray) {
            Category cat = element.getCategory();
            finishCategory(cat);
        }

        deferCategories = null;
    }

    /**
     * Save new category definition.
     */
    private void finishCategory(Category category) {
        String[] categoryPath = category.getParentPath();

        if (categoryPath != null) {
            for (String element : categoryPath) {
                WizardCollectionElement tempElement = getChildWithID(parent,
                        element);
                if (tempElement == null) {
                    return;
                }
                parent = tempElement;
            }
        }

        Object test = getChildWithID(parent, category.getId());
        if (test != null) {
			return;
		}

        if (parent != null) {
			createCollectionElement(parent, Adapters.adapt(category, IConfigurationElement.class));
		}
    }


	/**
     * Finishes the recognition of primary wizards.
     */
    private void finishPrimary() {
        if (deferPrimary != null) {
            ArrayList primary = new ArrayList();
            for (Iterator i = deferPrimary.iterator(); i.hasNext();) {
                String id = (String) i.next();
                WorkbenchWizardElement element = wizardElements == null ? null : wizardElements.findWizard(id, true);
                if (element != null) {
                    primary.add(element);
                }
            }

            primaryWizards = (WorkbenchWizardElement[]) primary
                    .toArray(new WorkbenchWizardElement[primary.size()]);

            deferPrimary = null;
        }
    }


    /**
     *	Insert the passed wizard element into the wizard collection appropriately
     *	based upon its defining extension's CATEGORY tag value
     *
     *	@param element WorkbenchWizardElement
     *	@param config configuration element
     */
    private void finishWizard(WorkbenchWizardElement element,
            IConfigurationElement config) {
        StringTokenizer familyTokenizer = new StringTokenizer(
                getCategoryStringFor(config), CATEGORY_SEPARATOR);

        boolean moveToOther = false;

        while (familyTokenizer.hasMoreElements()) {
            WizardCollectionElement tempCollectionElement = getChildWithID(
                    currentCollectionElement, familyTokenizer.nextToken());

                moveToOther = true;
                break;
            }
            currentCollectionElement = tempCollectionElement;
        }

        if (moveToOther) {
			moveElementToUncategorizedCategory(wizardElements, element);
		} else {
            currentCollectionElement.add(element);
            element.setParent(currentCollectionElement);
        }
    }

    /**
     * Finishes the addition of wizards.  The wizards are processed and categorized.
     */
    private void finishWizards() {
        if (deferWizards != null) {
            Iterator iter = deferWizards.iterator();
            while (iter.hasNext()) {
                WorkbenchWizardElement wizard = (WorkbenchWizardElement) iter
                        .next();
                IConfigurationElement config = wizard.getConfigurationElement();
                finishWizard(wizard, config);
            }
            deferWizards = null;
        }
    }

    /**
     *	Return the appropriate category (tree location) for this Wizard.
     *	If a category is not specified then return a default one.
     */
    protected String getCategoryStringFor(IConfigurationElement config) {
        String result = config.getAttribute(IWorkbenchRegistryConstants.TAG_CATEGORY);
        if (result == null) {
			result = UNCATEGORIZED_WIZARD_CATEGORY;
		}

        return result;
    }

    /**
     *	Go through the children of  the passed parent and answer the child
     *	with the passed name.  If no such child is found then return null.
     *
     *	@return org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param parent org.eclipse.ui.internal.model.WizardCollectionElement
     *	@param id java.lang.String
     */
    protected WizardCollectionElement getChildWithID(
            WizardCollectionElement parent, String id) {
        Object[] children = parent.getChildren(null);
        for (Object element : children) {
            WizardCollectionElement currentChild = (WizardCollectionElement) element;
            if (currentChild.getId().equals(id)) {
				return currentChild;
			}
        }
        return null;
    }

    /**
     *	Moves given element to "Other" category, previously creating one if missing.
     */
    protected void moveElementToUncategorizedCategory(
            WizardCollectionElement root, WorkbenchWizardElement element) {
        WizardCollectionElement otherCategory = getChildWithID(root,
                UNCATEGORIZED_WIZARD_CATEGORY);

        if (otherCategory == null) {
			otherCategory = createCollectionElement(root,
                    UNCATEGORIZED_WIZARD_CATEGORY, null,
                    UNCATEGORIZED_WIZARD_CATEGORY_LABEL);
		}

        otherCategory.add(element);
        element.setParent(otherCategory);
    }

    /**
     * Removes the empty categories from a wizard collection.
     */
    private void pruneEmptyCategories(WizardCollectionElement parent) {
        Object[] children = parent.getChildren(null);
        for (Object element : children) {
            WizardCollectionElement child = (WizardCollectionElement) element;
            pruneEmptyCategories(child);
            boolean shouldPrune = child.getId().equals(FULL_EXAMPLES_WIZARD_CATEGORY);
            if (child.isEmpty() && shouldPrune) {
				parent.remove(child);
			}
        }
    }

    /**
     * Implement this method to read element attributes.
     */
    @Override
	public boolean readElement(IConfigurationElement element) {
        if (element.getName().equals(IWorkbenchRegistryConstants.TAG_CATEGORY)) {
            deferCategory(element);
            return true;
        } else if (element.getName().equals(IWorkbenchRegistryConstants.TAG_PRIMARYWIZARD)) {
            if (deferPrimary == null) {
				deferPrimary = new HashSet();
			}
            deferPrimary.add(element.getAttribute(IWorkbenchRegistryConstants.ATT_ID));

            return true;
        } else {
            if (!element.getName().equals(IWorkbenchRegistryConstants.TAG_WIZARD)) {
				return false;
			}
            WorkbenchWizardElement wizard = createWizardElement(element);
            if (wizard != null) {
				addNewElementToResult(wizard, element);
			}
            return true;
        }
    }

    /**
     * Reads the wizards in a registry.
     * <p>
     * This implementation uses a defering strategy.  All of the elements
     * (categories, wizards) are read.  The categories are created as the read occurs.
     * The wizards are just stored for later addition after the read completes.
     * This ensures that wizard categorization is performed after all categories
     * have been read.
     * </p>
     */
    protected void readWizards() {
    	if (readAll) {
    	       if (!areWizardsRead()) {
                createEmptyWizardCollection();
                IExtensionRegistry registry = Platform.getExtensionRegistry();
                readRegistry(registry, plugin, pluginPoint);
            }
    	}
        finishCategories();
        finishWizards();
        finishPrimary();
        if (wizardElements != null) {
            pruneEmptyCategories(wizardElements);
        }
    }

    /**
     * Returns the list of wizards that are considered 'primary'.
     *
     * The return value for this method is cached since computing its value
     * requires non-trivial work.
     *
     * @return the primary wizards
     */
    public WorkbenchWizardElement [] getPrimaryWizards() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return (WorkbenchWizardElement[]) WorkbenchActivityHelper.restrictArray(primaryWizards);
    }


    /**
     * Returns whether the wizards have been read already
     */
    protected boolean areWizardsRead() {
        return wizardElements != null && readAll;
    }

    /**
     * Returns a list of wizards, project and not.
     *
     * The return value for this method is cached since computing its value
     * requires non-trivial work.
     *
     * @return the wizard collection
     */
    public WizardCollectionElement getWizardElements() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return wizardElements;
    }

    protected Object[] getWizardCollectionElements() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return wizardElements.getChildren();
    }

    /**
     * Returns a new WorkbenchWizardElement configured according to the parameters
     * contained in the passed Registry.
     *
     * May answer null if there was not enough information in the Extension to create
     * an adequate wizard
     */
    protected WorkbenchWizardElement createWizardElement(
            IConfigurationElement element) {
        if (element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_NAME);
            return null;
        }

        if (getClassValue(element, IWorkbenchRegistryConstants.ATT_CLASS) == null) {
            logMissingAttribute(element, IWorkbenchRegistryConstants.ATT_CLASS);
            return null;
        }
        return new WorkbenchWizardElement(element);
    }

    /**
     * Returns the first wizard with a given id.
     *
     * @param id wizard id to search for
     * @return WorkbenchWizardElement matching the given id, if found; null otherwise
     */
    public WorkbenchWizardElement findWizard(String id) {
        Object[] wizards = getWizardCollectionElements();
        for (Object wizard : wizards) {
            WizardCollectionElement collection = (WizardCollectionElement) wizard;
            WorkbenchWizardElement element = collection.findWizard(id, true);
            if (element != null && !WorkbenchActivityHelper.restrictUseOf(element)) {
				return element;
			}
        }
        return null;
    }
}
