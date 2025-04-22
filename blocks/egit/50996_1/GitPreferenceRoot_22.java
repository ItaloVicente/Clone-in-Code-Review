				for (String diffMergeToolName : diffMergeTools) {
					toolsList.add(diffMergeToolName);
					addExternalTool(userScopedConfig,
							sectionName4AllTools, diffMergeToolName, manager);
					loadExternalToolAttributes(userScopedConfig,
							sectionName4AllTools, diffMergeToolName, manager,
							allToolAttributes, false);
