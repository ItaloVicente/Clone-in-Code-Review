import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jgit.internal.transport.http.NetscapeCookieFile;
import org.eclipse.jgit.internal.transport.http.NetscapeCookieFileTest.HttpCookiesMatcher;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.eclipse.jgit.transport.http.HttpConnection;
