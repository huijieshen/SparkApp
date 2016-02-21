addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.6.8")

resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Spray Repository" at "http://repo.spray.cc/"

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.10.2")

addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.3.3")