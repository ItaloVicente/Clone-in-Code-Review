package org.eclipse.egit.core.op;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.gerrit.GerritUtil;
import org.eclipse.egit.core.op.CloneOperation.PostCloneTask;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class ConfigureGerritAfterCloneTask implements PostCloneTask {

	private String uri;

	private String remoteName;

	public ConfigureGerritAfterCloneTask(String uri, String remoteName) {
		this.uri = uri;
		this.remoteName = remoteName;
	}

	public void execute(Repository repository, IProgressMonitor monitor)
			throws CoreException {
		try {
			if (isGerrit()) {
				Activator.logInfo(uri
						+ " was detected to be hosted by a Gerrit server"); //$NON-NLS-1$
				configureGerrit(repository);
			}
		} catch (Exception e) {
			throw new CoreException(Activator.error(e.getMessage(), e));
		}
	}

	private boolean isGerrit() throws MalformedURLException, IOException {
		final String GERRIT_XSSI_MAGIC_STRING = ")]}\'\n"; //$NON-NLS-1$
		if (uri.startsWith("https://") || uri.startsWith("http://")) { //$NON-NLS-1$ //$NON-NLS-2$
			String u = uri;
			int protocolEnd = uri.indexOf("://") + 3; //$NON-NLS-1$
			HttpURLConnection httpConnection = null;
			try {
				while (true) {
					InputStream in = null;
					try {
						httpConnection = (HttpURLConnection) new URL(u
								+ "/config/server/version").openConnection(); //$NON-NLS-1$
						httpConnection.setRequestMethod("GET"); //$NON-NLS-1$
						int responseCode = httpConnection.getResponseCode();
						switch (responseCode) {
						case HttpURLConnection.HTTP_OK:
							in = httpConnection.getInputStream();
							String response = readFully(in, "UTF-8"); //$NON-NLS-1$
							if (response.startsWith(GERRIT_XSSI_MAGIC_STRING)) {
								return true;
							} else {
								return false;
							}
						case HttpURLConnection.HTTP_NOT_FOUND:
							int slash = u.lastIndexOf('/');
							if (slash <= protocolEnd) {
								return false;
							}
							u = u.substring(0, u.lastIndexOf('/'));
							break;
						default:
							return false;
						}
					} finally {
						if (in != null) {
							in.close();
						}
					}
				}
			} finally {
				if (httpConnection != null) {
					httpConnection.disconnect();
				}
			}
		}
		return false;
	}

	private String readFully(InputStream inputStream, String encoding)
			throws IOException {
		return new String(readFully(inputStream), encoding);
	}

	private byte[] readFully(InputStream inputStream) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			os.write(buffer, 0, length);
		}
		return os.toByteArray();
	}

	private void configureGerrit(Repository repository) {
		StoredConfig config = repository.getConfig();
		RemoteConfig remoteConfig;
		try {
			remoteConfig = GerritUtil.findRemoteConfig(config, remoteName);
			if (remoteConfig == null) {
				return;
			}
			GerritUtil.configurePushURI(remoteConfig, new URIish(uri));
			GerritUtil.configurePushRefSpec(remoteConfig, Constants.MASTER);
			GerritUtil.configureFetchNotes(remoteConfig);
			GerritUtil.setCreateChangeId(config);
			remoteConfig.update(config);
			config.save();
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		}
	}

}
