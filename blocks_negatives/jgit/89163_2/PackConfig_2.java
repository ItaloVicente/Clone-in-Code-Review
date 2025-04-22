  name = 'jgit-servlet',
  srcs = glob(['src/**']),
  resources = glob(['resources/**']),
  resource_strip_prefix = 'org.eclipse.jgit.http.server/resources',
  deps = [ # We want these deps to be provided_deps
  ],
