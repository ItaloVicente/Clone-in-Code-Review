		objectDigest.reset();
		objectDigest.update(Constants.encodedTypeString(uol.getType()));
		objectDigest.update((byte) ' ');
		objectDigest.update(Constants.encodeASCII(uol.getSize()));
		objectDigest.update((byte) 0);
		objectDigest.update(uol.getCachedBytes());
		idBuffer.fromRaw(objectDigest.digest(), 0);

		if (!AnyObjectId.equals(id, idBuffer)) {
			throw new TransportException(MessageFormat.format(JGitText.get().incorrectHashFor
					, id.name(), idBuffer.name(), Constants.typeString(uol.getType()), compressed.length));
		}
