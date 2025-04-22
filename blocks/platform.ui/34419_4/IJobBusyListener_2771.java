package org.eclipse.ui.internal.progress;

import org.eclipse.core.runtime.jobs.Job;

interface IAnimationProcessor {

    void addItem(AnimationItem item);

    void removeItem(AnimationItem item);

    boolean hasItems();

    void animationStarted();

    void animationFinished();

    int getPreferredWidth();

    boolean isProcessorJob(Job job);

}
