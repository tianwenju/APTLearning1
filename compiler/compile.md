`apply plugin: 'java'
 sourceCompatibility = 1.7 
 targetCompatibility = 1.7 
 dependencies {
     compile fileTree(dir: 'libs', include: ['*.jar'])
     compile 'com.google.auto.service:auto-service:1.0-rc2'
     compile 'com.squareup:javapoet:1.7.0'
     compile project(':annotation')
 }`
1. 定义编译的jdk版本为1.7，这个很重要，不写会报错。
2. AutoService 主要的作用是注解 processor 类，并对其生成 META-INF 的配置信息。
3. JavaPoet 这个库的主要作用就是帮助我们通过类调用的形式来生成代码。
4. 依赖上面创建的annotation Module。 