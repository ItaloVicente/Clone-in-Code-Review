	PushCertificate(String version
			String nonce
			String signature) {
		if (version == null || version.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (pusher == null) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (pushee == null || pushee.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (nonce == null || nonce.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (nonceStatus == null) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (commands == null || commands.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().pushCertificateInvalidField
		}
		if (signature == null || signature.isEmpty()) {
			throw new IllegalArgumentException(
					JGitText.get().pushCertificateInvalidSignature);
		}
		this.version = version;
		this.pusher = pusher;
		this.pushee = pushee;
		this.nonce = nonce;
		this.nonceStatus = nonceStatus;
		this.commands = commands;
		this.signature = signature;
