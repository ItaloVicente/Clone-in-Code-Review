package org.eclipse.ui.internal.views.properties.tabbed.view;

import java.util.Map;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.internal.views.properties.tabbed.l10n.TabbedPropertyMessages;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;


public class TabbedPropertyList
	extends Composite {

	private static final ListElement[] ELEMENTS_EMPTY = new ListElement[0];

	protected static final int NONE = -1;

	protected static final int INDENT = 7;

	private boolean focus = false;

	private ListElement[] elements;

	private Map tabToDynamicImageCountMap;

	private int selectedElementIndex = NONE;

	private int topVisibleIndex = NONE;

	private int bottomVisibleIndex = NONE;

	private TopNavigationElement topNavigationElement;

	private BottomNavigationElement bottomNavigationElement;

	private int widestLabelIndex = NONE;

	private int tabsThatFitInComposite = NONE;

	private Color widgetForeground;

	private Color widgetBackground;

	private Color widgetNormalShadow;

	private Color widgetDarkShadow;

	private Color listBackground;

	private Color hoverGradientStart;

	private Color hoverGradientEnd;

	private Color defaultGradientStart;

	private Color defaultGradientEnd;

	private Color indentedDefaultBackground;

	private Color indentedHoverBackground;

	private Color navigationElementShadowStroke;

	private Color bottomNavigationElementShadowStroke1;

	private Color bottomNavigationElementShadowStroke2;

	private TabbedPropertySheetWidgetFactory factory;

	public class ListElement extends Canvas {

		private ITabItem tab;

		private int index;

		private boolean selected;

		private boolean hover;

		private Image[] dynamicImages;

		private Color textColor = widgetForeground;

		public ListElement(Composite parent, final ITabItem tab, int index) {
			super(parent, SWT.NO_FOCUS);
			this.tab = tab;
			hover = false;
			selected = false;
			this.index = index;

			addPaintListener(new PaintListener() {

				public void paintControl(PaintEvent e) {
					paint(e);
				}
			});
			addMouseListener(new MouseAdapter() {

				public void mouseUp(MouseEvent e) {
					if (!selected) {
						select(getIndex(ListElement.this));
						Composite tabbedPropertyComposite = getParent();
						while (!(tabbedPropertyComposite instanceof TabbedPropertyComposite)) {
							tabbedPropertyComposite = tabbedPropertyComposite
								.getParent();
						}
						tabbedPropertyComposite.setFocus();
					}
				}
			});
			addMouseMoveListener(new MouseMoveListener() {

				public void mouseMove(MouseEvent e) {
					if (!hover) {
						hover = true;
						redraw();
					}
				}
			});
			addMouseTrackListener(new MouseTrackAdapter() {

				public void mouseExit(MouseEvent e) {
					hover = false;
					redraw();
				}
			});
		}

		public ListElement(Composite parent, final ITabItem tab,
				int dynamicImageCount, int index) {
			this(parent, tab, index);
			this.dynamicImages = new Image[dynamicImageCount];
			for (int i = 0; i < dynamicImageCount; i++) {
				this.dynamicImages[i] = null;
			}
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			redraw();
		}

		public void showDynamicImage(int index, Image image) {
			if (index >= 0 && index < dynamicImages.length) {
				if (dynamicImages[index] != image) {
					dynamicImages[index] = image;
					redraw();
				}
			}
		}

		public void hideDynamicImage(int index) {
			if (index >= 0 && index < dynamicImages.length) {
				if (dynamicImages[index] != null) {
					dynamicImages[index] = null;
					redraw();
				}
			}
		}

		public void setTextColor(Color textColor) {
			if (textColor != null && !this.textColor.equals(textColor)) {
				this.textColor = textColor;
				redraw();
			}
		}

		public void setDefaultTextColor() {
			if (!this.textColor.equals(widgetForeground)) {
				this.textColor = widgetForeground;
				redraw();
			}
		}

		private void paint(PaintEvent e) {
			Rectangle bounds = getBounds();
			e.gc.setForeground(widgetNormalShadow);
			e.gc.drawLine(0, 0, bounds.width - 1, 0);
			e.gc.setForeground(listBackground);
			e.gc.drawLine(0, 1, bounds.width - 1, 1);

			if (selected) {
				e.gc.setBackground(listBackground);
				e.gc.fillRectangle(0, 2, bounds.width, bounds.height - 1);
			} else if (hover && tab.isIndented()) {
				e.gc.setBackground(indentedHoverBackground);
				e.gc.fillRectangle(0, 2, bounds.width - 1, bounds.height - 1);
			} else if (hover) {
				e.gc.setForeground(hoverGradientStart);
				e.gc.setBackground(hoverGradientEnd);
				e.gc.fillGradientRectangle(0, 2, bounds.width - 1,
						bounds.height - 1, true);
			} else if (tab.isIndented()) {
				e.gc.setBackground(indentedDefaultBackground);
				e.gc.fillRectangle(0, 2, bounds.width - 1, bounds.height - 1);
			} else {
				e.gc.setForeground(defaultGradientStart);
				e.gc.setBackground(defaultGradientEnd);
				e.gc.fillGradientRectangle(0, 2, bounds.width - 1,
						bounds.height - 1, true);
			}

			if (!selected) {
				e.gc.setForeground(widgetNormalShadow);
				e.gc.drawLine(bounds.width - 1, 1, bounds.width - 1,
						bounds.height + 1);
			}

			int textIndent = INDENT;
			FontMetrics fm = e.gc.getFontMetrics();
			int height = fm.getHeight();
			int textMiddle = (bounds.height - height) / 2;

			if (selected && tab.getImage() != null
				&& !tab.getImage().isDisposed()) {
				if (tab.isIndented()) {
					textIndent = textIndent + INDENT;
				} else {
					textIndent = textIndent - 3;
				}
				e.gc.drawImage(tab.getImage(), textIndent, textMiddle - 1);
				textIndent = textIndent + 16 + 4;
			} else if (tab.isIndented()) {
				textIndent = textIndent + INDENT;
			}

			e.gc.setForeground(textColor);
			if (selected) {
				e.gc.setFont(JFaceResources.getFontRegistry().getBold(
						JFaceResources.DEFAULT_FONT));
			}
			e.gc.drawText(tab.getText(), textIndent, textMiddle, true);
			if (((TabbedPropertyList) getParent()).focus && selected) {
				Point point = e.gc.textExtent(tab.getText());
				e.gc.drawLine(textIndent, bounds.height - 4, textIndent
					+ point.x, bounds.height - 4);
			}

			boolean hasDynamicImage = false;
			for (int i = 0; i < dynamicImages.length; i++) {
				Image dynamicImage = dynamicImages[i];
				if (dynamicImage != null && !dynamicImage.isDisposed()) {
					hasDynamicImage = true;
					break;
				}
			}
			if (hasDynamicImage) {
				int drawPosition = textIndent
						+ e.gc.textExtent(tab.getText()).x + 4;
				boolean addSpace = false;
				for (int i = 0; i < dynamicImages.length; i++) {
					Image dynamicImage = dynamicImages[i];
					if (dynamicImage != null && !dynamicImage.isDisposed()) {
						if (addSpace) {
							drawPosition = drawPosition + 3;
						}
						e.gc.drawImage(dynamicImage, drawPosition,
								textMiddle - 1);
						drawPosition = drawPosition + 16;
						addSpace = true;
					}
				}
			}

			if (!hover) {
				e.gc.setForeground(listBackground);
				e.gc.drawLine(0, bounds.height - 1, bounds.width - 2,
						bounds.height - 1);
			}
		}

		public ITabItem getTabItem() {
			return tab;
		}

		public String toString() {
			return tab.getText();
		}
	}

	public class TopNavigationElement extends Canvas {

		public TopNavigationElement(Composite parent) {
			super(parent, SWT.NO_FOCUS);
			addPaintListener(new PaintListener() {

				public void paintControl(PaintEvent e) {
					paint(e);
				}
			});
			addMouseListener(new MouseAdapter() {

				public void mouseUp(MouseEvent e) {
					if (isUpScrollRequired()) {
						bottomVisibleIndex--;
						if (topVisibleIndex != 0) {
							topVisibleIndex--;
						}
						layoutTabs();
						topNavigationElement.redraw();
						bottomNavigationElement.redraw();
					}
				}
			});
		}

		private void paint(PaintEvent e) {
			e.gc.setBackground(widgetBackground);
			e.gc.setForeground(widgetForeground);
			Rectangle bounds = getBounds();

			if (elements.length != 0) {
				e.gc.fillRectangle(0, 0, bounds.width, bounds.height);
				e.gc.setForeground(widgetNormalShadow);
				e.gc.drawLine(bounds.width - 1, 0, bounds.width - 1,
					bounds.height - 1);
			} else {
				e.gc.setBackground(listBackground);
				e.gc.fillRectangle(0, 0, bounds.width, bounds.height);
				int textIndent = INDENT;
				FontMetrics fm = e.gc.getFontMetrics();
				int height = fm.getHeight();
				int textMiddle = (bounds.height - height) / 2;
				e.gc.setForeground(widgetForeground);
				String properties_not_available = TabbedPropertyMessages.TabbedPropertyList_properties_not_available;
				e.gc.drawText(properties_not_available, textIndent, textMiddle);
			}

			if (isUpScrollRequired()) {
				e.gc.setForeground(widgetDarkShadow);
				int middle = bounds.width / 2;
				e.gc.drawLine(middle + 1, 3, middle + 5, 7);
				e.gc.drawLine(middle, 3, middle - 4, 7);
				e.gc.drawLine(middle - 3, 7, middle + 4, 7);

				e.gc.setForeground(listBackground);
				e.gc.drawLine(middle, 4, middle + 1, 4);
				e.gc.drawLine(middle - 1, 5, middle + 2, 5);
				e.gc.drawLine(middle - 2, 6, middle + 3, 6);

				e.gc.setForeground(widgetNormalShadow);
				e.gc.drawLine(0, 0, bounds.width - 2, 0);
				e.gc.setForeground(navigationElementShadowStroke);
				e.gc.drawLine(0, 1, bounds.width - 2, 1);
				e.gc.drawLine(0, bounds.height - 1, bounds.width - 2,
						bounds.height - 1);
			}
		}
	}

	public class BottomNavigationElement extends Canvas {

		public BottomNavigationElement(Composite parent) {
			super(parent, SWT.NO_FOCUS);
			addPaintListener(new PaintListener() {

				public void paintControl(PaintEvent e) {
					paint(e);
				}
			});
			addMouseListener(new MouseAdapter() {

				public void mouseUp(MouseEvent e) {
					if (isDownScrollRequired()) {
						topVisibleIndex++;
						if (bottomVisibleIndex != elements.length - 1) {
							bottomVisibleIndex++;
						}
						layoutTabs();
						topNavigationElement.redraw();
						bottomNavigationElement.redraw();
					}
				}
			});
		}

		private void paint(PaintEvent e) {
			e.gc.setBackground(widgetBackground);
			e.gc.setForeground(widgetForeground);
			Rectangle bounds = getBounds();

			if (elements.length != 0) {
				e.gc.fillRectangle(0, 0, bounds.width, bounds.height);
				e.gc.setForeground(widgetNormalShadow);
				e.gc.drawLine(bounds.width - 1, 0, bounds.width - 1,
						bounds.height - 1);
				e.gc.drawLine(0, 0, bounds.width - 1, 0);

				e.gc.setForeground(bottomNavigationElementShadowStroke1);
				e.gc.drawLine(0, 1, bounds.width - 2, 1);
				e.gc.setForeground(bottomNavigationElementShadowStroke2);
				e.gc.drawLine(0, 2, bounds.width - 2, 2);
			} else {
				e.gc.setBackground(listBackground);
				e.gc.fillRectangle(0, 0, bounds.width, bounds.height);
			}

			if (isDownScrollRequired()) {
				e.gc.setForeground(widgetDarkShadow);
				int middle = bounds.width / 2;
				int bottom = bounds.height - 3;
				e.gc.drawLine(middle + 1, bottom, middle + 5, bottom - 4);
				e.gc.drawLine(middle, bottom, middle - 4, bottom - 4);
				e.gc.drawLine(middle - 3, bottom - 4, middle + 4, bottom - 4);

				e.gc.setForeground(listBackground);
				e.gc.drawLine(middle, bottom - 1, middle + 1, bottom - 1);
				e.gc.drawLine(middle - 1, bottom - 2, middle + 2, bottom - 2);
				e.gc.drawLine(middle - 2, bottom - 3, middle + 3, bottom - 3);

				e.gc.setForeground(widgetNormalShadow);
				e.gc.drawLine(0, bottom - 7, bounds.width - 2, bottom - 7);
				e.gc.setForeground(navigationElementShadowStroke);
				e.gc.drawLine(0, bottom + 2, bounds.width - 2, bottom + 2);
				e.gc.drawLine(0, bottom - 6, bounds.width - 2, bottom - 6);
			}
		}
	}

	public TabbedPropertyList(Composite parent,
			TabbedPropertySheetWidgetFactory factory) {
		super(parent, SWT.NO_FOCUS);
		this.factory = factory;
		removeAll();
		setLayout(new FormLayout());
		initColours();
		initAccessible();
		topNavigationElement = new TopNavigationElement(this);
		bottomNavigationElement = new BottomNavigationElement(this);

		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				focus = true;
				int i = getSelectionIndex();
				if (i >= 0) {
					elements[i].redraw();
				}
			}

			public void focusLost(FocusEvent e) {
				focus = false;
				int i = getSelectionIndex();
				if (i >= 0) {
					elements[i].redraw();
				}
			}
		});
		this.addControlListener(new ControlAdapter() {

			public void controlResized(ControlEvent e) {
				computeTopAndBottomTab();
			}
		});
		this.addTraverseListener(new TraverseListener() {

			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS
					|| e.detail == SWT.TRAVERSE_ARROW_NEXT) {
					int nMax = elements.length - 1;
					int nCurrent = getSelectionIndex();
					if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
						nCurrent -= 1;
						nCurrent = Math.max(0, nCurrent);
					} else if (e.detail == SWT.TRAVERSE_ARROW_NEXT) {
						nCurrent += 1;
						nCurrent = Math.min(nCurrent, nMax);
					}
					select(nCurrent);
					redraw();
				} else {
					e.doit = true;
				}
			}
		});
	}

	protected void computeTabsThatFitInComposite() {
		tabsThatFitInComposite = Math
			.round((getSize().y - 22) / getTabHeight());
		if (tabsThatFitInComposite <= 0) {
			tabsThatFitInComposite = 1;
		}
	}

	public int getNumberOfElements() {
		return elements.length;
	}

	public Object getElementAt(int index) {
		if (index >= 0 && index < elements.length) {
			return elements[index];
		}
		return null;
	}

	public int getSelectionIndex() {
		return selectedElementIndex;
	}

	public int getWidestLabelIndex() {
		return widestLabelIndex;
	}

	public void removeAll() {
		if (elements != null) {
			for (int i = 0; i < elements.length; i++) {
				elements[i].dispose();
			}
		}
		elements = ELEMENTS_EMPTY;
		selectedElementIndex = NONE;
		widestLabelIndex = NONE;
		topVisibleIndex = NONE;
		bottomVisibleIndex = NONE;
	}

	public void setDynamicImageCount(Map tabToDynamicImageCountMap) {
		this.tabToDynamicImageCountMap = tabToDynamicImageCountMap;
	}

	public void setElements(Object[] children) {
		if (elements != ELEMENTS_EMPTY) {
			removeAll();
		}
		elements = new ListElement[children.length];
		if (children.length == 0) {
			widestLabelIndex = NONE;
		} else {
			widestLabelIndex = 0;
			for (int i = 0; i < children.length; i++) {
				int dynamicImageCount = 0;
				if (tabToDynamicImageCountMap != null
						&& tabToDynamicImageCountMap.containsKey(children[i])) {
					dynamicImageCount = ((Integer) tabToDynamicImageCountMap
							.get(children[i])).intValue();
				}
				elements[i] = new ListElement(this, (ITabItem) children[i],
						dynamicImageCount, i);
				elements[i].setVisible(false);
				elements[i].setLayoutData(null);

				if (i != widestLabelIndex) {
					int width = getTabWidth((ITabItem) children[i]);
					if (width > getTabWidth((ITabItem) children[widestLabelIndex])) {
						widestLabelIndex = i;
					}
				}
			}
		}

		computeTopAndBottomTab();
	}

	private int getTabWidth(ITabItem tabItem) {
		int width = getTextDimension(tabItem.getText()).x;
		if (tabItem.getImage() != null) {
			width = width + 16 + 4;
		}
		if (tabItem.isIndented()) {
			width = width + INDENT;
		}
		if (tabToDynamicImageCountMap != null) {
			int dynamicImageCount = 0;
			if (tabToDynamicImageCountMap.containsKey(tabItem)) {
				dynamicImageCount = ((Integer) tabToDynamicImageCountMap
						.get(tabItem)).intValue();
			}
			if (dynamicImageCount > 0) {
				width = width + 4;
				width = width + (dynamicImageCount * 16);
				width = width + ((dynamicImageCount - 1) * 3);
			}
		}
		return width;
	}

	protected void select(int index) {
		if (getSelectionIndex() == index) {
			return;
		}
		if (index >= 0 && index < elements.length) {
			int lastSelected = getSelectionIndex();
			elements[index].setSelected(true);
			selectedElementIndex = index;
			if (lastSelected != NONE) {
				elements[lastSelected].setSelected(false);
				if (getSelectionIndex() != elements.length - 1) {
					elements[getSelectionIndex() + 1].setSelected(false);
				}
			}
			topNavigationElement.redraw();
			bottomNavigationElement.redraw();

			if (selectedElementIndex < topVisibleIndex
				|| selectedElementIndex > bottomVisibleIndex) {
				computeTopAndBottomTab();
			}
		}
		notifyListeners(SWT.Selection, new Event());
	}

	public void deselectAll() {
		if (getSelectionIndex() != NONE) {
			elements[getSelectionIndex()].setSelected(false);
			selectedElementIndex = NONE;
		}
	}

	private int getIndex(ListElement element) {
		return element.index;
	}

	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point result = super.computeSize(hHint, wHint, changed);
		if (widestLabelIndex == -1) {
			String properties_not_available = TabbedPropertyMessages.TabbedPropertyList_properties_not_available;
			result.x = getTextDimension(properties_not_available).x + INDENT;
		} else {
			int width = getTabWidth(elements[widestLabelIndex].getTabItem()) + INDENT;
			result.x = width + 10;
		}
		return result;
	}

	private Point getTextDimension(String text) {
		GC gc = new GC(this);
		gc.setFont(JFaceResources.getFontRegistry().getBold(
				JFaceResources.DEFAULT_FONT));
		Point point = gc.textExtent(text);
		point.x++;
		gc.dispose();
		return point;
	}

	private void initColours() {
		listBackground = Display.getCurrent().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND);

		widgetBackground = Display.getCurrent().getSystemColor(
				SWT.COLOR_WIDGET_BACKGROUND);

		widgetDarkShadow = Display.getCurrent().getSystemColor(
				SWT.COLOR_WIDGET_DARK_SHADOW);

		widgetForeground = Display.getCurrent().getSystemColor(
				SWT.COLOR_WIDGET_FOREGROUND);

		widgetNormalShadow = Display.getCurrent().getSystemColor(
				SWT.COLOR_WIDGET_NORMAL_SHADOW);

		RGB infoBackground = Display.getCurrent().getSystemColor(
				SWT.COLOR_INFO_BACKGROUND).getRGB();
		RGB white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE)
				.getRGB();
		RGB black = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK)
				.getRGB();

		defaultGradientStart = factory.getColors().createColor(
				"TabbedPropertyList.defaultTabGradientStart", //$NON-NLS-1$
				FormColors.blend(infoBackground, FormColors.blend(white,
						widgetNormalShadow.getRGB(), 20), 60));
		defaultGradientEnd = factory.getColors().createColor(
				"TabbedPropertyList.defaultTabGradientEnd", //$NON-NLS-1$
				FormColors.blend(infoBackground, widgetNormalShadow.getRGB(),
						40));

		navigationElementShadowStroke = factory.getColors().createColor(
				"TabbedPropertyList.shadowStroke", //$NON-NLS-1$
				FormColors.blend(white, widgetNormalShadow.getRGB(), 55));
		bottomNavigationElementShadowStroke1 = factory.getColors().createColor(
				"TabbedPropertyList.tabShadowStroke1", //$NON-NLS-1$
				FormColors.blend(black, widgetBackground.getRGB(), 10));
		bottomNavigationElementShadowStroke2 = factory.getColors().createColor(
				"TabbedPropertyList.tabShadowStroke2", //$NON-NLS-1$
				FormColors.blend(black, widgetBackground.getRGB(), 5));

		hoverGradientStart = factory.getColors().createColor(
				"TabbedPropertyList.hoverBackgroundGradientStart", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 20));
		hoverGradientEnd = factory.getColors().createColor(
				"TabbedPropertyList.hoverBackgroundGradientEnd", //$NON-NLS-1$
				FormColors.blend(widgetNormalShadow.getRGB(), widgetBackground
						.getRGB(), 10));

		indentedDefaultBackground = factory.getColors().createColor(
				"TabbedPropertyList.indentedDefaultBackground", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 10));
		indentedHoverBackground = factory.getColors().createColor(
				"TabbedPropertyList.indentedHoverBackground", //$NON-NLS-1$
				FormColors.blend(white, widgetBackground.getRGB(), 75));
	}

	private int getTabHeight() {
		int tabHeight = getTextDimension("").y + INDENT; //$NON-NLS-1$ 
		if (tabsThatFitInComposite == 1) {
			int ret = getBounds().height - 20;
			return (ret > tabHeight) ? tabHeight
				: (ret < 5) ? 5
					: ret;
		}
		return tabHeight;
	}

	private boolean isDownScrollRequired() {
		return elements.length > tabsThatFitInComposite
			&& bottomVisibleIndex != elements.length - 1;
	}

	private boolean isUpScrollRequired() {
		return elements.length > tabsThatFitInComposite && topVisibleIndex != 0;
	}

	private void computeTopAndBottomTab() {
		computeTabsThatFitInComposite();
		if (elements.length == 0) {
			topVisibleIndex = 0;
			bottomVisibleIndex = 0;
		} else if (tabsThatFitInComposite >= elements.length) {
			topVisibleIndex = 0;
			bottomVisibleIndex = elements.length - 1;
		} else if (getSelectionIndex() == NONE) {
			topVisibleIndex = 0;
			bottomVisibleIndex = tabsThatFitInComposite - 1;
		} else if (getSelectionIndex() + tabsThatFitInComposite > elements.length) {
			bottomVisibleIndex = elements.length - 1;
			topVisibleIndex = bottomVisibleIndex - tabsThatFitInComposite + 1;
		} else {
			topVisibleIndex = selectedElementIndex;
			bottomVisibleIndex = selectedElementIndex + tabsThatFitInComposite
				- 1;
		}
		layoutTabs();
	}

	private void layoutTabs() {
		if (tabsThatFitInComposite == NONE || elements.length == 0) {
			FormData formData = new FormData();
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.top = new FormAttachment(0, 0);
			formData.height = getTabHeight();
			topNavigationElement.setLayoutData(formData);

			formData = new FormData();
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.top = new FormAttachment(topNavigationElement, 0);
			formData.bottom = new FormAttachment(100, 0);
			bottomNavigationElement.setLayoutData(formData);
		} else {

			FormData formData = new FormData();
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.top = new FormAttachment(0, 0);
			formData.height = 10;
			topNavigationElement.setLayoutData(formData);

			Canvas nextElement = topNavigationElement;

			for (int i = 0; i < elements.length; i++) {
				if (i < topVisibleIndex || i > bottomVisibleIndex) {
					elements[i].setLayoutData(null);
					elements[i].setVisible(false);
				} else {
					formData = new FormData();
					formData.height = getTabHeight();
					formData.left = new FormAttachment(0, 0);
					formData.right = new FormAttachment(100, 0);
					formData.top = new FormAttachment(nextElement, 0);
					nextElement = elements[i];
					elements[i].setLayoutData(formData);
					elements[i].setVisible(true);
				}

			}
			formData = new FormData();
			formData.left = new FormAttachment(0, 0);
			formData.right = new FormAttachment(100, 0);
			formData.top = new FormAttachment(nextElement, 0);
			formData.bottom = new FormAttachment(100, 0);
			formData.height = 10;
			bottomNavigationElement.setLayoutData(formData);
		}

		Composite grandparent = getParent().getParent();
		grandparent.layout(true);
		layout(true);
	}

	private void initAccessible() {
		final Accessible accessible = getAccessible();
		accessible.addAccessibleListener(new AccessibleAdapter() {

			public void getName(AccessibleEvent e) {
				if (getSelectionIndex() != NONE) {
					e.result = elements[getSelectionIndex()].getTabItem()
							.getText();
				}
			}

			public void getHelp(AccessibleEvent e) {
				if (getSelectionIndex() != NONE) {
					e.result = elements[getSelectionIndex()].getTabItem()
							.getText();
				}
			}
		});

		accessible.addAccessibleControlListener(new AccessibleControlAdapter() {

			public void getChildAtPoint(AccessibleControlEvent e) {
				Point pt = toControl(new Point(e.x, e.y));
				e.childID = (getBounds().contains(pt)) ? ACC.CHILDID_SELF
					: ACC.CHILDID_NONE;
			}

			public void getLocation(AccessibleControlEvent e) {
				if (getSelectionIndex() != NONE) {
					Rectangle location = elements[getSelectionIndex()]
						.getBounds();
					Point pt = toDisplay(new Point(location.x, location.y));
					e.x = pt.x;
					e.y = pt.y;
					e.width = location.width;
					e.height = location.height;
				}
			}

			public void getChildCount(AccessibleControlEvent e) {
				e.detail = 0;
			}

			public void getRole(AccessibleControlEvent e) {
				e.detail = ACC.ROLE_TABITEM;
			}

			public void getState(AccessibleControlEvent e) {
				e.detail = ACC.STATE_NORMAL | ACC.STATE_SELECTABLE
					| ACC.STATE_SELECTED | ACC.STATE_FOCUSED
					| ACC.STATE_FOCUSABLE;
			}
		});

		addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event event) {
				if (isFocusControl()) {
					accessible.setFocus(ACC.CHILDID_SELF);
				}
			}
		});

		addListener(SWT.FocusIn, new Listener() {

			public void handleEvent(Event event) {
				accessible.setFocus(ACC.CHILDID_SELF);
			}
		});
	}
}
