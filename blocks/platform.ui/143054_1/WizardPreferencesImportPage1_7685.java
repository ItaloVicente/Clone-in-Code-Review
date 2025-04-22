		if (validFromFile()) {
			FileInputStream fis;

			try {
				fis = new FileInputStream(getDestinationValue());
			} catch (FileNotFoundException e) {
				WorkbenchPlugin.log(e.getMessage(), e);
				return new PreferenceTransferElement[0];
			}
			IPreferencesService service = Platform.getPreferencesService();
			try {
				IExportedPreferences prefs;
				prefs = service.readPreferences(fis);
				PreferenceTransferElement[] transfers = super.getTransfers();
				IPreferenceFilter[] filters = new IPreferenceFilter[transfers.length];
				for (int i = 0; i < transfers.length; i++) {
					PreferenceTransferElement transfer = transfers[i];
					filters[i] = transfer.getFilter();
				}
				IPreferenceFilter[] matches = service.matches(prefs, filters);
				PreferenceTransferElement[] returnTransfers = new PreferenceTransferElement[matches.length];
				int index = 0;
				for (IPreferenceFilter filter : matches) {
					for (PreferenceTransferElement element : transfers) {
						if (element.getFilter().equals(filter)) {
