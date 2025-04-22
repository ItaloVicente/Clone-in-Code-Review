package org.eclipse.egit.core.internal.signing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.GpgConfig;
import org.eclipse.jgit.lib.GpgObjectSigner;
import org.eclipse.jgit.lib.GpgSignature;
import org.eclipse.jgit.lib.GpgSignatureVerifier;
import org.eclipse.jgit.lib.GpgSignatureVerifierFactory;
import org.eclipse.jgit.lib.GpgSigner;
import org.eclipse.jgit.lib.ObjectBuilder;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.TemporaryBuffer;

public class ExternalGpgSigner extends GpgSigner implements GpgObjectSigner {

	private static final String PINENTRY_USER_DATA = "PINENTRY_USER_DATA"; //$NON-NLS-1$

	private static final byte[] SIGNATURE_START = "-----BEGIN PGP SIGNATURE-----" //$NON-NLS-1$
			.getBytes(StandardCharsets.US_ASCII);

	private static final PathScanner FROM_PATH = new PathScanner();

	private interface ResultHandler {
		void accept(TemporaryBuffer b) throws IOException, CanceledException;
	}

	private static void runProcess(ProcessBuilder process, InputStream in,
			ResultHandler stdout, ResultHandler stderr)
			throws IOException, CanceledException {
		String command = process.command().stream()
				.collect(Collectors.joining(" ")); //$NON-NLS-1$
		ExecutionResult result = null;
		int code = 0;
		try {
			result = FS.DETECTED.execute(process, in);
			code = result.getRc();
			if (code != 0) {
				if (stderr != null) {
					stderr.accept(result.getStderr());
				}
				throw new IOException(
						MessageFormat.format(
								CoreText.ExternalGpgSigner_processFailed,
								command, Integer.toString(code) + ": " //$NON-NLS-1$
										+ toString(result.getStderr())));
			}
			stdout.accept(result.getStdout());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IOException(MessageFormat
					.format(CoreText.ExternalGpgSigner_processInterrupted,
							command),
					e);
		} catch (IOException e) {
			if (code != 0) {
				throw e;
			}
			if (result != null) {
				throw new IOException(
						MessageFormat.format(
								CoreText.ExternalGpgSigner_processFailed,
								command, toString(result.getStderr())),
						e);
			}
			throw new IOException(
					MessageFormat.format(
							CoreText.ExternalGpgSigner_processFailed,
							command, e.getLocalizedMessage()),
					e);
		} finally {
			if (result != null) {
				if (result.getStderr() != null) {
					result.getStderr().destroy();
				}
				if (result.getStdout() != null) {
					result.getStdout().destroy();
				}
			}
		}
	}

