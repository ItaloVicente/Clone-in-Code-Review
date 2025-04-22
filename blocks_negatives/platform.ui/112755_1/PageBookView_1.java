			Set keys = newActionHandlers.entrySet();
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				bars.setGlobalActionHandler((String) entry.getKey(),
						(IAction) entry.getValue());
			}
