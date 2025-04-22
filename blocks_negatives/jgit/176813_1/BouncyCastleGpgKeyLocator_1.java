			return new SExprParser(calculatorProvider).parseSecretKey(
					new BufferedInputStream(in), passphraseProvider, publicKey);
		} catch (IOException | PGPException | ClassCastException e) {
			if (log.isDebugEnabled())
				log.debug("Ignoring unreadable file '{}': {}", keyFile, //$NON-NLS-1$
						e.getMessage(), e);
			return null;