	private static String toString(TemporaryBuffer b) {
		if (b != null) {
			try {
				return new String(b.toByteArray(4000),
						Charset.defaultCharset());
			} catch (IOException e) {
				Activator.logWarning(CoreText.ExternalGpgSigner_bufferError, e);
			}
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public void sign(CommitBuilder commit, String gpgSigningKey,
			PersonIdent committer, CredentialsProvider credentialsProvider)
			throws CanceledException {
		signObject(commit, gpgSigningKey, committer, null, null);
	}

	@Override
	public void signObject(ObjectBuilder object, String gpgSigningKey,
			PersonIdent committer, CredentialsProvider credentialsProvider,
			GpgConfig config) throws CanceledException {
		try {
			String keySpec = gpgSigningKey;
			if (StringUtils.isEmptyOrNull(gpgSigningKey)) {
				keySpec = '<' + committer.getEmailAddress() + '>';
			}
			String program = config != null ? config.getProgram() : null;
			object.setGpgSignature(new GpgSignature(
					signWithGpg(object.build(), keySpec, program)));
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		}
	}

	@Override
	public boolean canLocateSigningKey(String gpgSigningKey,
			PersonIdent committer, CredentialsProvider credentialsProvider)
			throws CanceledException {
		return canLocateSigningKey(gpgSigningKey, committer, null, null);
	}

	@Override
	public boolean canLocateSigningKey(String gpgSigningKey,
			PersonIdent committer, CredentialsProvider credentialsProvider,
			GpgConfig config) throws CanceledException {
		String program = config != null ? config.getProgram() : null;
		if (StringUtils.isEmptyOrNull(program)) {
			program = FROM_PATH.getGpg();
			if (StringUtils.isEmptyOrNull(program)) {
				return false;
			}
		}
		String keySpec = gpgSigningKey;
		if (StringUtils.isEmptyOrNull(keySpec)) {
			keySpec = '<' + committer.getEmailAddress() + '>';
		}
		ProcessBuilder process = new ProcessBuilder();
		process.command(program, "--locate-keys", //$NON-NLS-1$
				"--with-colons", //$NON-NLS-1$
				"--batch", //$NON-NLS-1$
				"--no-tty", //$NON-NLS-1$
				keySpec);
		gpgEnvironment(process);
		try {
			boolean[] result = { false };
			runProcess(process, null, b -> {
				try (BufferedReader r = new BufferedReader(
						new InputStreamReader(b.openInputStream(),
								StandardCharsets.UTF_8))) {
					boolean keyFound = false;
					String line;
					while ((line = r.readLine()) != null) {
						if (line.startsWith("pub:") //$NON-NLS-1$
								|| line.startsWith("sub:")) { //$NON-NLS-1$
							String[] fields = line.split(":"); //$NON-NLS-1$
							if (fields.length > 11
									&& fields[11].indexOf('s') >= 0) {
								keyFound = true;
								break;
							}
						}
					}
					result[0] = keyFound;
				}
			}, null);
			return result[0];
		} catch (IOException e) {
			Activator.logError(e.getLocalizedMessage(), e);
			return false;
		}
	}

	private byte[] signWithGpg(byte[] data, String keySpec, String gpgProgram)
			throws IOException, CanceledException {
		String program = gpgProgram;
		if (StringUtils.isEmptyOrNull(program)) {
			program = FROM_PATH.getGpg();
			if (StringUtils.isEmptyOrNull(program)) {
				throw new IOException(CoreText.ExternalGpgSigner_gpgNotFound);
			}
		}
		ProcessBuilder process = new ProcessBuilder();
		process.command(program,
				"-bsau", //$NON-NLS-1$
				keySpec,
				"--batch", //$NON-NLS-1$
				"--no-tty", //$NON-NLS-1$
				"--status-fd", //$NON-NLS-1$
				"2", //$NON-NLS-1$
				"--output", //$NON-NLS-1$
				"-"); //$NON-NLS-1$
		gpgEnvironment(process);
		try (ByteArrayInputStream dataIn = new ByteArrayInputStream(data)) {
			class Holder {
				byte[] rawData;
			}
			Holder result = new Holder();
			runProcess(process, dataIn, b -> {
				GpgSignatureVerifierFactory factory = GpgSignatureVerifierFactory
						.getDefault();
				boolean isValid = false;
				if (factory == null) {
					byte[] fromGpg = b.toByteArray(SIGNATURE_START.length);
					isValid = Arrays.equals(fromGpg, SIGNATURE_START);
					if (isValid) {
						result.rawData = b.toByteArray();
					}
				} else {
					byte[] fromGpg = b.toByteArray();
					GpgSignatureVerifier verifier = factory.getVerifier();
					try {
						GpgSignatureVerifier.SignatureVerification verification = verifier
								.verify(data, fromGpg);
						isValid = verification != null
								&& verification.getVerified();
						if (isValid) {
							result.rawData = fromGpg;
						}
					} catch (JGitInternalException e) {
						throw new IOException(e.getLocalizedMessage(), e);
					} finally {
						verifier.clear();
					}
				}
				if (!isValid) {
					throw new IOException(MessageFormat.format(
							CoreText.ExternalGpgSigner_noSignature,
							toString(b)));
				}
			}, e -> {
				try (BufferedReader r = new BufferedReader(
						new InputStreamReader(e.openInputStream(),
								StandardCharsets.UTF_8))) {
					String line;
					boolean pinentry = false;
					while ((line = r.readLine()) != null) {
						if (!pinentry && line
								.startsWith("[GNUPG:] PINENTRY_LAUNCHED")) { //$NON-NLS-1$
							pinentry = true;
						} else if (pinentry) {
							if (line.startsWith("[GNUPG:] FAILURE sign")) { //$NON-NLS-1$
								throw new CanceledException(
										CoreText.ExternalGpgSigner_signingCanceled);
							}
							if (line.startsWith("[GNUPG:]")) { //$NON-NLS-1$
								pinentry = false;
							}
						}
					}
				} catch (IOException ex) {
				}
			});
			return result.rawData;
		}
	}

	private void gpgEnvironment(ProcessBuilder process) {
		try {
			Map<String, String> childEnv = process.environment();
			String value = childEnv.get(PINENTRY_USER_DATA);
			if (!StringUtils.isEmptyOrNull(value)) {
				childEnv.remove(PINENTRY_USER_DATA);
			}
		} catch (SecurityException | UnsupportedOperationException
				| IllegalArgumentException e) {
			Activator.logWarning(CoreText.ExternalGpgSigner_environmentError,
					e);
		}
	}

	private static class PathScanner {

		private String gpg;

		synchronized String getGpg() {
			if (gpg == null) {
				gpg = findGpg();
			}
			return gpg.isEmpty() ? null : gpg;
		}

		private static String findGpg() {
			SystemReader system = SystemReader.getInstance();
			String path = system.getenv("PATH"); //$NON-NLS-1$
			String exe = null;
			if (system.isMacOS()) {
				String bash = searchPath(path, "bash"); //$NON-NLS-1$
				if (bash != null) {
					ProcessBuilder process = new ProcessBuilder();
					process.command(bash, "--login", "-c", "which gpg"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					process.directory(FS.DETECTED.userHome());
					String[] result = { null };
					try {
						runProcess(process, null, b -> {
							try (BufferedReader r = new BufferedReader(
									new InputStreamReader(b.openInputStream(),
											Charset.defaultCharset()))) {
								result[0] = r.readLine();
							}
						}, null);
					} catch (IOException | CanceledException e) {
						Activator.logWarning(
								CoreText.ExternalGpgSigner_cannotSearch, e);
					}
					exe = result[0];
				}
			}
			if (exe == null) {
				exe = searchPath(path, system.isWindows() ? "gpg.exe" : "gpg"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return exe == null ? "" : exe; //$NON-NLS-1$
		}

		private static String searchPath(String path, String name) {
			for (String p : path.split(File.pathSeparator)) {
				File exe = new File(p, name);
				try {
					if (exe.isFile() && exe.canExecute()) {
						return exe.getAbsolutePath();
					}
				} catch (SecurityException e) {
					Activator.logWarning(MessageFormat.format(
							CoreText.ExternalGpgSigner_skipNotAccessiblePath,
							exe.getPath()), e);
				}
			}
			return null;
		}
	}
}
