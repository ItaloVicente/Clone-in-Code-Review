			if (!readAdvertisedRefs()) {
				LongPollService service = new LongPollService(SVC_UPLOAD_PACK,
						getProtocolVersion());
				init(service.getInputStream(), service.getOutputStream());
				lsRefs(refSpecs, additionalPatterns);
			}
