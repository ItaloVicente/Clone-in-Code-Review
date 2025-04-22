		} catch (NotSupportedException e) {
			throw e;
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
			throw new TransportException(uri, MessageFormat.format(JGitText.get().cannotOpenService, service), e);
