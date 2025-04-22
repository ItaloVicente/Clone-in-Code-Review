
			ProtocolV0Parser parser = new ProtocolV0Parser(transferConfig);
			FetchV0Request req = parser.recvWants(pckIn);

			wantIds.addAll(req.getWantsIds());
			clientShallowCommits = req.getClientShallowCommits();
			filterBlobLimit = req.getFilterBlobLimit();
			options = req.getOptions();
			depth = req.getDepth();

			if (req.getWantsIds().isEmpty()) {
				preUploadHook.onBeginNegotiateRound(this
				preUploadHook.onEndNegotiateRound(this
						false);
