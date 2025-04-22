package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;

public interface ReceivePackErrorHandler {
	void receive(HttpServletRequest req
			ReceivePackRunnable r) throws IOException;

	public interface ReceivePackRunnable {
		void receive() throws ServiceMayNotContinueException
	}

}
