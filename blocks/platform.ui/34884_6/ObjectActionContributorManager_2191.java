package org.eclipse.ui.internal;

import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.SelectionEnabler;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class ObjectActionContributor extends PluginActionBuilder implements
        IObjectActionContributor, IAdaptable {    

    private static final String P_TRUE = "true"; //$NON-NLS-1$

    private IConfigurationElement config;

    private boolean configRead = false;

    private boolean adaptable = false;
    
    private String objectClass;

    public ObjectActionContributor(IConfigurationElement config) {
        this.config = config;
        this.adaptable = P_TRUE.equalsIgnoreCase(config
                .getAttribute(IWorkbenchRegistryConstants.ATT_ADAPTABLE));
        this.objectClass = config.getAttribute(IWorkbenchRegistryConstants.ATT_OBJECTCLASS);
    }

    @Override
	public boolean canAdapt() {
        return adaptable;
    }
    
	public String getObjectClass() {
		return objectClass;
	}

    @Override
	public void contributeObjectActionIdOverrides(List actionIdOverrides) {
        if (!configRead) {
			readConfigElement();
		}

        if (currentContribution.actions != null) {
            for (int i = 0; i < currentContribution.actions.size(); i++) {
                ActionDescriptor ad = (ActionDescriptor) currentContribution.actions
                        .get(i);
                String id = ad.getAction().getOverrideActionId();
                if (id != null) {
					actionIdOverrides.add(id);
				}
            }
        }
    }

    @Override
	public boolean contributeObjectActions(final IWorkbenchPart part,
            IMenuManager menu, ISelectionProvider selProv,
            List actionIdOverrides) {
        if (!configRead) {
			readConfigElement();
		}

        if (currentContribution.actions == null) {
			return false;
		}

        ISelection sel = selProv.getSelection();
        if ((sel == null) || !(sel instanceof IStructuredSelection)) {
			return false;
		}
        IStructuredSelection ssel = (IStructuredSelection) sel;
        
        if(canAdapt()) {        	
           IStructuredSelection newSelection = LegacyResourceSupport.adaptSelection(ssel, getObjectClass());     
           if(newSelection.size() != ssel.size()) {
        	   if (Policy.DEBUG_CONTRIBUTIONS) {
				WorkbenchPlugin.log("Error adapting selection to " + getObjectClass() +  //$NON-NLS-1$
            			". Contribution " + getID(config) + " is being ignored"); //$NON-NLS-1$ //$NON-NLS-2$            	
			}
            	return false;
           }
           ssel = newSelection;
        }
        
        final IStructuredSelection selection = ssel;
        	
        for (int i = 0; i < currentContribution.actions.size(); i++) {
            ActionDescriptor ad = (ActionDescriptor) currentContribution.actions
                    .get(i);
            if (!actionIdOverrides.contains(ad.getId())) {
                currentContribution.contributeMenuAction(ad, menu, true);
                if (ad.getAction() instanceof ObjectPluginAction) {
                    final ObjectPluginAction action = (ObjectPluginAction) ad
                            .getAction();
                    ISafeRunnable runnable = new ISafeRunnable() {
						@Override
						public void handleException(Throwable exception) {
							WorkbenchPlugin.log("Failed to update action "  //$NON-NLS-1$
									+ action.getId(), exception);
						}

						@Override
						public void run() throws Exception {
		                    action.setActivePart(part);
		                    action.selectionChanged(selection);
						}
                    };
                    SafeRunner.run(runnable);
                }
            }
        }
        return true;
    }

    @Override
	public boolean contributeObjectMenus(IMenuManager menu,
            ISelectionProvider selProv) {
        if (!configRead) {
			readConfigElement();
		}

        if (currentContribution.menus == null) {
			return false;
		}

        ISelection sel = selProv.getSelection();
        if ((sel == null) || !(sel instanceof IStructuredSelection)) {
			return false;
		}

        for (int i = 0; i < currentContribution.menus.size(); i++) {
            IConfigurationElement menuElement = (IConfigurationElement) currentContribution.menus
                    .get(i);
            currentContribution.contributeMenu(menuElement, menu, true);
        }
        return true;
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            IConfigurationElement element) {
        return new ActionDescriptor(element, ActionDescriptor.T_POPUP);
    }

    @Override
	protected BasicContribution createContribution() {
        return new ObjectContribution();
    }

    @Override
	public boolean isApplicableTo(Object object) {
        if (!configRead) {
			readConfigElement();
		}

        if (canAdapt()) {
			Object adapted = LegacyResourceSupport.getAdapter(object, getObjectClass());
			if (adapted == null) {
				if (Policy.DEBUG_CONTRIBUTIONS) {
					WorkbenchPlugin
							.log("Error adapting " + object.getClass().getName() + //$NON-NLS-1$
									" to " //$NON-NLS-1$
									+ getObjectClass()
									+ ". Contribution " + getID(config) + " is being ignored"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else {
				object = adapted;
			}
		}
			
        if (!testName(object)) {
			return false;
		}

        return ((ObjectContribution) currentContribution)
                .isApplicableTo(object);
    }

    private void readConfigElement() {
        currentContribution = createContribution();
        readElementChildren(config);
        configRead = true;
    }

    @Override
	protected boolean readElement(IConfigurationElement element) {
        String tag = element.getName();

        if (tag.equals(IWorkbenchRegistryConstants.TAG_VISIBILITY)) {
            ((ObjectContribution) currentContribution)
                    .setVisibilityTest(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_FILTER)) {
            ((ObjectContribution) currentContribution).addFilterTest(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_ENABLEMENT)) {
            ((ObjectContribution) currentContribution)
                    .setEnablementTest(element);
            return true;
        }

        return super.readElement(element);
    }

    private boolean testName(Object object) {
        String nameFilter = config.getAttribute(IWorkbenchRegistryConstants.ATT_NAME_FILTER);
        if (nameFilter == null) {
			return true;
		}
        String objectName = null;
        IWorkbenchAdapter de = (IWorkbenchAdapter)Util.getAdapter(object, IWorkbenchAdapter.class);
        if (de != null) {
			objectName = de.getLabel(object);
		}
        if (objectName == null) {
            objectName = object.toString();
        }
        return SelectionEnabler.verifyNameMatch(objectName, nameFilter);
    }

    private static class ObjectContribution extends BasicContribution {
        private ObjectFilterTest filterTest;

        private ActionExpression visibilityTest;

        private Expression enablement;

        public void addFilterTest(IConfigurationElement element) {
            if (filterTest == null) {
				filterTest = new ObjectFilterTest();
			}
            filterTest.addFilterElement(element);
        }

        public void setVisibilityTest(IConfigurationElement element) {
            visibilityTest = new ActionExpression(element);
        }

        public void setEnablementTest(IConfigurationElement element) {
            try {
                enablement = ExpressionConverter.getDefault().perform(element);
            } catch (CoreException e) {
                WorkbenchPlugin.log(e);
            }
        }

        public boolean isApplicableTo(Object object) {
            boolean result = true;
            if (visibilityTest != null) {
                result = result && visibilityTest.isEnabledFor(object);
                if (!result) {
					return result;
				}
            } else if (filterTest != null) {
                result = result && filterTest.matches(object, true);
                if (!result) {
					return result;
				}
            }
            if (enablement != null) {
                try {
                    IEvaluationContext context = new EvaluationContext(null,
                            object);
                    context.setAllowPluginActivation(true);
                    context.addVariable("selection", object); //$NON-NLS-1$
                    context.addVariable("org.eclipse.core.runtime.Platform", Platform.class); //$NON-NLS-1$
                    EvaluationResult evalResult = enablement.evaluate(context);
                    if (evalResult == EvaluationResult.FALSE) {
						return false;
					}
                } catch (CoreException e) {
                    enablement = null;
                    WorkbenchPlugin.log(e);
                    result = false;
                }
            }
            return result;
        }
    }    
    
    @Override
	public String toString() {
    	StringBuffer buffer = new StringBuffer();
    	IConfigurationElement[] children = config.getChildren();
    	for (int i = 0; i < children.length; i++) {
			IConfigurationElement element = children[i];
			String label = element.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
			if(label != null) {
				buffer.append(label);
				buffer.append('\n'); 
			}
		}
    	return buffer.toString();
    }

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IConfigurationElement.class)) {
			return config;
		}
		return null;
	}
}
