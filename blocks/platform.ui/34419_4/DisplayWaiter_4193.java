package org.eclipse.ui.tests.harness.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;

final class DisplayWaiter {
	public final class Timeout {
		boolean fTimeoutState= false;
		public boolean hasTimedOut() {
			synchronized (fMutex) {
				return fTimeoutState;
			}
		}
		void setTimedOut(boolean timedOut) {
			fTimeoutState= timedOut;
		}
		Timeout(boolean initialState) {
			fTimeoutState= initialState;
		}
	}

	private final Display fDisplay;
	private final Object fMutex= new Object();
	private final boolean fKeepRunningOnTimeout;

	private static final int RUNNING= 1 << 1;
	private static final int STOPPED= 1 << 2;
	private static final int IDLE= 1 << 3;

	private int fState;
	private long fNextTimeout;
	private Thread fCurrentThread;
	private Timeout fCurrentTimeoutState;

	public DisplayWaiter(Display display) {
		this(display, false);
	}

	public DisplayWaiter(Display display, boolean keepRunning) {
		Assert.assertNotNull(display);
		fDisplay= display;
		fState= STOPPED;
		fKeepRunningOnTimeout= keepRunning;
	}

	public Timeout start(long delay) {
		Assert.assertTrue(delay > 0);
		synchronized (fMutex) {
			switch (fState) {
				case STOPPED:
					startThread();
					setNextTimeout(delay);
					break;
				case IDLE:
					unhold();
					setNextTimeout(delay);
					break;
			}

			return fCurrentTimeoutState;
		}
	}

	private void setNextTimeout(long delay) {
		long currentTimeMillis= System.currentTimeMillis();
		long next= currentTimeMillis + delay;
		if (next > currentTimeMillis)
			fNextTimeout= next;
		else
			fNextTimeout= Long.MAX_VALUE;
	}

	public Timeout restart(long delay) {
		Assert.assertTrue(delay > 0);
		synchronized (fMutex) {
			switch (fState) {
				case STOPPED:
					startThread();
					break;
				case IDLE:
					unhold();
					break;
			}
			setNextTimeout(delay);

			return fCurrentTimeoutState;
		}
	}

	public void stop() {
		synchronized (fMutex) {
			if (tryTransition(RUNNING | IDLE, STOPPED))
				fMutex.notifyAll();
		}
	}

	public void hold() {
		synchronized (fMutex) {
			if (tryTransition(RUNNING, IDLE))
				fMutex.notifyAll();
		}
	}

	private void unhold() {
		checkedTransition(IDLE, RUNNING);
		fCurrentTimeoutState= new Timeout(false);
		fMutex.notifyAll();
	}

	private void startThread() {
		checkedTransition(STOPPED, RUNNING);
		fCurrentTimeoutState= new Timeout(false);
		fCurrentThread= new Thread() {
			final class ThreadChangedException extends Exception {
				private static final long serialVersionUID= 1L;
			}

			public void run() {
				try {
					run2();
				} catch (InterruptedException e) {
					Logger.global.log(Level.FINE, "", e); //$NON-NLS-1$
				} catch (ThreadChangedException e) {
					Logger.global.log(Level.FINE, "", e); //$NON-NLS-1$
					synchronized (fMutex) {
						fMutex.notifyAll();
					}
				}
			}

			private void run2() throws InterruptedException, ThreadChangedException {
				synchronized (fMutex) {
					checkThread();
					tryHold(); // wait / potential state change
					assertStates(STOPPED | RUNNING);

					while (isState(RUNNING)) {
						waitForTimeout(); // wait / potential state change

						if (isState(RUNNING))
							timedOut(); // state change
						assertStates(STOPPED | IDLE);

						tryHold(); // wait / potential state change
						assertStates(STOPPED | RUNNING);
					}
					assertStates(STOPPED);
				}
			}

			private void checkThread() throws ThreadChangedException {
				if (fCurrentThread != this)
					throw new ThreadChangedException();
			}

			private void waitForTimeout() throws InterruptedException, ThreadChangedException {
				long delta;
				while (isState(RUNNING) && (delta = fNextTimeout - System.currentTimeMillis()) > 0) {
					delta= Math.max(delta, 50); // wait at least 50ms in order to avoid timing out before the display is going to sleep
					Logger.global.finest("sleeping for " + delta + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
					fMutex.wait(delta);
					checkThread();
				}
			}

			private void timedOut() {
				Logger.global.finer("timed out"); //$NON-NLS-1$
				fCurrentTimeoutState.setTimedOut(true);
				fDisplay.wake(); // wake up call!
				if (fKeepRunningOnTimeout)
					checkedTransition(RUNNING, IDLE);
				else
					checkedTransition(RUNNING, STOPPED);
			}

			private void tryHold() throws InterruptedException, ThreadChangedException {
				while (isState(IDLE)) {
					fMutex.wait(0);
					checkThread();
				}
				assertStates(STOPPED | RUNNING);
			}
		};

		fCurrentThread.start();
	}

	private boolean tryTransition(int possibleStates, int nextState) {
		if (isState(possibleStates)) {
			Logger.global.finer(name(fState) + " > " + name(nextState) + " (" + name(possibleStates) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			fState= nextState;
			return true;
		}
		Logger.global.finest("noTransition" + name(fState) + " !> " + name(nextState) + " (" + name(possibleStates) + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		return false;
	}

	private void checkedTransition(int possibleStates, int nextState) {
		assertStates(possibleStates);
		Logger.global.finer(name(fState) + " > " + name(nextState)); //$NON-NLS-1$
		fState= nextState;
	}

	private void assertStates(int states) {
		Assert.assertTrue("illegal state", isState(states)); //$NON-NLS-1$
	}

	private boolean isState(int states) {
		return (states & fState) == fState;
	}

	private String name(int states) {
		StringBuffer buf= new StringBuffer();
		boolean comma= false;
		if ((states & RUNNING) == RUNNING) {
			buf.append("RUNNING"); //$NON-NLS-1$
			comma= true;
		}
		if ((states & STOPPED) == STOPPED) {
			if (comma)
				buf.append(","); //$NON-NLS-1$
			buf.append("STOPPED"); //$NON-NLS-1$
			comma= true;
		}
		if ((states & IDLE) == IDLE) {
			if (comma)
				buf.append(","); //$NON-NLS-1$
			buf.append("IDLE"); //$NON-NLS-1$
		}
		return buf.toString();
	}

}
