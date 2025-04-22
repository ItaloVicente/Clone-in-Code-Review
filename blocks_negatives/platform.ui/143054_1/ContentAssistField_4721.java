	 * @param parent
	 *            the parent of the decorated field.
	 * @param style
	 *            the desired style bits for the field.
	 * @param controlCreator
	 *            the IControlCreator used to specify the specific kind of
	 *            control that is to be decorated.
	 * @param controlContentAdapter
	 *            the <code>IControlContentAdapter</code> used to obtain and
	 *            update the control's contents as proposals are accepted. May
	 *            not be <code>null</code>.
	 * @param proposalProvider
	 *            the <code>IContentProposalProvider</code> used to obtain
	 *            content proposals for this control, or <code>null</code> if
	 *            no content proposal is available.
	 * @param commandId
	 *            the String id of the command that will invoke the content
	 *            assistant. If not supplied, the default value will be
	 *            "org.eclipse.ui.edit.text.contentAssist.proposals".
	 * @param autoActivationCharacters
	 *            An array of characters that trigger auto-activation of content
	 *            proposal. If specified, these characters will trigger
	 *            auto-activation of the proposal popup, regardless of the
	 *            specified command id.
