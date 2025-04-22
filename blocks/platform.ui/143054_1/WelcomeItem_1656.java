			logActionLinkError(pluginId, className);
			return;
		}
		action.run();
	}

	public void triggerLinkAt(int offset) {
		for (int i = 0; i < helpRanges.length; i++) {
			if (offset >= helpRanges[i][0]
					&& offset < helpRanges[i][0] + helpRanges[i][1]) {
				openHelpTopic(helpIds[i], helpHrefs[i]);
				return;
			}
		}

		for (int i = 0; i < actionRanges.length; i++) {
			if (offset >= actionRanges[i][0]
					&& offset < actionRanges[i][0] + actionRanges[i][1]) {
				runAction(actionPluginIds[i], actionClasses[i]);
				return;
			}
		}
	}
