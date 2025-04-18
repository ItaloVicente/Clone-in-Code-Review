commit 1d05ccf65b69e490471b8af36ab24c1caca798c3
Author: Pedro Ulisses <pedro.ulisses@aluno.uece.br>
Date:   Sun Apr 30 06:04:09 2023 0300

    First commited as after_156039_rev5

diff git a/org.eclipse.jgit.junit.ssh/pom.xml b/org.eclipse.jgit.junit.ssh/pom.xml
index 5c8110eb55..eb26b9e22c 100644
 a/org.eclipse.jgit.junit.ssh/pom.xml
 b/org.eclipse.jgit.junit.ssh/pom.xml
@@ 48,13 48,11 @@
     <dependency>
       <groupId>org.apache.sshd</groupId>
       <artifactId>sshdosgi</artifactId>
      <version>${apachesshdversion}</version>
     </dependency>
 
     <dependency>
       <groupId>org.apache.sshd</groupId>
       <artifactId>sshdsftp</artifactId>
      <version>${apachesshdversion}</version>
     </dependency>
 
     <dependency>
diff git a/org.eclipse.jgit.niofs.test/.classpath b/org.eclipse.jgit.niofs.test/.classpath
new file mode 100644
index 0000000000..f08af0a4e9
 /dev/null
 b/org.eclipse.jgit.niofs.test/.classpath
@@ 0,0 1,11 @@
<?xml version="1.0" encoding="UTF8"?>
<classpath>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE1.8"/>
	<classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
	<classpathentry kind="src" path="tst">
		<attributes>
			<attribute name="test" value="true"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="output" path="bin"/>
</classpath>
diff git a/org.eclipse.jgit.niofs.test/.gitignore b/org.eclipse.jgit.niofs.test/.gitignore
new file mode 100644
index 0000000000..934e0e06ff
 /dev/null
 b/org.eclipse.jgit.niofs.test/.gitignore
@@ 0,0 1,2 @@
/bin
/target
diff git a/org.eclipse.jgit.niofs.test/.project b/org.eclipse.jgit.niofs.test/.project
new file mode 100644
index 0000000000..d8e979903a
 /dev/null
 b/org.eclipse.jgit.niofs.test/.project
@@ 0,0 1,34 @@
<?xml version="1.0" encoding="UTF8"?>
<projectDescription>
	<name>org.eclipse.jgit.niofs.test</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.jdt.core.javabuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
		<buildCommand>
			<name>org.eclipse.pde.ManifestBuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
		<buildCommand>
			<name>org.eclipse.pde.SchemaBuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
		<buildCommand>
			<name>org.eclipse.pde.api.tools.apiAnalysisBuilder</name>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.pde.PluginNature</nature>
		<nature>org.eclipse.jdt.core.javanature</nature>
		<nature>org.eclipse.pde.api.tools.apiAnalysisNature</nature>
	</natures>
</projectDescription>
diff git a/org.eclipse.jgit.niofs.test/.settings/org.eclipse.jdt.core.prefs b/org.eclipse.jgit.niofs.test/.settings/org.eclipse.jdt.core.prefs
new file mode 100644
index 0000000000..0c68a61dca
 /dev/null
 b/org.eclipse.jgit.niofs.test/.settings/org.eclipse.jdt.core.prefs
@@ 0,0 1,7 @@
eclipse.preferences.version=1
org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled
org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.8
org.eclipse.jdt.core.compiler.compliance=1.8
org.eclipse.jdt.core.compiler.problem.assertIdentifier=error
org.eclipse.jdt.core.compiler.problem.enumIdentifier=error
org.eclipse.jdt.core.compiler.source=1.8
diff git a/org.eclipse.jgit.niofs.test/METAINF/MANIFEST.MF b/org.eclipse.jgit.niofs.test/METAINF/MANIFEST.MF
new file mode 100644
index 0000000000..fa8d03f112
 /dev/null
 b/org.eclipse.jgit.niofs.test/METAINF/MANIFEST.MF
@@ 0,0 1,58 @@
ManifestVersion: 1.0
BundleManifestVersion: 2
BundleName: %BundleName
BundleSymbolicName: org.eclipse.jgit.niofs.test
BundleVersion: 5.7.0.qualifier
AutomaticModuleName: org.eclipse.jgit.niofs.test
BundleRequiredExecutionEnvironment: JavaSE1.8
BundleVendor: %BundleVendor
ImportPackage: com.jcraft.jsch;version="[0.1.55,2.0.0)",
 javax.servlet;version="[3.1.0,4.0.0)",
 junit.framework;version="[4.12.0,5.0.0)",
 org.apache.commons.io;version="[2.6.0,3.0.0)",
 org.apache.sshd.common;version="[2.2.0,2.3.0)",
 org.apache.sshd.common.cipher;version="[2.2.0,2.3.0)",
 org.apache.sshd.common.helpers;version="[2.2.0,2.3.0)",
 org.apache.sshd.common.kex;version="[2.2.0,2.3.0)",
 org.apache.sshd.common.mac;version="[2.2.0,2.3.0)",
 org.apache.sshd.server;version="[2.2.0,2.3.0)",
 org.assertj.core.api;version="[3.14.0,4.0.0)",
 org.eclipse.jgit.api;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.api.errors;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.diff;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.dircache;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.errors;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.hooks;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.lib;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.fs;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.fs.attribute;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.fs.options;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.config;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.daemon.common;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.daemon.filter;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.daemon.git;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.daemon.http;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.daemon.ssh;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.hook;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.manager;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.op;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.op.commands;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.op.exceptions;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.op.model;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.security;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.niofs.internal.util;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.revwalk;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.transport;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.transport.resolver;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.treewalk;version="[5.7.0,5.8.0)",
 org.eclipse.jgit.util;version="[5.7.0,5.8.0)",
 org.junit;version="[4.12.0,5.0.0)",
 org.junit.runner;version="[4.12.0,5.0.0)",
 org.junit.runners;version="[4.12.0,5.0.0)",
 org.mockito;version="[2.23.0,3.0.0)",
 org.mockito.junit;version="[2.23.0,3.0.0)",
 org.mockito.runners;version="[2.23.0,3.0.0)",
 org.mockito.stubbing;version="[2.23.0,3.0.0)",
 org.slf4j;version="[1.7.2,2.0.0)"
diff git a/org.eclipse.jgit.niofs.test/build.properties b/org.eclipse.jgit.niofs.test/build.properties
new file mode 100644
index 0000000000..54c9cd2d79
 /dev/null
 b/org.eclipse.jgit.niofs.test/build.properties
@@ 0,0 1,4 @@
source.. = tst/
output.. = bin/
bin.includes = METAINF/,\
               .
diff git a/org.eclipse.jgit.niofs.test/pom.xml b/org.eclipse.jgit.niofs.test/pom.xml
new file mode 100644
index 0000000000..227c03664a
 /dev/null
 b/org.eclipse.jgit.niofs.test/pom.xml
@@ 0,0 1,204 @@
<?xml version="1.0" encoding="UTF8"?>
<!
  Copyright (C) 2019 Red Hat, Inc. and/or its affiliates.
  Copyright (C) 2020, Matthias Sohn <matthias.sohn@sap.com> and others

 This program and the accompanying materials are made available under the
 terms of the Eclipse Distribution License v. 1.0 which is available at
 https://www.eclipse.org/org/documents/edlv10.php.

 SPDXLicenseIdentifier: BSD3Clause

 >
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchemainstance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/mavenv4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>org.eclipse.jgit</groupId>
    <artifactId>org.eclipse.jgitparent</artifactId>
    <version>5.7.0SNAPSHOT</version>
  </parent>

  <artifactId>org.eclipse.jgit.niofs.test</artifactId>
  <name>JGit  NIO2 filesystem tests</name>

  <description>
    NIO2 filesystem tests
  </description>

  <properties>
    <maven.javadoc.skip>true</maven.javadoc.skip>
  </properties>

  <dependencies>
    <!URICodec>
    <dependency>
      <groupId>commonscodec</groupId>
      <artifactId>commonscodec</artifactId>
    </dependency>

    <dependency>
      <groupId>commonsio</groupId>
      <artifactId>commonsio</artifactId>
    </dependency>

    <dependency>
      <groupId>com.jcraft</groupId>
      <artifactId>jsch</artifactId>
    </dependency>


    <! HTTP Support >
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servletapi</artifactId>
      <scope>provided</scope>
    </dependency>

    <! Core Library >
    <dependency>
      <groupId>org.apache.sshd</groupId>
      <artifactId>sshdosgi</artifactId>
    </dependency>

    <dependency>
      <groupId>org.eclipse.jgit</groupId>
      <artifactId>org.eclipse.jgit</artifactId>
      <version>${project.version}</version>
    </dependency>

    <dependency>
      <groupId>org.eclipse.jgit</groupId>
      <artifactId>org.eclipse.jgit.niofs</artifactId>
      <version>${project.version}</version>
    </dependency>

    <dependency>
      <groupId>org.eclipse.jgit</groupId>
      <artifactId>org.eclipse.jgit.http.server</artifactId>
      <version>${project.version}</version>
    </dependency>

    <dependency>
      <groupId>org.jboss.byteman</groupId>
      <artifactId>byteman</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.jboss.byteman</groupId>
      <artifactId>bytemansubmit</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.jboss.byteman</groupId>
      <artifactId>bytemaninstall</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.jboss.byteman</groupId>
      <artifactId>bytemanbmunit</artifactId>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockitocore</artifactId>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.assertj</groupId>
      <artifactId>assertjcore</artifactId>
      <scope>test</scope>
    </dependency>

  </dependencies>

  <build>
    <testSourceDirectory>tst/</testSourceDirectory>
    <testResources>
      <testResource>
        <directory>resources/</directory>
      </testResource>
    </testResources>

    <resources>
      <resource>
        <directory>.</directory>
        <includes>
          <include>plugin.properties</include>
        </includes>
      </resource>
      <resource>
        <directory>resources/</directory>
      </resource>
    </resources>

    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>mavenjarplugin</artifactId>
        <executions>
          <execution>
            <goals>
              <goal>testjar</goal>
            </goals>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.jboss.byteman</groupId>
        <artifactId>bytemanrulecheckmavenplugin</artifactId>
        <configuration>
          <includes>
            <include>resources/**/*.btm</include>
          </includes>
        </configuration>
        <executions>
          <execution>
            <id>rulechecktest</id>
            <goals>
              <goal>rulecheck</goal>
            </goals>
            <phase>testcompile</phase>
            <configuration>
              <includes>
                <include>resources/**/*.btm</include>
              </includes>
              <verbose>true</verbose>
            </configuration>
          </execution>
        </executions>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>mavensurefireplugin</artifactId>
        <configuration>
          <useSystemClassLoader>true</useSystemClassLoader>
          <useManifestOnlyJar>true</useManifestOnlyJar>
          <forkMode>once</forkMode>
          <!<parallel>false</parallel>>
          <! ensure we don't inherit a byteman jar from any env settings >
          <environmentVariables>
            <BYTEMAN_HOME></BYTEMAN_HOME>
          </environmentVariables>
          <systemProperties>
            <property>
              <name>org.jboss.byteman.home</name>
              <value></value>
            </property>
            <property>
              <name>nio.git.dirname</name>
              <value>${project.build.directory}/.niogit</value>
            </property>
          </systemProperties>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/commit_exception.btm b/org.eclipse.jgit.niofs.test/resources/byteman/commit_exception.btm
new file mode 100644
index 0000000000..1835de1974
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/commit_exception.btm
@@ 0,0 1,18 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil.commit()

RULE exception on commit
CLASS org.eclipse.jgit.lib.RefUpdate
METHOD forceUpdate
AT ENTRY
IF TRUE
DO
   throw RuntimeException("dummy");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/check_path.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/check_path.btm
new file mode 100644
index 0000000000..00e0f496ae
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/check_path.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryCheckPath
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryCheckPath");
ENDRULE

RULE catch counter testRetryCheckPath
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT EXIT
IF readCounter("testRetryCheckPath") > 4
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_commits.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_commits.btm
new file mode 100644
index 0000000000..ce507a4cbd
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_commits.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryGetCommits
CLASS org.eclipse.jgit.revwalk.RevWalk
METHOD markStart(org.eclipse.jgit.revwalk.RevCommit)
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryGetCommits");
ENDRULE

RULE catch counter testRetryGetCommits
CLASS org.eclipse.jgit.revwalk.RevWalk
METHOD markStart(org.eclipse.jgit.revwalk.RevCommit)
AT EXIT
IF readCounter("testRetryGetCommits") > 3
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_last_commit.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_last_commit.btm
new file mode 100644
index 0000000000..f42fbe7d88
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/get_last_commit.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryGetLastCommit
CLASS org.eclipse.jgit.revwalk.RevCommit
METHOD parse(byte[])
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryGetLastCommit");
ENDRULE

RULE catch counter testRetryGetLastCommit
CLASS org.eclipse.jgit.revwalk.RevCommit
METHOD parse(byte[])
AT EXIT
IF readCounter("testRetryGetLastCommit") >  8 #each commit executes the RevCommit.parse too
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/list_path_content.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/list_path_content.btm
new file mode 100644
index 0000000000..74983aa7e1
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/list_path_content.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryListPathContent
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryListPathContent");
ENDRULE

RULE catch counter testRetryListPathContent
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT EXIT
IF readCounter("testRetryListPathContent") > 4
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_inputstream.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_inputstream.btm
new file mode 100644
index 0000000000..ccae474e88
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_inputstream.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryResolveInputStream
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryResolveInputStream");
ENDRULE

RULE catch counter testRetryResolveInputStream
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT EXIT
IF readCounter("testRetryResolveInputStream") > 4
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_path.btm b/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_path.btm
new file mode 100644
index 0000000000..12d8b59b90
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/retry/resolve_path.btm
@@ 0,0 1,28 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman creates an exception on JGitUtil, to simulate random
# issue found on Windows. (see RHBPMS4105)

RULE increment counter testRetryResolvePath
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT ENTRY
IF TRUE
DO
   incrementCounter("testRetryResolvePath");
ENDRULE

RULE catch counter testRetryResolvePath
CLASS org.eclipse.jgit.treewalk.TreeWalk
METHOD reset(org.eclipse.jgit.lib.AnyObjectId)
AT EXIT
IF readCounter("testRetryResolvePath") > 4
DO
   throw RuntimeException("almost random failure");
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/squash.btm b/org.eclipse.jgit.niofs.test/resources/byteman/squash.btm
new file mode 100644
index 0000000000..15a89b9a77
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/squash.btm
@@ 0,0 1,29 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman script makes both threads to reach locking
# state at the same time but then it resolve it.

RULE define rendezvous
CLASS me.porcelli.nio.jgit.impl.JGitFileSystemProvider
METHOD lockAndSquash
AT INVOKE java.nio.file.Path.getFileSystem
IF NOT isRendezvous("squash", 2)
DO createRendezvous("squash", 2, true);
   traceln("rendezvous created");
ENDRULE

RULE catch threads
CLASS me.porcelli.nio.jgit.impl.JGitFileSystemProvider
METHOD lockAndSquash
AFTER INVOKE java.nio.file.Path.getFileSystem
BIND threadName = Thread.currentThread().getName();
IF isRendezvous("squash", 2)
DO rendezvous("squash");
   rendezvous("squash")
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/squash_exception.btm b/org.eclipse.jgit.niofs.test/resources/byteman/squash_exception.btm
new file mode 100644
index 0000000000..62b8b89ab4
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/squash_exception.btm
@@ 0,0 1,16 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This scripts throws an Exception after invoking LOCK method.

RULE force exception
CLASS me.porcelli.nio.jgit.impl.op.commands.Squash
METHOD execute
IF TRUE
DO throw RuntimeException("check lock after")
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/byteman/squash_lock.btm b/org.eclipse.jgit.niofs.test/resources/byteman/squash_lock.btm
new file mode 100644
index 0000000000..6ab8b3536a
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/byteman/squash_lock.btm
@@ 0,0 1,27 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# This Byteman script makes both threads to reach locking
# state and cannot resolve it, so they remain locked for ever.

RULE define rendezvous
CLASS me.porcelli.nio.jgit.impl.JGitFileSystemProvider
METHOD lockAndSquash
AT INVOKE me.porcelli.nio.jgit.impl.JGitFileSystem.lock
IF TRUE
DO createRendezvous("rendezvous", 2, true);
ENDRULE

RULE catch threads
CLASS me.porcelli.nio.jgit.impl.JGitFileSystemProvider
METHOD lockAndSquash
AFTER INVOKE me.porcelli.nio.jgit.impl.JGitFileSystem.lock
IF isRendezvous("rendezvous", 2)
DO rendezvous("rendezvous");
   rendezvous("rendezvous")
ENDRULE
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/resources/images/drools.png b/org.eclipse.jgit.niofs.test/resources/images/drools.png
new file mode 100644
index 0000000000..41b2bd2efb
Binary files /dev/null and b/org.eclipse.jgit.niofs.test/resources/images/drools.png differ
diff git a/org.eclipse.jgit.niofs.test/resources/images/jbpm.png b/org.eclipse.jgit.niofs.test/resources/images/jbpm.png
new file mode 100644
index 0000000000..a200a649a1
Binary files /dev/null and b/org.eclipse.jgit.niofs.test/resources/images/jbpm.png differ
diff git a/org.eclipse.jgit.niofs.test/resources/images/opta.png b/org.eclipse.jgit.niofs.test/resources/images/opta.png
new file mode 100644
index 0000000000..d5baf43c38
Binary files /dev/null and b/org.eclipse.jgit.niofs.test/resources/images/opta.png differ
diff git a/org.eclipse.jgit.niofs.test/resources/simplelogger.properties b/org.eclipse.jgit.niofs.test/resources/simplelogger.properties
new file mode 100644
index 0000000000..cfaf28696f
 /dev/null
 b/org.eclipse.jgit.niofs.test/resources/simplelogger.properties
@@ 0,0 1,13 @@
# Copyright 2019 Red Hat, Inc. and/or its affiliates.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Distribution License v. 1.0 which is available at
# https://www.eclipse.org/org/documents/edlv10.php.
#
# SPDXLicenseIdentifier: BSD3Clause
#
# logging configuration for slf4jsimple
org.slf4j.simpleLogger.logFile=System.out
org.slf4j.simpleLogger.defaultLogLevel=info
# Some logging categories that may be of interest when troubleshooting:
org.slf4j.simpleLogger.log.me.porcelli.nio.jgit.impl.op.commands=debug
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/AbstractTestInfra.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/AbstractTestInfra.java
new file mode 100644
index 0000000000..81caeaf53c
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/AbstractTestInfra.java
@@ 0,0 1,309 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
import org.eclipse.jgit.util.FS_POSIX;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.ProcessResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;

public abstract class AbstractTestInfra {

	static class TestFile {

		final String path;
		final String content;

		TestFile(final String path, final String content) {
			this.path = path;
			this.content = content;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(AbstractTestInfra.class);

	protected static final Map<String, Object> EMPTY_ENV = Collections.emptyMap();

	protected static final List<File> tempFiles = new ArrayList<>();

	protected JGitFileSystemProvider provider;

	@Before
	public void createGitFsProvider() throws IOException {
		provider = new JGitFileSystemProvider(getGitPreferences());
	}

	/*
	 * Default Git preferences suitable for most of the tests. If specific test
	 * needs some custom configuration, it needs to override this method and provide
	 * own map of preferences.
	 */
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = new HashMap<>();
		// disable the daemons by default as they not needed in most of the cases
		gitPrefs.put(GIT_DAEMON_ENABLED, "false");
		gitPrefs.put(GIT_SSH_ENABLED, "false");
		return gitPrefs;
	}

	@After
	public void destroyGitFsProvider() throws IOException {
		if (provider == null) {
			// this would mean that setup failed. no need to clean up.
			return;
		}

		provider.shutdown();

		if (provider.getGitRepoContainerDir() != null && provider.getGitRepoContainerDir().exists()) {
			FileUtils.delete(provider.getGitRepoContainerDir(), FileUtils.RECURSIVE);
		}
	}

	@AfterClass
	@BeforeClass
	public static void cleanup() {
		for (final File tempFile : tempFiles) {
			try {
				FileUtils.delete(tempFile, FileUtils.RECURSIVE);
			} catch (IOException e) {
			}
		}
	}

	protected Git setupGit() throws IOException, GitAPIException {
		return setupGit(createTempDirectory());
	}

	protected Git setupGit(final File tempDir) throws IOException, GitAPIException {

		final Git git = Git.createRepository(tempDir);

		new Commit(git, "master", new CommitInfo(null, "name", "name@example.com", "cool1", null, null), false, null,
				new DefaultCommitContent(new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("content"));
						put("file2.txt", tempFile("content2"));
					}
				})).execute();

		return git;
	}

	protected static File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: "  temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "  temp.getAbsolutePath());
		}

		tempFiles.add(temp);

		return temp;
	}

	public static File tempFile(final String content) throws IOException {
		final File file = File.createTempFile("bar", "foo");
		final OutputStream out = new FileOutputStream(file);

		if (content != null && !content.isEmpty()) {
			out.write(content.getBytes());
			out.flush();
		}

		out.close();
		return file;
	}

	public File tempFile(final byte[] content) throws IOException {
		final File file = File.createTempFile("bar", "foo");
		final FileOutputStream out = new FileOutputStream(file);

		if (content != null && content.length > 0) {
			out.write(content);
			out.flush();
		}

		out.close();
		return file;
	}

	public PersonIdent getAuthor() {
		return new PersonIdent("user", "user@example.com");
	}

	public static int findFreePort() {
		int port = 0;
		try {
			ServerSocket server = new ServerSocket(0);
			port = server.getLocalPort();
			server.close();
		} catch (IOException e) {
			Assert.fail("Can't find free port!");
		}
		logger.debug("Found free port "  port);
		return port;
	}

	protected byte[] loadImage(final String path) throws IOException {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
		StringWriter writer = new StringWriter();
		IOUtils.copy(stream, writer, Charset.defaultCharset());
		return writer.toString().getBytes();
	}

	static void commit(final Git origin, final String branchName, final String message, final TestFile... testFiles)
			throws IOException {
		final Map<String, File> data = Arrays.stream(testFiles).collect(toMap(f > f.path, f > tmpFile(f.content)));
		new Commit(origin, branchName, "name", "name@example.com", message, null, null, false, data).execute();
	}

	public static File tmpFile(final String content) {
		try {
			return tempFile(content);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static TestFile content(final String path, final String content) {
		return new TestFile(path, content);
	}

	/**
	 * Creates mock hook in defined hooks directory.
	 * 
	 * @param hooksDirectory Directory in which mock hook is created.
	 * @param hookName       Name of the created hook. This is the filename of
	 *                       created hook file.
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	void writeMockHook(final File hooksDirectory, final String hookName)
			throws FileNotFoundException, UnsupportedEncodingException {
		final PrintWriter writer = new PrintWriter(new File(hooksDirectory, hookName), "UTF8");
		writer.println("# something");
		writer.close();
	}

	/**
	 * Tests if defined hook was executed or not.
	 * 
	 * @param gitRepoName    Name of test git repository that is created for
	 *                       committing changes.
	 * @param testedHookName Tested hook name. This hook is checked for its
	 *                       execution.
	 * @param wasExecuted    Expected hook execution state. If true, test expects
	 *                       that defined hook is executed. If false, test expects
	 *                       that defined hook is not executed.
	 * @throws IOException
	 */
	void testHook(final String gitRepoName, final String testedHookName, final boolean wasExecuted) throws IOException {
		final URI newRepo = URI.create("git://"  gitRepoName);

		final AtomicBoolean hookExecuted = new AtomicBoolean(false);
		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		provider.setDetectedFS(new FS_POSIX() {
			@Override
			public ProcessResult runHookIfPresent(Repository repox, String hookName, String[] args)
					throws JGitInternalException {
				if (hookName.equals(testedHookName)) {
					hookExecuted.set(true);
				}
				return new ProcessResult(ProcessResult.Status.OK);
			}
		});

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://user_branch@"  gitRepoName  "/some/path/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");

		if (wasExecuted) {
			assertThat(hookExecuted.get()).isTrue();
		} else {
			assertThat(hookExecuted.get()).isFalse();
		}
	}

	protected Ref branch(Git origin, String source, String target) throws Exception {
		final Repository repo = origin.getRepository();
		return org.eclipse.jgit.api.Git.wrap(repo).branchCreate().setName(target).setStartPoint(source).call();
	}

	protected List<Ref> listRefs(final Git cloned) {
		return new ListRefs(cloned.getRepository()).execute();
	}

	protected static String multiline(String prefix, String... lines) {
		return Arrays.stream(lines).map(s > prefix  s).reduce((s1, s2) > s1  "\n"  s2).orElse("");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/ExecutorServiceProducer.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/ExecutorServiceProducer.java
new file mode 100644
index 0000000000..bf1e5d96c9
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/ExecutorServiceProducer.java
@@ 0,0 1,87 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jgit.niofs.internal.util.DescriptiveThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ExecutorService Producer. It produces managed and unmanaged executor
 * services. For now the implementation is the same but it could change if any
 * other container gets under support. They are in different variables on
 * purpose.
 */
public class ExecutorServiceProducer {

	private Logger logger = LoggerFactory.getLogger(ExecutorServiceProducer.class);

	private final ExecutorService executorService;
	private final ExecutorService unmanagedExecutorService;
	private final ExecutorService indexingExecutorService;

	protected static final String MANAGED_LIMIT_PROPERTY = "concurrent.managed.thread.limit";
	protected static final String UNMANAGED_LIMIT_PROPERTY = "concurrent.unmanaged.thread.limit";
	protected static final String INDEXING_LIMIT_PROPERTY = "concurrent.indexing.thread.limit";

	public ExecutorServiceProducer() {
		this.executorService = this.buildFixedThreadPoolExecutorService(MANAGED_LIMIT_PROPERTY);
		this.unmanagedExecutorService = this.buildFixedThreadPoolExecutorService(UNMANAGED_LIMIT_PROPERTY);
		this.indexingExecutorService = this.buildFixedThreadPoolExecutorService(INDEXING_LIMIT_PROPERTY);
	}

	protected ExecutorService buildFixedThreadPoolExecutorService(String key) {
		String stringProperty = System.getProperty(key);
		int threadLimit = stringProperty == null ? 0 : toInteger(stringProperty);
		if (threadLimit > 0) {
			return Executors.newFixedThreadPool(threadLimit, new DescriptiveThreadFactory());
		} else {
			return Executors.newCachedThreadPool(new DescriptiveThreadFactory());
		}
	}

	private Integer toInteger(String stringProperty) {
		try {
			return Integer.valueOf(stringProperty);
		} catch (NumberFormatException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Property {} is invalid, defaulting to 0", stringProperty);
			}
			return 0;
		}
	}

	public ExecutorService produceExecutorService() {
		return this.getManagedExecutorService();
	}

	public ExecutorService produceUnmanagedExecutorService() {
		return this.getUnmanagedExecutorService();
	}

	public ExecutorService produceIndexingExecutorService() {
		return this.getIndexingExecutorService();
	}

	protected ExecutorService getManagedExecutorService() {
		return this.executorService;
	}

	protected ExecutorService getUnmanagedExecutorService() {
		return this.unmanagedExecutorService;
	}

	protected ExecutorService getIndexingExecutorService() {
		return this.indexingExecutorService;
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/HiddenBranchRefFilterTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/HiddenBranchRefFilterTest.java
new file mode 100644
index 0000000000..c4f2cb9546
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/HiddenBranchRefFilterTest.java
@@ 0,0 1,57 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class HiddenBranchRefFilterTest {

	private HiddenBranchRefFilter filter;

	@Mock
	private Ref ref;
	private Map<String, Ref> refs;

	@Before
	public void setUp() {

		refs = new HashMap<>();
		refs.put("master", ref);
		refs.put("develop", ref);
		refs.put("PRfrom/developmaster", ref);
		refs.put("PR1master", ref);
		refs.put("PRmaster", ref);
		refs.put("PR1from/developmaster", ref);

		filter = new HiddenBranchRefFilter();
	}

	@Test
	public void testHiddenBranchsFiltering() {
		final Map<String, Ref> filteredRefs = filter.filter(refs);
		final Set<Map.Entry<String, Ref>> set = filteredRefs.entrySet();
		assertEquals(5, set.size());
		assertFalse(set.stream().anyMatch(entry > entry.getKey().equals("PR1from/developmaster")));
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCacheInvalidationTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCacheInvalidationTest.java
new file mode 100644
index 0000000000..0dd82068bd
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCacheInvalidationTest.java
@@ 0,0 1,134 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsCache;
import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_EVICT_THRESHOLD_DURATION;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_INSTANCES;

public class JGitCacheInvalidationTest extends AbstractTestInfra {

	private JGitFileSystemsCache fsCache;
	private JGitFileSystemsManager fsManager;

	@Before
	public void createGitFsProvider() throws IOException {
		Map<String, String> gitPreferences = getGitPreferences();
		gitPreferences.put(JGIT_CACHE_EVICT_THRESHOLD_DURATION, "1");
		gitPreferences.put(JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT, TimeUnit.MILLISECONDS.name());
		gitPreferences.put(JGIT_CACHE_INSTANCES, "2");
		provider = new JGitFileSystemProvider(gitPreferences);
		fsManager = provider.getFsManager();
		fsCache = fsManager.getFsCache();
	}

	@Test
	public void testTwoInstancesForSameFS() throws IOException {
		String fs1Name = "dora";
		String fs2Name = "bento";
		String fs3Name = "bela";

		final JGitFileSystemProxy fs1 = (JGitFileSystemProxy) provider.newFileSystem(URI.create("git://"  fs1Name),
				EMPTY_ENV);
		final JGitFileSystemImpl realInstanceFs1 = (JGitFileSystemImpl) fs1.getRealJGitFileSystem();

		final FileSystem fs2 = provider.newFileSystem(URI.create("git://"  fs2Name), EMPTY_ENV);
		final FileSystem fs3 = provider.newFileSystem(URI.create("git://"  fs3Name), EMPTY_ENV);

		assertThat(fs1).isNotNull();
		assertThat(fs2).isNotNull();
		assertThat(fs3).isNotNull();

		// only proxies instances
		assertThat(fs1).isInstanceOf(JGitFileSystemProxy.class);
		assertThat(fs2).isInstanceOf(JGitFileSystemProxy.class);
		assertThat(fs3).isInstanceOf(JGitFileSystemProxy.class);

		// all the fs have suppliers registered
		assertThat(fsCache.getFileSystems()).contains(fs1.getName());
		assertThat(fsCache.getFileSystems()).contains(((JGitFileSystem) fs2).getName());
		assertThat(fsCache.getFileSystems()).contains(((JGitFileSystem) fs3).getName());

		// only the last two FS are memoized
		JGitFileSystemsCache.JGitFileSystemsCacheInfo cacheInfo = fsCache.getCacheInfo();

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs2).getName());
		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs3).getName());

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).doesNotContain(fs1.getName());

		// a hit on fs1 in order to put him on cache
		JGitFileSystemProxy anotherInstanceOfFs1Proxy = (JGitFileSystemProxy) fsManager.get(fs1Name);
		JGitFileSystemImpl anotherInstanceOfFs1 = (JGitFileSystemImpl) anotherInstanceOfFs1Proxy
				.getRealJGitFileSystem();

		// now fs2 are not memoized
		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(fs1.getName());
		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs3).getName());

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).doesNotContain(((JGitFileSystem) fs2).getName());

		// asserting that fs1 and anotherInstanceOfFs1 are instances of the same fs
		assertThat(realInstanceFs1.getName()).isEqualToIgnoringCase(anotherInstanceOfFs1.getName());
		// they share the same lock
		assertThat(realInstanceFs1.getLock()).isEqualTo(anotherInstanceOfFs1.getLock());

		// now lets commit on both instances and read with other one
		new Commit(realInstanceFs1.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("realInstanceFs1File.txt", tempFile("dora"));
					}
				}).execute();

		InputStream stream = provider.newInputStream(anotherInstanceOfFs1.getPath("realInstanceFs1File.txt"));
		assertNotNull(stream);
		String content = new Scanner(stream).useDelimiter("\\A").next();
		assertEquals("dora", content);

		new Commit(anotherInstanceOfFs1.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("anotherInstanceOfFs1File.txt", tempFile("bento"));
					}
				}).execute();

		stream = provider.newInputStream(realInstanceFs1.getPath("anotherInstanceOfFs1File.txt"));
		assertNotNull(stream);
		content = new Scanner(stream).useDelimiter("\\A").next();
		assertEquals("bento", content);

		realInstanceFs1.lock();
		assertThat(realInstanceFs1.hasBeenInUse()).isTrue();
		assertThat(anotherInstanceOfFs1.hasBeenInUse()).isTrue();

		// Unlock the lock so that cleanup can finish on Windows
		realInstanceFs1.unlock();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCloneTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCloneTest.java
new file mode 100644
index 0000000000..c4967fda1e
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitCloneTest.java
@@ 0,0 1,231 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JGitCloneTest extends AbstractTestInfra {

	private static final String TARGET_GIT = "target/target", SOURCE_GIT = "source/source";

	@Test
	public void testToCloneSuccess() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, null);

		final Git cloned = new Clone(gitTarget, gitSource.getAbsolutePath(), false, null,
				CredentialsProvider.getDefault(), null, null, true).execute().get();

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size(),
				new ListRefs(origin.getRepository()).execute().size());

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
	}

	@Test
	public void cloneShouldOnlyWorksWithEmptyRepos() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, null);

		final Git cloned = new Clone(gitTarget, gitSource.getAbsolutePath(), false, null,
				CredentialsProvider.getDefault(), null, null, true).execute().get();

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size(),
				new ListRefs(origin.getRepository()).execute().size());

		assertThatThrownBy(() > new Clone(gitTarget, gitSource.getAbsolutePath(), false, null,
				CredentialsProvider.getDefault(), null, null, true).execute().get())
						.isInstanceOf(Clone.CloneException.class);
	}

	@Test
	public void testCloneWithHookDir() throws IOException, GitAPIException {
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir, PostCommitHook.NAME);
		writeMockHook(hooksDir, PreCommitHook.NAME);

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, hooksDir);

		final Git cloned = new Clone(gitTarget, gitSource.getAbsolutePath(), false, null,
				CredentialsProvider.getDefault(), null, hooksDir, true).execute().get();

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);
		assertEquals(new ListRefs(cloned.getRepository()).execute().size(),
				new ListRefs(origin.getRepository()).execute().size());

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");

		boolean foundPreCommitHook = false;
		boolean foundPostCommitHook = false;
		File[] hooks = new File(cloned.getRepository().getDirectory(), "hooks").listFiles();
		assertThat(hooks).isNotEmpty().isNotNull();
		assertThat(hooks.length).isEqualTo(2);
		for (File hook : hooks) {
			if (hook.getName().equals(PreCommitHook.NAME)) {
				foundPreCommitHook = hook.canExecute();
			} else if (hook.getName().equals(PostCommitHook.NAME)) {
				foundPostCommitHook = hook.canExecute();
			}
		}
		assertThat(foundPreCommitHook).isTrue();
		assertThat(foundPostCommitHook).isTrue();
	}

	private Git setupGitRepo(File gitSource, File hooksDir) throws IOException {
		final Git origin = new CreateRepository(gitSource, hooksDir, true).execute().get();

		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2222"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();
		return origin;
	}

	@Test
	public void cloneNotMirrorRepoConfigTest() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, null);

		boolean isMirror = false;
		boolean sslVerify = true;
		final Git clonedNotMirror = new Clone(gitTarget, gitSource.getAbsolutePath(), isMirror, null,
				CredentialsProvider.getDefault(), null, null, sslVerify).execute().get();

		assertThat(clonedNotMirror).isNotNull();

		StoredConfig config = clonedNotMirror.getRepository().getConfig();

		assertNotEquals(Clone.REFS_MIRRORED, config.getString("remote", "origin", "fetch"));
		assertNull(config.getString("remote", "origin", "mirror"));
		assertEquals(gitSource.getAbsolutePath(), config.getString("remote", "origin", "url"));

		boolean missingDefaultValue = true;
		assertEquals(missingDefaultValue, config.getBoolean("http", null, "sslVerify", missingDefaultValue));
	}

	@Test
	public void cloneMirrorRepoNoSSLVerifyConfigTest() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, null);

		assertTrue(provider.config.isSslVerify());

		boolean isMirror = true;
		boolean sslVerify = false;
		final Git clonedMirror = new Clone(gitTarget, gitSource.getAbsolutePath(), isMirror, null,
				CredentialsProvider.getDefault(), null, null, sslVerify).execute().get();

		assertThat(clonedMirror).isNotNull();

		StoredConfig config = clonedMirror.getRepository().getConfig();

		assertEquals(Clone.REFS_MIRRORED, config.getString("remote", "origin", "fetch"));
		assertNull(config.getString("remote", "origin", "mirror"));
		assertEquals(gitSource.getAbsolutePath(), config.getString("remote", "origin", "url"));
		assertEquals(sslVerify, config.getBoolean("http", null, "sslVerify", !sslVerify));
	}

	@Test
	public void testCloneMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = setupGitRepo(gitSource, null);

		commit(origin, "master", "first", content("dir1/file.txt", "foo"), content("dir2/file2.txt", "bar"),
				content("file3.txt", "moogah"));

		branch(origin, "master", "dev");
		commit(origin, "dev", "second", content("dir1/file.txt", "foo1"), content("file3.txt", "bar1"));

		branch(origin, "master", "ignored");
		commit(origin, "ignored", "third", content("dir1/file.txt", "foo2"));

		final Git cloned = new Clone(gitTarget, gitSource.getAbsolutePath(), false, asList("master", "dev"),
				CredentialsProvider.getDefault(), null, null, false).execute().get();

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref > ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master", "refs/heads/dev");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitConflictBranchesCheckerTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitConflictBranchesCheckerTest.java
new file mode 100644
index 0000000000..16c713e231
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitConflictBranchesCheckerTest.java
@@ 0,0 1,113 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitConflictBranchesCheckerTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	private static final List<String> TXT_FILES = Stream.of("file0", "file1", "file2", "file3", "file4")
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1", "Line2", "Line3", "Line4" };

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));

		new CreateBranch((GitImpl) git, MASTER_BRANCH, DEVELOP_BRANCH).execute();
	}

	@Test
	public void testReportConflictsAllFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2ChangedDev", "Line3", "Line4")),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), "Line1", "Line2ChangedDev", "Line3", "Line4")));

		commit(git, MASTER_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2ChangedMaster", "Line3", "Line4")),
				content(TXT_FILES.get(2),
						multiline(TXT_FILES.get(2), "Line1", "Line2ChangedMaster", "Line3", "Line4")));

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(conflicts).isNotEmpty();
		assertThat(conflicts).hasSize(2);
		assertThat(conflicts.get(0)).isEqualTo(TXT_FILES.get(1));
		assertThat(conflicts.get(1)).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testReportConflictsSomeFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2ChangedDev", "Line3", "Line4")),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), "Line1", "Line2ChangedDev", "Line3", "Line4")));

		commit(git, MASTER_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2ChangedMaster", "Line3", "Line4")),
				content(TXT_FILES.get(2),
						multiline(TXT_FILES.get(2), "Line1", "Line2", "Line3", "Line4ChangedMaster")));

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(conflicts).isNotEmpty();
		assertThat(conflicts).hasSize(1);
		assertThat(conflicts.get(0)).isEqualTo(TXT_FILES.get(1));
	}

	@Test
	public void testReportConflictsNoFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2ChangedDev", "Line3", "Line4")),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), "Line1", "Line2ChangedDev", "Line3", "Line4")));

		commit(git, MASTER_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2", "Line3", "Line4ChangedMaster")),
				content(TXT_FILES.get(2),
						multiline(TXT_FILES.get(2), "Line1", "Line2", "Line3", "Line4ChangedMaster")));

		List<String> conflicts = git.conflictBranchesChecker(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(conflicts).isEmpty();
	}

	@Test(expected = GitException.class)
	public void testInvalidBranch() {
		git.conflictBranchesChecker(MASTER_BRANCH, "invalidbranch");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitDiffBranchesTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitDiffBranchesTest.java
new file mode 100644
index 0000000000..535b17bf9d
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitDiffBranchesTest.java
@@ 0,0 1,242 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.fs.attribute.FileDiff;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitDiffBranchesTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";
	private static final String NON_EXISTENT_FILE = "/dev/null";

	private static final List<String> TXT_FILES = Stream.of("file0", "file1", "file2", "file3", "file4")
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1", "Line2", "Line3", "Line4" };

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));

		new CreateBranch((GitImpl) git, MASTER_BRANCH, DEVELOP_BRANCH).execute();
	}

	@Test
	public void testDiffWithAddedFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Adding files",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff > assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString()));

		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(3));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithRemovedFile() {
		new Commit(git, DEVELOP_BRANCH, "name", "name@example.com", "Removing file", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(0), null);
					}
				}).execute();

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);

		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(0));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(0);
	}

	@Test
	public void testDiffWithUpdatedFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2Changed", "Line3", "Line4")),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), "Line1", "Line2Changed", "Line3", "Line4")));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff > assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString()));

		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(1).getNameB()).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testDiffWithUpdateFirstLine() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating file",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1Changed", "Line2", "Line3", "Line4")));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(1);
	}

	@Test
	public void testDiffWithUpdateLastLine() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating file",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2", "Line3", "Line4Changed")));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithUpdateTwoConsecutiveLines() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating file", content(TXT_FILES.get(1),
				multiline(TXT_FILES.get(1), "Line1", "Line2Changed", "Line3Changed", "Line4")));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(3);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(3);
	}

	@Test
	public void testDiffWithUpdateFirstAndLastLines() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating file", content(TXT_FILES.get(1),
				multiline(TXT_FILES.get(1), "Line1Changed", "Line2", "Line3", "Line4Changed")));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(2);

		fileDiffs.forEach(diff > {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
			assertThat(diff.getNameA()).isEqualTo(TXT_FILES.get(1));
			assertThat(diff.getNameB()).isEqualTo(TXT_FILES.get(1));
		});

		assertThat(fileDiffs.get(0).getStartA()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndA()).isEqualTo(1);
		assertThat(fileDiffs.get(0).getStartB()).isEqualTo(0);
		assertThat(fileDiffs.get(0).getEndB()).isEqualTo(1);

		assertThat(fileDiffs.get(1).getStartA()).isEqualTo(3);
		assertThat(fileDiffs.get(1).getEndA()).isEqualTo(4);
		assertThat(fileDiffs.get(1).getStartB()).isEqualTo(3);
		assertThat(fileDiffs.get(1).getEndB()).isEqualTo(4);
	}

	@Test
	public void testDiffWithAddedRemovedUpdatedFiles() throws IOException {
		new Commit(git, DEVELOP_BRANCH, "name", "name@example.com", "Removing file0", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(0), null);
					}
				}).execute();

		commit(git, DEVELOP_BRANCH, "Updating file1",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2Changed", "Line3", "Line4")));

		commit(git, DEVELOP_BRANCH, "Adding file3",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)));

		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(3);

		assertThat(fileDiffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(fileDiffs.get(0).getNameA()).isEqualTo(TXT_FILES.get(0));
		assertThat(fileDiffs.get(0).getNameB()).isEqualTo(NON_EXISTENT_FILE);

		assertThat(fileDiffs.get(1).getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
		assertThat(fileDiffs.get(1).getNameA()).isEqualTo(TXT_FILES.get(1));
		assertThat(fileDiffs.get(1).getNameB()).isEqualTo(TXT_FILES.get(1));

		assertThat(fileDiffs.get(2).getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
		assertThat(fileDiffs.get(2).getNameA()).isEqualTo(NON_EXISTENT_FILE);
		assertThat(fileDiffs.get(2).getNameB()).isEqualTo(TXT_FILES.get(3));
	}

	@Test
	public void testDiffWithNonExistentBranch() {
		List<FileDiff> fileDiffs = git.diffRefs(MASTER_BRANCH, "nonExistentBranch");

		assertThat(fileDiffs).isEmpty();
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemBuilderTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemBuilderTest.java
new file mode 100644
index 0000000000..991bda1167
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemBuilderTest.java
@@ 0,0 1,54 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.eclipse.jgit.niofs.JGitFileSystemBuilder;
import org.eclipse.jgit.util.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class JGitFileSystemBuilderTest {

	@AfterClass
	public static void cleanup() {
		try {
			FileUtils.delete(new File(".niogit"), FileUtils.RECURSIVE);
		} catch (IOException ex) {
			// ignore
		}
	}

	@Test
	public void testSimpleBuilderSample() throws IOException {
		final FileSystem fs = JGitFileSystemBuilder.newFileSystem("myrepo");

		Path foo = fs.getPath("/foo");
		Files.createDirectory(foo);

		Path hello = foo.resolve("hello.txt"); // /foo/hello.txt

		Files.write(hello, Collections.singletonList("hello world"), StandardCharsets.UTF_8);

		assertEquals("hello world", Files.readAllLines(hello).get(0));
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderBytemanTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderBytemanTest.java
new file mode 100644
index 0000000000..fa1f34e738
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderBytemanTest.java
@@ 0,0 1,347 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
import org.eclipse.jgit.niofs.fs.options.SquashOption;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
import org.eclipse.jgit.revwalk.RevCommit;
import org.jboss.byteman.contrib.bmunit.BMScript;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(org.jboss.byteman.contrib.bmunit.BMUnitRunner.class)
@BMUnitConfig(loadDirectory = "target/testclasses", debug = true) // set "debug=true to see debug output
public class JGitFileSystemImplProviderBytemanTest extends AbstractTestInfra {

	private static Logger logger = LoggerFactory.getLogger(JGitFileSystemImplProviderBytemanTest.class);

	@Before
	public void createGitFsProvider() throws IOException {
		provider = new JGitFileSystemProvider();
	}

	@Test
	@BMScript(value = "byteman/squash.btm")
	public void testConcurrentSquashWithThreeCommit() throws IOException, GitAPIException {
		final URI newRepo = URI.create("git://threesquashrepo");
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		final CyclicBarrier threadsFinishedBarrier = new CyclicBarrier(3);
		final Path master = provider.getPath(URI.create("git://threesquashrepo"));
		final RevCommit commit = commitThreeTimesAndGetReference(fs, "threesquashrepo", "master", "t1");

		Thread t1 = new Thread(() > {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH "  commit.getName()  "  "  commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing a!", new Date(),
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER1: Squashing");
			try {
				provider.setAttribute(master, SquashOption.SQUASH_ATTR, squashOption);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		Thread t2 = new Thread(() > {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH "  commit.getName()  "  "  commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing b!", new Date(),
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER2: Squashing");
			try {
				provider.setAttribute(master, SquashOption.SQUASH_ATTR, squashOption);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		t1.setName("COMMITTER1");
		t2.setName("COMMITTER2");
		t2.start();
		t1.start();

		waitFor(threadsFinishedBarrier);

		assertEquals(2, getCommitsFromBranch((GitImpl) fs.getGit(), "master").size());
	}

	@Test
	@BMScript(value = "byteman/squash.btm")
	public void testConcurrentSquashWithSixCommit() throws IOException, GitAPIException {
		final URI newRepo = URI.create("git://bytemansixsquashrepo");
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		final CyclicBarrier threadsFinishedBarrier = new CyclicBarrier(3);
		final Path master = provider.getPath(URI.create("git://master@bytemansixsquashrepo"));
		final RevCommit commit = commitSixTimesAndGetReference(fs, "bytemansixsquashrepo", "master", "t1");

		Thread t1 = new Thread(() > {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH "  commit.getName()  "  "  commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing a!", new Date(),
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER1: Squashing");
			try {
				provider.setAttribute(master, SquashOption.SQUASH_ATTR, squashOption);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		Thread t2 = new Thread(() > {
			logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH "  commit.getName()  "  "  commit.getFullMessage());
			printLog(fs.getGit());
			VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing b!", new Date(),
					commit.getName());
			SquashOption squashOption = new SquashOption(record);
			logger.info("COMMITTER2: Squashing");
			try {
				provider.setAttribute(master, SquashOption.SQUASH_ATTR, squashOption);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			printLog(fs.getGit());
			waitFor(threadsFinishedBarrier);
		});

		t1.setName("COMMITTER1");
		t2.setName("COMMITTER2");
		t2.start();
		t1.start();

		waitFor(threadsFinishedBarrier);

		assertEquals(2, getCommitsFromBranch((GitImpl) fs.getGit(), "master").size());
	}

	@Test
	@BMScript(value = "byteman/squash_exception.btm")
	public void testForceExceptionWhenTryingToSquash() throws IOException, GitAPIException {

		final URI newRepo = URI.create("git://bytemanexceptionsquashrepo");
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path master = provider.getPath(URI.create("git://master@bytemanexceptionsquashrepo"));
		final RevCommit commit = commitThreeTimesAndGetReference(fs, "bytemanexceptionsquashrepo", "master", "t1");

		logger.info("<<<<<<<<<<<<< COMMIT TO SQUASH "  commit.getName()  "  "  commit.getFullMessage());
		printLog(fs.getGit());
		VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing a!", new Date(),
				commit.getName());
		SquashOption squashOption = new SquashOption(record);
		logger.info("COMMITTER1: Squashing");

		try {
			provider.setAttribute(master, SquashOption.SQUASH_ATTR, squashOption);
		} catch (Exception e) {
			fs.lock();
			fs.unlock();
		}

		assertEquals(3, getCommitsFromBranch((GitImpl) fs.getGit(), "master").size());
	}

	@Test
	@BMScript(value = "byteman/commit_exception.btm")
	public void testFileSystemLockOnException() throws IOException, GitAPIException {

		final URI newRepo = URI.create("git://bytemanexceptioncommitrepo");
		final JGitFileSystemProxy fsProxy = (JGitFileSystemProxy) provider.newFileSystem(newRepo, EMPTY_ENV);
		JGitFileSystem fs = fsProxy.getRealJGitFileSystem();

		final Path path = provider.getPath(URI.create("git://master@bytemanexceptioncommitrepo/myfile.txt"));

		try {
			writeFile(fs, path, "master");
		} catch (RuntimeException e) {
		}

		// fs must be unlocked
		Object lock = null;
		try {
			Field field = JGitFileSystemImpl.class.getDeclaredField("lock");
			field.setAccessible(true);
			lock = field.get(fs);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		Object isLocked = null;
		try {
			Method method = lock.getClass().getMethod("hasBeenInUse");
			isLocked = method.invoke(lock);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertTrue(((Boolean) isLocked));
	}

	private VersionRecord makeVersionRecord(final String author, final String email, final String comment,
			final Date date, final String commit) {
		return new VersionRecord() {
			@Override
			public String id() {
				return commit;
			}

			@Override
			public String author() {
				return author;
			}

			@Override
			public String email() {
				return email;
			}

			@Override
			public String comment() {
				return comment;
			}

			@Override
			public Date date() {
				return date;
			}

			@Override
			public String uri() {
				return null;
			}
		};
	}

	private static void printLog(final Git git) {
		try {
			for (final RevCommit revCommit : ((GitImpl) git)._log().call()) {
				logger.info("[LOG]: "  revCommit.getName()  "  "  revCommit.getFullMessage());
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	protected static void waitFor(CyclicBarrier barrier) {
		String threadName = Thread.currentThread().getName();
		try {
			logger.info(threadName  " request for await");
			barrier.await();
			logger.info(threadName  " await finished");
		} catch (InterruptedException e) {
			fail("Thread '"  threadName  "' was interrupted while waiting for the other threads!");
		} catch (BrokenBarrierException e) {
			fail("Thread '"  threadName  "' barrier was broken while waiting for the other threads!");
		}
	}

	private RevCommit commitThreeTimesAndGetReference(JGitFileSystem fs, String repo, String branch, String thread) {
		try {
			final Path path = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile1.txt"));
			final Path path2 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile2.txt"));
			final Path path3 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile3.txt"));

			final RevCommit commit = writeFile(fs, path, branch);
			writeFile(fs, path2, branch);
			writeFile(fs, path3, branch);

			return commit;
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	private RevCommit commitSixTimesAndGetReference(JGitFileSystem fs, String repo, String branch, String thread) {
		try {
			final Path path = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile1.txt"));
			final Path path2 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile2.txt"));
			final Path path3 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile3.txt"));
			final Path path4 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile4.txt"));
			final Path path5 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile5.txt"));
			final Path path6 = provider
					.getPath(URI.create("git://"  branch  "@"  repo  "/"  thread  "myfile6.txt"));

			final RevCommit commit = writeFile(fs, path, branch);
			writeFile(fs, path2, branch);
			writeFile(fs, path3, branch);
			writeFile(fs, path4, branch);
			writeFile(fs, path5, branch);
			writeFile(fs, path6, branch);

			return commit;
		} catch (IOException | GitAPIException e) {
			throw new RuntimeException(e);
		}
	}

	private RevCommit writeFile(final JGitFileSystem fs, final Path path, String branch)
			throws IOException, GitAPIException {
		final OutputStream stream = provider.newOutputStream(path);
		logger.info("Writing file: "  path.getFileName().toString());
		stream.write("my cool content".getBytes());
		stream.close();
		return this.getCommitsFromBranch((GitImpl) fs.getGit(), branch).get(0);
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin, String branch)
			throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository(), branch).execute().getObjectId();
		for (RevCommit commit : origin._log().add(id).call()) {
			commits.add(commit);
		}
		return commits;
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderCpMvTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderCpMvTest.java
new file mode 100644
index 0000000000..fdb4e143c9
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderCpMvTest.java
@@ 0,0 1,509 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
import org.eclipse.jgit.niofs.fs.options.CherryPickCopyOption;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

public class JGitFileSystemImplProviderCpMvTest extends AbstractTestInfra {

	@Test
	public void testCopyBranches() throws IOException {
		final URI newRepo = URI.create("git://copybranchtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@copybranchtestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		{
			final Path path2 = provider
					.getPath(URI.create("git://user_branch@copybranchtestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@copybranchtestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		final Path source = provider.getPath(URI.create("git://user_branch@copybranchtestrepo"));
		final Path target = provider.getPath(URI.create("git://other_branch@copybranchtestrepo"));

		provider.copy(source, target);

		final DirectoryStream<Path> stream = provider
				.newDirectoryStream(provider.getPath(URI.create("git://other_branch@copybranchtestrepo/")), null);

		assertThat(stream).isNotNull().hasSize(2);

		assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(FileAlreadyExistsException.class);

		final Path notExists = provider.getPath(URI.create("git://xxx_user_branch@copybranchtestrepo"));
		final Path notExists2 = provider.getPath(URI.create("git://xxx_other_branch@copybranchtestrepo"));

		assertThatThrownBy(() > provider.copy(notExists, notExists2)).isInstanceOf(NoSuchFileException.class);
	}

	@Test
	public void testCopyFiles() throws IOException {
		final URI newRepo = URI.create("git://copyassettestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@copyassettestrepo/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		final Path path2 = provider.getPath(URI.create("git://user_branch@copyassettestrepo/other/path/myfile2.txt"));
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		final Path path3 = provider.getPath(URI.create("git://user_branch@copyassettestrepo/myfile3.txt"));
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		final Path target = provider.getPath(URI.create("git://user_branch@copyassettestrepo/myfile1.txt"));

		provider.copy(path, target);

		final DirectoryStream<Path> stream = provider
				.newDirectoryStream(provider.getPath(URI.create("git://user_branch@copyassettestrepo/")), null);

		for (Path path1 : stream) {
			System.out.println("content: "  path1.toUri());
		}

		assertThat(stream).isNotNull().hasSize(3);
	}

	@Test
	public void testCopyDir() throws IOException {
		final URI newRepo = URI.create("git://copydirtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@copydirtestrepo/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		final Path path2 = provider.getPath(URI.create("git://user_branch@copydirtestrepo/path/myfile2.txt"));
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		final Path path3 = provider.getPath(URI.create("git://user_branch@copydirtestrepo/path/myfile3.txt"));
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).hasSize(3);
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/some/place/here/"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).hasSize(2);
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/soXme/place/here"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).hasSize(2);
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/other_here/"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).hasSize(1);
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/not_exists"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/xxxxxxxxother_here/"));

			assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(NoSuchFileException.class);
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@copydirtestrepo/"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo/other_here/"));

			assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(FileAlreadyExistsException.class);
		}
	}

	@Test
	public void testCopyFilesAcrossRepositories() throws IOException {
		final URI newRepo1 = URI.create("git://copyassettestrepo1");
		provider.newFileSystem(newRepo1, EMPTY_ENV);

		final URI newRepo2 = URI.create("git://copyassettestrepo2");
		provider.newFileSystem(newRepo2, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@copyassettestrepo1/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		final Path target = provider.getPath(URI.create("git://master@copyassettestrepo2/myfile1.txt"));

		provider.copy(path, target);

		final DirectoryStream<Path> stream = provider
				.newDirectoryStream(provider.getPath(URI.create("git://master@copyassettestrepo2/")), null);

		for (Path path1 : stream) {
			System.out.println("content: "  path1.toUri());
		}

		assertThat(stream).isNotNull().hasSize(1);
	}

	@Test
	public void testCopyDirAcrossRepositories() throws IOException {
		final URI newRepo1 = URI.create("git://copydirtestrepo1");
		provider.newFileSystem(newRepo1, EMPTY_ENV);

		final URI newRepo2 = URI.create("git://copydirtestrepo2");
		provider.newFileSystem(newRepo2, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@copydirtestrepo1/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		final Path path2 = provider.getPath(URI.create("git://master@copydirtestrepo2/path/myfile2.txt"));
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		final Path path3 = provider.getPath(URI.create("git://master@copydirtestrepo2/path/myfile3.txt"));
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{
			final Path source = provider.getPath(URI.create("git://master@copydirtestrepo2/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo1/"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).isNotNull().hasSize(3);
		}

		{
			final Path source = provider.getPath(URI.create("git://master@copydirtestrepo2/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo1/some/place/here/"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).isNotNull().hasSize(2);
		}

		{
			final Path source = provider.getPath(URI.create("git://master@copydirtestrepo2/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo1/soXme/place/here"));

			provider.copy(source, target);

			final DirectoryStream<Path> stream = provider.newDirectoryStream(target, null);

			assertThat(stream).isNotNull().hasSize(2);
		}

		{
			final Path source = provider.getPath(URI.create("git://master@copydirtestrepo1/not_exists"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo2/xxxxxxxxother_here/"));

			assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(NoSuchFileException.class);
		}

		{
			final Path source = provider.getPath(URI.create("git://master@copydirtestrepo2/path"));
			final Path target = provider.getPath(URI.create("git://master@copydirtestrepo1/some/place/here/"));

			assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(FileAlreadyExistsException.class);
		}
	}

	@Test
	public void testMoveBranches() throws IOException {
		final URI newRepo = URI.create("git://movebranchtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@movebranchtestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		{
			final Path path2 = provider
					.getPath(URI.create("git://user_branch@movebranchtestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@movebranchtestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		final Path source = provider.getPath(URI.create("git://user_branch@movebranchtestrepo/"));
		final Path target = provider.getPath(URI.create("git://master@movebranchtestrepo/"));

		assertThatThrownBy(() > provider.move(source, target)).isInstanceOf(FileAlreadyExistsException.class);

		final Path source2 = provider.getPath(URI.create("git://user_branch@movebranchtestrepo/"));
		final Path target2 = provider.getPath(URI.create("git://xxxxddddkh@movebranchtestrepo/"));

		try {
			provider.move(source2, target2);
		} catch (final Exception e) {
			fail("should not throw");
		}
	}

	@Test
	public void testMoveFiles() throws IOException {
		final URI newRepo = URI.create("git://moveassettestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@moveassettestrepo/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		final Path path2 = provider.getPath(URI.create("git://user_branch@moveassettestrepo/other/path/myfile2.txt"));
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		final Path path3 = provider.getPath(URI.create("git://user_branch@moveassettestrepo/myfile3.txt"));
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		final Path target = provider.getPath(URI.create("git://user_branch@moveassettestrepo/myfile1.txt"));

		try {
			provider.move(path, target);
		} catch (final Exception e) {
			fail("should move file", e);
		}
	}

	@Test
	public void testMoveDir() throws IOException {
		final URI newRepo = URI.create("git://movedirtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@movedirtestrepo/myfile1.txt"));
		{
			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}
		final Path path2 = provider.getPath(URI.create("git://user_branch@movedirtestrepo/path/myfile2.txt"));
		{
			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		final Path path3 = provider.getPath(URI.create("git://user_branch@movedirtestrepo/path/myfile3.txt"));
		{
			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		{
			final Path source = provider.getPath(URI.create("git://user_branch@movedirtestrepo/path"));
			final Path target = provider.getPath(URI.create("git://master@movedirtestrepo/"));

			try {
				provider.move(source, target);
			} catch (IOException e) {
				assertThat(e).isInstanceOf(DirectoryNotEmptyException.class);
			}
		}
	}

	@Test
	public void testCherryPick() throws IOException {
		final URI newRepo = URI.create("git://cherrypicktestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@cherrypicktestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		{
			final Path path2 = provider
					.getPath(URI.create("git://user_branch@cherrypicktestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@cherrypicktestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		String commit2CherryPick;
		String cherryPickContent = "my 2nd cool content";
		{
			final Path path = provider.getPath(URI.create("git://master@cherrypicktestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(cherryPickContent.getBytes());
			outStream.close();

			final VersionAttributes versionAttributes = provider.readAttributes(path, VersionAttributes.class);

			assertThat(versionAttributes.history().records()).isNotNull().hasSize(2);
			commit2CherryPick = versionAttributes.history().records()
					.get(versionAttributes.history().records().size()  1).id();

			final OutputStream outStream2 = provider.newOutputStream(path);
			outStream2.write("my 3rd cool content".getBytes());
			outStream2.close();
		}

		final Path source = provider.getPath(URI.create("git://user_branch@cherrypicktestrepo"));
		final Path target = provider.getPath(URI.create("git://other_branch@cherrypicktestrepo"));

		provider.copy(source, target);

		String commit2CherryPick2;
		String cherryPickContent2 = "my 4tn cool content";
		{
			final Path path = provider.getPath(URI.create("git://master@cherrypicktestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(cherryPickContent2.getBytes());
			outStream.close();

			final VersionAttributes versionAttributes = provider.readAttributes(path, VersionAttributes.class);

			commit2CherryPick2 = versionAttributes.history().records()
					.get(versionAttributes.history().records().size()  1).id();
		}

		final Path target2 = provider.getPath(URI.create("git://other_branch2@cherrypicktestrepo"));
		provider.copy(source, target2);

		{
			provider.copy(source, target, new CherryPickCopyOption(commit2CherryPick));

			String result = convertStreamToString(provider.newInputStream(
					provider.getPath(URI.create("git://other_branch@cherrypicktestrepo/myfile1.txt"))));
			assertThat(result).isEqualTo(cherryPickContent);
		}

		{
			provider.copy(source, target2, new CherryPickCopyOption(commit2CherryPick, commit2CherryPick2));

			final String result = convertStreamToString(provider.newInputStream(
					provider.getPath(URI.create("git://other_branch2@cherrypicktestrepo/myfile1.txt"))));
			assertThat(result).isEqualTo(cherryPickContent2);
		}
	}

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderDiffTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderDiffTest.java
new file mode 100644
index 0000000000..8389798fd5
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderDiffTest.java
@@ 0,0 1,147 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.attribute.BranchDiff;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderDiffTest extends AbstractTestInfra {

	private Logger logger = LoggerFactory.getLogger(JGitFileSystemImplProviderDiffTest.class);

	@Test
	public void testDiffsBetweenBranches() throws IOException {

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "repo.git");
		final Git origin = new CreateRepository(gitSource).execute().get();
		final Repository gitRepo = origin.getRepository();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt",
								tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1\ntemp2\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\n"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop2", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop3", null, null, false,
				new HashMap<String, File>() {
					{
						put("file4.txt", tempFile("temp4"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop4", null, null, false,
				new HashMap<String, File>() {
					{
						put("file5.txt", tempFile("temp5"));
					}
				}).execute();

		final URI newRepo = URI.create("git://diffrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		provider.newFileSystem(newRepo, env);

		final Path path = provider.getPath(newRepo);
		final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path, "diff:master,develop").get("diff");

		branchDiff.diffs().forEach(elem > logger.info(elem.toString()));

		assertThat(branchDiff.diffs().size()).isEqualTo(5);
	}

	@Test
	public void testBranchesDoNotHaveDifferences() throws IOException {

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "repo.git");
		final Git origin = new CreateRepository(gitSource).execute().get();
		final Repository gitRepo = origin.getRepository();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt",
								tempFile("temp1\ntemp1\ntemp3\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\nother\n"));
					}
				}).execute();

		new Commit(origin, "master", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1\ntemp2\nmiddle\nmoremiddle\nmoremiddle\nmoremiddle\n"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		final URI newRepo = URI.create("git://diffrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		provider.newFileSystem(newRepo, env);

		final Path path = provider.getPath(newRepo);
		final BranchDiff branchDiff = (BranchDiff) provider.readAttributes(path, "diff:master,develop").get("diff");

		branchDiff.diffs().forEach(elem > logger.info(elem.toString()));

		assertThat(branchDiff.diffs().size()).isEqualTo(0);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderEncodingTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderEncodingTest.java
new file mode 100644
index 0000000000..c919a85c9d
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderEncodingTest.java
@@ 0,0 1,97 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;

public class JGitFileSystemImplProviderEncodingTest extends AbstractTestInfra {

	private int gitDaemonPort;

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();
		gitPrefs.put(GIT_DAEMON_ENABLED, "true");
		// use different port for every test > easy to run tests in parallel
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT, String.valueOf(gitDaemonPort));
		return gitPrefs;
	}

	@Test
	public void test() throws IOException {
		final URI originRepo = URI.create("git://encodingoriginname");

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo, Collections.emptyMap());

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("filename.txt", tempFile("temp1"));
					}
				}).execute();

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("filename.txt", tempFile("temp2"));
					}
				}).execute();

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file name.txt", tempFile("temp3"));
					}
				}).execute();

		final URI newRepo = URI.create("git://myencodingreponame");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						"git://localhost:"  gitDaemonPort  "/encodingoriginname");
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		fs.getPath("filename.txt").toUri();

		provider.getPath(fs.getPath("filename.txt").toUri());

		URI uri = fs.getPath("filename.txt").toUri();
		Path path = provider.getPath(uri);
		Path path1 = fs.getPath("filename.txt");
		assertThat(path).isEqualTo(path1);

		assertThat(provider.getPath(fs.getPath("file name.txt").toUri())).isEqualTo(fs.getPath("file name.txt"));

		assertThat(fs.getPath("file.txt").toUri());

		assertThat(provider.getPath(fs.getPath("file.txt").toUri())).isEqualTo(fs.getPath("file.txt"));
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderGCTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderGCTest.java
new file mode 100644
index 0000000000..66e88f7242
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderGCTest.java
@@ 0,0 1,68 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Path;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class JGitFileSystemImplProviderGCTest extends AbstractTestInfra {

	@Test
	public void testGC() throws IOException {
		final URI newRepo = URI.create("git://gcreponame");

		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo), null);
		assertThat(stream).isNotNull().hasSize(0);

		try {
			provider.newFileSystem(newRepo, EMPTY_ENV);
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (final Exception ex) {
		}

		for (int i = 0; i < 19; i) {
			assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(i);

			final Path path = provider.getPath(URI.create("git://gcreponame/path/to/myfile"  i  ".txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			assertThat(outStream).isNotNull();
			outStream.write(("my cool"  i  " content").getBytes());
			outStream.close();
		}

		final Path path = provider.getPath(URI.create("git://gcreponame/path/to/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();
		assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(0);

		final OutputStream outStream2 = provider.newOutputStream(path);
		assertThat(outStream2).isNotNull();
		outStream2.write("my co dwf sdf ol content".getBytes());
		outStream2.close();
		assertThat(fs.getNumberOfCommitsSinceLastGC()).isEqualTo(1);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHookTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHookTest.java
new file mode 100644
index 0000000000..086d8726de
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHookTest.java
@@ 0,0 1,79 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.Map;

import org.eclipse.jgit.hooks.PreCommitHook;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.HOOK_DIR;

public class JGitFileSystemImplProviderHookTest extends AbstractTestInfra {

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();
		try {
			final File hooksDir = createTempDirectory();
			gitPrefs.put(HOOK_DIR, hooksDir.getAbsolutePath());

			writeMockHook(hooksDir, "postcommit");
			writeMockHook(hooksDir, PreCommitHook.NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return gitPrefs;
	}

	@Test
	public void testInstalledHook() throws IOException {
		final URI newRepo = URI.create("git://hookreponame");

		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		if (fs instanceof JGitFileSystemImpl) {
			File[] hooks = new File(((JGitFileSystemImpl) fs).getGit().getRepository().getDirectory(), "hooks")
					.listFiles();
			assertThat(hooks).isNotEmpty().isNotNull();
			assertThat(hooks.length).isEqualTo(2);

			boolean foundPreCommitHook = false;
			boolean foundPostCommitHook = false;
			for (File hook : hooks) {
				if (hook.getName().equals("precommit")) {
					foundPreCommitHook = hook.canExecute();
				} else if (hook.getName().equals("postcommit")) {
					foundPostCommitHook = hook.canExecute();
				}
			}
			assertThat(foundPreCommitHook).isTrue();
			assertThat(foundPostCommitHook).isTrue();
		}
	}

	@Test
	public void testExecutedPostCommitHook() throws IOException {
		testHook("hookreponameexecuted", "postcommit", true);
	}

	@Test
	public void testNotSupportedPreCommitHook() throws IOException {
		testHook("hookreponameexecutedprecommit", "precommit", false);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHttpProxyTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHttpProxyTest.java
new file mode 100644
index 0000000000..10135e9c78
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderHttpProxyTest.java
@@ 0,0 1,102 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;

import org.junit.Test;

import static java.net.Authenticator.requestPasswordAuthentication;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JGitFileSystemImplProviderHttpProxyTest {

	@Test
	public void testHttpProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {
			{
				put("http.proxyUser", "user");
				put("http.proxyPassword", "passwd");
				put(GIT_DAEMON_ENABLED, "false");
				put(GIT_SSH_ENABLED, "false");
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost", InetAddress.getLocalHost(),
				8080, "http", "xxx", "http", new URL("http://localhost"), Authenticator.RequestorType.PROXY);

		assertEquals(userName, passwdAuth.getUserName());
		assertEquals(passw, new String(passwdAuth.getPassword()));

		provider.dispose();
	}

	@Test
	public void testHttpsProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {
			{
				put("https.proxyUser", "user");
				put("https.proxyPassword", "passwd");
				put(GIT_DAEMON_ENABLED, "false");
				put(GIT_SSH_ENABLED, "false");
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost", InetAddress.getLocalHost(),
				8080, "https", "xxx", "https", new URL("https://localhost"), Authenticator.RequestorType.PROXY);

		assertEquals(userName, passwdAuth.getUserName());
		assertEquals(passw, new String(passwdAuth.getPassword()));

		provider.dispose();
	}

	@Test
	public void testNoProxyInfo() throws IOException {
		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String, String>() {
			{
				put(GIT_DAEMON_ENABLED, "false");
				put(GIT_SSH_ENABLED, "false");
			}
		});

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
					InetAddress.getLocalHost(), 8080, "https", "xxx", "https", new URL("https://localhost"),
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost",
					InetAddress.getLocalHost(), 8080, "http", "xxx", "http", new URL("http://localhost"),
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		provider.dispose();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMergeTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMergeTest.java
new file mode 100644
index 0000000000..b76f50efb7
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMergeTest.java
@@ 0,0 1,253 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.jgit.niofs.fs.options.MergeCopyOption;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderMergeTest extends AbstractTestInfra {

	@Test
	public void testMergeSuccessful() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path userBranch = provider.getPath(URI.create("git://user_branch@mergetestrepo"));

		provider.copy(master, userBranch);

		{
			final Path path2 = provider.getPath(URI.create("git://user_branch@mergetestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}

		provider.copy(userBranch, master, new MergeCopyOption());

		final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();

		final List<DiffEntry> result = new ListDiffs(gitRepo, new GetTreeFromRef(gitRepo, "master").execute(),
				new GetTreeFromRef(gitRepo, "user_branch").execute()).execute();

		assertThat(result.size()).isEqualTo(0);
	}

	@Test(expected = GitException.class)
	public void testMergeConflicts() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path userBranch = provider.getPath(URI.create("git://user_branch@mergetestrepo"));

		provider.copy(master, userBranch);

		{
			final Path path2 = provider.getPath(URI.create("git://user_branch@mergetestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write("my cool content".getBytes());
			outStream2.close();
		}
		{
			final Path path = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content changed".getBytes());
			outStream.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write("my cool content".getBytes());
			outStream3.close();
		}
		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my very cool content".getBytes());
			outStream.close();
		}

		provider.copy(userBranch, master, new MergeCopyOption());
	}

	@Test
	public void testMergeBinarySuccessful() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/drools.png"));
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path userBranch = provider.getPath(URI.create("git://user_branch@mergetestrepo"));

		provider.copy(master, userBranch);

		{
			final Path path2 = provider.getPath(URI.create("git://user_branch@mergetestrepo/other/path/myfile2.txt"));

			final OutputStream outStream2 = provider.newOutputStream(path2);
			outStream2.write(this.loadImage("images/jbpm.png"));
			outStream2.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile3.txt"));

			final OutputStream outStream3 = provider.newOutputStream(path3);
			outStream3.write(this.loadImage("images/opta.png"));
			outStream3.close();
		}

		provider.copy(userBranch, master, new MergeCopyOption());

		final Git gitRepo = ((JGitFileSystem) master.getFileSystem()).getGit();
		final List<DiffEntry> result = new ListDiffs(gitRepo, new GetTreeFromRef(gitRepo, "master").execute(),
				new GetTreeFromRef(gitRepo, "user_branch").execute()).execute();

		assertThat(result.size()).isEqualTo(0);
	}

	@Test(expected = GitException.class)
	public void testBinaryMergeConflicts() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/drools.png"));
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path userBranch = provider.getPath(URI.create("git://user_branch@mergetestrepo"));

		provider.copy(master, userBranch);

		{
			final Path path2 = provider.getPath(URI.create("git://user_branch@mergetestrepo/other/path/myfile2.txt"));

			final OutputStream outStream = provider.newOutputStream(path2);
			outStream.write(this.loadImage("images/jbpm.png"));
			outStream.close();
		}
		{
			final Path path = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage("images/jbpm.png"));
			outStream.close();
		}
		{
			final Path path3 = provider.getPath(URI.create("git://user_branch@mergetestrepo/myfile3.txt"));

			final OutputStream outStream = provider.newOutputStream(path3);
			outStream.write(this.loadImage("images/opta.png"));
			outStream.close();
		}
		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write(this.loadImage(""));
			outStream.close();
		}

		provider.copy(userBranch, master, new MergeCopyOption());
	}

	@Test(expected = GitException.class)
	public void testTryToMergeNonexistentBranch() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path develop = provider.getPath(URI.create("git://develop@mergetestrepo"));

		provider.copy(develop, master, new MergeCopyOption());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMissingParemeter() throws IOException {
		final URI newRepo = URI.create("git://mergetestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://master@mergetestrepo/myfile1.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("my cool content".getBytes());
			outStream.close();
		}

		final Path master = provider.getPath(URI.create("git://master@mergetestrepo"));
		final Path develop = provider.getPath(URI.create("git://develop@mergetestrepo"));

		provider.copy(develop, null, new MergeCopyOption());
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMigrationTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMigrationTest.java
new file mode 100644
index 0000000000..e651fc5016
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderMigrationTest.java
@@ 0,0 1,42 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderMigrationTest extends AbstractTestInfra {

	@Test
	public void testCreateANewDirectoryWithMigrationEnv() throws IOException {

		final Map<String, ?> envMigrate = new HashMap<String, Object>() {
			{
				put("init", Boolean.TRUE);
				put("migratefrom", URI.create("git://old"));
			}
		};

		String newPath = "git://test/old";
		final URI newUri = URI.create(newPath);
		provider.newFileSystem(newUri, envMigrate);

		provider.getFileSystem(newUri);
		assertThat(new File(provider.getGitRepoContainerDir(), "test/old"  ".git")).exists();
		assertThat(provider.getFileSystem(newUri)).isNotNull();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderPostCommitHookTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderPostCommitHookTest.java
new file mode 100644
index 0000000000..63b9143d17
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderPostCommitHookTest.java
@@ 0,0 1,152 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooksConstants;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JGitFileSystemImplProviderPostCommitHookTest extends AbstractTestInfra {

	private static final Integer SUCCESS = 0;
	private static final Integer WARNING = 10;
	private static final Integer ERROR = 50;

	private static final String SCRIPT = "exit ";

	private static final String HOOKS_FOLDER = "hooks";

	private static final String GIT = "git://";

	private static final String REPO_NAME = "repo";

	private static final String NEW_FILE_PATH = "/folder/file.txt";

	@Mock
	private FileSystemHooks.FileSystemHook postCommitHook;

	@Captor
	private ArgumentCaptor<FileSystemHookExecutionContext> contextCaptor;

	private JGitFileSystem fs;

	@Before
	public void init() throws IOException {
		final URI newRepo = URI.create(GIT  REPO_NAME);

		final Map<String, Object> env = new HashMap<>();
		env.put(FileSystemHooks.PostCommit.name(), postCommitHook);

		fs = (JGitFileSystem) provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();
	}

	@Test
	public void testPostCommitWithoutHook() throws IOException {
		commitFile();

		verify(postCommitHook, never()).execute(any());
	}

	@Test
	public void testPostCommitHookSuccess() throws IOException {

		testPostCommit(SUCCESS);
	}

	@Test
	public void testPostCommitHookWarning() throws IOException {

		testPostCommit(WARNING);
	}

	@Test
	public void testPostCommitHookError() throws IOException {

		testPostCommit(ERROR);
	}

	private void testPostCommit(final Integer exitCode) throws IOException {
		prepareHook(exitCode);

		commitFile();

		verify(postCommitHook).execute(contextCaptor.capture());

		FileSystemHookExecutionContext context = contextCaptor.getValue();

		Assertions.assertThat(context).isNotNull().hasFieldOrPropertyWithValue("fsName", REPO_NAME);

		Assertions.assertThat(context.getParamValue(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE)).isNotNull()
				.isEqualTo(exitCode);
	}

	private void prepareHook(final Integer code) throws IOException {

		File destHookFile = fs.getGit().getRepository().getDirectory().toPath().resolve(HOOKS_FOLDER)
				.resolve("postcommit").toFile();

		FileUtils.write(destHookFile, SCRIPT  code, Charset.defaultCharset());

		if (SystemReader.getInstance().isWindows()) {
			destHookFile.setReadable(true);
			destHookFile.setWritable(true);
			destHookFile.setExecutable(true);
		} else {
			Set<PosixFilePermission> perms = new HashSet<>();
			perms.add(PosixFilePermission.OWNER_READ);
			perms.add(PosixFilePermission.OWNER_WRITE);
			perms.add(PosixFilePermission.GROUP_EXECUTE);
			perms.add(PosixFilePermission.OTHERS_EXECUTE);
			perms.add(PosixFilePermission.OWNER_EXECUTE);

			Files.setPosixFilePermissions(destHookFile.toPath(), perms);
		}
	}

	private void commitFile() throws IOException {
		final Path path = provider.getPath(URI.create(GIT  REPO_NAME  NEW_FILE_PATH));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my content").getBytes());
		outStream.close();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHBadConfigTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHBadConfigTest.java
new file mode 100644
index 0000000000..bfd0602c9b
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHBadConfigTest.java
@@ 0,0 1,39 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.util.Map;

import org.apache.sshd.server.SshServer;
import org.junit.Test;

import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_IDLE_TIMEOUT;
import static org.junit.Assert.assertEquals;

public class JGitFileSystemImplProviderSSHBadConfigTest extends AbstractTestInfra {

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();

		gitPrefs.put(GIT_SSH_ENABLED, "true");
		gitPrefs.put(GIT_SSH_IDLE_TIMEOUT, "bz");

		return gitPrefs;
	}

	@Test
	public void testCheckDefaultSSHIdleWithInvalidArg() throws IOException {
		assertEquals(JGitFileSystemProviderConfiguration.DEFAULT_SSH_IDLE_TIMEOUT,
				provider.getGitSSHService().getProperties().get(SshServer.IDLE_TIMEOUT));
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHTest.java
new file mode 100644
index 0000000000..eba9ddb4ec
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderSSHTest.java
@@ 0,0 1,137 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
import org.eclipse.jgit.niofs.internal.security.User;
import org.apache.sshd.server.SshServer;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Assume;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_IDLE_TIMEOUT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_PORT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class JGitFileSystemImplProviderSSHTest extends AbstractTestInfra {

	private int gitSSHPort;

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();

		gitPrefs.put(GIT_SSH_ENABLED, "true");
		gitSSHPort = findFreePort();
		gitPrefs.put(GIT_SSH_PORT, String.valueOf(gitSSHPort));
		gitPrefs.put(GIT_SSH_IDLE_TIMEOUT, "10001");

		return gitPrefs;
	}

	@Test
	public void testSSHPostReceiveHook() throws IOException {
		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
			@Override
			public void execute(FileSystemHookExecutionContext context) {
				assertEquals("repo", context.getFsName());
			}
		});

		Assume.assumeFalse("UF511", System.getProperty("java.vendor").equals("IBM Corporation"));
		// Setup Authorization/Authentication
		provider.setJAASAuthenticator(new AuthenticationService() {
			private User user;

			@Override
			public User login(String s, String s1) {
				user = new User() {
					@Override
					public String getIdentifier() {
						return s;
					}
				};
				return user;
			}

			@Override
			public boolean isLoggedIn() {
				return user != null;
			}

			@Override
			public void logout() {
				user = null;
			}

			@Override
			public User getUser() {
				return user;
			}
		});
		provider.setAuthorizer((fs, fileSystemUser) > true);

		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("admin", ""));
		assertEquals("10001", provider.getGitSSHService().getProperties().get(SshServer.IDLE_TIMEOUT));

		// Setup origin
		final URI originRepo = URI.create("git://repo");
		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo,
				new HashMap<String, Object>() {
					{
						put(FileSystemHooks.ExternalUpdate.name(), hook);
					}
				});

		// Write a file to origin that we won't amend in the clone
		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("filename.txt", tempFile("temp1"));
					}
				}).execute();

		// Setup clone
		JGitFileSystem clone;
		clone = (JGitFileSystem) provider.newFileSystem(URI.create("git://repoclone"), new HashMap<String, Object>() {
			{
				put("init", "true");
				put("origin", "ssh://admin@localhost:"  gitSSHPort  "/repo");
			}
		});

		assertNotNull(clone);

		// Push clone back to origin
		provider.getFileSystem(URI.create("git://repoclone?push=ssh://admin@localhost:"  gitSSHPort  "/repo"));

		ArgumentCaptor<FileSystemHookExecutionContext> captor = ArgumentCaptor
				.forClass(FileSystemHookExecutionContext.class);

		verify(hook).execute(captor.capture());

		Assertions.assertThat(captor.getValue()).isNotNull().hasFieldOrPropertyWithValue("fsName", "repo");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderTest.java
new file mode 100644
index 0000000000..4006e4d425
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderTest.java
@@ 0,0 1,2014 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.eclipse.jgit.niofs.fs.AmbiguousFileSystemNameException;
import org.eclipse.jgit.niofs.fs.FileSystemState;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
import org.eclipse.jgit.niofs.fs.options.CommentedOption;
import org.eclipse.jgit.niofs.fs.options.SquashOption;

import org.assertj.core.api.AssertionsForClassTypes;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
import org.eclipse.jgit.niofs.internal.op.model.PathType;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JGitFileSystemImplProviderTest extends AbstractTestInfra {

	private int gitDaemonPort;

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();
		gitPrefs.put(GIT_DAEMON_ENABLED, "true");
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT, String.valueOf(gitDaemonPort));
		System.out.println(gitDaemonPort);
		return gitPrefs;
	}

	@Test
	public void testNewFileSystem() throws IOException {
		final URI newRepo = URI.create("git://reponame");

		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo), null);
		assertThat(stream).isNotNull().hasSize(0);

		try {
			provider.newFileSystem(newRepo, EMPTY_ENV);
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (final Exception ignored) {
		}

		provider.newFileSystem(URI.create("git://reponame2"), EMPTY_ENV);
	}

	@Test
	public void testNewFileSystemInited() throws IOException {
		final URI newRepo = URI.create("git://initreponame");

		final Map<String, ?> env = new HashMap<String, Object>() {
			{
				put("init", Boolean.TRUE);
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo), null);
		assertThat(stream).isNotNull().hasSize(1);
	}

	@Test
	public void testInvalidURINewFileSystem() throws IOException {
		final URI newRepo = URI.create("git:///reponame");

		try {
			provider.newFileSystem(newRepo, EMPTY_ENV);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid, missing host repository!");
		}
	}

	@Test
	public void testNewFileSystemClone() throws IOException {

		final URI originRepo = URI.create("git://mysimpletestoriginname");

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo, Collections.emptyMap());

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();

		final URI newRepo = URI.create("git://myreponame");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						"git://localhost:"  gitDaemonPort  "/mysimpletestoriginname");
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("fileXXXXX.txt", tempFile("temp"));
					}
				}).execute();

		provider.getFileSystem(URI.create(
				"git://myreponame?sync=git://localhost:"  gitDaemonPort  "/mysimpletestoriginname&force"));

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		for (final Path root : fs.getRootDirectories()) {
			if (root.toAbsolutePath().toUri().toString().contains("upstream")) {
				assertThat(provider.newDirectoryStream(root, null)).hasSize(2);
			} else if (root.toAbsolutePath().toUri().toString().contains("origin")) {
				assertThat(provider.newDirectoryStream(root, null)).hasSize(1);
			} else {
				assertThat(provider.newDirectoryStream(root, null)).hasSize(2);
			}
		}

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("fileYYYY.txt", tempFile("tempYYYY"));
					}
				}).execute();

		provider.getFileSystem(URI.create(
				"git://myreponame?sync=git://localhost:"  gitDaemonPort  "/mysimpletestoriginname&force"));

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(provider.newDirectoryStream(fs.getRootDirectories().iterator().next(), null)).isNotEmpty()
				.hasSize(3);
	}

	@Test
	public void testNewFileSystemCloneAndPush() throws IOException {

		final URI originRepo = URI.create("git://mysimpletestoriginrepo");

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo, Collections.emptyMap());

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();

		final URI newRepo = URI.create("git://myrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						"git://localhost:"  gitDaemonPort  "/mysimpletestoriginrepo");
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		assertThat(fs.getPath("file.txt").toFile()).isNotNull().exists();

		new Commit(((JGitFileSystem) fs).getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("fileXXXXX.txt", tempFile("temp"));
					}
				}).execute();

		final URI newRepo2 = URI.create("git://myrepo2");

		final Map<String, Object> env2 = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						"git://localhost:"  gitDaemonPort  "/mysimpletestoriginrepo");
			}
		};

		final FileSystem fs2 = provider.newFileSystem(newRepo2, env2);

		new Commit(origin.getGit(), "userbranch", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1UserBranch.txt", tempFile("tempX"));
					}
				}).execute();

		provider.getFileSystem(URI
				.create("git://myrepo2?sync=git://localhost:"  gitDaemonPort  "/mysimpletestoriginrepo&force"));

		assertThat(fs2.getRootDirectories()).hasSize(2);

		final List<String> rootURIs1 = new ArrayList<String>() {
			{
				add("git://master@myrepo2/");
				add("git://userbranch@myrepo2/");
			}
		};

		final List<String> rootURIs2 = new ArrayList<String>() {
			{
				add("git://master@myrepo2/");
				add("git://userbranch@myrepo2/");
				add("git://userbranch2@myrepo2/");
			}
		};

		final Set<String> rootURIs = new HashSet<String>();
		for (final Path root : fs2.getRootDirectories()) {
			rootURIs.add(root.toUri().toString());
		}

		rootURIs.removeAll(rootURIs1);

		assertThat(rootURIs).isEmpty();

		new Commit(origin.getGit(), "userbranch2", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2UserBranch.txt", tempFile("tempX"));
					}
				}).execute();

		provider.getFileSystem(URI
				.create("git://myrepo2?sync=git://localhost:"  gitDaemonPort  "/mysimpletestoriginrepo&force"));

		assertThat(fs2.getRootDirectories()).hasSize(3);

		for (final Path root : fs2.getRootDirectories()) {
			rootURIs.add(root.toUri().toString());
		}

		rootURIs.removeAll(rootURIs2);

		assertThat(rootURIs).isEmpty();
	}

	@Test
	public void testNewFileSystemCloneAndRescan() throws IOException {

		final URI originRepo = URI.create("git://mysimpletestoriginname");

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo, Collections.emptyMap());

		new Commit(origin.getGit(), "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();

		final URI newRepo = URI.create("git://myreponame");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						"git://localhost:"  gitDaemonPort  "/mysimpletestoriginname");
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		assertThat(fs.getRootDirectories()).hasSize(1);

		final FileSystem fs2 = provider.getFileSystem(newRepo);

		assertThat(fs2).isNotNull();

		assertThat(fs2.getRootDirectories()).hasSize(1);
	}

	@Test
	public void testGetFileSystem() throws IOException {
		final URI newRepo = URI.create("git://newreponame");

		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		assertThat(provider.getFileSystem(newRepo)).isEqualTo(fs);
		assertThat(provider.getFileSystem(URI.create("git://master@newreponame"))).isEqualTo(fs);
		assertThat(provider.getFileSystem(URI.create("git://branch@newreponame"))).isEqualTo(fs);

		assertThat(provider.getFileSystem(URI.create("git://branch@newreponame?_fetch"))).isEqualTo(fs);
	}

	@Test
	public void testInvalidURIGetFileSystem() {
		final URI newRepo = URI.create("git:///newreponame");

		try {
			provider.getFileSystem(newRepo);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid, missing host repository!");
		}
	}

	@Test
	public void testGetPath() throws IOException {
		final URI newRepo = URI.create("git://newgetreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@newgetreponame/home"));

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		Path root = path.getRoot();
		Path path1 = root.toRealPath();
		assertThat(root.toRealPath().toUri().toString()).isEqualTo("git://master@newgetreponame/");
		assertThat(path.toString()).isEqualTo("/home");

		final Path pathRelative = provider.getPath(URI.create("git://master@newgetreponame/:home"));
		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.toRealPath().toUri().toString()).isEqualTo("git://master@newgetreponame/:home");
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testInvalidURIGetPath() {
		final URI uri = URI.create("git:///master@newgetreponame/home");

		try {
			provider.getPath(uri);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (final IllegalArgumentException ex) {
			assertThat(ex.getMessage()).isEqualTo("Parameter named 'uri' is invalid, missing host repository!");
		}
	}

	@Test
	public void testGetComplexPath() throws IOException {
		final URI newRepo = URI.create("git://newcomplexgetreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://origin/master@newcomplexgetreponame/home"));

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThat(path.toString()).isEqualTo("/home");

		final Path pathRelative = provider.getPath(URI.create("git://origin/master@newcomplexgetreponame/:home"));
		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testGetComplexPathComposed() throws IOException {
		final URI newRepo = URI.create("git://newcomplexgetreponame/composed");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path1 = provider.getPath(URI.create("git://newcomplexgetreponame/composed/home"));

		AssertionsForClassTypes.assertThat(path1).isNotNull();
		assertThat(path1.getRoot().toString()).isEqualTo("/");
		assertThat(path1.toString()).isEqualTo("/home");

		final Path path = provider.getPath(URI.create("git://origin/master@newcomplexgetreponame/composed/home"));

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThat(path.toString()).isEqualTo("/home");

		final Path pathRelative = provider
				.getPath(URI.create("git://origin/master@newcomplexgetreponame/composed/:home"));
		AssertionsForClassTypes.assertThat(pathRelative).isNotNull();
		assertThat(pathRelative.getRoot().toString()).isEqualTo("");
		assertThat(pathRelative.toString()).isEqualTo("home");
	}

	@Test
	public void testInputStream() throws IOException {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("myfile.txt", tempFile("temp\n.origin\n.content"));
					}
				}).execute();

		final URI newRepo = URI.create("git://inputstreamtestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://master@inputstreamtestrepo/myfile.txt"));

		final String content = extractContent(path);

		assertThat(content).isNotNull().isEqualTo("temp\n.origin\n.content");
	}

	@Test
	public void testInputStream2() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file/myfile.txt", tempFile("temp\n.origin\n.content"));
					}
				}).execute();

		final URI newRepo = URI.create("git://xinputstreamtestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://master@xinputstreamtestrepo/path/to/file/myfile.txt"));

		final String content = extractContent(path);

		assertThat(content).isNotNull().isEqualTo("temp\n.origin\n.content");
	}

	@Test(expected = NoSuchFileException.class)
	public void testInputStream3() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file/myfile.txt", tempFile("temp\n.origin\n.content"));
					}
				}).execute();

		final URI newRepo = URI.create("git://xxinputstreamtestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://origin/master@xxinputstreamtestrepo/path/to"));

		provider.newInputStream(path);
	}

	@Test(expected = NoSuchFileException.class)
	public void testInputStreamNoSuchFile() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user1", "user1@example.com", "commitx", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp.origin.content.2"));
					}
				}).execute();

		final URI newRepo = URI.create("git://inputstreamnotexiststestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://origin/master@inputstreamnotexiststestrepo/temp.txt"));

		provider.newInputStream(path);
	}

	@Test
	public void testNewOutputStream() throws Exception {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("myfile.txt", tempFile("temp\n.origin\n.content"));
					}
				}).execute();
		new Commit(origin, "user_branch", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/some/file/myfile.txt", tempFile("some\n.content\nhere"));
					}
				}).execute();

		final URI newRepo = URI.create("git://outstreamtestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://user_branch@outstreamtestrepo/some/path/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");

		try {
			provider.newOutputStream(provider.getPath(URI.create("git://user_branch@outstreamtestrepo/some/path/")));
			failBecauseExceptionWasNotThrown(IOException.class);
		} catch (Exception ignored) {
		}
	}

	@Test
	public void testNewOutputStreamWithJGitOp() throws Exception {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("myfile.txt", tempFile("temp\n.origin\n.content"));
					}
				}).execute();
		new Commit(origin, "user_branch", "user", "user@example.com", "commit message", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/some/file/myfile.txt", tempFile("some\n.content\nhere"));
					}
				}).execute();

		final URI newRepo = URI.create("git://outstreamwithoptestrepo");

		final Map<String, Object> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME,
						origin.getRepository().getDirectory().toString());
			}
		};

		final FileSystem fs = provider.newFileSystem(newRepo, env);

		assertThat(fs).isNotNull();

		final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		final CommentedOption op = new CommentedOption("User Tester", "user.tester@example.com", "omg, is it the end?",
				formatter.parse("31/12/2012"));

		final Path path = provider
				.getPath(URI.create("git://user_branch@outstreamwithoptestrepo/some/path/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path, op);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final InputStream inStream = provider.newInputStream(path);

		final String content = new Scanner(inStream).useDelimiter("\\A").next();

		inStream.close();

		assertThat(content).isNotNull().isEqualTo("my cool content");
	}

	@Test(expected = FileSystemNotFoundException.class)
	public void testGetPathFileSystemNotExisting() {
		provider.getPath(URI.create("git://master@notexistsgetreponame/home"));
	}

	@Test(expected = FileSystemNotFoundException.class)
	public void testGetFileSystemNotExisting() {
		final URI newRepo = URI.create("git://notnewreponame");

		provider.getFileSystem(newRepo);
	}

	@Test
	public void testDeleteShouldRemoveEmptyParentDir() throws IOException {

		final URI doraRepo = URI.create("git://parentDir/dorarepo");
		FileSystem doraFS = provider.newFileSystem(doraRepo, EMPTY_ENV);

		final File doraRepoDir = ((JGitFileSystemProxy) doraFS).getGit().getRepository().getDirectory();

		final File parentDir = doraRepoDir.getParentFile();
		final File gitProviderDir = provider.getGitRepoContainerDir();

		final URI doraRepo1 = URI.create("git://parentDir/dorarepo1");
		FileSystem doraFS1 = provider.newFileSystem(doraRepo1, EMPTY_ENV);
		final File dora1RepoDir = ((JGitFileSystemProxy) doraFS1).getGit().getRepository().getDirectory();

		final File parentDir1 = doraRepoDir.getParentFile();

		assertEquals(parentDir, parentDir1);

		provider.delete(doraFS.getPath(null));
		assertFalse(doraRepoDir.exists());
		assertTrue(parentDir.exists());
		assertTrue(gitProviderDir.exists());

		provider.delete(doraFS1.getPath(null));
		assertFalse(dora1RepoDir.exists());
		assertTrue(parentDir1.exists());
		assertTrue(gitProviderDir.exists());
	}

	@Test
	public void testDelete() throws IOException {
		final URI newRepo = URI.create("git://delete1testrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://user_branch@delete1testrepo/path/to/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		try {
			provider.delete(provider.getPath(URI.create("git://user_branch@delete1testrepo/non_existent_path")));
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		try {
			provider.delete(provider.getPath(URI.create("git://user_branch@delete1testrepo/path/to/")));
			failBecauseExceptionWasNotThrown(DirectoryNotEmptyException.class);
		} catch (DirectoryNotEmptyException ignored) {
		}

		provider.delete(path);

		try {
			provider.newFileSystem(newRepo, EMPTY_ENV);
			failBecauseExceptionWasNotThrown(FileSystemAlreadyExistsException.class);
		} catch (FileSystemAlreadyExistsException ignored) {
		}

		final Path fsPath = path.getFileSystem().getPath(null);
		provider.delete(fsPath);
		assertThat(fsPath.getFileSystem().isOpen()).isEqualTo(false);

		final URI newRepo2 = URI.create("git://delete1testrepo");
		provider.newFileSystem(newRepo2, EMPTY_ENV);
	}

	@Test
	public void testDeleteBranch() throws IOException {
		final URI newRepo = URI.create("git://deletebranchtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://user_branch@deletebranchtestrepo/path/to/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		provider.delete(provider.getPath(URI.create("git://user_branch@deletebranchtestrepo")));

		try {
			provider.delete(provider.getPath(URI.create("git://user_branch@deletebranchtestrepo")));
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		try {
			provider.delete(provider.getPath(URI.create("git://some_user_branch@deletebranchtestrepo")));
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testDeleteIfExists() throws IOException {
		final URI newRepo = URI.create("git://deleteifexists1testrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider
				.getPath(URI.create("git://user_branch@deleteifexists1testrepo/path/to/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		assertThat(provider.deleteIfExists(
				provider.getPath(URI.create("git://user_branch@deleteifexists1testrepo/non_existent_path"))))
						.isFalse();

		try {
			provider.deleteIfExists(
					provider.getPath(URI.create("git://user_branch@deleteifexists1testrepo/path/to/")));
			failBecauseExceptionWasNotThrown(DirectoryNotEmptyException.class);
		} catch (DirectoryNotEmptyException ignored) {
		}

		assertThat(provider.deleteIfExists(path)).isTrue();
	}

	@Test
	public void testDeleteBranchIfExists() throws IOException {
		final URI newRepo = URI.create("git://deletebranchifexists1testrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider
				.getPath(URI.create("git://user_branch@deletebranchifexists1testrepo/path/to/myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.newInputStream(path).close();

		assertThat(provider
				.deleteIfExists(provider.getPath(URI.create("git://user_branch@deletebranchifexists1testrepo"))))
						.isTrue();

		assertThat(provider
				.deleteIfExists(provider.getPath(URI.create("git://not_user_branch@deletebranchifexists1testrepo"))))
						.isFalse();

		assertThat(provider
				.deleteIfExists(provider.getPath(URI.create("git://user_branch@deletebranchifexists1testrepo"))))
						.isFalse();
	}

	@Test
	public void testIsHidden() throws IOException {
		final URI newRepo = URI.create("git://ishiddentestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/.myfile.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/myfile.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		assertThat(outStream2).isNotNull();
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		assertThat(provider
				.isHidden(provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/.myfile.txt"))))
						.isTrue();

		assertThat(provider
				.isHidden(provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/myfile.txt"))))
						.isFalse();

		assertThat(provider.isHidden(
				provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/non_existent/.myfile.txt"))))
						.isTrue();

		assertThat(provider.isHidden(
				provider.getPath(URI.create("git://user_branch@ishiddentestrepo/path/to/non_existent/myfile.txt"))))
						.isFalse();

		assertThat(provider.isHidden(provider.getPath(URI.create("git://user_branch@ishiddentestrepo/")))).isFalse();

		assertThat(provider.isHidden(provider.getPath(URI.create("git://user_branch@ishiddentestrepo/some"))))
				.isFalse();
	}

	@Test
	public void testIsSameFile() throws IOException {
		final URI newRepo = URI.create("git://issamefiletestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@issamefiletestrepo/path/to/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider.getPath(URI.create("git://user_branch@issamefiletestrepo/path/to/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@issamefiletestrepo/path/to/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		assertThat(provider.isSameFile(path, path2)).isTrue();

		assertThat(provider.isSameFile(path, path3)).isTrue();
	}

	@Test
	public void testCreateDirectory() throws IOException {
		final URI newRepo = URI.create("git://xcreatedirtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final JGitPathImpl path = (JGitPathImpl) provider
				.getPath(URI.create("git://master@xcreatedirtestrepo/some/path/to/"));

		final PathInfo result = path.getFileSystem().getGit().getPathInfo(path.getRefTree(), path.getPath());
		assertThat(result.getPathType()).isEqualTo(PathType.NOT_FOUND);

		provider.createDirectory(path);

		final PathInfo resultAfter = path.getFileSystem().getGit().getPathInfo(path.getRefTree(), path.getPath());
		assertThat(resultAfter.getPathType()).isEqualTo(PathType.DIRECTORY);

		final Path gitkeepPath = path.resolve(".gitkeep");
		assertThat(provider.exists(gitkeepPath)).isEqualTo(true);

		try {
			provider.createDirectory(path);
			failBecauseExceptionWasNotThrown(FileAlreadyExistsException.class);
		} catch (FileAlreadyExistsException ignored) {
		}
	}

	@Test
	public void testCheckAccess() throws Exception {
		final URI newRepo = URI.create("git://checkaccesstestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@checkaccesstestrepo/path/to/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		provider.checkAccess(path);

		final Path path_to_dir = provider.getPath(URI.create("git://master@checkaccesstestrepo/path/to"));

		provider.checkAccess(path_to_dir);

		final Path path_not_exists = provider
				.getPath(URI.create("git://master@checkaccesstestrepo/path/to/some.txt"));

		try {
			provider.checkAccess(path_not_exists);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testGetFileStore() throws Exception {
		final URI newRepo = URI.create("git://filestoretestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@filestoretestrepo/path/to/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final FileStore fileStore = provider.getFileStore(path);

		assertThat(fileStore).isNotNull();

		assertThat(fileStore.getAttribute("readOnly")).isEqualTo(Boolean.FALSE);
	}

	@Test
	public void testNewDirectoryStream() throws IOException {
		final URI newRepo = URI.create("git://dirstreamtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@dirstreamtestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final DirectoryStream<Path> stream1 = provider
				.newDirectoryStream(provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/")), null);

		assertThat(stream1).isNotNull().hasSize(2).contains(path3,
				provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/other")));

		final DirectoryStream<Path> stream2 = provider
				.newDirectoryStream(provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/other")), null);

		assertThat(stream2).isNotNull().hasSize(1)
				.contains(provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/other/path")));

		final DirectoryStream<Path> stream3 = provider.newDirectoryStream(
				provider.getPath(URI.create("git://user_branch@dirstreamtestrepo/other/path")), null);

		assertThat(stream3).isNotNull().hasSize(1).contains(path2);

		final DirectoryStream<Path> stream4 = provider
				.newDirectoryStream(provider.getPath(URI.create("git://master@dirstreamtestrepo/")), null);

		assertThat(stream4).isNotNull().hasSize(1).contains(path);

		try {
			provider.newDirectoryStream(path, null);
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}
		final Path crazyPath = provider.getPath(URI.create("git://master@dirstreamtestrepo/crazy/path/here"));
		try {
			provider.newDirectoryStream(crazyPath, null);
			failBecauseExceptionWasNotThrown(NotDirectoryException.class);
		} catch (NotDirectoryException ignored) {
		}

		provider.createDirectory(crazyPath);

		assertThat(provider.newDirectoryStream(crazyPath, null)).isNotNull().hasSize(1);
	}

	@Test
	public void testFilteredNewDirectoryStream() throws IOException {
		final URI newRepo = URI.create("git://filterdirstreamtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@filterdirstreamtestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider
				.getPath(URI.create("git://user_branch@filterdirstreamtestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@filterdirstreamtestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final Path path4 = provider.getPath(URI.create("git://user_branch@filterdirstreamtestrepo/myfile4.xxx"));

		final OutputStream outStream4 = provider.newOutputStream(path4);
		outStream4.write("my cool content".getBytes());
		outStream4.close();

		final DirectoryStream<Path> stream1 = provider.newDirectoryStream(
				provider.getPath(URI.create("git://user_branch@filterdirstreamtestrepo/")),
				entry > entry.toString().endsWith(".xxx"));

		assertThat(stream1).isNotNull().hasSize(1).contains(path4);

		final DirectoryStream<Path> stream2 = provider.newDirectoryStream(
				provider.getPath(URI.create("git://master@filterdirstreamtestrepo/")), entry > false);

		assertThat(stream2).isNotNull().hasSize(0);
	}

	@Test
	public void testGetFileAttributeView() throws IOException {
		final URI newRepo = URI.create("git://getfileattriviewtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@getfileattriviewtestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider
				.getPath(URI.create("git://user_branch@getfileattriviewtestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@getfileattriviewtestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path3,
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(1);
		assertThat(attrs.readAttributes().history().records().get(0).uri()).isNotNull();

		assertThat(attrs.readAttributes().isDirectory()).isFalse();
		assertThat(attrs.readAttributes().isRegularFile()).isTrue();
		assertThat(attrs.readAttributes().creationTime()).isNotNull();
		assertThat(attrs.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrs.readAttributes().size()).isEqualTo(15L);

		try {
			provider.getFileAttributeView(
					provider.getPath(URI.create("git://user_branch@getfileattriviewtestrepo/not_exists.txt")),
					BasicFileAttributeView.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (Exception ignored) {
		}

		assertThat(provider.getFileAttributeView(path3, MyInvalidFileAttributeView.class)).isNull();

		final Path rootPath = provider.getPath(URI.create("git://user_branch@getfileattriviewtestrepo/"));

		final BasicFileAttributeView attrsRoot = provider.getFileAttributeView(rootPath, BasicFileAttributeView.class);

		assertThat(attrsRoot.readAttributes().isDirectory()).isTrue();
		assertThat(attrsRoot.readAttributes().isRegularFile()).isFalse();
		assertThat(attrsRoot.readAttributes().creationTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.readAttributes().size()).isEqualTo(1L);

		final Path prRootPath = provider
				.getPath(URI.create("git://PR1from/developmaster@getfileattriviewtestrepo/"));

		final HiddenAttributeView extendedAttrs = provider.getFileAttributeView(prRootPath, HiddenAttributeView.class);

		assertThat(extendedAttrs.readAttributes().isDirectory()).isTrue();
		assertThat(extendedAttrs.readAttributes().isRegularFile()).isFalse();
		assertThat(extendedAttrs.readAttributes().isHidden()).isEqualTo(true);
		assertThat(extendedAttrs.readAttributes().size()).isEqualTo(1L);
		assertThat(extendedAttrs.readAttributes().creationTime()).isNotNull();
		assertThat(extendedAttrs.readAttributes().lastModifiedTime()).isNotNull();
	}

	@Test
	public void testReadAttributes() throws IOException {
		final URI newRepo = URI.create("git://readattrstestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@readattrstestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider.getPath(URI.create("git://user_branch@readattrstestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@readattrstestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		final BasicFileAttributes attrs = provider.readAttributes(path3, BasicFileAttributes.class);

		assertThat(attrs.isDirectory()).isFalse();
		assertThat(attrs.isRegularFile()).isTrue();
		assertThat(attrs.creationTime()).isNotNull();
		assertThat(attrs.lastModifiedTime()).isNotNull();
		assertThat(attrs.size()).isEqualTo(15L);

		try {
			provider.readAttributes(
					provider.getPath(URI.create("git://user_branch@readattrstestrepo/not_exists.txt")),
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}

		assertThat(provider.readAttributes(path3, MyAttrs.class)).isNull();

		final Path rootPath = provider.getPath(URI.create("git://user_branch@readattrstestrepo/"));

		final BasicFileAttributes attrsRoot = provider.readAttributes(rootPath, BasicFileAttributes.class);

		assertThat(attrsRoot.isDirectory()).isTrue();
		assertThat(attrsRoot.isRegularFile()).isFalse();
		assertThat(attrsRoot.creationTime()).isNotNull();
		assertThat(attrsRoot.lastModifiedTime()).isNotNull();
		assertThat(attrsRoot.size()).isEqualTo(1L);
	}

	@Test
	public void testReadAttributesMap() throws IOException {
		final URI newRepo = URI.create("git://readattrsmaptestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@readattrsmaptestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider
				.getPath(URI.create("git://user_branch@readattrsmaptestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@readattrsmaptestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		assertThat(provider.readAttributes(path, "*")).isNotNull().hasSize(9);
		assertThat(provider.readAttributes(path, "basic:*")).isNotNull().hasSize(9);
		assertThat(provider.readAttributes(path, "basic:isRegularFile")).isNotNull().hasSize(1);
		assertThat(provider.readAttributes(path, "basic:isRegularFile,isDirectory")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(path, "basic:isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(path, "basic:someThing")).isNotNull().hasSize(0);
		assertThat(provider.readAttributes(path, "version:version")).isNotNull().hasSize(1);

		assertThat(provider.readAttributes(path, "isRegularFile")).isNotNull().hasSize(1);
		assertThat(provider.readAttributes(path, "isRegularFile,isDirectory")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(path, "isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(path, "someThing")).isNotNull().hasSize(0);

		try {
			provider.readAttributes(path, ":someThing");
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(path, "advanced:isRegularFile");
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		final Path rootPath = provider.getPath(URI.create("git://user_branch@readattrsmaptestrepo/"));

		assertThat(provider.readAttributes(rootPath, "*")).isNotNull().hasSize(9);
		assertThat(provider.readAttributes(rootPath, "basic:*")).isNotNull().hasSize(9);
		assertThat(provider.readAttributes(rootPath, "basic:isRegularFile")).isNotNull().hasSize(1);
		assertThat(provider.readAttributes(rootPath, "basic:isRegularFile,isDirectory")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(rootPath, "basic:isRegularFile,isDirectory,someThing")).isNotNull()
				.hasSize(2);
		assertThat(provider.readAttributes(rootPath, "basic:someThing")).isNotNull().hasSize(0);

		assertThat(provider.readAttributes(rootPath, "isRegularFile")).isNotNull().hasSize(1);
		assertThat(provider.readAttributes(rootPath, "isRegularFile,isDirectory")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(rootPath, "isRegularFile,isDirectory,someThing")).isNotNull().hasSize(2);
		assertThat(provider.readAttributes(rootPath, "someThing")).isNotNull().hasSize(0);

		try {
			provider.readAttributes(rootPath, ":someThing");
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}

		try {
			provider.readAttributes(rootPath, "advanced:isRegularFile");
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.readAttributes(
					provider.getPath(URI.create("git://user_branch@readattrsmaptestrepo/not_exists.txt")),
					BasicFileAttributes.class);
			failBecauseExceptionWasNotThrown(NoSuchFileException.class);
		} catch (NoSuchFileException ignored) {
		}
	}

	@Test
	public void testSetAttribute() throws IOException {
		final URI newRepo = URI.create("git://setattrtestrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://master@setattrtestrepo/myfile1.txt"));

		final OutputStream outStream = provider.newOutputStream(path);
		outStream.write("my cool content".getBytes());
		outStream.close();

		final Path path2 = provider.getPath(URI.create("git://user_branch@setattrtestrepo/other/path/myfile2.txt"));

		final OutputStream outStream2 = provider.newOutputStream(path2);
		outStream2.write("my cool content".getBytes());
		outStream2.close();

		final Path path3 = provider.getPath(URI.create("git://user_branch@setattrtestrepo/myfile3.txt"));

		final OutputStream outStream3 = provider.newOutputStream(path3);
		outStream3.write("my cool content".getBytes());
		outStream3.close();

		try {
			provider.setAttribute(path3, "basic:isRegularFile", true);
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3, "isRegularFile", true);
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3, "notExisits", true);
			failBecauseExceptionWasNotThrown(IllegalStateException.class);
		} catch (IllegalStateException ignored) {
		}

		try {
			provider.setAttribute(path3, "advanced:notExisits", true);
			failBecauseExceptionWasNotThrown(UnsupportedOperationException.class);
		} catch (UnsupportedOperationException ignored) {
		}

		try {
			provider.setAttribute(path3, ":isRegularFile", true);
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
		} catch (IllegalArgumentException ignored) {
		}
	}

	private static class MyInvalidFileAttributeView implements BasicFileAttributeView {

		@Override
		public BasicFileAttributes readAttributes() throws IOException {
			return null;
		}

		@Override
		public void setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime)
				throws IOException {

		}

		@Override
		public String name() {
			return null;
		}
	}

	@Test
	public void checkProperAmend() throws Exception {

		final URI newRepo = URI.create("git://outstreamtestrepo");

		final FileSystem fs = provider.newFileSystem(newRepo, new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT, "true");
			}
		});

		assertThat(fs).isNotNull();

		for (int z = 0; z < 5; z) {
			final Path _path = provider
					.getPath(URI.create("git://user_branch@outstreamtestrepo/some/path/myfile.txt"));
			provider.setAttribute(_path, FileSystemState.FILE_SYSTEM_STATE_ATTR, FileSystemState.BATCH);
			{
				final Path path = provider
						.getPath(URI.create("git://user_branch@outstreamtestrepo/some/path/myfile.txt"));
				final OutputStream outStream = provider.newOutputStream(path);
				assertThat(outStream).isNotNull();
				outStream.write(("my cool content"  z).getBytes());
				outStream.close();
			}
			{
				final Path path2 = provider
						.getPath(URI.create("git://error_branch@outstreamtestrepo/some/path/myfile.txt"));
				final OutputStream outStream2 = provider.newOutputStream(path2);
				assertThat(outStream2).isNotNull();
				outStream2.write(("bad content"  z).getBytes());
				outStream2.close();
			}

			provider.setAttribute(_path, FileSystemState.FILE_SYSTEM_STATE_ATTR, FileSystemState.NORMAL);
		}

		final Path path = provider.getPath(URI.create("git://error_branch@outstreamtestrepo/some/path/myfile.txt"));
		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path.getRoot(),
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);
	}

	@Test
	public void accessOldVersions() throws Exception {

		final URI newRepo = URI.create("git://oldversionstestrepo");

		final FileSystem fs = provider.newFileSystem(newRepo, new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT, "true");
			}
		});

		assertThat(fs).isNotNull();

		for (int i = 0; i < 5; i) {
			final Path path = provider.getPath(URI.create("git://oldversionstestrepo/some/path/myfile.txt"));
			final OutputStream outStream = provider.newOutputStream(path);
			assertThat(outStream).isNotNull();
			outStream.write(("my cool content"  i).getBytes());
			outStream.close();
		}

		final Path path = provider.getPath(URI.create("git://oldversionstestrepo/some/path/myfile.txt"));
		final JGitVersionAttributeViewImpl attrs = provider.getFileAttributeView(path,
				JGitVersionAttributeViewImpl.class);

		assertThat(attrs.readAttributes().history().records().size()).isEqualTo(5);

		for (int i = 0; i < 5; i) {
			final Path oldPath = provider
					.getPath(URI.create("git://"  attrs.readAttributes().history().records().get(i).id()
							 "@oldversionstestrepo/some/path/myfile.txt"));
			final InputStream stream = provider.newInputStream(oldPath);
			assertNotNull(stream);
			final String content = new Scanner(stream).useDelimiter("\\A").next();
			assertEquals("my cool content"  i, content);
		}
	}

	@Test
	public void checkProperSquash() throws IOException, GitAPIException {

		final URI newRepo = URI.create("git://squashrepo");
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path generalPath = provider.getPath(URI.create("git://master@squashrepo/"));
		final Path path = provider.getPath(URI.create("git://master@squashrepo/myfile1.txt"));
		final Path path2 = provider.getPath(URI.create("git://master@squashrepo/myfile2.txt"));
		final Path path3 = provider.getPath(URI.create("git://master@squashrepo/myfile3.txt"));

		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();
		final RevCommit commit = ((GitImpl) fs.getGit())._log().add(fs.getGit().getRef("master").getObjectId())
				.setMaxCount(1).call().iterator().next();

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing!", new Date(),
				commit.getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath, SquashOption.SQUASH_ATTR, squashOption);

		int commitsCount = 0;
		for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
			commitsCount;
			System.out.println(com.getName()  "  "  com.getFullMessage());
		}
		assertThat(commitsCount).isEqualTo(2);
	}

	@Test(expected = GitException.class)
	public void testSquashFailBecauseCommitIsFromAnotherBranch() throws IOException, GitAPIException {

		final URI newRepo = URI.create("git://squashrepo");
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path generalPath = provider.getPath(URI.create("git://master@squashrepo/"));
		final Path path = provider.getPath(URI.create("git://develop@squashrepo/myfile1.txt"));
		final Path path2 = provider.getPath(URI.create("git://master@squashrepo/myfile2.txt"));
		final Path path3 = provider.getPath(URI.create("git://master@squashrepo/myfile3.txt"));

		final OutputStream aStream = provider.newOutputStream(path);
		aStream.write("my cool content".getBytes());
		aStream.close();

		final List<RevCommit> commits = getCommitsFromBranch((GitImpl) fs.getGit(), "develop");

		final OutputStream bStream = provider.newOutputStream(path2);
		bStream.write("my cool content".getBytes());
		bStream.close();
		final OutputStream cStream = provider.newOutputStream(path3);
		cStream.write("my cool content".getBytes());
		cStream.close();

		final VersionRecord record = makeVersionRecord("aparedes", "aparedes@redhat.com", "squashing!", new Date(),
				commits.get(0).getName());
		final SquashOption squashOption = new SquashOption(record);

		provider.setAttribute(generalPath, SquashOption.SQUASH_ATTR, squashOption);
	}

	@Test
	public void checkBatchError() throws Exception {
		final URI newRepo = URI.create("git://outstreamtestrepo");

		final FileSystem fs = provider.newFileSystem(newRepo, new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT, "true");
			}
		});

		provider = spy(provider);

		doThrow(new RuntimeException()).when(provider).notifyDiffs(any(JGitFileSystemImpl.class), any(String.class),
				any(String.class), any(String.class), any(String.class), any(ObjectId.class), any(ObjectId.class));

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://user_branch@outstreamtestrepo/some/path/myfile.txt"));
		provider.setAttribute(path, FileSystemState.FILE_SYSTEM_STATE_ATTR, FileSystemState.BATCH);
		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my cool content").getBytes());
		outStream.close();

		try {
			provider.setAttribute(path, FileSystemState.FILE_SYSTEM_STATE_ATTR, FileSystemState.NORMAL);
		} catch (Exception ex) {
			fail("Batch can't fail!", ex);
		}
	}

	@Test
	public void resolveFSName() {

		String fsName = "dorarepo";
		assertEquals(fsName, provider.extractFSName(URI.create("git://dorarepo")));
		assertEquals(fsName, provider.extractFSName(URI.create("default://dorarepo")));

		assertEquals(fsName, provider.extractFSName(URI.create("git://branch@dorarepo")));
		assertEquals(fsName, provider.extractFSName(URI.create("default://branch@dorarepo")));

		fsName = "dorarepo/subdir";
		assertEquals(fsName, provider.extractFSName(URI.create("git://dorarepo/subdir")));
		assertEquals("dorarepo/subdir", provider.extractFSName(URI.create("default://dorarepo/subdir")));

		assertEquals("dorarepo/subdir", provider.extractFSName(URI.create("git://branch@dorarepo/subdir")));
		assertEquals("dorarepo/subdir", provider.extractFSName(URI.create("default://branch@dorarepo/subdir")));

		fsName = "dorarepo/subdir/subdir";
		assertEquals(fsName, provider.extractFSName(URI.create("git://dorarepo/subdir/subdir")));
		assertEquals(fsName, provider.extractFSName(URI.create("default://dorarepo/subdir/subdir")));

		assertEquals(fsName, provider.extractFSName(URI.create("git://branch@dorarepo/subdir/subdir")));
		assertEquals(fsName, provider.extractFSName(URI.create("default://branch@dorarepo/subdir/subdir")));
	}

	@Test
	public void resolveSimpleFSNames() throws IOException {

		final URI newRepo = URI.create("git://dorarepo");

		try {
			final Path path = provider.getPath(URI.create("git://dorarepo/some/path/myfile.txt"));
			fail("should triggered FileSystemNotFoundException");
		} catch (FileSystemNotFoundException e) {
			// ignored
		}

		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		final Path path = provider.getPath(URI.create("git://dorarepo/some/path/myfile.txt"));
		final Path another = provider.getPath(URI.create("git://dorarepo/another/path/myfile.txt"));

		assertEquals(fs, path.getFileSystem());
		assertEquals(path.getFileSystem(), another.getFileSystem());
	}

	@Test
	public void resolveComposedFSNames() throws IOException {

		final URI simpleName = URI.create("git://dorarepo");

		final FileSystem fsSimpleName = provider.newFileSystem(simpleName, EMPTY_ENV);

		assertThat(fsSimpleName).isNotNull();

		final URI composedName = URI.create("git://oudora/dorarepo");

		final FileSystem fsComposedName = provider.newFileSystem(composedName, EMPTY_ENV);

		assertThat(fsComposedName).isNotNull();

		assertNotSame(fsSimpleName, fsComposedName);

		assertEquals(fsSimpleName, provider.getFileSystem(simpleName));

		assertEquals(fsComposedName, provider.getFileSystem(composedName));

		final URI simpleFileName = URI.create("git://dorarepo/file.txt");

		assertEquals(fsSimpleName, provider.getFileSystem(simpleFileName));

		final URI composedFileName = URI.create("git://oudora/dorarepo/file.txt");

		assertEquals(fsComposedName, provider.getFileSystem(composedFileName));
	}

	@Test
	public void validFSNameTest() throws IOException {

		checkAmbiguousFS("git://dorarepo", "git://dorarepo/subdir");

		checkAmbiguousFS("git://bentorepo/subdir", "git://bentorepo");

		checkAmbiguousFS("git://rex/subdir", "git://rex", "git://rex/subdir/subdir", "git://rex/subdir/subdir");

		provider.newFileSystem(URI.create("git://ou/dora"), EMPTY_ENV);
		provider.newFileSystem(URI.create("git://user1/dora"), EMPTY_ENV);
		provider.newFileSystem(URI.create("git://user2/dora"), EMPTY_ENV);
		provider.newFileSystem(URI.create("git://user3/dora"), EMPTY_ENV);
	}

	private void checkAmbiguousFS(String fsOriginalName, String... ambiguousFsName) throws IOException {
		provider.newFileSystem(URI.create(fsOriginalName), EMPTY_ENV);
		try {
			for (String fsName : ambiguousFsName) {
				provider.newFileSystem(URI.create(fsName), EMPTY_ENV);
			}
			fail("ambiguous fs");
		} catch (AmbiguousFileSystemNameException e) {
			// expected
		}
	}

	@Test
	public void checkRootPath() throws IOException {

		URI composedName = URI.create("git://dorarepo/subdir1");

		FileSystem fsComposedName = provider.newFileSystem(composedName, EMPTY_ENV);

		Path path = provider.getPath(URI.create("git://dorarepo/subdir1/file.txt"));
		Path path1 = provider.getPath(URI.create("git://origin/bla@dorarepo/subdir1/file2.txt"));

		assertEquals(fsComposedName, path.getRoot().getFileSystem());

		assertEquals(fsComposedName, path1.getRoot().getFileSystem());
	}

	@Test
	public void getPathForComposedFSNames() throws IOException {

		URI composedName = URI.create("git://dorarepo/subdir1");

		FileSystem fsComposedName = provider.newFileSystem(composedName, EMPTY_ENV);
		URI simpleFileName = URI.create("git://dorarepo/subdir1/file.txt");

		Path path = provider.getPath(simpleFileName);

		assertEquals(fsComposedName, path.getFileSystem());
		assertEquals("/file.txt", ((JGitPathImpl) path).getPath());

		URI simpleName = URI.create("git://bentorepo/");

		FileSystem fsSimpleName = provider.newFileSystem(simpleName, EMPTY_ENV);

		URI composedFileName = URI.create("git://bentorepo/subdir1/file.txt");

		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName, path.getFileSystem());
		assertEquals("/subdir1/file.txt", ((JGitPathImpl) path).getPath());

		composedFileName = URI.create("git://bentorepo/subdir1/subdir2/file.txt");

		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName, path.getFileSystem());
		assertEquals("/subdir1/subdir2/file.txt", ((JGitPathImpl) path).getPath());

		composedFileName = URI.create("git://bentorepo/subdir1/subdir2/subdir3");

		path = provider.getPath(composedFileName);

		assertEquals(fsSimpleName, path.getFileSystem());
		assertEquals("/subdir1/subdir2/subdir3", ((JGitPathImpl) path).getPath());
	}

	@Test
	public void getPathForComposedFSNames2() throws IOException {
		URI composedName = URI.create("git://user1/dora");

		FileSystem fsComposedName1 = provider.newFileSystem(composedName, EMPTY_ENV);

		URI composedName2 = URI.create("git://user2/dora");

		FileSystem fsComposedName2 = provider.newFileSystem(composedName2, EMPTY_ENV);

		URI composedFileName1 = URI.create("git://user1/dora/file.txt");

		Path path1 = provider.getPath(composedFileName1);

		URI composedFileName2 = URI.create("git://user2/dora/file.txt");

		Path path2 = provider.getPath(composedFileName2);

		assertNotEquals(fsComposedName1, fsComposedName2);
		assertNotEquals(path1.getFileSystem(), path2.getFileSystem());

		assertEquals(path2.toString(), provider.extractPath(composedFileName2));
	}

	@Test
	public void extractPathTest() throws IOException {

		URI composedName = URI.create("git://user1/dora");

		FileSystem fsComposedName1 = provider.newFileSystem(composedName, EMPTY_ENV);

		URI composedFileName1 = URI.create("git://user1/dora/file.txt");

		Path path1 = provider.getPath(composedFileName1);

		assertEquals(path1.toString(), provider.extractPath(composedFileName1));
	}

	@Test
	public void resolveByRepositoryTest() throws IOException {

		JGitFileSystem fsSimpleName = ((JGitFileSystemProxy) provider.newFileSystem(URI.create("git://repo"),
				EMPTY_ENV)).getRealJGitFileSystem();

		JGitFileSystemProvider.RepositoryResolverImpl<Object> objectRepositoryResolver = provider.new RepositoryResolverImpl<>();

		assertEquals(fsSimpleName, objectRepositoryResolver.resolveFileSystem(fsSimpleName.getGit().getRepository()));

		JGitFileSystem fsComposedName1 = ((JGitFileSystemProxy) provider.newFileSystem(URI.create("git://user1/dora"),
				EMPTY_ENV)).getRealJGitFileSystem();

		assertEquals(fsComposedName1,
				objectRepositoryResolver.resolveFileSystem(fsComposedName1.getGit().getRepository()));
	}

	@Test
	public void extractFSHooksTest() {
		Map<String, Object> env = new HashMap<>();

		Object hook = (FileSystemHooks.FileSystemHook) context > {
		};

		env.put("dora", "bento");
		env.put(FileSystemHooks.ExternalUpdate.name(), hook);

		Map<FileSystemHooks, ?> fileSystemHooksMap = JGitFileSystemProvider.extractFSHooks(env);

		assertEquals(1, fileSystemHooksMap.size());
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.ExternalUpdate));
		assertEquals(hook, fileSystemHooksMap.get(FileSystemHooks.ExternalUpdate));
	}

	@Test
	public void extractCheckBranchAccessHookTest() {
		Map<String, Object> env = new HashMap<>();

		Object hook = (FileSystemHooks.FileSystemHook) context > {
		};

		env.put("dora", "bento");
		env.put(FileSystemHooks.BranchAccessCheck.name(), hook);

		Map<FileSystemHooks, ?> fileSystemHooksMap = JGitFileSystemProvider.extractFSHooks(env);

		assertEquals(1, fileSystemHooksMap.size());
		assertTrue(fileSystemHooksMap.keySet().contains(FileSystemHooks.BranchAccessCheck));
		assertEquals(hook, fileSystemHooksMap.get(FileSystemHooks.BranchAccessCheck));
	}

	@Test
	public void testCloseFileSystem() throws IOException {

		JGitFileSystemProvider fsProvider = spy(new JGitFileSystemProvider(getGitPreferences()) {

			@Override
			protected void setupFileSystemsManager() {
				fsManager = mock(JGitFileSystemsManager.class);
				when(fsManager.allTheFSAreClosed()).thenReturn(true);
			}
		});

		fsProvider.onCloseFileSystem(mock(JGitFileSystem.class));

		verify(fsProvider, times(1)).shutdownEventsManager();
	}

	@Test
	public void moveBranchesTest() throws IOException {
		final URI newRepo = URI.create("git://movebranchrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby anotherbranch".getBytes());
			outStream.close();
		}

		final Path source = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/"));
		final Path target = provider.getPath(URI.create("git://anotherbranchmoved@movebranchrepo/"));

		provider.move(source, target);

		Throwable extractContentCall = catchThrowable(
				() > extractContent(provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt"))));

		assertThat(extractContentCall).isInstanceOf(NoSuchFileException.class);

		final String contentMoved = extractContent(
				provider.getPath(URI.create("git://anotherbranchmoved@movebranchrepo/dorinha.txt")));

		assertThat(contentMoved).isNotNull().isEqualTo("little baby anotherbranch");
	}

	@Test
	public void moveBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		final URI newRepo = URI.create("git://movebranchrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final URI anotherRepo = URI.create("git://anotherrepo");
		provider.newFileSystem(anotherRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby anotherbranch".getBytes());
			outStream.close();
		}

		final Path source = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/"));
		final Path target = provider.getPath(URI.create("git://anotherbranchmoved@anotherrepo/"));

		assertThatThrownBy(() > provider.move(source, target)).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void copyBranchesNotAtTheSameFSShouldNotBeAllowedTest() throws IOException {
		final URI newRepo = URI.create("git://movebranchrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		final URI anotherRepo = URI.create("git://anotherrepo");
		provider.newFileSystem(anotherRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby anotherbranch".getBytes());
			outStream.close();
		}

		final Path source = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/"));
		final Path target = provider.getPath(URI.create("git://anotherbranchmoved@anotherrepo/"));

		assertThatThrownBy(() > provider.copy(source, target)).isInstanceOf(IllegalStateException.class);
	}

	@Test
	public void copyBranchesTest() throws IOException {
		final URI newRepo = URI.create("git://movebranchrepo");
		provider.newFileSystem(newRepo, EMPTY_ENV);

		{
			final Path path = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt"));

			final OutputStream outStream = provider.newOutputStream(path);
			outStream.write("little baby anotherbranch".getBytes());
			outStream.close();
		}

		final Path source = provider.getPath(URI.create("git://anotherbranch@movebranchrepo/"));
		final Path target = provider.getPath(URI.create("git://anotherbranchmoved@movebranchrepo/"));

		provider.copy(source, target);

		final String originalContent = extractContent(
				provider.getPath(URI.create("git://anotherbranch@movebranchrepo/dorinha.txt")));

		assertThat(originalContent).isNotNull().isEqualTo("little baby anotherbranch");

		final String contentMoved = extractContent(
				provider.getPath(URI.create("git://anotherbranchmoved@movebranchrepo/dorinha.txt")));

		assertThat(contentMoved).isNotNull().isEqualTo("little baby anotherbranch");
	}

	private String extractContent(Path path) throws IOException {
		final InputStream inputStream = provider.newInputStream(path);
		assertThat(inputStream).isNotNull();

		final String content = new Scanner(inputStream).useDelimiter("\\A").next();

		inputStream.close();

		return content;
	}

	private interface MyAttrs extends BasicFileAttributes {

	}

	private VersionRecord makeVersionRecord(final String author, final String email, final String comment,
			final Date date, final String commit) {
		return new VersionRecord() {
			@Override
			public String id() {
				return commit;
			}

			@Override
			public String author() {
				return author;
			}

			@Override
			public String email() {
				return email;
			}

			@Override
			public String comment() {
				return comment;
			}

			@Override
			public Date date() {
				return date;
			}

			@Override
			public String uri() {
				return null;
			}
		};
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin, String branch)
			throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository(), branch).execute().getObjectId();
		for (RevCommit commit : origin._log().add(id).call()) {
			commits.add(commit);
		}
		return commits;
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderUnsupportedOpTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderUnsupportedOpTest.java
new file mode 100644
index 0000000000..10757168e7
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderUnsupportedOpTest.java
@@ 0,0 1,99 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Set;

import org.junit.Test;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JGitFileSystemImplProviderUnsupportedOpTest extends AbstractTestInfra {

	@Test
	public void testNewFileSystemUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://someunsupreponame");

		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = JGitPathImpl.create((JGitFileSystem) fs, "", "repo2name", false);

		assertThatThrownBy(() > provider.newFileSystem(path, EMPTY_ENV))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void testNewFileChannelUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://newfcreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://newfcreponame/file.txt"));

		final Set<? extends OpenOption> options = emptySet();
		assertThatThrownBy(() > provider.newFileChannel(path, options))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void testNewAsynchronousFileChannelUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://newasyncreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path path = provider.getPath(URI.create("git://newasyncreponame/file.txt"));

		final Set<? extends OpenOption> options = emptySet();
		assertThatThrownBy(() > provider.newAsynchronousFileChannel(path, options, null))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void testCreateSymbolicLinkUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://symbolicreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path link = provider.getPath(URI.create("git://symbolicreponame/link.lnk"));
		final Path path = provider.getPath(URI.create("git://symbolicreponame/file.txt"));

		assertThatThrownBy(() > provider.createSymbolicLink(link, path))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void testCreateLinkUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://linkreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path link = provider.getPath(URI.create("git://linkreponame/link.lnk"));
		final Path path = provider.getPath(URI.create("git://linkreponame/file.txt"));

		assertThatThrownBy(() > provider.createLink(link, path)).isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	public void testReadSymbolicLinkUnsupportedOp() throws IOException {
		final URI newRepo = URI.create("git://readlinkreponame");

		provider.newFileSystem(newRepo, EMPTY_ENV);

		final Path link = provider.getPath(URI.create("git://readlinkreponame/link.lnk"));

		assertThatThrownBy(() > provider.readSymbolicLink(link)).isInstanceOf(UnsupportedOperationException.class);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderWithFoldersTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderWithFoldersTest.java
new file mode 100644
index 0000000000..c365d25ac4
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplProviderWithFoldersTest.java
@@ 0,0 1,128 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.fs.FileSystemState;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitFileSystemImplProviderWithFoldersTest extends AbstractTestInfra {

	@Test
	public void testNewFileSystemWithSubfolder() throws IOException {
		final URI newRepo = URI.create("git://test/subreponame");
		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		assertThat(fs).isNotNull();

		final DirectoryStream<Path> stream = provider.newDirectoryStream(provider.getPath(newRepo), null);
		assertThat(stream).isEmpty();
	}

	@Test
	public void testCreateFileIntoRepositoryWithFolder() throws IOException, GitAPIException {

		final Map<String, ?> env = new HashMap<String, Object>() {
			{
				put("init", Boolean.TRUE);
			}
		};

		String oldPath = "git://test/old";
		final URI oldUri = URI.create(oldPath);
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(oldUri, env);

		final Path path = provider.getPath(URI.create("git://master@test/old/some/path/myfile.txt"));
		provider.setAttribute(path, FileSystemState.FILE_SYSTEM_STATE_ATTR, FileSystemState.BATCH);
		final OutputStream outStream = provider.newOutputStream(path);
		assertThat(outStream).isNotNull();
		outStream.write(("my cool content").getBytes());
		outStream.close();

		assertThat(new File(provider.getGitRepoContainerDir(), "test/old"  ".git")).exists();

		int commitsCount = 0;
		for (RevCommit com : ((GitImpl) fs.getGit())._log().all().call()) {
			commitsCount;
		}
	}

	@Test
	public void testExtractPathWithAuthority() throws IOException {

		provider.newFileSystem(URI.create("git://test/repo"), new HashMap<String, Object>() {
			{
				put("init", Boolean.TRUE);
			}
		});

		String path = "git://master@test/repo/readme.md";
		final URI uri = URI.create(path);
		final String extracted = provider.extractPath(uri);
		assertThat(extracted).isEqualTo("/readme.md");
	}

	@Test
	public void testComplexExtractPath() throws IOException {

		final URI newRepo = URI.create("git://test/repo");
		final FileSystem fs = provider.newFileSystem(newRepo, EMPTY_ENV);

		String path = "git://origin/master@test/repo/readme.md";
		final URI uri = URI.create(path);
		final String extracted = provider.extractPath(uri);
		assertThat(extracted).isEqualTo("/readme.md");
	}

	@Test
	public void testExtractComplexRepoName() throws IOException {
		provider.newFileSystem(URI.create("git://test/repo"), new HashMap<String, Object>() {
			{
				put("init", Boolean.TRUE);
			}
		});

		String path = "git://origin/master@test/repo/readme.md";
		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}

	@Test
	public void testExtractSimpleRepoName() {
		String path = "git://master@test/repo/readme.md";
		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}

	@Test
	public void testExtractVerySimpleRepoName() {
		String path = "git://test/repo/readme.md";
		final URI uri = URI.create(path);
		final String extracted = provider.extractFSNameWithPath(uri);
		assertThat(extracted).isEqualTo("test/repo/readme.md");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplTest.java
new file mode 100644
index 0000000000..d4bba281de
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemImplTest.java
@@ 0,0 1,373 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.AssertionsForClassTypes;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JGitFileSystemImplTest extends AbstractTestInfra {

	static {
		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest", ""));
	}

	@Test
	public void testOnlyLocalRoot() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();
		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("myrepo");

		assertThat(fileSystem.getRootDirectories()).hasSize(1);
		final Path root = fileSystem.getRootDirectories().iterator().next();
		assertThat(root.toString()).isEqualTo("/");

		assertThat(root.getRoot().toString()).isEqualTo("/");
	}

	@Test
	public void testRemoteRoot() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final File tempDir = createTempDirectory();
		final Git git = new GitImpl(
				GitImpl._cloneRepository().setNoCheckout(false).setBare(true).setCloneAllBranches(true)
						.setURI(setupGit().getRepository().getDirectory().toString()).setDirectory(tempDir).call());

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("myrepo");

		assertThat(fileSystem.getRootDirectories()).hasSize(1);
		final Path root = fileSystem.getRootDirectories().iterator().next();
		assertThat(root.toString()).isEqualTo("/");

		assertThat(root.getRoot().toString()).isEqualTo("/");
	}

	private JGitFileSystemLock createFSLock(Git git) {
		return new JGitFileSystemLock(git, TimeUnit.MILLISECONDS, 30_000L);
	}

	@Test
	public void testProvider() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.getName()).isEqualTo("myrepo");
		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");

		assertThat(fileSystem.provider()).isEqualTo(fsProvider);
	}

	@Test(expected = IllegalStateException.class)
	public void testClose() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("myrepo");

		assertThat(fileSystem.isOpen()).isTrue();
		assertThat(fileSystem.getFileStores()).isNotNull();
		fileSystem.close();
		assertThat(fileSystem.isOpen()).isFalse();
		assertThat(fileSystem.getFileStores()).isNotNull();
	}

	@Test
	public void testSupportedFileAttributeViews() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.isReadOnly()).isFalse();
		assertThat(fileSystem.getSeparator()).isEqualTo("/");
		assertThat(fileSystem.getName()).isEqualTo("myrepo");

		assertThat(fileSystem.supportedFileAttributeViews()).isNotEmpty().hasSize(2).contains("basic", "version");
	}

	@Test
	public void testPathNonBranchRooted() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("/path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myrepo/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathNonBranchNonRooted() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myrepo/:path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testPathBranchRooted() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("testbranch", "/path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://testbranch@myrepo/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathBranchNonRooted() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("testbranch", "path/to/some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://testbranch@myrepo/:path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testPathBranchRooted2() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("testbranch", "/path/to", "some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://testbranch@myrepo/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("/");
	}

	@Test
	public void testPathBranchNonRooted2() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
		when(fsProvider.getScheme()).thenReturn("git");

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		final Path path = fileSystem.getPath("testbranch", "path/to", "some/place.txt");

		AssertionsForClassTypes.assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://testbranch@myrepo/:path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isNotNull().isEqualTo("path");
		assertThat(path.getRoot().toString()).isNotNull().isEqualTo("");
	}

	@Test
	public void testFileStore() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final File tempDir = createTempDirectory();
		final Git git = setupGit(tempDir);

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		assertThat(fileSystem.getFileStores()).hasSize(1);
		final FileStore fileStore = fileSystem.getFileStores().iterator().next();
		assertThat(fileStore).isNotNull();

		assertThat(fileStore.getTotalSpace()).isEqualTo(tempDir.getTotalSpace());
		assertThat(fileStore.getUsableSpace()).isEqualTo(tempDir.getUsableSpace());
	}

	@Test
	public void testPathEqualsWithDifferentRepos() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git1 = setupGit();

		final JGitFileSystemImpl fileSystem1 = new JGitFileSystemImpl(fsProvider, null, git1, createFSLock(git1),
				"myrepo1", CredentialsProvider.getDefault(), null, null);

		final Git git2 = setupGit();

		final JGitFileSystemImpl fileSystem2 = new JGitFileSystemImpl(fsProvider, null, git2, createFSLock(git2),
				"myrepo2", CredentialsProvider.getDefault(), null, null);

		final Path path1 = fileSystem1.getPath("master", "/path/to/some.txt");
		final Path path2 = fileSystem2.getPath("master", "/path/to/some.txt");

		AssertionsForClassTypes.assertThat(path1).isNotEqualTo(path2);

		AssertionsForClassTypes.assertThat(path1).isEqualTo(fileSystem1.getPath("/path/to/some.txt"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetUserPrincipalLookupService() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);
		fileSystem.getUserPrincipalLookupService();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetPathMatcher() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);
		fileSystem.getPathMatcher("*");
	}

	@Test
	public void lockShouldSupportMultipleInnerLocksForTheSameThreadTest() throws IOException, GitAPIException {
		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fileSystem = new JGitFileSystemImpl(fsProvider, null, git, createFSLock(git),
				"myrepo", CredentialsProvider.getDefault(), null, null);

		fileSystem.lock();
		// inner locks
		fileSystem.lock();
		fileSystem.lock();
		fileSystem.unlock();
		fileSystem.unlock();
		fileSystem.unlock();
	}

	@Test
	public void lockTest() throws IOException, GitAPIException {

		final Git git = setupGit();
		JGitFileSystemLock lock = createFSLock(git);

		JGitFileSystemLock lockSpy = spy(lock);

		lockSpy.lock();
		lockSpy.lock();
		lockSpy.lock();
		verify(lockSpy, times(1)).physicalLockOnFS();

		lockSpy.unlock();
		lockSpy.unlock();
		lockSpy.unlock();
		verify(lockSpy, times(1)).physicalUnLockOnFS();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemLockTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemLockTest.java
new file mode 100644
index 0000000000..2c3d3a95c2
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemLockTest.java
@@ 0,0 1,67 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JGitFileSystemLockTest {

	@Test
	public void thresholdMaxTest() {
		long lastAccessThreshold = Long.MAX_VALUE;
		JGitFileSystemLock lock = createLock(lastAccessThreshold);
		lock.registerAccess();
		assertTrue(lock.hasBeenInUse());
	}

	@Test
	public void thresholdMinTest() {
		long lastAccessThreshold = Long.MIN_VALUE;
		JGitFileSystemLock lock = createLock(lastAccessThreshold);
		lock.registerAccess();

		lock.lock.lock();
		assertTrue(lock.hasBeenInUse());
		lock.lock.unlock();
		assertFalse(lock.hasBeenInUse());
	}

	private JGitFileSystemLock createLock(long lastAccessThreshold) {
		Git gitMock = mock(Git.class);
		Repository repo = mock(Repository.class);
		File directory = mock(File.class);
		when(directory.isDirectory()).thenReturn(true);
		when(directory.toURI()).thenReturn(URI.create(""));
		when(repo.getDirectory()).thenReturn(directory);
		when(gitMock.getRepository()).thenReturn(repo);
		return new JGitFileSystemLock(gitMock, TimeUnit.MILLISECONDS, lastAccessThreshold) {

			@Override
			Path createLockInfra(URI uri) {
				return mock(Path.class);
			}
		};
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConcurrentOperationsTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConcurrentOperationsTest.java
new file mode 100644
index 0000000000..a63c8edcad
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConcurrentOperationsTest.java
@@ 0,0 1,91 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class JGitFileSystemProviderConcurrentOperationsTest extends AbstractTestInfra {

	private Logger logger = LoggerFactory.getLogger(JGitFileSystemProviderConcurrentOperationsTest.class);

	@Test
	public void testConcurrentGitCreation() {

		int threadCount = 2;
		final CountDownLatch finished = new CountDownLatch(threadCount);
		List<Thread> threads = new ArrayList<>();

		for (int i = 0; i < threadCount; i) {
			final int name = i;
			Runnable r = () > {
				this.provider.createNewGitRepo(EMPTY_ENV, "git://parent/concurrenttest"  name);
				finished.countDown();
				logger.info("Countdown"  Thread.currentThread().getName());
			};
			Thread t = new Thread(r);
			threads.add(t);
			t.start();
		}

		wait(threads);
		assertEquals(0, finished.getCount());
	}

	@Test
	public void testConcurrentGitDeletion() throws IOException {

		String gitRepo = "git://parent/deletetestrepo";
		final URI newRepo = URI.create(gitRepo);
		JGitFileSystemProxy fs = (JGitFileSystemProxy) provider.newFileSystem(newRepo, EMPTY_ENV);

		int threadCount = 2;
		final CountDownLatch finished = new CountDownLatch(threadCount);
		List<Thread> threads = new ArrayList<>();

		for (int i = 0; i < threadCount; i) {
			final int name = i;
			Runnable r = () > {
				try {
					this.provider.deleteFS(fs.getRealJGitFileSystem());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				finished.countDown();
				logger.info("Countdown"  Thread.currentThread().getName());
			};
			Thread t = new Thread(r);
			threads.add(t);
			t.start();
		}

		wait(threads);
		assertEquals(0, finished.getCount());
	}

	private void wait(List<Thread> threads) {
		threads.forEach(thread > {
			try {
				thread.join();
			} catch (InterruptedException e) {
				logger.error("Error waiting for threads", e);
			}
		});
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProxyTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProxyTest.java
new file mode 100644
index 0000000000..ddc66920d3
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemProxyTest.java
@@ 0,0 1,51 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_PORT;
import static org.junit.Assert.assertTrue;

public class JGitFileSystemProxyTest extends AbstractTestInfra {

	private int gitDaemonPort;

	@Override
	public Map<String, String> getGitPreferences() {
		Map<String, String> gitPrefs = super.getGitPreferences();
		gitPrefs.put(GIT_DAEMON_ENABLED, "true");
		// use different port for every test > easy to run tests in parallel
		gitDaemonPort = findFreePort();
		gitPrefs.put(GIT_DAEMON_PORT, String.valueOf(gitDaemonPort));
		return gitPrefs;
	}

	@Test
	public void proxyTest() throws IOException {
		final URI originRepo = URI.create("git://encodingoriginname");

		final JGitFileSystem origin = (JGitFileSystem) provider.newFileSystem(originRepo, Collections.emptyMap());

		assertTrue(origin instanceof JGitFileSystemProxy);
		JGitFileSystemProxy proxy = (JGitFileSystemProxy) origin;
		JGitFileSystem realJGitFileSystem = proxy.getRealJGitFileSystem();
		assertTrue(realJGitFileSystem instanceof JGitFileSystemImpl);

		assertTrue(proxy.equals(realJGitFileSystem));
		assertTrue(realJGitFileSystem.equals(proxy));
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManagerTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManagerTest.java
new file mode 100644
index 0000000000..e900f8989c
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManagerTest.java
@@ 0,0 1,199 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class JGitFileSystemsEventsManagerTest {

	JGitFileSystemsEventsManager manager;
	JGitEventsBroadcast jGitEventsBroadcastMock = mock(JGitEventsBroadcast.class);

	@Before
	public void setup() {
		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}

			@Override
			JGitFileSystemWatchServices createFSWatchServicesManager() {
				return mock(JGitFileSystemWatchServices.class);
			}
		};
	}

	@Test
	public void doNotSetupClusterTest() {
		JGitFileSystemsEventsManager another = new JGitFileSystemsEventsManager();
		assertNull(another.getjGitEventsBroadcast());
	}

	@Test
	public void setupClusterTest() {
		assertNotNull(manager.getjGitEventsBroadcast());
	}

	@Test
	public void createWatchService() throws IOException {
		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}
		};

		WatchService fs = manager.newWatchService("fs");

		assertNotNull(fs);
		assertTrue(manager.getFsWatchServices().containsKey("fs"));
		verify(jGitEventsBroadcastMock).createWatchService("fs");
	}

	@Test
	public void shouldNotPublishEventsForANotWatchedFS() throws IOException {
		WatchService fsDora = manager.newWatchService("fsDora");
		WatchService fsBento = manager.newWatchService("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class), mock(WatchEvent.class));

		manager.publishEvents("another", mock(Path.class), elist);

		verify(jGitEventsBroadcastMock, never()).broadcast(any(), any(), any());
	}

	@Test
	public void publishEventsShouldBeWatched() throws IOException {
		WatchService fsDoraWS = manager.newWatchService("fsDora");
		WatchService fsBento = manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class), mock(WatchEvent.class));

		manager.publishEvents("fsDora", mock(Path.class), elist);

		verify(fsDoraWServices).publishEvents(any(), eq(elist));
		verify(jGitEventsBroadcastMock).broadcast(eq("fsDora"), any(), eq(elist));
		verify(fsBentoWServices, never()).publishEvents(any(), eq(elist));
	}

	@Test
	public void publishEventsWithoutBroadcast() throws IOException {
		manager.newWatchService("fsDora");
		manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		List<WatchEvent<?>> elist = Arrays.asList(mock(WatchEvent.class), mock(WatchEvent.class));

		manager.publishEvents("fsDora", mock(Path.class), elist, false);

		verify(fsDoraWServices).publishEvents(any(), eq(elist));
		verify(jGitEventsBroadcastMock, never()).broadcast(eq("fsDora"), any(), eq(elist));
		verify(fsBentoWServices, never()).publishEvents(any(), eq(elist));
	}

	@Test
	public void watchServicesEvents() throws IOException {

		manager = new JGitFileSystemsEventsManager() {
			@Override
			void setupJGitEventsBroadcast() {
				jGitEventsBroadcast = jGitEventsBroadcastMock;
			}
		};

		WatchService fsDora1 = manager.newWatchService("fsDora");
		WatchService fsDora2 = manager.newWatchService("fsDora");

		List<WatchEvent<?>> list3events = Arrays.asList(mock(WatchEvent.class), mock(WatchEvent.class),
				mock(WatchEvent.class));

		List<WatchEvent<?>> list2events = Arrays.asList(mock(WatchEvent.class), mock(WatchEvent.class));

		manager.publishEvents("fsDora", mock(Path.class), list3events, false);

		List<WatchEvent<?>> watchEvents = fsDora1.poll().pollEvents();
		assertEquals(3, watchEvents.size());
		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(3, watchEvents.size());

		manager.publishEvents("fsDora", mock(Path.class), list3events, false);
		manager.publishEvents("fsDora", mock(Path.class), list2events, false);

		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(3, watchEvents.size());

		watchEvents = fsDora2.poll().pollEvents();
		assertEquals(2, watchEvents.size());

		watchEvents = fsDora1.poll().pollEvents();
		assertEquals(3, watchEvents.size());

		watchEvents = fsDora1.poll().pollEvents();
		assertEquals(2, watchEvents.size());
	}

	@Test
	public void closeTest() throws IOException {
		manager.newWatchService("fsDora");
		manager.newWatchService("fsBento");

		JGitFileSystemWatchServices fsDoraWServices = manager.getFsWatchServices().get("fsDora");
		JGitFileSystemWatchServices fsBentoWServices = manager.getFsWatchServices().get("fsBento");

		manager.close("fsDora");

		verify(fsDoraWServices).close();
		verify(fsBentoWServices, never()).close();
	}

	@Test
	public void testShutdown() throws IOException {
		manager.newWatchService("fsPetra");
		manager.newWatchService("fsEureka");

		JGitFileSystemWatchServices fsPetraWatchService = manager.getFsWatchServices().get("fsPetra");
		JGitFileSystemWatchServices fsEurekaWatchService = manager.getFsWatchServices().get("fsEureka");

		manager.shutdown();

		verify(fsPetraWatchService).close();
		verify(fsEurekaWatchService).close();
		verify(jGitEventsBroadcastMock).close();
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitForkTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitForkTest.java
new file mode 100644
index 0000000000..536e604a71
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitForkTest.java
@@ 0,0 1,279 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystemAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.Fork;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class JGitForkTest extends AbstractTestInfra {

	private static final String TARGET_GIT = "target/target", SOURCE_GIT = "source/source";
	private static Logger logger = LoggerFactory.getLogger(JGitForkTest.class);

	@Test
	public void testToForkSuccess() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2222"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		new Fork(parentFolder, SOURCE_GIT, TARGET_GIT, null, CredentialsProvider.getDefault(), null, null).execute();

		final File gitCloned = new File(parentFolder, TARGET_GIT  ".git");
		final Git cloned = Git.createRepository(gitCloned);

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(2);

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(cloned.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");

		final String remotePath = new File(((GitImpl) cloned)._remoteList().call().get(0).getURIs().get(0).getPath())
				.getAbsolutePath();
		assertThat(remotePath).isEqualTo(new File(gitSource.getPath()).getAbsolutePath());
	}

	@Test(expected = GitException.class)
	public void testToForkAlreadyExists() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();

		final File gitTarget = new File(parentFolder, TARGET_GIT  ".git");
		final Git originTarget = new CreateRepository(gitTarget).execute().get();

		new Commit(originTarget, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();

		new Fork(parentFolder, SOURCE_GIT, TARGET_GIT, null, CredentialsProvider.getDefault(), null, null).execute();
	}

	@Test
	public void testToForkWrongSource() throws IOException {
		final File parentFolder = createTempDirectory();

		try {
			new Fork(parentFolder, SOURCE_GIT, TARGET_GIT, null, CredentialsProvider.getDefault(), null, null)
					.execute();
			fail("If got here is because it could for the repository");
		} catch (Clone.CloneException e) {
			assertThat(e).isNotNull();
			logger.info(e.getMessage(), e);
		}
	}

	@Test
	public void testForkRepository() throws GitAPIException, IOException {

		String SOURCE = "testforkA/source";
		String TARGET = "testforkB/target";

		final Map<String, ?> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT, "true");
			}
		};

		String sourcePath = "git://"  SOURCE;
		final URI sourceUri = URI.create(sourcePath);
		provider.newFileSystem(sourceUri, env);

		final Map<String, ?> forkEnv = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME, SOURCE);
			}
		};
		String forkPath = "git://"  TARGET;
		final URI forkUri = URI.create(forkPath);
		final JGitFileSystem fs = (JGitFileSystem) provider.newFileSystem(forkUri, forkEnv);

		assertThat(((GitImpl) fs.getGit())._remoteList().call().get(0).getURIs().get(0).toString())
				.isEqualTo(new File(provider.getGitRepoContainerDir(), SOURCE  ".git").toPath().toUri().toString());
	}

	@Test(expected = FileSystemAlreadyExistsException.class)
	public void testForkRepositoryThatAlreadyExists() throws IOException {

		String SOURCE = "testforkA/source";
		String TARGET = "testforkB/target";

		final Map<String, ?> env = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT, "true");
			}
		};

		String sourcePath = "git://"  SOURCE;
		final URI sourceUri = URI.create(sourcePath);
		provider.newFileSystem(sourceUri, env);

		final Map<String, ?> forkEnv = new HashMap<String, Object>() {
			{
				put(JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME, SOURCE);
			}
		};

		String forkPath = "git://"  TARGET;
		final URI forkUri = URI.create(forkPath);
		provider.newFileSystem(forkUri, forkEnv);
		provider.newFileSystem(forkUri, forkEnv);
	}

	@Test
	public void testForkWithoutHookDirShouldNotBeUpdatedAfterGitHookDirAdded() throws IOException, GitAPIException {

		final File hooksDir = createTempDirectory();

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		writeMockHook(hooksDir, PostCommitHook.NAME);
		writeMockHook(hooksDir, PreCommitHook.NAME);

		final Git repo = new CreateRepository(gitSource, null).execute().get();
		final Git existentRepoWithHookDirDefined = new CreateRepository(gitSource, hooksDir).execute().get();

		File[] hooks = new File(existentRepoWithHookDirDefined.getRepository().getDirectory(), "hooks").listFiles();
		assertThat(hooks).isEmpty();
	}

	@Test
	public void testForkWithHookDir() throws IOException, GitAPIException {
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir, PostCommitHook.NAME);
		writeMockHook(hooksDir, PreCommitHook.NAME);

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource, hooksDir).execute().get();

		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2222"));
					}
				}).execute();

		final Git cloned = new Fork(parentFolder, SOURCE_GIT, TARGET_GIT, null, CredentialsProvider.getDefault(), null,
				hooksDir).execute();

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).hasSize(1);

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/user_branch");

		final String remotePath = new File(((GitImpl) cloned)._remoteList().call().get(0).getURIs().get(0).getPath())
				.getAbsolutePath();
		assertThat(remotePath).isEqualTo(new File(gitSource.getPath()).getAbsolutePath());

		boolean foundPreCommitHook = false;
		boolean foundPostCommitHook = false;
		File[] hooks = new File(cloned.getRepository().getDirectory(), "hooks").listFiles();
		assertThat(hooks).isNotEmpty().isNotNull();
		assertThat(hooks.length).isEqualTo(2);
		for (File hook : hooks) {
			if (hook.getName().equals(PreCommitHook.NAME)) {
				foundPreCommitHook = hook.canExecute();
			} else if (hook.getName().equals(PostCommitHook.NAME)) {
				foundPostCommitHook = hook.canExecute();
			}
		}
		assertThat(foundPreCommitHook).isTrue();
		assertThat(foundPostCommitHook).isTrue();
	}

	@Test
	public void testForkMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");

		final Git origin = new CreateRepository(gitSource, null).execute().get();

		commit(origin, "master", "first", content("dir1/file.txt", "foo"), content("dir2/file2.txt", "bar"),
				content("file3.txt", "moogah"));

		branch(origin, "master", "dev");
		commit(origin, "dev", "second", content("dir1/file.txt", "foo1"), content("file3.txt", "bar1"));

		branch(origin, "master", "ignored");
		commit(origin, "ignored", "third", content("dir1/file.txt", "foo2"));

		final Git cloned = new Fork(parentFolder, SOURCE_GIT, TARGET_GIT, asList("master", "dev"),
				CredentialsProvider.getDefault(), null, null).execute();

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref > ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master", "refs/heads/dev");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommitTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommitTest.java
new file mode 100644
index 0000000000..e24fb93532
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommitTest.java
@@ 0,0 1,55 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitGetCommitTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();
	}

	@Test
	public void successTest() throws IOException {
		commit(git, MASTER_BRANCH, "Adding file", content("file.txt", "file content"));

		RevCommit lastCommit = git.getLastCommit(MASTER_BRANCH);

		RevCommit commit = git.getCommit(lastCommit.getName());

		assertThat(commit.getName()).isEqualTo(lastCommit.getName());
	}

	@Test
	public void notFoundTest() {
		RevCommit commit = git.getCommit("nonexistentcommitid");

		assertThat(commit).isNull();
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommonAncestorCommitTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommonAncestorCommitTest.java
new file mode 100644
index 0000000000..c58a89ca69
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitGetCommonAncestorCommitTest.java
@@ 0,0 1,64 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitGetCommonAncestorCommitTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();
	}

	@Test
	public void successTest() throws IOException {
		commit(git, MASTER_BRANCH, "Adding file", content("file.txt", "file content"));

		RevCommit expectedCommonAncestorCommit = git.getLastCommit(MASTER_BRANCH);

		new CreateBranch((GitImpl) git, MASTER_BRANCH, DEVELOP_BRANCH).execute();

		commit(git, MASTER_BRANCH, "Updating file", content("file.txt", "file content 1"));
		commit(git, MASTER_BRANCH, "Updating file", content("file.txt", "file content 2"));

		commit(git, DEVELOP_BRANCH, "Updating file", content("file.txt", "file content 3"));
		commit(git, DEVELOP_BRANCH, "Updating file", content("file.txt", "file content 4"));

		RevCommit actualCommonAncestorCommit = git.getCommonAncestorCommit(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(actualCommonAncestorCommit.getName()).isEqualTo(expectedCommonAncestorCommit.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidBranchTest() {
		git.getCommonAncestorCommit(MASTER_BRANCH, "invalidbranch");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitHistoryTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitHistoryTest.java
new file mode 100644
index 0000000000..233155978e
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitHistoryTest.java
@@ 0,0 1,170 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.ListCommits;
import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JGitHistoryTest extends AbstractTestInfra {

	private Git git;

	@Before
	public void setup() throws IOException {
		final File tmpDir = createTempDirectory();
		final File repoDir = new File(tmpDir, "testrepo.git");
		git = new CreateRepository(repoDir).execute()
				.orElseThrow(() > new IllegalStateException("Unable to create git repo for tests."));

		commit(git, "master", "create files", content("nonmoving.txt", multiline("a", "b", "c")),
				content("moving.txt", multiline("1", "2", "3")));
		moveCommit(singleMove("moving.txt", "moving1.txt"), "rename moving file");
		commit(git, "master", "change content, no moves", content("nonmoving.txt", multiline("a", "b", "d")),
				content("moving1.txt", multiline("1", "2", "4")));
		moveCommit(singleMove("moving1.txt", "dir/moving2.txt"), "move moving file to new dir");
		commit(git, "master", "simulate checkout old version", content("moving1.txt", multiline("1", "2", "4")));
	}

	private Map<String, String> singleMove(String from, String to) {
		Map<String, String> moves = new HashMap<>();
		moves.put(from, to);
		return moves;
	}

	private void moveCommit(Map<String, String> moves, String message) {
		git.commit("master", new CommitInfo(null, "name", "name@example.com", message, null, null), false, null,
				new MoveCommitContent(moves));
	}

	@Test
	public void listCommitsForUnmovedFile() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "nonmoving.txt").execute();
		assertEquals("nonmoving.txt", history.getTrackedFilePath());
		assertEquals(2, history.getCommits().size());
		assertEquals("/nonmoving.txt", history.trackedFileNameChangeFor(history.getCommits().get(0).getId()));
		assertEquals("/nonmoving.txt", history.trackedFileNameChangeFor(history.getCommits().get(1).getId()));
	}

	@Test
	public void listCommitsForMovedFile() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "dir/moving2.txt").execute();
		assertEquals("dir/moving2.txt", history.getTrackedFilePath());
		assertEquals(4, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("move moving file to new dir", commit0.getFullMessage());
		assertEquals("/dir/moving2.txt", oPath0);

		final RevCommit commit1 = history.getCommits().get(1);
		String oPath1 = history.trackedFileNameChangeFor(commit1.getId());
		assertEquals("change content, no moves", commit1.getFullMessage());
		assertEquals("/moving1.txt", oPath1);

		final RevCommit commit2 = history.getCommits().get(2);
		String oPath2 = history.trackedFileNameChangeFor(commit2.getId());
		assertEquals("rename moving file", commit2.getFullMessage());
		assertEquals("/moving1.txt", oPath2);

		final RevCommit commit3 = history.getCommits().get(3);
		String oPath3 = history.trackedFileNameChangeFor(commit3.getId());
		assertEquals("create files", commit3.getFullMessage());
		assertEquals("/moving.txt", oPath3);
	}

	@Test
	public void listCommitsForRestoredFile() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "moving1.txt").execute();
		assertEquals("moving1.txt", history.getTrackedFilePath());
		assertEquals(4, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version", commit0.getFullMessage());
		assertEquals("/moving1.txt", oPath0);

		final RevCommit commit1 = history.getCommits().get(1);
		String oPath1 = history.trackedFileNameChangeFor(commit1.getId());
		assertEquals("change content, no moves", commit1.getFullMessage());
		assertEquals("/moving1.txt", oPath1);

		final RevCommit commit2 = history.getCommits().get(2);
		String oPath2 = history.trackedFileNameChangeFor(commit2.getId());
		assertEquals("rename moving file", commit2.getFullMessage());
		assertEquals("/moving1.txt", oPath2);

		final RevCommit commit3 = history.getCommits().get(3);
		String oPath3 = history.trackedFileNameChangeFor(commit3.getId());
		assertEquals("create files", commit3.getFullMessage());
		assertEquals("/moving.txt", oPath3);
	}

	@Test
	public void listCommitsOnDirectory() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "dir").execute();
		assertEquals("dir", history.getTrackedFilePath());
		assertEquals(1, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("move moving file to new dir", commit0.getFullMessage());
		assertEquals("/dir", oPath0);
	}

	@Test
	public void listCommitsOnRootDirectoryViaAbsolute() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "/").execute();
		assertEquals("/", history.getTrackedFilePath());
		assertEquals(5, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version", commit0.getFullMessage());
		assertEquals("/", oPath0);
	}

	@Test
	public void listCommitsOnRootDirectoryViaNull() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), null).execute();
		assertEquals("/", history.getTrackedFilePath());
		assertEquals(5, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version", commit0.getFullMessage());
		assertEquals("/", oPath0);
	}

	@Test
	public void listCommitsOnRootDirectoryViaEmpty() throws Exception {
		final CommitHistory history = new ListCommits(git, git.getRef("master"), "").execute();
		assertEquals("/", history.getTrackedFilePath());
		assertEquals(5, history.getCommits().size());

		final RevCommit commit0 = history.getCommits().get(0);
		String oPath0 = history.trackedFileNameChangeFor(commit0.getId());
		assertEquals("simulate checkout old version", commit0.getFullMessage());
		assertEquals("/", oPath0);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMapDiffContentTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMapDiffContentTest.java
new file mode 100644
index 0000000000..3c22c7e987
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMapDiffContentTest.java
@@ 0,0 1,169 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitMapDiffContentTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";

	private static final List<String> TXT_FILES = Stream.of("file0", "file1", "file2", "file3", "file4")
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1", "Line2", "Line3", "Line4" };

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));
	}

	@Test
	public void testNoDiffOnlyOneCommit() throws IOException {
		Map<String, File> content = git.mapDiffContent(MASTER_BRANCH,
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(content).isEmpty();
	}

	@Test
	public void testHasContent() throws IOException {
		commit(git, MASTER_BRANCH, "Adding file into master",
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testHasContents() throws IOException {
		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
	}

	@Test
	public void testHasDeleteContents() throws IOException {
		new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(0), null);
					}
				}).execute();

		new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(1), null);
					}
				}).execute();

		Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(2);
		contents.values().forEach(v > assertThat(v).isNull());
	}

	@Test
	public void testWithManyCommitsOneFile() throws IOException {
		commit(git, MASTER_BRANCH, "Updating a file", content(TXT_FILES.get(0), "update 1"));

		commit(git, MASTER_BRANCH, "Updating a file", content(TXT_FILES.get(0), "update 2"));

		commit(git, MASTER_BRANCH, "Updating a file", content(TXT_FILES.get(0), "update 3"));

		Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH,
				git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(1);
	}

	@Test
	public void testWithMiddleCommits() throws IOException {
		commit(git, MASTER_BRANCH, "Updating a file", content(TXT_FILES.get(0), "update 1"));

		RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		new Commit(git, MASTER_BRANCH, "name", "name@example.com", "Removing file", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(1), null);
					}
				}).execute();

		RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git, MASTER_BRANCH, "Updating a file", content(TXT_FILES.get(0), "update 3"));

		Map<String, File> contents = git.mapDiffContent(MASTER_BRANCH, startCommit.getName(), endCommit.getName());

		assertThat(contents).isNotEmpty();
		assertThat(contents).hasSize(3);
	}

	@Test(expected = GitException.class)
	public void testWithWrongBranchName() throws IOException {
		git.mapDiffContent("wrongbranchname", git.getFirstCommit(git.getRef(MASTER_BRANCH)).getName(),
				git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
	}

	@Test(expected = GitException.class)
	public void testWithInvalidCommit() throws IOException {
		git.mapDiffContent(MASTER_BRANCH, "invalidcommitid", git.getLastCommit(git.getRef(MASTER_BRANCH)).getName());
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMergeTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMergeTest.java
new file mode 100644
index 0000000000..1fd71fa408
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMergeTest.java
@@ 0,0 1,297 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.commands.Merge;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitMergeTest extends AbstractTestInfra {

	private static final String SOURCE_GIT = "source/source";

	@Test
	public void testMergeFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop2", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop3", null, null, false,
				new HashMap<String, File>() {
					{
						put("file4.txt", tempFile("temp4"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop4", null, null, false,
				new HashMap<String, File>() {
					{
						put("file5.txt", tempFile("temp5"));
					}
				}).execute();

		new Merge(origin, "develop", "master").execute();

		final List<DiffEntry> result = new ListDiffs(origin, new GetTreeFromRef(origin, "master").execute(),
				new GetTreeFromRef(origin, "develop").execute()).execute();

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNonFastForwardSuccessful() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop2", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop3", null, null, false,
				new HashMap<String, File>() {
					{
						put("file4.txt", tempFile("temp4"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop4", null, null, false,
				new HashMap<String, File>() {
					{
						put("file5.txt", tempFile("temp5"));
					}
				}).execute();

		new Merge(origin, "develop", "master", true).execute();

		final List<DiffEntry> result = new ListDiffs(origin, new GetTreeFromRef(origin, "master").execute(),
				new GetTreeFromRef(origin, "develop").execute()).execute();

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeNoDiff() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1"));
					}
				}).execute();

		List<String> commitIds = new Merge(origin, "develop", "master").execute();

		assertThat(commitIds).isEmpty();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParametersNotNull() throws IOException {

		new Merge(null, "develop", "master").execute();
	}

	@Test(expected = GitException.class)
	public void testTryToMergeNonexistentBranch() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("temp1"));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop2", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop3", null, null, false,
				new HashMap<String, File>() {
					{
						put("file4.txt", tempFile("temp4"));
					}
				}).execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop4", null, null, false,
				new HashMap<String, File>() {
					{
						put("file5.txt", tempFile("temp5"));
					}
				}).execute();

		new Merge(origin, "develop", "nonexistent").execute();
	}

	@Test(expected = GitException.class)
	public void testMergeBinaryInformationButHasConflicts() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");
		final byte[] contentC = this.loadImage("images/opta.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.jpg", tempFile(contentA));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.jpg", tempFile(contentB));
					}
				}).execute();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.jpg", tempFile(contentC));
					}
				}).execute();

		new Merge(origin, "develop", "master").execute();

		final List<DiffEntry> result = new ListDiffs(origin, new GetTreeFromRef(origin, "master").execute(),
				new GetTreeFromRef(origin, "develop").execute()).execute();

		assertThat(result).isEmpty();
	}

	@Test
	public void testMergeBinaryInformationSuccessful() throws IOException {

		final byte[] contentA = this.loadImage("images/drools.png");
		final byte[] contentB = this.loadImage("images/jbpm.png");

		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, SOURCE_GIT  ".git");
		final Git origin = new CreateRepository(gitSource).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "master1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.jpg", tempFile(contentA));
					}
				}).execute();

		new CreateBranch((GitImpl) origin, "master", "develop").execute();

		new Commit(origin, "develop", "name", "name@example.com", "develop1", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.jpg", tempFile(contentB));
					}
				}).execute();

		new Merge(origin, "develop", "master").execute();

		final List<DiffEntry> result = new ListDiffs(origin, new GetTreeFromRef(origin, "master").execute(),
				new GetTreeFromRef(origin, "develop").execute()).execute();

		assertThat(result).isEmpty();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMirrorTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMirrorTest.java
new file mode 100644
index 0000000000..687c7615a2
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitMirrorTest.java
@@ 0,0 1,134 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Condition;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertFalse;

public class JGitMirrorTest extends AbstractTestInfra {

	public static final String TARGET_GIT = "test/target.git";
	public static final String ORIGIN = "https://github.com/uberfire/uberfirewebsite";
	private static Logger logger = LoggerFactory.getLogger(JGitMirrorTest.class);

	@Test
	public void testToHTTPMirrorSuccess() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();
		final File directory = new File(parentFolder, TARGET_GIT);
		new Clone(directory, ORIGIN, true, null, CredentialsProvider.getDefault(), null, null).execute();

		final Git cloned = Git.open(directory);

		assertThat(cloned).isNotNull();

		assertThat(cloned.getRepository().getAllRefs()).is(new Condition<Map<String, Ref>>() {
			@Override
			public boolean matches(final Map<String, Ref> refs) {
				final boolean hasMasterRef = refs.get("refs/heads/master") != null;
				final boolean hasNewWebsiteRef = refs.get("refs/heads/newwebsite") != null;
				final boolean hasRemoteOriginMaster = refs.get("refs/remotes/origin/master") != null;
				final boolean hasRemoteOriginNewWebSite = refs.get("refs/remotes/origin/master") != null;

				return hasMasterRef && hasNewWebsiteRef && hasRemoteOriginMaster & hasRemoteOriginNewWebSite;
			}
		});

		URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
		String remoteUrl = remoteUri.getScheme()  "://"  remoteUri.getHost()  remoteUri.getPath();
		assertThat(remoteUrl).isEqualTo(ORIGIN);
	}

	@Test
	public void testToHTTPUnmirrorSuccess() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();
		final File directory = new File(parentFolder, TARGET_GIT);
		new Clone(directory, ORIGIN, false, null, CredentialsProvider.getDefault(), null, null).execute();

		final Git cloned = Git.open(directory);

		assertThat(cloned).isNotNull();

		assertThat(cloned.getRepository().getAllRefs()).is(new Condition<Map<String, Ref>>() {
			@Override
			public boolean matches(final Map<String, Ref> refs) {
				final boolean hasMasterRef = refs.get("refs/heads/master") != null;
				final boolean hasNewWebsiteRef = refs.get("refs/heads/newwebsite") != null;
				final boolean hasRemoteOriginMaster = refs.get("refs/remotes/origin/master") != null;
				final boolean hasRemoteOriginNewWebSite = refs.get("refs/remotes/origin/master") != null;

				return hasMasterRef && hasNewWebsiteRef && hasRemoteOriginMaster & hasRemoteOriginNewWebSite;
			}
		});

		final boolean isMirror = cloned.getRepository().getConfig().getBoolean("remote", "origin", "mirror", false);
		assertFalse(isMirror);

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");

		URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
		String remoteUrl = remoteUri.getScheme()  "://"  remoteUri.getHost()  remoteUri.getPath();
		assertThat(remoteUrl).isEqualTo(ORIGIN);
	}

	@Test
	public void testEmptyCredentials() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();
		final File directory = new File(parentFolder, TARGET_GIT);
		new Clone(directory, ORIGIN, false, null, null, null, null).execute();

		final Git cloned = Git.open(directory);

		assertThat(cloned).isNotNull();

		assertThat(new ListRefs(cloned.getRepository()).execute()).is(new Condition<List<? extends Ref>>() {
			@Override
			public boolean matches(final List<? extends Ref> refs) {
				return refs.size() > 0;
			}
		});

		assertThat(new ListRefs(cloned.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");

		URIish remoteUri = cloned.remoteList().call().get(0).getURIs().get(0);
		String remoteUrl = remoteUri.getScheme()  "://"  remoteUri.getHost()  remoteUri.getPath();
		assertThat(remoteUrl).isEqualTo(ORIGIN);
	}

	@Test
	public void testBadUrl() throws IOException, GitAPIException {
		final File parentFolder = createTempDirectory();
		final File directory = new File(parentFolder, TARGET_GIT);
		try {
			new Clone(directory, ORIGIN  "sssss", false, null, CredentialsProvider.getDefault(), null, null).execute();
			fail("If got here the test is wrong because the ORIGIN does no exist");
		} catch (Clone.CloneException ex) {
			assertThat(ex).isNotNull();
			logger.info(ex.getMessage(), ex);
		}
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitPathTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitPathTest.java
new file mode 100644
index 0000000000..2176fa6ca1
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitPathTest.java
@@ 0,0 1,229 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

import org.eclipse.jgit.niofs.internal.util.EncodingUtil;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JGitPathTest {

	private final FileSystemProvider fsp = mock(FileSystemProvider.class);
	private final JGitFileSystem fs = mock(JGitFileSystem.class);

	@Before
	public void setup() {
		when(fs.getSeparator()).thenReturn("/");
		when(fs.provider()).thenReturn(fsp);
		when(fsp.getScheme()).thenReturn("git");
	}

	@Test
	public void testSimpleBranchedGit() {
		final Path path = JGitPathImpl.create(fs, "", "master@myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/");
		assertThat(path.getRoot()).isEqualTo(path);

		assertThat(path.getNameCount()).isEqualTo(0);

		assertThat(path.getRoot()).isEqualTo(path);
	}

	@Test
	public void testSimpleBranchedGitRoot() {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "/", "master@myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/");
		assertThat(path.getRoot().toString()).isEqualTo("/");

		assertThat(path.getNameCount()).isEqualTo(0);
	}

	@Test
	public void testSimpleBranchedGitRelative() {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "home", "master@myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isFalse();
		assertThat(path.toString()).isEqualTo("home");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/:home");
		assertThat(path.getRoot().toString()).isEqualTo("");

		assertThat(path.getNameCount()).isEqualTo(1);
	}

	@Test
	public void testSimpleBranchedGitRoot2() {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "/path/to/some/place.txt", "master@myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isEqualTo("path");
		assertThat(path.getRoot().toString()).isEqualTo("/");
	}

	@Test
	public void testSimpleBranchedGitRoot2Spaced() throws IllegalStateException {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, EncodingUtil.decode("/path/to/some/some%20place.txt"),
				"master@myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/some place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/path/to/some/some%20place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isEqualTo("path");
		assertThat(path.getRoot().toString()).isEqualTo("/");
	}

	@Test
	public void testSimpleImplicitBranchGit() {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "/path/to/some/place.txt", "myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/path/to/some/place.txt");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/path/to/some/place.txt");

		assertThat(path.getNameCount()).isEqualTo(4);

		assertThat(path.getName(0).toString()).isEqualTo("path");
		assertThat(path.getRoot().toString()).isEqualTo("/");
	}

	@Test
	public void testSimpleImplicitBranchGitRoot() {
		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "/", "myhost", false);

		assertThat(path).isNotNull();
		assertThat(path.isAbsolute()).isTrue();
		assertThat(path.toString()).isEqualTo("/");
		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThat(path.toUri().toString()).isEqualTo("git://master@myhost/");

		assertThat(path.getNameCount()).isEqualTo(0);

		assertThat(path.getRoot().toString()).isEqualTo("/");
		assertThatThrownBy(() > path.getName(0)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void testRelativize() {
		final FileSystemProvider fsprovider = mock(FileSystemProvider.class);
		when(fsprovider.getScheme()).thenReturn("file");
		when(fs.provider()).thenReturn(fsprovider);

		when(fs.getSeparator()).thenReturn("/");

		final Path path = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);
		final Path other = JGitPathImpl.create(fs, "/path/to/some/place", "master@myhost", false);

		final Path relative = path.relativize(other);
		assertThat(relative).isNotNull();
		assertThat(relative.toString()).isEqualTo("some/place");

		final Path path2 = JGitPathImpl.create(fs, "/path/to/some/place", "master@myhost", false);
		final Path other2 = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);

		final Path relative2 = path2.relativize(other2);
		assertThat(relative2).isNotNull();
		assertThat(relative2.toString()).isEqualTo("../..");

		final Path path3 = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);
		final Path other3 = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);

		final Path relative3 = path3.relativize(other3);
		assertThat(relative3).isNotNull();
		assertThat(relative3.toString()).isEqualTo("");

		final Path path4 = JGitPathImpl.create(fs, "path/to", "master@myhost", false);
		final Path other4 = JGitPathImpl.create(fs, "path/to/some/place", "master@myhost", false);

		final Path relative4 = path4.relativize(other4);
		assertThat(relative4).isNotNull();
		assertThat(relative4.toString()).isEqualTo("some/place");

		final Path path5 = JGitPathImpl.create(fs, "path/to", "master@myhost", false);
		final Path other5 = JGitPathImpl.create(fs, "some/place", "master@myhost", false);

		final Path relative5 = path5.relativize(other5);
		assertThat(relative5).isNotNull();
		assertThat(relative5.toString()).isEqualTo("../../some/place");

		final Path path6 = JGitPathImpl.create(fs, "some/place", "master@myhost", false);
		final Path other6 = JGitPathImpl.create(fs, "path/to", "master@myhost", false);

		final Path relative6 = path6.relativize(other6);
		assertThat(relative6).isNotNull();
		assertThat(relative6.toString()).isEqualTo("../../path/to");

		final Path path7 = JGitPathImpl.create(fs, "path/to/some/thing/here", "master@myhost", false);
		final Path other7 = JGitPathImpl.create(fs, "some/place", "master@myhost", false);

		final Path relative7 = path7.relativize(other7);
		assertThat(relative7).isNotNull();
		assertThat(relative7.toString()).isEqualTo("../../../../../some/place");

		final Path path8 = JGitPathImpl.create(fs, "some/place", "master@myhost", false);
		final Path other8 = JGitPathImpl.create(fs, "path/to/some/thing/here", "master@myhost", false);

		final Path relative8 = path8.relativize(other8);
		assertThat(relative8).isNotNull();
		assertThat(relative8.toString()).isEqualTo("../../path/to/some/thing/here");

		final Path path9 = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);
		final Path other9 = JGitPathImpl.create(fs, "/path/to", "master@myhost", false);

		final Path relative9 = path9.relativize(other9);
		assertThat(relative9).isNotNull();
		assertThat(relative9.toString()).isEqualTo("");

		final Path path10 = JGitPathImpl.create(fs, "path/to", "master@myhost", false);
		final Path other10 = JGitPathImpl.create(fs, "path/to", "master@myhost", false);

		final Path relative10 = path10.relativize(other10);
		assertThat(relative10).isNotNull();
		assertThat(relative10.toString()).isEqualTo("");
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitRevertMergeTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitRevertMergeTest.java
new file mode 100644
index 0000000000..c96347eee4
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitRevertMergeTest.java
@@ 0,0 1,125 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitRevertMergeTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	private static final List<String> TXT_FILES = Stream.of("file0", "file1", "file2", "file3", "file4")
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1", "Line2", "Line3", "Line4" };

	private String commonAncestorCommitId;

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));

		new CreateBranch((GitImpl) git, MASTER_BRANCH, DEVELOP_BRANCH).execute();

		commit(git, DEVELOP_BRANCH, "Adding files",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		commonAncestorCommitId = git.getCommonAncestorCommit(DEVELOP_BRANCH, MASTER_BRANCH).getName();
	}

	@Test(expected = GitException.class)
	public void testInvalidSourceBranch() throws IOException {
		String mergeCommitId = doMerge();

		git.revertMerge("invalidbranch", MASTER_BRANCH, commonAncestorCommitId, mergeCommitId);
	}

	@Test(expected = GitException.class)
	public void testInvalidTargetBranch() throws IOException {
		String mergeCommitId = doMerge();

		git.revertMerge(DEVELOP_BRANCH, "invalidbranch", commonAncestorCommitId, mergeCommitId);
	}

	@Test
	public void testRevertFailedMergeIsNotLastTargetCommit() throws IOException {
		String mergeCommitId = doMerge();

		commit(git, MASTER_BRANCH, "Updating file", content(TXT_FILES.get(0), "new content"));

		boolean result = git.revertMerge(DEVELOP_BRANCH, MASTER_BRANCH, commonAncestorCommitId, mergeCommitId);

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertFailedMergeParentTargetIsNotCommonAncestor() throws IOException {
		commit(git, MASTER_BRANCH, "Updating file", content(TXT_FILES.get(0), "new content"));

		String mergeCommitId = doMerge();

		boolean result = git.revertMerge(DEVELOP_BRANCH, MASTER_BRANCH, commonAncestorCommitId, mergeCommitId);

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertFailedMergeSourceParentIsNotLastSourceCommit() throws IOException {
		String mergeCommitId = doMerge();

		commit(git, DEVELOP_BRANCH, "Updating file", content(TXT_FILES.get(0), "new content"));

		boolean result = git.revertMerge(DEVELOP_BRANCH, MASTER_BRANCH, commonAncestorCommitId, mergeCommitId);

		assertThat(result).isFalse();
	}

	@Test
	public void testRevertSucceeded() throws IOException {
		String mergeCommitId = doMerge();

		boolean result = git.revertMerge(DEVELOP_BRANCH, MASTER_BRANCH, commonAncestorCommitId, mergeCommitId);

		assertThat(result).isTrue();
	}

	private String doMerge() throws IOException {
		git.merge(DEVELOP_BRANCH, MASTER_BRANCH, true);

		return git.getLastCommit(MASTER_BRANCH).getName();
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactoryTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactoryTest.java
new file mode 100644
index 0000000000..fe6bce69a8
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactoryTest.java
@@ 0,0 1,158 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.util.Collections;
import java.util.HashMap;

import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.Session;

import org.eclipse.jgit.niofs.internal.config.ConfigProperties;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SSH_OVER_HTTP;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SSH_OVER_HTTPS;
import static org.mockito.Mockito.mock;

public class JGitSSHConfigSessionFactoryTest {

	@Test
	public void testNoProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(Collections.emptyMap()));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				fail("no proxy should be set");
				return null;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class), mock(Session.class));
	}

	@Test
	public void testHttpProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String, String>() {
			{
				put(SSH_OVER_HTTP, "true");
				put("http.proxyHost", "somehost");
				put("http.proxyPort", "10");
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host", "somehost");
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port", 10);
				assertThat(proxy).hasFieldOrPropertyWithValue("user", null);
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd", null);
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class), mock(Session.class));
	}

	@Test
	public void testHttpProxyWithAuthentication() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String, String>() {
			{
				put(SSH_OVER_HTTP, "true");
				put("http.proxyHost", "somehost");
				put("http.proxyPort", "10");
				put("http.proxyUser", "user");
				put("http.proxyPassword", "passwd");
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host", "somehost");
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port", 10);
				assertThat(proxy).hasFieldOrPropertyWithValue("user", "user");
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd", "passwd");
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class), mock(Session.class));
	}

	@Test
	public void testHttpsProxy() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String, String>() {
			{
				put(SSH_OVER_HTTPS, "true");
				put("https.proxyHost", "somehost");
				put("https.proxyPort", "10");
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host", "somehost");
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port", 10);
				assertThat(proxy).hasFieldOrPropertyWithValue("user", null);
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd", null);
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class), mock(Session.class));
	}

	@Test
	public void testHttpsProxyWithAuthentication() {
		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {

		};
		config.load(new ConfigProperties(new HashMap<String, String>() {
			{
				put(SSH_OVER_HTTPS, "true");
				put("https.proxyHost", "somehost");
				put("https.proxyPort", "10");
				put("https.proxyUser", "user");
				put("https.proxyPassword", "passwd");
			}
		}));

		final JGitSSHConfigSessionFactory instance = new JGitSSHConfigSessionFactory(config) {
			@Override
			ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
				ProxyHTTP proxy = super.buildProxy(config);
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_host", "somehost");
				assertThat(proxy).hasFieldOrPropertyWithValue("proxy_port", 10);
				assertThat(proxy).hasFieldOrPropertyWithValue("user", "user");
				assertThat(proxy).hasFieldOrPropertyWithValue("passwd", "passwd");
				return proxy;
			}
		};
		instance.configure(mock(OpenSshConfig.Host.class), mock(Session.class));
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSquashingTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSquashingTest.java
new file mode 100644
index 0000000000..7411559d70
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSquashingTest.java
@@ 0,0 1,250 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
import org.eclipse.jgit.niofs.internal.op.commands.Squash;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.DIRECTORY;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.FILE;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.NOT_FOUND;

public class JGitSquashingTest extends AbstractTestInfra {

	private Logger logger = LoggerFactory.getLogger(JGitSquashingTest.class);

	static {
		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest", ""));
	}

	/*
	 * This test make 5 commits and then squah the last 4 into a single commit
	 */
	@Test
	public void testSquash4Of5Commits() throws IOException, GitAPIException {

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Forlder for the Test: "  parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder, "mylocalrepo.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 1!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file1.txt", tempFile("initial content file 1"));
					}
				}).execute();
		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 2!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("initial content file 2"));
					}
				}).execute();
		Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
		RevCommit secondCommit = logs.iterator().next();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 3!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file1.txt", tempFile("new content file 1"));
					}
				}).execute();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 4!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("new content file 2"));
					}
				}).execute();
		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 5!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file3.txt", tempFile("initial content file 3"));
					}
				}).execute();
		logs = ((GitImpl) origin)._log().all().call();
		int commitsCount = 0;
		for (RevCommit commit : logs) {
			logger.info(">>> Origin Commit: "  commit.getFullMessage()  "  "  commit.toString());
			commitsCount;
		}
		assertThat(commitsCount).isEqualTo(5);

		assertThat(origin.getPathInfo("master", "pathx/").getPathType()).isEqualTo(NOT_FOUND);
		assertThat(origin.getPathInfo("master", "path/to/file1.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/to/file2.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/to/file3.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/to").getPathType()).isEqualTo(DIRECTORY);

		logger.info("Squashing from "  secondCommit.getName()  "  to HEAD");
		new Squash((GitImpl) origin, "master", secondCommit.getName(), "squashed message").execute();

		commitsCount = 0;
		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Final Commit: "  commit.getFullMessage()  "  "  commit.toString());
			commitsCount;
		}
		assertThat(commitsCount).isEqualTo(2);
	}

	@Test
	public void testFailWhenTryToSquashCommitsFromDifferentBranches() throws IOException, GitAPIException {

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Forlder for the Test: "  parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder, "mylocalrepo.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "aparedes", "aparedes@example.com", "commit 1!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file1.txt", tempFile("initial content file 1"));
					}
				}).execute();
		new Commit(origin, "develop", "salaboy", "salaboy@example.com", "commit 2!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("initial content file 2"));
					}
				}).execute();
		new Commit(origin, "master", "aparedes", "aparedes@example.com", "commit 3!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file3.txt", tempFile("initial content file 1"));
					}
				}).execute();
		new Commit(origin, "master", "aparedes", "aparedes@example.com", "commit 4!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file4.txt", tempFile("initial content file 1"));
					}
				}).execute();

		List<RevCommit> masterCommits = getCommitsFromBranch((GitImpl) origin, "master");
		List<RevCommit> developCommits = getCommitsFromBranch((GitImpl) origin, "develop");

		assertThat(masterCommits.size()).isEqualTo(3);
		assertThat(developCommits.size()).isEqualTo(1);

		try {
			new Squash((GitImpl) origin, "master", developCommits.get(0).getName(), "squashed message").execute();
			fail("If it reaches here the test has failed because he found the commit into the branch");
		} catch (GitException e) {
			logger.info(e.getMessage());
			assertThat(e).isNotNull();
		}
	}

	private List<RevCommit> getCommitsFromBranch(final GitImpl origin, String branch)
			throws GitAPIException, MissingObjectException, IncorrectObjectTypeException {
		List<RevCommit> commits = new ArrayList<>();
		final ObjectId id = new GetRef(origin.getRepository(), branch).execute().getObjectId();
		for (RevCommit commit : origin._log().add(id).call()) {
			logger.info(">>> "  branch  " Commits: "  commit.getFullMessage()  "  "  commit.toString());
			commits.add(commit);
		}
		return commits;
	}

	/*
	 * This test also perform 5 commits and squash the last 4 into a single commit
	 * but now the changes are in different paths
	 */
	@Test
	public void testSquashCommitsWithDifferentPaths() throws IOException, GitAPIException {

		final File parentFolder = createTempDirectory();
		logger.info(">> Parent Folder for the Test: "  parentFolder.getAbsolutePath());
		final File gitFolder = new File(parentFolder, "mylocalrepo.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 1!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("initial content file 1"));
					}
				}).execute();
		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 2!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("initial content file 2"));
					}
				}).execute();
		Iterable<RevCommit> logs = ((GitImpl) origin)._log().setMaxCount(1).all().call();
		RevCommit secondCommit = logs.iterator().next();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 3!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file1.txt", tempFile("new content file 1"));
					}
				}).execute();

		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 4!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("new content file 2"));
					}
				}).execute();
		new Commit(origin, "master", "salaboy", "salaboy@example.com", "commit 5!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/file3.txt", tempFile("initial content file 3"));
					}
				}).execute();

		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Origin Commit: "  commit.getFullMessage()  "  "  commit.toString());
		}

		assertThat(origin.getPathInfo("master", "pathx/").getPathType()).isEqualTo(NOT_FOUND);
		assertThat(origin.getPathInfo("master", "file1.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/to/file2.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/file3.txt").getPathType()).isEqualTo(FILE);
		assertThat(origin.getPathInfo("master", "path/to").getPathType()).isEqualTo(DIRECTORY);

		logger.info("Squashing from "  secondCommit.getName()  "  to HEAD");
		new Squash((GitImpl) origin, "master", secondCommit.getName(), "squashed message").execute();

		int commitsCount = 0;
		for (RevCommit commit : ((GitImpl) origin)._log().all().call()) {
			logger.info(">>> Final Commit: "  commit.getFullMessage()  "  "  commit.toString());
			commitsCount;
		}

		assertThat(commitsCount).isEqualTo(2);
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSubdirectoryCloneTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSubdirectoryCloneTest.java
new file mode 100644
index 0000000000..571c46ae27
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitSubdirectoryCloneTest.java
@@ 0,0 1,345 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.hooks.PostCommitHook;
import org.eclipse.jgit.hooks.PreCommitHook;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.SubdirectoryClone;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class JGitSubdirectoryCloneTest extends AbstractTestInfra {

	private static final String TARGET_GIT = "target/target", SOURCE_GIT = "source/source";

	@Test
	public void cloneSubdirectorySingleBranch() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder, SOURCE_GIT  ".git");

		final File targetDir = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = gitRepo(sourceDir);
		commit(origin, "master", "first", content("dir1/file.txt", "foo"));
		commit(origin, "master", "second", content("dir2/file2.txt", "bar"));
		commit(origin, "master", "third", content("file3.txt", "moogah"));

		final Git cloned = new SubdirectoryClone(targetDir, sourceDir.getAbsoluteFile().toURI().toString(), "dir1",
				singletonList("master"), CredentialsProvider.getDefault(), null, null).execute();

		assertThat(origin.getRepository().getRemoteNames()).isEmpty();

		assertThat(cloned).isNotNull();
		assertThat(listRefs(cloned)).hasSize(1);

		final List<RevCommit> cloneCommits = getCommits(cloned, "master");
		assertThat(cloneCommits).hasSize(1);

		final RevCommit clonedCommit = cloneCommits.get(0);
		final RevCommit originCommit = getCommits(origin, "master").get(2); // Ordered children first

		assertClonedCommitData(origin, "dir1", clonedCommit, originCommit);
	}

	@Test
	public void cloneSubdirectoryMultipleBranches() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder, SOURCE_GIT  ".git");

		final File targetDir = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = gitRepo(sourceDir);
		commit(origin, "master", "first", content("dir1/file.txt", "foo"), content("dir2/file2.txt", "bar"),
				content("file3.txt", "moogah"));

		branch(origin, "master", "dev");
		commit(origin, "dev", "second", content("dir1/file.txt", "foo1"), content("file3.txt", "bar1"));

		branch(origin, "master", "ignored");
		commit(origin, "ignored", "third", content("dir1/file.txt", "foo2"));

		final Git cloned = new SubdirectoryClone(targetDir, sourceDir.getAbsoluteFile().toURI().toString(), "dir1",
				asList("master", "dev"), CredentialsProvider.getDefault(), null, null).execute();

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref > ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master", "refs/heads/dev");

		// Check master commits
		{
			final List<RevCommit> cloneCommits = getCommits(cloned, "master");
			assertThat(cloneCommits).hasSize(1);
			assertClonedCommitData(origin, "dir1", cloneCommits.get(0), getCommits(origin, "master").get(0));
		}

		// Check dev commits
		{
			final List<RevCommit> cloneCommits = getCommits(cloned, "dev");
			assertThat(cloneCommits).hasSize(2);

			final List<RevCommit> originCommits = getCommits(origin, "dev");
			assertClonedCommitData(origin, "dir1", cloneCommits.get(0), originCommits.get(0));
			assertClonedCommitData(origin, "dir1", cloneCommits.get(1), originCommits.get(1));
		}
	}

	@Test
	public void cloneSubdirectoryWithMergeCommit() throws Exception {
		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder, SOURCE_GIT  ".git");

		final File targetDir = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = gitRepo(sourceDir);
		commit(origin, "master", "first", content("dir1/file.txt", "foo"), content("dir2/file2.txt", "bar"),
				content("file3.txt", "moogah"));

		branch(origin, "master", "dev");
		commit(origin, "dev", "second", content("dir1/file.txt", "foo1"), content("file3.txt", "bar1"));

		commit(origin, "master", "another", content("dir1/file2.txt", "blah"));

		mergeCommit(origin, "master", "dev", content("dir1/file.txt", "merged value!"),
				content("dir2/file2.txt", "merged value!"), content("file3.txt", "merged value!"));

		final Git cloned = new SubdirectoryClone(targetDir, sourceDir.getAbsoluteFile().toURI().toString(), "dir1",
				asList("master", "dev"), CredentialsProvider.getDefault(), null, null).execute();

		assertThat(cloned).isNotNull();
		final Set<String> clonedRefs = listRefs(cloned).stream().map(ref > ref.getName()).collect(toSet());
		assertThat(clonedRefs).hasSize(2);
		assertThat(clonedRefs).containsExactly("refs/heads/master", "refs/heads/dev");

		// Check master commits
		{
			final List<RevCommit> cloneCommits = getCommits(cloned, "master");
			assertThat(cloneCommits).hasSize(4); // 2 on master  1 on dev  1 merge commit

			final List<RevCommit> originCommits = getCommits(origin, "master");
			assertClonedCommitData(origin, "dir1", cloneCommits.get(0), originCommits.get(0));
			assertClonedCommitData(origin, "dir1", cloneCommits.get(1), originCommits.get(1));
			assertClonedCommitData(origin, "dir1", cloneCommits.get(2), originCommits.get(2));
			assertClonedCommitData(origin, "dir1", cloneCommits.get(3), originCommits.get(3));

			// Check that we preserved the topology of commits.
			assertThat(cloneCommits.get(0).getParentCount()).isEqualTo(2);
			assertThat(cloneCommits.get(1).getParentCount()).isEqualTo(1);
			assertThat(cloneCommits.get(2).getParentCount()).isEqualTo(1);
			assertThat(cloneCommits.get(3).getParentCount()).isEqualTo(0);
		}

		// Check dev commits
		{
			final List<RevCommit> cloneCommits = getCommits(cloned, "dev");
			assertThat(cloneCommits).hasSize(2);

			final List<RevCommit> originCommits = getCommits(origin, "dev");
			assertClonedCommitData(origin, "dir1", cloneCommits.get(0), originCommits.get(0));
			assertClonedCommitData(origin, "dir1", cloneCommits.get(1), originCommits.get(1));
		}
	}

	@Test
	public void cloneSubdirectoryWithHookDir() throws Exception {
		final File hooksDir = createTempDirectory();

		writeMockHook(hooksDir, PostCommitHook.NAME);
		writeMockHook(hooksDir, PreCommitHook.NAME);

		final File parentFolder = createTempDirectory();

		final File sourceDir = new File(parentFolder, SOURCE_GIT  ".git");

		final File targetDir = new File(parentFolder, TARGET_GIT  ".git");

		final Git origin = gitRepo(sourceDir);
		commit(origin, "master", "first", content("dir1/file.txt", "foo"));
		commit(origin, "master", "second", content("dir2/file2.txt", "bar"));
		commit(origin, "master", "third", content("file3.txt", "moogah"));

		final Git cloned = new SubdirectoryClone(targetDir, sourceDir.getAbsoluteFile().toURI().toString(), "dir1",
				singletonList("master"), CredentialsProvider.getDefault(), null, hooksDir).execute();

		assertThat(origin.getRepository().getRemoteNames()).isEmpty();

		assertThat(cloned).isNotNull();
		assertThat(listRefs(cloned)).hasSize(1);

		final List<RevCommit> cloneCommits = getCommits(cloned, "master");
		assertThat(cloneCommits).hasSize(1);

		final RevCommit clonedCommit = cloneCommits.get(0);
		final RevCommit originCommit = getCommits(origin, "master").get(2); // Ordered children first

		assertClonedCommitData(origin, "dir1", clonedCommit, originCommit);

		boolean foundPreCommitHook = false;
		boolean foundPostCommitHook = false;
		File[] hooks = new File(cloned.getRepository().getDirectory(), "hooks").listFiles();
		assertThat(hooks).isNotEmpty().isNotNull();
		assertThat(hooks.length).isEqualTo(2);
		for (File hook : hooks) {
			if (hook.getName().equals(PreCommitHook.NAME)) {
				foundPreCommitHook = hook.canExecute();
			} else if (hook.getName().equals(PostCommitHook.NAME)) {
				foundPostCommitHook = hook.canExecute();
			}
		}
		assertThat(foundPreCommitHook).isTrue();
		assertThat(foundPostCommitHook).isTrue();
	}

	private void assertClonedCommitData(final Git origin, final String subdirectory, final RevCommit clonedCommit,
			final RevCommit originCommit) throws Exception {
		assertThat(clonedCommit.getFullMessage()).isEqualTo(originCommit.getFullMessage());

		final PersonIdent authorIdent = clonedCommit.getAuthorIdent();
		final PersonIdent commiterIdent = clonedCommit.getCommitterIdent();
		assertThat(authorIdent).isEqualTo(commiterIdent);
		assertThat(authorIdent.getName()).isEqualTo("name");
		assertThat(authorIdent.getEmailAddress()).isEqualTo("name@example.com");

		final ObjectId originDirId = findIdForPath(origin, originCommit, subdirectory);
		final ObjectId clonedTreeId = clonedCommit.getTree().getId();
		assertThat(clonedTreeId).isEqualTo(originDirId);
	}

	private ObjectId findIdForPath(final Git origin, final RevCommit originMasterTip, final String searchPath)
			throws Exception {
		try (TreeWalk treeWalk = new TreeWalk(origin.getRepository())) {
			final int treeId = treeWalk.addTree(originMasterTip.getTree());
			treeWalk.setRecursive(false);
			final CanonicalTreeParser treeParser = treeWalk.getTree(treeId, CanonicalTreeParser.class);
			while (treeWalk.next()) {
				final String path = treeParser.getEntryPathString();
				if (path.equals(searchPath)) {
					return treeParser.getEntryObjectId();
				}
			}
		}

		throw new AssertionError(
				String.format("Could not find path [%s] in commit [%s].", searchPath, originMasterTip.name()));
	}

	private List<RevCommit> getCommits(final Git git, String branch) throws Exception {
		List<RevCommit> commits = new ArrayList<>();
		try (RevWalk revWalk = new RevWalk(git.getRepository())) {
			final RevCommit branchTip = revWalk.parseCommit(git.getRepository().resolve(branch));
			revWalk.markStart(branchTip);
			revWalk.sort(RevSort.TOPO);
			final Iterator<RevCommit> iter = revWalk.iterator();
			while (iter.hasNext()) {
				commits.add(iter.next());
			}
		}
		return commits;
	}

	private Git gitRepo(File gitSource) throws IOException {
		return new CreateRepository(gitSource).execute().get();
	}

	/*
	 * Unfortunately there is no easier way to write a commit with multiple parents.
	 */
	private void mergeCommit(final Git origin, final String targetBranchName, final String sourceBranchName,
			final TestFile... testFiles) throws Exception {
		final Repository repo = origin.getRepository();
		final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repo);

		final ObjectId targetId = repo.resolve(targetBranchName);
		final ObjectId sourceId = repo.resolve(sourceBranchName);

		final DirCache dc = DirCache.newInCore();
		final DirCacheEditor editor = dc.editor();

		try (ObjectInserter inserter = repo.newObjectInserter()) {
			final ObjectId treeId = writeTestFilesToTree(dc, editor, inserter, testFiles);
			final ObjectId commitId = writeCommit(inserter, treeId, targetId, sourceId);
			updateBranch(targetBranchName, git, commitId);
		}
	}

	private void updateBranch(final String targetBranchName, final org.eclipse.jgit.api.Git git,
			final ObjectId commitId) throws Exception {
		git.branchCreate().setName(targetBranchName).setStartPoint(commitId.name()).setForce(true).call();
	}

	private ObjectId writeCommit(final ObjectInserter inserter, final ObjectId commitTreeId,
			final ObjectId... parentIds) throws IOException {
		final CommitBuilder builder = new CommitBuilder();
		builder.setAuthor(new PersonIdent("name", "name@example.com"));
		builder.setCommitter(new PersonIdent("name", "name@example.com"));
		builder.setTreeId(commitTreeId);
		builder.setMessage("merge commit");
		builder.setParentIds(parentIds);

		final ObjectId commitId = inserter.insert(builder);
		return commitId;
	}

	private ObjectId writeTestFilesToTree(final DirCache dc, final DirCacheEditor editor, ObjectInserter inserter,
			final TestFile... testFiles) throws Exception {
		for (TestFile data : testFiles) {
			writeBlob(editor, inserter, data);
		}
		editor.finish();
		final ObjectId commitTreeId = dc.writeTree(inserter);
		return commitTreeId;
	}

	private void writeBlob(final DirCacheEditor editor, ObjectInserter inserter, TestFile data) throws IOException {
		final ObjectId blobId = inserter.insert(Constants.OBJ_BLOB, data.content.length(),
				IOUtils.toInputStream(data.content, "UTF8"));
		editor.add(new PathEdit(data.path) {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setObjectId(blobId);
			}
		});
	}
}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitTextualDiffBranchesTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitTextualDiffBranchesTest.java
new file mode 100644
index 0000000000..788e522cb0
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitTextualDiffBranchesTest.java
@@ 0,0 1,191 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JGitTextualDiffBranchesTest extends AbstractTestInfra {

	private Git git;

	private static final String MASTER_BRANCH = "master";
	private static final String DEVELOP_BRANCH = "develop";

	private static final List<String> TXT_FILES = Stream.of("file0", "file1", "file2", "file3", "file4")
			.collect(Collectors.toList());

	private static final String[] COMMON_TXT_LINES = { "Line1", "Line2", "Line3", "Line4" };

	@Before
	public void setup() throws IOException {
		final File parentFolder = createTempDirectory();

		final File gitSource = new File(parentFolder, "source/source.git");

		git = new CreateRepository(gitSource).execute().get();

		commit(git, MASTER_BRANCH, "Adding files into master",
				content(TXT_FILES.get(0), multiline(TXT_FILES.get(0), COMMON_TXT_LINES)),
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), COMMON_TXT_LINES)),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), COMMON_TXT_LINES)));

		new CreateBranch((GitImpl) git, MASTER_BRANCH, DEVELOP_BRANCH).execute();
	}

	@Test
	public void testDiffWithAddedFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Adding files",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);

		diffs.forEach(diff > {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
			assertThat(diff.getDiffText()).isNotEmpty();
		});

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(TXT_FILES.get(3));
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(4);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(0);
	}

	@Test
	public void testDiffWithAddedFilesSameBranch() throws IOException {
		RevCommit startCommit = git.getLastCommit(MASTER_BRANCH);

		commit(git, MASTER_BRANCH, "Adding files",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)),
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		RevCommit endCommit = git.getLastCommit(MASTER_BRANCH);

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, MASTER_BRANCH, startCommit.getName(),
				endCommit.getName());

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);
	}

	@Test
	public void testDiffWithAddedFileAndTargetUpdatedLater() throws IOException {
		commit(git, DEVELOP_BRANCH, "Adding file",
				content(TXT_FILES.get(3), multiline(TXT_FILES.get(3), COMMON_TXT_LINES)));

		commit(git, MASTER_BRANCH, "Adding file",
				content(TXT_FILES.get(4), multiline(TXT_FILES.get(4), COMMON_TXT_LINES)));

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(1);

		assertThat(diffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.ADD.toString());
		assertThat(diffs.get(0).getDiffText()).isNotEmpty();

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(TXT_FILES.get(3));
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(4);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(0);
	}

	@Test
	public void testDiffWithRemovedFile() {
		new Commit(git, DEVELOP_BRANCH, "name", "name@example.com", "Removing file", null, null, false,
				new HashMap<String, File>() {
					{
						put(TXT_FILES.get(0), null);
					}
				}).execute();

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(1);

		assertThat(diffs.get(0).getChangeType()).isEqualTo(DiffEntry.ChangeType.DELETE.toString());
		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(0));
		assertThat(diffs.get(0).getNewFilePath()).isEqualTo(DiffEntry.DEV_NULL);
		assertThat(diffs.get(0).getLinesAdded()).isEqualTo(0);
		assertThat(diffs.get(0).getLinesDeleted()).isEqualTo(4);
		assertThat(diffs.get(0).getDiffText()).isNotEmpty();
	}

	@Test
	public void testDiffWithUpdatedFiles() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating files",
				content(TXT_FILES.get(1), multiline(TXT_FILES.get(1), "Line1", "Line2Changed", "Line3", "Line4")),
				content(TXT_FILES.get(2), multiline(TXT_FILES.get(2), "Line1", "Line2Changed", "Line3", "Line4")));

		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(diffs).isNotEmpty();
		assertThat(diffs).hasSize(2);

		diffs.forEach(diff > {
			assertThat(diff.getChangeType()).isEqualTo(DiffEntry.ChangeType.MODIFY.toString());
			assertThat(diff.getOldFilePath()).isEqualTo(diff.getNewFilePath());
			assertThat(diff.getLinesAdded()).isEqualTo(1);
			assertThat(diff.getLinesDeleted()).isEqualTo(1);
			assertThat(diff.getDiffText()).isNotEmpty();
		});

		assertThat(diffs.get(0).getOldFilePath()).isEqualTo(TXT_FILES.get(1));
		assertThat(diffs.get(1).getOldFilePath()).isEqualTo(TXT_FILES.get(2));
	}

	@Test
	public void testDiffWithUpdateFirstAndLastLines() throws IOException {
		commit(git, DEVELOP_BRANCH, "Updating file", content(TXT_FILES.get(1),
				multiline(TXT_FILES.get(1), "Line1Changed", "Line2", "Line3", "Line4Changed")));

		List<TextualDiff> fileDiffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(fileDiffs).isNotEmpty();
		assertThat(fileDiffs).hasSize(1);
	}

	@Test
	public void testDiffWithEvenBranches() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, DEVELOP_BRANCH);

		assertThat(diffs).isEmpty();
	}

	@Test(expected = GitException.class)
	public void testDiffWithNonExistentBranch() {
		List<TextualDiff> diffs = git.textualDiffRefs(MASTER_BRANCH, "nonExistentBranch");

		assertThat(diffs).isEmpty();
	}
}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitUtilTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitUtilTest.java
new file mode 100644
index 0000000000..f8e956d6e3
 /dev/null
 b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/JGitUtilTest.java
@@ 0,0 1,242 @@
/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 * https://www.eclipse.org/org/documents/edlv10.php.
 *
 * SPDXLicenseIdentifier: BSD3Clause
 */
package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;

import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.DIRECTORY;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.FILE;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JGitUtilTest extends AbstractTestInfra {

	@Test
	public void testNewRepo() throws IOException {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git git = new CreateRepository(gitFolder).execute().get();

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(0);

		new Commit(git, "master", "name", "name@example.com", "commit", null, null, false, new HashMap<String, File>() {
			{
				put("file.txt", tempFile("temp"));
			}
		}).execute();

		assertThat(new ListRefs(git.getRepository()).execute().size()).isEqualTo(1);
	}

	@Test
	public void testClone() throws IOException, InvalidRemoteException {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("file2.txt", tempFile("temp2222"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file.txt", tempFile("temp"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit", null, null, false,
				new HashMap<String, File>() {
					{
						put("file3.txt", tempFile("temp3"));
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder, "myclone.git");

		final Git git = new Clone(gitClonedFolder, origin.getRepository().getDirectory().toString(), false, null,
				CredentialsProvider.getDefault(), null, null).execute().get();

		assertThat(git).isNotNull();

		assertThat(new ListRefs(git.getRepository()).execute()).hasSize(2);

		assertThat(new ListRefs(git.getRepository()).execute().get(0).getName()).isEqualTo("refs/heads/master");
		assertThat(new ListRefs(git.getRepository()).execute().get(1).getName()).isEqualTo("refs/heads/user_branch");
	}

	@Test
	public void testPathResolve() throws IOException, InvalidRemoteException {
		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("temp2222"));
					}
				}).execute();
		new Commit(origin, "user_branch", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file3.txt", tempFile("temp2222"));
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder, "myclone.git");

		final Git git = new Clone(gitClonedFolder, origin.getRepository().getDirectory().toString(), false, null,
				CredentialsProvider.getDefault(), null, null).execute().get();

		assertThat(git.getPathInfo("user_branch", "pathx/").getPathType()).isEqualTo(NOT_FOUND);
		assertThat(git.getPathInfo("user_branch", "path/to/file2.txt").getPathType()).isEqualTo(FILE);
		assertThat(git.getPathInfo("user_branch", "path/to").getPathType()).isEqualTo(DIRECTORY);
	}

	@Test
	public void testAmend() throws IOException, InvalidRemoteException {
		final File parentFolder = createTempDirectory();
		System.out.println("COOL!:"  parentFolder.toString());
		final File gitFolder = new File(parentFolder, "myxxxtest.git");

		final Git origin = new CreateRepository(gitFolder).execute().get();

		new Commit(origin, "master", "name", "name@example.com", "commit!", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("tempwdf sdf asdf asd2222"));
					}
				}).execute();
		new Commit(origin, "master", "name", "name@example.com", "commit!", null, null, true,
				new HashMap<String, File>() {
					{
						put("path/to/file3.txt", tempFile("temp2x d dasdf asdf 222"));
					}
				}).execute();

		final File gitClonedFolder = new File(parentFolder, "myclone.git");

		final Git git = new Clone(gitClonedFolder, origin.getRepository().getDirectory().toString(), false, null,
				CredentialsProvider.getDefault(), null, null).execute().get();

		assertThat(git.getPathInfo("master", "pathx/").getPathType()).isEqualTo(NOT_FOUND);
		assertThat(git.getPathInfo("master", "path/to/file2.txt").getPathType()).isEqualTo(FILE);
		assertThat(git.getPathInfo("master", "path/to").getPathType()).isEqualTo(DIRECTORY);
	}

	@Test
	public void testBuildVersionAttributes() throws Exception {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git git = new CreateRepository(gitFolder).execute().get();

		new Commit(git, "master", "name", "name@example.com", "commit 1", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("who"));
					}
				}).execute();
		new Commit(git, "master", "name", "name@example.com", "commit 2", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("you"));
					}
				}).execute();
		new Commit(git, "master", "name", "name@example.com", "commit 3", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("gonna"));
					}
				}).execute();
		new Commit(git, "master", "name", "name@example.com", "commit 4", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file2.txt", tempFile("call?"));
					}
				}).execute();

		JGitFileSystem jGitFileSystem = mock(JGitFileSystem.class);
		when(jGitFileSystem.getGit()).thenReturn(git);

		final JGitPathImpl path = mock(JGitPathImpl.class);
		when(path.getFileSystem()).thenReturn(jGitFileSystem);
		when(path.getRefTree()).thenReturn("master");
		when(path.getPath()).thenReturn("path/to/file2.txt");

		final VersionAttributes versionAttributes = new JGitVersionAttributeViewImpl(path).readAttributes();

		List<VersionRecord> records = versionAttributes.history().records();
		assertEquals("commit 1", records.get(0).comment());
		assertEquals("commit 2", records.get(1).comment());
		assertEquals("commit 3", records.get(2).comment());
		assertEquals("commit 4", records.get(3).comment());
	}

	@Test
	public void testDiffForFileCreatedInEmptyRepositoryOrBranch() throws Exception {

		final File parentFolder = createTempDirectory();
		final File gitFolder = new File(parentFolder, "mytest.git");

		final Git git = new CreateRepository(gitFolder).execute().get();

		final ObjectId oldHead = new GetTreeFromRef(git, "master").execute();

		new Commit(git, "master", "name", "name@example.com", "commit 1", null, null, false,
				new HashMap<String, File>() {
					{
						put("path/to/file.txt", tempFile("who"));
					}
				}).execute();

		final ObjectId newHead = new GetTreeFromRef(git, "master").execute();

		List<DiffEntry> diff = new ListDiffs(git, oldHead, newHead).execute();
		assertNotNull(diff);
		assertFalse(diff.isEmpty());
		assertEquals(ChangeType.ADD, diff.get(0).getChangeType());
		assertEquals("path/to/file.txt", diff.get(0).getNewPath());
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/NewProviderDefineDirTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/NewProviderDefineDirTest.java
new file mode 100644
index 0000000000..f8dd5bf233
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/NewProviderDefineDirTest.java
@@ 0,0 +1,84 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.io.IOException;
+import java.net.URI;
+import java.util.Arrays;
+import java.util.Collection;
+import java.util.Map;
+
+import org.junit.Test;
+import org.junit.runner.RunWith;
+import org.junit.runners.Parameterized;
+
+import static org.assertj.core.api.Assertions.assertThat;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR_NAME;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.REPOSITORIES_CONTAINER_DIR;
+
+@RunWith(Parameterized.class)
+public class NewProviderDefineDirTest extends AbstractTestInfra {
+
+	private String dirPathName;
+	private File tempDir;
+
+	public NewProviderDefineDirTest(final String dirPathName) {
+		this.dirPathName = dirPathName;
+	}
+
+	@Parameterized.Parameters(name = "{index}: dir name: {0}")
+	public static Collection<Object[]> data() {
+		return Arrays.asList(new Object[][] { { REPOSITORIES_CONTAINER_DIR }, { ".tempgit" } });
+	}
+
+	@Override
+	public Map<String, String> getGitPreferences() {
+		try {
+			tempDir = createTempDirectory();
+		} catch (Exception ex) {
+			throw new RuntimeException(ex);
+		}
+		Map<String, String> gitPrefs = super.getGitPreferences();
+		gitPrefs.put(GIT_NIO_DIR, tempDir.toString());
+		if (!REPOSITORIES_CONTAINER_DIR.equals(dirPathName)) {
+			gitPrefs.put(GIT_NIO_DIR_NAME, dirPathName);
+		}
+		return gitPrefs;
+	}
+
+	@Test
+	public void testUsingProvidedPath() throws IOException {
+		final URI newRepo = URI.create("git://reponame");
+
+		JGitFileSystemProxy fileSystem = (JGitFileSystemProxy) provider.newFileSystem(newRepo, EMPTY_ENV);
+
+		// no infra created due to lazy loading nature of our FS
+		String[] names = tempDir.list();
+
+		assertThat(names).isEmpty();
+
+		String[] repos = new File(tempDir, dirPathName).list();
+
+		assertThat(repos).isNull();
+
+		// FS created
+		fileSystem.getRealJGitFileSystem();
+
+		names = tempDir.list();
+
+		assertThat(names).contains(dirPathName);
+
+		repos = new File(tempDir, dirPathName).list();
+
+		assertThat(repos).contains("reponame.git");
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/URITest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/URITest.java
new file mode 100644
index 0000000000..3b195befc2
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/URITest.java
@@ 0,0 +1,62 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.net.URI;
+import java.net.URISyntaxException;
+
+import org.junit.Test;
+
+import static org.assertj.core.api.Assertions.assertThat;
+
+public class URITest {
+
+	@Test
+	public void testURI() throws URISyntaxException {
+		final URI uri = new URI("git://branch@reponame/path/to/file.txt");
+
+		assertThat(uri.getScheme()).isEqualTo("git");
+		assertThat(uri.getAuthority()).isEqualTo("branch@reponame");
+		assertThat(uri.getPath()).isEqualTo("/path/to/file.txt");
+		assertThat(uri.getQuery()).isNull();
+
+		final URI uri2 = new URI("git://reponame");
+		assertThat(uri2).isNotNull();
+		assertThat(uri2.getAuthority()).isEqualTo("reponame");
+
+		final URI uri3 = URI.create("git://branch@reponame/path/to/file.txt");
+		assertThat(uri3).isNotNull();
+		assertThat(uri3.getScheme()).isEqualTo("git");
+		assertThat(uri3.getAuthority()).isEqualTo("branch@reponame");
+		assertThat(uri3.getPath()).isEqualTo("/path/to/file.txt");
+		assertThat(uri3.getQuery()).isNull();
+
+		final URI uri4 = URI.create("git://master@myrepo/:path/to/some/place.txt");
+		assertThat(uri4).isNotNull();
+		assertThat(uri4.getScheme()).isEqualTo("git");
+		assertThat(uri4.getAuthority()).isEqualTo("master@myrepo");
+		assertThat(uri4.getPath()).isEqualTo("/:path/to/some/place.txt");
+		assertThat(uri4.getQuery()).isNull();
+
+		final URI uri5 = URI.create("git://origin/master@myrepo/:path/to/some/place.txt");
+		assertThat(uri5).isNotNull();
+		assertThat(uri5.getScheme()).isEqualTo("git");
+		assertThat(uri5.getAuthority()).isEqualTo("origin");
+		assertThat(uri5.getPath()).isEqualTo("/master@myrepo/:path/to/some/place.txt");
+		assertThat(uri5.getQuery()).isNull();
+
+		final URI uri6 = URI.create("git://origin/master@myrepo/path/to/some/place.txt");
+		assertThat(uri6).isNotNull();
+		assertThat(uri6.getScheme()).isEqualTo("git");
+		assertThat(uri6.getAuthority()).isEqualTo("origin");
+		assertThat(uri6.getPath()).isEqualTo("/master@myrepo/path/to/some/place.txt");
+		assertThat(uri6.getQuery()).isNull();
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/UsernamePasswordCredentialsProvider.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/UsernamePasswordCredentialsProvider.java
new file mode 100644
index 0000000000..86e168782a
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/UsernamePasswordCredentialsProvider.java
@@ 0,0 +1,42 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import org.eclipse.jgit.errors.UnsupportedCredentialItem;
+import org.eclipse.jgit.transport.CredentialItem;
+import org.eclipse.jgit.transport.URIish;
+
+/**
+ * Mock CredentialsProvider that handles Yes/No requests
+ */
+public class UsernamePasswordCredentialsProvider
+		extends org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider {
+
+	public UsernamePasswordCredentialsProvider(final String username, final String password) {
+		super(username, password);
+	}
+
+	@Override
+	public boolean get(final URIish uri, final CredentialItem... items) throws UnsupportedCredentialItem {
+		try {
+			return super.get(uri, items);
+		} catch (UnsupportedCredentialItem e) {
+			for (CredentialItem i : items) {
+				if (i instanceof CredentialItem.YesNoType) {
+					((CredentialItem.YesNoType) i).setValue(true);
+					return true;
+				} else {
+					continue;
+				}
+			}
+		}
+		return false;
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/common/PortUtilTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/common/PortUtilTest.java
new file mode 100644
index 0000000000..abfe0b2b9f
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/common/PortUtilTest.java
@@ 0,0 +1,47 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.common;
+
+import java.net.ServerSocket;
+
+import org.junit.Test;
+
+import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
+import static org.assertj.core.api.AssertionsForClassTypes.fail;
+
+public class PortUtilTest {
+
+	@Test
+	public void testValidateOrGetNewWithPreferredNotAvailable() {
+		int result1 = PortUtil.validateOrGetNew(0);
+
+		try (ServerSocket ss = new ServerSocket(result1)) {
+			int result = PortUtil.validateOrGetNew(result1);
+			assertThat(result).isNotEqualTo(result1);
+		} catch (Exception x) {
+			fail("Port allocation should have work!");
+		}
+	}
+
+	@Test
+	public void testValidateOrGetNewWithZero() {
+		int result1 = PortUtil.validateOrGetNew(0);
+
+		assertThat(result1).isNotEqualTo(0);
+	}
+
+	@Test
+	public void testValidateOrGetNewWithAvailablePreferredPort() {
+		int result = PortUtil.validateOrGetNew(0);
+		int result2 = PortUtil.validateOrGetNew(result);
+
+		assertThat(result2).isEqualTo(result);
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/git/DaemonTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/git/DaemonTest.java
new file mode 100644
index 0000000000..a37aa563c5
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/git/DaemonTest.java
@@ 0,0 +1,50 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.git;
+
+import java.util.concurrent.ExecutorService;
+import java.util.concurrent.Executors;
+import java.util.concurrent.TimeUnit;
+
+import org.eclipse.jgit.niofs.internal.ExecutorServiceProducer;
+import org.junit.Test;
+
+import static org.junit.Assert.assertFalse;
+import static org.junit.Assert.assertTrue;
+
+public class DaemonTest {
+
+	private final ExecutorService executorService = new ExecutorServiceProducer().produceUnmanagedExecutorService();
+
+	@Test
+	public void testShutdownByStop() throws Exception {
+		ExecutorService executor = Executors.newCachedThreadPool();
+		Daemon d = new Daemon(null, executor, executorService);
+		d.start();
+		assertTrue(d.isRunning());
+
+		d.stop();
+
+		assertFalse(d.isRunning());
+	}
+
+	@Test
+	public void testShutdownByThreadPoolTermination() throws Exception {
+		ExecutorService executor = Executors.newCachedThreadPool();
+		Daemon d = new Daemon(null, executor, executorService);
+		d.start();
+		assertTrue(d.isRunning());
+
+		executor.shutdownNow();
+		executor.awaitTermination(10, TimeUnit.SECONDS);
+
+		assertFalse(d.isRunning());
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSOnlySupportTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSOnlySupportTest.java
new file mode 100644
index 0000000000..2b24f6894f
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSOnlySupportTest.java
@@ 0,0 +1,81 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.http;
+
+import java.util.Map;
+
+import javax.servlet.Servlet;
+import javax.servlet.ServletContext;
+import javax.servlet.ServletContextEvent;
+import javax.servlet.ServletRegistration;
+
+import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.junit.Test;
+import org.mockito.ArgumentCaptor;
+
+import static org.assertj.core.api.Assertions.assertThat;
+import static org.mockito.Matchers.any;
+import static org.mockito.Matchers.anyString;
+import static org.mockito.Mockito.mock;
+import static org.mockito.Mockito.times;
+import static org.mockito.Mockito.verify;
+import static org.mockito.Mockito.when;
+
+public class HTTPSOnlySupportTest extends AbstractTestInfra {
+
+	/*
+	 * Default Git preferences suitable for most of the tests. If specific test
+	 * needs some custom configuration, it needs to override this method and provide
+	 * own map of preferences.
+	 */
+	public Map<String, String> getGitPreferences() {
+		Map<String, String> gitPrefs = super.getGitPreferences();
+		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED, "false");
+		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTPS_ENABLED, "true");
+		return gitPrefs;
+	}
+
+	@Test
+	public void testRoot() {
+		base("/");
+		assertThat(provider.getFullHostNames().get("http")).isNull();
+		assertThat(provider.getFullHostNames().get("https")).isNotNull();
+	}
+
+	public void base(final String contextPath) {
+		final HTTPSupport httpSupport = new HTTPSupport() {
+			@Override
+			protected JGitFileSystemProvider resolveProvider() {
+				return provider;
+			}
+		};
+
+		final ServletContextEvent sce = mock(ServletContextEvent.class);
+
+		final ServletContext sc = mock(ServletContext.class);
+		final ServletRegistration.Dynamic dyn = mock(ServletRegistration.Dynamic.class);
+
+		ArgumentCaptor<Servlet> servletArgumentCaptor = ArgumentCaptor.forClass(Servlet.class);
+
+		when(sc.addServlet(anyString(), servletArgumentCaptor.capture())).thenReturn(dyn);
+
+		when(sce.getServletContext()).thenReturn(sc);
+		when(sc.getContextPath()).thenReturn(contextPath);
+
+		httpSupport.contextInitialized(sce);
+
+		verify(sc, times(1)).addServlet(anyString(), any(Servlet.class));
+		verify(dyn, times(1)).addMapping("/git/*");
+		verify(dyn, times(1)).setLoadOnStartup(1);
+		verify(dyn, times(1)).setAsyncSupported(false);
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportDisableTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportDisableTest.java
new file mode 100644
index 0000000000..a787e8fb7c
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportDisableTest.java
@@ 0,0 +1,67 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.http;
+
+import java.util.Map;
+
+import javax.servlet.Servlet;
+import javax.servlet.ServletContext;
+import javax.servlet.ServletContextEvent;
+
+import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.junit.Test;
+
+import static org.assertj.core.api.Assertions.assertThat;
+import static org.junit.Assert.assertFalse;
+import static org.mockito.Matchers.any;
+import static org.mockito.Matchers.anyString;
+import static org.mockito.Mockito.mock;
+import static org.mockito.Mockito.times;
+import static org.mockito.Mockito.verify;
+import static org.mockito.Mockito.when;
+
+public class HTTPSupportDisableTest extends AbstractTestInfra {
+
+	/*
+	 * Default Git preferences suitable for most of the tests. If specific test
+	 * needs some custom configuration, it needs to override this method and provide
+	 * own map of preferences.
+	 */
+	public Map<String, String> getGitPreferences() {
+		Map<String, String> gitPrefs = super.getGitPreferences();
+		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED, "false");
+		return gitPrefs;
+	}
+
+	@Test
+	public void testRoot() {
+		assertThat(provider.getFullHostNames().get("http")).isNull();
+	}
+
+	@Test
+	public void test() {
+		final HTTPSupport httpSupport = new HTTPSupport() {
+			@Override
+			protected JGitFileSystemProvider resolveProvider() {
+				return provider;
+			}
+		};
+
+		final ServletContextEvent sce = mock(ServletContextEvent.class);
+		final ServletContext sc = mock(ServletContext.class);
+		when(sce.getServletContext()).thenReturn(sc);
+		httpSupport.contextInitialized(sce);
+		assertFalse(provider.getFullHostNames().containsKey("http"));
+
+		verify(sc, times(0)).addServlet(anyString(), any(Servlet.class));
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportTest.java
new file mode 100644
index 0000000000..457a9ddd82
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupportTest.java
@@ 0,0 +1,80 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.http;
+
+import java.util.Map;
+
+import javax.servlet.Servlet;
+import javax.servlet.ServletContext;
+import javax.servlet.ServletContextEvent;
+import javax.servlet.ServletRegistration;
+
+import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.junit.Test;
+import org.mockito.ArgumentCaptor;
+
+import static org.assertj.core.api.Assertions.assertThat;
+import static org.mockito.Matchers.any;
+import static org.mockito.Matchers.anyString;
+import static org.mockito.Mockito.mock;
+import static org.mockito.Mockito.times;
+import static org.mockito.Mockito.verify;
+import static org.mockito.Mockito.when;
+
+public class HTTPSupportTest extends AbstractTestInfra {
+
+	public Map<String, String> getGitPreferences() {
+		Map<String, String> gitPrefs = super.getGitPreferences();
+		gitPrefs.put(JGitFileSystemProviderConfiguration.GIT_HTTP_ENABLED, "true");
+		return gitPrefs;
+	}
+
+	@Test
+	public void testRoot() {
+		base("");
+		assertThat(provider.getFullHostNames().get("http")).isEqualTo("localhost:8080/git");
+	}
+
+	@Test
+	public void testContext() {
+		base("/appformer");
+		assertThat(provider.getFullHostNames().get("http")).isEqualTo("localhost:8080/appformer/git");
+	}
+
+	public void base(final String contextPath) {
+		final HTTPSupport httpSupport = new HTTPSupport() {
+			@Override
+			protected JGitFileSystemProvider resolveProvider() {
+				return provider;
+			}
+		};
+
+		final ServletContextEvent sce = mock(ServletContextEvent.class);
+
+		final ServletContext sc = mock(ServletContext.class);
+		final ServletRegistration.Dynamic dyn = mock(ServletRegistration.Dynamic.class);
+
+		ArgumentCaptor<Servlet> servletArgumentCaptor = ArgumentCaptor.forClass(Servlet.class);
+
+		when(sc.addServlet(anyString(), servletArgumentCaptor.capture())).thenReturn(dyn);
+
+		when(sce.getServletContext()).thenReturn(sc);
+		when(sc.getContextPath()).thenReturn(contextPath);
+
+		httpSupport.contextInitialized(sce);
+
+		verify(sc, times(1)).addServlet(anyString(), any(Servlet.class));
+		verify(dyn, times(1)).addMapping("/git/*");
+		verify(dyn, times(1)).setLoadOnStartup(1);
+		verify(dyn, times(1)).setAsyncSupported(false);
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHServiceTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHServiceTest.java
new file mode 100644
index 0000000000..2e83ebf60f
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHServiceTest.java
@@ 0,0 +1,396 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.ssh;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.List;
+import java.util.concurrent.ExecutorService;
+
+import org.apache.sshd.common.cipher.BuiltinCiphers;
+import org.apache.sshd.common.mac.BuiltinMacs;
+import org.apache.sshd.server.SshServer;
+import org.eclipse.jgit.niofs.internal.ExecutorServiceProducer;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
+import org.eclipse.jgit.transport.resolver.UploadPackFactory;
+import org.eclipse.jgit.util.FileUtils;
+import org.junit.AfterClass;
+import org.junit.BeforeClass;
+import org.junit.Test;
+
+import static org.assertj.core.api.Assertions.assertThat;
+import static org.junit.Assert.assertFalse;
+import static org.junit.Assert.assertTrue;
+import static org.junit.Assert.fail;
+import static org.mockito.Mockito.mock;
+
+public class GitSSHServiceTest {
+
+	private static final List<File> tempFiles = new ArrayList<>();
+
+	private final ExecutorService executorService = new ExecutorServiceProducer().produceUnmanagedExecutorService();
+
+	protected static File createTempDirectory() throws IOException {
+		final File temp = File.createTempFile("temp", Long.toString(System.nanoTime()));
+		if (!(temp.delete())) {
+			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
+		}
+
+		if (!(temp.mkdir())) {
+			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
+		}
+
+		tempFiles.add(temp);
+
+		return temp;
+	}
+
+	@AfterClass
+	@BeforeClass
+	public static void cleanup() {
+		for (final File tempFile : tempFiles) {
+			try {
+				FileUtils.delete(tempFile, FileUtils.RECURSIVE);
+			} catch (IOException ignore) {
+			}
+		}
+	}
+
+	@Test
+	public void testStartStop() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		sshService.setup(certDir, null, "10000", "RSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+				mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testStartStopAlgo2() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		sshService.setup(certDir, null, "10000", "DSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+				mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckTimeout() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckAlgo() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		try {
+			sshService.setup(certDir, null, "10000", "xxxx", mock(ReceivePackFactory.class),
+					mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+					executorService);
+			fail("has to fail");
+		} catch (final Exception ex) {
+			assertThat(ex.getMessage()).contains("'xxxx'");
+		}
+	}
+
+	@Test
+	public void testCheckSetupParameters() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		try {
+			sshService.setup(null, null, "10000", "RSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'certDir'");
+		}
+
+		try {
+			sshService.setup(certDir, null, null, "RSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'sshIdleTimeout'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "", "RSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'sshIdleTimeout'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "1000", null, mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'algorithm'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "1000", "", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'algorithm'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "100", "RSA", null, null,
+					mock(JGitFileSystemProvider.RepositoryResolverImpl.class), executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'receivePackFactory'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "100", "RSA", mock(ReceivePackFactory.class), mock(UploadPackFactory.class),
+					null, executorService);
+			fail("has to fail");
+		} catch (IllegalArgumentException ex) {
+			assertThat(ex.getMessage()).contains("'repositoryResolver'");
+		}
+
+		try {
+			sshService.setup(certDir, null, "10000", "RSA", mock(ReceivePackFactory.class),
+					mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+					executorService);
+		} catch (IllegalArgumentException ex) {
+			fail("should not fail");
+		}
+	}
+
+	@Test
+	public void testCheckCiphersAndMacs() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		String ciphers = "aes128cbc,aes128ctr,aes192cbc,aes192ctr,aes256cbc,aes256ctr,arcfour128,arcfour256,blowfishcbc,3descbc";
+		String macs = "hmacmd5, hmacmd596, hmacsha1, hmacsha196, hmacsha2256, hmacsha2512";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, ciphers, macs);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(7);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckEmptyCiphers() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		String macs = "hmacmd5, hmacmd596, hmacsha1, hmacsha196, hmacsha2256, hmacsha2512";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, "", macs);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(7);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckEmptyMacs() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+
+		String ciphers = "aes128cbc,aes128ctr,aes192cbc,aes192ctr,aes256cbc,aes256ctr,arcfour128,arcfour256,blowfishcbc,3descbc";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, ciphers, "");
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(7);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckEmptyCiphersAndMacs() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, "", "");
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(7);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testCheckNullCiphersAndMacs() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, null, null);
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(7);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	@Test
+	public void testWithWrongCiphersAndMacs() throws Exception {
+		final GitSSHService sshService = new GitSSHService();
+		final File certDir = createTempDirectory();
+
+		String idleTimeout = "10000";
+		String ciphers = "aes126cbc,aes124ctr,aes192cbc,aes192ctr,aes255cbc,aes256ctr,arcfour128,arcfour256,blowfishcbc,3descbc";
+		sshService.setup(certDir, null, idleTimeout, "RSA", mock(ReceivePackFactory.class),
+				mock(UploadPackFactory.class), mock(JGitFileSystemProvider.RepositoryResolverImpl.class),
+				executorService, ciphers, "");
+
+		sshService.start();
+		assertTrue(sshService.isRunning());
+
+		List<String> ciphersReaded = sshService.getSshServer().getCipherFactoriesNames();
+		List<String> macsReaded = sshService.getSshServer().getMacFactoriesNames();
+
+		assertThat(ciphersReaded).hasSize(5);
+		checkCiphersName(ciphersReaded);
+
+		assertThat(macsReaded).hasSize(6);
+		checkMacsName(macsReaded);
+
+		assertThat(sshService.getSshServer().getProperties().get(SshServer.IDLE_TIMEOUT)).isEqualTo(idleTimeout);
+
+		sshService.stop();
+
+		assertFalse(sshService.isRunning());
+	}
+
+	private void checkCiphersName(List<String> ciphersReaded) {
+		for (String cipher : ciphersReaded) {
+			assertThat(BuiltinCiphers.fromFactoryName(cipher)).isNotNull();
+		}
+	}
+
+	private void checkMacsName(List<String> macsReaded) {
+		for (String mac : macsReaded) {
+			assertThat(BuiltinMacs.fromFactoryName(mac)).isNotNull();
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheTest.java
new file mode 100644
index 0000000000..daa1f7df7a
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheTest.java
@@ 0,0 +1,313 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.io.IOException;
+import java.util.Arrays;
+import java.util.concurrent.TimeUnit;
+import java.util.function.Supplier;
+
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
+import org.eclipse.jgit.niofs.internal.JGitFileSystem;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemImpl;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemLock;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProxy;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.junit.Before;
+import org.junit.Test;
+import org.junit.runner.RunWith;
+import org.mockito.junit.MockitoJUnitRunner;
+
+import static org.junit.Assert.assertEquals;
+import static org.junit.Assert.assertFalse;
+import static org.junit.Assert.assertTrue;
+import static org.mockito.Mockito.mock;
+import static org.mockito.Mockito.spy;
+import static org.mockito.Mockito.times;
+import static org.mockito.Mockito.verify;
+import static org.mockito.Mockito.when;
+
+@RunWith(MockitoJUnitRunner.class)
+public class JGitFileSystemsCacheTest extends AbstractTestInfra {
+
+	JGitFileSystemsCache cache;
+	private JGitFileSystemProviderConfiguration config;
+
+	@Before
+	public void setup() {
+		config = mock(JGitFileSystemProviderConfiguration.class);
+	}
+
+	@Test
+	public void addAndGetTest() {
+		when(config.getJgitFileSystemsInstancesCache()).thenReturn(2);
+		cache = new JGitFileSystemsCache(config);
+
+		assertTrue(cache.fileSystemsSuppliers.isEmpty());
+		assertTrue(cache.memoizedSuppliers.isEmpty());
+
+		assertEquals(null, cache.get("fs1"));
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs1Supplier = () > fs1;
+		cache.addSupplier("fs1", fs1Supplier);
+
+		assertFalse(cache.fileSystemsSuppliers.isEmpty());
+		assertFalse(cache.memoizedSuppliers.isEmpty());
+
+		JGitFileSystemProxy fs1Proxy = (JGitFileSystemProxy) cache.get("fs1");
+
+		assertEquals(fs1, fs1Proxy.getRealJGitFileSystem());
+
+		assertTrue(cache.containsKey("fs1"));
+
+		cache.clear();
+
+		assertTrue(cache.fileSystemsSuppliers.isEmpty());
+		assertTrue(cache.memoizedSuppliers.isEmpty());
+	}
+
+	@Test
+	public void addMoreFSThanCacheSupports() {
+		when(config.getJgitFileSystemsInstancesCache()).thenReturn(2);
+		cache = new JGitFileSystemsCache(config);
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs1Supplier = getSupplierSpy(fs1);
+		cache.addSupplier("fs1", fs1Supplier);
+
+		assertEquals(1, cache.fileSystemsSuppliers.size());
+		assertEquals(1, cache.memoizedSuppliers.size());
+
+		((JGitFileSystemProxy) cache.get("fs1")).getRealJGitFileSystem();
+
+		JGitFileSystem fs2 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
+		cache.addSupplier("fs2", fs2Supplier);
+		((JGitFileSystemProxy) cache.get("fs2")).getRealJGitFileSystem();
+
+		assertEquals(2, cache.fileSystemsSuppliers.size());
+		assertEquals(2, cache.memoizedSuppliers.size());
+
+		JGitFileSystem fs3 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
+		cache.addSupplier("fs3", fs3Supplier);
+
+		((JGitFileSystemProxy) cache.get("fs3")).getRealJGitFileSystem();
+
+		assertEquals(3, cache.fileSystemsSuppliers.size());
+		assertEquals(2, cache.memoizedSuppliers.size());
+
+		((JGitFileSystemProxy) cache.get("fs2")).getRealJGitFileSystem();
+
+		// just one call because is on memoized cache
+		verify(fs2Supplier, times(1)).get();
+
+		((JGitFileSystemProxy) cache.get("fs3")).getRealJGitFileSystem();
+
+		// just one call because is on memoized cache
+		verify(fs3Supplier, times(1)).get();
+
+		((JGitFileSystemProxy) cache.get("fs1")).getRealJGitFileSystem();
+
+		// two calls because is on no longer on memoized cache (oldest instance) needs
+		// to regenerate
+		// from fs supplier
+		verify(fs1Supplier, times(2)).get();
+	}
+
+	@Test
+	public void cacheIsOrderedByTheAccessOrder() {
+
+		JGitFileSystemProviderConfiguration config = setupConfigMock();
+
+		setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
+		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
+
+		setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
+		cache.get("fs1");
+		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
+
+		setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
+		cache.get("fs1");
+		cache.get("fs2");
+		assertFalse(cache.memoizedSuppliers.containsKey("fs3"));
+
+		cache.get("fs1");
+		cache.get("fs2");
+		cache.get("fs3");
+		setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
+		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
+	}
+
+	@Test
+	public void removeEldestEntryTest() {
+
+		JGitFileSystemProviderConfiguration config = setupConfigMock();
+
+		// no fs is on use
+		setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
+		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
+
+		// fs1 is on use
+		cache = new JGitFileSystemsCache(config);
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		when(fs1.hasBeenInUse()).thenReturn(true);
+		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
+		cache.addSupplier("fs1", fsSupplier1);
+
+		JGitFileSystem fs2 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fsSupplier2 = getSupplierSpy(fs2);
+		when(fs2.hasBeenInUse()).thenReturn(false);
+		cache.addSupplier("fs2", fsSupplier2);
+
+		JGitFileSystem fs3 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs3);
+		when(fs3.hasBeenInUse()).thenReturn(false);
+		cache.addSupplier("fs3", fsSupplier);
+
+		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
+		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
+		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
+	}
+
+	@Test
+	public void removeEldestEntryTestAllOpen() {
+
+		JGitFileSystemProviderConfiguration config = setupConfigMock();
+
+		cache = new JGitFileSystemsCache(config);
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		when(fs1.hasBeenInUse()).thenReturn(true);
+		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
+		cache.addSupplier("fs1", fsSupplier1);
+
+		JGitFileSystem fs2 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
+		when(fs2.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs2", fs2Supplier);
+
+		JGitFileSystem fs3 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
+		when(fs3.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs3", fs3Supplier);
+
+		JGitFileSystem fs4 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs4Supplier = getSupplierSpy(fs4);
+		when(fs4.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs4", fs3Supplier);
+
+		// > cache because all fs are in use
+		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
+		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
+		assertTrue(cache.memoizedSuppliers.containsKey("fs2"));
+		assertTrue(cache.memoizedSuppliers.containsKey("fs4"));
+
+		when(fs1.hasBeenInUse()).thenReturn(false);
+		when(fs2.hasBeenInUse()).thenReturn(false);
+		when(fs4.hasBeenInUse()).thenReturn(false);
+
+		JGitFileSystem fs5 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs5Supplier = getSupplierSpy(fs5);
+		when(fs5.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs5", fs5Supplier);
+
+		// fs5 is in use and also fs4
+		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
+		assertTrue(cache.memoizedSuppliers.containsKey("fs5"));
+		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
+		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
+	}
+
+	@Test
+	public void fsInUseAreAlwaysOnTheCache() throws IOException, GitAPIException {
+
+		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {
+			@Override
+			public int getJgitFileSystemsInstancesCache() {
+				return 2;
+			}
+		};
+
+		cache = new JGitFileSystemsCache(config);
+
+		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);
+
+		final Git git = setupGit();
+
+		final JGitFileSystemImpl fs1 = new JGitFileSystemImpl(fsProvider, null, git,
+				new JGitFileSystemLock(git, TimeUnit.MILLISECONDS, config.getJgitCacheEvictThresholdDuration()), "fs1",
+				CredentialsProvider.getDefault(), null, null);
+
+		Supplier<JGitFileSystem> fs1Supplier = getSupplierSpy(fs1);
+		cache.addSupplier("fs1", fs1Supplier);
+
+		fs1.lock();
+		fs1.lock();
+		fs1.unlock();
+		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
+
+		JGitFileSystem fs2 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
+		when(fs2.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs2", fs2Supplier);
+
+		JGitFileSystem fs3 = mock(JGitFileSystem.class);
+		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
+		when(fs3.hasBeenInUse()).thenReturn(true);
+		cache.addSupplier("fs5", fs3Supplier);
+
+		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
+	}
+
+	private void setupCacheToTestOrder(JGitFileSystemProviderConfiguration config, String... fsNames) {
+		cache = new JGitFileSystemsCache(config);
+
+		Arrays.stream(fsNames).forEach(fsName > {
+			JGitFileSystem fs = mock(JGitFileSystem.class);
+			Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs);
+			cache.addSupplier(fsName, fsSupplier);
+		});
+	}
+
+	private Supplier<JGitFileSystem> getSupplierSpy(final JGitFileSystem fs1) {
+		return spy(new Supplier<JGitFileSystem>() {
+			@Override
+			public JGitFileSystem get() {
+				return fs1;
+			}
+		});
+	}
+
+	private JGitFileSystemProviderConfiguration setupConfigMock() {
+		return new JGitFileSystemProviderConfiguration() {
+			@Override
+			public int getJgitFileSystemsInstancesCache() {
+				return 2;
+			}
+
+			@Override
+			public int getJgitRemoveEldestEntryIterations() {
+				return 10;
+			}
+
+			@Override
+			public int getJgitCacheOverflowCleanupSize() {
+				return 10;
+			}
+		};
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManagerTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManagerTest.java
new file mode 100644
index 0000000000..23ee85e21f
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManagerTest.java
@@ 0,0 +1,142 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.util.Arrays;
+import java.util.HashMap;
+import java.util.List;
+
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystem;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemLock;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemsEventsManager;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.junit.Before;
+import org.junit.Test;
+import org.junit.runner.RunWith;
+import org.mockito.junit.MockitoJUnitRunner;
+
+import static org.junit.Assert.assertEquals;
+import static org.junit.Assert.assertFalse;
+import static org.junit.Assert.assertTrue;
+import static org.mockito.Mockito.mock;
+import static org.mockito.Mockito.when;
+
+@RunWith(MockitoJUnitRunner.class)
+public class JGitFileSystemsManagerTest {
+
+	private Git git;
+	private JGitFileSystemProviderConfiguration config;
+
+	private JGitFileSystemsManager manager;
+
+	@Before
+	public void setup() {
+		config = mock(JGitFileSystemProviderConfiguration.class);
+		git = mock(Git.class);
+		when(git.getRepository()).thenReturn(mock(Repository.class));
+	}
+
+	@Test
+	public void newFSTest() {
+		JGitFileSystem fs = mock(JGitFileSystem.class);
+		when(fs.getName()).thenReturn("fs");
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		when(fs1.getName()).thenReturn("fs1");
+
+		manager = createFSManager();
+
+		manager.newFileSystem(() > new HashMap<>(), () > git, () > fs.getName(),
+				() > mock(CredentialsProvider.class), () > mock(JGitFileSystemsEventsManager.class), () > null);
+
+		manager.newFileSystem(() > new HashMap<>(), () > git, () > fs1.getName(),
+				() > mock(CredentialsProvider.class), () > mock(JGitFileSystemsEventsManager.class), () > null);
+
+		assertTrue(manager.containsKey("fs"));
+
+		manager.addClosedFileSystems(fs);
+
+		assertTrue(!manager.allTheFSAreClosed());
+
+		manager.clear();
+
+		assertTrue(manager.allTheFSAreClosed());
+	}
+
+	@Test
+	public void parseFSTest() {
+		manager = new JGitFileSystemsManager(mock(JGitFileSystemProvider.class), config);
+
+		checkParse("a", Arrays.asList("a"));
+
+		checkParse("/a", Arrays.asList("a"));
+
+		checkParse("/a/", Arrays.asList("a"));
+
+		checkParse("a/b/", Arrays.asList("a", "a/b"));
+
+		checkParse("/a/b/", Arrays.asList("a", "a/b"));
+
+		checkParse("a/b/c", Arrays.asList("a", "a/b", "a/b/c"));
+
+		checkParse("a/b/c/d", Arrays.asList("a", "a/b", "a/b/c", "a/b/c/d"));
+	}
+
+	@Test
+	public void removeFSTest() {
+		JGitFileSystem fs = mock(JGitFileSystem.class);
+		when(fs.getName()).thenReturn("fs");
+
+		JGitFileSystem fs1 = mock(JGitFileSystem.class);
+		when(fs1.getName()).thenReturn("fs1");
+
+		manager = createFSManager();
+
+		manager.newFileSystem(() > new HashMap<>(), () > git, () > fs.getName(),
+				() > mock(CredentialsProvider.class), () > mock(JGitFileSystemsEventsManager.class), () > null);
+
+		manager.newFileSystem(() > new HashMap<>(), () > git, () > fs1.getName(),
+				() > mock(CredentialsProvider.class), () > mock(JGitFileSystemsEventsManager.class), () > null);
+
+		assertTrue(manager.containsKey("fs1"));
+		assertTrue(manager.containsRoot("fs1"));
+		manager.addClosedFileSystems(fs1);
+		assertTrue(manager.getClosedFileSystems().contains("fs1"));
+
+		manager.remove("fs1");
+		assertFalse(manager.containsKey("fs1"));
+		assertFalse(manager.containsRoot("fs1"));
+		assertFalse(manager.containsRoot("fs1"));
+	}
+
+	private void checkParse(String fsKey, List<String> expected) {
+		List<String> actual = manager.parseFSRoots(fsKey);
+		assertEquals(actual.size(), expected.size());
+		for (String root : expected) {
+			if (!actual.contains(root)) {
+				throw new RuntimeException();
+			}
+		}
+		manager.clear();
+	}
+
+	private JGitFileSystemsManager createFSManager() {
+		return new JGitFileSystemsManager(mock(JGitFileSystemProvider.class), config) {
+			@Override
+			JGitFileSystemLock createLock(Git git) {
+				return mock(JGitFileSystemLock.class);
+			}
+		};
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplierTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplierTest.java
new file mode 100644
index 0000000000..bef3c6988d
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplierTest.java
@@ 0,0 +1,57 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.util.function.Supplier;
+
+import org.junit.Test;
+
+import static org.junit.Assert.assertEquals;
+
+public class MemoizedFileSystemsSupplierTest {
+
+	public static int instanceCount = 0;
+
+	@Test
+	public void supplierTest() {
+
+		getSupplier().get();
+		getSupplier().get();
+		assertEquals(2, instanceCount);
+
+		instanceCount = 0;
+		final Supplier<DummyObject> supplier = getLazySupplier();
+		supplier.get();
+		supplier.get();
+		supplier.get();
+		supplier.get();
+		assertEquals(1, instanceCount);
+	}
+
+	Supplier<DummyObject> getLazySupplier() {
+		return MemoizedFileSystemsSupplier.of(getSupplier());
+	}
+
+	Supplier<DummyObject> getSupplier() {
+		return () > new DummyObject();
+	}
+
+	private class DummyObject {
+
+		public DummyObject() {
+			test();
+			instanceCount++;
+		}
+
+		public void test() {
+			System.out.println("new Instance");
+		}
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/ConcurrentJGitUtilTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/ConcurrentJGitUtilTest.java
new file mode 100644
index 0000000000..a14e4d0816
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/ConcurrentJGitUtilTest.java
@@ 0,0 +1,267 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op;
+
+import java.io.File;
+import java.io.IOException;
+import java.nio.file.NoSuchFileException;
+import java.util.Date;
+import java.util.HashMap;
+
+import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
+import org.eclipse.jgit.niofs.internal.op.commands.Commit;
+import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.jboss.byteman.contrib.bmunit.BMScript;
+import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
+import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
+import org.junit.BeforeClass;
+import org.junit.Test;
+import org.junit.runner.RunWith;
+
+import static org.junit.Assert.assertNotNull;
+import static org.junit.Assert.fail;
+
+@RunWith(BMUnitRunner.class)
+@BMUnitConfig(loadDirectory = "target/testclasses", debug = true) // set "debug=true to see debug output
+public class ConcurrentJGitUtilTest extends AbstractTestInfra {
+
+	@BeforeClass
+	public static void setup() {
+		GitImpl.setRetryTimes(5);
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/resolve_path.btm")
+	public void testRetryResolvePath() throws IOException {
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		try {
+			assertNotNull(git.getPathInfo("master", "path/to/file1.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file1.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file1.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file1.txt"));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			git.getPathInfo("master", "path/to/file1.txt");
+			fail("forced to fail!");
+		} catch (RuntimeException ex) {
+		}
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/resolve_inputstream.btm")
+	public void testRetryResolveInputStream() throws IOException {
+
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		try {
+			assertNotNull(git.blobAsInputStream("master", "path/to/file1.txt"));
+			assertNotNull(git.blobAsInputStream("master", "path/to/file1.txt"));
+			assertNotNull(git.blobAsInputStream("master", "path/to/file1.txt"));
+			assertNotNull(git.blobAsInputStream("master", "path/to/file1.txt"));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			assertNotNull(git.blobAsInputStream("master", "path/to/file1.txt"));
+			fail("forced to fail!");
+		} catch (NoSuchFileException ex) {
+		}
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/list_path_content.btm")
+	public void testRetryListPathContent() throws IOException {
+
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		try {
+			assertNotNull(git.listPathContent("master", "path/to/"));
+			assertNotNull(git.listPathContent("master", "path/to/"));
+			assertNotNull(git.listPathContent("master", "path/to/"));
+			assertNotNull(git.listPathContent("master", "path/to/"));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			assertNotNull(git.listPathContent("master", "path/to/"));
+			fail("forced to fail!");
+		} catch (RuntimeException ex) {
+		}
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/check_path.btm")
+	public void testRetryCheckPath() throws IOException {
+
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		try {
+			assertNotNull(git.getPathInfo("master", "path/to/file2.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file2.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file2.txt"));
+			assertNotNull(git.getPathInfo("master", "path/to/file2.txt"));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			assertNotNull(git.getPathInfo("master", "path/to/file2.txt"));
+			fail("forced to fail!");
+		} catch (RuntimeException ex) {
+		}
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/get_last_commit.btm")
+	public void testRetryGetLastCommit() throws IOException {
+
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		try {
+			assertNotNull(git.getLastCommit("master"));
+			assertNotNull(git.getLastCommit("master"));
+			assertNotNull(git.getLastCommit("master"));
+			assertNotNull(git.getLastCommit("master"));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			assertNotNull(git.getLastCommit("master"));
+			fail("forced to fail!");
+		} catch (RuntimeException ex) {
+		}
+	}
+
+	@Test
+	@BMScript(value = "byteman/retry/get_commits.btm")
+	public void testRetryGetCommits() throws IOException {
+
+		final File parentFolder = createTempDirectory();
+		final File gitFolder = new File(parentFolder, "mytest.git");
+
+		final Git git = new CreateRepository(gitFolder).execute().get();
+
+		new Commit(git, "master", "name", "name@example.com", "1st commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file1.txt", tempFile("temp2222"));
+					}
+				}).execute();
+		new Commit(git, "master", "name", "name@example.com", "2nd commit", null, new Date(), false,
+				new HashMap<String, File>() {
+					{
+						put("path/to/file2.txt", tempFile("temp2222"));
+					}
+				}).execute();
+
+		final RevCommit commit = git.getLastCommit("master");
+		try {
+			assertNotNull(git.listCommits(null, commit));
+			assertNotNull(git.listCommits(null, commit));
+			assertNotNull(git.listCommits(null, commit));
+		} catch (Exception ex) {
+			fail();
+		}
+
+		try {
+			assertNotNull(git.listCommits(null, commit));
+			fail("forced to fail!");
+		} catch (RuntimeException ex) {
+		}
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/commands/SyncRemoteTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/commands/SyncRemoteTest.java
new file mode 100644
index 0000000000..b221b003c8
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/commands/SyncRemoteTest.java
@@ 0,0 +1,61 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.AbstractMap;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.List;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.junit.Before;
+import org.junit.Test;
+
+import static org.junit.Assert.assertEquals;
+import static org.mockito.Mockito.doReturn;
+import static org.mockito.Mockito.mock;
+
+public class SyncRemoteTest {
+
+	private SyncRemote syncRemote;
+
+	@Before
+	public void setup() {
+		syncRemote = new SyncRemote(mock(GitImpl.class), new AbstractMap.SimpleEntry<>("upstream", "b"));
+	}
+
+	@Test
+	public void fillBranchesTest() {
+		final List<Ref> branches = Arrays.asList(createBranch("refs/heads/local/branch1"),
+				createBranch("refs/heads/localBranch2"), createBranch("refs/remotes/upstream/remote/branch1"),
+				createBranch("refs/remotes/upstream/remoteBranch2"));
+
+		final List<String> remoteBranches = new ArrayList<>();
+		final List<String> localBranches = new ArrayList<>();
+
+		syncRemote.fillBranches(branches, remoteBranches, localBranches);
+
+		assertEquals(2, remoteBranches.size());
+		assertEquals("remote/branch1", remoteBranches.get(0));
+		assertEquals("remoteBranch2", remoteBranches.get(1));
+
+		assertEquals(2, localBranches.size());
+		assertEquals("local/branch1", localBranches.get(0));
+		assertEquals("localBranch2", localBranches.get(1));
+	}
+
+	private Ref createBranch(String branchName) {
+		final Ref branch = mock(Ref.class);
+		doReturn(branchName).when(branch).getName();
+
+		return branch;
+	}
+}
diff git a/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/extensions/JGitFSHooksTest.java b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/extensions/JGitFSHooksTest.java
new file mode 100644
index 0000000000..6167952653
 /dev/null
+++ b/org.eclipse.jgit.niofs.test/tst/org/eclipse/jgit/niofs/internal/op/extensions/JGitFSHooksTest.java
@@ 0,0 +1,122 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.extensions;
+
+import java.util.Arrays;
+import java.util.concurrent.atomic.AtomicBoolean;
+
+import org.assertj.core.api.Assertions;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooksConstants;
+import org.eclipse.jgit.niofs.internal.hook.JGitFSHooks;
+import org.junit.Test;
+import org.junit.runner.RunWith;
+import org.mockito.ArgumentCaptor;
+import org.mockito.Captor;
+import org.mockito.junit.MockitoJUnitRunner;
+
+import static org.junit.Assert.assertEquals;
+import static org.junit.Assert.assertTrue;
+import static org.mockito.Mockito.spy;
+import static org.mockito.Mockito.verify;
+
+@RunWith(MockitoJUnitRunner.class)
+public class JGitFSHooksTest {
+
+	private static final String FS_NAME = "dora";
+	private static final Integer EXIT_CODE = 0;
+
+	@Captor
+	private ArgumentCaptor<FileSystemHookExecutionContext> contextArgumentCaptor;
+
+	@Test
+	public void executeFSHooksTest() {
+
+		FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(FS_NAME);
+
+		testExecuteFSHooks(ctx, FileSystemHooks.ExternalUpdate);
+
+		ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE, EXIT_CODE);
+
+		testExecuteFSHooks(ctx, FileSystemHooks.PostCommit);
+	}
+
+	private void testExecuteFSHooks(FileSystemHookExecutionContext ctx, FileSystemHooks hookType) {
+		AtomicBoolean executedWithLambda = new AtomicBoolean(false);
+
+		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
+			@Override
+			public void execute(FileSystemHookExecutionContext context) {
+				assertEquals(FS_NAME, context.getFsName());
+			}
+		});
+
+		FileSystemHooks.FileSystemHook lambdaHook = context > {
+			assertEquals(FS_NAME, context.getFsName());
+			executedWithLambda.set(true);
+		};
+
+		JGitFSHooks.executeFSHooks(hook, hookType, ctx);
+		JGitFSHooks.executeFSHooks(lambdaHook, hookType, ctx);
+
+		verifyFSHook(hook, hookType);
+
+		assertTrue(executedWithLambda.get());
+	}
+
+	@Test
+	public void executeFSHooksArrayTest() {
+
+		FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(FS_NAME);
+
+		testExecuteFSHooksArray(ctx, FileSystemHooks.ExternalUpdate);
+
+		ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE, EXIT_CODE);
+
+		testExecuteFSHooksArray(ctx, FileSystemHooks.PostCommit);
+	}
+
+	private void testExecuteFSHooksArray(FileSystemHookExecutionContext ctx, FileSystemHooks hookType) {
+
+		AtomicBoolean executedWithLambda = new AtomicBoolean(false);
+
+		FileSystemHooks.FileSystemHook hook = spy(new FileSystemHooks.FileSystemHook() {
+			@Override
+			public void execute(FileSystemHookExecutionContext context) {
+				assertEquals(FS_NAME, context.getFsName());
+			}
+		});
+
+		FileSystemHooks.FileSystemHook lambdaHook = context > {
+			assertEquals(FS_NAME, context.getFsName());
+			executedWithLambda.set(true);
+		};
+
+		JGitFSHooks.executeFSHooks(Arrays.asList(hook, lambdaHook), hookType, ctx);
+
+		verifyFSHook(hook, hookType);
+
+		assertTrue(executedWithLambda.get());
+	}
+
+	private void verifyFSHook(FileSystemHooks.FileSystemHook hook, FileSystemHooks hookType) {
+		verify(hook).execute(contextArgumentCaptor.capture());
+
+		FileSystemHookExecutionContext ctx = contextArgumentCaptor.getValue();
+
+		Assertions.assertThat(ctx).isNotNull().hasFieldOrPropertyWithValue("fsName", FS_NAME);
+
+		if (hookType.equals(FileSystemHooks.PostCommit)) {
+			Assertions.assertThat(ctx.getParamValue(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE)).isNotNull()
+					.isEqualTo(EXIT_CODE);
+		}
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/.classpath b/org.eclipse.jgit.niofs/.classpath
new file mode 100644
index 0000000000..22f30643cb
 /dev/null
+++ b/org.eclipse.jgit.niofs/.classpath
@@ 0,0 +1,7 @@
+<?xml version="1.0" encoding="UTF8"?>
+<classpath>
+	<classpathentry kind="src" path="src"/>
+	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE1.8"/>
+	<classpathentry kind="con" path="org.eclipse.pde.core.requiredPlugins"/>
+	<classpathentry kind="output" path="bin"/>
+</classpath>
diff git a/org.eclipse.jgit.niofs/.gitignore b/org.eclipse.jgit.niofs/.gitignore
new file mode 100644
index 0000000000..934e0e06ff
 /dev/null
+++ b/org.eclipse.jgit.niofs/.gitignore
@@ 0,0 +1,2 @@
+/bin
+/target
diff git a/org.eclipse.jgit.niofs/.project b/org.eclipse.jgit.niofs/.project
new file mode 100644
index 0000000000..59d403f40a
 /dev/null
+++ b/org.eclipse.jgit.niofs/.project
@@ 0,0 +1,34 @@
+<?xml version="1.0" encoding="UTF8"?>
+<projectDescription>
+	<name>org.eclipse.jgit.niofs</name>
+	<comment></comment>
+	<projects>
+	</projects>
+	<buildSpec>
+		<buildCommand>
+			<name>org.eclipse.jdt.core.javabuilder</name>
+			<arguments>
+			</arguments>
+		</buildCommand>
+		<buildCommand>
+			<name>org.eclipse.pde.ManifestBuilder</name>
+			<arguments>
+			</arguments>
+		</buildCommand>
+		<buildCommand>
+			<name>org.eclipse.pde.SchemaBuilder</name>
+			<arguments>
+			</arguments>
+		</buildCommand>
+		<buildCommand>
+			<name>org.eclipse.pde.api.tools.apiAnalysisBuilder</name>
+			<arguments>
+			</arguments>
+		</buildCommand>
+	</buildSpec>
+	<natures>
+		<nature>org.eclipse.pde.PluginNature</nature>
+		<nature>org.eclipse.jdt.core.javanature</nature>
+		<nature>org.eclipse.pde.api.tools.apiAnalysisNature</nature>
+	</natures>
+</projectDescription>
diff git a/org.eclipse.jgit.niofs/.settings/org.eclipse.jdt.core.prefs b/org.eclipse.jgit.niofs/.settings/org.eclipse.jdt.core.prefs
new file mode 100644
index 0000000000..0c68a61dca
 /dev/null
+++ b/org.eclipse.jgit.niofs/.settings/org.eclipse.jdt.core.prefs
@@ 0,0 +1,7 @@
+eclipse.preferences.version=1
+org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode=enabled
+org.eclipse.jdt.core.compiler.codegen.targetPlatform=1.8
+org.eclipse.jdt.core.compiler.compliance=1.8
+org.eclipse.jdt.core.compiler.problem.assertIdentifier=error
+org.eclipse.jdt.core.compiler.problem.enumIdentifier=error
+org.eclipse.jdt.core.compiler.source=1.8
diff git a/org.eclipse.jgit.niofs/METAINF/MANIFEST.MF b/org.eclipse.jgit.niofs/METAINF/MANIFEST.MF
new file mode 100644
index 0000000000..617a89ec39
 /dev/null
+++ b/org.eclipse.jgit.niofs/METAINF/MANIFEST.MF
@@ 0,0 +1,72 @@
+ManifestVersion: 1.0
+BundleManifestVersion: 2
+BundleName: %BundleName
+AutomaticModuleName: org.eclipse.jgit.niofs
+BundleSymbolicName: org.eclipse.jgit.niofs
+BundleVersion: 5.7.0.qualifier
+BundleRequiredExecutionEnvironment: JavaSE1.8
+ImportPackage: com.jcraft.jsch;version="[0.1.54,2.0.0)",
+ javax.servlet;version="[2.5.0,3.2.0)",
+ javax.servlet.annotation;version="[2.5.0,3.2.0)",
+ javax.servlet.http;version="[2.5.0,3.2.0)",
+ org.apache.commons.codec;version="1.10.0",
+ org.apache.commons.codec.net;version="[1.10.0,2.0.0)",
+ org.apache.commons.io;version="[2.6.0,3.0.0)",
+ org.apache.sshd.common;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.channel;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.cipher;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.helpers;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.mac;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.session;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.util.closeable;version="[2.2.0,2.3.0)",
+ org.apache.sshd.common.util.security;version="[2.2.0,2.3.0)",
+ org.apache.sshd.server;version="[2.2.0,2.3.0)",
+ org.apache.sshd.server.auth.pubkey;version="[2.2.0,2.3.0)",
+ org.apache.sshd.server.command;version="2.2.0",
+ org.apache.sshd.server.keyprovider;version="[2.2.0,2.3.0)",
+ org.apache.sshd.server.session;version="[2.2.0,2.3.0)",
+ org.apache.sshd.server.shell;version="2.2.0",
+ org.eclipse.jgit.api;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.api.errors;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.diff;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.dircache;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.errors;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.http.server;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.internal;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.internal.ketch;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.internal.storage.reftree;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.lib;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.merge;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.patch;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.revwalk;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.revwalk.filter;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.storage.file;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.storage.pack;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.transport;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.transport.resolver;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.treewalk;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.treewalk.filter;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.util;version="[5.7.0,5.8.0)",
+ org.eclipse.jgit.util.io;version="[5.7.0,5.8.0)",
+ org.slf4j;version="[1.7.0,2.0.0)"
+ExportPackage: org.eclipse.jgit.niofs;version="5.7.0",
+ org.eclipse.jgit.niofs.cluster;version="5.7.0",
+ org.eclipse.jgit.niofs.fs;version="5.7.0",
+ org.eclipse.jgit.niofs.fs.attribute;version="5.7.0",
+ org.eclipse.jgit.niofs.fs.options;version="5.7.0";uses:="org.eclipse.jgit.niofs.fs.attribute",
+ org.eclipse.jgit.niofs.internal;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.config;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.daemon.common;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.daemon.filter;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.daemon.git;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.daemon.http;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.daemon.ssh;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.hook;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.manager;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.op;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.op.commands;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.op.exceptions;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.op.model;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.security;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test",
+ org.eclipse.jgit.niofs.internal.util;version="5.7.0";xfriends:="org.eclipse.jgit.niofs.test"
+BundleVendor: %BundleVendor
diff git a/org.eclipse.jgit.niofs/METAINF/SOURCEMANIFEST.MF b/org.eclipse.jgit.niofs/METAINF/SOURCEMANIFEST.MF
new file mode 100644
index 0000000000..a665f6f8e9
 /dev/null
+++ b/org.eclipse.jgit.niofs/METAINF/SOURCEMANIFEST.MF
@@ 0,0 +1,7 @@
+ManifestVersion: 1.0
+BundleManifestVersion: 2
+BundleName: org.eclipse.jgit.niofs  Sources
+BundleSymbolicName: org.eclipse.jgit.niofs.source
+BundleVendor: Eclipse.org  JGit
+BundleVersion: 5.7.0.qualifier
+EclipseSourceBundle: org.eclipse.jgit.niofs;version="5.7.0.qualifier";roots="."
diff git a/org.eclipse.jgit.niofs/README.md b/org.eclipse.jgit.niofs/README.md
new file mode 100644
index 0000000000..5935413b0d
 /dev/null
+++ b/org.eclipse.jgit.niofs/README.md
@@ 0,0 +1,43 @@
+JGitNIO
+=====
+
+JGitNIO is a jgit based file system for Java 8 and above, implementing the
+[java.nio.file](http://docs.oracle.com/javase/8/docs/api/java/nio/file/packagesummary.html)
+abstract file system APIs.
+
+Getting started
+
+
+```xml
+<dependency>
+  <groupId>me.porcelli</groupId>
+  <artifactId>jgitnio2</artifactId>
+  <version>1.0.0SNAPSHOT</version>
+</dependency>
+```
+
+Basic use
+
+
+The simplest way to use JGitNIO is to just create a new `FileSystem` instance using:
+
+```java
+import java.nio.charset.StandardCharsets;
+import java.nio.file.FileSystem;
+import java.nio.file.Files;
+import java.nio.file.Path;
+
+import me.porcelli.nio.jgit.JGitFileSystemBuilder;
+...
+
+final FileSystem fs = JGitFileSystemBuilder.newFileSystem("reponame");
+
+Path foo = fs.getPath("/foo");
+Files.createDirectory(foo);
+
+Path hello = foo.resolve("hello.txt"); // /foo/hello.txt
+
+Files.write(hello, Collections.singletonList("hello world"), StandardCharsets.UTF_8);
+
+Files.readAllLines(hello).get(0);
+```
diff git a/org.eclipse.jgit.niofs/build.properties b/org.eclipse.jgit.niofs/build.properties
new file mode 100644
index 0000000000..34d2e4d2da
 /dev/null
+++ b/org.eclipse.jgit.niofs/build.properties
@@ 0,0 +1,4 @@
+source.. = src/
+output.. = bin/
+bin.includes = METAINF/,\
+               .
diff git a/org.eclipse.jgit.niofs/pom.xml b/org.eclipse.jgit.niofs/pom.xml
new file mode 100644
index 0000000000..1408f8714b
 /dev/null
+++ b/org.eclipse.jgit.niofs/pom.xml
@@ 0,0 +1,255 @@
+<?xml version="1.0" encoding="UTF8"?>
+<!
+  Copyright (C) 2019 Red Hat, Inc. and/or its affiliates.
+  Copyright (C) 2020, Matthias Sohn <matthias.sohn@sap.com> and others
+
+ This program and the accompanying materials are made available under the
+ terms of the Eclipse Distribution License v. 1.0 which is available at
+ https://www.eclipse.org/org/documents/edlv10.php.
+
+ SPDXLicenseIdentifier: BSD3Clause
+ >
+<project xmlns="http://maven.apache.org/POM/4.0.0"
+    xmlns:xsi="http://www.w3.org/2001/XMLSchemainstance"
+    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/mavenv4_0_0.xsd">
+  <modelVersion>4.0.0</modelVersion>
+
+  <parent>
+    <groupId>org.eclipse.jgit</groupId>
+    <artifactId>org.eclipse.jgitparent</artifactId>
+    <version>5.7.0SNAPSHOT</version>
+  </parent>
+
+  <artifactId>org.eclipse.jgit.niofs</artifactId>
+  <name>JGit  NIO2 filesystem</name>
+
+  <description>
+    NIO2 filesystem
+  </description>
+
+  <properties>
+    <translatequalifier/>
+    <sourcebundlemanifest>${project.build.directory}/METAINF/SOURCEMANIFEST.MF</sourcebundlemanifest>
+  </properties>
+
+  <dependencies>
+    <!URICodec>
+    <dependency>
+      <groupId>commonscodec</groupId>
+      <artifactId>commonscodec</artifactId>
+    </dependency>
+
+    <dependency>
+      <groupId>commonsio</groupId>
+      <artifactId>commonsio</artifactId>
+    </dependency>
+
+    <dependency>
+      <groupId>com.jcraft</groupId>
+      <artifactId>jsch</artifactId>
+    </dependency>
+
+
+    <! HTTP Support >
+    <dependency>
+      <groupId>javax.servlet</groupId>
+      <artifactId>javax.servletapi</artifactId>
+      <scope>provided</scope>
+    </dependency>
+
+    <! Core Library >
+    <dependency>
+      <groupId>org.apache.sshd</groupId>
+      <artifactId>sshdosgi</artifactId>
+    </dependency>
+
+    <dependency>
+      <groupId>org.apache.sshd</groupId>
+      <artifactId>sshdscp</artifactId>
+    </dependency>
+
+    <dependency>
+      <groupId>org.eclipse.jgit</groupId>
+      <artifactId>org.eclipse.jgit</artifactId>
+      <version>${project.version}</version>
+    </dependency>
+
+    <dependency>
+      <groupId>org.eclipse.jgit</groupId>
+      <artifactId>org.eclipse.jgit.http.server</artifactId>
+      <version>${project.version}</version>
+    </dependency>
+  </dependencies>
+
+
+  <build>
+    <sourceDirectory>src/</sourceDirectory>
+
+    <pluginManagement>
+      <plugins>
+        <plugin>
+          <groupId>com.github.spotbugs</groupId>
+          <artifactId>spotbugsmavenplugin</artifactId>
+          <version>${spotbugsmavenpluginversion}</version>
+          <configuration>
+            <excludeFilterFile>findBugs/FindBugsExcludeFilter.xml</excludeFilterFile>
+          </configuration>
+        </plugin>
+      </plugins>
+    </pluginManagement>
+
+    <resources>
+      <resource>
+        <directory>.</directory>
+        <includes>
+          <include>plugin.properties</include>
+          <include>about.html</include>
+          <include>METAINF/eclipse.inf</include>
+        </includes>
+      </resource>
+      <resource>
+        <directory>resources/</directory>
+      </resource>
+    </resources>
+
+    <plugins>
+      <plugin>
+        <groupId>org.apache.maven.plugins</groupId>
+        <artifactId>mavenantrunplugin</artifactId>
+        <executions>
+          <execution>
+            <id>translatesourcequalifier</id>
+            <phase>generateresources</phase>
+            <configuration>
+              <target>
+                <copy file="METAINF/SOURCEMANIFEST.MF" tofile="${sourcebundlemanifest}" overwrite="true"/>
+                <replace file="${sourcebundlemanifest}">
+                  <replacefilter token=".qualifier" value=".${maven.build.timestamp}"/>
+                </replace>
+              </target>
+            </configuration>
+            <goals>
+              <goal>run</goal>
+            </goals>
+          </execution>
+        </executions>
+      </plugin>
+
+      <plugin>
+        <groupId>org.apache.maven.plugins</groupId>
+        <artifactId>mavensourceplugin</artifactId>
+        <inherited>true</inherited>
+        <executions>
+          <execution>
+            <id>attachsources</id>
+            <phase>processclasses</phase>
+            <goals>
+              <goal>jar</goal>
+            </goals>
+            <configuration>
+              <archive>
+                <manifestFile>${sourcebundlemanifest}</manifestFile>
+              </archive>
+            </configuration>
+          </execution>
+        </executions>
+      </plugin>
+
+      <plugin>
+        <artifactId>mavenjarplugin</artifactId>
+        <configuration>
+          <archive>
+            <manifestFile>${bundlemanifest}</manifestFile>
+          </archive>
+        </configuration>
+      </plugin>
+
+<!       <plugin>
+          <groupId>com.github.siom79.japicmp</groupId>
+          <artifactId>japicmpmavenplugin</artifactId>
+          <version>${japicmpversion}</version>
+          <configuration>
+              <oldVersion>
+                  <dependency>
+                      <groupId>${project.groupId}</groupId>
+                      <artifactId>${project.artifactId}</artifactId>
+                      <version>${jgitlastreleaseversion}</version>
+                  </dependency>
+              </oldVersion>
+              <newVersion>
+                  <file>
+                      <path>${project.build.directory}/${project.artifactId}${project.version}.jar</path>
+                  </file>
+              </newVersion>
+              <parameter>
+                  <onlyModified>true</onlyModified>
+                  <includes>
+                      <include>org.eclipse.jgit.*</include>
+                  </includes>
+                  <accessModifier>public</accessModifier>
+                  <breakBuildOnModifications>false</breakBuildOnModifications>
+                  <breakBuildOnBinaryIncompatibleModifications>false</breakBuildOnBinaryIncompatibleModifications>
+                  <onlyBinaryIncompatible>false</onlyBinaryIncompatible>
+                  <includeSynthetic>false</includeSynthetic>
+                  <ignoreMissingClasses>false</ignoreMissingClasses>
+                  <skipPomModules>true</skipPomModules>
+              </parameter>
+              <skip>false</skip>
+          </configuration>
+          <executions>
+            <execution>
+             <phase>verify</phase>
+             <goals>
+               <goal>cmp</goal>
+             </goals>
+          </execution>
+        </executions>
+      </plugin> >
+    </plugins>
+  </build>
+
+  <reporting>
+    <plugins>
+      <plugin>
+          <groupId>com.github.siom79.japicmp</groupId>
+          <artifactId>japicmpmavenplugin</artifactId>
+          <version>${japicmpversion}</version>
+          <reportSets>
+              <reportSet>
+                  <reports>
+                      <report>cmpreport</report>
+                  </reports>
+              </reportSet>
+          </reportSets>
+          <configuration>
+              <oldVersion>
+                  <dependency>
+                      <groupId>${project.groupId}</groupId>
+                      <artifactId>${project.artifactId}</artifactId>
+                      <version>${jgitlastreleaseversion}</version>
+                  </dependency>
+              </oldVersion>
+              <newVersion>
+                  <file>
+                      <path>${project.build.directory}/${project.artifactId}${project.version}.jar</path>
+                  </file>
+              </newVersion>
+              <parameter>
+                  <onlyModified>true</onlyModified>
+                  <includes>
+                      <include>org.eclipse.jgit.*</include>
+                  </includes>
+                  <accessModifier>public</accessModifier>
+                  <breakBuildOnModifications>false</breakBuildOnModifications>
+                  <breakBuildOnBinaryIncompatibleModifications>false</breakBuildOnBinaryIncompatibleModifications>
+                  <onlyBinaryIncompatible>false</onlyBinaryIncompatible>
+                  <includeSynthetic>false</includeSynthetic>
+                  <ignoreMissingClasses>false</ignoreMissingClasses>
+                  <skipPomModules>true</skipPomModules>
+              </parameter>
+              <skip>false</skip>
+          </configuration>
+      </plugin>
+    </plugins>
+  </reporting>
+</project>
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/JGitFileSystemBuilder.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/JGitFileSystemBuilder.java
new file mode 100644
index 0000000000..e33100f2b4
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/JGitFileSystemBuilder.java
@@ 0,0 +1,32 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs;
+
+import java.io.IOException;
+import java.net.URI;
+import java.nio.file.FileSystem;
+import java.util.HashMap;
+import java.util.Map;
+
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+
+public final class JGitFileSystemBuilder {
+
+	private static final JGitFileSystemProvider PROVIDER = new JGitFileSystemProvider();
+	private static final Map<String, String> DEFAULT_OPTIONS = new HashMap<>();
+
+	private JGitFileSystemBuilder() {
+		DEFAULT_OPTIONS.put("init", "true");
+	}
+
+	public static FileSystem newFileSystem(final String repoName) throws IOException {
+		return PROVIDER.newFileSystem(URI.create("git://" + repoName), DEFAULT_OPTIONS);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/cluster/ClusterMessageService.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/cluster/ClusterMessageService.java
new file mode 100644
index 0000000000..0c1cf420a1
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/cluster/ClusterMessageService.java
@@ 0,0 +1,30 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.cluster;
+
+import java.io.Serializable;
+import java.util.function.Consumer;
+
+public interface ClusterMessageService {
+
+	void connect();
+
+	<T> void createConsumer(DestinationType type, String channel, Class<T> clazz, Consumer<T> listener);
+
+	void broadcast(DestinationType type, String channel, Serializable object);
+
+	boolean isSystemClustered();
+
+	void close();
+
+	enum DestinationType {
+		PubSub, LoadBalancer
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/AmbiguousFileSystemNameException.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/AmbiguousFileSystemNameException.java
new file mode 100644
index 0000000000..7c152d266d
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/AmbiguousFileSystemNameException.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs;
+
+public class AmbiguousFileSystemNameException extends RuntimeException {
+
+	public AmbiguousFileSystemNameException() {
+	}
+
+	public AmbiguousFileSystemNameException(String msg) {
+		super(msg);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/FileSystemState.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/FileSystemState.java
new file mode 100644
index 0000000000..e501ceb921
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/FileSystemState.java
@@ 0,0 +1,16 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs;
+
+public enum FileSystemState {
+	BATCH, NORMAL;
+
+	public static String FILE_SYSTEM_STATE_ATTR = "FILE_SYSTEM_STATE_ATTR";
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/WatchContext.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/WatchContext.java
new file mode 100644
index 0000000000..1cbdcf1afb
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/WatchContext.java
@@ 0,0 +1,25 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs;
+
+import java.nio.file.Path;
+
+public interface WatchContext {
+
+	Path getPath();
+
+	Path getOldPath();
+
+	String getSessionId();
+
+	String getMessage();
+
+	String getUser();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/BranchDiff.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/BranchDiff.java
new file mode 100644
index 0000000000..1327dfd9d2
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/BranchDiff.java
@@ 0,0 +1,17 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.util.List;
+
+public interface BranchDiff {
+
+	List<FileDiff> diffs();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributeView.java
new file mode 100644
index 0000000000..70db55e7dc
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributeView.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.io.IOException;
+import java.nio.file.attribute.BasicFileAttributeView;
+
+public interface DiffAttributeView extends BasicFileAttributeView {
+
+	String name();
+
+	DiffAttributes readAttributes() throws IOException;
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributes.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributes.java
new file mode 100644
index 0000000000..cd64d7883c
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/DiffAttributes.java
@@ 0,0 +1,22 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.nio.file.attribute.BasicFileAttributes;
+
+/**
+ * Represents files attributes with the addition of a hidden field. That hidden
+ * attribute tell if a branch is hidden or not. I.E.: A Pull Request hidden
+ * branch. You should not use those branches unless you have to use them.
+ */
+public interface DiffAttributes extends BasicFileAttributes {
+
+	BranchDiff branchDiff();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/FileDiff.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/FileDiff.java
new file mode 100644
index 0000000000..28373d7554
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/FileDiff.java
@@ 0,0 +1,33 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.util.List;
+
+public interface FileDiff {
+
+	List<String> getLinesA();
+
+	List<String> getLinesB();
+
+	String getChangeType();
+
+	String getNameA();
+
+	String getNameB();
+
+	int getStartA();
+
+	int getEndA();
+
+	int getStartB();
+
+	int getEndB();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributeView.java
new file mode 100644
index 0000000000..e89bc83e07
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributeView.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.io.IOException;
+import java.nio.file.attribute.BasicFileAttributeView;
+
+public interface HiddenAttributeView extends BasicFileAttributeView {
+
+	String name();
+
+	HiddenAttributes readAttributes() throws IOException;
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributes.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributes.java
new file mode 100644
index 0000000000..b3428180d6
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/HiddenAttributes.java
@@ 0,0 +1,22 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.nio.file.attribute.BasicFileAttributes;
+
+/**
+ * Represents files attributes with the addition of a hidden field. That hidden
+ * attribute tell if a branch is hidden or not. I.E.: A Pull Request hidden
+ * branch. You should not use those branches unless you have to use them.
+ */
+public interface HiddenAttributes extends BasicFileAttributes {
+
+	boolean isHidden();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributeView.java
new file mode 100644
index 0000000000..b84c8a43ec
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributeView.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.io.IOException;
+import java.nio.file.attribute.BasicFileAttributeView;
+
+public interface VersionAttributeView extends BasicFileAttributeView {
+
+	String name();
+
+	VersionAttributes readAttributes() throws IOException;
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributes.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributes.java
new file mode 100644
index 0000000000..ebc4b4daab
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionAttributes.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.nio.file.attribute.BasicFileAttributes;
+
+/**
+ *
+ */
+public interface VersionAttributes extends BasicFileAttributes {
+
+	VersionHistory history();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionHistory.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionHistory.java
new file mode 100644
index 0000000000..b6729db060
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionHistory.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.util.List;
+
+/**
+ *
+ */
+public interface VersionHistory {
+
+	List<VersionRecord> records();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionRecord.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionRecord.java
new file mode 100644
index 0000000000..412953a284
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/attribute/VersionRecord.java
@@ 0,0 +1,30 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.attribute;
+
+import java.util.Date;
+
+/**
+ *
+ */
+public interface VersionRecord {
+
+	String id();
+
+	String author();
+
+	String email();
+
+	String comment();
+
+	Date date();
+
+	String uri();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CherryPickCopyOption.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CherryPickCopyOption.java
new file mode 100644
index 0000000000..c909bebd03
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CherryPickCopyOption.java
@@ 0,0 +1,25 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.options;
+
+import java.nio.file.CopyOption;
+
+public class CherryPickCopyOption implements CopyOption {
+
+	private final String[] commits;
+
+	public CherryPickCopyOption(final String... commits) {
+		this.commits = commits;
+	}
+
+	public String[] getCommits() {
+		return commits;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CommentedOption.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CommentedOption.java
new file mode 100644
index 0000000000..7bfd7c7aac
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/CommentedOption.java
@@ 0,0 +1,89 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.options;
+
+import java.nio.file.CopyOption;
+import java.nio.file.OpenOption;
+import java.util.Date;
+import java.util.TimeZone;
+
+public class CommentedOption implements OpenOption, CopyOption {
+
+	private final String sessionId;
+	private final String name;
+	private final String email;
+	private final String message;
+	private final Date when;
+	private final TimeZone timeZone;
+
+	public CommentedOption(final String name) {
+		this(null, name, null, null, null, null);
+	}
+
+	public CommentedOption(final String name, final String message) {
+		this(null, name, null, message, null, null);
+	}
+
+	public CommentedOption(final String name, final String email, final String message) {
+		this(null, name, email, message, null, null);
+	}
+
+	public CommentedOption(final String sessionId, final String name, final String email, final String message) {
+		this(sessionId, name, email, message, null, null);
+	}
+
+	public CommentedOption(final String name, final String email, final String message, final Date when) {
+		this(null, name, email, message, when, null);
+	}
+
+	public CommentedOption(final String sessionId, final String name, final String email, final String message,
+			final Date when) {
+		this(sessionId, name, email, message, when, null);
+	}
+
+	public CommentedOption(final String name, final String email, final String message, final Date when,
+			final TimeZone timeZone) {
+		this(null, name, email, message, when, timeZone);
+	}
+
+	public CommentedOption(final String sessionId, final String name, final String email, final String message,
+			final Date when, final TimeZone timeZone) {
+		this.sessionId = sessionId;
+		this.name = name;
+		this.email = email;
+		this.message = message;
+		this.when = when;
+		this.timeZone = timeZone;
+	}
+
+	public String getName() {
+		return name;
+	}
+
+	public String getEmail() {
+		return email;
+	}
+
+	public String getMessage() {
+		return message;
+	}
+
+	public String getSessionId() {
+		return sessionId;
+	}
+
+	public Date getWhen() {
+		return when;
+	}
+
+	public TimeZone getTimeZone() {
+		return timeZone;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/MergeCopyOption.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/MergeCopyOption.java
new file mode 100644
index 0000000000..3eea8aa07a
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/MergeCopyOption.java
@@ 0,0 +1,21 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.options;
+
+import java.nio.file.CopyOption;
+
+/**
+ * This is the CopyOption that allows to merge two branches when executing copy
+ * method. You have to apply it as the third parameter of
+ * FileSystemProvider.copy() method.
+ */
+public class MergeCopyOption implements CopyOption {
+
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/SquashOption.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/SquashOption.java
new file mode 100644
index 0000000000..2e0bd06392
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/fs/options/SquashOption.java
@@ 0,0 +1,31 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.fs.options;
+
+import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
+
+public class SquashOption extends CommentedOption {
+
+	public static final String SQUASH_ATTR = "SQUASH_ATTR";
+	public VersionRecord versionRecord;
+
+	public SquashOption(VersionRecord record) {
+		super(null, record.author(), record.email(), record.comment(), record.date(), null);
+		this.setRecord(record);
+	}
+
+	public VersionRecord getRecord() {
+		return versionRecord;
+	}
+
+	public void setRecord(final VersionRecord versionRecord) {
+		this.versionRecord = versionRecord;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractBasicFileAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractBasicFileAttributeView.java
new file mode 100644
index 0000000000..8f7be7acf4
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractBasicFileAttributeView.java
@@ 0,0 +1,130 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.IOException;
+import java.nio.file.Path;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.nio.file.attribute.BasicFileAttributes;
+import java.nio.file.attribute.FileTime;
+import java.util.HashMap;
+import java.util.HashSet;
+import java.util.Map;
+import java.util.Set;
+
+import org.eclipse.jgit.niofs.internal.util.Preconditions;
+
+public abstract class AbstractBasicFileAttributeView<P extends Path>
+		implements BasicFileAttributeView, ExtendedAttributeView {
+
+	static final String IS_REGULAR_FILE = "isRegularFile";
+	static final String IS_DIRECTORY = "isDirectory";
+	static final String IS_SYMBOLIC_LINK = "isSymbolicLink";
+	static final String IS_OTHER = "isOther";
+	static final String SIZE = "size";
+	static final String FILE_KEY = "fileKey";
+	static final String LAST_MODIFIED_TIME = "lastModifiedTime";
+	static final String LAST_ACCESS_TIME = "lastAccessTime";
+	static final String CREATION_TIME = "creationTime";
+
+	private static final Set<String> PROPERTIES = new HashSet<String>() {
+		{
+			add(IS_REGULAR_FILE);
+			add(IS_DIRECTORY);
+			add(IS_SYMBOLIC_LINK);
+			add(IS_OTHER);
+			add(SIZE);
+			add(FILE_KEY);
+			add(LAST_MODIFIED_TIME);
+			add(LAST_ACCESS_TIME);
+			add(CREATION_TIME);
+		}
+	};
+
+	protected final P path;
+
+	public AbstractBasicFileAttributeView(final P path) {
+		this.path = checkNotNull("path", path);
+	}
+
+	@Override
+	public String name() {
+		return "basic";
+	}
+
+	@Override
+	public void setTimes(final FileTime lastModifiedTime, final FileTime lastAccessTime, final FileTime createTime)
+			throws IOException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public Map<String, Object> readAllAttributes() throws IOException {
+		return readAttributes("*");
+	}
+
+	@Override
+	public Map<String, Object> readAttributes(final String... attributes) throws IOException {
+		final BasicFileAttributes attrs = readAttributes();
+
+		return new HashMap<String, Object>() {
+			{
+				for (final String attribute : attributes) {
+					Preconditions.checkNotEmpty("attribute", attribute);
+					if (attribute.equals("*") || attribute.equals(IS_REGULAR_FILE)) {
+						put(IS_REGULAR_FILE, attrs.isRegularFile());
+					}
+					if (attribute.equals("*") || attribute.equals(IS_DIRECTORY)) {
+						put(IS_DIRECTORY, attrs.isDirectory());
+					}
+					if (attribute.equals("*") || attribute.equals(IS_SYMBOLIC_LINK)) {
+						put(IS_SYMBOLIC_LINK, attrs.isSymbolicLink());
+					}
+					if (attribute.equals("*") || attribute.equals(IS_OTHER)) {
+						put(IS_OTHER, attrs.isOther());
+					}
+					if (attribute.equals("*") || attribute.equals(SIZE)) {
+						put(SIZE, new Long(attrs.size()));
+					}
+					if (attribute.equals("*") || attribute.equals(FILE_KEY)) {
+						put(FILE_KEY, attrs.fileKey());
+					}
+					if (attribute.equals("*") || attribute.equals(LAST_MODIFIED_TIME)) {
+						put(LAST_MODIFIED_TIME, attrs.lastModifiedTime());
+					}
+					if (attribute.equals("*") || attribute.equals(LAST_ACCESS_TIME)) {
+						put(LAST_ACCESS_TIME, attrs.lastAccessTime());
+					}
+					if (attribute.equals("*") || attribute.equals(CREATION_TIME)) {
+						put(CREATION_TIME, attrs.creationTime());
+					}
+					if (attribute.equals("*")) {
+						break;
+					}
+				}
+			}
+		};
+	}
+
+	@Override
+	public void setAttribute(final String attribute, final Object value) throws IOException {
+		Preconditions.checkNotEmpty("attribute", attribute);
+		Preconditions.checkCondition("invalid attribute", PROPERTIES.contains(attribute));
+
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public boolean isSerializable() {
+		return false;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractJGitBasicAttributesImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractJGitBasicAttributesImpl.java
new file mode 100644
index 0000000000..316104df47
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractJGitBasicAttributesImpl.java
@@ 0,0 +1,67 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.attribute.BasicFileAttributes;
+import java.nio.file.attribute.FileTime;
+
+public class AbstractJGitBasicAttributesImpl implements BasicFileAttributes {
+
+	private BasicFileAttributes attributes;
+
+	public AbstractJGitBasicAttributesImpl(final BasicFileAttributes attributes) {
+		this.attributes = attributes;
+	}
+
+	@Override
+	public FileTime lastModifiedTime() {
+		return attributes.lastModifiedTime();
+	}
+
+	@Override
+	public FileTime lastAccessTime() {
+		return attributes.lastAccessTime();
+	}
+
+	@Override
+	public FileTime creationTime() {
+		return attributes.creationTime();
+	}
+
+	@Override
+	public boolean isRegularFile() {
+		return attributes.isRegularFile();
+	}
+
+	@Override
+	public boolean isDirectory() {
+		return attributes.isDirectory();
+	}
+
+	@Override
+	public boolean isSymbolicLink() {
+		return attributes.isSymbolicLink();
+	}
+
+	@Override
+	public boolean isOther() {
+		return attributes.isOther();
+	}
+
+	@Override
+	public long size() {
+		return attributes.size();
+	}
+
+	@Override
+	public Object fileKey() {
+		return attributes.fileKey();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractPath.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractPath.java
new file mode 100644
index 0000000000..75a48af7ed
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AbstractPath.java
@@ 0,0 +1,677 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkInstanceOf;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.io.IOError;
+import java.io.IOException;
+import java.net.URI;
+import java.nio.file.ClosedWatchServiceException;
+import java.nio.file.FileSystem;
+import java.nio.file.InvalidPathException;
+import java.nio.file.LinkOption;
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchKey;
+import java.nio.file.WatchService;
+import java.nio.file.attribute.AttributeView;
+import java.util.AbstractMap;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.Iterator;
+import java.util.List;
+import java.util.Map;
+import java.util.NoSuchElementException;
+import java.util.regex.Matcher;
+
+import org.apache.commons.io.FilenameUtils;
+import org.eclipse.jgit.niofs.internal.util.EncodingUtil;
+
+public abstract class AbstractPath<FS extends FileSystem> implements Path, AttrHolder {
+
+	protected final FS fs;
+	protected final boolean usesWindowsFormat;
+
+	protected final boolean isAbsolute;
+	protected final byte[] path;
+	protected final boolean isRoot;
+	protected final boolean isRealPath;
+	protected final boolean isNormalized;
+	protected final String host;
+	protected final List<Map.Entry<Integer, Integer>> offsets = new ArrayList<>();
+	protected final AttrsStorage attrsStorage = new AttrsStorageImpl();
+	protected String toStringFormat;
+	protected File file = null;
+
+	protected AbstractPath(final FS fs, final File file) {
+		this(checkNotNull("fs", fs), checkNotNull("file", file).getAbsolutePath(), "", false, false, true);
+	}
+
+	protected AbstractPath(final FS fs, final String path, final String host, boolean isRoot, boolean isRealPath,
+			boolean isNormalized) {
+		checkNotNull("path", path);
+		this.fs = checkNotNull("fs", fs);
+		this.host = checkNotNull("host", host);
+		this.isRealPath = isRealPath;
+		this.isNormalized = isNormalized;
+		this.usesWindowsFormat = path.indexOf('\\') != 1;
+
+		final RootInfo rootInfo = setupRoot(fs, path, host, isRoot);
+		this.path = rootInfo.path;
+
+		checkNotNull("rootInfo", rootInfo);
+
+		this.isAbsolute = rootInfo.isAbsolute;
+
+		int lastOffset = rootInfo.startOffset;
+		for (int i = lastOffset; i < this.path.length; i++) {
+			final byte b = this.path[i];
+			if (b == getSeparator()) {
+				offsets.add(new AbstractMap.SimpleEntry<>(lastOffset, i));
+				i++;
+				lastOffset = i;
+			}
+		}
+
+		if (lastOffset < this.path.length) {
+			offsets.add(new AbstractMap.SimpleEntry<>(lastOffset, this.path.length));
+		}
+
+		this.isRoot = rootInfo.isRoot;
+	}
+
+	protected abstract Path newPath(FS fs, String substring, String host, boolean realPath, boolean isNormalized);
+
+	protected abstract Path newRoot(FS fs, String substring, String host, boolean realPath);
+
+	protected abstract RootInfo setupRoot(final FS fs, final String path, final String host, final boolean isRoot);
+
+	@Override
+	public FS getFileSystem() {
+		return fs;
+	}
+
+	@Override
+	public boolean isAbsolute() {
+		return isAbsolute;
+	}
+
+	@Override
+	public Path getRoot() {
+		if (isRoot) {
+			return this;
+		}
+		if (isAbsolute || !host.isEmpty()) {
+			return newRoot(fs, substring(1), host, isRealPath);
+		}
+		return null;
+	}
+
+	private String substring(int index) {
+		final byte[] result;
+		if (index == 1) {
+			result = new byte[offsets.get(0).getKey()];
+			System.arraycopy(path, 0, result, 0, result.length);
+		} else {
+			final Map.Entry<Integer, Integer> offset = offsets.get(index);
+			result = new byte[offset.getValue()  offset.getKey()];
+			System.arraycopy(path, offset.getKey(), result, 0, result.length);
+		}
+
+		return new String(result);
+	}
+
+	private String substring(int beginIndex, int endIndex) {
+		final int initPos;
+		if (beginIndex == 1) {
+			initPos = 0;
+		} else {
+			initPos = offsets.get(beginIndex).getKey();
+		}
+		final Map.Entry<Integer, Integer> offsetEnd = offsets.get(endIndex);
+		final byte[] result = new byte[offsetEnd.getValue()  initPos];
+		System.arraycopy(path, initPos, result, 0, result.length);
+
+		return new String(result);
+	}
+
+	@Override
+	public Path getFileName() {
+		if (getNameCount() == 0) {
+			return null;
+		}
+		return getName(getNameCount()  1);
+	}
+
+	@Override
+	public Path getParent() {
+		if (getNameCount() <= 0) {
+			return null;
+		}
+		if (getNameCount() == 1) {
+			return getRoot();
+		}
+		return newPath(fs, substring(1, getNameCount()  2), host, isRealPath, isNormalized);
+	}
+
+	@Override
+	public int getNameCount() {
+		return offsets.size();
+	}
+
+	@Override
+	public Path getName(int index) throws IllegalArgumentException {
+		if (isRoot && index > 0) {
+			throw new IllegalArgumentException("Invalid index argument: " + index);
+		}
+		if (index < 0) {
+			throw new IllegalArgumentException("Invalid index argument: " + index);
+		}
+		if (index >= offsets.size()) {
+			throw new IllegalArgumentException(
+					"Invalid index argument: " + index + ", max allowed is " + (offsets.size()  1));
+		}
+
+		return newPath(fs, substring(index), host, isRealPath, false);
+	}
+
+	@Override
+	public Path subpath(int beginIndex, int endIndex) throws IllegalArgumentException {
+		if (beginIndex < 0) {
+			throw new IllegalArgumentException("Invalid beginIndex argument: " + beginIndex);
+		}
+		if (beginIndex >= offsets.size()) {
+			throw new IllegalArgumentException("Invalid beginIndex argument: " + beginIndex);
+		}
+		if (endIndex > offsets.size()) {
+			throw new IllegalArgumentException("Invalid endIndex argument: " + endIndex);
+		}
+		if (beginIndex >= endIndex) {
+			throw new IllegalArgumentException("Invalid arguments, beginIndex must be < endIndex, but they were: "
+					+ "bI " + beginIndex + ", eI " + endIndex);
+		}
+
+		return newPath(fs, substring(beginIndex, endIndex  1), host, isRealPath, false);
+	}
+
+	@Override
+	public URI toUri() throws IOError, SecurityException {
+		if (!isAbsolute()) {
+			return toAbsolutePath().toUri();
+		}
+		return URI.create(fs.provider().getScheme() + "://" + host + toURIString());
+	}
+
+	private String toURIString() {
+		if (usesWindowsFormat) {
+			return encodePath("/" + toString().replace("\\", "/"));
+		}
+		return encodePath(new String(path));
+	}
+
+	private String encodePath(final String s) {
+		return EncodingUtil.encodePath(s);
+	}
+
+	@Override
+	public Path toAbsolutePath() throws IOError, SecurityException {
+		if (isAbsolute()) {
+			return this;
+		}
+		if (host.isEmpty()) {
+			return newPath(fs, FilenameUtils.normalize(defaultDirectory() + toString(), !usesWindowsFormat), host,
+					isRealPath, true);
+		}
+		return newPath(fs, defaultDirectory() + toString(false), host, isRealPath, true);
+	}
+
+	protected abstract String defaultDirectory();
+
+	@Override
+	public Path toRealPath(final LinkOption... options) throws IOException, SecurityException {
+		if (isRealPath) {
+			return this;
+		}
+		return newPath(fs, FilenameUtils.normalize(toString(), !usesWindowsFormat), host, true, true);
+	}
+
+	@Override
+	public Iterator<Path> iterator() {
+		return new Iterator<Path>() {
+			private int i = 0;
+
+			@Override
+			public boolean hasNext() {
+				return i < getNameCount();
+			}
+
+			@Override
+			public Path next() {
+				if (i < getNameCount()) {
+					Path result = getName(i);
+					i++;
+					return result;
+				} else {
+					throw new NoSuchElementException();
+				}
+			}
+
+			@Override
+			public void remove() {
+				throw new UnsupportedOperationException();
+			}
+		};
+	}
+
+	@Override
+	public boolean startsWith(final Path other) {
+		checkNotNull("other", other);
+
+		if (other.isAbsolute() != isAbsolute()) {
+			return false;
+		}
+
+		if (!(other instanceof AbstractPath)) {
+			return false;
+		}
+
+		final AbstractPath<?> that = (AbstractPath) other;
+
+		int thisNameCount = getNameCount();
+		int thatNameCount = that.getNameCount();
+
+		if (thatNameCount > thisNameCount) {
+			return false;
+		}
+
+		List<String> thisNames = getNamesIncludingRoot();
+		List<String> thatNames = that.getNamesIncludingRoot();
+
+		for (int i = 0; i < thatNames.size(); i++) {
+			if (!thisNames.get(i).equals(thatNames.get(i))) {
+				return false;
+			}
+		}
+
+		return true;
+	}
+
+	@Override
+	public boolean startsWith(final String other) throws InvalidPathException {
+		checkNotNull("other", other);
+		return startsWith(getFileSystem().getPath(other));
+	}
+
+	@Override
+	public boolean endsWith(final Path other) {
+		checkNotNull("other", other);
+
+		if (!(other instanceof AbstractPath)) {
+			return false;
+		}
+
+		final AbstractPath<?> that = (AbstractPath) other;
+
+		if (that.isAbsolute()) {
+			if (!isAbsolute()) {
+				return false;
+			}
+			if (!equalRoots(that)) {
+				return false;
+			}
+		}
+
+		if (endsWithSeparator() != that.endsWithSeparator()) {
+			return false;
+		}
+
+		int thisNameCount = getNameCount();
+		int thatNameCount = that.getNameCount();
+
+		if (thatNameCount > thisNameCount) {
+			return false;
+		}
+
+		if (thisNameCount > 0 && thatNameCount == 0) {
+			return false;
+		}
+
+		if (thatNameCount == thisNameCount) {
+			if (thisNameCount == 0) {
+				return true;
+			}
+		} else {
+			if (that.isAbsolute()) {
+				return false;
+			}
+		}
+
+		int thisPosition = thisNameCount;
+		int thatPosition = thatNameCount;
+
+		while (thatPosition > 0) {
+			if (!getName(thisPosition).equals(that.getName(thatPosition))) {
+				return false;
+			}
+		}
+
+		return true;
+	}
+
+	@Override
+	public boolean endsWith(final String other) throws InvalidPathException {
+		checkNotNull("other", other);
+		return endsWith(getFileSystem().getPath(other));
+	}
+
+	@Override
+	public Path normalize() {
+		if (isNormalized) {
+			return this;
+		}
+
+		return newPath(fs, FilenameUtils.normalize(new String(path), !usesWindowsFormat), host, isRealPath, true);
+	}
+
+	@Override
+	public Path resolve(final Path other) {
+		checkNotNull("other", other);
+		if (other.isAbsolute()) {
+			return other;
+		}
+		if (other.toString().trim().length() == 0) {
+			return this;
+		}
+
+		final StringBuilder sb = new StringBuilder();
+		sb.append(new String(path));
+		if (path[path.length  1] != getSeparator()) {
+			sb.append(getSeparator());
+		}
+		sb.append(other.toString());
+
+		return newPath(fs, sb.toString(), host, isRealPath, false);
+	}
+
+	@Override
+	public Path resolve(final String other) throws InvalidPathException {
+		checkNotNull("other", other);
+		return resolve(newPath(fs, other, host, isRealPath, false));
+	}
+
+	@Override
+	public Path resolveSibling(final Path other) {
+		checkNotNull("other", other);
+
+		final Path parent = this.getParent();
+		if (parent == null || other.isAbsolute()) {
+			return other;
+		}
+
+		return parent.resolve(other);
+	}
+
+	@Override
+	public Path resolveSibling(final String other) throws InvalidPathException {
+		checkNotNull("other", other);
+
+		return resolveSibling(newPath(fs, other, host, isRealPath, false));
+	}
+
+	@Override
+	public Path relativize(final Path otherx) throws IllegalArgumentException {
+		checkNotNull("otherx", otherx);
+		final AbstractPath other = checkInstanceOf("otherx", otherx, AbstractPath.class);
+
+		if (isAbsolute() != other.isAbsolute()) {
+			throw new IllegalArgumentException(
+					"Could not relativize path 'otherx', 'isAbsolute()' for 'this' and 'otherx' should be equal.");
+		}
+
+		if (isAbsolute() && !equalRoots(other)) {
+			throw new IllegalArgumentException(
+					"Could not relativize path 'otherx', 'getRoot()' for 'this' and 'otherx' should be equal.");
+		}
+
+		if (getNamesIncludingRoot().equals(other.getNamesIncludingRoot())) {
+			return emptyPath();
+		}
+
+		if (this.path.length == 0) {
+			return other;
+		}
+
+		int n = (getNameCount() > other.getNameCount()) ? other.getNameCount() : getNameCount();
+		int i = 0;
+		while (i < n) {
+			if (!this.getName(i).equals(other.getName(i))) {
+				break;
+			}
+			i++;
+		}
+
+		int numberOfDots = getNameCount()  i;
+
+		if (numberOfDots == 0 && i < other.getNameCount()) {
+			return other.subpath(i, other.getNameCount());
+		}
+
+		final StringBuilder sb = new StringBuilder();
+		while (numberOfDots > 0) {
+			sb.append("..");
+			if (numberOfDots > 1) {
+				sb.append(getSeparator());
+			}
+			numberOfDots;
+		}
+
+		if (i < other.getNameCount()) {
+			if (sb.length() > 0) {
+				sb.append(getSeparator());
+			}
+			String subpath = ((AbstractPath<FS>) other.subpath(i, other.getNameCount())).toString(false);
+			subpath = other.getSeparator() == getSeparator() ? subpath
+					: subpath.replaceAll(other.quoteSeparator(), quoteSeparator());
+			sb.append(subpath);
+		}
+
+		return newPath(fs, sb.toString(), host, isRealPath, false);
+	}
+
+	private Path emptyPath() {
+		return newPath(fs, "", host, isRealPath, true);
+	}
+
+	@Override
+	public int compareTo(final Path other) {
+		checkNotNull("other", other);
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers)
+			throws UnsupportedOperationException, IllegalArgumentException, ClosedWatchServiceException, IOException,
+			SecurityException {
+		return watcher.poll();
+	}
+
+	@Override
+	public WatchKey register(WatchService watcher, WatchEvent.Kind<?>... events) throws UnsupportedOperationException,
+			IllegalArgumentException, ClosedWatchServiceException, IOException, SecurityException {
+		return watcher.poll();
+	}
+
+	@Override
+	public String toString() {
+		if (toStringFormat == null) {
+			toStringFormat = toString(false);
+		}
+		return toStringFormat;
+	}
+
+	public String toString(boolean addHost) {
+		if (!addHost || host.isEmpty()) {
+			return new String(path);
+		}
+		if (isAbsolute) {
+			return host + new String(path);
+		} else {
+			return host + ":" + new String(path);
+		}
+	}
+
+	private char getSeparator() {
+		if (usesWindowsFormat) {
+			return '\\';
+		}
+		return '/';
+	}
+
+	public void clearCache() {
+		file = null;
+		attrsStorage.clear();
+	}
+
+	@Override
+	public boolean equals(final Object o) {
+		checkNotNull("o", o);
+
+		if (this == o) {
+			return true;
+		}
+		if (!(o instanceof AbstractPath)) {
+			return false;
+		}
+
+		AbstractPath other = (AbstractPath) o;
+
+		if (isAbsolute != other.isAbsolute) {
+			return false;
+		}
+		if (isRealPath != other.isRealPath) {
+			return false;
+		}
+		if (isRoot != other.isRoot) {
+			return false;
+		}
+		if (usesWindowsFormat != other.usesWindowsFormat) {
+			return false;
+		}
+		if (!host.equals(other.host)) {
+			return false;
+		}
+		if (!fs.equals(other.fs)) {
+			return false;
+		}
+
+		if (!usesWindowsFormat && !Arrays.equals(path, other.path)) {
+			return false;
+		}
+
+		if (usesWindowsFormat && !(new String(path).equalsIgnoreCase(new String(other.path)))) {
+			return false;
+		}
+
+		return true;
+	}
+
+	@Override
+	public int hashCode() {
+		int result = fs != null ? fs.hashCode() : 0;
+		result = 31 * result + (usesWindowsFormat ? 1 : 0);
+		result = 31 * result + (isAbsolute ? 1 : 0);
+
+		if (!usesWindowsFormat) {
+			result = 31 * result + (path != null ? Arrays.hashCode(path) : 0);
+		} else {
+			result = 31 * result + (path != null ? new String(path).toLowerCase().hashCode() : 0);
+		}
+
+		result = 31 * result + (isRoot ? 1 : 0);
+		result = 31 * result + (isRealPath ? 1 : 0);
+		result = 31 * result + (isNormalized ? 1 : 0);
+		return result;
+	}
+
+	public String getHost() {
+		return host;
+	}
+
+	public boolean isRealPath() {
+		return isRealPath;
+	}
+
+	@Override
+	public AttrsStorage getAttrStorage() {
+		return attrsStorage;
+	}
+
+	@Override
+	public <V extends AttributeView> void addAttrView(final V view) {
+		attrsStorage.addAttrView(view);
+	}
+
+	@Override
+	public <V extends AttributeView> V getAttrView(final Class<V> type) {
+		return attrsStorage.getAttrView(type);
+	}
+
+	@Override
+	public <V extends AttributeView> V getAttrView(final String name) {
+		return (V) attrsStorage.getAttrView(name);
+	}
+
+	public static class RootInfo {
+
+		private final int startOffset;
+		private final boolean isAbsolute;
+		private final boolean isRoot;
+		private final byte[] path;
+
+		public RootInfo(int startOffset, boolean isAbsolute, boolean isRoot, byte[] path) {
+			this.startOffset = startOffset;
+			this.isAbsolute = isAbsolute;
+			this.isRoot = isRoot;
+			this.path = path;
+		}
+	}
+
+	private List<String> getNamesIncludingRoot() {
+		String[] names = toString().split(String.valueOf(quoteSeparator()));
+		if (!usesWindowsFormat && isAbsolute() && names.length > 0) {
+			return Arrays.asList(Arrays.copyOfRange(names, 1, names.length));
+		}
+		return Arrays.asList(names);
+	}
+
+	private static String stripAllSeparators(String path) {
+		return path.replaceAll("/", "").replaceAll(Matcher.quoteReplacement("\\"), "");
+	}
+
+	private boolean equalRoots(AbstractPath other) {
+		String thisRootName = stripAllSeparators(getRoot().toString());
+		String otherRootName = stripAllSeparators(other.getRoot().toString());
+		if (!thisRootName.equals(otherRootName) || !host.equals(other.getHost())) {
+			return false;
+		}
+		return true;
+	}
+
+	private String quoteSeparator() {
+		return Matcher.quoteReplacement(String.valueOf(getSeparator()));
+	}
+
+	private boolean endsWithSeparator() {
+		return path[path.length  1] == getSeparator();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrHolder.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrHolder.java
new file mode 100644
index 0000000000..e3fa40d880
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrHolder.java
@@ 0,0 +1,23 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.attribute.AttributeView;
+
+public interface AttrHolder {
+
+	AttrsStorage getAttrStorage();
+
+	<V extends AttributeView> void addAttrView(final V view);
+
+	<V extends AttributeView> V getAttrView(final Class<V> type);
+
+	<V extends AttributeView> V getAttrView(final String name);
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorage.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorage.java
new file mode 100644
index 0000000000..72baadcad0
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorage.java
@@ 0,0 +1,15 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+public interface AttrsStorage extends AttrHolder {
+
+	void clear();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorageImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorageImpl.java
new file mode 100644
index 0000000000..277d1cc876
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/AttrsStorageImpl.java
@@ 0,0 +1,57 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.attribute.AttributeView;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.util.HashMap;
+import java.util.Map;
+
+public class AttrsStorageImpl implements AttrsStorage {
+
+	final Properties content = new Properties();
+	final Map<String, AttributeView> viewsNameIndex = new HashMap<String, AttributeView>();
+	final Map<Class<?>, AttributeView> viewsTypeIndex = new HashMap<Class<?>, AttributeView>();
+
+	@Override
+	public AttrsStorage getAttrStorage() {
+		return this;
+	}
+
+	@Override
+	public <V extends AttributeView> void addAttrView(final V view) {
+		viewsNameIndex.put(view.name(), view);
+		if (view instanceof ExtendedAttributeView) {
+			final ExtendedAttributeView extendedView = (ExtendedAttributeView) view;
+			for (Class<? extends BasicFileAttributeView> type : extendedView.viewTypes()) {
+				viewsTypeIndex.put(type, view);
+			}
+		} else {
+			viewsTypeIndex.put(view.getClass(), view);
+		}
+	}
+
+	@Override
+	public <V extends AttributeView> V getAttrView(final Class<V> type) {
+		return (V) viewsTypeIndex.get(type);
+	}
+
+	@Override
+	public <V extends AttributeView> V getAttrView(final String name) {
+		return (V) viewsNameIndex.get(name);
+	}
+
+	@Override
+	public void clear() {
+		viewsNameIndex.clear();
+		viewsTypeIndex.clear();
+		content.clear();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributeViewImpl.java
new file mode 100644
index 0000000000..a662a5abc7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributeViewImpl.java
@@ 0,0 +1,56 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+
+import java.io.IOException;
+import java.nio.file.Path;
+import java.util.HashMap;
+import java.util.Map;
+
+import org.eclipse.jgit.niofs.fs.attribute.DiffAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
+
+public abstract class DiffAttributeViewImpl<P extends Path> extends AbstractBasicFileAttributeView<P>
+		implements DiffAttributeView {
+
+	public static final String DIFF = "diff";
+
+	public DiffAttributeViewImpl(final P path) {
+		super(path);
+	}
+
+	@Override
+	public String name() {
+		return DIFF;
+	}
+
+	@Override
+	public Map<String, Object> readAttributes(final String... attributes) throws IOException {
+		final DiffAttributes attrs = readAttributes();
+
+		return new HashMap<String, Object>(super.readAttributes(attributes)) {
+			{
+				for (final String attribute : attributes) {
+					checkNotEmpty("attribute", attribute);
+
+					if (attribute.equals("*") || attribute.equals(DIFF)) {
+						put(DIFF, attrs.branchDiff());
+					}
+
+					if (attribute.equals("*")) {
+						break;
+					}
+				}
+			}
+		};
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributesImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributesImpl.java
new file mode 100644
index 0000000000..1d8a2c4c9b
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/DiffAttributesImpl.java
@@ 0,0 +1,30 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.attribute.BasicFileAttributes;
+
+import org.eclipse.jgit.niofs.fs.attribute.BranchDiff;
+import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
+
+public class DiffAttributesImpl extends AbstractJGitBasicAttributesImpl implements DiffAttributes {
+
+	private final BranchDiff branchDiff;
+
+	public DiffAttributesImpl(final BasicFileAttributes attributes, final BranchDiff branchDiff) {
+		super(attributes);
+		this.branchDiff = branchDiff;
+	}
+
+	@Override
+	public BranchDiff branchDiff() {
+		return branchDiff;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Disposable.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Disposable.java
new file mode 100644
index 0000000000..1300b218ef
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Disposable.java
@@ 0,0 +1,20 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+
+/**
+ * The Disposable interface is used for lifecycle management of resources.
+ */
+public interface Disposable {
+
+	void dispose() throws IOException;
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/ExtendedAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/ExtendedAttributeView.java
new file mode 100644
index 0000000000..66bb599d81
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/ExtendedAttributeView.java
@@ 0,0 +1,27 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.attribute.AttributeView;
+import java.util.Map;
+
+public interface ExtendedAttributeView extends AttributeView {
+
+	Map<String, Object> readAllAttributes() throws IOException;
+
+	Map<String, Object> readAttributes(final String... attributes) throws IOException;
+
+	void setAttribute(final String attribute, final Object value) throws IOException;
+
+	Class[] viewTypes();
+
+	boolean isSerializable();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileDiffImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileDiffImpl.java
new file mode 100644
index 0000000000..de2ee4ab75
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileDiffImpl.java
@@ 0,0 +1,156 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.util.List;
+
+import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
+
+/**
+ * Represents difference between two files. This is just a segment of the file,
+ * not necessary the differences of the whole file.
+ */
+public class FileDiffImpl implements FileDiff {
+
+	private List<String> linesA;
+	private List<String> linesB;
+	private String changeType;
+	private String nameA;
+	private String nameB;
+	private int startA;
+	private int endA;
+	private int startB;
+	private int endB;
+
+	public FileDiffImpl(final String nameA, final String nameB, final int startA, final int endA, final int startB,
+			final int endB, final String changeType, final List<String> linesA, final List<String> linesB) {
+
+		this.nameA = checkNotEmpty("nameA", nameA);
+		this.nameB = checkNotEmpty("nameB", nameB);
+		this.startA = startA;
+		this.endA = endA;
+		this.startB = startB;
+		this.endB = endB;
+		this.changeType = checkNotEmpty("nameA", changeType);
+		this.linesA = checkNotNull("linesA", linesA);
+		this.linesB = checkNotNull("linesB", linesB);
+	}
+
+	@Override
+	public List<String> getLinesA() {
+		return linesA;
+	}
+
+	@Override
+	public List<String> getLinesB() {
+		return linesB;
+	}
+
+	@Override
+	public String getChangeType() {
+		return changeType;
+	}
+
+	@Override
+	public String getNameA() {
+		return nameA;
+	}
+
+	@Override
+	public String getNameB() {
+		return nameB;
+	}
+
+	@Override
+	public int getStartA() {
+		return startA;
+	}
+
+	@Override
+	public int getEndA() {
+		return endA;
+	}
+
+	@Override
+	public int getStartB() {
+		return startB;
+	}
+
+	@Override
+	public int getEndB() {
+		return endB;
+	}
+
+	@Override
+	public String toString() {
+
+		final String linesFromA = this.getLinesA().stream().reduce("",
+				(acum, elem) > acum += "" + new String(elem.getBytes()) + "\n");
+		final String linesFromB = this.getLinesB().stream().reduce("",
+				(acum, elem) > acum += "+" + new String(elem.getBytes()) + "\n");
+
+		StringBuilder builder = new StringBuilder();
+		builder.append("FileDiff { \n");
+		builder.append(this.getChangeType());
+		builder.append(" , \n");
+
+		builder.append(this.getNameA());
+		builder.append(" > ");
+		builder.append("( " + this.getStartA() + " , " + this.getEndA() + " )");
+		builder.append("[ " + linesFromA + " ]");
+		builder.append(" || ");
+		builder.append(this.getNameB());
+		builder.append(" > ");
+		builder.append("( " + this.getStartB() + " , " + this.getEndB() + " )");
+		builder.append("[ " + linesFromB + " ]");
+		builder.append("}");
+
+		return builder.toString();
+	}
+
+	@Override
+	public int hashCode() {
+		int result = Integer.hashCode(startA);
+		result = ~~result;
+		result = 31 * result + (Integer.hashCode(endA));
+		result = ~~result;
+		result = 31 * result + (Integer.hashCode(startB));
+		result = ~~result;
+		result = 31 * result + (Integer.hashCode(endB));
+		result = ~~result;
+		result = 31 * result + (nameA.hashCode());
+		result = ~~result;
+		result = 31 * result + (nameB.hashCode());
+		result = ~~result;
+		result = 31 * result + (changeType.hashCode());
+		result = ~~result;
+		result = 31 * result + (linesA.hashCode());
+		result = ~~result;
+		result = 31 * result + (linesB.hashCode());
+		result = ~~result;
+		return result;
+	}
+
+	@Override
+	public boolean equals(final Object obj) {
+		if (obj instanceof FileDiffImpl) {
+			FileDiffImpl external = (FileDiffImpl) obj;
+			return this.startA == external.startA && this.endA == external.endA && this.startB == external.startB
+					&& this.endB == external.endB && this.changeType.equals(external.changeType)
+					&& this.nameA.equals(external.nameA) && this.nameB.equals(external.nameB)
+					&& this.linesA.equals(external.linesA) && this.linesB.equals(external.getLinesB());
+		} else {
+			return super.equals(obj);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemId.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemId.java
new file mode 100644
index 0000000000..eac08bd690
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemId.java
@@ 0,0 +1,15 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+public interface FileSystemId {
+
+	String id();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLock.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLock.java
new file mode 100644
index 0000000000..7b2279fef8
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLock.java
@@ 0,0 +1,136 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.io.RandomAccessFile;
+import java.net.URI;
+import java.nio.ByteBuffer;
+import java.nio.channels.FileChannel;
+import java.nio.channels.FileLock;
+import java.nio.file.FileAlreadyExistsException;
+import java.nio.file.Files;
+import java.nio.file.Path;
+import java.nio.file.Paths;
+import java.util.concurrent.TimeUnit;
+import java.util.concurrent.locks.ReentrantLock;
+
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+/**
+ * File system Lock. To instantiate a new Lock use {@link FileSystemLockManager}
+ */
+public class FileSystemLock {
+
+	private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemLock.class);
+
+	ReentrantLock lock = new ReentrantLock(true);
+	private FileLock physicalLock;
+	private Path lockFile;
+	private FileChannel fileChannel;
+	private long lastAccessMilliseconds;
+	private long lastAccessThresholdMilliseconds;
+	private final URI repoURI;
+	private String lockName;
+
+	protected FileSystemLock(File directory, String lockName, TimeUnit lastAccessTimeUnit, long lastAccessThreshold) {
+		this.lockName = lockName;
+		repoURI = directory.toURI();
+		this.lockFile = createLockInfra(repoURI);
+		this.lastAccessThresholdMilliseconds = lastAccessTimeUnit.toMillis(lastAccessThreshold);
+	}
+
+	void registerAccess() {
+		lastAccessMilliseconds = System.currentTimeMillis();
+	}
+
+	public void lock() {
+		registerAccess();
+		lock.lock();
+
+		if (needToCreatePhysicalLock()) {
+			physicalLockOnFS();
+		}
+	}
+
+	public void unlock() {
+		registerAccess();
+		if (lock.isLocked()) {
+			if (releasePhysicalLock()) {
+				physicalUnLockOnFS();
+			}
+			lock.unlock();
+		}
+	}
+
+	public boolean hasBeenInUse() {
+		if (recentlyAccessed()) {
+			return true;
+		}
+		return lock.isLocked();
+	}
+
+	private boolean recentlyAccessed() {
+		return (System.currentTimeMillis()  lastAccessMilliseconds) < lastAccessThresholdMilliseconds;
+	}
+
+	private boolean needToCreatePhysicalLock() {
+		return ((physicalLock == null || !physicalLock.isValid()) && lock.getHoldCount() == 1);
+	}
+
+	private boolean releasePhysicalLock() {
+		return physicalLock != null && physicalLock.isValid() && lock.isLocked() && lock.getHoldCount() == 1;
+	}
+
+	void physicalLockOnFS() {
+		try {
+			File file = lockFile.toFile();
+			RandomAccessFile raf = new RandomAccessFile(file, "rw");
+			fileChannel = raf.getChannel();
+			physicalLock = fileChannel.lock();
+			fileChannel.position(0);
+			fileChannel.write(ByteBuffer.wrap("locked".getBytes()));
+		} catch (Exception e) {
+			LOGGER.error("Error during lock of FS [" + repoURI.toString() + "  " + this.getLockName() + "]", e);
+			throw new RuntimeException(e);
+		}
+	}
+
+	void physicalUnLockOnFS() {
+		try {
+			physicalLock.release();
+			fileChannel.close();
+			fileChannel = null;
+			physicalLock = null;
+		} catch (Exception e) {
+			LOGGER.error("Error during unlock of FS [" + repoURI.toString() + "  " + this.getLockName() + "]", e);
+			throw new RuntimeException(e);
+		}
+	}
+
+	Path createLockInfra(URI uri) {
+		Path lockFile = null;
+		try {
+			Path repo = Paths.get(uri);
+			lockFile = repo.resolve(getLockName());
+			Files.createFile(lockFile);
+		} catch (FileAlreadyExistsException ignored) {
+		} catch (Exception e) {
+			LOGGER.error("Error building lock infra [" + toString() + "]", e);
+			throw new RuntimeException(e);
+		}
+		return lockFile;
+	}
+
+	protected String getLockName() {
+		return this.lockName;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockException.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockException.java
new file mode 100644
index 0000000000..f07f830796
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockException.java
@@ 0,0 +1,17 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+public class FileSystemLockException extends RuntimeException {
+
+	public FileSystemLockException(String message) {
+		super(message);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockManager.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockManager.java
new file mode 100644
index 0000000000..085c4a9659
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemLockManager.java
@@ 0,0 +1,36 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.util.Map;
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.concurrent.TimeUnit;
+
+public class FileSystemLockManager {
+
+	final Map<String, FileSystemLock> fileSystemsLocks = new ConcurrentHashMap<>();
+
+	private static class LazyHolder {
+
+		static final FileSystemLockManager INSTANCE = new FileSystemLockManager();
+	}
+
+	public static FileSystemLockManager getInstance() {
+		return LazyHolder.INSTANCE;
+	}
+
+	public FileSystemLock getFileSystemLock(File directory, String lockName, TimeUnit lastAccessTimeUnit,
+			long lastAccessThreshold) {
+
+		return fileSystemsLocks.computeIfAbsent(directory.getAbsolutePath(),
+				key > new FileSystemLock(directory, lockName, lastAccessTimeUnit, lastAccessThreshold));
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemStateAware.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemStateAware.java
new file mode 100644
index 0000000000..5dd4c6c196
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/FileSystemStateAware.java
@@ 0,0 +1,17 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import org.eclipse.jgit.niofs.fs.FileSystemState;
+
+public interface FileSystemStateAware {
+
+	FileSystemState getState();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HTTPProxyAuthenticator.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HTTPProxyAuthenticator.java
new file mode 100644
index 0000000000..bd966ac81c
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HTTPProxyAuthenticator.java
@@ 0,0 +1,43 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.net.Authenticator;
+import java.net.PasswordAuthentication;
+
+public class HTTPProxyAuthenticator extends Authenticator {
+
+	private final String httpProxyUser;
+	private final String httpProxyPassword;
+	private final String httpsProxyUser;
+	private final String httpsProxyPassword;
+
+	public HTTPProxyAuthenticator(final String httpProxyUser, final String httpProxyPassword,
+			final String httpsProxyUser, final String httpsProxyPassword) {
+		this.httpProxyUser = httpProxyUser;
+		this.httpProxyPassword = httpProxyPassword;
+		this.httpsProxyUser = httpsProxyUser;
+		this.httpsProxyPassword = httpsProxyPassword;
+	}
+
+	@Override
+	protected PasswordAuthentication getPasswordAuthentication() {
+		if (getRequestorType() == RequestorType.PROXY) {
+			final String protocol = getRequestingProtocol();
+
+			if (protocol.equalsIgnoreCase("http") && (httpProxyUser != null && httpProxyPassword != null)) {
+				return new PasswordAuthentication(httpProxyUser, httpProxyPassword.toCharArray());
+			} else if (protocol.equalsIgnoreCase("https") && (httpsProxyUser != null && httpsProxyPassword != null)) {
+				return new PasswordAuthentication(httpsProxyUser, httpsProxyPassword.toCharArray());
+			}
+		}
+		return super.getPasswordAuthentication();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributeViewImpl.java
new file mode 100644
index 0000000000..30483d7da9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributeViewImpl.java
@@ 0,0 +1,63 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+
+import java.io.IOException;
+import java.nio.file.Path;
+import java.util.HashMap;
+import java.util.Map;
+
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
+
+/**
+ * This is a view that extends a Basic Attribute View and adds the "isHidden"
+ * attribute. That attribute lets you know if the branch you are querying is a
+ * hidden branch or not. Hidden branches should not be used, are just a
+ * mechanism to merge.
+ */
+public abstract class HiddenAttributeViewImpl<P extends Path> extends AbstractBasicFileAttributeView<P>
+		implements HiddenAttributeView {
+
+	public static final String HIDDEN = "hidden";
+
+	public HiddenAttributeViewImpl(final P path) {
+		super(path);
+	}
+
+	@Override
+	public String name() {
+		return HIDDEN;
+	}
+
+	@Override
+	public Map<String, Object> readAttributes(final String... attributes) throws IOException {
+		final HiddenAttributes attrs = readAttributes();
+
+		return new HashMap<String, Object>(super.readAttributes(attributes)) {
+			{
+
+				for (final String attribute : attributes) {
+					checkNotEmpty("attribute", attribute);
+
+					if (attribute.equals("*") || attribute.equals(HIDDEN)) {
+						put(HIDDEN, attrs.isHidden());
+					}
+
+					if (attribute.equals("*")) {
+						break;
+					}
+				}
+			}
+		};
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributesImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributesImpl.java
new file mode 100644
index 0000000000..46b559cf08
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/HiddenAttributesImpl.java
@@ 0,0 +1,34 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.attribute.BasicFileAttributes;
+
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
+
+/**
+ * HiddenAttribute implementation. Receives a BasicFIleAttributes, and if file
+ * is hidden or not so creates a new object that has all those attributes
+ * together.
+ */
+public class HiddenAttributesImpl extends AbstractJGitBasicAttributesImpl implements HiddenAttributes {
+
+	private final boolean hidden;
+
+	public HiddenAttributesImpl(final BasicFileAttributes attributes, final boolean isHidden) {
+		super(attributes);
+		this.hidden = isHidden;
+	}
+
+	@Override
+	public boolean isHidden() {
+		return this.hidden;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitBasicAttributeView.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitBasicAttributeView.java
new file mode 100644
index 0000000000..bc26a5e678
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitBasicAttributeView.java
@@ 0,0 +1,123 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.NoSuchFileException;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.nio.file.attribute.BasicFileAttributes;
+import java.nio.file.attribute.FileTime;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathType;
+
+/**
+ *
+ */
+public class JGitBasicAttributeView extends AbstractBasicFileAttributeView<JGitPathImpl> {
+
+	private BasicFileAttributes attrs = null;
+
+	public JGitBasicAttributeView(final JGitPathImpl path) {
+		super(path);
+	}
+
+	@Override
+	public BasicFileAttributes readAttributes() throws IOException {
+		if (attrs == null) {
+			attrs = buildAttrs((JGitFileSystem) path.getFileSystem(), path.getRefTree(), path.getPath());
+		}
+		return attrs;
+	}
+
+	@Override
+	public Class<? extends BasicFileAttributeView>[] viewTypes() {
+		return new Class[] { BasicFileAttributeView.class, JGitBasicAttributeView.class };
+	}
+
+	private BasicFileAttributes buildAttrs(final JGitFileSystem fs, final String branchName, final String path)
+			throws NoSuchFileException {
+		final PathInfo pathInfo = fs.getGit().getPathInfo(branchName, path);
+
+		if (pathInfo == null || pathInfo.getPathType().equals(PathType.NOT_FOUND)) {
+			throw new NoSuchFileException(path);
+		}
+
+		final Ref ref = fs.getGit().getRef(branchName);
+
+		return new BasicFileAttributes() {
+
+			private FileTime lastModifiedDate = null;
+			private FileTime creationDate = null;
+
+			@Override
+			public FileTime lastModifiedTime() {
+				if (lastModifiedDate == null) {
+					try {
+						lastModifiedDate = FileTime
+								.fromMillis(fs.getGit().getLastCommit(ref).getCommitterIdent().getWhen().getTime());
+					} catch (final Exception e) {
+						lastModifiedDate = FileTime.fromMillis(0);
+					}
+				}
+				return lastModifiedDate;
+			}
+
+			@Override
+			public FileTime lastAccessTime() {
+				return lastModifiedTime();
+			}
+
+			@Override
+			public FileTime creationTime() {
+				if (creationDate == null) {
+					try {
+						creationDate = FileTime
+								.fromMillis(fs.getGit().getFirstCommit(ref).getCommitterIdent().getWhen().getTime());
+					} catch (final Exception e) {
+						creationDate = FileTime.fromMillis(0);
+					}
+				}
+				return creationDate;
+			}
+
+			@Override
+			public boolean isRegularFile() {
+				return pathInfo.getPathType().equals(PathType.FILE);
+			}
+
+			@Override
+			public boolean isDirectory() {
+				return pathInfo.getPathType().equals(PathType.DIRECTORY);
+			}
+
+			@Override
+			public boolean isSymbolicLink() {
+				return false;
+			}
+
+			@Override
+			public boolean isOther() {
+				return false;
+			}
+
+			@Override
+			public long size() {
+				return pathInfo.getSize();
+			}
+
+			@Override
+			public Object fileKey() {
+				return pathInfo.getObjectId() == null ? null : pathInfo.getObjectId().toString();
+			}
+		};
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitDiffAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitDiffAttributeViewImpl.java
new file mode 100644
index 0000000000..3601d48e47
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitDiffAttributeViewImpl.java
@@ 0,0 +1,55 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.util.List;
+
+import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
+import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;
+
+/**
+ *
+ */
+public class JGitDiffAttributeViewImpl extends DiffAttributeViewImpl<JGitPathImpl> {
+
+	private DiffAttributes attrs = null;
+	private final String params;
+
+	public JGitDiffAttributeViewImpl(final JGitPathImpl path, final String params) {
+		super(path);
+		this.params = params;
+	}
+
+	@Override
+	public DiffAttributes readAttributes() throws IOException {
+		if (attrs == null) {
+			attrs = buildAttrs(path.getFileSystem(), params);
+		}
+		return attrs;
+	}
+
+	@Override
+	public Class<? extends BasicFileAttributeView>[] viewTypes() {
+		return new Class[] { VersionAttributeView.class, VersionAttributeViewImpl.class,
+				JGitDiffAttributeViewImpl.class };
+	}
+
+	private DiffAttributes buildAttrs(final JGitFileSystem fs, final String params) throws IOException {
+		final String[] branches = params.split(",");
+		final String branchA = branches[0];
+		final String branchB = branches[1];
+		final List<FileDiff> diffs = fs.getGit().diffRefs(branchA, branchB);
+
+		return new DiffAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes(), () > diffs);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java
new file mode 100644
index 0000000000..4012afeafb
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitEventsBroadcast.java
@@ 0,0 +1,64 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.util.List;
+import java.util.UUID;
+import java.util.function.Consumer;
+
+import org.eclipse.jgit.niofs.cluster.ClusterMessageService;
+
+public class JGitEventsBroadcast {
+
+	public static final String DEFAULT_TOPIC = "defaultniogittopic";
+
+	private String nodeId = UUID.randomUUID().toString();
+	private Consumer<WatchEventsWrapper> eventsPublisher;
+	private final ClusterMessageService clusterMessageService;
+
+	public JGitEventsBroadcast(ClusterMessageService clusterMessageService,
+			Consumer<WatchEventsWrapper> eventsPublisher) {
+		this.clusterMessageService = clusterMessageService;
+		this.eventsPublisher = eventsPublisher;
+		setupJMSConnection();
+	}
+
+	private void setupJMSConnection() {
+		clusterMessageService.connect();
+	}
+
+	public void createWatchService(String topicName) {
+		clusterMessageService.createConsumer(ClusterMessageService.DestinationType.PubSub, getChannelName(topicName),
+				WatchEventsWrapper.class, (we) > {
+					if (!we.getNodeId().equals(nodeId)) {
+						eventsPublisher.accept(we);
+					}
+				});
+	}
+
+	public synchronized void broadcast(String fsName, Path watchable, List<WatchEvent<?>> events) {
+		clusterMessageService.broadcast(ClusterMessageService.DestinationType.PubSub, getChannelName(fsName),
+				new WatchEventsWrapper(nodeId, fsName, watchable, events));
+	}
+
+	private String getChannelName(String fsName) {
+		String channelName = DEFAULT_TOPIC;
+		if (fsName.contains("/")) {
+			channelName = fsName.substring(0, fsName.indexOf("/"));
+		}
+		return channelName;
+	}
+
+	public void close() {
+		clusterMessageService.close();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFSPath.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFSPath.java
new file mode 100644
index 0000000000..66e939dc09
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFSPath.java
@@ 0,0 +1,167 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.io.IOError;
+import java.io.IOException;
+import java.net.URI;
+import java.nio.file.ClosedWatchServiceException;
+import java.nio.file.FileSystem;
+import java.nio.file.InvalidPathException;
+import java.nio.file.LinkOption;
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchKey;
+import java.nio.file.WatchService;
+import java.util.Iterator;
+
+public class JGitFSPath implements Path {
+
+	private final JGitFileSystem fs;
+
+	public JGitFSPath(final JGitFileSystem fs) {
+		this.fs = fs;
+	}
+
+	@Override
+	public FileSystem getFileSystem() {
+		return fs;
+	}
+
+	@Override
+	public boolean isAbsolute() {
+		return true;
+	}
+
+	@Override
+	public Path getRoot() {
+		return null;
+	}
+
+	@Override
+	public Path getFileName() {
+		return null;
+	}
+
+	@Override
+	public Path getParent() {
+		return null;
+	}
+
+	@Override
+	public int getNameCount() {
+		return 1;
+	}
+
+	@Override
+	public Path getName(final int index) throws IllegalArgumentException {
+		return null;
+	}
+
+	@Override
+	public Path subpath(final int beginIndex, final int endIndex) throws IllegalArgumentException {
+		return null;
+	}
+
+	@Override
+	public boolean startsWith(final Path other) {
+		return false;
+	}
+
+	@Override
+	public boolean startsWith(final String other) throws InvalidPathException {
+		return false;
+	}
+
+	@Override
+	public boolean endsWith(final Path other) {
+		return false;
+	}
+
+	@Override
+	public boolean endsWith(final String other) throws InvalidPathException {
+		return false;
+	}
+
+	@Override
+	public Path normalize() {
+		return this;
+	}
+
+	@Override
+	public Path resolve(final Path other) {
+		return null;
+	}
+
+	@Override
+	public Path resolve(final String other) throws InvalidPathException {
+		return null;
+	}
+
+	@Override
+	public Path resolveSibling(final Path other) {
+		return null;
+	}
+
+	@Override
+	public Path resolveSibling(final String other) throws InvalidPathException {
+		return null;
+	}
+
+	@Override
+	public Path relativize(final Path other) throws IllegalArgumentException {
+		return null;
+	}
+
+	@Override
+	public URI toUri() throws IOError, SecurityException {
+		return URI.create(fs.toString());
+	}
+
+	@Override
+	public Path toAbsolutePath() throws IOError, SecurityException {
+		return this;
+	}
+
+	@Override
+	public Path toRealPath(final LinkOption... options) throws IOException, SecurityException {
+		return this;
+	}
+
+	@Override
+	public File toFile() throws UnsupportedOperationException {
+		return null;
+	}
+
+	@Override
+	public int compareTo(final Path path) {
+		return 0;
+	}
+
+	@Override
+	public Iterator<Path> iterator() {
+		return null;
+	}
+
+	@Override
+	public WatchKey register(final WatchService watcher, final WatchEvent.Kind<?>[] events,
+			final WatchEvent.Modifier... modifiers) throws UnsupportedOperationException, IllegalArgumentException,
+			ClosedWatchServiceException, IOException, SecurityException {
+		return null;
+	}
+
+	@Override
+	public WatchKey register(final WatchService watcher, final WatchEvent.Kind<?>... events)
+			throws UnsupportedOperationException, IllegalArgumentException, ClosedWatchServiceException, IOException,
+			SecurityException {
+		return null;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileStore.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileStore.java
new file mode 100644
index 0000000000..ebdac2ce8a
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileStore.java
@@ 0,0 +1,119 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.IOException;
+import java.nio.file.FileStore;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.nio.file.attribute.FileAttributeView;
+import java.nio.file.attribute.FileStoreAttributeView;
+
+import org.eclipse.jgit.lib.Repository;
+
+public class JGitFileStore extends FileStore {
+
+	private final Repository repository;
+
+	JGitFileStore(final Repository repository) {
+		this.repository = checkNotNull("repository", repository);
+	}
+
+	@Override
+	public String name() {
+		return repository.getDirectory().getName();
+	}
+
+	@Override
+	public String type() {
+		return "file";
+	}
+
+	@Override
+	public boolean isReadOnly() {
+		return false;
+	}
+
+	@Override
+	public long getTotalSpace() throws IOException {
+		return repository.getDirectory().getTotalSpace();
+	}
+
+	@Override
+	public long getUsableSpace() throws IOException {
+		return repository.getDirectory().getUsableSpace();
+	}
+
+	@Override
+	public long getUnallocatedSpace() throws IOException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public boolean supportsFileAttributeView(final Class<? extends FileAttributeView> type) {
+		checkNotNull("type", type);
+
+		return type.equals(BasicFileAttributeView.class);
+	}
+
+	@Override
+	public boolean supportsFileAttributeView(final String name) {
+		checkNotEmpty("name", name);
+
+		return name.equals("basic");
+	}
+
+	@Override
+	public <V extends FileStoreAttributeView> V getFileStoreAttributeView(Class<V> type) {
+		checkNotNull("type", type);
+
+		return null;
+	}
+
+	@Override
+	public Object getAttribute(final String attribute) throws UnsupportedOperationException, IOException {
+		checkNotEmpty("attribute", attribute);
+
+		if (attribute.equals("totalSpace")) {
+			return getTotalSpace();
+		}
+		if (attribute.equals("usableSpace")) {
+			return getUsableSpace();
+		}
+		if (attribute.equals("readOnly")) {
+			return isReadOnly();
+		}
+		if (attribute.equals("name")) {
+			return name();
+		}
+		throw new UnsupportedOperationException("Attribute '" + attribute + "' not available");
+	}
+
+	@Override
+	public boolean equals(final Object o) {
+		if (o == null) {
+			return false;
+		}
+		if (!(o instanceof FileStore)) {
+			return false;
+		}
+
+		final FileStore ofs = (FileStore) o;
+
+		return name().equals(ofs.name());
+	}
+
+	@Override
+	public int hashCode() {
+		return name().hashCode();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystem.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystem.java
new file mode 100644
index 0000000000..8fa20522cc
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystem.java
@@ 0,0 +1,79 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.FileSystem;
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.util.List;
+
+import org.eclipse.jgit.niofs.fs.options.CommentedOption;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.ReceiveCommand;
+import org.eclipse.jgit.transport.UploadPack;
+
+public abstract class JGitFileSystem extends FileSystem
+		implements FileSystemStateAware, FileSystemId, LockableFileSystem, Disposable {
+
+	abstract public Git getGit();
+
+	abstract CredentialsProvider getCredential();
+
+	abstract void checkClosed() throws IllegalStateException;
+
+	abstract void publishEvents(Path watchable, List<WatchEvent<?>> elist);
+
+	abstract boolean isOnBatch();
+
+	abstract void setState(String state);
+
+	abstract CommitInfo buildCommitInfo(String defaultMessage, CommentedOption op);
+
+	abstract void setBatchCommitInfo(String defaultMessage, CommentedOption op);
+
+	abstract void setHadCommitOnBatchState(Path path, boolean hadCommitOnBatchState);
+
+	abstract void setHadCommitOnBatchState(boolean value);
+
+	abstract boolean isHadCommitOnBatchState(Path path);
+
+	abstract void setBatchCommitInfo(CommitInfo batchCommitInfo);
+
+	abstract CommitInfo getBatchCommitInfo();
+
+	abstract int incrementAndGetCommitCount();
+
+	abstract void resetCommitCount();
+
+	abstract int getNumberOfCommitsSinceLastGC();
+
+	abstract void addPostponedWatchEvents(List<WatchEvent<?>> postponedWatchEvents);
+
+	abstract List<WatchEvent<?>> getPostponedWatchEvents();
+
+	abstract void clearPostponedWatchEvents();
+
+	abstract boolean hasPostponedEvents();
+
+	abstract public boolean hasBeenInUse();
+
+	abstract void notifyExternalUpdate();
+
+	abstract void notifyPostCommit(int exitCode);
+
+	abstract void checkBranchAccess(ReceiveCommand command, User user);
+
+	abstract void filterBranchAccess(UploadPack uploadPack, User user);
+
+	public abstract String getName();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemImpl.java
new file mode 100644
index 0000000000..8339e505eb
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemImpl.java
@@ 0,0 +1,510 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.FileStore;
+import java.nio.file.InvalidPathException;
+import java.nio.file.Path;
+import java.nio.file.PathMatcher;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchService;
+import java.nio.file.attribute.UserPrincipalLookupService;
+import java.nio.file.spi.FileSystemProvider;
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.Date;
+import java.util.HashSet;
+import java.util.Iterator;
+import java.util.List;
+import java.util.Map;
+import java.util.NoSuchElementException;
+import java.util.Set;
+import java.util.TimeZone;
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.concurrent.atomic.AtomicInteger;
+import java.util.regex.PatternSyntaxException;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.fs.FileSystemState;
+import org.eclipse.jgit.niofs.fs.options.CommentedOption;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHookExecutionContext;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooksConstants;
+import org.eclipse.jgit.niofs.internal.hook.JGitFSHooks;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.ReceiveCommand;
+import org.eclipse.jgit.transport.UploadPack;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static java.util.Arrays.asList;
+import static java.util.Collections.unmodifiableSet;
+import static org.eclipse.jgit.lib.Repository.shortenRefName;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_NIO_DIR;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+public class JGitFileSystemImpl extends JGitFileSystem {
+
+	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemImpl.class);
+	private static final Set<String> SUPPORTED_ATTR_VIEWS = unmodifiableSet(new HashSet<>(asList("basic", "version")));
+	private final JGitFileSystemProvider provider;
+	private final Git git;
+	private final String toStringContent;
+	private boolean isClosed = false;
+	private final FileStore fileStore;
+	private final String name;
+	private final CredentialsProvider credential;
+	private final Map<FileSystemHooks, ?> fsHooks;
+	private final AtomicInteger numberOfCommitsSinceLastGC = new AtomicInteger(0);
+	private FileSystemState state = FileSystemState.NORMAL;
+	private CommitInfo batchCommitInfo = null;
+	private Map<Path, Boolean> hadCommitOnBatchState = new ConcurrentHashMap<>();
+	private JGitFileSystemLock lock;
+	private JGitFileSystemsEventsManager fsEventsManager;
+
+	private List<WatchEvent<?>> postponedWatchEvents = Collections.synchronizedList(new ArrayList<>());
+
+	public JGitFileSystemImpl(final JGitFileSystemProvider provider, final Map<String, String> fullHostNames,
+			final Git git, final JGitFileSystemLock lock, final String name, final CredentialsProvider credential,
+			JGitFileSystemsEventsManager fsEventsManager, Map<FileSystemHooks, ?> fsHooks) {
+		this.fsEventsManager = fsEventsManager;
+		this.provider = checkNotNull("provider", provider);
+		this.git = checkNotNull("git", git);
+		this.name = checkNotEmpty("name", name);
+
+		this.lock = checkNotNull("lock", lock);
+		this.credential = checkNotNull("credential", credential);
+		this.fsHooks = fsHooks;
+		this.fileStore = new JGitFileStore(this.git.getRepository());
+		if (fullHostNames != null && !fullHostNames.isEmpty()) {
+			final StringBuilder sb = new StringBuilder();
+			final Iterator<Map.Entry<String, String>> iterator = fullHostNames.entrySet().iterator();
+			while (iterator.hasNext()) {
+				final Map.Entry<String, String> entry = iterator.next();
+				sb.append(entry.getKey()).append("://").append(entry.getValue()).append("/").append(name);
+				if (iterator.hasNext()) {
+					sb.append("\n");
+				}
+			}
+			toStringContent = sb.toString();
+		} else {
+			toStringContent = "git://" + name;
+		}
+	}
+
+	@Override
+	public String id() {
+		return name;
+	}
+
+	@Override
+	public String getName() {
+		return name;
+	}
+
+	@Override
+	public Git getGit() {
+		return git;
+	}
+
+	@Override
+	public CredentialsProvider getCredential() {
+		return credential;
+	}
+
+	@Override
+	public FileSystemProvider provider() {
+		return provider;
+	}
+
+	@Override
+	public boolean isOpen() {
+		return !isClosed;
+	}
+
+	@Override
+	public boolean isReadOnly() {
+		return false;
+	}
+
+	@Override
+	public String getSeparator() {
+		return "/";
+	}
+
+	@Override
+	public Iterable<Path> getRootDirectories() {
+		checkClosed();
+		return () > new Iterator<Path>() {
+
+			Iterator<Ref> branches = null;
+
+			@Override
+			public boolean hasNext() {
+				if (branches == null) {
+					init();
+				}
+				return branches.hasNext();
+			}
+
+			private void init() {
+				branches = git.listRefs().iterator();
+			}
+
+			@Override
+			public Path next() {
+
+				if (branches == null) {
+					init();
+				}
+				try {
+					return JGitPathImpl.createRoot(JGitFileSystemImpl.this, "/",
+							shortenRefName(branches.next().getName()) + "@" + name, false);
+				} catch (NoSuchElementException e) {
+					throw new IllegalStateException("The gitnio directory is in an invalid state. "
+							+ "If you are an IntelliJ IDEA user, " + "there is a known bug which requires specifying "
+							+ "a custom directory for your git repository. "
+							+ "You can specify a custom directory using 'D" + GIT_NIO_DIR + "=/tmp/dir'. "
+							+ "For more details please see https://issues.jboss.org/browse/UF275.", e);
+				}
+			}
+
+			@Override
+			public void remove() {
+				throw new UnsupportedOperationException();
+			}
+		};
+	}
+
+	@Override
+	public Iterable<FileStore> getFileStores() {
+		checkClosed();
+		return () > new Iterator<FileStore>() {
+
+			private int i = 0;
+
+			@Override
+			public boolean hasNext() {
+				return i < 1;
+			}
+
+			@Override
+			public FileStore next() {
+				if (i < 1) {
+					i++;
+					return fileStore;
+				} else {
+					throw new NoSuchElementException();
+				}
+			}
+
+			@Override
+			public void remove() {
+				throw new UnsupportedOperationException();
+			}
+		};
+	}
+
+	@Override
+	public Set<String> supportedFileAttributeViews() {
+		checkClosed();
+		return SUPPORTED_ATTR_VIEWS;
+	}
+
+	@Override
+	public Path getPath(final String first, final String... more) throws InvalidPathException {
+		checkClosed();
+		if (first == null || first.trim().isEmpty()) {
+			return new JGitFSPath(this);
+		}
+
+		if (more == null || more.length == 0) {
+			return JGitPathImpl.create(this, first, JGitPathImpl.DEFAULT_REF_TREE + "@" + name, false);
+		}
+
+		final StringBuilder sb = new StringBuilder();
+		for (final String segment : more) {
+			if (segment.length() > 0) {
+				if (sb.length() > 0) {
+					sb.append(getSeparator());
+				}
+				sb.append(segment);
+			}
+		}
+		return JGitPathImpl.create(this, sb.toString(), first + "@" + name, false);
+	}
+
+	@Override
+	public PathMatcher getPathMatcher(final String syntaxAndPattern)
+			throws IllegalArgumentException, PatternSyntaxException, UnsupportedOperationException {
+		checkClosed();
+		checkNotEmpty("syntaxAndPattern", syntaxAndPattern);
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public UserPrincipalLookupService getUserPrincipalLookupService() throws UnsupportedOperationException {
+		checkClosed();
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public WatchService newWatchService() throws UnsupportedOperationException, IOException {
+		checkClosed();
+		return fsEventsManager.newWatchService(name);
+	}
+
+	@Override
+	public void close() throws IOException {
+		if (isClosed) {
+			return;
+		}
+		git.getRepository().close();
+		isClosed = true;
+		try {
+			fsEventsManager.close(name);
+		} catch (final Exception ex) {
+			LOGGER.error("Error during close of WatchServices [" + toString() + "]", ex);
+		} finally {
+			provider.onCloseFileSystem(this);
+		}
+	}
+
+	@Override
+	public void checkClosed() throws IllegalStateException {
+		if (isClosed) {
+			throw new IllegalStateException("FileSystem is closed.");
+		}
+	}
+
+	@Override
+	public boolean equals(Object o) {
+		if (this == o) {
+			return true;
+		}
+		if (o == null || getClass() != o.getClass()) {
+			if (o != null && o instanceof JGitFileSystemProxy) {
+				o = ((JGitFileSystemProxy) o).getRealJGitFileSystem();
+			} else {
+				return false;
+			}
+		}
+
+		JGitFileSystemImpl that = (JGitFileSystemImpl) o;
+
+		if (!name.equals(that.name)) {
+			return false;
+		}
+
+		return true;
+	}
+
+	@Override
+	public String toString() {
+		return toStringContent;
+	}
+
+	@Override
+	public int hashCode() {
+		int result = name.hashCode();
+		return result;
+	}
+
+	@Override
+	public void publishEvents(final Path watchableRoot, final List<WatchEvent<?>> elist) {
+		fsEventsManager.publishEvents(name, watchableRoot, elist);
+	}
+
+	@Override
+	public void dispose() throws IOException {
+		if (!isClosed) {
+			close();
+		}
+		provider.onDisposeFileSystem(this);
+	}
+
+	@Override
+	public boolean isOnBatch() {
+		return state.equals(FileSystemState.BATCH);
+	}
+
+	@Override
+	public void setState(String state) {
+		try {
+			this.state = FileSystemState.valueOf(state);
+		} catch (final Exception ex) {
+			this.state = FileSystemState.NORMAL;
+		}
+	}
+
+	@Override
+	public CommitInfo buildCommitInfo(final String defaultMessage, final CommentedOption op) {
+		String sessionId = null;
+		String name = null;
+		String email = null;
+		String message = defaultMessage;
+		TimeZone timeZone = null;
+		Date when = null;
+
+		if (op != null) {
+			sessionId = op.getSessionId();
+			name = op.getName();
+			email = op.getEmail();
+			if (op.getMessage() != null && !op.getMessage().trim().isEmpty()) {
+				message = op.getMessage();
+			}
+			timeZone = op.getTimeZone();
+			when = op.getWhen();
+		}
+
+		return new CommitInfo(sessionId, name, email, message, timeZone, when);
+	}
+
+	@Override
+	public void setBatchCommitInfo(final String defaultMessage, final CommentedOption op) {
+		this.batchCommitInfo = buildCommitInfo(defaultMessage, op);
+	}
+
+	@Override
+	public void setHadCommitOnBatchState(final Path path, final boolean hadCommitOnBatchState) {
+		final Path root = checkNotNull("path", path).getRoot();
+		this.hadCommitOnBatchState.put(root.getRoot(), hadCommitOnBatchState);
+	}
+
+	@Override
+	public void setHadCommitOnBatchState(final boolean value) {
+		for (Map.Entry<Path, Boolean> entry : hadCommitOnBatchState.entrySet()) {
+			entry.setValue(value);
+		}
+	}
+
+	@Override
+	public boolean isHadCommitOnBatchState(final Path path) {
+		final Path root = checkNotNull("path", path).getRoot();
+		return hadCommitOnBatchState.containsKey(root) ? hadCommitOnBatchState.get(root) : false;
+	}
+
+	@Override
+	public void setBatchCommitInfo(CommitInfo batchCommitInfo) {
+		this.batchCommitInfo = batchCommitInfo;
+	}
+
+	@Override
+	public CommitInfo getBatchCommitInfo() {
+		return batchCommitInfo;
+	}
+
+	@Override
+	public int incrementAndGetCommitCount() {
+		return numberOfCommitsSinceLastGC.incrementAndGet();
+	}
+
+	@Override
+	public void resetCommitCount() {
+		numberOfCommitsSinceLastGC.set(0);
+	}
+
+	@Override
+	public int getNumberOfCommitsSinceLastGC() {
+		return numberOfCommitsSinceLastGC.get();
+	}
+
+	@Override
+	public FileSystemState getState() {
+		return state;
+	}
+
+	@Override
+	public void lock() {
+		lock.lock();
+	}
+
+	@Override
+	public void unlock() {
+		lock.unlock();
+	}
+
+	public JGitFileSystemLock getLock() {
+		return lock;
+	}
+
+	@Override
+	public void addPostponedWatchEvents(List<WatchEvent<?>> postponedWatchEvents) {
+		this.postponedWatchEvents.addAll(postponedWatchEvents);
+	}
+
+	@Override
+	public List<WatchEvent<?>> getPostponedWatchEvents() {
+		return postponedWatchEvents;
+	}
+
+	@Override
+	public void clearPostponedWatchEvents() {
+		this.postponedWatchEvents = Collections.synchronizedList(new ArrayList<>());
+	}
+
+	@Override
+	public boolean hasPostponedEvents() {
+		return !getPostponedWatchEvents().isEmpty();
+	}
+
+	@Override
+	public boolean hasBeenInUse() {
+		return lock.hasBeenInUse();
+	}
+
+	@Override
+	public void notifyExternalUpdate() {
+		Object hook = fsHooks.get(FileSystemHooks.ExternalUpdate);
+		if (hook != null) {
+			JGitFSHooks.executeFSHooks(hook, FileSystemHooks.ExternalUpdate, new FileSystemHookExecutionContext(name));
+		}
+	}
+
+	@Override
+	public void notifyPostCommit(int exitCode) {
+		Object hook = fsHooks.get(FileSystemHooks.PostCommit);
+		if (hook != null) {
+			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
+			ctx.addParam(FileSystemHooksConstants.POST_COMMIT_EXIT_CODE, exitCode);
+
+			JGitFSHooks.executeFSHooks(hook, FileSystemHooks.ExternalUpdate, ctx);
+		}
+	}
+
+	@Override
+	public void checkBranchAccess(final ReceiveCommand command, final User user) {
+		Object hook = fsHooks.get(FileSystemHooks.BranchAccessCheck);
+		if (hook != null) {
+			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
+			ctx.addParam(FileSystemHooksConstants.RECEIVE_COMMAND, command);
+			ctx.addParam(FileSystemHooksConstants.USER, user);
+
+			JGitFSHooks.executeFSHooks(hook, FileSystemHooks.BranchAccessCheck, ctx);
+		}
+	}
+
+	@Override
+	public void filterBranchAccess(final UploadPack uploadPack, final User user) {
+		Object hook = fsHooks.get(FileSystemHooks.BranchAccessFilter);
+		if (hook != null) {
+			FileSystemHookExecutionContext ctx = new FileSystemHookExecutionContext(name);
+			ctx.addParam(FileSystemHooksConstants.UPLOAD_PACK, uploadPack);
+			ctx.addParam(FileSystemHooksConstants.USER, user);
+
+			JGitFSHooks.executeFSHooks(hook, FileSystemHooks.BranchAccessFilter, ctx);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemLock.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemLock.java
new file mode 100644
index 0000000000..82b589413e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemLock.java
@@ 0,0 +1,22 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.util.concurrent.TimeUnit;
+
+import org.eclipse.jgit.niofs.internal.op.Git;
+
+public class JGitFileSystemLock extends FileSystemLock {
+
+	public JGitFileSystemLock(Git git, TimeUnit t, long duration) {
+
+		super(git.getRepository().getDirectory(), "af.lock", t, duration);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProvider.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProvider.java
new file mode 100644
index 0000000000..1b71589871
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProvider.java
@@ 0,0 +1,2193 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.io.FileNotFoundException;
+import java.io.FileOutputStream;
+import java.io.FilterOutputStream;
+import java.io.IOException;
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.io.RandomAccessFile;
+import java.io.UnsupportedEncodingException;
+import java.net.Authenticator;
+import java.net.InetSocketAddress;
+import java.net.URI;
+import java.net.URLEncoder;
+import java.nio.ByteBuffer;
+import java.nio.channels.AsynchronousFileChannel;
+import java.nio.channels.FileChannel;
+import java.nio.channels.SeekableByteChannel;
+import java.nio.file.AccessDeniedException;
+import java.nio.file.AccessMode;
+import java.nio.file.AtomicMoveNotSupportedException;
+import java.nio.file.CopyOption;
+import java.nio.file.DirectoryNotEmptyException;
+import java.nio.file.DirectoryStream;
+import java.nio.file.FileAlreadyExistsException;
+import java.nio.file.FileStore;
+import java.nio.file.FileSystem;
+import java.nio.file.FileSystemAlreadyExistsException;
+import java.nio.file.FileSystemNotFoundException;
+import java.nio.file.LinkOption;
+import java.nio.file.NoSuchFileException;
+import java.nio.file.NotDirectoryException;
+import java.nio.file.OpenOption;
+import java.nio.file.Path;
+import java.nio.file.StandardCopyOption;
+import java.nio.file.StandardOpenOption;
+import java.nio.file.WatchEvent;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.nio.file.attribute.BasicFileAttributes;
+import java.nio.file.attribute.FileAttribute;
+import java.nio.file.attribute.FileAttributeView;
+import java.util.AbstractMap;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.Collection;
+import java.util.Date;
+import java.util.HashMap;
+import java.util.HashSet;
+import java.util.Iterator;
+import java.util.List;
+import java.util.Map;
+import java.util.NoSuchElementException;
+import java.util.Set;
+import java.util.TimeZone;
+import java.util.concurrent.Executor;
+import java.util.concurrent.ExecutorService;
+import java.util.concurrent.Executors;
+import java.util.concurrent.TimeUnit;
+import java.util.function.Function;
+import java.util.stream.Collectors;
+
+import javax.servlet.http.HttpServletRequest;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.errors.RepositoryNotFoundException;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.internal.ketch.KetchSystem;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.fs.AmbiguousFileSystemNameException;
+import org.eclipse.jgit.niofs.fs.FileSystemState;
+import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
+import org.eclipse.jgit.niofs.fs.options.CherryPickCopyOption;
+import org.eclipse.jgit.niofs.fs.options.CommentedOption;
+import org.eclipse.jgit.niofs.fs.options.MergeCopyOption;
+import org.eclipse.jgit.niofs.fs.options.SquashOption;
+import org.eclipse.jgit.niofs.internal.config.ConfigProperties;
+import org.eclipse.jgit.niofs.internal.daemon.git.Daemon;
+import org.eclipse.jgit.niofs.internal.daemon.ssh.BaseGitCommand;
+import org.eclipse.jgit.niofs.internal.daemon.ssh.GitSSHService;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
+import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.commands.Clone;
+import org.eclipse.jgit.niofs.internal.op.commands.PathUtil;
+import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathType;
+import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
+import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
+import org.eclipse.jgit.niofs.internal.security.FileSystemAuthorization;
+import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.niofs.internal.util.DescriptiveThreadFactory;
+import org.eclipse.jgit.niofs.internal.util.EncodingUtil;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevTree;
+import org.eclipse.jgit.storage.file.WindowCacheConfig;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.ReceiveCommand;
+import org.eclipse.jgit.transport.ReceivePack;
+import org.eclipse.jgit.transport.SshSessionFactory;
+import org.eclipse.jgit.transport.UploadPack;
+import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
+import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
+import org.eclipse.jgit.transport.resolver.RepositoryResolver;
+import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
+import org.eclipse.jgit.transport.resolver.UploadPackFactory;
+import org.eclipse.jgit.util.FS;
+import org.eclipse.jgit.util.FileUtils;
+import org.eclipse.jgit.util.ProcessResult;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static java.nio.file.StandardOpenOption.READ;
+import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
+import static java.util.Collections.emptyList;
+import static org.eclipse.jgit.lib.Constants.DOT_GIT_EXT;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.DEFAULT_SCHEME_SIZE;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_BRANCH_LIST;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEST_PATH;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_MIRROR;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_PASSWORD;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_SUBDIRECTORY;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_USER_NAME;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SCHEME;
+import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SCHEME_SIZE;
+import static org.eclipse.jgit.niofs.internal.op.model.PathType.DIRECTORY;
+import static org.eclipse.jgit.niofs.internal.op.model.PathType.NOT_FOUND;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkCondition;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+public class JGitFileSystemProvider extends SecuredFileSystemProvider implements Disposable {
+
+	private static final Logger LOG = LoggerFactory.getLogger(JGitFileSystemProvider.class);
+	private static final TimeUnit LOCK_LAST_ACCESS_TIME_UNIT = TimeUnit.SECONDS;
+	private static final long LOCK_LAST_ACCESS_THRESHOLD = 10;
+
+	private final Map<String, String> fullHostNames = new HashMap<>();
+
+	private final Object postponedEventsLock = new Object();
+
+	private Daemon daemonService = null;
+
+	private GitSSHService gitSSHService = null;
+
+	private FS detectedFS = FS.DETECTED;
+
+	private ExecutorService executorService;
+
+	final KetchSystem system = new KetchSystem();
+
+	final KetchLeaderCache leaders = new KetchLeaderCache(system);
+
+	private AuthenticationService httpAuthenticator;
+	private FileSystemAuthorization authorizer;
+
+	JGitFileSystemProviderConfiguration config;
+
+	JGitFileSystemsManager fsManager;
+
+	JGitFileSystemsEventsManager fsEventsManager;
+
+	/**
+	 * Creates a JGit filesystem provider which takes its configuration from system
+	 * properties. In a normal production deployment, this is the constructor that
+	 * will be invoked by the ServiceLoader mechanism. For a list of properties that
+	 * affect the configuration of JGitFileSystemProvider, see the DEBUG log output
+	 * of this class during startup.
+	 */
+	public JGitFileSystemProvider() {
+		this(new ConfigProperties(System.getProperties()),
+				Executors.newCachedThreadPool(new DescriptiveThreadFactory()));
+	}
+
+	/**
+	 * Creates a JGit filesystem provider which takes its configuration from the
+	 * given map. For a list of properties that affect the configuration of
+	 * JGitFileSystemProvider, see the DEBUG log output of this class during
+	 * startup.
+	 */
+	public JGitFileSystemProvider(final Map<String, String> gitPrefs) throws IOException {
+		this(new ConfigProperties(gitPrefs), Executors.newCachedThreadPool(new DescriptiveThreadFactory()));
+	}
+
+	/**
+	 * Creates a JGit filesystem provider which takes its configuration from the
+	 * given ConfigProperties instance. For a list of properties that affect the
+	 * configuration of JGitFileSystemProvider, see the DEBUG log output of this
+	 * class during startup.
+	 */
+	public JGitFileSystemProvider(final ConfigProperties gitPrefs, final ExecutorService executorService) {
+		this.executorService = executorService;
+
+		setupConfigs(gitPrefs);
+
+		setupFileSystemsManager();
+
+		setupFSEvents();
+
+		setupGitDefaultCredentials();
+
+		setupSSH();
+
+		setupFullHostNames();
+
+		setupDaemon();
+
+		setupGitSSH();
+	}
+
+	private void setupFSEvents() {
+		fsEventsManager = new JGitFileSystemsEventsManager();
+	}
+
+	protected void setupFileSystemsManager() {
+		fsManager = new JGitFileSystemsManager(this, config);
+	}
+
+	private void setupConfigs(ConfigProperties gitPrefs) {
+		config = new JGitFileSystemProviderConfiguration();
+
+		loadConfig(gitPrefs);
+	}
+
+	private void setupGitSSH() {
+		if (config.isSshEnabled()) {
+			buildAndStartSSH();
+		} else {
+			gitSSHService = null;
+		}
+	}
+
+	private void setupDaemon() {
+		if (config.isDaemonEnabled()) {
+			buildAndStartDaemon();
+		} else {
+			daemonService = null;
+		}
+	}
+
+	private void setupFullHostNames() {
+		if (config.isDaemonEnabled()) {
+			fullHostNames.put("git", config.getDaemonHostName() + ":" + config.getDaemonPort());
+		}
+		if (config.isSshEnabled()) {
+			fullHostNames.put("ssh", config.getSshHostName() + ":" + config.getSshPort());
+		}
+	}
+
+	private void setupSSH() {
+		SshSessionFactory.setInstance(new JGitSSHConfigSessionFactory(config));
+	}
+
+	private void setupGitDefaultCredentials() {
+		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest", ""));
+	}
+
+	private void loadConfig(final ConfigProperties systemConfig) {
+
+		config.load(systemConfig);
+
+		if (config.httpProxyIsDefined()) {
+			setupProxyAuthentication();
+		}
+	}
+
+	private void setupProxyAuthentication() {
+		Authenticator.setDefault(new HTTPProxyAuthenticator(config.getHttpProxyUser(), config.getHttpProxyPassword(),
+				config.getHttpsProxyUser(), config.getHttpsProxyPassword()));
+	}
+
+	public void onCloseFileSystem(final JGitFileSystem fileSystem) {
+		fsManager.addClosedFileSystems(fileSystem);
+
+		synchronized (postponedEventsLock) {
+			fileSystem.clearPostponedWatchEvents();
+		}
+
+		if (fsManager.allTheFSAreClosed()) {
+			forceStopDaemon();
+			shutdownSSH();
+			shutdownEventsManager();
+		}
+	}
+
+	protected void shutdownEventsManager() {
+		this.fsEventsManager.shutdown();
+	}
+
+	public void onDisposeFileSystem(final JGitFileSystem fileSystem) {
+		onCloseFileSystem(fileSystem);
+		fsManager.remove(fileSystem.id());
+	}
+
+	@Override
+	public void setAuthorizer(FileSystemAuthorization authorizer) {
+		this.authorizer = checkNotNull("authorizer", authorizer);
+	}
+
+	@Override
+	public void setJAASAuthenticator(AuthenticationService authenticator) {
+		if (gitSSHService != null) {
+			gitSSHService.setUserPassAuthenticator(authenticator);
+		}
+	}
+
+	@Override
+	public void setHTTPAuthenticator(final AuthenticationService httpAuthenticator) {
+		this.httpAuthenticator = httpAuthenticator;
+	}
+
+	@Override
+	public void setSSHAuthenticator(PublicKeyAuthenticator authenticator) {
+		checkNotNull("authenticator", authenticator);
+
+		if (gitSSHService != null) {
+			gitSSHService.setPublicKeyAuthenticator(authenticator);
+		}
+	}
+
+	@Override
+	public void dispose() {
+		shutdown();
+	}
+
+	public void addHostName(final String protocol, String s) {
+		fullHostNames.put(protocol, s);
+	}
+
+	public Map<String, String> getFullHostNames() {
+		return new HashMap<>(fullHostNames);
+	}
+
+	public class RepositoryResolverImpl<T> implements RepositoryResolver<T> {
+
+		@Override
+		public Repository open(final T client, final String name)
+				throws RepositoryNotFoundException, ServiceNotAuthorizedException {
+			final User user = extractUser(client);
+			final JGitFileSystem fs = fsManager.get(name);
+			if (fs == null) {
+				throw new RepositoryNotFoundException(name);
+			}
+
+			if (authorizer != null && !authorizer.authorize(fs, user)) {
+				throw new ServiceNotAuthorizedException("User not authorized.");
+			}
+
+			return fs.getGit().getRepository();
+		}
+
+		public JGitFileSystem resolveFileSystem(final Repository repository) {
+			return fsManager.get(repository);
+		}
+	}
+
+	private User extractUser(Object client) {
+		if (httpAuthenticator != null && client instanceof HttpServletRequest) {
+			return httpAuthenticator.getUser();
+		} else if (client instanceof BaseGitCommand) {
+			return ((BaseGitCommand) client).getUser();
+		}
+
+		return () > "ANONYMOUS";
+	}
+
+	private void buildAndStartSSH() {
+		final ReceivePackFactory receivePackFactory = (req, db) > getReceivePack("ssh", req, db);
+
+		final UploadPackFactory uploadPackFactory = (UploadPackFactory<BaseGitCommand>) (req,
+				db) > new UploadPack(db) {
+					{
+						final JGitFileSystem fs = fsManager.get(db);
+						fs.filterBranchAccess(this, req.getUser());
+					}
+				};
+
+		gitSSHService = new GitSSHService();
+
+		gitSSHService.setup(config.getSshFileCertDir(),
+				InetSocketAddress.createUnresolved(config.getSshHostAddr(), config.getSshPort()),
+				config.getSshIdleTimeout(), config.getSshAlgorithm(), receivePackFactory, uploadPackFactory,
+				getRepositoryResolver(), executorService, config.getGitSshCiphers(), config.getGitSshMACs());
+
+		gitSSHService.start();
+	}
+
+	public <T> ReceivePack getReceivePack(final String protocol, final T req, final Repository db) {
+		return new ReceivePack(db) {
+			{
+
+				final JGitFileSystem fs = fsManager.get(db);
+				final Map<String, RevCommit> oldTreeRefs = new HashMap<>();
+
+				setPreReceiveHook((rp, commands2) > {
+					fs.lock();
+					final User user = extractUser(req);
+					for (final ReceiveCommand command : commands2) {
+						fs.checkBranchAccess(command, user);
+						final RevCommit lastCommit = fs.getGit().getLastCommit(command.getRefName());
+						oldTreeRefs.put(command.getRefName(), lastCommit);
+					}
+				});
+
+				setPostReceiveHook((rp, commands) > {
+					fs.unlock();
+					fs.notifyExternalUpdate();
+					final User user = extractUser(req);
+					for (Map.Entry<String, RevCommit> oldTreeRef : oldTreeRefs.entrySet()) {
+						final List<RevCommit> commits = fs.getGit().listCommits(oldTreeRef.getValue(),
+								fs.getGit().getLastCommit(oldTreeRef.getKey()));
+						for (final RevCommit revCommit : commits) {
+							final RevTree parent = revCommit.getParentCount() > 0 ? revCommit.getParent(0).getTree()
+									: null;
+							notifyDiffs(fs, oldTreeRef.getKey(), "<" + protocol + ">", user.getIdentifier(),
+									revCommit.getFullMessage(), parent, revCommit.getTree());
+						}
+					}
+				});
+			}
+		};
+	}
+
+	public <T> RepositoryResolverImpl<T> getRepositoryResolver() {
+		return new RepositoryResolverImpl<>();
+	}
+
+	void buildAndStartDaemon() {
+		if (daemonService == null || !daemonService.isRunning()) {
+			daemonService = new Daemon(new InetSocketAddress(config.getDaemonHostAddr(), config.getDaemonPort()),
+					new ExecutorWrapper(executorService), executorService, config.isEnableKetch() ? leaders : null);
+			daemonService.setRepositoryResolver(new RepositoryResolverImpl<>());
+			try {
+				daemonService.start();
+			} catch (java.io.IOException e) {
+				throw new RuntimeException(e);
+			}
+		}
+	}
+
+	private void shutdownSSH() {
+		if (gitSSHService != null) {
+			gitSSHService.stop();
+		}
+	}
+
+	void forceStopDaemon() {
+		if (daemonService != null && daemonService.isRunning()) {
+			daemonService.stop();
+		}
+	}
+
+	/**
+	 * Closes and disposes all open filesystems and stops the Git and SSH daemons if
+	 * they are running. This filesystem provider can be reactivated by attempting
+	 * to open a new filesystem or by creating a new filesystem.
+	 */
+	public void shutdown() {
+
+		for (JGitFileSystem jGitFileSystem : fsManager.getOpenFileSystems()) {
+			try {
+				jGitFileSystem.close();
+			} catch (IOException e) {
+				LOG.error("Failed to close the file system [" + jGitFileSystem.getName() + "].", e);
+			}
+		}
+		shutdownSSH();
+		forceStopDaemon();
+		fsManager.clear();
+	}
+
+	/**
+	 * Returns the directory that contains all the git repositories managed by this
+	 * file system provider.
+	 */
+	public File getGitRepoContainerDir() {
+		return config.getGitReposParentDir();
+	}
+
+	@Override
+	public String getScheme() {
+		return SCHEME;
+	}
+
+	@Override
+	public FileSystem newFileSystem(final Path path, final Map<String, ?> env)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public FileSystem newFileSystem(final URI uri, final Map<String, ?> env)
+			throws IllegalArgumentException, IOException, SecurityException, FileSystemAlreadyExistsException {
+		checkNotNull("uri", uri);
+		checkCondition("uri scheme not supported", uri.getScheme().equals(getScheme()));
+		checkURI("uri", uri);
+		checkNotNull("env", env);
+
+		String fsName = extractFSName(uri);
+
+		validateFSName(uri, fsName);
+
+		String envUsername = extractEnvProperty(GIT_ENV_KEY_USER_NAME, env);
+		String envPassword = extractEnvProperty(GIT_ENV_KEY_PASSWORD, env);
+
+		fsManager.newFileSystem(() > fullHostNames, () > createNewGitRepo(env, fsName), () > fsName,
+				() > buildCredential(envUsername, envPassword), () > fsEventsManager, () > extractFSHooks(env));
+
+		JGitFileSystem fs = fsManager.get(fsName);
+
+		boolean init = false;
+
+		if (env.containsKey(GIT_ENV_KEY_INIT) && Boolean.parseBoolean(env.get(GIT_ENV_KEY_INIT).toString())) {
+			init = true;
+		}
+
+		if (!env.containsKey(GIT_ENV_KEY_DEFAULT_REMOTE_NAME) && init) {
+			try {
+				final URI initURI = URI.create(getScheme() + "://master@" + fsName + "/readme.md");
+				final OutputStream stream = newOutputStream(getPath(initURI), (CommentedOption) null);
+				final String _init = "Repository Init Content\n" + "=======================\n" + "\n"
+						+ "Your project description here.";
+				stream.write(_init.getBytes());
+				stream.close();
+			} catch (final Exception e) {
+				e.printStackTrace();
+				LOG.info("Repository initialization may have failed.", e);
+			}
+		}
+
+		if (config.isEnableKetch()) {
+			createNewGitRepo(env, fsName).enableKetch();
+		}
+
+		if (config.isDaemonEnabled() && daemonService != null && !daemonService.isRunning()) {
+			buildAndStartDaemon();
+		}
+
+		return fs;
+	}
+
+	static Map<FileSystemHooks, ?> extractFSHooks(Map<String, ?> env) {
+
+		return Arrays.stream(FileSystemHooks.values()).filter(h > env.get(h.name()) != null)
+				.collect(Collectors.toMap(Function.identity(), k > env.get(k.name())));
+	}
+
+	private void validateFSName(URI uri, String fsName) {
+		if (fsManager.containsKey(fsName)) {
+			throw new FileSystemAlreadyExistsException("There is already a FS for " + uri + ".");
+		}
+		if (fsName.contains("/")) {
+			String fsNameRoot = fsName.substring(0, fsName.indexOf("/"));
+			if (fsManager.containsKey(fsNameRoot)) {
+				throw new AmbiguousFileSystemNameException(
+						"The file system name" + fsName + " is ambiguous with" + " another FS registered");
+			}
+		}
+		if (fsManager.containsRoot(fsName)) {
+			throw new AmbiguousFileSystemNameException(
+					"The file system name" + fsName + " is ambiguous with" + " another FS registered");
+		}
+	}
+
+	private String extractEnvProperty(String key, Map<String, ?> env) {
+		if (env == null || env.get(key) == null) {
+			return null;
+		}
+		return env.get(key).toString();
+	}
+
+	protected Git createNewGitRepo(Map<String, ?> env, String fsName) {
+
+		final File repoDest = getRepoDest(env, fsName);
+
+		File directory = repoDest.getParentFile();
+		String lockName = directory.getName();
+
+		if (!directory.exists()) {
+			directory.mkdirs();
+		}
+
+		FileSystemLock physicalLock = createLock(directory, lockName);
+		try {
+			physicalLock.lock();
+
+			return createNewGitRepo(env, fsName, repoDest);
+		} finally {
+			physicalLock.unlock();
+		}
+	}
+
+	protected Git createNewGitRepo(Map<String, ?> env, String fsName, File repoDest) {
+		final Git git;
+
+		String envUsername = extractEnvProperty(GIT_ENV_KEY_USER_NAME, env);
+		String envPassword = extractEnvProperty(GIT_ENV_KEY_PASSWORD, env);
+		Boolean envMirror = (Boolean) env.get(GIT_ENV_KEY_MIRROR);
+		boolean isMirror = envMirror == null ? true : envMirror;
+
+		CredentialsProvider credential = buildCredential(envUsername, envPassword);
+
+		if (syncWithRemote(env, repoDest)) {
+			final String origin = env.get(GIT_ENV_KEY_DEFAULT_REMOTE_NAME).toString();
+			final List<String> branches = (List<String>) env.get(GIT_ENV_KEY_BRANCH_LIST);
+			final String subdirectory = (String) env.get(GIT_ENV_KEY_SUBDIRECTORY);
+			try {
+				if (this.isForkOrigin(origin)) {
+					git = Git.fork(this.getGitRepoContainerDir(), origin, fsName, branches, credential,
+							config.isEnableKetch() ? leaders : null, config.getHookDir(), config.isSslVerify());
+				} else if (subdirectory != null) {
+					if (isMirror) {
+						throw new UnsupportedOperationException(
+								"Cannot make mirror repository when cloning subdirectory.");
+					}
+					git = Git.cloneSubdirectory(repoDest, origin, subdirectory, branches, credential, leaders,
+							config.getHookDir(), config.isSslVerify());
+				} else {
+					git = Git.clone(repoDest, origin, isMirror, branches, credential,
+							config.isEnableKetch() ? leaders : null, config.getHookDir(), config.isSslVerify());
+				}
+			} catch (Clone.CloneException | IOException ce) {
+				fsManager.remove(fsName);
+				throw new RuntimeException(ce);
+			}
+		} else {
+			git = Git.createRepository(repoDest, config.getHookDir(), config.isEnableKetch() ? leaders : null,
+					config.isSslVerify());
+		}
+		return git;
+	}
+
+	private FileSystemLock createLock(File directory, String lockName) {
+		return FileSystemLockManager.getInstance().getFileSystemLock(directory, lockName + ".lock",
+				LOCK_LAST_ACCESS_TIME_UNIT, LOCK_LAST_ACCESS_THRESHOLD);
+	}
+
+	private File getRepoDest(Map<String, ?> env, String fsName) {
+		final String outPath = (String) env.get(GIT_ENV_KEY_DEST_PATH);
+		final File repoDest;
+
+		if (outPath != null) {
+			repoDest = new File(outPath, fsName + DOT_GIT_EXT);
+		} else {
+			repoDest = new File(config.getGitReposParentDir(), fsName + DOT_GIT_EXT);
+		}
+		return repoDest;
+	}
+
+	private boolean syncWithRemote(Map<String, ?> env, File repoDest) {
+		return env.containsKey(GIT_ENV_KEY_DEFAULT_REMOTE_NAME) && !repoDest.exists();
+	}
+
+	String extractFSName(final URI _uri) {
+		String uri = _uri.toString().replace("git://", "").replace("default://", "");
+		if (uri.endsWith("/")) {
+			uri = uri.substring(0, uri.length()  1);
+		}
+		if (uri.contains("@")) {
+			uri = uri.substring(uri.indexOf('@') + 1);
+		}
+		if (uri.contains(":")) {
+			uri = uri.substring(0, uri.indexOf(':')  1);
+		}
+
+		return uri;
+	}
+
+	private boolean isForkOrigin(final String originURI) {
+		return originURI.matches("(^\\w+\\/\\w+$)");
+	}
+
+	@Override
+	public FileSystem getFileSystem(final URI uri)
+			throws IllegalArgumentException, FileSystemNotFoundException, SecurityException {
+		checkNotNull("uri", uri);
+		checkCondition("uri scheme not supported", uri.getScheme().equals(getScheme()));
+		checkURI("uri", uri);
+
+		JGitFileSystem fileSystem = deepLookupFSFrom(uri);
+
+		if (hasSyncFlag(uri)) {
+			try {
+				final String treeRef = "master";
+				final ObjectId oldHead = fileSystem.getGit().getTreeFromRef(treeRef);
+				final Map<String, String> params = getQueryParams(uri);
+				try {
+					fileSystem.lock();
+					final Map.Entry<String, String> remote = new AbstractMap.SimpleEntry<>("upstream",
+							params.get("sync"));
+					fileSystem.getGit().fetch(fileSystem.getCredential(), remote, emptyList());
+					fileSystem.getGit().syncRemote(remote);
+				} finally {
+					fileSystem.unlock();
+				}
+				final ObjectId newHead = fileSystem.getGit().getTreeFromRef(treeRef);
+				notifyDiffs(fileSystem, treeRef, "<system>", "<system>", "", oldHead, newHead);
+			} catch (final Exception ex) {
+				throw new RuntimeException("Failed to sync repository.", ex);
+			}
+		}
+		if (hasPushFlag(uri)) {
+			try {
+				final Map<String, String> params = getQueryParams(uri);
+				fileSystem.getGit().push(fileSystem.getCredential(),
+						new AbstractMap.SimpleEntry<>("upstream", params.get("push")), hasForceFlag(uri), emptyList());
+			} catch (final Exception ex) {
+				throw new RuntimeException("Failed to push repository.", ex);
+			}
+		}
+
+		return fileSystem;
+	}
+
+	String extractFSNameWithPath(final URI uri) {
+		checkNotNull("uri", uri);
+
+		String path = uri.getAuthority() + uri.getPath();
+
+		int index = path.indexOf('@');
+		if (index != 1) {
+			path = path.substring(index + 1);
+		}
+		return path;
+	}
+
+	@Override
+	public Path getPath(final URI uri) throws IllegalArgumentException, FileSystemNotFoundException, SecurityException {
+		checkNotNull("uri", uri);
+		checkCondition("uri scheme not supported", uri.getScheme().equals(getScheme()));
+		checkURI("uri", uri);
+
+		if (LOG.isDebugEnabled()) {
+			LOG.debug("Accessing uri " + uri.toString());
+		}
+
+		Path path;
+
+		JGitFileSystem fileSystem = deepLookupFSFrom(uri);
+
+		String branch = extractBranchFrom(uri);
+
+		String host = buildHostFrom(fileSystem, branch);
+
+		String pathStr = buildPathFrom(uri, host);
+		path = JGitPathImpl.create(fileSystem, pathStr, host, false);
+
+		return path;
+	}
+
+	private String buildPathFrom(URI uri, String host) {
+		String pathStr = uri.toString();
+		pathStr = pathStr.replace(host, "");
+		pathStr = pathStr.replace("git://", "").replace("default://", "");
+		pathStr = EncodingUtil.decode(pathStr);
+		if (pathStr.startsWith("/:")) {
+			pathStr = pathStr.substring(2);
+		}
+		return pathStr;
+	}
+
+	private String buildHostFrom(JGitFileSystem fileSystem, String branch) {
+		String host = branch + fileSystem.getName();
+
+		host = host.replace("git://", "").replace("default://", "");
+		return host;
+	}
+
+	private String extractBranchFrom(URI uri) {
+		String branch = "";
+
+		int index = uri.toString().indexOf('@');
+		if (index != 1) {
+			branch = uri.toString().substring(0, index + 1);
+		}
+		return branch;
+	}
+
+	public String extractPath(final URI uri) {
+		checkNotNull("uri", uri);
+
+		return getPath(uri).toString();
+	}
+
+	private JGitFileSystem deepLookupFSFrom(URI uri) {
+
+		String fullURI = extractFSNameWithPath(uri);
+		int index = fullURI.indexOf("/");
+		JGitFileSystem jGitFileSystem = fsManager.get(fullURI);
+
+		while (jGitFileSystem == null && index >= 0) {
+
+			String fsCandidate = fullURI.substring(0, index);
+			jGitFileSystem = fsManager.get(fsCandidate);
+
+			index = fullURI.indexOf("/", index + 1);
+		}
+
+		if (jGitFileSystem == null) {
+			throw new FileSystemNotFoundException("No filesystem for uri (" + uri + ") found.");
+		}
+
+		return jGitFileSystem;
+	}
+
+	@Override
+	public InputStream newInputStream(final Path path, final OpenOption... options)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("path", path);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		return cast(gPath.getFileSystem()).getGit().blobAsInputStream(gPath.getRefTree(), gPath.getPath());
+	}
+
+	@Override
+	public OutputStream newOutputStream(final Path path, final OpenOption... options)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("path", path);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(), gPath.getPath());
+
+		if (result.getPathType().equals(PathType.DIRECTORY)) {
+			throw new NotDirectoryException(path.toString());
+		}
+
+		try {
+			final File file = File.createTempFile("gitz", "woot");
+			return new FilterOutputStream(new FileOutputStream(file)) {
+				@Override
+				public void close() throws java.io.IOException {
+					super.close();
+
+					commit(gPath, buildCommitInfo("{" + toPathImpl(path).getPath() + "}", Arrays.asList(options)),
+							new DefaultCommitContent(new HashMap<String, File>() {
+								{
+									put(gPath.getPath(), file);
+								}
+							}));
+				}
+			};
+		} catch (java.io.IOException e) {
+			throw new IOException("Could not create file or output stream.", e);
+		}
+	}
+
+	private JGitFileSystem cast(final FileSystem fileSystem) {
+		return (JGitFileSystem) fileSystem;
+	}
+
+	private CommitInfo buildCommitInfo(final String defaultMessage, final Collection<?> options) {
+		String sessionId = null;
+		String name = null;
+		String email = null;
+		String message = defaultMessage;
+		TimeZone timeZone = null;
+		Date when = null;
+
+		if (options != null && !options.isEmpty()) {
+			final CommentedOption op = extractCommentedOption(options);
+			if (op != null) {
+				sessionId = op.getSessionId();
+				name = op.getName();
+				email = op.getEmail();
+				if (op.getMessage() != null && !op.getMessage().trim().isEmpty()) {
+					message = op.getMessage() + " " + defaultMessage;
+				}
+				timeZone = op.getTimeZone();
+				when = op.getWhen();
+			}
+		}
+
+		return new CommitInfo(sessionId, name, email, message, timeZone, when);
+	}
+
+	private CommentedOption extractCommentedOption(final Collection<?> options) {
+		for (final Object option : options) {
+			if (option instanceof CommentedOption) {
+				return (CommentedOption) option;
+			}
+		}
+		return null;
+	}
+
+	@Override
+	public FileChannel newFileChannel(final Path path, Set<? extends OpenOption> options,
+			final FileAttribute<?>... attrs)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public AsynchronousFileChannel newAsynchronousFileChannel(final Path path, final Set<? extends OpenOption> options,
+			final ExecutorService executor, FileAttribute<?>... attrs)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public SeekableByteChannel newByteChannel(final Path path, final Set<? extends OpenOption> options,
+			final FileAttribute<?>... attrs)
+			throws IllegalArgumentException, UnsupportedOperationException, IOException, SecurityException {
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		if (exists(path)) {
+			if (!shouldCreateOrOpenAByteChannel(options)) {
+				throw new FileAlreadyExistsException(path.toString());
+			}
+		}
+
+		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(), gPath.getPath());
+
+		if (result.getPathType().equals(PathType.DIRECTORY)) {
+			throw new NotDirectoryException(path.toString());
+		}
+
+		try {
+			if (options != null && options.contains(READ)) {
+				return openAByteChannel(path);
+			} else {
+				return createANewByteChannel(path, options, gPath, attrs);
+			}
+		} catch (java.io.IOException e) {
+			throw new IOException("Failed to open or create a byte channel.", e);
+		} finally {
+			cast(path).clearCache();
+		}
+	}
+
+	private SeekableByteChannel createANewByteChannel(final Path path, final Set<? extends OpenOption> options,
+			final JGitPathImpl gPath, final FileAttribute<?>[] attrs) throws java.io.IOException {
+		final File file = File.createTempFile("gitz", "woot");
+
+		return new SeekableByteChannelWrapper(new RandomAccessFile(file, "rw").getChannel()) {
+			@Override
+			public void close() throws java.io.IOException {
+				super.close();
+				commit(gPath, buildCommitInfo("{" + toPathImpl(path).getPath() + "}", options),
+						new DefaultCommitContent(new HashMap<String, File>() {
+							{
+								put(gPath.getPath(), file);
+							}
+						}));
+			}
+		};
+	}
+
+	private SeekableByteChannel openAByteChannel(Path path) throws FileNotFoundException {
+		return new RandomAccessFile(path.toFile(), "r").getChannel();
+	}
+
+	private boolean shouldCreateOrOpenAByteChannel(Set<? extends OpenOption> options) {
+		return (options != null && (options.contains(TRUNCATE_EXISTING) || options.contains(READ)));
+	}
+
+	protected boolean exists(final Path path) {
+		try {
+			readAttributes(path, BasicFileAttributes.class);
+			return true;
+		} catch (final Exception ignored) {
+			// this means the file does not exist
+		}
+		return false;
+	}
+
+	@Override
+	public DirectoryStream<Path> newDirectoryStream(final Path path, final DirectoryStream.Filter<? super Path> pfilter)
+			throws IOException, SecurityException {
+		checkNotNull("path", path);
+		final DirectoryStream.Filter<? super Path> filter;
+		if (pfilter == null) {
+			filter = entry > true;
+		} else {
+			filter = pfilter;
+		}
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(), gPath.getPath());
+
+		if (!result.getPathType().equals(PathType.DIRECTORY)) {
+			throw new NotDirectoryException(path.toString());
+		}
+
+		final List<PathInfo> pathContent = cast(gPath.getFileSystem()).getGit().listPathContent(gPath.getRefTree(),
+				gPath.getPath());
+
+		return new DirectoryStream<Path>() {
+			boolean isClosed = false;
+
+			@Override
+			public void close() throws IOException {
+				if (isClosed) {
+					throw new IOException("This stream is closed.");
+				}
+				isClosed = true;
+			}
+
+			@Override
+			public Iterator<Path> iterator() {
+				if (isClosed) {
+					throw new RuntimeException("This stream is closed.");
+				}
+				return new Iterator<Path>() {
+					int i = 1;
+					Path nextEntry = null;
+					boolean atEof = false;
+
+					@Override
+					public boolean hasNext() {
+						if (nextEntry == null && !atEof) {
+							nextEntry = readNextEntry();
+						}
+						return nextEntry != null;
+					}
+
+					@Override
+					public Path next() {
+						final Path result;
+						if (nextEntry == null && !atEof) {
+							result = readNextEntry();
+						} else {
+							result = nextEntry;
+							nextEntry = null;
+						}
+						if (result == null) {
+							throw new NoSuchElementException();
+						}
+						return result;
+					}
+
+					private Path readNextEntry() {
+						if (atEof) {
+							return null;
+						}
+
+						Path result = null;
+						while (true) {
+							i++;
+							if (i >= pathContent.size()) {
+								atEof = true;
+								break;
+							}
+
+							final PathInfo content = pathContent.get(i);
+							final Path path = JGitPathImpl.create(gPath.getFileSystem(), "/" + content.getPath(),
+									gPath.getHost(), content.getObjectId(), gPath.isRealPath());
+							try {
+								if (filter.accept(path)) {
+									result = path;
+									break;
+								}
+							} catch (IOException e) {
+								throw new RuntimeException(e);
+							}
+						}
+
+						return result;
+					}
+
+					@Override
+					public void remove() {
+						throw new UnsupportedOperationException();
+					}
+				};
+			}
+		};
+	}
+
+	@Override
+	public void createDirectory(final Path path, final FileAttribute<?>... attrs)
+			throws UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("path", path);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(), gPath.getPath());
+
+		if (!result.getPathType().equals(NOT_FOUND)) {
+			throw new FileAlreadyExistsException(path.toString());
+		}
+
+		try {
+			final OutputStream outputStream = newOutputStream(path.resolve(".gitkeep"));
+			outputStream.close();
+		} catch (final Exception e) {
+			throw new IOException("Failed to write to or close the output stream.", e);
+		}
+	}
+
+	@Override
+	public void createSymbolicLink(final Path link, final Path target, final FileAttribute<?>... attrs)
+			throws UnsupportedOperationException, IOException, SecurityException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public void createLink(final Path link, final Path existing)
+			throws UnsupportedOperationException, IOException, SecurityException {
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public void delete(final Path path) throws IOException, SecurityException {
+		checkNotNull("path", path);
+
+		if (path instanceof JGitFSPath) {
+			deleteFS(path.getFileSystem());
+			return;
+		}
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		if (isBranch(gPath)) {
+			deleteBranch(gPath);
+			return;
+		}
+
+		deleteAsset(gPath);
+	}
+
+	protected boolean deleteFS(final FileSystem _fs) throws IOException {
+
+		final JGitFileSystemImpl fileSystem = (JGitFileSystemImpl) _fs;
+		final File gitDir = fileSystem.getGit().getRepository().getDirectory();
+		File parentDir = gitDir.getParentFile();
+		FileSystemLock physicalLock = createLock(parentDir, parentDir.getName());
+
+		try {
+			physicalLock.lock();
+			fileSystem.close();
+			fileSystem.dispose();
+			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
+				// this operation forces a cache clean freeing any lock > windows only issue!
+				new WindowCacheConfig().install();
+			}
+
+			fsManager.remove(fileSystem.getName());
+
+			FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.SKIP_MISSING | FileUtils.RETRY);
+
+			cleanupParentDir(gitDir);
+			return true;
+		} catch (java.io.IOException e) {
+			throw new IOException("Failed to remove the git repository.", e);
+		} finally {
+			physicalLock.unlock();
+		}
+	}
+
+	private void cleanupParentDir(File gitDir) throws java.io.IOException {
+		final File parentDir = gitDir.getParentFile();
+		if (parentDir.isDirectory() && parentDirIsEmpty(parentDir) && !parentDir.equals(getGitRepoContainerDir())) {
+			FileUtils.delete(parentDir, FileUtils.RECURSIVE | FileUtils.RETRY);
+		}
+	}
+
+	private boolean parentDirIsEmpty(File parentDir) {
+		return parentDir.list().length == 0;
+	}
+
+	public void deleteAsset(final JGitPathImpl path) throws IOException {
+		final PathInfo result = cast(path.getFileSystem()).getGit().getPathInfo(path.getRefTree(), path.getPath());
+
+		if (result.getPathType().equals(PathType.DIRECTORY)) {
+			if (deleteNonEmptyDirectory()) {
+				deleteResource(path);
+				return;
+			}
+			final List<PathInfo> content = cast(path.getFileSystem()).getGit().listPathContent(path.getRefTree(),
+					path.getPath());
+			if (content.size() == 1 && content.get(0).getPath().equals(path.getPath().substring(1) + "/.gitkeep")) {
+				delete(path.resolve(".gitkeep"));
+				deleteResource(path);
+				return;
+			}
+			throw new DirectoryNotEmptyException(path.toString());
+		}
+
+		if (result.getPathType().equals(NOT_FOUND)) {
+			throw new NoSuchFileException(path.toString());
+		}
+
+		deleteResource(path);
+	}
+
+	private void deleteResource(final JGitPathImpl path) {
+		delete(path, buildCommitInfo("delete {" + path.getPath() + "}", emptyList()));
+	}
+
+	private boolean deleteNonEmptyDirectory() {
+		return false;
+	}
+
+	public void deleteBranch(final JGitPathImpl path) throws IOException {
+		final Ref branch = cast(path.getFileSystem()).getGit().getRef(path.getRefTree());
+
+		if (branch == null) {
+			throw new NoSuchFileException(path.toString());
+		}
+
+		try {
+			cast(path.getFileSystem()).lock();
+			cast(path.getFileSystem()).getGit().deleteRef(branch);
+		} finally {
+			cast(path.getFileSystem()).unlock();
+		}
+	}
+
+	@Override
+	public boolean deleteIfExists(final Path path) throws IOException, SecurityException {
+		checkNotNull("path", path);
+
+		if (path instanceof JGitFSPath) {
+			return deleteFS(path.getFileSystem());
+		}
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		if (isBranch(gPath)) {
+			return deleteBranchIfExists(gPath);
+		}
+
+		return deleteAssetIfExists(gPath);
+	}
+
+	public boolean deleteBranchIfExists(final JGitPathImpl path) throws IOException {
+		try {
+			deleteBranch(path);
+			return true;
+		} catch (final NoSuchFileException ignored) {
+			return false;
+		}
+	}
+
+	public boolean deleteAssetIfExists(final JGitPathImpl path) throws IOException {
+		final PathInfo result = cast(path.getFileSystem()).getGit().getPathInfo(path.getRefTree(), path.getPath());
+
+		if (result.getPathType().equals(PathType.DIRECTORY)) {
+			if (deleteNonEmptyDirectory()) {
+				deleteResource(path);
+				return true;
+			}
+			final List<PathInfo> content = cast(path.getFileSystem()).getGit().listPathContent(path.getRefTree(),
+					path.getPath());
+			if (content.size() == 1 && content.get(0).getPath().equals(path.getPath().substring(1) + "/.gitkeep")) {
+				delete(path.resolve(".gitkeep"));
+				return true;
+			}
+			throw new DirectoryNotEmptyException(path.toString());
+		}
+
+		if (result.getPathType().equals(NOT_FOUND)) {
+			return false;
+		}
+
+		deleteResource(path);
+		return true;
+	}
+
+	@Override
+	public Path readSymbolicLink(final Path link) throws UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("link", link);
+		throw new UnsupportedOperationException();
+	}
+
+	@Override
+	public void copy(final Path source, final Path target, final CopyOption... options)
+			throws UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("source", source);
+		checkNotNull("target", target);
+
+		final JGitPathImpl gSource = toPathImpl(source);
+		final JGitPathImpl gTarget = toPathImpl(target);
+		final boolean isBranch = isBranch(gSource) && isBranch(gTarget);
+
+		if (options.length == 1 && options[0] instanceof MergeCopyOption) {
+			if (!isBranch) {
+				throw new IOException("Merge needs source and target as root.");
+			}
+			this.merge(gSource, gTarget);
+		} else if (options.length == 1 && options[0] instanceof CherryPickCopyOption) {
+			if (!isBranch) {
+				throw new IOException("Cherry pick needs source and target as root.");
+			}
+			final String[] commits = ((CherryPickCopyOption) options[0]).getCommits();
+			if (commits == null || commits.length == 0) {
+				throw new IOException("Cherry pick needs at least one commit id.");
+			}
+			cherryPick(gSource, gTarget, commits);
+		} else {
+			if (isBranch) {
+				copyBranch(gSource, gTarget);
+				return;
+			}
+			copyAsset(gSource, gTarget, options);
+		}
+	}
+
+	private void merge(final JGitPathImpl source, final JGitPathImpl target) throws IOException {
+
+		try {
+			cast(target.getFileSystem()).lock();
+			cast(source.getFileSystem()).getGit().merge(source.getRefTree(), target.getRefTree());
+		} finally {
+			cast(target.getFileSystem()).unlock();
+		}
+	}
+
+	private void cherryPick(final JGitPathImpl source, final JGitPathImpl target, final String... commits)
+			throws IOException {
+		try {
+			cast(target.getFileSystem()).lock();
+			cast(source.getFileSystem()).getGit().cherryPick(target, commits);
+		} finally {
+			cast(target.getFileSystem()).unlock();
+		}
+	}
+
+	private void copyBranch(final JGitPathImpl source, final JGitPathImpl target)
+			throws FileAlreadyExistsException, NoSuchFileException {
+		checkCondition("source and target should have same file system", hasSameFileSystem(source, target));
+		if (existsBranch(target)) {
+			throw new FileAlreadyExistsException(target.toString());
+		}
+		if (!existsBranch(source)) {
+			throw new NoSuchFileException(target.toString());
+		}
+		createBranch(source, target);
+	}
+
+	private void copyAsset(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree(),
+				source.getPath());
+		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree(),
+				target.getPath());
+
+		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
+			if (!contains(options, StandardCopyOption.REPLACE_EXISTING)) {
+				throw new FileAlreadyExistsException(target.toString());
+			}
+		}
+
+		if (sourceResult.getPathType() == NOT_FOUND) {
+			throw new NoSuchFileException(target.toString());
+		}
+
+		if (!source.getRefTree().equals(target.getRefTree())) {
+			copyAssetContent(source, target, options);
+		} else if (!source.getFileSystem().equals(target.getFileSystem())) {
+			copyAssetContent(source, target, options);
+		} else {
+			final Map<JGitPathImpl, JGitPathImpl> sourceDest = new HashMap<>();
+			if (sourceResult.getPathType() == DIRECTORY) {
+				sourceDest.putAll(mapDirectoryContent(source, target, options));
+			} else {
+				sourceDest.put(source, target);
+			}
+
+			copyFiles(source, target, sourceDest, options);
+		}
+	}
+
+	private void copyAssetContent(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree(),
+				source.getPath());
+		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree(),
+				target.getPath());
+
+		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
+			if (!contains(options, StandardCopyOption.REPLACE_EXISTING)) {
+				throw new FileAlreadyExistsException(target.toString());
+			}
+		}
+
+		if (sourceResult.getPathType() == NOT_FOUND) {
+			throw new NoSuchFileException(target.toString());
+		}
+
+		if (sourceResult.getPathType() == DIRECTORY) {
+			copyDirectory(source, target, options);
+			return;
+		}
+
+		copyFile(source, target, options);
+	}
+
+	private boolean contains(final CopyOption[] options, final CopyOption opt) {
+		for (final CopyOption option : options) {
+			if (option.equals(opt)) {
+				return true;
+			}
+		}
+		return false;
+	}
+
+	private void copyDirectory(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+		final List<JGitPathImpl> directories = new ArrayList<>();
+		for (final Path path : newDirectoryStream(source, null)) {
+			final JGitPathImpl gPath = toPathImpl(path);
+			final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(),
+					gPath.getPath());
+			if (pathResult.getPathType() == DIRECTORY) {
+				directories.add(gPath);
+				continue;
+			}
+			final JGitPathImpl gTarget = composePath(target, (JGitPathImpl) gPath.getFileName());
+
+			copyFile(gPath, gTarget);
+		}
+		for (final JGitPathImpl directory : directories) {
+			createDirectory(composePath(target, (JGitPathImpl) directory.getFileName()));
+		}
+	}
+
+	private JGitPathImpl composePath(final JGitPathImpl directory, final JGitPathImpl fileName,
+			final CopyOption... options) {
+		if (directory.getPath().endsWith("/")) {
+			return toPathImpl(getPath(URI.create(directory.toUri().toString() + uriEncode(fileName.toString(false)))));
+		}
+		return toPathImpl(
+				getPath(URI.create(directory.toUri().toString() + "/" + uriEncode(fileName.toString(false)))));
+	}
+
+	private String uriEncode(final String s) {
+		try {
+			return URLEncoder.encode(s, "UTF8");
+		} catch (UnsupportedEncodingException e) {
+			return s;
+		}
+	}
+
+	private void copyFile(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+
+		final InputStream in = newInputStream(source, convert(options));
+		final SeekableByteChannel out = newByteChannel(target, new HashSet<OpenOption>() {
+			{
+				add(StandardOpenOption.TRUNCATE_EXISTING);
+				for (final CopyOption _option : options) {
+					if (_option instanceof OpenOption) {
+						add((OpenOption) _option);
+					}
+				}
+			}
+		});
+
+		try {
+			int count;
+			byte[] buffer = new byte[8192];
+			while ((count = in.read(buffer)) > 0) {
+				out.write(ByteBuffer.wrap(buffer, 0, count));
+			}
+		} catch (Exception e) {
+			throw new IOException("Failed to copy file from '" + source + "' to '" + target + "'", e);
+		} finally {
+			try {
+				out.close();
+			} catch (java.io.IOException e) {
+				throw new IOException("Could not close output stream.", e);
+			} finally {
+				try {
+					in.close();
+				} catch (java.io.IOException e) {
+					throw new IOException("Could not close input stream.", e);
+				}
+			}
+		}
+	}
+
+	private OpenOption[] convert(CopyOption... options) {
+		if (options == null || options.length == 0) {
+			return new OpenOption[0];
+		}
+		final List<OpenOption> newOptions = new ArrayList<>(options.length);
+		for (final CopyOption option : options) {
+			if (option instanceof OpenOption) {
+				newOptions.add((OpenOption) option);
+			}
+		}
+
+		return newOptions.toArray(new OpenOption[newOptions.size()]);
+	}
+
+	private void createBranch(final JGitPathImpl source, final JGitPathImpl target) {
+		try {
+			cast(target.getFileSystem()).lock();
+			cast(source.getFileSystem()).getGit().createRef(source.getRefTree(), target.getRefTree());
+		} finally {
+			cast(target.getFileSystem()).unlock();
+		}
+	}
+
+	private boolean existsBranch(final JGitPathImpl path) {
+		return cast(path.getFileSystem()).getGit().getRef(path.getRefTree()) != null;
+	}
+
+	private boolean isBranch(final JGitPathImpl path) {
+		return path.getPath().length() == 1 && path.getPath().equals("/");
+	}
+
+	private boolean isRoot(final JGitPathImpl path) {
+		return isBranch(path);
+	}
+
+	private boolean hasSameFileSystem(final JGitPathImpl source, final JGitPathImpl target) {
+		return source.getFileSystem().equals(target.getFileSystem());
+	}
+
+	@Override
+	public void move(final Path source, final Path target, final CopyOption... options)
+			throws DirectoryNotEmptyException, AtomicMoveNotSupportedException, IOException, SecurityException {
+		checkNotNull("source", source);
+		checkNotNull("target", target);
+
+		final JGitPathImpl gSource = toPathImpl(source);
+		final JGitPathImpl gTarget = toPathImpl(target);
+
+		final boolean isSourceBranch = isBranch(gSource);
+		final boolean isTargetBranch = isBranch(gTarget);
+
+		if (isSourceBranch && isTargetBranch) {
+			moveBranch(gSource, gTarget, options);
+			return;
+		}
+		moveAsset(gSource, gTarget, options);
+	}
+
+	private void moveBranch(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+		checkCondition("source and target should have same file system", hasSameFileSystem(source, target));
+
+		if (!exists(source)) {
+			throw new NoSuchFileException(target.toString());
+		}
+
+		boolean targetExists = existsBranch(target);
+		if (targetExists && !contains(options, StandardCopyOption.REPLACE_EXISTING)) {
+			throw new FileAlreadyExistsException(target.toString());
+		}
+
+		if (!targetExists) {
+			createBranch(source, target);
+			deleteBranch(source);
+		} else {
+			commit(target, buildCommitInfo("reverting from {" + source.getPath() + "}", Arrays.asList(options)),
+					new RevertCommitContent(source.getRefTree()));
+		}
+	}
+
+	private void moveAsset(final JGitPathImpl source, final JGitPathImpl target, final CopyOption... options)
+			throws IOException {
+		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree(),
+				source.getPath());
+		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree(),
+				target.getPath());
+
+		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
+			if (!contains(options, StandardCopyOption.REPLACE_EXISTING)) {
+				throw new FileAlreadyExistsException(target.toString());
+			}
+		}
+
+		if (sourceResult.getPathType() == NOT_FOUND) {
+			throw new NoSuchFileException(target.toString());
+		}
+
+		if (!source.getRefTree().equals(target.getRefTree())) {
+			copy(source, target, options);
+			delete(source);
+		} else {
+			final Map<JGitPathImpl, JGitPathImpl> fromTo = new HashMap<>();
+			if (sourceResult.getPathType() == DIRECTORY) {
+				fromTo.putAll(mapDirectoryContent(source, target, options));
+			} else {
+				fromTo.put(source, target);
+			}
+
+			moveFiles(source, target, fromTo, options);
+		}
+	}
+
+	private Map<JGitPathImpl, JGitPathImpl> mapDirectoryContent(final JGitPathImpl source, final JGitPathImpl target,
+			final CopyOption... options) throws IOException {
+		final Map<JGitPathImpl, JGitPathImpl> fromTo = new HashMap<>();
+		for (final Path path : newDirectoryStream(source, null)) {
+			final JGitPathImpl gPath = toPathImpl(path);
+			final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(),
+					gPath.getPath());
+			if (pathResult.getPathType() == DIRECTORY) {
+				fromTo.putAll(mapDirectoryContent(gPath, composePath(target, (JGitPathImpl) gPath.getFileName())));
+			} else {
+				final JGitPathImpl gTarget = composePath(target, (JGitPathImpl) gPath.getFileName());
+				fromTo.put(gPath, gTarget);
+			}
+		}
+
+		return fromTo;
+	}
+
+	private void moveFiles(final JGitPathImpl source, final JGitPathImpl target,
+			final Map<JGitPathImpl, JGitPathImpl> fromTo, final CopyOption... options) {
+		final Map<String, String> result = new HashMap<>(fromTo.size());
+		for (final Map.Entry<JGitPathImpl, JGitPathImpl> fromToEntry : fromTo.entrySet()) {
+			result.put(PathUtil.normalize(fromToEntry.getKey().getPath()),
+					PathUtil.normalize(fromToEntry.getValue().getPath()));
+		}
+		commit(source, buildCommitInfo("moving from {" + source.getPath() + "} to {" + target.getPath() + "}",
+				Arrays.asList(options)), new MoveCommitContent(result));
+	}
+
+	private void copyFiles(final JGitPathImpl source, final JGitPathImpl target,
+			final Map<JGitPathImpl, JGitPathImpl> sourceDest, final CopyOption... options) {
+		final Map<String, String> result = new HashMap<>(sourceDest.size());
+		for (final Map.Entry<JGitPathImpl, JGitPathImpl> sourceDestEntry : sourceDest.entrySet()) {
+			result.put(PathUtil.normalize(sourceDestEntry.getKey().getPath()),
+					PathUtil.normalize(sourceDestEntry.getValue().getPath()));
+		}
+		commit(source, buildCommitInfo("copy from {" + source.getPath() + "} to {" + target.getPath() + "}",
+				Arrays.asList(options)), new CopyCommitContent(result));
+	}
+
+	@Override
+	public boolean isSameFile(final Path pathA, final Path pathB) throws IOException, SecurityException {
+		checkNotNull("pathA", pathA);
+		checkNotNull("pathB", pathB);
+
+		final JGitPathImpl gPathA = toPathImpl(pathA);
+		final JGitPathImpl gPathB = toPathImpl(pathB);
+
+		final PathInfo resultA = cast(gPathA.getFileSystem()).getGit().getPathInfo(gPathA.getRefTree(),
+				gPathA.getPath());
+		final PathInfo resultB = cast(gPathB.getFileSystem()).getGit().getPathInfo(gPathB.getRefTree(),
+				gPathB.getPath());
+
+		if (resultA.getPathType() == PathType.FILE && resultA.getObjectId().equals(resultB.getObjectId())) {
+			return true;
+		}
+
+		return pathA.equals(pathB);
+	}
+
+	@Override
+	public boolean isHidden(final Path path) throws IllegalArgumentException, IOException, SecurityException {
+		checkNotNull("path", path);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		if (gPath.getFileName() == null) {
+			return false;
+		}
+
+		return toPathImpl(path.getFileName()).toString(false).startsWith(".");
+	}
+
+	@Override
+	public FileStore getFileStore(final Path path) throws IOException, SecurityException {
+		checkNotNull("path", path);
+
+		return new JGitFileStore(cast(toPathImpl(path).getFileSystem()).getGit().getRepository());
+	}
+
+	@Override
+	public void checkAccess(final Path path, final AccessMode... modes) throws UnsupportedOperationException,
+			NoSuchFileException, AccessDeniedException, IOException, SecurityException {
+		checkNotNull("path", path);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(), gPath.getPath());
+
+		if (result.getPathType().equals(NOT_FOUND)) {
+			throw new NoSuchFileException(path.toString());
+		}
+	}
+
+	@Override
+	public <V extends FileAttributeView> V getFileAttributeView(final Path path, final Class<V> type,
+			final LinkOption... options) {
+		checkNotNull("path", path);
+		checkNotNull("type", type);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(),
+				gPath.getPath());
+		if (pathResult.getPathType().equals(NOT_FOUND)) {
+			throw new RuntimeException(path.toString());
+		}
+
+		final V resultView = gPath.getAttrView(type);
+
+		if (resultView == null) {
+			if (type == BasicFileAttributeView.class || type == JGitBasicAttributeView.class) {
+				final V newView = (V) new JGitBasicAttributeView(gPath);
+				gPath.addAttrView(newView);
+				return newView;
+			} else if (type == HiddenAttributeView.class || type == HiddenAttributeViewImpl.class
+					|| type == JGitHiddenAttributeViewImpl.class) {
+				final V newView = (V) new JGitHiddenAttributeViewImpl(gPath);
+				gPath.addAttrView(newView);
+				return newView;
+			} else if (type == VersionAttributeView.class || type == VersionAttributeViewImpl.class
+					|| type == JGitVersionAttributeViewImpl.class) {
+				final V newView = (V) new JGitVersionAttributeViewImpl(gPath);
+				gPath.addAttrView(newView);
+				return newView;
+			}
+		}
+
+		return resultView;
+	}
+
+	private ExtendedAttributeView getFileAttributeView(final JGitPathImpl path, final String name, final String params,
+			final LinkOption... options) {
+		final ExtendedAttributeView view = cast(path).getAttrView(name);
+
+		if (view == null) {
+			if (name.equals("basic")) {
+				final JGitBasicAttributeView newView = new JGitBasicAttributeView(path);
+				path.addAttrView(newView);
+				return newView;
+			} else if (name.equals("extended")) {
+				final JGitHiddenAttributeViewImpl newView = new JGitHiddenAttributeViewImpl(path);
+				path.addAttrView(newView);
+				return newView;
+			} else if (name.equals("version")) {
+				final JGitVersionAttributeViewImpl newView = new JGitVersionAttributeViewImpl(path);
+				path.addAttrView(newView);
+				return newView;
+			} else if (name.equals("diff")) {
+				final JGitDiffAttributeViewImpl newView = new JGitDiffAttributeViewImpl(path, params);
+				path.addAttrView(newView);
+				return newView;
+			}
+		}
+		return view;
+	}
+
+	private JGitPathImpl cast(final Path path) {
+		return (JGitPathImpl) path;
+	}
+
+	@Override
+	public <A extends BasicFileAttributes> A readAttributes(final Path path, final Class<A> type,
+			final LinkOption... options)
+			throws NoSuchFileException, UnsupportedOperationException, IOException, SecurityException {
+		checkNotNull("path", path);
+		checkNotNull("type", type);
+
+		final JGitPathImpl gPath = toPathImpl(path);
+
+		final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree(),
+				gPath.getPath());
+		if (pathResult.getPathType().equals(NOT_FOUND)) {
+			throw new NoSuchFileException(path.toString());
+		}
+
+		if (type == VersionAttributes.class) {
+			final JGitVersionAttributeViewImpl view = getFileAttributeView(path, JGitVersionAttributeViewImpl.class,
+					options);
+			return (A) view.readAttributes();
+		} else if (type == HiddenAttributes.class) {
+			final JGitHiddenAttributeViewImpl view = getFileAttributeView(path, JGitHiddenAttributeViewImpl.class,
+					options);
+			return (A) view.readAttributes();
+		} else if (type == DiffAttributes.class) {
+			final JGitDiffAttributeViewImpl view = getFileAttributeView(path, JGitDiffAttributeViewImpl.class, options);
+			return (A) view.readAttributes();
+		} else if (type == BasicFileAttributes.class) {
+			final JGitBasicAttributeView view = getFileAttributeView(path, JGitBasicAttributeView.class, options);
+			return (A) view.readAttributes();
+		}
+
+		return null;
+	}
+
+	@Override
+	public Map<String, Object> readAttributes(final Path path, final String attributes, final LinkOption... options)
+			throws UnsupportedOperationException, IllegalArgumentException, IOException, SecurityException {
+		checkNotNull("path", path);
+		checkNotEmpty("attributes", attributes);
+
+		final String[] s = split(attributes);
+		if (s[0].length() == 0) {
+			throw new IllegalArgumentException(attributes);
+		}
+
+		if (s[0].equals("diff")) {
+			final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path), s[0], s[1], options);
+
+			return view.readAttributes("diff");
+		} else {
+			final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path), s[0], s[1], options);
+			if (view == null) {
+				throw new UnsupportedOperationException("View '" + s[0] + "' not available");
+			}
+
+			return view.readAttributes(s[1].split(","));
+		}
+	}
+
+	@Override
+	public void setAttribute(final Path path, final String attribute, final Object value, final LinkOption... options)
+			throws UnsupportedOperationException, IllegalArgumentException, ClassCastException, IOException,
+			SecurityException {
+		checkNotNull("path", path);
+		checkNotEmpty("attributes", attribute);
+
+		if (attribute.equals(SquashOption.SQUASH_ATTR) && value instanceof SquashOption) {
+			this.lockAndSquash(path, (SquashOption) value);
+			return;
+		}
+
+		if (attribute.equals(FileSystemState.FILE_SYSTEM_STATE_ATTR)) {
+			JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
+			try {
+				fileSystem.lock();
+
+				if (value instanceof CommentedOption) {
+					fileSystem.setBatchCommitInfo("Batch mode", (CommentedOption) value);
+					fileSystem.unlock();
+					return;
+				}
+
+				final boolean isOriginalStateBatch = fileSystem.isOnBatch();
+
+				fileSystem.setState(value.toString());
+				FileSystemState.valueOf(value.toString());
+
+				if (isOriginalStateBatch && !fileSystem.isOnBatch()) {
+					fileSystem.setBatchCommitInfo(null);
+					firePostponedBatchEvents(fileSystem);
+					postCommitHook(fileSystem);
+				}
+				fileSystem.setHadCommitOnBatchState(false);
+			} finally {
+				fileSystem.unlock();
+			}
+			return;
+		}
+
+		final String[] s = split(attribute);
+		if (s[0].length() == 0) {
+			throw new IllegalArgumentException(attribute);
+		}
+		final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path), s[0], null, options);
+		if (view == null) {
+			throw new UnsupportedOperationException("View '" + s[0] + "' not available");
+		}
+
+		view.setAttribute(s[1], value);
+	}
+
+	private void lockAndSquash(final Path path, final SquashOption value) {
+		final JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
+		try {
+			fileSystem.lock();
+			final JGitPathImpl gSource = toPathImpl(path);
+			String commitMessage = checkNotEmpty("commitMessage", value.getMessage());
+			String startCommit = checkNotEmpty("startCommit", value.getRecord().id());
+			cast(gSource.getFileSystem()).getGit().squash(gSource.getRefTree(), startCommit, commitMessage);
+		} finally {
+			fileSystem.unlock();
+		}
+	}
+
+	private void checkURI(final String paramName, final URI uri) throws IllegalArgumentException {
+		checkNotNull("uri", uri);
+
+		if (uri.getAuthority() == null || uri.getAuthority().isEmpty()) {
+			throw new IllegalArgumentException(
+					"Parameter named '" + paramName + "' is invalid, missing host repository!");
+		}
+
+		int atIndex = uri.getPath().indexOf("@");
+		if (atIndex != 1 && !uri.getAuthority().contains("@")) {
+			if (uri.getPath().indexOf("/", atIndex) == 1) {
+				throw new IllegalArgumentException(
+						"Parameter named '" + paramName + "' is invalid, missing host repository!");
+			}
+		}
+	}
+
+	public String extractHostForPath(final URI uri) {
+		checkNotNull("uri", uri);
+
+		int atIndex = uri.getPath().indexOf("@");
+		if (atIndex != 1 && !uri.getAuthority().contains("@")) {
+			return uri.getAuthority() + uri.getPath().substring(0, uri.getPath().indexOf("/", atIndex));
+		}
+
+		return uri.getAuthority();
+	}
+
+	private boolean hasSyncFlag(final URI uri) {
+		checkNotNull("uri", uri);
+
+		return uri.getQuery() != null && uri.getQuery().contains("sync");
+	}
+
+	private boolean hasForceFlag(URI uri) {
+		checkNotNull("uri", uri);
+
+		return uri.getQuery() != null && uri.getQuery().contains("force");
+	}
+
+	private boolean hasPushFlag(final URI uri) {
+		checkNotNull("uri", uri);
+
+		return uri.getQuery() != null && uri.getQuery().contains("push");
+	}
+
+	// by spec, it should be a list of pairs, but here we're just using a map.
+	private static Map<String, String> getQueryParams(final URI uri) {
+		final String[] params = uri.getQuery().split("&");
+		return new HashMap<String, String>(params.length) {
+			{
+				for (String param : params) {
+					final String[] kv = param.split("=");
+					final String name = kv[0];
+					final String value;
+					if (kv.length == 2) {
+						value = kv[1];
+					} else {
+						value = "";
+					}
+
+					put(name, value);
+				}
+			}
+		};
+	}
+
+	private CredentialsProvider buildCredential(String username, String password) {
+		if (username != null) {
+			if (password != null) {
+				return new UsernamePasswordCredentialsProvider(username, password);
+			}
+			return new UsernamePasswordCredentialsProvider(username, "");
+		}
+		return CredentialsProvider.getDefault();
+	}
+
+	private JGitPathImpl toPathImpl(final Path path) {
+		if (path instanceof JGitPathImpl) {
+			return (JGitPathImpl) path;
+		}
+		throw new IllegalArgumentException("Path not supported by current provider.");
+	}
+
+	private String[] split(final String attribute) {
+		final String[] s = new String[2];
+		final int pos = attribute.indexOf(':');
+		if (pos == 1) {
+			s[0] = "basic";
+			s[1] = attribute;
+		} else {
+			s[0] = attribute.substring(0, pos);
+			s[1] = (pos == attribute.length()) ? "" : attribute.substring(pos + 1);
+		}
+		return s;
+	}
+
+	private int getSchemeSize(final URI uri) {
+		if (uri.getScheme().equals(SCHEME)) {
+			return SCHEME_SIZE;
+		}
+		return DEFAULT_SCHEME_SIZE;
+	}
+
+	private void delete(final JGitPathImpl path, final CommitInfo commitInfo) {
+		commit(path, commitInfo, new DefaultCommitContent(new HashMap<String, File>() {
+			{
+				put(path.getPath(), null);
+			}
+		}));
+	}
+
+	private void commit(final JGitPathImpl path, final CommitInfo commitInfo, final CommitContent commitContent) {
+
+		final JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
+		try {
+			fileSystem.lock();
+
+			final Git git = fileSystem.getGit();
+			final String branchName = path.getRefTree();
+			final boolean batchState = fileSystem.isOnBatch();
+			final boolean amend = batchState && fileSystem.isHadCommitOnBatchState(path.getRoot());
+			final ObjectId oldHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);
+
+			final boolean hasCommit;
+			if (batchState && fileSystem.getBatchCommitInfo() != null) {
+				hasCommit = git.commit(branchName, fileSystem.getBatchCommitInfo(), amend, null, commitContent);
+			} else {
+				hasCommit = git.commit(branchName, commitInfo, amend, null, commitContent);
+			}
+
+			if (!batchState) {
+				if (hasCommit) {
+					int value = fileSystem.incrementAndGetCommitCount();
+					if (value >= config.getCommitLimit()) {
+						git.gc();
+						fileSystem.resetCommitCount();
+					}
+				}
+
+				final ObjectId newHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);
+
+				postCommitHook(fileSystem);
+
+				notifyDiffs((JGitFileSystem) path.getFileSystem(), branchName, commitInfo.getSessionId(),
+						commitInfo.getName(), commitInfo.getMessage(), oldHead, newHead);
+			} else {
+				synchronized (postponedEventsLock) {
+
+					String sessionId;
+					String userName;
+					String message;
+					if (fileSystem.getBatchCommitInfo() != null) {
+						sessionId = fileSystem.getBatchCommitInfo().getSessionId();
+						userName = fileSystem.getBatchCommitInfo().getName();
+						message = fileSystem.getBatchCommitInfo().getMessage();
+					} else {
+						sessionId = commitInfo.getSessionId();
+						userName = commitInfo.getName();
+						message = commitInfo.getMessage();
+					}
+
+					final ObjectId newHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);
+					List<WatchEvent<?>> postponedWatchEvents = compareDiffs(cast(path.getFileSystem()), branchName,
+							sessionId, userName, message, oldHead, newHead);
+
+					fileSystem.addPostponedWatchEvents(postponedWatchEvents);
+				}
+			}
+
+			if (cast(path.getFileSystem()).isOnBatch() && !fileSystem.isHadCommitOnBatchState(path.getRoot())) {
+				fileSystem.setHadCommitOnBatchState(path.getRoot(), hasCommit);
+			}
+		} finally {
+			fileSystem.unlock();
+		}
+	}
+
+	private void postCommitHook(final JGitFileSystem fileSystem) {
+
+		ProcessResult result = detectedFS.runHookIfPresent(fileSystem.getGit().getRepository(), "postcommit",
+				new String[0]);
+
+		if (result.getStatus().equals(ProcessResult.Status.OK)) {
+			fileSystem.notifyPostCommit(result.getExitCode());
+		}
+	}
+
+	private void firePostponedBatchEvents(JGitFileSystem fileSystem) {
+		synchronized (postponedEventsLock) {
+
+			if (fileSystem.hasPostponedEvents()) {
+				fileSystem.publishEvents(fileSystem.getRootDirectories().iterator().next(),
+						fileSystem.getPostponedWatchEvents());
+			}
+
+			fileSystem.clearPostponedWatchEvents();
+
+			int value = fileSystem.incrementAndGetCommitCount();
+			if (value >= config.getCommitLimit()) {
+				fileSystem.getGit().gc();
+				fileSystem.resetCommitCount();
+			}
+		}
+	}
+
+	List<WatchEvent<?>> notifyDiffs(final JGitFileSystem fs, final String _tree, final String sessionId,
+			final String userName, final String message, final ObjectId oldHead, final ObjectId newHead) {
+
+		List<WatchEvent<?>> watchEvents = compareDiffs(fs, _tree, sessionId, userName, message, oldHead, newHead);
+
+		final String tree;
+		if (_tree.startsWith("refs/")) {
+			tree = _tree.substring(_tree.lastIndexOf("/") + 1);
+		} else {
+			tree = _tree;
+		}
+
+		final String host = tree + "@" + fs.getName();
+
+		final Path root = JGitPathImpl.createRoot(fs, "/", host, false);
+		if (!watchEvents.isEmpty()) {
+			fs.publishEvents(root, watchEvents);
+		}
+		return watchEvents;
+	}
+
+	List<WatchEvent<?>> compareDiffs(final JGitFileSystem fs, final String _tree, final String sessionId,
+			final String userName, final String message, final ObjectId oldHead, final ObjectId newHead) {
+
+		final String tree;
+		if (_tree.startsWith("refs/")) {
+			tree = _tree.substring(_tree.lastIndexOf("/") + 1);
+		} else {
+			tree = _tree;
+		}
+
+		final String host = tree + "@" + fs.getName();
+
+		final List<DiffEntry> diff = fs.getGit().listDiffs(oldHead, newHead);
+		final List<WatchEvent<?>> events = new ArrayList<>(diff.size());
+
+		for (final DiffEntry diffEntry : diff) {
+			final Path oldPath;
+			if (!diffEntry.getOldPath().equals(DiffEntry.DEV_NULL)) {
+				oldPath = JGitPathImpl.create(fs, "/" + diffEntry.getOldPath(), host, null, false);
+			} else {
+				oldPath = null;
+			}
+
+			final Path newPath;
+			if (!diffEntry.getNewPath().equals(DiffEntry.DEV_NULL)) {
+				final PathInfo pathInfo = fs.getGit().getPathInfo(tree, diffEntry.getNewPath());
+				newPath = JGitPathImpl.create(fs, "/" + pathInfo.getPath(), host, pathInfo.getObjectId(), false);
+			} else {
+				newPath = null;
+			}
+
+			WatchEvent e = new JGitWatchEvent(sessionId, userName, message, diffEntry.getChangeType().name(), oldPath,
+					newPath);
+			events.add(e);
+		}
+
+		return events;
+	}
+
+	GitSSHService getGitSSHService() {
+		return gitSSHService;
+	}
+
+	public JGitFileSystemProviderConfiguration getConfig() {
+		return config;
+	}
+
+	/**
+	 * implement Executor directly due to bugs in some older CDI implementations.
+	 */
+	private static class ExecutorWrapper implements Executor {
+
+		private final ExecutorService simpleAsyncExecutor;
+
+		public ExecutorWrapper(ExecutorService simpleAsyncExecutor) {
+			this.simpleAsyncExecutor = checkNotNull("simpleAsyncExecutor", simpleAsyncExecutor);
+		}
+
+		@Override
+		public void execute(Runnable command) {
+			simpleAsyncExecutor.execute(command);
+		}
+	}
+
+	public void setDetectedFS(final FS detectedFS) {
+		this.detectedFS = detectedFS;
+	}
+
+	public JGitFileSystemsManager getFsManager() {
+		return fsManager;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConfiguration.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConfiguration.java
new file mode 100644
index 0000000000..b33f31ef3a
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProviderConfiguration.java
@@ 0,0 +1,526 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.util.concurrent.TimeUnit;
+import java.util.stream.Stream;
+
+import org.eclipse.jgit.niofs.internal.config.ConfigProperties;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static java.util.stream.Collectors.joining;
+import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
+
+public class JGitFileSystemProviderConfiguration {
+
+	private static final Logger LOG = LoggerFactory.getLogger(JGitFileSystemProviderConfiguration.class);
+
+	public static final String GIT_ENV_KEY_DEFAULT_REMOTE_NAME = DEFAULT_REMOTE_NAME;
+	public static final String GIT_ENV_KEY_BRANCH_LIST = "branches";
+	public static final String GIT_ENV_KEY_SUBDIRECTORY = "subdirectory";
+
+	public static final String GIT_DAEMON_ENABLED = "nio.git.daemon.enabled";
+	public static final String GIT_SSH_ENABLED = "nio.git.ssh.enabled";
+	public static final String GIT_HTTP_ENABLED = "nio.git.http.enabled";
+	public static final String GIT_HTTPS_ENABLED = "nio.git.https.enabled";
+
+	public static final String GIT_NIO_DIR = "nio.git.dir";
+	public static final String GIT_NIO_DIR_NAME = "nio.git.dirname";
+	public static final String ENABLE_GIT_KETCH = "nio.git.ketch";
+	public static final String HOOK_DIR = "nio.git.hooks";
+
+	public static final String GIT_HTTP_HOST = "nio.git.http.host";
+	public static final String GIT_HTTP_HOSTNAME = "nio.git.http.hostname";
+	public static final String GIT_HTTP_PORT = "nio.git.http.port";
+	public static final String GIT_HTTPS_HOST = "nio.git.https.host";
+	public static final String GIT_HTTPS_HOSTNAME = "nio.git.https.hostname";
+	public static final String GIT_HTTPS_PORT = "nio.git.https.port";
+
+	public static final String GIT_DAEMON_HOST = "nio.git.daemon.host";
+	public static final String GIT_DAEMON_HOSTNAME = "nio.git.daemon.hostname";
+	public static final String GIT_DAEMON_PORT = "nio.git.daemon.port";
+	public static final String GIT_SSH_HOST = "nio.git.ssh.host";
+	public static final String GIT_SSH_HOSTNAME = "nio.git.ssh.hostname";
+	public static final String GIT_SSH_PORT = "nio.git.ssh.port";
+	public static final String GIT_SSH_CERT_DIR = "nio.git.ssh.cert.dir";
+	public static final String GIT_SSH_IDLE_TIMEOUT = "nio.git.ssh.idle.timeout";
+	public static final String GIT_SSH_ALGORITHM = "nio.git.ssh.algorithm";
+	public static final String GIT_SSH_PASSPHRASE = "nio.git.ssh.passphrase";
+	public static final String GIT_GC_LIMIT = "nio.git.gc.limit";
+	public static final String GIT_HTTP_SSL_VERIFY = "nio.git.http.sslVerify";
+	public static final String SSH_OVER_HTTP = "nio.git.proxy.ssh.over.http";
+	public static final String HTTP_PROXY_HOST = "http.proxyHost";
+	public static final String HTTP_PROXY_PORT = "http.proxyPort";
+	public static final String HTTP_PROXY_USER = "http.proxyUser";
+	public static final String HTTP_PROXY_PASSWORD = "http.proxyPassword";
+	public static final String SSH_OVER_HTTPS = "nio.git.proxy.ssh.over.https";
+	public static final String HTTPS_PROXY_HOST = "https.proxyHost";
+	public static final String HTTPS_PROXY_PORT = "https.proxyPort";
+	public static final String HTTPS_PROXY_USER = "https.proxyUser";
+	public static final String HTTPS_PROXY_PASSWORD = "https.proxyPassword";
+	public static final String USER_DIR = "user.dir";
+	public static final String JGIT_CACHE_INSTANCES = "nio.jgit.cache.instances";
+	public static final String JGIT_CACHE_OVERFLOW_CLEANUP_SIZE = "nio.jgit.cache.overflow.cleanup.size";
+	public static final String JGIT_REMOVE_ELDEST_ENTRY_ITERATIONS = "nio.jgit.remove.eldest.iterations";
+	public static final String JGIT_CACHE_EVICT_THRESHOLD_DURATION = "nio.jgit.cache.evict.threshold.duration";
+	public static final String JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT = "nio.jgit.cache.evict.threshold.time.unit";
+
+	public static final String GIT_ENV_KEY_DEST_PATH = "outdir";
+	public static final String GIT_ENV_KEY_USER_NAME = "username";
+	public static final String GIT_ENV_KEY_PASSWORD = "password";
+	public static final String GIT_ENV_KEY_INIT = "init";
+	public static final String GIT_ENV_KEY_MIRROR = "mirror";
+	public static final String SCHEME = "git";
+	public static final String GIT_SSH_CIPHERS = "nio.git.ssh.ciphers";
+	public static final String GIT_SSH_MACS = "nio.git.ssh.macs";
+	public static final int SCHEME_SIZE = (SCHEME + "://").length();
+	public static final int DEFAULT_SCHEME_SIZE = ("default://").length();
+	public static final String DEFAULT_HOST_NAME = "localhost";
+	public static final String REPOSITORIES_CONTAINER_DIR = ".niogit";
+	public static final String SSH_FILE_CERT_CONTAINER_DIR = ".security";
+	public static final String DEFAULT_SSH_OVER_HTTP = "false";
+	public static final String DEFAULT_SSH_OVER_HTTPS = "false";
+	public static final String DEFAULT_HOST_ADDR = "127.0.0.1";
+	public static final String DEFAULT_DAEMON_DEFAULT_ENABLED = "true";
+	public static final String DEFAULT_DAEMON_DEFAULT_PORT = "9418";
+	public static final String DEFAULT_SSH_ENABLED = "true";
+	public static final String DEFAULT_HTTP_ENABLED = "true";
+	public static final String DEFAULT_HTTPS_ENABLED = "false";
+	public static final String DEFAULT_HTTP_PORT = "8080";
+	public static final String DEFAULT_HTTPS_PORT = "8443";
+	public static final String DEFAULT_SSH_PORT = "8001";
+	public static final String DEFAULT_SSH_IDLE_TIMEOUT = "10000";
+	public static final String DEFAULT_SSH_ALGORITHM = "RSA";
+	public static final String DEFAULT_SSH_CERT_PASSPHRASE = "";
+	public static final String DEFAULT_COMMIT_LIMIT_TO_GC = "20";
+	public static final Boolean DEFAULT_GIT_HTTP_SSL_VERIFY = Boolean.TRUE;
+	public static final String DEFAULT_ENABLE_GIT_KETCH = "false";
+	public static final String DEFAULT_JGIT_FILE_SYSTEM_INSTANCES_CACHE = "10000";
+	public static final String DEFAULT_JGIT_REMOVE_ELDEST_ENTRY_ITERATIONS = "10";
+	public static final String DEFAULT_JGIT_CACHE_OVERFLOW_CLEANUP_SIZE = "10";
+	public static final String DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_DURATION = "5";
+	public static final TimeUnit DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT = TimeUnit.MINUTES;
+
+	private int commitLimit;
+	private boolean sslVerify;
+	private boolean daemonEnabled;
+	private int daemonPort;
+	private String daemonHostAddr;
+	private String daemonHostName;
+
+	private boolean sshEnabled;
+	private int sshPort;
+	private String sshHostAddr;
+	private String sshHostName;
+	private File sshFileCertDir;
+	private String sshAlgorithm;
+	private String sshPassphrase;
+	private String sshIdleTimeout;
+	private String gitSshCiphers;
+	private String gitSshMACs;
+
+	private boolean httpEnabled;
+	private int httpPort;
+	private String httpHostAddr;
+	private String httpHostName;
+	private boolean httpsEnabled;
+	private int httpsPort;
+	private String httpsHostAddr;
+	private String httpsHostName;
+
+	private File gitReposParentDir;
+
+	private File hookDir;
+
+	boolean enableKetch = false;
+	private boolean sshOverHttpProxy;
+	private String httpProxyHost;
+	private int httpProxyPort;
+	private String httpProxyUser;
+	private String httpProxyPassword;
+	private boolean sshOverHttpsProxy;
+	private String httpsProxyHost;
+	private int httpsProxyPort;
+	private String httpsProxyUser;
+	private String httpsProxyPassword;
+
+	// Number of instances of filesystem in cache
+	private int jgitFileSystemsInstancesCache;
+	// Number of instances that was removed by iteration in case of cache overflow
+	private int jgitCacheOverflowCleanupSize;
+	// Number of attempts to remove FS instances on cache
+	private int jgitRemoveEldestEntryIterations;
+	// Duration of Threshold of jgit file system instances evict
+	private long jgitCacheEvictThresholdDuration;
+	// TimeUnit of Threshold of jgit file system instances evict
+	private TimeUnit jgitCacheEvictThresholdTimeUnit;
+
+	public void load(ConfigProperties systemConfig) {
+		LOG.debug("Configuring from properties:");
+
+		final String currentDirectory = System.getProperty(USER_DIR);
+		final ConfigProperties.ConfigProperty enableKetchProp = systemConfig.get(ENABLE_GIT_KETCH,
+				DEFAULT_ENABLE_GIT_KETCH);
+		final ConfigProperties.ConfigProperty hookDirProp = systemConfig.get(HOOK_DIR, null);
+		final ConfigProperties.ConfigProperty bareReposDirProp = systemConfig.get(GIT_NIO_DIR, currentDirectory);
+		final ConfigProperties.ConfigProperty reposDirNameProp = systemConfig.get(GIT_NIO_DIR_NAME,
+				REPOSITORIES_CONTAINER_DIR);
+		final ConfigProperties.ConfigProperty enabledProp = systemConfig.get(GIT_DAEMON_ENABLED,
+				DEFAULT_DAEMON_DEFAULT_ENABLED);
+		final ConfigProperties.ConfigProperty hostProp = systemConfig.get(GIT_DAEMON_HOST, DEFAULT_HOST_ADDR);
+		final ConfigProperties.ConfigProperty hostNameProp = systemConfig.get(GIT_DAEMON_HOSTNAME,
+				hostProp.isDefault() ? DEFAULT_HOST_NAME : hostProp.getValue());
+		final ConfigProperties.ConfigProperty portProp = systemConfig.get(GIT_DAEMON_PORT, DEFAULT_DAEMON_DEFAULT_PORT);
+
+		final ConfigProperties.ConfigProperty httpEnabledProp = systemConfig.get(GIT_HTTP_ENABLED,
+				DEFAULT_HTTP_ENABLED);
+		final ConfigProperties.ConfigProperty httpHostProp = systemConfig.get(GIT_HTTP_HOST, DEFAULT_HOST_ADDR);
+		final ConfigProperties.ConfigProperty httpHostNameProp = systemConfig.get(GIT_HTTP_HOSTNAME,
+				httpHostProp.isDefault() ? DEFAULT_HOST_NAME : httpHostProp.getValue());
+		final ConfigProperties.ConfigProperty httpPortProp = systemConfig.get(GIT_HTTP_PORT, DEFAULT_HTTP_PORT);
+		final ConfigProperties.ConfigProperty httpsEnabledProp = systemConfig.get(GIT_HTTPS_ENABLED,
+				DEFAULT_HTTPS_ENABLED);
+		final ConfigProperties.ConfigProperty httpsHostProp = systemConfig.get(GIT_HTTPS_HOST, DEFAULT_HOST_ADDR);
+		final ConfigProperties.ConfigProperty httpsHostNameProp = systemConfig.get(GIT_HTTPS_HOSTNAME,
+				httpHostProp.isDefault() ? DEFAULT_HOST_NAME : httpHostProp.getValue());
+		final ConfigProperties.ConfigProperty httpsPortProp = systemConfig.get(GIT_HTTPS_PORT, DEFAULT_HTTPS_PORT);
+
+		final ConfigProperties.ConfigProperty sshEnabledProp = systemConfig.get(GIT_SSH_ENABLED, DEFAULT_SSH_ENABLED);
+		final ConfigProperties.ConfigProperty sshHostProp = systemConfig.get(GIT_SSH_HOST, DEFAULT_HOST_ADDR);
+		final ConfigProperties.ConfigProperty sshHostNameProp = systemConfig.get(GIT_SSH_HOSTNAME,
+				sshHostProp.isDefault() ? DEFAULT_HOST_NAME : sshHostProp.getValue());
+		final ConfigProperties.ConfigProperty sshPortProp = systemConfig.get(GIT_SSH_PORT, DEFAULT_SSH_PORT);
+		final ConfigProperties.ConfigProperty sshCertDirProp = systemConfig.get(GIT_SSH_CERT_DIR, currentDirectory);
+		final ConfigProperties.ConfigProperty sshIdleTimeoutProp = systemConfig.get(GIT_SSH_IDLE_TIMEOUT,
+				DEFAULT_SSH_IDLE_TIMEOUT);
+		final ConfigProperties.ConfigProperty sshAlgorithmProp = systemConfig.get(GIT_SSH_ALGORITHM,
+				DEFAULT_SSH_ALGORITHM);
+		final ConfigProperties.ConfigProperty sshPassphraseProp = systemConfig.get(GIT_SSH_PASSPHRASE,
+				DEFAULT_SSH_CERT_PASSPHRASE);
+		final ConfigProperties.ConfigProperty commitLimitProp = systemConfig.get(GIT_GC_LIMIT,
+				DEFAULT_COMMIT_LIMIT_TO_GC);
+		final ConfigProperties.ConfigProperty sslVerifyProp = systemConfig.get(GIT_HTTP_SSL_VERIFY,
+				DEFAULT_GIT_HTTP_SSL_VERIFY.toString());
+		final ConfigProperties.ConfigProperty sshOverHttpProxyProp = systemConfig.get(SSH_OVER_HTTP,
+				DEFAULT_SSH_OVER_HTTP);
+		final ConfigProperties.ConfigProperty httpProxyHostProp = systemConfig.get(HTTP_PROXY_HOST, null);
+		final ConfigProperties.ConfigProperty httpProxyPortProp = systemConfig.get(HTTP_PROXY_PORT, null);
+		final ConfigProperties.ConfigProperty httpProxyUserProp = systemConfig.get(HTTP_PROXY_USER, null);
+		final ConfigProperties.ConfigProperty httpProxyPasswordProp = systemConfig.get(HTTP_PROXY_PASSWORD, null);
+		final ConfigProperties.ConfigProperty sshOverHttpsProxyProp = systemConfig.get(SSH_OVER_HTTPS,
+				DEFAULT_SSH_OVER_HTTPS);
+		final ConfigProperties.ConfigProperty httpsProxyHostProp = systemConfig.get(HTTPS_PROXY_HOST, null);
+		final ConfigProperties.ConfigProperty httpsProxyPortProp = systemConfig.get(HTTPS_PROXY_PORT, null);
+		final ConfigProperties.ConfigProperty httpsProxyUserProp = systemConfig.get(HTTPS_PROXY_USER, null);
+		final ConfigProperties.ConfigProperty httpsProxyPasswordProp = systemConfig.get(HTTPS_PROXY_PASSWORD, null);
+
+		final ConfigProperties.ConfigProperty jgitFileSystemsInstancesCacheProp = systemConfig.get(JGIT_CACHE_INSTANCES,
+				DEFAULT_JGIT_FILE_SYSTEM_INSTANCES_CACHE);
+
+		final ConfigProperties.ConfigProperty jgitFileSystemsCacheOverflowSizePropCacheProp = systemConfig
+				.get(JGIT_CACHE_OVERFLOW_CLEANUP_SIZE, DEFAULT_JGIT_CACHE_OVERFLOW_CLEANUP_SIZE);
+
+		final ConfigProperties.ConfigProperty jgitRemoveEldestEntryIterationsProp = systemConfig
+				.get(JGIT_REMOVE_ELDEST_ENTRY_ITERATIONS, DEFAULT_JGIT_REMOVE_ELDEST_ENTRY_ITERATIONS);
+
+		final ConfigProperties.ConfigProperty jgitCacheEvictThresoldDurationProp = systemConfig
+				.get(JGIT_CACHE_EVICT_THRESHOLD_DURATION, DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_DURATION);
+
+		final ConfigProperties.ConfigProperty jgitCacheEvictThresoldTimeUnitProp = systemConfig
+				.get(JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT, DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT.name());
+
+		final ConfigProperties.ConfigProperty jgitSshCiphers = systemConfig.get(GIT_SSH_CIPHERS, null);
+		final ConfigProperties.ConfigProperty jgitSshMacs = systemConfig.get(GIT_SSH_MACS, null);
+
+		gitSshCiphers = jgitSshCiphers.getValue();
+		gitSshMACs = jgitSshMacs.getValue();
+
+		sshOverHttpProxy = sshOverHttpProxyProp.getBooleanValue();
+		if (sshOverHttpProxy) {
+			httpProxyHost = httpProxyHostProp.getValue();
+			httpProxyPort = httpProxyPortProp.getIntValue();
+		}
+		httpProxyUser = httpProxyUserProp.getValue();
+		httpProxyPassword = httpProxyPasswordProp.getValue();
+		sshOverHttpsProxy = sshOverHttpsProxyProp.getBooleanValue();
+		if (sshOverHttpsProxy) {
+			httpsProxyHost = httpsProxyHostProp.getValue();
+			httpsProxyPort = httpsProxyPortProp.getIntValue();
+		}
+		httpsProxyUser = httpsProxyUserProp.getValue();
+		httpsProxyPassword = httpsProxyPasswordProp.getValue();
+
+		if (LOG.isDebugEnabled()) {
+			LOG.debug(systemConfig.getConfigurationSummary("Summary of JGit configuration:"));
+		}
+
+		if (enableKetchProp != null && enableKetchProp.getValue() != null) {
+			enableKetch = enableKetchProp.getBooleanValue();
+		}
+
+		if (hookDirProp != null && hookDirProp.getValue() != null) {
+			hookDir = new File(hookDirProp.getValue());
+			if (!hookDir.exists()) {
+				hookDir = null;
+			}
+		}
+
+		gitReposParentDir = new File(bareReposDirProp.getValue(), reposDirNameProp.getValue());
+		commitLimit = commitLimitProp.getIntValue();
+		sslVerify = sslVerifyProp.getBooleanValue();
+
+		jgitFileSystemsInstancesCache = jgitFileSystemsInstancesCacheProp.getIntValue();
+
+		if (jgitFileSystemsInstancesCache < 1) {
+			jgitFileSystemsInstancesCache = Integer.valueOf(DEFAULT_JGIT_FILE_SYSTEM_INSTANCES_CACHE);
+		}
+
+		jgitCacheOverflowCleanupSize = jgitFileSystemsCacheOverflowSizePropCacheProp.getIntValue();
+
+		if (jgitCacheOverflowCleanupSize < 1) {
+			jgitCacheOverflowCleanupSize = Integer.valueOf(DEFAULT_JGIT_CACHE_OVERFLOW_CLEANUP_SIZE);
+		}
+
+		jgitRemoveEldestEntryIterations = jgitRemoveEldestEntryIterationsProp.getIntValue();
+		if (jgitRemoveEldestEntryIterations < 1) {
+			jgitRemoveEldestEntryIterations = Integer.valueOf(DEFAULT_JGIT_REMOVE_ELDEST_ENTRY_ITERATIONS);
+		}
+
+		jgitCacheEvictThresholdDuration = Long.valueOf(jgitCacheEvictThresoldDurationProp.getValue());
+		if (jgitCacheEvictThresholdDuration < 1) {
+			jgitCacheEvictThresholdDuration = Integer.valueOf(DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_DURATION);
+		}
+
+		try {
+			jgitCacheEvictThresholdTimeUnit = TimeUnit.valueOf(jgitCacheEvictThresoldTimeUnitProp.getValue());
+		} catch (IllegalArgumentException e) {
+			String validValues = Stream.of(TimeUnit.values()).map(Enum::toString).collect(joining(","));
+			LOG.warn("Failed to parse TimeUnit from {}={}. Valid values are {}. Using default instead: {}",
+					JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT, jgitCacheEvictThresholdTimeUnit, validValues,
+					DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT);
+			jgitCacheEvictThresholdTimeUnit = DEFAULT_JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT;
+		}
+
+		daemonEnabled = enabledProp.getBooleanValue();
+		if (daemonEnabled) {
+			daemonPort = portProp.getIntValue();
+			daemonHostAddr = hostProp.getValue();
+			daemonHostName = hostNameProp.getValue();
+		}
+
+		sshEnabled = sshEnabledProp.getBooleanValue();
+		if (sshEnabled) {
+			sshPort = sshPortProp.getIntValue();
+			sshHostAddr = sshHostProp.getValue();
+			sshHostName = sshHostNameProp.getValue();
+			sshFileCertDir = new File(sshCertDirProp.getValue(), SSH_FILE_CERT_CONTAINER_DIR);
+			sshAlgorithm = sshAlgorithmProp.getValue();
+			sshIdleTimeout = sshIdleTimeoutProp.getValue();
+			try {
+				Integer.valueOf(sshIdleTimeout);
+			} catch (final NumberFormatException exception) {
+				LOG.error(
+						"SSH Idle Timeout value is not a valid integer  Parameter is ignored, now using default value.");
+				sshIdleTimeout = DEFAULT_SSH_IDLE_TIMEOUT;
+			}
+		}
+		sshPassphrase = sshPassphraseProp.getValue();
+
+		httpEnabled = httpEnabledProp.getBooleanValue();
+		if (httpEnabled) {
+			httpPort = httpPortProp.getIntValue();
+			httpHostAddr = httpHostProp.getValue();
+			httpHostName = httpHostNameProp.getValue();
+		}
+
+		httpsEnabled = httpsEnabledProp.getBooleanValue();
+		if (httpsEnabled) {
+			httpsPort = httpsPortProp.getIntValue();
+			httpsHostAddr = httpsHostProp.getValue();
+			httpsHostName = httpsHostNameProp.getValue();
+		}
+	}
+
+	public boolean httpProxyIsDefined() {
+		return (httpProxyUser != null && httpProxyPassword != null)
+				|| (httpsProxyUser != null && httpsProxyPassword != null);
+	}
+
+	public int getCommitLimit() {
+		return commitLimit;
+	}
+
+	public boolean isSslVerify() {
+		return sslVerify;
+	}
+
+	public boolean isDaemonEnabled() {
+		return daemonEnabled;
+	}
+
+	public int getDaemonPort() {
+		return daemonPort;
+	}
+
+	public String getDaemonHostAddr() {
+		return daemonHostAddr;
+	}
+
+	public String getDaemonHostName() {
+		return daemonHostName;
+	}
+
+	public boolean isSshEnabled() {
+		return sshEnabled;
+	}
+
+	public int getSshPort() {
+		return sshPort;
+	}
+
+	public String getSshHostAddr() {
+		return sshHostAddr;
+	}
+
+	public String getSshHostName() {
+		return sshHostName;
+	}
+
+	public File getSshFileCertDir() {
+		return sshFileCertDir;
+	}
+
+	public String getSshAlgorithm() {
+		return sshAlgorithm;
+	}
+
+	public String getSshPassphrase() {
+		return sshPassphrase;
+	}
+
+	public String getSshIdleTimeout() {
+		return sshIdleTimeout;
+	}
+
+	public File getGitReposParentDir() {
+		return gitReposParentDir;
+	}
+
+	public File getHookDir() {
+		return hookDir;
+	}
+
+	public boolean isEnableKetch() {
+		return enableKetch;
+	}
+
+	public boolean isSshOverHttpProxy() {
+		return sshOverHttpProxy;
+	}
+
+	public String getHttpProxyHost() {
+		return httpProxyHost;
+	}
+
+	public int getHttpProxyPort() {
+		return httpProxyPort;
+	}
+
+	public String getHttpProxyUser() {
+		return httpProxyUser;
+	}
+
+	public String getHttpProxyPassword() {
+		return httpProxyPassword;
+	}
+
+	public boolean isSshOverHttpsProxy() {
+		return sshOverHttpsProxy;
+	}
+
+	public String getHttpsProxyHost() {
+		return httpsProxyHost;
+	}
+
+	public int getHttpsProxyPort() {
+		return httpsProxyPort;
+	}
+
+	public String getHttpsProxyUser() {
+		return httpsProxyUser;
+	}
+
+	public String getHttpsProxyPassword() {
+		return httpsProxyPassword;
+	}
+
+	public boolean isHttpEnabled() {
+		return httpEnabled;
+	}
+
+	public int getHttpPort() {
+		return httpPort;
+	}
+
+	public String getHttpHostAddr() {
+		return httpHostAddr;
+	}
+
+	public String getHttpHostName() {
+		return httpHostName;
+	}
+
+	public boolean isHttpsEnabled() {
+		return httpsEnabled;
+	}
+
+	public int getHttpsPort() {
+		return httpsPort;
+	}
+
+	public String getHttpsHostAddr() {
+		return httpsHostAddr;
+	}
+
+	public String getHttpsHostName() {
+		return httpsHostName;
+	}
+
+	public int getJgitFileSystemsInstancesCache() {
+		return jgitFileSystemsInstancesCache;
+	}
+
+	public int getJgitCacheOverflowCleanupSize() {
+		return jgitCacheOverflowCleanupSize;
+	}
+
+	public int getJgitRemoveEldestEntryIterations() {
+		return jgitRemoveEldestEntryIterations;
+	}
+
+	public TimeUnit getDefaultJgitCacheEvictThresholdTimeUnit() {
+		return jgitCacheEvictThresholdTimeUnit;
+	}
+
+	public long getJgitCacheEvictThresholdDuration() {
+		return jgitCacheEvictThresholdDuration;
+	}
+
+	public String getGitSshCiphers() {
+		return gitSshCiphers;
+	}
+
+	public String getGitSshMACs() {
+		return gitSshMACs;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProxy.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProxy.java
new file mode 100644
index 0000000000..0ff80718b7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemProxy.java
@@ 0,0 +1,280 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.FileStore;
+import java.nio.file.InvalidPathException;
+import java.nio.file.Path;
+import java.nio.file.PathMatcher;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchService;
+import java.nio.file.attribute.UserPrincipalLookupService;
+import java.nio.file.spi.FileSystemProvider;
+import java.util.List;
+import java.util.Set;
+import java.util.function.Supplier;
+import java.util.regex.PatternSyntaxException;
+
+import org.eclipse.jgit.niofs.fs.FileSystemState;
+import org.eclipse.jgit.niofs.fs.options.CommentedOption;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.ReceiveCommand;
+import org.eclipse.jgit.transport.UploadPack;
+
+public class JGitFileSystemProxy extends JGitFileSystem {
+
+	private String fsName;
+	private Supplier<JGitFileSystem> cachedSupplier;
+
+	public JGitFileSystemProxy(String fsName, Supplier<JGitFileSystem> cachedSupplier) {
+		this.fsName = fsName;
+
+		this.cachedSupplier = cachedSupplier;
+	}
+
+	@Override
+	public String getName() {
+		return fsName;
+	}
+
+	@Override
+	public Git getGit() {
+		return cachedSupplier.get().getGit();
+	}
+
+	@Override
+	public CredentialsProvider getCredential() {
+		return cachedSupplier.get().getCredential();
+	}
+
+	@Override
+	public void checkClosed() throws IllegalStateException {
+		cachedSupplier.get().checkClosed();
+	}
+
+	@Override
+	public void publishEvents(Path watchable, List<WatchEvent<?>> elist) {
+		cachedSupplier.get().publishEvents(watchable, elist);
+	}
+
+	@Override
+	public boolean isOnBatch() {
+		return cachedSupplier.get().isOnBatch();
+	}
+
+	@Override
+	public void setState(String state) {
+		cachedSupplier.get().setState(state);
+	}
+
+	@Override
+	public CommitInfo buildCommitInfo(String defaultMessage, CommentedOption op) {
+		return cachedSupplier.get().buildCommitInfo(defaultMessage, op);
+	}
+
+	@Override
+	public void setBatchCommitInfo(String defaultMessage, CommentedOption op) {
+		cachedSupplier.get().setBatchCommitInfo(defaultMessage, op);
+	}
+
+	@Override
+	public void setHadCommitOnBatchState(Path path, boolean hadCommitOnBatchState) {
+		cachedSupplier.get().setHadCommitOnBatchState(path, hadCommitOnBatchState);
+	}
+
+	@Override
+	public void setHadCommitOnBatchState(boolean value) {
+		cachedSupplier.get().setHadCommitOnBatchState(value);
+	}
+
+	@Override
+	public boolean isHadCommitOnBatchState(Path path) {
+		return cachedSupplier.get().isHadCommitOnBatchState(path);
+	}
+
+	@Override
+	public void setBatchCommitInfo(CommitInfo batchCommitInfo) {
+		cachedSupplier.get().setBatchCommitInfo(batchCommitInfo);
+	}
+
+	@Override
+	public CommitInfo getBatchCommitInfo() {
+		return cachedSupplier.get().getBatchCommitInfo();
+	}
+
+	@Override
+	public int incrementAndGetCommitCount() {
+		return cachedSupplier.get().incrementAndGetCommitCount();
+	}
+
+	@Override
+	public void resetCommitCount() {
+		cachedSupplier.get().resetCommitCount();
+	}
+
+	@Override
+	public int getNumberOfCommitsSinceLastGC() {
+		return cachedSupplier.get().getNumberOfCommitsSinceLastGC();
+	}
+
+	@Override
+	public void lock() {
+		cachedSupplier.get().lock();
+	}
+
+	@Override
+	public void unlock() {
+		cachedSupplier.get().unlock();
+	}
+
+	@Override
+	public void addPostponedWatchEvents(List<WatchEvent<?>> postponedWatchEvents) {
+		cachedSupplier.get().addPostponedWatchEvents(postponedWatchEvents);
+	}
+
+	@Override
+	public List<WatchEvent<?>> getPostponedWatchEvents() {
+		return cachedSupplier.get().getPostponedWatchEvents();
+	}
+
+	@Override
+	public void clearPostponedWatchEvents() {
+		cachedSupplier.get().clearPostponedWatchEvents();
+	}
+
+	@Override
+	public boolean hasPostponedEvents() {
+		return cachedSupplier.get().hasPostponedEvents();
+	}
+
+	@Override
+	public boolean hasBeenInUse() {
+		return cachedSupplier.get().hasBeenInUse();
+	}
+
+	@Override
+	public void notifyExternalUpdate() {
+		cachedSupplier.get().notifyExternalUpdate();
+	}
+
+	@Override
+	public void notifyPostCommit(int exitCode) {
+		cachedSupplier.get().notifyPostCommit(exitCode);
+	}
+
+	@Override
+	public void checkBranchAccess(final ReceiveCommand command, final User user) {
+		cachedSupplier.get().checkBranchAccess(command, user);
+	}
+
+	@Override
+	public void filterBranchAccess(final UploadPack uploadPack, final User user) {
+		cachedSupplier.get().filterBranchAccess(uploadPack, user);
+	}
+
+	@Override
+	public FileSystemProvider provider() {
+		return cachedSupplier.get().provider();
+	}
+
+	@Override
+	public boolean isOpen() {
+		return cachedSupplier.get().isOpen();
+	}
+
+	@Override
+	public boolean isReadOnly() {
+		return false;
+	}
+
+	@Override
+	public String getSeparator() {
+		return "/";
+	}
+
+	@Override
+	public Iterable<Path> getRootDirectories() {
+		return cachedSupplier.get().getRootDirectories();
+	}
+
+	@Override
+	public Iterable<FileStore> getFileStores() {
+		return cachedSupplier.get().getFileStores();
+	}
+
+	@Override
+	public Set<String> supportedFileAttributeViews() {
+		return cachedSupplier.get().supportedFileAttributeViews();
+	}
+
+	@Override
+	public Path getPath(String first, String... more) throws InvalidPathException {
+		return cachedSupplier.get().getPath(first, more);
+	}
+
+	@Override
+	public PathMatcher getPathMatcher(String syntaxAndPattern)
+			throws IllegalArgumentException, PatternSyntaxException, UnsupportedOperationException {
+		return cachedSupplier.get().getPathMatcher(syntaxAndPattern);
+	}
+
+	@Override
+	public UserPrincipalLookupService getUserPrincipalLookupService() throws UnsupportedOperationException {
+		return cachedSupplier.get().getUserPrincipalLookupService();
+	}
+
+	@Override
+	public WatchService newWatchService() throws UnsupportedOperationException, IOException {
+		return cachedSupplier.get().newWatchService();
+	}
+
+	@Override
+	public void close() throws IOException {
+		cachedSupplier.get().close();
+	}
+
+	@Override
+	public void dispose() throws IOException {
+		cachedSupplier.get().dispose();
+	}
+
+	@Override
+	public String id() {
+		return fsName;
+	}
+
+	@Override
+	public FileSystemState getState() {
+		return cachedSupplier.get().getState();
+	}
+
+	public JGitFileSystem getRealJGitFileSystem() {
+		return cachedSupplier.get();
+	}
+
+	@Override
+	public boolean equals(Object obj) {
+		return cachedSupplier.get().equals(obj);
+	}
+
+	@Override
+	public int hashCode() {
+		return cachedSupplier.get().hashCode();
+	}
+
+	@Override
+	public String toString() {
+		return cachedSupplier.get().toString();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemWatchServices.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemWatchServices.java
new file mode 100644
index 0000000000..390c4f8cc3
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemWatchServices.java
@@ 0,0 +1,78 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.Serializable;
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchKey;
+import java.nio.file.WatchService;
+import java.nio.file.Watchable;
+import java.util.Collection;
+import java.util.List;
+import java.util.concurrent.CopyOnWriteArrayList;
+
+public class JGitFileSystemWatchServices implements Serializable {
+
+	private final Collection<JGitWatchService> watchServices = new CopyOnWriteArrayList<>();
+
+	public JGitFileSystemWatchServices() {
+	}
+
+	public WatchService newWatchService(String fsName) {
+		final JGitWatchService ws = new JGitWatchService(fsName, p > watchServices.remove(p));
+		watchServices.add(ws);
+		return ws;
+	}
+
+	public synchronized void publishEvents(Path watchable, List<WatchEvent<?>> elist) {
+		if (watchServices.isEmpty()) {
+			return;
+		}
+
+		for (JGitWatchService ws : watchServices) {
+			ws.publish(new WatchKey() {
+
+				@Override
+				public boolean isValid() {
+					return true;
+				}
+
+				@Override
+				public List<WatchEvent<?>> pollEvents() {
+					return new CopyOnWriteArrayList<>(elist);
+				}
+
+				@Override
+				public boolean reset() {
+					return !watchServices.isEmpty();
+				}
+
+				@Override
+				public void cancel() {
+				}
+
+				@Override
+				public Watchable watchable() {
+					return watchable;
+				}
+			});
+			synchronized (ws) {
+				ws.notifyAll();
+			}
+		}
+	}
+
+	public void close() {
+		watchServices.forEach(ws > ws.closeWithoutNotifyParent());
+		watchServices.clear();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManager.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManager.java
new file mode 100644
index 0000000000..fbed3fce07
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitFileSystemsEventsManager.java
@@ 0,0 +1,145 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.io.Serializable;
+import java.nio.file.Path;
+import java.nio.file.WatchEvent;
+import java.nio.file.WatchService;
+import java.util.List;
+import java.util.Map;
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.function.Consumer;
+
+import org.eclipse.jgit.niofs.cluster.ClusterMessageService;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class JGitFileSystemsEventsManager {
+
+	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemsEventsManager.class);
+
+	private final Map<String, JGitFileSystemWatchServices> fsWatchServices = new ConcurrentHashMap<>();
+
+	private final ClusterMessageService clusterMessageService;
+
+	JGitEventsBroadcast jGitEventsBroadcast;
+
+	public JGitFileSystemsEventsManager() {
+		clusterMessageService = getClusterMessageService();
+
+		if (clusterMessageService.isSystemClustered()) {
+			setupJGitEventsBroadcast();
+		}
+	}
+
+	ClusterMessageService getClusterMessageService() {
+		return new ClusterMessageService() {
+			@Override
+			public void connect() {
+
+			}
+
+			@Override
+			public <T> void createConsumer(DestinationType type, String channel, Class<T> clazz, Consumer<T> listener) {
+
+			}
+
+			@Override
+			public void broadcast(DestinationType type, String channel, Serializable object) {
+
+			}
+
+			@Override
+			public boolean isSystemClustered() {
+				return false;
+			}
+
+			@Override
+			public void close() {
+
+			}
+		};
+	}
+
+	void setupJGitEventsBroadcast() {
+		jGitEventsBroadcast = new JGitEventsBroadcast(clusterMessageService,
+				w > publishEvents(w.getFsName(), w.getWatchable(), w.getEvents(), false));
+	}
+
+	public WatchService newWatchService(String fsName) throws UnsupportedOperationException, IOException {
+		fsWatchServices.putIfAbsent(fsName, createFSWatchServicesManager());
+
+		if (jGitEventsBroadcast != null) {
+			jGitEventsBroadcast.createWatchService(fsName);
+		}
+
+		return fsWatchServices.get(fsName).newWatchService(fsName);
+	}
+
+	JGitFileSystemWatchServices createFSWatchServicesManager() {
+		return new JGitFileSystemWatchServices();
+	}
+
+	public void publishEvents(String fsName, Path watchable, List<WatchEvent<?>> elist) {
+
+		publishEvents(fsName, watchable, elist, true);
+	}
+
+	public void publishEvents(String fsName, Path watchable, List<WatchEvent<?>> elist, boolean broadcastEvents) {
+
+		JGitFileSystemWatchServices watchService = fsWatchServices.get(fsName);
+
+		if (watchService == null) {
+			return;
+		}
+
+		watchService.publishEvents(watchable, elist);
+
+		if (shouldIBroadcast(broadcastEvents)) {
+			jGitEventsBroadcast.broadcast(fsName, watchable, elist);
+		}
+	}
+
+	private boolean shouldIBroadcast(boolean broadcastEvents) {
+		return broadcastEvents && jGitEventsBroadcast != null;
+	}
+
+	public void close(String name) {
+
+		JGitFileSystemWatchServices watchService = fsWatchServices.get(name);
+
+		if (watchService != null) {
+			try {
+				watchService.close();
+			} catch (final Exception ex) {
+				LOGGER.error("Can't close watch service [" + toString() + "]", ex);
+			}
+		}
+	}
+
+	public void shutdown() {
+		fsWatchServices.keySet().forEach(key > this.close(key));
+
+		if (jGitEventsBroadcast != null) {
+			jGitEventsBroadcast.close();
+		}
+	}
+
+	JGitEventsBroadcast getjGitEventsBroadcast() {
+		return jGitEventsBroadcast;
+	}
+
+	Map<String, JGitFileSystemWatchServices> getFsWatchServices() {
+		return fsWatchServices;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitHiddenAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitHiddenAttributeViewImpl.java
new file mode 100644
index 0000000000..c956c5c4c1
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitHiddenAttributeViewImpl.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.attribute.BasicFileAttributeView;
+
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
+import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
+
+/**
+ * This is the JGit implementation of the {@link HiddenAttributeViewImpl}. It
+ * builds the HiddenAttributes object with "isHidden" attribute information.
+ * That attribute lets you know if the branch you are querying is a hidden
+ * branch or not. Hidden branches should not be used, are just a mechanism to
+ * merge.
+ */
+public class JGitHiddenAttributeViewImpl extends HiddenAttributeViewImpl<JGitPathImpl> {
+
+	private HiddenAttributes attrs = null;
+
+	public JGitHiddenAttributeViewImpl(final JGitPathImpl path) {
+		super(path);
+	}
+
+	@Override
+	public HiddenAttributes readAttributes() throws IOException {
+		if (attrs == null) {
+			attrs = buildAttrs(path.getFileSystem(), path.getRefTree(), path.getPath());
+		}
+		return attrs;
+	}
+
+	@Override
+	public Class<? extends BasicFileAttributeView>[] viewTypes() {
+		return new Class[] { HiddenAttributeView.class, HiddenAttributeViewImpl.class,
+				JGitVersionAttributeViewImpl.class };
+	}
+
+	private HiddenAttributes buildAttrs(final JGitFileSystem fileSystem, final String refTree, final String path)
+			throws IOException {
+		return new HiddenAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes(),
+				HiddenBranchRefFilter.isHidden(refTree));
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitPathImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitPathImpl.java
new file mode 100644
index 0000000000..d295177d9e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitPathImpl.java
@@ 0,0 +1,167 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.File;
+import java.io.FileOutputStream;
+import java.io.IOException;
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.nio.file.Path;
+import java.nio.file.attribute.BasicFileAttributes;
+
+import org.eclipse.jgit.lib.ObjectId;
+
+import static org.eclipse.jgit.lib.Constants.MASTER;
+
+public class JGitPathImpl extends AbstractPath<JGitFileSystem> implements SegmentedPath {
+
+	private static final int BUFFER_SIZE = 8192;
+	public final static String DEFAULT_REF_TREE = MASTER;
+
+	private final ObjectId objectId;
+
+	private JGitPathImpl(final JGitFileSystem fs, final String path, final String host, final ObjectId id,
+			final boolean isRoot, final boolean isRealPath, final boolean isNormalized) {
+		super(fs, path, host, isRoot, isRealPath, isNormalized);
+		this.objectId = id;
+	}
+
+	@Override
+	protected RootInfo setupRoot(final JGitFileSystem fs, final String pathx, final String host, final boolean isRoot) {
+		final boolean isRooted = isRoot ? true : pathx.startsWith("/");
+
+		final boolean isAbsolute;
+		if (isRooted) {
+			isAbsolute = true;
+		} else {
+			isAbsolute = false;
+		}
+
+		int lastOffset = isAbsolute ? 1 : 0;
+
+		final boolean isFinalRoot;
+		if (pathx.length() == 1 && lastOffset == 1) {
+			isFinalRoot = true;
+		} else {
+			isFinalRoot = isRoot;
+		}
+
+		return new RootInfo(lastOffset, isAbsolute, isFinalRoot, pathx.getBytes());
+	}
+
+	@Override
+	protected String defaultDirectory() {
+		return "/:";
+	}
+
+	@Override
+	protected Path newRoot(final JGitFileSystem fs, final String substring, final String host, final boolean realPath) {
+		return new JGitPathImpl(fs, substring, host, null, true, realPath, true);
+	}
+
+	@Override
+	protected Path newPath(final JGitFileSystem fs, final String substring, final String host, final boolean isRealPath,
+			final boolean isNormalized) {
+		return new JGitPathImpl(fs, substring, host, null, false, isRealPath, isNormalized);
+	}
+
+	public static JGitPathImpl create(final JGitFileSystem fs, final String path, final String host, final ObjectId id,
+			final boolean isRealPath) {
+		return new JGitPathImpl(fs, setupPath(path), setupHost(host), id, false, isRealPath, false);
+	}
+
+	public static JGitPathImpl create(final JGitFileSystem fs, final String path, final String host,
+			final boolean isRealPath) {
+		return new JGitPathImpl(fs, setupPath(path), setupHost(host), null, false, isRealPath, false);
+	}
+
+	public static JGitPathImpl createRoot(final JGitFileSystem fs, final String path, final String host,
+			final boolean isRealPath) {
+		return new JGitPathImpl(fs, setupPath(path), setupHost(host), null, true, isRealPath, true);
+	}
+
+	public static JGitPathImpl createFSDirect(final JGitFileSystem fs) {
+		return new JGitPathImpl(fs, null, null, null, true, true, true);
+	}
+
+	@Override
+	public File toFile() {
+		if (file == null) {
+			synchronized (this) {
+				if (isRegularFile()) {
+					try {
+						file = File.createTempFile("git", "temp");
+						try (final InputStream in = getFileSystem().provider().newInputStream(this);
+								final OutputStream out = new FileOutputStream(file)) {
+							internalCopy(in, out);
+						}
+					} catch (final Exception ex) {
+						file = null;
+					}
+				} else {
+					throw new UnsupportedOperationException();
+				}
+			}
+		}
+		return file;
+	}
+
+	private static String setupHost(final String host) {
+		if (!host.contains("@")) {
+			return DEFAULT_REF_TREE + "@" + host;
+		}
+
+		return host;
+	}
+
+	private static String setupPath(final String path) {
+		if (path.isEmpty()) {
+			return "/";
+		}
+		return path;
+	}
+
+	public String getRefTree() {
+		return host.substring(0, host.indexOf("@"));
+	}
+
+	public String getPath() {
+		return new String(path);
+	}
+
+	public boolean isRegularFile() throws IllegalAccessError, SecurityException {
+		try {
+			return getFileSystem().provider().readAttributes(this, BasicFileAttributes.class).isRegularFile();
+		} catch (IOException ioe) {
+		}
+		return false;
+	}
+
+	private static long internalCopy(final InputStream in, final OutputStream out) {
+		long read = 0L;
+		byte[] buf = new byte[BUFFER_SIZE];
+		int n;
+		try {
+			while ((n = in.read(buf)) > 0) {
+				out.write(buf, 0, n);
+				read += n;
+			}
+		} catch (java.io.IOException e) {
+			throw new RuntimeException(e);
+		}
+		return read;
+	}
+
+	@Override
+	public String getSegmentId() {
+		return getRefTree();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactory.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactory.java
new file mode 100644
index 0000000000..ce49f758da
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitSSHConfigSessionFactory.java
@@ 0,0 +1,84 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import com.jcraft.jsch.ProxyHTTP;
+import com.jcraft.jsch.Session;
+import com.jcraft.jsch.UserInfo;
+import org.eclipse.jgit.errors.UnsupportedCredentialItem;
+import org.eclipse.jgit.transport.CredentialItem;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.CredentialsProviderUserInfo;
+import org.eclipse.jgit.transport.OpenSshConfig;
+import org.eclipse.jgit.transport.URIish;
+
+public class JGitSSHConfigSessionFactory extends org.eclipse.jgit.transport.JschConfigSessionFactory {
+
+	private final JGitFileSystemProviderConfiguration config;
+
+	public JGitSSHConfigSessionFactory(final JGitFileSystemProviderConfiguration config) {
+		this.config = config;
+	}
+
+	@Override
+	protected void configure(final OpenSshConfig.Host hc, final Session session) {
+		final CredentialsProvider provider = new CredentialsProvider() {
+			@Override
+			public boolean isInteractive() {
+				return false;
+			}
+
+			@Override
+			public boolean supports(final CredentialItem... items) {
+				return true;
+			}
+
+			@Override
+			public boolean get(final URIish uri, final CredentialItem... items) throws UnsupportedCredentialItem {
+				for (CredentialItem item : items) {
+					if (item instanceof CredentialItem.YesNoType) {
+						((CredentialItem.YesNoType) item).setValue(true);
+					} else if (item instanceof CredentialItem.StringType) {
+						((CredentialItem.StringType) item).setValue(config.getSshPassphrase());
+					}
+				}
+				return true;
+			}
+		};
+		final UserInfo userInfo = new CredentialsProviderUserInfo(session, provider);
+		session.setUserInfo(userInfo);
+		if (config.isSshOverHttpProxy() || config.isSshOverHttpsProxy()) {
+			session.setProxy(buildProxy(config));
+		}
+	}
+
+	ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
+		final String host;
+		final int port;
+		String user = null;
+		String passw = null;
+		if (config.isSshOverHttpProxy()) {
+			host = config.getHttpProxyHost();
+			port = config.getHttpProxyPort();
+			user = config.getHttpProxyUser();
+			passw = config.getHttpProxyPassword();
+		} else {
+			host = config.getHttpsProxyHost();
+			port = config.getHttpsProxyPort();
+			user = config.getHttpsProxyUser();
+			passw = config.getHttpsProxyPassword();
+		}
+		final ProxyHTTP proxyHTTP = new ProxyHTTP(host, port);
+		if (user != null) {
+			proxyHTTP.setUserPasswd(user, passw);
+		}
+		return proxyHTTP;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitVersionAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitVersionAttributeViewImpl.java
new file mode 100644
index 0000000000..21fd06d7a4
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitVersionAttributeViewImpl.java
@@ 0,0 +1,169 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.NoSuchFileException;
+import java.nio.file.attribute.BasicFileAttributeView;
+import java.nio.file.attribute.FileTime;
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.Date;
+import java.util.List;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
+import org.eclipse.jgit.niofs.fs.attribute.VersionHistory;
+import org.eclipse.jgit.niofs.fs.attribute.VersionRecord;
+import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathType;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+/**
+ *
+ */
+public class JGitVersionAttributeViewImpl extends VersionAttributeViewImpl<JGitPathImpl> {
+
+	private VersionAttributes attrs = null;
+
+	public JGitVersionAttributeViewImpl(final JGitPathImpl path) {
+		super(path);
+	}
+
+	@Override
+	public VersionAttributes readAttributes() throws IOException {
+		if (attrs == null) {
+			attrs = buildAttrs((JGitFileSystem) path.getFileSystem(), path.getRefTree(), path.getPath());
+		}
+		return attrs;
+	}
+
+	@Override
+	public Class<? extends BasicFileAttributeView>[] viewTypes() {
+		return new Class[] { VersionAttributeView.class, VersionAttributeViewImpl.class,
+				JGitVersionAttributeViewImpl.class };
+	}
+
+	private VersionAttributes buildAttrs(final JGitFileSystem fs, final String branchName, final String path)
+			throws NoSuchFileException {
+		final PathInfo pathInfo = fs.getGit().getPathInfo(branchName, path);
+
+		if (pathInfo == null || pathInfo.getPathType().equals(PathType.NOT_FOUND)) {
+			throw new NoSuchFileException(path);
+		}
+
+		final Ref refId = fs.getGit().getRef(branchName);
+		final List<VersionRecord> records = new ArrayList<>();
+
+		if (refId != null) {
+			try {
+				final CommitHistory history = fs.getGit().listCommits(refId, pathInfo.getPath());
+				for (final RevCommit commit : history.getCommits()) {
+					final String recordPath = history.trackedFileNameChangeFor(commit.getId());
+					records.add(new VersionRecord() {
+						@Override
+						public String id() {
+							return commit.name();
+						}
+
+						@Override
+						public String author() {
+							return commit.getAuthorIdent().getName();
+						}
+
+						@Override
+						public String email() {
+							return commit.getAuthorIdent().getEmailAddress();
+						}
+
+						@Override
+						public String comment() {
+							return commit.getFullMessage();
+						}
+
+						@Override
+						public Date date() {
+							return commit.getAuthorIdent().getWhen();
+						}
+
+						@Override
+						public String uri() {
+							return fs.getPath(commit.name(), recordPath).toUri().toString();
+						}
+					});
+				}
+			} catch (Exception e) {
+				throw new RuntimeException(e);
+			}
+		}
+
+		Collections.reverse(records);
+
+		return new VersionAttributes() {
+			@Override
+			public VersionHistory history() {
+				return () > records;
+			}
+
+			@Override
+			public FileTime lastModifiedTime() {
+				if (records.size() > 0) {
+					return FileTime.fromMillis(records.get(records.size()  1).date().getTime());
+				}
+				return null;
+			}
+
+			@Override
+			public FileTime lastAccessTime() {
+				return lastModifiedTime();
+			}
+
+			@Override
+			public FileTime creationTime() {
+				if (records.size() > 0) {
+					return FileTime.fromMillis(records.get(0).date().getTime());
+				}
+				return null;
+			}
+
+			@Override
+			public boolean isRegularFile() {
+				return pathInfo.getPathType().equals(PathType.FILE);
+			}
+
+			@Override
+			public boolean isDirectory() {
+				return pathInfo.getPathType().equals(PathType.DIRECTORY);
+			}
+
+			@Override
+			public boolean isSymbolicLink() {
+				return false;
+			}
+
+			@Override
+			public boolean isOther() {
+				return false;
+			}
+
+			@Override
+			public long size() {
+				return pathInfo.getSize();
+			}
+
+			@Override
+			public Object fileKey() {
+				return pathInfo.getObjectId() == null ? null : pathInfo.getObjectId().toString();
+			}
+		};
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchEvent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchEvent.java
new file mode 100644
index 0000000000..c36ca9a264
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchEvent.java
@@ 0,0 +1,127 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.net.URI;
+import java.nio.file.Path;
+import java.nio.file.Paths;
+import java.nio.file.StandardWatchEventKinds;
+import java.nio.file.WatchEvent;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.niofs.fs.WatchContext;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class JGitWatchEvent implements WatchEvent {
+
+	private static final Logger LOGGER = LoggerFactory.getLogger(JGitWatchEvent.class);
+	public static final Kind<Path> ENTRY_RENAME = new Kind<Path>() {
+		@Override
+		public String name() {
+			return "ENTRY_RENAME";
+		}
+
+		@Override
+		public Class<Path> type() {
+			return Path.class;
+		}
+	};
+
+	private final URI oldPath;
+	private final URI newPath;
+	private final String sessionId;
+	private final String userName;
+	private final String message;
+	private final String changeType;
+
+	public JGitWatchEvent(String sessionId, String userName, String message, String changeType, Path oldPath,
+			Path newPath) {
+
+		this.sessionId = sessionId;
+		this.userName = userName;
+		this.message = message;
+		this.changeType = changeType;
+		this.oldPath = oldPath != null ? oldPath.toUri() : null;
+		this.newPath = newPath != null ? newPath.toUri() : null;
+	}
+
+	@Override
+	public WatchEvent.Kind kind() {
+		DiffEntry.ChangeType changeType = DiffEntry.ChangeType.valueOf(this.changeType);
+		switch (changeType) {
+		case ADD:
+		case COPY:
+			return StandardWatchEventKinds.ENTRY_CREATE;
+		case DELETE:
+			return StandardWatchEventKinds.ENTRY_DELETE;
+		case MODIFY:
+			return StandardWatchEventKinds.ENTRY_MODIFY;
+		case RENAME:
+			return ENTRY_RENAME;
+		default:
+			throw new RuntimeException("Unsupported change type: " + changeType);
+		}
+	}
+
+	@Override
+	public int count() {
+		return 1;
+	}
+
+	@Override
+	public Object context() {
+		return new WatchContext() {
+
+			@Override
+			public Path getPath() {
+				return newPath != null ? lookup(newPath) : null;
+			}
+
+			@Override
+			public Path getOldPath() {
+				return oldPath != null ? lookup(oldPath) : null;
+			}
+
+			private Path lookup(URI uri) {
+				Path path = null;
+				try {
+					path = Paths.get(uri);
+				} catch (Exception e) {
+					LOGGER.error("Error trying to translate to path uri: " + uri);
+				}
+				return path;
+			}
+
+			@Override
+			public String getSessionId() {
+				return sessionId;
+			}
+
+			@Override
+			public String getMessage() {
+				return message;
+			}
+
+			@Override
+			public String getUser() {
+				return userName;
+			}
+		};
+	}
+
+	@Override
+	public String toString() {
+		return "WatchEvent{" + "newPath=" + newPath + ", oldPath=" + oldPath + ", sessionId='" + sessionId + '\''
+				+ ", userName='" + userName + '\'' + ", message='" + message + '\'' + ", changeType=" + changeType
+				+ '}';
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchService.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchService.java
new file mode 100644
index 0000000000..024f133391
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/JGitWatchService.java
@@ 0,0 +1,86 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.IOException;
+import java.nio.file.ClosedWatchServiceException;
+import java.nio.file.WatchKey;
+import java.nio.file.WatchService;
+import java.util.Queue;
+import java.util.concurrent.ConcurrentLinkedQueue;
+import java.util.concurrent.TimeUnit;
+import java.util.function.Consumer;
+
+public class JGitWatchService implements WatchService {
+
+	private boolean wsClose = false;
+
+	private final Queue<WatchKey> events = new ConcurrentLinkedQueue<>();
+	private final String fsName;
+	private Consumer<JGitWatchService> notifyClose;
+
+	public JGitWatchService(String fsName, Consumer<JGitWatchService> notifyClose) {
+
+		this.fsName = fsName;
+		this.notifyClose = notifyClose;
+	}
+
+	@Override
+	public WatchKey poll() throws ClosedWatchServiceException {
+		return events.poll();
+	}
+
+	@Override
+	public WatchKey poll(long timeout, TimeUnit unit) throws ClosedWatchServiceException, InterruptedException {
+		return events.poll();
+	}
+
+	@Override
+	public synchronized WatchKey take() throws ClosedWatchServiceException, InterruptedException {
+		while (true) {
+			if (wsClose) {
+				throw new ClosedWatchServiceException();
+			} else if (events.size() > 0) {
+				return events.poll();
+			} else {
+				try {
+					this.wait();
+				} catch (final java.lang.InterruptedException e) {
+				}
+			}
+		}
+	}
+
+	public boolean isClose() {
+		return wsClose;
+	}
+
+	@Override
+	public synchronized void close() throws IOException {
+		wsClose = true;
+		notifyAll();
+		notifyClose.accept(this);
+	}
+
+	synchronized void closeWithoutNotifyParent() {
+		wsClose = true;
+		notifyAll();
+	}
+
+	@Override
+	public String toString() {
+		return "WatchService{" + "FileSystem=" + fsName + '}';
+	}
+
+	public void publish(WatchKey wk) {
+		events.add(wk);
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/LockableFileSystem.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/LockableFileSystem.java
new file mode 100644
index 0000000000..00209b921b
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/LockableFileSystem.java
@@ 0,0 +1,17 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+public interface LockableFileSystem {
+
+	void lock();
+
+	void unlock();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/NotificationModel.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/NotificationModel.java
new file mode 100644
index 0000000000..2629040490
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/NotificationModel.java
@@ 0,0 +1,44 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import org.eclipse.jgit.lib.ObjectId;
+
+public class NotificationModel {
+
+	private final ObjectId originalHead;
+	private final String sessionId;
+	private final String userName;
+	private final String message;
+
+	public NotificationModel(final ObjectId originalHead, final String sessionId, final String userName,
+			final String message) {
+		this.originalHead = originalHead;
+		this.sessionId = sessionId;
+		this.userName = userName;
+		this.message = message;
+	}
+
+	public ObjectId getOriginalHead() {
+		return originalHead;
+	}
+
+	public String getSessionId() {
+		return sessionId;
+	}
+
+	public String getUserName() {
+		return userName;
+	}
+
+	public String getMessage() {
+		return message;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Properties.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Properties.java
new file mode 100644
index 0000000000..984ebc1fbb
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/Properties.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.util.HashMap;
+import java.util.Map;
+
+/**
+ *
+ */
+public class Properties extends HashMap<String, Object> {
+
+	public Properties() {
+	}
+
+	public Properties(final Map<String, Object> original) {
+		for (Entry<String, Object> e : original.entrySet()) {
+			if (e.getValue() != null) {
+				put(e.getKey(), e.getValue());
+			}
+		}
+	}
+
+	public Object put(final String key, final Object value) {
+		if (value == null) {
+			return remove(key);
+		}
+		return super.put(key, value);
+	}
+
+	public void store(final OutputStream out) {
+		store(out, true);
+	}
+
+	public void store(final OutputStream out, boolean closeOnFinish) {
+	}
+
+	public void load(final InputStream in) {
+		load(in, true);
+	}
+
+	public void load(final InputStream in, boolean closeOnFinish) {
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SecuredFileSystemProvider.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SecuredFileSystemProvider.java
new file mode 100644
index 0000000000..76a1658b9d
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SecuredFileSystemProvider.java
@@ 0,0 +1,38 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.nio.file.spi.FileSystemProvider;
+
+import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
+import org.eclipse.jgit.niofs.internal.security.FileSystemAuthorization;
+import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;
+
+/**
+ * Specialization of {@link FileSystemProvider} for file systems that require
+ * username/password authentication and support authorization of certain
+ * actions.
+ */
+public abstract class SecuredFileSystemProvider extends FileSystemProvider {
+
+	/**
+	 * Sets the authenticator that decides which username/password pairs are valid
+	 * for the file systems managed by this provider.
+	 * 
+	 * @param authenticator The authenticator to use. Must not be null.
+	 */
+	public abstract void setJAASAuthenticator(final AuthenticationService authenticator);
+
+	public abstract void setHTTPAuthenticator(final AuthenticationService authenticator);
+
+	public abstract void setSSHAuthenticator(final PublicKeyAuthenticator authenticator);
+
+	public abstract void setAuthorizer(final FileSystemAuthorization authorizer);
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SeekableByteChannelWrapper.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SeekableByteChannelWrapper.java
new file mode 100644
index 0000000000..5a02e5f6c9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SeekableByteChannelWrapper.java
@@ 0,0 +1,68 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.IOException;
+import java.nio.ByteBuffer;
+import java.nio.channels.SeekableByteChannel;
+
+/**
+ *
+ */
+public class SeekableByteChannelWrapper implements SeekableByteChannel {
+
+	private final SeekableByteChannel channel;
+
+	public SeekableByteChannelWrapper(final SeekableByteChannel channel) {
+		this.channel = checkNotNull("channel", channel);
+	}
+
+	@Override
+	public long position() throws IOException {
+		return channel.position();
+	}
+
+	@Override
+	public SeekableByteChannel position(final long newPosition) throws IOException {
+		return channel.position(newPosition);
+	}
+
+	@Override
+	public long size() throws IOException {
+		return channel.size();
+	}
+
+	@Override
+	public SeekableByteChannel truncate(final long size) throws IOException {
+		return channel.truncate(size);
+	}
+
+	@Override
+	public int read(final ByteBuffer dst) throws java.io.IOException {
+		return channel.read(dst);
+	}
+
+	@Override
+	public int write(final ByteBuffer src) throws java.io.IOException {
+		return channel.write(src);
+	}
+
+	@Override
+	public boolean isOpen() {
+		return channel.isOpen();
+	}
+
+	@Override
+	public void close() throws java.io.IOException {
+		channel.close();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SegmentedPath.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SegmentedPath.java
new file mode 100644
index 0000000000..337ef41209
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/SegmentedPath.java
@@ 0,0 +1,15 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+public interface SegmentedPath {
+
+	String getSegmentId();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/VersionAttributeViewImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/VersionAttributeViewImpl.java
new file mode 100644
index 0000000000..1ba29a5675
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/VersionAttributeViewImpl.java
@@ 0,0 +1,59 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+
+import java.io.IOException;
+import java.nio.file.Path;
+import java.util.HashMap;
+import java.util.Map;
+
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;
+import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
+
+/**
+ *
+ */
+public abstract class VersionAttributeViewImpl<P extends Path> extends AbstractBasicFileAttributeView<P>
+		implements VersionAttributeView {
+
+	public static final String VERSION = "version";
+
+	public VersionAttributeViewImpl(final P path) {
+		super(path);
+	}
+
+	@Override
+	public String name() {
+		return VERSION;
+	}
+
+	@Override
+	public Map<String, Object> readAttributes(final String... attributes) throws IOException {
+		final VersionAttributes attrs = readAttributes();
+
+		return new HashMap<String, Object>(super.readAttributes(attributes)) {
+			{
+				for (final String attribute : attributes) {
+					checkNotEmpty("attribute", attribute);
+
+					if (attribute.equals("*") || attribute.equals(VERSION)) {
+						put(VERSION, attrs.history());
+					}
+
+					if (attribute.equals("*")) {
+						break;
+					}
+				}
+			}
+		};
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/WatchEventsWrapper.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/WatchEventsWrapper.java
new file mode 100644
index 0000000000..a1dee627e3
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/WatchEventsWrapper.java
@@ 0,0 +1,90 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal;
+
+import java.io.Serializable;
+import java.net.URI;
+import java.nio.file.Path;
+import java.nio.file.Paths;
+import java.nio.file.WatchEvent;
+import java.util.List;
+import java.util.Objects;
+
+public class WatchEventsWrapper implements Serializable {
+
+	private final String nodeId;
+	private final List<WatchEvent<?>> events;
+	private final URI watchable;
+	private final String fsName;
+
+	public WatchEventsWrapper(String nodeId, String fsName, Path watchable, List<WatchEvent<?>> events) {
+
+		this.nodeId = nodeId;
+		this.fsName = fsName;
+		this.events = events;
+		this.watchable = watchable != null ? watchable.toUri() : null;
+	}
+
+	public String getFsName() {
+		return fsName;
+	}
+
+	public String getNodeId() {
+		return nodeId;
+	}
+
+	public List<WatchEvent<?>> getEvents() {
+		return events;
+	}
+
+	public Path getWatchable() {
+		if (watchable == null) {
+			return null;
+		}
+		try {
+			return Paths.get(watchable);
+		} catch (Exception e) {
+			return null;
+		}
+	}
+
+	@Override
+	public boolean equals(Object o) {
+		if (this == o) {
+			return true;
+		}
+		if (o == null || getClass() != o.getClass()) {
+			return false;
+		}
+
+		WatchEventsWrapper that = (WatchEventsWrapper) o;
+
+		if (!Objects.equals(nodeId, that.nodeId)) {
+			return false;
+		}
+		if (!Objects.equals(events, that.events)) {
+			return false;
+		}
+		if (!Objects.equals(watchable, that.watchable)) {
+			return false;
+		}
+		return Objects.equals(fsName, that.fsName);
+	}
+
+	@Override
+	public int hashCode() {
+		int result = nodeId != null ? nodeId.hashCode() : 0;
+		result = 31 * result + (events != null ? events.hashCode() : 0);
+		result = 31 * result + (watchable != null ? watchable.hashCode() : 0);
+		result = 31 * result + (fsName != null ? fsName.hashCode() : 0);
+		return result;
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/config/ConfigProperties.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/config/ConfigProperties.java
new file mode 100644
index 0000000000..b8faab69fd
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/config/ConfigProperties.java
@@ 0,0 +1,165 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.config;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.util.ArrayList;
+import java.util.HashMap;
+import java.util.List;
+import java.util.Map;
+import java.util.Properties;
+
+/**
+ * Encapsulates a collection of Java System Properties by name and value.
+ * Includes handy methods for providing and identifying applicationprovided
+ * default values and converting from Strings to other common types.
+ */
+public class ConfigProperties {
+
+	private final Map<String, String> configuredValues;
+	private final List<ConfigProperty> configSummary = new ArrayList<>();
+
+	/**
+	 * Creates a new container of configured values from which specific config
+	 * properties can be obtained.
+	 * 
+	 * @param configuredValues The configured values, which may have been hardcoded
+	 *                         in a Map, read from a config file, or whatever.
+	 */
+	public ConfigProperties(Map<String, String> configuredValues) {
+		this.configuredValues = checkNotNull("configuredValues", configuredValues);
+	}
+
+	/**
+	 * Creates a new container of configured values from which specific config
+	 * properties can be obtained.
+	 * 
+	 * @param configuredValues The configured values, which may have been read from
+	 *                         a properties file, or obtained from
+	 *                         {@link System#getProperties()}. If the property set
+	 *                         contains entries whose key and value are not both
+	 *                         Strings, these entries will be ignored.
+	 */
+	public ConfigProperties(Properties configuredValues) {
+		Map<String, String> stringProperties = new HashMap<>();
+		for (String key : configuredValues.stringPropertyNames()) {
+			stringProperties.put(key, configuredValues.getProperty(key));
+		}
+		this.configuredValues = stringProperties;
+	}
+
+	/**
+	 * Returns the ConfigProperty instance corresponding to the configured value of
+	 * the given property, or the default if no configured value exists.
+	 * 
+	 * @param name         the property name. Must not be null.
+	 * @param defaultValue the value to use if no configured value exists. May be
+	 *                     null.
+	 * @return
+	 */
+	public ConfigProperty get(String name, String defaultValue) {
+		String val = configuredValues.get(name);
+		ConfigProperty cp;
+		if (val == null || val.trim().length() == 0) {
+			cp = new ConfigProperty(name, defaultValue, true);
+		} else {
+			cp = new ConfigProperty(name, val.trim(), false);
+		}
+		configSummary.add(cp);
+		return cp;
+	}
+
+	/**
+	 * Returns a multiline string containing a list of all the properties that were
+	 * retrieved from this instance, in the order they were retrieved. Does not list
+	 * unused values from the map given in the constructor, since these may contain
+	 * a lot of unrelated information (for example, when using
+	 * System.getProperties()). This is useful for printing a summary of the
+	 * configuration in a given subsystem. It also helps users learn about available
+	 * configuration values.
+	 * 
+	 * @param heading a line of text to print before listing the configuration
+	 *                values
+	 */
+	public String getConfigurationSummary(String heading) {
+		final String newLine = System.getProperty("line.separator");
+		StringBuilder sb = new StringBuilder(heading);
+		for (ConfigProperty cp : configSummary) {
+			sb.append(newLine).append(cp);
+		}
+		return sb.toString();
+	}
+
+	public static class ConfigProperty {
+
+		private final String name;
+		private final String value;
+		private final boolean isDefault;
+
+		ConfigProperty(String name, String value, boolean isDefault) {
+			this.name = name;
+			this.value = value;
+			this.isDefault = isDefault;
+		}
+
+		/**
+		 * Returns the name (map key) of this property.
+		 */
+		public String getName() {
+			return name;
+		}
+
+		/**
+		 * Returns the value of this property, which may or may not have been the
+		 * default value.
+		 * 
+		 * @return the property value. Will be null if both the configured value was
+		 *         missing and the default was given as null.
+		 * @see #isDefault()
+		 */
+		public String getValue() {
+			return value;
+		}
+
+		/**
+		 * Returns false if this value appeared among the usersupplied values; false if
+		 * it came from the applicationprovided default.
+		 * 
+		 * @return whether this value is a default
+		 */
+		public boolean isDefault() {
+			return isDefault;
+		}
+
+		/**
+		 * Returns the boolean value of this property, converting from string using the
+		 * same rules as {@link Boolean#valueOf(String)}.
+		 */
+		public boolean getBooleanValue() {
+			return Boolean.parseBoolean(value);
+		}
+
+		/**
+		 * Returns the integer value of this property, converting from string using the
+		 * same rules as {@link Integer#parseInt(String)}.
+		 * 
+		 * @throws NumberFormatException if the value cannot be parsed as an integer.
+		 */
+		public int getIntValue() {
+			return Integer.parseInt(value);
+		}
+
+		@Override
+		public String toString() {
+			return name + " = \"" + value + "\"" + (isDefault ? " (Defaulted)" : "");
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/common/PortUtil.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/common/PortUtil.java
new file mode 100644
index 0000000000..972d526ce9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/common/PortUtil.java
@@ 0,0 +1,45 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.common;
+
+import java.io.IOException;
+import java.net.ServerSocket;
+
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class PortUtil {
+
+	private static final String ERROR_MESSAGE = "Error trying to find a free port.";
+	private static final Logger LOG = LoggerFactory.getLogger(PortUtil.class);
+
+	public static int validateOrGetNew(int preferredPort) {
+		if (preferredPort == 0 || isPortInUse(preferredPort)) {
+			if (preferredPort != 0) {
+				LOG.warn("Port {} already in use, system will automatically look for a new one.", preferredPort);
+			}
+			try (ServerSocket ss = new ServerSocket(0)) {
+				return ss.getLocalPort();
+			} catch (IOException e) {
+				LOG.error(ERROR_MESSAGE, e);
+				throw new RuntimeException(ERROR_MESSAGE);
+			}
+		}
+		return preferredPort;
+	}
+
+	private static boolean isPortInUse(int port) {
+		try (final ServerSocket ss = new ServerSocket(port)) {
+			return false;
+		} catch (Exception e) {
+		}
+		return true;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/filter/HiddenBranchRefFilter.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/filter/HiddenBranchRefFilter.java
new file mode 100644
index 0000000000..838c65e067
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/filter/HiddenBranchRefFilter.java
@@ 0,0 +1,44 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.filter;
+
+import java.util.Map;
+import java.util.regex.Pattern;
+import java.util.stream.Collectors;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.transport.RefFilter;
+
+/**
+ * This RefFilter is used to exclude hidden branches from
+ * {@link org.eclipse.jgit.transport.UploadPack}. Check
+ * {@link org.eclipse.jgit.niofs.internal.daemon.git.Daemon}
+ */
+public class HiddenBranchRefFilter implements RefFilter {
+
+	private static final String HIDDEN_BRANCH_REGEXP = "PR\\d+\\S+\\S+";
+	private static Pattern pattern = Pattern.compile(HIDDEN_BRANCH_REGEXP);
+
+	@Override
+	public Map<String, Ref> filter(final Map<String, Ref> refs) {
+		return refs.entrySet().stream().filter(ref > !HiddenBranchRefFilter.isHidden(ref.getKey()))
+				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
+	}
+
+	/**
+	 * Checks if a branch name matches the hidden branch regexp
+	 * 
+	 * @param branch the branch you want to check.
+	 * @return return if the branch is hidden or not
+	 */
+	public static boolean isHidden(String branch) {
+		return pattern.matcher(branch).matches();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/Daemon.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/Daemon.java
new file mode 100644
index 0000000000..d91a7cf960
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/Daemon.java
@@ 0,0 +1,393 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.git;
+
+import java.io.IOException;
+import java.io.InputStream;
+import java.io.InterruptedIOException;
+import java.io.OutputStream;
+import java.net.InetAddress;
+import java.net.InetSocketAddress;
+import java.net.ServerSocket;
+import java.net.Socket;
+import java.net.SocketAddress;
+import java.net.URISyntaxException;
+import java.util.Date;
+import java.util.TimeZone;
+import java.util.concurrent.Executor;
+import java.util.concurrent.ExecutorService;
+import java.util.concurrent.atomic.AtomicBoolean;
+import java.util.zip.Deflater;
+
+import org.eclipse.jgit.errors.RepositoryNotFoundException;
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.internal.ketch.KetchLeader;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.internal.ketch.KetchPreReceive;
+import org.eclipse.jgit.internal.ketch.KetchText;
+import org.eclipse.jgit.lib.PersonIdent;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
+import org.eclipse.jgit.niofs.internal.util.DescriptiveRunnable;
+import org.eclipse.jgit.storage.pack.PackConfig;
+import org.eclipse.jgit.transport.ReceivePack;
+import org.eclipse.jgit.transport.ServiceMayNotContinueException;
+import org.eclipse.jgit.transport.UploadPack;
+import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
+import org.eclipse.jgit.transport.resolver.RepositoryResolver;
+import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
+import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
+import org.eclipse.jgit.transport.resolver.UploadPackFactory;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static org.eclipse.jgit.niofs.internal.daemon.common.PortUtil.validateOrGetNew;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+/**
+ * Basic daemon for the anonymous <code>git://</code> transport protocol.
+ */
+public class Daemon {
+
+	private static final Logger LOG = LoggerFactory.getLogger(Daemon.class);
+
+	private static final int BACKLOG = 5;
+
+	private InetSocketAddress myAddress;
+
+	private final DaemonService[] services;
+
+	private final AtomicBoolean run = new AtomicBoolean(false);
+
+	private int timeout;
+
+	private volatile RepositoryResolver<DaemonClient> repositoryResolver;
+
+	private volatile UploadPackFactory<DaemonClient> uploadPackFactory;
+
+	private volatile ReceivePackFactory<DaemonClient> receivePackFactory;
+
+	private ServerSocket listenSock = null;
+
+	private ExecutorService executorService;
+
+	private final Executor acceptThreadPool;
+
+	public Daemon(final InetSocketAddress addr, final Executor acceptThreadPool,
+			final ExecutorService executorService) {
+		this(addr, acceptThreadPool, executorService, null);
+	}
+
+	/**
+	 * Configures a new daemon for the specified network address. The daemon will
+	 * not attempt to bind to an address or accept connections until a call to
+	 * {@link #start()}.
+	 * 
+	 * @param addr             address to listen for connections on. If null, any
+	 *                         available port will be chosen on all network
+	 *                         interfaces.
+	 * @param acceptThreadPool source of threads for waiting for inbound socket
+	 *                         connections. Every time the daemon is started or
+	 *                         restarted, a new task will be submitted to this pool.
+	 *                         When the daemon is stopped, the task completes.
+	 */
+	public Daemon(final InetSocketAddress addr, final Executor acceptThreadPool, final ExecutorService executorService,
+			final KetchLeaderCache leaders) {
+		myAddress = addr;
+		this.acceptThreadPool = checkNotNull("acceptThreadPool", acceptThreadPool);
+
+		this.executorService = executorService;
+
+		repositoryResolver = (RepositoryResolver<DaemonClient>) RepositoryResolver.NONE;
+
+		uploadPackFactory = (req, db) > {
+			final UploadPack up = new UploadPack(db);
+			up.setTimeout(getTimeout());
+			up.setRefFilter(new HiddenBranchRefFilter());
+			final PackConfig config = new PackConfig(db);
+			config.setCompressionLevel(Deflater.BEST_COMPRESSION);
+			up.setPackConfig(config);
+
+			return up;
+		};
+
+		final ReceivePackFactory<DaemonClient> factory = (req, db) > {
+			final ReceivePack rp = new KetchCustomReceivePack(db);
+
+			final InetAddress peer = req.getRemoteAddress();
+			String host = peer.getCanonicalHostName();
+			if (host == null) {
+				host = peer.getHostAddress();
+			}
+			final String name = "anonymous";
+			final String email = name + "@" + host;
+			rp.setRefLogIdent(new PersonIdent("system", "system", new Date(1L), TimeZone.getDefault()));
+			rp.setTimeout(getTimeout());
+
+			rp.setPreReceiveHook(
+					(rp12, commands) > System.out.println("[" + addr.getHostString() + "]" + " onPreReceive!"));
+			rp.setPostReceiveHook(
+					(rp1, commands) > System.out.println("[" + addr.getHostString() + "]" + " onPostReceive!"));
+
+			return rp;
+		};
+
+//        if ( leaders == null ) {
+		if (true) {
+			receivePackFactory = factory;
+		} else {
+			receivePackFactory = (req, repo) > {
+				final ReceivePack rp = factory.create(req, repo);
+				final KetchLeader leader;
+				try {
+					leader = leaders.get(repo);
+				} catch (URISyntaxException err) {
+					throw new ServiceNotEnabledException(KetchText.get().invalidFollowerUri, err);
+				}
+				rp.setPreReceiveHook(new KetchPreReceive(leader));
+				return rp;
+			};
+		}
+
+		services = new DaemonService[] { new DaemonService("uploadpack", "uploadpack") {
+			{
+				setEnabled(true);
+			}
+
+			@Override
+			protected void execute(final DaemonClient dc, final Repository db)
+					throws IOException, ServiceNotEnabledException, ServiceNotAuthorizedException {
+				final UploadPack up = uploadPackFactory.create(dc, db);
+				final InputStream in = dc.getInputStream();
+				final OutputStream out = dc.getOutputStream();
+				up.upload(in, out, null);
+			}
+		}, new DaemonService("receivepack", "receivepack") {
+			{
+				setEnabled(true);
+			}
+
+			@Override
+			protected void execute(final DaemonClient dc, final Repository db)
+					throws IOException, ServiceNotEnabledException, ServiceNotAuthorizedException {
+				final ReceivePack rp = receivePackFactory.create(dc, db);
+				final InputStream in = dc.getInputStream();
+				final OutputStream out = dc.getOutputStream();
+				rp.receive(in, out, null);
+			}
+		} };
+	}
+
+	/**
+	 * @return the address connections are received on.
+	 */
+	public synchronized InetSocketAddress getAddress() {
+		return myAddress;
+	}
+
+	/**
+	 * Lookup a supported service so it can be reconfigured.
+	 * 
+	 * @param name name of the service; e.g. "receivepack"/"gitreceivepack" or
+	 *             "uploadpack"/"gituploadpack".
+	 * @return the service; null if this daemon implementation doesn't support the
+	 *         requested service type.
+	 */
+	public synchronized DaemonService getService(String name) {
+		if (!name.startsWith("git")) {
+			name = "git" + name;
+		}
+		for (final DaemonService s : services) {
+			if (s.getCommandName().equals(name)) {
+				return s;
+			}
+		}
+		return null;
+	}
+
+	/**
+	 * @return timeout (in seconds) before aborting an IO operation.
+	 */
+	public int getTimeout() {
+		return timeout;
+	}
+
+	/**
+	 * Set the timeout before willing to abort an IO call.
+	 * 
+	 * @param seconds number of seconds to wait (with no data transfer occurring)
+	 *                before aborting an IO read or write operation with the
+	 *                connected client.
+	 */
+	public void setTimeout(final int seconds) {
+		timeout = seconds;
+	}
+
+	/**
+	 * Sets the resolver that locates repositories by name.
+	 * 
+	 * @param resolver the resolver instance.
+	 */
+	public void setRepositoryResolver(RepositoryResolver<DaemonClient> resolver) {
+		repositoryResolver = resolver;
+	}
+
+	/**
+	 * Sets the factory that constructs and configures the perrequest UploadPack.
+	 * 
+	 * @param factory the factory. If null uploadpack is disabled.
+	 */
+	@SuppressWarnings("unchecked")
+	public void setUploadPackFactory(UploadPackFactory<DaemonClient> factory) {
+		if (factory != null) {
+			uploadPackFactory = factory;
+		} else {
+			uploadPackFactory = (UploadPackFactory<DaemonClient>) UploadPackFactory.DISABLED;
+		}
+	}
+
+	/**
+	 * Starts this daemon listening for connections on a thread supplied by the
+	 * executor service given to the constructor. The daemon can be stopped by a
+	 * call to {@link #stop()} or by shutting down the ExecutorService.
+	 * 
+	 * @throws IOException           the server socket could not be opened.
+	 * @throws IllegalStateException the daemon is already running.
+	 */
+	public synchronized void start() throws IOException {
+		if (run.get()) {
+			throw new IllegalStateException(JGitText.get().daemonAlreadyRunning);
+		}
+
+		InetAddress listenAddress = myAddress != null ? myAddress.getAddress() : null;
+		int listenPort = myAddress != null ? myAddress.getPort() : 0;
+
+		try {
+			this.listenSock = new ServerSocket(validateOrGetNew(listenPort), BACKLOG, listenAddress);
+		} catch (IOException e) {
+			throw new IOException("Failed to open server socket for " + listenAddress + ":" + listenPort, e);
+		}
+		if (listenSock.getLocalPort() != listenPort) {
+			LOG.error("Git original port {} not available, new free port {} assigned.", listenPort,
+					listenSock.getLocalPort());
+		}
+		myAddress = (InetSocketAddress) listenSock.getLocalSocketAddress();
+
+		run.set(true);
+		acceptThreadPool.execute(new DescriptiveRunnable() {
+			@Override
+			public String getDescription() {
+				return "GitDaemonAccept";
+			}
+
+			@Override
+			public void run() {
+				while (isRunning() && !Thread.currentThread().isInterrupted()) {
+					try {
+						listenSock.setSoTimeout(5000);
+						startClient(listenSock.accept());
+					} catch (InterruptedIOException e) {
+						// Test again to see if we should keep accepting.
+					} catch (IOException e) {
+						break;
+					}
+				}
+
+				stop();
+			}
+		});
+	}
+
+	/**
+	 * @return true if this daemon is receiving connections.
+	 */
+	public boolean isRunning() {
+		return run.get();
+	}
+
+	/**
+	 * Stops this daemon. It is safe to call this method on a daemon which is
+	 * already stopped, in which case the call has no effect.
+	 */
+	public synchronized void stop() {
+		if (run.getAndSet(false)) {
+			try {
+				listenSock.close();
+			} catch (IOException e) {
+			}
+		}
+	}
+
+	private void startClient(final Socket s) {
+		final DaemonClient dc = new DaemonClient(this);
+
+		final SocketAddress peer = s.getRemoteSocketAddress();
+		if (peer instanceof InetSocketAddress) {
+			dc.setRemoteAddress(((InetSocketAddress) peer).getAddress());
+		}
+
+		executorService.execute(new DescriptiveRunnable() {
+			@Override
+			public String getDescription() {
+				return "GitDaemonClient " + peer.toString();
+			}
+
+			@Override
+			public void run() {
+				try {
+					dc.execute(s);
+				} catch (ServiceNotEnabledException | ServiceNotAuthorizedException | IOException e) {
+					// Ignored. Client cannot use this repository.
+				} finally {
+					try {
+						s.getInputStream().close();
+					} catch (IOException e) {
+						// Ignore close exceptions
+					}
+					try {
+						s.getOutputStream().close();
+					} catch (IOException e) {
+						// Ignore close exceptions
+					}
+				}
+			}
+		});
+	}
+
+	synchronized DaemonService matchService(final String cmd) {
+		for (final DaemonService d : services) {
+			if (d.handles(cmd)) {
+				return d;
+			}
+		}
+		return null;
+	}
+
+	Repository openRepository(DaemonClient client, String name) throws ServiceMayNotContinueException {
+		// Assume any attempt to use \ was by a Windows client
+		// and correct to the more typical / used in Git URIs.
+		//
+		name = name.replace('\\', '/');
+
+		// git://thishost/path should always be name="/path" here
+		//
+		if (!name.startsWith("/")) {
+			return null;
+		}
+
+		try {
+			return repositoryResolver.open(client, name.substring(1));
+		} catch (RepositoryNotFoundException | ServiceNotAuthorizedException | ServiceNotEnabledException e) {
+			// null signals it "wasn't found", which is all that is suitable
+			// for the remote client to know.
+			return null;
+		}
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonClient.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonClient.java
new file mode 100644
index 0000000000..2178fe2a61
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonClient.java
@@ 0,0 +1,94 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.git;
+
+import java.io.BufferedInputStream;
+import java.io.BufferedOutputStream;
+import java.io.IOException;
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.net.InetAddress;
+import java.net.Socket;
+
+import org.eclipse.jgit.transport.PacketLineIn;
+import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
+import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
+
+public class DaemonClient {
+
+	private final Daemon daemon;
+
+	private InetAddress peer;
+
+	private InputStream rawIn;
+
+	private OutputStream rawOut;
+
+	DaemonClient(final Daemon d) {
+		daemon = d;
+	}
+
+	void setRemoteAddress(final InetAddress ia) {
+		peer = ia;
+	}
+
+	/**
+	 * @return the daemon which spawned this client.
+	 */
+	public Daemon getDaemon() {
+		return daemon;
+	}
+
+	/**
+	 * @return Internet address of the remote client.
+	 */
+	public InetAddress getRemoteAddress() {
+		return peer;
+	}
+
+	/**
+	 * @return input stream to read from the connected client.
+	 */
+	public InputStream getInputStream() {
+		return rawIn;
+	}
+
+	/**
+	 * @return output stream to send data to the connected client.
+	 */
+	public OutputStream getOutputStream() {
+		return rawOut;
+	}
+
+	void execute(final Socket sock) throws IOException, ServiceNotEnabledException, ServiceNotAuthorizedException {
+		rawIn = new BufferedInputStream(sock.getInputStream());
+		rawOut = new BufferedOutputStream(sock.getOutputStream());
+
+		if (0 < daemon.getTimeout()) {
+			sock.setSoTimeout(daemon.getTimeout() * 1000);
+		}
+		String cmd = new PacketLineIn(rawIn).readStringRaw();
+		final int nul = cmd.indexOf('\0');
+		if (nul >= 0) {
+			// Newer clients hide a "host" header behind this byte.
+			// Currently we don't use it for anything, so we ignore
+			// this portion of the command.
+			//
+			cmd = cmd.substring(0, nul);
+		}
+
+		final DaemonService srv = getDaemon().matchService(cmd);
+		if (srv == null) {
+			return;
+		}
+		sock.setSoTimeout(0);
+		srv.execute(this, cmd);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonService.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonService.java
new file mode 100644
index 0000000000..f447f7b710
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/DaemonService.java
@@ 0,0 +1,128 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.git;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.lib.Config;
+import org.eclipse.jgit.lib.Config.SectionParser;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.transport.PacketLineOut;
+import org.eclipse.jgit.transport.ServiceMayNotContinueException;
+import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
+import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
+
+public abstract class DaemonService {
+
+	private final String command;
+
+	private final SectionParser<ServiceConfig> configKey;
+
+	private boolean enabled;
+
+	private boolean overridable;
+
+	DaemonService(final String cmdName, final String cfgName) {
+		command = cmdName.startsWith("git") ? cmdName : "git" + cmdName;
+		configKey = cfg > new ServiceConfig(DaemonService.this, cfg, cfgName);
+		overridable = true;
+	}
+
+	private static class ServiceConfig {
+
+		final boolean enabled;
+
+		ServiceConfig(final DaemonService service, final Config cfg, final String name) {
+			enabled = cfg.getBoolean("daemon", name, service.isEnabled());
+		}
+	}
+
+	/**
+	 * @return is this service enabled for invocation?
+	 */
+	public boolean isEnabled() {
+		return enabled;
+	}
+
+	/**
+	 * @param on true to allow this service to be used; false to deny it.
+	 */
+	public void setEnabled(final boolean on) {
+		enabled = on;
+	}
+
+	/**
+	 * @return can this service be configured in the repository config file?
+	 */
+	public boolean isOverridable() {
+		return overridable;
+	}
+
+	/**
+	 * @param on true to permit repositories to override this service's enabled
+	 *           state with the <code>daemon.servicename</code> config setting.
+	 */
+	public void setOverridable(final boolean on) {
+		overridable = on;
+	}
+
+	/**
+	 * @return name of the command requested by clients.
+	 */
+	public String getCommandName() {
+		return command;
+	}
+
+	/**
+	 * Determine if this service can handle the requested command.
+	 * 
+	 * @param commandLine input line from the client.
+	 * @return true if this command can accept the given command line.
+	 */
+	public boolean handles(final String commandLine) {
+		return command.length() + 1 < commandLine.length() && commandLine.charAt(command.length()) == ' '
+				&& commandLine.startsWith(command);
+	}
+
+	void execute(final org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client, final String commandLine)
+			throws IOException, ServiceNotEnabledException, ServiceNotAuthorizedException {
+		final String name = commandLine.substring(command.length() + 1);
+		Repository db;
+		try {
+			db = client.getDaemon().openRepository(client, name);
+		} catch (ServiceMayNotContinueException e) {
+			// An error when opening the repo means the client is expecting a ref
+			// advertisement, so use that style of error.
+			PacketLineOut pktOut = new PacketLineOut(client.getOutputStream());
+			pktOut.writeString("ERR " + e.getMessage() + "\n");
+			db = null;
+		}
+		if (db == null) {
+			return;
+		}
+		try {
+			if (isEnabledFor(db)) {
+				execute(client, db);
+			}
+		} finally {
+			db.close();
+		}
+	}
+
+	private boolean isEnabledFor(final Repository db) {
+		if (isOverridable()) {
+			return db.getConfig().get(configKey).enabled;
+		}
+		return isEnabled();
+	}
+
+	abstract void execute(org.eclipse.jgit.niofs.internal.daemon.git.DaemonClient client, Repository db)
+			throws IOException, ServiceNotEnabledException, ServiceNotAuthorizedException;
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/KetchCustomReceivePack.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/KetchCustomReceivePack.java
new file mode 100644
index 0000000000..ca40f0a3e0
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/git/KetchCustomReceivePack.java
@@ 0,0 +1,80 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.git;
+
+import java.io.IOException;
+import java.text.MessageFormat;
+import java.util.List;
+import java.util.Map;
+import java.util.Set;
+
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
+import org.eclipse.jgit.lib.BatchRefUpdate;
+import org.eclipse.jgit.lib.NullProgressMonitor;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.RefDatabase;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.transport.ReceiveCommand;
+import org.eclipse.jgit.transport.ReceivePack;
+
+public class KetchCustomReceivePack extends ReceivePack {
+
+	public KetchCustomReceivePack(final Repository into) {
+		super(into);
+	}
+
+	@Override
+	public void setAdvertisedRefs(final Map<String, Ref> allRefs, final Set<ObjectId> additionalHaves) {
+		super.setAdvertisedRefs(allRefs, additionalHaves);
+		final Map<String, Ref> refs = getAdvertisedRefs();
+		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
+			final RefDatabase bootstrap = ((RefTreeDatabase) getRepository().getRefDatabase()).getBootstrap();
+			try {
+				for (Map.Entry<String, Ref> entry : bootstrap.getRefs("").entrySet()) {
+					refs.put(entry.getKey(), entry.getValue());
+				}
+			} catch (final IOException e) {
+				e.printStackTrace();
+			}
+		}
+	}
+
+	@Override
+	protected void executeCommands() {
+		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
+			List<ReceiveCommand> toApply = filterCommands(ReceiveCommand.Result.NOT_ATTEMPTED);
+			if (toApply.isEmpty()) {
+				return;
+			}
+			final BatchRefUpdate batch = ((RefTreeDatabase) getRepository().getRefDatabase()).getBootstrap()
+					.newBatchUpdate();
+			batch.setAllowNonFastForwards(true);
+			batch.setAtomic(false);
+			batch.setRefLogIdent(getRefLogIdent());
+			batch.setRefLogMessage("push", true); // $NONNLS1$
+			batch.addCommand(toApply);
+			try {
+				batch.setPushCertificate(getPushCertificate());
+				batch.execute(getRevWalk(), NullProgressMonitor.INSTANCE);
+			} catch (IOException err) {
+				for (ReceiveCommand cmd : toApply) {
+					if (cmd.getResult() == ReceiveCommand.Result.NOT_ATTEMPTED) {
+						cmd.setResult(ReceiveCommand.Result.REJECTED_OTHER_REASON,
+								MessageFormat.format(JGitText.get().lockError, err.getMessage()));
+					}
+				}
+			}
+		} else {
+			super.executeCommands();
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupport.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupport.java
new file mode 100644
index 0000000000..97ee591add
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/http/HTTPSupport.java
@@ 0,0 +1,72 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.http;
+
+import javax.servlet.ServletContext;
+import javax.servlet.ServletContextEvent;
+import javax.servlet.ServletContextListener;
+import javax.servlet.ServletRegistration;
+import javax.servlet.annotation.WebListener;
+
+import org.eclipse.jgit.http.server.GitServlet;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+@WebListener
+public class HTTPSupport implements ServletContextListener {
+
+	private static final String GIT_PATH = "git";
+	private static final Logger LOG = LoggerFactory.getLogger(HTTPSupport.class);
+
+	private ServletContext servletContext = null;
+
+	@Override
+	public void contextInitialized(ServletContextEvent sce) {
+		servletContext = sce.getServletContext();
+		final JGitFileSystemProvider fsProvider = resolveProvider();
+		if (fsProvider != null && (fsProvider.getConfig().isHttpEnabled() || fsProvider.getConfig().isHttpsEnabled())) {
+			if (fsProvider.getConfig().isHttpEnabled()) {
+				fsProvider.addHostName("http", fsProvider.getConfig().getHttpHostName() + ":"
+						+ fsProvider.getConfig().getHttpPort() + servletContext.getContextPath() + "/" + GIT_PATH);
+			}
+			if (fsProvider.getConfig().isHttpsEnabled()) {
+				fsProvider.addHostName("https", fsProvider.getConfig().getHttpsHostName() + ":"
+						+ fsProvider.getConfig().getHttpsPort() + servletContext.getContextPath() + "/" + GIT_PATH);
+			}
+			final GitServlet gitServlet = new GitServlet();
+			gitServlet.setRepositoryResolver(fsProvider.getRepositoryResolver());
+			gitServlet.setAsIsFileService(null);
+			gitServlet.setReceivePackFactory((req, db) > fsProvider.getReceivePack("http", req, db));
+			ServletRegistration.Dynamic sd = servletContext.addServlet("GitServlet", gitServlet);
+			sd.addMapping("/" + GIT_PATH + "/*");
+			sd.setLoadOnStartup(1);
+			sd.setAsyncSupported(false);
+		}
+	}
+
+	@Override
+	public void contextDestroyed(ServletContextEvent sce) {
+		servletContext = null;
+	}
+
+	protected JGitFileSystemProvider resolveProvider() {
+		try {
+			// TODO: change this
+			return null;
+			// return (JGitFileSystemProvider)
+			// FileSystemProviders.resolveProvider(URI.create(JGitFileSystemProviderConfiguration.SCHEME
+			// + "://whatever"));
+		} catch (Exception ex) {
+			LOG.error("Error trying to resolve JGitFileSystemProvider.", ex);
+		}
+		return null;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/BaseGitCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/BaseGitCommand.java
new file mode 100644
index 0000000000..95dbd02a8f
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/BaseGitCommand.java
@@ 0,0 +1,167 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.ssh;
+
+import java.io.IOException;
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.util.concurrent.ExecutorService;
+
+import org.apache.sshd.common.channel.ChannelOutputStream;
+import org.apache.sshd.common.session.Session;
+import org.apache.sshd.server.command.Command;
+import org.apache.sshd.server.Environment;
+import org.apache.sshd.server.ExitCallback;
+import org.apache.sshd.server.SessionAware;
+import org.apache.sshd.server.session.ServerSession;
+import org.eclipse.jgit.errors.RepositoryNotFoundException;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.niofs.internal.util.DescriptiveRunnable;
+import org.eclipse.jgit.transport.ServiceMayNotContinueException;
+import org.eclipse.jgit.transport.resolver.RepositoryResolver;
+import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
+import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
+
+public abstract class BaseGitCommand implements Command, SessionAware, Runnable {
+
+	public final static Session.AttributeKey<User> SUBJECT_KEY = new Session.AttributeKey<User>();
+
+	protected final String command;
+	protected final String repositoryName;
+	protected final RepositoryResolver repositoryResolver;
+	private final ExecutorService executorService;
+
+	private InputStream in;
+	private OutputStream out;
+	private OutputStream err;
+	private ExitCallback callback;
+	private User user;
+
+	public BaseGitCommand(final String command, final JGitFileSystemProvider.RepositoryResolverImpl repositoryResolver,
+			final ExecutorService executorService) {
+		this.command = command;
+		this.repositoryName = buildRepositoryName(command);
+		this.repositoryResolver = repositoryResolver;
+		this.executorService = executorService;
+	}
+
+	private String buildRepositoryName(String command) {
+		int start = getCommandName().length() + 2;
+		final String temp = command.substring(start);
+		return temp.substring(0, temp.indexOf("'"));
+	}
+
+	protected abstract String getCommandName();
+
+	@Override
+	public void setInputStream(InputStream in) {
+		this.in = in;
+	}
+
+	@Override
+	public void setOutputStream(OutputStream out) {
+		this.out = out;
+		if (out instanceof ChannelOutputStream) {
+			((ChannelOutputStream) out).setNoDelay(true);
+		}
+	}
+
+	@Override
+	public void setErrorStream(OutputStream err) {
+		this.err = err;
+		if (err instanceof ChannelOutputStream) {
+			((ChannelOutputStream) err).setNoDelay(true);
+		}
+	}
+
+	@Override
+	public void setExitCallback(ExitCallback callback) {
+		this.callback = callback;
+	}
+
+	@Override
+	public void start(final Environment env) throws IOException {
+		executorService.execute(new DescriptiveRunnable() {
+			@Override
+			public String getDescription() {
+				return "Git Command [" + getClass().getName() + "]";
+			}
+
+			@Override
+			public void run() {
+				BaseGitCommand.this.run();
+			}
+		});
+	}
+
+	@Override
+	public void run() {
+		try {
+			final Repository repository = openRepository(repositoryName);
+			execute(repository, in, out, err);
+		} catch (final Throwable e) {
+			try {
+				err.write(e.getMessage().getBytes());
+			} catch (IOException ignored) {
+			}
+		}
+		if (callback != null) {
+			callback.onExit(0);
+		}
+	}
+
+	private Repository openRepository(String name) throws ServiceMayNotContinueException {
+		// Assume any attempt to use \ was by a Windows client
+		// and correct to the more typical / used in Git URIs.
+		//
+		name = name.replace('\\', '/');
+
+		// git://thishost/path should always be name="/path" here
+		//
+		if (!name.startsWith("/")) {
+			return null;
+		}
+
+		try {
+			return repositoryResolver.open(this, name.substring(1));
+		} catch (RepositoryNotFoundException e) {
+			// null signals it "wasn't found", which is all that is suitable
+			// for the remote client to know.
+			return null;
+		} catch (ServiceNotAuthorizedException e) {
+			// null signals it "wasn't found", which is all that is suitable
+			// for the remote client to know.
+			return null;
+		} catch (ServiceNotEnabledException e) {
+			// null signals it "wasn't found", which is all that is suitable
+			// for the remote client to know.
+			return null;
+		}
+	}
+
+	protected abstract void execute(final Repository repository, final InputStream in, final OutputStream out,
+			final OutputStream err);
+
+	@Override
+	public void destroy() {
+	}
+
+	public User getUser() {
+		return user;
+	}
+
+	@Override
+	public void setSession(final ServerSession session) {
+		this.user = session.getAttribute(BaseGitCommand.SUBJECT_KEY);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitReceiveCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitReceiveCommand.java
new file mode 100644
index 0000000000..e744e3ded9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitReceiveCommand.java
@@ 0,0 +1,50 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.ssh;
+
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.util.concurrent.ExecutorService;
+
+import org.eclipse.jgit.api.Git;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.transport.ReceivePack;
+import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
+
+public class GitReceiveCommand extends BaseGitCommand {
+
+	private final ReceivePackFactory<BaseGitCommand> receivePackFactory;
+
+	public GitReceiveCommand(final String command,
+			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
+			final ReceivePackFactory<BaseGitCommand> receivePackFactory, final ExecutorService executorService) {
+		super(command, repositoryResolver, executorService);
+		this.receivePackFactory = receivePackFactory;
+	}
+
+	@Override
+	protected String getCommandName() {
+		return "gitreceivepack";
+	}
+
+	@Override
+	protected void execute(final Repository repository, final InputStream in, final OutputStream out,
+			final OutputStream err) {
+		try {
+			final ReceivePack rp = receivePackFactory.create(this, repository);
+			rp.receive(in, out, err);
+			rp.setPostReceiveHook((rp1, commands) > {
+				new Git(repository).gc();
+			});
+		} catch (final Exception ignored) {
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHService.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHService.java
new file mode 100644
index 0000000000..7d73c6250f
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitSSHService.java
@@ 0,0 +1,247 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.ssh;
+
+import static org.apache.sshd.common.NamedFactory.setUpBuiltinFactories;
+import static org.apache.sshd.server.ServerBuilder.builder;
+import static org.eclipse.jgit.niofs.internal.daemon.common.PortUtil.validateOrGetNew;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.io.IOException;
+import java.net.InetSocketAddress;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.Collections;
+import java.util.List;
+import java.util.Map;
+import java.util.concurrent.ExecutorService;
+
+import org.apache.sshd.common.cipher.BuiltinCiphers;
+import org.apache.sshd.common.mac.BuiltinMacs;
+import org.apache.sshd.common.util.security.SecurityUtils;
+import org.apache.sshd.server.SshServer;
+import org.apache.sshd.server.auth.pubkey.CachingPublicKeyAuthenticator;
+import org.apache.sshd.server.keyprovider.AbstractGeneratorHostKeyProvider;
+import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
+import org.apache.sshd.server.shell.UnknownCommand;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
+import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;
+import org.eclipse.jgit.niofs.internal.security.User;
+import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
+import org.eclipse.jgit.transport.resolver.UploadPackFactory;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class GitSSHService {
+
+	private static final Logger LOG = LoggerFactory.getLogger(GitSSHService.class);
+
+	private final List<BuiltinCiphers> managedCiphers = Collections.unmodifiableList(Arrays.asList(
+			BuiltinCiphers.aes128ctr, BuiltinCiphers.aes192ctr, BuiltinCiphers.aes256ctr, BuiltinCiphers.arcfour256,
+			BuiltinCiphers.arcfour128, BuiltinCiphers.aes192cbc, BuiltinCiphers.aes256cbc));
+
+	private final List<BuiltinMacs> managedMACs = Collections
+			.unmodifiableList(Arrays.asList(BuiltinMacs.hmacmd5, BuiltinMacs.hmacsha1, BuiltinMacs.hmacsha256,
+					BuiltinMacs.hmacsha512, BuiltinMacs.hmacsha196, BuiltinMacs.hmacmd596));
+
+	private SshServer sshd;
+	private AuthenticationService authenticationService;
+	private PublicKeyAuthenticator publicKeyAuthenticator;
+
+	private SshServer buildSshServer(String ciphersConfigured, String macsConfigured) {
+
+		return builder().cipherFactories(setUpBuiltinFactories(false, checkAndSetGitCiphers(ciphersConfigured)))
+				.macFactories(setUpBuiltinFactories(false, checkAndSetGitMacs(macsConfigured))).build();
+	}
+
+	public void setup(final File certDir, final InetSocketAddress inetSocketAddress, final String sshIdleTimeout,
+			final String algorithm, final ReceivePackFactory receivePackFactory,
+			final UploadPackFactory uploadPackFactory,
+			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
+			final ExecutorService executorService) {
+		setup(certDir, inetSocketAddress, sshIdleTimeout, algorithm, receivePackFactory, uploadPackFactory,
+				repositoryResolver, executorService, null, null);
+	}
+
+	public void setup(final File certDir, final InetSocketAddress inetSocketAddress, final String sshIdleTimeout,
+			final String algorithm, final ReceivePackFactory receivePackFactory,
+			final UploadPackFactory uploadPackFactory,
+			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
+			final ExecutorService executorService, final String gitSshCiphers, final String gitSshMacs) {
+		checkNotNull("certDir", certDir);
+		checkNotEmpty("sshIdleTimeout", sshIdleTimeout);
+		checkNotEmpty("algorithm", algorithm);
+		checkNotNull("receivePackFactory", receivePackFactory);
+		checkNotNull("uploadPackFactory", uploadPackFactory);
+		checkNotNull("repositoryResolver", repositoryResolver);
+
+		buildSSHServer(gitSshCiphers, gitSshMacs);
+
+		sshd.getProperties().put(SshServer.IDLE_TIMEOUT, sshIdleTimeout);
+
+		if (inetSocketAddress != null) {
+			sshd.setHost(inetSocketAddress.getHostName());
+			sshd.setPort(validateOrGetNew(inetSocketAddress.getPort()));
+
+			if (inetSocketAddress.getPort() != sshd.getPort()) {
+				LOG.error("SSH for Git original port {} not available, new free port {} assigned.",
+						inetSocketAddress.getPort(), sshd.getPort());
+			}
+		}
+
+		if (!certDir.exists()) {
+			certDir.mkdirs();
+		}
+
+		final AbstractGeneratorHostKeyProvider keyPairProvider = new SimpleGeneratorHostKeyProvider(
+				new File(certDir, "hostkey.ser").toPath());
+
+		try {
+			SecurityUtils.getKeyPairGenerator(algorithm);
+			keyPairProvider.setAlgorithm(algorithm);
+		} catch (final Exception ignore) {
+			throw new RuntimeException(String.format("Can't use '%s' algorithm for ssh key pair generator.", algorithm),
+					ignore);
+		}
+
+		sshd.setKeyPairProvider(keyPairProvider);
+		sshd.setCommandFactory(command > {
+			if (command.startsWith("gituploadpack")) {
+				return new GitUploadCommand(command, repositoryResolver, uploadPackFactory, executorService);
+			} else if (command.startsWith("gitreceivepack")) {
+				return new GitReceiveCommand(command, repositoryResolver, receivePackFactory, executorService);
+			} else {
+				return new UnknownCommand(command);
+			}
+		});
+		sshd.setPublickeyAuthenticator(new CachingPublicKeyAuthenticator((username, key, session) > {
+
+			final User user = getPublicKeyAuthenticator().authenticate(username, key);
+
+			if (user == null) {
+				return false;
+			}
+
+			session.setAttribute(BaseGitCommand.SUBJECT_KEY, user);
+
+			return true;
+		}));
+		sshd.setPasswordAuthenticator((username, password, session) > {
+
+			final User user = getUserPassAuthenticator().login(username, password);
+
+			if (user == null) {
+				return false;
+			}
+
+			session.setAttribute(BaseGitCommand.SUBJECT_KEY, user);
+			return true;
+		});
+	}
+
+	private void buildSSHServer(String gitSshCiphers, String gitSshMacs) {
+
+		sshd = buildSshServer(gitSshCiphers, gitSshMacs);
+	}
+
+	private List<BuiltinCiphers> checkAndSetGitCiphers(String gitSshCiphers) {
+		if (gitSshCiphers == null || gitSshCiphers.isEmpty()) {
+			return managedCiphers;
+		} else {
+			List<BuiltinCiphers> ciphersHandled = new ArrayList<>();
+			List<String> ciphers = Arrays.asList(gitSshCiphers.split(","));
+			for (String cipherCode : ciphers) {
+				BuiltinCiphers cipher = BuiltinCiphers.fromFactoryName(cipherCode.trim().toLowerCase());
+				if (cipher != null && managedCiphers.contains(cipher)) {
+					ciphersHandled.add(cipher);
+					LOG.info("Added Cipher {} to the git ssh configuration. ", cipher);
+				} else {
+					LOG.warn("Cipher {} not handled in git ssh configuration. ", cipher);
+				}
+			}
+			return ciphersHandled;
+		}
+	}
+
+	private List<BuiltinMacs> checkAndSetGitMacs(String gitSshMacs) {
+
+		if (gitSshMacs == null || gitSshMacs.isEmpty()) {
+			return managedMACs;
+		} else {
+
+			List<BuiltinMacs> macs = new ArrayList<>();
+			List<String> macsInput = Arrays.asList(gitSshMacs.split(","));
+			for (String macCode : macsInput) {
+				BuiltinMacs mac = BuiltinMacs.fromFactoryName(macCode.trim().toLowerCase());
+				if (mac != null && managedMACs.contains(mac)) {
+					macs.add(mac);
+					LOG.info("Added MAC {} to the git ssh configuration. ", mac);
+				} else {
+					LOG.warn("MAC {} not handled in git ssh configuration. ", mac);
+				}
+			}
+			return macs;
+		}
+	}
+
+	public void stop() {
+		try {
+			sshd.stop(true);
+		} catch (IOException ignored) {
+		}
+	}
+
+	public void start() {
+		try {
+			sshd.start();
+		} catch (IOException e) {
+			throw new RuntimeException("Couldn't start SSH daemon at " + sshd.getHost() + ":" + sshd.getPort(), e);
+		}
+	}
+
+	public boolean isRunning() {
+		return !(sshd.isClosed() || sshd.isClosing());
+	}
+
+	SshServer getSshServer() {
+		return sshd;
+	}
+
+	public Map<String, Object> getProperties() {
+		return Collections.unmodifiableMap(sshd.getProperties());
+	}
+
+	public AuthenticationService getUserPassAuthenticator() {
+		return authenticationService;
+	}
+
+	public void setUserPassAuthenticator(AuthenticationService authenticationService) {
+		this.authenticationService = authenticationService;
+	}
+
+	public PublicKeyAuthenticator getPublicKeyAuthenticator() {
+		return publicKeyAuthenticator;
+	}
+
+	public void setPublicKeyAuthenticator(PublicKeyAuthenticator publicKeyAuthenticator) {
+		this.publicKeyAuthenticator = publicKeyAuthenticator;
+	}
+
+	public List<BuiltinCiphers> getManagedCiphers() {
+		return managedCiphers;
+	}
+
+	public List<BuiltinMacs> getManagedMACs() {
+		return managedMACs;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitUploadCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitUploadCommand.java
new file mode 100644
index 0000000000..c7525bff06
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/daemon/ssh/GitUploadCommand.java
@@ 0,0 +1,59 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.daemon.ssh;
+
+import java.io.InputStream;
+import java.io.OutputStream;
+import java.util.concurrent.ExecutorService;
+import java.util.zip.Deflater;
+
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.daemon.filter.HiddenBranchRefFilter;
+import org.eclipse.jgit.storage.pack.PackConfig;
+import org.eclipse.jgit.transport.RefFilter;
+import org.eclipse.jgit.transport.UploadPack;
+import org.eclipse.jgit.transport.resolver.UploadPackFactory;
+
+public class GitUploadCommand extends BaseGitCommand {
+
+	private UploadPackFactory<BaseGitCommand> uploadPackFactory;
+
+	public GitUploadCommand(final String command,
+			final JGitFileSystemProvider.RepositoryResolverImpl<BaseGitCommand> repositoryResolver,
+			final UploadPackFactory uploadPackFactory, final ExecutorService executorService) {
+		super(command, repositoryResolver, executorService);
+		this.uploadPackFactory = uploadPackFactory;
+	}
+
+	@Override
+	protected String getCommandName() {
+		return "gituploadpack";
+	}
+
+	@Override
+	protected void execute(final Repository repository, final InputStream in, final OutputStream out,
+			final OutputStream err) {
+		try {
+			final UploadPack up = uploadPackFactory.create(this, repository);
+
+			final PackConfig config = new PackConfig(repository);
+			config.setCompressionLevel(Deflater.BEST_COMPRESSION);
+			up.setPackConfig(config);
+
+			if (up.getRefFilter() == RefFilter.DEFAULT) {
+				up.setRefFilter(new HiddenBranchRefFilter());
+			}
+
+			up.upload(in, out, err);
+		} catch (final Exception ignored) {
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHookExecutionContext.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHookExecutionContext.java
new file mode 100644
index 0000000000..0b72f15c58
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHookExecutionContext.java
@@ 0,0 +1,56 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.hook;
+
+import java.util.HashMap;
+import java.util.Map;
+
+/**
+ * Execution context for a {@link FileSystemHooks.FileSystemHook}. It contains
+ * the relevant information for the Hook execution
+ */
+public class FileSystemHookExecutionContext {
+
+	private String fsName;
+
+	private Map<String, Object> params = new HashMap<>();
+
+	public FileSystemHookExecutionContext(String fsName) {
+		this.fsName = fsName;
+	}
+
+	/**
+	 * Returns the fileSystem name that this hook executes
+	 */
+	public String getFsName() {
+		return fsName;
+	}
+
+	/**
+	 * Adds a param to the context
+	 * 
+	 * @param name  the name of the param
+	 * @param value
+	 */
+	public void addParam(String name, Object value) {
+		params.put(name, value);
+	}
+
+	/**
+	 * Gets a param value
+	 * 
+	 * @param name the name of the param
+	 * @return the param value
+	 */
+	public Object getParamValue(String name) {
+		return params.get(name);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooks.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooks.java
new file mode 100644
index 0000000000..2a31a28896
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooks.java
@@ 0,0 +1,26 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.hook;
+
+public enum FileSystemHooks {
+
+	ExternalUpdate, PostCommit, BranchAccessCheck, BranchAccessFilter;
+
+	@FunctionalInterface
+	public interface FileSystemHook {
+
+		/**
+		 * Performs this operation in a FileSystemHooks
+		 * 
+		 * @param context execution context
+		 */
+		void execute(FileSystemHookExecutionContext context);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooksConstants.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooksConstants.java
new file mode 100644
index 0000000000..60846a2816
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/FileSystemHooksConstants.java
@@ 0,0 +1,22 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.hook;
+
+public interface FileSystemHooksConstants {
+
+	String POST_COMMIT_EXIT_CODE = "POST_COMMIT_EXIT_CODE";
+
+	String RECEIVE_COMMAND = "RECEIVE_COMMAND";
+
+	String UPLOAD_PACK = "UPLOAD_PACK";
+
+	String USER = "USER";
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/JGitFSHooks.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/JGitFSHooks.java
new file mode 100644
index 0000000000..dfcba5f0d3
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/hook/JGitFSHooks.java
@@ 0,0 +1,44 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.hook;
+
+import java.util.List;
+
+import org.eclipse.jgit.niofs.internal.JGitFileSystemImpl;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class JGitFSHooks {
+
+	private static final Logger LOGGER = LoggerFactory.getLogger(JGitFileSystemImpl.class);
+
+	public static void executeFSHooks(Object fsHook, FileSystemHooks hookType, FileSystemHookExecutionContext ctx) {
+		if (fsHook == null) {
+			return;
+		}
+		if (fsHook instanceof List) {
+			List hooks = (List) fsHook;
+			hooks.forEach(h > executeHook(h, hookType, ctx));
+		} else {
+			executeHook(fsHook, hookType, ctx);
+		}
+	}
+
+	private static void executeHook(Object hook, FileSystemHooks hookType, FileSystemHookExecutionContext ctx) {
+		if (hook instanceof FileSystemHooks.FileSystemHook) {
+			FileSystemHooks.FileSystemHook fsHook = (FileSystemHooks.FileSystemHook) hook;
+			fsHook.execute(ctx);
+		} else {
+			LOGGER.error("Error executing FS Hook FS " + hookType + " on " + ctx.getFsName()
+					+ ". Callback methods should implement FileSystemHooks.FileSystemHook. ");
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCache.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCache.java
new file mode 100644
index 0000000000..9c1fd3dd2e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCache.java
@@ 0,0 +1,103 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.util.Collection;
+import java.util.Map;
+import java.util.Set;
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.function.Supplier;
+
+import org.eclipse.jgit.niofs.internal.JGitFileSystem;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProxy;
+
+public class JGitFileSystemsCache {
+
+	// supplier for creation of new fs
+	final Map<String, Supplier<JGitFileSystem>> fileSystemsSuppliers = new ConcurrentHashMap<>();
+
+	// limited amount of real instances of FS
+	final Map<String, Supplier<JGitFileSystem>> memoizedSuppliers;
+
+	public JGitFileSystemsCache(JGitFileSystemProviderConfiguration config) {
+
+		memoizedSuppliers = JGitFileSystemsCacheDataStructure.create(config);
+	}
+
+	public void addSupplier(String fsKey, Supplier<JGitFileSystem> createFSSupplier) {
+		checkNotNull("fsKey", fsKey);
+		checkNotNull("fsSupplier", createFSSupplier);
+
+		fileSystemsSuppliers.putIfAbsent(fsKey, createFSSupplier);
+
+		createMemoizedSupplier(fsKey, createFSSupplier);
+	}
+
+	public void remove(String fsName) {
+		fileSystemsSuppliers.remove(fsName);
+		memoizedSuppliers.remove(fsName);
+	}
+
+	public JGitFileSystem get(String fsName) {
+
+		Supplier<JGitFileSystem> memoizedSupplier = memoizedSuppliers.get(fsName);
+		if (memoizedSupplier != null) {
+			return new JGitFileSystemProxy(fsName, memoizedSupplier);
+		} else if (fileSystemsSuppliers.get(fsName) != null) {
+			Supplier<JGitFileSystem> newMemoizedSupplier = createMemoizedSupplier(fsName,
+					fileSystemsSuppliers.get(fsName));
+			return new JGitFileSystemProxy(fsName, newMemoizedSupplier);
+		}
+		return null;
+	}
+
+	private Supplier<JGitFileSystem> createMemoizedSupplier(String fsKey, Supplier<JGitFileSystem> createFSSupplier) {
+		Supplier<JGitFileSystem> memoizedFSSupplier = MemoizedFileSystemsSupplier.of(createFSSupplier);
+		memoizedSuppliers.putIfAbsent(fsKey, memoizedFSSupplier);
+		return memoizedFSSupplier;
+	}
+
+	public void clear() {
+		memoizedSuppliers.clear();
+		fileSystemsSuppliers.clear();
+	}
+
+	public boolean containsKey(String fsName) {
+		return fileSystemsSuppliers.containsKey(fsName);
+	}
+
+	public Collection<String> getFileSystems() {
+		return fileSystemsSuppliers.keySet();
+	}
+
+	public JGitFileSystemsCacheInfo getCacheInfo() {
+		return new JGitFileSystemsCacheInfo();
+	}
+
+	public class JGitFileSystemsCacheInfo {
+
+		public int fileSystemsCacheSize() {
+			return memoizedSuppliers.size();
+		}
+
+		public Set<String> memoizedFileSystemsCacheKeys() {
+			return memoizedSuppliers.keySet();
+		}
+
+		@Override
+		public String toString() {
+			return "JGitFileSystemsCacheInfo{fileSystemsCacheSize[" + fileSystemsCacheSize()
+					+ "], memoizedFileSystemsCacheKeys[" + memoizedFileSystemsCacheKeys() + "]}";
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheDataStructure.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheDataStructure.java
new file mode 100644
index 0000000000..99e9041f5e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsCacheDataStructure.java
@@ 0,0 +1,77 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.LinkedHashMap;
+import java.util.List;
+import java.util.Map;
+import java.util.function.Supplier;
+
+import org.eclipse.jgit.niofs.internal.JGitFileSystem;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+
+public class JGitFileSystemsCacheDataStructure {
+
+	public static Map<String, Supplier<JGitFileSystem>> create(JGitFileSystemProviderConfiguration config) {
+
+		return Collections.synchronizedMap(new LinkedHashMap<String, Supplier<JGitFileSystem>>(
+				config.getJgitFileSystemsInstancesCache() + 1, 0.75f, true) {
+
+			// prevent infinite loop if all FS instances are in use
+			private Integer removeEldestEntryIterations = 0;
+
+			@Override
+			public Supplier<JGitFileSystem> putIfAbsent(String key, Supplier<JGitFileSystem> value) {
+				Supplier<JGitFileSystem> jGitFileSystemSupplier = super.putIfAbsent(key, value);
+				if (size() > config.getJgitFileSystemsInstancesCache()) {
+					fitListToCacheSize();
+				}
+				return jGitFileSystemSupplier;
+			}
+
+			private void fitListToCacheSize() {
+				List<String> itemsToRemove = new ArrayList<>();
+				int maxIterations = config.getJgitCacheOverflowCleanupSize();
+				Object[] entries = this.entrySet().toArray();
+				for (int i = this.size()  1; (i >= 0 && (this.size()  i < maxIterations)); i) {
+					Map.Entry<String, Supplier<JGitFileSystem>> entry = (Map.Entry) entries[i];
+					JGitFileSystem targetFS = (JGitFileSystem) ((MemoizedFileSystemsSupplier) entry.getValue()).get();
+					if (!targetFS.hasBeenInUse()) {
+						itemsToRemove.add(entry.getKey());
+					}
+				}
+				itemsToRemove.forEach(item > this.remove(item));
+			}
+
+			@Override
+			public boolean removeEldestEntry(Map.Entry eldest) {
+				if (removeEldestEntryIterations > config.getJgitRemoveEldestEntryIterations()) {
+					removeEldestEntryIterations = 0;
+					return false;
+				}
+				if (size() > config.getJgitFileSystemsInstancesCache()) {
+					JGitFileSystem targetFS = (JGitFileSystem) ((MemoizedFileSystemsSupplier) eldest.getValue()).get();
+					if (targetFS.hasBeenInUse()) {
+						removeEldestEntryIterations++;
+						this.remove(eldest.getKey());
+						this.put((String) eldest.getKey(), (MemoizedFileSystemsSupplier) eldest.getValue());
+						return false;
+					} else {
+						return true;
+					}
+				}
+				return false;
+			}
+		});
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManager.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManager.java
new file mode 100644
index 0000000000..aefe9c1d47
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/JGitFileSystemsManager.java
@@ 0,0 +1,173 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.util.ArrayList;
+import java.util.HashSet;
+import java.util.List;
+import java.util.Map;
+import java.util.Set;
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.function.Supplier;
+import java.util.stream.Collectors;
+
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystem;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemImpl;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemLock;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemsEventsManager;
+import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.transport.CredentialsProvider;
+
+import static org.eclipse.jgit.lib.Constants.DOT_GIT_EXT;
+
+public class JGitFileSystemsManager {
+
+	private final Set<String> closedFileSystems = new HashSet<>();
+
+	private final Set<String> fileSystemsRoot = new HashSet<>();
+
+	private final JGitFileSystemProvider jGitFileSystemProvider;
+
+	private final JGitFileSystemsCache fsCache;
+
+	private final JGitFileSystemProviderConfiguration config;
+
+	private final Map<String, JGitFileSystemLock> fileSystemsLocks = new ConcurrentHashMap<>();
+
+	public JGitFileSystemsManager(final JGitFileSystemProvider jGitFileSystemProvider,
+			final JGitFileSystemProviderConfiguration config) {
+		this.jGitFileSystemProvider = jGitFileSystemProvider;
+		this.config = config;
+		this.fsCache = new JGitFileSystemsCache(config);
+	}
+
+	public void newFileSystem(Supplier<Map<String, String>> fullHostNames, Supplier<Git> git, Supplier<String> fsName,
+			Supplier<CredentialsProvider> credential, Supplier<JGitFileSystemsEventsManager> fsManager,
+			Supplier<Map<FileSystemHooks, ?>> fsHooks) {
+
+		Supplier<JGitFileSystem> fsSupplier = createFileSystemSupplier(fullHostNames, git, fsName, credential,
+				fsManager, fsHooks);
+
+		fsCache.addSupplier(fsName.get(), fsSupplier);
+		fileSystemsRoot.addAll(parseFSRoots(fsName.get()));
+	}
+
+	List<String> parseFSRoots(String fsKey) {
+		List<String> roots = new ArrayList<>();
+		fsKey = cleanupFsName(fsKey);
+		int index = fsKey.indexOf("/");
+		while (index >= 0) {
+			roots.add(fsKey.substring(0, index));
+			index = fsKey.indexOf("/", index + 1);
+		}
+		roots.add(fsKey);
+		return roots;
+	}
+
+	private String cleanupFsName(String fsKey) {
+		if (fsKey.startsWith("/")) {
+			fsKey = fsKey.substring(1);
+		}
+		if (fsKey.endsWith("/")) {
+			fsKey = fsKey.substring(0, fsKey.length()  1);
+		}
+
+		return fsKey;
+	}
+
+	private Supplier<JGitFileSystem> createFileSystemSupplier(Supplier<Map<String, String>> fullHostNames,
+			Supplier<Git> git, Supplier<String> fsName, Supplier<CredentialsProvider> credential,
+			Supplier<JGitFileSystemsEventsManager> fsManager, Supplier<Map<FileSystemHooks, ?>> fsHooks) {
+
+		return () > newFileSystem(fullHostNames.get(), git.get(), fsName.get(), credential.get(), fsManager.get(),
+				fsHooks.get());
+	}
+
+	private JGitFileSystem newFileSystem(Map<String, String> fullHostNames, Git git, String fsName,
+			CredentialsProvider credential, JGitFileSystemsEventsManager fsEventsManager,
+			Map<FileSystemHooks, ?> fsHooks) {
+		fileSystemsLocks.putIfAbsent(fsName, createLock(git));
+		final JGitFileSystem fs = new JGitFileSystemImpl(jGitFileSystemProvider, fullHostNames, git,
+				fileSystemsLocks.get(fsName), fsName, credential, fsEventsManager, fsHooks);
+
+		fs.getGit().gc();
+
+		return fs;
+	}
+
+	JGitFileSystemLock createLock(Git git) {
+		return new JGitFileSystemLock(git, config.getDefaultJgitCacheEvictThresholdTimeUnit(),
+				config.getJgitCacheEvictThresholdDuration());
+	}
+
+	public void remove(String realFSKey) {
+		fsCache.remove(realFSKey);
+		fileSystemsRoot.remove(realFSKey);
+		closedFileSystems.remove(realFSKey);
+	}
+
+	public JGitFileSystem get(String fsName) {
+		return fsCache.get(fsName);
+	}
+
+	public void clear() {
+		fsCache.clear();
+		closedFileSystems.clear();
+		fileSystemsRoot.clear();
+	}
+
+	public boolean containsKey(String fsName) {
+
+		return fsCache.getFileSystems().contains(fsName) && !closedFileSystems.contains(fsName);
+	}
+
+	public boolean containsRoot(String fsName) {
+		return fileSystemsRoot.contains(fsName);
+	}
+
+	public void addClosedFileSystems(JGitFileSystem fileSystem) {
+		String realFSKey = fileSystem.getName();
+		closedFileSystems.add(realFSKey);
+		fileSystemsRoot.remove(fileSystem.getName());
+	}
+
+	public boolean allTheFSAreClosed() {
+		return closedFileSystems.size() == fsCache.getFileSystems().size();
+	}
+
+	public JGitFileSystem get(Repository db) {
+		String key = extractFSNameFromRepo(db);
+		return fsCache.get(key);
+	}
+
+	public Set<JGitFileSystem> getOpenFileSystems() {
+		return fsCache.getFileSystems().stream().filter(fsName > !closedFileSystems.contains(fsName))
+				.map(fsName > get(fsName)).collect(Collectors.toSet());
+	}
+
+	public JGitFileSystemsCache getFsCache() {
+		return fsCache;
+	}
+
+	private String extractFSNameFromRepo(Repository db) {
+		final String fullRepoName = config.getGitReposParentDir().toPath().relativize(db.getDirectory().toPath())
+				.toString();
+		return fullRepoName.substring(0, fullRepoName.indexOf(DOT_GIT_EXT)).replace('\\', '/');
+	}
+
+	Set<String> getClosedFileSystems() {
+		return closedFileSystems;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplier.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplier.java
new file mode 100644
index 0000000000..749ae8133c
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/manager/MemoizedFileSystemsSupplier.java
@@ 0,0 +1,35 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.manager;
+
+import java.util.concurrent.ConcurrentHashMap;
+import java.util.concurrent.ConcurrentMap;
+import java.util.function.Supplier;
+
+public class MemoizedFileSystemsSupplier<T> implements Supplier<T> {
+
+	final Supplier<T> delegate;
+	ConcurrentMap<Class<?>, T> map = new ConcurrentHashMap<>(1);
+
+	private MemoizedFileSystemsSupplier(Supplier<T> delegate) {
+		this.delegate = delegate;
+	}
+
+	@Override
+	public T get() {
+
+		T t = this.map.computeIfAbsent(MemoizedFileSystemsSupplier.class, k > this.delegate.get());
+		return t;
+	}
+
+	public static <T> Supplier<T> of(Supplier<T> provider) {
+		return new MemoizedFileSystemsSupplier<>(provider);
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/Git.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/Git.java
new file mode 100644
index 0000000000..344218b46e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/Git.java
@@ 0,0 +1,219 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op;
+
+import java.io.File;
+import java.io.IOException;
+import java.io.InputStream;
+import java.net.URISyntaxException;
+import java.nio.file.NoSuchFileException;
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.api.errors.InvalidRemoteException;
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.internal.ketch.KetchLeader;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.JGitPathImpl;
+import org.eclipse.jgit.niofs.internal.op.commands.Clone;
+import org.eclipse.jgit.niofs.internal.op.commands.CreateRepository;
+import org.eclipse.jgit.niofs.internal.op.commands.Fork;
+import org.eclipse.jgit.niofs.internal.op.commands.SubdirectoryClone;
+import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.RefSpec;
+
+public interface Git {
+
+	static Git createRepository(final File repoDir) throws IOException {
+		return createRepository(repoDir, null, JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	static Git createRepository(final File repoDir, final boolean sslVerify) throws IOException {
+		return createRepository(repoDir, null, sslVerify);
+	}
+
+	static Git createRepository(final File repoDir, final File hookDir) throws IOException {
+		return createRepository(repoDir, hookDir, null,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	static Git createRepository(final File repoDir, final File hookDir, final boolean sslVerify) throws IOException {
+		return createRepository(repoDir, hookDir, null, sslVerify);
+	}
+
+	static Git createRepository(final File repoDir, final File hookDir, final KetchLeaderCache leaders)
+			throws IOException {
+		return new CreateRepository(repoDir, hookDir, leaders,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
+	}
+
+	static Git createRepository(final File repoDir, final File hookDir, final KetchLeaderCache leaders,
+			final boolean sslVerify) {
+		try {
+			return new CreateRepository(repoDir, hookDir, leaders, sslVerify).execute().get();
+		} catch (IOException e) {
+			throw new RuntimeException(e);
+		}
+	}
+
+	static Git fork(final File gitRepoContainerDir, final String origin, final String name, final List<String> branches,
+			final CredentialsProvider credential, final KetchLeaderCache leaders, final File hookDir)
+			throws IOException {
+		return new Fork(gitRepoContainerDir, origin, name, branches, credential, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
+	}
+
+	static Git fork(final File gitRepoContainerDir, final String origin, final String name, final List<String> branches,
+			final CredentialsProvider credential, final KetchLeaderCache leaders, final File hookDir,
+			final boolean sslVerify) throws IOException {
+		return new Fork(gitRepoContainerDir, origin, name, branches, credential, leaders, hookDir, sslVerify).execute();
+	}
+
+	static Git clone(final File repoDest, final String origin, final boolean isMirror, final List<String> branches,
+			final CredentialsProvider credential, final KetchLeaderCache leaders, final File hookDir)
+			throws IOException {
+		return new Clone(repoDest, origin, isMirror, branches, credential, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute().get();
+	}
+
+	static Git clone(final File repoDest, final String origin, final boolean isMirror, final List<String> branches,
+			final CredentialsProvider credential, final KetchLeaderCache leaders, final File hookDir,
+			final boolean sslVerify) throws IOException {
+		return new Clone(repoDest, origin, isMirror, branches, credential, leaders, hookDir, sslVerify).execute().get();
+	}
+
+	static Git cloneSubdirectory(final File repoDest, final String origin, final String subdirectory,
+			final List<String> branches, final CredentialsProvider credential, final KetchLeaderCache leaders,
+			final File hookDir) throws IOException {
+		return new SubdirectoryClone(repoDest, origin, subdirectory, branches, credential, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY).execute();
+	}
+
+	static Git cloneSubdirectory(final File repoDest, final String origin, final String subdirectory,
+			final List<String> branches, final CredentialsProvider credential, final KetchLeaderCache leaders,
+			final File hookDir, final boolean sslVerify) throws IOException {
+		return new SubdirectoryClone(repoDest, origin, subdirectory, branches, credential, leaders, hookDir, sslVerify)
+				.execute();
+	}
+
+	void convertRefTree();
+
+	void deleteRef(final Ref ref) throws IOException;
+
+	Ref getRef(final String ref);
+
+	void push(final CredentialsProvider credentialsProvider, final Map.Entry<String, String> remote,
+			final boolean force, final Collection<RefSpec> refSpecs) throws InvalidRemoteException;
+
+	void gc();
+
+	RevCommit getCommit(final String commitId);
+
+	RevCommit getLastCommit(final String refName);
+
+	RevCommit getLastCommit(final Ref ref) throws IOException;
+
+	RevCommit getCommonAncestorCommit(final String branchA, final String branchB);
+
+	CommitHistory listCommits(final Ref ref, final String path) throws IOException, GitAPIException;
+
+	List<RevCommit> listCommits(final String startCommitId, final String endCommitId);
+
+	List<RevCommit> listCommits(final ObjectId startRange, final ObjectId endRange);
+
+	Repository getRepository();
+
+	ObjectId getTreeFromRef(final String treeRef);
+
+	void fetch(final CredentialsProvider credential, final Map.Entry<String, String> remote,
+			final Collection<RefSpec> refSpecs) throws InvalidRemoteException;
+
+	void syncRemote(final Map.Entry<String, String> remote) throws InvalidRemoteException;
+
+	List<String> merge(final String source, final String target) throws IOException;
+
+	List<String> merge(final String source, final String target, final boolean noFastForward) throws IOException;
+
+	boolean revertMerge(final String source, final String target, final String commonAncestorCommitId,
+			final String mergeCommitId);
+
+	void cherryPick(final JGitPathImpl target, final String... commits) throws IOException;
+
+	void cherryPick(final String targetBranch, final String... commitsIDs) throws IOException;
+
+	void createRef(final String source, final String target);
+
+	List<FileDiff> diffRefs(final String branchA, final String branchB);
+
+	List<TextualDiff> textualDiffRefs(final String branchA, final String branchB);
+
+	List<TextualDiff> textualDiffRefs(final String branchA, final String branchB, final String commitIdBranchA,
+			final String commitIdBranchB);
+
+	List<String> conflictBranchesChecker(final String branchA, final String branchB);
+
+	void squash(final String branch, final String startCommit, final String commitMessage);
+
+	boolean commit(final String branchName, final CommitInfo commitInfo, final boolean amend, final ObjectId originId,
+			final CommitContent content);
+
+	List<DiffEntry> listDiffs(final String startCommitId, final String endCommitId);
+
+	List<DiffEntry> listDiffs(final ObjectId refA, final ObjectId refB);
+
+	Map<String, File> mapDiffContent(final String branch, final String startCommitId, final String endCommitId);
+
+	InputStream blobAsInputStream(final String treeRef, final String path) throws NoSuchFileException;
+
+	RevCommit getFirstCommit(final Ref ref) throws IOException;
+
+	List<Ref> listRefs();
+
+	List<ObjectId> resolveObjectIds(final String... commits);
+
+	RevCommit resolveRevCommit(final ObjectId objectId) throws IOException;
+
+	List<RefSpec> updateRemoteConfig(final Map.Entry<String, String> remote, final Collection<RefSpec> refSpecs)
+			throws IOException, URISyntaxException;
+
+	PathInfo getPathInfo(final String branchName, final String path);
+
+	List<PathInfo> listPathContent(final String branchName, final String path);
+
+	boolean isHEADInitialized();
+
+	void setHeadAsInitialized();
+
+	void refUpdate(final String branch, final RevCommit commit) throws IOException, ConcurrentRefUpdateException;
+
+	KetchLeader getKetchLeader();
+
+	boolean isKetchEnabled();
+
+	void enableKetch();
+
+	void updateRepo(Repository repo);
+
+	void updateLeaders(final KetchLeaderCache leaders);
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/GitImpl.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/GitImpl.java
new file mode 100644
index 0000000000..dec0e3faa7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/GitImpl.java
@@ 0,0 +1,469 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op;
+
+import static org.eclipse.jgit.niofs.internal.op.commands.PathUtil.normalize;
+
+import java.io.File;
+import java.io.IOException;
+import java.io.InputStream;
+import java.net.URISyntaxException;
+import java.nio.file.NoSuchFileException;
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+import java.util.concurrent.atomic.AtomicBoolean;
+
+import org.eclipse.jgit.api.AddCommand;
+import org.eclipse.jgit.api.CloneCommand;
+import org.eclipse.jgit.api.CommitCommand;
+import org.eclipse.jgit.api.CreateBranchCommand;
+import org.eclipse.jgit.api.DeleteBranchCommand;
+import org.eclipse.jgit.api.FetchCommand;
+import org.eclipse.jgit.api.GarbageCollectCommand;
+import org.eclipse.jgit.api.ListBranchCommand;
+import org.eclipse.jgit.api.LogCommand;
+import org.eclipse.jgit.api.PushCommand;
+import org.eclipse.jgit.api.RemoteListCommand;
+import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.api.errors.InvalidRemoteException;
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.internal.ketch.KetchLeader;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
+import org.eclipse.jgit.niofs.internal.JGitPathImpl;
+import org.eclipse.jgit.niofs.internal.config.ConfigProperties;
+import org.eclipse.jgit.niofs.internal.op.commands.BlobAsInputStream;
+import org.eclipse.jgit.niofs.internal.op.commands.CherryPick;
+import org.eclipse.jgit.niofs.internal.op.commands.Commit;
+import org.eclipse.jgit.niofs.internal.op.commands.ConflictBranchesChecker;
+import org.eclipse.jgit.niofs.internal.op.commands.ConvertRefTree;
+import org.eclipse.jgit.niofs.internal.op.commands.CreateBranch;
+import org.eclipse.jgit.niofs.internal.op.commands.DeleteBranch;
+import org.eclipse.jgit.niofs.internal.op.commands.DiffBranches;
+import org.eclipse.jgit.niofs.internal.op.commands.Fetch;
+import org.eclipse.jgit.niofs.internal.op.commands.GarbageCollector;
+import org.eclipse.jgit.niofs.internal.op.commands.GetCommit;
+import org.eclipse.jgit.niofs.internal.op.commands.GetCommonAncestorCommit;
+import org.eclipse.jgit.niofs.internal.op.commands.GetFirstCommit;
+import org.eclipse.jgit.niofs.internal.op.commands.GetLastCommit;
+import org.eclipse.jgit.niofs.internal.op.commands.GetPathInfo;
+import org.eclipse.jgit.niofs.internal.op.commands.GetRef;
+import org.eclipse.jgit.niofs.internal.op.commands.GetTreeFromRef;
+import org.eclipse.jgit.niofs.internal.op.commands.ListCommits;
+import org.eclipse.jgit.niofs.internal.op.commands.ListDiffs;
+import org.eclipse.jgit.niofs.internal.op.commands.ListPathContent;
+import org.eclipse.jgit.niofs.internal.op.commands.ListRefs;
+import org.eclipse.jgit.niofs.internal.op.commands.MapDiffContent;
+import org.eclipse.jgit.niofs.internal.op.commands.Merge;
+import org.eclipse.jgit.niofs.internal.op.commands.Push;
+import org.eclipse.jgit.niofs.internal.op.commands.RefTreeUpdateCommand;
+import org.eclipse.jgit.niofs.internal.op.commands.ResolveObjectIds;
+import org.eclipse.jgit.niofs.internal.op.commands.ResolveRevCommit;
+import org.eclipse.jgit.niofs.internal.op.commands.RevertMerge;
+import org.eclipse.jgit.niofs.internal.op.commands.SimpleRefUpdateCommand;
+import org.eclipse.jgit.niofs.internal.op.commands.Squash;
+import org.eclipse.jgit.niofs.internal.op.commands.SyncRemote;
+import org.eclipse.jgit.niofs.internal.op.commands.TextualDiffBranches;
+import org.eclipse.jgit.niofs.internal.op.commands.UpdateRemoteConfig;
+import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
+import org.eclipse.jgit.niofs.internal.util.ThrowableSupplier;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.RefSpec;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class GitImpl implements Git {
+
+	private static final Logger LOG = LoggerFactory.getLogger(GitImpl.class);
+	private static final String DEFAULT_JGIT_RETRY_SLEEP_TIME = "50";
+	private static int JGIT_RETRY_TIMES = initRetryValue();
+	private static final int JGIT_RETRY_SLEEP_TIME = initSleepTime();
+	private boolean isEnabled = false;
+
+	private static int initSleepTime() {
+		final ConfigProperties config = new ConfigProperties(System.getProperties());
+		return config.get("nio.git.retry.onfail.sleep", DEFAULT_JGIT_RETRY_SLEEP_TIME).getIntValue();
+	}
+
+	private static int initRetryValue() {
+		final ConfigProperties config = new ConfigProperties(System.getProperties());
+		final String osName = config.get("os.name", "any").getValue();
+		final String defaultRetryTimes;
+		if (osName.toLowerCase().contains("windows")) {
+			defaultRetryTimes = "10";
+		} else {
+			defaultRetryTimes = "0";
+		}
+		try {
+			return config.get("nio.git.retry.onfail.times", defaultRetryTimes).getIntValue();
+		} catch (NumberFormatException ex) {
+			return 0;
+		}
+	}
+
+	private org.eclipse.jgit.api.Git git;
+	private KetchLeaderCache leaders;
+	private final AtomicBoolean isHeadInitialized = new AtomicBoolean(false);
+
+	public GitImpl(final org.eclipse.jgit.api.Git git) {
+		this(git, null);
+	}
+
+	public GitImpl(final org.eclipse.jgit.api.Git git, final KetchLeaderCache leaders) {
+		this.git = git;
+		this.leaders = leaders;
+	}
+
+	@Override
+	public void convertRefTree() {
+		try {
+			new ConvertRefTree(this).execute();
+		} catch (IOException e) {
+			e.printStackTrace();
+			throw new RuntimeException(e);
+		}
+	}
+
+	@Override
+	public void deleteRef(final Ref ref) throws IOException {
+		new DeleteBranch(this, ref).execute();
+	}
+
+	@Override
+	public Ref getRef(final String ref) {
+		return new GetRef(git.getRepository(), ref).execute();
+	}
+
+	@Override
+	public void push(final CredentialsProvider credentialsProvider, final Map.Entry<String, String> remote,
+			final boolean force, final Collection<RefSpec> refSpecs) throws InvalidRemoteException {
+		new Push(this, credentialsProvider, remote, force, refSpecs).execute();
+	}
+
+	@Override
+	public void gc() {
+		new GarbageCollector(this).execute();
+	}
+
+	@Override
+	public RevCommit getCommit(final String commitId) {
+		return new GetCommit(this, commitId).execute();
+	}
+
+	@Override
+	public RevCommit getLastCommit(final String refName) {
+		return retryIfNeeded(RuntimeException.class, () > new GetLastCommit(this, refName).execute());
+	}
+
+	@Override
+	public RevCommit getLastCommit(final Ref ref) throws IOException {
+		return new GetLastCommit(this, ref).execute();
+	}
+
+	@Override
+	public RevCommit getCommonAncestorCommit(final String branchA, final String branchB) {
+		return new GetCommonAncestorCommit(this, getLastCommit(branchA), getLastCommit(branchB)).execute();
+	}
+
+	@Override
+	public CommitHistory listCommits(final Ref ref, final String path) throws IOException, GitAPIException {
+		return new ListCommits(this, ref, path).execute();
+	}
+
+	@Override
+	public List<RevCommit> listCommits(final String startCommitId, final String endCommitId) {
+		return listCommits(new GetCommit(this, startCommitId).execute(), new GetCommit(this, endCommitId).execute());
+	}
+
+	@Override
+	public List<RevCommit> listCommits(final ObjectId startRange, final ObjectId endRange) {
+		return retryIfNeeded(RuntimeException.class,
+				() > new ListCommits(this, startRange, endRange).execute().getCommits());
+	}
+
+	@Override
+	public Repository getRepository() {
+		return git.getRepository();
+	}
+
+	public DeleteBranchCommand _branchDelete() {
+		return git.branchDelete();
+	}
+
+	public ListBranchCommand _branchList() {
+		return git.branchList();
+	}
+
+	public CreateBranchCommand _branchCreate() {
+		return git.branchCreate();
+	}
+
+	public FetchCommand _fetch() {
+		return git.fetch();
+	}
+
+	public GarbageCollectCommand _gc() {
+		return git.gc();
+	}
+
+	public PushCommand _push() {
+		return git.push();
+	}
+
+	@Override
+	public ObjectId getTreeFromRef(final String treeRef) {
+		return new GetTreeFromRef(this, treeRef).execute();
+	}
+
+	@Override
+	public void fetch(final CredentialsProvider credential, final Map.Entry<String, String> remote,
+			final Collection<RefSpec> refSpecs) throws InvalidRemoteException {
+		new Fetch(this, credential, remote, refSpecs).execute();
+	}
+
+	@Override
+	public void syncRemote(final Map.Entry<String, String> remote) throws InvalidRemoteException {
+		new SyncRemote(this, remote).execute();
+	}
+
+	@Override
+	public List<String> merge(final String source, final String target) throws IOException {
+		return new Merge(this, source, target).execute();
+	}
+
+	@Override
+	public List<String> merge(final String source, final String target, final boolean noFastForward)
+			throws IOException {
+		return new Merge(this, source, target, noFastForward).execute();
+	}
+
+	@Override
+	public boolean revertMerge(final String source, final String target, final String commonAncestorCommitId,
+			final String mergeCommitId) {
+		return new RevertMerge(this, source, target, commonAncestorCommitId, mergeCommitId).execute();
+	}
+
+	@Override
+	public void cherryPick(final JGitPathImpl target, final String... commits) throws IOException {
+		new CherryPick(this, target.getRefTree(), commits).execute();
+	}
+
+	@Override
+	public void cherryPick(final String targetBranch, final String... commitsIDs) throws IOException {
+		new CherryPick(this, targetBranch, commitsIDs).execute();
+	}
+
+	@Override
+	public void createRef(final String source, final String target) {
+		new CreateBranch(this, source, target).execute();
+	}
+
+	@Override
+	public List<FileDiff> diffRefs(final String branchA, final String branchB) {
+		return new DiffBranches(this, branchA, branchB).execute();
+	}
+
+	@Override
+	public List<TextualDiff> textualDiffRefs(final String branchA, final String branchB) {
+		return new TextualDiffBranches(this, branchA, branchB).execute();
+	}
+
+	@Override
+	public List<TextualDiff> textualDiffRefs(final String branchA, final String branchB, final String commitIdBranchA,
+			final String commitIdBranchB) {
+		return new TextualDiffBranches(this, branchA, branchB, commitIdBranchA, commitIdBranchB).execute();
+	}
+
+	@Override
+	public List<String> conflictBranchesChecker(final String branchA, final String branchB) {
+		return new ConflictBranchesChecker(this, branchA, branchB).execute();
+	}
+
+	@Override
+	public void squash(final String branch, final String startCommit, final String commitMessage) {
+		new Squash(this, branch, startCommit, commitMessage).execute();
+	}
+
+	public LogCommand _log() {
+		return git.log();
+	}
+
+	@Override
+	public boolean commit(final String branchName, final CommitInfo commitInfo, final boolean amend,
+			final ObjectId originId, final CommitContent content) {
+		return new Commit(this, branchName, commitInfo, amend, originId, content).execute();
+	}
+
+	@Override
+	public List<DiffEntry> listDiffs(final String startCommitId, final String endCommitId) {
+		return listDiffs(getCommit(startCommitId).getTree(), getCommit(endCommitId).getTree());
+	}
+
+	@Override
+	public List<DiffEntry> listDiffs(final ObjectId refA, final ObjectId refB) {
+		return new ListDiffs(this, refA, refB).execute();
+	}
+
+	@Override
+	public Map<String, File> mapDiffContent(final String branch, final String startCommitId, final String endCommitId) {
+		return new MapDiffContent(this, branch, startCommitId, endCommitId).execute();
+	}
+
+	@Override
+	public InputStream blobAsInputStream(final String treeRef, final String path) throws NoSuchFileException {
+		return retryIfNeeded(NoSuchFileException.class,
+				() > new BlobAsInputStream(this, treeRef, normalize(path)).execute().get());
+	}
+
+	@Override
+	public RevCommit getFirstCommit(final Ref ref) throws IOException {
+		return new GetFirstCommit(this, ref).execute();
+	}
+
+	@Override
+	public List<Ref> listRefs() {
+		return new ListRefs(git.getRepository()).execute();
+	}
+
+	@Override
+	public List<ObjectId> resolveObjectIds(final String... commits) {
+		return new ResolveObjectIds(this, commits).execute();
+	}
+
+	@Override
+	public RevCommit resolveRevCommit(final ObjectId objectId) throws IOException {
+		return new ResolveRevCommit(git.getRepository(), objectId).execute();
+	}
+
+	@Override
+	public List<RefSpec> updateRemoteConfig(final Map.Entry<String, String> remote, final Collection<RefSpec> refSpecs)
+			throws IOException, URISyntaxException {
+		return new UpdateRemoteConfig(this, remote, refSpecs).execute();
+	}
+
+	public AddCommand _add() {
+		return git.add();
+	}
+
+	public CommitCommand _commit() {
+		return git.commit();
+	}
+
+	public RemoteListCommand _remoteList() {
+		return git.remoteList();
+	}
+
+	public static CloneCommand _cloneRepository() {
+		return org.eclipse.jgit.api.Git.cloneRepository();
+	}
+
+	@Override
+	public PathInfo getPathInfo(final String branchName, final String path) {
+		return retryIfNeeded(RuntimeException.class, () > new GetPathInfo(this, branchName, path).execute());
+	}
+
+	@Override
+	public List<PathInfo> listPathContent(final String branchName, final String path) {
+		return retryIfNeeded(RuntimeException.class, () > new ListPathContent(this, branchName, path).execute());
+	}
+
+	@Override
+	public boolean isHEADInitialized() {
+		return isHeadInitialized.get();
+	}
+
+	@Override
+	public void setHeadAsInitialized() {
+		isHeadInitialized.set(true);
+	}
+
+	@Override
+	public void refUpdate(final String branch, final RevCommit commit)
+			throws IOException, ConcurrentRefUpdateException {
+		if (getRepository().getRefDatabase() instanceof RefTreeDatabase) {
+			new RefTreeUpdateCommand(this, branch, commit).execute();
+		} else {
+			new SimpleRefUpdateCommand(this, branch, commit).execute();
+		}
+	}
+
+	@Override
+	public KetchLeader getKetchLeader() {
+		try {
+			return leaders.get(getRepository());
+		} catch (URISyntaxException e) {
+			throw new RuntimeException(e);
+		}
+	}
+
+	@Override
+	public boolean isKetchEnabled() {
+		return isEnabled;
+	}
+
+	@Override
+	public void enableKetch() {
+		isEnabled = true;
+	}
+
+	@Override
+	public void updateRepo(final Repository repo) {
+		this.git = new org.eclipse.jgit.api.Git(repo);
+	}
+
+	@Override
+	public void updateLeaders(final KetchLeaderCache leaders) {
+		this.leaders = leaders;
+	}
+
+	// just for test purposes
+	static void setRetryTimes(int retryTimes) {
+		JGIT_RETRY_TIMES = retryTimes;
+	}
+
+	public static <E extends Throwable, T> T retryIfNeeded(final Class<E> eclazz, final ThrowableSupplier<T> supplier)
+			throws E {
+		int i = 0;
+		do {
+			try {
+				return supplier.get();
+			} catch (final Throwable ex) {
+				if (i < (JGIT_RETRY_TIMES  1)) {
+					try {
+						Thread.sleep(JGIT_RETRY_SLEEP_TIME);
+					} catch (final InterruptedException ignored) {
+					}
+					LOG.debug(String.format("Unexpected exception (%d/%d).", i + 1, JGIT_RETRY_TIMES), ex);
+				} else {
+					LOG.error(String.format("Unexpected exception (%d/%d).", i + 1, JGIT_RETRY_TIMES), ex);
+					if (ex.getClass().isAssignableFrom(eclazz)) {
+						throw (E) ex;
+					}
+					throw new RuntimeException(ex);
+				}
+			}
+
+			i++;
+		} while (i < JGIT_RETRY_TIMES);
+
+		return null;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BaseCreateCommitTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BaseCreateCommitTree.java
new file mode 100644
index 0000000000..85ddec0b19
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BaseCreateCommitTree.java
@@ 0,0 +1,77 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.Optional;
+import java.util.function.BiConsumer;
+
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.lib.FileMode;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.treewalk.CanonicalTreeParser;
+import org.eclipse.jgit.treewalk.TreeWalk;
+
+abstract class BaseCreateCommitTree<T extends CommitContent> {
+
+	final T commitContent;
+	final Git git;
+	final ObjectId headId;
+	final ObjectInserter odi;
+
+	BaseCreateCommitTree(final Git git, final ObjectId headId, final ObjectInserter inserter, final T commitContent) {
+		this.git = git;
+		this.headId = headId;
+		this.odi = inserter;
+		this.commitContent = commitContent;
+	}
+
+	Optional<ObjectId> buildTree(final DirCacheEditor editor) {
+		try {
+			return Optional.ofNullable(editor.getDirCache().writeTree(odi));
+		} catch (Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+
+	void iterateOverTreeWalk(final Git git, final ObjectId headId,
+			final BiConsumer<String, CanonicalTreeParser> consumer) {
+		if (headId != null) {
+			try (final TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
+				final int hIdx = treeWalk.addTree(new RevWalk(git.getRepository()).parseTree(headId));
+				treeWalk.setRecursive(true);
+
+				while (treeWalk.next()) {
+					final String walkPath = treeWalk.getPathString();
+					final CanonicalTreeParser hTree = treeWalk.getTree(hIdx, CanonicalTreeParser.class);
+
+					consumer.accept(walkPath, hTree);
+				}
+			} catch (final Exception ex) {
+				throw new RuntimeException(ex);
+			}
+		}
+	}
+
+	void addToTemporaryInCoreIndex(final DirCacheEditor editor, final DirCacheEntry dcEntry, final ObjectId objectId,
+			final FileMode fileMode) {
+		editor.add(new DirCacheEditor.PathEdit(dcEntry) {
+			@Override
+			public void apply(final DirCacheEntry ent) {
+				ent.setObjectId(objectId);
+				ent.setFileMode(fileMode);
+			}
+		});
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BlobAsInputStream.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BlobAsInputStream.java
new file mode 100644
index 0000000000..bfbe81cca1
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BlobAsInputStream.java
@@ 0,0 +1,58 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.ByteArrayInputStream;
+import java.io.InputStream;
+import java.nio.file.NoSuchFileException;
+import java.util.Optional;
+
+import org.eclipse.jgit.lib.Constants;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.PathFilter;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class BlobAsInputStream {
+
+	private static final Logger LOG = LoggerFactory.getLogger(BlobAsInputStream.class);
+
+	private final Git git;
+	private final String treeRef;
+	private final String path;
+
+	public BlobAsInputStream(final Git git, final String treeRef, final String path) {
+		this.git = git;
+		this.treeRef = treeRef;
+		this.path = path;
+	}
+
+	public Optional<InputStream> execute() throws NoSuchFileException {
+		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
+			final ObjectId tree = git.getTreeFromRef(treeRef);
+			tw.setFilter(PathFilter.create(path));
+			tw.reset(tree);
+			while (tw.next()) {
+				if (tw.isSubtree() && !path.equals(tw.getPathString())) {
+					tw.enterSubtree();
+					continue;
+				}
+				return Optional.of(new ByteArrayInputStream(
+						git.getRepository().open(tw.getObjectId(0), Constants.OBJ_BLOB).getBytes()));
+			}
+		} catch (final Throwable t) {
+			LOG.debug("Unexpected exception, this will trigger a NoSuchFileException.", t);
+			throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
+		}
+		throw new NoSuchFileException("Can't find '" + path + "' in tree '" + treeRef + "'");
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BranchUtil.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BranchUtil.java
new file mode 100644
index 0000000000..ef499d2090
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/BranchUtil.java
@@ 0,0 +1,45 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.List;
+
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+
+public class BranchUtil {
+
+	private BranchUtil() {
+
+	}
+
+	public static void deleteUnfilteredBranches(final Repository repository, final List<String> branchesToKeep)
+			throws GitAPIException {
+		if (branchesToKeep == null || branchesToKeep.isEmpty()) {
+			return;
+		}
+
+		final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repository);
+		final String[] toDelete = git.branchList().call().stream().map(Ref::getName)
+				.map(fullname > fullname.substring(fullname.lastIndexOf('/') + 1))
+				.filter(name > !branchesToKeep.contains(name)).toArray(String[]::new);
+		git.branchDelete().setBranchNames(toDelete).setForce(true).call();
+	}
+
+	public static void existsBranch(final Git git, final String branch) {
+		if (git.getRef(branch) == null) {
+			throw new GitException(String.format("Branch <<%s>> does not exist", branch));
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CherryPick.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CherryPick.java
new file mode 100644
index 0000000000..1aaa45cb21
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CherryPick.java
@@ 0,0 +1,68 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.text.MessageFormat;
+import java.util.List;
+
+import org.eclipse.jgit.api.errors.JGitInternalException;
+import org.eclipse.jgit.api.errors.MultipleParentsNotAllowedException;
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class CherryPick {
+
+	private final Git git;
+	private final String targetBranch;
+	private final String[] commits;
+
+	public CherryPick(final Git git, final String targetBranch, final String... commits) {
+		this.git = git;
+		this.targetBranch = targetBranch;
+		this.commits = commits;
+	}
+
+	public void execute() throws IOException {
+		final List<ObjectId> commits = git.resolveObjectIds(this.commits);
+		if (commits.size() != this.commits.length) {
+			throw new IOException("Couldn't resolve some commits.");
+		}
+
+		final Ref headRef = git.getRef(targetBranch);
+		if (headRef == null) {
+			throw new IOException("Branch not found.");
+		}
+
+		try {
+			// loop through all refs to be cherrypicked
+			for (final ObjectId src : commits) {
+				final RevCommit srcCommit = git.resolveRevCommit(src);
+
+				// get the parent of the commit to cherrypick
+				if (srcCommit.getParentCount() != 1) {
+					throw new IOException(new MultipleParentsNotAllowedException(
+							MessageFormat.format(JGitText.get().canOnlyCherryPickCommitsWithOneParent, srcCommit.name(),
+									srcCommit.getParentCount())));
+				}
+
+				git.refUpdate(targetBranch, srcCommit);
+			}
+		} catch (final java.io.IOException e) {
+			throw new IOException(new JGitInternalException(
+					MessageFormat.format(JGitText.get().exceptionCaughtDuringExecutionOfCherryPickCommand, e), e));
+		} catch (final Exception e) {
+			throw new IOException(e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Clone.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Clone.java
new file mode 100644
index 0000000000..11db5ad5d2
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Clone.java
@@ 0,0 +1,137 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.AbstractMap;
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.storage.file.WindowCacheConfig;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.RefSpec;
+import org.eclipse.jgit.util.FileUtils;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static java.util.Collections.emptyList;
+import static java.util.Collections.singletonList;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+public class Clone {
+
+	public static final String REFS_MIRRORED = "+refs/heads/*:refs/remotes/origin/*";
+	private final File repoDir;
+	private final String origin;
+	private final List<String> branches;
+	private final CredentialsProvider credentialsProvider;
+	private final boolean isMirror;
+	private final KetchLeaderCache leaders;
+	private final File hookDir;
+	private final boolean sslVerify;
+
+	private Logger logger = LoggerFactory.getLogger(Clone.class);
+
+	public Clone(final File directory, final String origin, final boolean isMirror, final List<String> branches,
+			final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders, final File hookDir) {
+		this(directory, origin, isMirror, branches, credentialsProvider, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	public Clone(final File directory, final String origin, final boolean isMirror, final List<String> branches,
+			final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders, final File hookDir,
+			final boolean sslVerify) {
+		this.repoDir = checkNotNull("directory", directory);
+		this.origin = checkNotEmpty("origin", origin);
+		this.isMirror = isMirror;
+		this.branches = branches;
+		this.credentialsProvider = credentialsProvider;
+		this.leaders = leaders;
+		this.hookDir = hookDir;
+		this.sslVerify = sslVerify;
+	}
+
+	public Optional<Git> execute() throws IOException {
+
+		if (repoDir.exists()) {
+			String message = String.format("Cannot clone because destination repository <%s> already exists",
+					repoDir.getAbsolutePath());
+			logger.error(message);
+			throw new CloneException(message);
+		}
+
+		final Git git = Git.createRepository(repoDir, hookDir, sslVerify);
+
+		if (git != null) {
+			try {
+
+				final Collection<RefSpec> refSpecList;
+				if (isMirror) {
+					refSpecList = singletonList(new RefSpec(REFS_MIRRORED));
+				} else {
+					refSpecList = emptyList();
+				}
+				final Map.Entry<String, String> remote = new AbstractMap.SimpleEntry<>("origin", origin);
+				git.fetch(credentialsProvider, remote, refSpecList);
+
+				git.syncRemote(remote);
+
+				if (git.isKetchEnabled()) {
+					git.convertRefTree();
+					git.updateLeaders(leaders);
+				}
+
+				git.setHeadAsInitialized();
+
+				BranchUtil.deleteUnfilteredBranches(git.getRepository(), branches);
+
+				return Optional.of(git);
+			} catch (Exception e) {
+				String message = String.format("Error cloning origin <%s>.", origin);
+				logger.error(message);
+				cleanupDir(git.getRepository().getDirectory());
+				throw new CloneException(message, e);
+			}
+		}
+
+		return Optional.empty();
+	}
+
+	private void cleanupDir(final File gitDir) throws IOException {
+
+		try {
+			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
+				// this operation forces a cache clean freeing any lock > windows only issue!
+				new WindowCacheConfig().install();
+			}
+			FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.RETRY);
+		} catch (java.io.IOException e) {
+			throw new IOException("Failed to remove the git repository.", e);
+		}
+	}
+
+	public static class CloneException extends RuntimeException {
+
+		public CloneException(final String message) {
+			super(message);
+		}
+
+		public CloneException(final String message, final Throwable t) {
+			super(message, t);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Commit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Commit.java
new file mode 100644
index 0000000000..683989bca4
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Commit.java
@@ 0,0 +1,141 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.nio.charset.StandardCharsets;
+import java.util.Arrays;
+import java.util.Date;
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+import java.util.TimeZone;
+
+import org.eclipse.jgit.lib.CommitBuilder;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.lib.PersonIdent;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
+import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.MergeCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+import static java.util.Collections.reverse;
+
+public class Commit {
+
+	private final Git git;
+	private final String branchName;
+	private final CommitInfo commitInfo;
+	private final boolean amend;
+	private final ObjectId originId;
+	private final CommitContent content;
+
+	public Commit(final Git git, final String branchName, final String name, final String email, final String message,
+			final TimeZone timeZone, final Date when, final boolean amend, final Map<String, File> content) {
+		this(git, branchName, new CommitInfo(null, name, email, message, timeZone, when), amend, null,
+				new DefaultCommitContent(content));
+	}
+
+	public Commit(final Git git, final String branchName, final CommitInfo commitInfo, final boolean amend,
+			final ObjectId originId, final CommitContent content) {
+		this.git = git;
+		this.branchName = branchName;
+		this.commitInfo = commitInfo;
+		this.amend = amend;
+		this.content = content;
+		try {
+			if (originId == null) {
+				this.originId = git.getLastCommit(branchName);
+			} else {
+				this.originId = originId;
+			}
+		} catch (final Throwable t) {
+			throw new RuntimeException(t);
+		}
+	}
+
+	public boolean execute() {
+		boolean hadEffecitiveCommit = true;
+		final PersonIdent author = buildPersonIdent(git, commitInfo.getName(), commitInfo.getEmail(),
+				commitInfo.getTimeZone(), commitInfo.getWhen());
+
+		try (final ObjectInserter odi = git.getRepository().newObjectInserter()) {
+			final ObjectId headId = git.getRepository().resolve(branchName + "^{commit}");
+
+			final Optional<ObjectId> tree;
+			if (content instanceof DefaultCommitContent) {
+				tree = new CreateDefaultCommitTree(git, originId, odi, (DefaultCommitContent) content).execute();
+			} else if (content instanceof MoveCommitContent) {
+				tree = new CreateMoveCommitTree(git, originId, odi, (MoveCommitContent) content).execute();
+			} else if (content instanceof CopyCommitContent) {
+				tree = new CreateCopyCommitTree(git, originId, odi, (CopyCommitContent) content).execute();
+			} else if (content instanceof RevertCommitContent) {
+				tree = new CreateRevertCommitTree(git, originId, odi, (RevertCommitContent) content).execute();
+			} else {
+				tree = Optional.empty();
+			}
+
+			if (tree.isPresent()) {
+				final CommitBuilder commit = new CommitBuilder();
+				commit.setAuthor(author);
+				commit.setCommitter(author);
+				commit.setEncoding(StandardCharsets.UTF_8);
+				commit.setMessage(commitInfo.getMessage());
+				if (headId != null) {
+					if (content instanceof MergeCommitContent) {
+						commit.setParentIds(((MergeCommitContent) content).getParents());
+					} else {
+						if (amend) {
+							final RevCommit previousCommit = git.resolveRevCommit(headId);
+							final List<RevCommit> p = Arrays.asList(previousCommit.getParents());
+							reverse(p);
+							commit.setParentIds(p);
+						} else {
+							commit.setParentId(headId);
+						}
+					}
+				}
+				commit.setTreeId(tree.get());
+
+				final ObjectId commitId = odi.insert(commit);
+				odi.flush();
+
+				git.refUpdate(branchName, git.resolveRevCommit(commitId));
+			} else {
+				hadEffecitiveCommit = false;
+			}
+		} catch (final Throwable t) {
+			t.printStackTrace();
+			throw new RuntimeException(t);
+		}
+		return hadEffecitiveCommit;
+	}
+
+	private PersonIdent buildPersonIdent(final Git git, final String name, final String _email, final TimeZone timeZone,
+			final Date when) {
+		final TimeZone tz = timeZone == null ? TimeZone.getDefault() : timeZone;
+		final String email = _email == null ? "" : _email;
+
+		if (name != null) {
+			if (when != null) {
+				return new PersonIdent(name, email, when, tz);
+			} else {
+				return new PersonIdent(name, email);
+			}
+		}
+		return new PersonIdent("system", "system", new Date(), TimeZone.getDefault());
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConflictBranchesChecker.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConflictBranchesChecker.java
new file mode 100644
index 0000000000..fdeb26dd8b
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConflictBranchesChecker.java
@@ 0,0 +1,72 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.List;
+import java.util.Map;
+import java.util.stream.Collectors;
+
+import org.eclipse.jgit.merge.MergeResult;
+import org.eclipse.jgit.merge.MergeStrategy;
+import org.eclipse.jgit.merge.ResolveMerger;
+import org.eclipse.jgit.merge.ThreeWayMerger;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class ConflictBranchesChecker {
+
+	private final Git git;
+	private final String branchA;
+	private final String branchB;
+
+	public ConflictBranchesChecker(final Git git, final String branchA, final String branchB) {
+		this.git = checkNotNull("git", git);
+		this.branchA = checkNotEmpty("branchA", branchA);
+		this.branchB = checkNotEmpty("branchB", branchB);
+	}
+
+	public List<String> execute() {
+		BranchUtil.existsBranch(this.git, this.branchA);
+
+		BranchUtil.existsBranch(this.git, this.branchB);
+
+		List<String> result = new ArrayList<>();
+
+		try {
+			final RevCommit commitA = git.getLastCommit(branchA);
+			final RevCommit commitB = git.getLastCommit(branchB);
+
+			final RevCommit commonAncestor = git.getCommonAncestorCommit(branchA, branchB);
+
+			ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(git.getRepository(), true);
+			merger.setBase(commonAncestor);
+
+			boolean canMerge = merger.merge(commitA, commitB);
+
+			if (!canMerge) {
+				ResolveMerger resolveMerger = (ResolveMerger) merger;
+				Map<String, MergeResult<?>> mergeResults = resolveMerger.getMergeResults();
+				result.addAll(mergeResults.keySet().stream().sorted(String::compareToIgnoreCase)
+						.collect(Collectors.toList()));
+			}
+		} catch (IOException e) {
+			throw new GitException(String.format("Error when checking for conflicts between branches %s and %s: %s",
+					this.branchA, this.branchB, e));
+		}
+
+		return result;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConvertRefTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConvertRefTree.java
new file mode 100644
index 0000000000..39bfbe396e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ConvertRefTree.java
@@ 0,0 +1,136 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.io.IOException;
+import java.nio.file.Files;
+import java.nio.file.StandardCopyOption;
+import java.util.ArrayList;
+import java.util.Date;
+import java.util.List;
+import java.util.TimeZone;
+
+import org.eclipse.jgit.internal.storage.reftree.Command;
+import org.eclipse.jgit.internal.storage.reftree.RefTree;
+import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
+import org.eclipse.jgit.lib.CommitBuilder;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.PersonIdent;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.RefDatabase;
+import org.eclipse.jgit.lib.RefUpdate;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.lib.StoredConfig;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
+
+import static org.eclipse.jgit.lib.Constants.HEAD;
+
+public class ConvertRefTree {
+
+	private final Git git;
+	private String txnNamespace;
+	private String txnCommitted;
+
+	public ConvertRefTree(final Git git) {
+		this.git = git;
+	}
+
+	public void execute() throws IOException {
+		try (ObjectReader reader = git.getRepository().newObjectReader();
+				RevWalk rw = new RevWalk(reader);
+				ObjectInserter inserter = git.getRepository().newObjectInserter()) {
+			RefDatabase refDb = git.getRepository().getRefDatabase();
+			if (refDb instanceof RefTreeDatabase) {
+				RefTreeDatabase d = (RefTreeDatabase) refDb;
+				refDb = d.getBootstrap();
+				txnNamespace = d.getTxnNamespace();
+				txnCommitted = d.getTxnCommitted();
+			} else {
+				RefTreeDatabase d = new RefTreeDatabase(git.getRepository(), refDb);
+				txnNamespace = d.getTxnNamespace();
+				txnCommitted = d.getTxnCommitted();
+			}
+
+			CommitBuilder b = new CommitBuilder();
+			Ref ref = refDb.exactRef(txnCommitted);
+			RefUpdate update = refDb.newUpdate(txnCommitted, true);
+			ObjectId oldTreeId;
+
+			if (ref != null && ref.getObjectId() != null) {
+				ObjectId oldId = ref.getObjectId();
+				update.setExpectedOldObjectId(oldId);
+				b.setParentId(oldId);
+				oldTreeId = rw.parseCommit(oldId).getTree();
+			} else {
+				update.setExpectedOldObjectId(ObjectId.zeroId());
+				oldTreeId = ObjectId.zeroId();
+			}
+
+			RefTree tree = rebuild(refDb);
+			b.setTreeId(tree.writeTree(inserter));
+			b.setAuthor(new PersonIdent("system", "system", new Date(1481754897254L), TimeZone.getDefault()));
+			b.setCommitter(b.getAuthor());
+			if (b.getTreeId().equals(oldTreeId)) {
+				return;
+			}
+
+			update.setNewObjectId(inserter.insert(b));
+			inserter.flush();
+
+			RefUpdate.Result result = update.update(rw);
+			switch (result) {
+			case NEW:
+			case FAST_FORWARD:
+				break;
+			default:
+				throw new RuntimeException(String.format("%s: %s", update.getName(), result)); // $NONNLS1$
+			}
+
+			if (!(git.getRepository().getRefDatabase() instanceof RefTreeDatabase)) {
+				StoredConfig cfg = git.getRepository().getConfig();
+				cfg.setInt("core", null, "repositoryformatversion", 1); // $NONNLS1$ //$NONNLS2$
+				cfg.setString("extensions", null, "refsStorage", "reftree"); //$NONNLS3$ //$NONNLS2$ //$NONNLS3$
+				cfg.save();
+
+				final Repository repo = new FileRepositoryBuilder().setGitDir(git.getRepository().getDirectory())
+						.build();
+				git.updateRepo(repo);
+			}
+			final File commited = new File(git.getRepository().getDirectory(), txnCommitted);
+			final File accepted = new File(git.getRepository().getDirectory(), txnNamespace + "accepted");
+			Files.copy(commited.toPath(), accepted.toPath(), StandardCopyOption.REPLACE_EXISTING);
+		}
+	}
+
+	private RefTree rebuild(RefDatabase refdb) throws IOException {
+		RefTree tree = RefTree.newEmptyTree();
+		List<Command> cmds = new ArrayList<>();
+
+		Ref head = refdb.exactRef(HEAD);
+		if (head != null) {
+			cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(null, head));
+		}
+
+		for (Ref r : refdb.getRefsByPrefix(RefDatabase.ALL)) {
+			if (r.getName().equals(txnCommitted) || r.getName().equals(HEAD) || r.getName().startsWith(txnNamespace)) {
+				continue;
+			}
+			cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(null,
+					git.getRepository().getRefDatabase().peel(r)));
+		}
+		tree.apply(cmds);
+		return tree;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateBranch.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateBranch.java
new file mode 100644
index 0000000000..e3ba0fac64
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateBranch.java
@@ 0,0 +1,33 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+
+public class CreateBranch {
+
+	private final GitImpl git;
+	private final String source;
+	private final String target;
+
+	public CreateBranch(final GitImpl git, final String source, final String target) {
+		this.git = git;
+		this.source = source;
+		this.target = target;
+	}
+
+	public void execute() {
+		try {
+			git.refUpdate(target, git.resolveRevCommit(git.resolveObjectIds(source).get(0)));
+		} catch (final Exception e) {
+			throw new RuntimeException(e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateCopyCommitTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateCopyCommitTree.java
new file mode 100644
index 0000000000..d4abdbead0
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateCopyCommitTree.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.Map;
+import java.util.Optional;
+
+import org.eclipse.jgit.dircache.DirCache;
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;
+
+public class CreateCopyCommitTree extends BaseCreateCommitTree<CopyCommitContent> {
+
+	public CreateCopyCommitTree(final Git git, final ObjectId headId, final ObjectInserter inserter,
+			final CopyCommitContent commitContent) {
+		super(git, headId, inserter, commitContent);
+	}
+
+	public Optional<ObjectId> execute() {
+		final Map<String, String> content = commitContent.getContent();
+
+		final DirCacheEditor editor = DirCache.newInCore().editor();
+
+		try {
+			iterateOverTreeWalk(git, headId, (walkPath, hTree) > {
+				final String toPath = content.get(walkPath);
+				addToTemporaryInCoreIndex(editor, new DirCacheEntry(walkPath), hTree.getEntryObjectId(),
+						hTree.getEntryFileMode());
+				if (toPath != null) {
+					addToTemporaryInCoreIndex(editor, new DirCacheEntry(toPath), hTree.getEntryObjectId(),
+							hTree.getEntryFileMode());
+				}
+			});
+
+			editor.finish();
+		} catch (final Exception e) {
+			throw new RuntimeException(e);
+		}
+
+		return buildTree(editor);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateDefaultCommitTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateDefaultCommitTree.java
new file mode 100644
index 0000000000..99f4871343
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateDefaultCommitTree.java
@@ 0,0 +1,126 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.io.FileInputStream;
+import java.io.InputStream;
+import java.time.Instant;
+import java.util.AbstractMap;
+import java.util.HashMap;
+import java.util.HashSet;
+import java.util.Map;
+import java.util.Optional;
+import java.util.Set;
+
+import org.eclipse.jgit.dircache.DirCache;
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.lib.Constants;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.PathFilter;
+
+import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
+
+public class CreateDefaultCommitTree extends BaseCreateCommitTree<DefaultCommitContent> {
+
+	public CreateDefaultCommitTree(final Git git, final ObjectId headId, final ObjectInserter inserter,
+			final DefaultCommitContent commitContent) {
+		super(git, headId, inserter, commitContent);
+	}
+
+	public Optional<ObjectId> execute() {
+		final Map<String, File> content = commitContent.getContent();
+		final Map<String, Map.Entry<File, ObjectId>> paths = new HashMap<>(content.size());
+		final Set<String> path2delete = new HashSet<>();
+
+		final DirCacheEditor editor = DirCache.newInCore().editor();
+
+		try {
+			for (final Map.Entry<String, File> pathAndContent : content.entrySet()) {
+				final String gPath = PathUtil.normalize(pathAndContent.getKey());
+				if (pathAndContent.getValue() == null) {
+					path2delete.addAll(searchPathsToDelete(git, headId, gPath));
+				} else {
+					paths.putAll(storePathsIntoHashMap(odi, pathAndContent, gPath));
+				}
+			}
+
+			iterateOverTreeWalk(git, headId, (walkPath, hTree) > {
+				if (paths.containsKey(walkPath) && paths.get(walkPath).getValue().equals(hTree.getEntryObjectId())) {
+					paths.remove(walkPath);
+				}
+
+				if (paths.get(walkPath) == null && !path2delete.contains(walkPath)) {
+					addToTemporaryInCoreIndex(editor, new DirCacheEntry(walkPath), hTree.getEntryObjectId(),
+							hTree.getEntryFileMode());
+				}
+			});
+
+			paths.forEach((key, value) > {
+				if (value.getKey() != null) {
+					editor.add(new DirCacheEditor.PathEdit(new DirCacheEntry(key)) {
+						@Override
+						public void apply(final DirCacheEntry ent) {
+							ent.setLength(value.getKey().length());
+							ent.setLastModified(Instant.ofEpochMilli(value.getKey().lastModified()));
+							ent.setFileMode(REGULAR_FILE);
+							ent.setObjectId(value.getValue());
+						}
+					});
+				}
+			});
+
+			editor.finish();
+		} catch (Exception e) {
+			throw new RuntimeException(e);
+		}
+
+		if (path2delete.isEmpty() && paths.isEmpty()) {
+			editor.getDirCache().clear();
+			return Optional.empty();
+		}
+
+		return buildTree(editor);
+	}
+
+	private static Map<String, Map.Entry<File, ObjectId>> storePathsIntoHashMap(final ObjectInserter inserter,
+			final Map.Entry<String, File> pathAndContent, final String gPath) {
+		try (final InputStream inputStream = new FileInputStream(pathAndContent.getValue())) {
+			final Map<String, Map.Entry<File, ObjectId>> paths = new HashMap<>();
+			final ObjectId objectId = inserter.insert(Constants.OBJ_BLOB, pathAndContent.getValue().length(),
+					inputStream);
+			paths.put(gPath, new AbstractMap.SimpleEntry<>(pathAndContent.getValue(), objectId));
+			return paths;
+		} catch (final Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+
+	private static Set<String> searchPathsToDelete(final Git git, final ObjectId headId, final String gPath)
+			throws java.io.IOException {
+		try (final TreeWalk treeWalk = new TreeWalk(git.getRepository())) {
+			final Set<String> path2delete = new HashSet<>();
+			treeWalk.addTree(new RevWalk(git.getRepository()).parseTree(headId));
+			treeWalk.setRecursive(true);
+			treeWalk.setFilter(PathFilter.create(gPath));
+
+			while (treeWalk.next()) {
+				path2delete.add(treeWalk.getPathString());
+			}
+			return path2delete;
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateMoveCommitTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateMoveCommitTree.java
new file mode 100644
index 0000000000..c6128303ae
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateMoveCommitTree.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.ArrayList;
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+
+import org.eclipse.jgit.dircache.DirCache;
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
+
+public class CreateMoveCommitTree extends BaseCreateCommitTree<MoveCommitContent> {
+
+	public CreateMoveCommitTree(final Git git, final ObjectId headId, final ObjectInserter inserter,
+			final MoveCommitContent commitContent) {
+		super(git, headId, inserter, commitContent);
+	}
+
+	public Optional<ObjectId> execute() {
+		final Map<String, String> content = commitContent.getContent();
+		final DirCacheEditor editor = DirCache.newInCore().editor();
+		final List<String> pathsAdded = new ArrayList<>();
+
+		try {
+			iterateOverTreeWalk(git, headId, (walkPath, hTree) > {
+				final String toPath = content.get(walkPath);
+				final DirCacheEntry dcEntry = new DirCacheEntry((toPath == null) ? walkPath : toPath);
+				if (!pathsAdded.contains(dcEntry.getPathString())) {
+					addToTemporaryInCoreIndex(editor, dcEntry, hTree.getEntryObjectId(), hTree.getEntryFileMode());
+					pathsAdded.add(dcEntry.getPathString());
+				}
+			});
+			editor.finish();
+		} catch (final Exception e) {
+			throw new RuntimeException(e);
+		}
+
+		return buildTree(editor);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRepository.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRepository.java
new file mode 100644
index 0000000000..db5567bec9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRepository.java
@@ 0,0 +1,110 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.Optional;
+
+import org.apache.commons.io.FileUtils;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.lib.StoredConfig;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
+
+public class CreateRepository {
+
+	private final File repoDir;
+	private final File hookDir;
+	private final KetchLeaderCache leaders;
+	private final boolean sslVerify;
+
+	public CreateRepository(final File repoDir) {
+		this(repoDir, null, null, JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	public CreateRepository(final File repoDir, final boolean sslVerify) {
+		this(repoDir, null, null, sslVerify);
+	}
+
+	public CreateRepository(final File repoDir, final File hookDir) {
+		this(repoDir, hookDir, null, JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	public CreateRepository(final File repoDir, final File hookDir, final boolean sslVerify) {
+		this(repoDir, hookDir, null, sslVerify);
+	}
+
+	public CreateRepository(final File repoDir, final File hookDir, final KetchLeaderCache leaders) {
+		this(repoDir, hookDir, leaders, JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	public CreateRepository(final File repoDir, final File hookDir, final KetchLeaderCache leaders,
+			final boolean sslVerify) {
+		this.repoDir = repoDir;
+		this.hookDir = hookDir;
+		this.leaders = leaders;
+		this.sslVerify = sslVerify;
+	}
+
+	public Optional<Git> execute() throws IOException {
+		try {
+			boolean newRepository = !repoDir.exists();
+			final org.eclipse.jgit.api.Git _git = org.eclipse.jgit.api.Git.init().setBare(true).setDirectory(repoDir)
+					.call();
+
+			if (leaders != null) {
+				new WriteConfiguration(_git.getRepository(), cfg > {
+					cfg.setInt("core", null, "repositoryformatversion", 1);
+					cfg.setString("extensions", null, "refsStorage", "reftree");
+				}).execute();
+			}
+
+			final Repository repo = new FileRepositoryBuilder().setGitDir(repoDir).build();
+
+			final org.eclipse.jgit.api.Git git = new org.eclipse.jgit.api.Git(repo);
+
+			setupSSLVerify(repo);
+
+			if (setupGitHooks(newRepository)) {
+				final File repoHookDir = new File(repoDir, "hooks");
+
+				try {
+					FileUtils.copyDirectory(hookDir, repoHookDir);
+				} catch (final Exception ex) {
+					throw new RuntimeException(ex);
+				}
+
+				for (final File file : repoHookDir.listFiles()) {
+					if (file != null && file.isFile()) {
+						file.setExecutable(true);
+					}
+				}
+			}
+
+			return Optional.of(new GitImpl(git, leaders));
+		} catch (final Exception ex) {
+			throw new IOException(ex);
+		}
+	}
+
+	private void setupSSLVerify(Repository repo) throws java.io.IOException {
+		StoredConfig config = repo.getConfig();
+		config.setBoolean("http", null, "sslVerify", sslVerify);
+		config.save();
+	}
+
+	private boolean setupGitHooks(boolean newRepository) {
+		return newRepository && hookDir != null;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRevertCommitTree.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRevertCommitTree.java
new file mode 100644
index 0000000000..da97e714ef
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CreateRevertCommitTree.java
@@ 0,0 +1,45 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.Optional;
+
+import org.eclipse.jgit.dircache.DirCache;
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
+
+public class CreateRevertCommitTree extends BaseCreateCommitTree<RevertCommitContent> {
+
+	public CreateRevertCommitTree(final Git git, final ObjectId headId, final ObjectInserter inserter,
+			final RevertCommitContent commitContent) {
+		super(git, headId, inserter, commitContent);
+	}
+
+	public Optional<ObjectId> execute() {
+		final DirCacheEditor editor = DirCache.newInCore().editor();
+
+		try {
+			iterateOverTreeWalk(git, headId, (walkPath, hTree) > {
+				addToTemporaryInCoreIndex(editor, new DirCacheEntry(walkPath), hTree.getEntryObjectId(),
+						hTree.getEntryFileMode());
+			});
+
+			editor.finish();
+		} catch (final Exception e) {
+			throw new RuntimeException(e);
+		}
+
+		return buildTree(editor);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CustomDiffCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CustomDiffCommand.java
new file mode 100644
index 0000000000..171e333f70
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/CustomDiffCommand.java
@@ 0,0 +1,239 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.BufferedOutputStream;
+import java.io.IOException;
+import java.io.OutputStream;
+import java.util.List;
+
+import org.eclipse.jgit.api.GitCommand;
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.api.errors.JGitInternalException;
+import org.eclipse.jgit.api.errors.NoHeadException;
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.diff.DiffFormatter;
+import org.eclipse.jgit.dircache.DirCacheIterator;
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.lib.NullProgressMonitor;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.ProgressMonitor;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.treewalk.AbstractTreeIterator;
+import org.eclipse.jgit.treewalk.CanonicalTreeParser;
+import org.eclipse.jgit.treewalk.FileTreeIterator;
+import org.eclipse.jgit.treewalk.filter.TreeFilter;
+import org.eclipse.jgit.util.io.NullOutputStream;
+
+import static org.eclipse.jgit.lib.Constants.HEAD;
+
+public class CustomDiffCommand extends GitCommand<List<DiffEntry>> {
+
+	private final Git git;
+	private AbstractTreeIterator oldTree;
+
+	private AbstractTreeIterator newTree;
+
+	private boolean cached;
+
+	private TreeFilter pathFilter = TreeFilter.ALL;
+
+	private boolean showNameAndStatusOnly;
+
+	private OutputStream out;
+
+	private int contextLines = 1;
+
+	private String sourcePrefix;
+
+	private String destinationPrefix;
+
+	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;
+
+	/**
+	 * @param repo
+	 */
+	protected CustomDiffCommand(Git git) {
+		super(git.getRepository());
+		this.git = git;
+	}
+
+	/**
+	 * Executes the {@code Diff} command with all the options and parameters
+	 * collected by the setter methods (e.g. {@link #setCached(boolean)} of this
+	 * class. Each instance of this class should only be used for one invocation of
+	 * the command. Don't call this method twice on an instance.
+	 * 
+	 * @return a DiffEntry for each path which is different
+	 */
+	public List<DiffEntry> call() throws GitAPIException {
+		final DiffFormatter diffFmt;
+		if (out != null && !showNameAndStatusOnly) {
+			diffFmt = new DiffFormatter(new BufferedOutputStream(out));
+		} else {
+			diffFmt = new DiffFormatter(NullOutputStream.INSTANCE);
+		}
+		diffFmt.setRepository(repo);
+		diffFmt.setProgressMonitor(monitor);
+		diffFmt.setDetectRenames(true);
+		try {
+			if (cached) {
+				if (oldTree == null) {
+					ObjectId head = git.getTreeFromRef(HEAD);
+					if (head == null) {
+						throw new NoHeadException(JGitText.get().cannotReadTree);
+					}
+					CanonicalTreeParser p = new CanonicalTreeParser();
+					ObjectReader reader = repo.newObjectReader();
+					try {
+						p.reset(reader, head);
+					} finally {
+						reader.close();
+					}
+					oldTree = p;
+				}
+				newTree = new DirCacheIterator(repo.readDirCache());
+			} else {
+				if (oldTree == null) {
+					oldTree = new DirCacheIterator(repo.readDirCache());
+				}
+				if (newTree == null) {
+					newTree = new FileTreeIterator(repo);
+				}
+			}
+
+			diffFmt.setPathFilter(pathFilter);
+
+			List<DiffEntry> result = diffFmt.scan(oldTree, newTree);
+			if (showNameAndStatusOnly) {
+				return result;
+			} else {
+				if (contextLines >= 0) {
+					diffFmt.setContext(contextLines);
+				}
+				if (destinationPrefix != null) {
+					diffFmt.setNewPrefix(destinationPrefix);
+				}
+				if (sourcePrefix != null) {
+					diffFmt.setOldPrefix(sourcePrefix);
+				}
+				diffFmt.format(result);
+				diffFmt.flush();
+				return result;
+			}
+		} catch (IOException e) {
+			throw new JGitInternalException(e.getMessage(), e);
+		} finally {
+			diffFmt.close();
+		}
+	}
+
+	/**
+	 * @param cached whether to view the changes you staged for the next commit
+	 * @return this instance
+	 */
+	public CustomDiffCommand setCached(boolean cached) {
+		this.cached = cached;
+		return this;
+	}
+
+	/**
+	 * @param pathFilter parameter, used to limit the diff to the named path
+	 * @return this instance
+	 */
+	public CustomDiffCommand setPathFilter(TreeFilter pathFilter) {
+		this.pathFilter = pathFilter;
+		return this;
+	}
+
+	/**
+	 * @param oldTree the previous state
+	 * @return this instance
+	 */
+	public CustomDiffCommand setOldTree(AbstractTreeIterator oldTree) {
+		this.oldTree = oldTree;
+		return this;
+	}
+
+	/**
+	 * @param newTree the updated state
+	 * @return this instance
+	 */
+	public CustomDiffCommand setNewTree(AbstractTreeIterator newTree) {
+		this.newTree = newTree;
+		return this;
+	}
+
+	/**
+	 * @param showNameAndStatusOnly whether to return only names and status of
+	 *                              changed files
+	 * @return this instance
+	 */
+	public CustomDiffCommand setShowNameAndStatusOnly(boolean showNameAndStatusOnly) {
+		this.showNameAndStatusOnly = showNameAndStatusOnly;
+		return this;
+	}
+
+	/**
+	 * @param out the stream to write line data
+	 * @return this instance
+	 */
+	public CustomDiffCommand setOutputStream(OutputStream out) {
+		this.out = out;
+		return this;
+	}
+
+	/**
+	 * Set number of context lines instead of the usual three.
+	 * 
+	 * @param contextLines the number of context lines
+	 * @return this instance
+	 */
+	public CustomDiffCommand setContextLines(int contextLines) {
+		this.contextLines = contextLines;
+		return this;
+	}
+
+	/**
+	 * Set the given source prefix instead of "a/".
+	 * 
+	 * @param sourcePrefix the prefix
+	 * @return this instance
+	 */
+	public CustomDiffCommand setSourcePrefix(String sourcePrefix) {
+		this.sourcePrefix = sourcePrefix;
+		return this;
+	}
+
+	/**
+	 * Set the given destination prefix instead of "b/".
+	 * 
+	 * @param destinationPrefix the prefix
+	 * @return this instance
+	 */
+	public CustomDiffCommand setDestinationPrefix(String destinationPrefix) {
+		this.destinationPrefix = destinationPrefix;
+		return this;
+	}
+
+	/**
+	 * The progress monitor associated with the diff operation. By default, this is
+	 * set to <code>NullProgressMonitor</code>
+	 * 
+	 * @param monitor a progress monitor
+	 * @return this instance
+	 * @see NullProgressMonitor
+	 */
+	public CustomDiffCommand setProgressMonitor(ProgressMonitor monitor) {
+		this.monitor = monitor;
+		return this;
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DeleteBranch.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DeleteBranch.java
new file mode 100644
index 0000000000..c1c01915b0
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DeleteBranch.java
@@ 0,0 +1,35 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+
+public class DeleteBranch {
+
+	private final GitImpl git;
+	private final Ref branch;
+
+	public DeleteBranch(final GitImpl git, final Ref branch) {
+		this.git = git;
+		this.branch = branch;
+	}
+
+	public void execute() throws IOException {
+		try {
+			git._branchDelete().setBranchNames(branch.getName()).setForce(true).call();
+		} catch (final GitAPIException e) {
+			throw new IOException(e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DiffBranches.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DiffBranches.java
new file mode 100644
index 0000000000..29174cfec1
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/DiffBranches.java
@@ 0,0 +1,114 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.ByteArrayOutputStream;
+import java.io.IOException;
+import java.io.OutputStream;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.List;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.diff.DiffFormatter;
+import org.eclipse.jgit.diff.Edit;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectLoader;
+import org.eclipse.jgit.niofs.fs.attribute.FileDiff;
+import org.eclipse.jgit.niofs.internal.FileDiffImpl;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.patch.FileHeader;
+
+/**
+ * Implements the Git Diff command between branches for bare repositories. It
+ * needs the repository, and the two branches from that repository you want to
+ * diff. It returns a list of DiffFile with differences between branches.
+ */
+public class DiffBranches {
+
+	private final Git git;
+	private final String branchA;
+	private final String branchB;
+
+	public DiffBranches(Git git, String branchA, String branchB) {
+		this.git = checkNotNull("git", git);
+		this.branchA = checkNotEmpty("branchA", branchA);
+		this.branchB = checkNotEmpty("branchB", branchB);
+	}
+
+	public List<FileDiff> execute() {
+		final List<FileDiff> diffs = new ArrayList<>();
+
+		final List<DiffEntry> result = git.listDiffs(git.getTreeFromRef(this.branchA),
+				git.getTreeFromRef(this.branchB));
+
+		final DiffFormatter formatter = createFormatter();
+
+		result.forEach(elem > {
+			final FileHeader header = getFileHeader(formatter, elem);
+			header.toEditList().forEach(edit > diffs.add(createFileDiff(elem, header, edit)));
+		});
+
+		return diffs;
+	}
+
+	private FileHeader getFileHeader(final DiffFormatter formatter, final DiffEntry elem) {
+		try {
+			return formatter.toFileHeader(elem);
+		} catch (IOException e) {
+			throw new GitException("A problem occurred when trying to obtain diffs between files", e);
+		}
+	}
+
+	private DiffFormatter createFormatter() {
+
+		OutputStream outputStream = new ByteArrayOutputStream();
+		DiffFormatter formatter = new DiffFormatter(outputStream);
+		formatter.setRepository(git.getRepository());
+		return formatter;
+	}
+
+	private FileDiff createFileDiff(final DiffEntry elem, final FileHeader header, final Edit edit) {
+		try {
+			final String changeType = header.getChangeType().toString();
+			final int startA = edit.getBeginA();
+			final int endA = edit.getEndA();
+			final int startB = edit.getBeginB();
+			final int endB = edit.getEndB();
+
+			String pathA = header.getOldPath();
+			String pathB = header.getNewPath();
+
+			final List<String> linesA = getLines(elem.getOldId().toObjectId(), startA, endA);
+			final List<String> linesB = getLines(elem.getNewId().toObjectId(), startB, endB);
+
+			return new FileDiffImpl(pathA, pathB, startA, endA, startB, endB, changeType, linesA, linesB);
+		} catch (IOException e) {
+			throw new GitException("A problem occurred when trying to obtain diffs between files", e);
+		}
+	}
+
+	private List<String> getLines(final ObjectId id, final int fromStart, final int fromEnd) throws IOException {
+		List<String> lines = new ArrayList<>();
+		if (!id.equals(ObjectId.zeroId())) {
+			final ByteArrayOutputStream stream = new ByteArrayOutputStream();
+			final ObjectLoader loader = git.getRepository().open(id);
+			loader.copyTo(stream);
+			final String content = stream.toString();
+			final List<String> filteredLines = Arrays.asList(content.split("\n"));
+			lines = filteredLines.subList(fromStart, fromEnd);
+		}
+		return lines;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fetch.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fetch.java
new file mode 100644
index 0000000000..ce7604fd37
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fetch.java
@@ 0,0 +1,56 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.AbstractMap;
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.api.errors.InvalidRemoteException;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.RefSpec;
+
+public class Fetch {
+
+	private final GitImpl git;
+	private final CredentialsProvider credentialsProvider;
+	private final Map.Entry<String, String> remote;
+	private final Collection<RefSpec> refSpecs;
+
+	public Fetch(final GitImpl git, final CredentialsProvider credentialsProvider, final Collection<RefSpec> refSpecs) {
+		this.git = git;
+		this.credentialsProvider = credentialsProvider;
+		this.refSpecs = refSpecs;
+		this.remote = new AbstractMap.SimpleEntry<>("origin", null);
+	}
+
+	public Fetch(final GitImpl git, final CredentialsProvider credentialsProvider,
+			final Map.Entry<String, String> remote, final Collection<RefSpec> refSpecs) {
+		this.git = git;
+		this.credentialsProvider = credentialsProvider;
+		this.remote = remote;
+		this.refSpecs = refSpecs;
+	}
+
+	public void execute() throws InvalidRemoteException {
+		try {
+			final List<RefSpec> specs = git.updateRemoteConfig(remote, refSpecs);
+
+			git._fetch().setCredentialsProvider(credentialsProvider).setRemote(remote.getKey()).setRefSpecs(specs)
+					.call();
+		} catch (final InvalidRemoteException e) {
+			throw e;
+		} catch (final Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fork.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fork.java
new file mode 100644
index 0000000000..7dac717dd8
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Fork.java
@@ 0,0 +1,81 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.List;
+
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class Fork {
+
+	private static final String DOT_GIT_EXT = ".git";
+	private final KetchLeaderCache leaders;
+	private Logger logger = LoggerFactory.getLogger(Fork.class);
+
+	private File parentFolder;
+	private final String source;
+	private final String target;
+	private final List<String> branches;
+	private CredentialsProvider credentialsProvider;
+	private final File hookDir;
+	private final boolean sslVerify;
+
+	public Fork(final File parentFolder, final String source, final String target, final List<String> branches,
+			final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders, final File hookDir) {
+
+		this(parentFolder, source, target, branches, credentialsProvider, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	public Fork(final File parentFolder, final String source, final String target, final List<String> branches,
+			final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders, final File hookDir,
+			final boolean sslVerify) {
+		this.parentFolder = checkNotNull("parentFolder", parentFolder);
+		this.source = checkNotEmpty("source", source);
+		this.target = checkNotEmpty("target", target);
+		this.branches = branches;
+		this.credentialsProvider = checkNotNull("credentialsProvider", credentialsProvider);
+		this.leaders = leaders;
+
+		this.hookDir = hookDir;
+
+		this.sslVerify = sslVerify;
+	}
+
+	public Git execute() throws IOException {
+
+		if (logger.isDebugEnabled()) {
+			logger.debug("Forking repository <{}> to <{}>", source, target);
+		}
+
+		final File origin = new File(parentFolder, source + DOT_GIT_EXT);
+		final File destination = new File(parentFolder, target + DOT_GIT_EXT);
+
+		if (destination.exists()) {
+			String message = String.format("Cannot fork because destination repository <%s> already exists", target);
+			logger.error(message);
+			throw new GitException(message);
+		}
+
+		return Git.clone(destination, origin.toPath().toUri().toString(), false, branches, credentialsProvider, leaders,
+				hookDir, sslVerify);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GarbageCollector.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GarbageCollector.java
new file mode 100644
index 0000000000..d1a4001a89
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GarbageCollector.java
@@ 0,0 +1,40 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.api.errors.JGitInternalException;
+import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+public class GarbageCollector {
+
+	private Logger logger = LoggerFactory.getLogger(GarbageCollector.class);
+
+	private final GitImpl git;
+
+	public GarbageCollector(final GitImpl git) {
+		this.git = git;
+	}
+
+	public void execute() {
+		try {
+			if (!(git.getRepository().getRefDatabase() instanceof RefTreeDatabase)) {
+				git._gc().call();
+			}
+		} catch (GitAPIException | JGitInternalException e) {
+			if (this.logger.isDebugEnabled()) {
+				this.logger.error("Garbage collector can't perform this operation right now, please try it later.", e);
+			}
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommit.java
new file mode 100644
index 0000000000..a8fa7541d8
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommit.java
@@ 0,0 +1,42 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevWalk;
+
+public class GetCommit {
+
+	private final Git git;
+	private final String commitId;
+
+	public GetCommit(final Git git, final String commitId) {
+		this.git = checkNotNull("git", git);
+		this.commitId = checkNotEmpty("commitId", commitId);
+	}
+
+	public RevCommit execute() {
+		final Repository repository = git.getRepository();
+
+		try (final RevWalk revWalk = new RevWalk(repository)) {
+			final ObjectId id = repository.resolve(this.commitId);
+			return id != null ? revWalk.parseCommit(id) : null;
+		} catch (Exception e) {
+			throw new GitException("Error when trying to get commit", e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommonAncestorCommit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommonAncestorCommit.java
new file mode 100644
index 0000000000..649565a727
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetCommonAncestorCommit.java
@@ 0,0 +1,45 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.revwalk.filter.RevFilter;
+
+public class GetCommonAncestorCommit {
+
+	private final Git git;
+	private final RevCommit commitA;
+	private final RevCommit commitB;
+
+	public GetCommonAncestorCommit(final Git git, final RevCommit commitA, final RevCommit commitB) {
+		this.git = checkNotNull("git", git);
+		this.commitA = checkNotNull("commitA", commitA);
+		this.commitB = checkNotNull("commitB", commitB);
+	}
+
+	public RevCommit execute() {
+		try (final RevWalk revWalk = new RevWalk(git.getRepository())) {
+			final RevCommit validatedCommitA = revWalk.lookupCommit(this.commitA);
+			final RevCommit validatedCommitB = revWalk.lookupCommit(this.commitB);
+
+			revWalk.setRevFilter(RevFilter.MERGE_BASE);
+			revWalk.markStart(validatedCommitA);
+			revWalk.markStart(validatedCommitB);
+			return revWalk.next();
+		} catch (Exception e) {
+			throw new GitException("Error when trying to get common ancestor", e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetFirstCommit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetFirstCommit.java
new file mode 100644
index 0000000000..c8660421c3
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetFirstCommit.java
@@ 0,0 +1,44 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevSort;
+import org.eclipse.jgit.revwalk.RevWalk;
+
+public class GetFirstCommit {
+
+	private final Git git;
+	private final Ref ref;
+
+	public GetFirstCommit(final Git git, final String branchName) {
+		this(git, git.getRef(branchName));
+	}
+
+	public GetFirstCommit(final Git git, final Ref ref) {
+		this.git = git;
+		this.ref = ref;
+	}
+
+	public RevCommit execute() throws IOException {
+		try (final RevWalk rw = new RevWalk(git.getRepository())) {
+			final RevCommit root = rw.parseCommit(ref.getObjectId());
+			rw.sort(RevSort.REVERSE);
+			rw.markStart(root);
+			return rw.next();
+		} catch (final IOException ignored) {
+		}
+		return null;
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetLastCommit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetLastCommit.java
new file mode 100644
index 0000000000..8af6503858
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetLastCommit.java
@@ 0,0 +1,38 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class GetLastCommit {
+
+	private final Git git;
+	private final Ref ref;
+
+	public GetLastCommit(final Git git, final String branchName) {
+		this(git, git.getRef(branchName));
+	}
+
+	public GetLastCommit(final Git git, final Ref ref) {
+		this.git = git;
+		this.ref = ref;
+	}
+
+	public RevCommit execute() throws IOException {
+		if (ref == null) {
+			return null;
+		}
+		return git.resolveRevCommit(ref.getTarget().getObjectId());
+	}
+}
\ No newline at end of file
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetPathInfo.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetPathInfo.java
new file mode 100644
index 0000000000..61f5e7cbf9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetPathInfo.java
@@ 0,0 +1,69 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.lib.FileMode;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.niofs.internal.op.model.PathType;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.PathFilter;
+
+import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
+
+public class GetPathInfo {
+
+	private final Git git;
+	private final String branchName;
+	private final String path;
+
+	public GetPathInfo(final Git git, final String branchName, final String path) {
+		this.git = git;
+		this.branchName = branchName;
+		this.path = path;
+	}
+
+	public PathInfo execute() throws IOException {
+
+		final String gitPath = PathUtil.normalize(path);
+
+		if (gitPath.isEmpty()) {
+			return new PathInfo(null, gitPath, PathType.DIRECTORY);
+		}
+
+		final ObjectId tree = git.getTreeFromRef(branchName);
+		if (tree == null) {
+			return new PathInfo(null, gitPath, PathType.NOT_FOUND);
+		}
+		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
+			tw.setFilter(PathFilter.create(gitPath));
+			tw.reset(tree);
+			while (tw.next()) {
+				if (tw.getPathString().equals(gitPath)) {
+					if (tw.getFileMode(0).equals(FileMode.TYPE_TREE)) {
+						return new PathInfo(tw.getObjectId(0), gitPath, PathType.DIRECTORY);
+					} else if (tw.getFileMode(0).equals(FileMode.TYPE_FILE)
+							|| tw.getFileMode(0).equals(FileMode.EXECUTABLE_FILE)
+							|| tw.getFileMode(0).equals(FileMode.REGULAR_FILE)) {
+						final long size = tw.getObjectReader().getObjectSize(tw.getObjectId(0), OBJ_BLOB);
+						return new PathInfo(tw.getObjectId(0), gitPath, PathType.FILE, size);
+					}
+				}
+				if (tw.isSubtree()) {
+					tw.enterSubtree();
+				}
+			}
+		}
+		return new PathInfo(null, gitPath, PathType.NOT_FOUND);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetRef.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetRef.java
new file mode 100644
index 0000000000..63a1fcbb65
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetRef.java
@@ 0,0 +1,50 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectIdRef;
+import org.eclipse.jgit.lib.ObjectLoader;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+
+import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
+
+public class GetRef {
+
+	private final Repository repo;
+	private final String name;
+
+	public GetRef(final Repository repo, final String name) {
+		this.repo = repo;
+		this.name = name;
+	}
+
+	public Ref execute() {
+		try {
+			final Ref value = repo.getRefDatabase().findRef(name);
+			if (value != null) {
+				return value;
+			}
+			final ObjectId treeRef = repo.resolve(name + "^{tree}");
+			if (treeRef != null) {
+				try (final ObjectReader objectReader = repo.getObjectDatabase().newReader()) {
+					final ObjectLoader loader = objectReader.open(treeRef);
+					if (loader.getType() == OBJ_TREE) {
+						return new ObjectIdRef.PeeledTag(Ref.Storage.NEW, name, ObjectId.fromString(name), treeRef);
+					}
+				}
+			}
+		} catch (final Exception ignored) {
+		}
+		return null;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetTreeFromRef.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetTreeFromRef.java
new file mode 100644
index 0000000000..b6289a7076
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/GetTreeFromRef.java
@@ 0,0 +1,33 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class GetTreeFromRef {
+
+	private final Git git;
+	private final String treeRefName;
+
+	public GetTreeFromRef(final Git git, final String treeRefName) {
+		this.git = git;
+		this.treeRefName = treeRefName;
+	}
+
+	public ObjectId execute() {
+		final RevCommit commit = git.getLastCommit(treeRefName);
+		if (commit == null) {
+			return null;
+		}
+		return commit.getTree().getId();
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListCommits.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListCommits.java
new file mode 100644
index 0000000000..80c96455f1
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListCommits.java
@@ 0,0 +1,160 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.HashMap;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.diff.DiffConfig;
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.errors.IncorrectObjectTypeException;
+import org.eclipse.jgit.errors.MissingObjectException;
+import org.eclipse.jgit.lib.AnyObjectId;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.eclipse.jgit.niofs.internal.op.model.CommitHistory;
+import org.eclipse.jgit.revwalk.FollowFilter;
+import org.eclipse.jgit.revwalk.RenameCallback;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevSort;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.revwalk.TreeRevFilter;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.PathFilter;
+import org.eclipse.jgit.treewalk.filter.TreeFilter;
+
+import static java.util.stream.Collectors.toList;
+import static java.util.stream.StreamSupport.stream;
+
+public class ListCommits {
+
+	private final Git git;
+	private final ObjectId startRange;
+	private final ObjectId endRange;
+	private final String path;
+
+	public ListCommits(final Git git, final Ref ref, final String path) {
+		this.git = git;
+		this.path = makeRelative(path);
+		this.startRange = null;
+		this.endRange = ref.getObjectId();
+	}
+
+	private static String makeRelative(String path) {
+		return (path != null && path.startsWith("/")) ? path.substring(1) : path;
+	}
+
+	public ListCommits(final GitImpl git, final ObjectId startRange, final ObjectId endRange) {
+		this.git = git;
+		this.startRange = startRange;
+		this.endRange = endRange;
+		this.path = null;
+	}
+
+	public CommitHistory execute() throws IOException, GitAPIException {
+		try (final RevWalk rw = buildWalk()) {
+			if (path == null || path.isEmpty()) {
+				return fullCommitHistory(rw);
+			} else {
+				return pathCommitHistory(rw);
+			}
+		}
+	}
+
+	private CommitHistory pathCommitHistory(final RevWalk rw)
+			throws MissingObjectException, IncorrectObjectTypeException, IOException {
+		final Map<AnyObjectId, String> pathByCommit = new HashMap<>();
+		final List<RevCommit> commits = new ArrayList<>();
+		final RenameCaptor renameCaptor = new RenameCaptor();
+		/*
+		 * We have to go through all commits and filter ourselves so that we can use the
+		 * rename callback to map commits to path renames.
+		 */
+		final TreeRevFilter revFilter = createTreeRevFilter(rw, path, renameCaptor);
+		String curPath = path;
+		for (final RevCommit commit : rw) {
+			if (revFilter.include(rw, commit)) {
+				@SuppressWarnings("resource")
+				final TreeWalk tw = new TreeWalk(rw.getObjectReader());
+				tw.setRecursive(true);
+				tw.setFilter(PathFilter.create(curPath));
+				tw.addTree(commit.getTree());
+				// Checks for special case that path wasn't deleted in this commit
+				if (tw.next()) {
+					commits.add(commit);
+					// There is a rename to track
+					pathByCommit.put(commit.getId(), curPath);
+					if (renameCaptor.hasCaptured()) {
+						curPath = renameCaptor.getAndReset().getOldPath();
+					}
+				}
+			}
+		}
+
+		return new CommitHistory(commits, pathByCommit, path);
+	}
+
+	private CommitHistory fullCommitHistory(final RevWalk rw) {
+		final List<RevCommit> commits = stream(rw.spliterator(), false).collect(toList());
+		return new CommitHistory(commits, Collections.emptyMap(), null);
+	}
+
+	private TreeRevFilter createTreeRevFilter(final RevWalk rw, String curPath, final RenameCallback renameCallback) {
+		final FollowFilter followFilter = FollowFilter.create(curPath,
+				git.getRepository().getConfig().get(DiffConfig.KEY));
+		followFilter.setRenameCallback(renameCallback);
+		final TreeRevFilter revFilter = new TreeRevFilter(rw, followFilter);
+		return revFilter;
+	}
+
+	private RevWalk buildWalk() throws GitAPIException, IOException {
+		final RevWalk rw = new RevWalk(git.getRepository());
+		rw.setTreeFilter(TreeFilter.ANY_DIFF);
+		rw.markStart(rw.parseCommit(endRange));
+		rw.sort(RevSort.TOPO);
+		if (startRange != null) {
+			rw.markUninteresting(rw.parseCommit(startRange));
+		}
+
+		return rw;
+	}
+
+	private static class RenameCaptor extends RenameCallback {
+
+		private DiffEntry captured;
+
+		@Override
+		public void renamed(final DiffEntry entry) {
+			captured = entry;
+		}
+
+		public boolean hasCaptured() {
+			return captured != null;
+		}
+
+		public DiffEntry getAndReset() {
+			if (captured == null) {
+				throw new NullPointerException("Cannot get DiffEntry when none was captured.");
+			}
+
+			final DiffEntry retVal = captured;
+			captured = null;
+
+			return retVal;
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListDiffs.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListDiffs.java
new file mode 100644
index 0000000000..af84e349e7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListDiffs.java
@@ 0,0 +1,52 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.List;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.treewalk.CanonicalTreeParser;
+
+import static java.util.Collections.emptyList;
+
+public class ListDiffs {
+
+	private final Git git;
+	private final ObjectId oldRef;
+	private final ObjectId newRef;
+
+	public ListDiffs(final Git git, final ObjectId oldRef, final ObjectId newRef) {
+		this.git = git;
+		this.oldRef = oldRef;
+		this.newRef = newRef;
+	}
+
+	public List<DiffEntry> execute() {
+		if (newRef == null || git.getRepository() == null) {
+			return emptyList();
+		}
+
+		try (final ObjectReader reader = git.getRepository().newObjectReader()) {
+			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
+			if (oldRef != null) {
+				oldTreeIter.reset(reader, oldRef);
+			}
+			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
+			newTreeIter.reset(reader, newRef);
+			return new CustomDiffCommand(git).setNewTree(newTreeIter).setOldTree(oldTreeIter)
+					.setShowNameAndStatusOnly(true).call();
+		} catch (final Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListPathContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListPathContent.java
new file mode 100644
index 0000000000..7a95813ffc
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListPathContent.java
@@ 0,0 +1,65 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.List;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.PathFilter;
+
+public class ListPathContent {
+
+	private final Git git;
+	private final String branchName;
+	private final String path;
+
+	public ListPathContent(final Git git, final String branchName, final String path) {
+		this.git = git;
+		this.branchName = branchName;
+		this.path = path;
+	}
+
+	public List<PathInfo> execute() throws IOException {
+
+		final String gitPath = PathUtil.normalize(path);
+		final List<PathInfo> result = new ArrayList<>();
+		final ObjectId tree = git.getTreeFromRef(branchName);
+		if (tree == null) {
+			return result;
+		}
+		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
+			boolean found = false;
+			if (gitPath.isEmpty()) {
+				found = true;
+			} else {
+				tw.setFilter(PathFilter.create(gitPath));
+			}
+			tw.reset(tree);
+			while (tw.next()) {
+				if (!found && tw.isSubtree()) {
+					tw.enterSubtree();
+				}
+				if (tw.getPathString().equals(gitPath)) {
+					found = true;
+					continue;
+				}
+				if (found) {
+					result.add(new PathInfo(tw.getObjectId(0), tw.getPathString(), tw.getFileMode(0)));
+				}
+			}
+			return result;
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListRefs.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListRefs.java
new file mode 100644
index 0000000000..83fd60e802
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ListRefs.java
@@ 0,0 +1,33 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.ArrayList;
+import java.util.List;
+
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.Repository;
+
+public class ListRefs {
+
+	private final Repository repo;
+
+	public ListRefs(final Repository repo) {
+		this.repo = repo;
+	}
+
+	public List<Ref> execute() {
+		try {
+			return new ArrayList<>(repo.getRefDatabase().getRefsByPrefix("refs/heads/"));
+		} catch (java.io.IOException e) {
+			throw new RuntimeException(e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/MapDiffContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/MapDiffContent.java
new file mode 100644
index 0000000000..5d1751edfd
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/MapDiffContent.java
@@ 0,0 +1,75 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.io.IOException;
+import java.io.InputStream;
+import java.nio.file.Files;
+import java.nio.file.StandardCopyOption;
+import java.util.HashMap;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class MapDiffContent {
+
+	private final Git git;
+	private final String branch;
+	private final String startCommitId;
+	private final String endCommitId;
+
+	public MapDiffContent(final Git git, final String branch, final String startCommitId, final String endCommitId) {
+		this.git = checkNotNull("git", git);
+		this.branch = checkNotEmpty("branch", branch);
+		this.startCommitId = checkNotEmpty("startCommitId", startCommitId);
+		this.endCommitId = checkNotEmpty("endCommitId", endCommitId);
+	}
+
+	public Map<String, File> execute() {
+		BranchUtil.existsBranch(git, branch);
+
+		final RevCommit startCommit = git.getCommit(startCommitId);
+		final RevCommit endCommit = git.getCommit(endCommitId);
+
+		if (startCommit == null || endCommit == null) {
+			throw new GitException("Given commit ids cannot be found.");
+		}
+
+		Map<String, File> content = new HashMap<>();
+
+		final List<DiffEntry> diffs = git.listDiffs(startCommit.getTree(), endCommit.getTree());
+
+		diffs.forEach(entry > {
+			if (entry.getChangeType() != DiffEntry.ChangeType.DELETE) {
+				try (final InputStream inputStream = git.blobAsInputStream(branch, entry.getNewPath())) {
+					final File file = File.createTempFile("gitz", "woot");
+
+					Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
+
+					content.put(entry.getNewPath(), file);
+				} catch (IOException e) {
+					throw new GitException("Unable to get content from diffs", e);
+				}
+			} else {
+				content.put(entry.getOldPath(), null);
+			}
+		});
+
+		return content;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Merge.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Merge.java
new file mode 100644
index 0000000000..3ac9212458
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Merge.java
@@ 0,0 +1,150 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.Arrays;
+import java.util.Collections;
+import java.util.List;
+import java.util.Map;
+import java.util.stream.Collectors;
+import java.util.stream.Stream;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.lib.AnyObjectId;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.merge.MergeStrategy;
+import org.eclipse.jgit.merge.ThreeWayMerger;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.niofs.internal.op.model.MergeCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.MessageCommitInfo;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+/**
+ * Implements Git Merge command between branches in a bare repository. Branches
+ * needs to be part of the same repository, you cannot merge branches from
+ * different repositories (or forks). This command is based on Git Cherry Pick
+ * command for a simple fast forward merge, otherwise it will create a merge
+ * commit. It returns the list of commits involved in the merge operation.
+ */
+public class Merge {
+
+	private Logger logger = LoggerFactory.getLogger(Merge.class);
+
+	private final Git git;
+	private final String sourceBranch;
+	private final String targetBranch;
+	private final boolean noFastForward;
+
+	public Merge(final Git git, final String sourceBranch, final String targetBranch) {
+		this(git, sourceBranch, targetBranch, false);
+	}
+
+	public Merge(final Git git, final String sourceBranch, final String targetBranch, final boolean noFastForward) {
+
+		this.git = checkNotNull("git", git);
+		this.sourceBranch = checkNotEmpty("sourceBranch", sourceBranch);
+		this.targetBranch = checkNotEmpty("targetBranch", targetBranch);
+
+		this.noFastForward = noFastForward;
+	}
+
+	public List<String> execute() throws IOException {
+		BranchUtil.existsBranch(git, sourceBranch);
+		BranchUtil.existsBranch(git, targetBranch);
+
+		final RevCommit lastSourceCommit = git.getLastCommit(sourceBranch);
+		final RevCommit lastTargetCommit = git.getLastCommit(targetBranch);
+
+		final RevCommit commonAncestor = git.getCommonAncestorCommit(sourceBranch, targetBranch);
+
+		canMerge(git.getRepository(), commonAncestor, lastSourceCommit, lastTargetCommit, sourceBranch, targetBranch);
+
+		return proceedMerge(commonAncestor, lastSourceCommit, lastTargetCommit);
+	}
+
+	private List<String> proceedMerge(final RevCommit commonAncestor, final RevCommit lastSourceCommit,
+			final RevCommit lastTargetCommit) throws IOException {
+		final List<DiffEntry> diffBetweenCommits = git.listDiffs(commonAncestor.getName(), lastSourceCommit.getName());
+
+		final List<DiffEntry> diffBetweenBranches = diffBetweenCommits.isEmpty() ? Collections.emptyList()
+				: git.listDiffs(git.getTreeFromRef(targetBranch), git.getTreeFromRef(sourceBranch));
+
+		if (diffBetweenBranches.isEmpty()) {
+			logger.info("There is nothing to merge from branch {} to {}", sourceBranch, targetBranch);
+			return Collections.emptyList();
+		}
+
+		final List<RevCommit> targetCommits = git.listCommits(commonAncestor, lastTargetCommit);
+
+		return targetCommits.isEmpty() && !noFastForward ? doFastForward(commonAncestor, lastSourceCommit)
+				: doMerge(commonAncestor, lastSourceCommit, lastTargetCommit);
+	}
+
+	private void canMerge(final Repository repo, final RevCommit commonAncestor, final RevCommit sourceCommitTree,
+			final RevCommit targetCommitTree, final String sourceBranch, final String targetBranch) {
+		try {
+			ThreeWayMerger merger = MergeStrategy.RECURSIVE.newMerger(repo, true);
+			merger.setBase(commonAncestor);
+			boolean canMerge = merger.merge(sourceCommitTree, targetCommitTree);
+			if (!canMerge) {
+				throw new GitException(String.format("Cannot merge branches from <%s> to <%s>, merge conflicts",
+						sourceBranch, targetBranch));
+			}
+		} catch (IOException e) {
+			throw new GitException(String.format("Cannot merge branches from <%s> to <%s>, merge conflicts",
+					sourceBranch, targetBranch), e);
+		}
+	}
+
+	private List<String> doFastForward(final RevCommit commonAncestor, final RevCommit lastSourceCommit)
+			throws IOException {
+		final List<RevCommit> sourceCommits = git.listCommits(commonAncestor, lastSourceCommit);
+
+		Collections.reverse(sourceCommits);
+
+		final String[] commitsIDs = sourceCommits.stream().map(AnyObjectId::getName).toArray(String[]::new);
+
+		git.cherryPick(targetBranch, commitsIDs);
+
+		if (logger.isDebugEnabled()) {
+			logger.debug("Merging commits from <{}> to <{}>", sourceBranch, targetBranch);
+		}
+
+		return Arrays.asList(commitsIDs);
+	}
+
+	private List<String> doMerge(final RevCommit commonAncestorCommit, final RevCommit lastSourceCommit,
+			final RevCommit lastTargetCommit) {
+		try {
+			final Map<String, File> contents = git.mapDiffContent(sourceBranch, commonAncestorCommit.getName(),
+					lastSourceCommit.getName());
+
+			final List<RevCommit> parents = Stream.of(lastTargetCommit, lastSourceCommit).collect(Collectors.toList());
+
+			final boolean effective = git.commit(targetBranch, MessageCommitInfo.createMergeMessage(sourceBranch),
+					false, lastTargetCommit, new MergeCommitContent(contents, parents));
+			if (effective) {
+				return Collections.singletonList(git.getLastCommit(targetBranch).getName());
+			}
+		} catch (Exception e) {
+			logger.error(e.getMessage(), e);
+		}
+
+		throw new GitException(String.format("Cannot merge branches from <%s> to <%s>", sourceBranch, targetBranch));
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/PathUtil.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/PathUtil.java
new file mode 100644
index 0000000000..805603de1f
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/PathUtil.java
@@ 0,0 +1,33 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+public class PathUtil {
+
+	public static String normalize(final String path) {
+
+		if (path.equals("/")) {
+			return "";
+		}
+
+		final boolean startsWith = path.startsWith("/");
+		final boolean endsWith = path.endsWith("/");
+		if (startsWith && endsWith) {
+			return path.substring(1, path.length()  1);
+		}
+		if (startsWith) {
+			return path.substring(1);
+		}
+		if (endsWith) {
+			return path.substring(0, path.length()  1);
+		}
+		return path;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Push.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Push.java
new file mode 100644
index 0000000000..42fccf6429
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Push.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkInstanceOf;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.api.errors.InvalidRemoteException;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.transport.RefSpec;
+
+public class Push {
+
+	private final GitImpl git;
+	private final CredentialsProvider credentialsProvider;
+	private final Map.Entry<String, String> remote;
+	private final boolean force;
+	private final Collection<RefSpec> refSpecs;
+
+	public Push(final Git git, final CredentialsProvider credentialsProvider, final Map.Entry<String, String> remote,
+			final boolean force, final Collection<RefSpec> refSpecs) {
+		this.git = checkInstanceOf("git", git, GitImpl.class);
+		this.credentialsProvider = credentialsProvider;
+		this.remote = checkNotNull("remote", remote);
+		this.force = force;
+		this.refSpecs = refSpecs;
+	}
+
+	public void execute() throws InvalidRemoteException {
+		try {
+			final List<RefSpec> specs = new UpdateRemoteConfig(git, remote, refSpecs).execute();
+			git._push().setCredentialsProvider(credentialsProvider).setRefSpecs(specs).setRemote(remote.getKey())
+					.setForce(force).setPushAll().call();
+		} catch (final InvalidRemoteException e) {
+			throw e;
+		} catch (final Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RefTreeUpdateCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RefTreeUpdateCommand.java
new file mode 100644
index 0000000000..75f92ef032
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RefTreeUpdateCommand.java
@@ 0,0 +1,230 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static java.util.concurrent.TimeUnit.MILLISECONDS;
+import static org.eclipse.jgit.internal.ketch.Proposal.State.QUEUED;
+import static org.eclipse.jgit.lib.Constants.HEAD;
+import static org.eclipse.jgit.lib.Constants.MASTER;
+import static org.eclipse.jgit.lib.Ref.Storage.NETWORK;
+import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
+import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;
+
+import java.io.File;
+import java.io.IOException;
+import java.nio.file.Files;
+import java.nio.file.StandardCopyOption;
+import java.util.ArrayList;
+import java.util.Collections;
+import java.util.List;
+
+import org.eclipse.jgit.errors.MissingObjectException;
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.internal.ketch.Proposal;
+import org.eclipse.jgit.internal.storage.reftree.Command;
+import org.eclipse.jgit.internal.storage.reftree.RefTree;
+import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
+import org.eclipse.jgit.lib.CommitBuilder;
+import org.eclipse.jgit.lib.Constants;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectIdRef;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.PersonIdent;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.RefDatabase;
+import org.eclipse.jgit.lib.RefUpdate;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.lib.SymbolicRef;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.ConcurrentRefUpdateException;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevObject;
+import org.eclipse.jgit.revwalk.RevTag;
+import org.eclipse.jgit.revwalk.RevWalk;
+
+public class RefTreeUpdateCommand {
+
+	private final Git git;
+	private final String name;
+	private final RevCommit commit;
+
+	public RefTreeUpdateCommand(final Git git, final String branchName, final RevCommit commit) {
+		this.git = git;
+		this.name = branchName;
+		this.commit = commit;
+	}
+
+	public void execute() throws java.io.IOException, ConcurrentRefUpdateException {
+		update(git.getRepository(), Constants.R_HEADS + name, commit);
+		// this `initialization` aims to be temporary
+		// > without this cgit can't find master when cloning repos
+		if (name.equals(MASTER) && !git.isHEADInitialized()) {
+			synchronized (git.getRepository()) {
+				symRef(git, HEAD, Constants.R_HEADS + name);
+				git.setHeadAsInitialized();
+			}
+		}
+	}
+
+	private void symRef(final Git git, final String name, final String dst) throws java.io.IOException {
+		commit(git.getRepository(), null, (reader, tree) > {
+			Ref old = tree.exactRef(reader, name);
+			Ref newx = tree.exactRef(reader, dst);
+			final Command n;
+			if (newx != null) {
+				n = new Command(old, new SymbolicRef(name, newx));
+			} else {
+				n = new Command(old, new SymbolicRef(name, new ObjectIdRef.Unpeeled(Ref.Storage.NEW, dst, null)));
+			}
+			return tree.apply(Collections.singleton(n));
+		});
+	}
+
+	private void update(final Repository _repo, final String _name, final RevCommit _commit) throws IOException {
+		commit(_repo, _commit, (reader, refTree) > {
+			final Ref old = refTree.exactRef(reader, _name);
+			final List<Command> n = new ArrayList<>(1);
+			try (RevWalk rw = new RevWalk(_repo)) {
+				n.add(new Command(old, toRef(rw, _commit, _name, true)));
+				if (git.isKetchEnabled()) {
+					proposeKetch(n, _commit);
+				}
+			} catch (final IOException | InterruptedException e) {
+				String msg = JGitText.get().transactionAborted;
+				for (Command cmd : n) {
+					if (cmd.getResult() == NOT_ATTEMPTED) {
+						cmd.setResult(REJECTED_OTHER_REASON, msg);
+					}
+				}
+				throw new GitException("Error");
+				// log.error(msg, e);
+			}
+			return refTree.apply(n);
+		});
+	}
+
+	private void proposeKetch(final List<Command> n, final RevCommit _commit) throws IOException, InterruptedException {
+		final Proposal proposal = new Proposal(n).setAuthor(_commit.getAuthorIdent()).setMessage("push");
+		git.getKetchLeader().queueProposal(proposal);
+		if (proposal.isDone()) {
+			// This failed fast, e.g. conflict or bad precondition.
+			throw new GitException("Error");
+		}
+		if (proposal.getState() == QUEUED) {
+			waitForQueue(proposal);
+		}
+		if (!proposal.isDone()) {
+			waitForPropose(proposal);
+		}
+	}
+
+	private void waitForQueue(final Proposal proposal) throws InterruptedException {
+		while (!proposal.awaitStateChange(QUEUED, 250, MILLISECONDS)) {
+			System.out.println("waiting queue...");
+		}
+		switch (proposal.getState()) {
+		case RUNNING:
+		default:
+			break;
+
+		case EXECUTED:
+			break;
+
+		case ABORTED:
+			break;
+		}
+	}
+
+	private void waitForPropose(final Proposal proposal) throws InterruptedException {
+		while (!proposal.await(250, MILLISECONDS)) {
+			System.out.println("waiting propose...");
+		}
+	}
+
+	private static Ref toRef(final RevWalk rw, final ObjectId id, final String name, final boolean mustExist)
+			throws IOException {
+		if (ObjectId.zeroId().equals(id)) {
+			return null;
+		}
+
+		try {
+			RevObject o = rw.parseAny(id);
+			if (o instanceof RevTag) {
+				RevObject p = rw.peel(o);
+				return new ObjectIdRef.PeeledTag(NETWORK, name, id, p.copy());
+			}
+			return new ObjectIdRef.PeeledNonTag(NETWORK, name, id);
+		} catch (MissingObjectException e) {
+			if (mustExist) {
+				throw e;
+			}
+			return new ObjectIdRef.Unpeeled(NETWORK, name, id);
+		}
+	}
+
+	interface BiFunction {
+
+		boolean apply(final ObjectReader reader, final RefTree refTree) throws IOException;
+	}
+
+	private void commit(final Repository repo, final RevCommit original, final BiFunction fun) throws IOException {
+		try (final ObjectReader reader = repo.newObjectReader();
+				final ObjectInserter inserter = repo.newObjectInserter();
+				final RevWalk rw = new RevWalk(reader)) {
+
+			final RefTreeDatabase refdb = (RefTreeDatabase) repo.getRefDatabase();
+			final RefDatabase bootstrap = refdb.getBootstrap();
+			final RefUpdate refUpdate = bootstrap.newUpdate(refdb.getTxnCommitted(), false);
+
+			final CommitBuilder cb = new CommitBuilder();
+			final Ref ref = bootstrap.exactRef(refdb.getTxnCommitted());
+			final RefTree tree;
+			if (ref != null && ref.getObjectId() != null) {
+				tree = RefTree.read(reader, rw.parseTree(ref.getObjectId()));
+				cb.setParentId(ref.getObjectId());
+				refUpdate.setExpectedOldObjectId(ref.getObjectId());
+			} else {
+				tree = RefTree.newEmptyTree();
+				refUpdate.setExpectedOldObjectId(ObjectId.zeroId());
+			}
+
+			if (fun.apply(reader, tree)) {
+				final Ref ref2 = bootstrap.exactRef(refdb.getTxnCommitted());
+				if (ref2 == null || ref2.getObjectId().equals(ref != null ? ref.getObjectId() : null)) {
+					cb.setTreeId(tree.writeTree(inserter));
+					if (original != null) {
+						cb.setAuthor(original.getAuthorIdent());
+						cb.setCommitter(original.getAuthorIdent());
+					} else {
+						final PersonIdent personIdent = new PersonIdent("user", "user@example.com");
+						cb.setAuthor(personIdent);
+						cb.setCommitter(personIdent);
+					}
+					refUpdate.setNewObjectId(inserter.insert(cb));
+					inserter.flush();
+					final RefUpdate.Result result = refUpdate.update(rw);
+					switch (result) {
+					case NEW:
+					case FAST_FORWARD:
+						break;
+					default:
+						throw new RuntimeException(
+								repo.getDirectory() + " > " + result.toString() + " : " + refUpdate.getName());
+					}
+					final File commited = new File(repo.getDirectory(), refdb.getTxnCommitted());
+					final File accepted = new File(repo.getDirectory(), refdb.getTxnNamespace() + "accepted");
+					Files.copy(commited.toPath(), accepted.toPath(), StandardCopyOption.REPLACE_EXISTING);
+				}
+			}
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RemoveRemote.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RemoveRemote.java
new file mode 100644
index 0000000000..e58a0b9ab2
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RemoveRemote.java
@@ 0,0 +1,45 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import org.eclipse.jgit.lib.RefUpdate;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+
+public class RemoveRemote {
+
+	final PathUtil pathUtil = new PathUtil();
+	private Git git;
+	private String ref;
+	private String remote;
+
+	public RemoveRemote(final Git git, final String remote, final String ref) {
+
+		this.git = git;
+		this.ref = ref;
+		this.remote = remote;
+	}
+
+	public void execute() {
+		try {
+			// AF1715: Cleaning origin to prevent errors while importing the new generated
+			// repo.
+			git.getRepository().getConfig().unsetSection("remote", remote);
+			git.getRepository().getConfig().save();
+			RefUpdate updateRef = git.getRepository().updateRef(ref, false);
+			updateRef.setRefLogMessage(ref + " packedref deleted", false);
+			updateRef.setForceUpdate(true);
+			updateRef.delete();
+		} catch (Exception e) {
+			throw new GitException("Error when trying to remove remote", e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveObjectIds.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveObjectIds.java
new file mode 100644
index 0000000000..76657ff5ac
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveObjectIds.java
@@ 0,0 +1,53 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.ArrayList;
+import java.util.List;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.Git;
+
+public class ResolveObjectIds {
+
+	private final Git git;
+	private final String[] ids;
+
+	public ResolveObjectIds(final Git git, final String... ids) {
+		this.git = git;
+		this.ids = ids;
+	}
+
+	public List<ObjectId> execute() {
+		final List<ObjectId> result = new ArrayList<>();
+
+		for (final String id : ids) {
+			try {
+				final Ref refName = git.getRef(id);
+				if (refName != null) {
+					result.add(refName.getObjectId());
+					continue;
+				}
+
+				try {
+					final ObjectId _id = ObjectId.fromString(id);
+					if (git.getRepository().getObjectDatabase().has(_id)) {
+						result.add(_id);
+					}
+				} catch (final IllegalArgumentException ignored) {
+				}
+			} catch (final java.io.IOException ignored) {
+			}
+		}
+
+		return result;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveRevCommit.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveRevCommit.java
new file mode 100644
index 0000000000..03d89414f7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/ResolveRevCommit.java
@@ 0,0 +1,34 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class ResolveRevCommit {
+
+	private final Repository repo;
+	private final ObjectId objectId;
+
+	public ResolveRevCommit(final Repository repo, final ObjectId objectId) {
+		this.repo = repo;
+		this.objectId = objectId;
+	}
+
+	public RevCommit execute() throws IOException {
+		try (final ObjectReader reader = repo.newObjectReader()) {
+			return RevCommit.parse(reader.open(objectId).getBytes());
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RevertMerge.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RevertMerge.java
new file mode 100644
index 0000000000..383db627cb
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/RevertMerge.java
@@ 0,0 +1,83 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.File;
+import java.util.List;
+import java.util.Map;
+import java.util.stream.Collectors;
+import java.util.stream.Stream;
+
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.model.MergeCommitContent;
+import org.eclipse.jgit.niofs.internal.op.model.MessageCommitInfo;
+import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class RevertMerge {
+
+	private final Git git;
+	private final String sourceBranch;
+	private final String targetBranch;
+	private final String commonAncestorCommitId;
+	private final String mergeCommitId;
+
+	public RevertMerge(final Git git, final String sourceBranch, final String targetBranch,
+			final String commonAncestorCommitId, final String mergeCommitId) {
+		this.git = checkNotNull("git", git);
+		this.sourceBranch = checkNotEmpty("sourceBranch", sourceBranch);
+		this.targetBranch = checkNotEmpty("targetBranch", targetBranch);
+		this.commonAncestorCommitId = checkNotEmpty("commonAncestorCommitId", commonAncestorCommitId);
+		this.mergeCommitId = checkNotEmpty("mergeCommitId", mergeCommitId);
+	}
+
+	public boolean execute() {
+		BranchUtil.existsBranch(git, sourceBranch);
+		BranchUtil.existsBranch(git, targetBranch);
+
+		final RevCommit lastSourceCommit = git.getLastCommit(sourceBranch);
+		final RevCommit lastTargetCommit = git.getLastCommit(targetBranch);
+
+		boolean isDone = false;
+
+		if (canRevert(lastSourceCommit, lastTargetCommit)) {
+
+			git.commit(targetBranch, MessageCommitInfo.createRevertMergeMessage(sourceBranch), false,
+					lastTargetCommit.getParent(0), new RevertCommitContent(targetBranch));
+
+			final RevCommit newLastTargetCommit = git.getLastCommit(targetBranch);
+
+			final List<RevCommit> parents = Stream.of(lastSourceCommit, newLastTargetCommit)
+					.collect(Collectors.toList());
+
+			final Map<String, File> contents = git.mapDiffContent(targetBranch, lastTargetCommit.getName(),
+					newLastTargetCommit.getName());
+
+			git.commit(sourceBranch, MessageCommitInfo.createMergeMessage(targetBranch), false, lastSourceCommit,
+					new MergeCommitContent(contents, parents));
+
+			git.commit(sourceBranch, MessageCommitInfo.createFixMergeReversionMessage(), false,
+					git.getLastCommit(sourceBranch).getParent(0), new RevertCommitContent(sourceBranch));
+
+			isDone = true;
+		}
+
+		return isDone;
+	}
+
+	private boolean canRevert(final RevCommit lastSourceCommit, final RevCommit lastTargetCommit) {
+		return lastTargetCommit.getParentCount() > 1 && lastTargetCommit.getName().equals(mergeCommitId)
+				&& lastTargetCommit.getParent(0).getName().equals(commonAncestorCommitId)
+				&& lastTargetCommit.getParent(1).getName().equals(lastSourceCommit.getName());
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SimpleRefUpdateCommand.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SimpleRefUpdateCommand.java
new file mode 100644
index 0000000000..30364576ff
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SimpleRefUpdateCommand.java
@@ 0,0 +1,66 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.text.MessageFormat;
+
+import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
+import org.eclipse.jgit.api.errors.JGitInternalException;
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.lib.Constants;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.RefUpdate;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class SimpleRefUpdateCommand {
+
+	private final Git git;
+	private final String name;
+	private final RevCommit commit;
+
+	public SimpleRefUpdateCommand(final Git git, final String branchName, final RevCommit commit) {
+		this.git = git;
+		this.name = branchName;
+		this.commit = commit;
+	}
+
+	public void execute() throws IOException, ConcurrentRefUpdateException {
+		final ObjectId headId = git.getLastCommit(Constants.R_HEADS + name);
+		final RefUpdate ru = git.getRepository().updateRef(Constants.R_HEADS + name);
+		if (headId == null) {
+			ru.setExpectedOldObjectId(ObjectId.zeroId());
+		} else {
+			ru.setExpectedOldObjectId(headId);
+		}
+		ru.setNewObjectId(commit.getId());
+		ru.setRefLogMessage(commit.getShortMessage(), false);
+		forceUpdate(ru, commit.getId());
+	}
+
+	private void forceUpdate(final RefUpdate ru, final ObjectId id)
+			throws java.io.IOException, ConcurrentRefUpdateException {
+		final RefUpdate.Result rc = ru.forceUpdate();
+		switch (rc) {
+		case NEW:
+		case FORCED:
+		case FAST_FORWARD:
+		case NO_CHANGE:
+			break;
+		case REJECTED:
+		case LOCK_FAILURE:
+			throw new ConcurrentRefUpdateException(JGitText.get().couldNotLockHEAD, ru.getRef(), rc);
+		default:
+			throw new JGitInternalException(
+					MessageFormat.format(JGitText.get().updatingRefFailed, Constants.HEAD, id.toString(), rc));
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Squash.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Squash.java
new file mode 100644
index 0000000000..e0c92cbca3
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/Squash.java
@@ 0,0 +1,98 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.util.Spliterator;
+
+import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.errors.IncorrectObjectTypeException;
+import org.eclipse.jgit.errors.MissingObjectException;
+import org.eclipse.jgit.lib.CommitBuilder;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+import static java.util.stream.StreamSupport.stream;
+
+/**
+ * Implements the Git Squash command. It needs the repository were he is going
+ * to make the squash, the squash commit message, and the start commit, to know
+ * from where he has to squash. It return an Empty Optional because is not
+ * necessary to return anything. It throws a {@link GitException} if something
+ * bad happens.
+ */
+public class Squash {
+
+	private final String branch;
+	private final GitImpl git;
+	private String squashedCommitMessage;
+	private String startCommitString;
+
+	public Squash(final GitImpl git, final String branch, final String startCommitString,
+			final String squashedCommitMessage) {
+		this.git = git;
+		this.squashedCommitMessage = squashedCommitMessage;
+		this.branch = branch;
+		this.startCommitString = startCommitString;
+	}
+
+	public void execute() {
+		final Repository repo = this.git.getRepository();
+
+		final RevCommit latestCommit = git.getLastCommit(branch);
+		final RevCommit startCommit = checkIfCommitIsPresentAtBranch(this.git, this.branch, this.startCommitString);
+
+		RevCommit parent = startCommit;
+		if (startCommit.getParentCount() > 0) {
+			parent = startCommit.getParent(0);
+		}
+
+		final CommitBuilder commitBuilder = new CommitBuilder();
+		commitBuilder.setParentId(parent);
+		commitBuilder.setTreeId(latestCommit.getTree().getId());
+		commitBuilder.setMessage(squashedCommitMessage);
+		commitBuilder.setAuthor(startCommit.getAuthorIdent());
+		commitBuilder.setCommitter(startCommit.getAuthorIdent());
+
+		try (final ObjectInserter odi = repo.newObjectInserter()) {
+			final RevCommit squashedCommit = git.resolveRevCommit(odi.insert(commitBuilder));
+			git.refUpdate(branch, squashedCommit);
+		} catch (ConcurrentRefUpdateException | IOException e) {
+			throw new GitException("Error on executing squash.", e);
+		}
+	}
+
+	/**
+	 * It checks if the commit is present on branch logs. If not it throws a
+	 * {@link GitException}
+	 * 
+	 * @param git               The git repository
+	 * @param branch            The branch where it is going to do the search
+	 * @param startCommitString The commit it needs to find
+	 * @throws {@link GitException} when it cannot find the commit in that branch
+	 */
+	private RevCommit checkIfCommitIsPresentAtBranch(final GitImpl git, final String branch,
+			final String startCommitString) {
+
+		try {
+			final ObjectId id = git.getRef(branch).getObjectId();
+			final Spliterator<RevCommit> log = git._log().add(id).call().spliterator();
+			return stream(log, false).filter((elem) > elem.getName().equals(startCommitString)).findFirst()
+					.orElseThrow(() > new GitException("Commit is not present at branch " + branch));
+		} catch (GitAPIException | MissingObjectException | IncorrectObjectTypeException e) {
+			throw new GitException("A problem occurred when trying to get commit list", e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SubdirectoryClone.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SubdirectoryClone.java
new file mode 100644
index 0000000000..784ad83fb4
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SubdirectoryClone.java
@@ 0,0 +1,367 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.File;
+import java.io.IOException;
+import java.util.ArrayList;
+import java.util.Arrays;
+import java.util.HashMap;
+import java.util.HashSet;
+import java.util.LinkedList;
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+import java.util.Queue;
+import java.util.Set;
+
+import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
+import org.eclipse.jgit.api.RemoteRemoveCommand;
+import org.eclipse.jgit.api.errors.GitAPIException;
+import org.eclipse.jgit.api.errors.InvalidRefNameException;
+import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
+import org.eclipse.jgit.api.errors.RefNotFoundException;
+import org.eclipse.jgit.dircache.DirCache;
+import org.eclipse.jgit.dircache.DirCacheEditor;
+import org.eclipse.jgit.dircache.DirCacheEntry;
+import org.eclipse.jgit.errors.AmbiguousObjectException;
+import org.eclipse.jgit.errors.CorruptObjectException;
+import org.eclipse.jgit.errors.IncorrectObjectTypeException;
+import org.eclipse.jgit.errors.MissingObjectException;
+import org.eclipse.jgit.errors.UnmergedPathException;
+import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
+import org.eclipse.jgit.lib.CommitBuilder;
+import org.eclipse.jgit.lib.FileMode;
+import org.eclipse.jgit.lib.ObjectId;
+import org.eclipse.jgit.lib.ObjectInserter;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.revwalk.RevSort;
+import org.eclipse.jgit.revwalk.RevWalk;
+import org.eclipse.jgit.revwalk.filter.RevFilter;
+import org.eclipse.jgit.storage.file.WindowCacheConfig;
+import org.eclipse.jgit.transport.CredentialsProvider;
+import org.eclipse.jgit.treewalk.CanonicalTreeParser;
+import org.eclipse.jgit.treewalk.TreeWalk;
+import org.eclipse.jgit.treewalk.filter.TreeFilter;
+import org.eclipse.jgit.util.FileUtils;
+import org.slf4j.Logger;
+import org.slf4j.LoggerFactory;
+
+import static java.lang.String.format;
+import static java.util.stream.Collectors.toList;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+/**
+ * Copies a single subdirectory of a repository, preserving all relevant
+ * history.
+ */
+public class SubdirectoryClone {
+
+	private final File repoDir;
+	private final String origin;
+	private final CredentialsProvider credentialsProvider;
+	private final KetchLeaderCache leaders;
+	private final File hookDir;
+	private final boolean sslVerify;
+
+	private Logger logger = LoggerFactory.getLogger(SubdirectoryClone.class);
+	private List<String> branches;
+	private String subdirectory;
+
+	/**
+	 * @param directory           Directory for the local target repository (created
+	 *                            by this command). Must not be null.
+	 * @param origin              URI for the repository being cloned. Must not be
+	 *                            null.
+	 * @param subdirectory        The subdirectory within the origin being copied.
+	 *                            Must not be null.
+	 * @param branches            The branches that should be copied. Must not be
+	 *                            null.
+	 * @param credentialsProvider Provides credentials for the initial cloning of
+	 *                            the origin. May be null.
+	 * @param leaders             Used for initial cloning. May be null.
+	 * @param hookDir             Used to specify the directory containing the Git
+	 *                            Hooks to add to the repository. May be null.
+	 */
+	public SubdirectoryClone(final File directory, final String origin, final String subdirectory,
+			final List<String> branches, final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders,
+			final File hookDir) {
+		this(directory, origin, subdirectory, branches, credentialsProvider, leaders, hookDir,
+				JGitFileSystemProviderConfiguration.DEFAULT_GIT_HTTP_SSL_VERIFY);
+	}
+
+	/**
+	 * @param directory           Directory for the local target repository (created
+	 *                            by this command). Must not be null.
+	 * @param origin              URI for the repository being cloned. Must not be
+	 *                            null.
+	 * @param subdirectory        The subdirectory within the origin being copied.
+	 *                            Must not be null.
+	 * @param branches            The branches that should be copied. Must not be
+	 *                            null.
+	 * @param credentialsProvider Provides credentials for the initial cloning of
+	 *                            the origin. May be null.
+	 * @param leaders             Used for initial cloning. May be null.
+	 * @param hookDir             Used to specify the directory containing the Git
+	 *                            Hooks to add to the repository. May be null.
+	 * @param sslVerify           Used to disable http ssl verify on the repository
+	 */
+	public SubdirectoryClone(final File directory, final String origin, final String subdirectory,
+			final List<String> branches, final CredentialsProvider credentialsProvider, final KetchLeaderCache leaders,
+			final File hookDir, final boolean sslVerify) {
+		this.subdirectory = ensureTrailingSlash(subdirectory);
+		this.branches = branches;
+		this.repoDir = checkNotNull("directory", directory);
+		this.origin = checkNotEmpty("origin", origin);
+		this.credentialsProvider = credentialsProvider;
+		this.leaders = leaders;
+		this.hookDir = hookDir;
+		this.sslVerify = sslVerify;
+	}
+
+	private static String ensureTrailingSlash(String subdirectory) {
+		if (subdirectory.endsWith("/")) {
+			return subdirectory;
+		} else {
+			return subdirectory + "/";
+		}
+	}
+
+	public Git execute() throws IOException {
+		final Git git = new Clone(repoDir, origin, false, branches, credentialsProvider, leaders, hookDir, sslVerify)
+				.execute().get();
+		final Repository repository = git.getRepository();
+
+		try (final ObjectReader reader = repository.newObjectReader();
+				final ObjectInserter inserter = repository.newObjectInserter()) {
+			// Map all transformed commits that are nonempty so that we can properly map
+			// parents
+			final Map<ObjectId, ObjectId> commitMap = new HashMap<>();
+			final RevWalk revWalk = createRevWalk(repository, reader);
+			transformBranches(repository, reader, inserter, revWalk, commitMap);
+			overrideBranchNames(repository, revWalk, commitMap);
+
+			removeOriginRemote(repository);
+
+			return git;
+		} catch (Exception e) {
+			String message = String.format("Error cloning origin <%s> with subdirectory <%s>.", origin, subdirectory);
+			logger.error(message);
+			cleanupDir(git.getRepository().getDirectory());
+			throw new Clone.CloneException(message, e);
+		}
+	}
+
+	private void removeOriginRemote(Repository repository) throws GitAPIException {
+		final org.eclipse.jgit.api.Git git = org.eclipse.jgit.api.Git.wrap(repository);
+		final RemoteRemoveCommand cmd = git.remoteRemove();
+		cmd.setRemoteName(origin);
+		cmd.call();
+	}
+
+	private void overrideBranchNames(final Repository repository, final RevWalk revWalk,
+			final Map<ObjectId, ObjectId> commitMap)
+			throws AmbiguousObjectException, IncorrectObjectTypeException, IOException, MissingObjectException,
+			GitAPIException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException {
+		for (String branchName : branches) {
+			if (branchName.equals("HEAD")) {
+				continue;
+			}
+
+			final ObjectId oldBranchTipId = repository.resolve(branchName);
+			final ObjectId newBranchTipId = closestMappedAncestorOrSelf(commitMap,
+					revWalk.parseCommit(oldBranchTipId))[0];
+			final RevCommit newBranchTip = revWalk.parseCommit(newBranchTipId);
+			org.eclipse.jgit.api.Git.wrap(repository).branchCreate().setName(branchName).setForce(true)
+					.setStartPoint(newBranchTip).setUpstreamMode(SetupUpstreamMode.NOTRACK).call();
+		}
+	}
+
+	private void transformBranches(final Repository repository, final ObjectReader reader,
+			final ObjectInserter inserter, final RevWalk revWalk, final Map<ObjectId, ObjectId> commitMap)
+			throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException, IOException,
+			UnmergedPathException {
+		for (final RevCommit commit : revWalk) {
+			try {
+				final Optional<ObjectId> oNewCommitTree = filterCommitTree(reader, inserter, commit);
+				if (oNewCommitTree.isPresent()) {
+					final ObjectId newCommitTree = oNewCommitTree.get();
+					final CommitBuilder commitBuilder = generateNewCommit(commitMap, commit, newCommitTree);
+					final ObjectId newCommitId = inserter.insert(commitBuilder);
+
+					/*
+					 * We want to prune empty commits (i.e. no changes from parent), which will
+					 * exist whenever an origin commit did not touch files in the subdirectory.
+					 *
+					 * But we don't want to remove merge commits.
+					 */
+					if (isOrphanCommit(commitBuilder) || isMergeCommit(commitBuilder)
+							|| isDifferentFromParent(revWalk, commitBuilder)) {
+						commitMap.put(commit.getId(), newCommitId);
+					}
+				}
+			} catch (Throwable t) {
+				throw new RuntimeException(String.format("Problem occurred for commit [%s].", commit.getId().name()),
+						t);
+			}
+		}
+	}
+
+	private boolean isOrphanCommit(final CommitBuilder commitBuilder) {
+		return commitBuilder.getParentIds().length == 0;
+	}
+
+	private boolean isDifferentFromParent(final RevWalk revWalk, final CommitBuilder commitBuilder)
+			throws MissingObjectException, IncorrectObjectTypeException, IOException {
+		final ObjectId parentId = commitBuilder.getParentIds()[0];
+		final RevCommit parentCommit = revWalk.parseCommit(parentId);
+		final ObjectId parentTreeId = parentCommit.getTree().getId();
+		final ObjectId commitTreeId = commitBuilder.getTreeId();
+		// A commit with the same tree as its parent has no changes.
+		return !commitTreeId.equals(parentTreeId);
+	}
+
+	private boolean isMergeCommit(final CommitBuilder commitBuilder) {
+		return commitBuilder.getParentIds().length > 1;
+	}
+
+	private Optional<ObjectId> filterCommitTree(final ObjectReader reader, final ObjectInserter inserter,
+			final RevCommit commit) throws MissingObjectException, IncorrectObjectTypeException, CorruptObjectException,
+			IOException, UnmergedPathException {
+		final DirCache dc = DirCache.newInCore();
+		final DirCacheEditor editor = dc.editor();
+		@SuppressWarnings("resource")
+		final TreeWalk treeWalk = new TreeWalk(reader);
+		int treeId = treeWalk.addTree(commit.getTree());
+		treeWalk.setRecursive(true);
+		boolean empty = true;
+		while (treeWalk.next()) {
+			final String pathString = treeWalk.getPathString();
+			final CanonicalTreeParser treeParser = treeWalk.getTree(treeId, CanonicalTreeParser.class);
+			if (inSubdirectory(pathString)) {
+				moveFromSubdirectoryToRoot(editor, pathString, treeParser);
+				empty = false;
+			}
+		}
+		editor.finish();
+
+		if (empty) {
+			return Optional.empty();
+		} else {
+			return Optional.of(dc.writeTree(inserter));
+		}
+	}
+
+	private RevWalk createRevWalk(final Repository repository, final ObjectReader reader)
+			throws MissingObjectException, IncorrectObjectTypeException, IOException {
+		final RevWalk revWalk = new RevWalk(reader);
+		final List<RevCommit> branchTips = getBranchCommits(repository, revWalk);
+		// So that we traverse all branch histories at once
+		revWalk.markStart(branchTips);
+
+		// Gets parents before children
+		revWalk.sort(RevSort.TOPO, true);
+		revWalk.sort(RevSort.REVERSE, true);
+
+		revWalk.setRevFilter(RevFilter.ALL);
+		revWalk.setTreeFilter(TreeFilter.ALL);
+
+		return revWalk;
+	}
+
+	private List<RevCommit> getBranchCommits(final Repository repository, final RevWalk revWalk) {
+		final List<RevCommit> branchTips = branches.stream().map(b > {
+			try {
+				return revWalk.parseCommit(repository.resolve(b));
+			} catch (IOException ioe) {
+				throw new IllegalArgumentException(
+						format("Unable to parse branch [%s] in repository [%s].", b, repository.getDirectory()));
+			}
+		}).collect(toList());
+		return branchTips;
+	}
+
+	private CommitBuilder generateNewCommit(final Map<ObjectId, ObjectId> commitMap, final RevCommit commit,
+			final ObjectId newCommitTree) {
+		final CommitBuilder commitBuilder = new CommitBuilder();
+		commitBuilder.setAuthor(commit.getAuthorIdent());
+		commitBuilder.setCommitter(commit.getCommitterIdent());
+		commitBuilder.setTreeId(newCommitTree);
+		commitBuilder.setMessage(commit.getFullMessage());
+		commitBuilder.setEncoding(commit.getEncoding());
+		final ObjectId[] newParentIds = closestMappedAncestorOrSelf(commitMap, commit.getParents());
+		if (newParentIds.length > 0) {
+			commitBuilder.setParentIds(newParentIds);
+		}
+
+		return commitBuilder;
+	}
+
+	private ObjectId[] closestMappedAncestorOrSelf(final Map<ObjectId, ObjectId> commitMap, final RevCommit... start) {
+		final Queue<RevCommit> commitQueue = new LinkedList<>();
+		final Set<ObjectId> processed = new HashSet<>();
+		commitQueue.addAll(Arrays.asList(start));
+
+		final List<ObjectId> results = new ArrayList<>();
+
+		while (!commitQueue.isEmpty()) {
+			final RevCommit cur = commitQueue.poll();
+			if (!processed.contains(cur.getId())) {
+				final ObjectId mappedId = commitMap.get(cur.getId());
+
+				// Ancestors not mapped must have been empty commits, that we ignore.
+				if (mappedId != null) {
+					results.add(mappedId);
+				} else {
+					Arrays.stream(cur.getParents()).forEach(p > commitQueue.add(p));
+				}
+				processed.add(cur.getId());
+			}
+		}
+
+		return results.toArray(new ObjectId[results.size()]);
+	}
+
+	private void moveFromSubdirectoryToRoot(final DirCacheEditor editor, final String pathString,
+			final CanonicalTreeParser treeParser) {
+		final String newPath = pathString.substring(subdirectory.length());
+		final ObjectId entryObjectId = treeParser.getEntryObjectId();
+		final FileMode entryFileMode = treeParser.getEntryFileMode();
+		editor.add(new DirCacheEditor.PathEdit(new DirCacheEntry(newPath)) {
+
+			@Override
+			public void apply(DirCacheEntry ent) {
+				ent.setObjectId(entryObjectId);
+				ent.setFileMode(entryFileMode);
+			}
+		});
+	}
+
+	private boolean inSubdirectory(final String pathString) {
+		return pathString.startsWith(subdirectory);
+	}
+
+	private void cleanupDir(final File gitDir) throws IOException {
+		try {
+			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
+				// this operation forces a cache clean freeing any lock > windows only issue!
+				new WindowCacheConfig().install();
+			}
+			FileUtils.delete(gitDir, FileUtils.RECURSIVE | FileUtils.RETRY);
+		} catch (java.io.IOException e) {
+			throw new IOException("Failed to remove the git repository.", e);
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SyncRemote.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SyncRemote.java
new file mode 100644
index 0000000000..0e2ef380f5
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/SyncRemote.java
@@ 0,0 +1,106 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.util.Collection;
+import java.util.HashSet;
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+import java.util.Set;
+
+import org.eclipse.jgit.api.CreateBranchCommand;
+import org.eclipse.jgit.api.ListBranchCommand;
+import org.eclipse.jgit.api.errors.InvalidRemoteException;
+import org.eclipse.jgit.lib.Constants;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.niofs.internal.op.GitImpl;
+
+public class SyncRemote {
+
+	private final GitImpl git;
+	private final Map.Entry<String, String> remote;
+
+	public SyncRemote(final GitImpl git, final Map.Entry<String, String> remote) {
+		this.git = git;
+		this.remote = remote;
+	}
+
+	public Optional execute() throws InvalidRemoteException {
+		try {
+			final List<Ref> branches = git._branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
+			final Set<String> remoteBranches = new HashSet<>();
+			final Set<String> localBranches = new HashSet<>();
+			fillBranches(branches, remoteBranches, localBranches);
+
+			/*
+			 * We filter out HEAD below because otherwise it appears as a branch in the UI
+			 * importing repositories.
+			 *
+			 * We may need to revisit this in the future when we support mirror
+			 * repositories.
+			 */
+
+			for (final String localBranch : localBranches) {
+				if (localBranch.equals(Constants.HEAD)) {
+					continue;
+				}
+				if (remoteBranches.contains(localBranch)) {
+					try {
+						git._branchCreate().setName(localBranch)
+								.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
+								.setStartPoint(remote.getKey() + "/" + localBranch).setForce(true).call();
+					} catch (Throwable t) {
+						throw new RuntimeException("Error creating branch [" + localBranch + "].");
+					}
+				}
+			}
+
+			remoteBranches.removeAll(localBranches);
+
+			for (final String branch : remoteBranches) {
+				if (branch.equals(Constants.HEAD)) {
+					continue;
+				}
+				try {
+					git._branchCreate().setName(branch)
+							.setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
+							.setStartPoint(remote.getKey() + "/" + branch).setForce(true).call();
+				} catch (Throwable t) {
+					throw new RuntimeException("Error creating branch [" + branch + "].");
+				}
+			}
+			return null;
+		} catch (final InvalidRemoteException e) {
+			throw e;
+		} catch (final RuntimeException re) {
+			throw re;
+		} catch (final Exception ex) {
+			throw new RuntimeException(ex);
+		}
+	}
+
+	void fillBranches(final List<Ref> branches, final Collection<String> remoteBranches,
+			final Collection<String> localBranches) {
+		for (final Ref branch : branches) {
+			final String branchFullName = branch.getName();
+			final String remotePrefix = "refs/remotes/" + remote.getKey() + "/";
+			final String localPrefix = "refs/heads/";
+
+			if (branchFullName.startsWith(remotePrefix)) {
+				remoteBranches.add(branchFullName.replaceFirst(remotePrefix, ""));
+			} else if (branchFullName.startsWith(localPrefix)) {
+				localBranches.add(branchFullName.replaceFirst(localPrefix, ""));
+			} else {
+				localBranches.add(branchFullName.substring(branchFullName.lastIndexOf("/") + 1));
+			}
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/TextualDiffBranches.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/TextualDiffBranches.java
new file mode 100644
index 0000000000..27f8ec56b2
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/TextualDiffBranches.java
@@ 0,0 +1,161 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;
+
+import java.io.ByteArrayOutputStream;
+import java.io.IOException;
+import java.io.OutputStream;
+import java.util.ArrayList;
+import java.util.HashMap;
+import java.util.List;
+import java.util.Map;
+import java.util.regex.Matcher;
+import java.util.regex.Pattern;
+import java.util.stream.Collectors;
+
+import org.eclipse.jgit.diff.DiffEntry;
+import org.eclipse.jgit.diff.DiffFormatter;
+import org.eclipse.jgit.lib.ObjectReader;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
+import org.eclipse.jgit.niofs.internal.op.model.TextualDiff;
+import org.eclipse.jgit.patch.FileHeader;
+import org.eclipse.jgit.revwalk.RevCommit;
+import org.eclipse.jgit.treewalk.CanonicalTreeParser;
+
+public class TextualDiffBranches {
+
+	private final Git git;
+	private final String branchA;
+	private final String branchB;
+	private final String commitIdBranchA;
+	private final String commitIdBranchB;
+
+	private static final String DIFF_REGEX_DELIMITER = "diff git.*";
+	private static final String DIFF_KEY = "diff git a/%s b/%s";
+
+	public TextualDiffBranches(final Git git, final String branchA, final String branchB) {
+		this(git, branchA, branchB, null, null);
+	}
+
+	public TextualDiffBranches(final Git git, final String branchA, final String branchB, final String commitIdBranchA,
+			final String commitIdBranchB) {
+		this.git = checkNotNull("git", git);
+		this.branchA = checkNotEmpty("branchA", branchA);
+		this.branchB = checkNotEmpty("branchB", branchB);
+
+		this.commitIdBranchA = commitIdBranchA;
+		this.commitIdBranchB = commitIdBranchB;
+	}
+
+	public List<TextualDiff> execute() {
+		final DiffFormatter formatter = createFormatter();
+
+		BranchUtil.existsBranch(this.git, this.branchA);
+		BranchUtil.existsBranch(this.git, this.branchB);
+
+		try (final ObjectReader reader = git.getRepository().newObjectReader()) {
+
+			final RevCommit commitA = this.commitIdBranchA != null ? git.getCommit(commitIdBranchA)
+					: git.getCommonAncestorCommit(branchA, branchB);
+
+			final RevCommit commitB = this.commitIdBranchB != null ? git.getCommit(commitIdBranchB)
+					: git.getLastCommit(branchB);
+
+			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
+			oldTreeIter.reset(reader, commitA.getTree());
+
+			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
+			newTreeIter.reset(reader, commitB.getTree());
+
+			OutputStream out = new ByteArrayOutputStream();
+			List<DiffEntry> diffEntries = new CustomDiffCommand(git).setNewTree(newTreeIter).setOldTree(oldTreeIter)
+					.setOutputStream(out).call();
+
+			List<String> parts = TextualDiffBranches.splitWithDelimiters(String.valueOf(out), DIFF_REGEX_DELIMITER);
+
+			Map<String, String> diffMap = new HashMap<>();
+			for (int i = 1, j = 0; i < parts.size(); i += 2, j++) {
+				String diffKey = buildDiffKey(diffEntries.get(j).getChangeType(), diffEntries.get(j).getOldPath(),
+						diffEntries.get(j).getNewPath());
+
+				diffMap.put(diffKey, parts.get(i));
+			}
+
+			return diffEntries.stream().map(entry > getFileHeader(formatter, entry)).map(header > {
+				int linesAdded = header.toEditList().stream().mapToInt(elem > elem.getEndB()  elem.getBeginB()).sum();
+
+				int linesDeleted = header.toEditList().stream().mapToInt(elem > elem.getEndA()  elem.getBeginA())
+						.sum();
+
+				DiffEntry.ChangeType changeType = header.getChangeType();
+
+				String diffKey = buildDiffKey(changeType, header.getOldPath(), header.getNewPath());
+
+				String diffText = diffKey + diffMap.get(diffKey);
+
+				return new TextualDiff(header.getOldPath(), header.getNewPath(), changeType.toString(), linesAdded,
+						linesDeleted, diffText);
+			}).collect(Collectors.toList());
+		} catch (final Exception e) {
+			throw new GitException("Unable to get textual diff", e);
+		}
+	}
+
+	private String buildDiffKey(final DiffEntry.ChangeType changeType, final String oldPath, final String newPath) {
+		return String.format(DIFF_KEY, changeType != DiffEntry.ChangeType.ADD ? oldPath : newPath,
+				changeType != DiffEntry.ChangeType.DELETE ? newPath : oldPath);
+	}
+
+	private DiffFormatter createFormatter() {
+		OutputStream outputStream = new ByteArrayOutputStream();
+		DiffFormatter formatter = new DiffFormatter(outputStream);
+		formatter.setRepository(git.getRepository());
+		return formatter;
+	}
+
+	private FileHeader getFileHeader(final DiffFormatter formatter, final DiffEntry elem) {
+		try {
+			return formatter.toFileHeader(elem);
+		} catch (IOException e) {
+			throw new GitException("A problem occurred when trying to obtain diffs between files", e);
+		}
+	}
+
+	private static List<String> splitWithDelimiters(String str, String regex) {
+		List<String> parts = new ArrayList<>();
+
+		Pattern p = Pattern.compile(regex);
+		Matcher m = p.matcher(str);
+
+		int lastEnd = 0;
+		while (m.find()) {
+			int start = m.start();
+			if (lastEnd != start) {
+				String nonDelim = str.substring(lastEnd, start);
+				parts.add(nonDelim);
+			}
+			String delim = m.group();
+			parts.add(delim);
+
+			lastEnd = m.end();
+		}
+
+		if (lastEnd != str.length()) {
+			String nonDelim = str.substring(lastEnd);
+			parts.add(nonDelim);
+		}
+
+		return parts;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/UpdateRemoteConfig.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/UpdateRemoteConfig.java
new file mode 100644
index 0000000000..f351eec7f2
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/UpdateRemoteConfig.java
@@ 0,0 +1,59 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.net.URISyntaxException;
+import java.util.ArrayList;
+import java.util.Collection;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.lib.StoredConfig;
+import org.eclipse.jgit.niofs.internal.op.Git;
+import org.eclipse.jgit.transport.RefSpec;
+import org.eclipse.jgit.transport.RemoteConfig;
+import org.eclipse.jgit.transport.URIish;
+
+public class UpdateRemoteConfig {
+
+	private final Git git;
+	private final Map.Entry<String, String> remote;
+	private final Collection<RefSpec> refSpecs;
+
+	public UpdateRemoteConfig(final Git git, final Map.Entry<String, String> remote,
+			final Collection<RefSpec> refSpecs) {
+		this.git = git;
+		this.remote = remote;
+		this.refSpecs = refSpecs;
+	}
+
+	public List<RefSpec> execute() throws IOException, URISyntaxException {
+		final List<RefSpec> specs = new ArrayList<>();
+		if (refSpecs == null || refSpecs.isEmpty()) {
+			specs.add(new RefSpec("+refs/heads/*:refs/remotes/" + remote.getKey() + "/*"));
+			specs.add(new RefSpec("+refs/tags/*:refs/tags/*"));
+			specs.add(new RefSpec("+refs/notes/*:refs/notes/*"));
+		} else {
+			specs.addAll(refSpecs);
+		}
+
+		final StoredConfig config = git.getRepository().getConfig();
+		final String url = config.getString("remote", remote.getKey(), "url");
+		if (url == null) {
+			final RemoteConfig remoteConfig = new RemoteConfig(git.getRepository().getConfig(), remote.getKey());
+			remoteConfig.addURI(new URIish(remote.getValue()));
+			specs.forEach(remoteConfig::addFetchRefSpec);
+			remoteConfig.update(git.getRepository().getConfig());
+			git.getRepository().getConfig().save();
+		}
+		return specs;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/WriteConfiguration.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/WriteConfiguration.java
new file mode 100644
index 0000000000..c6a849b6c8
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/commands/WriteConfiguration.java
@@ 0,0 +1,37 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.commands;
+
+import java.io.IOException;
+import java.util.function.Consumer;
+
+import org.eclipse.jgit.lib.Repository;
+import org.eclipse.jgit.lib.StoredConfig;
+
+public class WriteConfiguration {
+
+	private final Repository repo;
+	private final Consumer<StoredConfig> consumer;
+
+	public WriteConfiguration(final Repository repo, final Consumer<StoredConfig> consumer) {
+		this.repo = repo;
+		this.consumer = consumer;
+	}
+
+	public void execute() {
+		final StoredConfig cfg = repo.getConfig();
+		consumer.accept(cfg);
+		try {
+			cfg.save();
+		} catch (IOException e) {
+			e.printStackTrace();
+		}
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/ConcurrentRefUpdateException.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/ConcurrentRefUpdateException.java
new file mode 100644
index 0000000000..d4316a14ca
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/ConcurrentRefUpdateException.java
@@ 0,0 +1,29 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.exceptions;
+
+import java.text.MessageFormat;
+
+import org.eclipse.jgit.internal.JGitText;
+import org.eclipse.jgit.lib.Ref;
+import org.eclipse.jgit.lib.RefUpdate;
+
+public class ConcurrentRefUpdateException extends GitException {
+
+	private RefUpdate.Result rc;
+	private Ref ref;
+
+	public ConcurrentRefUpdateException(final String message, final Ref ref, final RefUpdate.Result rc) {
+		super(rc == null ? message
+				: message + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas, new Object[] { rc }));
+		this.rc = rc;
+		this.ref = ref;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/GitException.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/GitException.java
new file mode 100644
index 0000000000..a9317744fd
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/exceptions/GitException.java
@@ 0,0 +1,21 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.exceptions;
+
+public class GitException extends RuntimeException {
+
+	public GitException(final String message) {
+		super(message);
+	}
+
+	public GitException(final String message, final Throwable t) {
+		super(message, t);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitContent.java
new file mode 100644
index 0000000000..c43ae6dbb7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitContent.java
@@ 0,0 +1,14 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+public interface CommitContent {
+
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitHistory.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitHistory.java
new file mode 100644
index 0000000000..8b599ecb7f
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitHistory.java
@@ 0,0 +1,49 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.util.List;
+import java.util.Map;
+import java.util.Optional;
+
+import org.eclipse.jgit.lib.AnyObjectId;
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class CommitHistory {
+
+	private final List<RevCommit> commits;
+	private final Map<AnyObjectId, String> pathsByCommit;
+	private final String trackedPath;
+
+	public CommitHistory(final List<RevCommit> commits, final Map<AnyObjectId, String> pathsByCommit,
+			final String trackedPath) {
+		this.commits = commits;
+		this.pathsByCommit = pathsByCommit;
+		this.trackedPath = trackedPath;
+	}
+
+	public List<RevCommit> getCommits() {
+		return commits;
+	}
+
+	/**
+	 * @return The initial file path that was followed, or else the root path (/) if
+	 *         none was given.
+	 */
+	public String getTrackedFilePath() {
+		return (trackedPath == null) ? "/" : trackedPath;
+	}
+
+	public String trackedFileNameChangeFor(final AnyObjectId commitId) {
+		return Optional.ofNullable(pathsByCommit.get(commitId)).map(path > "/" + path)
+				.orElseGet(() > getTrackedFilePath());
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitInfo.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitInfo.java
new file mode 100644
index 0000000000..45d679dd56
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CommitInfo.java
@@ 0,0 +1,57 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.util.Date;
+import java.util.TimeZone;
+
+public class CommitInfo {
+
+	private final String sessionId;
+	private final String name;
+	private final String email;
+	private final String message;
+	private final TimeZone timeZone;
+	private final Date when;
+
+	public CommitInfo(final String sessionId, final String name, final String email, final String message,
+			final TimeZone timeZone, final Date when) {
+		this.sessionId = sessionId;
+		this.name = name;
+		this.email = email;
+		this.message = message;
+		this.timeZone = timeZone;
+		this.when = when;
+	}
+
+	public String getSessionId() {
+		return sessionId;
+	}
+
+	public String getName() {
+		return name;
+	}
+
+	public String getEmail() {
+		return email;
+	}
+
+	public String getMessage() {
+		return message;
+	}
+
+	public TimeZone getTimeZone() {
+		return timeZone;
+	}
+
+	public Date getWhen() {
+		return when;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CopyCommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CopyCommitContent.java
new file mode 100644
index 0000000000..0bb56b164c
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/CopyCommitContent.java
@@ 0,0 +1,25 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.util.Map;
+
+public class CopyCommitContent implements CommitContent {
+
+	private final Map<String, String> content;
+
+	public CopyCommitContent(final Map<String, String> content) {
+		this.content = content;
+	}
+
+	public Map<String, String> getContent() {
+		return content;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/DefaultCommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/DefaultCommitContent.java
new file mode 100644
index 0000000000..8ff1c16583
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/DefaultCommitContent.java
@@ 0,0 +1,26 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.io.File;
+import java.util.Map;
+
+public class DefaultCommitContent implements CommitContent {
+
+	private final Map<String, File> content;
+
+	public DefaultCommitContent(final Map<String, File> content) {
+		this.content = content;
+	}
+
+	public Map<String, File> getContent() {
+		return content;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MergeCommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MergeCommitContent.java
new file mode 100644
index 0000000000..0e10163cc7
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MergeCommitContent.java
@@ 0,0 +1,31 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.io.File;
+import java.util.List;
+import java.util.Map;
+
+import org.eclipse.jgit.revwalk.RevCommit;
+
+public class MergeCommitContent extends DefaultCommitContent {
+
+	private final List<RevCommit> parents;
+
+	public MergeCommitContent(final Map<String, File> content, final List<RevCommit> parents) {
+		super(content);
+
+		this.parents = parents;
+	}
+
+	public List<RevCommit> getParents() {
+		return parents;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MessageCommitInfo.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MessageCommitInfo.java
new file mode 100644
index 0000000000..bd62f0e396
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MessageCommitInfo.java
@@ 0,0 +1,41 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.util.Date;
+import java.util.TimeZone;
+
+public class MessageCommitInfo extends CommitInfo {
+
+	public static final String MERGE_MESSAGE = "Merge branch '%s'";
+	public static final String REVERT_MERGE_MESSAGE = "Revert merge from branch '%s'";
+	public static final String FIX_REVERT_MERGE_MESSAGE = "Fix after merge reversion";
+
+	public MessageCommitInfo(final String message) {
+		this(null, null, null, message, null, null);
+	}
+
+	private MessageCommitInfo(final String sessionId, final String name, final String email, final String message,
+			final TimeZone timeZone, final Date when) {
+		super(sessionId, name, email, message, timeZone, when);
+	}
+
+	public static MessageCommitInfo createMergeMessage(final String sourceBranch) {
+		return new MessageCommitInfo(String.format(MERGE_MESSAGE, sourceBranch));
+	}
+
+	public static MessageCommitInfo createRevertMergeMessage(final String sourceBranch) {
+		return new MessageCommitInfo(String.format(REVERT_MERGE_MESSAGE, sourceBranch));
+	}
+
+	public static MessageCommitInfo createFixMergeReversionMessage() {
+		return new MessageCommitInfo(FIX_REVERT_MERGE_MESSAGE);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MoveCommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MoveCommitContent.java
new file mode 100644
index 0000000000..c32d376581
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/MoveCommitContent.java
@@ 0,0 +1,25 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import java.util.Map;
+
+public class MoveCommitContent implements CommitContent {
+
+	private final Map<String, String> content;
+
+	public MoveCommitContent(final Map<String, String> content) {
+		this.content = content;
+	}
+
+	public Map<String, String> getContent() {
+		return content;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathInfo.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathInfo.java
new file mode 100644
index 0000000000..ed6545a2b9
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathInfo.java
@@ 0,0 +1,67 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import org.eclipse.jgit.lib.FileMode;
+import org.eclipse.jgit.lib.ObjectId;
+
+import static org.eclipse.jgit.lib.FileMode.TYPE_FILE;
+
+public class PathInfo {
+
+	private final long size;
+	private final ObjectId objectId;
+	private final String path;
+	private final PathType pathType;
+
+	public PathInfo(final ObjectId objectId, final String path, final FileMode fileMode) {
+		this(objectId, path, convert(fileMode), 1);
+	}
+
+	public PathInfo(final ObjectId objectId, final String path, final FileMode fileMode, final long size) {
+		this(objectId, path, convert(fileMode));
+	}
+
+	public PathInfo(final ObjectId objectId, final String path, final PathType pathType) {
+		this(objectId, path, pathType, 1);
+	}
+
+	public PathInfo(final ObjectId objectId, final String path, final PathType pathType, final long size) {
+		this.objectId = objectId;
+		this.path = path;
+		this.pathType = pathType;
+		this.size = size;
+	}
+
+	private static PathType convert(final FileMode fileMode) {
+		if (fileMode.equals(FileMode.TYPE_TREE)) {
+			return PathType.DIRECTORY;
+		} else if (fileMode.equals(TYPE_FILE)) {
+			return PathType.FILE;
+		}
+		return null;
+	}
+
+	public ObjectId getObjectId() {
+		return objectId;
+	}
+
+	public String getPath() {
+		return path;
+	}
+
+	public PathType getPathType() {
+		return pathType;
+	}
+
+	public long getSize() {
+		return size;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathType.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathType.java
new file mode 100644
index 0000000000..0435387f1e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/PathType.java
@@ 0,0 +1,14 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+public enum PathType {
+	NOT_FOUND, DIRECTORY, FILE
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/RevertCommitContent.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/RevertCommitContent.java
new file mode 100644
index 0000000000..25cec606ed
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/RevertCommitContent.java
@@ 0,0 +1,23 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+public class RevertCommitContent implements CommitContent {
+
+	private final String refTree;
+
+	public RevertCommitContent(final String refTree) {
+		this.refTree = refTree;
+	}
+
+	public String getRefTree() {
+		return refTree;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/TextualDiff.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/TextualDiff.java
new file mode 100644
index 0000000000..104f1cdbd8
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/op/model/TextualDiff.java
@@ 0,0 +1,58 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.op.model;
+
+import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
+
+public class TextualDiff {
+
+	private String oldFilePath;
+	private String newFilePath;
+	private String changeType;
+	private int linesAdded;
+	private int linesDeleted;
+	private String diffText;
+
+	public TextualDiff(final String oldFilePath, final String newFilePath, final String changeType,
+			final int linesAdded, final int linesDeleted, final String diffText) {
+		this.oldFilePath = checkNotEmpty("oldFilePath", oldFilePath);
+		this.newFilePath = checkNotEmpty("newFilePath", newFilePath);
+		this.changeType = checkNotEmpty("changeType", changeType);
+
+		this.linesAdded = linesAdded;
+		this.linesDeleted = linesDeleted;
+
+		this.diffText = checkNotEmpty("diffText", diffText);
+	}
+
+	public String getOldFilePath() {
+		return oldFilePath;
+	}
+
+	public String getNewFilePath() {
+		return newFilePath;
+	}
+
+	public String getChangeType() {
+		return changeType;
+	}
+
+	public int getLinesAdded() {
+		return linesAdded;
+	}
+
+	public int getLinesDeleted() {
+		return linesDeleted;
+	}
+
+	public String getDiffText() {
+		return diffText;
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/AuthenticationService.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/AuthenticationService.java
new file mode 100644
index 0000000000..0d70381c63
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/AuthenticationService.java
@@ 0,0 +1,34 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.security;
+
+/**
+ * AuthenticationService service for authenticating users and getting their
+ * roles.
+ * 
+ * @author edewit@redhat.com
+ */
+public interface AuthenticationService {
+
+	User login(String username, String password);
+
+	/**
+	 * @return True iff the user is currently logged in.
+	 */
+	boolean isLoggedIn();
+
+	/**
+	 * Log out the currently authenticated user. Has no effect if there is no
+	 * current user.
+	 */
+	void logout();
+
+	User getUser();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/FileSystemAuthorization.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/FileSystemAuthorization.java
new file mode 100644
index 0000000000..7b389eb192
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/FileSystemAuthorization.java
@@ 0,0 +1,28 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.security;
+
+import java.nio.file.FileSystem;
+
+/**
+ * Strategy for authorizing users to perform actions in a secured file system.
+ */
+public interface FileSystemAuthorization {
+
+	/**
+	 * Returns true if the given user is permitted to perform actions within the
+	 * given file system.
+	 * 
+	 * @param fs
+	 * @param user
+	 * @return
+	 */
+	boolean authorize(final FileSystem fs, final User user);
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/PublicKeyAuthenticator.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/PublicKeyAuthenticator.java
new file mode 100644
index 0000000000..8b4037da5e
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/PublicKeyAuthenticator.java
@@ 0,0 +1,17 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.security;
+
+import java.security.PublicKey;
+
+public interface PublicKeyAuthenticator {
+
+	User authenticate(String userName, PublicKey key);
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/User.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/User.java
new file mode 100644
index 0000000000..a5c0a49c35
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/security/User.java
@@ 0,0 +1,15 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.security;
+
+public interface User {
+
+	String getIdentifier();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveRunnable.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveRunnable.java
new file mode 100644
index 0000000000..fea5d5bfa0
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveRunnable.java
@@ 0,0 +1,15 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.util;
+
+public interface DescriptiveRunnable extends Runnable {
+
+	String getDescription();
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveThreadFactory.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveThreadFactory.java
new file mode 100644
index 0000000000..97c73bbf93
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/DescriptiveThreadFactory.java
@@ 0,0 +1,23 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.util;
+
+import java.util.concurrent.ThreadFactory;
+
+public class DescriptiveThreadFactory implements ThreadFactory {
+
+	@Override
+	public Thread newThread(final Runnable r) {
+		if (r instanceof DescriptiveRunnable) {
+			return new Thread(r, ((DescriptiveRunnable) r).getDescription());
+		}
+		return new Thread(r);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/EncodingUtil.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/EncodingUtil.java
new file mode 100644
index 0000000000..2d71ea609c
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/EncodingUtil.java
@@ 0,0 +1,424 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.util;
+
+import java.io.UnsupportedEncodingException;
+import java.util.BitSet;
+
+import org.apache.commons.codec.DecoderException;
+import org.apache.commons.codec.net.URLCodec;
+
+/**
+ * The URIUtil class that was available in commonshttpclient 3.x was retired
+ * when httpclient moved to the 4.x branch.
+ * <p>
+ * See http://marc.info/?l=httpclientusers&m=125425095705062&w=2 for more
+ * informatoin.
+ */
+public class EncodingUtil {
+
+	/**
+	 * Those characters that are allowed for the abs_path.
+	 */
+	public static final BitSet allowed_abs_path = new BitSet(256);
+
+	// bitsets
+	// 
+	/**
+	 * The percent "%" character always has the reserved purpose of being the escape
+	 * indicator, it must be escaped as "%25" in order to be used as data within a
+	 * URI.
+	 */
+	protected static final BitSet percent = new BitSet(256);
+	/**
+	 * BitSet for digit.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * digit = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet digit = new BitSet(256);
+	/**
+	 * BitSet for alpha.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * alpha = lowalpha | upalpha
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet alpha = new BitSet(256);
+	/**
+	 * BitSet for alphanum (join of alpha &amp; digit).
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * alphanum = alpha | digit
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet alphanum = new BitSet(256);
+	/**
+	 * BitSet for hex.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * hex = digit | "A" | "B" | "C" | "D" | "E" | "F" | "a" | "b" | "c" | "d" | "e" | "f"
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet hex = new BitSet(256);
+	/**
+	 * BitSet for escaped.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * escaped       = "%" hex hex
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet escaped = new BitSet(256);
+	/**
+	 * BitSet for mark.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * mark = "" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet mark = new BitSet(256);
+	/**
+	 * Data characters that are allowed in a URI but do not have a reserved purpose
+	 * are called unreserved.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * unreserved = alphanum | mark
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet unreserved = new BitSet(256);
+	/**
+	 * BitSet for pchar.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * pchar = unreserved | escaped | ":" | "@" | "&amp;" | "=" | "+" | "$" | ","
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet pchar = new BitSet(256);
+	/**
+	 * BitSet for param (alias for pchar).
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * param         = *pchar
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet param = pchar;
+	/**
+	 * BitSet for segment.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * segment       = *pchar *( ";" param )
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet segment = new BitSet(256);
+	/**
+	 * BitSet for path segments.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * path_segments = segment *( "/" segment )
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet path_segments = new BitSet(256);
+	/**
+	 * URI absolute path.
+	 * <p>
+	 * <blockquote>
+	 * 
+	 * <pre>
+	 * abs_path      = "/"  path_segments
+	 * </pre>
+	 * 
+	 * </blockquote>
+	 * <p>
+	 */
+	protected static final BitSet abs_path = new BitSet(256);
+
+	// Static initializer for percent
+	static {
+		percent.set('%');
+	}
+
+	// Static initializer for digit
+	static {
+		for (int i = '0'; i <= '9'; i++) {
+			digit.set(i);
+		}
+	}
+
+	// Static initializer for alpha
+	static {
+		for (int i = 'a'; i <= 'z'; i++) {
+			alpha.set(i);
+		}
+		for (int i = 'A'; i <= 'Z'; i++) {
+			alpha.set(i);
+		}
+	}
+
+	// Static initializer for alphanum
+	static {
+		alphanum.or(alpha);
+		alphanum.or(digit);
+	}
+
+	// Static initializer for hex
+	static {
+		hex.or(digit);
+		for (int i = 'a'; i <= 'f'; i++) {
+			hex.set(i);
+		}
+		for (int i = 'A'; i <= 'F'; i++) {
+			hex.set(i);
+		}
+	}
+
+	// Static initializer for escaped
+	static {
+		escaped.or(percent);
+		escaped.or(hex);
+	}
+
+	// Static initializer for mark
+	static {
+		mark.set('');
+		mark.set('_');
+		mark.set('.');
+		mark.set('!');
+		mark.set('~');
+		mark.set('*');
+		mark.set('\'');
+		mark.set('(');
+		mark.set(')');
+	}
+
+	// Static initializer for unreserved
+	static {
+		unreserved.or(alphanum);
+		unreserved.or(mark);
+	}
+
+	// Static initializer for pchar
+	static {
+		pchar.or(unreserved);
+		pchar.or(escaped);
+		pchar.set(':');
+		pchar.set('@');
+		pchar.set('&');
+		pchar.set('=');
+		pchar.set('+');
+		pchar.set('$');
+		pchar.set(',');
+	}
+
+	// Static initializer for segment
+	static {
+		segment.or(pchar);
+		segment.set(';');
+		segment.or(param);
+	}
+
+	// Static initializer for path_segments
+	static {
+		path_segments.set('/');
+		path_segments.or(segment);
+	}
+
+	// Static initializer for abs_path
+	static {
+		abs_path.set('/');
+		abs_path.or(path_segments);
+	}
+
+	// Static initializer for allowed_abs_path
+	static {
+		allowed_abs_path.or(abs_path);
+		// allowed_abs_path.set('/'); // aleady included
+		allowed_abs_path.andNot(percent);
+		allowed_abs_path.clear('+');
+	}
+
+	private EncodingUtil() {
+		// utility class, does not need a constructor
+	}
+
+	// methods
+	// 
+
+	/**
+	 * Escape and encode a string regarded as the path component of an URI with the
+	 * default protocol charset.
+	 * 
+	 * @param unescaped an unescaped string
+	 * @return the escaped string
+	 */
+	public static String encodePath(String unescaped) {
+		byte[] rawdata = URLCodec.encodeUrl(allowed_abs_path, getBytes(unescaped, "UTF8"));
+		return getAsciiString(rawdata);
+	}
+
+	/**
+	 * Converts the specified string to a byte array. If the charset is not
+	 * supported the default system charset is used.
+	 * 
+	 * @param data    the string to be encoded
+	 * @param charset the desired character encoding
+	 * @return The resulting byte array.
+	 */
+	public static byte[] getBytes(final String data, String charset) {
+		if (data == null) {
+			throw new IllegalArgumentException("data may not be null");
+		}
+
+		if (charset == null || charset.length() == 0) {
+			throw new IllegalArgumentException("charset may not be null or empty");
+		}
+
+		try {
+			return data.getBytes(charset);
+		} catch (UnsupportedEncodingException e) {
+			return data.getBytes();
+		}
+	}
+
+	/**
+	 * Converts the byte array of ASCII characters to a string. This method is to be
+	 * used when decoding content of HTTP elements (such as response headers)
+	 * 
+	 * @param data the byte array to be encoded
+	 * @return The string representation of the byte array
+	 */
+	public static String getAsciiString(final byte[] data) {
+		if (data == null) {
+			throw new IllegalArgumentException("Parameter may not be null");
+		}
+
+		try {
+			return new String(data, 0, data.length, "USASCII");
+		} catch (UnsupportedEncodingException e) {
+			throw new IllegalStateException(EncodingUtil.class.getSimpleName() + " requires ASCII support");
+		}
+	}
+
+	/**
+	 * Converts the specified string to byte array of ASCII characters.
+	 * 
+	 * @param data the string to be encoded
+	 * @return The string as a byte array.
+	 */
+	public static byte[] getAsciiBytes(final String data) {
+		if (data == null) {
+			throw new IllegalArgumentException("Parameter may not be null");
+		}
+
+		try {
+			return data.getBytes("USASCII");
+		} catch (UnsupportedEncodingException e) {
+			throw new IllegalStateException(EncodingUtil.class.getSimpleName() + " requires ASCII support");
+		}
+	}
+
+	/**
+	 * Converts the byte array of HTTP content characters to a string. If the
+	 * specified charset is not supported, default system encoding is used.
+	 * 
+	 * @param data    the byte array to be encoded
+	 * @param charset the desired character encoding
+	 * @return The result of the conversion.
+	 */
+	public static String getString(final byte[] data, String charset) {
+		if (data == null) {
+			throw new IllegalArgumentException("Parameter may not be null");
+		}
+
+		if (charset == null || charset.length() == 0) {
+			throw new IllegalArgumentException("charset may not be null or empty");
+		}
+
+		try {
+			return new String(data, 0, data.length, charset);
+		} catch (UnsupportedEncodingException e) {
+			return new String(data, 0, data.length);
+		}
+	}
+
+	/**
+	 * Unescape and decode a given string regarded as an escaped string with the
+	 * UTF8 protocol charset.
+	 * 
+	 * @param escaped a string
+	 * @return the unescaped string
+	 * @throws IllegalStateException if the escaped string is not a correct URL
+	 */
+	public static String decode(String escaped) {
+		byte[] asciiData = getAsciiBytes(escaped);
+		byte[] rawdata;
+		try {
+			rawdata = URLCodec.decodeUrl(asciiData);
+		} catch (DecoderException e) {
+			throw new IllegalStateException(e.getMessage());
+		}
+		return getString(rawdata, "UTF8");
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/Preconditions.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/Preconditions.java
new file mode 100644
index 0000000000..315a4c9b24
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/Preconditions.java
@@ 0,0 +1,150 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.util;
+
+import java.util.Collection;
+import java.util.Map;
+
+/**
+ * Helper class for parameters validation, such as not null arguments.
+ */
+public class Preconditions {
+
+	/**
+	 * Should not be instantiated
+	 */
+	protected Preconditions() {
+		throw new IllegalStateException("This class should be not instantiated!");
+	}
+
+	/**
+	 * Assert that this parameter is marked as valid by the condition passed as
+	 * parameter.
+	 * 
+	 * @param name      of parameter
+	 * @param condition itself
+	 */
+	public static void checkCondition(final String name, final boolean condition) {
+		if (!condition) {
+			throw new IllegalStateException("Condition '" + name + "' is invalid!");
+		}
+	}
+
+	/**
+	 * Assert that this parameter is not null, as also each item of the array is not
+	 * null.
+	 * 
+	 * @param <T>        parameter type
+	 * @param name       of parameter
+	 * @param parameters itself
+	 */
+	public static <T> void checkEachParameterNotNull(final String name, final T... parameters) {
+		if (parameters == null) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
+		}
+		for (final Object parameter : parameters) {
+			if (parameter == null) {
+				throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
+			}
+		}
+	}
+
+	/**
+	 * Assert that this parameter is not empty. It will test for null and also the
+	 * size of this array.
+	 * 
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static <T extends Collection<?>> T checkNotEmpty(final String name, final T parameter) {
+		if (parameter == null || parameter.size() == 0) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
+		}
+		return parameter;
+	}
+
+	/**
+	 * Assert that this parameter is not empty. It will test for null and also the
+	 * size of this array.
+	 * 
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static void checkNotEmpty(final String name, final Map<?, ?> parameter) {
+		if (parameter == null || parameter.size() == 0) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
+		}
+	}
+
+	/**
+	 * Assert that this parameter is not empty. It trims the parameter to see if
+	 * have any valid data on that.
+	 * 
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static String checkNotEmpty(final String name, final String parameter) {
+		if (parameter == null || parameter.trim().length() == 0) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
+		}
+		return parameter;
+	}
+
+	/**
+	 * Assert that this parameter is not empty. It will test for null and also the
+	 * size of this array.
+	 * 
+	 * @param <T>       type of the array
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static <T> T[] checkNotEmpty(final String name, final T[] parameter) {
+		if (parameter == null || parameter.length == 0) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
+		}
+		return parameter;
+	}
+
+	/**
+	 * Assert that this parameter is not null.
+	 * 
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static <T> T checkNotNull(final String name, final T parameter) {
+		if (parameter == null) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
+		}
+		return parameter;
+	}
+
+	/**
+	 * Assert that this parameter is null.
+	 * 
+	 * @param name      of parameter
+	 * @param parameter itself
+	 */
+	public static void checkNullMandatory(final String name, final Object parameter) {
+		if (parameter != null) {
+			throw new IllegalArgumentException("Parameter named '" + name + "' should be null!");
+		}
+	}
+
+	public static <T> T checkInstanceOf(final String name, Object parameter, final Class<T> clazz) {
+		checkNotNull(name, parameter);
+		checkNotNull("clazz", clazz);
+		if (!clazz.isInstance(parameter)) {
+			throw new IllegalArgumentException(
+					"Parameter named '" + name + "' is not instance of clazz '" + clazz.getName() + "'!");
+		}
+
+		return clazz.cast(parameter);
+	}
+}
diff git a/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/ThrowableSupplier.java b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/ThrowableSupplier.java
new file mode 100644
index 0000000000..01e582e30a
 /dev/null
+++ b/org.eclipse.jgit.niofs/src/org/eclipse/jgit/niofs/internal/util/ThrowableSupplier.java
@@ 0,0 +1,26 @@
+/*
+ * Copyright 2019 Red Hat, Inc. and/or its affiliates.
+ *
+ * This program and the accompanying materials are made available under the
+ * terms of the Eclipse Distribution License v. 1.0 which is available at
+ * https://www.eclipse.org/org/documents/edlv10.php.
+ *
+ * SPDXLicenseIdentifier: BSD3Clause
+ */
+package org.eclipse.jgit.niofs.internal.util;
+
+/**
+ * Represents a supplier of results.
+ * <p>
+ * Borrowed from java 8 java.util.function, just added `throws` support.
+ */
+@FunctionalInterface
+public interface ThrowableSupplier<T> {
+
+	/**
+	 * Gets a result.
+	 * 
+	 * @return a result
+	 */
+	T get() throws Throwable;
+}
diff git a/org.eclipse.jgit.packaging/org.eclipse.jgit.target/orbit/R20200224183213202003.tpd b/org.eclipse.jgit.packaging/org.eclipse.jgit.target/orbit/R20200224183213202003.tpd
index b3b360029b..fb0977310d 100644
 a/org.eclipse.jgit.packaging/org.eclipse.jgit.target/orbit/R20200224183213202003.tpd
+++ b/org.eclipse.jgit.packaging/org.eclipse.jgit.target/orbit/R20200224183213202003.tpd
@@ 24,6 +24,8 @@ location "https://download.eclipse.org/tools/orbit/downloads/drops/R202002241832
 	org.apache.commons.codec.source [1.13.0.v202001080001,1.13.0.v202001080001]
 	org.apache.commons.compress [1.19.0.v202001062343,1.19.0.v202001062343]
 	org.apache.commons.compress.source [1.19.0.v202001062343,1.19.0.v202001062343]
+	org.apache.commons.io [2.6.0.v201901232029,2.6.0.v201901232029]
+	org.apache.commons.io.source [2.6.0.v201901232029,2.6.0.v201901232029]
 	org.apache.commons.logging [1.2.0.v201804091502,1.2.0.v201804091502]
 	org.apache.commons.logging.source [1.2.0.v201804091502,1.2.0.v201804091502]
 	org.apache.httpcomponents.httpclient [4.5.10.v202001141512,4.5.10.v202001141512]
diff git a/org.eclipse.jgit.ssh.apache/pom.xml b/org.eclipse.jgit.ssh.apache/pom.xml
index 4eb9cc747a..e82961fa10 100644
 a/org.eclipse.jgit.ssh.apache/pom.xml
+++ b/org.eclipse.jgit.ssh.apache/pom.xml
@@ 43,13 +43,11 @@
     <dependency>
       <groupId>org.apache.sshd</groupId>
       <artifactId>sshdosgi</artifactId>
      <version>${apachesshdversion}</version>
     </dependency>
 
     <dependency>
       <groupId>org.apache.sshd</groupId>
       <artifactId>sshdsftp</artifactId>
      <version>${apachesshdversion}</version>
     </dependency>
 
     <dependency>
diff git a/org.eclipse.jgit.test/pom.xml b/org.eclipse.jgit.test/pom.xml
index 100bd11e14..11bf0f221a 100644
 a/org.eclipse.jgit.test/pom.xml
+++ b/org.eclipse.jgit.test/pom.xml
@@ 73,7 +73,6 @@
     <dependency>
       <groupId>org.mockito</groupId>
       <artifactId>mockitocore</artifactId>
      <version>2.23.0</version>
     </dependency>
 
     <dependency>
diff git a/pom.xml b/pom.xml
index 73ab762236..bf365a929b 100644
 a/pom.xml
+++ b/pom.xml
@@ 179,6 +179,7 @@
     <mavensurefirereportpluginversion>${mavensurefirepluginversion}</mavensurefirereportpluginversion>
     <mavencompilerpluginversion>3.8.1</mavencompilerpluginversion>
     <plexuscompilerversion>2.8.8</plexuscompilerversion>
+    <bytemanversion>4.0.6</bytemanversion>
 
     <! Properties to enable jacoco code coverage analysis >
     <sonar.core.codeCoveragePlugin>jacoco</sonar.core.codeCoveragePlugin>
@@ 391,6 +392,11 @@
           <artifactId>springbootmavenplugin</artifactId>
           <version>2.1.5.RELEASE</version>
         </plugin>
+        <plugin>
+          <groupId>org.jboss.byteman</groupId>
+          <artifactId>bytemanrulecheckmavenplugin</artifactId>
+          <version>${bytemanversion}</version>
+        </plugin>
       </plugins>
     </pluginManagement>
 
@@ 776,6 +782,66 @@
         <version>${bouncycastleversion}</version>
       </dependency>
 
+      <dependency>
+        <groupId>org.mockito</groupId>
+        <artifactId>mockitocore</artifactId>
+        <version>2.23.0</version>
+      </dependency>
+
+      <dependency>
+        <groupId>commonscodec</groupId>
+        <artifactId>commonscodec</artifactId>
+        <version>1.10</version>
+      </dependency>
+
+      <dependency>
+        <groupId>commonsio</groupId>
+        <artifactId>commonsio</artifactId>
+        <version>2.6</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.apache.sshd</groupId>
+        <artifactId>sshdosgi</artifactId>
+        <version>${apachesshdversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.apache.sshd</groupId>
+        <artifactId>sshdscp</artifactId>
+        <version>${apachesshdversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.apache.sshd</groupId>
+        <artifactId>sshdsftp</artifactId>
+        <version>${apachesshdversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.jboss.byteman</groupId>
+        <artifactId>byteman</artifactId>
+        <version>${bytemanversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.jboss.byteman</groupId>
+        <artifactId>bytemansubmit</artifactId>
+        <version>${bytemanversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.jboss.byteman</groupId>
+        <artifactId>bytemaninstall</artifactId>
+        <version>${bytemanversion}</version>
+      </dependency>
+
+      <dependency>
+        <groupId>org.jboss.byteman</groupId>
+        <artifactId>bytemanbmunit</artifactId>
+        <version>${bytemanversion}</version>
+      </dependency>
+
       <dependency>
         <groupId>org.assertj</groupId>
         <artifactId>assertjcore</artifactId>
@@ 988,6 +1054,7 @@
     <module>org.eclipse.jgit.junit</module>
     <module>org.eclipse.jgit.junit.http</module>
     <module>org.eclipse.jgit.junit.ssh</module>
+    <module>org.eclipse.jgit.niofs</module>
 
     <module>org.eclipse.jgit.test</module>
     <module>org.eclipse.jgit.ant.test</module>
@@ 997,7 +1064,12 @@
     <module>org.eclipse.jgit.lfs.test</module>
     <module>org.eclipse.jgit.lfs.server.test</module>
     <module>org.eclipse.jgit.ssh.apache.test</module>
+<<<<<<< HEAD
     <module>org.eclipse.jgit.ssh.jsch.test</module>
+=======
+    <module>org.eclipse.jgit.niofs.test</module>
+
+>>>>>>> [WIP] NIO2 filesystem based on JGit storage
     <module>org.eclipse.jgit.coverage</module>
     <module>org.eclipse.jgit.benchmarks</module>
   </modules>
