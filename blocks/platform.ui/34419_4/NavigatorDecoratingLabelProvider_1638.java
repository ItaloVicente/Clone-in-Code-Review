package org.eclipse.ui.internal.navigator;


import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentExtension;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonLabelProvider;
import org.eclipse.ui.navigator.INavigatorContentService;

public class NavigatorContentServiceLabelProvider extends EventManager
		implements ILabelProvider, IColorProvider, IFontProvider, ITreePathLabelProvider, ITableLabelProvider, ILabelProviderListener, IStyledLabelProvider {
 
	private final NavigatorContentService contentService;
	private final boolean isContentServiceSelfManaged;
	private final ReusableViewerLabel reusableLabel = new ReusableViewerLabel();

  
	public NavigatorContentServiceLabelProvider(NavigatorContentService aContentService) {
		contentService = aContentService; 
		isContentServiceSelfManaged = false;
	}

	@Override
	public Image getImage(Object anElement) {
		return getColumnImage(anElement, -1);
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		Collection contentExtensions = contentService.findPossibleLabelExtensions(element);
		Image image = null; 
		for (Iterator itr = contentExtensions.iterator(); itr.hasNext() && image == null; ) { 
			image = findImage((NavigatorContentExtension) itr.next(), element, columnIndex);
		}
		return image;
	}

	@Override
	public String getText(Object anElement) {
		return getColumnText(anElement, -1);
	}

	@Override
	public String getColumnText(Object anElement, int aColumn) {
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		if (labelProviders.length == 0)
			return NLS.bind(CommonNavigatorMessages.NavigatorContentServiceLabelProvider_Error_no_label_provider_for_0_, makeSmallString(anElement));	
		String text = null;
		for (int i = 0; i < labelProviders.length; i++) {
			if (labelProviders[i] instanceof ITableLabelProvider && aColumn != -1)
				text = ((ITableLabelProvider)labelProviders[i]).getColumnText(anElement, aColumn);
			else
				text = labelProviders[i].getText(anElement);
			if (text != null && text.length() > 0)
				return text;
		}
		return text;
	}
	
	@Override
	public StyledString getStyledText(Object anElement) {
		Collection extensions = contentService.findPossibleLabelExtensions(anElement);
		if (extensions.size() == 0)
			return new StyledString(NLS.bind(CommonNavigatorMessages.NavigatorContentServiceLabelProvider_Error_no_label_provider_for_0_, makeSmallString(anElement)));	

		StyledString text = null;
		for (Iterator itr = extensions.iterator(); itr.hasNext() && text == null; ) { 
			text = findStyledText((NavigatorContentExtension) itr.next(), anElement);
		}
		return text != null ? text : new StyledString();
	}
	
	private StyledString findStyledText(NavigatorContentExtension foundExtension, Object anElement) { 
		ICommonLabelProvider labelProvider= foundExtension.getLabelProvider();
		if (labelProvider instanceof IStyledLabelProvider) {
			StyledString styledText= ((IStyledLabelProvider) labelProvider).getStyledText(anElement);
			if (styledText != null && styledText.length() > 0) {
				return styledText;
			}
		} else {
			String text= labelProvider.getText(anElement);
			if (text != null && text.length() > 0) {
				return new StyledString(text);
			}
		}  
		return null;
	}
	
	private String makeSmallString(Object obj) {
		if (obj == null)
			return "null"; //$NON-NLS-1$
		String str = obj.toString();
		int len = str.length();
		return str.substring(0, len < 50 ? len : 49);
	}
	
	private Image findImage(NavigatorContentExtension foundExtension, Object anElement, int aColumn) { 
		Image image = null;
		ICommonLabelProvider provider = foundExtension.getLabelProvider();
		if (provider instanceof ITableLabelProvider && aColumn >= 0)
			image = ((ITableLabelProvider)provider).getColumnImage(anElement, aColumn);
		else
			image = provider.getImage(anElement);
  
		return image;
	}
	
	@Override
	public Font getFont(Object anElement) {
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		for (int i = 0; i < labelProviders.length; i++) {
			ILabelProvider provider = labelProviders[i];
			if (provider instanceof IFontProvider) {
				IFontProvider fontProvider = (IFontProvider) provider;
				Font font = fontProvider.getFont(anElement);
				if (font != null) {
					return font;
				}
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object anElement) {
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		for (int i = 0; i < labelProviders.length; i++) {
			ILabelProvider provider = labelProviders[i];
			if (provider instanceof IColorProvider) {
				IColorProvider colorProvider = (IColorProvider) provider;
				Color color = colorProvider.getForeground(anElement);
				if (color != null) {
					return color;
				}
			}
		}
		return null;
	}

	@Override
	public Color getBackground(Object anElement) {
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		for (int i = 0; i < labelProviders.length; i++) {
			ILabelProvider provider = labelProviders[i];
			if (provider instanceof IColorProvider) {
				IColorProvider colorProvider = (IColorProvider) provider;
				Color color = colorProvider.getBackground(anElement);
				if (color != null) {
					return color;
				}
			}
		}
		return null;
	}	

	@Override
	public boolean isLabelProperty(Object anElement, String aProperty) {
		boolean result = false;
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		for (int i = 0; i < labelProviders.length && !result; i++) {
			result = labelProviders[i].isLabelProperty(anElement, aProperty);
		}
		return result;
	}


	@Override
	public void addListener(ILabelProviderListener aListener) {
		addListenerObject(aListener);
	}

	@Override
	public void removeListener(ILabelProviderListener aListener) {
		removeListenerObject(aListener);
	}

	@Override
	public void dispose() {
		if (isContentServiceSelfManaged) {
			contentService.dispose();
		}

	}
	
    protected void fireLabelProviderChanged(
            final LabelProviderChangedEvent event) {
        Object[] theListeners = getListeners();
        for (int i = 0; i < theListeners.length; ++i) {
            final ILabelProviderListener l = (ILabelProviderListener) theListeners[i];
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.labelProviderChanged(event);
                }
            });

        }
    }

	@Override
	public void updateLabel(ViewerLabel label, TreePath elementPath) { 
		 
		Collection contentExtensions = contentService.findPossibleLabelExtensions(elementPath.getLastSegment());
		reusableLabel.reset(label);
		for (Iterator itr = contentExtensions.iterator(); itr.hasNext() && !(reusableLabel.isValid() && reusableLabel.hasChanged()); ) {			 
			findUpdateLabel((NavigatorContentExtension)itr.next(), reusableLabel, elementPath);			 
		}
		reusableLabel.fill(label);
	}


	private void findUpdateLabel(NavigatorContentExtension foundExtension, ReusableViewerLabel label, TreePath elementPath) {
		
		ILabelProvider labelProvider = foundExtension.getLabelProvider();
		if (labelProvider instanceof ITreePathLabelProvider) {
			ITreePathLabelProvider tplp = (ITreePathLabelProvider) labelProvider;
			tplp.updateLabel(label, elementPath);
		} else {
			label.setImage(labelProvider.getImage(elementPath.getLastSegment()));
			label.setText(labelProvider.getText(elementPath.getLastSegment()));
		}
	}
 
	@Override
	public void labelProviderChanged(LabelProviderChangedEvent event) { 
		fireLabelProviderChanged(event);		
	}



}
