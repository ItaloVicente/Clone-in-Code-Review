package org.eclipse.e4.ui.progress.internal;

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
