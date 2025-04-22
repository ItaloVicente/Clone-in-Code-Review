	 * IExecutableExtension arguements to this class in order and compare with
	 * the elements. If the element is directly assignable to the provided class
	 * then it is added to the result array as is. If the class has specified
	 * "adapt=true" as an argument and there is an available adapter in the
	 * platform IAdapterManager then it is returned. Finally, if "adapt=true"
	 * and the class is already loaded (determined by inspecting exported
	 * bundles via the platform PackageAdmin) a direct query for the adapter is
	 * made on the object and if it is not <code>null</code> then it is
	 * returned.
