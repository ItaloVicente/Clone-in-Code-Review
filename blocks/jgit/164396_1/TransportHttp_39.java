			} catch (InterruptedIOException e) {
				throw new TransportException(uri
						JGitText.get().connectionTimeOut
			} catch (SocketException e) {
				throw new TransportException(uri
						JGitText.get().connectionFailed
