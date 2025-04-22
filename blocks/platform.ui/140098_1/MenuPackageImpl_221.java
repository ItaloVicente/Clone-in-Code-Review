		initEClass(toolBarElementEClass, MToolBarElement.class, "ToolBarElement", IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(toolControlEClass, MToolControl.class, "ToolControl", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(handledToolItemEClass, MHandledToolItem.class, "HandledToolItem", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(directToolItemEClass, MDirectToolItem.class, "DirectToolItem", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(toolBarSeparatorEClass, MToolBarSeparator.class, "ToolBarSeparator", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(menuContributionsEClass, MMenuContributions.class, "MenuContributions", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMenuContributions_MenuContributions(), this.getMenuContribution(), null, "menuContributions", //$NON-NLS-1$
				null, 0, -1, MMenuContributions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(toolBarContributionEClass, MToolBarContribution.class, "ToolBarContribution", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getToolBarContribution_ParentId(), ecorePackage.getEString(), "parentId", null, 0, 1, //$NON-NLS-1$
				MToolBarContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getToolBarContribution_PositionInParent(), ecorePackage.getEString(), "positionInParent", null, //$NON-NLS-1$
				0, 1, MToolBarContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(toolBarContributionsEClass, MToolBarContributions.class, "ToolBarContributions", IS_ABSTRACT, //$NON-NLS-1$
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getToolBarContributions_ToolBarContributions(), this.getToolBarContribution(), null,
				"toolBarContributions", null, 0, -1, MToolBarContributions.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trimContributionEClass, MTrimContribution.class, "TrimContribution", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTrimContribution_ParentId(), ecorePackage.getEString(), "parentId", null, 0, 1, //$NON-NLS-1$
				MTrimContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTrimContribution_PositionInParent(), ecorePackage.getEString(), "positionInParent", null, 0, //$NON-NLS-1$
				1, MTrimContribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(trimContributionsEClass, MTrimContributions.class, "TrimContributions", IS_ABSTRACT, IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrimContributions_TrimContributions(), this.getTrimContribution(), null, "trimContributions", //$NON-NLS-1$
				null, 0, -1, MTrimContributions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicMenuContributionEClass, MDynamicMenuContribution.class, "DynamicMenuContribution", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
