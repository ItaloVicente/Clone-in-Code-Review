package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.DestroyFailedException;

import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.apache.sshd.common.config.keys.loader.KeyPairResourceParser;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.common.util.io.IoUtils;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.eclipse.jgit.transport.sshd.RepeatingFilePasswordProvider;
import org.eclipse.jgit.transport.sshd.RepeatingFilePasswordProvider.ResourceDecodeResult;

public class EncryptedFileKeyPairProvider extends FileKeyPairProvider {


	public EncryptedFileKeyPairProvider(List<Path> paths) {
		super(paths);
	}

	@Override
	protected KeyPair doLoadKey(String resourceKey
			FilePasswordProvider provider)
			throws IOException
		if (!(provider instanceof RepeatingFilePasswordProvider)) {
			return super.doLoadKey(resourceKey
		}
		KeyPairResourceParser parser = SecurityUtils.getKeyPairResourceParser();
		if (parser == null) {
			throw new NoSuchProviderException(
		}
		RepeatingFilePasswordProvider realProvider = (RepeatingFilePasswordProvider) provider;
		List<String> lines = IoUtils.readAllLines(inputStream);
		Collection<KeyPair> ids = null;
		while (ids == null) {
			try {
				ids = parser.loadKeyPairs(resourceKey
				realProvider.handleDecodeAttemptResult(resourceKey
				break;
			} catch (IOException | GeneralSecurityException
					| RuntimeException e) {
				ResourceDecodeResult loadResult = realProvider
						.handleDecodeAttemptResult(resourceKey
				if (loadResult == null
						|| loadResult == ResourceDecodeResult.TERMINATE) {
					throw e;
				} else if (loadResult == ResourceDecodeResult.RETRY) {
					continue;
				}
				break;
			}
		}
		if (ids == null) {
			throw new InvalidKeyException(
					format(SshdText.get().identityFileNoKey
		}
		Iterator<KeyPair> keys = ids.iterator();
		if (!keys.hasNext()) {
			throw new InvalidKeyException(format(
					SshdText.get().identityFileUnsupportedFormat
		}
		KeyPair result = keys.next();
		if (keys.hasNext()) {
			log.warn(format(SshdText.get().identityFileMultipleKeys
					resourceKey));
			keys.forEachRemaining(k -> {
				PrivateKey pk = k.getPrivate();
				if (pk != null) {
					try {
						pk.destroy();
					} catch (DestroyFailedException e) {
					}
				}
			});
		}
		return result;
	}
}
