package org.eclipse.jgit.transport.http.apache;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.AbstractHttpMessage;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HttpClientConnectionTest {
	@Test
	public void testGetHeaderFieldsAllowMultipleValues()
			throws MalformedURLException {
		HttpResponse responseMock = new HttpResponseMock();
		String headerField = "WWW-Authenticate";
		responseMock.addHeader(headerField
		responseMock.addHeader(headerField
		responseMock.addHeader(headerField
		HttpClientConnection connection = new HttpClientConnection(
		connection.resp = responseMock;
		List<String> headerValues = connection.getHeaderFields()
				.get(headerField);
		assertEquals(3
		assertTrue(headerValues.contains("Basic"));
		assertTrue(headerValues.contains("Digest"));
		assertTrue(headerValues.contains("NTLM"));
	}

	private static class HttpResponseMock extends AbstractHttpMessage
			implements HttpResponse {
		@Override
		public StatusLine getStatusLine() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setStatusLine(StatusLine statusLine) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setStatusLine(ProtocolVersion protocolVersion
			throw new UnsupportedOperationException();
		}

		@Override
		public void setStatusLine(ProtocolVersion protocolVersion
				String s) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setStatusCode(int i) throws IllegalStateException {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setReasonPhrase(String s) throws IllegalStateException {
			throw new UnsupportedOperationException();
		}

		@Override
		public HttpEntity getEntity() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setEntity(HttpEntity httpEntity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Locale getLocale() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setLocale(Locale locale) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ProtocolVersion getProtocolVersion() {
			throw new UnsupportedOperationException();
		}
	}
}
