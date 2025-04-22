			clear();
			snapshot = newSnapshot;
		} catch (IOException e) {
			final IOException e2 = new IOException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()));
			e2.initCause(e);
			throw e2;
		} catch (ConfigInvalidException e) {
			throw new ConfigInvalidException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()), e);
