			} catch (StorageException e) {
				Activator.handleError(MessageFormat.format(
						UIText.SecureStoreUtils_writingCredentialsFailed, uri),
						e, true);
				return false;
			} catch (IOException e) {
