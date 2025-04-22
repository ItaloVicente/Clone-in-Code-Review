package org.eclipse.jgit.http.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.UploadPack;

public interface UploadPackErrorHandler {
	void upload(HttpServletRequest req
			UploadPackRunnable r) throws IOException;

	public interface UploadPackRunnable {
		void upload() throws ServiceMayNotContinueException
	}
}
