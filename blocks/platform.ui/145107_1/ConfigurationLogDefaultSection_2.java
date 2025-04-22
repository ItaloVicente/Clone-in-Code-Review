			reference = context.getServiceReferences(ESystemInformation.class, About.createSectionFilter(section)).stream().findFirst().get();
			ESystemInformation service = context.getService(reference);
			service.append(writer);
		} catch (Exception e) {
			WorkbenchPlugin.log(NLS.bind("Failed to retrieve data for section: {0}", section), e); //$NON-NLS-1$
			writer.println(WorkbenchMessages.SystemSummary_sectionError);
		} finally {
			if (reference != null) {
				context.ungetService(reference);
