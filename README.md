Author: @caeus
#### The problem
You know, I'm the kind of guy who enjoys a new technology as long as he understands the design decisions/philosophy/data model behind it. I've tried more than once, I can assure you, to do non trivial stuff with SBT and I always end up promising myself to read SBT documentation, and when I finally do, I stumble upon this ultra complex data model based on scopes, SettingKeys, TaskKeys, that for me, looks more like a messy workaround, than a smart design. Li Haoyi placed it nicely in his article ["So, what's wrong with sbt?"](http://www.lihaoyi.com/post/SowhatswrongwithSBT.html) by saying that the real problem of SBT was not it's whimsical and convoluted syntax, more than it was the underlying model that supports it.

I'm going to give my own understanding of the problem, and try to use his proposed solution (mill) to see if we can use it here (in Lunatech), what we'd have to do to start using it, or if, ultimately, we need to stick with SBT :(

That being said, for me, the biggest problem is that makes you mix two different language in the same file without you noticing it. Look, many would say that it's Scala, but it's not. an SBT is both, the SBT language instructions, and Scala manipulating them. Check this.

```scala
lazy val sbtInstruction1 = (scalacOptions := Seq("-Ywarn-unused:imports"))
lazy val sbtInstruction2  = (scalacOptions += "-Ypartial-unification")

lazy val root=(project in file("."))
.settings(
    sbtInstruction2,
    sbtInstruction1)
```

There's something wrong with that example and it's basically that the order in which you create settings (sbt instructions) is not the order in which those instructions will be interpreted. In that example you're actually using SBT assignment operator `:=` and Scala assignment statement. To know what's gonna happen here you don't only need to know how scala works, you also need to know how the sbt `Setting`s interpreter works.

You see, you can actually create binding in sbt as normal Scala code `val number = 5`, but you can also use the `:=` operator that `TaskKey`s and `SettingKey`s have. SettingKeys are actually, in analogy to scala, a `var` and `TaskKey`s would be like methods (if they were redefinable in scala).  So yes, you use scala in a pretty functional way (with immutability and referential transparency in mind), but you're being hidden the fact that the sbt embedded language is super NON functional, everything is mutable and well, it's just a mess.

I like Scala because it's typesafe, more than I like it for it's proneness to be functional. Yes, I like the compilers help and the way you can abstract problems into types and a couple of relations between them (functions). When you do a really good job abstracting of the problems you want to solve, in a language with such a good type system, you can end up making it very difficult for the developer to create buggy code, because well, buggy code won't just compile. Illegal states such as an Http GET request with body, or an OAuth2 authentication mechanism created with Basic auth data. Compiler should just reject that stuff. It doesn't mean you can't. You can do inherently type unsafe stuff in Scala if you don't take advantage of their type system... Like SBT. You can do irrational stuff in sbt because SBT as a language is not as typesafe as scala is. It gets partially benefited from Scala's type system, but it still leaves open for developers to do nonsensical stuff like `scalacOptions in Runtime`; that makes no sense whatsoever but it's valid SBT.

Among more problems, like a flat scope for variables (so if you create a plugin with its own `Key`s, you need to pray to satan you named them differently from all the other plugins in the universe of SBT), the real problem is the mixing of two different languages and two different layers of abstraction living together in one file.

Anyway, just go and read the article, it's super cool, and super explanatory. I'm pretty sure you'll understand SBT better by reading it... and then hate it for the right reasons.

Mill tackles those problems taking ideas from other build systems that Li Haoyi found cool. There's only one layer of abstraction living in the build file, which helps to create the second layer. Not two living there, to create the third (like SBT). It just leverages the scala language and the ammonite scala console (made by Li Haoyi himself), to do a very straightforward and proactively designed tool for building ANY kind of project (meaning scala projects, mainly).

That being said, I will just try to use mill to create a project of my own, to see how it falls short and where it shines.

### The project
I won't explain the details of the project because this project is mainly about exploring Mill. Just assume it's a normal RESTful server with UI.

### The journal...

- First day, there's no "mill new" command, darn.
- Playframework is an sbt plugin, Akka Http will be.
- Intellij support is done via a command in mill that creates the idea files, it works well, but I need to run that command every time I change the build.
- Debugging Mill is amazingly easy... So much time dealing with sbt that I thought build systems were inherently difficult to debug. Not with Mill, I can just see the source code, method calls, everything, and because it's just scala, Intellij helps a lot.
- It's more verbose for simple commands because it works on the idea that every project is multimodule, so if I want to compile my main module I need to prefix my command with the name of my main module.
- File structure is new compared to maven/sbt project structures... A different folder for each project, no language specific folders. That can make really difficult migrating from SBT to Mill.
- I haven't checked if it works ok if I also have java files.
- Testing looks to be kinda easy, but there's a problem here. The highly coupled nature of libraries like Akka (http, streams and everything else), makes it really difficult to test them without using a test framework different from scalatest or specs2. As Li Haoyi recommends uTest (which I recommend too), it's difficult to find examples of how to use it with scalatest. And that actually enlightens me about a new problem... Community around mill is small and young. It will be a while since it actually becomes a relevant competitor or sbt.
- There are no templates for .gitignore, but it's pretty stupid to complain about it
- It doesn't mix (and it splits very explicitely), concepts from other build tools such as maven dependency scopes. Here you have runIvyDeps, compileIvyDeps, and ivyDeps (which are used both at compile time, and run time). Doing this I also got to test the resolving skills this have. I didn't have much of a change to do it before because I checked task dependencies by looking at the sourcecode, which reminds me of...
- Jumping to the relevant part of the sourcecode of mill is super easy. Good luck with SBT.
- I've come to understand better how builds work as I can explore much more easily the sourcecode on which mill is build upon.
- Given that modules are just plain scala objects that extend the mill.Module class, I can already tell that you cannot do hot replacement of variables [as in SBT](https://www.scala-sbt.org/1.x/docs/Command-Line-Reference.html#Commands+for+managing+the+build+definition). That's because once created an object, it cannot be modified. But who uses that anyway?


### Conclusion

So I haven't got the chance to test everything but I certainly like this build system. Mainly I liked the design decisions and the fact that it's just plain Scala. Easy to navigate the sourcecode, easy to extend and very very very straightforward. I would have liked to create a custom Task, but I didn't have the time. Yet, as I was thinking of integrating webpack for the UI, I stumbled upon [this](https://twitter.com/li_haoyi/status/969776409924153346) and so I assume it will be equally easy.
I faced many problems that all can be reduced to the fact that it's a young tool. Yet I'm confided it will replace SBT. (Fixing SBT looks too difficult to achieve)












