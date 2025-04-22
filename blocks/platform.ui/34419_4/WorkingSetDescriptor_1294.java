package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.WizardCollectionElement;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.util.Util;

import com.ibm.icu.text.Collator;

public class WizardsRegistryReader extends RegistryReader {

	private String pluginPoint;
    
    private WizardCollectionElement wizardElements = null;

    private ArrayList deferWizards = null;

    private ArrayList deferCategories = null;

    private Set deferPrimary;

    public final static String FULL_EXAMPLES_WIZARD_CATEGORY = "org.eclipse.ui.Examples";//$NON-NLS-1$
    final public static String UNCATEGORIZED_WIZARD_CATEGORY = "org.eclipse.ui.Other";//$NON-NLS-1$
    final public static String GENERAL_WIZARD_CATEGORY = "org.eclipse.ui.Basic";	//$NON-NLS-1$

    final private static String UNCATEGORIZED_WIZARD_CATEGORY_LABEL = WorkbenchMessages.NewWizardsRegistryReader_otherCategory;
    
    private final static String CATEGORY_SEPARATOR = "/";//$NON-NLS-1$

    private WorkbenchWizardElement[] primaryWizards = new WorkbenchWizardElement[0];
    
    private class CategoryNode {
        private Category category;

        private String path;

        CategoryNode(Category cat) {
            category = cat;
            path = ""; //$NON-NLS-1$
            String[] categoryPath = category.getParentPath();
            if (categoryPath != null) {
                for (int nX = 0; nX < categoryPath.length; nX++) {
                    path += categoryPath[nX] + '/';
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

    public WizardsRegistryReader(String pluginId, String pluginPointId) {
        pluginPoint = pluginPointId;
        plugin = pluginId;
    }

    protected void addNewElementToResult(WorkbenchWizardElement element,
            IConfigurationElement config) {
        deferWizard(element);
    }

    private WizardCollectionElement createCollectionElement(WizardCollectionElement parent, IConfigurationElement element) {
        WizardCollectionElement newElement = new WizardCollectionElement(
				element, parent);

        parent.add(newElement);
        return newElement;		
	}
    protected WizardCollectionElement createCollectionElement(
            WizardCollectionElement parent, String id, String pluginId,
            String label) {
        WizardCollectionElement newElement = new WizardCollectionElement(id,
                pluginId, label, parent);

        parent.add(newElement);
        return newElement;
    }

    protected void createEmptyWizardCollection() {
        wizardElements = new WizardCollectionElement("root", null, "root", null);//$NON-NLS-2$//$NON-NLS-1$
    }
    
    public void setInitialCollection(WizardCollectionElement wizards) {
    	wizardElements = wizards;
    	readAll = false;
    }

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


    private void deferWizard(WorkbenchWizardElement element) {
        if (deferWizards == null) {
			deferWizards = new ArrayList(50);
		}
        deferWizards.add(element);
    }

    private void finishCategories() {
        if (deferCategories == null) {
			return;
		}

        CategoryNode[] flatArray = new CategoryNode[deferCategories.size()];
        for (int i = 0; i < deferCategories.size(); i++) {
            flatArray[i] = new CategoryNode((Category) deferCategories.get(i));
        }
        Collections.sort(Arrays.asList(flatArray), comparer);

        for (int nX = 0; nX < flatArray.length; nX++) {
            Category cat = flatArray[nX].getCategory();
            finishCategory(cat);
        }

        deferCategories = null;
    }

    private void finishCategory(Category category) {
        String[] categoryPath = category.getParentPath();
        WizardCollectionElement parent = wizardElements; // ie.- root

        if (categoryPath != null) {
            for (int i = 0; i < categoryPath.length; i++) {
                WizardCollectionElement tempElement = getChildWithID(parent,
                        categoryPath[i]);
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
			createCollectionElement(parent, (IConfigurationElement) Util.getAdapter(category,
					IConfigurationElement.class));
		}
    }


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


    private void finishWizard(WorkbenchWizardElement element,
            IConfigurationElement config) {
        StringTokenizer familyTokenizer = new StringTokenizer(
                getCategoryStringFor(config), CATEGORY_SEPARATOR);

        WizardCollectionElement currentCollectionElement = wizardElements; // ie.- root
        boolean moveToOther = false;

        while (familyTokenizer.hasMoreElements()) {
            WizardCollectionElement tempCollectionElement = getChildWithID(
                    currentCollectionElement, familyTokenizer.nextToken());

            if (tempCollectionElement == null) { // can't find the path; bump it to uncategorized
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

    protected String getCategoryStringFor(IConfigurationElement config) {
        String result = config.getAttribute(IWorkbenchRegistryConstants.TAG_CATEGORY);
        if (result == null) {
			result = UNCATEGORIZED_WIZARD_CATEGORY;
		}

        return result;
    }

    protected WizardCollectionElement getChildWithID(
            WizardCollectionElement parent, String id) {
        Object[] children = parent.getChildren(null);
        for (int i = 0; i < children.length; ++i) {
            WizardCollectionElement currentChild = (WizardCollectionElement) children[i];
            if (currentChild.getId().equals(id)) {
				return currentChild;
			}
        }
        return null;
    }

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

    private void pruneEmptyCategories(WizardCollectionElement parent) {
        Object[] children = parent.getChildren(null);
        for (int nX = 0; nX < children.length; nX++) {
            WizardCollectionElement child = (WizardCollectionElement) children[nX];
            pruneEmptyCategories(child);
            boolean shouldPrune = child.getId().equals(FULL_EXAMPLES_WIZARD_CATEGORY);
            if (child.isEmpty() && shouldPrune) {
				parent.remove(child);
			}
        }
    }

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

    public WorkbenchWizardElement [] getPrimaryWizards() {
        if (!areWizardsRead()) {
            readWizards();
        }
        return (WorkbenchWizardElement[]) WorkbenchActivityHelper.restrictArray(primaryWizards);
    }


    protected boolean areWizardsRead() {
        return wizardElements != null && readAll;
    }

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

    public WorkbenchWizardElement findWizard(String id) {
        Object[] wizards = getWizardCollectionElements();
        for (int nX = 0; nX < wizards.length; nX++) {
            WizardCollectionElement collection = (WizardCollectionElement) wizards[nX];
            WorkbenchWizardElement element = collection.findWizard(id, true);
            if (element != null && !WorkbenchActivityHelper.restrictUseOf(element)) {
				return element;
			}
        }
        return null;
    }
}
