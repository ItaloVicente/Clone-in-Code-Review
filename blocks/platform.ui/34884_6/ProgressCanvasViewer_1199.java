package org.eclipse.ui.internal.progress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;

class ProgressAnimationProcessor implements IAnimationProcessor {

    AnimationManager manager;

    ProgressAnimationProcessor(AnimationManager animationManager) {
        manager = animationManager;
    }

    List items = Collections.synchronizedList(new ArrayList());

    public void startAnimationLoop(IProgressMonitor monitor) {

        if (items.size() == 0) {
			return;
		}
        if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}

        while (manager.isAnimated() && !monitor.isCanceled()) {
        }

        ProgressAnimationItem[] animationItems = getAnimationItems();
        for (int i = 0; i < animationItems.length; i++) {
            animationItems[i].animationDone();
        }

    }

    @Override
	public void addItem(AnimationItem item) {
        Assert.isTrue(item instanceof ProgressAnimationItem);
        items.add(item);
    }

    @Override
	public void removeItem(AnimationItem item) {
        Assert.isTrue(item instanceof ProgressAnimationItem);
        items.remove(item);
    }

    @Override
	public boolean hasItems() {
        return items.size() > 0;
    }

    public void itemsInactiveRedraw() {

    }

    @Override
	public void animationStarted() {
        AnimationItem[] animationItems = getAnimationItems();
        for (int i = 0; i < animationItems.length; i++) {
            animationItems[i].animationStart();
        }

    }

    @Override
	public int getPreferredWidth() {
        return 30;
    }

    private ProgressAnimationItem[] getAnimationItems() {
        ProgressAnimationItem[] animationItems = new ProgressAnimationItem[items
                .size()];
        items.toArray(animationItems);
        return animationItems;
    }

    @Override
	public void animationFinished() {
        AnimationItem[] animationItems = getAnimationItems();
        for (int i = 0; i < animationItems.length; i++) {
            animationItems[i].animationDone();
        }

    }

    @Override
	public boolean isProcessorJob(Job job) {
        return false;
    }

}
