/*******************************************************************************
 * Copyright (c) 2010, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Patrik Suzzi <psuzzi@gmail.com> - Bug 497348
 ******************************************************************************/

package org.eclipse.e4.ui.workbench.addons.dndaddon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MStackElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * This agent manage drag and drop when dragging a Tab in Eclipse Part Stacks.
 */
public class StackDropAgent extends DropAgent {
	private Rectangle tabArea;
	private MPartStack dropStack;
	private CTabFolder dropCTF;

	/**
	 * @param manager
	 */
	public StackDropAgent(DnDManager manager) {
		super(manager);
	}

	@Override
	public boolean canDrop(MUIElement dragElement, DnDInfo info) {
		if (!(dragElement instanceof MStackElement) && !(dragElement instanceof MPartStack)) {
			return false;
		}

		if (!(info.curElement instanceof MPartStack)) {
			return false;
		}

		MPartStack stack = (MPartStack) info.curElement;

		if (stack.getTags().contains(IPresentationEngine.STANDALONE)) {
			return false;
		}

		if (!(stack.getWidget() instanceof CTabFolder)) {
			return false;
		}

		if (stack == dragElement) {
			return false;
		}

			EModelService ms = dndManager.getModelService();
			MWindow dragElementWin = ms.getTopLevelWindowFor(dragElement);
			MWindow dropWin = ms.getTopLevelWindowFor(stack);
			if (dragElementWin != dropWin)
			 {
				return false;
			}

		Rectangle areaRect = getTabAreaRect((CTabFolder) stack.getWidget());
		boolean inArea = areaRect.contains(info.cursorPos);
		if (inArea) {
			tabArea = areaRect;
			dropStack = (MPartStack) info.curElement;
			dropCTF = (CTabFolder) dropStack.getWidget();
		}
		return inArea;
	}

	private Rectangle getTabAreaRect(CTabFolder theCTF) {
		Rectangle ctfBounds = theCTF.getBounds();
		ctfBounds.height = theCTF.getTabHeight();

		Rectangle displayBounds = Display.getCurrent().map(theCTF.getParent(), null, ctfBounds);
		return displayBounds;
	}

	/**
	 * Static helper to get visible items without using member variables in this
	 * stateful Agent.
	 *
	 * @param dropCTF
	 * @return
	 */
	private static List<CTabItem> getVisibleItems(CTabFolder dropCTF) {
		return Stream.of(dropCTF.getItems())
			.filter(i -> i.isShowing())
			.collect(Collectors.toList());
	}

	/**
	 * Static helper to compute the visual rectangles to drop, without using
	 * member variables in this stateful Agent.
	 *
	 * @param dropCTF
	 * @param visibleItems
	 * @param tabArea
	 * @return
	 */
	private static ArrayList<Rectangle> getItemRects(CTabFolder dropCTF, List<CTabItem> visibleItems,
			Rectangle tabArea) {
		ArrayList<Rectangle> itemRects = new ArrayList<Rectangle>();
		CTabItem item;
		if (visibleItems.size() > 0) {
			item = visibleItems.get(0);
			Rectangle itemRect = item.getBounds();
			int centerX = itemRect.x + (itemRect.width / 2);
			itemRect.width /= 2;
			int curX = itemRect.x + itemRect.width;
			Rectangle insertRect = dropCTF.getDisplay().map(dropCTF, null, itemRect);
			itemRects.add(insertRect);

			for (int i = 1; i < visibleItems.size(); i++) {
				item = visibleItems.get(i);
				itemRect = item.getBounds();
				centerX = itemRect.x + (itemRect.width / 2);
				itemRect.width = centerX - curX;
				itemRect.x = curX;
				curX = centerX;
				insertRect = dropCTF.getDisplay().map(dropCTF, null, itemRect);
				itemRects.add(insertRect);
			}
			itemRect.x = curX;
			itemRect.width = dropCTF.getBounds().width - curX;
			insertRect = dropCTF.getDisplay().map(dropCTF, null, itemRect);
			itemRects.add(insertRect);
		} else {
			itemRects.add(tabArea);
		}
		return itemRects;
	}

