package org.eclipse.ui.internal;

import java.util.ArrayList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.AbstractGroupMarker;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.RegistryReader;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public abstract class PluginActionBuilder extends RegistryReader {
    protected String targetID;

    protected String targetContributionTag;

    protected BasicContribution currentContribution;

	protected ArrayList cache;

    public PluginActionBuilder() {
    }

    public final void contribute(IMenuManager menu, IToolBarManager toolbar,
            boolean appendIfMissing) {
        if (cache == null) {
			return;
		}

        for (int i = 0; i < cache.size(); i++) {
			BasicContribution contribution = (BasicContribution) cache.get(i);
            contribution.contribute(menu, appendIfMissing, toolbar,
                    appendIfMissing);
        }
    }

    protected abstract ActionDescriptor createActionDescriptor(
            IConfigurationElement element);

    protected BasicContribution createContribution() {
        return new BasicContribution();
    }

    protected String getTargetID(IConfigurationElement element) {
        String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
        return value != null ? value : "???"; //$NON-NLS-1$
    }
    
    protected String getID(IConfigurationElement element) {
        String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        return value != null ? value : "???"; //$NON-NLS-1$
    }

    protected void readContributions(String id, String tag,
            String extensionPoint) {
        cache = null;
        currentContribution = null;
        targetID = id;
        targetContributionTag = tag;
        readRegistry(Platform.getExtensionRegistry(), PlatformUI.PLUGIN_ID,
                extensionPoint);
    }

    @Override
	protected boolean readElement(IConfigurationElement element) {
        String tag = element.getName();

        if (tag.equals(IWorkbenchRegistryConstants.TAG_OBJECT_CONTRIBUTION)) {
            return true;
        }

        if (tag.equals(targetContributionTag)) {
            if (targetID != null) {
                String id = getTargetID(element);
                if (id == null || !id.equals(targetID)) {
					return true;
				}
            }

            currentContribution = createContribution();
            readElementChildren(element);
            if (cache == null) {
				cache = new ArrayList(4);
			}
            cache.add(currentContribution);
            currentContribution = null;
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_MENU)) {
            currentContribution.addMenu(element);
            return true;
        }

        if (tag.equals(IWorkbenchRegistryConstants.TAG_ACTION)) {
            currentContribution.addAction(createActionDescriptor(element));
            return true;
        }

        return false;
    }

    protected static class BasicContribution {
		protected ArrayList menus;

		protected ArrayList actions;

        public void addMenu(IConfigurationElement element) {
            if (menus == null) {
				menus = new ArrayList(1);
			}
            menus.add(element);
        }

        public void addAction(ActionDescriptor desc) {
            if (actions == null) {
				actions = new ArrayList(3);
			}
            actions.add(desc);
        }

        public void contribute(IMenuManager menu, boolean menuAppendIfMissing,
                IToolBarManager toolbar, boolean toolAppendIfMissing) {
            if (menus != null && menu != null) {
                for (int i = 0; i < menus.size(); i++) {
					IConfigurationElement menuElement = (IConfigurationElement) menus
                            .get(i);
                    contributeMenu(menuElement, menu, menuAppendIfMissing);
                }
            }

            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
					ActionDescriptor ad = (ActionDescriptor) actions.get(i);
                    if (menu != null) {
						contributeMenuAction(ad, menu, menuAppendIfMissing);
					}
                    if (toolbar != null) {
						contributeToolbarAction(ad, toolbar,
                                toolAppendIfMissing);
					}
                }
            }
        }

        protected void contributeMenu(IConfigurationElement menuElement,
                IMenuManager mng, boolean appendIfMissing) {
            String id = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
            String label = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_LABEL);
            String path = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_PATH);
            String icon = menuElement.getAttribute(IWorkbenchRegistryConstants.ATT_ICON);
            ImageDescriptor image = null;
            if (icon != null) {
            	String extendingPluginId = menuElement.getDeclaringExtension()
						.getContributor().getName();
				image = AbstractUIPlugin.imageDescriptorFromPlugin(
						extendingPluginId, icon);
			}
            if (label == null) {
				WorkbenchPlugin.log("Plugin \'" //$NON-NLS-1$
						+ menuElement.getContributor().getName()
						+ "\' invalid Menu Extension (label == null): " + id); //$NON-NLS-1$
				return;
			}

            String group = null;
            if (path != null) {
                int loc = path.lastIndexOf('/');
                if (loc != -1) {
                    group = path.substring(loc + 1);
                    path = path.substring(0, loc);
                } else {
                    group = path;
                    path = null;
                }
            }

            IMenuManager parent = mng;
            if (path != null) {
                parent = mng.findMenuUsingPath(path);
                if (parent == null) {
					ideLog("Plugin \'" //$NON-NLS-1$
									+ menuElement.getContributor().getName()
									+ "\' invalid Menu Extension (Path \'"  //$NON-NLS-1$
									+ path + "\' is invalid): " + id); //$NON-NLS-1$
					return;
				}
            }

            if (group == null) {
				group = IWorkbenchActionConstants.MB_ADDITIONS;
			}
            IContributionItem sep = parent.find(group);
            if (sep == null) {
                if (appendIfMissing) {
					addGroup(parent, group);
				} else {
                    WorkbenchPlugin
                            .log("Plugin \'" //$NON-NLS-1$
									+ menuElement.getContributor().getName()
									+ "\' invalid Menu Extension (Group \'"  //$NON-NLS-1$
									+ group + "\' is invalid): " + id); //$NON-NLS-1$
                    return;
                }
            }

            IMenuManager newMenu = parent.findMenuUsingPath(id);
            if (newMenu == null) {
				newMenu = new MenuManager(label, image, id);
			}

			try {
                insertAfter(parent, group, newMenu);
            } catch (IllegalArgumentException e) {
                WorkbenchPlugin
                        .log("Plugin \'" //$NON-NLS-1$
								+ menuElement.getContributor().getName()
								+ "\' invalid Menu Extension (Group \'"  //$NON-NLS-1$
								+ group + "\' is missing): " + id); //$NON-NLS-1$
            }

            newMenu = parent.findMenuUsingPath(id);
            if (newMenu == null) {
				WorkbenchPlugin.log("Could not find new menu: " + id); //$NON-NLS-1$
			}

            IConfigurationElement[] children = menuElement.getChildren();
            for (int i = 0; i < children.length; i++) {
                String childName = children[i].getName();
                if (childName.equals(IWorkbenchRegistryConstants.TAG_SEPARATOR)) {
                    contributeSeparator(newMenu, children[i]);
                } else if (childName.equals(IWorkbenchRegistryConstants.TAG_GROUP_MARKER)) {
                    contributeGroupMarker(newMenu, children[i]);
                }
            }
        }

        protected void contributeMenuAction(ActionDescriptor ad,
                IMenuManager menu, boolean appendIfMissing) {
            String mpath = ad.getMenuPath();
            String mgroup = ad.getMenuGroup();
            if (mpath == null && mgroup == null) {
				return;
			}
            IMenuManager parent = menu;
            if (mpath != null) {
                parent = parent.findMenuUsingPath(mpath);
                if (parent == null) {
                    ideLog("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Path: '" + mpath + "' is invalid): " + ad.getId()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    return;
                }
            }

            if (mgroup == null) {
				mgroup = IWorkbenchActionConstants.MB_ADDITIONS;
			}
            IContributionItem sep = parent.find(mgroup);
            if (sep == null) {
                if (appendIfMissing) {
					addGroup(parent, mgroup);
				} else {
                    WorkbenchPlugin
                            .log("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Group: '" + mgroup + "' is invalid): " + ad.getId()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    return;
                }
            }

            try {
                insertAfter(parent, mgroup, ad);
            } catch (IllegalArgumentException e) {
                WorkbenchPlugin
                        .log("Plug-in '" + ad.getPluginId() + "' contributed an invalid Menu Extension (Group: '" + mgroup + "' is missing): " + ad.getId()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
        }

        protected void contributeSeparator(IMenuManager menu,
                IConfigurationElement element) {
            String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
            if (id == null || id.length() <= 0) {
				return;
			}
            IContributionItem sep = menu.find(id);
            if (sep != null) {
				return;
			}
            insertMenuGroup(menu, new Separator(id));
        }

        protected void contributeGroupMarker(IMenuManager menu,
                IConfigurationElement element) {
            String id = element.getAttribute(IWorkbenchRegistryConstants.ATT_NAME);
            if (id == null || id.length() <= 0) {
				return;
			}
            IContributionItem marker = menu.find(id);
            if (marker != null) {
				return;
			}
            insertMenuGroup(menu, new GroupMarker(id));
        }

        protected void contributeToolbarAction(ActionDescriptor ad,
                IToolBarManager toolbar, boolean appendIfMissing) {
            String tId = ad.getToolbarId();
            String tgroup = ad.getToolbarGroupId();
            if (tId == null && tgroup == null) {
				return;
			}

            if (tgroup == null) {
				tgroup = IWorkbenchActionConstants.MB_ADDITIONS;
			}
            IContributionItem sep = null;
            sep = toolbar.find(tgroup);
            if (sep == null) {
                if (appendIfMissing) {
                    addGroup(toolbar, tgroup);
                } else {
                    WorkbenchPlugin
                            .log("Plug-in '" + ad.getPluginId()  //$NON-NLS-1$
                            		+ "' invalid Toolbar Extension (Group \'" //$NON-NLS-1$
                            		+ tgroup + "\' is invalid): " + ad.getId()); //$NON-NLS-1$
                    return;
                }
            }
            try {
                insertAfter(toolbar, tgroup, ad);
            } catch (IllegalArgumentException e) {
                WorkbenchPlugin
                        .log("Plug-in '" + ad.getPluginId()  //$NON-NLS-1$
                        		+ "' invalid Toolbar Extension (Group \'" //$NON-NLS-1$
                        		+ tgroup + "\' is missing): " + ad.getId()); //$NON-NLS-1$
            }
        }

        protected void insertMenuGroup(IMenuManager menu,
                AbstractGroupMarker marker) {
            menu.add(marker);
        }

        protected void insertAfter(IContributionManager mgr, String refId,
                ActionDescriptor desc) {
            final PluginActionContributionItem item = new PluginActionContributionItem(desc.getAction());
            item.setMode(desc.getMode());
			insertAfter(mgr, refId, item);
        }

        protected void insertAfter(IContributionManager mgr, String refId,
                IContributionItem item) {
            mgr.insertAfter(refId, item);
        }

        protected void addGroup(IContributionManager mgr, String name) {
            mgr.add(new Separator(name));
        }

		public void dispose() {
		}
		
		protected void disposeActions() {
            if (actions != null) {
                for (int i = 0; i < actions.size(); i++) {
					PluginAction proxy = ((ActionDescriptor) actions.get(i))
                            .getAction();
					proxy.dispose();
                }
				actions = null;
            }
		}
    }
    
    private static boolean allowIdeLogging = false;
    
    public static void setAllowIdeLogging(boolean b) {
    	allowIdeLogging = b;
    }
    
    private static void ideLog(String msg) {
    	if (allowIdeLogging) {
    		WorkbenchPlugin.log(msg);
    	}
    }
}
