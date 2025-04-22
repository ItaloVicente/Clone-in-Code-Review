package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;

public class SafeDelegateCommonLabelProvider implements ICommonLabelProvider, IColorProvider, IFontProvider, ITreePathLabelProvider, ITableLabelProvider, IStyledLabelProvider {

	private final ILabelProvider delegateLabelProvider;

	public SafeDelegateCommonLabelProvider(ILabelProvider aLabelProvider) {
		super();
		delegateLabelProvider = aLabelProvider;
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
	}

	@Override
	public String getDescription(Object element) {
		if (delegateLabelProvider instanceof IDescriptionProvider) {
			IDescriptionProvider provider = (IDescriptionProvider) delegateLabelProvider;
			return provider.getDescription(element);
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		delegateLabelProvider.addListener(listener);
	}

	@Override
	public void dispose() {
		delegateLabelProvider.dispose();
	}

	@Override
	public boolean equals(Object obj) {
		return delegateLabelProvider.equals(obj);
	}

	@Override
	public Image getImage(Object element) {
		return delegateLabelProvider.getImage(element);
	}

	@Override
	public String getText(Object element) {
		return delegateLabelProvider.getText(element);
	}
	
	@Override
	public StyledString getStyledText(Object element) {
		if (delegateLabelProvider instanceof IStyledLabelProvider) {
			return ((IStyledLabelProvider)delegateLabelProvider).getStyledText(element);
		}
		String text= getText(element);
		if (text == null)
			text= ""; //$NON-NLS-1$
		return new StyledString(text);
	}	

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (delegateLabelProvider instanceof ITableLabelProvider) {
			return ((ITableLabelProvider)delegateLabelProvider).getColumnImage(element, columnIndex);
		}
		return getImage(element);
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (delegateLabelProvider instanceof ITableLabelProvider) {
			return ((ITableLabelProvider)delegateLabelProvider).getColumnText(element, columnIndex);
		}
		return getText(element);
	}
	
	@Override
	public int hashCode() {
		return delegateLabelProvider.hashCode();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return delegateLabelProvider.isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		delegateLabelProvider.removeListener(listener);
	}

	@Override
	public String toString() {
		return delegateLabelProvider.toString();
	}

	@Override
	public void restoreState(IMemento aMemento) {

	}

	@Override
	public void saveState(IMemento aMemento) {

	}

	@Override
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		if (delegateLabelProvider instanceof ITreePathLabelProvider) {
			ITreePathLabelProvider tplp = (ITreePathLabelProvider) delegateLabelProvider;
			String text = label.getText() != null ? label.getText() : ""; //$NON-NLS-1$
			Image image = label.getImage();
			tplp.updateLabel(label, elementPath);
			if(label.getText() == null)
				label.setText(text);
			if(label.getImage() == null && image != null)
				label.setImage(image);
		} else {
			Image image = getImage(elementPath.getLastSegment());
			if(image != null)
				label.setImage(image);
			String text = getText(elementPath.getLastSegment());
			if(text != null)
				label.setText(text);
		}
	}

	@Override
	public Color getForeground(Object element) {
		if(delegateLabelProvider instanceof IColorProvider) {
			return ((IColorProvider)delegateLabelProvider).getForeground(element);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		if(delegateLabelProvider instanceof IColorProvider) {
			return ((IColorProvider)delegateLabelProvider).getBackground(element);
		}
		return null;
	}

	@Override
	public Font getFont(Object element) {
		if(delegateLabelProvider instanceof IFontProvider) {
			return ((IFontProvider)delegateLabelProvider).getFont(element);
		}
		return null;
	}
}
