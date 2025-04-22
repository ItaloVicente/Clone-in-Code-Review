				String uriText = uriCombo.getText();
				ChangeList list = changeRefs.get(uriText);
				if (list != null) {
					list.cancel(ChangeList.CancelMode.INTERRUPT);
				}
				list = new ChangeList(repository, uriText);
				changeRefs.put(uriText, list);
				preFetch(list);
