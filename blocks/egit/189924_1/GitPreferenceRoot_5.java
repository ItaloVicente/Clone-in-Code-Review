					@Override
					public void setPreferenceStore(IPreferenceStore store) {
						super.setPreferenceStore(store == null ? null
								: getSecondaryPreferenceStore());
					}
				};
				defaultSshAgent.getLabelControl(remoteConnectionsGroup)
						.setToolTipText(
								UIText.GitPreferenceRoot_SshDefaultAgent_Tooltip);
				GridDataFactory.fillDefaults()
						.indent(UIUtils.getControlIndent(), 0)
						.applyTo(defaultSshAgent
								.getLabelControl(remoteConnectionsGroup));

				addField(defaultSshAgent);
