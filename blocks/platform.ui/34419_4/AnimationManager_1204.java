package org.eclipse.ui.internal.progress;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.WorkbenchWindow;

public abstract class AnimationItem {
    WorkbenchWindow window;

    interface IAnimationContainer {
        public abstract void animationStart();

        public abstract void animationDone();
    }

    IAnimationContainer animationContainer = new IAnimationContainer() {
        @Override
		public void animationDone() {
        }

        @Override
		public void animationStart() {
        }
    };

    public AnimationItem(WorkbenchWindow workbenchWindow) {
        this.window = workbenchWindow;
    }

    public void createControl(Composite parent) {

        Control animationItem = createAnimationItem(parent);

        animationItem.addMouseListener(new MouseListener() {
            @Override
			public void mouseDoubleClick(MouseEvent arg0) {
                ProgressManagerUtil.openProgressView(AnimationItem.this.window);
            }

            @Override
			public void mouseDown(MouseEvent arg0) {
            }

            @Override
			public void mouseUp(MouseEvent arg0) {
            }
        });
        animationItem.addDisposeListener(new DisposeListener() {
            @Override
			public void widgetDisposed(DisposeEvent e) {
                AnimationManager.getInstance().removeItem(AnimationItem.this);
            }
        });
        AnimationManager.getInstance().addItem(this);
    }

    protected abstract Control createAnimationItem(Composite parent);

    void paintImage(PaintEvent event, Image image, ImageData imageData) {
        event.gc.drawImage(image, 0, 0);
    }

    public abstract Control getControl();

    void animationStart() {
        animationContainer.animationStart();
    }

    void animationDone() {
        animationContainer.animationDone();
    }

    public int getPreferredWidth() {
        return AnimationManager.getInstance().getPreferredWidth() + 5;
    }

    void setAnimationContainer(IAnimationContainer container) {
        this.animationContainer = container;
    }

	public WorkbenchWindow getWindow() {
		return window;
	}
}
