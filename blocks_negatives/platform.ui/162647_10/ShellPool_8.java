/*******************************************************************************
 * Copyright (c) 2007, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Creates an animation effect where the interpolated rectangles are displayed
 * using Canvas controls that show an image of the bits that were originally
 * occupied by the various 'start' rectangles.
 *
 * @since 3.3
 *
 */
public class RectangleAnimationImageFeedback extends RectangleAnimationFeedbackBase {
	private static class ImageCanvas extends Canvas {
		private Image image;

		/**
		 * @param parent
		 * @param style
		 */
		public ImageCanvas(Composite parent, int style, Image image) {
			super(parent, style);
			this.image = image;

			addPaintListener(e -> paintImage(e.gc));
		}

		/**
		 * @param gc
		 */
		protected void paintImage(GC gc) {
			gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, getBounds().width,
					getBounds().height);
		}

		@Override
		public void dispose() {
			super.dispose();
			image.dispose();
		}
	}

	private Image backingStore;
	private Shell theShell;
	private Display display;
	private List<ImageCanvas> controls = new ArrayList<>();

	public RectangleAnimationImageFeedback(Shell parentShell, Rectangle start, Rectangle end) {
		super(parentShell, start, end);
	}

	@Override
	public void dispose() {
		backingStore.dispose();
		for (Iterator<ImageCanvas> ctrlIter = controls.iterator(); ctrlIter.hasNext();) {
			ImageCanvas canvas = ctrlIter.next();
			canvas.dispose();
		}

		theShell.setVisible(false);
		theShell.dispose();
	}

	@Override
	public void initialize(AnimationEngine engine) {
		display = getAnimationShell().getDisplay();

		Rectangle psRect = getAnimationShell().getBounds();
		theShell = new Shell(getAnimationShell(), SWT.NO_TRIM | SWT.ON_TOP);
		theShell.setBounds(getAnimationShell().getBounds());

		backingStore = new Image(theShell.getDisplay(), psRect);
		GC gc = new GC(display);
		gc.copyArea(backingStore, psRect.x, psRect.y);
		gc.dispose();
		theShell.setBackgroundImage(backingStore);
		theShell.setVisible(true);
		display.update();

	}

	@Override
	public boolean jobInit(AnimationEngine engine) {
		changeCoordinates();
		captureImages();
		return super.jobInit(engine);
	}

	@Override
	public void addStartRect(Rectangle rect) {
		if (rect == null)
			return;

		super.addStartRect(rect);

	}

	@Override
	public void addEndRect(Rectangle rect) {
		if (rect != null) {
			super.addEndRect(rect);
		}
	}

	@Override
	public void renderStep(AnimationEngine engine) {
		Iterator<ImageCanvas> ctrlIter = controls.iterator();
		Iterator<Rectangle> currentRects = getCurrentRects(engine.amount()).iterator();
		while (currentRects.hasNext()) {
			ImageCanvas canvas = ctrlIter.next();
			canvas.setBounds(currentRects.next());
		}
		display.update();

	}

	public void changeCoordinates() {
		Iterator<Rectangle> startRectIter = getStartRects().iterator();
		Iterator<Rectangle> endRectIter = getEndRects().iterator();
		while (startRectIter.hasNext()) {
			Rectangle startRect = startRectIter.next();
			Rectangle mapStartRect = Geometry.toControl(theShell, startRect);
			startRect.x = mapStartRect.x;
			startRect.y = mapStartRect.y;
			Rectangle endRect = endRectIter.next();
			Rectangle mapEndRect = Geometry.toControl(theShell, endRect);
			endRect.x = mapEndRect.x;
			endRect.y = mapEndRect.y;
		}
	}

	private void captureImages() {

		for (Iterator<Rectangle> iterator = getStartRects().iterator(); iterator.hasNext();) {
			Rectangle rect = iterator.next();
			Image image = new Image(display, rect.width, rect.height);
			GC gc = new GC(backingStore);
			gc.copyArea(image, rect.x, rect.y);
			gc.dispose();
			ImageCanvas canvas = new ImageCanvas(theShell, SWT.BORDER | SWT.NO_BACKGROUND, image);
			controls.add(canvas);

		}
	}

}
