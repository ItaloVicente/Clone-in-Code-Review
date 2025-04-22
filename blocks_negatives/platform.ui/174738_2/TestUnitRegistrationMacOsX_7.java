		InputStream inputStream = getClass().getResourceAsStream("lsregisterForOtherApp.txt");
		lsregisterDumpForOtherApp = convert(inputStream);
		inputStream = getClass().getResourceAsStream("lsregisterForOwnApp.txt");
		lsregisterDumpForOwnApp = convert(inputStream);
		inputStream = getClass().getResourceAsStream("lsregisterForOwnAppPlus.txt");
		lsregisterDumpForOwnAppPlus = convert(inputStream);
		inputStream = getClass().getResourceAsStream("lsreigsterForOwnApp_macOS_10_15_3.txt");
		lsregisterDumpMacOS10_15_3 = convert(inputStream);
