package org.eclipse.jgit.internal.transport.sshd.agent.connector;

import java.text.MessageFormat;

import com.sun.jna.LastErrorException;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.User32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LibraryHolder {

	private static final Logger LOG = LoggerFactory
			.getLogger(LibraryHolder.class);

	private static LibraryHolder INSTANCE;

	private static boolean libraryLoaded = false;

	public static synchronized LibraryHolder getLibrary() {
		if (!libraryLoaded) {
			libraryLoaded = true;
			try {
				INSTANCE = new LibraryHolder();
			} catch (Exception | UnsatisfiedLinkError
					| NoClassDefFoundError e) {
				LOG.error(Texts.get().logErrorLoadLibrary
			}
		}
		return INSTANCE;
	}

	User32 user;

	Kernel32 kernel;

	private LibraryHolder() {
		user = User32.INSTANCE;
		kernel = Kernel32.INSTANCE;
	}

	String systemError(String pattern) {
		int lastError = kernel.GetLastError();
		String msg;
		try {
			msg = Kernel32Util.formatMessageFromLastErrorCode(lastError);
		} catch (Exception e) {
			String err = e instanceof LastErrorException
					? Integer.toString(((LastErrorException) e).getErrorCode())
					: Texts.get().errUnknown;
			msg = MessageFormat.format(Texts.get().errLastError
					Integer.toString(lastError)
			LOG.error(msg
		}
		return MessageFormat.format(pattern
	}

}
