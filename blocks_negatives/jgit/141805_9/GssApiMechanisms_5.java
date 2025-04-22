/*
 * Copyright (C) 2018, Thomas Wolf <thomas.wolf@paranor.ch>
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
import org.eclipse.jgit.internal.transport.sshd.RepeatingFilePasswordProvider.ResourceDecodeResult;

/**
 * A {@link FileKeyPairProvider} that asks repeatedly for a passphrase for an
 * encrypted private key if the {@link FilePasswordProvider} is a
 * {@link RepeatingFilePasswordProvider}.
 */
public class EncryptedFileKeyPairProvider extends FileKeyPairProvider {


	/**
	 * Creates a new {@link EncryptedFileKeyPairProvider} for the given
	 * {@link Path}s.
	 *
	 * @param paths
	 *            to read keys from
	 */
	public EncryptedFileKeyPairProvider(List<Path> paths) {
		super(paths);
	}

	@Override
	protected KeyPair doLoadKey(String resourceKey, InputStream inputStream,
			FilePasswordProvider provider)
			throws IOException, GeneralSecurityException {
		if (!(provider instanceof RepeatingFilePasswordProvider)) {
			return super.doLoadKey(resourceKey, inputStream, provider);
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
				ids = parser.loadKeyPairs(resourceKey, realProvider, lines);
				realProvider.handleDecodeAttemptResult(resourceKey, "", null); //$NON-NLS-1$
				break;
			} catch (IOException | GeneralSecurityException
					| RuntimeException e) {
				ResourceDecodeResult loadResult = realProvider
						.handleDecodeAttemptResult(resourceKey, "", e); //$NON-NLS-1$
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
					format(SshdText.get().identityFileNoKey, resourceKey));
		}
		Iterator<KeyPair> keys = ids.iterator();
		if (!keys.hasNext()) {
			throw new InvalidKeyException(format(
					SshdText.get().identityFileUnsupportedFormat, resourceKey));
		}
		KeyPair result = keys.next();
		if (keys.hasNext()) {
			log.warn(format(SshdText.get().identityFileMultipleKeys,
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
