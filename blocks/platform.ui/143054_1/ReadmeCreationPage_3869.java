		if (!sectionCheckbox.getSelection())
			return null;

		StringBuilder sb = new StringBuilder();
		sb.append(MessageUtil.getString("SAMPLE_README_FILE")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("SECTION_1")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("SECTION_1_BODY_1")); //$NON-NLS-1$

		if (subsectionCheckbox.getSelection()) {
			sb.append(MessageUtil.getString("Subsection_1_1")); //$NON-NLS-1$
			sb.append(MessageUtil.getString("Subsection_1_1_Body_1")); //$NON-NLS-1$
		}

		sb.append(MessageUtil.getString("SECTION_2")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("SECTION_2_BODY_1")); //$NON-NLS-1$
		sb.append(MessageUtil.getString("SECTION_2_BODY_2")); //$NON-NLS-1$

		if (subsectionCheckbox.getSelection()) {
			sb.append(MessageUtil.getString("Subsection_2_1")); //$NON-NLS-1$
			sb.append(MessageUtil.getString("Subsection_2_1_BODY_1")); //$NON-NLS-1$
			sb.append(MessageUtil.getString("Subsection_2_2")); //$NON-NLS-1$
			sb.append(MessageUtil.getString("Subsection_2_2_BODY_1")); //$NON-NLS-1$
		}

		return new ByteArrayInputStream(sb.toString().getBytes());
	}

	@Override
