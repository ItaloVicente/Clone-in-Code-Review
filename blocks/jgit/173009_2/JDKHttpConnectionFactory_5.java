package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.eclipse.jgit.annotations.NonNull;

public interface HttpConnectionFactory2 extends HttpConnectionFactory {

	@NonNull
	GitSession newSession();

	interface GitSession {

		@NonNull
		HttpConnection configure(@NonNull HttpConnection connection
				boolean sslVerify) throws IOException

		void close();
	}
}
