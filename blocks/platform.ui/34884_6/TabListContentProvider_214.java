package org.eclipse.ui.internal.views.properties.tabbed.view;

import com.ibm.icu.text.MessageFormat;

import org.eclipse.swt.graphics.Image;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.eclipse.ui.internal.views.properties.tabbed.TabbedPropertyViewPlugin;
import org.eclipse.ui.internal.views.properties.tabbed.TabbedPropertyViewStatusCodes;
import org.eclipse.ui.internal.views.properties.tabbed.l10n.TabbedPropertyMessages;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;

public class TabDescriptor extends AbstractTabDescriptor {

	private static final String ATT_ID = "id"; //$NON-NLS-1$

	private static final String ATT_LABEL = "label"; //$NON-NLS-1$

	private static final String ATT_IMAGE = "image"; //$NON-NLS-1$

	private static final String ATT_INDENTED = "indented"; //$NON-NLS-1$

	private static final String ATT_CATEGORY = "category"; //$NON-NLS-1$

	private static final String ATT_AFTER_TAB = "afterTab"; //$NON-NLS-1$

	private final static String TAB_ERROR = TabbedPropertyMessages.TabDescriptor_Tab_error;

	private String id;

	private String label;

	private Image image;

	private boolean selected;

	private boolean indented;

	private String category;

	private String afterTab;

	public TabDescriptor(IConfigurationElement configurationElement) {
		super();
		if (configurationElement != null) {
			id = configurationElement.getAttribute(ATT_ID);
			label = configurationElement.getAttribute(ATT_LABEL);
			String imageString = configurationElement.getAttribute(ATT_IMAGE);
			if (imageString != null) {
				image = AbstractUIPlugin.imageDescriptorFromPlugin(
						configurationElement.getDeclaringExtension()
								.getNamespaceIdentifier(), imageString)
						.createImage();
			}
			String indentedString = configurationElement
					.getAttribute(ATT_INDENTED);
			indented = indentedString != null && indentedString.equals("true"); //$NON-NLS-1$
			category = configurationElement.getAttribute(ATT_CATEGORY);
			afterTab = configurationElement.getAttribute(ATT_AFTER_TAB);
			if (id == null || label == null || category == null) {
				handleTabError(configurationElement, null);
			}
		}
		selected = false;
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public String getAfterTab() {
		if (afterTab == null) {
			return super.getAfterTab();
		}
		return afterTab;
	}

	public String getCategory() {
		return category;
	}

	protected boolean append(ISectionDescriptor target) {
		if (!target.getTargetTab().equals(id)) {
			return false;
		}

		if (insertSectionDescriptor(target)) {
			return true;
		}

		getSectionDescriptors().add(target);
		return true;
	}

	private boolean insertSectionDescriptor(ISectionDescriptor target) {
		if (target.getAfterSection().equals(TOP)) {
			getSectionDescriptors().add(0, target);
			return true;
		}
		for (int i = 0; i < getSectionDescriptors().size(); i++) {
			ISectionDescriptor descriptor = (ISectionDescriptor) getSectionDescriptors()
					.get(i);
			if (target.getAfterSection().equals(descriptor.getId())) {
				getSectionDescriptors().add(i + 1, target);
				return true;
			} else if (descriptor.getAfterSection().equals(target.getId())) {
				getSectionDescriptors().add(i, target);
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return getId();
	}

	private void handleTabError(IConfigurationElement configurationElement,
			CoreException exception) {
		String pluginId = configurationElement.getDeclaringExtension()
				.getNamespaceIdentifier();
		String message = MessageFormat.format(TAB_ERROR,
				new Object[] { pluginId });
		IStatus status = new Status(IStatus.ERROR, pluginId,
				TabbedPropertyViewStatusCodes.TAB_ERROR, message, exception);
		TabbedPropertyViewPlugin.getPlugin().getLog().log(status);
	}

	protected void setImage(Image image) {
		this.image = image;
	}

	protected void setIndented(boolean indented) {
		this.indented = indented;
	}

	protected void setSelected(boolean selected) {
		this.selected = selected;
	}

	protected void setLabel(String label) {
		this.label = label;
	}

	public Image getImage() {
		return image;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isIndented() {
		return indented;
	}

	public String getText() {
		return label;
	}

	public void dispose() {
		if (image != null) {
			image.dispose();
			image = null;
		}
	}
}
