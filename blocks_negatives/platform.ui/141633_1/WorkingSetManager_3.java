				BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

				IMemento memento = XMLMemento.createReadRoot(reader);
				restoreWorkingSetState(memento);
				restoreMruList(memento);
				reader.close();
