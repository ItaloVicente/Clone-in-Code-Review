				try (FileInputStream input = new FileInputStream(stateFile);
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(input, StandardCharsets.UTF_8))) {
					IMemento memento = XMLMemento.createReadRoot(reader);
					restoreWorkingSetState(memento);
					restoreMruList(memento);
				}
			} catch (IOException | WorkbenchException e) {
				handleInternalError(e, WorkbenchMessages.ProblemRestoringWorkingSetState_title,
