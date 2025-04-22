package org.eclipse.e4.ui.progress.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.ui.progress.internal.legacy.PlatformUI;

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

    public void addItem(AnimationItem item) {
        Assert.isTrue(item instanceof ProgressAnimationItem);
        items.add(item);
    }

    public void removeItem(AnimationItem item) {
        Assert.isTrue(item instanceof ProgressAnimationItem);
        items.remove(item);
    }

    public boolean hasItems() {
        return items.size() > 0;
    }

    public void itemsInactiveRedraw() {

    }

    public void animationStarted() {
        AnimationItem[] animationItems = getAnimationItems();
        for (int i = 0; i < animationItems.length; i++) {
            animationItems[i].animationStart();
        }

    }

    public int getPreferredWidth() {
        return 30;
    }

    private ProgressAnimationItem[] getAnimationItems() {
        ProgressAnimationItem[] animationItems = new ProgressAnimationItem[items
                .size()];
        items.toArray(animationItems);
        return animationItems;
    }

    public void animationFinished() {
        AnimationItem[] animationItems = getAnimationItems();
        for (int i = 0; i < animationItems.length; i++) {
            animationItems[i].animationDone();
        }

    }

    public boolean isProcessorJob(Job job) {
        return false;
    }

}
