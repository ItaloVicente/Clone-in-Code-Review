
			ProtocolV0Parser parser = new ProtocolV0Parser(transferConfig);
			FetchV0Request req = parser.recvWants(pckIn);

			wantIds = req.getWantIds();
			clientShallowCommits = req.getClientShallowCommits();
			filterBlobLimit = req.getFilterBlobLimit();
			options = req.getClientCapabilities();
			depth = req.getDepth();

			if (req.getWantIds().isEmpty()) {
				preUploadHook.onBeginNegotiateRound(this
				preUploadHook.onEndNegotiateRound(this
						false);
