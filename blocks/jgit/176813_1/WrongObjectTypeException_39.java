package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.ServiceUnavailableException;
import org.eclipse.jgit.api.errors.WrongObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.GpgConfig;
import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.lib.GpgSignatureVerifier.SignatureVerification;
import org.eclipse.jgit.lib.GpgSignatureVerifierFactory;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevWalk;

public class VerifySignatureCommand extends GitCommand<Map<String

	public enum VerifyMode {
		ANY
		COMMITS
		TAGS
	}

	private final Set<String> namesToCheck = new HashSet<>();

	private VerifyMode mode = VerifyMode.ANY;

	private GpgSignatureVerifier verifier;

	private GpgConfig config;

	private boolean ownVerifier;

	public VerifySignatureCommand(Repository repo) {
		super(repo);
	}

	public VerifySignatureCommand addName(String name) {
		checkCallable();
		namesToCheck.add(name);
		return this;
	}

	public VerifySignatureCommand addNames(String... names) {
		checkCallable();
		namesToCheck.addAll(Arrays.asList(names));
		return this;
	}

	public VerifySignatureCommand addNames(Collection<String> names) {
		checkCallable();
		namesToCheck.addAll(names);
		return this;
	}

	public VerifySignatureCommand setMode(@NonNull VerifyMode mode) {
		checkCallable();
		this.mode = mode;
		return this;
	}

	public VerifySignatureCommand setVerifier(GpgSignatureVerifier verifier) {
		checkCallable();
		this.verifier = verifier;
		return this;
	}

	public VerifySignatureCommand setGpgConfig(GpgConfig config) {
		checkCallable();
		this.config = config;
		return this;
	}

	public GpgSignatureVerifier getVerifier() {
		return verifier;
	}

	@Override
	@NonNull
	public Map<String
			throws ServiceUnavailableException
		checkCallable();
		setCallable(false);
		Map<String
		if (verifier == null) {
			GpgSignatureVerifierFactory factory = GpgSignatureVerifierFactory
					.getDefault();
			if (factory == null) {
				throw new ServiceUnavailableException(
						JGitText.get().signatureVerificationUnavailable);
			}
			verifier = factory.getVerifier();
			ownVerifier = true;
		}
		if (config == null) {
			config = new GpgConfig(repo.getConfig());
		}
		try (RevWalk walk = new RevWalk(repo)) {
			for (String toCheck : namesToCheck) {
				ObjectId id = repo.resolve(toCheck);
				if (id != null && !ObjectId.zeroId().equals(id)) {
					RevObject object;
					try {
						object = walk.parseAny(id);
					} catch (MissingObjectException e) {
						continue;
					}
					VerificationResult verification = verifyOne(object);
					if (verification != null) {
						result.put(toCheck
					}
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().signatureVerificationError
		} finally {
			if (ownVerifier) {
				verifier.clear();
			}
		}
		return result;
	}

	private VerificationResult verifyOne(RevObject object)
			throws WrongObjectTypeException
		int type = object.getType();
		if (VerifyMode.TAGS.equals(mode) && type != Constants.OBJ_TAG) {
			throw new WrongObjectTypeException(object
		} else if (VerifyMode.COMMITS.equals(mode)
				&& type != Constants.OBJ_COMMIT) {
			throw new WrongObjectTypeException(object
		}
		if (type == Constants.OBJ_COMMIT || type == Constants.OBJ_TAG) {
			try {
				GpgSignatureVerifier.SignatureVerification verification = verifier
						.verifySignature(object
				if (verification == null) {
					return null;
				}
				return new Result(object
			} catch (JGitInternalException e) {
				return new Result(object
			}
		}
		return null;
	}

	private static class Result implements VerificationResult {

		private final Throwable throwable;

		private final SignatureVerification verification;

		private final RevObject object;

		public Result(RevObject object
				Throwable throwable) {
			this.object = object;
			this.verification = verification;
			this.throwable = throwable;
		}

		@Override
		public Throwable getException() {
			return throwable;
		}

		@Override
		public SignatureVerification getVerification() {
			return verification;
		}

		@Override
		public RevObject getObject() {
			return object;
		}

	}
}
