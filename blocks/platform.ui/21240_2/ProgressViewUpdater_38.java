package org.eclipse.e4.ui.progress.internal;

import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.progress.legacy.TrimUtil;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class ProgressRegion {
    ProgressCanvasViewer viewer;

    ProgressAnimationItem animationItem;

    Composite region;

	private int fWidthHint = SWT.DEFAULT;
	
	private int fHeightHint = SWT.DEFAULT;

	private int side = SWT.BOTTOM;
	
	private boolean forceHorizontal;

    public ProgressRegion() {
    }

    @PostConstruct
    public Control createContents(Composite parent) {
        GC gc = new GC(parent);
        gc.setAdvanced(true);
        forceHorizontal = !gc.getAdvanced();
        gc.dispose();
        
        region = new Composite(parent, SWT.NONE) {
			public Point computeSize(int wHint, int hHint, boolean changed) {
				Point size = super.computeSize(wHint, hHint, changed);
				if (isHorizontal(side))
					size.y = TrimUtil.TRIM_DEFAULT_HEIGHT;
				else {
					size.x = TrimUtil.TRIM_DEFAULT_HEIGHT;
				}
				return size;
			}
		};
		
        GridLayout gl = new GridLayout();
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        if (isHorizontal(side))
        	gl.numColumns = 3;
        region.setLayout(gl);

        viewer = new ProgressCanvasViewer(region, SWT.NO_FOCUS, 1, 36, isHorizontal(side) ? SWT.HORIZONTAL : SWT.VERTICAL);
        viewer.setUseHashlookup(true);
        Control viewerControl = viewer.getControl();
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        Point viewerSizeHints = viewer.getSizeHints();
        if (isHorizontal(side)) {
        	gd.widthHint = viewerSizeHints.x;
        	gd.heightHint = viewerSizeHints.y;
        } else {
        	gd.widthHint = viewerSizeHints.y;
        	gd.heightHint = viewerSizeHints.x;
        }
        viewerControl.setLayoutData(gd);

        int widthPreference = AnimationManager.getInstance()
                .getPreferredWidth() + 25;
        animationItem = new ProgressAnimationItem(this, isHorizontal(side) ? SWT.HORIZONTAL : SWT.VERTICAL);
        animationItem.createControl(region);

        animationItem.setAnimationContainer(new AnimationItem.IAnimationContainer() {
            public void animationDone() {
                if (viewer.getControl().isDisposed()) {
					return;
				}
                viewer.refresh();
            }

            public void animationStart() {

            }
        });
        if (isHorizontal(side)) {
        	gd = new GridData(GridData.FILL_VERTICAL);
            gd.widthHint = widthPreference;
        } else {
        	gd = new GridData(GridData.FILL_HORIZONTAL);
            gd.heightHint = widthPreference;
        }

        animationItem.getControl().setLayoutData(gd);

        viewerControl.addMouseListener(new MouseAdapter() {
            public void mouseDoubleClick(MouseEvent e) {
                processDoubleClick();
            }
        });

        IContentProvider provider = new ProgressViewerContentProvider(viewer,
                false,false);
        viewer.setContentProvider(provider);
        viewer.setInput(provider);
        viewer.setLabelProvider(new ProgressViewerLabelProvider(viewerControl));
        viewer.setComparator(ProgressManagerUtil.getProgressViewerComparator());
        viewer.addFilter(new ViewerFilter() {
            public boolean select(Viewer viewer, Object parentElement, Object element) {
                if (element instanceof JobInfo) {
                    JobInfo info= (JobInfo)element;
                    if (info.isBlocked() || info.getJob().getState() == Job.WAITING) {
                    	return false;
                    }
                }
                return true;
            }
        	
		});
        return region;
    }

    public AnimationItem getAnimationItem() {
        return animationItem;
    }

    public Control getControl() {
        return region;
    }

    public void processDoubleClick() {
        ProgressManagerUtil.openProgressView();
    }

	public void dock(int dropSide) {
		int oldSide = side;
		side = dropSide;
		if (oldSide == dropSide || (isVertical(oldSide) && isVertical(dropSide)) || (isHorizontal(oldSide) && isHorizontal(dropSide)))
			return;
		recreate();
		
	}

	private boolean isHorizontal(int dropSide) {
		if (forceHorizontal)
			return true;
		return dropSide == SWT.TOP || dropSide == SWT.BOTTOM;
	}


	private boolean isVertical(int dropSide) {
		if (forceHorizontal)
			return false;
		return dropSide == SWT.LEFT || dropSide == SWT.RIGHT;
	}

	private void recreate() {
		if (region != null && !region.isDisposed()) {
			Composite parent = region.getParent();
			boolean animating = animationItem.animationRunning();
	        AnimationManager.getInstance().removeItem(animationItem);
			region.dispose();
			createContents(parent);
			if (animating)
				animationItem.animationStart();
		}
	}

	public String getId() {
		return "org.eclipse.ui.internal.progress.ProgressRegion"; //$NON-NLS-1$
	}

	public String getDisplayName() {
		return "TRIM NAME";
	}

	public int getValidSides() {
		return SWT.BOTTOM | SWT.TOP | SWT.LEFT | SWT.RIGHT ;
	}

	public boolean isCloseable() {
		return false;
	}

	public void handleClose() {
	}

	public int getWidthHint() {
		return fWidthHint;
	}
	
	public void setWidthHint(int w) {
		fWidthHint = w;
	}

	public int getHeightHint() {
		return fHeightHint;
	}
	
	public void setHeightHint(int h) {
		fHeightHint = h;
	}

	public boolean isResizeable() {
		return false;
	}
}
