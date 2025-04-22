package org.eclipse.jgit.niofs.internal;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class URITest {

	@Test
	public void testURI() throws URISyntaxException {

		assertThat(uri.getScheme()).isEqualTo("git");
		assertThat(uri.getAuthority()).isEqualTo("branch@repo-name");
		assertThat(uri.getPath()).isEqualTo("/path/to/file.txt");
		assertThat(uri.getQuery()).isNull();

		assertThat(uri2).isNotNull();
		assertThat(uri2.getAuthority()).isEqualTo("repo-name");

		assertThat(uri3).isNotNull();
		assertThat(uri3.getScheme()).isEqualTo("git");
		assertThat(uri3.getAuthority()).isEqualTo("branch@repo-name");
		assertThat(uri3.getPath()).isEqualTo("/path/to/file.txt");
		assertThat(uri3.getQuery()).isNull();

		assertThat(uri4).isNotNull();
		assertThat(uri4.getScheme()).isEqualTo("git");
		assertThat(uri4.getAuthority()).isEqualTo("master@my-repo");
		assertThat(uri4.getPath()).isEqualTo("/:path/to/some/place.txt");
		assertThat(uri4.getQuery()).isNull();

		assertThat(uri5).isNotNull();
		assertThat(uri5.getScheme()).isEqualTo("git");
		assertThat(uri5.getAuthority()).isEqualTo("origin");
		assertThat(uri5.getPath()).isEqualTo("/master@my-repo/:path/to/some/place.txt");
		assertThat(uri5.getQuery()).isNull();

		assertThat(uri6).isNotNull();
		assertThat(uri6.getScheme()).isEqualTo("git");
		assertThat(uri6.getAuthority()).isEqualTo("origin");
		assertThat(uri6.getPath()).isEqualTo("/master@my-repo/path/to/some/place.txt");
		assertThat(uri6.getQuery()).isNull();
	}
}
