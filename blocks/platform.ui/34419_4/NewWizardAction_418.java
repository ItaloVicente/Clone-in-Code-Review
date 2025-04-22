package org.eclipse.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.PropertyChangeEvent;

public class LabelRetargetAction extends RetargetAction {
    private String defaultText;

    private String defaultToolTipText;

    private ImageDescriptor defaultHoverImage;

    private ImageDescriptor defaultImage;

    private ImageDescriptor defaultDisabledImage;

    private String acceleratorText;

    public LabelRetargetAction(String actionID, String text) {
        this(actionID, text, IAction.AS_UNSPECIFIED);
    }

    public LabelRetargetAction(String actionID, String text, int style) {
        super(actionID, text, style);
        this.defaultText = text;
        this.defaultToolTipText = text;
        acceleratorText = LegacyActionTools.extractAcceleratorText(text);
    }

    @Override
	protected void propagateChange(PropertyChangeEvent event) {
        super.propagateChange(event);
        String prop = event.getProperty();
        if (prop.equals(IAction.TEXT)) {
            String str = (String) event.getNewValue();
            super.setText(appendAccelerator(str));
        } else if (prop.equals(IAction.TOOL_TIP_TEXT)) {
            String str = (String) event.getNewValue();
            super.setToolTipText(str);
        } else if (prop.equals(IAction.IMAGE)) {
            updateImages(getActionHandler());
        }
    }

    @Override
	protected void setActionHandler(IAction handler) {
        super.setActionHandler(handler);

        if (handler == null) {
            super.setText(defaultText);
            super.setToolTipText(defaultToolTipText);
        } else {
            String handlerText = handler.getText();
            if (handlerText == null || handlerText.length() == 0) {
                handlerText = defaultText;
            }
            super.setText(appendAccelerator(handlerText));
            super.setToolTipText(handler.getToolTipText());
        }
        updateImages(handler);
    }

    @Override
	public void setDisabledImageDescriptor(ImageDescriptor image) {
        super.setDisabledImageDescriptor(image);
        defaultDisabledImage = image;
    }

    @Override
	public void setHoverImageDescriptor(ImageDescriptor image) {
        super.setHoverImageDescriptor(image);
        defaultHoverImage = image;
    }

    @Override
	public void setImageDescriptor(ImageDescriptor image) {
        super.setImageDescriptor(image);
        defaultImage = image;
    }

    @Override
	public void setText(String text) {
        super.setText(text);
        acceleratorText = LegacyActionTools.extractAcceleratorText(text);
        defaultText = text;
    }

    @Override
	public void setToolTipText(String text) {
        super.setToolTipText(text);
        defaultToolTipText = text;
    }

    private String appendAccelerator(String newText) {
        if (newText == null) {
			return null;
		}

        String str = removeAcceleratorText(newText);
        if (acceleratorText != null) {
			str = str + '\t' + acceleratorText;
		} else if (str != newText) {
			str = str + '\t';
		}
        return str;
    }

    private void updateImages(IAction handler) {
        if (handler == null) {
            super.setHoverImageDescriptor(defaultHoverImage);
            super.setImageDescriptor(defaultImage);
            super.setDisabledImageDescriptor(defaultDisabledImage);
        } else {
            ImageDescriptor hoverImage = handler.getHoverImageDescriptor();
            ImageDescriptor image = handler.getImageDescriptor();
            ImageDescriptor disabledImage = handler
                    .getDisabledImageDescriptor();
            if (hoverImage != null || image != null || disabledImage != null) {
                super.setHoverImageDescriptor(hoverImage);
                super.setImageDescriptor(image);
                super.setDisabledImageDescriptor(disabledImage);
            } else {
                super.setHoverImageDescriptor(defaultHoverImage);
                super.setImageDescriptor(defaultImage);
                super.setDisabledImageDescriptor(defaultDisabledImage);
            }
        }
    }

}
