			clear();
			snapshot = newSnapshot;
		} catch (IOException e) {
			throw new IOException(MessageFormat
					.format(JGitText.get().cannotReadFile, getFile()), e);
		} catch (ConfigInvalidException e) {
			throw new ConfigInvalidException(MessageFormat.format(JGitText.get().cannotReadFile, getFile()), e);
