# Hello World app in SpringBoot compiled with GraalVM

## Dependencies
* **Important**: Because of [#1057](https://github.com/oracle/graal/issues/1057) this tutorial is only runable on Linux machines. (native image agent is only available for Linux) 
* Install GraalVM from here https://www.graalvm.org/ (Used version **20.0.0**)
    * (For Mac OSX versions > Catalina: Remove quarantine from the bits, [info](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-21.0.0.2))  
    `$ sudo xattr -r -d com.apple.quarantine path/to/graalvm/folder/`
* Configure your env 
	* Prepend the GraalVM bin directory to the **PATH** environment variable:  
	  `$ export PATH=<path to GraalVM>/Contents/Home/bin:$PATH`.  
	   To verify whether you are using GraalVM, run: `$ which java`
	* Set the **JAVA_HOME** environment variable to resolve to the GraalVM installation directory:  
	 `$ export JAVA_HOME=<path to GraalVM>/Contents/Home`
	* (Specify GraalVM as the JRE or JDK installation in your Java IDE) 

## Version Information
* GraalVM **20.0.0** was the latest version available to download. Newer releases should have fix for Bug https://github.com/oracle/graal/issues/2198
* SpringBoot **2.3.x** was needed for GraalVM native-image support

## Benchmark
The application is very simple (just giving out "Hello World" at `localhost:8080` but nevertheless the performance difference is significant:
Spring Boot JVM  | Spring Boot Native
------------- | -------------
Startup: Started GraalDemoApplication in 1.305 seconds (JVM running for 1.644)  | Startup: Started GraalDemoApplication in 0.066 seconds (JVM running for 0.068)
RAM Usage: ca 502.9 MB  | RAM Usage: ca 37 MB
Size: 16 MB | Size: 89 MB


## Compile
The following steps are needed to compile the spring boot application to native code with GraalVm

```
mvn -DskipTests clean package
export MI=src/main/resources/META-INF
mkdir -p $MI 
java -agentlib:native-image-agent=config-output-dir=${MI}/native-image -jar target/<YOUR_APP_ARTIFACT>.jar

## Due to https://github.com/oracle/graal/issues/2198 you have to add a class to reflect-config.json. Maybe its fixed in GraalVM version > 20.0.0
{
 "name":"org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl",
 "allDeclaredConstructors":true,
 "allDeclaredMethods":true
},

## Check if the application is working correctly: http://localhost:8080
## then hit CTRL + C to stop the running application.

tree $MI
mvn -Pgraal clean package
```

## Start the application
After successfully compiling the application you can start the application with
`./target/de.wandi34.graaldemo.graaldemoapplication`
## Resources
Application was built with following documentations:
* https://spring.io/blog/2020/04/16/spring-tips-the-graalvm-native-image-builder-feature
* https://repo.spring.io/milestone/org/springframework/experimental/spring-graal-native-docs/0.6.0.RELEASE/spring-graal-native-docs-0.6.0.RELEASE.zip!/reference/index.html
* https://github.com/oracle/graal/issues/2198
