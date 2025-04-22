					});
			return result != null ? result : NO_PACKED_REFS;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new IOException(MessageFormat
					.format(JGitText.get().cannotReadFile
