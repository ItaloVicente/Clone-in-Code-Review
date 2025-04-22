package org.eclipse.jgit.niofs.internal.daemon.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;

import org.eclipse.jgit.niofs.internal.BaseTest;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class HTTPSOnlySupportTest extends BaseTest {

	public Map<String
		Map<String
		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED
		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTPS_ENABLED
		return gitPrefs;
	}

	@Test
	public void testRoot() {
		base("/");
		assertThat(provider.getFullHostNames().get("http")).isNull();
		assertThat(provider.getFullHostNames().get("https")).isNotNull();
	}

	public void base(final String contextPath) {
		final HTTPSupport httpSupport = new HTTPSupport() {
			@Override
			protected JGitFileSystemProvider resolveProvider() {
				return provider;
			}
		};

		final ServletContextEvent sce = mock(ServletContextEvent.class);

		final ServletContext sc = mock(ServletContext.class);
		final ServletRegistration.Dynamic dyn = mock(ServletRegistration.Dynamic.class);

		ArgumentCaptor<Servlet> servletArgumentCaptor = ArgumentCaptor.forClass(Servlet.class);

		when(sc.addServlet(anyString()

		when(sce.getServletContext()).thenReturn(sc);
		when(sc.getContextPath()).thenReturn(contextPath);

		httpSupport.contextInitialized(sce);

		verify(sc
		verify(dyn
		verify(dyn
		verify(dyn
	}
}