	private ArrayList<Rectangle> computeInsertRects() {
		List<CTabItem> visibleItems = getVisibleItems(dropCTF);
		return getItemRects(dropCTF, visibleItems, tabArea);
	}

	private int getDropIndex(DnDInfo info) {
		ArrayList<Rectangle> itemRects = computeInsertRects();
		if (itemRects == null) {
			return -1;
		}

		for (Rectangle itemRect : itemRects) {
			if (itemRect.contains(info.cursorPos)) {
				return itemRects.indexOf(itemRect);
			}
		}
		return -1;
	}

	@Override
	public void dragLeave(MUIElement dragElement, DnDInfo info) {
		dndManager.clearOverlay();

		if (dndManager.getFeedbackStyle() == DnDManager.HOSTED) {
			if (dragElement.getParent() != null) {
				dndManager.hostElement(dragElement, 16, 10);
			}
		} else {
			dndManager.setHostBounds(null);
		}

		tabArea = null;

		super.dragLeave(dragElement, info);
	}

	/**
	 * Tracks movements of mouse on the Stack where the user is dropping the element
	 */
	@Override
	public boolean track(MUIElement dragElement, DnDInfo info) {
		if (!tabArea.contains(info.cursorPos) || dropStack == null || !dropStack.isToBeRendered()) {
			return false;
		}

		int dropIndex = getDropIndex(info);
		if (dropIndex == -1) {
			return true;
		}

		dndManager.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND));

		if (dropStack.getChildren().indexOf(dragElement) == dropIndex){
			return true;
		}
		if (dndManager.getFeedbackStyle() == DnDManager.HOSTED) {
			dock(dragElement, dropIndex);
			Display.getCurrent().update();
			showFrame(dragElement);
		} else {
			List<CTabItem> visibleItems = getVisibleItems(dropCTF);
			if (dropIndex < visibleItems.size()) {
				Rectangle itemBounds = visibleItems.get(dropIndex).getBounds();
				itemBounds.width = 2;
				itemBounds = Display.getCurrent().map(dropCTF, null, itemBounds);
				dndManager.frameRect(itemBounds);
			} else if (visibleItems.size() > 0) {
				Rectangle itemBounds = visibleItems.get(dropIndex - 1).getBounds();
				itemBounds.x = itemBounds.x + itemBounds.width;
				itemBounds.width = 2;
				itemBounds = Display.getCurrent().map(dropCTF, null, itemBounds);
				dndManager.frameRect(itemBounds);
			} else {
				Rectangle fr = new Rectangle(tabArea.x, tabArea.y, tabArea.width, tabArea.height);
				fr.width = 2;
				dndManager.frameRect(fr);
			}
			if (dndManager.getFeedbackStyle() == DnDManager.GHOSTED) {
				Rectangle ca = dropCTF.getClientArea();
				ca = Display.getCurrent().map(dropCTF, null, ca);
				dndManager.setHostBounds(ca);
			}
		}

		return true;
	}

	/**
	 * @param dragElement
	 * @param dropIndex
	 */
	private void dock(MUIElement dragElement, int dropIndex) {

		List<CTabItem> vItems = getVisibleItems(dropCTF);
		boolean hiddenTabs = (vItems.size() < dropCTF.getChildren().length);

		int elementIndex = dropStack.getChildren().indexOf(dragElement);
		if (elementIndex != -1 && !(dragElement instanceof MPartStack)) {
			Control dragCtrl = (Control) dragElement.getWidget();
			for (CTabItem cti : dropCTF.getItems()) {
				if (dragCtrl == cti.getControl()) {
					int itemIndex = dropCTF.indexOf(cti);
					if (dropIndex > 0 && itemIndex < dropIndex) {
						dropIndex--;
					}
				}
			}
		}


		if (hiddenTabs) {
			int nVisibleItems = vItems.size();
			if(dropIndex<nVisibleItems){
				CTabItem item = vItems.get(dropIndex);
				MUIElement itemModel = (MUIElement) item.getData(AbstractPartRenderer.OWNING_ME);

				if (itemModel == dragElement) {
					return;
				}
				dropIndex = itemModel.getParent().getChildren().indexOf(itemModel);
			} else if (dropIndex == nVisibleItems) {
				dropIndex = dropStack.getChildren().size();
			}


		} else {
			int ctfItemCount = dropCTF.getItemCount();
			if (dropIndex < ctfItemCount) {
				CTabItem item = dropCTF.getItem(dropIndex);
				MUIElement itemModel = (MUIElement) item.getData(AbstractPartRenderer.OWNING_ME);

				if (itemModel == dragElement) {
					return;
				}

				dropIndex = itemModel.getParent().getChildren().indexOf(itemModel);
			} else if (dropIndex == ctfItemCount) {
				dropIndex = dropStack.getChildren().size();
			}
		}

		if (dragElement instanceof MStackElement) {
			if (dragElement.getParent() != null) {
				dragElement.getParent().getChildren().remove(dragElement);
			}

			if (dropIndex >= 0 && dropIndex < dropStack.getChildren().size()) {
				dropStack.getChildren().add(dropIndex, (MStackElement) dragElement);
			} else {
				dropStack.getChildren().add((MStackElement) dragElement);
			}

			dropStack.setSelectedElement((MStackElement) dragElement);
		} else {
			MPartStack stack = (MPartStack) dragElement;
			MStackElement curSel = stack.getSelectedElement();
			List<MStackElement> kids = stack.getChildren();

			int selIndex = kids.indexOf(curSel);
			boolean curSelProcessed = false;
			while (kids.size() > 1) {
				MStackElement kid = curSelProcessed ? kids.get(kids.size() - 2) : kids.get(kids.size() - 1);
				if (kid == curSel) {
					curSelProcessed = true;
					continue;
				}

				kids.remove(kid);
				if (dropIndex >= 0 && dropIndex < dropStack.getChildren().size()) {
					dropStack.getChildren().add(dropIndex, kid);
				} else {
					dropStack.getChildren().add(kid);
				}
			}

			kids.remove(curSel);
			dropIndex = dropIndex + selIndex;
			if (dropIndex >= 0 && dropIndex < dropStack.getChildren().size()) {
				dropStack.getChildren().add(dropIndex, curSel);
			} else {
				dropStack.getChildren().add(curSel);
			}

			dropStack.setSelectedElement(curSel);
		}
	}

	/**
	 * @param dragElement
	 */
	private void showFrame(MUIElement dragElement) {
		CTabFolder ctf = (CTabFolder) dropStack.getWidget();
		CTabItem[] items = ctf.getItems();
		CTabItem item = null;
		for (CTabItem tabItem : items) {
			if (tabItem.getData(AbstractPartRenderer.OWNING_ME) == dragElement) {
				item = tabItem;
				break;
			}
		}

		Rectangle bounds = item.getBounds();
		bounds = Display.getCurrent().map(dropCTF, null, bounds);
		Rectangle outerBounds = new Rectangle(bounds.x - 3, bounds.y - 3, bounds.width + 6,
				bounds.height + 6);

		dndManager.frameRect(outerBounds);
	}

	@Override
	public boolean drop(MUIElement dragElement, DnDInfo info) {
		if (dndManager.getFeedbackStyle() != DnDManager.HOSTED) {
			int dropIndex = getDropIndex(info);
			if (dropIndex != -1) {
				MUIElement toActivate = dragElement instanceof MPartStack ? ((MPartStack) dragElement)
						.getSelectedElement() : dragElement;
				dock(dragElement, dropIndex);
				reactivatePart(toActivate);
			}
		}
		return true;
	}
}
