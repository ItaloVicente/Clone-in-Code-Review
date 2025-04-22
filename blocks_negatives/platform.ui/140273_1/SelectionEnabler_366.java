 * Note: The dependency on org.eclipse.jface.text for ITextSelection must be
 * severed It may be possible to do with IActionFilter generic workbench
 * registers IActionFilter for "size" property against IStructuredSelection
 * workbench text registers IActionFilter for "size" property against
 * ITextSelection code here: sel.getAdapter(IActionFilter.class) As an interim
 * solution, use reflection to access selections implementing ITextSelection
