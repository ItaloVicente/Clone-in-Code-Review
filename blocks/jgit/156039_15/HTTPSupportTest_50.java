package org.eclipse.jgit.niofs.internal.daemon.http;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
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

import org.eclipse.jgit.niofs.internal.BaseTest;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
import org.junit.Test;

public class HTTPSupportDisableTest extends BaseTest {

	public Map<String
		Map<String
		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED
		return gitPrefs;
	}

	@Test
	public void testRoot() {
		assertThat(provider.getFullHostNames().get("http")).isNull();
	}

	@Test
	public void test() {
		final HTTPSupport httpSupport = new HTTPSupport() {
			@Override
			protected JGitFileSystemProvider resolveProvider() {
				return provider;
			}
		};

		final ServletContextEvent sce = mock(ServletContextEvent.class);
		final ServletContext sc = mock(ServletContext.class);
		when(sce.getServletContext()).thenReturn(sc);
		httpSupport.contextInitialized(sce);
		assertFalse(provider.getFullHostNames().containsKey("http"));

		verify(sc
	}
}
