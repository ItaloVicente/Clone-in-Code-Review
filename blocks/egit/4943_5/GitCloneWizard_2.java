			return performClone(currentSearchResult.getGitRepositoryInfo());
		} catch (URISyntaxException e) {
			Activator.error(UIText.GitImportWizard_errorParsingURI, e);
		} catch (NoRepositoryInfoException e) {
			Activator.error(UIText.GitImportWizard_noRepositoryInfo, e);
		} catch (Exception e) {
			Activator.error(e.getMessage(), e);
