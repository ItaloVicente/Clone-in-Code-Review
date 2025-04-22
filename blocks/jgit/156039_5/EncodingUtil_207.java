package org.eclipse.jgit.niofs.internal.util;

import java.util.concurrent.ThreadFactory;

public class DescriptiveThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(final Runnable r) {
		if (r instanceof DescriptiveRunnable) {
			return new Thread(r
		}
		return new Thread(r);
	}
}
