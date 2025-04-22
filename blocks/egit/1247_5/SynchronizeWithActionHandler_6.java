
		GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				gsdSet);
		ResourceMapping[] mappings = getSelectedResourceMappings(event);
		RemoteResourceMappingContext remoteContext = new GitSubscriberResourceMappingContext(gsdSet);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				subscriber.getName(), mappings, subscriber, remoteContext, true);
		SynchronizationContext context = new GitSubscriberMergeContext(
				subscriber, manager, gsdSet);
		GitModelSynchronizeParticipant participant = new GitModelSynchronizeParticipant(context);
		TeamUI.getSynchronizeManager().addSynchronizeParticipants(
				new ISynchronizeParticipant[] { participant });
		participant.run(getTargetPart());

