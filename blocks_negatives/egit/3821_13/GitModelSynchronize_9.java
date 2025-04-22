		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsdSet);
		RemoteResourceMappingContext remoteContext = new GitSubscriberResourceMappingContext(
				gsdSet);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), mappings, subscriber, remoteContext, true);
		GitSubscriberMergeContext context = new GitSubscriberMergeContext(
				subscriber, manager, gsdSet);
		final GitModelSynchronizeParticipant participant = new GitModelSynchronizeParticipant(
				context);

		TeamUI.getSynchronizeManager().addSynchronizeParticipants(
				new ISynchronizeParticipant[] { participant });
