			this.branchCombo.setEnabled(false);
		} else {
			List<String> refs = new ArrayList<String>();
			RefDatabase refDatabase = myRepository.getRefDatabase();
			try {
				for (Ref ref : refDatabase.getAdditionalRefs())
					refs.add(ref.getName());

				Set<Entry<String, Ref>> entrys = refDatabase.getRefs(RefDatabase.ALL).entrySet();
				for (Entry<String, Ref> ref : entrys)
						refs.add(ref.getValue().getName());
			} catch (IOException e1) {
			}

			Collections.sort(refs, CommonUtils.STRING_ASCENDING_COMPARATOR);
			for (String refName : refs)
				this.branchCombo.add(refName);

			this.branchCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					upstreamConfig = getDefaultUpstreamConfig(myRepository,
							branchCombo.getText());
					checkPage();
				}
			});
			if (myBaseRef != null)
				this.branchCombo.setText(myBaseRef);
		}
