
			ProtocolV0Parser parser = new ProtocolV0Parser(transferConfig);
			FetchV0Request req = parser.recvWants(pckIn);

			wantIds.addAll(req.getWantIds());
			clientShallowCommits = req.getClientShallowCommits();
			filterBlobLimit = req.getFilterBlobLimit();
			options = req.getOptions();
			depth = req.getDepth();

			if (req.getWantIds().isEmpty()) {
				preUploadHook.onBeginNegotiateRound(this
				preUploadHook.onEndNegotiateRound(this
						false);
