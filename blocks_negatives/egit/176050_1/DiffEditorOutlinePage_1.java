		toggleAction
				.setImageDescriptor(compact ? UIIcons.FLAT : UIIcons.COMPACT);
		toggleAction
				.setToolTipText(compact
						? UIText.DiffEditor_OutlineShowFlatListTooltip
						: UIText.DiffEditor_OutlineShowCompactTreeTooltip);
		preference.setValue(prefID, compact);
