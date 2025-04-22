
			ProtocolV1Parser parser = new ProtocolV1Parser(transferConfig);
			FetchV1Request req = parser.recvWants(pckIn);

			wantIds.addAll(req.getWantsIds());
			clientShallowCommits = req.getClientShallowCommits();
			filterBlobLimit = req.getFilterBlobLimit();
			options = req.getOptions();
			depth = req.getDepth();

			if (req.getWantsIds().isEmpty()) {
				preUploadHook.onBeginNegotiateRound(this
				preUploadHook.onEndNegotiateRound(this
						false);
