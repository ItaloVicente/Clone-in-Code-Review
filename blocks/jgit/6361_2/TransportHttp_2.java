
		class HttpOutputStream extends TemporaryBuffer {
			HttpOutputStream() {
				super(http.postBuffer);
			}

			@Override
			protected OutputStream overflow() throws IOException {
				openStream();
				conn.setChunkedStreamingMode(0);
				return conn.getOutputStream();
			}
		}
	}

	class MultiRequestService extends Service {
		boolean finalRequest;

		MultiRequestService(final String serviceName) {
			super(serviceName);
		}

		@Override
		void execute() throws IOException {
			out.close();

			if (conn == null) {
				if (out.length() == 0) {
					if (finalRequest)
						return;
					throw new TransportException(uri
							JGitText.get().startingReadStageWithoutWrittenRequestDataPendingIsNotSupported);
				}

				sendRequest();
			}

			out.reset();

			openResponse();

			in.add(openInputStream(conn));
			if (!finalRequest)
				in.add(execute);
			conn = null;
		}
	}

	class LongPollService extends Service {
		LongPollService(String serviceName) {
			super(serviceName);
		}

		@Override
		void execute() throws IOException {
			out.close();
			if (conn == null)
				sendRequest();
			openResponse();
			in.add(openInputStream(conn));
		}
